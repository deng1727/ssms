package com.aspire.ponaadmin.web.repository.category;

import java.util.List;

public class ProductShelvesVO {

	public ProductShelvesVO() {
	}
	
	private String actionCode;	
	
	private String transactionID;
	
	private String version;
	
	private String processTime;
	/**
	 * 货架ID
	 */
	private String shelvesID;
	
	/**
	 * 补齐的应用数目
	 */
	private String appNum;
	
	/**
	 * 上架动作: 0-全量上架,1-增量上架，2-增量下架
	 */
	private String action;
	
	private List<ApplicationVO> list;	

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

	public String getShelvesID() {
		return shelvesID;
	}

	public void setShelvesID(String shelvesID) {
		this.shelvesID = shelvesID;
	}

	public String getAppNum() {
		return appNum;
	}

	public void setAppNum(String appNum) {
		this.appNum = appNum;
	}

	public String getAction() {
		if(action == null){
			return "";
		}
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<ApplicationVO> getList() {
		return list;
	}

	public void setList(List<ApplicationVO> list) {
		this.list = list;
	}

}
