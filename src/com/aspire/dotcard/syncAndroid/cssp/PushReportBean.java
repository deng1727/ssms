package com.aspire.dotcard.syncAndroid.cssp;

/**
 * 
 * @author aiyan
 *
 */
//create table t_a_pushreport(
//		id number(10),
//		pushid varchar2(20),
//		status number(10),
//		record_time varchar2(14),
//		LUPDATE date default sysdate,
//		primary key(id)
//		)
public class PushReportBean implements Bean{
	private String id;
	private String pushid;
	private String recordtime;
	private String status;
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
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
