/**
 * <p>
 * 动漫频道 支持平台数据检验类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 7, 2009
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
public class ComicPlatFormChecker extends DataCheckerImp {

	
	
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ComicChecker.class) ;

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp#checkDateRecord(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	public int checkDateRecord(DataRecord record) throws Exception {
		// TODO Auto-generated method stub
//		pkgid
		String tmp=(String)record.get(1);
		String platformId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("开始验证动漫频道支持平台字段格式，platformid="+platformId);
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("platformId="+tmp+",platformId验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}else{			
			String platForm = ComicPlatFormDAO.getInstance().getPlatFormByID(platformId);
			if(null == platForm  || "".equals(platForm)){
				//不在101，102,200,300,400以内，抛弃不插入
				logger.error("platformId="+tmp+",platformId验证错误，该字段不在已知的五种取值范围内");
				return DataSyncConstants.CHECK_FAILED;
			}
		}

		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
