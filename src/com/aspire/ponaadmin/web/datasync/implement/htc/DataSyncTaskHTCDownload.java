package com.aspire.ponaadmin.web.datasync.implement.htc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.util.FileUtils;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class DataSyncTaskHTCDownload extends DataSyncTask
{
	
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected DataDealer dataDealer;
	private int maxProcessThread;
	
	private static JLogger LOG = LoggerFactory
			.getLogger(DataSyncTaskHTCDownload.class);
	
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class.forName(
				config.get("task.data-reader-class")).newInstance();
		this.dataDealer = (DataDealer) Class.forName(
				config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config);
		dataReader.init(config);
		dataDealer.init(config);
		dataChecker.init(config);
		maxProcessThread = Integer
				.parseInt(config.get("task.maxProcessThread"));
	}
	
	@Override
	protected void doTask() throws BOException
	{
		LOG.info("HTC�������ݵ��뿪ʼ�ˣ�");
		
		String[] filenameList = ftp.process();
		List<String> fileList = new ArrayList<String>();
		
		if (filenameList.length == 0)
		{
			throw new BOException("û���ҵ�����������ļ��쳣",
					DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		String lineText = null;
		BufferedReader reader = null;
		
		try
		{
			TaskRunner runner = new TaskRunner(maxProcessThread);
			
			// ������Ҫ��ͬ��ǰ��Ҫ��֤��ǰͬ���������ȷ�ԡ�
			dataDealer.prepareData();
			
			if (dataChecker instanceof DataHTCDownloadChecker)
			{
				((DataHTCDownloadChecker) dataChecker).initContentId();
			}
			
			for (int a = 0; a < filenameList.length; a++)
			{
				String htcFileRootPath = DataSyncTools.unZip(filenameList[a]);
				String[] htcFile = getServiceInfoFileName(htcFileRootPath);
				fileList.add(htcFileRootPath);
				
				for (int i = 0; i < htcFile.length; i++)
				{
					LOG.info("��ʼ�����ļ�������xmlͷ" + htcFile[i]);
					StringBuffer line = new StringBuffer();
					
					// ����ļ�Ϊ��
					if (new File(htcFileRootPath + File.separator
							+ htcFile[i]).length() == 0)
					{
						throw new BOException("���ļ�Ϊ��",
								DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
					}
					
					reader = new BufferedReader(new InputStreamReader(
							new FileInputStream(htcFileRootPath
									+ File.separator + htcFile[i]),
							this.fileEncoding));
					
					while ((lineText = reader.readLine()) != null)
					{
						line.append(lineText);
					}
					
					reader.close();
					
					int t = 1;
					
					for (String temp : parseTextByXML(line))
					{
						LOG.debug("��ǰ����У��ֵΪ��" + temp);
						// �õ�����
						DataRecord data = dataReader.readDataRecord(temp);
						
						LOG.debug("���ؽ�����ֵΪ��" + data.size());
						
						// �����У�������࣬��ִ������У��
						if (dataChecker != null)
						{
							int checkResult = dataChecker.checkDateRecord(data);
							
							if (checkResult == DataSyncConstants.CHECK_FAILED)
							{
								LOG.error("��" + t + "�����ݼ��ʧ�ܣ����Ը����ݡ�");
								this.addStatisticCount(checkResult);
								this.addCheckFiledRow(t++);
								continue;
							}
						}
						
						// �����첽����
						Task htcTask = new DataHTCDownloadTask(this,
								dataDealer, data, t++);
						
						// ������ӵ���������
						runner.addTask(htcTask);
					}
				}
			}
			// �ȴ���������ɡ�
			runner.waitToFinished();
			
			LOG.info("##############���е�taskִ�����###############");
			
		}
		catch (Exception e)
		{
			throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
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
				LOG.error(e);
			}
			
			// clear data �h���ļ�
			dataDealer.clearDirtyData();
			deletePath(fileList);
		}
	}
	
	/**
	 * ���ڽ�����ǰ�Է�����αxml�����xmlͷ������ɽ���������
	 * @param textXml
	 * @return
	 */
	public List<String> parseTextByXML(StringBuffer textXml)
	{
		List<String> contentList = new ArrayList<String>();
		StringBuffer text = new StringBuffer();
		text.append(
				"<?xml version=\"1.0\" encoding=\""
						+ this.fileEncoding
						+ "\" standalone=\"yes\"?>").append(textXml);
		
		Document doc = null;
		try
		{
			doc = DocumentHelper.parseText(text.toString());
		}
		catch (DocumentException e)
		{
			LOG.error("������ǰ�ļ�ʱ����", e);
			return contentList;
		}
		Element rootElt = doc.getRootElement();
		Iterator<?> iter = rootElt.elementIterator("Application");
		while (iter.hasNext())
		{
			Element recordEle = (Element) iter.next();
			StringBuffer sb1 = new StringBuffer();
			
			sb1.append(recordEle.elementTextTrim("APCode")).append(
					"|");
			sb1.append(recordEle.elementTextTrim("APPID")).append(
					"|");
			sb1.append(recordEle.elementTextTrim("MMAPPID"))
					.append("|");
			sb1.append(recordEle.elementTextTrim("DownCount"));
			contentList.add(sb1.toString());
		}
		return contentList;
	}
	
	/**
	 * ����ɾ�����ص�ZIP�ļ�
	 * 
	 * @param filenameList
	 * 
	 * private void deleteFile(String[] filenameList) { for (String file :
	 * filenameList) { new File(file).delete(); } }
	 */
	
	/**
	 * ����ɾ����ѹ���ZIPĿ¼
	 * 
	 * @param fileList
	 */
	public void deletePath(List<String> fileList)
	{
		for (String filePath : fileList)
		{
			File path = new File(filePath);
			FileUtils.delete(path);
		}
	}
	
	/**
	 * ��ȡ��ѹ������ļ��б�
	 * 
	 * @param htcFileRootPath
	 * @return
	 * @throws BOException
	 */
	private String[] getServiceInfoFileName(String htcFileRootPath)
			throws BOException
	{
		File dir = new File(htcFileRootPath);
		
		String fileNames[] = dir.list(new FilenameFilter()
		{
			public boolean accept(File arg0, String arg1)
			{
				return arg1.matches("APP_downloadCount_\\d{8}_\\d{4}.txt");
			};
		});
		
		if (fileNames != null && fileNames.length > 0)
		{
			return fileNames;
		}
		
		throw new BOException("�Ҳ�����ѹ���HTC�����������ļ���");
	}
	
	public List<String> getContentList(String lineText)
	{
		List<String> list = new ArrayList<String>();
		lineText = lineText.replaceAll("</Applications>", "");
		
		String[] temp = lineText.split("</Application>");
		
		for (String a : temp)
		{
			list.add(a);
		}
		
		return list;
	}
	
	/**
	 * ���ͽ���ʼ������������д�÷����ʼ�������
	 * 
	 * @param result
	 *            ����ִ���Ƿ�����
	 * @param errorCode
	 *            ������롣��Ҫ�����ʼ�������ʾ��ֻ�������쳣�˲���������˼
	 * @param reason
	 *            �����쳣�ľ���ԭ��ֻ�������쳣�˲���������˼
	 */
	protected void mailToAdmin(boolean result, int errorCode, String reason)
	{
		String mailTitle;
		// �����ʼ���ʾ���δ������
		Date endDate = new Date();
		StringBuffer sb = new StringBuffer();
		int totalSuccessCount = this.getSuccessAdd() + this.getSuccessUpdate()
				+ this.getSuccessDelete();
		int totalFailureCount = this.getFailureCheck()
				+ this.getFailureProcess();
		int totalCount = totalSuccessCount + totalFailureCount;
		if (result)
		{
			// MailUtil.sen
			if (totalSuccessCount == 0 && totalFailureCount == 0)
			{
				mailTitle = this.getDesc() + "���,û����Ҫ������������ݣ�����ʼ�����!!";// XX���ݵ�����ɣ�û����Ҫ������������ݣ�����ʼ�����!!
			}
			else if (totalSuccessCount > 0 && totalFailureCount == 0)
			{
				mailTitle = this.getDesc() + "�ɹ�,�ɹ�" + totalSuccessCount
						+ "����ʧ��0��������ʼ�����!";// XX���ݵ���ɹ����ɹ�Y����ʧ��0��������ʼ�����!
			}
			else
			{
				mailTitle = this.getDesc() + "���,�ɹ�" + totalSuccessCount
						+ "����ʧ��" + totalFailureCount + "��������ʼ�����!";// XX���ݵ�����ɣ��ɹ�Y����ʧ��Z��������ʼ�����!
			}
			
			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<h4>��������</h4>");
			sb.append("����" + this.getDesc() + "�����ܹ�����<b>");
			sb.append(totalCount);
			sb.append("</b>����<p>�ɹ�����<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>����");
			if (totalSuccessCount != 0)
			{
				sb.append("<br>����Ϊ���ɹ�����");
				sb.append(this.getSuccessAdd());
				sb.append("�����ɹ�����");
				sb.append(this.getSuccessUpdate());
				sb.append("�����ɹ�ɾ��");
				sb.append(this.getSuccessDelete());
				sb.append("��");
			}
			sb.append("<p>ʧ�ܴ���<b>");
			sb.append(totalFailureCount);
			sb.append("</b>����");
			if (totalFailureCount != 0)
			{
				sb.append("<br>����Ϊ��ʧ��У��");
				sb.append(this.getFailureCheck());
				sb.append("����ʧ�ܴ���");
				sb.append(this.getFailureProcess());
				sb.append("����");
			}
		}
		else
		{
			if (errorCode == DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle = this.getDesc() + "ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!";// XX���ݵ���ʧ�ܣ�û��ȡ����ȷ�������ļ�������ʼ�����!
			}
			else
			{
				mailTitle = this.getDesc() + "ʧ�ܣ�����ʼ�����!";
			}
			
			sb.append("��ʼʱ�䣺");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",����ʱ�䣺");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("��<p>ʧ��ԭ��<br>");
			sb.append(reason);
			
		}
		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), this.getMailTo());
	}
}
