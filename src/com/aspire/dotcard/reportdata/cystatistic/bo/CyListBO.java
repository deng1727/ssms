
package com.aspire.dotcard.reportdata.cystatistic.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.reportdata.cystatistic.ListFtpProcessor;
import com.aspire.dotcard.reportdata.cystatistic.TopListConfig;
import com.aspire.dotcard.reportdata.cystatistic.vo.CyListVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * 报表榜单数据文件处理任务 应用top榜单、创意孵化榜单、创业大赛作品
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class CyListBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(CyListBO.class);

    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");

    private static CyListBO instance = new CyListBO();

    private TaskRunner updateTaskRunner;

    private CyListBO()
    {

    }

    /**
     * 得到单例模式
     * 
     */
    public static CyListBO getInstance()
    {

        return instance;
    }

    /**
     * 
     * 
     */
    public void service()
    {

        Date startDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (logger.isDebugEnabled())
        {
            logger.debug("CyListBO.getInstance().service()");
        }
        String mailContent = "";
        try
        {

            mailContent = saveCategoryStatistic();

        }
        catch (Exception e)
        {
            logger.error("每日创业大赛作品运营属性数据处理任务异常：", e);
            sb.append("<p>每日创业大赛作品运营属性数据处理任务异常：<p>"
                      + StringUtils.stackTrace(e, true));
        }
        // 将业务同步结果发邮件给相关人员
        finally
        {
            sb.append("开始时间:");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间:");
            sb.append(PublicUtil.getDateString(new Date(),
                                               "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<h4>处理结果:</h4>");
            sb.append(mailContent);
            logger.info(sb.toString());
            String[] mailTo = TopListConfig.get("mailTo").trim().split(",");
            Mail.sendMail("每日创业大赛作品运营属性数据文件处理任务通知邮件", sb.toString(), mailTo);
        }
    }

    /**
     * 保存统计信息到
     * 
     * @param fileNameList
     * @return
     * @throws Exception
     */
    public String saveCategoryStatistic() throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("CyListBO.saveCategoryStatistic()");
        }
        String fileName = TopListConfig.get("cy2011_product_add_value");
        String fileDirName= fileName+"_d";
        StringBuffer sb = new StringBuffer();

        ListFtpProcessor ftp = new ListFtpProcessor();
        List fileList = null;
        try
        {
            // 获取配置项的日期相差天数,默认为-1
            int days = TopListConfig.getFileDay();
            // 获取当天的日期
            String currentFile = fileName
                               + "_"
                               + DateUtil.dateChangeDays(PublicUtil.getCurDateTime("yyyyMMdd"),
                                                         "yyyyMMdd",
                                                         days);
            fileList = ftp.process(fileDirName, currentFile);

            if (fileList.size() == 0)
            {
                sb.append(currentFile+".txt数据文件不存在<p>");
            }
            else
            {
                for (int i = 0; i < fileList.size(); i++)
                {
                    sb.append(processFileData(( String ) fileList.get(i)));
                }
            }

        }
        catch (Exception e)
        {
            logger.error("数据处理异常：数据类型=" + fileName, e);
            sb.append("<p>数据处理异常：数据类型=" + fileName + "<p>"
                      + StringUtils.stackTrace(e, true));
        }

        return sb.toString();

    }

    /**
     * 报表文件数据插入到数据库
     * 
     * @param fileName String
     * @throws Exception
     * @return int 成功更新的次数
     */
    private String processFileData(String fileName) throws Exception
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("CyListBO.processFileData("+fileName+")");
        }

        updateTaskRunner = new TaskRunner(TopListConfig.getCyUpdateTaskNum(),
                                          TopListConfig.getCyMaxReceivedNum());
        StringBuffer sb = new StringBuffer();
        if (logger.isDebugEnabled())
        {
            logger.debug("processFileData is：" + fileName);
        }
        File f = new File(fileName);
        sb.append("<p><b>文件" + f.getName() + "处理结果：");
        // 读取文件内容
        List arrColorContent = readAllFileLine(fileName);
        int iDataNum = arrColorContent.size();
        if (iDataNum == 0)
        {
            logger.debug(f.getName() + "没有要导入的数据");
            sb.append("<p><b>文件" + f.getName() + ":文件为空，没有要导入的数据。");
            return sb.toString();
        }
        else
        {
            logger.debug("一共要导入" + iDataNum + "条数据");
            sb.append("<p><b>一共要导入" + iDataNum + "条数据");
        }
        // 成功条数
        int successCount = 0;
        // 数据不符合规范条数
        int checkfaild = 0;
        // 失败条数
        int dealfaild = 0;
        // 逐行处理
        for (int i = 0; i < iDataNum; i++)
        {
            CyListVO vo = new CyListVO();
            String recordContent = ( String ) arrColorContent.get(i);
            try
            {
                parseLineText(recordContent, vo);
                this.updateCyListVO(vo);

                successCount += 1;

            }
            catch (NumberFormatException e)
            {
                logger.error("该" + (i + 1) + "行导入失败:" + recordContent
                             + " 统计必须是整数");
                REPORT_LOG.error("该" + (i + 1) + "行导入失败:" + recordContent
                                 + " 统计必须是整数");
            }

            catch (BOException e)
            {
                checkfaild += 1;
                logger.error("该" + (i + 1) + "行导入失败:" + recordContent, e);
                REPORT_LOG.error("该" + (i + 1) + "行导入失败:" + recordContent, e);
            }
            catch (Exception e)
            {
                dealfaild += 1;
                logger.error("该" + (i + 1) + "行导入失败:" + recordContent, e);
                REPORT_LOG.error("该" + (i + 1) + "行导入失败:" + recordContent, e);
            }
        }
        updateTaskRunner.waitToFinished();// 等待更新数据库完毕。
        updateTaskRunner.end();// 结束运行器

        sb.append("<p>其中成功导入<b>");
        sb.append(successCount);
        sb.append("条;<p>处理失败<b>");
        sb.append(dealfaild);
        sb.append("条;<p>数据不符合规范<b>");
        sb.append(checkfaild);
        sb.append("条;");
        return sb.toString();
    }

    /**
     * 解析每一行数据到数组中
     */
    public CyListVO parseLineText(String recordContent, CyListVO vo)
                    throws BOException
    {

        String[] r = new String[13];
        try
        {
            if (StringTool.lengthOfHZ(recordContent) != 200)
            {
                throw new BOException("文件内容格式不正确!，总长度不等于140个字符");
            }
            int[] l = { 8, 12, 60, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
            int startIndex = 0;// 字段开始位置
            for (int i = 0; i < 13; i++)
            {
                r[i] = StringTool.formatByLen(StringTool.substring(recordContent,
                                                                   startIndex),
                                              l[i],
                                              "")
                                 .trim();
                startIndex += l[i];
            }

            vo.setStattime(r[0]);
            vo.setContentid(r[1]);
            vo.setContentname(r[2]);
            vo.setDaydownloadusernum(Integer.parseInt(r[3]));
            // vo.setTeststar(Integer.parseInt(r[4]));
             //vo.setTestusernum(Integer.parseInt(r[5]));
             vo.setDaytestusernum(Integer.parseInt(r[4]));
             vo.setDayteststar(Integer.parseInt(r[5]));
             vo.setDaystarscorecount(Integer.parseInt(r[6]));
             vo.setDayglobalscorecount(Integer.parseInt(r[7]));
             
            vo.setDownloadusernum(Integer.parseInt(r[8]));
            vo.setTestusernum(Integer.parseInt(r[9]));
            vo.setTeststar(Integer.parseInt(r[10]));
            vo.setStarscorecount(Integer.parseInt(r[11]));
            vo.setGlobalscorecount(Integer.parseInt(r[12]));
        }
        catch (Exception e)
        {
            logger.error("readAllFileLine is：" + recordContent, e);
            throw new BOException("文件内容格式不正确!" + r.toString());
        }

        return vo;
    }

    /**
     * 读取文件中的全部数据，组成一个String
     * 
     * @param fileName ,文件的全路径名称
     * @return String
     * @throws IOException
     */
    public static List readAllFileLine(String fileName) throws IOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("readAllFileLine is：" + fileName);
        }
        ArrayList list = new ArrayList();
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null)
            {
                list.add(line);
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            logger.error("文件读取错误：" + fileName, e);
            throw e;
        }
        finally
        {
            br.close();
            fr.close();
        }

        return list;
    }

    private void updateCyListVO(CyListVO vo)
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateCyListVO() id=" + vo.getContentid());
        }
        CyListBOTask cyTask = new CyListBOTask(vo);
        // 构造异步任务
        ReflectedTask task = new ReflectedTask(cyTask,
                                               "updateCyListVO",
                                               null,
                                               null);
        // 将任务加到运行器中
        updateTaskRunner.addTask(task);
    }

}
