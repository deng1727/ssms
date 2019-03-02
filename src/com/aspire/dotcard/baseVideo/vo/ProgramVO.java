package com.aspire.dotcard.baseVideo.vo;

public class ProgramVO
{
    /**
     * ��Ŀid
     */
    private String programId;
    
    /**
     * ��Ŀ����
     */
    private String programName;
    
    /**
     * ��Ƶid
     */
    private String videoId;
    
    /**
     * ��Ŀid
     */
    private String nodeId;
    
    /**
     * ��Ŀ����
     */
    private String nodeName;
    
    /**
     * ��Ŀ���
     */
    private String nodeDesc;
    
    /**
     * ��Ŀ���
     */
    private String desc;
    
    /**
     * ��Ŀ����д
     */
    private String shortDesc;
    
    /**
     * ��Ŀ��ʾʱ��
     */
    private String showTime;
    
    /**
     * ����޸�ʱ��
     */
    private String lastUpTime;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_Y;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_M;
    
    /**
     * ����޸�ʱ��_��
     */
    private String lastUpTime_D;
    /**
     * �Ƿ�����
     */
    private String isLink;
   /**
    * ��Ŀ·��
    */
    private String fullName;

    public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

	public String getIsLink() {
		return isLink;
	}

	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}

	public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
        
        if(this.desc.length() > 20)
        {
            this.shortDesc = this.desc.substring(0, 20) + "...";
        }
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

    public String getShowTime()
    {
        return showTime;
    }

    public void setShowTime(String showTime)
    {
        this.showTime = showTime;
    }

    public String getVideoId()
    {
        return videoId;
    }

    public void setVideoId(String videoId)
    {
        this.videoId = videoId;
    }

    public String getShortDesc()
    {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc)
    {
        this.shortDesc = shortDesc;
    }

	public String getNodeName()
	{
		return nodeName;
	}

	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	public String getNodeDesc()
	{
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc)
	{
		this.nodeDesc = nodeDesc;
		
        if(this.nodeDesc.length() > 20)
        {
            this.nodeDesc = this.nodeDesc.substring(0, 20) + "...";
        }
	}

	public String getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
    
	public String getLastUpTime_Y()
	{
		return lastUpTime_Y;
	}

	public String getLastUpTime_M()
	{
		return lastUpTime_M;
	}

	public String getLastUpTime_D()
	{
		return lastUpTime_D;
	}

	public String getLastUpTime()
	{
		return lastUpTime;
	}

	public void setLastUpTime(String lastUpTime)
	{
		this.lastUpTime = lastUpTime;
		this.lastUpTime_Y = lastUpTime.substring(0,4);
		this.lastUpTime_M = lastUpTime.substring(4,6);
		this.lastUpTime_D = lastUpTime.substring(6);
	}
}
