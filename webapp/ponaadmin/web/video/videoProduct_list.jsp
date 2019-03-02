<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="name" name="name"  value=""/>
<bean:parameter id="spName" name="spName"  value=""/>
<bean:parameter id="contentID" name="contentID"  value="" />
<bean:parameter id="type" name="type"  value=""/>
<bean:parameter id="isrecomm" name="isrecomm"  value=""/>
<bean:parameter id="date" name="date"  value=""/>
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market视频货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----视频产品属性管理</td>
  </tr>
</table>


<form name="contentForm" action="" method="post">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
	<td align="center" class="text5">
	<input name="buttonExport" type="button" class="input1" onClick="contentExtImport();" value="批量导入资费描述">
	</td>
	</tr>
</table>

<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
  
    <td width="4%" align="center" class="title1">ID</td>
    <td width="15%" align="center" class="title1">名称</td>
    <td width="11%" align="center" class="title1">提供商</td>
    <td width="6%" align="center" class="title1">计费类型</td>
    <td width="20%" align="center" class="title1">资费描述</td>
    <td width="4%" align="center" class="title1">资费</td>
    <td width="10%" align="center" class="title1">开始日期</td>
    <td width="10%" align="center" class="title1">免费属性</td>
    <td width="10%" align="center" class="title1">免费体验生效时间</td>
    <td width="10%" align="center" class="title1">免费体验失效时间</td>
 </tr>


 <logic:iterate id="product" indexId="ind" name="videoProductList"
type="com.aspire.dotcard.baseVideo.vo.VideoProductVO">

<tr class=text5>

	<td align="center">
		<bean:write name="product" property="productID" />
	</td>
	<td align="center">
		<bean:write name="product" property="productName" />
	</td>
	<td align="center">
		<bean:write name="product" property="cpid" />
	</td>
	<td align="center">
		<logic:equal name="product" property="feeType" value="01">免费</logic:equal>
		<logic:equal name="product" property="feeType" value="02">按次</logic:equal>
		<logic:equal name="product" property="feeType" value="03">包月</logic:equal>
		<logic:equal name="product" property="feeType" value="04">大包月</logic:equal>
	</td>
	<td align="center">
		<bean:write name="product" property="feeDesc" />
	</td>
	<td align="center">
		<bean:write name="product" property="fee" />
	</td>
	<td align="center">
		<bean:write name="product" property="startdate" />
	</td>
	<td align="center">
		<logic:equal name="product" property="freeType" value="0">无免费体验</logic:equal>
		<logic:equal name="product" property="freeType" value="1">新产品</logic:equal>
		<logic:equal name="product" property="freeType" value="2">初次订购</logic:equal>
	</td>
	<td align="center">
		<bean:write name="product" property="freeEffecTime" />
	</td>
	<td align="center">
		<bean:write name="product" property="freeTimeFail" />
	</td>
</tr>
</logic:iterate>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">

</table>


</form>
</body>
</html>
<script language="JavaScript">
<!--




function contentExtAdd()
{
    contentForm.action="contentExtAdd.do";
    contentForm.submit();
}

function contentExtImport()
{
    window.location.href="../video/product_desc_Import.jsp";
}

//-->
</script>