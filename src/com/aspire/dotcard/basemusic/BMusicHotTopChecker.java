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
	 * ��־����
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
            logger.error("record.size()="+record.size()+";�ֶ���������"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
        if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤���ְ��ֶθ�ʽ��musicId=" + record.get(2));
		}
		// musicId
		String tmp = (String) record.get(1);
		String musicId = tmp;
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",����Id��֤���������ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// ListName
		tmp = (String) record.get(0);
		if (!this.checkFieldLength(tmp, 100, true))
		{
			logger.error("musicId=" + musicId
					+ ",��������֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����listName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// MusicName
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",MusicName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����MusicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// sortNumber
		tmp = (String) record.get(3);
		if (!this.checkIntegerField("sortNumber",tmp, 6, true))
		{

			logger.error("musicId=" + musicId
					+ ",sortNumber��֤�����䳤�ȳ���6���ַ�����sortNumber=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
/*		 type
		01-�������񵥣�
		02-PC�񵥣�
		03-WWW�񵥣�
		04-��Ա��
*/
		tmp = (String) record.get(4);
	//	if (!this.checkFieldLength(tmp, 2, true))
		if (!this.checkFieldLength(tmp, 64, false))//2013-06-05
		{

			logger.error("musicId=" + musicId
					+ ",type��֤�����䳤�ȳ���64���ַ�����type=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
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
