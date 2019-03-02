<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<%
	String root = request.getContextPath();
	String cgyPath=request.getAttribute("cgyPath")==null?"":(String)request.getAttribute("cgyPath");
	String deviceName=request.getAttribute("deviceName")==null?"":(String)request.getAttribute("deviceName");
	String platFormName=request.getAttribute("platFormName")==null?"":(String)request.getAttribute("platFormName");
	String cityName=request.getAttribute("cityName")==null?"":(String)request.getAttribute("cityName");
 %>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../ztree/js/jquery-1.4.4.min.js"></script>
</head>
<script language="JavaScript">
<!--
function addCategory()
{
  window.location.href = "<%=root%>/web/channelUser/categoryEdit.do?action=new&pCategoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>"+
                         "&ctype=<bean:write name="category" property="ctype"/>";
}

function modCategory()
{
  window.location.href = "<%=root%>/web/channelUser/categoryEdit.do?action=update&pCategoryID=<bean:write name="category" property="parentID"/>&categoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>"+
                         "&ctype=<bean:write name="category" property="ctype"/>";
}

function delCategory()
{
  if(confirm("<%=ResourceUtil.getResource("PUBLIC_DELETE_ALERT")%>"))
  {
    window.location.href = "<%=root%>/web/channelUser/categoryDel.do?pCategoryID=<bean:write name="category" property="parentID"/>&categoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>";
    
  }
}
 
 

 
//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><bean:write name="category" property="name"/></td>
  </tr>
  
  <tr>
    <td width="29%" align="right" class="text3">关联门店： </td>
    <td width="71%" class="text4">
		<bean:write name="category" property="displayRelation"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架说明： </td>
    <td class="text4">
      <textarea name="desc" rows="4" cols="50" readonly><bean:write name="category" property="desc"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架序号： </td>
    <td class="text4">
      <bean:write name="category" property="sortID"/>
    </td>
  </tr>
  
  <tr>
    <td align="right" class="text3">货架URL： </td>
    <td class="text4">
      <bean:write name="category" property="multiurl"/>
    </td>
  </tr>
  
   <tr>
    <td align="right" class="text3">货架开始/生效时间： </td>
    <td class="text4">
      <bean:write name="category" property="startDate"/>
    </td>
  </tr>
   <tr>
    <td align="right" class="text3">货架结束/失效时间： </td>
    <td class="text4">
      <bean:write name="category" property="endDate"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">是否支持异网： </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="othernet"> 支持 </logic:equal>
     <logic:equal value="0" name="category" property="othernet"> 不支持 </logic:equal>
    </td>
  </tr>
   <tr>
    <td align="right" class="text3">是否在门户展示： </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="state"> 是 </logic:equal>
     <logic:equal value="0" name="category" property="state"> 否 </logic:equal>
    </td>
  </tr>
  <logic:equal value="11" name="category" property="ctype">
   <tr>
    <td width="29%" align="right" class="text3">货架预览图： </td>
    <td width="71%" class="text4">
        <logic:notEmpty name="category" property="picURL">
		<img src="<bean:write name="category" property="picURL"/>logo4.png">
		</logic:notEmpty>
		<logic:empty name="category" property="picURL">
		无预览图
		</logic:empty>
		
    </td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架统计信息： </td>
    <td width="71%" class="text4">
		<bean:write name="category" property="statistic"/>
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">货架是否是机型货架： </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="deviceCategory"> 是 </logic:equal>
     <logic:equal value="0" name="category" property="deviceCategory"> 否 </logic:equal>
    </td>
  </tr>
  <logic:equal value="1" name="category" property="deviceCategory">
  <tr>
    <td align="right" class="text3">货架对应机型： </td>
    <td class="text4">
		<%=deviceName %>
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">货架适配平台： </td>
    <td class="text4">
		<%=platFormName %>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架适配地域： </td>
    <td class="text4">
		<%=cityName %>
    </td>
  </tr>
 <logic:iterate id="vo" indexId="ind" name="keyResourceList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	   <td width="29%" align="right" class="text3"><bean:write name="vo" property="keydesc"/> </td>
    <td width="71%" class="text4">
    <logic:equal value="3" name="vo" property="keyType">
    <textarea name="desc" rows="4" cols="50" readonly>
    <bean:write name="vo" property="value"/>
    </textarea>
    </logic:equal>
    <logic:equal value="1" name="vo" property="keyType">
 
    <bean:write name="vo" property="value"/>

    </logic:equal><logic:equal value="2" name="vo" property="keyType">
   
    <bean:write name="vo" property="value"/>
    
    </logic:equal>
    </td> 
	  </tr>
	    </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="新增" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="修改属性" onclick="return modCategory();">
        <logic:notEqual name="category" property="ctype" value="1"><input name="Submit" type="submit" class="input1" value="删除" onclick="return delCategory();"></logic:notEqual>
    </td>
  </tr>
</table>
</body>
</html>
