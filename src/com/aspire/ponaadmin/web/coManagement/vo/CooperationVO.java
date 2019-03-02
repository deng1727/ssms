package com.aspire.ponaadmin.web.coManagement.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CooperationVO {
	
	//合作商ID
	private String cooperationId;
	//合作商名称
	private String cooperationName;
	//合作日期
	private Date cooperationDate;
	//状态
	private String status;
	
	/**
	 * 时间格式化
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public CooperationVO(){}

	public String getCooperationId() {
		return cooperationId;
	}

	public void setCooperationId(String cooperationId) {
		this.cooperationId = cooperationId;
	}

	public String getCooperationName() {
		return cooperationName;
	}

	public void setCooperationName(String cooperationName) {
		this.cooperationName = cooperationName;
	}

	public String getCooperationDate() {
		if(cooperationDate!=null){
			return format.format(cooperationDate);
		}else{
			return "";
		}
	}

	public void setCooperationDate(Date cooperationDate) {
		this.cooperationDate = cooperationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
