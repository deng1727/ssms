package com.aspire.ponaadmin.web.datasync.implement.game;
/**
 * 保存游戏的业务信息。
 * @author zhangwei
 *
 */
public class GameServiceVO
{
	/**
	 * 内容id
	 */
	private String contentid;
	/**
	 * 产品归属的CP
	 */
	private String icpCode;
	/**
	 * CP名称
	 */
	private String spName;
	/**
	 * 产品的业务代码
	 */
	private String icpServid;
	/**
	 * 产品名称。
	 */
	private String servName;
	/**
	 * 产品简介
	 */
	private String servDesc;
	/**
	 * 资费
	 */
	private int mobilePrice;
	/**
	 * 计费类型
	 */
	private String chargeType;
	/**
	 * 资费表述
	 */
	private String chargeDesc;
	/**
	 * 业务类型。
	 */
	private int servType;
	/**
	 * 业务标识
	 */
	private int servFlag;
	/**
	 * 业务推广方式
	 */
	private int ptypeId;
	
	//add by aiyan 2012-09-23 新游戏的两个字段
	private String oldprice;//原价
	private String firsttype;//首发类型
	private String contenttag;//基地给的原始内容ID 
	/**
	 * 最后更新时间
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
