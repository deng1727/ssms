/**
 * <p>
 * ��֤�������ݺϷ��Ե�BO��
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 4, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

/**
 * @author dongke
 *
 */
public class ComicChecker extends DataCheckerImp {

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp#checkDateRecord(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ComicChecker.class) ;

	
	
	public int checkDateRecord(DataRecord record) throws Exception {
		
		
		//pkgid
		String tmp=(String)record.get(1);
		String recommendId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����Ƽ��ֶθ�ʽ��recommendId="+recommendId);
		}
		
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("ComicID="+tmp+",ComicID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		//ComicTitle
		tmp=(String)record.get(2);
		if(!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("ComicTitle="+tmp+",ComicTitle��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ComicDesc
		tmp=(String)record.get(3);
		if(!this.checkFieldLength(tmp, 1000, true))
		{
			logger.error("ComicDesc="+tmp+",ComicDesc��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����1000���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
//		Author
		tmp=(String)record.get(4);
		if(!this.checkFieldLength(tmp, 200, false))
		{
			logger.error("Author="+tmp+",Author��֤���󣬸��ֶ��ǿ�ѡ�ֶΣ��Ҳ�����200���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ComicCat
		tmp=(String)record.get(5);
		if(!this.checkFieldLength(tmp, 30, false))
		{
			logger.error("ComicCat="+tmp+",ComicCat��֤���󣬸��ֶ��ǿ�ѡ�ֶΣ��Ҳ�����30���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
//		ContentUrl
		tmp=(String)record.get(6);
		if(!this.checkFieldLength(tmp, 250, true))
		{
			logger.error("ContentUrl="+tmp+",ContentUrl��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����250���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}	
//		InvalidTime
		tmp=(String)record.get(7);
		if(!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("InvalidTime="+tmp+",InvalidTime��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����14���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}	
//		ChangeType
		tmp=(String)record.get(8);
		if(!this.checkFieldLength(tmp, 1, true))
		{
			logger.error("ChangeType="+tmp+",ChangeType��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����1���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}	
		
		
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
