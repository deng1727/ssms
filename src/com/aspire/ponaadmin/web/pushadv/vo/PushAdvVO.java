package com.aspire.ponaadmin.web.pushadv.vo;

public class PushAdvVO {
	/*
	 * 主键id
	 */
	private String id;
	/*
	 * 应用id
	 */
	private String contentId;
	/*
	 * 应用名称
	 */
	private String contentName;
	/*
	 * 主标题
	 */
	private String title;
	/*
	 * 副标题
	 */
	private String subTitle;
	/*
	 * 开始时间
	 */
	private String startTime;
	/*
	 * 结束时间
	 */
	private String endTime;
	/*
	 * 推荐品牌
	 */
	private String rebrand;
	/*
	 * 推荐型号
	 */
	private String redevice;
	
	/*
	 * 推荐类型
	 */
	
	private String type ;
	/*
	 * 推荐图片url
	 */
	
	private String picUrl ;
	/*
	 * 推荐必备版本号
	 */
	
	private String versions ;
	/*
	 * 推荐uri
	 */
	private String uri ;
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getRedevice() {
		return redevice;
	}
	public void setRedevice(String redevice) {
		this.redevice = redevice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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
	public String getRebrand() {
		return rebrand;
	}
	public void setRebrand(String rebrand) {
		this.rebrand = rebrand;
	}
}
