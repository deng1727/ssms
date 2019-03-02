package com.aspire.ponaadmin.webpps.mycontent.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO;

public class MyContentVO {

	private String id; //id
	private String contentid;	//应用Id
	private String name;		//应用名称
	private String catename;	//应用类型
	private String marketdate;	//上架时间
	private String plupddate;	//最后更新时间
	private String introduction;//应用简介
	private String keywords;//内容标签

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

	public String getCatename() {
		return catename;
	}

	public void setCatename(String catename) {
		this.catename = catename;
	}

	public String getMarketdate() {
		return marketdate;
	}

	public void setMarketdate(String marketdate) {
		this.marketdate = marketdate;
	}

	public String getPlupddate() {
		return plupddate;
	}

	public void setPlupddate(String plupddate) {
		this.plupddate = plupddate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
