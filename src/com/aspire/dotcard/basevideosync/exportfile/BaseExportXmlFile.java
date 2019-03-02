package com.aspire.dotcard.basevideosync.exportfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.ReflectedTask;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basevideosync.bo.BaseVideoBO;
import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportXMLFile;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

public abstract class BaseExportXmlFile implements BaseFileAbility {

	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportXmlFile.class);

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
	 * ִ����
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * �Ƿ�ִ�����������ݵ���
	 */
	protected boolean isImputDate = false;

	/**
	 * �ܸ���
	 */
	protected int countNum = 0;
	
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
	 * �����ļ���ʽ�ж���ļ����
	 */
	protected String dataSpacers = "";
	
	/**
	 * ͬ����ʼʱ��
	 */
	protected Date startDate = null;
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
	 * �ʼ���Ϣ
	 */
	protected StringBuffer mailText = new StringBuffer();

	/**
	 * ͬ���ļ�����Ϣ
	 */
	protected StringBuffer syncMailText = new StringBuffer();

	/**
	 * �����б�
	 */
	protected static List<String> dataList = Collections.synchronizedList(new ArrayList<String>());

	/**
	 * ����MAP
	 */
	protected static Map<String, String> keyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * �������׼����������
	 */
	public void init() {
		localDir = BaseVideoConfig.LOCALDIR;
		dataSpacers = getDataSpacers();
	}

	/**
	 * �����ֶηָ���
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "\\|";
	}
	
	public String execution(boolean isSendMail) {
		
		// ׼������
		init();
		
		// ��¼��ʼʱ��
		startDate = new Date();
		// �������������ļ�
		
		try {
			
			exportDataFile();
			
		} catch (BOException e) {
			logger.error(e);
		}
		
		// �����ͷŶ��м���
		destroy();

		// ��װ��������ʼ�
		getMailText();

		// ������ռ�������
		clear();

		if (isSendMail) {
			// ִ�з��ʼ��Ĺ���
			BaseVideoBO.getInstance().sendResultMail(mailTitle, mailText);
			return "";
		} else {
			return mailText.toString();
		}

	}

	/**
	 * �������������ļ�
	 */
	protected void exportDataFile() throws BOException {

		if (dataList.size() == 0)
		{
			// �����ʼ�������Ϣ..............
			syncMailText.append("�˴�ͬ��������Ϊ�գ��˴���������ͬ����ֹ������");
			throw new BOException("�˴�ͬ��������Ϊ�գ��˴���������ͬ����ֹ������",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// ������Ƶ��Ŀ����xml����ʱ��ɾ���м��
		if (this instanceof ProgramExportXMLFile)
		{
			// ����м������
			BaseVideoBO.getInstance().delMidTable();
		}
		
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum,
				BaseVideoConfig.taskMaxReceivedNum);

		try {
			
			// ������ڣ�����
			for (int i = 0; i < dataList.size(); i++) {
				String data = String.valueOf(dataList.get(i));

				if (logger.isDebugEnabled()) {
					logger.debug("��ʼ�����������ݣ�" + data);
				}

				// ����ļ�Ϊ��
				if (data == null || "".equals(data)) {
					// ��������ʼ���Ϣ..............
					syncMailText.append("��ǰ����������Ϊ��");
					continue;
				}

				// ִ�����������ݵ���
				isImputDate = true;
				
				lineNumeber++;
				countNum++;
				SynBaseVideoXmlTask task = new SynBaseVideoXmlTask(data, lineNumeber,
						dataSpacers, this);

				ReflectedTask refTask = new ReflectedTask(task, "sysDataByXml",
						null, null);
				dataSynTaskRunner.addTask(refTask);
				
			}
			//��������1��ʱ�����̵߳ȴ�2�룬��ȷ���߳�������� 
			if(countNum == 1)
				dataSynTaskRunner.waitRunningTask();
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		} catch (Exception e) {
			throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
		}
	}

	/**
	 * ���ڵõ������ļ�
	 * 
	 * @param fileName
	 *            ģ���ļ���
	 * @return
	 */
	protected String getXMLDataFile(String fileName) throws BOException {
		String filePath = null;

		FTPClient ftp = getFTPClient();
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ƥ���ļ�����ʼ��" + fileName);
			}

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + fileDir + File.separator
					+ fileName;
			// �ж�FTP�Ƿ�����ļ�
			remoteFilePath = remoteFilePath.replace('\\', '/');
			String absolutePath = localDir + File.separator + fileDir 
					+ File.separator + fileName;
			absolutePath = absolutePath.replace('\\', '/');
			BaseFileTools.createFilePath(absolutePath);
			ftp.get(absolutePath, remoteFilePath);
			filePath = absolutePath;
			
		} catch (Exception e) {
			throw new BOException(e, BaseVideoConfig.EXCEPTION_FTP);
		}finally
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
		return filePath;
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
	 * д�������Ϣ
	 * 
	 * @param fileName
	 * @param lineNum
	 * @param dataText
	 * @param reasonText
	 */
	public synchronized void writeErrorToMail(int lineNum,
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
			syncMailText.append("�������ݵ�").append(lineNum).append(
					"�����������д�ԭ��Ϊ��").append(reasonText).append("<br>");
			failMailTextNum++;
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
		if(dataList!=null) dataList.clear();
		keyMap.clear();
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
			
			mailText.append("������������ļ���������� <br>");
			mailText.append("��������").append(countNum).append("��").append(
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
	
	public void setExportTime(Calendar setTime) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ʵ����ʵ��У���ֶε���ȷ��
	 * 
	 * @param data
	 *            ������Ϣ
	 * @param flag
	 *            ��־λ
	 * @return ������ȷ/���ش�����Ϣ
	 */
	protected abstract String checkData(Object object,String[] data);
	
	/**
	 * ʵ����ʵ�ֽ���xml�ļ����ݵ�����
	 * 
	 * @param data
	 *            ������Ϣ
	 * @return ����
	 */
	protected abstract Object getObject(Element root);
	
	/**
	 * ʵ����ʵ�ֽ�����ת����sql������Object[][]
	 * 
	 * @param object
	 *            ������Ϣ
	 * @return ����
	 */
	protected abstract Object getObjectParas(Object object);
	
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

}
