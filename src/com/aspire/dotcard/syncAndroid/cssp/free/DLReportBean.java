package com.aspire.dotcard.syncAndroid.cssp.free;

import com.aspire.dotcard.syncAndroid.cssp.Bean;

/**
 * 免费应用下载回执日志表 T_A_REPORT add by fanqh
 */
public class DLReportBean implements Bean
{

    private String contentid;
    private String subsplace;
    private String ua;
    private String status;
    private String lupdate;

    private String filename;

    private String orderid;
    private String reportid;

    public String getOrderid()
    {
        return orderid;
    }

    public void setOrderid(String orderid)
    {
        this.orderid = orderid;
    }

    public String getReportid()
    {
        return reportid;
    }

    public void setReportid(String reportid)
    {
        this.reportid = reportid;
    }

    public String getContentid()
    {
        return contentid;
    }

    public void setContentid(String contentid)
    {
        this.contentid = contentid;
    }

    public String getSubsplace()
    {
        return subsplace;
    }

    public void setSubsplace(String subsplace)
    {
        this.subsplace = subsplace;
    }

    public String getUa()
    {
        return ua;
    }

    public void setUa(String ua)
    {
        this.ua = ua;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getLupdate()
    {
        return lupdate;
    }

    public void setLupdate(String lupdate)
    {
        this.lupdate = lupdate;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

}
