package com.aspire.dotcard.audit.vo;

import java.util.Date;

/**
 * ����ģ��ʵ�����
 */
public class CheckDetailVO
{
    //��Ӧ���ݿ������ֶ�
    private String taskid;//����ID
    private String categoryid;//����ID 
    private Date checktime;//ִ��ʱ��
    private String lucenepath;//��ӦLucene����
    private String ip;//��Ӧ�Ļ���IP
    private String pkid;//��lucene����/���ݿ��pkֵ
    private String statusinfo;//״̬��Ϣ 1��lucene�д��ڣ����ݿ��в����� 2��lucene�в����ڣ����ݿ��д���

    //�����ֶ�
    private String categoryPath;//����·��
    private String beginDate;//ִ�п�ʼʱ��
    private String endDate;//ִ�н���ʱ��

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
