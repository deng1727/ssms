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
		this.mailTitle = "�»�����Ƶ�Ʒ�����ͬ�����";
		this.fileDir = "output\\full";
	}
	
	/**
	 * �������׼����������
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
			logger.debug("��ʼ��֤�Ʒ������ļ��ֶθ�ʽ��FEEcode=" + FEEcode);
		}
		
		if (data.length != 4)
		{
			logger.error("�ֶ���������4");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//FEEcode
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",FEEcode��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���,FEEcode=" + FEEcode);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// feetype
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 4, true))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",feetype��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4���ַ���feetype="+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// fee
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 8, false))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",fee��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����8���ַ���fee=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// productDesc
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 2000, false))
		{
			logger.error("FEEcode=" + FEEcode
					+ ",productDesc��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2000���ַ���productDesc=" + tmp);
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
