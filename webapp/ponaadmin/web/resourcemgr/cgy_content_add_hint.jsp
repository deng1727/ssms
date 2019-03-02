<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String backURL = String.valueOf(request.getAttribute("backURL"));
String menuStatus = Validator.filter(request.getParameter("menuStatus"));
backURL = backURL + "&menuStatus="+menuStatus;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>添加内容到货架</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="window.returnValue=true">
<form action="<%=backURL%>" method="post">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<bean:write name="category" property="namePath"/>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">货架栏目管理----添加内容</td>
  </tr>
</table>
<table width="95%"  border="0" align="center">
  <tr class="text1">
    <td align="center"><msg:messageList/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
      <input name="btn_cont" type="submit" class="input1" value="继续添加">
      <input name="btn_close" type="button" class="input1" onClick="window.returnValue=true,window.close();" value="关闭窗口">
    </td>
  </tr>
</table>
</form>
</body>
</html>
