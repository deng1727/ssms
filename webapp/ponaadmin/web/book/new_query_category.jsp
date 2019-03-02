<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.dotcard.baseread.vo.ReadCategoryVO" %>
<% ReadCategoryVO readCategoryVO = (ReadCategoryVO)request.getAttribute("categoryVO");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market阅读货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
function into()
{
	<%
	String isReload = request.getParameter("isReload");
	if(isReload != null && "yes".equals(isReload))
	{
	%>
	var href = "parent.treeframe.location='categoryTree.do?perType=tree'";
	window.setTimeout(href,0);
	window.status="完成";
	<%
	}
	%>
}

function addCategory()
{
	window.location.href = "categoryTree.do?parentCategoryId=<bean:write name="categoryId" />&perType=add";
}

function modCategory()
{
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryId" />&parentId=<bean:write name="categoryVO" property="parentId"/>&perType=mod";
}

function delCategory()
{
	window.location.href = "categoryTree.do?perType=del&categoryId=<bean:write name="categoryId" />";
}
function approvalCategory(){
	window.location.href = "categoryTree.do?perType=approval&categoryId=<bean:write name="categoryId" />";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="into();">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">图书货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架路径： </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><bean:write name="categoryVO" property="categoryName"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架ID： </td>
    <td width="71%" class="text4"><bean:write name="categoryVO" property="categoryId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述： </td>
    <td class="text4">
		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="categoryVO" property="decrisption"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架是否在门户展示：</td>
    <td class="text4">
    	<logic:equal value="1" name="categoryVO" property="type"> 是 </logic:equal> 
    	<logic:equal value="0" name="categoryVO" property="type"> 否 </logic:equal> 
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架排序信息：</td>
    <td class="text4">
    	<bean:write name="categoryVO" property="sortId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架适配平台： </td>
    <td class="text4">
		<bean:write name="platFormName" />
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架适配地域： </td>
    <td class="text4">
		<bean:write name="cityName" />
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">引用图片路径： </td>
    <td class="text4">
		<bean:write name="categoryVO" property="picUrl"/>
    </td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="keyBaseList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	<tr>
	   <td width="29%" align="right" class="text3"><bean:write name="vo" property="keydesc"/>：</td>
    	<td width="71%" class="text4">
			<logic:equal value="3" name="vo" property="keyType">
	    		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="vo" property="value"/></textarea>
	    	</logic:equal>
    		<logic:equal value="1" name="vo" property="keyType">
 				<bean:write name="vo" property="value"/>
			</logic:equal>
			<logic:equal value="2" name="vo" property="keyType">
   			    <bean:write name="vo" property="value"/>
		    </logic:equal></td> 
	  </tr>
	</logic:iterate>
	
	<tr>
    <td align="right" class="text3">审批状态：</td>
    <td class="text4">
    	<logic:equal value="0" name="categoryVO" property="read_status"> 编辑 </logic:equal> 
    	<logic:equal value="1" name="categoryVO" property="read_status"> 已发布 </logic:equal> 
    	<logic:equal value="2" name="categoryVO" property="read_status"> 待审批 </logic:equal> 
    	<logic:equal value="3" name="categoryVO" property="read_status"> 审批不通过</logic:equal> 
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <% if(request.getParameter("operation") != null && !"".equals(request.getParameter("operation")) && "1".equals(request.getParameter("operation"))){ %>
        <input name="button1" type="button" class="input1" value="关闭" onclick="window.close();">
        <%}else{ %>
        <input name="button1" type="button" class="input1" value="新增" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="修改属性" <% if("2".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return modCategory();">
        <input name="Submit" type="submit" class="input1" value="删除" <% if("2".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return delCategory();">
        <input name="Submit" type="submit" class="input1" value="提交审批" <% if(!"0".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
        
        <%} %>
    </td>
  </tr>
</table>
</body>
</html>
