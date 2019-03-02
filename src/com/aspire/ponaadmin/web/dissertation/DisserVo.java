package com.aspire.ponaadmin.web.dissertation;

import java.io.Serializable;

import com.aspire.ponaadmin.web.util.StringTool;

/**
 * ������Ϣ��
 * @author yushiming
 *          2011-2-22
 */
public class DisserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2862270867609515133L;
	/**������*/
	private String dissId;
	/**logoͼƬUrl*/
	private String logoURL;
	/**����Url*/
	private String dissURL;
	/**�������*/
	private String descr;
	/**�����ǩ*/
	private String keywords;
	/**������Ч��ʼ����*/
	private String startDate;
	/**������Ч��������*/
	private String endDate;
	/**�����������ID*/
	private String categoryId;
	/**���������������*/
	private String categoryName;
	/**����״̬*/
	private Integer status;
	/**��������*/
	private String dissName;
	/**��������*/
	private String dissType;
	/**����������Ż�*/
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
					sb.append("��Ϸ");
				}else if(types[i].equals("Soft")){
					sb.append("���");
				}else{
					sb.append("����");
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
			sta="����";
			break;
		case 0:
			sta="δ��Ч";
			break;
		case 1:
			sta="��Ч";
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
