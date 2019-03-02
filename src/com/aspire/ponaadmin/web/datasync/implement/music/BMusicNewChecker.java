package com.aspire.ponaadmin.web.datasync.implement.music;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class BMusicNewChecker extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicNewChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
        int size=3;
		// Musicid
		String tmp = (String) record.get(1);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐字段格式，bookId=" + musicId);
		}
        if(record.size()!=size)
        {
            logger.error("字段数不等于"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId验证错误，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName验证错误，该字段是必填字段，且长度不超过100个字符错误！musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Sortnumber
		tmp = (String) record.get(3);
        if(!this.checkIntegerField("Sortnumber", tmp, 6, true))
        {
            return DataSyncConstants.CHECK_FAILED;
        }
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
}
