<%@ page contentType="text/html; charset=gb2312"  %>
<%@ page import="java.util.List" %>
<%@ page import="com.aspire.ponaadmin.web.repository.camonitor.CategoryMonitor" %>
<%

List allCategoryList = (List)request.getSession().getAttribute("allCategoryList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>

<script type="text/javascript" src="../../js/dtree.js"></script>
<link rel="StyleSheet" href="../../css/dtree.css" type="text/css" />
<link href="../../css/common.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#E9F0F8" leftmargin="0" topmargin="5">


<div class="dtree">
<!--
	<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
-->
	<script type="text/javascript">
		<!--

		d = new dTree('d');

		d.add(0,-1,'货架');
		
		<% if(allCategoryList != null && allCategoryList.size()> 0){
		  for(int i = 0;i < allCategoryList.size();i++){
		  CategoryMonitor cm = (CategoryMonitor)allCategoryList.get(i);
		  %>
		  d.add(<%=cm.getCategoryid()%>,<%=cm.getParentcategoryid()%>,'<%=cm.getName()%>','moContentList.do?categoryID=<%=cm.getId()%>&cgyPath=<%=cm.getFullName()%>','<%=cm.getFullName()%>','mainFrame1');
		  <%
		  
		  }
		} %>
		//d.add(1,0,'终端集团根货架','example01.html');
		//d.add(2,1,'软件','api.html','软件','mainFrame1');
		//d.add(3,1,'游戏','example01.html');
		//d.add(4,1,'主题','example01.html');
		//d.add(5,1,'热卖场','example01.html');
		//d.add(6,5,'品牌区','example01.html');
		//d.add(7,2,'免费','example01.html');
		//d.add(8,3,'免费','example01.html');
		//d.add(9,4,'免费','example01.html');
		//d.add(10,2,'排行','example01.html');
		
		//d.add(9,0,'My Pictures','example01.html','Pictures I\'ve taken over the years','','','img/imgfolder.gif');
		//d.add(10,9,'The trip to Iceland','example01.html','Pictures of Gullfoss and Geysir');
		//d.add(11,9,'Mom\'s birthday','example01.html');
		//d.add(12,0,'WWW门户根货架','example01.html');
		//d.add(21,12,'软件','example01.html');
		//d.add(31,12,'游戏','example01.html');
		//d.add(41,12,'主题','example01.html');
		//d.add(13,1,'Node 1.3','example01.html');
		document.write(d);

		//-->
	</script>

</div>

</body>
</html>
