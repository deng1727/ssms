package com.aspire.dotcard.syncAndroid.ppms;


public class ReceiveChangeVO {
	private String id;//自然键
	private String type;//1:内容改变，2：适配资源改变，3：都改变。
	private String entityid;//contentid或pid
	private String status;//处理状态,-1：刚录入，0：处理完毕，-2，处理失败
	private String imagetype;
	private String opt;
	private String appid;
	
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getImagetype() {
		return imagetype;
	}
	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(id);
		sb.append("type:").append(type);
		sb.append("entityid:").append(entityid);
		sb.append("status:").append(status);
		return sb.toString();
		
	}
	

}
