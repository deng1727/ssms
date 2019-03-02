<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicCategoryVO" %>
<% NewMusicCategoryVO musicCategoryVO = (NewMusicCategoryVO)request.getAttribute("newMusicCategoryVO");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
function addCategory()
{
	window.location.href = "categoryTree.do?parentCategoryId=<bean:write name="categoryId" />&perType=add";
}

function modCategory()
{
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryId" />&parentCategoryId=<bean:write name="newMusicCategoryVO" property="parentCategoryId"/>&perType=mod";
}

function delCategory()
{
	window.location.href = "categoryTree.do?perType=del&categoryId=<bean:write name="categoryId" />";
}
function approvalCategory(){
	window.location.href = "categoryTree.do?perType=approval&categoryId=<bean:write name="categoryId" />";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">新音乐货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架路径： </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><bean:write name="newMusicCategoryVO" property="categoryName"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架ID： </td>
    <td width="71%" class="text4"><bean:write name="newMusicCategoryVO" property="categoryId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述： </td>
    <td class="text4">
		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="newMusicCategoryVO" property="desc"/></textarea>
    </td>
  </tr>
   <tr>
    <td width="29%" align="right" class="text3">货架图片： </td>
    <td width="71%" class="text4"><bean:write name="newMusicCategoryVO" property="albumPic"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架排序：</td>
    <td class="text4">
		<bean:write name="newMusicCategoryVO" property="sortId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">是否在门户展示：</td>
    <td class="text4">
    	<logic:equal value="1" name="newMusicCategoryVO" property="type"> 是 </logic:equal> 
    	<logic:equal value="0" name="newMusicCategoryVO" property="type"> 否 </logic:equal> 
    </td>
  </tr>
   
    <tr>
    <td align="right" class="text3">是否属于彩铃货架：</td>
    <td class="text4">
    	<logic:equal value="1" name="newMusicCategoryVO" property="cateType"> 是 </logic:equal> 
    	<logic:equal value="0" name="newMusicCategoryVO" property="cateType"> 否 </logic:equal> 
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
   <logic:iterate id="vo" indexId="ind" name="keyBaseList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	   <td width="29%" align="right" class="text3"><bean:write name="vo" property="keydesc"/> </td>
    <td width="71%" class="text4"> <logic:equal value="3" name="vo" property="keyType">
    <textarea name="desc" rows="4" cols="50" readonly>
    <bean:write name="vo" property="value"/>
    </textarea>
    </logic:equal>
    <logic:equal value="1" name="vo" property="keyType">
 
    <bean:write name="vo" property="value"/>

    </logic:equal><logic:equal value="2" name="vo" property="keyType">
   
    <bean:write name="vo" property="value"/>
    
    </logic:equal></td> 
	  </tr>
	    </logic:iterate>
	 <tr>
    <td align="right" class="text3">审批状态：</td>
    <td class="text4">
    	<logic:equal value="0" name="newMusicCategoryVO" property="music_status"> 编辑 </logic:equal> 
    	<logic:equal value="1" name="newMusicCategoryVO" property="music_status"> 已发布 </logic:equal> 
    	<logic:equal value="2" name="newMusicCategoryVO" property="music_status"> 待审批 </logic:equal> 
    	<logic:equal value="3" name="newMusicCategoryVO" property="music_status"> 审批不通过</logic:equal> 
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
        <input name="Submit" type="submit" class="input1" value="修改属性" <% if("2".equals(musicCategoryVO.getMusic_status())){  %>disabled="disabled"<%} %> onclick="return modCategory();">
        <input name="Submit" type="submit" class="input1" value="删除" <% if("2".equals(musicCategoryVO.getMusic_status())){  %>disabled="disabled"<%} %> onclick="return delCategory();">
        <input name="Submit" type="submit" class="input1" value="提交审批" <% if(!"0".equals(musicCategoryVO.getMusic_status())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
        <%} %>
    </td>
  </tr>
</table>
</body>
</html>
