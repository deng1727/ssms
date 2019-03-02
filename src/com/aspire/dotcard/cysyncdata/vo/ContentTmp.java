
package com.aspire.dotcard.cysyncdata.vo;

import java.util.Date;

/**
 * <p>
 * 内容同步临时对象属性
 * </p>
 * <p>
 * Copyright (c) 2003-2007 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentTmp
{

    /**
     * 内容id
     */
    private String contentId;
    /**
     * 应用名称
     */
    private String name;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 最后更新日期
     */
    private Date LupdDate;

    /**
     * 数据库中的序号
     */
    private int id;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * @return Returns the contented.
     */
    public String getContentId()
    {

        return contentId;
    }

    /**
     * @param contented The contented to set.
     */
    public void setContentId(String contentId)
    {

        this.contentId = contentId;
    }

    /**
     * @return Returns the contentType.
     */
    public String getContentType()
    {

        return contentType;
    }

    /**
     * @param contentType The contentType to set.
     */
    public void setContentType(String contentType)
    {

        this.contentType = contentType;
    }

    /**
     * @return Returns the id.
     */
    public int getId()
    {

        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(int id)
    {

        this.id = id;
    }

    /**
     * @return Returns the LupdDate.
     */
    public Date getLupdDate()
    {

        return LupdDate;
    }

    /**
     * @param date The LupdDate to set.
     */
    public void setLupdDate(Date date)
    {

        LupdDate = date;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus()
    {

        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status)
    {

        this.status = status;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("内容编码:");
        sb.append(this.contentId);
        sb.append(",");
        sb.append("名称:");
        sb.append(this.name);
        return sb.toString();
    }
    public int hashCode()
    {
    	
    	return contentId.hashCode();
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof ContentTmp))
		{
			return false;
		}
		ContentTmp temp=(ContentTmp)obj;
		if(this.contentId.equals(temp.contentId))
		{
			return true;
		}else
		{
			return true;
		}	
	}
}
