package com.aspire.dotcard.syncAndroid.cssp;

/**
 *
 * @author aiyan
 *
 */

//create table t_a_order_log (
//		contentid varchar2(50),
//		subsplace number(2),
//		ua varchar2(256),
//		status number(1) default -1,
//		lupdate date default sysdate
//		)
public class ReportBean implements Bean{
	private String contentid;
	private String subsplace;
	private String ua;
	
	private String filename;
	
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getSubsplace() {
		return subsplace;
	}
	public void setSubsplace(String subsplace) {
		this.subsplace = subsplace;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	

	
	

}
