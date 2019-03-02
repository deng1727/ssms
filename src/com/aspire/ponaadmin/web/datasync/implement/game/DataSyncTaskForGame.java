package com.aspire.ponaadmin.web.datasync.implement.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.common.threadtask.TaskTokenCenter;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;

/**
 * game游戏文件的处理task，由于游戏同步比较复杂，所以特地写了一个专为游戏同步的task类。
 * 该类应用处理类直接游戏处理类（gameDealer）。
 * @author zhangwei
 *
 */
public class DataSyncTaskForGame extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	private GameDealer gameDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForGame.class);
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.gameDealer = (GameDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config);
		dataReader.init(config);
		gameDealer.init(config);
		dataChecker.init(config);
		//初始化游戏配置信息。
		GameConfig.init(config);

	}

	/**
	 * 游戏进行全量同步，同时需要上传图片文件到资源服务器上。
	 */
	public void doTask()throws BOException
	{
		String[] filenameList = null;
		filenameList = ftp.process();
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}

		String lineText = null;
		BufferedReader reader = null;
		// 记录当文件当前处理的行数。
		int lineNumeber = 0;
		try
		{
			// 首先需要在同步前需要保证当前同步任务的正确性。
			gameDealer.prepareData();

			// 首先解压缩文件到当前目录。
			String gameFileRootPath = DataSyncTools.ungzip(filenameList[0]);

			// String todayString = PublicUtil.getCurDateTime("yyyyMMdd");
			/*
			 * String serviceinfoPath = gameFileRootPath + File.separator +
			 * "SERVICEINFO" + todayString + ".000";
			 */
			String serviceinfoPath = getServiceInfoFileName(gameFileRootPath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(
					serviceinfoPath), this.fileEncoding));

			// 设置游戏文件根目录的路径。
			gameDealer.setGameFileRootPath(gameFileRootPath);
			// 读取游戏列表文件。
			LOG.info("开始处理游戏信息文件：" + serviceinfoPath);
			// 多个线程处理游戏的文件。
			//TaskTokenCenter.getInstance().initTokens();
			TaskRunner runner = new TaskRunner(GameConfig.maxProcessThread);

			List failureChecked=new ArrayList();
			while ((lineText = reader.readLine()) != null)
			{
				lineNumeber++;// 记录文件的行数。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始解析第" + lineNumeber + "行数据。");
				}
				if("".equals(lineText.trim()))//对于空行的记录不处理。
				{
					LOG.debug("该行是空行，不处理。lineNumeber="+lineNumeber);
					continue;
				}
				DataRecord dr = dataReader.readDataRecord(lineText);
				int checkResult = dataChecker.checkDateRecord(dr);
				if (checkResult == DataSyncConstants.CHECK_FAILED)
				{
					// 需要先确定该游戏主键。
					BaseGameKeyVO gameKey = new BaseGameKeyVO();
					gameKey.setIcpCode((String) dr.get(1));
					gameKey.setIcpServid((String) dr.get(3));
					gameKey.setStatus(6);
					failureChecked.add(gameKey);
					this.addStatisticCount(checkResult);//增加统计。
                    this.addCheckFiledRow(lineNumeber);
					continue;
				}
				// 构造异步任务
				Task gameTask = new ReflectedGameTask(this, gameDealer, dr, lineNumeber);
				// 将任务加到运行器中
				runner.addTask(gameTask);
			}
			// 等待任务处理完成。
			runner.waitToFinished();
			System.out.println("##############所有的task执行完毕###############");
			Thread.sleep(2000);//休眠2秒钟。等待一下日志写入。
			// 新增更新完了，还需要删除下线的游戏。
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始删除已经下线的游戏");
			}
			int result[]=gameDealer.deleteOldGame(failureChecked);
			this.addSuccessDelete(result[0]);
			this.addFailureProcess(result[1]);
			// 需要刷新物化视图
			GameSyncDAO.getInstance().refreshVService();

		} catch (Exception e)
		{
			throw new BOException(e, DataSyncConstants.EXCEPTION_INNER_ERR);
		} finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException e)
			{
				LOG.error(e);
			}
			// clear data 无论是否同步成功，数据还需要清理的。
			gameDealer.clearDirtyData();
		}
	}

	/**
	 * 增加统计信息。记录成功新增，更新，删除的统计信息。
	 * @param flag
	 *//*
	synchronized public void addStatisticCount(int flag)
	{
		if (flag == DataSyncConstants.SUCCESS_ADD)
		{
			this.successAdd++;
		}
		else if (flag == DataSyncConstants.SUCCESS_UPDATE)
		{
			this.successUpdate++;
		}
		else if (flag == DataSyncConstants.SUCCESS_DEL)
		{
			this.successDelete++;
		}else if(flag ==DataSyncConstants.FAILURE)
		{
			this.failureProcess++;
		}
	}*/
	/**
	 * 获取游戏列表文件。游戏列表文件名匹配取消时间的限制，既返回第一个匹配SERVICEINFO\d{8}.000的字符串
	 * 目的是为了以后手动同步的时候只需要更改tar包的文件名即可,不需要更改tar包里的内容。
	 * @param gameFileRootPath
	 * @return
	 * @throws BOException
	 */
	private String getServiceInfoFileName(String gameFileRootPath)throws BOException
	{
		File dir=new File(gameFileRootPath);
		String fileNames[]=dir.list();
		for(int i=0;i<fileNames.length;i++)
		{
			if(fileNames[i].matches("SERVICEINFO\\d{8}\\.000"))
			{
				return gameFileRootPath+File.separator+fileNames[i];
			}
		}
		throw new BOException("找不到基地游戏列表文件异常！");
	}

}
