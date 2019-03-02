/*
 * �ļ�����VideoAddExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.ProgramByHourExportFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: �ȵ������Ƽ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author 
 * @version
 */
public class RecommendByHourExportFile extends BaseExportFile
{

	/**
     * ��ĿID�б�
     */
    private Map programIDMap = null;
	public RecommendByHourExportFile()
	{
		
		this.fileName = "a_v-recommend_~DyyyyMMddHH~_[0-9]{6}.txt";
		this.verfFileName = "a_v-recommend_~DyyyyMMddHH~.verf";
		this.isDelTable = false;
		this.mailTitle = "�����ȵ������Ƽ����������ļ���Сʱ�������ݵ�����";
		this.isByHour=true;
		this.getTimeNum=BaseVideoNewConfig.GET_TIME_NUM;
		this.isDelMidTable = false;
	}

	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		programIDMap = ProgramByHourExportFileDAO.getInstance().getProgramIDMap();
		keyMap=ProgramByHourExportFileDAO.getInstance().getRecommendIDMap();
	}

	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
		programIDMap.clear();
		keyMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
	{
		String recommendId = data[0];
		String tmp = recommendId;

		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤�ȵ������Ƽ������ļ��ֶθ�ʽ��recommendId=" + recommendId);
		}

		if (data.length != 4)
		{
			logger.error("�ֶ���������4");
			return BaseVideoNewConfig.CHECK_FAILED;
		}

		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId=" + recommendId
					+ ",recommendId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// recommendName
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger
					.error("recommendId="
							+ recommendId
							+ ",recommendName��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����60���ַ�����recommendName="
							+ tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// programId
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("recommendId=" + recommendId
					+ ",programId��֤�������Ȳ�����512���ַ���programId=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}
		// if(!programIDMap.containsKey(tmp))
		if(!checkProgramId(tmp,programIDMap))
	        {
	            logger.error("recommendId=" + recommendId
	                         + ",programId��֤������ĿID�б��в����ڴ˽�ĿID��programId=" + tmp);
	            return BaseVideoNewConfig.CHECK_FAILED;
	        }
		
		// sortId
		tmp = data[3];
		if (!BaseFileNewTools.checkIntegerField("sortId", tmp, 10, false))
		{
			logger.error("recommendId=" + recommendId
					+ ",sortId���Ȳ�����10����ֵ��sortId=" + tmp);
			return BaseVideoNewConfig.CHECK_FAILED;
		}

		return BaseVideoNewConfig.CHECK_DATA_SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[4];

		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[0];

		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
	 */
	protected String getInsertSqlCode()
	{
		// insert into t_vo_recommend (recommendName,programId,sortId,UPDATETIME,recommendId)values (?,?,?,sysdate,?)
		return "baseVideo.exportfile.RecommendByHourExportFile.getInsertSqlCode";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
	 */
	protected String getUpdateSqlCode()
	{
		//update t_vo_recommend  set recommendName=?, programId=?, sortId=? , UPDATETIME=sysdate where recommendId=?
		return "baseVideo.exportfile.RecommendByHourExportFile.getUpdateSqlCode";
	}

	protected String getDelSqlCode()
	{
		// delete from t_vo_recommend where recommendId=?
		return "baseVideo.exportfile.RecommendByHourExportFile.getDelSqlCode";
	}

	protected Object[] getHasObject(String[] data)
	{
		Object[] object = new Object[1];

		object[0] = data[0];

		return object;
	}

	protected String getHasSqlCode()
	{
		// select 1 from t_vo_recommend where recommendId=? 
		return "baseVideo.exportfile.RecommendByHourExportFile.getHasSqlCode";
	}

	protected String getKey(String[] data)
	{
		return data[0];
	}
	
	protected boolean checkProgramId(String programid,Map map){
		boolean status = false;
		Set ts =	map.entrySet();
		Iterator it =	ts.iterator();
		
		while(it.hasNext()){
			Entry ey = (Entry) it.next();
			String key = (String)ey.getKey();
			if(key.indexOf(programid+"|")>=0){
				status = true;
				return status;
			}
			
		}
		return status;
	}

}
