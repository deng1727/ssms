/*
 * �ļ�����BaseBook.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.baserecomm.basebook;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class BaseBookVO
{
    /**
     * ����ͼ��id
     */
    private String bookId;
    
    /**
     * ����id
     */
    private String contentId;
    
    /**
     * ����ͼ������
     */
    private String bookName;
    
    /**
     * ��������
     */
    private String authorName;

    /**
     * ����ͼ������
     */
    private String bookType;
    
    /**
     * ����ͼ����
     */
    private String bookDesc;
    
    /**
     * ����ͼ��ؼ���
     */
    private String key;
    
    /**
     * �Ƿ�����Ч����
     */
    private String validityData;
    
    public String getAuthorName()
    {
        return authorName;
    }

    
    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    
    public String getBookDesc()
    {
        return bookDesc;
    }

    
    public void setBookDesc(String bookDesc)
    {
        this.bookDesc = bookDesc;
    }

    
    public String getBookId()
    {
        return bookId;
    }

    
    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    
    public String getBookName()
    {
        return bookName;
    }

    
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    
    public String getBookType()
    {
        return bookType;
    }

    
    public void setBookType(String bookType)
    {
        this.bookType = bookType;
    }

    
    public String getContentId()
    {
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }


    
    public String getKey()
    {
        return key;
    }


    
    public void setKey(String key)
    {
        this.key = key;
    }


    
    public String getValidityData()
    {
        return validityData;
    }


    
    public void setValidityData(String validityData)
    {
        this.validityData = validityData;
    }
}
