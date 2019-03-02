<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>
<%@ page import="com.aspire.common.Validator" %>
<%

String id = Validator.filter(request.getParameter("id"));
if(id==null){
	out.println("[{id:701,pId:100,name:'»õ¼Ü',isParent:true,open:true,url:'../lockLocation/lockLocationListAction.do?nodeId=701&cgyPath=»õ¼Ü',target:'mainFrame1',childs:");
	List nodeList = TreeNodeManager.getchildrenNodes("701");
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);
		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../lockLocation/lockLocationListAction.do?nodeId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'mainFrame1'}");	
		if(i!=nodeList.size()-1){
			out.println(",");
		}
	}
	out.println("]");
	out.println("}]");
}else{
	
List nodeList = new TreeNodeManager().getchildrenNodes(id);
if(nodeList.size()>0){
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);

		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../lockLocation/lockLocationListAction.do?nodeId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'mainFrame1'}");
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