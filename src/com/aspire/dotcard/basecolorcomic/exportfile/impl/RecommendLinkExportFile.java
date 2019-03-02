/*
 * 文件名：RankExportFile.java
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
public class RecommendLinkExportFile extends BaseExportFile
{
	/**
	 * 分类列表
	 */
	private Map<String, String> typeIDMap = null;
	
	/**
	 * 资源列表
	 */
	private Map<String, String> contentIDMap = null;
	
	/**
	 * 推荐列表
	 */
	private Map<String, String> recIDMap = null;
	
	public RecommendLinkExportFile()
	{
		this.fileName = "i_c-cmrecommendlink_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmrecommendlink_~DyyyyMMdd~.verf";
		this.mailTitle = "推荐关联关系数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
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
	 * 用于回收数据
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
			logger.debug("开始验证推荐关联关系字段格式，关联主键ID" + linkID);
		}
		
		if (data.length != 5)
		{
			logger.error("字段数不等于5");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("关联主键ID", tmp, 12, true))
		{
			logger
					.error("关联主键ID" + linkID
							+ ",linkID验证错误，该字段是必填字段，且不超过12个数值长度");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 关联类型
		tmp = data[1];
		if (!this.checkIntegerField("关联类型", tmp, 12, true))
		{
			logger.error("关联主键ID" + linkID
					+ ",关联类型验证出错，该字段是必填字段，长度不超过12个数值长度！关联类型=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("14".indexOf(tmp) == -1)
		{
			logger.error("关联主键ID" + linkID
					+ ",本次导入只导入关联类型为1、4的数值，其他状态暂时不入数据库，当前关联类型值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐主键Id
		tmp = data[2];
		if (!this.checkIntegerField("推荐主键Id", tmp, 12, false))
		{
			logger.error("关联主键ID" + linkID + ",推荐主键Id验证出错，长度不超过12个数值长度！推荐主键Id="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!recIDMap.containsKey(tmp))
		{
			logger.error("关联主键ID" + linkID
					+ ",推荐主键Id出错，推荐主键Id列表中不存在此推荐主键Id！推荐主键Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 推荐外部主键Id
		tmp = data[3];
		if (!this.checkIntegerField("推荐外部主键Id", tmp, 12, false))
		{
			logger.error("关联主键ID" + linkID
					+ ",推荐外部主键Id验证出错，长度不超过12个数值长度！推荐外部主键Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if ("4".equals(data[1]))
		{
			if (!typeIDMap.containsKey(tmp))
			{
				logger.error("关联主键ID" + linkID
						+ ",分类主键验证出错，分类列表中不存在此分类主键！分类主键=" + tmp);
				return BaseColorComicConfig.CHECK_FAILED;
			}
		}
		else if ("1".equals(data[1]))
		{
			if (!contentIDMap.containsKey(tmp))
			{
				logger.error("关联主键ID" + linkID
						+ ",素材主键验证出错，素材主键列表中不存在此素材主键！素材主键=" + tmp);
				return BaseColorComicConfig.CHECK_FAILED;
			}
		}
		else
		{
			logger.error("关联主键ID" + linkID
					+ ",本次导入只导入关联类型为1、4的数值，其他状态暂时不入数据库，当前关联类型值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 排序
		tmp = data[4];
		if (!this.checkIntegerField("排序", tmp, 12, false))
		{
			logger.error("关联主键ID" + linkID + ",排序验证出错，长度不超过12个数值长度！排序=" + tmp);
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
