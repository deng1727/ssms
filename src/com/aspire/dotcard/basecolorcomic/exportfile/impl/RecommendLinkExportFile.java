/*
 * �ļ�����RankExportFile.java
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
public class RecommendLinkExportFile extends BaseExportFile
{
	/**
	 * �����б�
	 */
	private Map<String, String> typeIDMap = null;
	
	/**
	 * ��Դ�б�
	 */
	private Map<String, String> contentIDMap = null;
	
	/**
	 * �Ƽ��б�
	 */
	private Map<String, String> recIDMap = null;
	
	public RecommendLinkExportFile()
	{
		this.fileName = "i_c-cmrecommendlink_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmrecommendlink_~DyyyyMMdd~.verf";
		this.mailTitle = "�Ƽ�������ϵ���ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK);
		typeIDMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CATEGORY);
		contentIDMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CONTENT);
		recIDMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_RECOMMEND);
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		super.destroy();
		typeIDMap.clear();
		contentIDMap.clear();
		recIDMap.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String linkID = data[0];
		String tmp = linkID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�Ƽ�������ϵ�ֶθ�ʽ����������ID" + linkID);
		}
		
		if (data.length != 5)
		{
			logger.error("�ֶ���������5");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("��������ID", tmp, 12, true))
		{
			logger
					.error("��������ID" + linkID
							+ ",linkID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����12����ֵ����");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ��������
		tmp = data[1];
		if (!this.checkIntegerField("��������", tmp, 12, true))
		{
			logger.error("��������ID" + linkID
					+ ",����������֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12����ֵ���ȣ���������=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("14".indexOf(tmp) == -1)
		{
			logger.error("��������ID" + linkID
					+ ",���ε���ֻ�����������Ϊ1��4����ֵ������״̬��ʱ�������ݿ⣬��ǰ��������ֵΪ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ�����Id
		tmp = data[2];
		if (!this.checkIntegerField("�Ƽ�����Id", tmp, 12, false))
		{
			logger.error("��������ID" + linkID + ",�Ƽ�����Id��֤�������Ȳ�����12����ֵ���ȣ��Ƽ�����Id="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!recIDMap.containsKey(tmp))
		{
			logger.error("��������ID" + linkID
					+ ",�Ƽ�����Id�����Ƽ�����Id�б��в����ڴ��Ƽ�����Id���Ƽ�����Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// �Ƽ��ⲿ����Id
		tmp = data[3];
		if (!this.checkIntegerField("�Ƽ��ⲿ����Id", tmp, 12, false))
		{
			logger.error("��������ID" + linkID
					+ ",�Ƽ��ⲿ����Id��֤�������Ȳ�����12����ֵ���ȣ��Ƽ��ⲿ����Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("4".equals(data[1]))
		{
			if (!typeIDMap.containsKey(tmp))
			{
				logger.error("��������ID" + linkID
						+ ",����������֤���������б��в����ڴ˷�����������������=" + tmp);
				return BaseColorComicConfig.CHECK_FAILED;
			}
		}
		else if ("1".equals(data[1]))
		{
			if (!contentIDMap.containsKey(tmp))
			{
				logger.error("��������ID" + linkID
						+ ",�ز�������֤�����ز������б��в����ڴ��ز��������ز�����=" + tmp);
				return BaseColorComicConfig.CHECK_FAILED;
			}
		}
		else
		{
			logger.error("��������ID" + linkID
					+ ",���ε���ֻ�����������Ϊ1��4����ֵ������״̬��ʱ�������ݿ⣬��ǰ��������ֵΪ:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// ����
		tmp = data[4];
		if (!this.checkIntegerField("����", tmp, 12, false))
		{
			logger.error("��������ID" + linkID + ",������֤�������Ȳ�����12����ֵ���ȣ�����=" + tmp);
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
		Object[] object = new Object[5];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.RecommendLinkExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.RecommendLinkExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.RecommendLinkExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
}
