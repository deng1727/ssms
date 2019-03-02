package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

public class GRead extends GContent
{

	/**
     * ��Դ���ͣ��������ͣ�����ͼ��
     */
    public static final String TYPE_READ = "nt:gcontent:read";

    /**
     * ���췽��
     */
    public GRead()
    {
        this.type = TYPE_READ;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GRead(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_READ;
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
//////////
    /**
     * ��ȡ��������
     * @return Returns the chargeType.
     * �������ͣ�0��ѣ�1�����Ʒѣ�2�����¼Ʒѣ�3�����ּƷ�
     * 
     */
    public String getChargeType()
    {
        return getNoNullString((String) this.getProperty("chargeType").getValue());
    }


    /**
     * ���÷�������
     * @param changeType The chargeType to set.
     * �������ͣ�0��ѣ�1�����Ʒѣ�2�����¼Ʒѣ�3�����ּƷ�
     */
    public void setChargeType(String chargeType)
    {
        Property pro = new Property("chargeType", chargeType);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����
     * @return Returns the Fee.
     * ���ʣ���λ����
     */
    public String getFee()
    {
        return getNoNullString((String) this.getProperty("fee").getValue());
    }


    /**
     * ���÷���
     * @param changeType The Fee to set.
     * ���ʣ���λ����
     */
    public void setFee(String fee)
    {
        Property pro = new Property("fee", fee);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�Ƿ��걾
     * @return Returns the isFinish.
     * �Ƿ��걾��0��δ�걾��1�����걾 
     */
    public String getIsFinish()
    {
        return getNoNullString((String) this.getProperty("isFinish").getValue());
    }


    /**
     * �����Ƿ��걾
     * @param changeType The isFinish to set.
     * �Ƿ��걾��0��δ�걾��1�����걾 
     */
    public void setIsFinish(String isFinish)
    {
        Property pro = new Property("isFinish", isFinish);
        this.setProperty(pro);
    }
    
    
    
    /**
     * ��ȡ��������
     * @return Returns the handBook.
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * ������������
     * @param handBook
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ������
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * ���ø�����
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }



}
