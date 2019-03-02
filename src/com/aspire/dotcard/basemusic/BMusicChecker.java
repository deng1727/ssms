package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class BMusicChecker //extends DataCheckerImp
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicChecker.class);
private static  BMusicChecker  instance = new BMusicChecker();

private BMusicChecker(){
	
}
	public static BMusicChecker getInstance(){
		return instance;
	}
	
	public int checkDateRecord(List record) throws Exception
	{
		if(record.size() != 5){
			logger.error("字段数不对，应该有5个字段，目前该行有"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// bookId
		String tmp = (String) record.get(0);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐字段格式，musicId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId验证错误，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 200, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName验证错误，该字段是必填字段，且长度不超过200个字符错误！musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer验证错误，该字段是必填字段，且长度不超过100个字符错误！singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// validity
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("musicId=" + musicId
					+ ",Music_style验证错误，其长度不超过80个字符错误！validity=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// ChangeType
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 2, true))
		{

			logger.error("musicId=" + musicId
					+ ",ChangeType验证错误，其长度不超过2个字符错误！ChangeType=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	/**
	 * 
	 *@desc 检查新音乐数据格式
	 *@author dongke
	 *Oct 4, 2011
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public int checkNewDateRecord(List record) throws Exception
	{
		int recodesize = record.size() ;
		//if(recodesize != 6 && recodesize!= 5 && recodesize!= 7&& recodesize!= 8){
		if( recodesize != 9 && recodesize != 11){
			logger.error("字段数不对，应该有9个或11个字段，目前该行有"+record.size()+"个字段");
			return DataSyncConstants.CHECK_FAILED;
		}
		// bookId
		String tmp = (String) record.get(0);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐字段格式，musicId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId验证错误，该字段是必填字段，且不超过25个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName验证错误，该字段是必填字段，且长度不超过512个字符错误！musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(2);
		//if (!this.checkFieldLength(tmp, 100, true))
		if (!this.checkFieldLength(tmp, 1024, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer验证错误，该字段是必填字段，且长度不超过1024个字符错误！singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// validity
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("musicId=" + musicId
					+ ",validity验证错误，其长度不超过80个字符错误！validity=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(recodesize == 6){
			// productmask
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 4, true))
			{

				logger.error("musicId=" + musicId
						+ ",productmask验证错误，其长度不超过4个字符错误！productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			// 当前掩码为全是0 2013.08.01 + by wml 
			if(0 == Integer.parseInt(tmp))
			{
				logger.error("musicId=" + musicId
						+ ",productmask验证错误，当前四位全为0！productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			
			tmp = (String) record.get(5);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType验证错误，其长度不超过2个字符错误！ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
		}else if(recodesize == 7||recodesize == 8||recodesize == 9||recodesize == 11){
			//新音乐二期MM一站式融合
//			 productmask
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 4, true))
			{

				logger.error("musicId=" + musicId
						+ ",productmask验证错误，其长度不超过4个字符错误！productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			tmp = (String) record.get(5);
			if (!this.checkFieldLength(tmp, 14, false))
			{

				logger.error("musicId=" + musicId
						+ ",pubtime验证错误，其长度不超过14个字符错误！pubtime=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(6);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType验证错误，其长度不超过2个字符错误！ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(7);
			if (!this.checkFieldLength(tmp, 1, false))
			{

				logger.error("musicId=" + musicId
						+ ",dolbytype验证错误，其长度不超过1个字符错误！dolbytype=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(8);
			if (!this.checkFieldLength(tmp, 512, false))
			{

				logger.error("musicId=" + musicId
						+ ",musicPic验证错误，其长度不超过512个字符错误！musicPic=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			if(recodesize == 11){
				tmp = (String) record.get(9);
				if (!this.checkFieldLength(tmp, 1, false))
				{
					
					logger.error("musicId=" + musicId
							+ ",format320kbps验证错误，其长度不超过1个字符错误！losslessmusic=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
				tmp = (String) record.get(10);
				if (!this.checkFieldLength(tmp, 1, false))
				{
					
					logger.error("musicId=" + musicId
							+ ",losslessmusic验证错误，其长度不超过1个字符错误！format320kbps=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
			}
			
			tmp = (String) record.get(6);
			// 如果是新增
			if("1".equals(tmp))
			{
				// 当前掩码为全是0 2013.08.01 + by wml 
				if(0 == Integer.parseInt((String)record.get(4)))
				{
					logger.error("musicId=" + musicId
							+ ",productmask验证错误，当前四位全为0！productmask=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
			}
			else if("2".equals(tmp))
			{
				// 当前掩码为全是0 2013.08.01 + by wml 
				if(0 == Integer.parseInt((String)record.get(4)))
				{
					logger.error("musicId=" + musicId
							+ ",productmask验证错误，当前四位全为0，将此更新类型变为删除操作！productmask=" + tmp);
					record.remove(6);
					record.add("3");
					logger.error("现在的执行类型为：" + (String) record.get(6));
				}
			}
		}
		else if(recodesize == 5){
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType验证错误，其长度不超过2个字符错误！ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
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
