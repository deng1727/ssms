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
 * 实现导入对象基本行为能力
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
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportFile.class);
	
	/**
	 * 用来存放校验数据
	 */
	protected List<VerfDataVO> verfDataList = new ArrayList<VerfDataVO>();
	
	/**
	 * 主键MAP
	 */
	protected static Map<String, String> keyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * 邮件标题信息
	 */
	protected String mailTitle = "";
	
	/**
	 * FTP对象
	 */
	protected FTPClient ftp = null;
	
	/**
	 * 指定ftp上的文件存放路径
	 */
	protected String ftpDir = "";
	
	/**
	 * 文件存放的本地路径
	 */
	protected String localDir = "";
	
	/**
	 * 取文件的日期，0：为当天，1：为前一天，2：为前二天
	 */
	protected int getDateNum = 0;
	
	/**
	 * 导入的文件名
	 */
	protected String fileName = "";
	
	/**
	 * 是否有校验文件
	 */
	protected boolean hasVerf = true;
	
	/**
	 * 文件解析编码
	 */
	protected String fileEncoding = "GBK";
	
	/**
	 * 校验文件名
	 */
	protected String verfFileName = "";
	
	/**
	 * 校验数值长度用的正则表达式
	 */
	private static String INTEGER = "-?[0-9]{0,d}";
	
	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String dataSpacers = "";
	
	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String verDataSpacers = "";
	
	/**
	 * 同步开始时间
	 */
	protected Date startDate = null;
	
	/**
	 * 校验文件错误邮件信息
	 */
	protected StringBuffer verfMailText = new StringBuffer();
	
	/**
	 * 同步文件总信息
	 */
	protected StringBuffer syncMailText = new StringBuffer();
	
	/**
	 * 邮件信息
	 */
	protected StringBuffer mailText = new StringBuffer();
	
	/**
	 * 总个数
	 */
	protected int countNum = 0;
	
	/**
	 * 成功处理的个数
	 */
	protected int successAdd = 0;
	
	/**
	 * 失败处理的个数
	 */
	protected int failureProcess = 0;
	
	/**
	 * 校验失败的个数
	 */
	protected int failureCheck = 0;
	
	/**
	 * 写入失败邮件条数
	 */
	protected int failMailTextNum = 0;
	
	/**
	 * 是否还写入失败邮件
	 */
	protected boolean isMailText = true;
	
	/**
	 * 执行器
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * 是否执行了内容数据导入
	 */
	protected boolean isImputDate = false;
	
	/**
	 * 用于添加准备动作数据
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
	 * 执行体
	 */
	public String execution(boolean isSendMail)
	{
		// 准备工作
		init();
		
		// 记录开始时间
		startDate = new Date();
		
		// 导入校验数据文件
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
		
		// 导入内容数据文件
		try
		{
			exportDataFile();
		}
		catch (BOException e)
		{
			logger.error(e);
		}
		
		// 组装操作结果邮件
		getMailText();
		
		// 用于释放队列集合
		destroy();
		
		if (isSendMail)
		{
			// 执行发邮件的功能
			this.sendResultMail(mailTitle, mailText);
			return "";
		}
		else
		{
			return mailText.toString();
		}
	}
	
	/**
	 * 用于组装发送
	 * 
	 */
	public void getMailText()
	{
		if (mailText.length() <= 0)
		{
			mailText.append("<b>同步").append(mailTitle).append("情况</b>： <br>");
			mailText.append("同步文件开始时间：").append(
					PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
					.append("，结束时间：").append(
							PublicUtil.getDateString(new Date(),
									"yyyy-MM-dd HH:mm:ss"));
			mailText.append("<br><br>");
			
			mailText.append("校验文件数据导入情况： <br>");
			mailText.append(verfMailText).append("<br>");
			for (int i = 0; i < verfDataList.size(); i++)
			{
				mailText
						.append(((VerfDataVO) verfDataList.get(i)).toMailText());
			}
			mailText.append("<br><br>");
			
			mailText.append("文件数据导入情况： <br>");
			mailText.append("总导入条数：").append(countNum).append("条").append(
					"<br>");
			mailText.append("成功导入条数：").append(successAdd).append("条").append(
					"<br>");
			mailText.append("失败处理条数：").append(failureProcess).append("条")
					.append("<br>");
			mailText.append("校验失败条数：").append(failureCheck).append("条").append(
					"<br>");
			mailText.append(syncMailText).append("<br>");
			mailText.append("<br><br>");
		}
	}
	
	/**
	 * 导入内容数据文件
	 */
	public void exportDataFile() throws BOException
	{
		// 转义模糊文件名中的日期
		fileName = fileNameDataChange(fileName);
		
		// 得到文件列表
		List<String> fileList = getDataFileList(fileName);
		
		if (fileList.size() == 0)
		{
			// 加入邮件错误信息..............
			syncMailText.append("查找不到当前所要的数据文件！！！");
			throw new BOException("查找不到当前所要的数据文件！！！",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// 判断文件长度是否为0
		if (isNullFile(fileList))
		{
			// 加入邮件错误信息..............
			syncMailText.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
			throw new BOException("此次同步的数据文件都为空，此次类型数据同步中止！！！",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseColorComicConfig.taskRunnerNum,
				BaseColorComicConfig.taskMaxReceivedNum);
		
		try
		{
			// 如果存在，解析
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				logger.info("开始处理内容文件：" + tempFileName);
				
				File file = new File(tempFileName);
				
				// 如果文件为空
				if (file.length() == 0)
				{
					// 加入错误邮件信息..............
					syncMailText.append("当前的数据文件内容为空，fileName="
							+ "tempFileName");
					continue;
				}
				
				// 执行了内容数据导入
				isImputDate = true;
				
				// 读文件
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
	 * 返回字段分隔符
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
	 * 校验文件是否全为空以决定是否删表
	 * 
	 * @param fileList
	 * @return
	 */
	public boolean isNullFile(List<String> fileList)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("校验文件是否全为空以决定是否删表：开始");
		}
		
		boolean isNullFile = true;
		
		for (int i = 0; i < fileList.size(); i++)
		{
			String tempFileName = fileList.get(i);
			
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
	 * 导入校验数据文件
	 */
	public void exportVerfFile() throws BOException
	{
		// 转义模糊文件名中的日期
		verfFileName = fileNameDataChange(verfFileName);
		
		// 得到文件列表
		List<String> fileList = getDataFileList(verfFileName);
		
		if (fileList.size() == 0)
		{
			// 加入邮件错误信息..............
			verfMailText.append("查找不到当前所要的校验文件！！！<br>");
			throw new BOException("校验文件为空",
					BaseColorComicConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		
		try
		{
			// 如果存在，解析
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				logger.info("开始处理校验文件：" + tempFileName);
				
				// 读文件
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(tempFileName), this.fileEncoding));
				
				while ((lineText = reader.readLine()) != null)
				{
					// 记录文件的行数。
					lineNumeber++;
					
					if (logger.isDebugEnabled())
					{
						logger.debug("开始处理校验文件第" + lineNumeber + "行数据。");
					}
					
					if (lineNumeber == 1)
					{
						// 删除第一行bom字符
						lineText = PublicUtil.delStringWithBOM(lineText);
					}
					
					// 校验文件的最后一行
					if (BaseColorComicConfig.FILE_END.equals(lineText.trim()))
					{
						break;
					}
					
					// 读文件数据
					VerfDataVO vo = readVerfData(lineText, lineNumeber);
					
					// 写入集合列表中
					verfDataList.add(vo);
				}
			}
		}
		catch (Exception e)
		{
			verfMailText.append("解析校验文件时发生错误 ！！！<br>");
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
	 * 实现类实现校验字段的正确性
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回正确/返回错误信息
	 */
	protected abstract String checkData(String[] data);
	
	/**
	 * 实现类实现返回执行当前业务的sql语句对应的值
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回sql对应值
	 */
	protected abstract Object[] getObject(String[] data);
	
	/**
	 * 实现类实现返回执行当前数据的key值
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回当前数据的key值
	 */
	protected abstract String getKey(String[] data);
	
	/**
	 * 实现类实现返回添加当前业务的sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract String getInsertSqlCode();
	
	/**
	 * 实现类实现返回更新当前业务的sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract String getUpdateSqlCode();
	
	/**
	 * 得到清空原表信息的sql语句
	 * 
	 * @return
	 */
	protected abstract String getDelSqlCode();
	
	/**
	 * 解析校验文件信息（统一处理方式，因为校验文件格式一样）
	 * 
	 * @param lineText
	 *            当前行信息
	 * @param lineNumeber
	 *            第几行 便于记录错误信息
	 * @return 校验文件数据对象
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
			// 加入邮件错误信息...............
			writeErrorToVerfMail(lineNumeber, lineText, "校验文件结构不对，大于或小于5个属性");
		}
		
		return vo;
	}
	
	/**
	 * 得到ftp信息
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
		// 初始化ftp连接模式（FTPConnectMode.PASV或者FTPConnectMode.ACTIVE）
		ftp.setConnectMode(FTPConnectMode.PASV);
		// 使用给定的用户名、密码登陆ftp
		ftp.login(user, password);
		// 设置文件传输类型（FTPTransferType.ASCII或者FTPTransferType.BINARY）
		ftp.setType(FTPTransferType.BINARY);
		
		return ftp;
	}
	
	/**
	 * 得到ftp信息
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
	 * 用于得到数据文件列表
	 * 
	 * @param fileName
	 *            模糊文件名
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
			mailText.append("FTP配置出错，FTP链接出错！！！<br>");
			logger.error(e);
		}
		
		try
		{
			
			// 进入下载路径
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
			
			// 得到目录下文件列表
			String[] ftpFileList = ftp.dir();
			
			if (logger.isDebugEnabled())
			{
				logger.debug("匹配文件名开始：" + fileName);
			}
			
			for (int j = 0; j < ftpFileList.length; j++)
			{
				String tempFileName = ftpFileList[j];
				
				// 匹配文件名是否相同
				if (isMatchFileName(fileName, tempFileName))
				{
					// 得到本地路径
					String absolutePath = localDir + File.separator
							+ tempFileName;
					absolutePath = absolutePath.replace('\\', '/');
					ftp.get(absolutePath, ftpFileList[j]);
					
					// 存入结果集
					fileList.add(absolutePath);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("成功下载文件：" + absolutePath);
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
	 * 用于得到数据文件列表
	 * 
	 * @param fileName
	 *            模糊文件名
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
			mailText.append("FTP配置出错，FTP链接出错！！！<br>");
			logger.error(e);
		}
		
		try
		{
			// 进入下载路径
			if (!"".equals(ftpDir))
			{
				sftp.cd(ftpDir);
			}
			
			// 得到目录下文件列表
			String[] ftpFileList = server.getDirFilenames(sftp);
			
			if (logger.isDebugEnabled())
			{
				logger.debug("匹配文件名开始：" + fileName);
			}
			
			for (int j = 0; j < ftpFileList.length; j++)
			{
				String tempFileName = ftpFileList[j];
				
				// 匹配文件名是否相同
				if (isMatchFileName(fileName, tempFileName))
				{
					// 得到本地路径
					String absolutePath = localDir + File.separator
							+ tempFileName;
					absolutePath = absolutePath.replace('\\', '/');
					// ftp.get(absolutePath, ftpFileList[j]);
					// sftp.get(absolutePath, ftpFileList[j]);
					SFTPServer.downloadSingleFile(absolutePath, ftpFileList[j],
							sftp);
					
					// 存入结果集
					fileList.add(absolutePath);
					
					if (logger.isDebugEnabled())
					{
						logger.debug("成功下载文件：" + absolutePath);
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
	 * 判读是否是本次任务需要下载的文件名
	 * 
	 * @param fName
	 *            当前ftp服务器上的文件
	 * @return true 是，false 否
	 */
	public boolean isMatchFileName(String fileName, String FtpFName)
	{
		return FtpFName.matches(fileName);
	}
	
	/**
	 * 用于转义模糊文件名中的日期数值
	 * 
	 * @param fileName
	 * @return
	 */
	public String fileNameDataChange(String fileName)
	{
		// 如果文件名定义中有日期定义
		if (fileName.indexOf("~D") != -1)
		{
			StringBuffer tempString = new StringBuffer();
			
			Calendar nowTime = Calendar.getInstance();
			
			int temp = fileName.indexOf("~D");
			
			tempString.append(fileName.substring(0, temp));
			
			String dataType = fileName.substring(temp + 2, fileName
					.lastIndexOf("~"));
			
			// 调整日期
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
	 * 用于回收数据
	 */
	public void destroy()
	{
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。开始");
		
		// 如果执行过内容数据导入，则执行下面逻辑
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
						logger.debug("开始删除在本次全量产品文件不存在的老的数据ID，ID=" + keyId);
					}
					
					// 这个地方要加一个删除的逻辑。。。
					try
					{
						DB.getInstance().executeBySQLCode(this.getDelSqlCode(), new Object[]{keyId});
					}
					catch (DAOException e)
					{
						logger.error("删除在本次全量产品文件不存在的老的数据ID时发生错误，ID=" + keyId, e);
					}
				}
			}
		}
		
		logger.info("删除当前数据文件中不存在，但数据库中却存在的数据。结束");
		
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
	 * 写入错误信息
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
			syncMailText.append("由于数据过大，以下不提供详情...").append("<br>");
			isMailText = false;
		}
		else
		{
			/*syncMailText.append("数据内容文件").append(fileName).append("中第").append(
					lineNum).append("行：").append(dataText)
					.append("。 此数据有错原因为：").append(reasonText).append("<br>");*/
			syncMailText.append("数据内容文件中第").append(
					lineNum).append("行，此数据有错原因为：").append(reasonText).append("<br>");
		}
		
		failMailTextNum++;
	}
	
	/**
	 * 写入校验处理发生的错误信息至邮件
	 * 
	 * @param lineNum
	 *            第几行
	 * @param dataText
	 *            什么内容
	 * @param reasonText
	 *            出错原因
	 */
	public void writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		verfMailText.append("校验文件：").append(" 第").append(lineNum).append("行: ")
				.append(dataText).append("。 此数据有错原因为：").append(reasonText)
				.append("<br>");
	}
	
	/**
	 * 发送结果邮件。
	 */
	public void sendResultMail(String mailTitle, StringBuffer mailText)
	{
		logger.info(mailText.toString());
		Mail.sendMail(mailTitle, mailText.toString(),
				BaseColorComicConfig.mailTo);
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
