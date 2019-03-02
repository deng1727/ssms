package com.aspire.dotcard.baseVideoNew.vo;

public class NodeVO {

	/**
	 * 栏目标识
	 */
	private String nodeId;
	/**
	 * 栏目中文名称
	 */
	private String nodename;
	/**
	 * 栏目介绍
	 */
	private String description;
	/**
	 * 图片路径
	 */
	private String logopath;
	/**
	 * 产品标识
	 */
	private String productid;
	/**
	 * 内容集标识,1 是 2 否 是否内容集
	 */
	private int collectflag;
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogopath() {
		return logopath;
	}
	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public int getCollectflag() {
		return collectflag;
	}
	public void setCollectflag(int collectflag) {
		this.collectflag = collectflag;
	} 
	
}
