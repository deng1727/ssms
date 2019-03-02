package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDCategoryChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDCategoryChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		//categoryId
		String tmp=(String)record.get(1);
		String categoryId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤ͼ������ֶθ�ʽ��categoryId="+categoryId);
		}
		int size=3;
		if(record.size()!=size)
		{
			logger.error("�ֶ���������"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("categoryId="+tmp+",categoryId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		//CategoryName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("categoryId="+categoryId+",����������֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����CategoryName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//Changetype
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("categoryId="+categoryId+",���������֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����2���ַ���Changetype="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
