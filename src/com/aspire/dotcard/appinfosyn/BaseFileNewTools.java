package com.aspire.dotcard.appinfosyn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class BaseFileNewTools
{
	private static JLogger logger = LoggerFactory
			.getLogger(BaseFileNewTools.class);
	
	/**
	 * 校验数值长度用的正则表达式
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
     * 每次写入文件的大小
     */
    final static int BUFFEREDSIZE = 1024;
	
	/**
	 * 校验文件是否全为空以决定是否删表
	 * 
	 * @param fileList
	 * @return
	 */
	public static boolean isNullFile(List<String> fileList)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("校验文件是否全为空以决定是否删表：开始");
		}
		
		boolean isNullFile = true;
		
		for (int i = 0; i < fileList.size(); i++)
		{
			String tempFileName = String.valueOf(fileList.get(i));
			
			File file = new File(tempFileName);
			
			// 如果文件为空
			if (file.length() > 0)
			{
				isNullFile = false;
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("校验文件是否全为空以决定是否删表：" + isNullFile);
		}
		
		return isNullFile;
	}
	
	/**
	 * 用于得到shell脚本应用的时间参数
	 * 
	 * @param getDateNum
	 * @param setTime
	 * @return
	 */
	public static String getShellFileDate(int getDateNum, Calendar setTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar nowTime = null;
		
		if (null == setTime)
		{
			nowTime = Calendar.getInstance();
		}
		else
		{
			nowTime = setTime;
		}
		
		// 调整日期
		nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
		
		return sdf.format(nowTime.getTime());
	}
	
	/**
	 * 用于得到shell脚本应用的时间参数
	 * 
	 * @param getDateNum
	 * @param setTime
	 * @return
	 */
	public static String getShellFileEndDate(String date)
	{
		return "_" + date.replaceAll("-", "");
	}
	
	/**
	 * 用于转义模糊文件名中的日期数值
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileNameDataChange(String fileName, int getDateNum,
			Calendar setTime,boolean isByHour,int getTimeNum)
	{
		Calendar nowTime = null;
		
		// 如果文件名定义中有日期定义
		if (fileName.indexOf("~D") != -1)
		{
			StringBuffer tempString = new StringBuffer();
			
			if (null == setTime)
			{
				nowTime = Calendar.getInstance();
			}
			else
			{
				nowTime = setTime;
			}
			
			int temp = fileName.indexOf("~D");
			
			tempString.append(fileName.substring(0, temp));
			
			String dataType = fileName.substring(temp + 2, fileName
					.lastIndexOf("~"));
			
			// 调整日期
			//不是按小时增量
            if(!isByHour)
            {
            	// 调整日期
            	nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
            }
            else
            {
            	nowTime.add(Calendar.HOUR_OF_DAY, 0 - getTimeNum);
            }
			
			tempString.append(PublicUtil.getDateString(nowTime.getTime(),
					dataType));
			
			tempString
					.append(fileName.substring(fileName.lastIndexOf("~") + 1));
			
			return tempString.toString();
		}
		
		return fileName;
	}
	
	/**
	 * 得到ftp信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public static FTPClient getFTPClient() throws IOException, FTPException
	{
		String ip = BaseVideoConfig.FTPIP;
		int port = BaseVideoConfig.FTPPORT;
		String user = BaseVideoConfig.FTPNAME;
		String password = BaseVideoConfig.FTPPAS;
		
		FTPClient ftp = new FTPClient(ip, port);
		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);
		// 使用给定的用户名、密码登陆ftp
		ftp.login(user, password);
		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
		ftp.setType(FTPTransferType.BINARY);
		
		return ftp;
	}
	
	/**
	 * 判读是否是本次任务需要下载的文件名
	 * 
	 * @param fName
	 *            当前ftp服务器上的文件
	 * @return true 是，false 否
	 */
	public static boolean isMatchFileName(String fileName, String FtpFName)
	{
		return FtpFName.matches(fileName);
	}
	
	public static boolean checkFieldLength(String field, int maxLength,
			boolean must)
	{
		if (field == null)
		{
			return false;
		}
		if (StringTool.lengthOfHZ(field) > maxLength)
		{
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 简化验证步骤。验证失败打印日志。
	 * 
	 * @param fieldName
	 *            该域的名称。用于日志记录
	 * @param field
	 *            域的值
	 * @param maxLength
	 *            最大允许长度。
	 * @param must
	 *            是否必须的
	 * @return 验证失败返回false，否则返回true
	 */
	public static boolean checkFieldLength(String fieldName, String field,
			int maxLength, boolean must)
	{
		boolean result = true;
		if (field == null)
		{
			result = false;
		}
		if (StringTool.lengthOfHZ(field) > maxLength)
		{
			result = false;
			logger.error(fieldName + "=" + field + ",该字段验证失败。原因：长度超过了"
					+ maxLength + "个字符！");
		}
		if (must)
		{
			if (field.equals(""))
			{
				result = false;
				logger.error(fieldName + "=" + field
						+ ",该字段验证失败。原因：该字段是必填字段，不允许为空");
			}
		}
		return result;
		
	}
	
	/**
	 * 检查该字段的数字类型
	 * 
	 * @param field
	 *            待检查的字段
	 * @param maxLength
	 *            数字的最大长度
	 * @param must
	 *            是否必须的字段
	 * @return
	 */
	public static boolean checkIntegerField(String fieldName, String field,
			int maxLength, boolean must)
	{
		if (field == null)
		{
			return false;
		}
		if (!field
				.matches(INTEGER.replaceFirst("d", String.valueOf(maxLength))))
		{
			logger.error(fieldName + "=" + field + ",该字段验证失败。原因：长度超过了"
					+ maxLength + "个数字！");
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				logger.error(fieldName + "=" + field
						+ ",该字段验证失败。原因：该字段是必填字段，不允许为空");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 执行SHELL对比文件差异生成对比后文件
	 * 
	 * @return 对比后文件生成列表
	 * @throws BOException
	 */
	protected static List<String> execShell(String data) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		Process pid = null;
		String cmd = BaseVideoNewConfig.EXEC_SHELL_PATH
				+ BaseVideoNewConfig.EXEC_SHELL_FILE + " " + data;
		
		logger.info("开始执行SHELL脚本文件:" + cmd);
		
		try
		{
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			
			if (pid != null)
			{
				logger.info("当前脚本进程号：" + pid.toString());
				pid.waitFor();
			}
			else
			{
				logger.info("当前脚本没有进程号。");
			}
		}
		catch (IOException e)
		{
			logger.info("当前脚本执行时出错！！！", e);
			return fileList;
		}
		catch (InterruptedException e)
		{
			logger.info("当前脚本执行时同步等待出错！！！", e);
			return fileList;
		}
		finally
		{
			if (pid != null)
			{
				pid.destroy();
			}
		}
		
		logger.info("当前脚本命令执行完毕!");
		
		String add = BaseVideoNewConfig.LOCALDIR + File.separator
				+ BaseVideoNewConfig.ADD_SHELL_FILE + getShellFileEndDate(data);
		String del = BaseVideoNewConfig.LOCALDIR + File.separator
				+ BaseVideoNewConfig.DEL_SHELL_FILE + getShellFileEndDate(data);
		
		logger.info("当前文件名为：" + add + ", " + del);
		fileList.add(add.replace('\\', '/'));
		fileList.add(del.replace('\\', '/'));
		
		return fileList;
	}
	
	/**
	 * 执行SHELL将视频物理全量文件导入临时表
	 * 
	 * @throws BOException
	 */
	protected static void execShellOfVideoFullImport(String data) throws BOException
	{
		Process pid = null;
		String cmd = BaseVideoNewConfig.EXEC_SHELL_PATH
				+ BaseVideoNewConfig.EXEC_SHELL_VIDEO_FULL_IMPORT_FILE + " " + data;
		
		logger.info("开始执行视频物理全量文件导入SHELL脚本文件:" + cmd);
		
		try
		{
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			
			if (pid != null)
			{
				logger.info("当前脚本进程号：" + pid.toString());
				//pid.waitFor();
				  StreamGobbler errorGobbler = new BaseFileNewTools().new StreamGobbler(pid.getErrorStream(), "Error");            
	                 StreamGobbler outputGobbler = new BaseFileNewTools().new StreamGobbler(pid.getInputStream(), "Output");
	                 errorGobbler.start();
	                 outputGobbler.start();
	                 pid.waitFor();
	                 
	              
			}
			else
			{
				logger.info("当前脚本没有进程号。");
			}
		}
		catch (IOException e)
		{
			logger.info("当前脚本执行时出错！！！", e);
		}
		catch (InterruptedException e)
		{
			logger.info("当前脚本执行时同步等待出错！！！", e);
		}
		finally
		{
			if (pid != null)
			{
				pid.destroy();
			}
		}
		logger.info("当前脚本命令执行完毕!");
	}
	
	/** 
     * 获取当前日期是星期几 
     * 
     * @param date 
     * @return 当前日期是星期几 
     */ 
    public static int getWeekOfDate(Date date) { 
        //String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) 
            w = 0; 
        //0:星期日,1:星期一,2:星期一,3:星期三,4:星期四,5:星期五,6:星期六
        return w;
    } 
    
    
    /**
     * 解压tar.gz文件到当前目录。压缩文件的文件名（不包含tar.gz）作为解压后文件的父目录
     * 
     * @param gzipFileName 待压缩文件的绝对目录
     * @return 解压缩后压缩文件的根目录。
     * @exception Exception 解压缩出现异常
     */
    public static String untargzip(String gzipFileName) throws Exception
    {
        String outputDirectory = gzipFileName.substring(0,
                                                        gzipFileName.indexOf('.'));
        File file = new File(outputDirectory);
        if (!file.exists())
        {
            file.mkdir();
        }
        ungzip(gzipFileName, outputDirectory);
        return outputDirectory;
    }
    /**
     * 解压tar.gz文件到当前目录。压缩文件的文件名（不包含tar.gz）作为解压后文件的父目录
     * 
     * @param gzipFileName 待压缩文件的绝对目录
     * @return 解压缩后压缩文件的根目录。
     * @exception Exception 解压缩出现异常
     */
    public static String ungzip(String gzipFileName) throws Exception
    {
        String outputDirectory = gzipFileName.substring(0,
                                                        gzipFileName.indexOf('.'));
        File file = new File(outputDirectory);
        if (!file.exists())
        {
            file.mkdir();
        }
        ungzip(gzipFileName, outputDirectory);
        return outputDirectory;
    }

    /**
     * 解压tar.gz文件到指定目录
     * 
     * @param gzipFileName 压缩文件的绝对路径
     * @param outputDirectory 解压文件的保存目录路径。
     * @exception Exception 解压缩出现异常
     */
    public static void ungzip(String gzipFileName, String outputDirectory)
                    throws Exception
    {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            fis = new FileInputStream(gzipFileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = ( TarArchiveEntry ) in.getNextEntry();
            while (entry != null)
            {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for (int i = 0; i < names.length; i++)
                {
                    fileName = fileName + File.separator + names[i];
                }
                if (name.endsWith("/"))
                {
                    // mkFolder(fileName);
                    IOUtil.checkAndCreateDir(fileName);
                }
                else
                {
                    File file = new File(fileName);
                    //
                    // IOUtil.
                    IOUtil.checkAndCreateDir(file);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    // 记录本次读取长度
                    int b;
                    byte[] by = new byte[BUFFEREDSIZE];
                    while ((b = in.read(by)) != -1)
                    {
                        bufferedOutputStream.write(by, 0, b);
                    }

                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = ( TarArchiveEntry ) in.getNextEntry();
            }

        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	logger.error(e);
            throw e;
        }
        finally
        {
            try
            {
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            	logger.error(e);
            }
        }
    }

	
	public static void main(String[] args){
		File file= new File("D://test.txt");
		System.out.println(file.exists()+":"+file.length());
		Date date = new Date();
		System.out.println(getWeekOfDate(date));
		String string = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.tar.gz";
		System.out.println(fileNameDataChange(string,1,null,false,0));
	}
public	   class StreamGobbler extends Thread {
  		 InputStream is;
  		 String type;
  		 StreamGobbler(InputStream is, String type) {
  		  this.is = is;
  		  this.type = type;
  		 }
  		 public void run() {
  		  try {
  		   InputStreamReader isr = new InputStreamReader(is);
  		   BufferedReader br = new BufferedReader(isr);
  		   String line = null;
  		   while ((line = br.readLine()) != null) {
  		    if (type.equals("Error"))
  		    	logger.error(line);
  		    else
  		    	logger.debug(line);
  		   }
  		  } catch (IOException ioe) {
  		   ioe.printStackTrace();
  		  }
  		 }
  		}
	
}
