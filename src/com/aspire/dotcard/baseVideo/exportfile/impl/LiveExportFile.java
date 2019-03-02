/*
 * 文件名：LiveExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
        this.mailTitle = "基地直播节目数据导入结果";
    }
    /**
     * 用于添加准备动作数据
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
            logger.debug("开始验证直播节目数据字段格式，nodeID=" + nodeID);
        }

        if (data.length != 5)
        {
            logger.error("字段数不等于5");
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        // nodeid
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("nodeID=" + nodeID + ",nodeID验证错误，该字段是必填字段，且不超过60个字符");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // productID
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",productID验证出错，该字段是必填字段，长度不超过60个数值长度！productID="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        if(!keyMap.containsKey(nodeID + "|" + tmp))
        {
            logger.error("programID=" + tmp + ", nodeID=" + nodeID
                         + ",此条记录验证出错，节目详情中不存在此记录对应关系！");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // liveName
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 200, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",liveName验证出错，该字段是必填字段，长度不超过200个字符！liveName=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // startTime
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 14, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",startTime验证出错，该字段是必填字段，长度不超过14个字符！startTime="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // endTime
        tmp = data[4];
        if (!this.checkFieldLength(tmp, 14, true))
        {
            logger.error("nodeID=" + nodeID
                         + ",endTime验证出错，该字段是必填字段，长度不超过14个字符！endTime=" + tmp);
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
    	//直播节目单 要加上开始时间 作为唯一索引
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
