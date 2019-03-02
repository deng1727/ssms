package com.aspire.dotcard.basegame;

import com.aspire.common.threadtask.Task;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;

public class ReflectedGameTask extends Task
{
	private int lineNumber;
	private DataDealer dl;
	private DataRecord dr;
	private DataSyncTask gameTask;
	public ReflectedGameTask(DataSyncTask gameTask,DataDealer dl,DataRecord dr,int lineNumber)
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
