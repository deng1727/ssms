package com.aspire.dotcard.basegame;

public class GameService {
	
	String serviceCode;//计费业务代码
	String contentCode;//游戏内容标识ID
	String contentName;//内容名称
	String serviceName;//业务名称(计费业务名称)
	String cpId;//CP代码
	String cpName;//CP名称
	
	/*
	 * 1:客户端单机,
	*	2:客户端网游
	*	3:WAP网游(图文)
	**	4:WAP单机(图文)
	*	9:Android网游
*		10:Android单机

	 */
	String serviceType;//业务类型
	
	
	String twUrl;//图文游戏入口;业务类型为 3， 4 时提供图文游戏入口URL

	String payType;//支付方式 1:点数 2:话费
	String oldPrice;//原价资费 单位:分
	String price;//现价资费 单位:分 （与业务代码关联）
	String feeType;//资费类型:1 包外价格；2 包内价格

	/*
	 * 1：免费
2：按次/按条计费
3：包月计费
5：包次计费
7：包天计费
9：按栏目包月计费

	 */
	String billType;//计费类型
	String pkgId; //游戏包标识
	
	/*
	 * 1:当月首发
2:历史首发
99:其他

	 */
	String firstType; //首发类型
	/*
	 * 0：全价业务
1：打折业务
2：普通业务

	 */
	String discountType;//折扣类型
	/**
	 * 1:新增；2:更新；3:下线。全量文件中都是1，增量文件中
	 */
	String chargeType;//操作类型
	String scale;//分成比例
//	0:其他
//	1:CMWAP
//	2:CMNET
//	3:CMWAP,CMNET
	String connectionType;//接入方式

	//用于表示游戏包内业务是否计数。
//	0：计数
//	1：不计数
	String countFlag;//是否计数

	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTwUrl() {
		return twUrl;
	}
	public void setTwUrl(String twUrl) {
		this.twUrl = twUrl;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getPkgId() {
		return pkgId;
	}
	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}
	public String getFirstType() {
		return firstType;
	}
	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	
	public String getCountFlag() {
		return countFlag;
	}
	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}
	
	

	
	
	

}
