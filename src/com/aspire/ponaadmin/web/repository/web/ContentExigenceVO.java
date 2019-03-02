/*
 * 文件名：ContentExigenceVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 内容id
     */
    private String contentId;
    
    /**
     * 导入时间
     */
    private String sysdate;
    
    /**
     * 同步数据类型
     */
    private String type;
    
    /**
     * 内容类型
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
