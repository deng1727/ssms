package com.aspire.dotcard.syncAndroid.ppms;

public class CmContentVO {
	private String id;//�����ݿ�õ��ġ�
	private String typename;
	private String catalogid;
	private String cateName;
	private String contentid;
	private String name;
	private String contentCode;
	private String keywords;
	private String servAttr;
	private String createDate;
	private String marketdate;
	private String icpservid;
	private String productID;
	private String icpcode;
	private String companyid;
	private String companyname;
	private String plupddate;
	private String lupddate;
	private String chargeTime;
	private String cityid;
	private String appCateID;
	private String appCateName;
	private String introduction;
	private String WWWPropaPicture1;
	private String WWWPropaPicture2;
	private String WWWPropaPicture3;
	private String provider;
	private String language;
	private String logo1;
	private String logo2;
	private String logo3;
	private String logo4;
	private String logo5;
	private String logo6;//add fanqh 201310
	private String onlinetype;
	private String version;
	private String picture1;
	private String picture2;
	private String picture3;
	private String picture4;
	private String ismmtoevent;
	private String copyrightFlag;
	//1��ʾmm��ͨӦ��,2��ʾwidgetӦ��,3��ʾZCOMӦ��,4��ʾFMMӦ�ã�5��ʾjilӦ�ã�6��ʾMM����Ӧ�ã�7��ʾ����Ӧ�ã�
	//8��ʾ��������Ӧ�ã�9��ʾ���MM��10��ʾOVIӦ�ã�11��ʾ�ײ�,12 MOTOӦ��,13��ʾ��������Ӧ��,14��ʾ���Ž���(������󸶷�)Ӧ��,
	//15��ʾ��ҳ��Ʒ�Ӧ��,16��ʾHTCӦ��
	private String subtype;	
	private String pvcid;
	private String handbook;
	private String handbookPicture;
	private String mapname;
	//2015-09-30 add ,��������������ַ����ͣ�0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
	private String channeldisptype;
	
	/*
	��Ӧ����ͼ����  ISCMPASSAPP	Varchar2(1)				ͳһ��֤��ǣ�
	1����ͳһ��֤SDK
	0����ͳһ��֤SDK
	logo7	VARCHAR2(256)				logo7��ַ,���120*120Ӧ�ô�ͼ��
	
	*/
	private String isCmpassApp;
	
	private String logo7;
	private String risktag;
	private String apptype;
	private String ctrldev;
	private String developer;  //��Ӧ��
	private String appid;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getRisktag() {
		return risktag;
	}
	public void setRisktag(String risktag) {
		this.risktag = risktag;
	}
	public String getApptype() {
		return apptype;
	}
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	public String getCtrldev() {
		return ctrldev;
	}
	public void setCtrldev(String ctrldev) {
		this.ctrldev = ctrldev;
	}
	public String getIsCmpassApp() {
		return isCmpassApp;
	}
	public void setIsCmpassApp(String isCmpassApp) {
		this.isCmpassApp = isCmpassApp;
	}
	public String getLogo7() {
		return logo7;
	}
	public void setLogo7(String logo7) {
		this.logo7 = logo7;
	}
	public String getChanneldisptype() {
		return channeldisptype;
	}
	public void setChanneldisptype(String channeldisptype) {
		this.channeldisptype = channeldisptype;
	}
	public String getMapname() {
		return mapname;
	}
	public void setMapname(String mapname) {
		this.mapname = mapname;
	}
	public String getHandbook() {
		return handbook;
	}
	public void setHandbook(String handbook) {
		this.handbook = handbook;
	}
	public String getHandbookPicture() {
		return handbookPicture;
	}
	public void setHandbookPicture(String handbookPicture) {
		this.handbookPicture = handbookPicture;
	}
	public String getPvcid() {
		return pvcid;
	}
	public void setPvcid(String pvcid) {
		this.pvcid = pvcid;
	}
	public String getIsmmtoevent() {
		return ismmtoevent;
	}
	public void setIsmmtoevent(String ismmtoevent) {
		this.ismmtoevent = ismmtoevent;
	}
	public String getCopyrightFlag() {
		return copyrightFlag;
	}
	public void setCopyrightFlag(String copyrightFlag) {
		this.copyrightFlag = copyrightFlag;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getCatalogid() {
		return catalogid;
	}
	public void setCatalogid(String catalogid) {
		this.catalogid = catalogid;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getServAttr() {
		return servAttr;
	}
	public void setServAttr(String servAttr) {
		this.servAttr = servAttr;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLupddate() {
		return lupddate;
	}
	public void setLupddate(String lupddate) {
		this.lupddate = lupddate;
	}
	public String getPlupddate() {
		return plupddate;
	}
	public void setPlupddate(String plupddate) {
		this.plupddate = plupddate;
	}
	public String getMarketdate() {
		return marketdate;
	}
	public void setMarketdate(String marketdate) {
		this.marketdate = marketdate;
	}
	public String getIcpcode() {
		return icpcode;
	}
	
	
	
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public void setIcpcode(String icpcode) {
		this.icpcode = icpcode;
	}
	public String getIcpservid() {
		return icpservid;
	}
	public void setIcpservid(String icpservid) {
		this.icpservid = icpservid;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getAppCateName() {
		return appCateName;
	}
	public void setAppCateName(String appCateName) {
		this.appCateName = appCateName;
	}
	public String getAppCateID() {
		return appCateID;
	}
	public void setAppCateID(String appCateID) {
		this.appCateID = appCateID;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getWWWPropaPicture1() {
		return WWWPropaPicture1;
	}
	public void setWWWPropaPicture1(String propaPicture1) {
		WWWPropaPicture1 = propaPicture1;
	}
	public String getWWWPropaPicture2() {
		return WWWPropaPicture2;
	}
	public void setWWWPropaPicture2(String propaPicture2) {
		WWWPropaPicture2 = propaPicture2;
	}
	public String getWWWPropaPicture3() {
		return WWWPropaPicture3;
	}
	public void setWWWPropaPicture3(String propaPicture3) {
		WWWPropaPicture3 = propaPicture3;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLogo1() {
		return logo1;
	}
	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}
	public String getLogo2() {
		return logo2;
	}
	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}
	public String getLogo3() {
		return logo3;
	}
	public void setLogo3(String logo3) {
		this.logo3 = logo3;
	}
	public String getLogo4() {
		return logo4;
	}
	public void setLogo4(String logo4) {
		this.logo4 = logo4;
	}
	public String getLogo5() {
		return logo5;
	}
	public void setLogo5(String logo5) {
		this.logo5 = logo5;
	}
	//add fanqh 201310
    public String getLogo6()
    {
        return logo6;
    }
    //add fanqh 201310
    public void setLogo6(String logo6)
    {
        this.logo6 = logo6;
    }
	public String getOnlinetype() {
		return onlinetype;
	}
	public void setOnlinetype(String onlinetype) {
		this.onlinetype = onlinetype;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPicture1() {
		return picture1;
	}
	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}
	public String getPicture2() {
		return picture2;
	}
	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}
	public String getPicture3() {
		return picture3;
	}
	public void setPicture3(String picture3) {
		this.picture3 = picture3;
	}
	public String getPicture4() {
		return picture4;
	}
	public void setPicture4(String picture4) {
		this.picture4 = picture4;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	
	
	
	
}
