package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.Validateable;


public class InfoVO extends Validateable implements VO{
	
	private String id;//内容标识
	private String name;//内容名称
	private String description;//内容描述
	private String provider;//内容提供方
	private String providerType;//内容提供者类型
	private String authodid;//作者标识
	private String type;//内容类型
	private String keywords;//内容关键字-
	private String expireTime;//内容超期时间-
	private String location; //内容归属地
	private String first;//内容的首字母
	private String url1;//预览图1-
	private String url2;//预览图2- 
	private String url3;//预览图3-
	private String url4;//预览图4-
	private String infoContent;//资讯内容
	private String infoPic;//资讯配套的图片
	private String infoSource;//资讯来源-
	private String createTime;//创建时间	
	private String classify;//内容分类
	
	private String baseType;//记录原始type的数据
//	101:Theme，主题
//	116-104:MovieSeries，动画片
//	115:Information，资讯
//	220-221:ComicSeries，漫画书
	
	private String portal;//门户，资讯就搞所有门户都可以吧。用3表示。1:终端、2：WAP
	
	private final String domain = "http://rs.base.mmarket.com/dm/logo/portalone";
	
	public InfoVO(String[] field){
		if(field!=null&&field.length>=20){
			id = field[0];//内容标识
			name = field[1];//内容名称
			description = field[2];//内容描述
			provider = field[3];//内容提供方
			providerType= field[4];//内容提供者类型
			authodid= field[5];//作者标识
			
//			101:Theme，主题
//			116 动画单集
//			115:Information，资讯
//			220 漫画单话

//			if("".equals(field[6])){
//				this.setType("115");
//			}else{
//				this.setType(field[6]);
//			}
			
			this.setType("115");
			this.setBaseType(field[6]);
			
			keywords= field[7];//内容关键字
			expireTime= field[8];//内容超期时间
			location= field[9]; //内容归属地
			first= field[10];//内容的首字母
			
			if(field[11].trim().length()!=0){
				field[11]=domain+field[11];
			}
			
			url1= field[11];//预览图1
			
			if(field[12].trim().length()!=0){
				field[12]=domain+field[12];
			}
			
			url2= field[12];//预览图2  
			
			if(field[13].trim().length()!=0){
				field[13]=domain+field[13];
			}
			url3= field[13];//预览图3
			
			if(field[14].trim().length()!=0){
				field[14]=domain+field[14];
			}
			
			url4= field[14];//预览图4
			infoContent= removeSomeString(field[15]);//资讯内容
			infoPic= removeSomeString(field[16]);//资讯配套的图片
			infoSource= removeSomeString(field[17]);//资讯来源
			createTime= field[18];//创建时间	
			classify= field[19];//内容分类
			
			portal=Const.PORTAL_ALL;
		}
	}
	//<values><Tvalue> ,</Tvalue></values> ,//<values><Tvalue/></values>
	public String removeSomeString(String str){
		str = str.replaceAll("<values><Tvalue>", "");
		str = str.replaceAll("</Tvalue></values>", "");
		str = str.replaceAll("<values><Tvalue/></values>", "");
		return str;
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

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoPic() {
		return infoPic;
	}

	public void setInfoPic(String infoPic) {
		this.infoPic = infoPic;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	public String getPortal() {
		return portal;
	}
	
	public String getBaseType() {
		return baseType;
	}
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	
	
	
	public void validate() {
		// TODO Auto-generated method stub
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
		if(isEmpty(infoContent)){
			this.addFieldError("infoContent不能为空");
		}
		//remove by aiyan 2012-09-26
//		if(isEmpty(infoPic)){
//			this.addFieldError("infoPic不能为空");
//		}
		if(isEmpty(createTime)){
			this.addFieldError("createTime不能为空");
		}
		if(isEmpty(classify)){
			this.addFieldError("classify不能为空");
		}
		
		
		
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id;
	}

	

}
