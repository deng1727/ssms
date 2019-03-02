<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@page import="com.aspire.dotcard.syncData.dao.DataSyncDAO"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<%
 //同步电子流的应用促销信息
    //    DataSyncDAO.getInstance().refreshContetExt();
       
//      自动打徽章
        DataSyncDAO.getInstance().refreshIteMmark ();

%>


<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>所在位置：货架管理中心----应用活动属性同步</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>同步应用活动信息及自动更新徽章</FONT></td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
     执行完成，请查看日志
    </td>
  </tr>
</table>

<br>
<table width="95%"  border="0" align="center" cellspacing="1">


</table>
</body>
</html>
