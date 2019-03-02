/*
 * 文件名：ProgramExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
	 * 栏目ID列表
	 */
	private Map<String, String> nodeIDMap = null;
	
	/**
	 * 视频ID列表
	 */
	private Map<String, String> videoIDMap = null;
	
	public ProgramExportFile()
	{
		this.tableName = "t_vo_program";
		this.fileName = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.txt";
		this.gzFileName = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.tar.gz";
		this.verfFileName = "i_v-videodetail_~DyyyyMMdd~.verf";
		this.mailTitle = "基地节目单详情文件数据导入结果";
	}
	
	/**
	 * 用于添加准备动作数据
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
	 * 用于回收数据
	 */
	public void destroy()
	{
	}
	
	/**
	 * 得到时间毫秒转换为秒
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
	 * 用来得到显示时长 ，格式为00:00:00
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
			logger.debug("开始验证节目单详情文件字段格式，programID=" + programID);
		}
		
		if (data.length != 12)
		{
			logger.error("字段数不等于12");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("programID=" + programID
					+ ",programID验证错误，该字段是必填字段，且不超过60个字符");
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// videoID
		tmp = data[1];
		if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
		{
			logger.error("programID=" + programID
					+ ",videoID验证错误，该字段是必填字段，且长度不超过60个字符错误！videoID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// 临时注销FOR TEST _AIYAN 2012-07-14
		if (flag && !videoIDMap.containsKey(tmp))
		{
			logger.error("programID=" + programID
					+ ",videoID验证出错，视频列表中不存在此视频ID！videoID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// programName
		tmp = data[2];
		if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
		{
			logger.error("programID=" + programID
					+ ",programName验证出错，该字段是必填字段，长度不超过128个字符！programName="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// nodeID
		tmp = data[3];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, true))
		{
			logger.error("programID=" + programID
					+ ",nodeID验证出错，该字段是必填字段，长度不超过512个字符！nodeID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// 临时注销FOR TEST _AIYAN 2012-07-14
		if (flag && !nodeIDMap.containsKey(tmp))
		{
			logger.error("programID=" + programID
					+ ",nodeID验证出错，栏目列表中不存在此栏目ID！nodeID=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		// description
		tmp = data[4];
		if (!BaseFileNewTools.checkFieldLength(tmp, 4000, true))
		{
			logger.error("programID=" + programID
					+ ",description验证出错，该字段是必填字段，长度不超过4000个字符！description="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// logoPath
		tmp = data[5];
		if (!BaseFileNewTools.checkFieldLength(tmp, 512, false))
		{
			logger.error("programID=" + programID
					+ ",logoPath验证出错，该字段是必填字段，长度不超过512个字符！logoPath=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// timeLength
		tmp = data[6];
		if (!BaseFileNewTools.checkIntegerField("时长", tmp, 12, true))
		{
			logger
					.error("programID="
							+ programID
							+ ",timeLength验证出错，该字段是必填字段，长度不超过12个数值长度！timeLength="
							+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// lastUpTime
		tmp = data[7];
		if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
		{
			logger.error("programID=" + programID
					+ ",lastUpTime验证出错，该字段是必填字段，长度不超过14个字符！lastUpTime=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		// programType
		tmp = data[8];
		if (!BaseFileNewTools.checkIntegerField("节目类型", tmp, 2, true))
		{
			logger.error("programID=" + programID
					+ ",programType验证出错，该字段是必填字段，长度不超过2个数值长度！programType="
					+ tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		if ("12".indexOf(tmp) == -1)
		{
			logger.error("programID=" + programID
					+ ",programType验证错误，该字段是必填字段，且只能为1、2! programType=" + tmp);
			return BaseVideoConfig.CHECK_FAILED;
		}
		
		 // sortId
        tmp = data[9];
        if (!BaseFileNewTools.checkIntegerField("排列序号",tmp, 10, false))
        {
            logger.error("programID=" + programID
                         + ",sortId验证出错，该字段是必填字段，长度不超过10个字符！sortId="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        
        // isLink
        tmp = data[10];
        if (!BaseFileNewTools.checkFieldLength(tmp, 10, true))
        {
            logger.error("programID=" + programID
                         + ",isLink验证出错，该字段是必填字段，长度不超过10个字符！isLink="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }

        // productid
        tmp = data[11];
        if (!BaseFileNewTools.checkFieldLength(tmp, 1024, false))
        {
            logger.error("programID=" + programID
                         + ",productid验证出错，该字段是必填字段，长度不超过1024个字符！productid="
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
		
		object[4] = BaseVideoConfig.logoPath;// 填写LOGOPATH
		object[5] = data[5];// 填写FTPLOGOPATH
		if (data[5] != null && !data[5].equals(""))
		{
			String ftplogo = (String) data[5];
			if (ftplogo.startsWith("/"))
			{
				object[6] = BaseVideoConfig.ProgramLogoPath + data[5];// 填写p.TRUELOGOPATH
			}
			else
			{
				object[6] = BaseVideoConfig.ProgramLogoPath + "/" + data[5];// 填写p.TRUELOGOPATH
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
	 * 用于回收数据
	 */
	protected void clear()
	{
		super.clear();
		nodeIDMap.clear();
		videoIDMap.clear();
	}
}
