package com.aspire.dotcard.basevideosync.vo;

public class SalesPromotionVO {

	public SalesPromotionVO() {
	}
	/**
	 * 促销产品ID
	 */
	private String salesProductId;
	/**
	 * 促销类型    1.预售  2.预订   3.限免
	 */
	private String salesCategory;
	/**
	 * 促销开始时间
	 */
	private String salesStartTime;
	/**
	 * 促销结束时间
	 */
	private String salesEndTime;
	/**
	 * 折扣
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
