<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.newmusic139.bo.New139AlbumBO"%>
<%@page import="com.aspire.ponaadmin.web.newmusic139.bo.New139BillboardBO"%>
<%@page import="com.aspire.ponaadmin.web.newmusic139.bo.New139KeyWorldSynBO"%>
<%@page import="com.aspire.ponaadmin.web.newmusic139.bo.New139PicSynBO"%>
<%@page import="com.aspire.ponaadmin.web.newmusic139.bo.New139TagSynBO"%>
<%@ page import="com.aspire.common.Validator" %>

<%
		String type = Validator.filter(request.getParameter("type"));
		if (type != null && type.equals("ablum"))
		{
			New139AlbumBO ab = new New139AlbumBO();
			ab.handDeal();
		}
		else if (type != null && type.equals("billbor"))
		{
			New139BillboardBO ab = new New139BillboardBO();
			ab.handDeal();
		}
		else if (type != null && type.equals("keyword"))
		{
			New139KeyWorldSynBO ab = new New139KeyWorldSynBO();
			ab.handDeal();
		}
		else if (type != null && type.equals("musicTag"))
		{
			New139TagSynBO ab = new New139TagSynBO();
			ab.handDeal();
		}
		else if (type != null && type.equals("picSysnchro"))
		{
			New139PicSynBO ab = new New139PicSynBO();
			ab.handDeal();
		}

	%>
<html>
<head>
<title>新139音乐数据导入</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>新139音乐数据导入执行完毕，请检查相关目录结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
