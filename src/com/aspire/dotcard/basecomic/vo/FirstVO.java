package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class FirstVO extends Validateable implements VO {

	private String contentid;//���ݱ�ʶ
	private String sortid;//�������
	private String type;//��������
	private String portal;//�Ż�

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
			this.addFieldError("contentid����Ϊ��");
		}
		if (isEmpty(sortid)) {
			this.addFieldError("sortid����Ϊ��");
		}
		if (isEmpty(type)) {
			this.addFieldError("type����Ϊ��");
		}
		if (isEmpty(portal)) {
			this.addFieldError("portal����Ϊ��");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
