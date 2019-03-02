package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class StatistcsChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(StatistcsChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("��ʼ��֤�ֶθ�ʽ��id="+record.get(1));
			
		if(record.size()!=10)
		{
			logger.error("�ֶ���������10,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		//id
		String tmp=(String)record.get(1);
        
		if(!this.checkFieldLength("ҵ�����", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//2 name
		tmp=(String)record.get(9);
		if(!this.checkFieldLength("����ʱ��", tmp, 14, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
