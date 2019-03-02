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
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/alai_tree.js"></script>
<script type="text/javascript" src="../../js/tree/xmlextras.js"></script>
<script type="text/javascript" src="../../js/alaiload_tree.js"></script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF"><FONT class=large>权限管理----角色权限设定</FONT></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3"><tr><td id="textTD" name="textTD" align="center">&nbsp;</td></tr></table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="addRoleForm" method="post" action="roleRights.do" onSubmit="return checkForm();" >
  <tr align="center" bgcolor="#E9F0F8">
    <td height="20" colspan="4" bgcolor="#E9F0F8">角色：<%=name%><input name="roleID" type="hidden" value="<%=roleID%>"><input name="rightIDs" type="hidden"></td>
  </tr>
  <tr>
  <td>
     <div id="divTree1"></div>
  </td>

  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#BFE8FF">
      <input type="submit" class="input1" name="submit01" value="确定">
      <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
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

//控制
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

//回调
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

//检查一个节点是否需要“打勾”
function isNeedChecked(key)
{
  if(ownedRight.indexOf(key)>-1)
  {
    return true;
  }
  return false;
}

//当用户点击一个节点后，需要更新内存数组中的用户权限
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
      showMsgText("<font color=#ff0000>这是一个复合权限，用户拥有了复合权限后就拥有复合权限下的所有权限。</font>");
    }
    else
    {
      showMsgText("<font color=#ff0000>这是一个操作权限，如果用户已经拥有本操作权限所属的复合权限，您就不用设置本操作权限了。</font>");
    }
  }
  catch(ex)
  {
  }
}

//检查表单
function checkForm()
{
  addRoleForm.rightIDs.value = ownedRight;
  return true;
}

//定义根节点
var key="_tree_root_right_id";
var rootNode;
var name="操作权限";
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
