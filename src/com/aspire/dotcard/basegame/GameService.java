package com.aspire.dotcard.basegame;

public class GameService {
	
	String serviceCode;//�Ʒ�ҵ�����
	String contentCode;//��Ϸ���ݱ�ʶID
	String contentName;//��������
	String serviceName;//ҵ������(�Ʒ�ҵ������)
	String cpId;//CP����
	String cpName;//CP����
	
	/*
	 * 1:�ͻ��˵���,
	*	2:�ͻ�������
	*	3:WAP����(ͼ��)
	**	4:WAP����(ͼ��)
	*	9:Android����
*		10:Android����

	 */
	String serviceType;//ҵ������
	
	
	String twUrl;//ͼ����Ϸ���;ҵ������Ϊ 3�� 4 ʱ�ṩͼ����Ϸ���URL

	String payType;//֧����ʽ 1:���� 2:����
	String oldPrice;//ԭ���ʷ� ��λ:��
	String price;//�ּ��ʷ� ��λ:�� ����ҵ����������
	String feeType;//�ʷ�����:1 ����۸�2 ���ڼ۸�

	/*
	 * 1�����
2������/�����Ʒ�
3�����¼Ʒ�
5�����μƷ�
7������Ʒ�
9������Ŀ���¼Ʒ�

	 */
	String billType;//�Ʒ�����
	String pkgId; //��Ϸ����ʶ
	
	/*
	 * 1:�����׷�
2:��ʷ�׷�
99:����

	 */
	String firstType; //�׷�����
	/*
	 * 0��ȫ��ҵ��
1������ҵ��
2����ͨҵ��

	 */
	String discountType;//�ۿ�����
	/**
	 * 1:������2:���£�3:���ߡ�ȫ���ļ��ж���1�������ļ���
	 */
	String chargeType;//��������
	String scale;//�ֳɱ���
//	0:����
//	1:CMWAP
//	2:CMNET
//	3:CMWAP,CMNET
	String connectionType;//���뷽ʽ

	//���ڱ�ʾ��Ϸ����ҵ���Ƿ������
//	0������
//	1��������
	String countFlag;//�Ƿ����

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
