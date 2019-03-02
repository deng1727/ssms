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
 * Description: ���а�����FTP��ȡ����ִ���ࡣ
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
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(TopDataFTPProcessor.class);

	protected static JLogger topDatalog = LoggerFactory
			.getLogger("reportimp.client");

	/**
	 * �����࣬��˹��췽��˽��
	 */
	private TopDataFTPProcessor()
	{
	}

	/**
	 * ���а������ļ�ftp��ȡ�ķ���
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
		// ���������ȡftp���Ӵ���Ķ˿ں�
		String ftpServerPort = TopDataConfig.get("TopDataFTPServerPort");
		// ���������ȡftp���ӵĵ�ַ
		String ftpServerIP = TopDataConfig.get("TopDataFTPServerIP");
		// ���������ȡFTP�������ĵ�¼�û���
		String ftpServerUser = TopDataConfig.get("TopDataFTPServerUser");
		// ���������ȡFTP�������ĵ�¼����
		String ftpServerPassword = TopDataConfig
				.get("TopDataFTPServerPassword");
		// ���������ȡFTP�������ϱ������а�������Ϣ�ӿ��ļ��Ĵ��·��
		String reportFTPTopDir = getFTPDir(type);

		String pasPath = TopDataConfig.get("TopDataSiteDir");
		// ��ȡϵͳ�����������ļ������·��
		String curDate = getCurDateTime("yyyyMMdd");
		String topDataSiteDir = TopDataConfig.getTopDataFilePath(pasPath + File.separator
				+ curDate,type);
		// ���Ŀ¼�������򴴽�Ŀ¼
		IOUtil.checkAndCreateDir(topDataSiteDir);
		// logger.error("the topDataSiteDir is " + topDataSiteDir);

		File file = new File(topDataSiteDir);
		// �ж�Ŀ¼�Ƿ���ڣ��粻���ڣ��򴴽�
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
			// ��ʼ��ftp�ͻ���
			if (logger.isDebugEnabled())
			{
				logger.debug("Construct FTPClient...");
			}
			ftp = new FTPClient(ftpServerIP, Integer.parseInt(ftpServerPort));
			// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
			ftp.setConnectMode(FTPConnectMode.PASV);

			// ʹ�ø������û����������½ftp
			if (logger.isDebugEnabled())
			{
				logger.debug("login to FTPServer...");
			}
			ftp.login(ftpServerUser, ftpServerPassword);
			// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
			ftp.setType(FTPTransferType.BINARY);

			// ȡ��Զ��Ŀ¼���ļ��б�
			String[] remotefiles = ftp.dir(reportFTPTopDir);
			// ����һ����־��ϢFoundFlag
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

				//�������������ļ�ȡ������.
				if (logger.isDebugEnabled())
				{
					logger.debug("Transfer is beginning......");
				}
				// ��ʼ�ļ�����,��Զ��������Ϣ�ӿ��ļ����䵽�����ļ���TopDataSiteDir
				// ftp.chdir(reportFTPTopDir);
				ftp.get(topDataSiteDir + File.separator + remotefileName,
						remotefileName);
				topDatalog.error("File from FTP is: "+ remotefileName);

				FoundFlag = true;
				
			}

			// û��ȡ���ӿ��ļ��������к����ĵ������ݲ�����ֱ�ӷ��ء�
			// Ϊ�˲����ã��ȰѴ���ע��(����δ�ṩ����)
			if (FoundFlag)
			{
				// FoundFlag==trueʱ��˵���ҵ���Ҫ�����ݣ���Ϣ�ӿ��ļ�
				return TopDataConstants.TD_SUCC;
			}
			else
			{
				// FoundFlag==falseʱ��˵��û���ҵ���Ҫ�����ݣ���Ϣ�ӿ��ļ�
				return TopDataConstants.TD_FTP_DATAFILE_NOTFOUND;
			}
		}
		catch (Exception e)
		{
			logger.error("TopDataFTPProcessor.doit() failed!", e);
			topDatalog.error("the FTP ��ȡ�����쳣!");
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
                logger.error("ftp�˳�ʱ�����쳣��");
            }
        }
	}

	/**
	 * �õ����������ĵ�ǰʱ���ַ���
	 * 
	 * @param TIME_FORMAT
	 *            String,��Ҫ��ʱ���ʽ
	 * @return String
	 */
	private static String getCurDateTime(String TIME_FORMAT)
	{

		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(new Date());
	}
	
	/**
	 * �ļ����Ƽ�� 
	 * �����ļ���������:product_add_value_m_YYYYMMDD_xxxxxx.txt
	 * YYYYMMDD��Ϊ���� ��xxxxxx��Ϊ��ţ����к�Ĭ��Ϊ��λ��Ϊ�����ͣ��粻����λ��ǰ�油��;
	 * ���ڱ�ʶ: ��:d  ��:w  ��:m
	 * @param fileName �ļ���
	 * @type   �ļ�����
	 * @startTime
	 * @endTime
	 * @maxDate
	 * @return false������������ true��û������
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
    	//���������л�ȡ�����ݵ���������, ����ʱ��HH:MM
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
