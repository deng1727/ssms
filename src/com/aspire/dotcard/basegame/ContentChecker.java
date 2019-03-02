package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ContentChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(ContentChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("��ʼ��֤��Ϸ�����ֶθ�ʽ��id="+record.get(1));
			
		if(record.size()!=28)
		{
			logger.error("�ֶ���������28,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("���ݴ���", tmp, 32, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("��������", tmp, 400, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("���ݼ��", tmp, 1000, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("CP����", tmp, 16, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(6);
		if(!this.checkFieldLength("CP����", tmp, 200, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("��ƷLOGO", tmp, 200, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(14);
		if(!this.checkFieldLength("��������", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("��������", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		

		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
