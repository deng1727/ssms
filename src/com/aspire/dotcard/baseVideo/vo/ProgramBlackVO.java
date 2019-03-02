package com.aspire.dotcard.baseVideo.vo;

public class ProgramBlackVO {
	private String id;
	  /**
     * 节目id
     */
    private String programId;
    
    /**
     * 节目名称
     */
    private String programName;
    /**
     * 栏目id
     */
    private String nodeId;
    /**
     * 内容id
     */
    private String videoId;
    /**
     * 最后修改时间
     */
    private String lastUpTime;
    
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

	public String getLastUpTime() {
		return lastUpTime;
	}
	public void setLastUpTime(String lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
    
}

