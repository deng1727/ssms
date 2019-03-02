<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo"%>
<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>根货架配置</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">
	function checkForm(form)
	{
	  if(!checkLength(form.categoryId,"根货架ID",-1,12)) return false;
	  return true;
	}
	function selectRootCategory()
{
	var returnv=window.showModalDialog("<%=request.getContextPath()%>/web/channeladmin/categoryRuleIdQuery.do?isFirst=1", "new","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	if(returnv != undefined)
	{
		document.getElementById('categoryId').value=returnv;
	}
}
	
	function back(){
		window.history.back(-1);
	}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="form1" method="post" action="openChannelsCategory.do"   enctype="multipart/form-data"   onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    新增根货架配置
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <input type="hidden" name="method" value="save">
	      <% 
	      	OpenChannelsCategoryVo categoryVo = (OpenChannelsCategoryVo)request.getAttribute("vo");
	      %>
	      <input type="hidden" name="channelsId" value="<%=categoryVo.getChannelsId() %>">
	      <font color="#ff0000">(*)</font>根货架ID：
	    </td>
	    <td width="75%" class="text4">
	    	<input name="categoryId" id = "categoryId" type="text" value="<%=categoryVo.getCategoryId()==null?"":categoryVo.getCategoryId() %>" >
	    	<input type="button" value="选择根货架" onclick="selectRootCategory();"/>
	    	<span id="platForm"></span>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="确定">
		    <input type="button" class="input1" value="返回" onclick="back();">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
