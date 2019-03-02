package com.aspire.dotcard.baseVideo.vo;

import java.util.Date;

public class VideoRefVO
{
    /**
     * ��Ʒid
     */
    private String refId;
    
    /**
     * ��Ŀid
     */
    private String programId;
    
    /**
     * ��Ŀ����
     */
    private String programName;
    
    /**
     * ����id
     */
    private String categoryId;
    
    /**
     * ����id
     */
    private int sortId;
    
    /**
     * ��Ŀid
     */
    private String nodeId;
    
    /**
     * ����ʱ��
     */
    private Date exportTime;
    
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
     * ��ʼʱ��
     */
    private String startTime;
    
    /**
     * ����ʱ��
     */
    private String endTime;
    
    /**
     * ��Ƶid ע������id
     */
    private String videoId;
    /**
     * �Ƿ�����
     */
    private String isLink;
    /**
     * ��Ŀ·��
     */
    private String fullName;
    
    private String verify_status;
    
    public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

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

	public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public Date getExportTime()
    {
        return exportTime;
    }

    public void setExportTime(Date exportTime)
    {
        this.exportTime = exportTime;
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

    public String getRefId()
    {
        return refId;
    }

    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    public int getSortId()
    {
        return sortId;
    }

    public void setSortId(int sortId)
    {
        this.sortId = sortId;
    }

	public String getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
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

	public String getVideoId()
	{
		return videoId;
	}

	public void setVideoId(String videoId)
	{
		this.videoId = videoId;
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
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
