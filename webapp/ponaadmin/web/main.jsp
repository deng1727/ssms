<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
</head>
<frameset rows="*,18" frameborder="NO" border="0" framespacing="0">
  <frameset rows="68,*" frameborder="NO" border="0" framespacing="0">
    <frame src="<%=request.getContextPath()%>/web/common/head.jsp" name="topFrame" scrolling="NO" noresize>
    <frameset cols="160,*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=request.getContextPath()%>/web/common/menu.jsp" name="leftFrame" scrolling="auto">
		<right:checkNotDisplay rightID="0_0101_SITE_VIEW"><frame src="<%=request.getContextPath()%>/web/blank.html" name="mainFrame"></right:checkNotDisplay>
	</frameset>
  </frameset>
  <frame src="<%=request.getContextPath()%>/web/common/foot.jsp" name="bottomFrame" scrolling="NO" noresize>
</frameset>
<noframes><body>
</body></noframes>

<body>
</body
</html>
