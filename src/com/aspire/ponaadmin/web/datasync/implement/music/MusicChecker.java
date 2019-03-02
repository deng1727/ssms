package com.aspire.ponaadmin.web.datasync.implement.music;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class MusicChecker extends DataCheckerImp
{
	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(MusicChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// bookId
		String tmp = (String) record.get(1);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����ֶθ�ʽ��bookId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 200, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����200���ַ�����musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Music_style
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 80, false))
		{

			logger.error("musicId=" + musicId
					+ ",Music_style��֤�����䳤�Ȳ�����80���ַ�����Music_style=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Album
		tmp = (String) record.get(5);
		if (!this.checkFieldLength(tmp, 80, false))
		{

			logger.error("musicId=" + musicId + ",Album��֤�����䳤�Ȳ�����80���ַ�����Album=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// ChangeType
		tmp = (String) record.get(6);
		if (!this.checkFieldLength(tmp, 2, false))
		{

			logger.error("musicId=" + musicId
					+ ",ChangeType��֤�����䳤�Ȳ�����2���ַ�����ChangeType=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
