package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class StatistcsChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(StatistcsChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("开始验证字段格式，id="+record.get(1));
			
		if(record.size()!=10)
		{
			logger.error("字段数不等于10,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		//id
		String tmp=(String)record.get(1);
        
		if(!this.checkFieldLength("业务代码", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//2 name
		tmp=(String)record.get(9);
		if(!this.checkFieldLength("上线时间", tmp, 14, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
