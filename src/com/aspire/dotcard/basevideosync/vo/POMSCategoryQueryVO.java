package com.aspire.dotcard.basevideosync.vo;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * 
 * @author 杜永春
 *货架查询vo
 */
public class POMSCategoryQueryVO {
	/**
	 * 货架名称
	 */
	private String cname;
	/**
	 * 货架id
	 */
	private String categoryID;
	/**
	 * 货架描述
	 */
	private String cDesc;
	/**
	 * 货架图片
	 */
	private String pic;
	/**
	 * 货架排序
	 */
	private String sortID;
	/**
	 * 审批状态
	 */
	private String video_status;
	/**
	 * 
	 * 货架路径，如果parentid为-1，则父货架为根货架
	 */
	private String path;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getcDesc() {
		return cDesc;
	}
	public void setcDesc(String cDesc) {
		this.cDesc = cDesc;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSortID() {
		return sortID;
	}
	public void setSortID(String sortID) {
		this.sortID = sortID;
	}
	public String getVideo_status() {
		return video_status;
	}
	public void setVideo_status(String video_status) {
		this.video_status = video_status;
	}
	
}
