/*
 * 文件名：VideoDetailExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
		this.mailTitle = "彩漫推荐接口数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
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
			logger.debug("开始验证视频节目统计文件字段格式，推荐ID=" + recommendId);
		}
		
		if (data.length != 11)
		{
			logger.error("字段数不等于11");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("推荐ID", tmp, 12, true))
		{
			logger.error("推荐ID=" + recommendId
					+ ",recommendId验证错误，该字段是必填字段，且不超过12个字符");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("363,364,365".indexOf(tmp) == -1)
		{
			logger.error("推荐ID=" + recommendId
					+ ",本次导入推荐ID值必须为“363,364,365”的数值，其他状态暂时不入数据库，当前推荐ID值为:"
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐名称
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("推荐ID=" + recommendId
					+ ",推荐名称验证错误，该字段是必填字段，长度不超过60个位置！推荐名称=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐描述
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("推荐ID=" + recommendId + ",推荐描述验证错误，长度不超过4000个位置！推荐描述="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐Banner
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("推荐ID=" + recommendId
					+ ",推荐Banner验证错误，长度不超过4000个位置！推荐Banner=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐内容类型
		tmp = data[4];
		if (!this.checkIntegerField("推荐内容类型", tmp, 12, false))
		{
			logger.error("推荐ID=" + recommendId
					+ ",推荐内容类型验证错误，长度不超过12个位置！推荐内容类型=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("推荐ID=" + recommendId
					+ ",本次导入只导入推荐内容类型为1的数值，其他状态暂时不入数据库，当前推荐内容类型值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐内容状态
		tmp = data[5];
		if (!this.checkIntegerField("推荐内容类型", tmp, 12, false))
		{
			logger.error("推荐ID=" + recommendId
					+ ",推荐内容状态验证错误，长度不超过12个位置！推荐内容状态=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 排序
		tmp = data[6];
		if (!this.checkIntegerField("推荐内容类型", tmp, 12, false))
		{
			logger.error("推荐ID=" + recommendId + ",排序验证验证错误，长度不超过12个位置！排序="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 创建时间
		tmp = data[7];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("推荐ID=" + recommendId + ",创建时间验证错误，长度不超过14个位置！创建时间="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 修改时间
		tmp = data[8];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("推荐ID=" + recommendId + ",修改时间验证错误，长度不超过14个位置！修改时间="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐内容放置位置
		tmp = data[9];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("推荐ID=" + recommendId
					+ ",推荐内容放置位置验证错误，长度不超过600个位置！推荐内容放置位置=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 外部链接URL
		tmp = data[10];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("推荐ID=" + recommendId
					+ ",外部链接URL验证错误，长度不超过600个位置！外部链接URL=" + tmp);
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
