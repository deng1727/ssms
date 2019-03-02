package com.aspire.dotcard.syncAndroid.cssp.free;

import java.util.Queue;

import com.aspire.common.threadtask.Task;
import com.aspire.dotcard.syncAndroid.cssp.Bean;
import com.aspire.dotcard.syncAndroid.cssp.DataDealer;

public class FreeReportImportTask extends Task
{

	private DataDealer dl;
	private  Queue<Bean> data;

	public FreeReportImportTask(DataDealer dl, Queue<Bean> data){
		this.dl = dl;
		this.data=data;
	}
	public void task()
	{
		dl.insert(data);
		
	}

}
