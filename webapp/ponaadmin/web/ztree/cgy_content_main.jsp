<%@page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.aspire.common.Validator" %>
<% 
  String menuStatus = Validator.filter(request.getParameter("menuStatus")==null?"":request.getParameter("menuStatus"));  
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架树</title>
</head>

<frameset rows="*" cols="220,*" framespacing="0" frameborder="yes" border="0">
  <frame src="cgy_content_async.jsp?menuStatus=<%=menuStatus %>" name="leftFrame1" scrolling="auto">
  <frame src="cgy_content_main_notes.jsp" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
