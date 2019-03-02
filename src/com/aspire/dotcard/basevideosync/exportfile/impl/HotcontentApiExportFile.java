package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class HotcontentApiExportFile extends BaseExportFile{

	public HotcontentApiExportFile()
	{
		this.mailTitle = "�»�����Ƶ�ȵ������б�����api����ͬ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getTitleIDMap();
		this.dataList = BaseVideoDAO.getInstance().getApiRequestParamter("04");
		this.apiRequest = 1;
		this.hasVerf = false;
	}

	protected String checkData(String[] data) {
		
		String titleId = data[0];
		String tmp = titleId;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�ȵ������б��ļ��ֶθ�ʽ��titleId=" + titleId);
		}
		
		if (data.length != 3)
		{
			logger.error("�ֶ���������3");
			return BaseVideoConfig.CHECK_FAILED;
		}
		//titleId
		if (!BaseFileTools.checkFieldLength(tmp, 21, true))
		{
			logger.error("titleId=" + titleId
					+ ",titleId��֤���󣬸��ֶ��Ǳ����ֶΣ����Ȳ�����21���ַ���,titleId=" + titleId);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// titlename
		tmp = data[1];
		if (!BaseFileTools.checkFieldLength(tmp, 50, true))
		{
			logger.error("titleId=" + titleId
					+ ",titlename��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����50���ַ���titlename="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// pubtime
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("titleId=" + titleId
					+ ",pubtime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���pubtime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	protected Object[] getObject(String[] data) {
        Object[] object = new Object[3];
		
        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[0];
        
        return object;
	}
	
	protected String getKey(String[] data) {
		return  data[0];
	}

	protected String getInsertSqlCode() {
		// insert into t_v_hotcontent(id,titlename,pubtime,exestatus,lupdate,titleid) values(SEQ_T_V_HOTCONT_ID.nextval,?,?,'0',sysdate,?)
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode() {
		// update t_v_hotcontent set titlename = ?,pubtime = ?,exestatus ='0',lupdate=sysdate where titleid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode() {
		// delete from t_v_hotcontent h where h.titleid = ?
		return "com.aspire.dotcard.basevideosync.exportfile.impl.HotcontentExportFile.getDelSqlCode";
	}

	@Override
	protected void destroy() {
		keyMap.clear();
	}
	
	
}

