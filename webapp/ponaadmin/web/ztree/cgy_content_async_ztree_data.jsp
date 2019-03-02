<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>
<%@ page import="com.aspire.common.Validator" %>

<%
String menuStatus = Validator.filter(request.getParameter("menuStatus")==null?"":request.getParameter("menuStatus"));
String id = Validator.filter(request.getParameter("id"));
if(id==null){
	out.println("[{id:701,pId:100,name:'货架分类',isParent:true,open:true,url:'cgyContentList.do?categoryID=701&cgyPath=货架&menuStatus=" + menuStatus + "',target:'mainFrame1',childs:");
	List nodeList;
	if(menuStatus != null && !"".equals(menuStatus)){
		nodeList = TreeNodeManager.getchildrenNodes("701"+","+menuStatus);
	}else{
		nodeList = TreeNodeManager.getchildrenNodes("701");
	}
	 
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);
		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../resourcemgr/cgyContentList.do?categoryID="+node.getId()+"&menuStatus=" + menuStatus +"&cgyPath="+TreeNodeManager.getPath(node.getId())+"' ,isParent:'true',target:'mainFrame1'}");	
		if(i!=nodeList.size()-1){
			out.println(",");
		}
	}
	out.println("]");
	out.println("}]");
}else{	
	List nodeList;
	if(menuStatus != null && !"".equals(menuStatus)){
		nodeList = TreeNodeManager.getchildrenNodes(id+","+menuStatus);
	}else{
		nodeList = TreeNodeManager.getchildrenNodes(id);
	}
if(nodeList.size()>0){
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);

		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../resourcemgr/cgyContentList.do?categoryID="+node.getId()+"&menuStatus=" + menuStatus +"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'mainFrame1'}");
		if(i!=nodeList.size()-1){
			out.println(",");
		}
		
	}
	out.println("]");
	
	
}

}

//out.println("[{id:1,name:'aiyan123',pId:0,isParent:'true'},{id:101,name:'qqq',pId:1}]");
//out.println("{id:2,name:'qqq',,pId:1}]");
%>