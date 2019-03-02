package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.syncAndroid.dc.MessageSendDAO;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 游戏业务同步
 * @author aiyan
 *
 */
public class DataSyncTaskForService extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected ServiceDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForService.class);
	private int maxProcessThread=10;
	
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.dataDealer = (ServiceDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config); 
		dataReader.init(config);
		dataDealer.init(config);
		dataChecker.init(config);
		
		maxProcessThread=Integer.parseInt(config.get("task.maxProcessThread"));
	}

	public void doTask()throws BOException
	{
		long start = System.currentTimeMillis();
		LOG.debug("业务线程开始运行。。。");
		String[] filenameList = ftp.process();	
		//String[] filenameList ={"E:/var/i_g-gameService_20130422_000001.txt"};
		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}
		TaskRunner runner = new TaskRunner(maxProcessThread);
		String lineText = null;
		BufferedReader reader = null;
		//记录当文件当前处理的行数。
		int lineNumeber = 0;
		List failureChecked=new ArrayList();
		//是否已经执行同步。为防止基地提供空文件，删除现有的数据。
		boolean isSync = false;
		try
		{
			//首先需要在同步前需要保证当前同步任务的正确性。
			dataDealer.prepareData();
			
			for(int i =0;i<filenameList.length;i++){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						filenameList[i]), this.fileEncoding));
				
				//读取游戏列表文件。
				LOG.info("开始处理文件：" + filenameList[0]);
				while ((lineText = reader.readLine()) != null)
				{
					lineNumeber++;//记录文件的行数。
					if(lineNumeber==1)//删除第一行bom字符
					{
						lineText=PublicUtil.delStringWithBOM(lineText);
					}
					if (LOG.isDebugEnabled())
					{
						LOG.debug("开始处理第" + lineNumeber + "行数据。");
					}
					if("".equals(lineText.trim()))//对于空行的记录不处理。
					{
						LOG.debug("该行是空行，不处理。lineNumeber="+lineNumeber);
						continue;
					}
					DataRecord dr = dataReader.readDataRecord(lineText);
					if (dataChecker != null)
					{
						int checkResult = dataChecker.checkDateRecord(dr);
	
						if (checkResult == DataSyncConstants.CHECK_FAILED)
						{				
							failureChecked.add(dr.get(1));
							LOG.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
							this.addStatisticCount(checkResult);
							this.addCheckFiledRow(lineNumeber);
							continue;
						}
						
					}
					//执行了同步
					isSync = true;
					// 构造异步任务
					Task gameTask = new ReflectedGameTask(this, dataDealer, dr, lineNumeber);
					// 将任务加到运行器中
					runner.addTask(gameTask);
	
				}
			}
			
			// 等待任务处理完成。
			runner.waitToFinished();
			LOG.info("##############所有的task执行完毕###############");
			
			Thread.sleep(2000);//休眠2秒钟。等待一下日志写入。
			
			if(isSync){
				// 新增更新完了，还需要删除下线的游戏。
				if (LOG.isDebugEnabled())
				{
					LOG.debug("开始删除已经下线的游戏");
				}
				
				dataDealer.delOldData(this,failureChecked);
				
				//把包的适配关系算出来放到包表（t_game_pkg）中的fulldevicename,fulldeviceid
				putGamePkgUA();
				
//				create index T_GAME_PKG_INDEXALL
//				on T_GAME_PKG(fulldeviceid) indextype is ctxsys.context 
//			--	parameters('lexer chinese_lexer') 
//		    -- parallel(degree 4)
				
				//execute immediate 'create index '||seq_INDEX_V_DEVICENAME_APP||' 
				//on '||tableName||'(full_deviceName) indextype is ctxsys.context 
				//parameters(''lexer chinese_lexer'') parallel(degree 4) ';
				//对t_game_pkg中的fulldeviceid建立全文检索。
				buildIndexContext();
				
//				//包下的商品数量算出来。 这个大胖又说不用了，故注销了。
//				putGamePkgRefNum();
				
				//add by aiyan 2012-11-1
				//业务同步完成后把t_game_statistcs里面的统计信息（DOWNLOADNUM，STARTTIME）写到t_r_gcontent（MODAYORDERTIMES,LUPDATE）
				putGameStatistcs();
				
				// 需要刷新物化视图
				LOG.info("开始刷新v_service表");
				GameSyncDAO.getInstance().refreshVService();
				LOG.info("刷新v_service表完成！");
				//MessageSendDAO.getInstance().changeStatus(-5, -1);//这步是没有必要的。remove by aiyan2013-05-20
			}
			
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
		}
		dataDealer.clearDirtyData();
		
		LOG.info("游戏内容同步完成,hehe.mygame_content:"+(System.currentTimeMillis()-start));
	}

//	private void putGamePkgRefNum() {
//		// TODO Auto-generated method stub
//		try {
//			GameSyncDAO.getInstance().putGamePkgRefNum();
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			LOG.error("计算包下的商品数量时出错了！",e);
//		}
//	}

	private void putGameStatistcs() {
		// TODO Auto-generated method stub
		List l= GameSyncDAO.getInstance().queryGameStatistcs();
		for(int i=0;i<l.size();i++){
			GameSyncDAO.getInstance().updateGameStatistcs((GameStatistcs)l.get(i));
		}
		
	}

	//这里的UA就是将包表（t_game_pkg）中的fulldevicename,fulldeviceid算出来。
	private void putGamePkgUA() {
		// TODO Auto-generated method stub
		List pkgid = GameSyncDAO.getInstance().queryGamePkgid();
		for(int i=0;i<pkgid.size();i++){
			GameSyncDAO.getInstance().updateGamePkgUA((String)pkgid.get(i));
		}
		
	}
	
	
	//exec ctx_ddl.sync_index('T_GAME_PKG_INDEXALL')  可以用这个方式重建全文索引的，没有弄成。所以搞了个   drop  & create
	private void buildIndexContext() {
		// TODO Auto-generated method stub
		try {
		    String sql1 = "drop index T_GAME_PKG_INDEXALL";
			DB.getInstance().execute(sql1,null);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("drop index T_GAME_PKG_INDEXALL出错！",e);
		}
		try {
			String sql2="create index T_GAME_PKG_INDEXALL on T_GAME_PKG(fulldeviceid) indextype is ctxsys.context";
			DB.getInstance().execute(sql2,null);
			LOG.info("T_GAME_PKG_INDEXALL成功执行完成！");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("create index T_GAME_PKG_INDEXALL出错！",e);
		}
		
	
				
			
		
	}
	
	


}
