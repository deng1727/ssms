<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>���������û�����Ϣ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					���������û�����Ϣ
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="categoryRuleEdit.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="right" class="text3">
						�������룺
					</td>
					<td class="text4" style="word-break:break-all;">
						<input name="categoryId" type="text" value="" />
						<input type="button" value="��ѡ��" onclick="selectCid();"/>
						<input name="button2" type="button" onClick="javascript:ContentForm.categoryId.value='';" value="���">
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�û������룺
					</td>
					<td class="text4" style="word-break:break-all;">
						<input name="code" type="text" value=""/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�û������ƣ�
					</td>
					<td class="text4">
						<input name="name" value="" type="text"/>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="text3">
						�û������ͣ�
					</td>
					<td class="text4">
						<select name="type">
							<option value="1" >���ܷ���</option>
							<option value="2" >������Ʒ</option>
						</select>
					</td>
				</tr>
				
				<tr align="center" class="text3">
					<td colspan="2">
						<input name="button2" type="button" onClick="add();" value="����">
						&nbsp;&nbsp;
						<input name="button2" type="button" onClick="returnList();"
							value="����">
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script language="javascript">
	function returnList()
	{
		window.self.location.href="../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList";
	}
   
   	function selectCid()
   	{
   		var returnv=window.showModalDialog("../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryCategoryList&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
   		if(returnv != undefined)
		{
			if(returnv!='')
			{
				if(ContentForm.categoryId.value=="")
				{
					ContentForm.categoryId.value=returnv;
				}
				else
				{
					ContentForm.categoryId.value=ContentForm.categoryId.value+","+returnv;
				}
			}
		}  
   	}
   	
	function add()
	{
		var code = ContentForm.code.value;
		var categoryId = ContentForm.categoryId.value;
		var name = ContentForm.name.value;
		
		if(categoryId=="")
		{
			alert("�Բ����������������");
			ContentForm.categoryId.focus();
			return false;
		}
		if(code=="")
		{
			alert("�Բ����������û�������");
			ContentForm.code.focus();
			return false;
		}
		if(name=="")
		{
			alert("�Բ����������û�������");
			ContentForm.name.focus();
			return false;
		}
		
		ContentForm.action="../singlecategory/singleCategoryUserGroupAction.do?opType=doAdd";
		ContentForm.submit();
	}
</script>
</html>
