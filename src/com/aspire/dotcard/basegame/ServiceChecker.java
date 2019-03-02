package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ServiceChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(ContentChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("开始验证游戏业务字段格式，id="+record.get(18));
			
		if(record.size()!=20)
		{
			logger.error("字段数不等于20,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("业务代码", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("内容代码", tmp, 32, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("内容名称", tmp, 400, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("业务名称", tmp, 400, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("CP代码", tmp, 16, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(6);
		if(!this.checkFieldLength("CP名称", tmp, 200, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(7);
		if(!this.checkFieldLength("业务类型", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(9);
		if(!this.checkFieldLength("支付方式", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("原价资费", tmp, 12, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		tmp=(String)record.get(11);
		if(!this.checkFieldLength("现价资费", tmp, 12, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(12);
		if(!this.checkFieldLength("资费类型", tmp, 14, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(13);
		if(!this.checkFieldLength("计费类型", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		
		tmp=(String)record.get(15);
		if(!this.checkFieldLength("首发类型", tmp, 5, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
//		tmp=(String)record.get(16);
//		if(!this.checkFieldLength("折扣类型", tmp, 1, true)){
//			return DataSyncConstants.CHECK_FAILED;	
//		}
		tmp=(String)record.get(17);
		if(!this.checkFieldLength("操作类型", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("分成比例", tmp, 10, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(19);
		if(!this.checkFieldLength("接入方式", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		

		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
