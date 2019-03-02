package com.aspire.dotcard.basevideosync.vo;

public class VideoCategoryVO
{
    /**
     * ���Iid
     */
    private String id;
    
    /**
     * ���ܱ���ID
     */
    private String categoryId;
    
    /**
     * �����ܱ���ID
     */
    private String parentcId;
    
    /**
     * ��������
     */
    private String cname;
    
    /**
     * ��Ƶ��������
     */
    private String cdesc;
    
    /**
     * ��Ƶ����ͼƬ
     */
    private String picture;
    
    /**
     * �����
     */
    private int sortId;
    
    /**
     * �Ƿ����Ż���ʾ
     */
    private int isShow;
    
    /**
     * ��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     */
    private String videoStatus;
    /**
     * ������Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��
     */
    private String goodsStatus;
    
    /**
     * ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��
     */
    private String delproStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getParentcId() {
		return parentcId;
	}

	public void setParentcId(String parentcId) {
		this.parentcId = parentcId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(String videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getDelproStatus() {
		return delproStatus;
	}

	public void setDelproStatus(String delproStatus) {
		this.delproStatus = delproStatus;
	}
}
