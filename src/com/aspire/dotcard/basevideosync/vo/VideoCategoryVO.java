package com.aspire.dotcard.basevideosync.vo;

public class VideoCategoryVO
{
    /**
     * 主鍵id
     */
    private String id;
    
    /**
     * 货架编码ID
     */
    private String categoryId;
    
    /**
     * 父货架编码ID
     */
    private String parentcId;
    
    /**
     * 货架名称
     */
    private String cname;
    
    /**
     * 视频货架描述
     */
    private String cdesc;
    
    /**
     * 视频货架图片
     */
    private String picture;
    
    /**
     * 排序号
     */
    private int sortId;
    
    /**
     * 是否在门户显示
     */
    private int isShow;
    
    /**
     * 货架审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     */
    private String videoStatus;
    /**
     * 货架商品审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过
     */
    private String goodsStatus;
    
    /**
     * 删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过
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
