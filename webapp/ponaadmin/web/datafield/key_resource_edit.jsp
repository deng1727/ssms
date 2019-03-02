<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----应用扩展属性修改</td>
  </tr>
</table>
<form name="form1" method="post" action="resourceUpdate.do"    enctype="multipart/form-data"  onSubmit="return resourceUpdate();" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">应用扩展属性修改</td>
  </tr>
</table>
<input type="hidden" name="tid" value="<bean:write name="vo" property="contentID"/>" />
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="35%" align="right" class="text3">ID：</td>
    <td width="65%" class="text4"><bean:write name="vo" property="id"/></td>
  </tr>
	<tr>
    <td align="right" class="text3">内容ID：</td>
    <td class="text4"><bean:write name="vo" property="contentID"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">内容名称：</td>
    <td class="text4"><bean:write name="vo" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">业务内容分类：</td>
    <td class="text4"><bean:write name="vo" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">提供商：</td>
    <td class="text4"><bean:write name="vo" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP企业代码：</td>
    <td class="text4"><bean:write name="vo" property="icpCode"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">业务代码：</td>
    <td class="text4"><bean:write name="vo" property="icpServId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">提供范围：</td>
    <td class="text4"><logic:equal name="vo" property="servattr" value="G">全网业务</logic:equal><logic:equal name="vo" property="servattr" value="L">省内业务</logic:equal><td>
  </tr>
  <tr>
    <td align="right" class="text3">内容标识：</td>
    <td class="text4"><bean:write name="vo" property="contenttag"/><td>
  </tr>
  <tr>
    <td align="right" class="text3">内容标签：</td>
    <td class="text4"><bean:write name="vo" property="keywordsFormat"/><td>
  </tr>
  <tr>
    <td align="right" class="text3">上线时间：</td>
    <td class="text4"><bean:write name="vo" property="marketdate"/><td>
  </tr>
    <logic:iterate id="vo" indexId="ind" name="map"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	    <td align="right" class="text3"><bean:write name="vo" property="keydesc"/></td>
	    <td class="text4"><logic:equal value="1" name="vo" property="keyType"> <input name="<bean:write name="vo" property="keyname"/>" type="text" value="<bean:write name="vo" property="value"/>" size="60"> </logic:equal> 
			<logic:equal value="2" name="vo" property="keyType"> <input  type="file"  name="<bean:write name="vo" property="keyname"/>"   size="60"> &nbsp;仅支持png格式<bean:write name="vo" property="value"/>	
			</logic:equal> 
			 <logic:equal value="3" name="vo" property="keyType"> <textarea  name="<bean:write name="vo" property="keyname"/>" rows="5" cols="40"><bean:write name="vo" property="value"/></textarea></logic:equal>
			 <logic:notEqual value="" name="vo" property="value">
      			<input type="checkbox" value="1" name="clear_<bean:write name='vo' property='keyname'/>"/>
      			清空当前扩展字段内容
      		</logic:notEqual>
	    </td>
	  </tr>
	    </logic:iterate>
 
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><logic:notEmpty name="map"><input name="Submit" type="submit" class="input1" value="修改"></logic:notEmpty> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回"></td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
function resourceUpdate()
{
  if(confirm("您确定要进行修改操作吗？"))
  {
    listForm.submit();
  }
  else
  {
  	return false;	
  }
}
//-->
</script>