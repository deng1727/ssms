package com.aspire.dotcard.basemusic.vo;

/**
 * ������
 * @author ouyangguangming
 *
 */
public class ToneBoxVO {

	private String id;//�����б�ʶ
	private String name;//����������
	private String description;//�����н���
	private String charge;//�ʷ�,��λ��
	private String valid;//��Ч��
	private String operType;//��������(	1��������2�����£�3�����ߡ�ȫ���ļ��ж���1)
	private String updateTime;
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	

}
