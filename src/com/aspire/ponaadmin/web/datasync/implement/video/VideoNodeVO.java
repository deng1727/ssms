/*
 * �ļ�����VideoNodeVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.datasync.implement.video;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * @author wangminlong
 * @version 
 */
public class VideoNodeVO
{
    /**
     * ��ĿId
     */
    private String nodeId;
    
    /**
     * ��Ŀ��������
     */
    private String nodeName;
    
    /**
     * �㼶
     */
    private String classLevel;
    
    /**
     * MM�ͻ���չʾ���
     */
    private String showPosition;
    
    /**
     * �������Ż�
     */
    private String accessType;

    public String getAccessType()
    {
        return accessType;
    }

    public void setAccessType(String accessType)
    {
        this.accessType = accessType;
    }

    public String getClassLevel()
    {
        return classLevel;
    }

    public void setClassLevel(String classLevel)
    {
        this.classLevel = classLevel;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getShowPosition()
    {
        return showPosition;
    }

    public void setShowPosition(String showPosition)
    {
        this.showPosition = showPosition;
    }
    
    
    

}
