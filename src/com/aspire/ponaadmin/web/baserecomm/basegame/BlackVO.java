package com.aspire.ponaadmin.web.baserecomm.basegame;

import java.util.Date;

public class BlackVO
{
	
    private String id;
    private String icpservid;
    private String servname;
    private int oldprice;
    private int mobileprice;
    private String servdesc;
    private String createby;
    private Date createDate;
    private Date lupdate;
    private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcpservid() {
		return icpservid;
	}
	public void setIcpservid(String icpservid) {
		this.icpservid = icpservid;
	}
	public String getServname() {
		return servname;
	}
	public void setServname(String servname) {
		this.servname = servname;
	}
	public int getOldprice() {
		return oldprice;
	}
	public void setOldprice(int oldprice) {
		this.oldprice = oldprice;
	}
	public int getMobileprice() {
		return mobileprice;
	}
	public void setMobileprice(int mobileprice) {
		this.mobileprice = mobileprice;
	}
	public String getServdesc() {
		return servdesc;
	}
	public void setServdesc(String servdesc) {
		this.servdesc = servdesc;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLupdate() {
		return lupdate;
	}
	public void setLupdate(Date lupdate) {
		this.lupdate = lupdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

   

}
