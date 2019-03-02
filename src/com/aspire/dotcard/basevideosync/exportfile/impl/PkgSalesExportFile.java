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
		this.mailTitle = "�»�����Ƶ��Ʒ�������Ʒ�����ͬ�����";
		this.fileDir = "output\\full";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		//keyMap = BaseVideoDAO.getInstance().getPkgSalesIDMap();
	}
	
	/**
	 * ���ڻ�������
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
			logger.debug("��ʼ��֤��Ʒ�������Ʒ������ļ��ֶθ�ʽ��prdpackID=" + prdpackID);
		}
		
		if (data.length != 6)
		{
			logger.error("�ֶ���������6");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//prdpackID
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",prdpackID��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesProductId
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesProductId��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ��� salesProductId=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesDiscount
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesDiscount��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���salesDiscount=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesCategory
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesCategory��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����2���ַ��� salesCategory=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesStartTime
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 19, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesStartTime��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ��� salesStartTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// salesEndTime
		tmp = data[5];
		if (!BaseFileTools.checkFieldLength(tmp, 19, true))
		{
			logger.error("prdpackID=" + prdpackID
					+ ",salesEndTime��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����19���ַ��� salesEndTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	protected Object[] getObject(String[] data) {
       Object[] object = new Object[6];
		
		object[0] = data[2];
		if("�������MM����".equals(object[0]))
		{
			object[0] = "20Ԫ��������";
		}
		object[1] = data[3];
		object[2] = data[4];
		object[3] = data[5];
		object[4] = data[1];
		object[5] = data[0];
		
		return object;
	}
	
	protected String getKey(String[] data) {
		//��Ʒ��ID�ʹ�����ƷID��ΪΨһ����
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
