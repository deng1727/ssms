package com.aspire.dotcard.awms;

public class BaseVO {
	private String id;
	private String parentid;
	private String path;
	
	BaseVO(){
		
	}
	BaseVO(String id,String parentid,String path){
		this.id=id;
		this.parentid=parentid;
		this.path=path;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String toString(){
		return "id:"+id+",parentid:"+parentid+",path:"+path;
	}

}
