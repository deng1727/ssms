package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.datasync.implement.DataCheckerImp;
import com.aspire.ponaadmin.web.util.StringTool;

public class BMusicNewChecker //extends DataCheckerImp
{
	/**
	 * 日志引用
	 * 
	 */
	private static final JLogger logger = LoggerFactory.getLogger(BMusicNewChecker.class);
	private static  BMusicNewChecker  instance = new BMusicNewChecker();
	private static String INTEGER="-?[0-9]{0,d}";
	private BMusicNewChecker(){
		
	}
		public static BMusicNewChecker getInstance(){
			return instance;
		}
		
		
		/**
		 * 
		 *@desc 专辑接口校验
		 *@author dongke
		 *Sep 26, 2012
		 * @param record
		 * @return
		 * @throws Exception
		 */
	public int checkAlbumDateRecord(List record) throws Exception
	{
        int size=8;
		// albumid
		String tmp = (String) record.get(0);
		String albumid = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐专辑字段格式，albumid=" + albumid);
		}
        if(record.size()!=size)
        {
            logger.error("字段数不等于"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("albumid=" + tmp + ",albumid验证错误，该字段是必填字段，且不超过64个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// singersids
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("albumid=" + albumid
					+ ",singersids验证错误，该字段是必填字段，且长度不超过512个字符错误！singersids=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// albumUper
		tmp = (String) record.get(2);
        if(!this.checkFieldLength( tmp, 3, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumUper验证错误，该字段是必填字段，且长度不超过3个字符错误！albumUper=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
    	// albumName
		tmp = (String) record.get(3);
        if(!this.checkFieldLength( tmp, 64, true))
        {
        	logger.error("albumid=" + albumid
					+ ",albumName验证错误，该字段是必填字段，且长度不超过64个字符错误！albumName=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
    	// albumdesc
		tmp = (String) record.get(4);
        if(!this.checkFieldLength( tmp, 1024, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumdesc 验证错误，该字段是必填字段，且长度不超过1024个字符错误！albumdesc=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      albumpic
		tmp = (String) record.get(5);
        if(!this.checkFieldLength( tmp, 512, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumpic 验证错误，该字段是必填字段，且长度不超过512个字符错误！albumpic=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      pubtime
		tmp = (String) record.get(6);
        if(!this.checkFieldLength( tmp, 14, false))
        {
        	logger.error("albumid=" + albumid
					+ ",pubtime 验证错误，该字段是必填字段，且长度不超过14个字符错误！pubtime=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      changetype
		tmp = (String) record.get(7);
        if(!this.checkIntegerField("changetype", tmp, 2, true))
        {
        	logger.error("albumid=" + albumid
					+ ",changetype 验证错误，该字段是必填字段，且长度不超过2个字符错误！changetype=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
		
		/**
		 * 
		 *@desc 专辑歌曲接口校验
		 *@author dongke
		 *Sep 26, 2012
		 * @param record
		 * @return
		 * @throws Exception
		 */
	public int checkDateRecord(List record) throws Exception
	{
        int size=4;
		// albumid
		String tmp = (String) record.get(0);
		String albumid = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证音乐专辑歌曲字段格式，albumid=" + albumid);
		}
        if(record.size()!=size)
        {
            logger.error("字段数不等于"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("albumid=" + tmp + ",albumid验证错误，该字段是必填字段，且不超过64个字符");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicid
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("albumid=" + albumid
					+ ",musicid验证错误，该字段是必填字段，且长度不超过25个字符错误！musicid=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Sortnumber
		tmp = (String) record.get(2);
        if(!this.checkIntegerField("Sortnumber", tmp, 11, true))
        {
        	logger.error("albumid=" + albumid
					+ ",Sortnumber 验证错误，该字段是必填字段，且长度不超过11个字符错误！Sortnumber=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      changetype
		tmp = (String) record.get(3);
        if(!this.checkIntegerField("changetype", tmp, 2, true))
        {
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
