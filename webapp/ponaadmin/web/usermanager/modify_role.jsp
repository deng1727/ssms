<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RoleVO" %>
<%@page import="java.util.List" %>
<%
  String userID = (String)request.getAttribute("userID");
  List userRoleList = (List)request.getAttribute("userRoleList");
  List allRoleList = (List)request.getAttribute("allRoleList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="javascript">
function setUserRole()
{
  setUserRoleForm.submit();
}
</script>
</head>


<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="setUserRoleForm" action="saveUserRole.do" mothod="post">
  <tr>
    <td colspan="4" align="center" class="title1"><FONT class=large>用户管理----用户角色修改</FONT></td>
  </tr>
  <tr align="center" class="text3">
    <td height="20" colspan="4">用户：<%=userID%><input name="userID" type="hidden" value="<%=userID%>"></td>
  </tr>
<%
  int i = 0;
  for(i=0;i<allRoleList.size();i++)
  {
    RoleVO role = (RoleVO)allRoleList.get(i);
%>

  <%if(i%4==0){%><tr bgcolor="#E9F0F8"><%}%>
    <td width="23%" height="20" bgcolor="#E9F0F8">
      &nbsp;<input type="checkbox" name="roleIDs" value="<%=role.getRoleID()%>" <%if(userRoleList.contains(role)){%>checked<%}%>> 
      <%=role.getName()%>
    </td>
  <%if(i%4==3){%></tr><%}%>
<%}%>
<%
  //计算出剩下的还有几个需要补<td>
  int leave = 4-(i)%4;
  if((i-1)%4!=3)
  {
    for(int b=0;b<leave;b++)
    {
%>
      <td width="23%" height="20" bgcolor="#E9F0F8">&nbsp;</td>
<%  
    }
%>
    </tr>
<%    
  }  
%>
  <tr>
    <td align="center" colspan="4" class="text3">
      <input type="button" class="input1" name="btn_setrole" value="确定" onClick="javascript:setUserRole();">
      <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</form>
</table>
</body>
</html>
