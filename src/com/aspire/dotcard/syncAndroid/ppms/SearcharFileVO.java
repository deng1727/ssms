package com.aspire.dotcard.syncAndroid.ppms;

public class SearcharFileVO {
//	ʵʱ���²�������	Option	1	��	������Ʒ��A
//	������Ʒ��Ϣ��U
//	ɾ����Ʒ��D
//	һ�����ݷ���	FatherCategory	2	��	��Ϸ��appGame      
//	�����appSoftWare      
//	���⣺appTheme
//	������audio         
//	�鼮��book             
//	���壺colorring
//	������comic         
//	���֣�music            
//	���ţ�news
//	�Ķ���read          
//	��Ƶ��video 
//	����ʹ�÷�Χ	Range	3	��	���ţ�JT
//	�㶫��GD
//	��ƷID	GoodsID	4	��	��ƷID
//	��ƷID	ProdID	5	��	��ƷID
//	����	Name	6	��	��Ʒ������
//	�����ȶ�	NameBoost	7	�ǣ�1��	�����ȶȣ�Ĭ��Ϊ1
//	�ݳ���	Singer	8	��	���ֺͲ����ע�������ֶΣ������������ݸ��ֶ�Ϊ��
//	�ݳ����ȶ�	SingerBoost	9	�ǣ�1��	�ݳ����ȶȣ�Ĭ��Ϊ1
//	��Ӧ��	SPName	10	��	�ṩ�ĳ���
//	�ն�����	FullDevice	11	��	�ն�����
//	���ݷ���	Type	12	��	�ֻ��ն�ҵ��ϵͳ���ݣ�O
//	PC�ն�ҵ��ϵͳ���ݣ�P
//	WWWҵ��ϵͳ���ݣ�W
//	WAPҵ��ϵͳ���ݣ�A
//	�۸�	Price	13	�ǣ�0��	��Ʒ�ļ۸�
//	����	Star	14	�ǣ�0��	��Ʒ����
//	ʱ��	Date	15	��	��Ʒ�ϼ�ʱ�䣨yyyy-MM-dd HH:mm:ss��
//	��ǩ	Label	16	��	 
//	���	Brief	17	��	 
//	����	Hot	18	��	 
//	�������� 	ChildCategory	19	��	����������
//	�Ʒ�ʱ��	ChargeTime	20	��	 
//	ʡ	Province	21	��	 
//	��	City	22	��	 
//	����	OtherNet	23	��	�Ƿ������ɼ���0���������ɼ� 1�������ɼ�
//	��������	SubType	24	��	'1��ʾmm��ͨӦ��,2��ʾwidgetӦ��,3��ʾZCOMӦ��,4��ʾFMMӦ�ã�5��ʾjilӦ�ã�6��ʾMM����Ӧ�ã�7��ʾ����Ӧ�ã�8��ʾ��������Ӧ�ã�9��ʾ���MM��10��ʾOVIӦ�ã�11��ʾ�ײ�;12 MOTOӦ�ã�99 ��Ϸ����
//	ƽ̨����	Platform	22	��	ȫ��֧�ֻ򲻽��й��ˣ���all
//	�г��ƹ��ʶ	marketingCha	23	��	1��ʾ����΢�����´����ƹ�Ӧ��
//	ͼ���ַ1	imgurl1	24	��	�������Ϸ��������д50*50��Ʒͼ��ĵ�ַ	���棬����
//	ͼ���ַ2	imgurl2	25	��	�������Ϸ������65*65��Ʒͼ��ĵ�ַ	���棬����

	String option;
	String fatherCategory;
	String range;
	String goodsID;
	String prodID;
	String name;
	String nameBoost;
	String singer;
	String singerBoost;
	String spName;
	String fullDevice;
	String type;
	String price;
	String star;
	String date;
	String label;
	String brief;
	String hot;
	String childCategory;
	String chargeTime;
	String province;
	String city;
	String otherNet;
	String subType;
	String platform;
	String marketingCha;
	String imgurl1;
	String imgurl2;
	String copyRightFlag;
	
	public String getCopyRightFlag() {
		return copyRightFlag;
	}
	public void setCopyRightFlag(String copyRightFlag) {
		this.copyRightFlag = copyRightFlag;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getFatherCategory() {
		return fatherCategory;
	}
	public void setFatherCategory(String fatherCategory) {
		this.fatherCategory = fatherCategory;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getProdID() {
		return prodID;
	}
	public void setProdID(String prodID) {
		this.prodID = prodID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameBoost() {
		return nameBoost;
	}
	public void setNameBoost(String nameBoost) {
		this.nameBoost = nameBoost;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSingerBoost() {
		return singerBoost;
	}
	public void setSingerBoost(String singerBoost) {
		this.singerBoost = singerBoost;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getFullDevice() {
		return fullDevice;
	}
	public void setFullDevice(String fullDevice) {
		this.fullDevice = fullDevice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}
	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOtherNet() {
		return otherNet;
	}
	public void setOtherNet(String otherNet) {
		this.otherNet = otherNet;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getMarketingCha() {
		return marketingCha;
	}
	public void setMarketingCha(String marketingCha) {
		this.marketingCha = marketingCha;
	}
	public String getImgurl1() {
		return imgurl1;
	}
	public void setImgurl1(String imgurl1) {
		this.imgurl1 = imgurl1;
	}
	public String getImgurl2() {
		return imgurl2;
	}
	public void setImgurl2(String imgurl2) {
		this.imgurl2 = imgurl2;
	}
	public String toString(){
		String  compart = getCompart();
		StringBuilder sb = new StringBuilder();
		sb.append(checkFileldNull(option)).append(compart);
		sb.append(checkFileldNull(fatherCategory)).append(getCompart());
		sb.append(checkFileldNull(range)).append(compart);
		sb.append(checkFileldNull(goodsID)).append(compart);
		sb.append(checkFileldNull(prodID)).append(compart);
		
		sb.append(checkFileldNull(name)).append(compart);
		sb.append(checkFileldNull(nameBoost)).append(getCompart());
		sb.append(checkFileldNull(singer)).append(compart);
		sb.append(checkFileldNull(singerBoost)).append(compart);
		sb.append(checkFileldNull(spName)).append(compart);
		
		sb.append(checkFileldNull(fullDevice)).append(compart);
		sb.append(checkFileldNull(type)).append(getCompart());
		sb.append(checkFileldNull(price)).append(compart);
		sb.append(checkFileldNull(star)).append(compart);
		sb.append(checkFileldNull(date)).append(compart);
		
		sb.append(checkFileldNull(label)).append(compart);
		sb.append(checkFileldNull(brief)).append(getCompart());
		sb.append(checkFileldNull(hot)).append(compart);
		sb.append(checkFileldNull(childCategory)).append(compart);
		sb.append(checkFileldNull(chargeTime)).append(compart);
		
		sb.append(checkFileldNull(province)).append(compart);
		sb.append(checkFileldNull(city)).append(getCompart());
		sb.append(checkFileldNull(otherNet)).append(compart);
		sb.append(checkFileldNull(subType)).append(compart);
		sb.append(checkFileldNull(platform)).append(compart);
		sb.append(checkFileldNull(marketingCha)).append(compart);
		
		sb.append(checkFileldNull(imgurl1)).append(compart);
		sb.append(checkFileldNull(imgurl2)).append(compart);
		sb.append(checkFileldNull(copyRightFlag));
		return sb.toString();
		
	}
	
//	   /**
//     * ������Ҫ��д��txt�С����������¸Ķ�
//     * 
//     * @return
//     */
//    private String objectToString(Object[] obj)
//    {
//        StringBuffer sb = new StringBuffer();
//
//        for (int j = 0; j < obj.length; j++)
//        {
//            sb.append(checkFileldNull(obj[j]));
//
//            // ����ָ���
//            if (j == obj.length - 1)
//            {
//            }
//            else
//            {
//                sb.append(getCompart());
//            }
//        }
//        return sb.toString();
//    }
    /**
     * �жϸ��ַ��Ƿ�Ϊnull��
     * @param temp ��������ֵ
     * @return
     */
    private Object checkFileldNull(Object temp)
    {
        if(temp == null)
        {
            return "";
        }
        else
        {
            return temp;
        }
    }
    
    /**
     * ����ָ���ָ���0X01
     * @return
     */
    private String getCompart()
    {
        int  a = 0x01;
        char r = (char)a;
        return String.valueOf(r);
    }
	

}
