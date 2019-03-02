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
	 * 数据获取接口的参数
	 */
	protected List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 当前任务对应的表名
	 */
	protected String tableName = "";
	
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
	 * 取文件的日期，0：为当天，1：为前一天，2：为前二天
	 */
	protected int getDateNum = 0;
	
	/**
     * 取文件的时间，0：为当小时，1：为前小时，2：为前两小时
     */
    protected int getTimeNum = 0;
    
    protected boolean isByHour=false;
	
	/**
	 * 手动设置调整导出日期
	 */
	protected Calendar setTime = null;
	
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
	protected String fileEncoding = "UTF-8";
	
	/**
	 * 校验文件名
	 */
	protected String verfFileName = "";
	
	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String dataSpacers = "";
	
	/**
	 * 数据文件格式中定义的间隔符
	 */
	protected String verDataSpacers = "###";
	
	/**
	 * 同步开始时间
	 */
	protected Date startDate = null;
	
	/**
     * 是否要全量清表
     */
    protected boolean isDelTable = true;
	
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
	 * 失败处理的个数
	 */
	protected int failureProcess = 0;
	
	/**
	 * 校验失败的个数
	 */
	protected int failureCheck = 0;
	/**
	 * 路径为空
	 */
	protected int pathIsNotExit = 0;
	
	/**
	 * 写入失败邮件条数
	 */
	protected int failMailTextNum = 0;
	
	protected int apiRequest = 0;
	/**
	 * 认证失败
	 */
	protected int failAuth = 0;
	/**
	 * 系统异常
	 * 
	 */
	protected int systemException = 0;
	/**
	 * 参数格式错误
	 */
	protected int parameterFormatError = 0;
	
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
		localDir = BaseVideoConfig.LOCALDIR;
		getDateNum = BaseVideoConfig.GET_DATE_NUM;
		dataSpacers = getDataSpacers();
		verDataSpacers = BaseVideoConfig.verDataSpacers;
	}
	
	/**
	 * 返回字段分隔符
	 * 
	 * @return
	 */
	public String getDataSpacers()
	{
		return "###";
	}
	
	/**
     *  执行体
     *  @param isSendMail 是否发送邮件
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
		
		// 用于释放队列集合
		destroy();
		
		// 组装操作结果邮件
		getMailText();
		
		// 用于清空集合数据
		clear();
		
		if (isSendMail)
		{
			// 执行发邮件的功能
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
	 * 导入校验数据文件
	 */
	protected void exportVerfFile() throws BOException
	{
		// 转义模糊文件名中的日期, 得到文件列表
        List<String> fileList = getDataFileList(BaseFileTools
				.fileNameDataChange(verfFileName, getDateNum, setTime,isByHour,getTimeNum));
		if (fileList.size() == 0)
		{
			// 加入邮件错误信息..............
			verfMailText.append("查找不到当前所要的校验文件！！！<br>");
			throw new BOException("校验文件为空",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
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
				
				if (logger.isDebugEnabled())
				{
					logger.debug("开始处理校验文件：" + tempFileName);
				}
				
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
					if (BaseVideoConfig.FILE_END.equals(lineText.trim()))
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
	 * 导入内容数据文件
	 */
	protected void exportDataFile() throws BOException
	{
		if (this instanceof ProgramByHourExportFile)
		{
			//视频节目增量时如果时间为每天的第一个时间点就取前一天的数据
			String[] hoursConf =BaseVideoConfig.STARTTIME_HOURS.split("\\|");
			Calendar date = Calendar.getInstance();
			String hours = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
			if(hoursConf[0].equals(hours)){
				getDateNum = 1;
				isByHour = false;
			}
		}
		
		// 转义模糊文件名中的日期, 得到文件列表
		List<String> fileList = getDataFileList(BaseFileTools
					.fileNameDataChange(fileName, getDateNum, setTime,isByHour,getTimeNum));
		// 判断文件长度是否为0
		if (BaseFileTools.isNullFile(fileList))
		{
			// 加入邮件错误信息..............
			syncMailText.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
			throw new BOException("此次同步的数据文件都为空，此次类型数据同步中止！！！",
					BaseVideoConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// 基地视频产品包促销计费数据特殊处理
		if (this instanceof PkgSalesExportFile)
		{
			// 清空产品包促销计费数据表数据
			BaseVideoBO.getInstance().delTable(tableName);
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoConfig.taskRunnerNum,
				BaseVideoConfig.taskMaxReceivedNum);
		
		try
		{
			// 如果存在，解析
			for (int i = 0; i < fileList.size(); i++)
			{
				String tempFileName = String.valueOf(fileList.get(i));
				
				if (logger.isDebugEnabled())
				{
					logger.debug("开始处理内容文件：" + tempFileName);
				}
				
				File file = new File(tempFileName);
				
				// 如果文件为空
				if (file.length() == 0)
				{
					// 加入错误邮件信息..............
					syncMailText.append("当前的数据文件内容为空，fileName="
							+ tempFileName);
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
					
					SynBaseVideoTask task = new SynBaseVideoTask(lineText,
							tempFileName, lineNumeber, dataSpacers, this);
					
					ReflectedTask refTask = new ReflectedTask(task,
							"sysDataByFile", null, null);
					dataSynTaskRunner.addTask(refTask);
				}
			}
			//当总数才1个时，让线程等待2秒，以确保线程运行完成 
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
	 * 用于得到数据文件列表
	 * 
	 * @param fileName
	 *            模糊文件名
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
				logger.debug("匹配文件名开始：" + fileName);
			}

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + fileDir + File.separator + fileName;
			//判断FTP是否存在文件
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// 得到本地路径
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
	 * 解析校验文件信息（统一处理方式，因为校验文件格式一样）
	 * 
	 * @param lineText
	 *            当前行信息
	 * @param lineNumeber
	 *            第几行 便于记录错误信息
	 * @return 校验文件数据对象
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
			// 加入邮件错误信息...............
			writeErrorToVerfMail(lineNumeber, lineText, "校验文件结构不对，大于或小于4个属性");
		}
		
		return vo;
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
	protected void writeErrorToVerfMail(int lineNum, String dataText,
			String reasonText)
	{
		verfMailText.append("校验文件：").append(" 第").append(lineNum).append("行: ")
				.append(dataText).append("。 此数据有错原因为：").append(reasonText)
				.append("<br>");
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
			syncMailText.append("数据内容文件中第").append(lineNum).append(
					"行，此数据有错原因为：").append(reasonText).append("<br>");
			failMailTextNum++;
		}
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
			
			if(this.apiRequest == 1){		
				mailText.append("内容数据获取接口系统异常：").append(systemException).append("次").append(
						"<br>");
				mailText.append("内容数据获取接口认证失败：").append(failAuth).append("次").append(
						"<br>");
				mailText.append("内容数据获取接口参数格式错误：").append(parameterFormatError).append("次").append(
						"<br>");
				mailText.append("内容数据获取接口路径为空：").append(this.pathIsNotExit).append("次").append(
						"<br>");
			}
			
			mailText.append("校验文件数据导入情况： <br>");
			mailText.append(verfMailText).append("<br>");
			for (int i = 0; i < verfDataList.size(); i++)
			{
				mailText
						.append(((VerfDataVO) verfDataList.get(i)).toMailText());
			}
			mailText.append("<br><br>");
			
			mailText.append("内容数据输出文件导入情况： <br>");
			mailText.append("总导入条数：").append(countNum).append("条").append(
			"<br>");
			mailText.append("成功导入条数：").append(successAdd).append("条").append(
			"<br>");
			mailText.append("新增条数：").append(addNum).append("条").append(
			"<br>");
	        mailText.append("修改条数：").append(modifyNum).append("条").append(
	        "<br>");
	        mailText.append("删除条数：").append(deleteNum).append("条").append(
	        "<br>");
			mailText.append("失败处理条数：").append(failureProcess).append("条")
			.append("<br>");
			mailText.append("校验失败条数：").append(failureCheck).append("条").append(
			"<br>");
			mailText.append(syncMailText).append("<br>");
			// 基地视频热点主题货架更新
		/*	if (this instanceof HotcontentExportFile || this instanceof HotcontentApiExportFile)
			{
				// 根据热点主题列表，调用存储过程更新热点主题货架
				mailText.append(BaseVideoBO.getInstance().updateHotcontentCategoryMap());
			}*/
			mailText.append("<br><br>");
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
	 * 实现类实现返回执行当前数据的key值
	 * 
	 * @param data
	 *            数据信息
	 * @return 返回当前数据的key值
	 */
	protected abstract String getKey(String[] data);
	
	/**
	 * 实现类实现校验字段的正确性
	 * 
	 * @param data
	 *            数据信息
	 * @param flag
	 *            标志位
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
	
	public void setExportTime(Calendar setTime) {
		this.setTime = setTime;
	}
	
	/**
	 * 数据api接口调用
	 * 
	 * <p>
	 * 1.接口调用方超时时间设置5分钟。
	 * </p>
	 * <p>
	 * 2.请求流控：1次/每分钟/每接入方
	 * </p>
	 * <p>
	 * 调用参数
	 * </p>
	 * <p>
	 * u： 调用系统用户名
	 * </p>
	 * <p>
	 * t：01普通节目 02 业务产品和产品促销计费 03 计费数据 04热点主题列表
	 * </p>
	 * <p>
	 * s： 记录起始记录行数，默认为0
	 * </p>
	 * <p>
	 * l：分页大小，最大为5000，默认为1000，超过5000后，系统强制设置为5000
	 * </p>
	 * <p>
	 * contentType：一级分类代码 1000 电影 1001 电视剧 1002 纪实 1003 体育 1004 新闻 1005 综艺 1006
	 * 娱乐 1007 动漫 1008 生活 1009 旅游 1010 原创 1011 教育 500020 直播 500060 悦听-有声小说
	 * 500067 悦听-评书 500072 悦听-电台 500078 悦听-热点资讯 500100 悦听-儿童 500106 悦听-娱乐 500111
	 * 悦听-都市白领 500213 渠道推广 500323 音频 500405 军事 500468 健康 500424 搞笑 500377 法制
	 * 500320 财经 500422 时尚
	 * </p>
	 * <p>
	 * startDate: 数据开始时间 日期格式：YYYYMMDDHH24MISS
	 * </p>
	 * <p>
	 * endDate:数据结束始时间 日期格式：YYYYMMDDHH24MISS
	 * </p>
	 * 
	 * @throws BOException
	 */
	protected void requestExportDataFile() throws BOException {
		long t1 = 0;
		/**
		 * 获取数据接口请求的数据
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
			// 得到启动的小时和分钟
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
				logger.error("api请求数据失败：", e1);
				continue;
			}

			/**
			 * 成功获取返回的数据
			 */
			if (jsonObject != null && !"".equals(jsonObject)) {
				// 成功
				if ("000000".equals((jsonObject.get("returnCode").toString()
						.trim()))) {
					if(jsonObject.get("pth") == null || "".equals(jsonObject.get("pth").toString())){
						pathIsNotExit ++;
						continue;
					}
					String path = this.getDataFile(jsonObject);
					this.operationData(path);
					// 认证失败
				} else if ("200001".equals((jsonObject.get("returnCode")
						.toString().trim()))) {
					failAuth++;
					// 参数格式错误
				} else if ("200002".equals((jsonObject.get("returnCode")
						.toString().trim()))) {
					parameterFormatError++;
					// 系统异常
				} else {
					systemException++;
				}
			}
		}
	}
	
	/**
	 * 用于得到数据文件
	 * 
	 * @param jsonObject
	 *      api请求返回的数据
	 * @return
	 * @throws BOException
	 */
	protected String getDataFile(JSONObject jsonObject) throws BOException {
		FTPClient ftp = getFTPClient();
		try {
			/**
			 * ftp下载远程数据到本地
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("匹配文件名开始：" + jsonObject.get("pth"));
			}
			logger.info("remoteFilePath: " + jsonObject.get("pth"));

			String remoteFilePath = BaseVideoConfig.FTPPATH + File.separator + jsonObject.get("pth");
			// 判断FTP是否存在文件
			remoteFilePath = remoteFilePath.replace('\\', '/');
			// 得到本地路径
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
	 * 处理数据
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
			// 如果存在，解析
			File file = new File(path);

			// 如果文件为空
			if (file.length() == 0) {
				// 加入错误邮件信息..............
				syncMailText.append("当前的数据文件内容为空，fileName=" + path);
				return;
			}

			// 执行了内容数据导入
			isImputDate = true;

			// 读文件
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
			// 当总数才1个时，让线程等待2秒，以确保线程运行完成
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
