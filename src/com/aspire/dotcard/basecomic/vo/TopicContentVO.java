package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class TopicContentVO extends Validateable implements VO {

	
	private String contentId;// 内容标识
	private String topicId;// 目录标识
	private String sortid;// 排序序号

	public TopicContentVO(String[] field) {
		if (field != null && field.length >= 3) {
			this.contentId = field[0];
			this.topicId = field[1];
			this.sortid = field[2];
		}

	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}



	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (contentId == null || "".equals(contentId)) {
			this.addFieldError("contentId不能为空");
		}

		if (topicId == null || "".equals(topicId)) {
			this.addFieldError("topicId不能为空");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
