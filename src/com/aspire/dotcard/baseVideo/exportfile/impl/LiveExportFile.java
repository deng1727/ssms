/*
 * �ļ�����LiveExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.baseVideo.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.dao.BaseVideoFileDAO;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;

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
public class LiveExportFile extends BaseExportFile
{
    public LiveExportFile()
    {
        this.fileName = "i_v-live_~DyyyyMMdd~_[0-9]{6}.txt";
        this.verfFileName = "i_v-live_~DyyyyMMdd~.verf";
        this.mailTitle = "����ֱ����Ŀ���ݵ�����";
    }
    /**
     * �������׼����������
     */
    public void init()
    {
        super.init();
        keyMap = BaseVideoFileDAO.getInstance().getProgramNodeIDMap();
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String nodeID = data[0];
        String tmp = nodeID;

        if (logger.isDebugEnabled())
        {
            logger.debug("��ʼ��ֱ֤����Ŀ�����ֶθ�ʽ��nodeID=" + nodeID);
        }

        if (data.length != 5)
        {
            logger.error("�ֶ���������5");
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        // nodeid
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("nodeID=" + nodeID + ",nodeID��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // productID
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",productID��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����60����ֵ���ȣ�productID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        if(!keyMap.containsKey(nodeID + "|" + tmp))
        {
            logger.error("programID=" + tmp + ", nodeID=" + nodeID
                         + ",������¼��֤������Ŀ�����в����ڴ˼�¼��Ӧ��ϵ��");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // liveName
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 200, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",liveName��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����200���ַ���liveName=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // startTime
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 14, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",startTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���startTime="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // endTime
        tmp = data[4];
        if (!this.checkFieldLength(tmp, 14, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",endTime��֤�������ֶ��Ǳ����ֶΣ����Ȳ�����14���ַ���endTime=" + tmp);
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
//        Object[] object = new Object[5];
//
//        object[0] = data[2];
//        object[1] = data[3];
//        object[2] = data[4];
//        object[3] = data[0];
//        object[4] = data[1];
//
//        return object;
    	return new Object[]{data[2],data[4],data[0],data[1],data[3]};
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getInsertSqlCode()
     */
    protected String getInsertSqlCode()
    {
        // insert into t_vo_live (livename,  endtime, nodeid,
        // programid,starttime) values (?,?,?,?,?)
        return "baseVideo.exportfile.LiveExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update t_vo_live l set l.livename=?,l.endtime=? where
        // l.nodeid=? and l.programid=? and l.starttime=?
        return "baseVideo.exportfile.LiveExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // truncate table t_vo_live
        return "baseVideo.exportfile.LiveExportFile.getDelSqlCode";
    }

//    protected Object[] getHasObject(String[] data)
//    {
//        Object[] object = new Object[2];
//
//        object[0] = data[0];
//        object[1] = data[1];
//
//        return object;
//    }
//
//    protected String getHasSqlCode()
//    {
//        // select 1 from t_vo_live l where l.nodeid=? and l.programid=?
//        return "baseVideo.exportfile.LiveExportFile.getHasSqlCode";
//    }

    protected String getKey(String[] data)
    {
    	//ֱ����Ŀ�� Ҫ���Ͽ�ʼʱ�� ��ΪΨһ����
        return data[0]+"|"+data[1]+"|"+data[3];
    }

	protected Object[] getHasObject(String[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getHasSqlCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
