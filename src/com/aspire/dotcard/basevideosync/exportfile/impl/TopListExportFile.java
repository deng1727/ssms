package com.aspire.dotcard.basevideosync.exportfile.impl;

import java.io.File;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class TopListExportFile extends BaseExportFile{

	public TopListExportFile()
	{
		this.tableName = "T_V_LIST_PUBLISH";
		this.fileName = "~DyyyyMMdd~"+File.separator+"i_~DyyyyMMdd~_week.txt";
		this.verfFileName = "~DyyyyMMdd~"+File.separator+"i_~DyyyyMMdd~_week.verf";
		this.mailTitle = "基地视频榜单数据同步结果";
		this.fileDir = "toplist";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getBankNameAndProgramIDMap();
	}

	protected String checkData(String[] data) {
		String tmp = data[2];
		String bankName = tmp;
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证视频榜单文件字段格式，bankName=" + bankName);
		}
		
		if (data.length != 11)
		{
			logger.error("字段数不等于11");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//bankName
		if (!BaseFileTools.checkFieldLength(tmp, 50, true))
		{
			logger.error("bankName=" + bankName
					+ ",bankName验证错误，该字段是必填字段，长度不超过50个字符！");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// PLATFORM
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 10, true))
		{
			logger.error("bankName=" + bankName
					+ ",PLATFORM验证错误，该字段是必填字段，长度不超过10个字符！ PLATFORM=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// sortid
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("bankName=" + bankName
					+ ",SORTID验证错误，该字段是必填字段，长度不超过20个字符！SORTID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// PROGRAMID
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("bankName=" + bankName
					+ ",PROGRAMID验证错误，该字段是必填字段，长度不超过20个字符！ PROGRAMID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// PROGRAMNAME
		tmp = data[5];
		if (!BaseFileTools.checkFieldLength(tmp, 100, false))
		{
			logger.error("bankName=" + bankName
					+ ",PROGRAMNAME验证错误，该字段是必填字段，长度不超过100个字符！ PROGRAMNAME=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// VAUTHOR
		tmp = data[6];
		if (!BaseFileTools.checkFieldLength(tmp, 50, false))
		{
			logger.error("bankName=" + bankName
					+ ",VAUTHOR验证错误，该字段是必填字段，长度不超过50个字符！ VAUTHOR=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// DETAIL
		tmp = data[7];
		if (!BaseFileTools.checkFieldLength(tmp, 1024, false))
		{
			logger.error("bankName=" + bankName
					+ ",DETAIL验证错误，该字段是必填字段，长度不超过1024个字符！ DETAIL=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// WWWURL
		tmp = data[8];
		if (!BaseFileTools.checkFieldLength(tmp, 100, false))
		{
			logger.error("bankName=" + bankName
					+ ",WWWURL验证错误，该字段是必填字段，长度不超过100个字符！ WWWURL=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// WAPURL
		tmp = data[9];
		if (!BaseFileTools.checkFieldLength(tmp, 100, false))
		{
			logger.error("bankName=" + bankName
					+ ",WAPURL验证错误，该字段是必填字段，长度不超过100个字符！ WAPURL=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// ISFINISH
		tmp = data[10];
		if (!BaseFileTools.checkFieldLength(tmp, 20, false))
		{
			logger.error("bankName=" + bankName
					+ ",ISFINISH验证错误，该字段是必填字段，长度不超过100个字符！ ISFINISH=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	protected Object[] getObject(String[] data) {
		Object[] object = new Object[10];
		
		object[0] = data[2];
		object[1] = data[3];
		object[2] = data[5];
		object[3] = data[6];
		object[4] = data[7];
		object[5] = data[8];
		object[6] = data[9];
		object[7] = data[10];
		object[8] = data[4];
		object[9] = data[1];
		return object;
	}
	
	protected String getKey(String[] data) {
		return  data[4]+"|"+data[1];
	}
	
	
	protected String getInsertSqlCode() {
		// insert into T_V_LIST_PUBLISH (bankname,sortid,programname,vauthor,detail,wwwurl,wapurl,isfinish,updatetime,programid,platform) values (?,?,?,?,?,?,?,?,sysdate,?,?)
		return "com.aspire.dotcard.basevideonew.exportfile.impl.TopListExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update T_V_LIST_PUBLISH t set t.bankname=?,t.sortid=?,t.programname=?,t.vauthor=?,t.detail=?,t.wwwurl=?,t.wapurl=?,t.isfinish=?,t.updatetime = sysdate where t.programid = ? and t.platform =?
		return "com.aspire.dotcard.basevideonew.exportfile.impl.TopListExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from T_V_LIST_PUBLISH t where t.programid = ? and t.platform =?
		return "com.aspire.dotcard.basevideonew.exportfile.impl.TopListExportFile.getDelSqlCode";
	}


}

