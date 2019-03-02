/*
 * 
 */
package com.aspire.ponaadmin.web.datasync.implement.htc;

import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

/**
 * @author x_wangml
 * 
 */
public class DataHTCDownloadChecker extends DataCheckerImp implements
		DataChecker
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory
			.getLogger(DataHTCDownloadChecker.class);
	
	private Set<String> contentIdSet;
	
	public void initContentId() throws DAOException
	{
		contentIdSet = DataHTCDownloadDAO.getInstance().queryContentByHTC();
	}
	
	public int checkDateRecord(DataRecord record)
	{
		// APCode
		String tmp = (String) record.get(1);
		String APCode = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证HTC下载量数据格式，APCode=" + APCode);
		}
		if (record.size() != 4 )
		{
			logger.error("字段数不等于4");
			return DataSyncConstants.CHECK_FAILED;
		}
		
		if (!this.checkFieldLength(tmp, 6, true))
		{
			logger.error("APCode=" + tmp + ",APCode验证错误，该字段是必填字段，且不超过6个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// APPID
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("APCode=" + APCode
					+ ",APPID验证错误，该字段是必填字段，且长度不超过20个字符错误！APPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// MMAPPID
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 12, true))
		{
			logger.error("APCode=" + APCode
					+ ",MMAPPID验证出错，该字段是必填字段，长度不超过12个字符！MMAPPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// 校验内容是否有效
		if (!contentIdSet.contains(tmp))
		{
			logger.error("当前contentID不存在内容表HTC类型的数据中，MMAPPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// DownCount
		tmp = (String) record.get(4);
		if (!this.checkIntegerField("DownCount", tmp, 10, false))
		{
			logger.error("APCode=" + APCode
					+ ",DownCount验证出错，该字段是必填字段，长度不超过10！DownCount=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	public void init(DataSyncConfig config) throws Exception
	{
		
	}
	
}
