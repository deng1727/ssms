<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>资源树</title>
</head>
<%
//approvalFlag = "yes" 表示该操作需要经过审批。
String approvalFlag = request.getParameter("approvalFlag")==null?"no":request.getParameter("approvalFlag");
 %>
<frameset rows="*" cols="220,*" framespacing="0" frameborder="yes" border="0">
  <frame src="category_async.jsp?approvalFlag=<%=approvalFlag%>" name="leftFrame1" scrolling="auto">
  <frame src="category_main_notes.jsp" name="mainFrame1">
</frameset>
<noframes><body>
</body></noframes>
</html>
