package com.aspire.ponaadmin.web.datasync.implement.video;

public class ProgramDetailVO
{
    /**
     * ��ĿID
     */
    private String programId;
    
    /**
     * ��Ŀ��������
     */
    private String programName;
    
    /**
     * ��ĿID
     */
    private String nodeId;
    
    /**
     * ���
     */
    private String description;
    
    /**
     * �ļ���С
     */
    private String size;
    
    /**
     * ʱ��
     */
    private String duration;
    
    /**
     * LOGO URL
     */
    private String logoUrl;
    
    /**
     * ��ĿURL
     */
    private String programUrl;
    
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getLogoUrl()
    {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getProgramId()
    {
        return programId;
    }

    public void setProgramId(String programId)
    {
        this.programId = programId;
    }

    public String getProgramName()
    {
        return programName;
    }

    public void setProgramName(String programName)
    {
        this.programName = programName;
    }

    public String getProgramUrl()
    {
        return programUrl;
    }

    public void setProgramUrl(String programUrl)
    {
        this.programUrl = programUrl;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }
}
