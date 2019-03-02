package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class ToneBoxChecker
{
	/**
	 * ��־����
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
			logger.error("�ֶ������ԣ�Ӧ����6���ֶΣ�Ŀǰ������"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// id
		String tmp = (String) record.get(0);
		String id = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�������ֶθ�ʽ��ID=" + id);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + tmp + ",id��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����64���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// Name
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + id
					+ ",Name��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����3���ַ�����Name=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// description
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 4000, false))
		{

			logger.error("id=" + id
					+ ",description��֤�����䳤�Ȳ�����4000���ַ�����description=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// charge
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 12, true))
		{

			logger.error("id=" + id
					+ ",charge��֤�����䳤�Ȳ�����12���ַ�����charge=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// valid
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 14, true))
		{

			logger.error("id=" + id
					+ ",valid��֤�����䳤�Ȳ�����14���ַ�����valid=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 operType
//		tmp = (String) record.get(5);
//		if (!this.checkFieldLength(tmp, 2, true))
//		{
//
//			logger.error("id=" + id
//					+ ",operType��֤�����䳤�Ȳ�����2���ַ�����operType=" + tmp);
//			return DataSyncConstants.CHECK_FAILED;
//		}

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
}
