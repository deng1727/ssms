/*
 * �ļ�����DeviceExportFile.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
        this.mailTitle = "���ػ����ļ����ݵ�����";
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
            logger.debug("��ʼ��֤�����ļ��ֶθ�ʽ��videoId=" + deviceId);
        }

        if (data.length != 4)
        {
            logger.error("�ֶ���������4");
            return BaseVideoConfig.CHECK_FAILED;
        }

        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",deviceId��֤���󣬸��ֶ��Ǳ����ֶΣ��Ҳ�����60���ַ�");
            return BaseVideoConfig.CHECK_FAILED;
        }
        // UA
        tmp = data[1];
        if (!this.checkFieldLength(tmp, 1024, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",UA��֤���󣬸��ֶ��Ǳ����ֶΣ��ҳ��Ȳ�����1024���ַ�����UA="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // deviceName
        tmp = data[2];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId
                         + ",deviceName��֤�������Ȳ�����60���ַ���deviceName=" + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        // codeRateType
        tmp = data[3];
        if (!this.checkFieldLength(tmp, 60, true))
        {
            logger.error("deviceId=" + deviceId + ",codeRateType��֤�������Ȳ�����60���ַ���codeRateType="
                         + tmp);
            return BaseVideoConfig.CHECK_FAILED;
        }
        
        if("12".indexOf(tmp) == -1)
        {
            logger.error("����ID="+deviceId+",codeRateType��֤���󣬸��ֶ��Ǳ����ֶΣ���ֻ��Ϊ1��2! codeRateType="+tmp);
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
