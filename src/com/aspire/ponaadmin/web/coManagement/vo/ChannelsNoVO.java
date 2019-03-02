package com.aspire.ponaadmin.web.coManagement.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChannelsNoVO {

	public ChannelsNoVO() {
	}
	//������
	private String channelsNo;
	//����ʱ��
	private Date createDate;
	//����״̬
	private String status;
	//������
	private String operator;
	
	private String total;
	
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * ʱ���ʽ��
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public String getChannelsNo() {
		return channelsNo;
	}

	public void setChannelsNo(String channelsNo) {
		this.channelsNo = channelsNo;
	}

	public String getCreateDate() {
		if(createDate!=null){
			return format.format(createDate);
		}else{
			return "";
		}
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
