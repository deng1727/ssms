<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.common.Validator" %>
<%@ page import="com.aspire.dotcard.baseVideo.vo.VideoCategoryVO" %>
<% VideoCategoryVO videoCategoryVO = (VideoCategoryVO)request.getAttribute("videoCategoryVO"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market视频货架管理系统</title>
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
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryId" />&parentId=<bean:write name="videoCategoryVO" property="parentId"/>&perType=mod";
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
    <td align="center" class="title1">视频货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架路径： </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><bean:write name="videoCategoryVO" property="baseName"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架ID： </td>
    <td width="71%" class="text4"><bean:write name="videoCategoryVO" property="id"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述： </td>
    <td class="text4">
		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="videoCategoryVO" property="desc"/></textarea>
    </td>
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
    <td align="right" class="text3">
    <logic:equal value="1" name="videoCategoryVO" property="baseType">栏目ID：</logic:equal>
    <logic:equal value="2" name="videoCategoryVO" property="baseType">排行榜ID：</logic:equal>
    <logic:equal value="10" name="videoCategoryVO" property="baseType">自定义基础ID：</logic:equal>
    </td>
    <td class="text4">
    	<bean:write name="videoCategoryVO" property="baseId"/>
    </td>
  </tr>
  <logic:equal value="1" name="videoCategoryVO" property="baseType">
  <tr>
    <td align="right" class="text3">产品ID：</td>
    <td class="text4">
    	<bean:write name="videoCategoryVO" property="productId"/>
    </td>
  </tr>
  </logic:equal>
	<logic:iterate id="vo" indexId="ind" name="keyBaseList"
		type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
		<tr>
			<td width="29%" align="right" class="text3">
				<bean:write name="vo" property="keydesc" />
				：
			</td>
			<td width="71%" class="text4">
				<logic:equal value="3" name="vo" property="keyType">
					<textarea name="desc" rows="4" cols="50" readonly><bean:write name="vo" property="value" /></textarea>
				</logic:equal>
				<logic:equal value="1" name="vo" property="keyType">
	
					<bean:write name="vo" property="value" />
	
				</logic:equal>
				<logic:equal value="2" name="vo" property="keyType">
	
					<bean:write name="vo" property="value" />
	
				</logic:equal>
			</td>
		</tr>
	</logic:iterate>
	
	<tr>
    <td align="right" class="text3">审批状态：</td>
    <td class="text4">
    	<logic:equal value="0" name="videoCategoryVO" property="video_status"> 编辑 </logic:equal> 
    	<logic:equal value="1" name="videoCategoryVO" property="video_status"> 已发布 </logic:equal> 
    	<logic:equal value="2" name="videoCategoryVO" property="video_status"> 待审批 </logic:equal> 
    	<logic:equal value="3" name="videoCategoryVO" property="video_status"> 审批不通过</logic:equal> 
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
        <input name="Submit" type="submit" class="input1" <% if("2".equals(videoCategoryVO.getVideo_status())){  %>disabled="disabled"<%} %> value="修改属性" onclick="return modCategory();">
        <input name="Submit" type="submit" class="input1" <% if("2".equals(videoCategoryVO.getVideo_status())){  %>disabled="disabled"<%} %> value="删除" onclick="return delCategory();">
        <input name="Submit" type="submit" class="input1" value="提交审批" <% if(!"0".equals(videoCategoryVO.getVideo_status())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
        
        <%} %>
    </td>
  </tr>
</table>
</body>
</html>
