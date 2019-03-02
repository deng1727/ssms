/*
 * 文件名：DeviceExportFile.java
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
public class ContentExportFile extends BaseExportFile
{
	public ContentExportFile()
	{
		this.fileName = "i_c-cmmaterialstore_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_c-cmmaterialstore_~DyyyyMMdd~.verf";
		this.mailTitle = "彩漫素材资源数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseColorComicFileDAO.getInstance().getKeyIDMap(
				BaseColorComicConfig.FILE_TYPE_CONTENT);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data)
	{
		String cmId = data[0];
		String tmp = cmId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证素材资源文件字段格式，素材Id=" + cmId);
		}
		
		if (data.length != 15)
		{
			logger.error("字段数不等于14");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		if (!this.checkIntegerField("素材Id", tmp, 12, true))
		{
			logger.error("素材Id=" + cmId + ",素材Id验证错误，该字段是必填字段，且不超过12个数值");
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材业务Id
		tmp = data[1];
		if (!this.checkIntegerField("素材业务Id", tmp, 12, true))
		{
			logger.error("素材Id=" + cmId
					+ ",素材业务Id验证错误，该字段是必填字段，且长度不超过12个数值！素材业务Id=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材内容Id
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 100, false))
		{
			logger.error("素材Id=" + cmId + ",素材内容Id验证出错，长度不超过100个字符！素材内容Id="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材名称
		tmp = data[3];
		if (!this.checkFieldLength(tmp, 60, false))
		{
			logger.error("素材Id=" + cmId + ",素材名称验证出错，长度不超过60个字符！素材名称=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材描述
		tmp = data[4];
		if (!this.checkFieldLength(tmp, 600, false))
		{
			logger.error("素材Id=" + cmId + ",素材描述验证出错，长度不超过60个字符！素材描述=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材形象主键
		tmp = data[5];
		if (!this.checkIntegerField("素材业务Id", tmp, 12, false))
		{
			logger.error("素材Id=" + cmId + ",素材形象主键验证出错，长度不超过12个数值！素材形象主键="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材类型
		tmp = data[6];
		if (!this.checkIntegerField("素材类型", tmp, 12, false))
		{
			logger.error("素材Id=" + cmId + ",素材类型验证出错，长度不超过12个数值！素材类型=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("素材Id=" + cmId
					+ ",本次导入只导入素材类型为1的数值，其他状态暂时不入数据库，当前素材类型值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材排序
		tmp = data[7];
		if (!this.checkIntegerField("素材排序", tmp, 12, false))
		{
			logger.error("素材Id=" + cmId + ",素材排序验证出错，长度不超过12个数值！素材排序=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材状态
		tmp = data[8];
		if (!this.checkIntegerField("素材状态", tmp, 12, true))
		{
			logger.error("素材Id=" + cmId + ",素材状态验证出错，长度不超过12个数值！素材状态=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 再加工素材
		tmp = data[9];
		if (!this.checkIntegerField("再加工素材", tmp, 12, true))
		{
			logger.error("素材Id=" + cmId + ",再加工素材验证出错，长度不超过12个数值！再加工素材=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		if (!"1".equals(tmp))
		{
			logger.error("素材Id=" + cmId
					+ ",本次导入只导入再加工素材为1的数值，其他状态暂时不入数据库，当前再加工素材值为:" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材访问路径
		tmp = data[10];
		if (!this.checkFieldLength(tmp, 4000, true))
		{
			logger.error("素材Id=" + cmId + ",素材访问路径材验证出错，长度不超过4000个数值！素材访问路径="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材预览路径
		tmp = data[11];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("素材Id=" + cmId + ",素材预览路径材验证出错，长度不超过4000个数值！素材预览路径="
					+ tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// 素材属性
		tmp = data[12];
		if (!this.checkFieldLength(tmp, 4000, false))
		{
			logger.error("素材Id=" + cmId + ",素材属性验证出错，长度不超过4000个数值！素材属性=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// createtime
		tmp = data[13];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("素材Id=" + cmId
					+ ",createtime验证出错，该字段是必填字段，长度不超过14个字符！createtime=" + tmp);
			return BaseColorComicConfig.CHECK_FAILED;
		}
		
		// updatetime
		tmp = data[14];
		if (!this.checkFieldLength(tmp, 14, false))
		{
			logger.error("素材Id=" + cmId
					+ ",updatetime验证出错，该字段是必填字段，长度不超过14个字符！updatetime=" + tmp);
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
		Object[] object = new Object[15];
		
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
		object[10] = data[11];
		object[11] = data[12];
		object[12] = data[13];
		object[13] = data[14];
		object[14] = data[0];
		
		return object;
	}
	
	protected String getInsertSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		return "baseColorComic.exportfile.ContentExportFile.getDelSqlCode";
	}
	
	protected String getKey(String[] data)
	{
		return data[0];
	}
	
}
