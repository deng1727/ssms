package com.aspire.ponaadmin.web.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * 处理和转换时间日期对象的工具类
 *
 */
public class TimeUtils
{
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * SimpleDateFormat对象
     */
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 时间字符串格式每次
     * @author xieyonggui
     *
     */
    public static enum DateFormat{
        /**
         * 以-和:分隔
         */
        separated,
        /**
         * 连接
         */
        join
    };
    /**
     * 将时间日期字符串转换为java.util.Date对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr 时间日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr)
    {
        return parseToDate(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为java.util.Date对象
     * @param dateStr 时间日期字符串
     * @param format 时间格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateString,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    /**
     * 将时间日期字符串转换为时间戳(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr)
    {
        return parseToTimestamp(dateStr, DateFormat.separated);
    }
    /**
     * 将时间日期字符串转换为时间戳
     * @param dateStr
     * @param format 时间格式
     * @return 
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr,DateFormat format)
    {
        try
        {
            return new Timestamp(parseToDate(dateStr,format).getTime());
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param date 被转换的java.util.Date对象
     * @return
     */
    public static String format(Date date)
    {
        return format(date,DateFormat.separated);
    }
    /**
     * 将java.util.Date对象转换为格式化的时间日期字符串
     * @param date 被转换的java.util.Date对象
     * @param format 时间格式
     * @return
     */
    public static String format(Date date,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @param timestamp  被转换的时间戳
     * @return
     */
    public static String format(Timestamp timestamp)
    {
        return format(timestamp, DateFormat.separated);
    }
    /**
     * 将时间戳转换为格式化的时间日期字符串
     * @param timestamp  被转换的时间戳
     * @param format 时间格式
     * @return
     */
    public static String format(Timestamp timestamp,DateFormat format)
    {
        SimpleDateFormat sdf = getSDF(format);
        try
        {
            Date date = new Date(timestamp.getTime());
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    /**
     * 获取当前时间对应的java.util.Date对象
     * @return
     */
    public static Date getDate()
    {
        return new Date(System.currentTimeMillis());
    }
    /**
     * 获取当前时间对应的时间戳
     * @return
     */
    public static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * 获取当前时间的时间戳的字符串(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getTimestampString()
    {
        return format(new Date());
    }
    /**
     * 获取当前时间的时间戳的字符串 
     * @param format 时间字符串格式
     * @return
     */
    public static String getTimestampString(DateFormat format)
    {
        return format(new Date(),format);
    }
    
    /**
     * 将合乎格式的日期字串转换成Timestamp对象(默认时间格式为yyyy-MM-dd HH:mm:ss)
     * 转换失败则返回null
     * @param dateString 时间字符串
     * @return
     */
    public static Timestamp getTimestamp(String dateString){
        return getTimestamp(dateString, DateFormat.separated);
    }
    /**
     * 将合乎格式的日期字串转换成Timestamp对象
     * 转换失败则返回null
     * @param dateString 时间字符串
     * @param format 时间格式
     * @return
     */
    public static Timestamp getTimestamp(String dateString,DateFormat format)
    {
        Date date = parseToDate(dateString, format);
        if (date==null)
        {
            return null;
        }
        return new Timestamp(date.getTime());
    }
    /**
     * 获取指定毫秒数对应的时间戳
     * @param timeMillis
     * @return
     */
    public static Timestamp getTimestamp(long timeMillis)
    {
        return new Timestamp(timeMillis);
    }
    
    /**
     * 获取两个java.util.Date之间的相差的毫秒数
     * @param lower
     * @param upper
     * @return
     */
    public static long timeDifference(Date lower,Date upper)
    {
        return upper.getTime()-lower.getTime();
    }
    /**
     * 获取java.util.Date中年、月、日等值
     * @param date
     * @param type Calendar.YEAR 年 Calendar.MONTH月 Calendar.DATE日等
     * @return
     */
    public static int get(Date date,int type)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }
    /**
     * 获取时间戳中年、月、日等值 
     * @param date
     * @param type Calendar.YEAR 年 Calendar.MONTH月 Calendar.DATE日等
     * @return
     */
    public static int get(Timestamp timestamp,int type)
    {
        Date date = new Date(timestamp.getTime());
        return get(date, type);
    }
    /**
     * 获取指定的SimpleDateFormat对象
     * @param format
     * @return
     */
    private static SimpleDateFormat getSDF(DateFormat format)
    {
        switch (format)
        {
            case separated:
                return sdf1;
            case join:
                return sdf2;
            default:
                return sdf1;
        }
    }
    
    
    /**
     * 得到当前日期字符
     * @return String，当前日期字符
     */
    public static String getCurDateTime ()
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
            DATE_FORMAT) ;
        sdf.setTimeZone(TimeZone.getDefault()) ;
        return sdf.format(now.getTime()) ;
    }

    /**
     * 得到当前日期
     *
     * @param dateFormate String，格式化字符串 
     * @return String，当前日期
     */
    public static String getCurDateTime (String dateFormate)
    {
        try
        {
            Calendar now = Calendar.getInstance(TimeZone.getDefault()) ;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                dateFormate) ;
            sdf.setTimeZone(TimeZone.getDefault()) ;
            return sdf.format(now.getTime()) ;
        }
        catch (Exception e)
        {
            return getCurDateTime() ; //如果无法转化，则返回默认格式的时间。
        }
    }

    /**
     * 格式化日期为字符串
     * @param date，日期
     * @param dateFormate，格式化字符串
     * @return String，格式化后的日期字符串
     */
    public static String getDateString (java.util.Date date, String dateFormate)
    {
        if (date == null)
        {
            return "" ;
        }
        try
        {

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormate) ;
            //Stringsdf.format(date);
            return sdf.format(date) ;
        }
        catch (Exception e)
        {
            //如果无法转化，则返回默认格式的时间。
            return getCurDateTime() ; 
        }
    }
    
    
    /**
     * 将时间戳转换为格式化的时间日期字符串
     * @param timestamp  被转换的时间戳
     * @param format 时间格式
     * @return
     */
    public static String format(Timestamp timestamp,String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try
        {
            Date date = new Date(timestamp.getTime());
            return sdf.format(date);
        }
        catch (RuntimeException e)
        {
            return null;
        }
    }
    
    /**
     * 获取相隔几天的日期日期
     * @param date
     * @param dayCount 相隔天数
     * @return
     */
    public static Date getDay(Date date,int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);
		date = calendar.getTime();
		return date;
	}
    
    /**
     * 将时间日期字符串转换为java.util.Date对象
     * @param dateStr 时间日期字符串
     * @param format 时间格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateString,String format)
    {
    	 SimpleDateFormat sdf = new SimpleDateFormat(format);
        
        try
        {
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    public static String getTimeString(long timelnMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timelnMillis);
        Date date = calendar.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTypeDate = f.format(date);
        return newTypeDate;    
      }
    
    public static Date getTimeString(int day,int hour,int minute)
    {
    	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);    
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.HOUR, hour);
		Date date = calendar.getTime();
//		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String newTypeDate = f.format(date);
		return date;    
    }
    
    public static void main(String[] args) {
    	Calendar calendar = Calendar.getInstance();
    	System.out.println(calendar.getTime());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    	
    	System.out.println(sdf.format(System.currentTimeMillis()));
//		calendar.add(Calendar.DATE, -1);    //得到前一天
//		calendar.add(Calendar.MINUTE, -1);
//		calendar.add(Calendar.HOUR, -1);
//		Date date = calendar.getTime();
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		System.out.println(df.format(date));
//		System.out.println(TimeUtils.getCurDateTime("yyyyMMddHHmmssSSS").substring(0,15));
//		//315504000000
//		System.out.println(TimeUtils.parseToDate("1980-01-01", "yyyy-MM-dd").getTime());
//												   
//	
//		System.out.println(TimeUtils.getTimeString(1395637140000l));
//		System.out.println(TimeUtils.getTimeString(1395635880054l));;
//		System.out.println(TimeUtils.getTimeString(1395639480054l));;
//		
//		String current=TimeUtils.getTimeString(0, 0, 0).getTime()+"";//当前
//		String source=TimeUtils.getTimeString(0, 0, -1).getTime()+"";//前一分钟
//		
//		System.out.println(current);
//		System.out.println(source);
//		
//		System.out.println(TimeUtils.parseToDate("2099-01-01", "yyyy-MM-dd").getTime());
//		
		
	}

}
