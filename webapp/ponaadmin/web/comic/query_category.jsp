<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.comic.vo.CategoryVO" %>
<% CategoryVO categoryVO = (CategoryVO)request.getAttribute("categoryVO"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
function addCategory()
{
	window.location.href = "categoryTree.do?parentCategoryId=<bean:write name="categoryVO" property="categoryId"/>&method=add";
}

function modCategory()
{
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryVO" property="categoryId"/>&method=mod";
}

function delCategory()
{
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryVO" property="categoryId"/>&method=del";
}

function approvalCategory(){
	window.location.href = "categoryTree.do?categoryId=<bean:write name="categoryVO" property="categoryId"/>&method=approval";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">����������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">����·���� </td>
    <td width="71%" class="text4"><bean:write name="path"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">�������ƣ� </td>
    <td width="71%" class="text4"><bean:write name="categoryVO" property="categoryName"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">����ID�� </td>
    <td width="71%" class="text4"><bean:write name="categoryVO" property="categoryId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">���������� </td>
    <td class="text4">
		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="categoryVO" property="categoryDesc"/></textarea>
    </td>
  </tr>
   <tr>
    <td width="29%" align="right" class="text3">����ͼƬ�� </td>
    <td width="71%" class="text4"><bean:write name="categoryVO" property="picture"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">��������</td>
    <td class="text4">
		<bean:write name="categoryVO" property="sortId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�Ƿ����Ż�չʾ��</td>
    <td class="text4">
    	<logic:equal value="0" name="categoryVO" property="delFlag"> �� </logic:equal> 
    	<logic:equal value="1" name="categoryVO" property="delFlag"> �� </logic:equal> 
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����״̬��</td>
    <td class="text4">
    	<logic:equal value="0" name="categoryVO" property="anime_status"> �༭ </logic:equal> 
    	<logic:equal value="1" name="categoryVO" property="anime_status"> �ѷ��� </logic:equal> 
    	<logic:equal value="2" name="categoryVO" property="anime_status"> ������ </logic:equal> 
    	<logic:equal value="3" name="categoryVO" property="anime_status"> ������ͨ��</logic:equal> 
    </td>
  </tr>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <% if(request.getParameter("operation") != null && !"".equals(request.getParameter("operation")) && "1".equals(request.getParameter("operation"))){ %>
        <input name="button1" type="button" class="input1" value="�ر�" onclick="window.close();">
        <%}else{ %>
        <input name="button1" type="button" class="input1" value="����" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="�޸�����" <% if("2".equals(categoryVO.getAnime_status())){  %>disabled="disabled"<%} %> onclick="return modCategory();">
        <input name="Submit" type="submit" class="input1" value="ɾ��" <% if("2".equals(categoryVO.getAnime_status())){  %>disabled="disabled"<%} %> onclick="return delCategory();">
        <input name="Submit" type="submit" class="input1" value="�ύ����" <% if(!"0".equals(categoryVO.getAnime_status())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
         <%} %>
    </td>
  </tr>
</table>
</body>
</html>
