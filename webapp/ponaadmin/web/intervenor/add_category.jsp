<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.repository.Category"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv=X-UA-Compatible content=IE=EmulateIE8>
<title>�˹���Ԥ����</title>

<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">�񵥹�����Ϣ</td>
	  </tr>
	</table>
<form name="ContentForm" action="categoryList.do" method="post">
	<input name="actionType" type="hidden" value="relatingView"/>
	<input id="id" name='id' type="hidden" value=""/>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr>
			<td width="10%" align="right" class="text3">
			  	ѡ��񵥣�
			</td>
			<td width="40%"  class="text4" >
				<input id="cateBut" type="button" value="�ӻ���Ŀ¼����ȡ" onclick="addCategory();">
			</td>
			<td width="10%" align="right" class="text3">
			  	���������
			</td>
			<td width="40%" class="text4">  
				<input type="button" value="������Ŀ¼����ȡ" onclick="addIntervenorId();">
			</td>
		</tr>
		<tr>
			<td width="10%" align="right" class="text3">
			  	ѡ��񵥣�
			</td>
			<td width="40%"  class="text4" colspan="3">
				<select name='androidCategoryId' id='androidCategoryId'>
					<option value="rank_new_appSoftWare">�������</option>
					<option value="rank_hot_appSoftWare">�������</option>
					<option value="rank_scores_appSoftWare">������</option>
					<option value="rank_new_appGame">��Ϸ����</option>
					<option value="rank_hot_appGame">��Ϸ����</option>
					<option value="rank_scores_appGame">��Ϸ���</option>
					
					<option value="rank_fee_appAllPricePay">�շѰ�</option>
					<option value="rank_fee_appAllPriceFree">��Ѱ�</option>
					<option value="rank_scores_appAll">ȫ�������</option>
					<option value="rank_all_appAll">ȫ�����ܰ�</option>
					<option value="rank_new_appAll">ȫ��������</option>
				</select>
				<input type="button" value="����Ʒ�����Ŀ¼����ȡ" onclick="addAndroidCategory();">
			</td>
		</tr>
	</table>
</form>	
<br>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">����Ӹ�Ԥ������</font></td>
      </tr>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
    	<input type="button" value="����" onclick="back();">
    </td>
  </tr>
</table>

<script language="javascript">
function back()
{
	ContentForm.action = "categoryList.do?actionType=listCategory&id=";
	ContentForm.submit();
}

function addIntervenorId()
{
	var categoryId = document.getElementById('id').value;
	
	if(categoryId=='')
	{
		alert("����ѡ��񵥣�");
		return false;
	}
	
	var ids=categoryId;
	var returnv=window.showModalDialog("categoryAddIntervenor.do?actionType=addIntervenor&isFirst=1", "","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300")
	if(returnv != undefined)
	{
		var id = returnv;
		ContentForm.action = "categoryRelating.do?actionType=relating&type=2&intervenorId=" + id + "&categoryId=" + ids;
		ContentForm.submit();
	}
}

function addCategory()
{
	var returnv=window.showModalDialog("../autoupdate/categoryRuleIdQuery.do?isFirst=1", "new","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300");
	if(returnv != undefined && returnv != '')
	{
		ContentForm.action = "categoryRelatingView2.do?actionType=relatingView2&id="+returnv;
		ContentForm.submit();
	}
}

function addAndroidCategory()
{
	document.getElementById('id').value=document.getElementById('androidCategoryId').value;
	document.getElementById('cateBut').disabled = true;
	alert("��ǰ����Ϊ��Ʒ����ܸ�Ԥ��ϵ������");
}

</script>
</body>
</html>
