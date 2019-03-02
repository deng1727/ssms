<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.dotcard.synczcoms.bo.ZcomsDataSyncBO"%>
<%@ page import="com.aspire.common.Validator" %>
<%
String type = Validator.filter(request.getParameter("type"));
ZcomsDataSyncBO zb =  ZcomsDataSyncBO.getInstance();
  zb.syncZcomCon(type);
%>
<html>
<head>
<title>Zcom数据导入</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>Zcom数据导入执行完毕，请检查相关目录结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
