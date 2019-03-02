package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的彩铃节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author bihui
 */

public class GColorring extends GContent
{
    /**
     * 资源类型：内容类型，彩铃
     */
    public static final String TYPE_COLORRING = "nt:gcontent:colorring";

    /**
     * 构造方法
     */
    public GColorring()
    {
        this.type = TYPE_COLORRING;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GColorring(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_COLORRING;
    }

    /**
     * 设置内容的有效日期
     * @param value，内容的有效日期
     */
    public void setExpire(String value)
    {
        Property pro = new Property("expire", value);
        this.setProperty(pro);
    }

    /**
     * 获取内容的有效日期
     * @return，内容的有效日期
     */
    public String getExpire()
    {
        return getNoNullString((String) this.getProperty("expire").getValue());
    }

    /**
     * 获取内容的有效日期的显示
     * @return String 内容的有效日期的显示
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
     * 获取价格，以分为单位
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
     * 设置价格
     * @param price The price to set.
     */
    public void setPrice(String price)
    {
        Property pro = new Property("price", price);
        this.setProperty(pro);
    }


    /**
     * 获取歌手名
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * 设置歌手名
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }

    /**
     * 获取彩铃的资费信息：元
     * @return
     */
    public float getPrice_Y()
    {
        return (Float.parseFloat(this.getPrice())/100);
    }

    /**
     * 设置铃音名称检索首字母
     * @param value，铃音名称检索首字母
     */
    public void setTonenameletter(String value)
    {
        Property pro = new Property("tonenameletter", value);
        this.setProperty(pro);
    }

    /**
     * 获取铃音名称检索首字母
     * @return，铃音名称检索首字母
     */
    public String getTonenameletter()
    {
        return getNoNullString((String) this.getProperty("tonenameletter").getValue());
    }

    /**
     * 歌手名称检索首字母
     * @param value，歌手名称检索首字母
     */
    public void setSingerletter(String value)
    {
        Property pro = new Property("singerletter", value);
        this.setProperty(pro);
    }

    /**
     * 获取歌手名称检索首字母
     * @return，歌手名称检索首字母
     */
    public String getSingerletter()
    {
        return getNoNullString((String) this.getProperty("singerletter").getValue());
    }

    /**
     * 设置该铃音下载的次数
     * @param value，该铃音下载的次数
     */
    public void setDownloadtimes(int value)
    {
        Property pro = new Property("downloadtimes", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取该铃音下载的次数
     * @return，该铃音下载的次数
     */
    public int getDownloadtimes()
    {
        return ((Integer) this.getProperty("downloadtimes").getValue()).intValue();
    }

    /**
     * 设置该铃音设置次数
     * @param value，该铃音设置次数
     */
    public void setSettimes(int value)
    {
        Property pro = new Property("settimes", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取该铃音设置次数
     * @return，该铃音设置次数
     */
    public int getSettimes()
    {
        return ((Integer) this.getProperty("settimes").getValue()).intValue();
    }

    /**
     * 设置铃音大类
     * @param value，铃音大类
     */
    public void setTonebigtype(String value)
    {
        Property pro = new Property("tonebigtype", value);
        this.setProperty(pro);
    }

    /**
     * 获取铃音大类
     * @return，铃音大类
     */
    public String getTonebigtype()
    {
        return getNoNullString((String) this.getProperty("tonebigtype").getValue());
    }
    
    /**
     * 获取试听地址
     * @return Returns the auditionUrl.
     */
    public String getAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("auditionUrl").getValue());
    }


    /**
     * 设置试听地址
     * @param auditionUrl The auditionUrl to set.
     */
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }
    
    /**
     * 获取终端试听地址
     * @return Returns the clientAuditionUrl.
     */
    public String getClientAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("clientAuditionUrl").getValue());
    }


    /**
     * 设置终端试听地址
     * @param clientAuditionUrl The clientAuditionUrl to set.
     */
    public void setClientAuditionUrl(String clientAuditionUrl)
    {
        Property pro = new Property("clientAuditionUrl", clientAuditionUrl);
        this.setProperty(pro);
    }

}
