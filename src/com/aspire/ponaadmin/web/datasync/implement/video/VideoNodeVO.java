/*
 * 文件名：VideoNodeVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 栏目Id
     */
    private String nodeId;
    
    /**
     * 栏目中文名称
     */
    private String nodeName;
    
    /**
     * 层级
     */
    private String classLevel;
    
    /**
     * MM客户端展示情况
     */
    private String showPosition;
    
    /**
     * 可适用门户
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
