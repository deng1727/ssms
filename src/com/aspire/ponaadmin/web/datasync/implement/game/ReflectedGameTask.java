package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.threadtask.Task;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
/**
 * ���̴߳�����Ϸ�ĸ��������ࡣͳ��ÿ����Ϸִ�н����
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
		//Ϊ������־�и��õĶ�λ���⣬���������浽��������ȥ��
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
