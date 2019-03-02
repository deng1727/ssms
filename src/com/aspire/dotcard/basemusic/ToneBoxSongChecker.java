package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basemusic.dao.ToneBoxDAO;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class ToneBoxSongChecker
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ToneBoxSongChecker.class);
private static  ToneBoxSongChecker  instance = new ToneBoxSongChecker();

private ToneBoxSongChecker(){
	
}
	public static ToneBoxSongChecker getInstance(){
		return instance;
	}
	
	public int checkDateRecord(List record) throws Exception
	{
		if(record.size() != 4){
			logger.error("字段数不对，应该有4个字段，目前该行有"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// id
		String tmp = (String) record.get(1);
		String id = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证铃音盒歌曲字段格式，ID=" + id);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + tmp + ",id验证错误，该字段是必填字段，且不超过64个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// boxId
		tmp = (String) record.get(0);
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + id
					+ ",boxId验证错误，该字段是必填字段，且长度不超过64个字符错误！boxId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		if(!new ToneBoxDAO().existToneBoxID(tmp))
		{
			logger.error("id=" + id
					+ ",boxId在铃音盒表中不存在！boxId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// sortId
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 10, false))
		{

			logger.error("id=" + id
					+ ",sortId验证错误，其长度不超过10个字符错误！sortId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
//		 operType
//		tmp = (String) record.get(3);
//		if (!this.checkFieldLength(tmp, 512, true))
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
