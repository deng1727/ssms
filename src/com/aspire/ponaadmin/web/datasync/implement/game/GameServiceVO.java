package com.aspire.ponaadmin.web.datasync.implement.game;
/**
 * ������Ϸ��ҵ����Ϣ��
 * @author zhangwei
 *
 */
public class GameServiceVO
{
	/**
	 * ����id
	 */
	private String contentid;
	/**
	 * ��Ʒ������CP
	 */
	private String icpCode;
	/**
	 * CP����
	 */
	private String spName;
	/**
	 * ��Ʒ��ҵ�����
	 */
	private String icpServid;
	/**
	 * ��Ʒ���ơ�
	 */
	private String servName;
	/**
	 * ��Ʒ���
	 */
	private String servDesc;
	/**
	 * �ʷ�
	 */
	private int mobilePrice;
	/**
	 * �Ʒ�����
	 */
	private String chargeType;
	/**
	 * �ʷѱ���
	 */
	private String chargeDesc;
	/**
	 * ҵ�����͡�
	 */
	private int servType;
	/**
	 * ҵ���ʶ
	 */
	private int servFlag;
	/**
	 * ҵ���ƹ㷽ʽ
	 */
	private int ptypeId;
	
	//add by aiyan 2012-09-23 ����Ϸ�������ֶ�
	private String oldprice;//ԭ��
	private String firsttype;//�׷�����
	private String contenttag;//���ظ���ԭʼ����ID 
	/**
	 * ������ʱ��
	 */
	private String lupDDate;
	
	public String getIcpCode()
	{
		return icpCode;
	}
	public void setIcpCode(String icpCode)
	{
		this.icpCode = icpCode;
	}
	public String getSpName()
	{
		return spName;
	}
	public void setSpName(String spName)
	{
		this.spName = spName;
	}
	public String getIcpServid()
	{
		return icpServid;
	}
	public void setIcpServid(String icpServid)
	{
		this.icpServid = icpServid;
	}
	public String getServName()
	{
		return servName;
	}
	public void setServName(String servName)
	{
		this.servName = servName;
	}
	public String getServDesc()
	{
		return servDesc;
	}
	public void setServDesc(String servDesc)
	{
		this.servDesc = servDesc;
	}
	public int getMobilePrice()
	{
		return mobilePrice;
	}
	public void setMobilePrice(int mobilePrice)
	{
		this.mobilePrice = mobilePrice;
	}
	public String getChargeType()
	{
		return chargeType;
	}
	public void setChargeType(String chargeType)
	{
		this.chargeType = chargeType;
	}
	public String getChargDesc()
	{
		return chargeDesc;
	}
	public void setChargDesc(String chargDesc)
	{
		this.chargeDesc = chargDesc;
	}
	public int getServType()
	{
		return servType;
	}
	public void setServType(int servType)
	{
		this.servType = servType;
	}
	public int getServFlag()
	{
		return servFlag;
	}
	public void setServFlag(int servFlag)
	{
		this.servFlag = servFlag;
	}
	public int getPtypeId()
	{
		return ptypeId;
	}
	public void setPtypeId(int ptypeId)
	{
		this.ptypeId = ptypeId;
	}
	public String getLupDDate()
	{
		return lupDDate;
	}
	public void setLupDDate(String lupDDate)
	{
		this.lupDDate = lupDDate;
	}
	public String getContentid()
	{
		return contentid;
	}
	public void setContentid(String contentid)
	{
		this.contentid = contentid;
	}
	public String getOldprice() {
		return oldprice;
	}
	public void setOldprice(String oldprice) {
		this.oldprice = oldprice;
	}
	public String getFirsttype() {
		return firsttype;
	}
	public void setFirsttype(String firsttype) {
		this.firsttype = firsttype;
	}
	public String getContenttag() {
		return contenttag;
	}
	public void setContenttag(String contenttag) {
		this.contenttag = contenttag;
	}
	
	
	
	

}
