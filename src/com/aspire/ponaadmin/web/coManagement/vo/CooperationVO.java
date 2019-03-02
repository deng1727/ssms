package com.aspire.ponaadmin.web.coManagement.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CooperationVO {
	
	//������ID
	private String cooperationId;
	//����������
	private String cooperationName;
	//��������
	private Date cooperationDate;
	//״̬
	private String status;
	
	/**
	 * ʱ���ʽ��
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
