package com.aspire.dotcard.basevideosync.vo;

public class DocFileVO {

	public DocFileVO() {
	}
	/**
	 * 内容创建的时候，系统生成的ID
	 */
	private String docID;
	/**
	 * 字幕文件名称
	 */
	private String docName;
	/**
	 * 字幕文件说明
	 */
	private String docDetail;
	
	public String getDocID() {
		return docID;
	}
	public void setDocID(String docID) {
		this.docID = docID;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocDetail() {
		return docDetail;
	}
	public void setDocDetail(String docDetail) {
		this.docDetail = docDetail;
	}

}
