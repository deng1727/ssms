/*
 * 文件名：VideoAddExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import java.util.Map;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.ProgramByHourExportFileDAO;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 热点内容推荐数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author 
 * @version
 */
public class RecommendByHourExportFile extends BaseExportFile
{

	/**
     * 节目ID列表
     */
    private Map programIDMap = null;
	public RecommendByHourExportFile()
	{
		
		this.fileName = "a_v-recommend_~DyyyyMMddHH~_[0-9]{6}.txt";
		this.verfFileName = "a_v-recommend_~DyyyyMMddHH~.verf";
		this.isDelTable = false;
		this.mailTitle = "基地热点内容推荐数据物理文件按小时增量数据导入结果";
		this.isByHour=true;
		this.getTimeNum=BaseVideoConfig.GET_TIME_NUM;;
	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		programIDMap = ProgramByHourExportFileDAO.getInstance().getProgramIDMap();
		keyMap=ProgramByHourExportFileDAO.getInstance().getRecommendIDMap();
	}

	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
		super.destroy();
		programIDMap.clear();
		keyMap.clear();
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
			logger.debug("开始验证热点内容推荐物理文件字段格式，recommendId=" + recommendId);
		}

		if (data.length != 4)
		{
			logger.error("字段数不等于4");
			return BaseVideoConfig.CHECK_FAILED;
		}

		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId=" + recommendId
					+ ",recommendId验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		// recommendName
		tmp = data[1];
		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger
					.error("recommendId="
							+ recommendId
							+ ",recommendName验证错误，该字段是必填字段，且长度不超过60个字符错误！recommendName="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// programId
		tmp = data[2];
		if (!this.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId=" + recommendId
					+ ",programId验证出错，长度不超过512个字符！programId=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		 if(!programIDMap.containsKey(tmp))
	        {
	            logger.error("recommendId=" + recommendId
	                         + ",programId验证出错，节目ID列表中不存在此节目ID！programId=" + tmp);
	            return BaseVideoConfig.CHECK_FAILED;
	        }
		
		// sortId
		tmp = data[3];
		if (!this.checkIntegerField("sortId", tmp, 10, false))
		{
			logger.error("recommendId=" + recommendId
					+ ",sortId长度不超过10个数值！sortId=" + tmp);
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
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[0];

		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
	 */
	protected String getInsertSqlCode()
	{
		// insert into t_vo_recommend (recommendName,programId,sortId,UPDATETIME,recommendId)values (?,?,?,sysdate,?)
		return "baseVideo.exportfile.RecommendByHourExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		//update t_vo_recommend  set recommendName=?, programId=?, sortId=? , UPDATETIME=sysdate where recommendId=?
		return "baseVideo.exportfile.RecommendByHourExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		// delete from t_vo_recommend where recommendId=?
		return "baseVideo.exportfile.RecommendByHourExportFile.getDelSqlCode";
	}

	protected Object[] getHasObject(String[] data)
	{
		Object[] object = new Object[1];

		object[0] = data[0];

		return object;
	}

	protected String getHasSqlCode()
	{
		// select 1 from t_vo_recommend where recommendId=? 
		return "baseVideo.exportfile.RecommendByHourExportFile.getHasSqlCode";
	}

	protected String getKey(String[] data)
	{
		return data[0];
	}

}
