<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.common.Validator" %>
<%@ page import="com.aspire.dotcard.basevideosync.vo.VideoCategoryVO" %>
<html>
<head>
<% VideoCategoryVO videoCategoryVO = (VideoCategoryVO)request.getAttribute("videoCategoryVO");
%>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market新视频货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
function into()
{
	<%
	String isReload = Validator.filter(request.getParameter("isReload"));
	if(isReload != null && "yes".equals(isReload))
	{
	%>
	var href = "parent.treeframe.location='videoCategory.do?perType=tree'";
	window.setTimeout(href,0);
	window.status="完成";
	<%
	}
	%>
}
function addCategory()
{
	window.location.href = "videoCategory.do?pCategoryId=<bean:write name="categoryId" />&perType=toAdd";
}

function updateCategory()
{
	window.location.href = "videoCategory.do?categoryId=<bean:write name="categoryId" />&perType=toUpdate";
}

function delCategory()
{
	window.location.href = "videoCategory.do?perType=del&categoryId=<bean:write name="categoryId" />";
}
function approvalCategory(){
	window.location.href = "videoCategory.do?perType=approval&categoryId=<bean:write name="categoryId" />";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="into();">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">POMS货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架路径： </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><bean:write name="videoCategoryVO" property="cname"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架ID： </td>
    <td width="71%" class="text4"><bean:write name="videoCategoryVO" property="categoryId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述： </td>
    <td class="text4">
		<textarea name="cdesc" rows="4" cols="50" readonly><bean:write name="videoCategoryVO" property="cdesc"/></textarea>
    </td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架图片： </td>
    <td width="71%" class="text4"><bean:write name="videoCategoryVO" property="picture"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架是否在门户展示：</td>
    <td class="text4">
    	<logic:equal value="1" name="videoCategoryVO" property="isShow"> 是 </logic:equal> 
    	<logic:equal value="0" name="videoCategoryVO" property="isShow"> 否 </logic:equal> 
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架排序信息：</td>
    <td class="text4">
    	<bean:write name="videoCategoryVO" property="sortId"/>
    </td>
  </tr>
 <tr>
    <td align="right" class="text3">审批状态：</td>
    <td class="text4">
    	<logic:equal value="0" name="videoCategoryVO" property="videoStatus"> 编辑 </logic:equal> 
    	<logic:equal value="1" name="videoCategoryVO" property="videoStatus"> 已发布 </logic:equal> 
    	<logic:equal value="2" name="videoCategoryVO" property="videoStatus"> 待审批 </logic:equal> 
    	<logic:equal value="3" name="videoCategoryVO" property="videoStatus"> 审批不通过</logic:equal> 
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="新增" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="修改属性" <% if("2".equals(videoCategoryVO.getVideoStatus())){  %>disabled="disabled"<%} %> onclick="return updateCategory();">
        <input name="Submit" type="submit" class="input1" value="删除" <% if("2".equals(videoCategoryVO.getVideoStatus())){  %>disabled="disabled"<%} %> onclick="return delCategory();">
        
        <input name="Submit" type="submit" class="input1" value="提交审批" <% if(!"0".equals(videoCategoryVO.getVideoStatus())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
    </td>
  </tr>
</table>
</body>
</html>
