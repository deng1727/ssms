/*
 * 文件名：DeviceExportFile.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.dotcard.baseVideo.exportfile.impl;

import com.aspire.dotcard.baseVideo.conf.BaseVideoConfig;
import com.aspire.dotcard.baseVideo.exportfile.BaseExportFile;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class DeviceExportFile extends BaseExportFile
{
    public DeviceExportFile()
    {
        this.fileName = "i_v-device_~DyyyyMMdd~_[0-9]{6}.txt";
        this.hasVerf = false;
        this.mailTitle = "基地机型文件数据导入结果";
    }
    
    /* (non-Javadoc)
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#checkData(java.lang.String[])
     */
    protected String checkData(String[] data)
    {
        String deviceId = data[0];
        String tmp = deviceId;

        if (logger.isDebugEnabled())
        {
            logger.debug("开始验证机型文件字段格式，videoId=" + deviceId);
        }

        if (data.length != 4)
        {
            logger.error("字段数不等于4");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",deviceId验证错误，该字段是必填字段，且不超过60个字符");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // UA
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 1024, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",UA验证错误，该字段是必填字段，且长度不超过1024个字符错误！UA="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // deviceName
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",deviceName验证出错，长度不超过60个字符！deviceName=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // codeRateType
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId + ",codeRateType验证出错，长度不超过60个字符！codeRateType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        if("12".indexOf(tmp) == -1)
        {
            logger.error("机型ID="+deviceId+",codeRateType验证错误，该字段是必填字段，且只能为1、2! codeRateType="+tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }

        return BaseVideoConfig.CHECK_DATA_SUCCESS;
    }
    
    /* (non-Javadoc)
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
        // insert into T_VO_DEVICE (UA, DEVICENAME, CODERATETYPE, DEVICEID) values (?,?,?,?)
        return "baseVideo.exportfile.DeviceExportFile.getInsertSqlCode";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.dotcard.baseVideo.exportfile.BaseExportFile#getUpdateSqlCode()
     */
    protected String getUpdateSqlCode()
    {
        // update T_VO_DEVICE set UA=?, DEVICENAME=?, CODERATETYPE=? where DEVICEID=?
        return "baseVideo.exportfile.DeviceExportFile.getUpdateSqlCode";
    }

    protected String getDelSqlCode()
    {
        // truncate table T_VO_DEVICE
        return "baseVideo.exportfile.DeviceExportFile.getDelSqlCode";
    }

    protected Object[] getHasObject(String[] data)
    {
        Object[] object = new Object[1];
        object[0] = data[0];
        return object;
    }

    protected String getHasSqlCode()
    {
        // select 1 from T_VO_DEVICE c where DEVICEID=?
        return "baseVideo.exportfile.DeviceExportFile.getHasSqlCode";
    }
    
    protected String getKey(String[] data)
    {
        return data[0];
    }

}
