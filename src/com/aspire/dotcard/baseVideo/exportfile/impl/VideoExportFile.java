/*
 * 文件名：VideoExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
 * @author wangminlong
 * @version
 */
public class VideoExportFile extends BaseExportFile
{
	private boolean isAll;

	public VideoExportFile(boolean isAll)
	{
		this.isAll = isAll;

		if (isAll)
		{
			this.fileName = "i_v-videoCode_~DyyyyMMdd~_[0-9]{6}.txt";
			this.verfFileName = "i_v-videoCode_~DyyyyMMdd~.verf";
			this.mailTitle = "基地视频物理文件全量数据导入结果";
		}
		else
		{
			this.fileName = "a_v-videoCode_~DyyyyMMdd~_[0-9]{6}.txt";
			this.verfFileName = "a_v-videoCode_~DyyyyMMdd~.verf";
			this.isDelTable = false;
			this.mailTitle = "基地视频物理文件增量数据导入结果";
		}
	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		// BaseVideoFileDAO.getInstance().dropIndex();
	}

	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
		super.destroy();
		// BaseVideoFileDAO.getInstance().createIndex();
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
			logger.debug("开始验证视频物理文件字段格式，videoId=" + videoId);
		}

		if (data.length != 6)
		{
			logger.error("字段数不等于6");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("videoId=" + videoId
					+ ",videoId验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// codeRateID
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 6, true))
		{
			logger
					.error("videoId="
							+ videoId
							+ ",codeRateID验证错误，该字段是必填字段，且长度不超过6个字符错误！codeRateID="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// filePath
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 512, true))
		{
			logger.error("videoId=" + videoId
					+ ",filePath验证出错，长度不超过512个字符！filePath=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// downLoadFilePath
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 512, false))
		{
			logger.error("videoId=" + videoId
					+ ",downLoadFilePath验证出错，长度不超过512个字符！downLoadFilePath="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fileSize
		tmp = data[4];
		if (!this.checkIntegerField("fileSize", tmp, 12, true))
		{
			logger.error("videoId=" + videoId + ",fileSize长度不超过12个数值！fileSize="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// changeType
		tmp = data[5];
		if (!this.checkIntegerField("changeType", tmp, 2, true))
		{
			logger.error("videoId=" + videoId
					+ ",changeType长度不超过2个数值！fileSize=" + tmp);
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
		// insert into T_VO_VIDEO_TRA (filePath, downloadfilepath, fileSize,
		// videoId, codeRateID) values (?,?,?,?,?)
		return "baseVideo.exportfile.VideoExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		if (isAll)
		{
			// update T_VO_VIDEO_TRA set filePath=?, downloadfilepath=?,
			// fileSize=? where videoId=? and codeRateID=?
			return "baseVideo.exportfile.VideoExportFile.getUpdateSqlCode";
		}
		else
		{
			// delete from T_VO_VIDEO_TRA where videoId=? and codeRateID=?
			return "baseVideo.exportfile.VideoExportFile.getUpdateSqlCode_add";
		}

	}

	protected String getDelSqlCode()
	{
		// delete from T_VO_VIDEO_TRA
		return "baseVideo.exportfile.VideoExportFile.getDelSqlCode";
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
		return "baseVideo.exportfile.VideoExportFile.getHasSqlCode";
	}

	protected String getKey(String[] data)
	{
		return data[0] + "|" + data[1];
	}

}
