<%@ page contentType="text/html; charset=gb2312" session="true" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<script type="text/javascript" src="../../js/alai_tree.js"></script>
<script type="text/javascript" src="../../js/tree/xmlextras.js"></script>
<script type="text/javascript" src="../../js/alaiload_tree.js"></script>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#E9F0F8" leftmargin="0" topmargin="5">
<div id="divTree1"></div>
<script  LANGUAGE="JavaScript">
function reload(mykey){
	var obj=findByMykey(tree1,mykey);
	obj.reload();
}

//控制
function treeControl(){
    var node=tree1.getSelectedNode();
    var url = 'categoryInfo.do?categoryID='+node.label.id+'&cgyPath='+getFormatPath(node);
    doAction(url);//消除页面加载完毕，进度条一直加载的问题。
}

//回调
function WWWExtend(obj){
    return " (<a href='categoryEdit.do?action=new&pCategoryID="+obj.label.id+"&cgyPath="+getFormatPath(obj)+"' target='mainFrame1'>+</a>) ";
}
function getFormatPath(node)
{
   return node.getPath('>>').replace(/(\s*\(\+\))/g, "").replace(/(\s*>>)/g, ">>").substr(2);//先去掉‘(+)’字符，再去掉'>>'前的空格。
}
function doAction(url)
{
  var href = "parent.mainFrame1.location='" + url + "'";
  window.setTimeout(href,0);
  window.status="完成";
}

//定义根节点
var key="<%=rootNodeID%>";
var name="货架";
var url="../../tree?type=resource&key="+key;
var tree1=new alaiload_tree(key,name,divTree1,url);
</script>
</body>
</html>
