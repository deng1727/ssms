<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RightVO"%>
<%@page import="java.util.*"%>
<%@ page import="com.aspire.common.Validator" %>
<%
String roleID= Validator.filter(request.getParameter("roleID"));
String name= Validator.filter(request.getParameter("name"));
String action = Constants.FORWARD_ADD_TOKEN;
List roleRightList=(List)request.getAttribute("roleRight");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/alai_tree.js"></script>
<script type="text/javascript" src="../../js/tree/xmlextras.js"></script>
<script type="text/javascript" src="../../js/alaiload_tree.js"></script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF"><FONT class=large>Ȩ�޹���----��ɫȨ���趨</FONT></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3"><tr><td id="textTD" name="textTD" align="center">&nbsp;</td></tr></table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="addRoleForm" method="post" action="roleRights.do" onSubmit="return checkForm();" >
  <tr align="center" bgcolor="#E9F0F8">
    <td height="20" colspan="4" bgcolor="#E9F0F8">��ɫ��<%=name%><input name="roleID" type="hidden" value="<%=roleID%>"><input name="rightIDs" type="hidden"></td>
  </tr>
  <tr>
  <td>
     <div id="divTree1"></div>
  </td>

  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#BFE8FF">
      <input type="submit" class="input1" name="submit01" value="ȷ��">
      <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</form>
</table>
<script>
<!--
function showMsgText(msg)
{
  textTD.innerHTML=msg;
}
function reload(mykey){
	var obj=findByMykey(tree1,mykey);
	obj.reload();
}

//����
function treeControl()
{
  var mynode=tree1.getSelectedNode();
  var nowkey = mynode.label.id;
  for(var i=0;i<addRoleForm.length;i++)
  {
    if(addRoleForm.elements[i].type=="checkbox" && addRoleForm.elements[i].value ==nowkey)
    {
	  addRoleForm.elements[i].checked=!addRoleForm.elements[i].checked;
	  dealArray(nowkey);
      break;
    }
  }
}

//�ص�
function WWWExtend(treeObj){
    var mykey = treeObj.label.id;
    if(mykey=="_tree_root_right_id")
    {
      rootNode = treeObj;
      return "";
    }
    var checkStr = "";
    if(isNeedChecked(mykey)==true)
    {
      checkStr = "checked";
    }
    return " <input type='checkbox' name='rightID' value='"+mykey+"' " + checkStr + " onClick=\"dealArray('"+mykey+"');\"> ";
}

//���һ���ڵ��Ƿ���Ҫ���򹴡�
function isNeedChecked(key)
{
  if(ownedRight.indexOf(key)>-1)
  {
    return true;
  }
  return false;
}

//���û����һ���ڵ����Ҫ�����ڴ������е��û�Ȩ��
function dealArray(rightID)
{
  if(isNeedChecked(rightID))
  {
    var start = ownedRight.indexOf(rightID);
    var middle = start + rightID.length + 1;
    if(start+rightID.length==ownedRight.length) start = start -1;
    ownedRight = ownedRight.substring(0,start) + ownedRight.substring(middle,ownedRight.length);
  }
  else
  {
    if(ownedRight.length>0) ownedRight = ownedRight + ":";
    ownedRight = ownedRight + rightID;
  }
  var mynode = findByMykey(rootNode,rightID);
  try
  {
    if(mynode.folder!=undefined && mynode.folder==1)
    {
      showMsgText("<font color=#ff0000>����һ������Ȩ�ޣ��û�ӵ���˸���Ȩ�޺��ӵ�и���Ȩ���µ�����Ȩ�ޡ�</font>");
    }
    else
    {
      showMsgText("<font color=#ff0000>����һ������Ȩ�ޣ�����û��Ѿ�ӵ�б�����Ȩ�������ĸ���Ȩ�ޣ����Ͳ������ñ�����Ȩ���ˡ�</font>");
    }
  }
  catch(ex)
  {
  }
}

//����
function checkForm()
{
  addRoleForm.rightIDs.value = ownedRight;
  return true;
}

//������ڵ�
var key="_tree_root_right_id";
var rootNode;
var name="����Ȩ��";
var url="../../tree?type=right&key="+key;
var tree1=new alaiload_tree(key,name,divTree1,url);
var ownedRight = "";
<%
for(int i=0;i<roleRightList.size();i++)
{
  String tmpStr = "";
  if(i>0) tmpStr = ":";
  RightVO right = (RightVO)roleRightList.get(i);
%>
  ownedRight = ownedRight + "<%=tmpStr%>" + "<%=right.getRightID()%>";
<%
}
%>
-->
</script>
</body>
</html>
