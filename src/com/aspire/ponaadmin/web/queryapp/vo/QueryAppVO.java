package com.aspire.ponaadmin.web.queryapp.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.aspire.ponaadmin.common.page.PageVOInterface;


public class QueryAppVO  implements PageVOInterface {
	
	private String contentid;	//Ӧ��Id
	private String name;		//Ӧ������
	private String icpcode;		//AP ID
	private String spname;		//AP����
	private String developer;	//������
	private String catename;	//Ӧ������
	private String marketdate;	//�ϼ�ʱ��
	private String plupddate;	//������ʱ��
	private String syntype;		//ͬ�����ͣ�Ĭ�� ȫ����ö�٣�1,���ߣ�2�����¡�
	private String id;			//����ID
	
	private String introduction;//Ӧ�ü��
	private String mobileprice;//�۸�
	private String servattr;//����Χ,'ȫ��ҵ��G;ʡ��ҵ��L';
	private String orderTimes;//��ʷ������
	private String keywords;//���ݱ�ǩ

	
	public Object createObject() {

		return new QueryAppVO();
	}

	public void CopyValFromResultSet(Object obj, ResultSet rs)
			throws SQLException {
		QueryAppVO vo = (QueryAppVO) obj;
		vo.setIntroduction(rs.getString("introduction"));
		
		vo.setServattr(rs.getString("servattr"));
		vo.setOrderTimes(rs.getString("orderTimes"));
		vo.setKeywords(rs.getString("keywords"));
		vo.setContentid(rs.getString("contentid"));
		vo.setName(rs.getString("name"));
		vo.setIcpcode(rs.getString("icpcode"));
		vo.setSpname(rs.getString("spname"));
		vo.setDeveloper(rs.getString("developer"));
		vo.setCatename(rs.getString("catename"));
		vo.setMarketdate(rs.getString("marketdate"));
		vo.setPlupddate(rs.getString("plupddate"));
		vo.setId(rs.getString("id"));
		
		if(null==vo.getOrderTimes()||"".equals(vo.getOrderTimes()))
		{
			vo.setOrderTimes("0");
		}
		
		try {
			double mobileprice=Double.parseDouble(rs.getString("mobileprice"))/1000;
			vo.setMobileprice(getPriceFormat(mobileprice));
		} catch (Exception e) {
			vo.setMobileprice("0.00");
		}
		try {
			vo.setSyntype(rs.getString("syntype"));
		} catch (Exception e) {
		}
		
		try {
			String keywordsStr=rs.getString("keywords");
			keywordsStr=keywordsStr.replaceAll("\\{", "").replaceAll("}", "").replaceAll(";", "\n");
			vo.setKeywords(keywordsStr);
		} catch (Exception e) {
			
		}
		
	}
	
	/**
     * ��ʽ�����Ϊ��λС��
     * 
     * @param mPrice
     * @return
     */
    private  String getPriceFormat(double mPrice)
    {

        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(mPrice);
    }
    
	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
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

	public String getIcpcode() {
		return icpcode;
	}

	public void setIcpcode(String icpcode) {
		this.icpcode = icpcode;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
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

	public String getSyntype() {
		return syntype;
	}

	public void setSyntype(String syntype) {
		this.syntype = syntype;
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

	public String getMobileprice() {
		return mobileprice;
	}

	public void setMobileprice(String mobileprice) {
		this.mobileprice = mobileprice;
	}

	public String getServattr() {
		return servattr;
	}

	public void setServattr(String servattr) {
		this.servattr = servattr;
	}



	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getOrderTimes() {
		return orderTimes;
	}

	public void setOrderTimes(String orderTimes) {
		this.orderTimes = orderTimes;
	}

}
