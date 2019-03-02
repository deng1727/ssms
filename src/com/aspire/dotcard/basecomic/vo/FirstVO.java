package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class FirstVO extends Validateable implements VO {

	private String contentid;//内容标识
	private String sortid;//排序序号
	private String type;//内容类型
	private String portal;//门户

	public FirstVO(String[] field) {
		if (field != null && field.length >= 4) {
			this.contentid = field[0];
			this.sortid = field[1];
			this.type = field[2];
			this.portal = field[3];

		}

	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (isEmpty(contentid)) {
			this.addFieldError("contentid不能为空");
		}
		if (isEmpty(sortid)) {
			this.addFieldError("sortid不能为空");
		}
		if (isEmpty(type)) {
			this.addFieldError("type不能为空");
		}
		if (isEmpty(portal)) {
			this.addFieldError("portal不能为空");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
