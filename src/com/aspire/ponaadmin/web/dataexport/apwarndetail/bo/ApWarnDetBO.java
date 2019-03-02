/*
 * �ļ�����ApWarnDetBO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  ���ڶ���Ԥ�����ݴ���
 */

package com.aspire.ponaadmin.web.dataexport.apwarndetail.bo;

import java.io.File;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.apwarn.ApWarnConfig;
import com.aspire.ponaadmin.web.dataexport.apwarn.vo.ApWarnVo;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConfig;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.ApWarnDetConstants;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.dao.ApWarnDetDAO;
import com.aspire.ponaadmin.web.dataexport.apwarndetail.vo.ApWarnDetVO;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title:���ڶ���Ԥ�����ݴ���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class ApWarnDetBO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(ApWarnDetBO.class);

    /**
     * ʵ������
     */
    private static ApWarnDetBO bo = new ApWarnDetBO();

    /**
     * ����SP��Ӧ�������û��ص���
     */
    private static Map SPUSERITERANCEMAP = Collections.synchronizedMap(new HashMap());

    /**
     * ���ڷ���ͳ��ȫ��Ϣ��file��Ӧ�����ļ���cityWarn��Ӧ�������������޴��ͳ�ƣ�timeWarn��Ӧʱ��ι̶���ͳ��
     */
    private static Map RETMAP = new HashMap();

    /**
     * �������������޴��ͳ��
     */
    private static int cityWarn = 0;

    /**
     * ʱ��ι̶���ͳ��
     */
    private static int timeWarn = 0;

    /**
     * ���û�����Ӧ���쳣��׼ͳ��
     */
    private static int downloadWarn = 0;

    /**
     * �������������׼ͳ��
     */
    private static int seriesNumWarn = 0;

    /**
     * �����û��ص��������׼ͳ��
     */
    private static int downloadUserIteranceWarn = 0;

    /**
     * ˽�й��췽��
     */
    private ApWarnDetBO()
    {
    }

    /**
     * ��̬����
     * 
     * @return
     */
    public static ApWarnDetBO getInstance()
    {
        return bo;
    }

    /**
     * �õ���ǰ���ں�ǰһ���ڵ��ַ���ʽ
     * 
     * @param date
     */
    private String[] getDataString()
    {
        String[] date = new String[2];

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // ��ǰ����
        date[0] = sdf.format(cal.getTime());

        // ǰһ����
        cal.add(Calendar.DATE, -1);
        date[1] = sdf.format(cal.getTime());

        return date;
    }

    /**
     * ���ݵ�һ����Ԥ����������id���õ��������ݣ����뱾����ʱ���С�
     * 
     * @param date ��ǰ���ڣ�ǰһ����
     * @throws DAOException
     */
    private void copyReportDateToTemp(String[] date) throws DAOException
    {
        logger.info("ɾ��֮ǰ������ʱ������");

        // ɾ��֮ǰ����
        ApWarnDetDAO.getInstance().clearOldDate();

        logger.info("����һ��Ԥ����ϸ��Ϣ��������ʱ��");

        // ����������
        ApWarnDetDAO.getInstance().copyReportDateToTemp(date);

        logger.info("������ʱ������");

        // ������ʱ������
        ApWarnDetDAO.getInstance().createIndexByTable();

        logger.info("������ʱ��ı����");

        String dbName = ConfigFactory.getSystemConfig()
                                     .getModuleConfig("ssms")
                                     .getItemValue("DB_NAME");

        // ���б����
        ApWarnDetDAO.getInstance()
                    .fullGetTableStats(dbName.toUpperCase(),
                                       "t_ap_warn_detail".toUpperCase());
    }

    /**
     * �жϵ��û�����Ӧ���Ƿ�����쳣
     * 
     * ���û�����Ӧ���쳣 ��Ҫͬʱ�������¹��� 1. ��Ʒ�������ش��� > 200�������ã� 2.������ͬ�ֻ��������ش���Ʒ���� > 10�������ã�
     * 3. ��2����ֻ����뼯�������ش���Ʒ�����ܺ� > ����Ʒ�������ش�����30%�������ã�
     * 
     * @param element Ӧ��������Ϣ
     * @return ��/��
     * @throws BOException
     */
    private String isSingleUserDownloadWarn(ApWarnDetVO element)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int dayDownloadNum = 0;
        int userDownloadNum = 0;
        int userQuotietyNum = 0;

        try
        {
            // �õ���Ʒ�������ش���
            dayDownloadNum = ApWarnDetDAO.getInstance()
                                         .getDayDownloadNum(element.getContentId());
            logger.info("����id:" + element.getContentId() + " �������ش���Ϊ"
                        + dayDownloadNum);
        }
        catch (DAOException e)
        {
            throw new BOException("�õ���Ʒ�������ش���ʱ����", e);
        }

        // ������ڵ������ش���ָ��
        if (dayDownloadNum > ApWarnDetConfig.MAX_DOWNLOAD_DAY)
        {
            try
            {
                // �õ�ͬ�ֻ������ش���
                userDownloadNum = ApWarnDetDAO.getInstance()
                                              .getUserDownloadNum(element.getContentId());
                logger.info("����id:" + element.getContentId() + " ����ͬ�ֻ�������������Ϊ"
                            + userDownloadNum);
            }
            catch (DAOException e)
            {
                throw new BOException("�õ�ͬ�ֻ������ش���ʱ����", e);
            }

            // ������ڵ���ͬ�ֻ���������ָ��
            if (userDownloadNum > ApWarnDetConfig.MAX_DOWNLOAD_USER)
            {
                try
                {
                    // ͬ�ֻ��������������ر���
                    userQuotietyNum = ApWarnDetDAO.getInstance()
                                                  .getUserQuotietyNum(element.getContentId(),
                                                                      ApWarnDetConfig.MAX_DOWNLOAD_USER);
                    logger.info("����id:" + element.getContentId()
                                + " ����ͬ�ֻ��������������ر���Ϊ" + userQuotietyNum);
                }
                catch (DAOException e)
                {
                    throw new BOException("�õ�ͬ�ֻ��������������ر���ʱ����", e);
                }

                // �������ͬ�ֻ��������������ر���ָ��
                if (userQuotietyNum > ApWarnDetConfig.MAX_USER_DOWNLOAD_QUOTIETY)
                {
                    // ��װԤ��������Ϣ
                    retString.delete(0, 3);
                    retString.append(ApWarnDetConstants.WARN_DET_YES)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_DAY)
                             .append(dayDownloadNum)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_USER)
                             .append(userDownloadNum)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    retString.append(ApWarnDetConstants.WARN_DET_USER_DOWNLOAD_QUOTIETY)
                             .append(userQuotietyNum)
                             .append(ApWarnDetConstants.WARN_DET_PERCENT)
                             .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
                    downloadWarn++;
                }
            }
        }
        logger.info("����id" + element.getContentId() + "�жϵ��û�����Ӧ���Ƿ�����쳣:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * �ж��Ƿ����������ѵ�����
     * 
     * �����������󣨻��˱�׼������Ԥ��������Ӧ����Ҫ��ȷ�˾����׼ֵ�� 1���ֻ����������û�����500���������ã�
     * 
     * @param element Ӧ��������Ϣ
     * @return ��/��
     * @throws BOException
     */
    private String isSeriesNumWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int seriesNum = 0;

        try
        {
            // �õ�������
            seriesNum = ApWarnDetDAO.getInstance()
                                    .getSeriesNum(element.getContentId());
            logger.info("����id:" + element.getContentId() + " �õ��������Ѵ���Ϊ"
                        + seriesNum);
        }
        catch (DAOException e)
        {
            throw new BOException("�õ��������Ѵ���ʱ����", e);
        }

        // �����������ָ��
        if (seriesNum > ApWarnDetConfig.MAX_SERIES_USER)
        {
            // ��װԤ��������Ϣ
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_SERIES_USER)
                     .append(seriesNum)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            seriesNumWarn++;
        }

        logger.info("����id:" + element.getContentId() + " �ж��Ƿ����������ѵ�����:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * �ж��Ƿ��з�ָ���������������޴�
     * 
     * @param citys ��Ʒ������ǰX�ĵ���
     * @return
     */
    private String compareCityWarn(List citys)
    {
        // �õ�ָ�����������������޴�ĳ���
        String[] city = ApWarnDetConfig.MAX_DOWNLOAD_CITY.split(":");
        StringBuffer str = new StringBuffer();
        boolean isHave = false;

        // �������ݿ���������������
        for (Iterator iter = citys.iterator(); iter.hasNext();)
        {
            String data = ( String ) iter.next();

            isHave = false;

            // ����ָ��������������
            for (int i = 0; i < city.length; i++)
            {
                String ci = city[i];

                // ���������ָ��������������
                if (ci.indexOf(data) != -1)
                {
                    isHave = true;
                }
            }

            if (!isHave)
            {
                str.append(data).append(",");
            }
        }

        return str.toString();
    }

    /**
     * �ж��Ƿ��з�ָ���������������޴������
     * 
     * �������������޴󣨻��˱�׼������Ԥ��������Ӧ����Ҫ��ȷ�˾����׼ֵ�� 1����Ʒ������ǰ���ĵ����д��ڷǹ��ݡ����ڡ���ݸ����ɽ�ĵ��У������ã�
     * 
     * @param element Ӧ��������Ϣ
     * @return ��/��
     * @throws BOException
     */
    public String isCityDownloadIncreaseWarn(ApWarnDetVO element)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        List list = null;
        String temp;

        try
        {
            list = ApWarnDetDAO.getInstance()
                               .getCityDownloadIncrease(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("�õ��Ƿ��з�ָ���������������޴������ʱ����", e);
        }

        // �õ���ָ����������������
        temp = compareCityWarn(list);

        // �ж��Ƿ��з�ָ���������������޴�
        if (!"".equals(temp))
        {
            // ��װԤ��������Ϣ
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_CITY)
                     .append(temp.substring(0, temp.length() - 1))
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            cityWarn++;
        }

        logger.info("����id" + element.getContentId() + "�ж��Ƿ��е������������޴������:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * �����жϵ�ǰ����id������ʱ��������Ƿ�����쳣
     * 
     * @param timeDate �洢ʱ������������ļ���
     * @return �쳣��Ϣ
     */
    private String compareTimeWarn(List timeDate, String contentId)
    {
        // �õ���׼�ĵķ�ֵ��������ʱ��� ��11:00-12:00
        String[] times = ApWarnDetConfig.MAX_DOWNLOAD_TIME.split(",");
        StringBuffer retString = new StringBuffer();
        boolean isWarn = true;

        // ����ʱ���
        for (int j = 0; j < timeDate.size(); j++)
        {
            // ���ݿ���ʱ��η�ֵ �� 19-100
            String[] date = String.valueOf(timeDate.get(j)).split("-");

            // ֻȡ��߶���ʱ���
            if (j < 2)
            {
                for (int i = 0; i < times.length; i++)
                {
                    // ʱ�� ��11:00
                    String[] time = times[i].split("-");

                    // ��ʼСʱ�� ��11
                    int startT = Integer.parseInt(time[0].substring(0,
                                                                    time[0].lastIndexOf(":")));

                    // ����Сʱ�� ��12
                    int endT = Integer.parseInt(time[1].substring(0,
                                                                  time[1].lastIndexOf(":")));

                    // ��ǰʱ���� �� 11
                    int dT = Integer.parseInt(date[0]);

                    // �����ָ��ʱ���ڡ�Ϊ��ȷ������
                    if (startT <= dT && dT < endT)
                    {
                        isWarn = false;
                        break;
                    }
                }

                // �������ָ��ʱ�����
                if (isWarn)
                {
                    retString.append(date[0])
                             .append("ʱ�ڵ�������=")
                             .append(date[1])
                             .append(" ");
                }

                isWarn = true;
            }

            logger.info("����id:" + contentId + ",��ʱ����ͳ��Ϊ:��" + date[0]
                        + "ʱ��ʼһСʱ��������Ϊ" + date[1]);
        }

        return retString.toString();
    }

    /**
     * �ж��Ƿ�������ʱ���쳣������
     * 
     * ����ʱ��̶��� 1����ʱͳ�Ƴ���Ʒ�������ط�ֵʱ�Σ�
     * 2���ж�ʵ�ʷ�ֵʱ���Ƿ�����������ֵʱ�Σ�11:00-12:00��17:00-18:00�������ã�
     * 
     * @param element Ӧ��������Ϣ
     * @return ��/��
     * @throws BOException
     */
    public String isDownloadTimeWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        List timeDate = null;
        String str = null;

        try
        {
            timeDate = ApWarnDetDAO.getInstance()
                                   .getDownloadTime(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("�õ�����ʱ�����Ϣʱ����", e);
        }

        // У�����������ʱ����Ƿ�����쳣
        str = compareTimeWarn(timeDate, element.getContentId());

        // �ж��Ƿ�������ʱ���쳣������
        if (!"".equals(str))
        {
            // ��װԤ��������Ϣ
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_DOWNLOAD_TIME)
                     .append(str)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            timeWarn++;
        }

        logger.info("����id:" + element.getContentId() + " �ж��Ƿ�������ʱ����쳣������:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * �ж��Ƿ��������û������ʹ��������
     * 
     * �����û��ص��ʣ����˱�׼������Ԥ��������Ӧ����Ҫ��ȷ�˾����׼ֵ������ֵ���ο���
     * ���ݱ��������ж�Ϊ����ˢ��ĵ�Ӧ������ȡ��ͬһAP�Ķ��Ӧ�ð������¹�ʽ�����ص��ʣ�
     * ���㹫ʽ���ص���=��Ӧ��A��Ӧ��B�����û�������/A��B�������û�����ABΪͬһAP������ˢ��Ӧ�ã�
     * 
     * @param element Ӧ��������Ϣ
     * @return ��/��
     * @throws BOException
     */
    private String isDownloadUserIteranceWarn(ApWarnDetVO element, String date)
                    throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int iterance = 0;

        try
        {
            // ��������д���ָ��SP���ص���
            if (SPUSERITERANCEMAP.containsKey(element.getSpName()))
            {
                iterance = Integer.parseInt(String.valueOf(SPUSERITERANCEMAP.get(element.getSpName())));
            }
            else
            {
                if (ApWarnDetDAO.getInstance()
                                .getCountContentBySPName(element.getSpName(),
                                                         date) > 1)
                {
                    // �õ������û�������
                    iterance = ApWarnDetDAO.getInstance()
                                           .getDownloadUserIterance(element.getSpName(),
                                                                    date);
                }

                SPUSERITERANCEMAP.put(element.getSpName(),
                                      new Integer(iterance));

            }
        }
        catch (DAOException e)
        {
            throw new BOException("�õ������û�������ʱ����", e);
        }
        logger.info("����id:" + element.getContentId() + " �����û�������Ϊ" + iterance);

        // �Ƿ��������û������ʹ��������
        if (iterance > ApWarnDetConfig.MAX_USER_ITERANCE_QUOTIETY)
        {
            // ��װԤ��������Ϣ
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_USER_ITERANCE_QUOTIETY)
                     .append(iterance)
                     .append(ApWarnDetConstants.WARN_DET_PERCENT)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            downloadUserIteranceWarn++;
        }

        logger.info("����id" + element.getContentId() + "�Ƿ��������û������ʹ��������:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * �Ƿ���������������쳣
     * 
     * @param element
     * @param warnList
     */
    private void isDownWarn(ApWarnDetVO element, List warnList)
    {
        // ���һ����Ϣ�еõ��������
        for (Iterator iter = warnList.iterator(); iter.hasNext();)
        {
            ApWarnVo temp = ( ApWarnVo ) iter.next();

            // �������ͬ�Ĵ���
            if (element.getContentId().equals(temp.getContentId()))
            {
                element.setIsDownWarn(temp.getWarnDesc());
                break;
            }
        }
    }

    /**
     * �Ƿ����������ʱ�ηֲ��쳣���ж����賿0����8��Ӧ������������ȫ����������30%���������쳣��
     * 
     * @param element
     * @return
     */
    private String isTimeWarn(ApWarnDetVO element) throws BOException
    {
        StringBuffer retString = new StringBuffer(ApWarnDetConstants.WARN_DET_NO);
        int str = 0;

        try
        {
            str = ApWarnDetDAO.getInstance()
                              .getTimeWarn(element.getContentId());
        }
        catch (DAOException e)
        {
            throw new BOException("�õ��Ƿ����������ʱ�ηֲ��쳣ʱ����", e);
        }

        // �ж��Ƿ�������ʱ���쳣������ �ж����賿0����8��Ӧ������������ȫ����������30%���������쳣
        if (str > 30)
        {
            // ��װԤ��������Ϣ
            retString.delete(0, 3);
            retString.append(ApWarnDetConstants.WARN_DET_YES)
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            retString.append(ApWarnDetConstants.WARN_DET_TIME)
                     .append(str)
                     .append("%")
                     .append(ApWarnDetConstants.WARN_DET_INTERPUNCTION);
            timeWarn++;
        }

        logger.info("����id:" + element.getContentId() + " �ж��Ƿ�������ʱ����쳣������:"
                    + retString.toString());

        return retString.toString();
    }

    /**
     * ���ڷ��ص�ǰ���ݵ�Ԥ���ȼ�,Ԥ������
     * 
     * @param element
     * @return
     */
    private void getWarnDet(ApWarnDetVO element)
    {
        int retI = 0;
        StringBuffer retS = new StringBuffer("1");

        // ������û�����Ӧ���쳣
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsSingleUserDownloadWarn()))
        {
            retI += 9;
            retS.append(",2");
        }

        // ����ʱ�ηֲ��Ƿ�����쳣
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsDownloadTimeWarn()))
        {
            retI += 9;
            retS.append(",3");
        }

        // �����û��ص����Ƿ�����쳣
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsDownloadUserIteranceWarn()))
        {
            retI += 9;
            retS.append(",4");
        }

        // �Ƿ����������������
        if (!ApWarnDetConstants.WARN_DET_NO.equals(element.getIsSeriesNumWarn()))
        {
            retI += 11;
            retS.append(",5");
        }

        // �������Ԥ���ȼ�
        if (retI > 10)
        {
            element.setWarnGrade(ApWarnDetConstants.WARN_GRADE_VALIDATE);
        }
        else if (retI > 0)
        {
            element.setWarnGrade(ApWarnDetConstants.WARN_GRADE_SUSPICION);
        }
        else
        {
            element.setWarnGrade("");
        }

        // �������Ԥ������
        element.setWarnRule(retS.toString());
    }

    /**
     * ������һ��Ԥ������ �õ�ͳ����Ϣ
     * 
     * @param element Ԥ��������Ϣ
     * @param date ǰһ����
     * @throws BOException
     */
    private void transactFirstDate(ApWarnDetVO element, String[] date,
                                   List warnList) throws BOException
    {
        // �Ƿ���������������쳣
        isDownWarn(element, warnList);

        // ���û�����Ӧ���쳣(���غ���ֲ�)
        element.setIsSingleUserDownloadWarn(isSingleUserDownloadWarn(element));

        // ����ʱ�ηֲ�
        element.setIsDownloadTimeWarn(isTimeWarn(element));

        // �����û��ص��ʣ����˱�׼������Ԥ��������Ӧ����Ҫ��ȷ�˾����׼ֵ������ֵ���ο���
        element.setIsDownloadUserIteranceWarn(isDownloadUserIteranceWarn(element,
                                                                         date[1]));
        // �����������󣨻��˱�׼������Ԥ��������Ӧ����Ҫ��ȷ�˾����׼ֵ��
        element.setIsSeriesNumWarn(isSeriesNumWarn(element));

        // ���ڷ��ص�ǰ���ݵ�Ԥ���ȼ�,Ԥ������
        getWarnDet(element);
    }

    /**
     * ��������д��ͳ���ļ������ݼ���
     * 
     * @param date ��ǰ���ڣ�ǰһ����
     * @return
     * @throws BOException
     */
    private List getFileDateList(String[] date, List warnList)
                    throws BOException
    {
        logger.info("�õ�һ�����ݼ���");

        // ���ڴ�ŷ������ݼ���
        List retList = new ArrayList();
        List firstDateList = null;

        try
        {
            // �õ�ǰһ�յ�һ��Ԥ�����ݣ���Ҫ��������id��spName
            firstDateList = ApWarnDetDAO.getInstance()
                                        .getFirstDateList(date[1]);
        }
        catch (DAOException e)
        {
            throw new BOException("�õ�ǰһ�յ�һ��Ԥ������ʱ����", e);
        }

        logger.info("�����������");

        // ��������ÿһ��һ��Ԥ������
        for (Iterator iter = firstDateList.iterator(); iter.hasNext();)
        {
            ApWarnDetVO element = ( ApWarnDetVO ) iter.next();

            try
            {
                // ���������ݶ���Ԥ���жϡ�������
                transactFirstDate(element, date, warnList);
            }
            catch (BOException e)
            {
                logger.info("У��ָ��ʱ�����쳣����������idΪ��", element.getContentId());
            }

            // ���뷵�����ݼ���
            retList.add(element);
        }

        return retList;
    }

    /**
     * ����Ԥ��������Ч���ݵ��ļ���
     * 
     * @param list Ԥ��������Ч����
     * @param file �ļ���
     * @param date Ԥ������
     * @throws SocketException
     * @throws BOException
     */
    private void exportToExcel(List list, File file, String date)
                    throws SocketException, BOException
    {

        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        ApWarnDetVO vo = null;

        // ����sheet�������
        int maxSheetRowSize = 65534;
        int sheetNumber = 0;

        String[] titles = new String[] { "ʱ��", "��Ʒ����", "AP����", "Ӧ��ID", "Ӧ������",
                        "Ԥ���ȼ�", "���Ϲ���", "����1���Ƿ���������������쳣", "����2���Ƿ�������غ���ֲ��쳣",
                        "����3���Ƿ��������ʱ�ηֲ��쳣", "����4���Ƿ�Ǵ��������û��ظ����쳣",
                        "����5���Ƿ�Ǵ��ں��������쳣" };
        try
        {
            // �����ļ�
            workbook = Workbook.createWorkbook(file);

            for (int i = 0; i < list.size(); i++)
            {
                // ��ǰsheet��������
                int rowNumber = i % maxSheetRowSize;

                // �������sheet����������������˳�򴴽���������sheet
                if (rowNumber == 0)
                {
                    sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                                 sheetNumber);
                    sheetNumber++;

                    for (int j = 0; j < titles.length; j++)
                    {
                        // ��һ�� ���� �ڶ��� ���� ������ ����
                        sheet.addCell(new Label(j, 0, titles[j]));
                    }
                }

                vo = ( ApWarnDetVO ) list.get(i);
                sheet.addCell(new Label(0, rowNumber + 1, date));
                sheet.addCell(new Label(1, rowNumber + 1, vo.getContentName()));
                sheet.addCell(new Label(2, rowNumber + 1, vo.getSpName()));
                sheet.addCell(new Label(3, rowNumber + 1, vo.getContentId()));
                sheet.addCell(new Label(4, rowNumber + 1, vo.getPayType()));
                sheet.addCell(new Label(5, rowNumber + 1, vo.getWarnGrade()));
                sheet.addCell(new Label(6, rowNumber + 1, vo.getWarnRule()));
                sheet.addCell(new Label(7, rowNumber + 1, vo.getIsDownWarn()));
                sheet.addCell(new Label(8,
                                        rowNumber + 1,
                                        vo.getIsSingleUserDownloadWarn()));
                sheet.addCell(new Label(9,
                                        rowNumber + 1,
                                        vo.getIsDownloadTimeWarn()));
                sheet.addCell(new Label(10,
                                        rowNumber + 1,
                                        vo.getIsDownloadUserIteranceWarn()));
                sheet.addCell(new Label(11,
                                        rowNumber + 1,
                                        vo.getIsSeriesNumWarn()));
            }

            if (sheet == null)
            {
                sheet = workbook.createSheet("sheet" + (sheetNumber + 1),
                                             sheetNumber);
            }

        }
        catch (SocketException e)
        {
            // ���û�ѡ��ȡ����ʱ����������쳣�����쳣�������
            throw e;
        }
        catch (Exception e)
        {
            throw new BOException("����excel�ļ�����", e);
        }
        finally
        {
            try
            {
                workbook.write();
                workbook.close();
            }
            catch (Exception e)
            {
                throw new BOException("����excel��������", e);
            }
        }
    }

    /**
     * �������ݼ�������ͳ���ļ�
     * 
     * @param date ��ǰ���ڣ�ǰһ����
     * @param list ���ݼ���
     * @return
     * @throws BOException
     */
    private File createFileByList(String[] date, List list) throws BOException
    {
        File file = null;

        // �����ļ�
        file = new File(ApWarnConfig.localAttachFile + File.separator + date[1]
                        + "ApWarnDet" + PublicUtil.getCurDateTime("yyyy_MM_dd_HH_ss") + ".xls");

        // �������ݵ��ļ���
        try
        {
            exportToExcel(list, file, date[1]);
        }
        catch (SocketException e)
        {
            throw new BOException("�������ݵ��ļ���ʱ���û�ѡ��ȡ��", e);
        }

        return file;
    }

    /**
     * ׼������
     * 
     */
    private void start()
    {
        // ���������Ϣ
        SPUSERITERANCEMAP.clear();
        RETMAP.clear();
        cityWarn = 0;
        timeWarn = 0;
        downloadWarn = 0;
        seriesNumWarn = 0;
        downloadUserIteranceWarn = 0;
    }

    /**
     * ������װ����ȫ��Ϣ
     * 
     * @param File �����ļ�����Ϣ
     * @param list ���ݼ���
     */
    private void end(File warnDetailFile, List list)
    {
        RETMAP.put("file", warnDetailFile);

        // ����
        int suspicionNumber = 0;
        int suspicionNumber_2 = 0;
        int suspicionNumber_3 = 0;
        int suspicionNumber_4 = 0;

        // ȷ��
        int validateNumber = 0;
        int validateNumber_2_3 = 0;
        int validateNumber_3_4 = 0;
        int validateNumber_2_4 = 0;
        int validateNumber_2_3_4 = 0;
        int validateNumber_5 = 0;

        // ѭ���ж�
        for (Iterator iter = list.iterator(); iter.hasNext();)
        {
            ApWarnDetVO element = ( ApWarnDetVO ) iter.next();

            String temp = element.getWarnRule();

            // ���Ϊ����
            if (ApWarnDetConstants.WARN_GRADE_SUSPICION.equals(element.getWarnGrade()))
            {
                suspicionNumber++;

                if (temp.indexOf("2") > 0)
                {
                    suspicionNumber_2++;
                }
                else if (temp.indexOf("3") > 0)
                {
                    suspicionNumber_3++;
                }
                else if (temp.indexOf("4") > 0)
                {
                    suspicionNumber_4++;
                }
            }
            // ���Ϊȷ��
            else if (ApWarnDetConstants.WARN_GRADE_VALIDATE.equals(element.getWarnGrade()))
            {
                validateNumber++;

                if (temp.indexOf("2") > 0 && temp.indexOf("3") > 0
                    && temp.indexOf("4") > 0)
                {
                    validateNumber_2_3_4++;
                }
                else if (temp.indexOf("2") > 0 && temp.indexOf("3") > 0)
                {
                    validateNumber_2_3++;
                }
                else if (temp.indexOf("3") > 0 && temp.indexOf("4") > 0)
                {
                    validateNumber_3_4++;
                }
                else if (temp.indexOf("2") > 0 && temp.indexOf("4") > 0)
                {
                    validateNumber_2_4++;
                }

                if (temp.indexOf("5") > 0)
                {
                    validateNumber_5++;
                }
            }
        }
        
        RETMAP.put("suspicionNumber", String.valueOf(suspicionNumber));
        RETMAP.put("suspicionNumber_2", String.valueOf(suspicionNumber_2));
        RETMAP.put("suspicionNumber_3", String.valueOf(suspicionNumber_3));
        RETMAP.put("suspicionNumber_4", String.valueOf(suspicionNumber_4));
        
        RETMAP.put("validateNumber", String.valueOf(validateNumber));
        RETMAP.put("validateNumber_2_3", String.valueOf(validateNumber_2_3));
        RETMAP.put("validateNumber_3_4", String.valueOf(validateNumber_3_4));
        RETMAP.put("validateNumber_2_4", String.valueOf(validateNumber_2_4));
        RETMAP.put("validateNumber_2_3_4", String.valueOf(validateNumber_2_3_4));
        RETMAP.put("validateNumber_5", String.valueOf(validateNumber_5));
    }

    /**
     * ����Ԥ���������巽��
     * 
     * @throws BOException
     */
    public Map init(List warnList) throws BOException
    {
        logger.info("��ʼִ�ж���Ԥ��");

        File warnDetailFile = null;

        List dataList = null;

        // ��ʼǰ׼��
        start();

        // ����������
        ApWarnDetConfig.loadConfig();

        // �õ���ǰ���ں���һ���ڵ��ַ���ʽ
        String[] date = getDataString();

        try
        {
            // ���ݵ�һ����Ԥ����������id���õ��������ݣ����뱾����ʱ���С�
            copyReportDateToTemp(date);
        }
        catch (DAOException e)
        {
            throw new BOException("��Ԥ�����������뱾����ʱ����̳���", e);
        }

        // Ҫ�ش������ݼ���ͳ��
        dataList = getFileDateList(date, warnList);

        // д���ļ�
        warnDetailFile = createFileByList(date, dataList);

        logger.info("����Ԥ��ִ�н����������ļ�Ϊ=" + warnDetailFile.getName());

        // ����ǰ��װ��Ϣ׼��
        end(warnDetailFile, dataList);

        return RETMAP;
    }
}
