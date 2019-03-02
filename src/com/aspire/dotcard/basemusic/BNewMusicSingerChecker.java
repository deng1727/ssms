package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;
import com.aspire.ponaadmin.web.util.StringTool;

public class BNewMusicSingerChecker
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BNewMusicSingerChecker.class);
private static  BNewMusicSingerChecker  instance = new BNewMusicSingerChecker();

private BNewMusicSingerChecker(){
	
}
	public static BNewMusicSingerChecker getInstance(){
		return instance;
	}
	
	public int checkDateRecord(List record) throws Exception
	{
		if(record.size() != 7){
			logger.error("字段数不对，应该有7个字段，目前该行有"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// singerId
		String tmp = (String) record.get(0);
		String singerId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证新音乐歌手字段格式，singerId=" + singerId);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("singerId=" + tmp + ",singerId验证错误，该字段是必填字段，且不超过64个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// singerN
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 3, false))
		{
			logger.error("singerId=" + singerId
					+ ",singerN歌手首字母验证错误，该字段是必填字段，且长度不超过3个字符错误！singerN=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singername
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("singerId=" + singerId
					+ ",singername验证错误，该字段是必填字段，且长度不超过512个字符错误！singername=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singerdesc
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 1024, false))
		{

			logger.error("singerId=" + singerId
					+ ",SINGERDESC验证错误，其长度不超过1024个字符错误！SINGERDESC=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// singerpicurl
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 512, false))
		{

			logger.error("singerId=" + singerId
					+ ",singerpicurl验证错误，其长度不超过512个字符错误！singerpicurl=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 singertype
		tmp = (String) record.get(5);
		if (!this.checkFieldLength(tmp, 512, false))
		{

			logger.error("singerId=" + singerId
					+ ",singertype验证错误，其长度不超过512个字符错误！singertype=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 optype
		tmp = (String) record.get(6);
		if (!this.checkFieldLength(tmp, 2, true))
		{

			logger.error("singerId=" + singerId
					+ ",optype验证错误，其长度不超过2个字符错误！optype=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
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
