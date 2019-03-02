package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

public class GAppSoftWare extends GAppContent 
{
	/**
     * 资源类型：业务内容，应用软件类型
     */
    public static final String TYPE_APPSOFTWARE = "nt:gcontent:appSoftWare";
    
    /**
     * 构造方法
     */
    public GAppSoftWare()
    {
       this.type=TYPE_APPSOFTWARE;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GAppSoftWare(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPSOFTWARE;
    }
    /**
     * 获取操作指南
     * @return Returns the handBook.  保存创业大赛开发者高校信息  add by dongke 2011
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * 设置操作指南 
     * @param handBook  保存创业大赛开发者高校信息  add by dongke 2011
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
    /**
     * 获取使用手册的文件地址
     * @return Returns the manual.
     */
    public String getManual()
    {
        return getNoNullString((String) this.getProperty("manual").getValue());
    }
    /**
     * 获取操作指南图片地址
     * @return Returns the handBookPicture.
     */
    public String getHandBookPicture()
    {
        return getNoNullString((String) this.getProperty("handBookPicture").getValue());
    }
    /**
     * 设置操作指南图片地址
     * @param handBookPicture
     */
    public void setHandBookPicture(String handBookPicture)
    {
        Property pro = new Property("handBookPicture", handBookPicture);
        this.setProperty(pro);
    }

    /**
     * 设置使用手册的文件地址 
     * @param manual
     */
    public void setManual(String manual)
    {
        Property pro = new Property("manual", manual);
        this.setProperty(pro);
    }
    


    /**
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GSoftware[");
        sb.append(this.getProperty("detailDescURL"));
        sb.append(',');
        sb.append(this.getProperty("contentSize"));        
        sb.append(',');
        sb.append(this.getProperty("mobileURL"));
        sb.append(',');
        sb.append(this.getProperty("deviceName"));
        sb.append(',');
        sb.append(this.getProperty("terminalSupport"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }

    
    

}
