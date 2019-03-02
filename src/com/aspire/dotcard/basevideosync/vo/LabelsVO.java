package com.aspire.dotcard.basevideosync.vo;

public class LabelsVO {

	/**
     * 主键id
     */
    private String id;
	/**
     * CMS内容ID,节目id
     */
    private String programId;
    /**
     * CMS内容ID,内容编号,10位内容唯一短编码，媒资IDCMS以2开头，MMS以3开头,相当于视频ID
     */
    private String CMSID;
    /**
     * 标签ID
     */
    private String labelId;
    /**
     * 单个标签的名称
     */
    private String labelName;
    /**
     * 标签组名称
     */
    private String labelZone;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCMSID() {
		return CMSID;
	}
	public void setCMSID(String cMSID) {
		CMSID = cMSID;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getLabelZone() {
		return labelZone;
	}
	public void setLabelZone(String labelZone) {
		this.labelZone = labelZone;
	}
}
