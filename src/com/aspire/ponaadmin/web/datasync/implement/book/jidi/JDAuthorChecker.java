package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDAuthorChecker extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(JDAuthorChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// AuthorId
		String tmp = (String) record.get(1);
		String authorId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证作者字段格式，AuthorId=" + authorId);
		}
		int size=4;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if (!this.checkFieldLength("AuthorId", tmp, 25, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		// AuthorName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength("AuthorName", tmp, 50, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		// AuthorDesc
		tmp = (String) record.get(3);
		if (!this.checkFieldLength("AuthorDesc", tmp, 1024, false))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
        // Changetype
        tmp = (String) record.get(4);
        if (!this.checkFieldLength("Changetype", tmp, 2, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
