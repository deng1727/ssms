<%@page language="java" contentType="text/html;charset=GBK" errorPage="error.html"%>
<%@page import="com.aspire.ponaadmin.web.category.export.repchange.CategoryRepChangeBO"%>
<%
  CategoryRepChangeBO cb =  CategoryRepChangeBO.getInstance();
  cb.categoryRepChangeExport();
%>
<html>
<head>
<title>ָ�������ظ����ֻ��ʵ���</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>����ִ����ϣ��������Ŀ¼�����<br/><br/>
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
