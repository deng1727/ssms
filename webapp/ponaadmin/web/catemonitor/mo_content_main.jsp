<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
String keywordid = Validator.filter(request.getParameter("keywordid"));
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架树</title>
</head>

<frameset rows="*" cols="170,*" framespacing="0" frameborder="yes" border="0">
  <frame src="mo_content_tree.jsp" name="leftFrame1" scrolling="auto">
  <frame src="cate_monitor.jsp" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
