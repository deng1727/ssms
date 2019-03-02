package com.aspire.ponaadmin.web.datasync.implement.music;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class BMusicHotTopChecker extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicHotTopChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐榜单字段格式，musicId=" + record.get(2));
		}
        int size=4;
        if(record.size()!=size)
        {
            logger.error("字段数不等于"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		// musicId
		String tmp = (String) record.get(2);
		String musicId = tmp;
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",歌曲Id验证出错。，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// ListName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("musicId=" + musicId
					+ ",榜单名称验证错误，该字段是必填字段，且长度不超过100个字符错误！listName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// MusicName
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",MusicName验证错误，该字段是必填字段，且长度不超过100个字符错误！MusicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// sortNumber
		tmp = (String) record.get(4);
		if (!this.checkIntegerField("sortNumber",tmp, 6, true))
		{

			logger.error("musicId=" + musicId
					+ ",sortNumber验证错误，其长度超过6个字符错误！sortNumber=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	

	public void init(DataSyncConfig config) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
