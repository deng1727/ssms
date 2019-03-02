package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class BrandVO extends Validateable implements VO {
	private String brandId;//Ʒ�ƹ�˾/����Ŀ��ʶ
	private String parentBrandId;//����ʶ
	private String name;//����
	private String logo1;//Ʒ�ƹ�LOGO1
	private String logo2;//Ʒ�ƹ�LOGO2
	private String logo3;//Ʒ�ƹ�LOGO3
	private String description;//����
	private String sortid;//�������




	public BrandVO(String[] field) {
		if (field != null && field.length >= 8) {
			this.brandId = field[0];
			this.parentBrandId = field[1];
			this.name = field[2];
			this.logo1 = field[3];
			this.logo2 = field[4];
			this.logo3 = field[5];
			this.description = field[6];
			this.sortid = field[7];
		}

	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getParentBrandId() {
		return parentBrandId;
	}

	public void setParentBrandId(String parentBrandId) {
		this.parentBrandId = parentBrandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getLogo1() {
		return logo1;
	}

	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}

	public String getLogo2() {
		return logo2;
	}

	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}

	public String getLogo3() {
		return logo3;
	}

	public void setLogo3(String logo3) {
		this.logo3 = logo3;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (brandId == null || "".equals(brandId)) {
			this.addFieldError("brandId����Ϊ��");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return brandId+"|"+parentBrandId;
	}

}
