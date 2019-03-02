<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.category.export.CategoryExportBO"%>
<%
  CategoryExportBO cb =  CategoryExportBO.getInstance();
  cb.doCategory();
%>
<html>
<head>
<title>指定货架数据导出</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>导出执行完毕，请检查相关目录结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
