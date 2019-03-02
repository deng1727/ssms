package com.aspire.dotcard.basegame;

public class GameTypeEntity{
	private String id;
	private String name;
	private String mmid;
	private String mmname;
	public GameTypeEntity(String id,String name,String mmid,String mmname){
		this.id=id;
		this.name=name;
		this.mmid=mmid;
		this.mmname=mmname;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMmid() {
		return mmid;
	}
	public void setMmid(String mmid) {
		this.mmid = mmid;
	}
	public String getMmname() {
		return mmname;
	}
	public void setMmname(String mmname) {
		this.mmname = mmname;
	}
	
	
	
}
