package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���еĲ���ڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author bihui
 */

public class GColorring extends GContent
{
    /**
     * ��Դ���ͣ��������ͣ�����
     */
    public static final String TYPE_COLORRING = "nt:gcontent:colorring";

    /**
     * ���췽��
     */
    public GColorring()
    {
        this.type = TYPE_COLORRING;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GColorring(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_COLORRING;
    }

    /**
     * �������ݵ���Ч����
     * @param value�����ݵ���Ч����
     */
    public void setExpire(String value)
    {
        Property pro = new Property("expire", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ���ݵ���Ч����
     * @return�����ݵ���Ч����
     */
    public String getExpire()
    {
        return getNoNullString((String) this.getProperty("expire").getValue());
    }

    /**
     * ��ȡ���ݵ���Ч���ڵ���ʾ
     * @return String ���ݵ���Ч���ڵ���ʾ
     */
    public String getDisplayExpire()
    {
        try
        {
            String createTime = this.getExpire();
            String display = createTime.substring(0, 4) + '-' +
                createTime.substring(4, 6) + '-'
                + createTime.substring(6, 8);
            return display;
        }
        catch(Exception e)
        {
            return "";
        }
    }

    /**
     * ��ȡ�۸��Է�Ϊ��λ
     * @return Returns the price.
     */
    public String getPrice()
    {
        String price = getNoNullString((String) this.getProperty("price").getValue());
        if(price==null || "".equals(price))
        {
            return "0";
        }
        return price;
    }


    /**
     * ���ü۸�
     * @param price The price to set.
     */
    public void setPrice(String price)
    {
        Property pro = new Property("price", price);
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

    /**
     * ��ȡ������ʷ���Ϣ��Ԫ
     * @return
     */
    public float getPrice_Y()
    {
        return (Float.parseFloat(this.getPrice())/100);
    }

    /**
     * �����������Ƽ�������ĸ
     * @param value���������Ƽ�������ĸ
     */
    public void setTonenameletter(String value)
    {
        Property pro = new Property("tonenameletter", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�������Ƽ�������ĸ
     * @return���������Ƽ�������ĸ
     */
    public String getTonenameletter()
    {
        return getNoNullString((String) this.getProperty("tonenameletter").getValue());
    }

    /**
     * �������Ƽ�������ĸ
     * @param value���������Ƽ�������ĸ
     */
    public void setSingerletter(String value)
    {
        Property pro = new Property("singerletter", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ�������Ƽ�������ĸ
     * @return���������Ƽ�������ĸ
     */
    public String getSingerletter()
    {
        return getNoNullString((String) this.getProperty("singerletter").getValue());
    }

    /**
     * ���ø��������صĴ���
     * @param value�����������صĴ���
     */
    public void setDownloadtimes(int value)
    {
        Property pro = new Property("downloadtimes", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ���������صĴ���
     * @return�����������صĴ���
     */
    public int getDownloadtimes()
    {
        return ((Integer) this.getProperty("downloadtimes").getValue()).intValue();
    }

    /**
     * ���ø��������ô���
     * @param value�����������ô���
     */
    public void setSettimes(int value)
    {
        Property pro = new Property("settimes", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * ��ȡ���������ô���
     * @return�����������ô���
     */
    public int getSettimes()
    {
        return ((Integer) this.getProperty("settimes").getValue()).intValue();
    }

    /**
     * ������������
     * @param value����������
     */
    public void setTonebigtype(String value)
    {
        Property pro = new Property("tonebigtype", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��������
     * @return����������
     */
    public String getTonebigtype()
    {
        return getNoNullString((String) this.getProperty("tonebigtype").getValue());
    }
    
    /**
     * ��ȡ������ַ
     * @return Returns the auditionUrl.
     */
    public String getAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("auditionUrl").getValue());
    }


    /**
     * ����������ַ
     * @param auditionUrl The auditionUrl to set.
     */
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ�ն�������ַ
     * @return Returns the clientAuditionUrl.
     */
    public String getClientAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("clientAuditionUrl").getValue());
    }


    /**
     * �����ն�������ַ
     * @param clientAuditionUrl The clientAuditionUrl to set.
     */
    public void setClientAuditionUrl(String clientAuditionUrl)
    {
        Property pro = new Property("clientAuditionUrl", clientAuditionUrl);
        this.setProperty(pro);
    }

}
