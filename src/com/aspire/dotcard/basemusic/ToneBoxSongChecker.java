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
	 * ��־����
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
			logger.error("�ֶ������ԣ�Ӧ����4���ֶΣ�Ŀǰ������"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// id
		String tmp = (String) record.get(1);
		String id = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����и����ֶθ�ʽ��ID=" + id);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + tmp + ",id��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����64���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// boxId
		tmp = (String) record.get(0);
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("id=" + id
					+ ",boxId��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����64���ַ�����boxId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		if(!new ToneBoxDAO().existToneBoxID(tmp))
		{
			logger.error("id=" + id
					+ ",boxId�������б��в����ڣ�boxId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// sortId
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 10, false))
		{

			logger.error("id=" + id
					+ ",sortId��֤�����䳤�Ȳ�����10���ַ�����sortId=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
//		 operType
//		tmp = (String) record.get(3);
//		if (!this.checkFieldLength(tmp, 512, true))
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
