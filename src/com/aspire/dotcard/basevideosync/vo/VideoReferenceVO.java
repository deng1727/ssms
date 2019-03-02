package com.aspire.dotcard.basevideosync.vo;

import java.util.Date;

public class VideoReferenceVO {

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
	private String cmsId;

	/**
	 * ����id
	 */
	private String categoryId;

	/**
	 * ����id
	 */
	private int sortId;
	/**
	 * ������������
	 */
	private String subcateName;
	/**
	 * һ����ǩ����
	 */
	private String tagName;
	/**
	 * �ؼ�������
	 */
	private String keyName;

	/**
	 * ����޸�ʱ��
	 */
	private String lastUpTime;
	/**
	 * ����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
	 */
	private String verifyStatus;
	/**
	 * ��������
	 */
	private Date verifyDate;
	/**
	 * ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��
	 */
	private String delflag;
	/**
	 * �ʷ�����,ö��ֵ��1-��ѣ�2-�շ�; 3-��֧�ְ���
	 */
	private String feetype;

	/**
	 * �������
	 */
	private String broadcast;
	/**
	 * ���Ҽ�����
	 */
	private String countriy;
	/**
	 * ��������
	 */
	private String contentType;
	
	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	public String getCountriy() {
		return countriy;
	}

	public void setCountriy(String countriy) {
		this.countriy = countriy;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
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

	public String getCmsId() {
		return cmsId;
	}

	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getLastUpTime() {
		return lastUpTime;
	}

	public void setLastUpTime(String lastUpTime) {
		this.lastUpTime = lastUpTime;
	}

	public String getSubcateName() {
		if(subcateName == null)
			subcateName = "";
		return subcateName;
	}

	public void setSubcateName(String subcateName) {
		this.subcateName = subcateName;
	}

	public String getTagName() {
		if(tagName == null)
			tagName = "";
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public String getKeyName() {
		if(keyName == null)
			keyName = "";
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	
}
