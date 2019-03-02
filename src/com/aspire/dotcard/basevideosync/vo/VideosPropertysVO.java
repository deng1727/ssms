package com.aspire.dotcard.basevideosync.vo;

public class VideosPropertysVO {

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
     * 内容二级分类名称
     */
    private String propertyKey;
    /**
     * 属性Value值
     */
    private String propertyValue;
    /**
     * 辅助分类信息
     */
    private String assist;
    
	public String getAssist() {
		return assist;
	}
	public void setAssist(String assist) {
		this.assist = assist;
	}
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
	public String getPropertyKey() {
		return propertyKey;
	}
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
    
}
