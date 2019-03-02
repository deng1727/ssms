package com.aspire.dotcard.basecolorcomic.exportfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.ftpfile.SFTPServer;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.dotcard.basecolorcomic.vo.VerfDataVO;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.ponaadmin.web.util.StringTool;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

/**
 * ʵ�ֵ�����������Ϊ����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public abstract class BaseExportFile implements BaseFileAbility
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportFile.class);
	
	/**
	 * �������У������
	 */
	protected List<VerfDataVO> verfDataList = new ArrayList<VerfDataVO>();
	
	/**
	 * ����MAP
	 */
	protected static Map<String, String> keyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * �ʼ�������Ϣ
	 */
	protected String mailTitle = "";
	
	/**
	 * FTP����
	 */
	protected FTPClient ftp = null;
	
	/**
	 * ָ��ftp�ϵ��ļ����·��
	 */
	protected String ftpDir = "";
	
	/**
	 * �ļ���ŵı���·��
	 */
	protected String localDir = "";
	
	/**
	 * ȡ�ļ������ڣ�0��Ϊ���죬1��Ϊǰһ�죬2��Ϊǰ����
	 */
	protected int getDateNum = 0;
	
	/**
	 * ������ļ���
	 */
	protected String fileName = "";
	
	/**
	 * �Ƿ���У���ļ�
	 */
	protected boolean hasVerf = true;
	
	/**
	 * �ļ���������
	 */
	protected String fileEncoding = "GBK";
	
	/**
	 * У���ļ���
	 */
	protected String verfFileName = "";
	
	/**
	 * У����ֵ�����õ�������ʽ
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String dataSpacers = "";
	
	/**
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String verDataSpacers = "";
	
	/**
	 * ͬ����ʼʱ��
	 */
	protected Date startDate = null;
	
	/**
	 * У���ļ������ʼ���Ϣ
	 */
	protected StringBuffer verfMailText = new StringBuffer();
	
	/**
	 * ͬ���ļ�����Ϣ
	 */
	protected StringBuffer syncMailText = new StringBuffer();
	
	/**
	 * �ʼ���Ϣ
	 */
	protected StringBuffer mailText = new StringBuffer();
	
	/**
	 * �ܸ���
	 */
	protected int countNum = 0;
	
	/**
	 * �ɹ�����ĸ���
	 */
	protected int successAdd = 0;
	
	/**
	 * ʧ�ܴ���ĸ���
	 */
	protected int failureProcess = 0;
	
	/**
	 * У��ʧ�ܵĸ���
	 */
	protected int failureCheck = 0;
	
	/**
	 * д��ʧ���ʼ�����
	 */
	protected int failMailTextNum = 0;
	
	/**
	 * �Ƿ�д��ʧ���ʼ�
	 */
	protected boolean isMailText = true;
	
	/**
	 * ִ����
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * �Ƿ�ִ�����������ݵ���
	 */
	protected boolean isImputDate = false;
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		ftpDir = BaseColorComicConfig.FTPPAHT;
		localDir = BaseColorComicConfig.LOCALDIR;
		getDateNum = BaseColorComicConfig.GET_DATE_NUM;
		dataSpacers = getDataSpacers();
		verDataSpacers = BaseColorComicConfig.verDataSpacers;
	}
	
	/**
	 * ִ����
	 */
	public String execution(boolean isSendMail)
	{
		// ׼������
		init();
		
		// ��¼��ʼʱ��
		startDate = new Date();
		
		// ����У�������ļ�
		if (this.hasVerf)
		{
			try
			{
				exportVerfFile();
			}
			catch (BOException e)
			{
				logger.error(e);
			}
		}
		
		// �������������ļ�
		try
		{
			exportDataFile();
		}
		catch (BOException e)
		{
			logger.error(e);
		}
		
		// ��װ��������ʼ�
		getMailText();
		
		// �����ͷŶ��м���
		destroy();
		
		if (isSendMail)
		{
			// ִ�з��ʼ��Ĺ���
			this.sendResultMail(mailTitle, mailText);
			return "";
		}
		else
		{
			return mailText.toString();
		}
	}
	
	/**
	 * ������װ����
	 * 
	 */
	public void getMailText()
	{
		if (mailText.length() <= 0)
		{
			mailText.append("<b>ͬ��").append(mailTitle).append("���</b>�� <br>");
			mailText.append("ͬ���ļ���ʼʱ�䣺").append(
					PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
					.append("������ʱ�䣺").append(
							PublicUtil.getDateString(new Date(),
									"yyyy-MM-dd HH:mm:ss"));
			mailText.append("<br><br>");
			
			mailText.append("У���ļ����ݵ�������� <br>");
			mailText.append(verfMailText).append("<br>");
			for (int i = 0; i < verfDataList.size(); i++)
			{
				mailText
						.append(((VerfDataVO) verfDataList.get(i)).toMailText());
			}
			mailText.append("<br><br>");
			
			mailText.append("�ļ����ݵ�������� <br>");
			mailText.append("�ܵ���������").append(countNum).append("��").append(
					"<br>");
			mailText.append("�ɹ�����������").append(successAdd).append("��").append(
					"<br>");
			mailText.append("ʧ�ܴ���������").append(failureProcess).append("��")
					.append("<br>");
			mailText.append("У��ʧ��������").append(failureCheck).append("��").append(
					"<br>");
			mailText.append(syncMailText).append("<br>");
			mailText.append("<br><br>");
		}
	}
	
	/**
	 * �������������ļ�
	 */
	public void exportDataFile() throws BOException
	{
		// ת��ģ���ļ����е�����
		fileName = fileNameDataChange(fileName);
		
		// �õ��ļ��б�
		List<String> fileList = getDataFileList(fileName);
		
		if (fileList.size() == 0)
		{
			// �����ʼ�������Ϣ..............
			syncMailText.append("���Ҳ�����ǰ��Ҫ�������ļ�������");
			throw new BOException("���Ҳ�����ǰ��Ҫ�������ļ�������",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// �ж��ļ������Ƿ�Ϊ0
		if (isNullFile(fileList))
		{
			// �����ʼ�������Ϣ..............
			syncMailText.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
			throw new BOException("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseColorComicConfig.taskRunnerNum,
				BaseColorComicConfig.taskMaxReceivedNum);
		
		try
		{
			// ������ڣ�����
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				logger.info("��ʼ���������ļ���" + tempFileName);
				
				File file = new File(tempFileName);
				
				// ����ļ�Ϊ��
				if (file.length() == 0)
				{
					// ��������ʼ���Ϣ..............
					syncMailText.append("��ǰ�������ļ�����Ϊ�գ�fileName="
							+ "tempFileName");
					continue;
				}
				
				// ִ�����������ݵ���
				isImputDate = true;
				
				// ���ļ�
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(tempFileName), this.fileEncoding));
				
				while ((lineText = reader.readLine()) != null)
				{
					lineNumeber++;
					countNum++;
					
					SynTask task = new SynTask(lineText, tempFileName,
							lineNumeber, dataSpacers, this);
					
					ReflectedTask refTask = new ReflectedTask(task,
							"sysDataByFile", null, null);
					dataSynTaskRunner.addTask(refTask);
				}
			}
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		}
		catch (Exception e)
		{
			throw new BOException(e, BaseColorComicConfig.EXCEPTION_INNER_ERR);
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				logger.error(e);
			}
		}
	}
	
	/**
	 * �����ֶηָ���
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		int a = 0x1f;
		char r = (char) a;
		return String.valueOf(r);
	}
	
	/**
	 * У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��
	 * 
	 * @param fileList
	 * @return
	 */
	public boolean isNullFile(List<String> fileList)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ����ʼ");
		}
		
		boolean isNullFile = true;
		
		for (int i = 0; i < fileList.size(); i++)
		{
			String tempFileName = fileList.get(i);
			
			File file = new File(tempFileName);
			
			// ����ļ�Ϊ��
			if (file.length() > 0)
			{
				isNullFile = false;
			}
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("У���ļ��Ƿ�ȫΪ���Ծ����Ƿ�ɾ��" + isNullFile);
		}
		
		return isNullFile;
	}
	
	/**
	 * ����У�������ļ�
	 */
	public void exportVerfFile() throws BOException
	{
		// ת��ģ���ļ����е�����
		verfFileName = fileNameDataChange(verfFileName);
		
		// �õ��ļ��б�
		List<String> fileList = getDataFileList(verfFileName);
		
		if (fileList.size() == 0)
		{
			// �����ʼ�������Ϣ..............
			verfMailText.append("���Ҳ�����ǰ��Ҫ��У���ļ�������<br>");
			throw new BOException("У���ļ�Ϊ��",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		
		try
		{
			// ������ڣ�����
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				logger.info("��ʼ����У���ļ���" + tempFileName);
				
				// ���ļ�
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(tempFileName), this.fileEncoding));
				
				while ((lineText = reader.readLine()) != null)
				{
					// ��¼�ļ���������
					lineNumeber++;
					
					if (logger.isDebugEnabled())
					{
						logger.debug("��ʼ����У���ļ���" + lineNumeber + "�����ݡ�");
					}
					
					if (lineNumeber == 1)
					{
						// ɾ����һ��bom�ַ�
						lineText = PublicUtil.delStringWithBOM(lineText);
					}
					
					// У���ļ������һ��
					if (BaseColorComicConfig.FILE_END.equals(lineText.trim()))
					{
						break;
					}
					
					// ���ļ�����
					VerfDataVO vo = readVerfData(lineText, lineNumeber);
					
					// д�뼯���б���
					verfDataList.add(vo);
				}
			}
		}
		catch (Exception e)
		{
			verfMailText.append("����У���ļ�ʱ�������� ������<br>");
			throw new BOException(e, BaseColorComicConfig.EXCEPTION_INNER_ERR);
		}
		finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				logger.error(e);
			}
		}
	}
	
	/**
	 * ʵ����ʵ��У���ֶε���ȷ��
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	protected abstract String checkData(String[] data);
	
	/**
	 * ʵ����ʵ�ַ���ִ�е�ǰҵ���sql����Ӧ��ֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ����sql��Ӧֵ
	 */
	protected abstract Object[] getObject(String[] data);
	
	/**
	 * ʵ����ʵ�ַ���ִ�е�ǰ���ݵ�keyֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ���ص�ǰ���ݵ�keyֵ
	 */
	protected abstract String getKey(String[] data);
	
	/**
	 * ʵ����ʵ�ַ�����ӵ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getInsertSqlCode();
	
	/**
	 * ʵ����ʵ�ַ��ظ��µ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getUpdateSqlCode();
	
	/**
	 * �õ����ԭ����Ϣ��sql���
	 * 
	 * @return
	 */
	protected abstract String getDelSqlCode();
	
	/**
	 * ����У���ļ���Ϣ��ͳһ����ʽ����ΪУ���ļ���ʽһ����
	 * 
	 * @param lineText
	 *            ��ǰ����Ϣ
	 * @param lineNumeber
	 *            �ڼ��� ���ڼ�¼������Ϣ
	 * @return У���ļ����ݶ���
	 */
	public VerfDataVO readVerfData(String lineText, int lineNumeber)
	{
		VerfDataVO vo = new VerfDataVO();
		// String[] tempData = lineText.split(dataSpacers);
		String[] tempData = lineText.split(verDataSpacers);
		
		if (tempData.length == 5)
		{
			vo.setFileName(tempData[0]);
			vo.setFileSiz(tempData[1]);
			vo.setFileDataNum(tempData[2]);
			vo.setLineNum(lineNumeber);
		}
		else
		{
			// �����ʼ�������Ϣ...............
			writeErrorToVerfMail(lineNumeber, lineText, "У���ļ��ṹ���ԣ����ڻ�С��5������");
		}
		
		return vo;
	}
	
	/**
	 * �õ�ftp��Ϣ
	 * 
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public FTPClient getFTPClient() throws IOException, FTPException
	{
		String ip = BaseColorComicConfig.FTPIP;
		int port = BaseColorComicConfig.FTPPORT;
		String user = BaseColorComicConfig.FTPNAME;
		String password = BaseColorComicConfig.FTPPAS;
		
		ftp = new FTPClient(ip, port);
		// ��ʼ��ftp����ģʽ��FTPConnectMode.PASV����FTPConnectMode.ACTIVE��
		ftp.setConnectMode(FTPConnectMode.PASV);
		// ʹ�ø������û����������½ftp
		ftp.login(user, password);
		// �����ļ��������ͣ�FTPTransferType.ASCII����FTPTransferType.BINARY��
		ftp.setType(FTPTransferType.BINARY);
		
		return ftp;
	}
	
	/**
	 * �õ�ftp��Ϣ
	 * 
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 * @throws FTPException
	 */
	public SFTPServer getSFTPClient() throws JSchException
	{
		
		String ip = BaseColorComicConfig.FTPIP;
		int port = BaseColorComicConfig.FTPPORT;
		String user = BaseColorComicConfig.FTPNAME;
		String password = BaseColorComicConfig.FTPPAS;
		
		SFTPServer server = new SFTPServer(ip, user, password, port);
		if (logger.isDebugEnabled())
		{
			logger
					.debug("login SFTPServer successfully,transfer type is binary");
		}
		return server;
	}
	
	/**
	 * ���ڵõ������ļ��б�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	public List<String> getDataFileList(String fileName) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		
		fileList = this.getDateFileListByFtp(fileName);
		
		return fileList;
	}
	
	public List<String> getDateFileListByFtp(String fileName)
			throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		try
		{
			ftp = getFTPClient();
		}
		catch (Exception e)
		{
			mailText.append("FTP���ó���FTP���ӳ�������<br>");
			logger.error(e);
		}
		
		try
		{
			
			// ��������·��
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
			
			// �õ�Ŀ¼���ļ��б�
			String[] ftpFileList = ftp.dir();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("ƥ���ļ�����ʼ��" + fileName);
			}
			
			for (int j = 0; j < ftpFileList.length; j++)
			{
				String tempFileName = ftpFileList[j];
				
				// ƥ���ļ����Ƿ���ͬ
				if (isMatchFileName(fileName, tempFileName))
				{
					// �õ�����·��
					String absolutePath = localDir + File.separator
							+ tempFileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, ftpFileList[j]);
					
					// ��������
					fileList.add(absolutePath);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("�ɹ������ļ���" + absolutePath);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new BOException(e, BaseColorComicConfig.EXCEPTION_FTP);
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
				{}
			}
		}
		return fileList;
	}
	
	/**
	 * ���ڵõ������ļ��б�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	public List<String> getDataFileListBySftp(String fileName)
			throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		SFTPServer server = null;
		ChannelSftp sftp = null;
		try
		{
			server = this.getSFTPClient();
			sftp = server.login();
		}
		catch (Exception e)
		{
			mailText.append("FTP���ó���FTP���ӳ�������<br>");
			logger.error(e);
		}
		
		try
		{
			// ��������·��
			if (!"".equals(ftpDir))
			{
				sftp.cd(ftpDir);
			}
			
			// �õ�Ŀ¼���ļ��б�
			String[] ftpFileList = server.getDirFilenames(sftp);
			
			if (logger.isDebugEnabled())
			{
				logger.debug("ƥ���ļ�����ʼ��" + fileName);
			}
			
			for (int j = 0; j < ftpFileList.length; j++)
			{
				String tempFileName = ftpFileList[j];
				
				// ƥ���ļ����Ƿ���ͬ
				if (isMatchFileName(fileName, tempFileName))
				{
					// �õ�����·��
					String absolutePath = localDir + File.separator
							+ tempFileName;
					absolutePath = absolutePath.replace('\\', '/');
					// ftp.get(absolutePath, ftpFileList[j]);
					// sftp.get(absolutePath, ftpFileList[j]);
					SFTPServer.downloadSingleFile(absolutePath, ftpFileList[j],
							sftp);
					
					// ��������
					fileList.add(absolutePath);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("�ɹ������ļ���" + absolutePath);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BOException(e, BaseColorComicConfig.EXCEPTION_FTP);
		}
		finally
		{
			if (sftp != null)
			{
				try
				{
					sftp.disconnect();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (server != null)
			{
				try
				{
					server.disconnect();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		}
		return fileList;
	}
	
	/**
	 * �ж��Ƿ��Ǳ���������Ҫ���ص��ļ���
	 * 
	 * @param fName
	 *            ��ǰftp�������ϵ��ļ�
	 * @return true �ǣ�false ��
	 */
	public boolean isMatchFileName(String fileName, String FtpFName)
	{
		return FtpFName.matches(fileName);
	}
	
	/**
	 * ����ת��ģ���ļ����е�������ֵ
	 * 
	 * @param fileName
	 * @return
	 */
	public String fileNameDataChange(String fileName)
	{
		// ����ļ��������������ڶ���
		if (fileName.indexOf("~D") != -1)
		{
			StringBuffer tempString = new StringBuffer();
			
			Calendar nowTime = Calendar.getInstance();
			
			int temp = fileName.indexOf("~D");
			
			tempString.append(fileName.substring(0, temp));
			
			String dataType = fileName.substring(temp + 2, fileName
					.lastIndexOf("~"));
			
			// ��������
			nowTime.add(Calendar.DAY_OF_MONTH, 0 - getDateNum);
			
			tempString.append(PublicUtil.getDateString(nowTime.getTime(),
					dataType));
			
			tempString
					.append(fileName.substring(fileName.lastIndexOf("~") + 1));
			
			return tempString.toString();
		}
		
		return fileName;
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ���ʼ");
		
		// ���ִ�й��������ݵ��룬��ִ�������߼�
		if (isImputDate)
		{
			Set<String> keySet = keyMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			
			while (keyIt.hasNext())
			{
				String keyId = keyIt.next();
				String keyValue = keyMap.get(keyId);
				
				if ("".equals(keyValue))
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("��ʼɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����ID��ID=" + keyId);
					}
					
					// ����ط�Ҫ��һ��ɾ�����߼�������
					try
					{
						DB.getInstance().executeBySQLCode(this.getDelSqlCode(), new Object[]{keyId});
					}
					catch (DAOException e)
					{
						logger.error("ɾ���ڱ���ȫ����Ʒ�ļ������ڵ��ϵ�����IDʱ��������ID=" + keyId, e);
					}
				}
			}
		}
		
		logger.info("ɾ����ǰ�����ļ��в����ڣ������ݿ���ȴ���ڵ����ݡ�����");
		
		verfDataList.clear();
		keyMap.clear();
	}
	
	public synchronized boolean hasKey(String key)
	{
		if (!keyMap.containsKey(key))
		{
			keyMap.put(key, "A");
			return false;
		}
		else
		{
			keyMap.put(key, "U");
			return true;
		}
	}
	
	public synchronized void setFailureCheckAdd()
	{
		this.failureCheck++;
	}
	
	public synchronized void setFailureProcessAdd()
	{
		this.failureProcess++;
	}
	
	public synchronized void setSuccessAddAdd()
	{
		this.successAdd++;
	}
	
	public synchronized void setCountNumAdd()
	{
		this.countNum++;
	}
	
	/**
	 * д�������Ϣ
	 * 
	 * @param fileName
	 * @param lineNum
	 * @param dataText
	 * @param reasonText
	 */
	public synchronized void writeErrorToMail(String fileName, int lineNum,
			String dataText, String reasonText)
	{
		if (!isMailText)
		{
			return;
		}
		
		if (failMailTextNum >= 10000)
		{
			syncMailText.append("�������ݹ������²��ṩ����...").append("<br>");
			isMailText = false;
		}
		else
		{
			/*syncMailText.append("���������ļ�").append(fileName).append("�е�").append(
					lineNum).append("�У�").append(dataText)
					.append("�� �������д�ԭ��Ϊ��").append(reasonText).append("<br>");*/
			syncMailText.append("���������ļ��е�").append(
					lineNum).append("�У��������д�ԭ��Ϊ��").append(reasonText).append("<br>");
		}
		
		failMailTextNum++;
	}
	
	/**
	 * д��У�鴦�����Ĵ�����Ϣ���ʼ�
	 * 
	 * @param lineNum
	 *            �ڼ���
	 * @param dataText
	 *            ʲô����
	 * @param reasonText
	 *            ����ԭ��
	 */
	public void writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		verfMailText.append("У���ļ���").append(" ��").append(lineNum).append("��: ")
				.append(dataText).append("�� �������д�ԭ��Ϊ��").append(reasonText)
				.append("<br>");
	}
	
	/**
	 * ���ͽ���ʼ���
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(),
				BaseColorComicConfig.mailTo);
	}
	
	/**
	 * ����֤���衣��֤ʧ�ܴ�ӡ��־��
	 * 
	 * @param fieldName
	 *            ��������ơ�������־��¼
	 * @param field
	 *            ���ֵ
	 * @param maxLength
	 *            ��������ȡ�
	 * @param must
	 *            �Ƿ�����
	 * @return ��֤ʧ�ܷ���false�����򷵻�true
	 */
	protected boolean checkFieldLength(String fieldName, String field,
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
			logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
					+ maxLength + "���ַ���");
		}
		if (must)
		{
			if (field.equals(""))
			{
				result = false;
				logger.error(fieldName + "=" + field
						+ ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
			}
		}
		return result;
		
	}
	
	/**
	 * �����ֶε���������
	 * 
	 * @param field
	 *            �������ֶ�
	 * @param maxLength
	 *            ���ֵ���󳤶�
	 * @param must
	 *            �Ƿ������ֶ�
	 * @return
	 */
	protected boolean checkIntegerField(String fieldName, String field,
			int maxLength, boolean must)
	{
		if (field == null)
		{
			return false;
		}
		if (!field
				.matches(INTEGER.replaceFirst("d", String.valueOf(maxLength))))
		{
			logger.error(fieldName + "=" + field + ",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"
					+ maxLength + "�����֣�");
			return false;
		}
		if (must)
		{
			if (field.equals(""))
			{
				logger.error(fieldName + "=" + field
						+ ",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
				return false;
			}
		}
		return true;
	}
	
	protected boolean checkFieldLength(String field, int maxLength, boolean must)
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
}
