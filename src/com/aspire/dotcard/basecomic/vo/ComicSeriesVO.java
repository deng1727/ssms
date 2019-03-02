package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class ComicSeriesVO extends Validateable implements VO{
	private String id;//内容标识
	private String name;//内容名称
	private String description;//内容描述
	private String provider;//内容提供方
	private String providerType;//内容提供者类型
	private String authodid;//作者标识
	private String type;//内容类型
	private String keywords;//内容关键字
	private String expireTime;//内容超期时间
	private String fee;//资费
	private String location; //内容归属地
	private String first;//内容的首字母
	private String url1;//预览图1
	private String url2;//预览图2 
	private String url3;//预览图3
	private String url4;//预览图4
	private String feeCode;//计费代码
	private String detailUrl1;//内容详情页URL
	private String detailUrl2;//内容详情页URL 
	private String detailUrl3;//内容详情页URL 
	private String bookNum;//话数
	private String classify;//漫画书类型
	private String bookType;//漫画书分类
	private String bookStyle;//漫画书风格
	private String bookColor;//漫画书颜色
	private String area;//出品地区
	private String language;//语种
	private String year;//发行年份
	private String status;//连载状态
	private String chapterType;//篇章类型
	private String userType;//用户类型
	private String portal = "0";//门户 //漫画的漫画属性是根据“呈现表”算出来的。见new BaseComicDBOpration().buildPortal();
	private String businessid;//业务代码
	private String createTime;//创建时间
	private String baseType;//记录原始type的数据
//	101:Theme，主题
//	116-104:MovieSeries，动画片
//	115:Information，资讯
//	220-221:ComicSeries，漫画书
	
	private String ebookUrl;//电子书下载URL
	
	

	
	private final String domain = "http://rs.base.mmarket.com/dm/logo/portalone";

	public ComicSeriesVO(String[] field){
		if(field.length>=34){
			this.setId(field[0]);
			this.setName(field[1]);
			this.setDescription(field[2]);
			this.setProvider(field[3]);
			this.setProviderType(field[4]);
			this.setAuthodid(field[5]);
//			101:Theme，主题
//			116 动画单集
//			115:Information，资讯
//			220 漫画单话

//			if("".equals(field[6])){
//				this.setType("220");
//			}else{
//				this.setType(field[6]);
//			}
			
			this.setType("220");
			
			this.setBaseType(field[6]);
			
			
			this.setKeywords(field[7]);
			this.setExpireTime(field[8]);
			this.setFee(field[9]);
			this.setLocation(field[10]);
			this.setFirst(field[11]);
			
			if(field[12].trim().length()!=0){
				field[12]=domain+field[12];
			}
			
			this.setUrl1(field[12]);
			
			if(field[13].trim().length()!=0){
				field[13]=domain+field[13];
			}
			
			this.setUrl2(field[13]);
			
			if(field[14].trim().length()!=0){
				field[14]=domain+field[14];
			}
			
			this.setUrl3(field[14]);
			
			if(field[15].trim().length()!=0){
				field[15]=domain+field[15];
			}
			
			this.setUrl4(field[15]);
			this.setFeeCode(field[16]);
			
			if(field[17].trim().length()!=0){
				this.setDetailUrl1(domain+field[17]);
			}
			
			this.setDetailUrl2(field[18]);
			this.setDetailUrl3(field[19]);
			this.setBookNum(field[20]);
			this.setClassify(field[21]);
			this.setBookType(field[22]);
			this.setBookStyle(field[23]);
			this.setBookColor(field[24]);
			this.setArea(field[25]);
			this.setLanguage(field[26]);
			this.setYear(field[27]);
			this.setStatus(field[28]);
			this.setChapterType(field[29]);
			this.setUserType(field[30]);
			this.setPortal(field[31]);
			this.setBusinessid(field[32]);
			this.setCreateTime(field[33]);
			this.setEbookUrl(field[34]);
		}
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}


	public String getProviderType() {
		return providerType;
	}


	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}


	public String getAuthodid() {
		return authodid;
	}


	public void setAuthodid(String authodid) {
		this.authodid = authodid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public String getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}


	public String getFee() {
		return fee;
	}


	public void setFee(String fee) {
		this.fee = fee;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getFirst() {
		return first;
	}


	public void setFirst(String first) {
		this.first = first;
	}


	public String getUrl1() {
		return url1;
	}


	public void setUrl1(String url1) {
		this.url1 = url1;
	}


	public String getUrl2() {
		return url2;
	}


	public void setUrl2(String url2) {
		this.url2 = url2;
	}


	public String getUrl3() {
		return url3;
	}


	public void setUrl3(String url3) {
		this.url3 = url3;
	}


	public String getUrl4() {
		return url4;
	}


	public void setUrl4(String url4) {
		this.url4 = url4;
	}

	
	

	public String getFeeCode() {
		return feeCode;
	}


	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}


	public String getDetailUrl1() {
		return detailUrl1;
	}


	public void setDetailUrl1(String detailUrl1) {
		this.detailUrl1 = detailUrl1;
	}


	public String getDetailUrl2() {
		return detailUrl2;
	}


	public void setDetailUrl2(String detailUrl2) {
		this.detailUrl2 = detailUrl2;
	}


	public String getDetailUrl3() {
		return detailUrl3;
	}


	public void setDetailUrl3(String detailUrl3) {
		this.detailUrl3 = detailUrl3;
	}


	public String getBookNum() {
		return bookNum;
	}


	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}


	public String getClassify() {
		return classify;
	}


	public void setClassify(String classify) {
		this.classify = classify;
	}


	public String getBookType() {
		return bookType;
	}


	public void setBookType(String bookType) {
		this.bookType = bookType;
	}


	public String getBookStyle() {
		return bookStyle;
	}


	public void setBookStyle(String bookStyle) {
		this.bookStyle = bookStyle;
	}


	public String getBookColor() {
		return bookColor;
	}


	public void setBookColor(String bookColor) {
		this.bookColor = bookColor;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getChapterType() {
		return chapterType;
	}


	public void setChapterType(String chapterType) {
		this.chapterType = chapterType;
	}
	
	


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getPortal() {
		return portal;
	}


	public void setPortal(String portal) {
		this.portal = portal;
	}


	public String getBusinessid() {
		return businessid;
	}


	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBaseType() {
		return baseType;
	}


	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}


	public void validate() {
		// TODO Auto-generated method stub
//		{"id","name","description","provider","providerType","authodid","type","location","first","detailUrl1","bookNum","classify"
//			,"bookType","bookStyle","bookColor","area","language","year","status","chapterType","userType","portal","businessid","createTime"}; 

		if(isEmpty(id)){
			this.addFieldError("id不能为空");
		}
		if(isEmpty(name)){
			this.addFieldError("name不能为空");
		}
		if(isEmpty(description)){
			this.addFieldError("description不能为空");
		}
		if(isEmpty(provider)){
			this.addFieldError("provider不能为空");
		}
		if(isEmpty(providerType)){
			this.addFieldError("providerType不能为空");
		}
		if(isEmpty(authodid)){
			this.addFieldError("authodid不能为空");
		}
		if(isEmpty(type)){
			this.addFieldError("type不能为空");
		}
		//2015-01-26,这两个字段改为可选
		/*if(isEmpty(location)){
			this.addFieldError("location不能为空");
		}
		if(isEmpty(first)){
			this.addFieldError("first不能为空");
		}*/
		if(isEmpty(bookNum)){
			this.addFieldError("bookNum不能为空");
		}

		if(isEmpty(bookType)){
			this.addFieldError("bookType不能为空");
		}
		if(isEmpty(bookStyle)){
			this.addFieldError("bookStyle不能为空");
		}
		if(isEmpty(bookColor)){
			this.addFieldError("bookColor不能为空");
		}
		if(isEmpty(status)){
			this.addFieldError("status不能为空");
		}
		//2016-02-29 by shiyangwang 该字段改为可选
		/*if(isEmpty(chapterType)){
			this.addFieldError("chapterType不能为空");
		}*/
		if(isEmpty(portal)){
			this.addFieldError("portal不能为空");
		}
		if(isEmpty(createTime)){
			this.addFieldError("createTime不能为空");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getEbookUrl()
	{
		return ebookUrl;
	}

	public void setEbookUrl(String ebookUrl)
	{
		this.ebookUrl = ebookUrl;
	}
}
