package com.aspire.dotcard.baseVideo.vo;

import java.util.Date;

public class BlackVO {
	private String id;
    /**
     * ��Ŀid
     */
    private String programId;
    
    /**
     * ��Ŀ����
     */
    private String programName;
    /**
     * ��Ŀid
     */
    private String nodeId;
    /**
     * ����id
     */
    private String videoId;
    /**
     * ����޸�ʱ��
     */
    private Date lastUpTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Date getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
    
}
