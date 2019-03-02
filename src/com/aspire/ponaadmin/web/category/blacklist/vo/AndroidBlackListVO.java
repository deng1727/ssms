package com.aspire.ponaadmin.web.category.blacklist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AndroidBlackListVO {

	/**
     * contentID
     */
	private String contentID;
	/**
     * name
     */
	private String name;
	/**
     * 修改时间
     */
	private String opDate;
	/**
     * 修改时间
     */
	private String createDate;
	
	/**
     * 时间设置
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpDate() {
		return opDate;
	}
	public void setOpDate(Date opDate) {
		this.opDate = this.sdf.format(opDate);
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = this.sdf.format(createDate);
	}
	
}
