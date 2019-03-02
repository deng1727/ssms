package com.aspire.dotcard.basecommon.vo;

import java.util.Date;


public class SyncTacticEntity {
	private String id;
	private String delSql;
	private String insertSql;
	private Date effectivetime;
	private Date luptime;
	private long timeConsuming;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	public String getDelSql() {
		return delSql;
	}
	public void setDelSql(String delSql) {
		this.delSql = delSql;
	}
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public Date getEffectivetime() {
		return effectivetime;
	}
	public void setEffectivetime(Date effectivetime) {
		this.effectivetime = effectivetime;
	}
	public Date getLuptime() {
		return luptime;
	}
	public void setLuptime(Date luptime) {
		this.luptime = luptime;
	}
	public long getTimeConsuming() {
		return timeConsuming;
	}
	public void setTimeConsuming(long timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
	
	public String toString(){
		return "id="+id+",delSql="+delSql+",insertSql="+insertSql+"effectivetime="+effectivetime;
	}
	
	
	
	

}
