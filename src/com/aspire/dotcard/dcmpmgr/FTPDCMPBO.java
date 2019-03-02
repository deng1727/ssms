
package com.aspire.dotcard.dcmpmgr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

/**
 * DCMP资讯内容数据同步BO类
 * 
 * @author zhangwei
 * 
 */
public class FTPDCMPBO
{

    /**
     * 日志引用
     */
    private static JLogger logger = LoggerFactory.getLogger(FTPDCMPBO.class);

    private static JLogger syncDataLog = LoggerFactory.getLogger("FTP-DCMP-Log");

    /**
     * 工具类，因此构造方法私有
     */
    private FTPDCMPBO()
    {

    }

    /**
     * DCMP数据文件ftp获取的方法
     * 
     * @param FullDataTime String
     * @param RefDataTime String
     * @return int 执行结果标志，-1表示失败，1表示成功
     */
    public static int syncNewsFromDCMP()
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("syncDataFromDCMP is beginning......");
        }
        String startTime = getCurDateTime("yyyy-MM-dd HH:mm:ss");
        FTPClient ftp = null;
        try
        {
            ftp = getFTPClient();
            // 取得远程目录中文件列表
            String curDateDir = getCurDateTime("yyyyMMdd");
            // 从配置项读取FTP服务器上DCMP存放目录
            String dateDir = FTPDCMPConfig.getInstance().getDateDir();
            String[] remotefiles;
            List fileList = new ArrayList();
            try
            {
                remotefiles = ftp.dir(dateDir + "/" + curDateDir);
                if (remotefiles.length == 0)
                {
                    throw new RuntimeException("文件夹:" + dateDir + "/"
                                               + curDateDir + "没有媒体文件异常！");
                }

                // 循环读取每一个文件并进行解析，生成相应的vo类保存在list中
                // 目前只有一个类型文件，所以都要放入fileList中，如果将来增加新的文件，更改某类型文件的获取方式
                
                for (int j = 0; j < remotefiles.length; j++)
                {
                    // 处理下一个文件钱，需要进行清理
                    String remotefileName = remotefiles[j];
                    byte file[] = null;
                    try
                    {
                        file = ftp.get(remotefileName);
                    }
                    catch (Exception e)
                    {
                        String content = "DCMP资讯内容数据同步_获取文件失败。失败读取的文件名为："
                                         + remotefileName
                                         + "。开始时间为："
                                         + startTime
                                         + "结束时间为"
                                         + getCurDateTime("yyyy-MM-dd HH:mm:ss");
                        logger.error(e);
                        syncDataLog.error(content);
                        syncDataLog.error(e);
                        mailToAdmin(content + "该异常的堆栈信息为：\n"
                                    + PublicUtil.GetCallStack(e), false);
                        
                        return -1;
                    }
                    // 获取咨询文件的列表
                    String fileName = remotefileName.substring(remotefileName.lastIndexOf("/") + 1);
                    if ("media.xml".equals(fileName)
                        || "hot.xml".equals(fileName))
                    {
                        fileList.add(file);
                    }

                }
                if (fileList.size() == 0)
                {
                    throw new RuntimeException("文件夹:" + dateDir + "/"
                                               + curDateDir + "没有媒体文件异常！");

                }
            }
            catch (RuntimeException e)
            {
                // 没有当天的媒体文件
                String content = "DCMP资讯内容数据同步_DCMP上没有当天的媒体文件异常。路径为：" + dateDir
                                 + "/" + curDateDir + "。开始时间为：" + startTime
                                 + "结束时间为"
                                 + getCurDateTime("yyyy-MM-dd HH:mm:ss");
                logger.error(e);
                syncDataLog.error(content);
                syncDataLog.error(e);
                // mail
                mailToAdmin(content + "\n该异常的堆栈信息为："
                            + PublicUtil.GetCallStack(e), false);
                return -1;
            }

            // 同步咨询信息
            syncNewsData(startTime, fileList);

            ftp.quit();
        }
        catch (Exception e)
        {
            try
            {
                ftp.quit();
            }
            catch (Exception e1)
            {
                logger.error("ftp退出时发生异常！");
            }
            logger.error(e);
            syncDataLog.error("DCMP资讯内容数据同步出现异常" + e.getMessage());
            syncDataLog.error(e);
            /*
             * String content = "DCMP资讯内容数据同步_获取文件失败。失败读取的文件名为：" + + "。开始时间为：" +
             * startTime + "结束时间为" + getCurDateTime("yyyy-MM-dd HH:mm:ss");
             */
            mailToAdmin("DCMP资讯内容数据同步出现异常.该异常的堆栈信息为：\n"
                        + PublicUtil.GetCallStack(e), false);
            return -1;

        }

        return 1;
    }

    /**
     * 保存咨询列表
     * 
     * @param startTime 执行开始时间
     * @param mediaList 保存媒体文件的列表
     * @param hotList 保存头条的列表
     */
    private static void syncNewsData(String startTime, List fileList)
                    throws BOException
    {

        // 保存媒体列表
        List mediaList = new ArrayList();
        // 保存头条列表
        List hotList = new ArrayList();
        for (int i = 0; i < fileList.size(); i++)
        {
            byte file[] = ( byte[] ) fileList.get(i);
            ByteArrayInputStream input = new ByteArrayInputStream(file);
            parseDCMPFile(input, mediaList, hotList);
        }

        // 获取数据中的列表
        List dbList = MediaDAO.getInstance().getAllMedias();
        if (dbList == null)
        {
            // 获取资讯内容失败。
            String error = "获取数据库中资讯失败失败，同步DCMP数据失败";
            syncDataLog.error(error);
            mailToAdmin(error, false);
            return;
        }

        // 进行更新，新增，删除的核对
        List addList = new ArrayList();
        List updList = new ArrayList();

        // 本次共获取的资讯文件数
        int sum = mediaList.size();
        MediaVO media = null;
        boolean flag;
        for (int i = 0; i < sum; i++)
        {
            media = ( MediaVO ) mediaList.get(i);
            flag = false;
            for (int j = 0; j < dbList.size(); j++)
            {
                if (media.equals(dbList.get(j)))
                {
                    // 更新
                    updList.add(media);
                    dbList.remove(j);
                    flag = true;
                    break;
                }
            }
            // 新增
            if (!flag)
            {
                addList.add(media);
            }

        }
        // 用于日志统计
        int addCount = 0;
        int updateCount = 0;
        int delCount = 0;
        int hotConnt = 0;
        int totleHotCount = hotList.size();

        // 剩余的dbList即为应该删除的项
        //处理文件数还要增加待删除的文件数。
        sum=sum+dbList.size();
        delCount = MediaDAO.getInstance().deleteMedia(dbList);
        // add meidas
        addCount = MediaDAO.getInstance().addMedia(addList);
        // update media
        updateCount = MediaDAO.getInstance().updateMedia(updList);
        // add hots
        hotConnt = MediaDAO.getInstance().updateHot(hotList);

        String endTime = getCurDateTime("yyyy-MM-dd HH:mm:ss");
        StringBuffer content = new StringBuffer();
        content.append("同步DCMP数据成功。开始时间为：");
        content.append(startTime);
        content.append("，结束时间：");
        content.append(endTime);
        content.append("。本次共处理");
        content.append(sum);
        content.append("条媒体数据，其中成功新增");
        content.append(addCount);
        content.append("条，成功更新");
        content.append(updateCount);
        content.append("条，成功删除");
        content.append(delCount);
        content.append("条。本次共处理头条个数为：");
        content.append(totleHotCount);
        content.append("条，其中成功处理个数");
        content.append(hotConnt);
        content.append("条。");
        mailToAdmin(content.toString(), true);
    }

    /**
     * 获取ftp连接
     * 
     * @return
     * @throws BOException
     */
    private static FTPClient getFTPClient() throws BOException
    {

        // 从配置项读取ftp连接传输的端口号
        int ftpServerPort = Integer.parseInt(FTPDCMPConfig.getInstance()
                                                          .getFtpPort());
        // 从配置项读取ftp连接的地址
        String ftpServerIP = FTPDCMPConfig.getInstance().getFtpIp();
        // 从配置项读取FTP服务器的登录用户名
        String ftpServerUser = FTPDCMPConfig.getInstance().getUsername();
        // 从配置项读取FTP服务器的登录密码
        String ftpServerPassword = FTPDCMPConfig.getInstance().getPassword();

        FTPClient ftp = null;
        try
        {
            ftp = new FTPClient(ftpServerIP, ftpServerPort);
            // 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.login(ftpServerUser, ftpServerPassword);
            // 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
            ftp.setType(FTPTransferType.BINARY);
        }
        catch (Exception e)
        {
            logger.error(e);
            syncDataLog.error(e);
            throw new BOException("获取ftp连接失败，请填写正确的参数！");
        }

        return ftp;
    }

    /**
     * 得到满足条件的当前时间字符串
     * 
     * @param TIME_FORMAT String,需要的时间格式
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 处理xml文件，处理结果保存在list中。
     * 
     * @param input 待处理的xml文件流
     * @param resultList 保存处理结果
     * @return 1表示媒体类型，2表示头条类型,-1表示没有处理
     * @throws Exception
     */
    private static void parseDCMPFile(InputStream input, List mediaList,
                                      List hotList) throws BOException
    {

        SAXBuilder saxBuilder = new SAXBuilder();
        org.jdom.Document document;
        try
        {
            document = saxBuilder.build(input);
        }
        catch (Exception e)
        {
            syncDataLog.error("解析DCMP文件出错");
            logger.error(e);
            syncDataLog.error(e);
            throw new BOException("解析DCMP文件出错");
        }
        Element root = document.getRootElement();

        Element contentType = ( Element ) root.getChildren().get(0);
        if ("Medias".equals(contentType.getName()))
        {
            // 处理媒体信息
            List medias = contentType.getChildren();
            MediaVO vo = null;
            for (int i = 0; i < medias.size(); i++)
            {
                // 处理每一个媒体信息
                Element node = ( Element ) medias.get(i);
                vo = new MediaVO();
                vo.setId("dcmp" + node.getChildText("Id"));
                vo.setName(node.getChildText("Name"));
                vo.setIconUrl(node.getChildText("IconUrl"));
                vo.setUrl(node.getChildText("Url"));

                mediaList.add(vo);

            }

        }
        else if ("Hots".equals(contentType.getName()))
        {
            // 处理咨询头条信息
            List medias = contentType.getChildren();
            HotVO vo = null;
            for (int i = 0; i < medias.size(); i++)
            {
                // 处理每一个媒体信息
                Element node = ( Element ) medias.get(i);
                vo = new HotVO();
                vo.setMediaId("dcmp" + node.getChildText("MediaId"));
                vo.setId(node.getChildText("Id"));
                vo.setTitle(node.getChildText("Title"));
                vo.setImageUrl(node.getChildText("ImageUrl"));
                vo.setContent(node.getChildText("Content"));
                vo.setPublishDate(node.getChildText("PublishDate"));
                vo.setUrl(node.getChildText("Url"));

                hotList.add(vo);

            }
        }

    }

    /**
     * 通知管理人员，同步DCMP的执行结果
     * 
     * @param content
     */
    private static void mailToAdmin(String content, boolean result)
    {

        String mailTitle;
        if (result)
        {
            mailTitle = getCurDateTime("yyyy-MM-dd") + " 同步DCMP数据结果成功";
        }
        else
        {
            mailTitle = getCurDateTime("yyyy-MM-dd") + " 同步DCMP数据结果失败";
        }
        Mail.sendMail(mailTitle, content, FTPDCMPConfig.getInstance()
                                                       .getEmails());

    }

}
