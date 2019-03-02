/*
 * 文件名：VideoDetailExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
		this.mailTitle = "基地视频节目统计文件数据导入结果";
	}

	/**
	 * 用于添加准备动作数据
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
			logger.debug("开始验证视频节目统计文件字段格式，program=" + program);
		}

		if (data.length != 3)
		{
			logger.error("字段数不等于3");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("program=" + program
					+ ",program验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// playNum
		tmp = data[1];
		if (!BaseFileNewTools.checkIntegerField("日播放次数", tmp, 12, true))
		{
			logger.error("program=" + program
					+ ",codeRateID验证错误，该字段是必填字段，长度不超过12个位置！codeRateID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// downloadNum
		tmp = data[2];
		if (!BaseFileNewTools.checkIntegerField("日下载次数", tmp, 12, true))
		{
			logger
					.error("program="
							+ program
							+ ",downloadNum验证出错，该字段是必填字段，长度不超过12个位置！downloadNum="
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
	 * 用于回收数据
	 */
	public void destroy()
	{
	}
}
