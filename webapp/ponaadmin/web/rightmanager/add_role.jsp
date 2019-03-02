<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.*"%>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%
  RoleVO roleVO =(RoleVO)request.getAttribute("roleVO");
  //System.out.println("roleVO...."+roleVO);
  String value=null;
  String name=null;
  String desc=null;
  String roleID=null;
  if (roleVO==null)
   {
   value=Constants.FORWARD_ADD_TOKEN;
   name="";
   desc="";
   roleID="";
   }
   else
   {
   value=Constants.FORWARD_UPDATE_TOKEN;
   name=roleVO.getName();
   desc=roleVO.getDesc();
   roleID=roleVO.getRoleID();
   }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
function checkForm()
{
  //检查角色名称长度
  var name = addRoleForm.name.value;
  if(trim(name)=="" || getLength(trim(name))<6  || getLength(trim(name))>40)
  {
    alert("角色名称长度必须为6－40个字符（一个中文占两个字符）。");
    addRoleForm.name.focus();
    return false;
  }
  //检查角色名称内容合法性
  if(!checkIllegalChar(name,"'\"<>\\/"))
  {
    alert("角色名称不能包含 ' \" < > \\ /  等字符。");
    addRoleForm.name.focus();
    return false;
  }
  //检查角色描述长度
  var descs = addRoleForm.descs.value;
  if(trim(descs)=="" || getLength(trim(descs))>200)
  {
    alert("描述长度必须小于200个字符（一个中文占两个字符），并且不能为空！");
    addRoleForm.descs.focus();
    return false;
  }
  //检查角色描述内容合法性
  if(!checkIllegalChar(descs,"'\"<>\\/"))
  {
    alert("角色描述不能包含 ' \" < > \\ /  等字符。");
    addRoleForm.name.focus();
    return false;
  }
  return true;
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>权限管理----<%if(value.equals(Constants.FORWARD_ADD_TOKEN)){%>新增<%}else{%>修改<%}%>角色</FONT></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="addRoleForm" method="post" action="roleManager.do" onSubmit="return checkForm();">
  <tr>
    <td width="22%" height="20" align="right" class="text3"><font color="#ff0000">(*)</font>角色名称：</td>
    <td width="78%" class="text3">
      <input name="name" type="text" value="<%=PublicUtil.getPageStr(name)%>">
    </td>
  </tr>
  <tr>
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>角色描述：</td>
    <td class="text4">
      <textarea name="descs" cols="60" rows="4" ><%=PublicUtil.getPageStr(desc)%></textarea></td>
      <input type="hidden" name="action" value="<%=PublicUtil.getPageStr(value)%>">
      <input type="hidden" name="roleID" value="<%=PublicUtil.getPageStr(roleID)%>">
  </tr>
  <tr class="text3">
    <td align="center" colspan="2">
      <input type="submit" class="input1" name="submit01" value="确定">
      <%if(!value.equals(Constants.FORWARD_ADD_TOKEN)){%><input type="button" class="input1" name="btn_back" value="返回" onClick="history.go(-1);">
<%}%>
    </td>
  </tr>
</table>
</form>
</body>
</html>
