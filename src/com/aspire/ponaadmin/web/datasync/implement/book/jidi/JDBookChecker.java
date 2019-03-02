package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class JDBookChecker extends DataCheckerImp
{
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(JDBookChecker.class) ;


	public int checkDateRecord(DataRecord record) throws Exception
	{
		int size=11;
		//bookId
		String tmp=(String)record.get(1);
		String bookId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤ͼ���ֶθ�ʽ��bookId="+bookId);
		}
		if(record.size()!=size)
		{
			logger.error("�ֶ���������"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(!this.checkFieldLength(tmp, 16, true))
		{
			logger.error("bookId="+tmp+",bookId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����16���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		//BookName
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("bookId="+bookId+",BookName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����BookName="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//Desc
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 2048, false))
		{
			logger.error("bookId="+bookId+",Desc��֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����2048���ַ���Desc="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//AuthorID
		tmp=(String)record.get(4);
		if(!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("bookId="+bookId+",AuthorID��֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����25���ַ���AuthorID="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//CategoryId
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("bookId="+bookId+",CategoryId��֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����20���ַ���CategoryId="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//InTime
		tmp=(String)record.get(6);
		if(!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("bookId="+bookId+",InTime��֤�������ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����14���ַ���InTime="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//��֤ʱ���Ƿ���Խ�����
		Date date=PublicUtil.parseStringToDate(tmp, "yyyyMMddHHmmss");
		if(date==null)
		{
			logger.error("bookId="+bookId+",InTime��֤����ʱ���ʽ���ԣ��޷�����ʱ�䣡InTime="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//BOOKURL
		tmp=(String)record.get(7);
		if(!this.checkFieldLength(tmp, 255, true))
		{
			logger.error("bookId="+bookId+",BOOKURL���Ȳ�����255���ַ���BOOKURL="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		//chargeType
		tmp=(String)record.get(8);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",chargeType���Ȳ�����2���ַ���chargeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		Fee  �����chargeType��Ϊ0ʱ����Ϊ0
		tmp=(String)record.get(9);
		if(!this.checkFieldLength(tmp, 10, true))
		{
			logger.error("bookId="+bookId+",Fee���Ȳ�����10���ַ���Fee="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		IsFinish
		tmp=(String)record.get(10);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",IsFinish���Ȳ�����2���ַ���IsFinish="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		ChangeType
		tmp=(String)record.get(11);
		if(!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("bookId="+bookId+",ChangeType���Ȳ�����2���ַ���ChangeType="+tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
