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
	 * 构造方法
	 */
	private PublicUtil () 
	{

	}

    /** Automatically generated javadoc for: BYTE_CHINESE */
    private static final int BYTE_CHINESE = 255 ;

    /**
     * 临时缓冲区的大小
     */
    private static final int TEMP_BUF_SIZE = 1024 ;
    /**

     * 全文检索机型中需要过滤的特殊字符

     */

    public static final String SEARCH_MBRAND_CHARACTER_FILTRATE_STR = "_ -";
    /**
     * 日志
     */
    private static final JLogger LOG = LoggerFactory.getLogger(PublicUtil.class) ;
    /**

     * 字符串替换

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
     * 在特定字符串的左边添加一些位，默认添加为0
     * @param bitNumber int，是补0后的长度，即总长度
     * @param s String，待添加的字符串 
     * @return String 处理过的字符
     */
    public static String lPad (String s, int bitNumber)
    {
        if (s == null)
        {
            return null ;
        }

        int length = s.length() ;
        //判断输入参数是否合法
        if (length > bitNumber)
        {
            return s.substring(0,bitNumber) ;
        }

        if (length == bitNumber)
        {
            return s ;
        }

        //先形成添加的0串
        int addNum = bitNumber - length ;
        String addString = "0" ;
        for (int i = 1 ; i < addNum ; i++)
        {
            addString += '0' ;
        }
        return addString + s ;
    }
    
    /**
     * 在特定字符串的右边添加一些位，添加的字符为传入的
     * 
     * @param bitNumber
     *            int，是补后的长度，即总长度
     * @param s
     *            String，待添加的字符串
     * @return String 处理过的字符
     */
    public static String rPad(String s, int bitNumber, String add)
    {
        if (s == null)
        {
            return null;
        }

        int length = s.length();
        // 判断输入参数是否合法
        if (length > bitNumber)
        {
            return s.substring(0, bitNumber);
        }

        if (length == bitNumber)
        {
            return s;
        }

        // 先形成添加的0串
        int addNum = bitNumber - length;
        String addString = "";
        for (int i = 0; i < addNum; i++)
        {
            addString += add;
        }
        return s + addString;
    }

    /**
     * 按时间生成临时文件名
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
     * 字符串替换
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
     * 专门处理 & ——〉&amp;的转换
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
     * 根据不同的操作系统生成相应的目录
     * @param path
     * @return
     */
    public static String toOSDir (String path)
    {
        String osName = "" ;
        String retPath = "" ;
        osName = System.getProperty("os.name").toLowerCase() ;
        if (osName.indexOf("windows") != -1)
        { //操作系统是WINDOWS
            retPath = path.replace('/', '\\') ;
        }
        else
        { //unix
            retPath = path.replace('\\', '/') ;
        }
        return retPath ;

    }

    /**
     * 得到当前分钟
     * @return int，当前分钟
     */
    public static int getCurMin ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
    }

    /**
     * 得到当前小时
     * @return int，当前小时
     */
    public static int getCurHour ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("k") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
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
            LOG.error(e) ;
            //如果无法转化，则返回默认格式的时间。
            return getCurDateTime() ; 
        }
    }
    
    /**
     * 格式化日期为字符串
     * @param dateString，日期字符串
     * @param dateFormate，格式化字符串
     * @return String，解析后的日期，无法转化返回null。
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
            //如果无法转化，则返回默认格式的时间。
            return null;
        }
    }

    /**
     * 格式化日期为字符串
     * @param date Date，日期
     * @return String，格式化后的日期字符串
     */
    public static String getDateString (java.util.Date date)
    {
        return getDateString(date,"yyyyMMddHHmmss");
    }
    
    /**
     * 得到当前日
     * @return int，当前日
     */
    public static int getCurDate ()
    {
        java.util.Date date = new java.util.Date() ;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd") ;
        int currentTime = Integer.parseInt(simpleDateFormat.format(date)) ;
        return currentTime ;
    }

    /**
     * 得到当前年
     * @return String，当前年
     */
    public static String getCurYear ()
    {
        java.util.Date now = new java.util.Date() ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy") ;
        return formatter.format(now) ;
    }

    /**
     * 得到当前月
     * @return String，当前月
     */
    public static String getCurMonth ()
    {
        java.util.Date now = new java.util.Date() ;
        SimpleDateFormat formatter = new SimpleDateFormat("MM") ;
        return formatter.format(now) ;
    }

    /**
     * 得到上个月的月份，返回为两位字符串
     * @return String，上个月的月份 
     */
    public static String getLastMonth ()
    {
        //得到当前月份
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
     * 得到调用堆栈异常信息
     * @return String，调用堆栈信息
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
     * 用来实现在string 中的替换
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

    /** 功能：转换特殊字符以便在页面上正确显示，特殊字符如下：
     * "、&、<、>、空格、回车 对应的html字符为 &quot; &amp; &lt; &gt; &nbsp; br
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
                //不是特殊字符
                newString.append(cTemp) ;
            }
            else
            {
                //是特殊字符
                newString.append(charToString[iTemp]) ;
            }
        }
        String result = newString.toString() ;
        result = replace(result,"\r\n","<br>");
        return result;
    }

    /**
     * 获取一个目录下的文件列表
     * @param dir String，目录路径
     * @return String[]，文件列表
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
     * 获取一个适用于页面显示的字符串
     * 如果为null显示空字符串，以后可能还会加上html的编码。
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
     * 获取字符串长度，中文字符当成两个
     * @param strTemp String，输入的字符
     * @return int，字符长度
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
     * 检查字符串中是否有非法字符
     * @param targetStr String，要检查的字符串
     * @param IllegalString String， 非法字符的集合
     * @return boolean，非法false或合法true
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
     * 检查一个字符串，只能包含数字
     * @param targetStr String，输入的字符
     * @return boolean，非法false或合法true
     */

    public static final boolean checkDigital (String targetStr)
    {
        return Pattern.matches("[0-9]*", targetStr) ;
    }

    /**
     * 检查电话号码是否是以数字或者+开头后面都是数字的字符
     * @param RegularExp String，输入的字符
     * @return boolean，非法false或合法true
     */

    public static final boolean checkTel (String RegularExp)
    {
        boolean result = (RegularExp.substring(0, 1).equals("+") ||
                          checkDigital(RegularExp.substring(0, 1))) &&
            checkDigital(RegularExp.substring(1)) ;
        return result ;
    }

    /**
     * 把一个日期字符串yyyymmddhhmmss替换为yyyy-mm-dd hh:mm:ss
     * @param dateChar String，日期字符串
     * @return String，转换后的字符串
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
     * 把一个字符串的根据长度len来截取，汉字当成两个字符计算，ascii英文字符当成一个。
     * @param aStr 目标字符串
     * @param len 长度
     * @param endStr 如果超出长度，在截取后的字符串尾巴上添加的字符
     * @return 截取后的长度
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
     * 从输入流中读取数据到输出字节流中
     *
     * @param in InputStream
     * @return ByteArrayOutputStream，输出字节流
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
     * 关闭输入流
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
     * 关闭输出流
     * @param out OutputStream，输出流
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
     * 从源list列表中随机的获取num个对象出来，list中实际对象个数小于num时原样返回
     * @param srclist 源list对象
     * @param num 获取的对象个数
     * @return 随机获取的对象组成的新list
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
     * 从start和end之间（可以包括临界值）取出不重复的getNum个数字对象组成的列表
     * @param start 数字开始值
     * @param end 数字结束值
     * @param getNum 获取数字对象的个数
     * @return 数字集合
     */
    public static ArrayList getRandomNumBetweenNumbers(int start, int end, int getNum)
    {
        //构造临时列表，将start~end之间（包括临界值）的数字以对象形式放进去
        ArrayList tmpList = new ArrayList();
        for (int i = start; i < end + 1; i++)
        {
            tmpList.add(String.valueOf(i));
        }
        return getRandomChildOfCollection(tmpList, getNum);
    }
    
    /**
     * 将内容按给定分隔符及其所允许的最大长度进行分隔，以分割符作为界限，分割出的内容长度<=最大值
     * @param content 待分割内容
     * @param seperator 内容中的分割符号
     * @param maxlength 允许的最大长度。
     * @return 返回分隔后的数组
     */
    public static Object[] getConentArray(String content ,String seperator, int maxlength)
    {
        ArrayList list = new ArrayList();
        String[] array = content.split(seperator);

        String a = "";
        String b = "";
        int length = array.length;
        // 根据需求中的规则进行处理
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
     * 获取ftp的工具类，注意，用完后记得close
     * @param ip ftp地址
     * @param port ftp 端口号
     * @param user 登陆ftp的用户名
     * @param password 登陆ftp 的密码
     * @param ftpDir 相对目录。如果是用户目录可以为空
     * @return
     * @throws IOException
     * @throws FTPException
     */
    public static FTPClient getFTPClient(String ip,int port,String user,String password,String ftpDir) throws IOException, FTPException
	{
		FTPClient ftp = new FTPClient(ip, port);
		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);
		ftp.login(user, password);
		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
		ftp.setType(FTPTransferType.BINARY);
		if(ftpDir!=null&&!"".equals(ftpDir))
		{
			ftp.chdir(ftpDir);
		}
		return ftp;
	}
	/**
	 * 检查当前路径下是否含有指定的目录。如果没有则创建。该方法需要同步，防止多线程出现异常。
	 * 
	 * @param ftp
	 *            FTPClient
	 * @param dir
	 *            目录名称
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void checkAndCreateDir(FTPClient ftp, String dir)
			throws IOException, FTPException
	{
		String subFiles[] = ftp.dir();
		boolean isFound = false;//该目录是否存在dir目录
		for (int i = 0; i < subFiles.length; i++)
		{
			if (subFiles[i].equals(dir))
			{
				isFound = true;
				break;
			}
		}
		if (!isFound)//不存在该目录，就创建。
		{
			ftp.mkdir(dir);
		}
	}
	/**
	 * 返回以斜杠结尾的字符串。
	 * @param str 原始字符
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
     * 删除List中重复元素，保持顺序
     * @param list 待去重的列表
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
     * 删除字符串含有的bom字符,如果系统不支持utf-8编码，则直接返回原字符串
     * @param str 待处理的字符串
     * @return 
     */
	public static String delStringWithBOM(String str) 
	{
		try
		{
			byte a[] = str.getBytes("UTF-8");
			if (a.length <= 3)// 肯定不含有bom
			{
				return str;
			}
			else
			{
				if (a[0] == 0xffffffef && a[1] == 0xffffffbb && a[2] == 0xffffffbf)// BOM前三个字符分别为：EF,BB,BF
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
