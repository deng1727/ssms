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
<link href="../../css/common.css" rel="stylesheet" type="text/css">
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
    if(tree1.getSelectedNode().label.id!="<%=rootNodeID%>")
    {
      parent.mainFrame1.location = 'cateContent.do?cateId='+tree1.getSelectedNode().label.id;
    }
}

//�ص�
function WWWExtend(obj){
    return "";
}

//������ڵ�
var key="<%=rootNodeID%>";
var name="����";
var url="../../tree?type=resource&key="+key;
var tree1=new alaiload_tree(key,name,divTree1,url);
</script>
<table>
  <tr>
    <td height="3">
      &nbsp;&nbsp;<img src="../../image/tree/folder_03.gif" width="18" height="18" align="absmiddle"><a href="cateContent.do?cateId=<%=rootNodeID%>" target="mainFrame1">δ��������</a>
    </td>
  </tr>
</table>
</body>
</html>
