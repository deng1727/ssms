package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class ProductExportFile extends BaseExportFile {

	public ProductExportFile()
	{
		this.tableName = "t_v_product";
		this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PRODUCT.txt";
		this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd~_PRODUCT.verf";
		this.mailTitle = "新基地视频计费数据同步结果";
		this.fileDir = "output\\full";
	}
	
	/**
	 * 用于添加准备动作数据
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getFEEcodeMap();
	}

	protected String checkData(String[] data) {
		String FEEcode = data[0];
		String tmp = FEEcode;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("开始验证计费数据文件字段格式，FEEcode=" + FEEcode);
		}
		
		if (data.length != 4)
		{
			logger.error("字段数不等于4");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//FEEcode
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",FEEcode验证错误，该字段是必填字段，长度不超过21个字符！,FEEcode=" + FEEcode);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feetype
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 4, true))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",feetype验证出错，该字段是必填字段，长度不超过4个字符！feetype="+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 8, false))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",fee验证出错，该字段是必填字段，长度不超过8个字符！fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productDesc
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 2000, false))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",productDesc验证出错，该字段是必填字段，长度不超过2000个字符！productDesc=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	protected Object[] getObject(String[] data) {
        Object[] object = new Object[4];
		
        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        object[3] = data[0];
        
        return object;
	}

	protected String getKey(String[] data) {
		return data[0];
	}
	
	protected String getInsertSqlCode() {
		// insert into t_v_product(id,feetype,fee,productdesc,lupdate,feecode) values(SEQ_T_V_PRODUCT_ID.nextval,?,?,?,sysdate,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProductExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_product set feetype = ?, fee = ?, productdesc = ?, lupdate = sysdate where feecode = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProductExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_product p where p.feecode = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.ProductExportFile.getDelSqlCode";
	}

}
