package com.aspire.dotcard.syncAndroid.ppms;


public class ReceiveChangeVO {
	private String id;//��Ȼ��
	private String type;//1:���ݸı䣬2��������Դ�ı䣬3�����ı䡣
	private String entityid;//contentid��pid
	private String status;//����״̬,-1����¼�룬0��������ϣ�-2������ʧ��
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
