/*
 * �ļ�����CodeRateExportFile.java
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
public class CategoryExportFile extends BaseExportFile
{
	public CategoryExportFile()
	{
		this.fileName = "i_c-cmcategory_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmcategory_~DyyyyMMdd~.verf";
		this.mailTitle = "�����زķ������ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CATEGORY);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String codeRateId = data[0];
		String tmp = codeRateId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�زķ����ļ��ֶθ�ʽ����������=" + codeRateId);
		}
		
		if (data.length != 8)
		{
			logger.error("�ֶ���������8");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("��������", tmp, 11, true))
		{
			logger.error("��������=" + codeRateId + ",����������֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����11����ֵ");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// canonicalName
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 50, true))
		{
			logger.error("��������=" + codeRateId
					+ ",cateName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����50���ַ���cateName=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// pcateId
		tmp = data[2];
		if (!this.checkIntegerField("������������", tmp, 11, false))
		{
			logger.error("��������=" + codeRateId
					+ ",pcateId��֤�������ֶ��ǷǱ����ֶΣ����Ȳ�����11����ֵ��pcateId=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// sortId
		tmp = data[3];
		if (!this.checkIntegerField("��������ֵ", tmp, 11, false))
		{
			logger.error("��������=" + codeRateId
					+ ",sortId��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����11����ֵ��sortId=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// createtime
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("��������=" + codeRateId
					+ ",createtime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���createtime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// updatetime
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("��������=" + codeRateId
					+ ",updatetime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���updatetime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// state
		tmp = data[6];
		if (!this.checkIntegerField("����״̬", tmp, 11, true))
		{
			logger.error("��������=" + codeRateId
					+ ",state��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����11����ֵ��state=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		/**
		if (!"1".equals(tmp))
		{
			logger.error("���ε���ֻ�������״̬Ϊ1����ֵ������״̬��ʱ�������ݿ⣬��ǰ״ֵ̬Ϊ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		**/
		
		// CATEGORYBANNER
		tmp = data[7];
		if (!this.checkFieldLength(tmp, 255, false))
		{
			logger
					.error("��������="
							+ codeRateId
							+ ",CATEGORYBANNER��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����255���ַ���CATEGORYBANNER="
							+ tmp);
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
		Object[] object = new Object[8];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		object[5] = data[6];
		object[6] = data[7];
		object[7] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.CategoryExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.CategoryExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.CategoryExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
}
