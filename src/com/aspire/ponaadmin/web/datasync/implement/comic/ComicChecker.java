/**
 * <p>
 * 验证动漫数据合法性的BO类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 4, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

/**
 * @author dongke
 *
 */
public class ComicChecker extends DataCheckerImp {

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp#checkDateRecord(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ComicChecker.class) ;

	
	
	public int checkDateRecord(DataRecord record) throws Exception {
		
		
		//pkgid
		String tmp=(String)record.get(1);
		String recommendId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证动漫推荐字段格式，recommendId="+recommendId);
		}
		
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("ComicID="+tmp+",ComicID验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		//ComicTitle
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("ComicTitle="+tmp+",ComicTitle验证错误，该字段是必填字段，且不超过60个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ComicDesc
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 1000, true))
		{
			logger.error("ComicDesc="+tmp+",ComicDesc验证错误，该字段是必填字段，且不超过1000个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
//		Author
		tmp=(String)record.get(4);
		if(!this.checkFieldLength(tmp, 200, false))
		{
			logger.error("Author="+tmp+",Author验证错误，该字段是可选字段，且不超过200个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ComicCat
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 30, false))
		{
			logger.error("ComicCat="+tmp+",ComicCat验证错误，该字段是可选字段，且不超过30个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ContentUrl
		tmp=(String)record.get(6);
		if(!this.checkFieldLength(tmp, 250, true))
		{
			logger.error("ContentUrl="+tmp+",ContentUrl验证错误，该字段是必填字段，且不超过250个字符");
			return DataSyncConstants.CHECK_FAILED;
		}	
//		InvalidTime
		tmp=(String)record.get(7);
		if(!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("InvalidTime="+tmp+",InvalidTime验证错误，该字段是必填字段，且不超过14个字符");
			return DataSyncConstants.CHECK_FAILED;
		}	
//		ChangeType
		tmp=(String)record.get(8);
		if(!this.checkFieldLength(tmp, 1, true))
		{
			logger.error("ChangeType="+tmp+",ChangeType验证错误，该字段是必填字段，且不超过1个字符");
			return DataSyncConstants.CHECK_FAILED;
		}	
		
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
