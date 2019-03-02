package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ProgramChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ProgramChecker.class) ;

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// programId
		String tmp=(String)record.get(1);
		String programId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ��Ŀ�ֶθ�ʽ����ĿID="+programId);
		}
        
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("��ĿID="+tmp+",programId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����10���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// ProgramNAME
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 128, true))
		{
			logger.error("��ĿID="+programId+",��Ŀ����������֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����128���ַ�!ProgramNAME="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
        
		// ContentID
        tmp=(String)record.get(3);
        if(!this.checkFieldLength(tmp, 21, true))
        {
            logger.error("��ĿID="+programId+",��ƵID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����11���ַ�!ContentID="+tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
		
	}

}
