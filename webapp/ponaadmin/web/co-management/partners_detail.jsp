<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
   Map<String,Object> map = (Map)request.getAttribute("cooperationInfo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>查询合作商</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function back(){
	window.history.back(-1);
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	            查询合作商
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	           合作商ID：
	    </td>
	    <td width="75%" class="text4"><%=map.get("cooperationId") %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	合作商名称：
	    </td>
	    <td class="text4"><%=map.get("cooperationName") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	合作日期：
	    </td>
	    <td class="text4"><%=map.get("codate") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	合作方式：
	    </td>
	    <td class="text4">
	      <label><input name="cooperationType" type="checkbox" value="1" <% if(map.get("cotype").toString().contains("1")){ %> checked<%} %> disabled="disabled" />内容合作 </label> 
          <label><input name="cooperationType" type="checkbox" value="2" <% if(map.get("cotype").toString().contains("2")){ %> checked<%} %> disabled="disabled" />换量合作</label> 
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	根货架ID：
	    </td>
	    <td class="text4"><%=map.get("categoryId") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	渠道个数：
	    </td>
	    <td class="text4"><%=map.get("channelsnumber") %>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" onclick="back();" value="返回列表">
		  </td>
		</tr>
	</table>
</body>
</html>
