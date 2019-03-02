package com.aspire.dotcard.gcontent;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.EntityNode;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的内容节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author 张敏
 */
public class GApp extends EntityNode
{

    /**
     * 日志记录对象
     */
    protected static JLogger logger = LoggerFactory.getLogger(GApp.class);

    /**
     * 资源类型：业务内容，内容类型
     */
    public static final String TYPE_CONTENT = "nt:gapp";

    /**
     * 构造方法
     */
    public GApp()
    {
        this.type = TYPE_CONTENT;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GApp(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_CONTENT;
    }
    
    
    
    
    
//    public String getId(){
//    	 return (String) this.getProperty("id").getValue();
//    }
//    public void setId(String id){
//        Property pro = new Property("id", id);
//        this.setProperty(pro);
//    }
    
    
    /**
     * 获取内容编码
     * @return Returns the contentID.
     */
    public String getAppID()
    {
        return (String) this.getProperty("appID").getValue();
    }


    /**
     * 设置内容编码
     * @param contentID The contentID to set.
     */
    public void setAppID(String contentID)
    {
        Property pro = new Property("appID", contentID);
        this.setProperty(pro);
    }
    /**
     * 获取内容编码
     * @return Returns the contentID.
     */
    public String getContentID()
    {
        return (String) this.getProperty("contentID").getValue();
    }


    /**
     * 设置内容编码
     * @param contentID The contentID to set.
     */
    public void setContentID(String contentID)
    {
        Property pro = new Property("contentID", contentID);
        this.setProperty(pro);
    }

    
    /**
     * 创建日期
     * @return Returns the createDate.
     */
    public String getCreateDate()
    {
        return (String) this.getProperty("createDate").getValue();
    }


    /**
     * 设置导入日期
     * @param createDate The createDate to set.
     */
    public void setCreateDate(String createDate)
    {
        Property pro = new Property("createDate", createDate);
        this.setProperty(pro);
    }
    
}
