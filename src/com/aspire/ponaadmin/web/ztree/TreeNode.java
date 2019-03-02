package com.aspire.ponaadmin.web.ztree;

public class TreeNode {
	private String id;
	private String name;
	private String parentId;
	private String nodeId = "";
	
	TreeNode(String id,String name,String parentId){
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
	
	TreeNode(String id,String name,String parentId, String nodeId){
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.nodeId = nodeId;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getNodeId()
	{
		return nodeId;
	}
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
	
	
	
	

}
