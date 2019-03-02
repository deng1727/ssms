/*
 * �ļ�����VideoAddExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;

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
 * @author 
 * @version
 */
public class VideoByHourExportFile extends BaseExportFile
{

	public VideoByHourExportFile()
	{
		
		this.fileName = "a_v-videoCode_~DyyyyMMddHH~_[0-9]{6}.txt";
		this.verfFileName = "a_v-videoCode_~DyyyyMMddHH~.verf";
		this.isDelTable = false;
		this.mailTitle = "������Ƶ�����ļ���Сʱ�������ݵ�����";
		this.isByHour=true;
		this.getTimeNum=BaseVideoConfig.GET_TIME_NUM;;
	}

	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		//keyMap = ProgramByHourExportFileDAO.getInstance().getAllVideoIDMap();
	}

	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		super.destroy();
//		keyMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String videoId = data[0];
		String tmp = videoId;

		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ƶ�����ļ��ֶθ�ʽ��videoId=" + videoId);
		}

		if (data.length != 6)
		{
			logger.error("�ֶ���������6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("videoId=" + videoId
					+ ",videoId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// codeRateID
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 6, true))
		{
			logger
					.error("videoId="
							+ videoId
							+ ",codeRateID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����6���ַ�����codeRateID="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// filePath
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("videoId=" + videoId
					+ ",filePath��֤�������Ȳ�����512���ַ���filePath=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// downLoadFilePath
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 512, false))
		{
			logger.error("videoId=" + videoId
					+ ",downLoadFilePath��֤�������Ȳ�����512���ַ���downLoadFilePath="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fileSize
		tmp = data[4];
		if (!this.checkIntegerField("fileSize", tmp, 12, true))
		{
			logger.error("videoId=" + videoId + ",fileSize���Ȳ�����12����ֵ��fileSize="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// changeType
		tmp = data[5];
		if (!this.checkIntegerField("changeType", tmp, 2, true))
		{
			logger.error("videoId=" + videoId
					+ ",changeType���Ȳ�����2����ֵ��fileSize=" + tmp);
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

		object[0] = data[2];
		object[1] = data[3];
		object[2] = data[4];
		object[3] = data[0];
		object[4] = data[1];

		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
	 */
	protected String getInsertSqlCode()
	{
		// insert into T_VO_VIDEO (filePath, downloadfilepath, fileSize,
		// videoId, codeRateID) values (?,?,?,?,?)
		return "baseVideo.exportfile.VideoByHourExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		//update T_VO_VIDEO  set filePath=?, downloadfilepath=?, fileSize=? , UPDATETIME=sysdate where videoId=? and codeRateID=?
		return "baseVideo.exportfile.VideoByHourExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		// delete from T_VO_VIDEO where videoId=? and codeRateID=?
		return "baseVideo.exportfile.VideoByHourExportFile.getDelSqlCode";
	}

	protected Object[] getHasObject(String[] data)
	{
		Object[] object = new Object[2];

		object[0] = data[0];
		object[1] = data[1];

		return object;
	}

	protected String getHasSqlCode()
	{
		// select 1 from T_VO_VIDEO where videoId=? and codeRateID=?
		return "baseVideo.exportfile.VideoByHourExportFile.getHasSqlCode";
	}

	protected String getKey(String[] data)
	{
		return data[0] + "|" + data[1];
	}

}
