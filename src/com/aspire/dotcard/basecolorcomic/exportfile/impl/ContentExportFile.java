/*
 * �ļ�����DeviceExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.basecolorcomic.exportfile.impl;

import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;
import com.aspire.dotcard.basecolorcomic.dao.BaseColorComicFileDAO;
import com.aspire.dotcard.basecolorcomic.exportfile.BaseExportFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ContentExportFile extends BaseExportFile
{
	public ContentExportFile()
	{
		this.fileName = "i_c-cmmaterialstore_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmmaterialstore_~DyyyyMMdd~.verf";
		this.mailTitle = "�����ز���Դ���ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CONTENT);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String cmId = data[0];
		String tmp = cmId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�ز���Դ�ļ��ֶθ�ʽ���ز�Id=" + cmId);
		}
		
		if (data.length != 15)
		{
			logger.error("�ֶ���������14");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("�ز�Id", tmp, 12, true))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�Id��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����12����ֵ");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�ҵ��Id
		tmp = data[1];
		if (!this.checkIntegerField("�ز�ҵ��Id", tmp, 12, true))
		{
			logger.error("�ز�Id=" + cmId
					+ ",�ز�ҵ��Id��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����12����ֵ���ز�ҵ��Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����Id
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 100, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�����Id��֤�������Ȳ�����100���ַ����ز�����Id="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 60, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�������֤�������Ȳ�����60���ַ����ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�������֤�������Ȳ�����60���ַ����ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز���������
		tmp = data[5];
		if (!this.checkIntegerField("�ز�ҵ��Id", tmp, 12, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�����������֤�������Ȳ�����12����ֵ���ز���������="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[6];
		if (!this.checkIntegerField("�ز�����", tmp, 12, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�������֤�������Ȳ�����12����ֵ���ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("�ز�Id=" + cmId
					+ ",���ε���ֻ�����ز�����Ϊ1����ֵ������״̬��ʱ�������ݿ⣬��ǰ�ز�����ֵΪ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[7];
		if (!this.checkIntegerField("�ز�����", tmp, 12, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�������֤�������Ȳ�����12����ֵ���ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�״̬
		tmp = data[8];
		if (!this.checkIntegerField("�ز�״̬", tmp, 12, true))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�״̬��֤�������Ȳ�����12����ֵ���ز�״̬=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ټӹ��ز�
		tmp = data[9];
		if (!this.checkIntegerField("�ټӹ��ز�", tmp, 12, true))
		{
			logger.error("�ز�Id=" + cmId + ",�ټӹ��ز���֤�������Ȳ�����12����ֵ���ټӹ��ز�=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("�ز�Id=" + cmId
					+ ",���ε���ֻ�����ټӹ��ز�Ϊ1����ֵ������״̬��ʱ�������ݿ⣬��ǰ�ټӹ��ز�ֵΪ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �زķ���·��
		tmp = data[10];
		if (!this.checkFieldLength(tmp, 4000, true))
		{
			logger.error("�ز�Id=" + cmId + ",�زķ���·������֤�������Ȳ�����4000����ֵ���زķ���·��="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�Ԥ��·��
		tmp = data[11];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�Ԥ��·������֤�������Ȳ�����4000����ֵ���ز�Ԥ��·��="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[12];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("�ز�Id=" + cmId + ",�ز�������֤�������Ȳ�����4000����ֵ���ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// createtime
		tmp = data[13];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�ز�Id=" + cmId
					+ ",createtime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���createtime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// updatetime
		tmp = data[14];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�ز�Id=" + cmId
					+ ",updatetime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���updatetime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		return BaseColorComicConfig.CHECK_DATA_SUCCESS;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[15];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		object[5] = data[6];
		object[6] = data[7];
		object[7] = data[8];
		object[8] = data[9];
		object[9] = data[10];
		object[10] = data[11];
		object[11] = data[12];
		object[12] = data[13];
		object[13] = data[14];
		object[14] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
	
}
