package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class ChapterVO extends Validateable implements VO {

	private String chapterId;//章节（话，集）ID
	private String contentid;//所属内容标识
	private String name;//章节名称
	private String description;//章节描述
	
	private String small;//小图片；
	private String medium;//中图片；
	private String large;//大图片；
	
	private String fee;//资费
	
	private String updateFlag;//更新标志
	private String type;//内容类型
	private String feeCode;//计费代码
	private String sortid;//排序序号
	
	private String published;//发布时间 0930+

	public ChapterVO(String[] field) {
		if (field != null && field.length >= 13) {
			this.chapterId = field[0];
			this.contentid = field[1];
			this.name = field[2];
			this.description = field[3];
			
			this.small = field[4];
			this.medium = field[5];
			this.large = field[6];
			
			this.fee = field[7];
			this.updateFlag = field[8];
			this.type = field[9];
			this.feeCode = field[10];
			this.sortid = field[11];
			this.published = field[12];
		}

	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	
	
	
	

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	
	public void validate() {
		// TODO Auto-generated method stub
		if (isEmpty(chapterId)) {
			this.addFieldError("chapterId不能为空");
		}
		if (isEmpty(contentid)) {
			this.addFieldError("contentid不能为空");
		}
		if (isEmpty(name)) {
			this.addFieldError("name不能为空");
		}
		if (isEmpty(description)) {
			this.addFieldError("description不能为空");
		}
		if (isEmpty(small)) {
			this.addFieldError("small不能为空");
		}
		if (isEmpty(medium)) {
			this.addFieldError("medium不能为空");
		}
		if (isEmpty(large)) {
			this.addFieldError("large不能为空");
		}
		if (isEmpty(type)) {
			this.addFieldError("type不能为空");
		}
		if (isEmpty(sortid)) {
			this.addFieldError("sortid不能为空");
		}
		if (isEmpty(published)) {
			this.addFieldError("发布时间不能为空");
		}
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return chapterId;
	}

	public String getPublished()
	{
		return published;
	}

	public void setPublished(String published)
	{
		this.published = published;
	}

}
