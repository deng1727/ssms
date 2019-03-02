<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String goURL = String.valueOf(request.getAttribute("goURL"));
if(goURL == null || goURL.trim().equals(""))
    {
      goURL = "javascript:history.go(-1);";
    }
%>
<script  LANGUAGE="JavaScript">
	function gotoURL()
{
	window.location.href = "<%=goURL%>";
}
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>修改货架商品锁定位置</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="window.returnValue=true">
<form action="<%=goURL%>" method="post">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">商品锁定管理--->修改货架商品锁定位置</td>
  </tr>
</table>
<table width="95%"  border="0" align="center">
  <tr class="text1">
    <td align="center">
    	<p><msg:messageList/><br></p>
	</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
      <input type="button" class="input1" name="btn_back" value="返回" onClick="gotoURL();">
    </td>
  </tr>
</table>
</form>
</body>
</html>
