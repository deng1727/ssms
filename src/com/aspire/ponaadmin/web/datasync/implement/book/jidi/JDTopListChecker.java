package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDTopListChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDTopListChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		
		//recommendId
		String tmp=(String)record.get(1);
		String recommendId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证图书字段格式，recommendId="+recommendId);
		}
		int size=5;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("recommendId="+tmp+",recommendId验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		//recommendName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId="+recommendId+",recommendName验证出错，该字段是必填字段，且长度不超过60个字符！recommendName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//BookId
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("recommendId="+recommendId+",BookId验证错误，该字段是必填字段，且不超过20个字符！BookId="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//sortNumber
		tmp=(String)record.get(4);
		if(!this.checkIntegerField("sortNumber",tmp, 6, true))
		{
			logger.error("recommendId="+recommendId+",BookId验证错误，该字段是必填字段，最多只能是6位整数！sortNumber="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//ChangeType
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("recommendId="+recommendId+",ChangeType长度不超过2个字符！ChangeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
