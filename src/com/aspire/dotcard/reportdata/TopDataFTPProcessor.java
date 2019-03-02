package com.aspire.dotcard.reportdata;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Title: IMALL PAS
 * </p>
 * <p>
 * Description: 排行榜数据FTP获取数据执行类。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Aspire Technologies
 * </p>
 * 
 * @author x_liyunhong
 * @version 1.0
 */

public class TopDataFTPProcessor
{

	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(TopDataFTPProcessor.class);

	protected static JLogger topDatalog = LoggerFactory
			.getLogger("reportimp.client");

	/**
	 * 工具类，因此构造方法私有
	 */
	private TopDataFTPProcessor()
	{
	}

	/**
	 * 排行榜数据文件ftp获取的方法
	 * 
	 * @param FullDataTime
	 *            String
	 * @param RefDataTime
	 *            String
	 * @return int
	 */
	public static int getTopDataFromFTP(String startDataTime,
			String endDataTime, int type)
	{

		logger.debug("TopDataFTPProcessor is beginning......");
		// 从配置项读取ftp连接传输的端口号
		String ftpServerPort = TopDataConfig.get("TopDataFTPServerPort");
		// 从配置项读取ftp连接的地址
		String ftpServerIP = TopDataConfig.get("TopDataFTPServerIP");
		// 从配置项读取FTP服务器的登录用户名
		String ftpServerUser = TopDataConfig.get("TopDataFTPServerUser");
		// 从配置项读取FTP服务器的登录密码
		String ftpServerPassword = TopDataConfig
				.get("TopDataFTPServerPassword");
		// 从配置项读取FTP服务器上报表排行榜数据信息接口文件的存放路径
		String reportFTPTopDir = getFTPDir(type);

		String pasPath = TopDataConfig.get("TopDataSiteDir");
		// 获取系统中排行数据文件保存的路径
		String curDate = getCurDateTime("yyyyMMdd");
		String topDataSiteDir = TopDataConfig.getTopDataFilePath(pasPath + File.separator
				+ curDate,type);
		// 如果目录不存在则创建目录
		IOUtil.checkAndCreateDir(topDataSiteDir);
		// logger.error("the topDataSiteDir is " + topDataSiteDir);

		File file = new File(topDataSiteDir);
		// 判断目录是否存在，如不存在，则创建
		if (!file.isDirectory())
		{
			file.mkdirs();
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("the FTPServerPort is " + ftpServerPort);
			logger.debug("the FTPServerIP is " + ftpServerIP);
			logger.debug("the FTPServerUser is " + ftpServerUser);
			logger.debug("the FTPServerPassword is " + ftpServerPassword);
			logger.debug("the reportTopDataDir is " + reportFTPTopDir);
			logger.debug("the TopDataSiteDir is " + topDataSiteDir);
		}
		FTPClient ftp = null;
		try
		{
			// 初始化ftp客户端
			if (logger.isDebugEnabled())
			{
				logger.debug("Construct FTPClient...");
			}
			ftp = new FTPClient(ftpServerIP, Integer.parseInt(ftpServerPort));
			// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
			ftp.setConnectMode(FTPConnectMode.PASV);

			// 使用给定的用户名、密码登陆ftp
			if (logger.isDebugEnabled())
			{
				logger.debug("login to FTPServer...");
			}
			ftp.login(ftpServerUser, ftpServerPassword);
			// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
			ftp.setType(FTPTransferType.BINARY);

			// 取得远程目录中文件列表
			String[] remotefiles = ftp.dir(reportFTPTopDir);
			// 设置一个标志信息FoundFlag
			boolean FoundFlag = false;
			if (logger.isDebugEnabled())
			{
				logger.debug("the startDataTime is ::::" + startDataTime);
				logger.debug("the endDataTime is ::::" + endDataTime);
			}

			ftp.chdir(reportFTPTopDir);
			for (int j = 0; j < remotefiles.length; j++)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("check ftp remote file:" + remotefiles[j]);
				}
				String remotefileName = remotefiles[j].substring(remotefiles[j]
						.lastIndexOf("/") + 1);				
				if(!checkFileName(remotefileName,type,Long.parseLong(startDataTime),Long.parseLong(endDataTime),null))
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("File name check error:"+ remotefileName);
					}
					continue;
				}

				//将满足条件的文件取到本地.
				if (logger.isDebugEnabled())
				{
					logger.debug("Transfer is beginning......");
				}
				// 开始文件传输,将远程排行信息接口文件传输到本地文件夹TopDataSiteDir
				// ftp.chdir(reportFTPTopDir);
				ftp.get(topDataSiteDir + File.separator + remotefileName,
						remotefileName);
				topDatalog.error("File from FTP is: "+ remotefileName);

				FoundFlag = true;
				
			}

			// 没有取到接口文件，不进行后续的导入数据操作，直接返回。
			// 为了测试用，先把代码注释(报表未提供数据)
			if (FoundFlag)
			{
				// FoundFlag==true时，说明找到需要的数据：信息接口文件
				return TopDataConstants.TD_SUCC;
			}
			else
			{
				// FoundFlag==false时，说明没有找到需要的数据：信息接口文件
				return TopDataConstants.TD_FTP_DATAFILE_NOTFOUND;
			}
		}
		catch (Exception e)
		{
			logger.error("TopDataFTPProcessor.doit() failed!", e);
			topDatalog.error("the FTP 获取数据异常!");
			return TopDataConstants.TD_FTP_GETDATAFILE_ERROR;
		}
        finally
        {
            try
            {
                ftp.quit();
            }
            catch (Exception e1)
            {
                logger.error("ftp退出时发生异常！");
            }
        }
	}

	/**
	 * 得到满足条件的当前时间字符串
	 * 
	 * @param TIME_FORMAT
	 *            String,需要的时间格式
	 * @return String
	 */
	private static String getCurDateTime(String TIME_FORMAT)
	{

		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * 文件名称检查 
	 * 内容文件名称形如:product_add_value_m_YYYYMMDD_xxxxxx.txt
	 * YYYYMMDD”为日期 “xxxxxx”为序号，序列号默认为六位，为数字型，如不足六位，前面补零;
	 * 周期标识: 日:d  周:w  月:m
	 * @param fileName 文件名
	 * @type   文件类型
	 * @startTime
	 * @endTime
	 * @maxDate
	 * @return false：不满足条件 true：没有问题
	 */
	public static boolean checkFileName(String fileName,int type,long startTime,
			long endTime,List maxDate)
	{
		String[] element = fileName.split("_");
		if(element.length != 6)
		{
			return false;
		}
		
		String head = element[5].substring(0,6);
		String end = element[5].substring(7,10);

		if(!("txt".equals(end)))
		{
			return false;
		}
		try
		{
			Integer.parseInt(head);
			long time = Integer.parseInt(element[4]);
			if(time > endTime || time < startTime)
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
		
		switch(type)
    	{
    		case TopDataConstants.CONTENT_TYPE_DAY:
    			if(39 != fileName.length())
    			{
    				return false;
    			}    							
    			if(!fileName.startsWith("product_add_value_d"))
    			{
    				return false;
    			}
    			break;
    		case TopDataConstants.CONTENT_TYPE_WEEK:
    			if(39 != fileName.length())
    			{
    				return false;
    			}    							
    			if(!fileName.startsWith("product_add_value_w"))
    			{
    				return false;
    			}
    			break;
    		case TopDataConstants.CONTENT_TYPE_MONTH:
    			if(39 != fileName.length())
    			{
    				return false;
    			}    							
    			if(!fileName.startsWith("product_add_value_m"))
    			{
    				return false;
    			}
    			break;
    		default:
    			return false;
    	}		
		if(maxDate != null)
		{
			maxDate.clear();
			maxDate.add(element[4]);
		}
		return true;
	}
	
	private static String getFTPDir(int type)
	{
    	//从配置项中获取日数据导入配置项, 具体时间HH:MM
        try
        {
        	String dir="";
        	switch(type)
        	{
        		case TopDataConstants.CONTENT_TYPE_DAY:
        			dir = TopDataConfig.get("ContentDayReportDir");
        			break;
        		case TopDataConstants.CONTENT_TYPE_WEEK:
        			dir = TopDataConfig.get("ContentWeekReportDir");
        			break;
        		case TopDataConstants.CONTENT_TYPE_MONTH:
        			dir = TopDataConfig.get("ContentMonthReportDir");
        			break;
        	}
        	return dir;
        }
        catch (Exception ex)
        {
        	logger.error(ex);
        	return "";
        }
	}
	
	public static void main(String args[])
	{
		String filename = "product_add_value_d_20080314_111111.txt";
		boolean b = checkFileName(filename,4,Long.parseLong("20080314"),Long.parseLong("20080314"),null);
		System.out.println(b);
	}
}
