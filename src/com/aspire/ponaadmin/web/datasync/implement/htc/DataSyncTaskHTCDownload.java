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
		LOG.info("HTC下载数据导入开始了！");
		
		String[] filenameList = ftp.process();
		List<String> fileList = new ArrayList<String>();
		
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",
					DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		String lineText = null;
		BufferedReader reader = null;
		
		try
		{
			TaskRunner runner = new TaskRunner(maxProcessThread);
			
			// 首先需要在同步前需要保证当前同步任务的正确性。
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
					LOG.info("开始处理文件：加入xml头" + htcFile[i]);
					StringBuffer line = new StringBuffer();
					
					// 如果文件为空
					if (new File(htcFileRootPath + File.separator
							+ htcFile[i]).length() == 0)
					{
						throw new BOException("此文件为空",
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
						LOG.debug("当前传入校验值为：" + temp);
						// 得到数据
						DataRecord data = dataReader.readDataRecord(temp);
						
						LOG.debug("返回解析的值为：" + data.size());
						
						// 如果有校验数据类，则执行数据校验
						if (dataChecker != null)
						{
							int checkResult = dataChecker.checkDateRecord(data);
							
							if (checkResult == DataSyncConstants.CHECK_FAILED)
							{
								LOG.error("第" + t + "个数据检查失败，忽略该数据。");
								this.addStatisticCount(checkResult);
								this.addCheckFiledRow(t++);
								continue;
							}
						}
						
						// 构造异步任务
						Task htcTask = new DataHTCDownloadTask(this,
								dataDealer, data, t++);
						
						// 将任务加到运行器中
						runner.addTask(htcTask);
					}
				}
			}
			// 等待任务处理完成。
			runner.waitToFinished();
			
			LOG.info("##############所有的task执行完毕###############");
			
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
			
			// clear data 刪除文件
			dataDealer.clearDirtyData();
			deletePath(fileList);
		}
	}
	
	/**
	 * 用于解析当前对方传来伪xml（添加xml头，以完成解析工作）
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
			LOG.error("解析当前文件时出错", e);
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
	 * 用于删除下载的ZIP文件
	 * 
	 * @param filenameList
	 * 
	 * private void deleteFile(String[] filenameList) { for (String file :
	 * filenameList) { new File(file).delete(); } }
	 */
	
	/**
	 * 用于删除解压后的ZIP目录
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
	 * 获取解压缩后的文件列表
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
		
		throw new BOException("找不到解压后的HTC下载量数据文件！");
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
	 * 发送结果邮件。子类可以重写该发送邮件方法。
	 * 
	 * @param result
	 *            任务执行是否正常
	 * @param errorCode
	 *            出错代码。主要用于邮件标题显示。只有任务异常此参数才有意思
	 * @param reason
	 *            任务异常的具体原因，只有任务异常此参数才有意思
	 */
	protected void mailToAdmin(boolean result, int errorCode, String reason)
	{
		String mailTitle;
		// 发送邮件表示本次处理结束
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
				mailTitle = this.getDesc() + "完成,没有需要导入的内容数据，详见邮件正文!!";// XX内容导入完成，没有需要导入的内容数据，详见邮件正文!!
			}
			else if (totalSuccessCount > 0 && totalFailureCount == 0)
			{
				mailTitle = this.getDesc() + "成功,成功" + totalSuccessCount
						+ "条，失败0条，详见邮件正文!";// XX内容导入成功，成功Y条，失败0条，详见邮件正文!
			}
			else
			{
				mailTitle = this.getDesc() + "完成,成功" + totalSuccessCount
						+ "条，失败" + totalFailureCount + "条，详见邮件正文!";// XX内容导入完成，成功Y条，失败Z条，详见邮件正文!
			}
			
			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<h4>处理结果：</h4>");
			sb.append("本次" + this.getDesc() + "任务总共处理<b>");
			sb.append(totalCount);
			sb.append("</b>条。<p>成功处理<b>");
			sb.append(totalSuccessCount);
			sb.append("</b>条。");
			if (totalSuccessCount != 0)
			{
				sb.append("<br>具体为：成功新增");
				sb.append(this.getSuccessAdd());
				sb.append("条，成功更新");
				sb.append(this.getSuccessUpdate());
				sb.append("条，成功删除");
				sb.append(this.getSuccessDelete());
				sb.append("条");
			}
			sb.append("<p>失败处理<b>");
			sb.append(totalFailureCount);
			sb.append("</b>条。");
			if (totalFailureCount != 0)
			{
				sb.append("<br>具体为：失败校验");
				sb.append(this.getFailureCheck());
				sb.append("条，失败处理");
				sb.append(this.getFailureProcess());
				sb.append("条。");
			}
		}
		else
		{
			if (errorCode == DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED)
			{
				mailTitle = this.getDesc() + "失败，没有取到正确的数据文件，详见邮件正文!";// XX内容导入失败，没有取到正确的数据文件，详见邮件正文!
			}
			else
			{
				mailTitle = this.getDesc() + "失败，详见邮件正文!";
			}
			
			sb.append("开始时间：");
			sb.append(PublicUtil
					.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append(",结束时间：");
			sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
			sb.append("。<p>失败原因：<br>");
			sb.append(reason);
			
		}
		LOG.info(sb.toString());
		Mail.sendMail(mailTitle, sb.toString(), this.getMailTo());
	}
}
