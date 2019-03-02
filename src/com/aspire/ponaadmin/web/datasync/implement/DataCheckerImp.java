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
 * ������Ĭ��ʵ�֡�������Ҫ����ʱ�򣬿���ʹ�ô˼���ࡣ
 * @author zhangwei
 *
 */
public class DataCheckerImp implements DataChecker
{

	private static final JLogger logger = LoggerFactory.getLogger(MusicChecker.class);
	/**
	 * ���ݼ���Ĭ��ʵ�֡�
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
	 * �жϸ��ַ��Ƿ񳬳�maxLength�ĳ��ȡ�
	 * @param field Ҫ��֤���ֶ�����
	 * @param maxLength �������󳤶�
	 * @param must �Ƿ��Ǳ����ֶΣ����Ϊtrue����Ҫ��֤���ֶ��Ƿ�Ϊ�գ�""��
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
	 * ����֤���衣��֤ʧ�ܴ�ӡ��־��
	 * @param fieldName ��������ơ�������־��¼
	 * @param field  ���ֵ
	 * @param maxLength ��������ȡ�
	 * @param must �Ƿ�����
	 * @return ��֤ʧ�ܷ���false�����򷵻�true
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
			logger.error(fieldName+"="+field+",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"+maxLength+"���ַ���");
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			  result=false;
			  logger.error(fieldName+"="+field+",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
		  }
		}
		return result;
		
	}
	/**
	 * �����ֶε���������
	 * @param field �������ֶ�
	 * @param maxLength ���ֵ���󳤶�
	 * @param must  �Ƿ������ֶ�
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
			logger.error(fieldName+"="+field+",���ֶ���֤ʧ�ܡ�ԭ�򣺳��ȳ�����"+maxLength+"�����֣�");
			return false;
		}
		if(must)
		{
		  if(field.equals(""))
		  {	
			logger.error(fieldName+"="+field+",���ֶ���֤ʧ�ܡ�ԭ�򣺸��ֶ��Ǳ����ֶΣ�������Ϊ��");
			return false;
		  }
		}
		return true;
		
	}

}
