/*
 * 文件名：VideoAddExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
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
		this.mailTitle = "基地视频物理文件按小时增量数据导入结果";
		this.isByHour=true;
		this.getTimeNum=BaseVideoNewConfig.GET_TIME_NUM;
		this.isDelMidTable = false;
	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		//keyMap = ProgramByHourExportFileDAO.getInstance().getAllVideoIDMap();
	}

	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
//		keyMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
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
			return BaseVideoNewConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("videoId=" + videoId
					+ ",videoId验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// codeRateID
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 6, true))
		{
			logger
					.error("videoId="
							+ videoId
							+ ",codeRateID验证错误，该字段是必填字段，且长度不超过6个字符错误！codeRateID="
							+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// filePath
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, true))
		{
			logger.error("videoId=" + videoId
					+ ",filePath验证出错，长度不超过512个字符！filePath=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// downLoadFilePath
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, false))
		{
			logger.error("videoId=" + videoId
					+ ",downLoadFilePath验证出错，长度不超过512个字符！downLoadFilePath="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// fileSize
		tmp = data[4];
		if (!BaseFileNewTools.checkIntegerField("fileSize", tmp, 12, true))
		{
			logger.error("videoId=" + videoId + ",fileSize长度不超过12个数值！fileSize="
					+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}

		// changeType
		tmp = data[5];
		if (!BaseFileNewTools.checkIntegerField("changeType", tmp, 2, true))
		{
			logger.error("videoId=" + videoId
					+ ",changeType长度不超过2个数值！fileSize=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}

		return BaseVideoNewConfig.CHECK_DATA_SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[6];

		object[0] = data[2];
		object[1] = data[3];
		object[2] = data[4];
		object[3] = data[0]+data[1]+data[2]+data[3]+data[4];
		object[4] = data[0];
		object[5] = data[1];

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
		// videoId, codeRateID) values (?,?,?,F_MD5(?),?,?)
		return "baseVideo.exportfile.VideoByHourExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		//update T_VO_VIDEO  set filePath=?, downloadfilepath=?, fileSize=? , hashc = F_MD5(?), UPDATETIME=sysdate where videoId=? and codeRateID=?
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
