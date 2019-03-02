/**
 * <p>
 * ����Ƶ�� ֧��ƽ̨���ݼ�����
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 7, 2009
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
public class ComicPlatFormChecker extends DataCheckerImp {

	
	
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ComicChecker.class) ;

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp#checkDateRecord(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	public int checkDateRecord(DataRecord record) throws Exception {
		// TODO Auto-generated method stub
//		pkgid
		String tmp=(String)record.get(1);
		String platformId=tmp;
		if(logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤����Ƶ��֧��ƽ̨�ֶθ�ʽ��platformid="+platformId);
		}
		if(!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("platformId="+tmp+",platformId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����20���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}else{			
			String platForm = ComicPlatFormDAO.getInstance().getPlatFormByID(platformId);
			if(null == platForm  || "".equals(platForm)){
				//����101��102,200,300,400���ڣ�����������
				logger.error("platformId="+tmp+",platformId��֤���󣬸��ֶβ�����֪������ȡֵ��Χ��");
				return DataSyncConstants.CHECK_FAILED;
			}
		}

		return DataSyncConstants.CHECK_SUCCESSFUL;
	}

}
