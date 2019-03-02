package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ContentChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(ContentChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("开始验证游戏分类字段格式，id="+record.get(1));
			
		if(record.size()!=28)
		{
			logger.error("字段数不等于28,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("内容代码", tmp, 32, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("内容名称", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("内容简介", tmp, 1000, true)){
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
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("产品LOGO", tmp, 200, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(14);
		if(!this.checkFieldLength("内容类型", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("操作类型", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		

		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
