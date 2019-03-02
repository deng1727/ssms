package com.aspire.ponaadmin.web.datasync.implement;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataChecker;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.music.MusicChecker;
import com.aspire.ponaadmin.web.util.StringTool;
/**
 * 检查类的默认实现。当不需要检查的时候，可是使用此检查类。
 * @author zhangwei
 *
 */
public class DataCheckerImp implements DataChecker
{

	private static final JLogger logger = LoggerFactory.getLogger(MusicChecker.class);
	/**
	 * 数据检查的默认实现。
	 */
	public  int  checkDateRecord(DataRecord record) throws Exception
	{
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	private static String INTEGER="-?[0-9]{0,d}";
	public void init(DataSyncConfig config) throws Exception
	{

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
	 * 简化验证步骤。验证失败打印日志。
	 * @param fieldName 该域的名称。用于日志记录
	 * @param field  域的值
	 * @param maxLength 最大允许长度。
	 * @param must 是否必须的
	 * @return 验证失败返回false，否则返回true
	 */
	protected  boolean checkFieldLength(String fieldName,String field,int maxLength,boolean must)
	{
		boolean result=true;
		if(field==null)
		{
			result= false;
		}
		if(StringTool.lengthOfHZ(field)>maxLength)
		{
			result= false;
			logger.error(fieldName+"="+field+",该字段验证失败。原因：长度超过了"+maxLength+"个字符！");
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			  result=false;
			  logger.error(fieldName+"="+field+",该字段验证失败。原因：该字段是必填字段，不允许为空");
		  }
		}
		return result;
		
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
