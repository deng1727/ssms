<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">�˹���Ԥ������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="intervenorView.do" method="post">
  <input type="hidden" name="id" value="" />
  <input type="hidden" name="contentId" value="" />
  <input type="hidden" name="actionType" value="" />
  <input type="hidden" name="startSortId" value="" />
  <input type="hidden" name="endSortId" value="" />
  <tr>
    <td width="20%" align="right" class="text3">
    	��ǰ����:
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="name" value="" />
    </td>
    <td width="50%" align="center" class="text4"> 
    	<input type="button" value="���������" onclick="saveIntervenor();">  
    </td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	�趨��������λ��:
    </td>
    <td class="text4" colspan="2">
    	<select name="isselect" onchange="selectSort();">
    		<option value="0">�ֶ�����</option>
    		<option value="-1">���񵥶���</option>
    		<option value="-2">���񵥵ײ�</option>
    	</select>
    	&nbsp;
		<input type="text" name="sortId" 
		value=""
		onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false">
		&nbsp;&nbsp;&nbsp;
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	���������ͷ�ʱ������:
    </td>
    <td class="text4" colspan="2">
    	<input name="startDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value=""
			readonly="true" />
			&nbsp;��&nbsp;
		<input name="endDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value=""
			readonly="true" />
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	������Ʒ������:
    </td>
    <td colspan="2" class="text4">
    	<input type="button" value="����ƷĿ¼����ȡ" onclick="addContentId();">
	</td>
  </tr>
  </form>
</table>
<br>
<br>
<script language="javascript">
function selectSort()
{
	var iss = ContentForm.isselect.value;
	
	if(iss!=0)
	{
		ContentForm.sortId.style.display="none";
	}
	else
	{
		ContentForm.sortId.style.display="";
		ContentForm.sortId.value='1';
	}
}

function addContentId()
{
	alert("�ڼ�����Ʒ������ǰ���뱣�浱ǰ����������Ϣ");
	return false;
}

function saveIntervenor()
{
	var name = ContentForm.name.value;
	var sdate = ContentForm.startDate.value;
	var edate = ContentForm.endDate.value;
	
	if(getLength(name)>30)
	{
		alert("�������Ʋ����Գ���30λ�����޸ģ�");
		ContentForm.name.focus();
		return false;
	}
	
	if(trim(name)=='')
	{
		alert("�������Ʋ�����Ϊ��");
		ContentForm.name.focus();
		return false;
	}
	
	var reg = /^[\w\-\u4e00-\u9fa5]+$/g
	
    if(!reg.test(name))
    {
		alert("��������ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߣ����������룡");
		ContentForm.name.focus();
		return false;
    }
	

	var iss = ContentForm.isselect.value;
	
	if(iss==0)
	{
		if(ContentForm.sortId.value == '')
		{
			alert("�趨��������λ���еĹ̶�λ�ò�����Ϊ��");
			ContentForm.sortId.focus();
			return false;
		}
		ContentForm.startSortId.value = ContentForm.sortId.value;
		ContentForm.endSortId.value = ContentForm.sortId.value;
	}
	else
	{
		ContentForm.startSortId.value = iss;
		ContentForm.endSortId.value = iss;
	}

	if(sdate=='')
	{
		alert("�����ͷ�ʱ�����޿�ʼʱ�䲻����Ϊ��");
		ContentForm.startDate.focus();
		return false;
	}
	
	if(edate=='')
	{
		alert("�����ͷ�ʱ�����޽���ʱ�䲻����Ϊ��");
		ContentForm.endDate.focus();
		return false;
	}
	
	if(edate < sdate)
	{
		alert("�����ͷ�ʱ�����޿�ʼʱ�䲻�ܴ��ڽ���ʱ��");
		ContentForm.startDate.focus();
		return false;
	}
	
	ContentForm.actionType.value = 'add';
	ContentForm.action = 'intervenorAdd.do';
	ContentForm.submit();
}

</script>
</body>
</html>
