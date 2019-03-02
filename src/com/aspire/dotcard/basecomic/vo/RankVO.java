package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class RankVO extends Validateable implements VO {

	//这个是“排行榜”的数据列
	private String categoryValue;//榜单ID
	private String name;//榜单名称
	private String contentid;//内容标识
	private String sortid;//排序序号
	private String portal;//门户

	public RankVO(String[] field) {
		if (field != null && field.length >= 5) {
			this.categoryValue = field[0];
			this.name = field[1];
			this.contentid = field[2];
			//this.sortid = field[3];
			//modify 20140211 
			if(field[3] != null ){
				Integer f3 = -1*Integer.valueOf(field[3]);
				this.sortid = f3.toString();
			}
			this.portal = field[4];

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
		if (isEmpty(categoryValue)) {
			this.addFieldError("categoryValue不能为空");
		}
		if (isEmpty(name)) {
			this.addFieldError("name不能为空");
		}
		if (isEmpty(contentid)) {
			this.addFieldError("contentid不能为空");
		}
		if (isEmpty(sortid)) {
			this.addFieldError("sortid不能为空");
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
