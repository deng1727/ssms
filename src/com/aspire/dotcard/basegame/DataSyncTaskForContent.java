package com.aspire.dotcard.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.threadtask.Task;
import com.aspire.common.threadtask.TaskRunner;
import com.aspire.dotcard.basecomic.dao.BaseComicDAO;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataReader;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.datasync.FtpProcessor;
import com.aspire.ponaadmin.web.datasync.implement.game.GameConfig;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 游戏内容同步
 * @author aiyan
 *
 */
public class DataSyncTaskForContent extends DataSyncTask
{
	private FtpProcessor ftp;
	private DataReader dataReader;
	private DataChecker dataChecker;
	protected DataDealer dataDealer;
	private static JLogger LOG = LoggerFactory.getLogger(DataSyncTaskForAll.class);
	public void init(DataSyncConfig config) throws Exception
	{
		super.init(config);
		this.ftp = (FtpProcessor) Class.forName(config.get("task.ftp-class"))
				.newInstance();
		this.dataReader = (DataReader) Class
				.forName(config.get("task.data-reader-class")).newInstance();
		this.dataDealer = (DataDealer) Class
				.forName(config.get("task.data-dealer-class")).newInstance();
		this.dataChecker = (DataChecker) Class.forName(
				config.get("task.data-checker-class")).newInstance();
		ftp.init(config);
		dataReader.init(config);
		dataDealer.init(config);
		dataChecker.init(config);
		//初始化游戏配置信息。
		GameConfig.init(config);
	}
	
	public void doTask()throws BOException
	{
		long start = System.currentTimeMillis();
//
//		e:/temp/Gamelist20121016.tar.gz
//
//		e:/temp/Gamelist20121016

		String[] filenameList = ftp.process();
		//String[] filenameList ={"E:/var/Gamelist20130418.tar.gz"};
		//String[] filenameList ={"E:/var/i_g-gameService_20130422_000001.txt"};

		if (filenameList.length == 0)
		{
			throw new BOException("没有找到本次任务的文件异常",DataSyncConstants.EXCEPTION_FILE_NOT_EXISTED);
		}

		String lineText = null;
		BufferedReader reader = null;
		//记录当文件当前处理的行数。
		int lineNumeber = 0;
		try
		{
			// 首先解压缩文件到当前目录。
			String gameFileRootPath = DataSyncTools.ungzip(filenameList[0]);
			//String gameFileRootPath = "e:/temp/Gamelist20121016";
			// 设置游戏文件根目录的路径。
			((ContentDataDealer)dataDealer).setGameFileRootPath(gameFileRootPath);
			TaskRunner runner = new TaskRunner(GameConfig.maxProcessThread);
			
			//首先需要在同步前需要保证当前同步任务的正确性。
			dataDealer.prepareData();
			
			String[] serviceinfoPath = getServiceInfoFileName(gameFileRootPath);
			for(int i=0;i<serviceinfoPath.length;i++){
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(
						gameFileRootPath+File.separator+serviceinfoPath[i]), this.fileEncoding));
				LOG.info("开始处理文件：" + serviceinfoPath[i]);
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
							LOG.error("第" + lineNumeber + "行数据检查失败，忽略该数据。");
							this.addStatisticCount(checkResult);
							this.addCheckFiledRow(lineNumeber);
							continue;
						}
						
					}
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
			// 新增更新完了，还需要删除下线的游戏。
			if (LOG.isDebugEnabled())
			{
				LOG.debug("开始删除上次同步有，而本次无的游戏！");
			}
			//删除存在于上次同步而不存在于本次同步的数据。（因为数据是全量的）
			dataDealer.delOldData();
			
			//生成或修改游戏包信息。
			genGamePkg();
			
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
	
	/**
	 * 获取游戏内容列表文件。
	 */
	private String[] getServiceInfoFileName(String gameFileRootPath)throws BOException
	{
		
		File dir=new File(gameFileRootPath);
		String fileNames[]=dir.list(new FilenameFilter(){

			public boolean accept(File arg0, String arg1) {
				// TODO Auto-generated method stub
				return arg1.matches("i_g-game_\\d{8}_\\d{6}.txt");
			};
		
		});
		if(fileNames!=null&&fileNames.length>0){
			return fileNames;
		}
		
		throw new BOException("找不到基地游戏列表文件异常！");
	}
	
	private void genGamePkg(){
		String sqlCode="com.aspire.dotcard.basegame.ServiceDealer.prepareData.getPkgGameKeys";
		Set pkgGameKeys = null;
		try {
			pkgGameKeys = GameSyncDAO.getInstance().getAllKeyId(sqlCode);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			return;
		}
		// TODO Auto-generated method stub
		List pkgList = GameSyncDAO.getInstance().queryGameContent();
		for(int i=0;i<pkgList.size();i++){
			//updateGamePkg((GameContent)pkgList.get(i));
			dealPkgGame((GameContent)pkgList.get(i),pkgGameKeys);
		}
		
		delOldPKGGame(pkgGameKeys);
		
	}
	


	private void dealPkgGame(GameContent content,Set pkgGameKeys){
		try {
			//添加T_GAME_PKG表
			if(pkgGameKeys.contains(content.getContentCode())){
				GameSyncDAO.getInstance().updatePkgGame(content);
				pkgGameKeys.remove(content.getContentCode());//为删除做的准备工作
			}else{
				GameSyncDAO.getInstance().insertPkgGame(content);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("处理游戏包出错！",e);
		}
		
	}
	private void delOldPKGGame(Set pkgGameKeys){
		LOG.debug("开始清理游戏包！");
		if(pkgGameKeys==null||pkgGameKeys.size()==0){
			return;
		}
		Object[] arr = pkgGameKeys.toArray();
		int size = arr.length;
		int start=0;int end =0;
		String sql;
		try {
			sql = DB.getInstance().getSQLByCode("com.aspire.dotcard.basegame.delOldPKGGame");
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			LOG.error("com.aspire.dotcard.basegame.delOldPKGGame没有在sql.properties配置",e1);
			return;
		}
		do{
			end=end+100;
			if(end>size) end=size;
			
			StringBuffer sb = new StringBuffer();
			for(int i=start;i<end;i++){
				sb.append(",'").append(arr[i]).append("'");
			}
			if(sb.length()>0){
				try {
					String whereStr = " where pkgid in ("+sb.substring(1)+")";
					GameSyncDAO.getInstance().delOldData(sql+whereStr);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					LOG.error("游戏包删除旧数据出错！",e);
				}
			}
			
			start = end;
		}while(end<size);
		
	}

}
