package com.aspire.dotcard.syncAndroid.cssp.free;

import com.aspire.dotcard.syncAndroid.cssp.Bean;

/**
 * 
 * 免费应用下载回执日志表 T_free_dl_report
 * add by fanqh
 */
public class FreeDLReportBean implements Bean
{
    private String id;
    private String sequenceId;
    private String pushId;
    private String status;
    private String outputTime;
    private String params;
    private String userAccessType;
    private String lupdatetime;
    private String flag;

    private String filename;

    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

   

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

   
    public String getParams()
    {
        return params;
    }

    public void setParams(String params)
    {
        this.params = params;
    }

    public String getUserAccessType()
    {
        return userAccessType;
    }

    public void setUserAccessType(String userAccessType)
    {
        this.userAccessType = userAccessType;
    }

    public String getLupdatetime()
    {
        return lupdatetime;
    }

    public void setLupdatetime(String lupdatetime)
    {
        this.lupdatetime = lupdatetime;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getSequenceId()
    {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId)
    {
        this.sequenceId = sequenceId;
    }

    public String getPushId()
    {
        return pushId;
    }

    public void setPushId(String pushId)
    {
        this.pushId = pushId;
    }

    public String getOutputTime()
    {
        return outputTime;
    }

    public void setOutputTime(String outputTime)
    {
        this.outputTime = outputTime;
    }

}
