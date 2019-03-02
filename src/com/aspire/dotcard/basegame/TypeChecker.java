package com.aspire.dotcard.basegame;


import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class TypeChecker extends DataCheckerImp
{
	private static JLogger logger = LoggerFactory.getLogger(TypeChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		//logger.info("��ʼ��֤��Ϸ�����ֶθ�ʽ��id="+record.get(1));
			
		if(record.size()!=2)
		{
			logger.error("�ֶ���������2,�������ݵ��ֶ���Ϊ��"+record.size());
			return DataSyncConstants.CHECK_FAILED;
		}
		
		//id
		String tmp=(String)record.get(1);
        
		if(!this.checkFieldLength("��Ϸ�����ʶ", tmp, 12, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		//2 name
		tmp=(String)record.get(2);
		if(!this.checkFieldLength("��Ϸ��������", tmp, 40, true))
		{
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
		
	}



}
