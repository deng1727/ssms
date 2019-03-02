package com.aspire.ponaadmin.web.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * �����ת��ʱ�����ڶ���Ĺ�����
 *
 */
public class TimeUtils
{
    /**
     * SimpleDateFormat����
     */
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * SimpleDateFormat����
     */
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * ʱ���ַ�����ʽÿ��
     * @author xieyonggui
     *
     */
    public static enum DateFormat{
        /**
         * ��-��:�ָ�
         */
        separated,
        /**
         * ����
         */
        join
    };
    /**
     * ��ʱ�������ַ���ת��Ϊjava.util.Date����(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * @param dateStr ʱ�������ַ���
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr)
    {
        return parseToDate(dateStr, DateFormat.separated);
    }
    /**
     * ��ʱ�������ַ���ת��Ϊjava.util.Date����
     * @param dateStr ʱ�������ַ���
     * @param format ʱ���ʽ
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
     * ��ʱ�������ַ���ת��Ϊʱ���(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Timestamp parseToTimestamp(String dateStr)
    {
        return parseToTimestamp(dateStr, DateFormat.separated);
    }
    /**
     * ��ʱ�������ַ���ת��Ϊʱ���
     * @param dateStr
     * @param format ʱ���ʽ
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
     * ��java.util.Date����ת��Ϊ��ʽ����ʱ�������ַ���(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * @param date ��ת����java.util.Date����
     * @return
     */
    public static String format(Date date)
    {
        return format(date,DateFormat.separated);
    }
    /**
     * ��java.util.Date����ת��Ϊ��ʽ����ʱ�������ַ���
     * @param date ��ת����java.util.Date����
     * @param format ʱ���ʽ
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
     * ��ʱ���ת��Ϊ��ʽ����ʱ�������ַ���(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * @param timestamp  ��ת����ʱ���
     * @return
     */
    public static String format(Timestamp timestamp)
    {
        return format(timestamp, DateFormat.separated);
    }
    /**
     * ��ʱ���ת��Ϊ��ʽ����ʱ�������ַ���
     * @param timestamp  ��ת����ʱ���
     * @param format ʱ���ʽ
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
     * ��ȡ��ǰʱ���Ӧ��java.util.Date����
     * @return
     */
    public static Date getDate()
    {
        return new Date(System.currentTimeMillis());
    }
    /**
     * ��ȡ��ǰʱ���Ӧ��ʱ���
     * @return
     */
    public static Timestamp getTimestamp()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * ��ȡ��ǰʱ���ʱ������ַ���(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String getTimestampString()
    {
        return format(new Date());
    }
    /**
     * ��ȡ��ǰʱ���ʱ������ַ��� 
     * @param format ʱ���ַ�����ʽ
     * @return
     */
    public static String getTimestampString(DateFormat format)
    {
        return format(new Date(),format);
    }
    
    /**
     * ���Ϻ���ʽ�������ִ�ת����Timestamp����(Ĭ��ʱ���ʽΪyyyy-MM-dd HH:mm:ss)
     * ת��ʧ���򷵻�null
     * @param dateString ʱ���ַ���
     * @return
     */
    public static Timestamp getTimestamp(String dateString){
        return getTimestamp(dateString, DateFormat.separated);
    }
    /**
     * ���Ϻ���ʽ�������ִ�ת����Timestamp����
     * ת��ʧ���򷵻�null
     * @param dateString ʱ���ַ���
     * @param format ʱ���ʽ
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
     * ��ȡָ����������Ӧ��ʱ���
     * @param timeMillis
     * @return
     */
    public static Timestamp getTimestamp(long timeMillis)
    {
        return new Timestamp(timeMillis);
    }
    
    /**
     * ��ȡ����java.util.Date֮������ĺ�����
     * @param lower
     * @param upper
     * @return
     */
    public static long timeDifference(Date lower,Date upper)
    {
        return upper.getTime()-lower.getTime();
    }
    /**
     * ��ȡjava.util.Date���ꡢ�¡��յ�ֵ
     * @param date
     * @param type Calendar.YEAR �� Calendar.MONTH�� Calendar.DATE�յ�
     * @return
     */
    public static int get(Date date,int type)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }
    /**
     * ��ȡʱ������ꡢ�¡��յ�ֵ 
     * @param date
     * @param type Calendar.YEAR �� Calendar.MONTH�� Calendar.DATE�յ�
     * @return
     */
    public static int get(Timestamp timestamp,int type)
    {
        Date date = new Date(timestamp.getTime());
        return get(date, type);
    }
    /**
     * ��ȡָ����SimpleDateFormat����
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
     * �õ���ǰ�����ַ�
     * @return String����ǰ�����ַ�
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
     * �õ���ǰ����
     *
     * @param dateFormate String����ʽ���ַ��� 
     * @return String����ǰ����
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
            return getCurDateTime() ; //����޷�ת�����򷵻�Ĭ�ϸ�ʽ��ʱ�䡣
        }
    }

    /**
     * ��ʽ������Ϊ�ַ���
     * @param date������
     * @param dateFormate����ʽ���ַ���
     * @return String����ʽ����������ַ���
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
            //����޷�ת�����򷵻�Ĭ�ϸ�ʽ��ʱ�䡣
            return getCurDateTime() ; 
        }
    }
    
    
    /**
     * ��ʱ���ת��Ϊ��ʽ����ʱ�������ַ���
     * @param timestamp  ��ת����ʱ���
     * @param format ʱ���ʽ
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
     * ��ȡ����������������
     * @param date
     * @param dayCount �������
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
     * ��ʱ�������ַ���ת��Ϊjava.util.Date����
     * @param dateStr ʱ�������ַ���
     * @param format ʱ���ʽ
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
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
    	
    	System.out.println(sdf.format(System.currentTimeMillis()));
//		calendar.add(Calendar.DATE, -1);    //�õ�ǰһ��
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
//		String current=TimeUtils.getTimeString(0, 0, 0).getTime()+"";//��ǰ
//		String source=TimeUtils.getTimeString(0, 0, -1).getTime()+"";//ǰһ����
//		
//		System.out.println(current);
//		System.out.println(source);
//		
//		System.out.println(TimeUtils.parseToDate("2099-01-01", "yyyy-MM-dd").getTime());
//		
		
	}

}
