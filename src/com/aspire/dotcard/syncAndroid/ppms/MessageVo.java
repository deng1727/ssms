package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Date;

public class MessageVo {
	private String id;
	private String type;
	private String message;
	private String status;
	private Date lupdate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLupdate() {
		return lupdate;
	}
	public void setLupdate(Date lupdate) {
		this.lupdate = lupdate;
	}

}
