package com.aspire.dotcard.basevideosync.vo;

public class LabelsVO {

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
     * ��ǩID
     */
    private String labelId;
    /**
     * ������ǩ������
     */
    private String labelName;
    /**
     * ��ǩ������
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
