package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDCommendCheck extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDCommendCheck.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//recommendId
		String tmp=(String)record.get(1);
		String recommendId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证图书推荐字段格式，recommendId="+recommendId);
		}	
		
		int size=4;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!checkFieldLength("recommendId", tmp, 20, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//CategoryId
		tmp=(String)record.get(2);
		if(!checkFieldLength("CategoryId", tmp, 20, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//BookId
		tmp=(String)record.get(3);
		if(!checkFieldLength("BookId", tmp, 20, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//Changetype
		tmp=(String)record.get(4);
		if(!checkFieldLength("Changetype", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
