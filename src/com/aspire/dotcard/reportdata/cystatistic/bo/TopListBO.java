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
public class TopListBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(TopListBO.class);

    /**
     * ����ר����־
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");

    private static TopListBO instance = new TopListBO();

    private TopListBO()
    {

    }

    /**
     * �õ�����ģʽ
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
     * 0����ʾȫ�����У���������ʾidִ�У��ο�ListConstant��
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
                logger.error("����������ļ����������쳣��tag="+tag+" ������id��ʾ");
                sb.append("<p>����������ļ����������쳣��<p>tag="+tag+" �޴�����id��ʾ");
            }

        }
        catch (Exception e)
        {
            logger.error("����������ļ����������쳣��", e);
            sb.append("<p>����������ļ����������쳣��<p>" + StringUtils.stackTrace(e, true));
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
            Mail.sendMail("����������ļ���������֪ͨ�ʼ�", sb.toString(), mailTo);
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
     * ����ͳ����Ϣ��
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
                sb.append(currentFile + ".txt�����ļ�������<p>");
            }
            else
            {
                boolean clearTag = false;
                for (int i = 0; i < fileList.size(); i++)
                {
                    File f = new File(( String ) fileList.get(i));

                    // ��ȡ�ļ�����
                    List arrColorContent = readAllFileLine(( String ) fileList.get(i));

                    // �����ļ���Ϊ�ղ���û��ִ�й���ձ����
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
            logger.error("���ݴ����쳣����������=" + fileName, e);
            sb.append("<p>���ݴ����쳣����������=" + fileName + "<p>"
                      + StringUtils.stackTrace(e, true));
        }

        return sb.toString();

    }

    /**
     * �����ļ����ݲ��뵽���ݿ�
     * @param fileName �ļ�����
     * @param type int �ɹ����µĴ���
     * @param SerialNumber �ļ����к�
     * @return
     * @throws Exception
     */
    private String processFileData(String fileName,List arrColorContent, int type, boolean clearTag) throws Exception
    {

        StringBuffer sb = new StringBuffer();
        if (logger.isDebugEnabled())
        {
            logger.debug("processFileData is��" + fileName + " type:" + type);
        }
        sb.append("<p><b>�ļ�" + fileName + "��������");
        // ��ȡ�ļ�����
        int iDataNum = arrColorContent.size();
        if (iDataNum == 0)
        {
            logger.debug("û��Ҫ���������");
            sb.append("<p><b>�ļ�" + fileName + ":�ļ�Ϊ�գ�û��Ҫ��������ݡ�");
            return sb.toString();
        }
        else if(clearTag)
        {
            //����ǵ�һ�������ļ�����Ҫ����б�
            logger.debug("�������ݴ��ڣ����type=" + type + "�ı�����");
            // �������ݴ��ڣ���ձ�����
            ListImportDAO.getInstance().deleteToListStatistic(type);
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
                logger.error("��" + (i + 1) + "�е���ʧ��:" + recordContent
                             + " ���ݿ����", e);
                REPORT_LOG.error("��" + (i + 1) + "�е���ʧ��:" + recordContent
                                 + " ���ݿ����", e);
            }
        }
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
    private String[] parseLineText(String recordContent) throws BOException
    {

        String[] r = new String[3];

        if (StringTool.lengthOfHZ(recordContent) != 31)
        {
            throw new BOException("�ļ����ݸ�ʽ����ȷ!���ܳ��Ȳ�����31���ַ�");
        }
        int[] l = { 8, 12, 11 };
        int startIndex = 0;// �ֶο�ʼλ��
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

}
