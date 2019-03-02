package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class BusinessVO extends Validateable implements VO {

	//这个是“排行榜”的数据列
	private String categoryValue;//榜单ID
	private String name;//榜单名称
	private String contentid;//内容标识
	private String sortid;//排序序号
	private String portal;//门户

	//这个“首发”的数据列,
	//	榜单ID
	//	榜单名称
	//	内容标识
	//	排序序号
	//	内容类型
	//	门户

	//两个数据列合并，我把“首发”的“内容类型”忽略了，呵呵。add by aiyan 2012-04-26

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
			this.addFieldError("categoryValue不能为空");
		}
		if (contentid == null || "".equals(contentid)) {
			this.addFieldError("contentid不能为空");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
