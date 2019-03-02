package com.aspire.ponaadmin.web.util ;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone ;
import java.util.Date ;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * 日期的一些相关函数
 *
 * <p>Copyright: Copyright (c) 2003-2005 <p>
 *
 * <p>ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 *
 * <p>Company: 卓望数码技术（深圳）有限公司</p>
 *
 *  @author    胡春雨
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
     * 把java.util.Date类型转换为java.sql.Timestamp
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
     * 把ava.sql.Timestamp类型转换为ava.util.Date
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
     * 得到与当前日期相差distanceDate天的日期，
     * 返回型如：yyyy-MM-dd的值。
     * @param distanceDate int 相差时间。
     * @return String 返回型如：yyyy-MM-dd的值。
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
     * 转换日期函数
     * <pre>
     *  如果转换出错,返回对象串
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
     * 转换日期函数
     * <pre>
     * 如果转换出错,返回对象串
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
     * 把String型的日期转换为Timestamp型的日期。
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
     * 把yyyymmddhhmmss的字符串转换为yyyymmddhhmmss的java.util.Date型
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
     * 将输入的日期字符串按日期进行加减天数后返回相应格式的日期字符串
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @param days 相差天数
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
