/*
 * �ļ�����ContentExigenceVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.repository.web;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ContentExigenceVO
{
    /**
     * ����id
     */
    private String contentId;
    
    /**
     * ����ʱ��
     */
    private String sysdate;
    
    /**
     * ͬ����������
     */
    private String type;
    
    /**
     * ��������
     */
    private String subType;

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    public String getSysdate()
    {
        return sysdate;
    }

    public void setSysdate(String sysdate)
    {
        this.sysdate = sysdate;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSubType()
    {
        return subType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
    }
}
