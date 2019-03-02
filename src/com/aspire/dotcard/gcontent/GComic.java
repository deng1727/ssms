/**
 * <p>
 *  ��Դ���еĶ����ڵ���
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 4, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * @author dongke
 *
 */
public class GComic extends GContent {

	
	/**
     * ��Դ���ͣ�ҵ�����ݣ���Ƶ����
     */
    public static final String TYPE_COMIC = "nt:gcontent:comic";
    
   /**
    * ���췽��
    *
    */
    public GComic(){   	
    	this.type = TYPE_COMIC;
    }
    
    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GComic(String nodeID){
    	super (nodeID);
    	this.type = TYPE_COMIC;
    	
    }
    

    

    
    
    /**
     * ��������
     * @return Returns the author.
     */
    public String getAuthor()
    {
        return getNoNullString((String) this.getProperty("author").getValue());
    }


    /**
     * ��������
     * @param author
     */
    public void setAuthor(String author)
    {
        Property pro = new Property("author", author);
        this.setProperty(pro);
    }
    
    

    
    
    /**
     * ��������URL
     * @return Returns the contentUrl.
     */
    public String getContentUrl()
    {
        return getNoNullString((String) this.getProperty("contentUrl").getValue());
    }


    /**
     * ��������URL
     * @param contentUrl
     */
    public void setContentUrl(String contentUrl)
    {
        Property pro = new Property("contentUrl", contentUrl);
        this.setProperty(pro);
    }
    
    
    /**
     * ʧЧʱ��
     * @return Returns the invalidTime.
     */
    public String getInvalidTime()
    {
        return getNoNullString((String) this.getProperty("invalidTime").getValue());
    }


    /**
     * ʧЧʱ��
     * @param invalidTime
     */
    public void setInvalidTime(String invalidTime)
    {
        Property pro = new Property("invalidTime", invalidTime);
        this.setProperty(pro);
    }
    
    
 
    
    
    
    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @return Returns the Changetype.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @param changetype
     */
    public void setChangeType(String changeType)
    {
        Property pro = new Property("changeType", changeType);
        this.setProperty(pro);
    }
    
    
    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GComic[");
        sb.append(this.getProperty("author"));
        sb.append(',');
        sb.append(this.getProperty("contentUrl"));
        sb.append(',');
        sb.append(this.getProperty("invalidTime"));
        sb.append(',');
        sb.append(this.getProperty("changeType"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
    
}
