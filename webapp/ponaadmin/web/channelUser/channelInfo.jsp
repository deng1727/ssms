<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.channelUser.vo.ChannelVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%@ page language="java"   %>   
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�������˺���ϸ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<%
    	ChannelVO channel = (ChannelVO)request.getAttribute("ChannelVO");
    	
     %>
<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">�˺���ϸ</td>
	  </tr>
	</table>
	<form name="ContentForm" action="categoryList.do" method="post">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<input name="actionType" type="hidden" value="listCategory"/>
		<tr>
		<td width="10%" align="right" class="text3">
		  	������ID��
		</td>
		<td width="40%" class="text4">
			<p><%=PublicUtil.getPageStr(channel.getChannelsId())%></p>
		  	
		</td>
		<td width="10%" align="right" class="text3">
		  	���������ƣ�
		</td>
		<td width="40%" class="text4">  
			<p><%=PublicUtil.getPageStr(channel.getChannelsName())%></p>
		</td>
		</tr>
		<tr>
		<td width="10%" align="right" class="text3">
		  	�������˺ţ�
		</td>
		<td width="40%" class="text4">
			<p><%=PublicUtil.getPageStr(channel.getChannelsNO())%></p>
		  	
		</td>
		<td width="10%" align="right" class="text3">
		  	״̬��
		</td>
		<td width="40%" class="text4">  
			<p><%
    if(channel.getStatus()==null||"0".equals(channel.getStatus())) out.write("����");
    else out.write("����");
  %> </p>
		</td>
		</tr>
		<tr>
		<td width="10%" align="right" class="text3">
		  	����ʱ�䣺
		</td>
		<td width="40%" class="text4">
			<p><%=PublicUtil.getDateString(channel.getCreateDate(),"yyyy-MM-dd hh:mm:ss")%></p>
		  	
		</td>
		<td width="10%" align="right" class="text3">
		  	������������
		</td>
		<td width="40%" class="text4">  
			<%=PublicUtil.getPageStr(channel.getChannelsDesc())%>
		</td>
		</tr>
	</table>
	
  </form>
<br>

</body>
</html>
