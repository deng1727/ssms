
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
 * ����������ļ��������� Ӧ��top�񵥡���������񵥡���ҵ������Ʒ
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
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(CyListBO.class);

    /**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");

    private static CyListBO instance = new CyListBO();

    private TaskRunner updateTaskRunner;

    private CyListBO()
    {

    }

    /**
     * �õ�����ģʽ
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
            logger.error("ÿ�մ�ҵ������Ʒ��Ӫ�������ݴ��������쳣��", e);
            sb.append("<p>ÿ�մ�ҵ������Ʒ��Ӫ�������ݴ��������쳣��<p>"
                      + StringUtils.stackTrace(e, true));
        }
        // ��ҵ��ͬ��������ʼ��������Ա
        finally
        {
            sb.append("��ʼʱ��:");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ��:");
            sb.append(PublicUtil.getDateString(new Date(),
                                               "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<h4>������:</h4>");
            sb.append(mailContent);
            logger.info(sb.toString());
            String[] mailTo = TopListConfig.get("mailTo").trim().split(",");
            Mail.sendMail("ÿ�մ�ҵ������Ʒ��Ӫ���������ļ���������֪ͨ�ʼ�", sb.toString(), mailTo);
        }
    }

    /**
     * ����ͳ����Ϣ��
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
            // ��ȡ������������������,Ĭ��Ϊ-1
            int days = TopListConfig.getFileDay();
            // ��ȡ���������
            String currentFile = fileName
                               + "_"
                               + DateUtil.dateChangeDays(PublicUtil.getCurDateTime("yyyyMMdd"),
                                                         "yyyyMMdd",
                                                         days);
            fileList = ftp.process(fileDirName, currentFile);

            if (fileList.size() == 0)
            {
                sb.append(currentFile+".txt�����ļ�������<p>");
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
            logger.error("���ݴ����쳣����������=" + fileName, e);
            sb.append("<p>���ݴ����쳣����������=" + fileName + "<p>"
                      + StringUtils.stackTrace(e, true));
        }

        return sb.toString();

    }

    /**
     * �����ļ����ݲ��뵽���ݿ�
     * 
     * @param fileName String
     * @throws Exception
     * @return int �ɹ����µĴ���
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
            logger.debug("processFileData is��" + fileName);
        }
        File f = new File(fileName);
        sb.append("<p><b>�ļ�" + f.getName() + "��������");
        // ��ȡ�ļ�����
        List arrColorContent = readAllFileLine(fileName);
        int iDataNum = arrColorContent.size();
        if (iDataNum == 0)
        {
            logger.debug(f.getName() + "û��Ҫ���������");
            sb.append("<p><b>�ļ�" + f.getName() + ":�ļ�Ϊ�գ�û��Ҫ��������ݡ�");
            return sb.toString();
        }
        else
        {
            logger.debug("һ��Ҫ����" + iDataNum + "������");
            sb.append("<p><b>һ��Ҫ����" + iDataNum + "������");
        }
        // �ɹ�����
        int successCount = 0;
        // ���ݲ����Ϲ淶����
        int checkfaild = 0;
        // ʧ������
        int dealfaild = 0;
        // ���д���
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
                logger.error("��" + (i + 1) + "�е���ʧ��:" + recordContent
                             + " ͳ�Ʊ���������");
                REPORT_LOG.error("��" + (i + 1) + "�е���ʧ��:" + recordContent
                                 + " ͳ�Ʊ���������");
            }

            catch (BOException e)
            {
                checkfaild += 1;
                logger.error("��" + (i + 1) + "�е���ʧ��:" + recordContent, e);
                REPORT_LOG.error("��" + (i + 1) + "�е���ʧ��:" + recordContent, e);
            }
            catch (Exception e)
            {
                dealfaild += 1;
                logger.error("��" + (i + 1) + "�е���ʧ��:" + recordContent, e);
                REPORT_LOG.error("��" + (i + 1) + "�е���ʧ��:" + recordContent, e);
            }
        }
        updateTaskRunner.waitToFinished();// �ȴ��������ݿ���ϡ�
        updateTaskRunner.end();// ����������

        sb.append("<p>���гɹ�����<b>");
        sb.append(successCount);
        sb.append("��;<p>����ʧ��<b>");
        sb.append(dealfaild);
        sb.append("��;<p>���ݲ����Ϲ淶<b>");
        sb.append(checkfaild);
        sb.append("��;");
        return sb.toString();
    }

    /**
     * ����ÿһ�����ݵ�������
     */
    public CyListVO parseLineText(String recordContent, CyListVO vo)
                    throws BOException
    {

        String[] r = new String[13];
        try
        {
            if (StringTool.lengthOfHZ(recordContent) != 200)
            {
                throw new BOException("�ļ����ݸ�ʽ����ȷ!���ܳ��Ȳ�����140���ַ�");
            }
            int[] l = { 8, 12, 60, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
            int startIndex = 0;// �ֶο�ʼλ��
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
            logger.error("readAllFileLine is��" + recordContent, e);
            throw new BOException("�ļ����ݸ�ʽ����ȷ!" + r.toString());
        }

        return vo;
    }

    /**
     * ��ȡ�ļ��е�ȫ�����ݣ����һ��String
     * 
     * @param fileName ,�ļ���ȫ·������
     * @return String
     * @throws IOException
     */
    public static List readAllFileLine(String fileName) throws IOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("readAllFileLine is��" + fileName);
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
            logger.error("�ļ���ȡ����" + fileName, e);
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
        // �����첽����
        ReflectedTask task = new ReflectedTask(cyTask,
                                               "updateCyListVO",
                                               null,
                                               null);
        // ������ӵ���������
        updateTaskRunner.addTask(task);
    }

}
