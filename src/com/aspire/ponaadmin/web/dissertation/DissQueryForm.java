package com.aspire.ponaadmin.web.dissertation;

import org.apache.struts.action.ActionForm;

public class DissQueryForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3649662545595556958L;
	private String dissName;
	private Integer status=new Integer(2);
	private String tag;
	private String type="all";
	public String getDissName() {
		return dissName;
	}
	public void setDissName(String dissName) {
		this.dissName = dissName.trim();
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag.trim();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
