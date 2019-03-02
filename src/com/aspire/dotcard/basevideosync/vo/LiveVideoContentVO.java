package com.aspire.dotcard.basevideosync.vo;

import java.util.List;

public class LiveVideoContentVO {

	public LiveVideoContentVO() {
	}
	/**
	 * 节目ID（当ContentType为剧集/非剧集/直播时有效）
	 */
	private String prdContId;
	/**
	 * 内容ID（当ContentType为剧集/非剧集/直播时有效）
	 */
	private String contentId;
	/**
	 * 专题栏目ID（当ContentType为专题时有效）
	 */
	private String nodeId;
	/**
	 * 内容类型 枚举值：1-剧集、2-非剧集、3-直播，4-专题
	 */
	private String contentType;
	/**
	 * 内容长标题
	 */
	private String title;
	/**
	 * 内容短标题
	 */
	private String shortTitle;
	/**
	 * 内容描述
	 */
	private String description;
	/**
	 * 位置信息
	 */
	private String location;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	private List<String> imageLists;

	public String getPrdContId() {
		return prdContId;
	}

	public void setPrdContId(String prdContId) {
		this.prdContId = prdContId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImageLists() {
		return imageLists;
	}

	public void setImageLists(List<String> imageLists) {
		this.imageLists = imageLists;
	}

}
