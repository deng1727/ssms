package com.aspire.dotcard.audit.vo;

import java.util.Date;

/**
 * 稽核模块实体对象
 */
public class CheckDetailVO
{
    //对应数据库属性字段
    private String taskid;//任务ID
    private String categoryid;//货架ID 
    private Date checktime;//执行时间
    private String lucenepath;//对应Lucene索引
    private String ip;//对应的机器IP
    private String pkid;//该lucene索引/数据库的pk值
    private String statusinfo;//状态信息 1：lucene中存在，数据库中不存在 2：lucene中不存在，数据库中存在

    //辅助字段
    private String categoryPath;//货架路径
    private String beginDate;//执行开始时间
    private String endDate;//执行结束时间

    public String getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }


    public String getTaskid()
    {
        return taskid;
    }

    public void setTaskid(String taskid)
    {
        this.taskid = taskid;
    }

    public String getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(String categoryid)
    {
        this.categoryid = categoryid;
    }

    public Date getChecktime()
    {
        return checktime;
    }

    public void setChecktime(Date checktime)
    {
        this.checktime = checktime;
    }

    public String getLucenepath()
    {
        return lucenepath;
    }

    public void setLucenepath(String lucenepath)
    {
        this.lucenepath = lucenepath;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getPkid()
    {
        return pkid;
    }

    public void setPkid(String pkid)
    {
        this.pkid = pkid;
    }


    public String getCategoryPath()
    {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath)
    {
        this.categoryPath = categoryPath;
    }

    public String getStatusinfo()
    {
        return statusinfo;
    }

    public void setStatusinfo(String statusinfo)
    {
        this.statusinfo = statusinfo;
    }

}
