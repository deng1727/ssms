package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDCategoryChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDCategoryChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		//categoryId
		String tmp=(String)record.get(1);
		String categoryId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证图书分类字段格式，categoryId="+categoryId);
		}
		int size=3;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("categoryId="+tmp+",categoryId验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		//CategoryName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("categoryId="+categoryId+",分类名称验证错误，该字段是必填字段，且长度不超过100个字符错误！CategoryName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//Changetype
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("categoryId="+categoryId+",变更类型验证出错，该字段是必填字段，且长度不超过2个字符！Changetype="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
