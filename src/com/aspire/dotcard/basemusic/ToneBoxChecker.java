package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class ToneBoxChecker
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ToneBoxChecker.class);
private static  ToneBoxChecker  instance = new ToneBoxChecker();

private ToneBoxChecker(){
	
}
	public static ToneBoxChecker getInstance(){
		return instance;
	}
	
	public int checkDateRecord(List record) throws Exception
	{
		if(record.size() != 6){
			logger.error("字段数不对，应该有6个字段，目前该行有"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// id
		String tmp = (String) record.get(0);
		String id = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证铃音盒字段格式，ID=" + id);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + tmp + ",id验证错误，该字段是必填字段，且不超过64个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// Name
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + id
					+ ",Name验证错误，该字段是必填字段，且长度不超过3个字符错误！Name=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// description
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 4000, false))
		{

			logger.error("id=" + id
					+ ",description验证错误，其长度不超过4000个字符错误！description=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// charge
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 12, true))
		{

			logger.error("id=" + id
					+ ",charge验证错误，其长度不超过12个字符错误！charge=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// valid
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 14, true))
		{

			logger.error("id=" + id
					+ ",valid验证错误，其长度不超过14个字符错误！valid=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 operType
//		tmp = (String) record.get(5);
//		if (!this.checkFieldLength(tmp, 2, true))
//		{
//
//			logger.error("id=" + id
//					+ ",operType验证错误，其长度不超过2个字符错误！operType=" + tmp);
//			return DataSyncConstants.CHECK_FAILED;
//		}

		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	
	/**
	 * 判断该字符是否超出maxLength的长度。
	 * @param field 要验证的字段内容
	 * @param maxLength 允许的最大长度
	 * @param must 是否是必填字段，如果为true，需要验证该字段是否为空（""）
	 * @return
	 */
	protected  boolean checkFieldLength(String field,int maxLength,boolean must)
	{
		if(field==null)
		{
			return false;
		}
		if(StringTool.lengthOfHZ(field)>maxLength)
		{
			return false;
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			return false;
		  }
		}
		return true;
		
	}
}
