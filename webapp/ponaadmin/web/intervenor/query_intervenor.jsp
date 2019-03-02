<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>人工干预管理</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">

<br>
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="创建容器" onClick="addIntervenor();"/>
		</td>
	</tr>
</table>
<br>
<br>
<br>

<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="intervenorListView.do" method="post">
	<input name="actionType" type="hidden" value="list"/>
	<tr>
	  <td width="50%" align="right" bgcolor="#B8E2FC">
	  	<input name="where" type="text" value="<bean:write name="where"/>" size="12"/>
	  </td>
	  <td width="30%" bgcolor="#B8E2FC">
	  	<input name="button" type="button" value="查找" onClick="queryIntervenor();"/>
	  </td>
	</tr>
  </form>
</table>
<br>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>容器列表</b>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO">
	    <%
		if("text5".equals(tmpStyle))
	  	{
			tmpStyle = "text4";
	  	}
	  	else
	  	{
	  		tmpStyle = "text5";
	  	}
		%>
		<tr class=<%=tmpStyle%>>
			<td align="center">
				<a href="#" onclick="doAction('<bean:write name="vo" property="id"/>');">
					<bean:write name="vo" property="name"/>
				</a>
			</td>
		</tr>
	</logic:iterate>
</table>
</logic:notEmpty>
<script language="javascript">
function addIntervenor()
{
	var href = "parent.viewFrame.location='add_intervenor.jsp'";
	window.setTimeout(href,0);
	window.status="完成";
}
function doAction(id)
{
  var href = "parent.viewFrame.location='intervenorView.do?actionType=editView&id=" + id + "'";
  window.setTimeout(href,0);
  window.status="完成";
}
function queryIntervenor()
{
	ContentForm.submit();
}
</script>
</body>
</html>
