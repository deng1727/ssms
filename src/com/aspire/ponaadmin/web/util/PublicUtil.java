package com.aspire.ponaadmin.web.util ;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat ;
import com.aspire.common.log.proxy.JLogger ;
import com.aspire.common.log.proxy.LoggerFactory ;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

import java.util.regex.Pattern ;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author		zhaoping
 * @version 1.0
 */

public class PublicUtil
{

	public static double scale2Ten(double d)
	{
		 DecimalFormat myformat5 = null;
		  try{
		      myformat5 = (DecimalFormat)NumberFormat.getPercentInstance();
		  }catch(ClassCastException e)
		  {
		   System.err.println(e); 
		  }
		  myformat5.applyPattern("0.0000");

		
		String temp = myformat5.format(d);
		
		int length = temp.length();
		 
		int index = temp.indexOf('.');
		temp = temp.replaceAll("[.]", "");
		
		double intStr = Integer.parseInt(temp);
		
		double rate = 0.01;
		
		for(int i=1; i<(length-index); i++)
		{
			rate*=10;
		}
		
		intStr =  intStr/rate;
		
		return intStr;
		
	}
	/**
	 * ���췽��
	 */
	private PublicUtil () 
	{

	}

    /** Automatically generated javadoc for: BYTE_CHINESE */
    private static final int BYTE_CHINESE = 255 ;

    /**
     * ��ʱ�������Ĵ�С
     */
    private static final int TEMP_BUF_SIZE = 1024 ;
    /**

     * ȫ�ļ�����������Ҫ���˵������ַ�

     */

    public static final String SEARCH_MBRAND_CHARACTER_FILTRATE_STR = "_ -";
    /**
     * ��־
     */
    private static final JLogger LOG = LoggerFactory.getLogger(PublicUtil.class) ;
    /**

     * �ַ����滻

     * 

     * @return String

     * @param src

     * @param str1

     * @param str2

     */

    public static String StrReplaceFull(String src, String str1, String str2)

    {

        StringBuffer str_temp;

        int start;

        int position;

        if (src == null || "".equals(src) || "".equals(str1))

        {

            return src;

        }

        else

        {

            str_temp = new StringBuffer().append(src);

            start = 0;

            position = str_temp.toString().indexOf(str1, start);

            while ((position >= 0) && (position < str_temp.toString().length()))

            {

                str_temp = str_temp.replace(position,

                                            position + str1.length(),

                                            str2);

                start = position + str2.length();

                position = str_temp.toString().indexOf(str1, start);

            }

            return str_temp.toString();

        }

    }

   

public static String filterMbrandEspecialChar(String mbrand)
    {

		if (mbrand == null)
		mbrand = "";
		mbrand = mbrand.trim();
		String filterStr = SEARCH_MBRAND_CHARACTER_FILTRATE_STR;
		for (int i = 0; i < filterStr.length(); i++)
	{
			char filter = filterStr.charAt(i);
			boolean flag = false;

			for (int k = 0; k < mbrand.length(); k++)

			{
				if (String.valueOf(filter)

				.equals(String.valueOf(mbrand.charAt(k))))
				{

					flag = true;

				}

			}
			if (flag)

			{
				mbrand = StrReplaceFull(mbrand,

				String.valueOf(filter),

				"");
			}
		}

		return mbrand;

	}
    /**
     * ���ض��ַ�����������һЩλ��Ĭ�����Ϊ0
     * @param bitNumber int���ǲ�0��ĳ��ȣ����ܳ���
     * @param s String������ӵ��ַ��� 
     * @return String ��������ַ�
     */
    public static String lPad (String s, int bitNumber)
    {
        if (s == null)
        {
            return null ;
        }

        int length = s.length() ;
        //�ж���������Ƿ�Ϸ�
        if (length > bitNumber)
        {
            return s.substring(0,bitNumber) ;
        }

        if (length == bitNumber)
        {
            return s ;
        }

        //���γ���ӵ�0��
        int addNum = bitNumber - length ;
        String addString = "0" ;
        for (int i = 1 ; i < addNum ; i++)
        {
            addString += '0' ;
        }
        return addString + s ;
    }
    
    /**
     * ���ض��ַ������ұ����һЩλ����ӵ��ַ�Ϊ�����
     * 
     * @param bitNumber
     *            int���ǲ���ĳ��ȣ����ܳ���
     * @param s
     *            String������ӵ��ַ���
     * @return String ��������ַ�
     */
    public static String rPad(String s, int bitNumber, String add)
    {
        if (s == null)
        {
            return null;
        }

        int length = s.length();
        // �ж���������Ƿ�Ϸ�
        if (length > bitNumber)
        {
            return s.substring(0, bitNumber);
        }

        if (length == bitNumber)
        {
            return s;
        }

        // ���γ���ӵ�0��
        int addNum = bitNumber - length;
        String addString = "";
        for (int i = 0; i < addNum; i++)
        {
            addString += add;
        }
        return s + addString;
    }

    /**
     * ��ʱ��������ʱ�ļ���
     *
     * @return String
     */
    public static String getGenFileName ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS") ;
        String sf = "icp" + sdf.format(date) ;
        return sf ;
    }

    /**
     * �ַ����滻
     *
     * @return String
     * @param src
     * @param str1
     * @param str2 
     */
    public static String StrReplace (String src, String str1, String str2)
    {
        StringBuffer str_temp ;
        int start ;
        int position ;
        if (!(src.length() > 0) || "".equals(str1))
        {
            return src ;
        }
        else
        {
            str_temp = new StringBuffer().append(src) ;
            start = 0 ;
            position = str_temp.toString().indexOf(str1, start) ;
            while ((position > 0) && (position < str_temp.toString().length()))
            {
                str_temp = str_temp.replace(position, position + str1.length(),
                                            str2) ;
                start = position + str2.length() ;
                position = str_temp.toString().indexOf(str1, start) ;
            }
            return str_temp.toString() ;
        }
    }

    /**
     * ר�Ŵ��� & ������&amp;��ת��
     *
     * @param src
     * @param str1
     * @param str2 
     * @return String
     */
    public static String StrReplaceForAmp (String src, String str1, String str2)
    {
        String tmp = StrReplace(src, str2, str1) ;
        return StrReplace(tmp, str1, str2) ;
    }

    /**
     * ���ݲ�ͬ�Ĳ���ϵͳ������Ӧ��Ŀ¼
     * @param path
     * @return
     */
    public static String toOSDir (String path)
    {
        String osName = "" ;
        String retPath = "" ;
        osName = System.getProperty("os.name").toLowerCase() ;
        if (osName.indexOf("windows") != -1)
        { //����ϵͳ��WINDOWS
            retPath = path.replace('/', '\\') ;
        }
        else
        { //unix
            retPath = path.replace('\\', '/') ;
        }
        return retPath ;

    }

    /**
     * �õ���ǰ����
     * @return int����ǰ����
     */
    public static int getCurMin ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
    }

    /**
     * �õ���ǰСʱ
     * @return int����ǰСʱ
     */
    public static int getCurHour ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("k") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
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
            LOG.error(e) ;
            //����޷�ת�����򷵻�Ĭ�ϸ�ʽ��ʱ�䡣
            return getCurDateTime() ; 
        }
    }
    
    /**
     * ��ʽ������Ϊ�ַ���
     * @param dateString�������ַ���
     * @param dateFormate����ʽ���ַ���
     * @return String������������ڣ��޷�ת������null��
     */
    public static java.util.Date parseStringToDate (String dateString, String dateFormate)
    {
        try
        {

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormate) ;
            //Stringsdf.format(date);
            return sdf.parse(dateString);
        }
        catch (Exception e)
        {
            LOG.error(e) ;
            //����޷�ת�����򷵻�Ĭ�ϸ�ʽ��ʱ�䡣
            return null;
        }
    }

    /**
     * ��ʽ������Ϊ�ַ���
     * @param date Date������
     * @return String����ʽ����������ַ���
     */
    public static String getDateString (java.util.Date date)
    {
        return getDateString(date,"yyyyMMddHHmmss");
    }
    
    /**
     * �õ���ǰ��
     * @return int����ǰ��
     */
    public static int getCurDate ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
    }

    /**
     * �õ���ǰ��
     * @return String����ǰ��
     */
    public static String getCurYear ()
    {
        java.util.Date now = new java.util.Date() ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy") ;
        return formatter.format(now) ;
    }

    /**
     * �õ���ǰ��
     * @return String����ǰ��
     */
    public static String getCurMonth ()
    {
        java.util.Date now = new java.util.Date() ;
        SimpleDateFormat formatter = new SimpleDateFormat("MM") ;
        return formatter.format(now) ;
    }

    /**
     * �õ��ϸ��µ��·ݣ�����Ϊ��λ�ַ���
     * @return String���ϸ��µ��·� 
     */
    public static String getLastMonth ()
    {
        //�õ���ǰ�·�
        int currentMonth = Integer.parseInt(getCurMonth()) ;
        if (currentMonth == 1)
        {
            return "12" ;
        }
        int lastMonth = currentMonth - 1 ;
        String lastMonthString = Integer.toString(lastMonth) ;

        if (lastMonthString.length() == 2)
        {
            return lastMonthString ;
        }
        else
        {
            lastMonthString = "0" + lastMonthString ;
            return lastMonthString ;
        }

    }

    /**
     * �õ����ö�ջ�쳣��Ϣ
     * @return String�����ö�ջ��Ϣ
     */
    public static String GetCallStack (Exception e)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        e.printStackTrace(ps);
        ps.flush();
        String stck = os.toString();
        ps.close();
        PublicUtil.CloseOutStream(os);
        return stck;
    }

    /**
     * ����ʵ����string �е��滻
     *
     * @return String
     * @param line
     * @param newString
     * @param oldString 
     */
    public static final String replace (String line, String oldString,
                                        String newString)
    {
        if (line == null)
        {
            return null ;
        }
        int i = 0 ;
        if ((i = line.indexOf(oldString, i)) >= 0)
        {
            char[] line2 = line.toCharArray() ;
            char[] newString2 = newString.toCharArray() ;
            int oLength = oldString.length() ;
            StringBuffer buf = new StringBuffer(line2.length) ;
            buf.append(line2, 0, i).append(newString2) ;
            i += oLength ;
            int j = i ;
            while ((i = line.indexOf(oldString, i)) > 0)
            {
                buf.append(line2, j, i - j).append(newString2) ;
                i += oLength ;
                j = i ;
            }
            buf.append(line2, j, line2.length - j) ;
            return buf.toString() ;
        }
        return line ;
    }

    /** ���ܣ�ת�������ַ��Ա���ҳ������ȷ��ʾ�������ַ����£�
     * "��&��<��>���ո񡢻س� ��Ӧ��html�ַ�Ϊ &quot; &amp; &lt; &gt; &nbsp; br
     *
     * @param htmlString
     * @return
     */
    public static String htmlSpecialCharConvert (String htmlString)
    {
        if (htmlString == null)
        {
            return "" ;
        }
        StringBuffer newString = new StringBuffer() ;
        String specialChars = "\"&<> " ;
        String[] charToString =
            {"&quot;", "&amp;", "&lt;", "&gt;", "&nbsp;"} ;

        for (int i = 0 ; i < htmlString.length() ; i++)
        {
            char cTemp = htmlString.charAt(i) ;
            int iTemp = specialChars.indexOf(cTemp) ;
            if (iTemp == -1)
            {
                //���������ַ�
                newString.append(cTemp) ;
            }
            else
            {
                //�������ַ�
                newString.append(charToString[iTemp]) ;
            }
        }
        String result = newString.toString() ;
        result = replace(result,"\r\n","<br>");
        return result;
    }

    /**
     * ��ȡһ��Ŀ¼�µ��ļ��б�
     * @param dir String��Ŀ¼·��
     * @return String[]���ļ��б�
     * @throws Exception
     */
    public static String[] getFileListFromDir (String dir)
        throws Exception
    {
        File file = new File(dir) ;
        return file.list() ;
    }

    /**
     * return the truncated string within the fixed length
     * @param str the given string
     * @param len the given length
     * @return
     */
    public static String getFixLengthStr (String str, int len)
    {
        if ((str == null) || (len <= 0))
        {
            return "" ;
        }
        int oldlen = str.length() ;
        if (oldlen <= len)
        { //the real length less than the assigned one
            return str ;
        }
        else
        { //the assigned length is shorted than str,cut it
            String newStr = str.substring(0, len) ;
            return newStr ;
        }
    }

    /**
     * return the trimmed string even it's null
     * @param str the string need to be trimed
     * @return string
     */
    public static String trim (String str)
    {
        if (str == null)
        {
            return "" ;
        }
        return str.trim() ;
    }

    /**
     * ��ȡһ��������ҳ����ʾ���ַ���
     * ���Ϊnull��ʾ���ַ������Ժ���ܻ������html�ı��롣
     * @param str String
     * @return String
     */
    public static String getPageStr (String str)
    {
        if (str == null)
        {
            return "" ;
        }
        return str ;
    }

    /**
     * ��ȡ�ַ������ȣ������ַ���������
     * @param strTemp String��������ַ�
     * @return int���ַ�����
     */

    public static final int getLength (String strTemp)
    {
        int i ;
        int sum ;
        sum = 0 ;
        for (i = 0 ; i < strTemp.length() ; i++)
        {
            if ((strTemp.charAt(i) >= 0) && (strTemp.charAt(i) <= BYTE_CHINESE))
            {
                sum += 1 ;
            }
            else
            {
                sum += 2 ;
            }
        }
        return sum ;

    }

    /**
     * ����ַ������Ƿ��зǷ��ַ�
     * @param targetStr String��Ҫ�����ַ���
     * @param IllegalString String�� �Ƿ��ַ��ļ���
     * @return boolean���Ƿ�false��Ϸ�true
     */

    public static final boolean checkIllegalChar (String targetStr,
                                                  String IllegalString)
    {
        for (int j = 0 ; j < IllegalString.length() ; j++)
        {
            if (targetStr.indexOf(IllegalString.charAt(j)) >= 0)
            {
                return false ;
            }
        }
        return true ;

    }

    /**
     * ���һ���ַ�����ֻ�ܰ�������
     * @param targetStr String��������ַ�
     * @return boolean���Ƿ�false��Ϸ�true
     */

    public static final boolean checkDigital (String targetStr)
    {
        return Pattern.matches("[0-9]*", targetStr) ;
    }

    /**
     * ���绰�����Ƿ��������ֻ���+��ͷ���涼�����ֵ��ַ�
     * @param RegularExp String��������ַ�
     * @return boolean���Ƿ�false��Ϸ�true
     */

    public static final boolean checkTel (String RegularExp)
    {
        boolean result = (RegularExp.substring(0, 1).equals("+") ||
                          checkDigital(RegularExp.substring(0, 1))) &&
            checkDigital(RegularExp.substring(1)) ;
        return result ;
    }

    /**
     * ��һ�������ַ���yyyymmddhhmmss�滻Ϊyyyy-mm-dd hh:mm:ss
     * @param dateChar String�������ַ���
     * @return String��ת������ַ���
     */
    public static String formateDate(String dateChar)
    {
        String display = dateChar.substring(0, 4) + '-' +
            dateChar.substring(4, 6) + '-'
            + dateChar.substring(6, 8) + ' ' +
            dateChar.substring(8, 10)
            + ':' + dateChar.substring(10, 12)
            + ':' + dateChar.substring(12, 14);
        return display;
    }

    /**
     * ��һ���ַ����ĸ��ݳ���len����ȡ�����ֵ��������ַ����㣬asciiӢ���ַ�����һ����
     * @param aStr Ŀ���ַ���
     * @param len ����
     * @param endStr ����������ȣ��ڽ�ȡ����ַ���β������ӵ��ַ�
     * @return ��ȡ��ĳ���
     */
    public static String formatByLen(String aStr, int len , String endStr)
    {
        char c;
        int availableLen = len - getLength(endStr);
        int aStrLen = aStr.length() ;
        StringBuffer resultStr = new StringBuffer();
        int i;
        for (i = 0; i < aStrLen; i++)
        {
            c = aStr.charAt(i);
            if (c >= 127)
            {
                availableLen -= 2;
            }
            else
            {
                availableLen -= 1;
            }
            if (availableLen >= 0)
            {
                resultStr.append(c);
            }
            else
            {
                break;
            }
        }
        if(i < aStrLen)
        {
            resultStr.append(endStr);
        }
        return resultStr.toString();
    }

    /**
     * ���������ж�ȡ���ݵ�����ֽ�����
     *
     * @param in InputStream
     * @return ByteArrayOutputStream������ֽ���
     * @throws java.lang.Exception 
     */
    public static ByteArrayOutputStream InputStreamToOutStream (InputStream in)
        throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buff = new byte[TEMP_BUF_SIZE + 1] ;
        int rc = 0 ;
        if (in != null)
        {
            try
            {
                while ((rc = in.read(buff, 0, TEMP_BUF_SIZE)) > 0)
                {
                    out.write(buff, 0, rc) ;
                }
            }
            catch (Exception e)
            {
                throw e ;
            }
            finally
            {
                CloseInputStream(in) ;
            }
        }
        return out;
    }

    /**
     * �ر�������
     * @param in InputStream
     */
    public static void CloseInputStream (InputStream in)
    {
        if (in != null)
        {
            try
            {
                in.close() ;
            }
            catch (Exception e)
            {e.printStackTrace();}
        }
         ;
    }

    /**
     * �ر������
     * @param out OutputStream�������
     */
    public static void CloseOutStream (OutputStream out)
    {
        if (out != null)
        {
            try
            {
                out.close() ;
            }
            catch (Exception e)
            {e.printStackTrace();}
        }
         ;
    }
    
    /**
     * ��Դlist�б�������Ļ�ȡnum�����������list��ʵ�ʶ������С��numʱԭ������
     * @param srclist Դlist����
     * @param num ��ȡ�Ķ������
     * @return �����ȡ�Ķ�����ɵ���list
     */
    public static ArrayList getRandomChildOfCollection(ArrayList srclist, int num)
    {
        ArrayList retList = new ArrayList();
        int size = srclist.size();
        if(size <= num)
        {
            return srclist;
        }
        else
        {
            Random rd = new Random();
            for (int i = 0; i < num; i++)
            {
                retList.add(srclist.remove(rd.nextInt(srclist.size())));
            }
        }
        return retList;
    }
    
    /**
     * ��start��end֮�䣨���԰����ٽ�ֵ��ȡ�����ظ���getNum�����ֶ�����ɵ��б�
     * @param start ���ֿ�ʼֵ
     * @param end ���ֽ���ֵ
     * @param getNum ��ȡ���ֶ���ĸ���
     * @return ���ּ���
     */
    public static ArrayList getRandomNumBetweenNumbers(int start, int end, int getNum)
    {
        //������ʱ�б���start~end֮�䣨�����ٽ�ֵ���������Զ�����ʽ�Ž�ȥ
        ArrayList tmpList = new ArrayList();
        for (int i = start; i < end + 1; i++)
        {
            tmpList.add(String.valueOf(i));
        }
        return getRandomChildOfCollection(tmpList, getNum);
    }
    
    /**
     * �����ݰ������ָ����������������󳤶Ƚ��зָ����Էָ����Ϊ���ޣ��ָ�������ݳ���<=���ֵ
     * @param content ���ָ�����
     * @param seperator �����еķָ����
     * @param maxlength �������󳤶ȡ�
     * @return ���طָ��������
     */
    public static Object[] getConentArray(String content ,String seperator, int maxlength)
    {
        ArrayList list = new ArrayList();
        String[] array = content.split(seperator);

        String a = "";
        String b = "";
        int length = array.length;
        // ���������еĹ�����д���
        for (int i = 0; i < length; i++)
        {
            b = (length != (i + 1) ? a + array[i] + seperator : a + array[i]);
            if (b.length() > maxlength)
            {
                list.add(a);
                if (length == (i + 1))
                {
                    list.add(array[i]);
                }
                else
                {
                  a = array[i] + seperator;
                }
            }
            else
            {
                a = b;
                if (length == (i + 1))
                {
                    list.add(a);
                }
            }
        }
        return list.toArray();
    }
    /**
     * ��ȡftp�Ĺ����࣬ע�⣬�����ǵ�close
     * @param ip ftp��ַ
     * @param port ftp �˿ں�
     * @param user ��½ftp���û���
     * @param password ��½ftp ������
     * @param ftpDir ���Ŀ¼��������û�Ŀ¼����Ϊ��
     * @return
     * @throws IOException
     * @throws FTPException
     */
    public static FTPClient getFTPClient(String ip,int port,String user,String password,String ftpDir) throws IOException, FTPException
	{
		FTPClient ftp = new FTPClient(ip, port);
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);
		ftp.login(user, password);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);
		if(ftpDir!=null&&!"".equals(ftpDir))
		{
			ftp.chdir(ftpDir);
		}
		return ftp;
	}
	/**
	 * ��鵱ǰ·�����Ƿ���ָ����Ŀ¼�����û���򴴽����÷�����Ҫͬ������ֹ���̳߳����쳣��
	 * 
	 * @param ftp
	 *            FTPClient
	 * @param dir
	 *            Ŀ¼����
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void checkAndCreateDir(FTPClient ftp, String dir)
			throws IOException, FTPException
	{
		String subFiles[] = ftp.dir();
		boolean isFound = false;//��Ŀ¼�Ƿ����dirĿ¼
		for (int i = 0; i < subFiles.length; i++)
		{
			if (subFiles[i].equals(dir))
			{
				isFound = true;
				break;
			}
		}
		if (!isFound)//�����ڸ�Ŀ¼���ʹ�����
		{
			ftp.mkdir(dir);
		}
	}
	/**
	 * ������б�ܽ�β���ַ�����
	 * @param str ԭʼ�ַ�
	 * @return
	 */
	public static String getEndWithSlash(String str)
	{
		if(!str.endsWith("/"))
		{
			return str+"/";
		}else
		{
			return str;
		}
	}
	/**
     * ɾ��List���ظ�Ԫ�أ�����˳��
     * @param list ��ȥ�ص��б�
     */
    public static void removeDuplicateWithOrder(List list)
	{
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Object element = iter.next();
			if (set.add(element))
			{
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
	} 
    /**
     * ɾ���ַ������е�bom�ַ�,���ϵͳ��֧��utf-8���룬��ֱ�ӷ���ԭ�ַ���
     * @param str ��������ַ���
     * @return 
     */
	public static String delStringWithBOM(String str) 
	{
		try
		{
			byte a[] = str.getBytes("UTF-8");
			if (a.length <= 3)// �϶�������bom
			{
				return str;
			}
			else
			{
				if (a[0] == 0xffffffef && a[1] == 0xffffffbb && a[2] == 0xffffffbf)// BOMǰ�����ַ��ֱ�Ϊ��EF,BB,BF
				{
					str = new String(a, 3, a.length - 3, "UTF-8");
				}
				return str;
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return str;
		}
	}
 
	
}
