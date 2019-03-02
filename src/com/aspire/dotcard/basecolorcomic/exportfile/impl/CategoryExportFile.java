/*
 * 文件名：CodeRateExportFile.java
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
public class CategoryExportFile extends BaseExportFile
{
	public CategoryExportFile()
	{
		this.fileName = "i_c-cmcategory_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmcategory_~DyyyyMMdd~.verf";
		this.mailTitle = "彩漫素材分类数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
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
			logger.debug("开始验证素材分类文件字段格式，分类主键=" + codeRateId);
		}
		
		if (data.length != 8)
		{
			logger.error("字段数不等于8");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("分类主键", tmp, 11, true))
		{
			logger.error("分类主键=" + codeRateId + ",分类主键验证错误，该字段是必填字段，且不超过11个数值");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// canonicalName
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 50, true))
		{
			logger.error("分类主键=" + codeRateId
					+ ",cateName验证错误，该字段是必填字段，且长度不超过50个字符！cateName=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// pcateId
		tmp = data[2];
		if (!this.checkIntegerField("父级分类主键", tmp, 11, false))
		{
			logger.error("分类主键=" + codeRateId
					+ ",pcateId验证出错，该字段是非必填字段，长度不超过11个数值！pcateId=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// sortId
		tmp = data[3];
		if (!this.checkIntegerField("分类排序值", tmp, 11, false))
		{
			logger.error("分类主键=" + codeRateId
					+ ",sortId验证出错，该字段是必填字段，长度不超过11个数值！sortId=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// createtime
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("分类主键=" + codeRateId
					+ ",createtime验证出错，该字段是必填字段，长度不超过14个字符！createtime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// updatetime
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, true))
		{
			logger.error("分类主键=" + codeRateId
					+ ",updatetime验证出错，该字段是必填字段，长度不超过14个字符！updatetime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// state
		tmp = data[6];
		if (!this.checkIntegerField("分类状态", tmp, 11, true))
		{
			logger.error("分类主键=" + codeRateId
					+ ",state验证出错，该字段是必填字段，长度不超过11个数值！state=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		/**
		if (!"1".equals(tmp))
		{
			logger.error("本次导入只导入分类状态为1的数值，其他状态暂时不入数据库，当前状态值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		**/
		
		// CATEGORYBANNER
		tmp = data[7];
		if (!this.checkFieldLength(tmp, 255, false))
		{
			logger
					.error("分类主键="
							+ codeRateId
							+ ",CATEGORYBANNER验证出错，该字段是必填字段，长度不超过255个字符！CATEGORYBANNER="
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
