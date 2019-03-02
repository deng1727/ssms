package com.aspire.dotcard.syncAndroid.ssms;

public class InterveneVO {
	
	private long id;
	private String type;
	private String contentid;
	private int sort;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String toString() {
		return (id+":" + type+":"+contentid+":"+sort);
	}


	
}
