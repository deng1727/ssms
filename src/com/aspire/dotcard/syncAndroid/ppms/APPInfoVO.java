package com.aspire.dotcard.syncAndroid.ppms;

public class APPInfoVO {
	private String actionCode;	
	private String transactionID;
	private String version;
	private String processTime;
	private String type;
	private String contentID;
	private String pid;
	private String imageType;
	
	
	
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProcessTime() {
		return processTime;
	}
	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("actionCode:").append(actionCode);
		sb.append("transactionID:").append(transactionID);
		sb.append("version:").append(version);
		sb.append("processTime:").append(processTime);
		sb.append("type:").append(type);
		sb.append("contentID:").append(contentID);
		return sb.toString();
		
	}
	
	
}
