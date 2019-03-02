package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class BusinessVO extends Validateable implements VO {

	//����ǡ����а񡱵�������
	private String categoryValue;//��ID
	private String name;//������
	private String contentid;//���ݱ�ʶ
	private String sortid;//�������
	private String portal;//�Ż�

	//������׷�����������,
	//	��ID
	//	������
	//	���ݱ�ʶ
	//	�������
	//	��������
	//	�Ż�

	//���������кϲ����Ұѡ��׷����ġ��������͡������ˣ��Ǻǡ�add by aiyan 2012-04-26

	public BusinessVO(String[] field) {
		if (field != null && field.length >= 5) {
			this.categoryValue = field[0];
			this.name = field[1];
			this.contentid = field[2];
			this.sortid = field[3];
			if (field.length >= 6) {
				this.portal = field[5];
			} else {
				this.portal = field[4];
			}

		}

	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
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
		if (categoryValue == null || "".equals(categoryValue)) {
			this.addFieldError("categoryValue����Ϊ��");
		}
		if (contentid == null || "".equals(contentid)) {
			this.addFieldError("contentid����Ϊ��");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
