package com.aspire.dotcard.syncAndroid.cssp;

/**
 *
 * @author aiyan
 *
 */

//create table t_a_push(
//		id number(10),
//		pushid varchar2(20),
//		contentid varchar2(12),
//		ua varchar2(50),
//		primary key(id)
//		)
public class PushBean implements Bean{
	private String id;
	private String pushid;
	private String contentid;
	private String ua;
	private String fileName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPushid() {
		return pushid;
	}
	public void setPushid(String pushid) {
		this.pushid = pushid;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	

}
