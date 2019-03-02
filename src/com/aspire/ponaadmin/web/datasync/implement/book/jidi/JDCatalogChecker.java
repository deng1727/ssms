package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;

public class JDCatalogChecker extends DataCheckerImp
{

	/**
	 * 日志引用
	 */
	private static JLogger logger = LoggerFactory.getLogger(JDCatalogChecker.class);

	public int checkDateRecord(DataRecord record) throws Exception
	{
		// Catalogid
		String tmp = (String) record.get(1);
		String Catalogid = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证图书分类字段格式，Catalogid=" + Catalogid);
		}
		
		int size=6;
		if(record.size()!=size)
		{
			logger.error("字段数不等于"+size);
			return DataSyncConstants.CHECK_FAILED;
		}
		if (!this.checkFieldLength(tmp, 20, true))
		{
			logger.error("Catalogid=" + tmp + ",Catalogid验证错误，该字段是必填字段，且不超过20个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// CatalogName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("Catalogid=" + Catalogid
					+ ",分类名称验证错误，该字段是必填字段，且长度不超过64个字符错误！CatalogName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// desc
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 256, false))
		{
			logger.error("Catalogid=" + Catalogid
					+ ",分类名称验证错误，长度不超过256个字符错误！desc=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Changetype
		tmp = (String) record.get(6);
		if (!this.checkFieldLength(tmp, 2, true))
		{
			logger.error("Catalogid=" + Catalogid
					+ ",变更类型验证出错，该字段是必填字段，且长度不超过2个字符！Changetype=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}

		return DataSyncConstants.CHECK_SUCCESSFUL;

	}

}
