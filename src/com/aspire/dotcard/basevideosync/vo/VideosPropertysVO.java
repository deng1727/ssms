package com.aspire.dotcard.basevideosync.vo;

public class VideosPropertysVO {

	/**
     * ����id
     */
    private String id;
	/**
     * CMS����ID,��Ŀid
     */
    private String programId;
    /**
     * CMS����ID,���ݱ��,10λ����Ψһ�̱��룬ý��IDCMS��2��ͷ��MMS��3��ͷ,�൱����ƵID
     */
    private String CMSID;
    /**
     * ���ݶ�����������
     */
    private String propertyKey;
    /**
     * ����Valueֵ
     */
    private String propertyValue;
    /**
     * ����������Ϣ
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
