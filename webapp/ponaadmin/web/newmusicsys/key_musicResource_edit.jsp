<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>货架管理子系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：音乐货架管理----商品管理-音乐扩展属性修改</td>
  </tr>
</table>
<form name="listForm" method="post" action="queryReference.do"    enctype="multipart/form-data"  onSubmit="return resourceUpdate();" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">音乐扩展属性修改</td>
  </tr>
</table>
<input type="hidden" name="perType" value="saveMusic" />
<input type="hidden" name="musicId" value="<bean:write name="vo" property="musicId"/>" />
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
	<tr>
    <td align="right" class="text3">音乐ID：</td>
    <td class="text4"><bean:write name="vo" property="musicId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">音乐名称：</td>
    <td class="text4"><bean:write name="vo" property="musicName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">歌手名称：</td>
    <td class="text4"><bean:write name="vo" property="singer"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">有效期：</td>
    <td class="text4"><bean:write name="vo" property="validity"/></td>
  </tr>
 <logic:iterate id="vo" indexId="ind" name="keyBaseList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
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
		<td align="center" class="text5"><input name="Submit" type="submit" class="input1" value="修改"> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回"></td>
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