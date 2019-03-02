package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class ProgramByHourExportFile extends BaseExportFile{

	/**
	 * 获取上次增量的时间的时间
	 */
	private String lastUpDate = null;
	
	public ProgramByHourExportFile()
	{
		this.tableName = "t_v_sprogram";
		//this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd-HH~_PROGRAM.txt";
		this.fileName = "~DyyyyMMdd~.txt";
		this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd-HH~_PROGRAM.verf";
		this.mailTitle = "新基地视频普通节目增量数据同步结果";
		this.isByHour=true;
		this.hasVerf = false;
		this.getTimeNum=BaseVideoConfig.GET_TIME_NUM;
		this.fileDir = "output\\updatecontent";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getsProgramIDMap();
		lastUpDate = BaseVideoDAO.getInstance().getLastUpdateTime();
	}

	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
		keyMap.clear();
		lastUpDate = null;
	}
	
	protected String checkData(String[] data) {
		String programID = data[0];
		String tmp = programID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证普通节目文件字段格式，programID=" + programID);
		}
		
		if (data.length != 5)
		{
			logger.error("字段数不等于5");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (tmp == null || tmp.trim().length() != 9)
		{
			logger.error("programID=" + programID
					+ ",programID验证错误，该字段是必填字段，且长度必须等于9");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// CMSID
		tmp = data[1];
		if (tmp == null || tmp.trim().length() != 10)
		{
			logger.error("programID=" + programID
					+ ",CMSID验证错误，该字段是必填字段，且长度必须等于10！CMSID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// chananleID
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 128, false))
		{
			logger.error("programID=" + programID
					+ ",chananleID验证出错，该字段是必填字段，长度不超过128个字符！chananleID="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// publishDate
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("programID=" + programID
					+ ",publishDate验证出错，该字段是必填字段，长度不超过20个字符！publishDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if(keyMap.containsKey(programID)){
			if(BaseFileTools.compare_date(lastUpDate, tmp)){
				logger.error("programID=" + programID
						+ ",publishDate验证出错，该条数据已经更新过了，publishDate="
						+ tmp +"比上次更新时间小lastUpDate="+lastUpDate);
				return BaseVideoConfig.CHECK_FAILED;
			}
		}
		// status
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",status验证出错，该字段是必填字段，长度不超过2个字符！status="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
        
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	protected Object[] getObject(String[] data) {
        Object[] object = new Object[5];
		
        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        object[3] = data[4];
        object[4] = data[0];
        return object;
	}

	protected String getKey(String[] data) {
		return data[0];
	}

	protected String getInsertSqlCode() {
		// insert into t_v_sprogram (id,cmsid, channelid, pubtime, status, updatetime, programid) values (SEQ_T_V_SPROGRAM_ID.nextval,?,?,?,?,sysdate,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_sprogram p set p.cmsid = ?,p.channelid=?,p.pubtime=?,p.status = ?,p.updatetime = sysdate where p.programid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_sprogram p where p.programid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProgramExportFile.getDelSqlCode";
	}



}
