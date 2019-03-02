package com.aspire.ponaadmin.web.datasync.implement.music;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class MusicChecker extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(MusicChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// bookId
		String tmp = (String) record.get(1);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐字段格式，bookId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId验证错误，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 200, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName验证错误，该字段是必填字段，且长度不超过200个字符错误！musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer验证错误，该字段是必填字段，且长度不超过100个字符错误！singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Music_style
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 80, false))
		{

			logger.error("musicId=" + musicId
					+ ",Music_style验证错误，其长度不超过80个字符错误！Music_style=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Album
		tmp = (String) record.get(5);
		if (!this.checkFieldLength(tmp, 80, false))
		{

			logger.error("musicId=" + musicId + ",Album验证错误，其长度不超过80个字符错误！Album=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// ChangeType
		tmp = (String) record.get(6);
		if (!this.checkFieldLength(tmp, 2, false))
		{

			logger.error("musicId=" + musicId
					+ ",ChangeType验证错误，其长度不超过2个字符错误！ChangeType=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
