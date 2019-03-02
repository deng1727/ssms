/*
 * �ļ�����VideoDetailExportFile.java
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
public class RecommendExportFile extends BaseExportFile
{
	public RecommendExportFile()
	{
		this.fileName = "i_c-cmrecommend_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmrecommend_~DyyyyMMdd~.verf";
		this.mailTitle = "�����Ƽ��ӿ����ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_RECOMMEND);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String recommendId = data[0];
		String tmp = recommendId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ��Ŀͳ���ļ��ֶθ�ʽ���Ƽ�ID=" + recommendId);
		}
		
		if (data.length != 11)
		{
			logger.error("�ֶ���������11");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("�Ƽ�ID", tmp, 12, true))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",recommendId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����12���ַ�");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("363,364,365".indexOf(tmp) == -1)
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",���ε����Ƽ�IDֵ����Ϊ��363,364,365������ֵ������״̬��ʱ�������ݿ⣬��ǰ�Ƽ�IDֵΪ:"
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ�����
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�Ƽ�������֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����60��λ�ã��Ƽ�����=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ�����
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("�Ƽ�ID=" + recommendId + ",�Ƽ�������֤���󣬳��Ȳ�����4000��λ�ã��Ƽ�����="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ�Banner
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�Ƽ�Banner��֤���󣬳��Ȳ�����4000��λ�ã��Ƽ�Banner=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ���������
		tmp = data[4];
		if (!this.checkIntegerField("�Ƽ���������", tmp, 12, false))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�Ƽ�����������֤���󣬳��Ȳ�����12��λ�ã��Ƽ���������=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",���ε���ֻ�����Ƽ���������Ϊ1����ֵ������״̬��ʱ�������ݿ⣬��ǰ�Ƽ���������ֵΪ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ�����״̬
		tmp = data[5];
		if (!this.checkIntegerField("�Ƽ���������", tmp, 12, false))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�Ƽ�����״̬��֤���󣬳��Ȳ�����12��λ�ã��Ƽ�����״̬=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ����
		tmp = data[6];
		if (!this.checkIntegerField("�Ƽ���������", tmp, 12, false))
		{
			logger.error("�Ƽ�ID=" + recommendId + ",������֤��֤���󣬳��Ȳ�����12��λ�ã�����="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ����ʱ��
		tmp = data[7];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�Ƽ�ID=" + recommendId + ",����ʱ����֤���󣬳��Ȳ�����14��λ�ã�����ʱ��="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �޸�ʱ��
		tmp = data[8];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("�Ƽ�ID=" + recommendId + ",�޸�ʱ����֤���󣬳��Ȳ�����14��λ�ã��޸�ʱ��="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ����ݷ���λ��
		tmp = data[9];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�Ƽ����ݷ���λ����֤���󣬳��Ȳ�����600��λ�ã��Ƽ����ݷ���λ��=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �ⲿ����URL
		tmp = data[10];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("�Ƽ�ID=" + recommendId
					+ ",�ⲿ����URL��֤���󣬳��Ȳ�����600��λ�ã��ⲿ����URL=" + tmp);
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
		Object[] object = new Object[11];
		
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
		object[10] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.RecommendExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.RecommendExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.RecommendExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
}
