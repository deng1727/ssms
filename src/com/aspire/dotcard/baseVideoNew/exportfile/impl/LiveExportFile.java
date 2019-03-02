/*
 * 文件名：LiveExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.util.Iterator;
import java.util.Map;

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
public class LiveExportFile extends BaseExportFile {
	/**
	 * 栏目ID列表
	 */
	private Map<String, String> programNodeIDMap = null;

	public LiveExportFile() {
		this.tableName = "t_vo_live";
		this.fileName = "i_v-live-new_~DyyyyMMdd~_[0-9]{6}.txt";
		this.verfFileName = "i_v-live-new_~DyyyyMMdd~.verf";
		this.mailTitle = "基地直播节目数据导入结果";
	}

	/**
	 * 用于添加准备动作数据
	 */
	public void init() {
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getLiveIDMap();
		programNodeIDMap = getCheckMidIDMap(BaseVideoNewFileDAO.getInstance()
				.getVideoIDMap(), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("A"), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("U"), BaseVideoNewFileDAO.getInstance()
				.getVideoMidIDMap("D"));
	}

	/**
	 * 得到用于校验数据合法性的正式表与中间表合表数据
	 * 
	 * @param map
	 *            正式表數據集
	 * @param mapAdd
	 *            中間表新增數據集
	 * @param mapDel
	 *            中間表刪除數據集
	 * @return 合表數據集
	 */
	public Map<String, String> getCheckMidIDMap(Map<String, String> map,
			Map<String, String> mapAdd, Map<String, String> mapUpdate,
			Map<String, String> mapDel) {

		Iterator<String> it = mapDel.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (map.containsKey(key)) {
				map.remove(key);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("uniall map and mapadd");
		}
		map.putAll(mapAdd); // 逻辑要和存储过程合并正式表一致
		map.putAll(mapUpdate); // 逻辑要和存储过程合并正式表一致
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java
	 * .lang.String[])
	 */
	protected String checkData(String[] data, boolean flag) {
		String videoId = data[0];
		String tmp = videoId;

		if (logger.isDebugEnabled()) {
			logger.debug("开始验证直播节目数据字段格式，videoId=" + videoId);
		}

		if (data.length != 4) {
			logger.error("字段数不等于4");
			return BaseVideoConfig.CHECK_FAILED;
		}

		// videoId
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true)) {
			logger.error("videoId=" + videoId
					+ ",videoId验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		if (!programNodeIDMap.containsKey(tmp)) {
			logger.error("videoId=" + tmp + ",此条记录验证出错，节目详情中不存在此记录对应关系！");
			return BaseVideoConfig.CHECK_FAILED;
		}

		// startTime
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true)) {
			logger.error("videoId=" + videoId
					+ ",startTime验证出错，该字段是必填字段，长度不超过14个字符！startTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// endTime
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true)) {
			logger.error("videoId=" + videoId
					+ ",endTime验证出错，该字段是必填字段，长度不超过14个字符！endTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// liveName
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 200, true)) {
			logger.error("videoId=" + videoId
					+ ",liveName验证出错，该字段是必填字段，长度不超过200个字符！liveName=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java
	 * .lang.String[])
	 */
	protected Object[] getObject(String[] data) {
		return new Object[] { data[0], data[3], data[1], data[2],
				data[0] + data[1] + data[2]};
	}

	protected String getKey(String[] data) {
		// 直播节目单 要加上开始时间 作为唯一索引
		return data[0] + "|" + data[1] ;
	}

	protected String getInsertSqlCode() {
		// insert into t_vo_live_mid (videoid, livename, starttime,endtime,sid, STATUS) 
		//values (?,?,?,?,?,'A')
		return "baseVideoNew.exportfile.LiveExportFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode() {
		// insert into t_vo_live_mid (videoid, livename, starttime,endtime,sid, STATUS)
		//values (?,?,?,?,?,'U')
		return "baseVideoNew.exportfile.LiveExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode() {
		// insert into t_vo_live_mid (videoid, starttime, status, LIVENAME, ENDTIME) values (?,?,'D','temp','temp')
		return "baseVideoNew.exportfile.LiveExportFile.getDelSqlCode";
	}

	/**
	 * 用于回收数据
	 */
	protected void clear() {
		super.clear();
		programNodeIDMap.clear();
	}
}
