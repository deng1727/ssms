package com.aspire.dotcard.audit.vo;

import java.util.Date;

/**
 * ����ģ��ʵ�����
 */
public class CheckLogVO
{
    //��Ӧ���ݿ������ֶ�
    private String taskid;//����ID 
    private String categoryid;//����ID
    private Date checktime;//ִ��ʱ��
    private String lucenepath;//��ӦLucene����
    private String ip;//��Ӧ�Ļ���IP
    private int dbcount;//���ݿ��¼��
    private int lucenecount;//Lucene�м�¼��

    //�����ֶ�
    private String categoryPath;//����·��
    private String resultcount;//��¼���ȶԽ��
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

    public int getDbcount()
    {
        return dbcount;
    }

    public void setDbcount(int dbcount)
    {
        this.dbcount = dbcount;
    }

    public int getLucenecount()
    {
        return lucenecount;
    }

    public void setLucenecount(int lucenecount)
    {
        this.lucenecount = lucenecount;
    }

    public String getResultcount()
    {
        return resultcount;
    }

    public void setResultcount(String resultcount)
    {
        this.resultcount = resultcount;
    }

    public String getCategoryPath()
    {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath)
    {
        this.categoryPath = categoryPath;
    }

}
