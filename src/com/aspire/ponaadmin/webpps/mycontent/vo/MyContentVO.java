package com.aspire.ponaadmin.webpps.mycontent.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO;

public class MyContentVO {

	private String id; //id
	private String contentid;	//Ӧ��Id
	private String name;		//Ӧ������
	private String catename;	//Ӧ������
	private String marketdate;	//�ϼ�ʱ��
	private String plupddate;	//������ʱ��
	private String introduction;//Ӧ�ü��
	private String keywords;//���ݱ�ǩ

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
