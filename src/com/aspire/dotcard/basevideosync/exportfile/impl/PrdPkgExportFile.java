package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class PrdPkgExportFile extends BaseExportFile{

	public PrdPkgExportFile()
	{
		this.tableName = "t_v_propkg";
		this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PRD_PKG.txt";
		this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PRD_PKG.verf";
		this.mailTitle = "新基地视频业务产品数据同步结果";
		this.fileDir = "output\\full";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getServiceIDMap();
	}

	protected String checkData(String[] data) {
		String servid = data[0];
		String tmp = servid;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证业务产品文件字段格式，servid=" + servid);
		}
		
		if (data.length != 8)
		{
			logger.error("字段数不等于8");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//servid
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("servid=" + servid
					+ ",servid验证错误，该字段是必填字段，长度不超过21个字符！");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// Prodname
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 4000, true)) {
			logger.error("servid=" + servid
					+ ",Prodname验证错误，该字段是必填字段，长度不超过4000个字符！ Prodname=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// PROPKG_PARENTID
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false)) {
			logger.error("servid=" + servid
					+ ",PROPKG_PARENTID验证错误，该字段是必填字段，长度不超过21个字符！ PROPKG_PARENTID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// Protype
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 21, true)) {
			logger.error("servid=" + servid
					+ ",Protype验证错误，该字段是必填字段，长度不超过21个字符！Protype=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// propkgtype
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",propkgtype验证错误，该字段长度不超过21个字符！ propkgtype=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// monthfeecode
		tmp = data[5];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",monthfeecode验证错误，该字段是必填字段，长度不超过21个字符！ monthfeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// dotfeecode
		tmp = data[6];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",propkgtype验证错误，该字段是必填字段，长度不超过21个字符！ dotfeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// freefeecode
		tmp = data[7];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",freefeecode验证错误，该字段是必填字段，长度不超过21个字符！ freefeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	protected Object[] getObject(String[] data) {
		Object[] object = new Object[8];
		
		object[0] = data[1];
		if("内容输出MM包月".equals(object[0]))
		{
			object[0] = "20元订购包月";
		}
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		object[4] = data[5];
		object[5] = data[6];
		object[6] = data[7];
		object[7] = data[0];
		
		return object;
	}
	
	protected String getKey(String[] data) {
		return  data[0];
	}
	
	protected String getInsertSqlCode() {
		// insert into t_v_propkg (id,Prodname, PROPKG_PARENTID,Protype, propkgtype, monthfeecode, dotfeecode, freefeecode,lupdate,Servid) values (SEQ_T_V_PROPKG_ID.nextval,?,?,?,?,?,?,?,sysdate,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PrdPkgExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_propkg t set t.Prodname=?,PROPKG_PARENTID=?,Protype=?,propkgtype=?,monthfeecode=?,dotfeecode=?,freefeecode=?,lupdate=sysdate where Servid=?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PrdPkgExportFile.getUpdateSqlCode";
	}
	
	@Override
	protected void destroy() {
		keyMap.clear();
	}

	protected String getDelSqlCode() {
		// delete from t_v_propkg t where t.Servid=?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PrdPkgExportFile.getDelSqlCode";
	}

}
