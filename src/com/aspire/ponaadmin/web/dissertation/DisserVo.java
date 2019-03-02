package com.aspire.ponaadmin.web.dissertation;

import java.io.Serializable;

import com.aspire.ponaadmin.web.util.StringTool;

/**
 * 主题信息类
 * @author yushiming
 *          2011-2-22
 */
public class DisserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2862270867609515133L;
	/**主题编号*/
	private String dissId;
	/**logo图片Url*/
	private String logoURL;
	/**主题Url*/
	private String dissURL;
	/**主题介绍*/
	private String descr;
	/**主题标签*/
	private String keywords;
	/**主题有效开始日期*/
	private String startDate;
	/**主题有效结束日期*/
	private String endDate;
	/**主题关联货架ID*/
	private String categoryId;
	/**主题关联货架名称*/
	private String categoryName;
	/**主题状态*/
	private Integer status;
	/**主题名称*/
	private String dissName;
	/**主题类型*/
	private String dissType;
	/**主题关联的门户*/
	private String relation;
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDissId() {
		return dissId;
	}
	public void setDissId(String dissId) {
		this.dissId = dissId;
	}
	public String getDissName() {
		return dissName;
	}
	public void setDissName(String dissName) {
		this.dissName = dissName;
	}
	public String getDissType() {
		return dissType;
	}
	public void setDissType(String dissType) {
		this.dissType = dissType;
	}
	public String getDissURL() {
		return dissURL;
	}
	public void setDissURL(String dissURL) {
		this.dissURL = dissURL;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType(){
		String type=this.dissType;
		if(type!=null && !type.trim().equals("")){
			String[] types=type.split(":");
				StringBuffer sb=new StringBuffer();
			for(int i=0;i<types.length;i++){
				if(types[i].equals("Game")){
					sb.append("游戏");
				}else if(types[i].equals("Soft")){
					sb.append("软件");
				}else{
					sb.append("主题");
				}
				if(i!=types.length-1 && type.length()!=1){
					sb.append(";");
				}
			}
			type=sb.toString();
		}
		return type;
	}
	public String showStatus(){
		String sta=null;
		switch (status.intValue()) {
		case -1:
			sta="过期";
			break;
		case 0:
			sta="未生效";
			break;
		case 1:
			sta="有效";
			break;
		default:
			break;
		}
		return sta;
	}
	public  String getShowRelation(){
		if (relation.equals("M")) {
			return "MO";
		} else if (relation.equals("W")) {
			return "WWW";
		} else if (relation.equals("P1")) {
			return "WAP1.0";
		} else if (relation.equals("P2")) {
			return "WAP2.0";
		}
		else 
		{
			return "WAPTouch";
		}
	}
}
