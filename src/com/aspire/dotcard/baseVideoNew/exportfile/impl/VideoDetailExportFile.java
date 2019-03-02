/*
 * �ļ�����VideoDetailExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;

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
public class VideoDetailExportFile extends BaseExportFile
{
	public VideoDetailExportFile()
	{
		this.tableName = "t_vo_videodetail";
		this.fileName = "i_v-statist_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-statist_~DyyyyMMdd~.verf";
		this.mailTitle = "������Ƶ��Ŀͳ���ļ����ݵ�����";
	}

	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getVideoDetailIDMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
	{
		String program = data[0];
		String tmp = program;

		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ��Ŀͳ���ļ��ֶθ�ʽ��program=" + program);
		}

		if (data.length != 3)
		{
			logger.error("�ֶ���������3");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("program=" + program
					+ ",program��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// playNum
		tmp = data[1];
		if (!BaseFileNewTools.checkIntegerField("�ղ��Ŵ���", tmp, 12, true))
		{
			logger.error("program=" + program
					+ ",codeRateID��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����12��λ�ã�codeRateID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// downloadNum
		tmp = data[2];
		if (!BaseFileNewTools.checkIntegerField("�����ش���", tmp, 12, true))
		{
			logger
					.error("program="
							+ program
							+ ",downloadNum��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12��λ�ã�downloadNum="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		return BaseVideoConfig.CHECK_DATA_SUCCESS;
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
		object[1] = data[1];
		object[2] = data[2];
		object[3] = data[2];
		object[4] = data[0];

		return object;
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}

	protected String getInsertSqlCode()
	{
		// insert into T_VO_VIDEODETAIL_MID (DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, PROGRAMID, status) values (?,?,?,?,?,'A')
		return "baseVideoNew.exportfile.VideoDetailExportFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode()
	{
		// insert into T_VO_VIDEODETAIL_MID (DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, PROGRAMID, status) values select ? as DAYPLAYNUM, v.totalplaynum+? as totalplaynum, ? as DAYDOWNLOADNUM, v.totaldownloadnum+? as totaldownloadnum, sysdate as updatetime, 'U' from T_VO_VIDEODETAIL v where v.programid=?
		return "baseVideoNew.exportfile.VideoDetailExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		return null;
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
	}
}
