/*
 * �ļ�����ProgramExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideoNew.exportfile.impl;

import java.util.Map;

import com.aspire.dotcard.baseVideoNew.config.BaseVideoNewConfig;
import com.aspire.dotcard.baseVideoNew.dao.ProgramByHourExportFileDAO;
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
public class ProgramByHourExportFile extends BaseExportFile
{
    /**
     * ��ĿID�б�
     */
    private Map nodeIDMap = null;
    
    /**
     * ��ƵID�б�
     */
    private Map videoIDMap = null;
    
    
    public ProgramByHourExportFile()
    {
        this.fileName = "a_v-videodetail_~DyyyyMMddHH~_[0-9]{6}.txt";
        this.verfFileName = "a_v-videodetail_~DyyyyMMddHH~.verf";
        this.mailTitle = "���ؽ�Ŀ�������ļ����ݵ�����";
        this.isByHour=true;
		this.getTimeNum=BaseVideoNewConfig.GET_TIME_NUM;;
        this.isDelTable=false;//add by aiyan 2012-07-16
        this.isDelMidTable = false;
    }
    
    /**
     * �������׼����������
     */
    public void init()
    {
        super.init();
        nodeIDMap = ProgramByHourExportFileDAO.getInstance().getNodeIDMap();
        videoIDMap = ProgramByHourExportFileDAO.getInstance().getVideoIDMap();
        keyMap = ProgramByHourExportFileDAO.getInstance().getProgramIDMap();
       
    }
    
    /**
     * ���ڻ�������
     */
    public void destroy()
    {
        nodeIDMap.clear();
        videoIDMap.clear();
        keyMap.clear();
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
            return BaseVideoNewConfig.CHECK_FAILED;
        }

        if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
        {
            logger.error("programID=" + programID
                         + ",programID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // videoID
        tmp = data[1];
        if (!BaseFileNewTools.checkFieldLength(tmp, 60, true))
        {
            logger.error("programID=" + programID
                         + ",videoID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����60���ַ�����videoID="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
//��ʱע��FOR TEST _AIYAN 2012-07-14        
        if(!videoIDMap.containsKey(tmp))
        {
            logger.error("programID=" + programID
                         + ",videoID��֤������Ƶ�б��в����ڴ���ƵID��videoID=" + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        
        // programName
        tmp = data[2];
        if (!BaseFileNewTools.checkFieldLength(tmp, 128, true))
        {
            logger.error("programID=" + programID
                         + ",programName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����128���ַ���programName="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // nodeID
        tmp = data[3];
        if (!BaseFileNewTools.checkFieldLength(tmp, 512, true))
        {
            logger.error("programID=" + programID
                         + ",nodeID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���nodeID=" + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
//  ��ʱע��FOR TEST _AIYAN 2012-07-14       
        if(!nodeIDMap.containsKey(tmp))
        {
            logger.error("programID=" + programID
                         + ",nodeID��֤������Ŀ�б��в����ڴ���ĿID��nodeID=" + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        
        // description
        tmp = data[4];
        if (!BaseFileNewTools.checkFieldLength(tmp, 4000, true))
        {
            logger.error("programID="
                         + programID
                         + ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // logoPath
        tmp = data[5];
        if (!BaseFileNewTools.checkFieldLength(tmp, 512, false))
        {
            logger.error("programID=" + programID
                         + ",logoPath��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���logoPath=" + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // timeLength
        tmp = data[6];
        if (!BaseFileNewTools.checkIntegerField("ʱ��", tmp, 12, true))
        {
            logger.error("programID=" + programID
                         + ",timeLength��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12����ֵ���ȣ�timeLength="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // lastUpTime
        tmp = data[7];
        if (!BaseFileNewTools.checkFieldLength(tmp, 14, true))
        {
            logger.error("programID=" + programID
                         + ",lastUpTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���lastUpTime="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }
        // programType
        tmp = data[8];
        if (!BaseFileNewTools.checkIntegerField("��Ŀ����", tmp, 2, true))
        {
            logger.error("programID=" + programID
                         + ",programType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2����ֵ���ȣ�programType="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
        }

        if ("12".indexOf(tmp) == -1)
        {
            logger.error("programID=" + programID
                         + ",programType��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ1��2! programType="
                         + tmp);
            return BaseVideoNewConfig.CHECK_FAILED;
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
        
        return BaseVideoNewConfig.CHECK_DATA_SUCCESS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getObject(java.lang.String[])
     */
    protected Object[] getObject(String[] data)
    {
    	//videoid, programname, nodeid, description, logopath,ftplogopath,
    	//TRUELOGOPATH, timelength, showtime, lastuptime, programtype, exporttime,sortid,islink programid
        Object[] object = new Object[15];

        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[11];//nodeid �滻productid
        object[3] = data[4];

        object[4] = BaseVideoNewConfig.logoPath;//��дLOGOPATH
        object[5] = data[5];//��дFTPLOGOPATH
        if(data[5] != null && !data[5].equals("")){
        	String ftplogo = (String)data[5];
        	 if(ftplogo.startsWith("/")){
        		  object[6] = BaseVideoNewConfig.ProgramLogoPath + data[5];//��дp.TRUELOGOPATH
        	 }else{
        		 object[6] = BaseVideoNewConfig.ProgramLogoPath + "/"+ data[5];//��дp.TRUELOGOPATH
        	 }
        	
        }
        
        object[7] = getTimeLength(data[6]);
        object[8] = getShowTime(data[6]);
        object[9] = data[7];
        object[10] = data[8];
        object[11] = data[9];
        object[12] = data[10];
        object[13] = data[3];//nodeid �滻productid
        object[14] = data[0];

        return object;
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
        return String.valueOf(( int ) (milliSeconds / 1000));
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

		/*double second = Double.parseDouble(timeLength);
		 double second = milliSeconds / 1000;*/
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
	 * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
	 */
    protected String getInsertSqlCode()
    {
        // insert into t_vo_program (videoid, programname, nodeid, description,
        // logopath, TRUELOGOPATH,timelength, showtime, lastuptime, programtype, exporttime,sortid,islink
        // programid) values (?,?,?,?,?,?,?,?,?,sysdate,?)
        return "baseVideo.exportfile.ProgramByHourExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update t_vo_program p set p.videoid=?, p.programname=?, p.nodeid=?,
        // p.description, p.logopath, p.timelength, p.showtime, p.lastuptime=?,
        // p.programtype=?, p.exporttime=sysdate,p.sortid=?,p.islink=? where p.programid=?
        return "baseVideo.exportfile.ProgramByHourExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // delete from T_VO_VIDEO where programid=? 
        return "baseVideo.exportfile.ProgramByHourExportFile.getDelSqlCode";
    }

    protected Object[] getHasObject(String[] data)
    {
        Object[] object = new Object[1];
        object[0] = data[0];
        return object;
    }

    protected String getHasSqlCode()
    {
        // select 1 from t_vo_program p where p.programid=?
        return "baseVideo.exportfile.ProgramByHourExportFile.getHasSqlCode";
    }

    protected String getKey(String[] data)
    {
        return data[0]+ "|" + data[3] ;
    }

}
