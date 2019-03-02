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
	 * ��־����
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
		 *@desc ר���ӿ�У��
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
			logger.debug("��ʼ��֤����ר���ֶθ�ʽ��albumid=" + albumid);
		}
        if(record.size()!=size)
        {
            logger.error("�ֶ���������"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("albumid=" + tmp + ",albumid��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����64���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// singersids
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("albumid=" + albumid
					+ ",singersids��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����512���ַ�����singersids=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// albumUper
		tmp = (String) record.get(2);
        if(!this.checkFieldLength( tmp, 3, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumUper��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����3���ַ�����albumUper=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
    	// albumName
		tmp = (String) record.get(3);
        if(!this.checkFieldLength( tmp, 64, true))
        {
        	logger.error("albumid=" + albumid
					+ ",albumName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����64���ַ�����albumName=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
    	// albumdesc
		tmp = (String) record.get(4);
        if(!this.checkFieldLength( tmp, 1024, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumdesc ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����1024���ַ�����albumdesc=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      albumpic
		tmp = (String) record.get(5);
        if(!this.checkFieldLength( tmp, 512, false))
        {
        	logger.error("albumid=" + albumid
					+ ",albumpic ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����512���ַ�����albumpic=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      pubtime
		tmp = (String) record.get(6);
        if(!this.checkFieldLength( tmp, 14, false))
        {
        	logger.error("albumid=" + albumid
					+ ",pubtime ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����14���ַ�����pubtime=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
//      changetype
		tmp = (String) record.get(7);
        if(!this.checkIntegerField("changetype", tmp, 2, true))
        {
        	logger.error("albumid=" + albumid
					+ ",changetype ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����2���ַ�����changetype=" + tmp);
            return DataSyncConstants.CHECK_FAILED;
        }
		return DataSyncConstants.CHECK_SUCCESSFUL;
	}
		
		/**
		 * 
		 *@desc ר�������ӿ�У��
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
			logger.debug("��ʼ��֤����ר�������ֶθ�ʽ��albumid=" + albumid);
		}
        if(record.size()!=size)
        {
            logger.error("�ֶ���������"+size);
            return DataSyncConstants.CHECK_FAILED;
        }
		if (!this.checkFieldLength(tmp, 64, true))
		{
			logger.error("albumid=" + tmp + ",albumid��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����64���ַ�");
			return DataSyncConstants.CHECK_FAILED;
		}
		// musicid
		tmp = (String) record.get(1);
		if (!this.checkFieldLength(tmp, 25, true))
		{
			logger.error("albumid=" + albumid
					+ ",musicid��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����25���ַ�����musicid=" + tmp);
			return DataSyncConstants.CHECK_FAILED;
		}
		// Sortnumber
		tmp = (String) record.get(2);
        if(!this.checkIntegerField("Sortnumber", tmp, 11, true))
        {
        	logger.error("albumid=" + albumid
					+ ",Sortnumber ��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����11���ַ�����Sortnumber=" + tmp);
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
