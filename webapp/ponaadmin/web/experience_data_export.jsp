<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.experience.ExperienceBO"%>
<%@ page import="com.aspire.common.Validator" %>
<%
  String  opType    =   Validator.filter(request.getParameter("opType"));
ExperienceBO experienceBO = new ExperienceBO();
 
  	experienceBO.fullExport(opType);
%>
<html>
<head>
<title>货架体验营销劳动竞赛数据导出</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>手动导出执行完毕，请检查相关结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
