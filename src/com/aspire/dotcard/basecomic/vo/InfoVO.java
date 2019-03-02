package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Const;
import com.aspire.dotcard.basecomic.common.Validateable;


public class InfoVO extends Validateable implements VO{
	
	private String id;//���ݱ�ʶ
	private String name;//��������
	private String description;//��������
	private String provider;//�����ṩ��
	private String providerType;//�����ṩ������
	private String authodid;//���߱�ʶ
	private String type;//��������
	private String keywords;//���ݹؼ���-
	private String expireTime;//���ݳ���ʱ��-
	private String location; //���ݹ�����
	private String first;//���ݵ�����ĸ
	private String url1;//Ԥ��ͼ1-
	private String url2;//Ԥ��ͼ2- 
	private String url3;//Ԥ��ͼ3-
	private String url4;//Ԥ��ͼ4-
	private String infoContent;//��Ѷ����
	private String infoPic;//��Ѷ���׵�ͼƬ
	private String infoSource;//��Ѷ��Դ-
	private String createTime;//����ʱ��	
	private String classify;//���ݷ���
	
	private String baseType;//��¼ԭʼtype������
//	101:Theme������
//	116-104:MovieSeries������Ƭ
//	115:Information����Ѷ
//	220-221:ComicSeries��������
	
	private String portal;//�Ż�����Ѷ�͸������Ż������԰ɡ���3��ʾ��1:�նˡ�2��WAP
	
	private final String domain = "http://rs.base.mmarket.com/dm/logo/portalone";
	
	public InfoVO(String[] field){
		if(field!=null&&field.length>=20){
			id = field[0];//���ݱ�ʶ
			name = field[1];//��������
			description = field[2];//��������
			provider = field[3];//�����ṩ��
			providerType= field[4];//�����ṩ������
			authodid= field[5];//���߱�ʶ
			
//			101:Theme������
//			116 ��������
//			115:Information����Ѷ
//			220 ��������

//			if("".equals(field[6])){
//				this.setType("115");
//			}else{
//				this.setType(field[6]);
//			}
			
			this.setType("115");
			this.setBaseType(field[6]);
			
			keywords= field[7];//���ݹؼ���
			expireTime= field[8];//���ݳ���ʱ��
			location= field[9]; //���ݹ�����
			first= field[10];//���ݵ�����ĸ
			
			if(field[11].trim().length()!=0){
				field[11]=domain+field[11];
			}
			
			url1= field[11];//Ԥ��ͼ1
			
			if(field[12].trim().length()!=0){
				field[12]=domain+field[12];
			}
			
			url2= field[12];//Ԥ��ͼ2  
			
			if(field[13].trim().length()!=0){
				field[13]=domain+field[13];
			}
			url3= field[13];//Ԥ��ͼ3
			
			if(field[14].trim().length()!=0){
				field[14]=domain+field[14];
			}
			
			url4= field[14];//Ԥ��ͼ4
			infoContent= removeSomeString(field[15]);//��Ѷ����
			infoPic= removeSomeString(field[16]);//��Ѷ���׵�ͼƬ
			infoSource= removeSomeString(field[17]);//��Ѷ��Դ
			createTime= field[18];//����ʱ��	
			classify= field[19];//���ݷ���
			
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
			this.addFieldError("id����Ϊ��");
		}
		if(isEmpty(name)){
			this.addFieldError("name����Ϊ��");
		}
		if(isEmpty(description)){
			this.addFieldError("description����Ϊ��");
		}
		if(isEmpty(provider)){
			this.addFieldError("provider����Ϊ��");
		}
		if(isEmpty(providerType)){
			this.addFieldError("providerType����Ϊ��");
		}
		if(isEmpty(authodid)){
			this.addFieldError("authodid����Ϊ��");
		}
		if(isEmpty(type)){
			this.addFieldError("type����Ϊ��");
		}
		//2015-01-26,�������ֶθ�Ϊ��ѡ
		/*if(isEmpty(location)){
			this.addFieldError("location����Ϊ��");
		}
		if(isEmpty(first)){
			this.addFieldError("first����Ϊ��");
		}*/
		if(isEmpty(infoContent)){
			this.addFieldError("infoContent����Ϊ��");
		}
		//remove by aiyan 2012-09-26
//		if(isEmpty(infoPic)){
//			this.addFieldError("infoPic����Ϊ��");
//		}
		if(isEmpty(createTime)){
			this.addFieldError("createTime����Ϊ��");
		}
		if(isEmpty(classify)){
			this.addFieldError("classify����Ϊ��");
		}
		
		
		
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id;
	}

	

}
