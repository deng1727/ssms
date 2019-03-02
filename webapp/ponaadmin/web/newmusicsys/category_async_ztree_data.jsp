<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>
<%@ page import="com.aspire.common.Validator" %>

<%

String id = Validator.filter(request.getParameter("id"));
if(id==null){
	out.println("[{id:0,pId:0,name:'»õ¼Ü',isParent:true,open:true,url:'',target:'viewFrame',childs:");
	List nodeList = TreeNodeManager.getchildrenNodesNewMusic(null,null);
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);
		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'categoryTree.do?perType=query&categoryId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'viewFrame'}");	
		if(i!=nodeList.size()-1){
			out.println(",");
		}
	}
	out.println("]");
	out.println("}]");
}else{
	
List nodeList = TreeNodeManager.getchildrenNodesNewMusic(id,null);
if(nodeList.size()>0){
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);

		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName().replaceAll("'","¡¯")+"',url:'categoryTree.do?perType=query&categoryId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'viewFrame'}");
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