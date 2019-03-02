package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class ChapterVO extends Validateable implements VO {

	private String chapterId;//�½ڣ���������ID
	private String contentid;//�������ݱ�ʶ
	private String name;//�½�����
	private String description;//�½�����
	
	private String small;//СͼƬ��
	private String medium;//��ͼƬ��
	private String large;//��ͼƬ��
	
	private String fee;//�ʷ�
	
	private String updateFlag;//���±�־
	private String type;//��������
	private String feeCode;//�ƷѴ���
	private String sortid;//�������
	
	private String published;//����ʱ�� 0930+

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
			this.addFieldError("chapterId����Ϊ��");
		}
		if (isEmpty(contentid)) {
			this.addFieldError("contentid����Ϊ��");
		}
		if (isEmpty(name)) {
			this.addFieldError("name����Ϊ��");
		}
		if (isEmpty(description)) {
			this.addFieldError("description����Ϊ��");
		}
		if (isEmpty(small)) {
			this.addFieldError("small����Ϊ��");
		}
		if (isEmpty(medium)) {
			this.addFieldError("medium����Ϊ��");
		}
		if (isEmpty(large)) {
			this.addFieldError("large����Ϊ��");
		}
		if (isEmpty(type)) {
			this.addFieldError("type����Ϊ��");
		}
		if (isEmpty(sortid)) {
			this.addFieldError("sortid����Ϊ��");
		}
		if (isEmpty(published)) {
			this.addFieldError("����ʱ�䲻��Ϊ��");
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
