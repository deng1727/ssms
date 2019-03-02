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
	 * ��־����
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
			logger.debug("��ʼ��֤HTC���������ݸ�ʽ��APCode=" + APCode);
		}
		if (record.size() != 4 )
		{
			logger.error("�ֶ���������4");
			return DataSyncConstants.CHECK_FAILED;
		}
		
		if (!this.checkFieldLength(tmp, 6, true))
		{
			logger.error("APCode=" + tmp + ",APCode��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����6���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// APPID
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("APCode=" + APCode
					+ ",APPID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����20���ַ�����APPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// MMAPPID
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 12, true))
		{
			logger.error("APCode=" + APCode
					+ ",MMAPPID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12���ַ���MMAPPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// У�������Ƿ���Ч
		if (!contentIdSet.contains(tmp))
		{
			logger.error("��ǰcontentID���������ݱ�HTC���͵������У�MMAPPID=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// DownCount
		tmp = (String) record.get(4);
		if (!this.checkIntegerField("DownCount", tmp, 10, false))
		{
			logger.error("APCode=" + APCode
					+ ",DownCount��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����10��DownCount=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	public void init(DataSyncConfig config) throws Exception
	{
		
	}
	
}
