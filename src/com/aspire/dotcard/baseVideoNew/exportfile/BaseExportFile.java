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
	 * 当前任务对应的表名
	 */
	protected String tableName = "";
	
	/**
	 * 邮件标题信息
	 */
	protected String mailTitle = "";
	
	/**
	 * 文件存放的本地路径
	 */
	protected String localDir = "";
	
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
	 * 导入的压缩文件名
	 */
	protected String gzFileName = "";
	
	/**
	 * 是否有校验文件
	 */
	protected boolean hasVerf = true;
	
	/**
	 * 是否清空中间表
	 */
	protected boolean isDelMidTable = true;
	
	/**
	 * 文件解析编码
	 */
	protected String fileEncoding = "GBK";
	
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
	protected String verDataSpacers = "|";
	
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
	 * 写入失败邮件条数
	 */
	protected int failMailTextNum = 0;
	
	/**
	 * 是否还写入失败邮件
	 */
	protected boolean isMailText = true;
	
	/**
	 * 是否内容集节点和内容集
	 */
	protected boolean isCollect = false;
	
	/**
	 * 执行器
	 */
	private TaskRunner dataSynTaskRunner;
	
	/**
	 * 是否执行了内容数据导入
	 */
	protected boolean isImputDate = false;
	
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
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		localDir = BaseVideoNewConfig.LOCALDIR;
		getDateNum = BaseVideoNewConfig.GET_DATE_NUM;
		dataSpacers = getDataSpacers();
		verDataSpacers = BaseVideoNewConfig.verDataSpacers;
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
			// 加入邮件错误信息...............
			writeErrorToVerfMail(lineNumeber, lineText, "校验文件结构不对，大于或小于5个属性");
		}
		
		return vo;
	}
	
	/**
	 * 得到当前FTP连接对象
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
				if (BaseFileNewTools.isMatchFileName(fileName, tempFileName))
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
	 * 用于得到视频详情对比后文件列表
	 * 
	 * @return
	 */
	protected List<String> getDataFileListByVideoDetail() throws BOException
	{
		/*String fileNameNew = BaseFileNewTools.fileNameDataChange(fileName,
				getDateNum, setTime,isByHour,getTimeNum);
		String fileNameOld = BaseFileNewTools.fileNameDataChange(fileName,
				getDateNum + 1, setTime,isByHour,getTimeNum);
		
		// 获取当前文件与前一天的文件
		getDataFileList(fileNameNew);
		getDataFileList(fileNameOld);*/
		
		// 执行shell脚本，对比文件差异，得出最终文件列表
		return BaseFileNewTools.execShell(BaseFileNewTools.getShellFileDate(getDateNum, setTime));
	}
	
	/**
	 * 用于得到视频详情全量压缩文件并解压文件
	 * 
	 * @return
	 */
	protected void getAndUngzipDataFileByVideoDetail() throws BOException
	{
		String fileNameNew = BaseFileNewTools.fileNameDataChange(gzFileName,
				getDateNum, setTime,isByHour,getTimeNum);
		String fileNameOld = BaseFileNewTools.fileNameDataChange(gzFileName,
				getDateNum + 1, setTime,isByHour,getTimeNum);
		
		// 获取当前文件
		List<String> fileNameNews =  getDataFileList(fileNameNew);
		if(fileNameNews.size() == 0 ){
			throw new BOException("压缩文件为空,文件名称为："+fileNameNew,
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		try {
			//解压文件到指定目录
			BaseFileNewTools.ungzip(fileNameNews.get(0),localDir);
			
		} catch (Exception e) {
			throw new BOException("解压文件失败，文件名称为："+fileNameNews.get(0),e);
		}
		// 获取前一天的文件
		List<String> fileNameOlds = getDataFileList(fileNameOld);
		if(fileNameOlds.size() == 0 ){
			throw new BOException("压缩文件为空,文件名称为："+fileNameOld,
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		try {
			//解压文件到指定目录
			BaseFileNewTools.ungzip(fileNameOlds.get(0),localDir);
			
		} catch (Exception e) {
			
			throw new BOException("解压文件失败，文件名称为："+fileNameOlds.get(0),e);
		}
		
	}
	
	/**
	 * 导入校验数据文件
	 */
	protected void exportVerfFile() throws BOException
	{
        List<String> fileList;
		
		// 如果是视频详情导入操作，这里要特殊处理，
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
			
			// 转义模糊文件名中的日期, 得到文件列表
			fileList = getDataFileList(BaseFileNewTools
					.fileNameDataChange(verfFileName, getDateNum, setTime,isByHour,getTimeNum));
			
		}
		if (fileList.size() == 0)
		{
			// 加入邮件错误信息..............
			verfMailText.append("查找不到当前所要的校验文件！！！<br>");
			throw new BOException("校验文件为空",
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
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
					if (BaseVideoNewConfig.FILE_END.equals(lineText.trim()))
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
	 * 导入内容数据文件
	 */
	protected void exportDataFile() throws BOException
	{
		List<String> fileList;
		
		// 如果是视频详情导入操作，这里要特殊处理，得到对比文件结果。
		if (this instanceof ProgramExportFile)
		{
			fileList = getDataFileListByVideoDetail();
		}
		else
		{
			// 转义模糊文件名中的日期, 得到文件列表
			fileList = getDataFileList(BaseFileNewTools
					.fileNameDataChange(fileName, getDateNum, setTime,isByHour,getTimeNum));
		}
		
		// 判断文件长度是否为0
		if (BaseFileNewTools.isNullFile(fileList))
		{
			// 加入邮件错误信息..............
			syncMailText.append("此次同步的数据文件都为空，此次类型数据同步中止！！！");
			throw new BOException("此次同步的数据文件都为空，此次类型数据同步中止！！！",
					BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
		}
		
		// 是否清空中间表
		if (isDelMidTable)
		{
			// 清空中间表数据
			BaseVideoNewFileBO.getInstance().delMidTable(tableName);
		}
		if (this instanceof VideoExportFile)
		{//视频全量文件处理
			logger.info(" 获取全量视频物理文件同步时间："+BaseVideoNewConfig.GET_WEEK_NUM+"，当前时间："+BaseFileNewTools.getWeekOfDate(new Date()));
			getFullVideoFile();
		}
		
		BufferedReader reader = null;
		String lineText = null;
		int lineNumeber = 0;
		dataSynTaskRunner = new TaskRunner(BaseVideoNewConfig.taskRunnerNum,
				BaseVideoNewConfig.taskMaxReceivedNum);
		
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
	 * 用于得到全量视频物理文件，并将全量视频物理文件导入全量临时表
	 * 
	 * @return
	 */
	protected void getFullVideoFile()
	{
		List<String> fileList;
		// 获取全量视频物理文件
		try {
			if(BaseVideoNewConfig.GET_WEEK_NUM == BaseFileNewTools.getWeekOfDate(new Date())){
				fileList = getDataFileList(BaseFileNewTools
						.fileNameDataChange(gzFileName, getDateNum, setTime,isByHour,getTimeNum));
				// 判断文件长度是否为0
				if (BaseFileNewTools.isNullFile(fileList))
				{
					// 加入邮件错误信息..............
					syncMailText.append("此次同步的视频物理全量数据文件都为空，此次类型数据同步中止！！！");
					throw new BOException("此次同步的视频物理全量数据文件都为空，此次类型数据同步中止！！！",
							BaseVideoNewConfig.EXCEPTION_FILE_NOT_EXISTED);
				}
				//解压压缩文件
				BaseFileNewTools.ungzip(fileList.get(0),localDir);
				// 清空视频全量临时表数据
				BaseVideoNewFileBO.getInstance().delVideoFullTable(tableName);
				// 执行shell脚本，将全量视频物理文件导入临时表中
				BaseFileNewTools.execShellOfVideoFullImport(BaseFileNewTools.getShellFileDate(getDateNum, setTime));
				// 调用存储过程 用以执行视频全量临时表与中间表中数据转移
				BaseVideoNewFileBO.getInstance().syncVideoFullData();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 用于组装发送
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
			
			mailText.append("校验文件数据导入情况： <br>");
			mailText.append(verfMailText).append("<br>");
			for (int i = 0; i < verfDataList.size(); i++)
			{
				mailText
						.append(((VerfDataVO) verfDataList.get(i)).toMailText());
			}
			mailText.append("<br><br>");
			
			mailText.append("校验文件数据导入情况： <br>");
			mailText.append("总导入条数：").append(countNum).append("条").append(
			"<br>");
			if(isCollect){
				mailText.append("新增条数：").append(addNum).append("条").append(
						"<br>");
				mailText.append("修改条数：").append(modifyNum).append("条").append(
				"<br>");
				mailText.append("删除条数：").append(deleteNum).append("条").append(
				"<br>");
			}else{
				mailText.append("成功导入条数：").append(successAdd).append("条").append(
				"<br>");
			}
			mailText.append("失败处理条数：").append(failureProcess).append("条")
			.append("<br>");
			mailText.append("校验失败条数：").append(failureCheck).append("条").append(
			"<br>");
			mailText.append(syncMailText).append("<br>");
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
	 * 用于回收数据
	 */
	protected void clear()
	{
		verfDataList.clear();
		keyMap.clear();
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
		
		// 如果是视频详情全量导入操作，这里要特殊处理，下载压缩文件并解压到本地
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
		
		// 用于释放队列集合
		destroy();
		
		// 组装操作结果邮件
		getMailText();
		
		// 用于清空集合数据
		clear();
		
		if (isSendMail)
		{
			// 执行发邮件的功能
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
	protected abstract String checkData(String[] data,boolean flag);
	
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
	
	public void setExportTime(Calendar setTime)
	{
		this.setTime = setTime;
	}
}
