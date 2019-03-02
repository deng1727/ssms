package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class StopChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(StopChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("��ʼ��֤��Ϸ��ͣ�ֶθ�ʽ��id="+record.get(1));
			
		if(record.size()!=8)
		{
			logger.error("�ֶ���������8,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("ҵ�����/�ײͰ�ID", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("���ݴ���", tmp, 12, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("�Ƿ���ͣʡ������", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(5);
		if(!this.checkFieldLength("����ͣʡ�ݵ�ID", tmp, 12, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}

		tmp=(String)record.get(6);
		if(!this.checkFieldLength("����ͣʡ������", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(8);
		if(!this.checkFieldLength("��������", tmp, 2, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
