package com.aspire.dotcard.basevideosync.vo;

public class SalesPromotionVO {

	public SalesPromotionVO() {
	}
	/**
	 * ������ƷID
	 */
	private String salesProductId;
	/**
	 * ��������    1.Ԥ��  2.Ԥ��   3.����
	 */
	private String salesCategory;
	/**
	 * ������ʼʱ��
	 */
	private String salesStartTime;
	/**
	 * ��������ʱ��
	 */
	private String salesEndTime;
	/**
	 * �ۿ�
	 */
	private String salesDiscount;
	public String getSalesProductId() {
		return salesProductId;
	}
	public void setSalesProductId(String salesProductId) {
		this.salesProductId = salesProductId;
	}
	public String getSalesCategory() {
		return salesCategory;
	}
	public void setSalesCategory(String salesCategory) {
		this.salesCategory = salesCategory;
	}
	public String getSalesStartTime() {
		return salesStartTime;
	}
	public void setSalesStartTime(String salesStartTime) {
		this.salesStartTime = salesStartTime;
	}
	public String getSalesEndTime() {
		return salesEndTime;
	}
	public void setSalesEndTime(String salesEndTime) {
		this.salesEndTime = salesEndTime;
	}
	public String getSalesDiscount() {
		return salesDiscount;
	}
	public void setSalesDiscount(String salesDiscount) {
		this.salesDiscount = salesDiscount;
	}
}
