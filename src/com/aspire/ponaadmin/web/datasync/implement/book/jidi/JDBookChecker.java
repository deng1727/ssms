package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class JDBookChecker extends DataCheckerImp
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDBookChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		int size=11;
		//bookId
		String tmp=(String)record.get(1);
		String bookId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证图书字段格式，bookId="+bookId);
		}
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 16, true))
		{
			logger.error("bookId="+tmp+",bookId验证错误，该字段是必填字段，且不超过16个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		//BookName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("bookId="+bookId+",BookName验证错误，该字段是必填字段，且长度不超过100个字符错误！BookName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//Desc
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 2048, false))
		{
			logger.error("bookId="+bookId+",Desc验证出错，该字段是必填字段，且长度不超过2048个字符！Desc="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//AuthorID
		tmp=(String)record.get(4);
		if(!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("bookId="+bookId+",AuthorID验证出错，该字段是必填字段，且长度不超过25个字符！AuthorID="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//CategoryId
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("bookId="+bookId+",CategoryId验证出错，该字段是必填字段，且长度不超过20个字符！CategoryId="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//InTime
		tmp=(String)record.get(6);
		if(!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("bookId="+bookId+",InTime验证出错，该字段是必填字段，且长度不超过14个字符！InTime="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//验证时间是否可以解析。
		Date date=PublicUtil.parseStringToDate(tmp, "yyyyMMddHHmmss");
		if(date==null)
		{
			logger.error("bookId="+bookId+",InTime验证出错，时间格式不对，无法解析时间！InTime="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//BOOKURL
		tmp=(String)record.get(7);
		if(!this.checkFieldLength(tmp, 255, true))
		{
			logger.error("bookId="+bookId+",BOOKURL长度不超过255个字符！BOOKURL="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//chargeType
		tmp=(String)record.get(8);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",chargeType长度不超过2个字符！chargeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		Fee  必填，当chargeType不为0时，必为0
		tmp=(String)record.get(9);
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("bookId="+bookId+",Fee长度不超过10个字符！Fee="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		IsFinish
		tmp=(String)record.get(10);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",IsFinish长度不超过2个字符！IsFinish="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		ChangeType
		tmp=(String)record.get(11);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",ChangeType长度不超过2个字符！ChangeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
