<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.dotcard.basecomic.BaseComicLoadTask"%>
<%
 new BaseComicLoadTask().run();
%>
<html>
<head>
<title>�����������ݵ���</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>�����������ݵ���ִ����ϣ�������ؽ����<br/><br/>
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
