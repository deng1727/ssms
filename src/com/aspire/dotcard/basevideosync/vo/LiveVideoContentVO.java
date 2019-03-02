package com.aspire.dotcard.basevideosync.vo;

import java.util.List;

public class LiveVideoContentVO {

	public LiveVideoContentVO() {
	}
	/**
	 * ��ĿID����ContentTypeΪ�缯/�Ǿ缯/ֱ��ʱ��Ч��
	 */
	private String prdContId;
	/**
	 * ����ID����ContentTypeΪ�缯/�Ǿ缯/ֱ��ʱ��Ч��
	 */
	private String contentId;
	/**
	 * ר����ĿID����ContentTypeΪר��ʱ��Ч��
	 */
	private String nodeId;
	/**
	 * �������� ö��ֵ��1-�缯��2-�Ǿ缯��3-ֱ����4-ר��
	 */
	private String contentType;
	/**
	 * ���ݳ�����
	 */
	private String title;
	/**
	 * ���ݶ̱���
	 */
	private String shortTitle;
	/**
	 * ��������
	 */
	private String description;
	/**
	 * λ����Ϣ
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
