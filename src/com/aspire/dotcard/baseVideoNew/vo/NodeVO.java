package com.aspire.dotcard.baseVideoNew.vo;

public class NodeVO {

	/**
	 * ��Ŀ��ʶ
	 */
	private String nodeId;
	/**
	 * ��Ŀ��������
	 */
	private String nodename;
	/**
	 * ��Ŀ����
	 */
	private String description;
	/**
	 * ͼƬ·��
	 */
	private String logopath;
	/**
	 * ��Ʒ��ʶ
	 */
	private String productid;
	/**
	 * ���ݼ���ʶ,1 �� 2 �� �Ƿ����ݼ�
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
