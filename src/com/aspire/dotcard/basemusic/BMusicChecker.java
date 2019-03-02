package com.aspire.dotcard.basemusic;

import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.util.StringTool;

public class BMusicChecker //extends DataCheckerImp
{
	/**
	 * ��־����
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
			logger.error("�ֶ������ԣ�Ӧ����5���ֶΣ�Ŀǰ������"+record.size());
			return DataSyncConstants.CHECK_FAILED;
			
		}
		// bookId
		String tmp = (String) record.get(0);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����ֶθ�ʽ��musicId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 200, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����200���ַ�����musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(2);
		if (!this.checkFieldLength(tmp, 100, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����100���ַ�����singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// validity
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("musicId=" + musicId
					+ ",Music_style��֤�����䳤�Ȳ�����80���ַ�����validity=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		
		// ChangeType
		tmp = (String) record.get(4);
		if (!this.checkFieldLength(tmp, 2, true))
		{

			logger.error("musicId=" + musicId
					+ ",ChangeType��֤�����䳤�Ȳ�����2���ַ�����ChangeType=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
	
	/**
	 * 
	 *@desc ������������ݸ�ʽ
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
			logger.error("�ֶ������ԣ�Ӧ����9����11���ֶΣ�Ŀǰ������"+record.size()+"���ֶ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// bookId
		String tmp = (String) record.get(0);
		String musicId = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����ֶθ�ʽ��musicId=" + musicId);
		}
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("musicId=" + tmp + ",musicId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����25���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicName
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("musicId=" + musicId
					+ ",musicName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����512���ַ�����musicName=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// singer
		tmp = (String) record.get(2);
		//if (!this.checkFieldLength(tmp, 100, true))
		if (!this.checkFieldLength(tmp, 1024, true))
		{

			logger.error("musicId=" + musicId
					+ ",singer��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����1024���ַ�����singer=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// validity
		tmp = (String) record.get(3);
		if (!this.checkFieldLength(tmp, 512, true))
		{

			logger.error("musicId=" + musicId
					+ ",validity��֤�����䳤�Ȳ�����80���ַ�����validity=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		if(recodesize == 6){
			// productmask
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 4, true))
			{

				logger.error("musicId=" + musicId
						+ ",productmask��֤�����䳤�Ȳ�����4���ַ�����productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			// ��ǰ����Ϊȫ��0 2013.08.01 + by wml 
			if(0 == Integer.parseInt(tmp))
			{
				logger.error("musicId=" + musicId
						+ ",productmask��֤���󣬵�ǰ��λȫΪ0��productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			
			tmp = (String) record.get(5);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType��֤�����䳤�Ȳ�����2���ַ�����ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
		}else if(recodesize == 7||recodesize == 8||recodesize == 9||recodesize == 11){
			//�����ֶ���MMһվʽ�ں�
//			 productmask
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 4, true))
			{

				logger.error("musicId=" + musicId
						+ ",productmask��֤�����䳤�Ȳ�����4���ַ�����productmask=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			
			tmp = (String) record.get(5);
			if (!this.checkFieldLength(tmp, 14, false))
			{

				logger.error("musicId=" + musicId
						+ ",pubtime��֤�����䳤�Ȳ�����14���ַ�����pubtime=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(6);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType��֤�����䳤�Ȳ�����2���ַ�����ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(7);
			if (!this.checkFieldLength(tmp, 1, false))
			{

				logger.error("musicId=" + musicId
						+ ",dolbytype��֤�����䳤�Ȳ�����1���ַ�����dolbytype=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			tmp = (String) record.get(8);
			if (!this.checkFieldLength(tmp, 512, false))
			{

				logger.error("musicId=" + musicId
						+ ",musicPic��֤�����䳤�Ȳ�����512���ַ�����musicPic=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
			if(recodesize == 11){
				tmp = (String) record.get(9);
				if (!this.checkFieldLength(tmp, 1, false))
				{
					
					logger.error("musicId=" + musicId
							+ ",format320kbps��֤�����䳤�Ȳ�����1���ַ�����losslessmusic=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
				tmp = (String) record.get(10);
				if (!this.checkFieldLength(tmp, 1, false))
				{
					
					logger.error("musicId=" + musicId
							+ ",losslessmusic��֤�����䳤�Ȳ�����1���ַ�����format320kbps=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
			}
			
			tmp = (String) record.get(6);
			// ���������
			if("1".equals(tmp))
			{
				// ��ǰ����Ϊȫ��0 2013.08.01 + by wml 
				if(0 == Integer.parseInt((String)record.get(4)))
				{
					logger.error("musicId=" + musicId
							+ ",productmask��֤���󣬵�ǰ��λȫΪ0��productmask=" + tmp);
					return DataSyncConstants.CHECK_FAILED;
				}
			}
			else if("2".equals(tmp))
			{
				// ��ǰ����Ϊȫ��0 2013.08.01 + by wml 
				if(0 == Integer.parseInt((String)record.get(4)))
				{
					logger.error("musicId=" + musicId
							+ ",productmask��֤���󣬵�ǰ��λȫΪ0�����˸������ͱ�Ϊɾ��������productmask=" + tmp);
					record.remove(6);
					record.add("3");
					logger.error("���ڵ�ִ������Ϊ��" + (String) record.get(6));
				}
			}
		}
		else if(recodesize == 5){
			tmp = (String) record.get(4);
			if (!this.checkFieldLength(tmp, 2, true))
			{

				logger.error("musicId=" + musicId
						+ ",ChangeType��֤�����䳤�Ȳ�����2���ַ�����ChangeType=" + tmp);
				return DataSyncConstants.CHECK_FAILED;
			}
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
