package com.aspire.ponaadmin.web.dataexport.apwarn.vo;

public class ApWarnVo {

	/**
	 * ����ID
	 */
	private String contentId;
	
	/**
	 * ��������	
	 */
	private String name;
	
	/**
	 * Ԥ������  ˢ������ 1:������������   
	 * 2����ʱ���ܼ��ҵ�λ����ʱ�����  
	 *  3:������������   
	 *  4:�����û��ص���  
	 *  5:�������������޴�  
	 *  6:����ʱ��̶�
	 */
	private int warnType;
	
	/**
	 * �������ش���
	 */
	private int dayDLTimes;
	
	/**
	 * �������ش���
	 */
	private int yesterdayDLTimes;
	
	/**
	 * 7�����ش���
	 */
	private int dl7DaysCounts;
	
	/**
	 * ��������
	 */
	private String payType;
	
	/**
	 * Ԥ������
	 */
	private String warnDesc;
	
	/**
	 * ���ݼ۸�
	 */
	private int price;
	
	/**
	 * �������� 'nt:gcontent:appGame','1','nt:gcontent:appSoftWare','0','nt:gcontent:appTheme','2'
	 */
	private String type;
	
	private String appCateName;
	
	private String appCateId;
	
	private String warnDate;
	
	private String spName;
	
	private String marketDate;

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	public String getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(String warnDate) {
		this.warnDate = warnDate;
	}

	public String getAppCateName() {
		return appCateName;
	}

	public void setAppCateName(String appCateName) {
		this.appCateName = appCateName;
	}

	public String getAppCateId() {
		return appCateId;
	}

	public void setAppCateId(String appCateId) {
		this.appCateId = appCateId;
	}

	public int getWarnType() {
		return warnType;
	}

	public void setWarnType(int warnType) {
		this.warnType = warnType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getDayDLTimes() {
		return dayDLTimes;
	}

	public void setDayDLTimes(int dayDLTimes) {
		this.dayDLTimes = dayDLTimes;
	}

	public int getYesterdayDLTimes() {
		return yesterdayDLTimes;
	}

	public void setYesterdayDLTimes(int yesterdayDLTimes) {
		this.yesterdayDLTimes = yesterdayDLTimes;
	}

	public int getDl7DaysCounts() {
		return dl7DaysCounts;
	}

	public void setDl7DaysCounts(int dl7DaysCounts) {
		this.dl7DaysCounts = dl7DaysCounts;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getWarnDesc() {
		return warnDesc;
	}

	public void setWarnDesc(String warnDesc) {
		this.warnDesc = warnDesc;
	}
	
}
