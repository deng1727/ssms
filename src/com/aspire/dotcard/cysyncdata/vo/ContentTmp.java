
package com.aspire.dotcard.cysyncdata.vo;

import java.util.Date;

/**
 * <p>
 * ����ͬ����ʱ��������
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
     * ����id
     */
    private String contentId;
    /**
     * Ӧ������
     */
    private String name;

    /**
     * ����״̬
     */
    private String status;

    /**
     * ����������
     */
    private Date LupdDate;

    /**
     * ���ݿ��е����
     */
    private int id;

    /**
     * ��������
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
        sb.append("���ݱ���:");
        sb.append(this.contentId);
        sb.append(",");
        sb.append("����:");
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
