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
    <td>所在位置：货架管理中心----扩展属性新增</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">扩展属性新增</td>
  </tr>
</table>
<form name="keybaseForm" action="keyBaseInsert.do" method="post">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
    <td width="35%" align="right" class="text3">表名：</td>
    <td width="65%" class="text4"><SELECT name="keytable" id="keytable"></SELECT></td>
  </tr>
  <tr>
    <td align="right" class="text3">字段名：</td>
    <td class="text4"><input name="keyname" type="text" value="" size="50"></td>
  </tr>
  <tr>
    <td align="right" class="text3">字段描述：</td>
    <td class="text4"><input name="keydesc" type="text" value="" size="50"></td>
  </tr>  
 <tr>
    <td align="right" class="text3">字段描述：</td>
    <td class="text4"><select  name="keytype">
		<option value="1">普通文本</option>
		<option value="2">图片域</option>
		<option value="3">大文本</option>
	</select></td>
  </tr>  
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><input name="Submit" type="submit" class="input1" value="新增"> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回"></td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
var keytableObj=document.getElementById("keytable");
var keytableStr="";
var keytableArr= new Array(); 

<logic:iterate id="keytablename" indexId="ind" name="keytableArr" >
keytableStr='<bean:write name="keytablename"/>';
keytableArr=keytableStr.split('|');
keytableObj.options.add(new Option(keytableArr[1],keytableArr[0]));
</logic:iterate>



 
//-->
</script>