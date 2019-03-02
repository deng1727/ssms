package com.aspire.ponaadmin.web.datasync.implement.htc;

import com.aspire.common.threadtask.Task;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.DataSyncTask;

public class DataHTCDownloadTask extends Task
{
	private int lineNumber;
	private DataDealer dl;
	private DataRecord dr;
	private DataSyncTask htcTask;
	
	public DataHTCDownloadTask(DataSyncTask htcTask, DataDealer dl, DataRecord dr,
			int lineNumber)
	{
		this.dl = dl;
		this.dr = dr;
		this.lineNumber = lineNumber;
		this.htcTask = htcTask;
	}
	
	public void task()
	{
		int flag = DataSyncConstants.FAILURE;
		try
		{
			flag = dl.dealDataRecrod(dr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		if (flag != DataSyncConstants.SUCCESS_ADD
				&& flag != DataSyncConstants.SUCCESS_UPDATE
				&& flag != DataSyncConstants.SUCCESS_DEL
				&& flag != DataSyncConstants.CHECK_FAILED)
		{
			
			htcTask.addDealFiledRow(lineNumber);
		}
		htcTask.addStatisticCount(flag);
	}
	
}
