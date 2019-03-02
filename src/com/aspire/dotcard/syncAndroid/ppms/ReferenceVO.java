package com.aspire.dotcard.syncAndroid.ppms;

import java.util.Date;

public class ReferenceVO {
	private String id;
	private String refnodeid;
	private int sortid;
	private String goodsid;
	private String categoryid;
	private String loaddate;
	private int variation;
	private String verify_status;
	private Date verify_date;
	private int delflag;
	private int islock;
	private Date locktime;
	private String lockuser;
	private int locknum;
	private String appid;
	private String type;
	private String path;
	private String parentid;
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefnodeid() {
		return refnodeid;
	}

	public void setRefnodeid(String refnodeid) {
		this.refnodeid = refnodeid;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public String getLoaddate() {
		return loaddate;
	}

	public void setLoaddate(String loaddate) {
		this.loaddate = loaddate;
	}

	public int getVariation() {
		return variation;
	}

	public void setVariation(int variation) {
		this.variation = variation;
	}

	public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public Date getVerify_date() {
		return verify_date;
	}

	public void setVerify_date(Date verify_date) {
		this.verify_date = verify_date;
	}

	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}

	public int getIslock() {
		return islock;
	}

	public void setIslock(int islock) {
		this.islock = islock;
	}

	public Date getLocktime() {
		return locktime;
	}

	public void setLocktime(Date locktime) {
		this.locktime = locktime;
	}

	public String getLockuser() {
		return lockuser;
	}

	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}

	public int getLocknum() {
		return locknum;
	}

	public void setLocknum(int locknum) {
		this.locknum = locknum;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
