package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class VideoChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(VideoChecker.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//pkgid
		String tmp=(String)record.get(1);
		String pkgId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ�ֶθ�ʽ��pkgId="+pkgId);
		}
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("pkgid="+tmp+",pkgid��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����10���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		//pkgName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("pkgid="+pkgId+",pkgName��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����100���ַ�!pkgName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//      fee
        tmp=(String)record.get(3);
       // if(!this.checkFieldLength(tmp, 11, true))
        if(!this.checkIntegerField("fee",tmp, 11, true))
        {
            logger.error("pkgid="+pkgId+",fee��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����11���ַ�!fee="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
		
	}

}
