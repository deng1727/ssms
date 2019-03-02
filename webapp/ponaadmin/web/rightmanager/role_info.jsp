<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.*"%>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="java.util.*"%>
<%
  RoleVO roleVO =(RoleVO)request.getAttribute("roleVO");
  String name=roleVO.getName();
  String desc=roleVO.getDesc();
  List list=(List)request.getAttribute("roleRight");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF"><FONT class=large>权限管理----角色属性</FONT></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="15%" align="right" class="text3">角色名称：</td>
    <td width="85%" height="20" class="text4"><%=PublicUtil.getPageStr(name)%></td>
  </tr>
  <tr>
    <td align="right" class="text3">角色描述：</td>
    <td height="20" class="text4"><%=PublicUtil.getPageStr(desc)%></td>
  </tr>
  <tr>
    <td align="right" class="text3">角色权限：</td>
    <td height="20" class="text4">
        <%
        for (int i=0;i<list.size();i++)
        {
            RightVO vo = (RightVO)list.get(i);
        %>
        <%=vo.getName()%>&nbsp;
        <%
        }
        %>
</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF" class="text3">
      <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</table>
</body>
</html>
