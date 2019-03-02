package com.aspire.dotcard.baseVideoNew.exportfile;

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

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.baseVideo.vo.VerfDataVO;
import com.aspire.dotcard.baseVideoNew.bo.BaseVideoNewFileBO;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.ProgramExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.impl.VideoExportFile;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

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
	 * ��ǰ�����Ӧ�ı���
	 */
	protected String tableName = "";
	
	/**
	 * �ʼ�������Ϣ
	 */
	protected String mailTitle = "";
	
	/**
	 * �ļ���ŵı���·��
	 */
	protected String localDir = "";
	
	/**
	 * ȡ�ļ������ڣ�0��Ϊ���죬1��Ϊǰһ�죬2��Ϊǰ����
	 */
	protected int getDateNum = 0;
	
	/**
     * ȡ�ļ���ʱ�䣬0��Ϊ��Сʱ��1��ΪǰСʱ��2��Ϊǰ��Сʱ
     */
    protected int getTimeNum = 0;
    
    protected boolean isByHour=false;
	
	/**
	 * �ֶ����õ�����������
	 */
	protected Calendar setTime = null;
	
	/**
	 * ������ļ���
	 */
	protected String fileName = "";
	
	/**
	 * �����ѹ���ļ���
	 */
	protected String gzFileName = "";
	
	/**
	 * �Ƿ���У���ļ�
	 */
	protected boolean hasVerf = true;
	
	/**
	 * �Ƿ�����м��
	 */
	protected boolean isDelMidTable = true;
	
	/**
	 * �ļ���������
	 */
	protected String fileEncoding = "GBK";
	
	/**
	 * У���ļ���
	 */
	protected String verfFileName = "";
	
	/**
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String dataSpacers = "";
	
	/**
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String verDataSpacers = "|";
	
	/**
	 * ͬ����ʼʱ��
	 */
	protected Date startDate = null;
	
	/**
     * �Ƿ�Ҫȫ�����
     */
    protected boolean isDelTable = true;
	
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
	 * ��������
	 */
	protected int addNum = 0;
	
	/**
	 * �޸ĸ���
	 */
	protected int modifyNum = 0;
	
	/**
	 * ɾ������
	 */
	protected int deleteNum = 0;
	
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
	 * �Ƿ����ݼ��ڵ�����ݼ�
	 */
	protected boolean isCollect = false;
	
	/**
	 * ִ����
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * �Ƿ�ִ�����������ݵ���
	 */
	protected boolean isImputDate = false;
	
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
	 * �������׼����������
	 */
	public void init()
	{
		localDir = BaseVideoNewConfig.LOCALDIR;
		getDateNum = BaseVideoNewConfig.GET_DATE_NUM;
		dataSpacers = getDataSpacers();
		verDataSpacers = BaseVideoNewConfig.verDataSpacers;
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
	protected void writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		verfMailText.append("У���ļ���").append(" ��").append(lineNum).append("��: ")
				.append(dataText).append("�� �������д�ԭ��Ϊ��").append(reasonText)
				.append("<br>");
	}
	
	/**
	 * ����У���ļ���Ϣ��ͳһ����ʽ����ΪУ���ļ���ʽһ����
	 * 
	 * @param lineText
	 *            ��ǰ����Ϣ
	 * @param lineNumeber
	 *            �ڼ��� ���ڼ�¼������Ϣ
	 * @return У���ļ����ݶ���
	 */
	protected VerfDataVO readVerfData(String lineText, int lineNumeber)
	{
		VerfDataVO vo = new VerfDataVO();
		String[] tempData = lineText.split(verDataSpacers, -1);
		
		if (tempData.length == 5)
		{
			vo.setFileName(tempData[0]);
			vo.setFileSiz(tempData[1]);
			vo.setFileDataNum(tempData[2]);
			vo.setFileDate(tempData[3]);
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
	 * �õ���ǰFTP���Ӷ���
	 * 
	 * @return
	 */
	protected FTPClient getFTPClient()
	{
		FTPClient ftp = null;
		String ftpDir = BaseVideoNewConfig.FTPPAHT;
		try
		{
			ftp = BaseFileNewTools.getFTPClient();
			// ��������·��
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
		}
		catch (Exception e)
		{
			mailText.append("FTP���ó���FTP���ӳ�������<br>");
			logger.error(e);
		}
		return ftp;
	}
	
	/**
	 * ���ڵõ������ļ��б�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	protected List<String> getDataFileList(String fileName) throws BOException
	{
		List<String> fileList = new ArrayList<String>();
		
		FTPClient ftp = getFTPClient();
		
		try
		{
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
				if (BaseFileNewTools.isMatchFileName(fileName, tempFileName))
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
			throw new BOException(e, BaseVideoNewConfig.EXCEPTION_FTP);
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
	 * ���ڵõ���Ƶ����ԱȺ��ļ��б�
	 * 
	 * @return
	 */
	protected List<String> getDataFileListByVideoDetail() throws BOException
	{
		/*String fileNameNew = BaseFileNewTools.fileNameDataChange(fileName,
				getDateNum, setTime,isByHour,getTimeNum);
		String fileNameOld = BaseFileNewTools.fileNameDataChange(fileName,
				getDateNum + 1, setTime,isByHour,getTimeNum);
		
		// ��ȡ��ǰ�ļ���ǰһ����ļ�
		getDataFileList(fileNameNew);
		getDataFileList(fileNameOld);*/
		
		// ִ��shell�ű����Ա��ļ����죬�ó������ļ��б�
		return BaseFileNewTools.execShell(BaseFileNewTools.getShellFileDate(getDateNum, setTime));
	}
	
	/**
	 * ���ڵõ���Ƶ����ȫ��ѹ���ļ�����ѹ�ļ�
	 * 
	 * @return
	 */
	protected void getAndUngzipDataFileByVideoDetail() throws BOException
	{
		String fileNameNew = BaseFileNewTools.fileNameDataChange(gzFileName,
				getDateNum, setTime,isByHour,getTimeNum);
		String fileNameOld = BaseFileNewTools.fileNameDataChange(gzFileName,
				getDateNum + 1, setTime,isByHour,getTimeNum);
		
		// ��ȡ��ǰ�ļ�
		List<String> fileNameNews =  getDataFileList(fileNameNew);
		if(fileNameNews.size() == 0 ){
			throw new BOException("ѹ���ļ�Ϊ��,�ļ�����Ϊ��"+fileNameNew,
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		try {
			//��ѹ�ļ���ָ��Ŀ¼
			BaseFileNewTools.ungzip(fileNameNews.get(0),localDir);
			
		} catch (Exception e) {
			throw new BOException("��ѹ�ļ�ʧ�ܣ��ļ�����Ϊ��"+fileNameNews.get(0),e);
		}
		// ��ȡǰһ����ļ�
		List<String> fileNameOlds = getDataFileList(fileNameOld);
		if(fileNameOlds.size() == 0 ){
			throw new BOException("ѹ���ļ�Ϊ��,�ļ�����Ϊ��"+fileNameOld,
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		try {
			//��ѹ�ļ���ָ��Ŀ¼
			BaseFileNewTools.ungzip(fileNameOlds.get(0),localDir);
			
		} catch (Exception e) {
			
			throw new BOException("��ѹ�ļ�ʧ�ܣ��ļ�����Ϊ��"+fileNameOlds.get(0),e);
		}
		
	}
	
	/**
	 * ����У�������ļ�
	 */
	protected void exportVerfFile() throws BOException
	{
        List<String> fileList;
		
		// �������Ƶ���鵼�����������Ҫ���⴦��
		if (this instanceof ProgramExportFile)
		{
			fileList = new ArrayList<String>();
			String filename = localDir + File.separator + BaseFileNewTools.fileNameDataChange(verfFileName, getDateNum, setTime,isByHour,getTimeNum);
			File file = new File(filename);
			if(file.exists() && file.length() > 0){
				fileList.add(filename);
			}
		}
		else
		{
			
			// ת��ģ���ļ����е�����, �õ��ļ��б�
			fileList = getDataFileList(BaseFileNewTools
					.fileNameDataChange(verfFileName, getDateNum, setTime,isByHour,getTimeNum));
			
		}
		if (fileList.size() == 0)
		{
			// �����ʼ�������Ϣ..............
			verfMailText.append("���Ҳ�����ǰ��Ҫ��У���ļ�������<br>");
			throw new BOException("У���ļ�Ϊ��",
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
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
				
				if (logger.isDebugEnabled())
				{
					logger.debug("��ʼ����У���ļ���" + tempFileName);
				}
				
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
					if (BaseVideoNewConfig.FILE_END.equals(lineText.trim()))
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
			throw new BOException(e, BaseVideoNewConfig.EXCEPTION_INNER_ERR);
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
	 * �������������ļ�
	 */
	protected void exportDataFile() throws BOException
	{
		List<String> fileList;
		
		// �������Ƶ���鵼�����������Ҫ���⴦���õ��Ա��ļ������
		if (this instanceof ProgramExportFile)
		{
			fileList = getDataFileListByVideoDetail();
		}
		else
		{
			// ת��ģ���ļ����е�����, �õ��ļ��б�
			fileList = getDataFileList(BaseFileNewTools
					.fileNameDataChange(fileName, getDateNum, setTime,isByHour,getTimeNum));
		}
		
		// �ж��ļ������Ƿ�Ϊ0
		if (BaseFileNewTools.isNullFile(fileList))
		{
			// �����ʼ�������Ϣ..............
			syncMailText.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
			throw new BOException("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������",
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// �Ƿ�����м��
		if (isDelMidTable)
		{
			// ����м������
			BaseVideoNewFileBO.getInstance().delMidTable(tableName);
		}
		if (this instanceof VideoExportFile)
		{//��Ƶȫ���ļ�����
			logger.info(" ��ȡȫ����Ƶ�����ļ�ͬ��ʱ�䣺"+BaseVideoNewConfig.GET_WEEK_NUM+"����ǰʱ�䣺"+BaseFileNewTools.getWeekOfDate(new Date()));
			getFullVideoFile();
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoNewConfig.taskRunnerNum,
				BaseVideoNewConfig.taskMaxReceivedNum);
		
		try
		{
			// ������ڣ�����
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				if (logger.isDebugEnabled())
				{
					logger.debug("��ʼ���������ļ���" + tempFileName);
				}
				
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
					
					SynBaseVideoTask task = new SynBaseVideoTask(lineText,
							tempFileName, lineNumeber, dataSpacers, this);
					
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
			throw new BOException(e, BaseVideoNewConfig.EXCEPTION_INNER_ERR);
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
	 * ���ڵõ�ȫ����Ƶ�����ļ�������ȫ����Ƶ�����ļ�����ȫ����ʱ��
	 * 
	 * @return
	 */
	protected void getFullVideoFile()
	{
		List<String> fileList;
		// ��ȡȫ����Ƶ�����ļ�
		try {
			if(BaseVideoNewConfig.GET_WEEK_NUM == BaseFileNewTools.getWeekOfDate(new Date())){
				fileList = getDataFileList(BaseFileNewTools
						.fileNameDataChange(gzFileName, getDateNum, setTime,isByHour,getTimeNum));
				// �ж��ļ������Ƿ�Ϊ0
				if (BaseFileNewTools.isNullFile(fileList))
				{
					// �����ʼ�������Ϣ..............
					syncMailText.append("�˴�ͬ������Ƶ����ȫ�������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
					throw new BOException("�˴�ͬ������Ƶ����ȫ�������ļ���Ϊ�գ��˴���������ͬ����ֹ������",
							BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
				}
				//��ѹѹ���ļ�
				BaseFileNewTools.ungzip(fileList.get(0),localDir);
				// �����Ƶȫ����ʱ������
				BaseVideoNewFileBO.getInstance().delVideoFullTable(tableName);
				// ִ��shell�ű�����ȫ����Ƶ�����ļ�������ʱ����
				BaseFileNewTools.execShellOfVideoFullImport(BaseFileNewTools.getShellFileDate(getDateNum, setTime));
				// ���ô洢���� ����ִ����Ƶȫ����ʱ�����м��������ת��
				BaseVideoNewFileBO.getInstance().syncVideoFullData();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * ������װ����
	 * 
	 */
	protected void getMailText()
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
			
			mailText.append("У���ļ����ݵ�������� <br>");
			mailText.append("�ܵ���������").append(countNum).append("��").append(
			"<br>");
			if(isCollect){
				mailText.append("����������").append(addNum).append("��").append(
						"<br>");
				mailText.append("�޸�������").append(modifyNum).append("��").append(
				"<br>");
				mailText.append("ɾ��������").append(deleteNum).append("��").append(
				"<br>");
			}else{
				mailText.append("�ɹ�����������").append(successAdd).append("��").append(
				"<br>");
			}
			mailText.append("ʧ�ܴ���������").append(failureProcess).append("��")
			.append("<br>");
			mailText.append("У��ʧ��������").append(failureCheck).append("��").append(
			"<br>");
			mailText.append(syncMailText).append("<br>");
			mailText.append("<br><br>");
		}
	}
	
	/**
	 * ����д��Ԥɾ������
	 */
	protected void destroy()
	{
		// ���ִ�й��������ݵ��룬��ִ�������߼�
		if (isImputDate)
		{
			Iterator<String> ite = keyMap.keySet().iterator();
			
			while (ite.hasNext())
			{
				String key = ite.next();
				if (!"0".equals(keyMap.get(key)))
				{
					// ������¼Ϊû��ʹ�ù��ģ���������ɾ������
					BaseVideoNewFileBO.getInstance().delDataByKey(
							getDelSqlCode(), getDelKey(key));
					if(isCollect){
						setDeleteNumAdd();
					}
				}
			}
		}
	}
	
	/**
	 * ���ڻ�������
	 */
	protected void clear()
	{
		verfDataList.clear();
		keyMap.clear();
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
		
		// �������Ƶ����ȫ���������������Ҫ���⴦������ѹ���ļ�����ѹ������
		if (this instanceof ProgramExportFile)
		{
			try
			{
				getAndUngzipDataFileByVideoDetail();
			} 
			catch (BOException e)
			{
				logger.error(e);;
			}
		}
		
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
		
		// �����ͷŶ��м���
		destroy();
		
		// ��װ��������ʼ�
		getMailText();
		
		// ������ռ�������
		clear();
		
		if (isSendMail)
		{
			// ִ�з��ʼ��Ĺ���
			BaseVideoNewFileBO.getInstance()
					.sendResultMail(mailTitle, mailText);
			return "";
		}
		else
		{
			return mailText.toString();
		}
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
			syncMailText.append("���������ļ��е�").append(lineNum).append(
					"�У��������д�ԭ��Ϊ��").append(reasonText).append("<br>");
			failMailTextNum++;
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
	
	public synchronized void setAddNumAdd()
	{
		this.addNum++;
	}
	
	public synchronized void setModifyNumAdd()
	{
		this.modifyNum++;
	}
	
	public synchronized void setDeleteNumAdd()
	{
		this.deleteNum++;
	}
	
	public synchronized boolean hasKey(String key)
	{
		if (!keyMap.containsKey(key))
		{
			keyMap.put(key, "0");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public synchronized void delKeyMap(String key)
	{
		keyMap.put(key, "0");
	}
	
	/**
	 * ʵ����ʵ�ַ���ִ�е�ǰ���ݵ�keyֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ���ص�ǰ���ݵ�keyֵ
	 */
	protected abstract String getKey(String[] data);
	
	/**
	 * ʵ����ʵ��У���ֶε���ȷ��
	 * 
	 * @param data
	 *            ������Ϣ
	 * @param flag
	 *            ��־λ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	protected abstract String checkData(String[] data,boolean flag);
	
	/**
	 * ʵ����ʵ�ַ���ִ�е�ǰҵ���sql����Ӧ��ֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ����sql��Ӧֵ
	 */
	protected abstract Object[] getObject(String[] data);
	
	/**
	 * ʵ����ʵ�ַ���ִ��Ԥɾ����ǰ���ݵ�keyֵ
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ���ص�ǰ���ݵ�keyֵ
	 */
	protected String[] getDelKey(String key)
	{
		return key.split("\\|", -1);
	}
	
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
	 * ʵ����ʵ�ַ��ظ��µ�ǰҵ���sql���
	 * 
	 * @return sql���
	 */
	protected abstract String getDelSqlCode();
	
	public void setExportTime(Calendar setTime)
	{
		this.setTime = setTime;
	}
}
