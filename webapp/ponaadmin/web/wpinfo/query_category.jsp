<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.common.Validator" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>WP���Ӧ�û��ܹ���ϵͳ</title>
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
	var href = "parent.treeframe.location='appInfoCategory.do?perType=tree'";
	window.setTimeout(href,0);
	window.status="���";
	<%
	}
	%>
}
function addCategory()
{
	window.location.href = "appInfoCategory.do?pCategoryId=<bean:write name="categoryId" />&perType=toAdd";
}

function updateCategory()
{
	window.location.href = "appInfoCategory.do?categoryId=<bean:write name="categoryId" />&perType=toUpdate";
}

function delCategory()
{
	window.location.href = "appInfoCategory.do?perType=del&categoryId=<bean:write name="categoryId" />";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="into();">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">WP���Ӧ�û�����Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">����·���� </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">�������ƣ� </td>
    <td width="71%" class="text4"><bean:write name="appInfoCategoryVO" property="cname"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">����ID�� </td>
    <td width="71%" class="text4"><bean:write name="appInfoCategoryVO" property="categoryId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">���������� </td>
    <td class="text4">
		<textarea name="cdesc" rows="4" cols="50" readonly><bean:write name="appInfoCategoryVO" property="cdesc"/></textarea>
    </td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">����ͼƬ�� </td>
    <td width="71%" class="text4"><bean:write name="appInfoCategoryVO" property="picture"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">�����Ƿ����Ż�չʾ��</td>
    <td class="text4">
    	<logic:equal value="1" name="appInfoCategoryVO" property="isShow"> �� </logic:equal> 
    	<logic:equal value="0" name="appInfoCategoryVO" property="isShow"> �� </logic:equal> 
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����������Ϣ��</td>
    <td class="text4">
    	<bean:write name="appInfoCategoryVO" property="sortId"/>
    </td>
  </tr>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="����" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="�޸�����" onclick="return updateCategory();">
        <input name="Submit" type="submit" class="input1" value="ɾ��" onclick="return delCategory();">
    </td>
  </tr>
</table>
</body>
</html>
