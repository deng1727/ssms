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
	 * 日志对象
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseExportXmlFile.class);

	/**
	 * 邮件标题信息
	 */
	protected String mailTitle = "";

	/**
	 * 文件存放的本地根路径
	 */
	protected String localDir = "";
	
	/**
	 * 文件存放目录
	 */
	protected String fileDir = "";

	/**
	 * 执行器
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * 是否执行了内容数据导入
	 */
	protected boolean isImputDate = false;

	/**
	 * 总个数
	 */
	protected int countNum = 0;
	
	/**
	 * 新增个数
	 */
	protected int addNum = 0;
	
	/**
	 * 修改个数
	 */
	protected int modifyNum = 0;
	
	/**
	 * 删除个数
	 */
	protected int deleteNum = 0;

	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String dataSpacers = "";
	
	/**
	 * 同步开始时间
	 */
	protected Date startDate = null;
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
	 * 邮件信息
	 */
	protected StringBuffer mailText = new StringBuffer();

	/**
	 * 同步文件总信息
	 */
	protected StringBuffer syncMailText = new StringBuffer();

	/**
	 * 数据列表
	 */
	protected static List<String> dataList = Collections.synchronizedList(new ArrayList<String>());

	/**
	 * 主键MAP
	 */
	protected static Map<String, String> keyMap = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init() {
		localDir = BaseVideoConfig.LOCALDIR;
		dataSpacers = getDataSpacers();
	}

	/**
	 * 返回字段分隔符
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "\\|";
	}
	
	public String execution(boolean isSendMail) {
		
		// 准备工作
		init();
		
		// 记录开始时间
		startDate = new Date();
		// 导入内容数据文件
		
		try {
			
			exportDataFile();
			
		} catch (BOException e) {
			logger.error(e);
		}
		
		// 用于释放队列集合
		destroy();

		// 组装操作结果邮件
		getMailText();

		// 用于清空集合数据
		clear();

		if (isSendMail) {
			// 执行发邮件的功能
			BaseVideoBO.getInstance().sendResultMail(mailTitle, mailText);
			return "";
		} else {
			return mailText.toString();
		}

	}

	/**
	 * 导入内容数据文件
	 */
	protected void exportDataFile() throws BOException {

		if (dataList.size() == 0)
		{
			// 加入邮件错误信息..............
			syncMailText.append("此次同步的数据为空，此次类型数据同步中止！！！");
			throw new BOException("此次同步的数据为空，此次类型数据同步中止！！！",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// 基地视频节目详情xml导入时先删除中间表
		if (this instanceof ProgramExportXMLFile)
		{
			// 清空中间表数据
			BaseVideoBO.getInstance().delMidTable();
		}
		
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum,
				BaseVideoConfig.taskMaxReceivedNum);

		try {
			
			// 如果存在，解析
			for (int i = 0; i < dataList.size(); i++) {
				String data = String.valueOf(dataList.get(i));

				if (logger.isDebugEnabled()) {
					logger.debug("开始处理数据内容：" + data);
				}

				// 如果文件为空
				if (data == null || "".equals(data)) {
					// 加入错误邮件信息..............
					syncMailText.append("当前的数据内容为空");
					continue;
				}

				// 执行了内容数据导入
				isImputDate = true;
				
				lineNumeber++;
				countNum++;
				SynBaseVideoXmlTask task = new SynBaseVideoXmlTask(data, lineNumeber,
						dataSpacers, this);

				ReflectedTask refTask = new ReflectedTask(task, "sysDataByXml",
						null, null);
				dataSynTaskRunner.addTask(refTask);
				
			}
			//当总数才1个时，让线程等待2秒，以确保线程运行完成 
			if(countNum == 1)
				dataSynTaskRunner.waitRunningTask();
			dataSynTaskRunner.waitToFinished();
			dataSynTaskRunner.stop();
		} catch (Exception e) {
			throw new BOException(e, BaseVideoConfig.EXCEPTION_INNER_ERR);
		}
	}

	/**
	 * 用于得到数据文件
	 * 
	 * @param fileName
	 *            模糊文件名
	 * @return
	 */
	protected String getXMLDataFile(String fileName) throws BOException {
		String filePath = null;

		FTPClient ftp = getFTPClient();
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("匹配文件名开始：" + fileName);
			}

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + fileDir + File.separator
					+ fileName;
			// 判断FTP是否存在文件
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
	 * 得到当前FTP连接对象
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
			// 进入下载路径
			if (!"".equals(ftpDir))
			{
				ftp.chdir(ftpDir);
			}
		}
		catch (Exception e)
		{
			mailText.append("FTP配置出错，FTP链接出错！！！<br>");
			logger.error(e);
		}
		return ftp;
	}
	
	/**
	 * 写入错误信息
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
			syncMailText.append("由于数据过大，以下不提供详情...").append("<br>");
			isMailText = false;
		}
		else
		{
			syncMailText.append("数据内容第").append(lineNum).append(
					"条，此数据有错原因为：").append(reasonText).append("<br>");
			failMailTextNum++;
		}
	}
	
	/**
	 * 用于写入预删除数据
	 */
	protected void destroy()
	{
		// 如果执行过内容数据导入，则执行下面逻辑
		if (isImputDate)
		{
			Iterator<String> ite = keyMap.keySet().iterator();
			
			while (ite.hasNext())
			{
				String key = ite.next();
				if (!"0".equals(keyMap.get(key)))
				{
					// 此条记录为没被使用过的，可以列入删除行列
					BaseVideoBO.getInstance().delDataByKey(
							getDelSqlCode(), getDelKey(key));
					setDeleteNumAdd();
				}
			}
		}
	}
	
	/**
	 * 用于回收数据
	 */
	protected void clear()
	{
		if(dataList!=null) dataList.clear();
		keyMap.clear();
	}
	
	/**
	 * 用于组装发送的邮件内容
	 * 
	 */
	protected void getMailText()
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
			
			mailText.append("内容数据输出文件导入情况： <br>");
			mailText.append("总条数：").append(countNum).append("条").append(
			"<br>");
			mailText.append("成功处理条数：").append(successAdd).append("条").append(
			"<br>");
			
			mailText.append("失败处理条数：").append(failureProcess).append("条")
			.append("<br>");
			mailText.append("校验失败条数：").append(failureCheck).append("条").append(
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
	 * 实现类实现校验字段的正确性
	 * 
	 * @param data
	 *            数据信息
	 * @param flag
	 *            标志位
	 * @return 返回正确/返回错误信息
	 */
	protected abstract String checkData(Object object,String[] data);
	
	/**
	 * 实现类实现解析xml文件内容到对象
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回
	 */
	protected abstract Object getObject(Element root);
	
	/**
	 * 实现类实现将对象转换成sql参数的Object[][]
	 * 
	 * @param object
	 *            数据信息
	 * @return 返回
	 */
	protected abstract Object getObjectParas(Object object);
	
	/**
	 * 实现类实现返回执行预删除当前数据的key值
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回当前数据的key值
	 */
	protected String[] getDelKey(String key)
	{
		return key.split("\\|", -1);
	}
	
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
	 * 实现类实现返回更新当前业务的sql语句
	 * 
	 * @return sql语句
	 */
	protected abstract String getDelSqlCode();

}
