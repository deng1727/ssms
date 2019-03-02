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
<title>人工干预容器信息</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">人工干预容器信息</td>
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
    	当前容器:
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="name" value="" />
    </td>
    <td width="50%" align="center" class="text4"> 
    	<input type="button" value="保存该容器" onclick="saveIntervenor();">  
    </td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	设定容器排名位置:
    </td>
    <td class="text4" colspan="2">
    	<select name="isselect" onchange="selectSort();">
    		<option value="0">手动输入</option>
    		<option value="-1">至榜单顶部</option>
    		<option value="-2">至榜单底部</option>
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
    	设置容器释放时间期限:
    </td>
    <td class="text4" colspan="2">
    	<input name="startDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value=""
			readonly="true" />
			&nbsp;至&nbsp;
		<input name="endDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value=""
			readonly="true" />
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	加入商品至容器:
    </td>
    <td colspan="2" class="text4">
    	<input type="button" value="从商品目录中提取" onclick="addContentId();">
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
	alert("在加入商品至容器前，请保存当前容器基本信息");
	return false;
}

function saveIntervenor()
{
	var name = ContentForm.name.value;
	var sdate = ContentForm.startDate.value;
	var edate = ContentForm.endDate.value;
	
	if(getLength(name)>30)
	{
		alert("容器名称不可以超过30位。请修改！");
		ContentForm.name.focus();
		return false;
	}
	
	if(trim(name)=='')
	{
		alert("容器名称不可以为空");
		ContentForm.name.focus();
		return false;
	}
	
	var reg = /^[\w\-\u4e00-\u9fa5]+$/g
	
    if(!reg.test(name))
    {
		alert("容器名称只能包含汉字、英文字母、数字、横杠、下划线！请重新输入！");
		ContentForm.name.focus();
		return false;
    }
	

	var iss = ContentForm.isselect.value;
	
	if(iss==0)
	{
		if(ContentForm.sortId.value == '')
		{
			alert("设定容器排名位置中的固定位置不可以为空");
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
		alert("容器释放时间期限开始时间不可以为空");
		ContentForm.startDate.focus();
		return false;
	}
	
	if(edate=='')
	{
		alert("容器释放时间期限结束时间不可以为空");
		ContentForm.endDate.focus();
		return false;
	}
	
	if(edate < sdate)
	{
		alert("容器释放时间期限开始时间不能大于结束时间");
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
