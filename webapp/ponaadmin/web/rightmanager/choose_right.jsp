<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RightVO"%>
<%@page import="java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ѡ��Ȩ��</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script type="text/javascript" src="../../js/alai_tree.js"></script>
<script type="text/javascript" src="../../js/tree/xmlextras.js"></script>
<script type="text/javascript" src="../../js/alaiload_tree.js"></script>
<script language="JavaScript">
<!--
function setRight()
{
  var choosed = false;
  var value;
  for(var i=0;i<chooseForm.length;i++)
  {
    if(chooseForm.elements[i].type=="radio")
    {
      if(chooseForm.elements[i].checked)
      {
        choosed = true;
        value = chooseForm.elements[i].value;
        break;
      }
    }
  }
  if(choosed==false)
  {
    alert("����ѡ��!");
    return;
  }
  window.returnValue=value;
  window.close();
}
//-->
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="chooseForm" method="post" action="" >
  <tr>
    <td colspan="4" align="center" bgcolor="#BFE8FF">
      Ȩ��ѡ��
    </td>
  </tr>
  <tr>
  <td>
     <div id="divTree1"></div>
  </td>

  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#BFE8FF">
      <input type="button" name="btn_001" value="ȷ��" onClick="setRight();">
    </td>
  </tr>
</form>
</table>
<br>
<br>
<script>
<!--
function reload(mykey){
	var obj=findByMykey(tree1,mykey);
	obj.reload();
}

//����
function treeControl()
{
}

//�ص�
function WWWExtend(treeObj){
    var mykey = treeObj.label.id;
    var keyText = treeObj.label.innerHTML.substring(88,treeObj.label.innerHTML.lastIndexOf('<')) ;
    if(mykey!="_tree_root_right_id")
    {
      return " <input type='radio' name='rightID' value='"+mykey+"#"+keyText+"'\"> ";
    }
    return "";
}


//������ڵ�
var key="_tree_root_right_id";
var rootNode;
var name="����Ȩ��";
var url="../../tree?type=right&key="+key;
var tree1=new alaiload_tree(key,name,divTree1,url);
-->
</script>
</body>
</html>
