package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class TopicVO extends Validateable implements VO {
	private String topicId;// 专题、子专题标识
	
	//如果父标识为固定的“专题”目录ID，则值默认为1，且本行数据为具体专题；
	private String parentTopicId;// 父标识
	
	
	private String name;// 名称

	public TopicVO(String[] field) {
		if (field != null && field.length >= 3) {
			this.topicId = field[0];
			this.parentTopicId = field[1];
			this.name = field[2];
		}

	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getParentTopicId() {
		return parentTopicId;
	}

	public void setParentTopicId(String parentTopicId) {
		this.parentTopicId = parentTopicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (topicId == null || "".equals(topicId)) {
			this.addFieldError("topicId不能为空");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return topicId + "|" + parentTopicId;
	}

}
