package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class ServiceChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(ContentChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("��ʼ��֤��Ϸҵ���ֶθ�ʽ��id="+record.get(18));
			
		if(record.size()!=20)
		{
			logger.error("�ֶ���������20,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		String tmp=(String)record.get(1);
		if(!this.checkFieldLength("ҵ�����", tmp, 16, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("���ݴ���", tmp, 32, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		
		tmp=(String)record.get(3);
		if(!this.checkFieldLength("��������", tmp, 400, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(4);
		if(!this.checkFieldLength("ҵ������", tmp, 400, true)){
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
		tmp=(String)record.get(7);
		if(!this.checkFieldLength("ҵ������", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(9);
		if(!this.checkFieldLength("֧����ʽ", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		
		tmp=(String)record.get(10);
		if(!this.checkFieldLength("ԭ���ʷ�", tmp, 12, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		tmp=(String)record.get(11);
		if(!this.checkFieldLength("�ּ��ʷ�", tmp, 12, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(12);
		if(!this.checkFieldLength("�ʷ�����", tmp, 14, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(13);
		if(!this.checkFieldLength("�Ʒ�����", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
		
		tmp=(String)record.get(15);
		if(!this.checkFieldLength("�׷�����", tmp, 5, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		
//		tmp=(String)record.get(16);
//		if(!this.checkFieldLength("�ۿ�����", tmp, 1, true)){
//			return DataSyncConstants.CHECK_FAILED;	
//		}
		tmp=(String)record.get(17);
		if(!this.checkFieldLength("��������", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(18);
		if(!this.checkFieldLength("�ֳɱ���", tmp, 10, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		tmp=(String)record.get(19);
		if(!this.checkFieldLength("���뷽ʽ", tmp, 2, true)){
			return DataSyncConstants.CHECK_FAILED;	
		}
		

		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
