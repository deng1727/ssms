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
		this.mailTitle = "�»�����Ƶҵ���Ʒ����ͬ�����";
		this.fileDir = "output\\full";
	}
	
	/**
	 * �������׼����������
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
			logger.debug("��ʼ��֤ҵ���Ʒ�ļ��ֶθ�ʽ��servid=" + servid);
		}
		
		if (data.length != 8)
		{
			logger.error("�ֶ���������8");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//servid
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("servid=" + servid
					+ ",servid��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// Prodname
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 4000, true)) {
			logger.error("servid=" + servid
					+ ",Prodname��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ��� Prodname=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// PROPKG_PARENTID
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false)) {
			logger.error("servid=" + servid
					+ ",PROPKG_PARENTID��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ��� PROPKG_PARENTID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}

		// Protype
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 21, true)) {
			logger.error("servid=" + servid
					+ ",Protype��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���Protype=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// propkgtype
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",propkgtype��֤���󣬸��ֶγ��Ȳ�����21���ַ��� propkgtype=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// monthfeecode
		tmp = data[5];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",monthfeecode��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ��� monthfeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// dotfeecode
		tmp = data[6];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",propkgtype��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ��� dotfeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// freefeecode
		tmp = data[7];
		if (!BaseFileTools.checkFieldLength(tmp, 21, false))
		{
			logger.error("servid=" + servid
					+ ",freefeecode��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ��� freefeecode=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}

	protected Object[] getObject(String[] data) {
		Object[] object = new Object[8];
		
		object[0] = data[1];
		if("�������MM����".equals(object[0]))
		{
			object[0] = "20Ԫ��������";
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
