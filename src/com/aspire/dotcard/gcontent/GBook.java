package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * ��Դ���е�ͼ��ڵ���
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author ����
 * @version 1.0.0.0
 */
public class GBook extends GContent
{
	/**
     * ��Դ���ͣ��������ͣ�ͼ��
     */
    public static final String TYPE_BOOK = "nt:gcontent:book";

    /**
     * ���췽��
     */
    public GBook()
    {
        this.type = TYPE_BOOK;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GBook(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_BOOK;
    }
    /**
     * ��ȡͼ��ID
     * @return Returns the bookID.
     */
    public String getBookID()
    {
        return getNoNullString((String) this.getProperty("contentid").getValue());
    }


    /**
     * ����ͼ��ID
     * @param bookID The bookID to set.
     */
    public void setBookID(String bookID)
    {
        Property pro = new Property("contentid", bookID);
        this.setProperty(pro);
    }
    /**
     * ��ȡͼ�����
     * @return Returns the bookTitle.
     */
    public String getBookTitle()
    {
        return getNoNullString((String) this.getProperty("bookTitle").getValue());
    }


    /**
     * ����ͼ�����
     * @param bookTitle The bookTitle to set.
     */
    public void setBookTitle(String bookTitle)
    {
        Property pro = new Property("bookTitle", bookTitle);
        this.setProperty(pro);
    }
    /**
     * ��ȡͼ������
     * @return Returns the author.
     */
    public String getAuthor()
    {
        return getNoNullString((String) this.getProperty("author").getValue());
    }


    /**
     * ����ͼ������
     * @param author The author to set.
     */
    public void setAuthor(String author)
    {
        Property pro = new Property("author", author);
        this.setProperty(pro);
    }
    /**
     * ��ȡͼ�����
     * @return Returns the bookCat.
     */
    public String getBookCat()
    {
        return getNoNullString((String) this.getProperty("cateName").getValue());
    }


    /**
     * ����ͼ�����
     * @param bookCat The bookCat to set.
     */
    public void setBookCat(String bookCat)
    {
        Property pro = new Property("cateName", bookCat);
        this.setProperty(pro);
    }
    /**
     * ��ȡͼ������URL
     * @return Returns the contentUrl.
     */
    public String getContentUrl()
    {
        return getNoNullString((String) this.getProperty("contentUrl").getValue());
    }


    /**
     * ����ͼ������URL
     * @param contentUrl The contentUrl to set.
     */
    public void setContentUrl(String contentUrl)
    {
        Property pro = new Property("contentUrl", contentUrl);
        this.setProperty(pro);
    }
    /**
     * ��ȡʧЧʱ��
     * @return Returns the invalidTime.
     */
    public String getInvalidTime()
    {
        return getNoNullString((String) this.getProperty("invalidTime").getValue());
    }


    /**
     * ����ʧЧʱ��
     * @param invalidTime The invalidTime to set.
     */
    public void setInvalidTime(String invalidTime)
    {
        Property pro = new Property("invalidTime", invalidTime);
        this.setProperty(pro);
    }
    /**
     * ��ȡ������
     * @return Returns the changeType.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * ���ñ�����
     * @param changeType The changeType to set.
     */
    public void setChangeType(String changeType)
    {
        Property pro = new Property("changeType", changeType);
        this.setProperty(pro);
    }

}
