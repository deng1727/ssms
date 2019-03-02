package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDTopListChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDTopListChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		
		//recommendId
		String tmp=(String)record.get(1);
		String recommendId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤ͼ���ֶθ�ʽ��recommendId="+recommendId);
		}
		int size=5;
		if(record.size()!=size)
		{
			logger.error("�ֶ���������"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("recommendId="+tmp+",recommendId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		//recommendName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId="+recommendId+",recommendName��֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����60���ַ���recommendName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//BookId
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("recommendId="+recommendId+",BookId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ���BookId="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//sortNumber
		tmp=(String)record.get(4);
		if(!this.checkIntegerField("sortNumber",tmp, 6, true))
		{
			logger.error("recommendId="+recommendId+",BookId��֤���󣬸��ֶ��Ǳ����ֶΣ����ֻ����6λ������sortNumber="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//ChangeType
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("recommendId="+recommendId+",ChangeType���Ȳ�����2���ַ���ChangeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
