package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class BMusicHotTopChecker //extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicHotTopChecker.class);
	private static  BMusicHotTopChecker  instance = new BMusicHotTopChecker();
	private static String INTEGER="-?[0-9]{0,d}";
	private BMusicHotTopChecker(){
		
	}
		public static BMusicHotTopChecker getInstance(){
			return instance;
		}
	public int checkDateRecord(List record) throws Exception
	{
		
        //int size=4;
		int size=5;  // change by dongke 20120202
        if(record.size()!=size)
        {
            logger.error("record.size()="+record.size()+";字段数不等于"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
        if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐榜单字段格式，musicId=" + record.get(2));
		}
		// musicId
		String tmp = (String) record.get(1);
		String musicId = tmp;
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",歌曲Id验证出错。，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// ListName
		tmp = (String) record.get(0);
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("musicId=" + musicId
					+ ",榜单名称验证错误，该字段是必填字段，且长度不超过100个字符错误！listName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// MusicName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",MusicName验证错误，该字段是必填字段，且长度不超过100个字符错误！MusicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// sortNumber
		tmp = (String) record.get(3);
		if (!this.checkIntegerField("sortNumber",tmp, 6, true))
		{

			logger.error("musicId=" + musicId
					+ ",sortNumber验证错误，其长度超过6个字符错误！sortNumber=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
/*		 type
		01-随身听榜单，
		02-PC榜单，
		03-WWW榜单，
		04-会员榜单
*/
		tmp = (String) record.get(4);
	//	if (!this.checkFieldLength(tmp, 2, true))
		if (!this.checkFieldLength(tmp, 64, false))//2013-06-05
		{

			logger.error("musicId=" + musicId
					+ ",type验证错误，其长度超过64个字符错误！type=" + tmp);
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
	/**
	 * 检查该字段的数字类型
	 * @param field 待检查的字段
	 * @param maxLength 数字的最大长度
	 * @param must  是否必须的字段
	 * @return
	 */
	protected  boolean checkIntegerField(String fieldName,String field,int maxLength,boolean must)
	{
		if(field==null)
		{
			return false;
		}
		if(!field.matches(INTEGER.replaceFirst("d", String.valueOf(maxLength))))
		{
			logger.error(fieldName+"="+field+",该字段验证失败。原因：长度超过了"+maxLength+"个数字！");
			return false;
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			logger.error(fieldName+"="+field+",该字段验证失败。原因：该字段是必填字段，不允许为空");
			return false;
		  }
		}
		return true;
		
	}
	

}
