package com.aspire.dotcard.baseVideo.vo;

public class ProgramVO
{
    /**
     * 节目id
     */
    private String programId;
    
    /**
     * 节目名称
     */
    private String programName;
    
    /**
     * 视频id
     */
    private String videoId;
    
    /**
     * 栏目id
     */
    private String nodeId;
    
    /**
     * 栏目名称
     */
    private String nodeName;
    
    /**
     * 栏目简介
     */
    private String nodeDesc;
    
    /**
     * 节目简介
     */
    private String desc;
    
    /**
     * 节目简介简写
     */
    private String shortDesc;
    
    /**
     * 节目显示时长
     */
    private String showTime;
    
    /**
     * 最后修改时间
     */
    private String lastUpTime;
    
    /**
     * 最后修改时间_年
     */
    private String lastUpTime_Y;
    
    /**
     * 最后修改时间_月
     */
    private String lastUpTime_M;
    
    /**
     * 最后修改时间_日
     */
    private String lastUpTime_D;
    /**
     * 是否链接
     */
    private String isLink;
   /**
    * 栏目路径
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
