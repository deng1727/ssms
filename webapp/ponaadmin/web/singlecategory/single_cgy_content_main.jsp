<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
String keywordid = Validator.filter(request.getParameter("keywordid"));
String userId = Validator.filter(request.getParameter("userId"));
String type = (String)request.getAttribute("type");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架树</title>
</head>

<frameset rows="*" cols="170,*" framespacing="0" frameborder="yes" border="0">
  <frame src="../singlecategory/single_cgy_content_tree.jsp?userId=<%=userId %>&type=<%=type %>" name="leftFrame1" scrolling="auto">
  <frame src="../resourcemgr/cgy_content_main_notes.jsp?subSystem=ssms" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
