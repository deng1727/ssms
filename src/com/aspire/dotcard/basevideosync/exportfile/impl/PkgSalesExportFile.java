package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class PkgSalesExportFile extends BaseExportFile{

	public PkgSalesExportFile()
	{
		this.tableName = "t_v_pkgsales";
		this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PKG_SALES.txt";
		this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PKG_SALES.verf";
		this.mailTitle = "新基地视频产品包促销计费数据同步结果";
		this.fileDir = "output\\full";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		//keyMap = BaseVideoDAO.getInstance().getPkgSalesIDMap();
	}
	
	/**
	 * 用于回收数据
	 */
	public void destroy()
	{
		keyMap.clear();
	}

	protected String checkData(String[] data) {
		String prdpackID = data[0];
		String tmp = prdpackID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证产品包促销计费数据文件字段格式，prdpackID=" + prdpackID);
		}
		
		if (data.length != 6)
		{
			logger.error("字段数不等于6");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//prdpackID
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",prdpackID验证错误，该字段是必填字段，长度不超过21个字符！");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesProductId
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesProductId验证错误，该字段是必填字段，长度不超过21个字符！ salesProductId=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesDiscount
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesDiscount验证错误，该字段是必填字段，长度不超过20个字符！salesDiscount=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesCategory
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesCategory验证错误，该字段是必填字段，长度不超过2个字符！ salesCategory=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesStartTime
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 19, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesStartTime验证错误，该字段是必填字段，长度不超过19个字符！ salesStartTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesEndTime
		tmp = data[5];
		if (!BaseFileTools.checkFieldLength(tmp, 19, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesEndTime验证错误，该字段是必填字段，长度不超过19个字符！ salesEndTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	protected Object[] getObject(String[] data) {
       Object[] object = new Object[6];
		
		object[0] = data[2];
		if("内容输出MM包月".equals(object[0]))
		{
			object[0] = "20元订购包月";
		}
		object[1] = data[3];
		object[2] = data[4];
		object[3] = data[5];
		object[4] = data[1];
		object[5] = data[0];
		
		return object;
	}
	
	protected String getKey(String[] data) {
		//产品包ID和促销产品ID作为唯一索引
		return data[0]+ "|" + data[1];
	}
	
	protected String getInsertSqlCode() {
		// insert into t_v_pkgsales(id,salesdiscount,salescategory,salesstarttime,salesendtime,lupdate,salesproductid,prdpack_id) values(SEQ_T_V_PKGSALES_ID.nextval,?,?,?,?,sysdate,?,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile.getInsertSqlCode";
	}

	protected String getUpdateSqlCode() {
		// update t_v_pkgsales set salesdiscount =?,salescategory =?,salesstarttime =?,salesendtime =?,lupdate =sysdate where salesproductid =? and prdpack_id = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_pkgsales s where s.prdpack_id = ? and s.salesproductid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.PkgSalesExportFile.getDelSqlCode";
	}

}
