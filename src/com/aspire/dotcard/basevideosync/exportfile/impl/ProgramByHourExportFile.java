package com.aspire.dotcard.basevideosync.exportfile.impl;

import com.aspire.dotcard.basevideosync.config.BaseVideoConfig;
import com.aspire.dotcard.basevideosync.dao.BaseVideoDAO;
import com.aspire.dotcard.basevideosync.exportfile.BaseExportFile;
import com.aspire.dotcard.basevideosync.exportfile.BaseFileTools;

public class ProgramByHourExportFile extends BaseExportFile{

	/**
	 * ��ȡ�ϴ�������ʱ���ʱ��
	 */
	private String lastUpDate = null;
	
	public ProgramByHourExportFile()
	{
		this.tableName = "t_v_sprogram";
		//this.fileName = "~Dyyyy-MM-dd\\yyyy-MM-dd-HH~_PROGRAM.txt";
		this.fileName = "~DyyyyMMdd~.txt";
		this.verfFileName = "~Dyyyy-MM-dd\\yyyy-MM-dd-HH~_PROGRAM.verf";
		this.mailTitle = "�»�����Ƶ��ͨ��Ŀ��������ͬ�����";
		this.isByHour=true;
		this.hasVerf = false;
		this.getTimeNum=BaseVideoConfig.GET_TIME_NUM;
		this.fileDir = "output\\updatecontent";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoDAO.getInstance().getsProgramIDMap();
		lastUpDate = BaseVideoDAO.getInstance().getLastUpdateTime();
	}

	/**
	 * ���ڻ�������
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
			logger.debug("��ʼ��֤��ͨ��Ŀ�ļ��ֶθ�ʽ��programID=" + programID);
		}
		
		if (data.length != 5)
		{
			logger.error("�ֶ���������5");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (tmp == null || tmp.trim().length() != 9)
		{
			logger.error("programID=" + programID
					+ ",programID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��ȱ������9");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// CMSID
		tmp = data[1];
		if (tmp == null || tmp.trim().length() != 10)
		{
			logger.error("programID=" + programID
					+ ",CMSID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��ȱ������10��CMSID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// chananleID
		tmp = data[2];
		if (!BaseFileTools.checkFieldLength(tmp, 128, false))
		{
			logger.error("programID=" + programID
					+ ",chananleID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����128���ַ���chananleID="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// publishDate
		tmp = data[3];
		if (!BaseFileTools.checkFieldLength(tmp, 20, true))
		{
			logger.error("programID=" + programID
					+ ",publishDate��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����20���ַ���publishDate=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if(keyMap.containsKey(programID)){
			if(BaseFileTools.compare_date(lastUpDate, tmp)){
				logger.error("programID=" + programID
						+ ",publishDate��֤�������������Ѿ����¹��ˣ�publishDate="
						+ tmp +"���ϴθ���ʱ��СlastUpDate="+lastUpDate);
				return BaseVideoConfig.CHECK_FAILED;
			}
		}
		// status
		tmp = data[4];
		if (!BaseFileTools.checkFieldLength(tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",status��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2���ַ���status="
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
