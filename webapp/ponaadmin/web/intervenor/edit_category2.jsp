<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.repository.Category"%>
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
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">榜单关联信息</td>
	  </tr>
	</table>
<form name="ContentForm" action="categoryList.do" method="post">
	<input name="actionType" type="hidden" value="relatingView"/>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	

		<tr>
		
		<%
		String tmpStyle = "text5";
	%>
	
			<td width="50%" >
			<table border="0" width="100%">
			
		
		<logic:iterate id="vo" indexId="ind" name="list" type="com.aspire.ponaadmin.web.repository.Category">
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
		
				<td><input type="hidden" name="id" value="<bean:write name="vo" property="id"/>"/>
			  	榜单路径：<bean:write name="vo" property="namePath"/>
			  	</td>
			  	</tr>
			 </logic:iterate>
			 </table>
			</td>
			
			<bean:size id="length" name="list"/>
			<td width="50%" class="text4" collapse ="<bean:write name="length"/>">  
				<input type="button" value="从容器目录中提取" onclick="addIntervenorId();">
			</td>
			
		</tr>
		
	</table>
</form>	
<br>

<script language="javascript">

function addIntervenorId()
{
	var categoryId = document.getElementsByName("id");
	var i;
	var ids="";
	for(i=0;i<categoryId.length;i++){
		ids =ids+","+categoryId[i].value;
	}
	if(ids.indexOf(",")==0){
		ids = ids.substring(1,ids.length);
	}
	var returnv=window.showModalDialog("categoryAddIntervenor.do?actionType=addIntervenor&isFirst=1", "","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300")
	
	if(returnv != undefined)
	{
		var id = returnv;
		
		
		ContentForm.action = "categoryRelating.do?actionType=relating&intervenorId=" + id + "&categoryId=" + ids;
		ContentForm.submit();
	} 
}

</script>
</body>
</html>
