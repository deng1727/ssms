package com.aspire.dotcard.basevideosync.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ֻ��ܹ���-��������
 * 
 * @author duyongchun
 *
 */
public class CategoryApprovalVO {
	
	private String id;
	/**
	 * ����ID
	 */
	private String categoryId;
	/**
	 * ��������
	 */
	private String categoryName;
	/**
	 * ���������
	 */
	private String sortId;
	/**
	 * ����״̬
	 */
	private String approvalStatus;
	/**
	 * ������
	 */
	private String operator;
	/**
	 * ����ʱ��
	 */
	private Date operatorTime;
	/**
	 * ������
	 */
	private String approval;
	/**
	 * ����ʱ��
	 */
	private Date approvalTime;
	/**
	 * ���ڱ�ʶ����ʽ�� 1 ���ܷ������; 2 ������Ʒ����
	 */
	private String operation;
	
	/**
	 * ʱ���ʽ��
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getOperatorTime() {
		if(operatorTime!=null){
			return format.format(operatorTime);
		}else{
			return "";
		}
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getApprovalTime() {
		if(approvalTime!=null){
			return format.format(approvalTime);
		}else{
			return "";
		}
		
	}
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}

