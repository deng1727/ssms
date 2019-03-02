package com.aspire.ponaadmin.web.repository ;

import java.util.Date;

/**
 * <p>����Դ���е����ýڵ�</p>
 * <p>�̳�Node������Դ���е����ýڵ㡣</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author zhangmin
 * @version 1.0.1.0
 * @since 1.0.0.0
 */
public class ReferenceNode extends Node
{	
	

    /**
     * �����õ�ʵ��ڵ��ʵ��
     */
    protected Node refNode;

    /**
     * ���췽��
     */
    public ReferenceNode()
    {
        this.type = "nt:reference";
    }

    /**
     * ���췽��
     * @param nodeID����Դid
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
     * ��ȡ���õ���Դ��id
     * @return�����õ���Դ��id
     */
    public String getRefNodeID()
    {
        return (String) this.getProperty("refNodeID").getValue();
    }

    /**
     * �������õ���Դ��id
     * @param value�����õ���Դ��id
     */
    public void setRefNodeID(String value)
    {
        Property prop = new Property("refNodeID", value);
        this.setProperty(prop);
    }

    /**
     * ��ȡ����˳��id
     * @return������˳��id
     */
    public int getSortID()
    {
        return ((Integer) this.getProperty("sortID").getValue()).intValue();
    }

    /**
     * ��������˳��id
     * @param value������˳��id
     */
    public void setSortID(int value)
    {
        Property prop = new Property("sortID", new Integer(value));
        this.setProperty(prop);
    }

    /**
     * ����������Դ��ʵ��
     * @param refNode��������Դ��ʵ��
     */
    public void setRefNode (Node refNode)
    {
        this.refNode = refNode ;
    }

    /**
     * ��ȡ������Դ��ʵ��
     * @return��������Դ��ʵ��
     */
    public Node getRefNode ()
    {
        return refNode ;
    }

    /**
     * ���û��ܱ���
     * @param value�����ܱ���
     */
    public void setCategoryID(String value)
    {
        Property pro = new Property("categoryID", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ���ܱ���
     * @return�����ܱ���
     */
    public String getCategoryID()
    {
        return (String) this.getProperty("categoryID").getValue();
    }

    /**
     * ������Ʒ����
     * @param value����Ʒ����
     */
    public void setGoodsID(String value)
    {
        Property pro = new Property("goodsID", value);
        this.setProperty(pro);
    }
    
    /*
     * ��ȡ��Ʒ�ϼ�ʱ��
     */
    public String getLoadDate()
    {
        return (String) this.getProperty("loadDate").getValue();
    }
    
    /*
     * ������Ʒ�ϼ�ʱ��
     */
    public void setLoadDate(String value)
    {
        Property pro = new Property("loadDate", value);
        this.setProperty(pro);
    }

    /**
     * ��ȡ��Ʒ����
     * @return����Ʒ����
     */
    public String getGoodsID()
    {
        return (String) this.getProperty("goodsID").getValue();
    }
    /**
     * ��ȡ����id�ı����Ϣ
     * @return
     */
    public int getVariation()
    {
    	return ((Integer) this.getProperty("variation").getValue()).intValue();
    }
    /**
     * ��������id�ı����Ϣ
     * @param value
     */
    public void setVariation(int value)
    {
    	Property pro = new Property("variation", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * ��ȡ����״̬
     * @return
     */
    public String getVerifyStatus()
    {
    	return  (String) this.getProperty("verifyStatus").getValue();
    }
    /**
     * ��������״̬
     * @param value
     */
    public void setVerifyStatus(String value)
    {
    	Property pro = new Property("verifyStatus", new String(value == null ? "" : value));
        this.setProperty(pro);
    }
    /**
     * ��ȡ����ʱ��
     * @return
     */
    public String getVerifyDate()
    {
    	return  (String) this.getProperty("verifyDate").getValue();
    }
    /**
     * ��������ʱ��
     * @param value
     */
    public void setVerifyDate(Date value)
    {
    	Property pro = new Property("verifyDate", value);
        this.setProperty(pro);
    }
    
    /**
     * �Ƿ�ɾ��
     * @return
     */
    public String getDelFlag()
    {
    	return  (String) this.getProperty("delFlag").getValue();
    }
    /**
     * �Ƿ�ɾ��
     * @param value
     */
    public void setDelFlag(String value)
    {
    	Property pro = new Property("delFlag", value);
        this.setProperty(pro);
    }
    /**
     * �Ƿ����� 0��   1��
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
     * ����������
     * @return
     */
    public String getLockUser()
    {
    	return  (String) this.getProperty("lockUser").getValue();
    }
    /**
     * ����������
     * @param value
     */
    public void setLockUser(String value)
    {
    	Property pro = new Property("lockUser", value);
        this.setProperty(pro);
    }
    /**
     * ����λ��
     * @return
     */
    public int getLockNum(){
    	return   ((Integer)this.getProperty("lockNum").getValue()).intValue();
    }
    /**
     * ����λ��
     * @return
     */
    public void setLockNum(int value){
    	Property pro = new Property("lockNum", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * ����ʱ��
     * @return
     */
    public String getLockTime()
    {
    	return  (String) this.getProperty("lockTime").getValue();
    }
    /**
     * ����ʱ��
     * @param valuelockTime
     */
    public void setLockTime(String value)
    {
    	Property pro = new Property("lockTime", value);
        this.setProperty(pro);
    }
    /**
     * toString����
     * @return ������Ϣ
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
