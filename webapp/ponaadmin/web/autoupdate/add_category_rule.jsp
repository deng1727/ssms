<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryRuleVO"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�������ܲ��Թ�����Ϣ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					���ܲ��Թ�����ϸ��Ϣ
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
						<input name="id" type="text" value="" 
						onkeydown="if(event.keyCode==13) event.keyCode=9"
						onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="��ѡ��" onclick="selectCid();"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						����id��
					</td>
					<td class="text4" style="word-break:break-all;">
						<input name="ruleId" type="text"
							value=""
							onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" maxlength="8"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�����ϱ��������Чʱ�䣺
					</td>
					<td class="text4">
						<input name="effectiveTime" class="Wdate" type="text"
							id="textbox2" style="width:170px"
							onFocus="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true)"
							readonly="true" />
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
		window.self.location.href="categoryRuleList.do";
	}
   
   	function selectCid()
   	{
   		var returnv=window.showModalDialog("categoryRuleIdQuery.do?isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
   		if(returnv != undefined)
		{
			ContentForm.id.value=returnv;
		}  
   	}
   	
	function add()
	{
		var id = ContentForm.id.value;
		var ruleId = ContentForm.ruleId.value;
		var time = ContentForm.effectiveTime.value;
		
		if(id=="")
		{
			alert("�Բ����������������");
			ContentForm.id.focus();
			return false;
		}
		if(ruleId=="")
		{
			alert("�Բ���������������");
			ContentForm.ruleId.focus();
			return false;
		}
		if(time=="")
		{
			alert("�Բ�������д��Ч����");
			ContentForm.effectiveTime.focus();
			return false;
		}
		
		ContentForm.action="categoryRuleAdd.do?action=add";
		ContentForm.submit();
	}
</script>
</html>
