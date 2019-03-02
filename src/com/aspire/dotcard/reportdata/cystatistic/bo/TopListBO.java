package com.aspire.dotcard.reportdata.cystatistic.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.reportdata.cystatistic.ListConstant;
import com.aspire.dotcard.reportdata.cystatistic.ListFtpProcessor;
import com.aspire.dotcard.reportdata.cystatistic.TopListConfig;
import com.aspire.dotcard.reportdata.cystatistic.dao.ListImportDAO;
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
public class TopListBO
{

    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(TopListBO.class);

    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");

    private static TopListBO instance = new TopListBO();

    private TopListBO()
    {

    }

    /**
     * 得到单例模式
     * 
     */
    public static TopListBO getInstance()
    {

        return instance;
    }

    /**
     * 
     * 
     */
    /**
     * 0：表示全部執行，其他按标示id执行，参考ListConstant类
     * @param tag
     */
    public void service(int tag)
    {

        Date startDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (logger.isDebugEnabled())
        {
            logger.debug("TopListBO.getInstance().service()");
        }
        String mailContent = "";
        try
        {
            if (tag == 0)
            {
                for (int i = 1; i < 6; i++)
                {

                    mailContent += global_incubate(i);
                }
            }
            else if(tag<6)
            {
                mailContent += global_incubate(tag);
            }
            else{
                logger.error("报表榜单数据文件处理任务异常：tag="+tag+" 无任务id标示");
                sb.append("<p>报表榜单数据文件处理任务异常：<p>tag="+tag+" 无此任务id标示");
            }

        }
        catch (Exception e)
        {
            logger.error("报表榜单数据文件处理任务异常：", e);
            sb.append("<p>报表榜单数据文件处理任务异常：<p>" + StringUtils.stackTrace(e, true));
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
            Mail.sendMail("报表榜单数据文件处理任务通知邮件", sb.toString(), mailTo);
        }
    }

    private String global_incubate(int type) throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("TopListBO.global_incubate(" + type + ")");
        }
        String fileName = "";
        switch (type)
        {
            case ListConstant.GLOBAL_SOFTORGAME_LIST:
                fileName = TopListConfig.get("global_softorgame_list");
                break;
            case ListConstant.GLOBAL_INCUBATE_LIST:
                fileName = TopListConfig.get("global_incubate_list");
                break;
            case ListConstant.STAR_SOFTORGAME_LIST:
                fileName = TopListConfig.get("star_softorgame_list");
                break;
            case ListConstant.STAR_INCUBATE_LIST:
                fileName = TopListConfig.get("star_incubate_list");
                break;
            case ListConstant.STAR_MARK_PK_FEE:
                fileName = TopListConfig.get("mark_pk_fee_list");
                break;

        }
        String fileDirName= fileName+"_d";
        return saveCategoryStatistic(fileDirName,fileName, type);
    }

    /**
     * 保存统计信息到
     * 
     * @param fileNameList
     * @return
     * @throws Exception
     */
    public String saveCategoryStatistic(String fileDirName,String fileName, int type)
                    throws Exception
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("TopListBO.saveCategoryStatistic(" + fileDirName + ","
                         + fileName + "," + type + ")");
        }
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
                sb.append(currentFile + ".txt数据文件不存在<p>");
            }
            else
            {
                boolean clearTag = false;
                for (int i = 0; i < fileList.size(); i++)
                {
                    File f = new File(( String ) fileList.get(i));

                    // 读取文件内容
                    List arrColorContent = readAllFileLine(( String ) fileList.get(i));

                    // 数据文件不为空并且没有执行过清空表操作
                    if (arrColorContent.size() != 0 && clearTag == false)
                    {
                        clearTag = true;
                        sb.append(processFileData(f.getName(),
                                                  arrColorContent,
                                                  type,
                                                  true));
                    }
                    else
                    {
                        sb.append(processFileData(f.getName(),
                                                  arrColorContent,
                                                  type,
                                                  false));
                    }

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
     * @param fileName 文件名称
     * @param type int 成功更新的次数
     * @param SerialNumber 文件序列号
     * @return
     * @throws Exception
     */
    private String processFileData(String fileName,List arrColorContent, int type, boolean clearTag) throws Exception
    {

        StringBuffer sb = new StringBuffer();
        if (logger.isDebugEnabled())
        {
            logger.debug("processFileData is：" + fileName + " type:" + type);
        }
        sb.append("<p><b>文件" + fileName + "处理结果：");
        // 读取文件内容
        int iDataNum = arrColorContent.size();
        if (iDataNum == 0)
        {
            logger.debug("没有要导入的数据");
            sb.append("<p><b>文件" + fileName + ":文件为空，没有要导入的数据。");
            return sb.toString();
        }
        else if(clearTag)
        {
            //如果是第一个数据文件则需要清空列表
            logger.debug("报表数据存在，清空type=" + type + "的表数据");
            // 报表数据存在，清空表数据
            ListImportDAO.getInstance().deleteToListStatistic(type);
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
            String[] lineData;
            String recordContent = ( String ) arrColorContent.get(i);
            String time = "";
            String contentid = "";
            long count = 0;
            try
            {
                lineData = parseLineText(recordContent);
                time = lineData[0];
                contentid = lineData[1];
                count = Long.parseLong(lineData[2]);
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

            try
            {

                int result = ListImportDAO.getInstance()
                                             .insertToListStatistic(time,
                                                                    contentid,
                                                                    count,
                                                                    type);
                successCount += result;
            }
            catch (DAOException e)
            {
                dealfaild += 1;
                logger.error("该" + (i + 1) + "行导入失败:" + recordContent
                             + " 数据库出错", e);
                REPORT_LOG.error("该" + (i + 1) + "行导入失败:" + recordContent
                                 + " 数据库出错", e);
            }
        }
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
    private String[] parseLineText(String recordContent) throws BOException
    {

        String[] r = new String[3];

        if (StringTool.lengthOfHZ(recordContent) != 31)
        {
            throw new BOException("文件内容格式不正确!，总长度不等于31个字符");
        }
        int[] l = { 8, 12, 11 };
        int startIndex = 0;// 字段开始位置
        for (int i = 0; i < 3; i++)
        {
            r[i] = StringTool.formatByLen(recordContent.substring(startIndex),
                                          l[i],
                                          "").trim();
            startIndex += l[i];
        }
        return r;
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

}
