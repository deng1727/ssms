package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class StopChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(StopChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("开始验证游戏暂停字段格式，id="+record.get(1));
			
		if(record.size()!=8)
		{
			logger.error("字段数不等于8,该条数据的字段数为："+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("业务代码/套餐包ID", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("内容代码", tmp, 12, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("是否暂停省测批开", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("被暂停省份的ID", tmp, 12, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}

		tmp=(String)record.get(6);
		if(!this.checkFieldLength("被暂停省份名称", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(8);
		if(!this.checkFieldLength("操作类型", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
