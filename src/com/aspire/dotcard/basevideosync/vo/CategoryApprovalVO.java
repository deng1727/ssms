package com.aspire.dotcard.basevideosync.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 音乐货架管理-审批发布
 * 
 * @author duyongchun
 *
 */
public class CategoryApprovalVO {
	
	private String id;
	/**
	 * 货架ID
	 */
	private String categoryId;
	/**
	 * 货架名称
	 */
	private String categoryName;
	/**
	 * 货架排序号
	 */
	private String sortId;
	/**
	 * 审批状态
	 */
	private String approvalStatus;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间
	 */
	private Date operatorTime;
	/**
	 * 审批人
	 */
	private String approval;
	/**
	 * 审批时间
	 */
	private Date approvalTime;
	/**
	 * 用于标识处理方式： 1 货架分类管理; 2 货架商品管理
	 */
	private String operation;
	
	/**
	 * 时间格式化
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

