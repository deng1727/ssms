package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * 资源树中的视频节点类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class GVideo extends GContent
{
    /**
     * 资源类型：业务内容，视频类型
     */
    public static final String TYPE_VIDEO = "nt:gcontent:video";
    
    /**
     * 构造方法
     */
    public GVideo()
    {
        this.type = TYPE_VIDEO;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GVideo(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_VIDEO;
    }
    
    /**
     * 产品包pkgURL
     * @return Returns the pkgURL.
     */
    public String getPkgURL()
    {
        return getNoNullString((String) this.getProperty("pkgURL").getValue());
    }


    /**
     * 产品包pkgURL
     * @param pkgURL
     */
    public void setPkgURL(String pkgURL)
    {
        Property pro = new Property("pkgURL", pkgURL);
        this.setProperty(pro);
    }
    
    
    /**
     * 产品包ID产品包图片url1，大小：30x30 
     * @return Returns the pkgPICURL1.
     */
    public String getPkgPICURL1()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL1").getValue());
    }


    /**
     * 产品包ID产品包图片url1，大小：30x30
     * @param pkgPICURL1
     */
    public void setPkgPICURL1(String pkgPICURL1)
    {
        Property pro = new Property("pkgPICURL1", pkgPICURL1);
        this.setProperty(pro);
    }
    
    /**
     * 产品包ID产品包图片url2，大小：34x34 
     * @return Returns the pkgPICURL2.
     */
    public String getPkgPICURL2()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL2").getValue());
    }


    /**
     * 产品包ID产品包图片url2，大小：34x34
     * @param pkgPICURL2
     */
    public void setPkgPICURL2(String pkgPICURL2)
    {
        Property pro = new Property("pkgPICURL2", pkgPICURL2);
        this.setProperty(pro);
    }
    
    /**
     * 产品包ID产品包图片url3，大小：50x50 
     * @return Returns the pkgPICURL3.
     */
    public String getPkgPICURL3()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL3").getValue());
    }


    /**
     * 产品包ID产品包图片url3，大小：50x50
     * @param pkgPICURL3
     */
    public void setPkgPICURL3(String pkgPICURL3)
    {
        Property pro = new Property("pkgPICURL3", pkgPICURL3);
        this.setProperty(pro);
    }
    
    /**
     * 产品包ID产品包图片url4，大小：65x65 
     * @return Returns the pkgPICURL4.
     */
    public String getPkgPICURL4()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL4").getValue());
    }


    /**
     * 产品包ID产品包图片url4，大小：30x30
     * @param pkgPICURL4
     */
    public void setPkgPICURL4(String pkgPICURL4)
    {
        Property pro = new Property("pkgPICURL4", pkgPICURL4);
        this.setProperty(pro);
    }
    
    /**
     * 设置产品包排位 
     */
    public void setSortnumber(int sortnumber)
    {
        Property pro = new Property("sortnumber", new Integer(sortnumber));
        this.setProperty(pro);
    }
    
    /**
     * 获取产品包排位 
     * @return
     */
    public int getSortnumber()
    {
        return (( Integer ) this.getProperty("sortnumber").getValue()).intValue();
    }
    
    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @return Returns the Changetype.
     */
    public String getChangetype()
    {
        return getNoNullString((String) this.getProperty("changetype").getValue());
    }


    /**
     * 变更类型，1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现。
     * @param changetype
     */
    public void setChangetype(String changetype)
    {
        Property pro = new Property("changetype", changetype);
        this.setProperty(pro);
    }
    
    /**
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GVideo[");
        sb.append(this.getProperty("pkgPICURL1"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL2"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL3"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL4"));
        sb.append(',');
        sb.append(this.getProperty("sortnumber"));
        sb.append(',');
        sb.append(this.getProperty("changetype"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
}
