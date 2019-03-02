<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资源树</title>
</head>

<frameset rows="*" cols="220,*" framespacing="0" frameborder="yes" border="0">
  <frame src="<%=request.getContextPath()%>/web/channelUser/ztree/category_async.jsp" name="leftFrame1" scrolling="auto">
  <frame src="<%=request.getContextPath()%>/web/channelUser/ztree/category_main_notes.jsp" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
