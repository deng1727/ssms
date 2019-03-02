package com.aspire.dotcard.basevideosync.vo;

import java.util.Date;

public class VideoReferenceVO {

	/**
	 * 商品id
	 */
	private String refId;

	/**
	 * 节目id
	 */
	private String programId;

	/**
	 * 节目名称
	 */
	private String programName;

	/**
	 * 内容id
	 */
	private String cmsId;

	/**
	 * 货架id
	 */
	private String categoryId;

	/**
	 * 排序id
	 */
	private int sortId;
	/**
	 * 二级分类名称
	 */
	private String subcateName;
	/**
	 * 一级标签名称
	 */
	private String tagName;
	/**
	 * 关键字名称
	 */
	private String keyName;

	/**
	 * 最后修改时间
	 */
	private String lastUpTime;
	/**
	 * 审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
	 */
	private String verifyStatus;
	/**
	 * 审批日期
	 */
	private Date verifyDate;
	/**
	 * 删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过
	 */
	private String delflag;
	/**
	 * 资费类型,枚举值：1-免费；2-收费; 3-仅支持按次
	 */
	private String feetype;

	/**
	 * 播出年代
	 */
	private String broadcast;
	/**
	 * 国家及地区
	 */
	private String countriy;
	/**
	 * 内容类型
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
