package com.aspire.dotcard.syncAndroid.ppms;

public class SearcharFileVO {
//	实时更新操作类型	Option	1	是	新增商品：A
//	更新商品信息：U
//	删除商品：D
//	一级数据分类	FatherCategory	2	是	游戏：appGame      
//	软件：appSoftWare      
//	主题：appTheme
//	铃声：audio         
//	书籍：book             
//	彩铃：colorring
//	动漫：comic         
//	音乐：music            
//	新闻：news
//	阅读：read          
//	视频：video 
//	数据使用范围	Range	3	否	集团：JT
//	广东：GD
//	商品ID	GoodsID	4	是	商品ID
//	产品ID	ProdID	5	是	产品ID
//	名称	Name	6	否	商品的名称
//	名称热度	NameBoost	7	是（1）	名称热度，默认为1
//	演唱者	Singer	8	否	音乐和彩铃关注这两个字段，其他分类数据该字段为空
//	演唱者热度	SingerBoost	9	是（1）	演唱者热度，默认为1
//	供应商	SPName	10	否	提供的厂商
//	终端类型	FullDevice	11	否	终端类型
//	数据分类	Type	12	是	手机终端业务系统数据：O
//	PC终端业务系统数据：P
//	WWW业务系统数据：W
//	WAP业务系统数据：A
//	价格	Price	13	是（0）	商品的价格
//	评分	Star	14	是（0）	商品评分
//	时间	Date	15	否	商品上架时间（yyyy-MM-dd HH:mm:ss）
//	标签	Label	16	否	 
//	简介	Brief	17	否	 
//	人气	Hot	18	否	 
//	二级分类 	ChildCategory	19	否	二级分类编号
//	计费时机	ChargeTime	20	否	 
//	省	Province	21	否	 
//	市	City	22	否	 
//	异网	OtherNet	23	否	是否异网可见，0、异网不可见 1、异网可见
//	数据类型	SubType	24	否	'1表示mm普通应用,2表示widget应用,3表示ZCOM应用,4表示FMM应用，5表示jil应用，6表示MM大赛应用，7表示孵化应用，8表示孵化厂商应用，9表示香港MM，10表示OVI应用，11表示套餐;12 MOTO应用，99 游戏基地
//	平台类型	Platform	22	否	全部支持或不进行过滤，填all
//	市场推广标识	marketingCha	23	否	1表示新浪微博创新大赛推广应用
//	图标地址1	imgurl1	24	是	软件、游戏、主题填写50*50商品图标的地址	保存，索引
//	图标地址2	imgurl2	25	是	软件、游戏、主题65*65商品图标的地址	保存，索引

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
//     * 因需求要求写入txt中。这里做如下改动
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
//            // 加入分隔符
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
     * 判断该字符是否为null。
     * @param temp 传进来的值
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
     * 返回指定分隔符0X01
     * @return
     */
    private String getCompart()
    {
        int  a = 0x01;
        char r = (char)a;
        return String.valueOf(r);
    }
	

}
