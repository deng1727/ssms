
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
 * DCMP��Ѷ��������ͬ��BO��
 * 
 * @author zhangwei
 * 
 */
public class FTPDCMPBO
{

    /**
     * ��־����
     */
    private static JLogger logger = LoggerFactory.getLogger(FTPDCMPBO.class);

    private static JLogger syncDataLog = LoggerFactory.getLogger("FTP-DCMP-Log");

    /**
     * �����࣬��˹��췽��˽��
     */
    private FTPDCMPBO()
    {

    }

    /**
     * DCMP�����ļ�ftp��ȡ�ķ���
     * 
     * @param FullDataTime String
     * @param RefDataTime String
     * @return int ִ�н����־��-1��ʾʧ�ܣ�1��ʾ�ɹ�
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
            // ȡ��Զ��Ŀ¼���ļ��б�
            String curDateDir = getCurDateTime("yyyyMMdd");
            // ���������ȡFTP��������DCMP���Ŀ¼
            String dateDir = FTPDCMPConfig.getInstance().getDateDir();
            String[] remotefiles;
            List fileList = new ArrayList();
            try
            {
                remotefiles = ftp.dir(dateDir + "/" + curDateDir);
                if (remotefiles.length == 0)
                {
                    throw new RuntimeException("�ļ���:" + dateDir + "/"
                                               + curDateDir + "û��ý���ļ��쳣��");
                }

                // ѭ����ȡÿһ���ļ������н�����������Ӧ��vo�ౣ����list��
                // Ŀǰֻ��һ�������ļ������Զ�Ҫ����fileList�У�������������µ��ļ�������ĳ�����ļ��Ļ�ȡ��ʽ
                
                for (int j = 0; j < remotefiles.length; j++)
                {
                    // ������һ���ļ�Ǯ����Ҫ��������
                    String remotefileName = remotefiles[j];
                    byte file[] = null;
                    try
                    {
                        file = ftp.get(remotefileName);
                    }
                    catch (Exception e)
                    {
                        String content = "DCMP��Ѷ��������ͬ��_��ȡ�ļ�ʧ�ܡ�ʧ�ܶ�ȡ���ļ���Ϊ��"
                                         + remotefileName
                                         + "����ʼʱ��Ϊ��"
                                         + startTime
                                         + "����ʱ��Ϊ"
                                         + getCurDateTime("yyyy-MM-dd HH:mm:ss");
                        logger.error(e);
                        syncDataLog.error(content);
                        syncDataLog.error(e);
                        mailToAdmin(content + "���쳣�Ķ�ջ��ϢΪ��\n"
                                    + PublicUtil.GetCallStack(e), false);
                        
                        return -1;
                    }
                    // ��ȡ��ѯ�ļ����б�
                    String fileName = remotefileName.substring(remotefileName.lastIndexOf("/") + 1);
                    if ("media.xml".equals(fileName)
                        || "hot.xml".equals(fileName))
                    {
                        fileList.add(file);
                    }

                }
                if (fileList.size() == 0)
                {
                    throw new RuntimeException("�ļ���:" + dateDir + "/"
                                               + curDateDir + "û��ý���ļ��쳣��");

                }
            }
            catch (RuntimeException e)
            {
                // û�е����ý���ļ�
                String content = "DCMP��Ѷ��������ͬ��_DCMP��û�е����ý���ļ��쳣��·��Ϊ��" + dateDir
                                 + "/" + curDateDir + "����ʼʱ��Ϊ��" + startTime
                                 + "����ʱ��Ϊ"
                                 + getCurDateTime("yyyy-MM-dd HH:mm:ss");
                logger.error(e);
                syncDataLog.error(content);
                syncDataLog.error(e);
                // mail
                mailToAdmin(content + "\n���쳣�Ķ�ջ��ϢΪ��"
                            + PublicUtil.GetCallStack(e), false);
                return -1;
            }

            // ͬ����ѯ��Ϣ
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
                logger.error("ftp�˳�ʱ�����쳣��");
            }
            logger.error(e);
            syncDataLog.error("DCMP��Ѷ��������ͬ�������쳣" + e.getMessage());
            syncDataLog.error(e);
            /*
             * String content = "DCMP��Ѷ��������ͬ��_��ȡ�ļ�ʧ�ܡ�ʧ�ܶ�ȡ���ļ���Ϊ��" + + "����ʼʱ��Ϊ��" +
             * startTime + "����ʱ��Ϊ" + getCurDateTime("yyyy-MM-dd HH:mm:ss");
             */
            mailToAdmin("DCMP��Ѷ��������ͬ�������쳣.���쳣�Ķ�ջ��ϢΪ��\n"
                        + PublicUtil.GetCallStack(e), false);
            return -1;

        }

        return 1;
    }

    /**
     * ������ѯ�б�
     * 
     * @param startTime ִ�п�ʼʱ��
     * @param mediaList ����ý���ļ����б�
     * @param hotList ����ͷ�����б�
     */
    private static void syncNewsData(String startTime, List fileList)
                    throws BOException
    {

        // ����ý���б�
        List mediaList = new ArrayList();
        // ����ͷ���б�
        List hotList = new ArrayList();
        for (int i = 0; i < fileList.size(); i++)
        {
            byte file[] = ( byte[] ) fileList.get(i);
            ByteArrayInputStream input = new ByteArrayInputStream(file);
            parseDCMPFile(input, mediaList, hotList);
        }

        // ��ȡ�����е��б�
        List dbList = MediaDAO.getInstance().getAllMedias();
        if (dbList == null)
        {
            // ��ȡ��Ѷ����ʧ�ܡ�
            String error = "��ȡ���ݿ�����Ѷʧ��ʧ�ܣ�ͬ��DCMP����ʧ��";
            syncDataLog.error(error);
            mailToAdmin(error, false);
            return;
        }

        // ���и��£�������ɾ���ĺ˶�
        List addList = new ArrayList();
        List updList = new ArrayList();

        // ���ι���ȡ����Ѷ�ļ���
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
                    // ����
                    updList.add(media);
                    dbList.remove(j);
                    flag = true;
                    break;
                }
            }
            // ����
            if (!flag)
            {
                addList.add(media);
            }

        }
        // ������־ͳ��
        int addCount = 0;
        int updateCount = 0;
        int delCount = 0;
        int hotConnt = 0;
        int totleHotCount = hotList.size();

        // ʣ���dbList��ΪӦ��ɾ������
        //�����ļ�����Ҫ���Ӵ�ɾ�����ļ�����
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
        content.append("ͬ��DCMP���ݳɹ�����ʼʱ��Ϊ��");
        content.append(startTime);
        content.append("������ʱ�䣺");
        content.append(endTime);
        content.append("�����ι�����");
        content.append(sum);
        content.append("��ý�����ݣ����гɹ�����");
        content.append(addCount);
        content.append("�����ɹ�����");
        content.append(updateCount);
        content.append("�����ɹ�ɾ��");
        content.append(delCount);
        content.append("�������ι�����ͷ������Ϊ��");
        content.append(totleHotCount);
        content.append("�������гɹ��������");
        content.append(hotConnt);
        content.append("����");
        mailToAdmin(content.toString(), true);
    }

    /**
     * ��ȡftp����
     * 
     * @return
     * @throws BOException
     */
    private static FTPClient getFTPClient() throws BOException
    {

        // ���������ȡftp���Ӵ���Ķ˿ں�
        int ftpServerPort = Integer.parseInt(FTPDCMPConfig.getInstance()
                                                          .getFtpPort());
        // ���������ȡftp���ӵĵ�ַ
        String ftpServerIP = FTPDCMPConfig.getInstance().getFtpIp();
        // ���������ȡFTP�������ĵ�¼�û���
        String ftpServerUser = FTPDCMPConfig.getInstance().getUsername();
        // ���������ȡFTP�������ĵ�¼����
        String ftpServerPassword = FTPDCMPConfig.getInstance().getPassword();

        FTPClient ftp = null;
        try
        {
            ftp = new FTPClient(ftpServerIP, ftpServerPort);
            // ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.login(ftpServerUser, ftpServerPassword);
            // �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
            ftp.setType(FTPTransferType.BINARY);
        }
        catch (Exception e)
        {
            logger.error(e);
            syncDataLog.error(e);
            throw new BOException("��ȡftp����ʧ�ܣ�����д��ȷ�Ĳ�����");
        }

        return ftp;
    }

    /**
     * �õ����������ĵ�ǰʱ���ַ���
     * 
     * @param TIME_FORMAT String,��Ҫ��ʱ���ʽ
     * @return String
     */
    private static String getCurDateTime(String TIME_FORMAT)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * ����xml�ļ���������������list�С�
     * 
     * @param input �������xml�ļ���
     * @param resultList ���洦����
     * @return 1��ʾý�����ͣ�2��ʾͷ������,-1��ʾû�д���
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
            syncDataLog.error("����DCMP�ļ�����");
            logger.error(e);
            syncDataLog.error(e);
            throw new BOException("����DCMP�ļ�����");
        }
        Element root = document.getRootElement();

        Element contentType = ( Element ) root.getChildren().get(0);
        if ("Medias".equals(contentType.getName()))
        {
            // ����ý����Ϣ
            List medias = contentType.getChildren();
            MediaVO vo = null;
            for (int i = 0; i < medias.size(); i++)
            {
                // ����ÿһ��ý����Ϣ
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
            // ������ѯͷ����Ϣ
            List medias = contentType.getChildren();
            HotVO vo = null;
            for (int i = 0; i < medias.size(); i++)
            {
                // ����ÿһ��ý����Ϣ
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
     * ֪ͨ������Ա��ͬ��DCMP��ִ�н��
     * 
     * @param content
     */
    private static void mailToAdmin(String content, boolean result)
    {

        String mailTitle;
        if (result)
        {
            mailTitle = getCurDateTime("yyyy-MM-dd") + " ͬ��DCMP���ݽ���ɹ�";
        }
        else
        {
            mailTitle = getCurDateTime("yyyy-MM-dd") + " ͬ��DCMP���ݽ��ʧ��";
        }
        Mail.sendMail(mailTitle, content, FTPDCMPConfig.getInstance()
                                                       .getEmails());

    }

}
