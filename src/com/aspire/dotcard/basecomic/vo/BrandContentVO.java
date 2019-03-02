package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class BrandContentVO extends Validateable implements VO {

	private String contentId;// ���ݱ�ʶ
	private String brandId;// Ŀ¼��ʶ
	private String sortid;// �������

	public BrandContentVO(String[] field) {
		if (field != null && field.length >= 3) {
			this.contentId = field[0];
			this.brandId = field[1];
			this.sortid = field[2];
		}

	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (contentId == null || "".equals(contentId)) {
			this.addFieldError("contentId����Ϊ��");
		}

		if (brandId == null || "".equals(brandId)) {
			this.addFieldError("brandId����Ϊ��");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
