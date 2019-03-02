/*
 * �ļ�����ProgramExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import java.io.File;
import java.util.Map;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;
import com.aspire.ponaadmin.web.category.export.ftp.FTPUtil;

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
    private Map nodeIDMap = null;
    
    /**
     * ��ƵID�б�
     */
    private Map videoIDMap = null;
    
    /**
     * FTP����
     */
    private FTPUtil fromFtp = null;

    private FTPUtil toFtp = null;
    
    
    public ProgramExportFile()
    {
        this.fileName = "i_v-videodetail_~DyyyyMMdd~_[0-9]{6}.txt";
        this.verfFileName = "i_v-videodetail_~DyyyyMMdd~.verf";
        this.mailTitle = "���ؽ�Ŀ�������ļ����ݵ�����";
        //this.isDelTable=false;//add by aiyan 2012-07-16
    }
    
    /**
     * �������׼����������
     */
    public void init()
    {
        super.init();
        nodeIDMap = BaseVideoFileDAO.getInstance().getNodeIDMap();
        videoIDMap = BaseVideoFileDAO.getInstance().getVideoIDMap();
        //keyMap = BaseVideoFileDAO.getInstance().getProgramIDMap();�����ⲻ��Ҫ�ˡ�T_vo_program ��DEL ,Ȼ��INSERT;
        
        fromFtp = new FTPUtil(BaseVideoConfig.FromFTPIP,
                              BaseVideoConfig.FromFTPPort,
                              BaseVideoConfig.FromFTPUser,
                              BaseVideoConfig.FromFTPPassword,
                              BaseVideoConfig.FromProgramFTPDir);

        toFtp = new FTPUtil(BaseVideoConfig.ToFTPIP,
                            BaseVideoConfig.ToFTPPort,
                            BaseVideoConfig.ToFTPUser,
                            BaseVideoConfig.ToFTPPassword,
                            BaseVideoConfig.ToProgramFTPDir);
    }
    
    /**
     * ���ڻ�������
     */
    public void destroy()
    {
        super.destroy();
        nodeIDMap.clear();
        videoIDMap.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String programID = data[0];
        String tmp = programID;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��֤��Ŀ�������ļ��ֶθ�ʽ��programID=" + programID);
        }

        if (data.length != 9)
        {
            logger.error("�ֶ���������9");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("programID=" + programID
                         + ",programID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // videoID
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("programID=" + programID
                         + ",videoID��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����60���ַ�����videoID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
//��ʱע��FOR TEST _AIYAN 2012-07-14        
        if(!videoIDMap.containsKey(tmp))
        {
            logger.error("programID=" + programID
                         + ",videoID��֤������Ƶ�б��в����ڴ���ƵID��videoID=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        // programName
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 128, true))
        {
            logger.error("programID=" + programID
                         + ",programName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����128���ַ���programName="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // nodeID
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 512, true))
        {
            logger.error("programID=" + programID
                         + ",nodeID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���nodeID=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
//  ��ʱע��FOR TEST _AIYAN 2012-07-14       
        if(!nodeIDMap.containsKey(tmp))
        {
            logger.error("programID=" + programID
                         + ",nodeID��֤������Ŀ�б��в����ڴ���ĿID��nodeID=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        // description
        tmp = data[4];
        if (!this.checkFieldLength(tmp, 4000, true))
        {
            logger.error("programID="
                         + programID
                         + ",description��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����4000���ַ���description="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // logoPath
        tmp = data[5];
        if (!this.checkFieldLength(tmp, 512, false))
        {
            logger.error("programID=" + programID
                         + ",logoPath��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����512���ַ���logoPath=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // timeLength
        tmp = data[6];
        if (!this.checkIntegerField("ʱ��", tmp, 12, true))
        {
            logger.error("programID=" + programID
                         + ",timeLength��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����12����ֵ���ȣ�timeLength="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // lastUpTime
        tmp = data[7];
        if (!this.checkFieldLength(tmp, 14, true))
        {
            logger.error("programID=" + programID
                         + ",lastUpTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���lastUpTime="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // programType
        tmp = data[8];
        if (!this.checkIntegerField("��Ŀ����", tmp, 2, true))
        {
            logger.error("programID=" + programID
                         + ",programType��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����2����ֵ���ȣ�programType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }

        if ("12".indexOf(tmp) == -1)
        {
            logger.error("programID=" + programID
                         + ",programType��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ1��2! programType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
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
        Object[] object = new Object[12];

        object[0] = data[1];
        object[1] = data[2];
        object[2] = data[3];
        object[3] = data[4];

      //Ϊ������ٶȣ������Ȳ�������ʵ��·������ʵ·����Ҫͬ�������modify aiyan 2012-07-14
//        if ("".equals(data[5]))
//        {
//            object[4] = BaseVideoConfig.logoPath;
//        }
//        else
//        {
//            object[4] = getLogoPath(data[5], data[0]);
//        	
//        }
        object[4] = BaseVideoConfig.logoPath;//��дLOGOPATH
        object[5] = data[5];//��дFTPLOGOPATH
        if(data[5] != null && !data[5].equals("")){
        	String ftplogo = (String)data[5];
        	 if(ftplogo.startsWith("/")){
        		  object[6] = BaseVideoConfig.ProgramLogoPath + data[5];//��дp.TRUELOGOPATH
        	 }else{
        		 object[6] = BaseVideoConfig.ProgramLogoPath + "/"+ data[5];//��дp.TRUELOGOPATH
        	 }
        	
        }
      
        
        //object[7] = data[6];
        object[7] = getTimeLength(data[6]);
        object[8] = getShowTime(data[6]);
        object[9] = data[7];
        object[10] = data[8];
        object[11] = data[0];

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
        // logopath, TRUELOGOPATH,timelength, showtime, lastuptime, programtype, exporttime,
        // programid) values (?,?,?,?,?,?,?,?,?,sysdate,?)
        return "baseVideo.exportfile.ProgramExportFile.getInsertSqlCode";
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
        // p.programtype=?, p.exporttime=sysdate where p.programid=?
        return "baseVideo.exportfile.ProgramExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // 
        return "baseVideo.exportfile.ProgramExportFile.getDelSqlCode";
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
        return "baseVideo.exportfile.ProgramExportFile.getHasSqlCode";
    }

    protected String getKey(String[] data)
    {
        return data[0];
    }

}
