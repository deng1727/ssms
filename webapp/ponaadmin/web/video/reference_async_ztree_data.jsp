<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>
<%@ page import="com.aspire.common.Validator" %>

<%

String id = Validator.filter(request.getParameter("id"));
if(id==null){
	out.println("[{id:0,pId:0,name:'»õ¼Ü',isParent:true,open:true,url:'',target:'mainFrame1',childs:");
	List nodeList = TreeNodeManager.getchildrenNodesVideo(null,"1");
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);
		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../baseVideo/queryReference.do?perType=query&categoryId="+node.getId()+"&nodeId="+node.getNodeId()+"',isParent:'true',target:'mainFrame1'}");	
		if(i!=nodeList.size()-1){
			out.println(",");
		}
	}
	out.println("]");
	out.println("}]");
}else{
	
List nodeList = TreeNodeManager.getchildrenNodesVideo(id,"1");
if(nodeList.size()>0){
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);

		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../baseVideo/queryReference.do?perType=query&categoryId="+node.getId()+"&nodeId="+node.getNodeId()+"',isParent:'true',target:'mainFrame1'}");
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