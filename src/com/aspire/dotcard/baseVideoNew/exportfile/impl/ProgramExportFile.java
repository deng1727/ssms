/*
 * �ļ�����ProgramExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.util.Map;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseExportFile;
import com.aspire.dotcard.baseVideoNew.exportfile.BaseFileNewTools;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ProgramExportFile extends BaseExportFile
{
	/**
	 * ��ĿID�б�
	 */
	private Map<String, String> nodeIDMap = null;
	
	/**
	 * ��ƵID�б�
	 */
	private Map<String, String> videoIDMap = null;
	
	public ProgramExportFile()
	{
		this.tableName = "t_vo_program";
		this.fileName = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.txt";
		this.gzFileName = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.tar.gz";
		this.verfFileName = "i_v-videodetail_~DyyyyMMdd~.verf";
		this.mailTitle = "���ؽ�Ŀ�������ļ����ݵ�����";
	}
	
	/**
	 * �������׼����������
	 */
	public void init()
	{
		super.init();
		keyMap = BaseVideoNewFileDAO.getInstance().getProgramIDMap();
		nodeIDMap = BaseVideoNewFileDAO.getInstance().getCheckMidIDMap(
				BaseVideoNewFileDAO.getInstance().getNodeIDMap(),
				BaseVideoNewFileDAO.getInstance().getNodeMidIDMap("A"),
				BaseVideoNewFileDAO.getInstance().getNodeMidIDMap("D"));
		videoIDMap = BaseVideoNewFileDAO.getInstance().getCheckMidIDMap(
				BaseVideoNewFileDAO.getInstance().getVideoIDMap(),
				BaseVideoNewFileDAO.getInstance().getVideoMidIDMap("A"),
				BaseVideoNewFileDAO.getInstance().getVideoMidIDMap("D"));
	}
	
	/**
	 * ���ڻ�������
	 */
	public void destroy()
	{
	}
	
	/**
	 * �õ�ʱ�����ת��Ϊ��
	 * 
	 * @param timeLength
	 * @return
	 */
	private String getTimeLength(String timeLength)
	{
		double milliSeconds = Double.parseDouble(timeLength);
		return String.valueOf((int) (milliSeconds / 1000));
	}
	
	/**
	 * �����õ���ʾʱ�� ����ʽΪ00:00:00
	 * 
	 * @param timeLength
	 * @return 00:00:00
	 */
	private String getShowTime(String timeLength)
	{
		StringBuffer showTime = new StringBuffer();
		double milliSeconds = Double.parseDouble(timeLength);
		double second = milliSeconds / 1000;
		int hours = (int) (second / 3600);
		int hoursY = (int) (second % 3600);
		int minutes = hoursY / 60;
		int seconds = hoursY % 60;
		
		if (hours < 10)
		{
			showTime.append("0");
		}
		showTime.append(hours).append(":");
		
		if (minutes < 10)
		{
			showTime.append("0");
		}
		showTime.append(minutes).append(":");
		
		if (seconds < 10)
		{
			showTime.append("0");
		}
		showTime.append(seconds);
		
		return showTime.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
	 */
	protected String checkData(String[] data,boolean flag)
	{
		String programID = data[0];
		String tmp = programID;
		
		if (logger.isDebugEnabled())
		{
			logger.debug("��ʼ��֤��Ŀ�������ļ��ֶθ�ʽ��programID=" + programID);
		}
		
		if (data.length != 12)
		{
			logger.error("�ֶ���������12");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("programID=" + programID
					+ ",programID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// videoID
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("programID=" + programID
					+ ",videoID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����60���ַ�����videoID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// ��ʱע��FOR TEST _AIYAN 2012-07-14
		if (flag && !videoIDMap.containsKey(tmp))
		{
			logger.error("programID=" + programID
					+ ",videoID��֤������Ƶ�б��в����ڴ���ƵID��videoID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// programName
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("programID=" + programID
					+ ",programName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����128���ַ���programName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// nodeID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, true))
		{
			logger.error("programID=" + programID
					+ ",nodeID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���nodeID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// ��ʱע��FOR TEST _AIYAN 2012-07-14
		if (flag && !nodeIDMap.containsKey(tmp))
		{
			logger.error("programID=" + programID
					+ ",nodeID��֤������Ŀ�б��в����ڴ���ĿID��nodeID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// description
		tmp = data[4];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, true))
		{
			logger.error("programID=" + programID
					+ ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// logoPath
		tmp = data[5];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, false))
		{
			logger.error("programID=" + programID
					+ ",logoPath��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���logoPath=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// timeLength
		tmp = data[6];
		if (!BaseFileNewTools.checkIntegerField("ʱ��", tmp, 12, true))
		{
			logger
					.error("programID="
							+ programID
							+ ",timeLength��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12����ֵ���ȣ�timeLength="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// lastUpTime
		tmp = data[7];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
		{
			logger.error("programID=" + programID
					+ ",lastUpTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���lastUpTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// programType
		tmp = data[8];
		if (!BaseFileNewTools.checkIntegerField("��Ŀ����", tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",programType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2����ֵ���ȣ�programType="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if ("12".indexOf(tmp) == -1)
		{
			logger.error("programID=" + programID
					+ ",programType��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ1��2! programType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		 // sortId
        tmp = data[9];
        if (!BaseFileNewTools.checkIntegerField("�������",tmp, 10, false))
        {
            logger.error("programID=" + programID
                         + ",sortId��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����10���ַ���sortId="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        
        // isLink
        tmp = data[10];
        if (!BaseFileNewTools.checkFieldLength(tmp, 10, true))
        {
            logger.error("programID=" + programID
                         + ",isLink��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����10���ַ���isLink="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }

        // productid
        tmp = data[11];
        if (!BaseFileNewTools.checkFieldLength(tmp, 1024, false))
        {
            logger.error("programID=" + programID
                         + ",productid��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����1024���ַ���productid="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
		
		return BaseVideoConfig.CHECK_DATA_SUCCESS;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
	 */
	protected Object[] getObject(String[] data)
	{
		Object[] object = new Object[15];
		
		object[0] = data[1];
		object[1] = data[2];
		object[2] = data[3];
		object[3] = data[4];
		
		object[4] = BaseVideoConfig.logoPath;// ��дLOGOPATH
		object[5] = data[5];// ��дFTPLOGOPATH
		if (data[5] != null && !data[5].equals(""))
		{
			String ftplogo = (String) data[5];
			if (ftplogo.startsWith("/"))
			{
				object[6] = BaseVideoConfig.ProgramLogoPath + data[5];// ��дp.TRUELOGOPATH
			}
			else
			{
				object[6] = BaseVideoConfig.ProgramLogoPath + "/" + data[5];// ��дp.TRUELOGOPATH
			}
		}
		object[7] = getTimeLength(data[6]);
		object[8] = getShowTime(data[6]);
		object[9] = data[7];
		object[10] = data[8];
		object[11] = data[9];
		object[12] = data[10];
		object[13] = data[11];
		object[14] = data[0];
		
		return object;
	}
	
	protected String getKey(String[] data)
	{
		return data[0]+ "|" + data[3] ;
	}
	
	protected String getInsertSqlCode()
	{
		// insert into t_vo_program_mid (videoid, programname, nodeid, description, logopath,ftplogopath,TRUELOGOPATH, timelength, showtime, lastuptime, programtype, sortid, islink, productid, exporttime, programid, status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?, 'A')
		return "baseVideoNew.exportfile.ProgramExportFile.getInsertSqlCode";
	}
	
	protected String getUpdateSqlCode()
	{
		// insert into t_vo_program_mid (videoid, programname, nodeid, description, logopath,ftplogopath,TRUELOGOPATH, timelength, showtime, lastuptime, programtype, sortid, islink, productid, exporttime, programid, status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?, 'U')
		//baseVideoNew.exportfile.ProgramExportFile.getDelSqlCode=insert into t_vo_program_mid (programid, status, videoid, programname, nodeid, description, logopath, timelength, showtime, lastuptime, programtype, exporttime) values (?, 'D','temp','temp','temp',?,'temp',0,'temp','temp',0,sysdate)
		return "baseVideoNew.exportfile.ProgramExportFile.getUpdateSqlCode";
	}
	
	protected String getDelSqlCode()
	{
		// insert into t_vo_program_mid (programid, status) values (?, 'D')
		return "baseVideoNew.exportfile.ProgramExportFile.getDelSqlCode";
	}
	
	/**
	 * ���ڻ�������
	 */
	protected void clear()
	{
		super.clear();
		nodeIDMap.clear();
		videoIDMap.clear();
	}
}
