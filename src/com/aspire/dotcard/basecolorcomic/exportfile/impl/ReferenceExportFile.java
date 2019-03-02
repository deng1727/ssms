/*
 * �ļ�����ProgramExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.basecolorcomic.exportfile.impl;

import java.util.Map;

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
public class ReferenceExportFile extends BaseExportFile
{
	/**
	 * �����б�
	 */
	private Map<String, String> typeIDMap = null;
	
	/**
	 * ��Դ�б�
	 */
	private Map<String, String> contentIDMap = null;
	
	public ReferenceExportFile()
	{
		this.fileName = "i_c-cmmaterialcategorylink_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmmaterialcategorylink_~DyyyyMMdd~.verf";
		this.mailTitle = "�����زĹ�����ϵ���ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_REFERENCE);
		typeIDMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CATEGORY);
		contentIDMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CONTENT);
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		super.destroy();
		typeIDMap.clear();
		contentIDMap.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String refId = data[0];
		String tmp = refId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�����زĹ�����ϵ�ļ��ֶθ�ʽ�������زĹ�����ϵ����=" + refId);
		}
		
		if (data.length != 7)
		{
			logger.error("�ֶ���������7");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("�����زĹ�����ϵ����", tmp, 11, true))
		{
			logger.error("�����زĹ�����ϵ����=" + refId
					+ ",refId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����11����ֵ����");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ��������
		tmp = data[1];
		if (!this.checkIntegerField("��������", tmp, 11, true))
		{
			logger.error("�����زĹ�����ϵ����=" + refId
					+ ",����������֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����11����ֵ���ȣ���������=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!typeIDMap.containsKey(tmp))
		{
			logger.error("�����زĹ�����ϵ����=" + refId
					+ ",����������֤���������б��в����ڴ˷�����������������=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ز�����
		tmp = data[2];
		if (!this.checkIntegerField("�ز�����", tmp, 11, true))
		{
			logger.error("�����زĹ�����ϵ����=" + refId
					+ ",�ز�������֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����11����ֵ���ȣ��ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!contentIDMap.containsKey(tmp))
		{
			logger.error("�����زĹ�����ϵ����=" + refId
					+ ",�ز�������֤�����ز������б��в����ڴ��ز��������ز�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ����
		tmp = data[3];
		if (!this.checkIntegerField("����", tmp, 11, false))
		{
			logger.error("�����زĹ�����ϵ����=" + refId + ",������֤���󣬳��Ȳ�����11����ֵ���ȣ�����="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ״̬
		tmp = data[4];
		if (!this.checkIntegerField("״̬", tmp, 11, false))
		{
			logger.error("�����زĹ�����ϵ����=" + refId + ",״̬��֤���󣬳��Ȳ�����11����ֵ���ȣ�״̬="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ����ʱ��
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�����زĹ�����ϵ����=" + refId + ",����ʱ����֤���󣬳��Ȳ�����14��λ�ã�����ʱ��="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �޸�ʱ��
		tmp = data[6];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�����زĹ�����ϵ����=" + refId + ",�޸�ʱ����֤���󣬳��Ȳ�����14��λ�ã��޸�ʱ��="
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
		Object[] object = new Object[7];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		object[5] = data[6];
		object[6] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.ReferenceExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.ReferenceExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.ReferenceExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
}
