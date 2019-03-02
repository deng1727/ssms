<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资源树</title>
</head>

<frameset rows="*" cols="170,*" framespacing="0" frameborder="yes" border="0">
  <frame src="cgy_content_tree.jsp?subSystem=ssms" name="leftFrame1" scrolling="auto">
  <frame src="cgy_content_main_notes.jsp?subSystem=ssms" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
