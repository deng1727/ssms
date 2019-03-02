package com.aspire.ponaadmin.web.util ;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone ;
import java.util.Date ;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * ���ڵ�һЩ��غ���
 *
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 *
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *
 * <p>Company: ׿�����뼼�������ڣ����޹�˾</p>
 *
 *  @author    ������
 *
 *  @version   1.0 *
 *
 */

public class DateUtil
{

    /**
     * Automatically generated method: DateUtil
     */
    private DateUtil () {

    }

    /**
     * ��java.util.Date����ת��Ϊjava.sql.Timestamp
     * @param date java.util.Date
     * @return Timestamp
     */
    public static final java.sql.Timestamp changToSqlTimestamp (
        java.util.Date date)
    {

        if (date == null)
            return null ;
        else
            return new java.sql.Timestamp(date.getTime()) ;
    }

    /**
     * ��ava.sql.Timestamp����ת��Ϊava.util.Date
     * @param sqlData Timestamp
     * @return Date
     */
    public static final java.util.Date changToUtilData (
        java.sql.Timestamp sqlData)
    {

        if (sqlData == null)
            return null ;
        else
            return new java.util.Date(sqlData.getTime()) ;
    }

    /**
     * �õ��뵱ǰ�������distanceDate������ڣ�
     * �������磺yyyy-MM-dd��ֵ��
     * @param distanceDate int ���ʱ�䡣
     * @return String �������磺yyyy-MM-dd��ֵ��
     */
    public static String getSpecifyDate (int distanceDate)
    {
        String dateFormate = "yyyy-MM-dd" ;
        long longDate = System.currentTimeMillis() +
            (distanceDate * 24 * 60 * 60 * 1000) ;
        Date date = new Date(longDate) ;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            dateFormate) ;
        sdf.setTimeZone(TimeZone.getDefault()) ;

        return sdf.format(date) ;

    }

    /**
     * ת�����ں���
     * <pre>
     *  ���ת������,���ض���
     * </pre>
     * @param date Date
     * @param formateString String
     * @return String
     */
    public static String formatDate(Date date, String formateString)
    {
        try
        {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                formateString) ;
            return sdf.format(date);
        }
        catch (Exception e)
        {
            return date.toString();
        }
    }

    /**
     * ת�����ں���
     * <pre>
     * ���ת������,���ض���
     * </pre>
     * @param time Timestamp
     * @param formateString String
     * @return String
     */
    public static String formatDate(Timestamp time, String formateString)
    {
        try
        {
            Date date = new Date(time.getTime()) ;
            return formatDate(date, formateString);
        }
        catch (Exception e)
        {
            return time.toString();
        }
    }

    /**
     * ��String�͵�����ת��ΪTimestamp�͵����ڡ�
     *
     * @param sDate String
     * @param dateFormat String
     * @return Timestamp
     */
    public static java.sql.Timestamp stringToTimestamp (String sDate,
                                                       String dateFormat)
    {
        java.util.Date date = stringToDate(sDate,dateFormat);
        if(date==null)
        {
            return null;
        }
        return new Timestamp(date.getTime()) ;
    }

    /**
     * ��yyyymmddhhmmss���ַ���ת��Ϊyyyymmddhhmmss��java.util.Date��
     *
     * @param strDateFormat String
     * @param strValue String
     * @return Date
     */
    public static java.util.Date stringToDate (String strValue,
                                               String strDateFormat)
    {
        try
        {
            SimpleDateFormat oFormatter = new SimpleDateFormat(strDateFormat) ;
            return oFormatter.parse(strValue) ;
        }
        catch (ParseException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    /**
     * ������������ַ��������ڽ��мӼ������󷵻���Ӧ��ʽ�������ַ���
     * @param dateStr �����ַ���
     * @param format ���ڸ�ʽ
     * @param days �������
     * @return
     */
    public static String dateChangeDays(String dateStr, String format, int days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try
        {
            c.setTime(sdf.parse(dateStr));
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, days);
        return sdf.format(c.getTime());
    }

}
