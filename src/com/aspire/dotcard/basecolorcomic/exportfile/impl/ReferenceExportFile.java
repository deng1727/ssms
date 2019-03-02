/*
 * 文件名：ProgramExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
	 * 分类列表
	 */
	private Map<String, String> typeIDMap = null;
	
	/**
	 * 资源列表
	 */
	private Map<String, String> contentIDMap = null;
	
	public ReferenceExportFile()
	{
		this.fileName = "i_c-cmmaterialcategorylink_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmmaterialcategorylink_~DyyyyMMdd~.verf";
		this.mailTitle = "分类素材关联关系数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
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
	 * 用于回收数据
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
			logger.debug("开始验证分类素材关联关系文件字段格式，分类素材关联关系主键=" + refId);
		}
		
		if (data.length != 7)
		{
			logger.error("字段数不等于7");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("分类素材关联关系主键", tmp, 11, true))
		{
			logger.error("分类素材关联关系主键=" + refId
					+ ",refId验证错误，该字段是必填字段，且不超过11个数值长度");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 分类主键
		tmp = data[1];
		if (!this.checkIntegerField("分类主键", tmp, 11, true))
		{
			logger.error("分类素材关联关系主键=" + refId
					+ ",分类主键验证错误，该字段是必填字段，长度不超过11个数值长度！分类主键=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!typeIDMap.containsKey(tmp))
		{
			logger.error("分类素材关联关系主键=" + refId
					+ ",分类主键验证出错，分类列表中不存在此分类主键！分类主键=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材主键
		tmp = data[2];
		if (!this.checkIntegerField("素材主键", tmp, 11, true))
		{
			logger.error("分类素材关联关系主键=" + refId
					+ ",素材主键验证错误，该字段是必填字段，长度不超过11个数值长度！素材主键=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!contentIDMap.containsKey(tmp))
		{
			logger.error("分类素材关联关系主键=" + refId
					+ ",素材主键验证出错，素材主键列表中不存在此素材主键！素材主键=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 排序
		tmp = data[3];
		if (!this.checkIntegerField("排序", tmp, 11, false))
		{
			logger.error("分类素材关联关系主键=" + refId + ",排序验证错误，长度不超过11个数值长度！排序="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 状态
		tmp = data[4];
		if (!this.checkIntegerField("状态", tmp, 11, false))
		{
			logger.error("分类素材关联关系主键=" + refId + ",状态验证错误，长度不超过11个数值长度！状态="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 创建时间
		tmp = data[5];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("分类素材关联关系主键=" + refId + ",创建时间验证错误，长度不超过14个位置！创建时间="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 修改时间
		tmp = data[6];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("分类素材关联关系主键=" + refId + ",修改时间验证错误，长度不超过14个位置！修改时间="
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
