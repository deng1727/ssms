<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>

<%

String id = request.getParameter("id");
if(id==null){
	out.println("[{id:0,pId:0,name:'����',isParent:true,open:true,url:'',target:'mainFrame1',childs:");
	List nodeList = TreeNodeManager.getchildrenNodesRead(null,null);
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);
		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../baseRead/categoryTree.do?perType=query&categoryId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'mainFrame1'}");	
		if(i!=nodeList.size()-1){
			out.println(",");
		}
	}
	out.println("]");
	out.println("}]");
}else{
	
List nodeList = TreeNodeManager.getchildrenNodesRead(id,null);
if(nodeList.size()>0){
	out.println("[");
	for(int i=0;i<nodeList.size();i++){
		TreeNode node = (TreeNode)nodeList.get(i);

		out.print("{id:"+node.getId()+",pId:"+node.getParentId()+",name:'"+node.getName()+"',url:'../baseRead/categoryTree.do?perType=query&categoryId="+node.getId()+"&cgyPath="+TreeNodeManager.getPath(node.getId())+"',isParent:'true',target:'mainFrame1'}");
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