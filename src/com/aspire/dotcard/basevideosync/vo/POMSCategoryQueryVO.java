package com.aspire.dotcard.basevideosync.vo;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * 
 * @author ������
 *���ܲ�ѯvo
 */
public class POMSCategoryQueryVO {
	/**
	 * ��������
	 */
	private String cname;
	/**
	 * ����id
	 */
	private String categoryID;
	/**
	 * ��������
	 */
	private String cDesc;
	/**
	 * ����ͼƬ
	 */
	private String pic;
	/**
	 * ��������
	 */
	private String sortID;
	/**
	 * ����״̬
	 */
	private String video_status;
	/**
	 * 
	 * ����·�������parentidΪ-1���򸸻���Ϊ������
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
