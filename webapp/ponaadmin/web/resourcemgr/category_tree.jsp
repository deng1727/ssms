<%@ page contentType="text/html; charset=gb2312" session="true" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
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

//����
function treeControl(){
    var node=tree1.getSelectedNode();
    var url = 'categoryInfo.do?categoryID='+node.label.id+'&cgyPath='+getFormatPath(node);
    doAction(url);//����ҳ�������ϣ�������һֱ���ص����⡣
}

//�ص�
function WWWExtend(obj){
    return " (<a href='categoryEdit.do?action=new&pCategoryID="+obj.label.id+"&cgyPath="+getFormatPath(obj)+"' target='mainFrame1'>+</a>) ";
}
function getFormatPath(node)
{
   return node.getPath('>>').replace(/(\s*\(\+\))/g, "").replace(/(\s*>>)/g, ">>").substr(2);//��ȥ����(+)���ַ�����ȥ��'>>'ǰ�Ŀո�
}
function doAction(url)
{
  var href = "parent.mainFrame1.location='" + url + "'";
  window.setTimeout(href,0);
  window.status="���";
}

//������ڵ�
var key="<%=rootNodeID%>";
var name="����";
var url="../../tree?type=resource&key="+key;
var tree1=new alaiload_tree(key,name,divTree1,url);
</script>
</body>
</html>
