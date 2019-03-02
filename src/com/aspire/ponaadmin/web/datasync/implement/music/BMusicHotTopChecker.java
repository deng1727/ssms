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
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicHotTopChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤���ְ��ֶθ�ʽ��musicId=" + record.get(2));
		}
        int size=4;
        if(record.size()!=size)
        {
            logger.error("�ֶ���������"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		// musicId
		String tmp = (String) record.get(2);
		String musicId = tmp;
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",����Id��֤���������ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// ListName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("musicId=" + musicId
					+ ",��������֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����listName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// MusicName
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",MusicName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����MusicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// sortNumber
		tmp = (String) record.get(4);
		if (!this.checkIntegerField("sortNumber",tmp, 6, true))
		{

			logger.error("musicId=" + musicId
					+ ",sortNumber��֤�����䳤�ȳ���6���ַ�����sortNumber=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	

	public void init(DataSyncConfig config) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
