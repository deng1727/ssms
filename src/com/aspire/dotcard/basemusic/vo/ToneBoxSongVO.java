package com.aspire.dotcard.basemusic.vo;

/**
 * �����и���
 * @author ouyangguangming
 *
 */
public class ToneBoxSongVO {

	private String id;//������ʶ
	private String boxId;//�����б�ʶ
	private String sortId;//�������
	private String operType;//��������(	1��������2�����£�3�����ߡ�ȫ���ļ��ж���1)
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
