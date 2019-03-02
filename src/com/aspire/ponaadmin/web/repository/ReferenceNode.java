package com.aspire.ponaadmin.web.repository ;

import java.util.Date;

/**
 * <p>是资源树中的引用节点</p>
 * <p>继承Node，是资源树中的引用节点。</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */
public class ReferenceNode extends Node
{	
	

    /**
     * 所引用的实体节点的实例
     */
    protected Node refNode;

    /**
     * 构造方法
     */
    public ReferenceNode()
    {
        this.type = "nt:reference";
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public ReferenceNode(String nodeID)
    {
        super(nodeID);
        this.type = "nt:reference";
    }
    
    
    
    public String getAppId(){
    	
    	return (String) this.getProperty("appId").getValue();
    }
    
    public void setAppId(String appId)
    {
        Property prop = new Property("appId", appId);
        this.setProperty(prop);
    }
    
    /**
     * 获取引用的资源的id
     * @return，引用的资源的id
     */
    public String getRefNodeID()
    {
        return (String) this.getProperty("refNodeID").getValue();
    }

    /**
     * 设置引用的资源的id
     * @param value，引用的资源的id
     */
    public void setRefNodeID(String value)
    {
        Property prop = new Property("refNodeID", value);
        this.setProperty(prop);
    }

    /**
     * 获取排序顺序id
     * @return，排序顺序id
     */
    public int getSortID()
    {
        return ((Integer) this.getProperty("sortID").getValue()).intValue();
    }

    /**
     * 设置排序顺序id
     * @param value，排序顺序id
     */
    public void setSortID(int value)
    {
        Property prop = new Property("sortID", new Integer(value));
        this.setProperty(prop);
    }

    /**
     * 设置引用资源的实例
     * @param refNode，引用资源的实例
     */
    public void setRefNode (Node refNode)
    {
        this.refNode = refNode ;
    }

    /**
     * 获取引用资源的实例
     * @return，引用资源的实例
     */
    public Node getRefNode ()
    {
        return refNode ;
    }

    /**
     * 设置货架编码
     * @param value，货架编码
     */
    public void setCategoryID(String value)
    {
        Property pro = new Property("categoryID", value);
        this.setProperty(pro);
    }

    /**
     * 获取货架编码
     * @return，货架编码
     */
    public String getCategoryID()
    {
        return (String) this.getProperty("categoryID").getValue();
    }

    /**
     * 设置商品编码
     * @param value，商品编码
     */
    public void setGoodsID(String value)
    {
        Property pro = new Property("goodsID", value);
        this.setProperty(pro);
    }
    
    /*
     * 获取商品上架时间
     */
    public String getLoadDate()
    {
        return (String) this.getProperty("loadDate").getValue();
    }
    
    /*
     * 设置商品上架时间
     */
    public void setLoadDate(String value)
    {
        Property pro = new Property("loadDate", value);
        this.setProperty(pro);
    }

    /**
     * 获取商品编码
     * @return，商品编码
     */
    public String getGoodsID()
    {
        return (String) this.getProperty("goodsID").getValue();
    }
    /**
     * 获取排序id的变更信息
     * @return
     */
    public int getVariation()
    {
    	return ((Integer) this.getProperty("variation").getValue()).intValue();
    }
    /**
     * 设置排序id的变更信息
     * @param value
     */
    public void setVariation(int value)
    {
    	Property pro = new Property("variation", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * 获取审批状态
     * @return
     */
    public String getVerifyStatus()
    {
    	return  (String) this.getProperty("verifyStatus").getValue();
    }
    /**
     * 设置审批状态
     * @param value
     */
    public void setVerifyStatus(String value)
    {
    	Property pro = new Property("verifyStatus", new String(value == null ? "" : value));
        this.setProperty(pro);
    }
    /**
     * 获取审批时间
     * @return
     */
    public String getVerifyDate()
    {
    	return  (String) this.getProperty("verifyDate").getValue();
    }
    /**
     * 设置审批时间
     * @param value
     */
    public void setVerifyDate(Date value)
    {
    	Property pro = new Property("verifyDate", value);
        this.setProperty(pro);
    }
    
    /**
     * 是否删除
     * @return
     */
    public String getDelFlag()
    {
    	return  (String) this.getProperty("delFlag").getValue();
    }
    /**
     * 是否删除
     * @param value
     */
    public void setDelFlag(String value)
    {
    	Property pro = new Property("delFlag", value);
        this.setProperty(pro);
    }
    /**
     * 是否被锁定 0是   1否
     * @return
     */
    public int getIsLock(){
    	return   ((Integer)this.getProperty("isLock").getValue()).intValue();
    }
    public void setIsLock(int value){
    	Property pro = new Property("isLock", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 锁定操作人
     * @return
     */
    public String getLockUser()
    {
    	return  (String) this.getProperty("lockUser").getValue();
    }
    /**
     * 锁定操作人
     * @param value
     */
    public void setLockUser(String value)
    {
    	Property pro = new Property("lockUser", value);
        this.setProperty(pro);
    }
    /**
     * 锁定位置
     * @return
     */
    public int getLockNum(){
    	return   ((Integer)this.getProperty("lockNum").getValue()).intValue();
    }
    /**
     * 锁定位置
     * @return
     */
    public void setLockNum(int value){
    	Property pro = new Property("lockNum", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * 锁定时间
     * @return
     */
    public String getLockTime()
    {
    	return  (String) this.getProperty("lockTime").getValue();
    }
    /**
     * 锁定时间
     * @param valuelockTime
     */
    public void setLockTime(String value)
    {
    	Property pro = new Property("lockTime", value);
        this.setProperty(pro);
    }
    /**
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("ReferenceNode[");
        sb.append(this.getProperty("refType"));
        sb.append(',');
        sb.append(this.getProperty("refNodeID"));
        sb.append(',');
        sb.append(this.getProperty("refType"));
        sb.append(',');
        sb.append(this.getProperty("sortID"));
        sb.append(',');
        sb.append(this.getProperty("goodsID"));
        sb.append(',');
        sb.append(this.getProperty("variation"));
        sb.append(',');
        sb.append(this.getProperty("delFlag"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
}
