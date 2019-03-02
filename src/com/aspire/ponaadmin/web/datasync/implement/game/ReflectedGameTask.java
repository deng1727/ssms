package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.threadtask.Task;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
/**
 * 多线程处理游戏的辅助任务类。统计每个游戏执行结果。
 * @author zhangwei
 *
 */
public class ReflectedGameTask extends Task
{
	private int lineNumber;
	private GameDealer dl;
	private DataRecord dr;
	private DataSyncTaskForGame gameTask;
	public ReflectedGameTask(DataSyncTaskForGame gameTask,GameDealer dl,DataRecord dr,int lineNumber)
	{
		this.dl=dl;
		this.dr=dr;
		this.lineNumber=lineNumber;
		this.gameTask=gameTask;
	}
	public void task()
	{
		//为了在日志中更好的定位问题，把行数保存到处理类中去，
		dr.put(dr.size()+1, new Integer(lineNumber));
		int flag = dl.dealDataRecrod(dr);
		 if(flag != DataSyncConstants.SUCCESS_ADD &&
				 flag != DataSyncConstants.SUCCESS_UPDATE &&
				 flag != DataSyncConstants.SUCCESS_DEL&&
				 flag != DataSyncConstants.CHECK_FAILED ){
			 
			 gameTask.addDealFiledRow(lineNumber);
		 }
		gameTask.addStatisticCount(flag);
		
	}

}
