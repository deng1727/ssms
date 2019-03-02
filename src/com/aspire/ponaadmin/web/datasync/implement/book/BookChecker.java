package com.aspire.ponaadmin.web.datasync.implement.book;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class BookChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(BookChecker.class) ;


	public int checkDateRecord(DataRecord record)
	{
		//bookId
		String tmp=(String)record.get(1);
		String bookId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证图书字段格式，bookId="+bookId);
		}	
		int size=8;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("bookId="+tmp+",bookId验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		//bookTitle
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("bookId="+bookId+",bookTitle验证错误，该字段是必填字段，且长度不超过60个字符错误！bookTitle="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//author
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 200, false))
		{
			logger.error("bookId="+bookId+",author验证出错，长度不超过200个字符！author="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//bookCate
		tmp=(String)record.get(4);
		if(!this.checkFieldLength(tmp, 30, false))
		{
			logger.error("bookId="+bookId+",bookCate长度不超过30个字符！bookCate="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//contentUrl
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 200, true))
		{
			logger.error("bookId="+bookId+",contentUrl长度不超过30个字符！contentUrl="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//InvalidTime
		tmp=(String)record.get(6);
		if(!this.checkIntegerField("InvalidTime", tmp, 14, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//ChangeType
		tmp=(String)record.get(7);
		String changeType=tmp;
		if(!this.checkFieldLength(tmp, 1, true))
		{
			logger.error("bookId="+bookId+",ChangeType长度不超过1个字符！ChangeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//OLTime
		tmp=(String)record.get(8);
		if(!this.checkIntegerField("OLTime", tmp, 14, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//新增和更新必须提供
		if(changeType.equals("1")||changeType.equals("2"))
		{
			if(tmp.equals(""))
			{
				return DataSyncConstants.CHECK_FAILED;
			}
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

	public void init(DataSyncConfig config)
	{

	}

}
