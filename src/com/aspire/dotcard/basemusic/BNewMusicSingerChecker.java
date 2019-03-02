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
	 * ��־����
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
			logger.error("�ֶ������ԣ�Ӧ����7���ֶΣ�Ŀǰ������"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// singerId
		String tmp = (String) record.get(0);
		String singerId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����ָ����ֶθ�ʽ��singerId=" + singerId);
		}
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("singerId=" + tmp + ",singerId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����64���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// singerN
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 3, false))
		{
			logger.error("singerId=" + singerId
					+ ",singerN��������ĸ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����3���ַ�����singerN=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singername
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("singerId=" + singerId
					+ ",singername��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����512���ַ�����singername=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singerdesc
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 1024, false))
		{

			logger.error("singerId=" + singerId
					+ ",SINGERDESC��֤�����䳤�Ȳ�����1024���ַ�����SINGERDESC=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// singerpicurl
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 512, false))
		{

			logger.error("singerId=" + singerId
					+ ",singerpicurl��֤�����䳤�Ȳ�����512���ַ�����singerpicurl=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 singertype
		tmp = (String) record.get(5);
		if (!this.checkFieldLength(tmp, 512, false))
		{

			logger.error("singerId=" + singerId
					+ ",singertype��֤�����䳤�Ȳ�����512���ַ�����singertype=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
//		 optype
		tmp = (String) record.get(6);
		if (!this.checkFieldLength(tmp, 2, true))
		{

			logger.error("singerId=" + singerId
					+ ",optype��֤�����䳤�Ȳ�����2���ַ�����optype=" + tmp);
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
}
