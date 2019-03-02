package com.aspire.dotcard.basecomic.common;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class BaseComicFtpProcessor 
{
	private static JLogger logger = LoggerFactory.getLogger(BaseComicFtpProcessor.class);

	/**
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	//protected String  []fNameRegex;
	/**
	 * ����ƥ�䵱ǰ������ļ�����������ʽ
	 */
	protected String [] fName;

	/**
	 * �����ļ����Ŀ¼
	 */
	protected String ftpDir;

	/**
	 * ���汾�������ļ��ľ���Ŀ¼
	 */
	protected String localDir;

	/**
	 * ������ftp��¼��������Ϣ
	 */
	private String ip;
	private int port;
	private String user;
	private String password;
	
	private final String regEx = "_(\\d{8})";//SFTP�ϵ��ļ�������Ϣ��������ʽ��
	private final Pattern p = Pattern.compile(regEx);
	private int delDayNum;//��Ҫɾ��SFTP��ǰ��������ݡ�

	public void init()
	{
		ModuleConfig module = ConfigFactory.getSystemConfig()
		.getModuleConfig("dmbase");
		this.ftpDir = module.getItemValue("FTPDir");
		this.localDir = module.getItemValue("localDir");
		this.ip = module.getItemValue("FTPServerIP");
		this.port = Integer.parseInt(module.getItemValue("FTPServerPort"));
		this.user = module.getItemValue("FTPServerUser");
		this.password = module.getItemValue("FTPServerPassword");
		this.delDayNum = -3;
		try{
			this.delDayNum = Integer.parseInt(module.getItemValue("delDayNum"));
		}catch(Exception e){
			logger.error("delDayNum���ò������֣�");
		}
			
		
		
	}

	public String[] process(String [] fNameRegex)throws BOException
	{
		this.init();
		FTPClient ftp = null;
		this.fName=new String[fNameRegex.length];
		try
		{
			//�������debug��־�����汾������ͬ�����ļ���
			String debugNameRegex="";
			//ȷ����ǰ����Ҫͬ�����ļ���
			for(int i=0;i<fNameRegex.length;i++)
			{
				fName[i]=parseFileName(fNameRegex[i]);
				debugNameRegex+=fName[i]+",";
			}
			//ȥ�����һ������
			debugNameRegex=debugNameRegex.substring(0, debugNameRegex.length()-1);
			//��ȷ������Ŀ¼�Ѿ������ˡ�
			IOUtil.checkAndCreateDir(this.localDir);
			
			//��Ż�ȡ�����ļ��б��list
			ArrayList fileList = new ArrayList();
			
			// ȡ��Զ��Ŀ¼���ļ��б�
			ftp = getFTPClient();
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
			String[] Remotefiles = ftp.dir();
			//����������ʽ����ȡƥ����ļ��������档
			if (logger.isDebugEnabled())
			{
				logger.debug("ƥ���ļ�����ʼ��" + debugNameRegex);
			}
			for (int j = 0; j < Remotefiles.length; j++)
			{
				String RemotefileName = Remotefiles[j]; 
				// Remotefiles[j].substring(Remotefiles[j].lastIndexOf("/") + 1);
				//if (RemotefileName.matches(fNameRegex))
				if (isMatchFileName(RemotefileName))
				{
					String absolutePath = localDir + File.separator + RemotefileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, Remotefiles[j]);
					fileList.add(absolutePath);
					if (logger.isDebugEnabled())
					{
						logger.debug("�ɹ������ļ���" + absolutePath);
					}
				}
			}
			
			String fileName[]=new String[fileList.size()];
			return (String[]) fileList.toArray(fileName);
		} catch(Exception e)
		{
			throw new BOException(e,1);
		}
		finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} 
				catch (Exception e)
				{
				}
			}

		}
	}

	private FTPClient getFTPClient() throws IOException, FTPException
	{
		FTPClient ftp = new FTPClient(ip, port);
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);

		// ʹ�ø������û����������½ftp
		if (logger.isDebugEnabled())
		{
			logger.debug("login to FTPServer...");
		}
		ftp.login(user, password);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);
		if (logger.isDebugEnabled())
		{
			logger.debug("login FTPServer successfully,transfer type is binary");
		}
		return ftp;
	}
    /**
     * �������������ַ����ļ���������ʽ����������~d��ʼ����~����
     * @param fNameRegex 
     * @return
     */
	private String parseFileName(String fNameRegex)
	{
		if (fNameRegex == null)
		{
			return "";//
		}
		StringBuffer dateCharSequence = new StringBuffer();
		boolean dStart = false;
		boolean dEnd = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fNameRegex.length(); i++)
		{
			char c = fNameRegex.charAt(i);
			if (c == '~' && fNameRegex.charAt(i + 1) == 'D')// ֻ���ַ�Ϊ~DΪ��ͷ���ַ��ű�ʾ����
			{
				dStart = true;
				i++;// ��Ҫ������һ���ַ�
				continue;
			}
			else if (dStart && c == '~')// ƥ�����������ַ�������
			{
				Calendar nowTime=Calendar.getInstance();
				int index=dateCharSequence.indexOf("[");//�鿴�Ƿ��ж����ڵĵ���
				if(index!=-1)
				{
					int difference=Integer.parseInt(dateCharSequence.subSequence(index+1, dateCharSequence.lastIndexOf("]")).toString());
					nowTime.add(Calendar.DAY_OF_MONTH,difference);
					dateCharSequence.delete(index, dateCharSequence.length());
				}
				String date = PublicUtil.getDateString(nowTime.getTime(), dateCharSequence.toString());
				//getCurDateTime(dateCharSequence.toString());

				sb.append(date);
				dEnd = true;
				continue;
			}

			if (dStart && !dEnd)// ���������ַ�
			{
				dateCharSequence.append(c);
			}
			else
			// ��ӷ����������ַ�
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/**
	 * �ж��Ƿ��Ǳ���������Ҫ���ص��ļ���,֧�ֶ���ļ�������ļ���Ӣ�Ķ��ŷָ�
	 * @param fName ��ǰftp�������ϵ��ļ�
	 * @return true �ǣ�false ��
	 */
	protected boolean isMatchFileName(String FtpFName)
	{	
		
		for(int i=0;i<fName.length;i++)
		{
			if(FtpFName.matches(fName[i]))
			{
				return true;
			}
		}
		return false; 
	}
	
	public void delFile()throws BOException
	{
		
		
		this.init();
		FTPClient ftp = null;
		try
		{
			// ȡ��Զ��Ŀ¼���ļ��б�
			ftp = getFTPClient();
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
			String minDayStr = DateUtil.getSpecifyDate(delDayNum).replaceAll("-", "");
			String[] Remotefiles = ftp.dir();
			for (int j = 0; j < Remotefiles.length; j++)
			{
				String RemotefileName = Remotefiles[j]; 
				System.out.println(RemotefileName);
				if (needDel(RemotefileName,minDayStr))
				{
					ftp.delete(RemotefileName);
					logger.debug("�ɹ�ɾ�� "+RemotefileName+"�ļ���");
				}
			}
			
		} catch(Exception e)
		{
			throw new BOException(e,1);
		}
		finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} 
				catch (Exception e)
				{
				}
			}

		}
	}

	private String getDateStr(String fileName){
		
		Matcher m = p.matcher(fileName);
		if(m.find()) return m.group(1);
		return null;
		
	}
	private boolean needDel(String fileName,String minDayStr){
		
		String dateStr = getDateStr(fileName);
		if(dateStr==null){
			return false;
		}
		return dateStr.compareTo(minDayStr)<0;
	}
	
	
	
	public static void main(String[] argv){
		String regEx = "_(\\d{8})";
		String str = "20120917";
		String str2 = "i_c-firstlist_20120918_000000.txt";
		
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		boolean rs = m.find();
		if(rs){
			System.out.println(m.group(1));
		}
		m = p.matcher(str2);
		rs = m.find();
		if(rs){
			System.out.println(m.group(1));
		}
		
		String a="aaa",b="aab";
		System.out.println(a.compareTo(b));
		
		
		
	}
}
