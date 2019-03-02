package com.aspire.dotcard.syncAndroid.cssp;

import com.aspire.common.threadtask.Task;

public class CSSPImportTask extends Task
{

	private DataDealer dl;
	private Bean data;

	public CSSPImportTask(DataDealer dl,Bean data){
		this.dl = dl;
		this.data=data;
	}
	public void task()
	{
		dl.insert(data);
		
	}

}
