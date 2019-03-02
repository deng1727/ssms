package com.aspire.dotcard.gcontent;

import java.util.ArrayList;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.repository.EntityNode;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���е����ݽڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author ����
 */
public class GApp extends EntityNode
{

    /**
     * ��־��¼����
     */
    protected static JLogger logger = LoggerFactory.getLogger(GApp.class);

    /**
     * ��Դ���ͣ�ҵ�����ݣ���������
     */
    public static final String TYPE_CONTENT = "nt:gapp";

    /**
     * ���췽��
     */
    public GApp()
    {
        this.type = TYPE_CONTENT;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
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
     * ��ȡ���ݱ���
     * @return Returns the contentID.
     */
    public String getAppID()
    {
        return (String) this.getProperty("appID").getValue();
    }


    /**
     * �������ݱ���
     * @param contentID The contentID to set.
     */
    public void setAppID(String contentID)
    {
        Property pro = new Property("appID", contentID);
        this.setProperty(pro);
    }
    /**
     * ��ȡ���ݱ���
     * @return Returns the contentID.
     */
    public String getContentID()
    {
        return (String) this.getProperty("contentID").getValue();
    }


    /**
     * �������ݱ���
     * @param contentID The contentID to set.
     */
    public void setContentID(String contentID)
    {
        Property pro = new Property("contentID", contentID);
        this.setProperty(pro);
    }

    
    /**
     * ��������
     * @return Returns the createDate.
     */
    public String getCreateDate()
    {
        return (String) this.getProperty("createDate").getValue();
    }


    /**
     * ���õ�������
     * @param createDate The createDate to set.
     */
    public void setCreateDate(String createDate)
    {
        Property pro = new Property("createDate", createDate);
        this.setProperty(pro);
    }
    
}
