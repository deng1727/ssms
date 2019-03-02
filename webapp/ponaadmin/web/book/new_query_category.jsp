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
<title>M-Market�Ķ����ܹ���ϵͳ</title>
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
	window.status="���";
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
    <td align="center" class="title1">ͼ�������Ϣ</td>
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
		<textarea name="desc" rows="4" cols="50" readonly><bean:write name="categoryVO" property="decrisption"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�����Ƿ����Ż�չʾ��</td>
    <td class="text4">
    	<logic:equal value="1" name="categoryVO" property="type"> �� </logic:equal> 
    	<logic:equal value="0" name="categoryVO" property="type"> �� </logic:equal> 
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����������Ϣ��</td>
    <td class="text4">
    	<bean:write name="categoryVO" property="sortId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">��������ƽ̨�� </td>
    <td class="text4">
		<bean:write name="platFormName" />
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����������� </td>
    <td class="text4">
		<bean:write name="cityName" />
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����ͼƬ·���� </td>
    <td class="text4">
		<bean:write name="categoryVO" property="picUrl"/>
    </td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="keyBaseList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	<tr>
	   <td width="29%" align="right" class="text3"><bean:write name="vo" property="keydesc"/>��</td>
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
    <td align="right" class="text3">����״̬��</td>
    <td class="text4">
    	<logic:equal value="0" name="categoryVO" property="read_status"> �༭ </logic:equal> 
    	<logic:equal value="1" name="categoryVO" property="read_status"> �ѷ��� </logic:equal> 
    	<logic:equal value="2" name="categoryVO" property="read_status"> ������ </logic:equal> 
    	<logic:equal value="3" name="categoryVO" property="read_status"> ������ͨ��</logic:equal> 
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
        <input name="Submit" type="submit" class="input1" value="�޸�����" <% if("2".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return modCategory();">
        <input name="Submit" type="submit" class="input1" value="ɾ��" <% if("2".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return delCategory();">
        <input name="Submit" type="submit" class="input1" value="�ύ����" <% if(!"0".equals(readCategoryVO.getRead_status())){  %>disabled="disabled"<%} %> onclick="return approvalCategory();">
        
        <%} %>
    </td>
  </tr>
</table>
</body>
</html>