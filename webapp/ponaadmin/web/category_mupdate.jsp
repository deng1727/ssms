<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryRuleBO"%>
<%
  String   cid[]     =   request.getParameterValues("cid");

  CategoryRuleBO cb =  CategoryRuleBO.getInstance();
  	cb.manualUpdateCategory(cid);
%>
<html>
<head>
<title>������Ʒ�ֶ�����</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>�ֶ�����ִ����ϣ�������ؽ����<br/><br/>
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
