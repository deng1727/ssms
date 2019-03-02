package com.aspire.dotcard.basevideosync.exportfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.httpclient.NameValuePair;

import net.sf.json.JSONObject;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;
import com.aspire.dotcard.basevideosync.client.HttpSender;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentApiExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramByHourExportFile;
import com.aspire.dotcard.basevideosync.vo.VerfDataVO;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

public abstract class BaseExportFile implements BaseFileAbility{

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
	 * ���ݻ�ȡ�ӿڵĲ���
	 */
	protected List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	
	/**
	 * ��ǰ�����Ӧ�ı���
	 */
	protected String tableName = "";
	
	/**
	 * �ʼ�������Ϣ
	 */
	protected String mailTitle = "";
	
	/**
	 * �ļ���ŵı��ظ�·��
	 */
	protected String localDir = "";
	
	/**
	 * �ļ����Ŀ¼
	 */
	protected String fileDir = "";
	
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
	 * �Ƿ���У���ļ�
	 */
	protected boolean hasVerf = true;
	
	/**
	 * �ļ���������
	 */
	protected String fileEncoding = "UTF-8";
	
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
	protected String verDataSpacers = "###";
	
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
	 * ·��Ϊ��
	 */
	protected int pathIsNotExit = 0;
	
	/**
	 * д��ʧ���ʼ�����
	 */
	protected int failMailTextNum = 0;
	
	protected int apiRequest = 0;
	/**
	 * ��֤ʧ��
	 */
	protected int failAuth = 0;
	/**
	 * ϵͳ�쳣
	 * 
	 */
	protected int systemException = 0;
	/**
	 * ������ʽ����
	 */
	protected int parameterFormatError = 0;
	
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
		localDir = BaseVideoConfig.LOCALDIR;
		getDateNum = BaseVideoConfig.GET_DATE_NUM;
		dataSpacers = getDataSpacers();
		verDataSpacers = BaseVideoConfig.verDataSpacers;
	}
	
	/**
	 * �����ֶηָ���
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "###";
	}
	
	/**
     *  ִ����
     *  @param isSendMail �Ƿ����ʼ�
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
			if(this.apiRequest == 1){
				this.requestExportDataFile();
			}else{		
				exportDataFile();
			}
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
			BaseVideoBO.getInstance()
					.sendResultMail(mailTitle, mailText);
			return "";
		}
		else
		{
			return mailText.toString();
		}
	}

	/**
	 * ����У�������ļ�
	 */
	protected void exportVerfFile() throws BOException
	{
		// ת��ģ���ļ����е�����, �õ��ļ��б�
        List<String> fileList = getDataFileList(BaseFileTools
				.fileNameDataChange(verfFileName, getDateNum, setTime,isByHour,getTimeNum));
		if (fileList.size() == 0)
		{
			// �����ʼ�������Ϣ..............
			verfMailText.append("���Ҳ�����ǰ��Ҫ��У���ļ�������<br>");
			throw new BOException("У���ļ�Ϊ��",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
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
					if (BaseVideoConfig.FILE_END.equals(lineText.trim()))
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
			throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
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
		if (this instanceof ProgramByHourExportFile)
		{
			//��Ƶ��Ŀ����ʱ���ʱ��Ϊÿ��ĵ�һ��ʱ����ȡǰһ�������
			String[] hoursConf =BaseVideoConfig.STARTTIME_HOURS.split("\\|");
			Calendar date = Calendar.getInstance();
			String hours = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
			if(hoursConf[0].equals(hours)){
				getDateNum = 1;
				isByHour = false;
			}
		}
		
		// ת��ģ���ļ����е�����, �õ��ļ��б�
		List<String> fileList = getDataFileList(BaseFileTools
					.fileNameDataChange(fileName, getDateNum, setTime,isByHour,getTimeNum));
		// �ж��ļ������Ƿ�Ϊ0
		if (BaseFileTools.isNullFile(fileList))
		{
			// �����ʼ�������Ϣ..............
			syncMailText.append("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������");
			throw new BOException("�˴�ͬ���������ļ���Ϊ�գ��˴���������ͬ����ֹ������",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// ������Ƶ��Ʒ�������Ʒ��������⴦��
		if (this instanceof PkgSalesExportFile)
		{
			// ��ղ�Ʒ�������Ʒ����ݱ�����
			BaseVideoBO.getInstance().delTable(tableName);
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum,
				BaseVideoConfig.taskMaxReceivedNum);
		
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
							+ tempFileName);
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
			//��������1��ʱ�����̵߳ȴ�2�룬��ȷ���߳�������� 
			if(countNum == 1)
				dataSynTaskRunner.waitRunningTask();
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		}
		catch (Exception e)
		{
			throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
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
			if (logger.isDebugEnabled())
			{
				logger.debug("ƥ���ļ�����ʼ��" + fileName);
			}

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + fileDir + File.separator + fileName;
			//�ж�FTP�Ƿ�����ļ�
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// �õ�����·��
			String absolutePath = localDir + File.separator + fileDir 
			                + File.separator + fileName;
			absolutePath = absolutePath.replace('\\', '/');
			BaseFileTools.createFilePath(absolutePath);
			ftp.get(absolutePath, remoteFilePath);
			fileList.add(absolutePath);
			
		}
		catch (Exception e)
		{
			throw new BOException(e, BaseVideoConfig.EXCEPTION_FTP);
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
	 * �õ���ǰFTP���Ӷ���
	 * 
	 * @return
	 */
	protected FTPClient getFTPClient()
	{
		FTPClient ftp = null;
		String ftpDir = BaseVideoConfig.FTPPATH;
		try
		{
			ftp = BaseFileTools.getFTPClient();
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
		
		if (tempData.length == 4)
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
			writeErrorToVerfMail(lineNumeber, lineText, "У���ļ��ṹ���ԣ����ڻ�С��4������");
		}
		
		return vo;
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
	
	/**
	 * ������װ���͵��ʼ�����
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
			
			if(this.apiRequest == 1){		
				mailText.append("�������ݻ�ȡ�ӿ�ϵͳ�쳣��").append(systemException).append("��").append(
						"<br>");
				mailText.append("�������ݻ�ȡ�ӿ���֤ʧ�ܣ�").append(failAuth).append("��").append(
						"<br>");
				mailText.append("�������ݻ�ȡ�ӿڲ�����ʽ����").append(parameterFormatError).append("��").append(
						"<br>");
				mailText.append("�������ݻ�ȡ�ӿ�·��Ϊ�գ�").append(this.pathIsNotExit).append("��").append(
						"<br>");
			}
			
			mailText.append("У���ļ����ݵ�������� <br>");
			mailText.append(verfMailText).append("<br>");
			for (int i = 0; i < verfDataList.size(); i++)
			{
				mailText
						.append(((VerfDataVO) verfDataList.get(i)).toMailText());
			}
			mailText.append("<br><br>");
			
			mailText.append("������������ļ���������� <br>");
			mailText.append("�ܵ���������").append(countNum).append("��").append(
			"<br>");
			mailText.append("�ɹ�����������").append(successAdd).append("��").append(
			"<br>");
			mailText.append("����������").append(addNum).append("��").append(
			"<br>");
	        mailText.append("�޸�������").append(modifyNum).append("��").append(
	        "<br>");
	        mailText.append("ɾ��������").append(deleteNum).append("��").append(
	        "<br>");
			mailText.append("ʧ�ܴ���������").append(failureProcess).append("��")
			.append("<br>");
			mailText.append("У��ʧ��������").append(failureCheck).append("��").append(
			"<br>");
			mailText.append(syncMailText).append("<br>");
			// ������Ƶ�ȵ�������ܸ���
		/*	if (this instanceof HotcontentExportFile || this instanceof HotcontentApiExportFile)
			{
				// �����ȵ������б����ô洢���̸����ȵ��������
				mailText.append(BaseVideoBO.getInstance().updateHotcontentCategoryMap());
			}*/
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
					BaseVideoBO.getInstance().delDataByKey(
							getDelSqlCode(), getDelKey(key));
					setDeleteNumAdd();
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
		this.dataList.clear();
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
	
	public void setExportTime(Calendar setTime) {
		this.setTime = setTime;
	}
	
	/**
	 * ����api�ӿڵ���
	 * 
	 * <p>
	 * 1.�ӿڵ��÷���ʱʱ������5���ӡ�
	 * </p>
	 * <p>
	 * 2.�������أ�1��/ÿ����/ÿ���뷽
	 * </p>
	 * <p>
	 * ���ò���
	 * </p>
	 * <p>
	 * u�� ����ϵͳ�û���
	 * </p>
	 * <p>
	 * t��01��ͨ��Ŀ 02 ҵ���Ʒ�Ͳ�Ʒ�����Ʒ� 03 �Ʒ����� 04�ȵ������б�
	 * </p>
	 * <p>
	 * s�� ��¼��ʼ��¼������Ĭ��Ϊ0
	 * </p>
	 * <p>
	 * l����ҳ��С�����Ϊ5000��Ĭ��Ϊ1000������5000��ϵͳǿ������Ϊ5000
	 * </p>
	 * <p>
	 * contentType��һ��������� 1000 ��Ӱ 1001 ���Ӿ� 1002 ��ʵ 1003 ���� 1004 ���� 1005 ���� 1006
	 * ���� 1007 ���� 1008 ���� 1009 ���� 1010 ԭ�� 1011 ���� 500020 ֱ�� 500060 ����-����С˵
	 * 500067 ����-���� 500072 ����-��̨ 500078 ����-�ȵ���Ѷ 500100 ����-��ͯ 500106 ����-���� 500111
	 * ����-���а��� 500213 �����ƹ� 500323 ��Ƶ 500405 ���� 500468 ���� 500424 ��Ц 500377 ����
	 * 500320 �ƾ� 500422 ʱ��
	 * </p>
	 * <p>
	 * startDate: ���ݿ�ʼʱ�� ���ڸ�ʽ��YYYYMMDDHH24MISS
	 * </p>
	 * <p>
	 * endDate:���ݽ���ʼʱ�� ���ڸ�ʽ��YYYYMMDDHH24MISS
	 * </p>
	 * 
	 * @throws BOException
	 */
	protected void requestExportDataFile() throws BOException {
		long t1 = 0;
		/**
		 * ��ȡ���ݽӿ����������
		 */
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> map = dataList.get(i);
			if (i != 0 && (System.currentTimeMillis() - t1) < 60000*5) {
				try {
					Thread.sleep(60000*5 - System.currentTimeMillis() + t1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			t1 = System.currentTimeMillis();
			NameValuePair[] nameValuePair = new NameValuePair[7];
			nameValuePair[0] = new NameValuePair("u", BaseVideoConfig.userId);
			nameValuePair[1] = new NameValuePair("s", "0");
			nameValuePair[2] = new NameValuePair("l", "5000");
			String syncDataTime = BaseVideoConfig.APISTARTTIME;
			// �õ�������Сʱ�ͷ���
			int syncDataHours = Integer.parseInt(syncDataTime.substring(0, 2));
			int syncDataMin = Integer.parseInt(syncDataTime.substring(3, 5));
			Date date = new Date();
			date.setHours(syncDataHours);
			date.setMinutes(syncDataMin);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			nameValuePair[3] = new NameValuePair("endDate",
					new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
							.getTime()));
			if (BaseVideoConfig.APIDURATION > 0){	
				calendar.add(Calendar.DATE, -BaseVideoConfig.APIDURATION);
			}else{
				calendar.add(Calendar.DATE, -1);
			}
			nameValuePair[4] = new NameValuePair("startDate",
					new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
							.getTime()));
			nameValuePair[5] = new NameValuePair("t", map.get("content")
					.toString());
			nameValuePair[6] = new NameValuePair("contentType", map.get(
					"contentType").toString());
			logger.info("t: " + map.get("content") + " contentType: " + map.get("contentType"));
			JSONObject jsonObject = null;
			try {
				jsonObject = HttpSender.sendRequest(BaseVideoConfig.baseUrl,
						nameValuePair);
				logger.info("jsonObject:" + jsonObject.toString());
			} catch (Exception e1) {
				logger.error("api��������ʧ�ܣ�", e1);
				continue;
			}

			/**
			 * �ɹ���ȡ���ص�����
			 */
			if (jsonObject != null && !"".equals(jsonObject)) {
				// �ɹ�
				if ("000000".equals((jsonObject.get("returnCode").toString()
						.trim()))) {
					if(jsonObject.get("pth") == null || "".equals(jsonObject.get("pth").toString())){
						pathIsNotExit ++;
						continue;
					}
					String path = this.getDataFile(jsonObject);
					this.operationData(path);
					// ��֤ʧ��
				} else if ("200001".equals((jsonObject.get("returnCode")
						.toString().trim()))) {
					failAuth++;
					// ������ʽ����
				} else if ("200002".equals((jsonObject.get("returnCode")
						.toString().trim()))) {
					parameterFormatError++;
					// ϵͳ�쳣
				} else {
					systemException++;
				}
			}
		}
	}
	
	/**
	 * ���ڵõ������ļ�
	 * 
	 * @param jsonObject
	 *      api���󷵻ص�����
	 * @return
	 * @throws BOException
	 */
	protected String getDataFile(JSONObject jsonObject) throws BOException {
		FTPClient ftp = getFTPClient();
		try {
			/**
			 * ftp����Զ�����ݵ�����
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("ƥ���ļ�����ʼ��" + jsonObject.get("pth"));
			}
			logger.info("remoteFilePath: " + jsonObject.get("pth"));

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + jsonObject.get("pth");
			// �ж�FTP�Ƿ�����ļ�
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// �õ�����·��
			String absolutePath = localDir + File.separator 
					+ File.separator + jsonObject.get("pth");
			logger.info("remoteFilePath: " + remoteFilePath);
			absolutePath = absolutePath.replace('\\', '/');
			BaseFileTools.createFilePath(absolutePath);
			ftp.get(absolutePath, remoteFilePath);
			ftp.delete(remoteFilePath);
			return absolutePath;
		} catch (Exception e) {
			throw new BOException(e, BaseVideoConfig.EXCEPTION_FTP);
		} finally {
			if (ftp != null) {
				try {
					ftp.quit();
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * ��������
	 * 
	 * @param path
	 * 
	 * @throws BOException
	 */
	protected void operationData(String path) throws BOException {
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum,
				BaseVideoConfig.taskMaxReceivedNum);
		try {
			// ������ڣ�����
			File file = new File(path);

			// ����ļ�Ϊ��
			if (file.length() == 0) {
				// ��������ʼ���Ϣ..............
				syncMailText.append("��ǰ�������ļ�����Ϊ�գ�fileName=" + path);
				return;
			}

			// ִ�����������ݵ���
			isImputDate = true;

			// ���ļ�
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), this.fileEncoding));

			while ((lineText = reader.readLine()) != null) {
				lineNumeber++;
				countNum++;
				SynBaseVideoTask task = new SynBaseVideoTask(lineText, path,
						lineNumeber, dataSpacers, this);

				ReflectedTask refTask = new ReflectedTask(task,
						"sysDataByFile", null, null);
				dataSynTaskRunner.addTask(refTask);
			}
			// ��������1��ʱ�����̵߳ȴ�2�룬��ȷ���߳��������
			if (countNum == 1)
				dataSynTaskRunner.waitRunningTask();
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		} catch (Exception e) {
			throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

}
