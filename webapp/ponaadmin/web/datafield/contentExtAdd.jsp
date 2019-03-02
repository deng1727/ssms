<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@page import="com.aspire.ponaadmin.web.util.DateUtil" %>
<%
String dateStart = DateUtil.getSpecifyDate(1)+" 00:00:00";
String dateEnd = DateUtil.getSpecifyDate(1)+" 23:59:59";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----应用活动属性新增</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">应用活动属性新增</td>
  </tr>
</table>
<form name="contentForm" action="contentExtSave.do" method="post" onsubmit="return checkForm()">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<input type="hidden" name="mobilePrice" value="">
	<tr>
    <td width="35%" align="right" class="text3">内容ID：</td>
    <td width="65%" class="text4"><input name="contentID" type="text" value="" size="35" ><input name="buttonAdd" type="button" class="input1"  onClick="contentExtAddQuery();"  value="查询内容资源"></td>
  </tr>
  <tr>
    <td align="right" class="text3">内容名称：</td>
    <td class="text4"><input name="name" type="text" value="" size="35" ></td>
  </tr>
  <tr>
    <td align="right" class="text3">提供商：</td>
    <td class="text4"><input name="spName" type="text" value="" size="35" ></td>
  </tr>
  <tr>
	<td align="right" class="text3">企业代码：</td>
    <td class="text4"><input name="icpcode" type="text" value="" size="35" ></td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容类型：</td>
    <td class="text4"><select style="width=100pt"  name="type"  id="type" onchange="reSetOption(this.options[this.options.selectedIndex].value);" >
		<!-- 
		<option value="1">折扣</option>
		<option value="2">秒杀</option>
		<option value="3">限时免费</option>
		-->
		<option value="4">特约限免</option>
	</select></td>
  </tr>
  <tr id="tuanDiv" >
    <td id="timeName" align="right" class="text3">活动日期：</td>
    <td class="text4">从 <input name="dateStart" class="Wdate" type="text" id="dateStart" style="width:180px" onFocus="new WdatePicker(this,'%Y-%M-%D',false)" MINDATE="#Year#-#Month#-{#Day#+1}"  MAXDATE="#F{$('dateEnd').value}"   value="<%=dateStart%>" readonly="true" /> 到 <input name="dateEnd" id="dateEnd"  type="text"   class="Wdate" value="<%=dateEnd%>"   style="width:180px;cursor:hand;"  onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  MINDATE="#F{$('dateStart').value}"  /></td>
  </tr>
  <!-- 
  <tr id="miaoDiv" >
    <td align="right" class="text3">秒杀日期：</td>
    <td class="text4">从 <input name="dateStartM" id="dateStartM" type="text"  class="Wdate" value='<%=DateUtil.getSpecifyDate(1)%>'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  /> 到 <input name="dateEndM" id="dateEndM" type="text"  class="Wdate"  value='<%=DateUtil.getSpecifyDate(1)%>'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"   /></td>
  </tr>
  
   <tr id="timeDiv" >
    <td align="right" class="text3">时间：</td>
    <td class="text4">从 <input name="timeStart" id="timeStart" type="text" class="Wdate"  value="09:00:00"  style="width:100px;cursor:hand;"" onFocus="new WdatePicker(this,'%h:%m:%s',true)"   /> 到 <input name="timeEnd" id="timeEnd" type="text"  class="Wdate" value="11:00:00"  style="width:100px;cursor:hand;"" onFocus="new WdatePicker(this,'%h:%m:%s',true)"   />
    </td>
  </tr>
  -->
  <input name="timeStart" id="timeStart" type="hidden" class="Wdate"  value="00:00:00" />
  <input name="timeEnd" id="timeEnd" type="hidden"  class="Wdate" value="23:59:59" />
  
	<tr>
    <td align="right" class="text3">原价：</td>
    <td class="text4"><input name="mobilePriceStr" type="text" value="" size="35" >.元</td>
  </tr>
    <tr>
    <td align="right" class="text3">折扣率：</td>
    <td class="text4">
     
    <input name="discount" type="hidden" value="0" size="35" >
    
    0
    <font color="red" > 折扣率必须是0-100的整数</font></td>
  </tr>
	<tr>
    <td align="right" class="text3">是否推荐：</td>
    <td class="text4"><input name="isrecomm" type="radio" value="0" checked="checked" />不推荐<input type="radio" name="isrecomm" value="1" />推荐</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><input type="submit" class="input1" value="保存"> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回"></td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
function contentExtAddQuery()
{                               
	var retvar=window.showModalDialog("contentExtAddQuery.do","modal","center:yes;dialogWidth:700px;dialogHeight:800px;resizable: yes; help: no; status: yes; scroll: auto; ");
	if(retvar!=undefined)
	{
		ret=retvar.split("|");   
		contentForm.contentID.value=ret[0];
		contentForm.name.value=ret[1];
		contentForm.spName.value=ret[2];
		contentForm.icpcode.value=ret[3];
		contentForm.mobilePrice.value=ret[4];
		contentForm.mobilePriceStr.value=ret[5];
	}
}

function checkForm()
{
	//开始时间不能同时为空
  if(contentForm.contentID.value=="")
  {
    alert("请查询内容资源并选择！");
    return false;
   }
 
	var tValue=getTypeValue();
	if(tValue==1 || tValue==3)
	{	//团购 开始时间不能同时为空
  		if(trim(contentForm.dateStart.value)=="")
  		{
    		alert("开始日期不能为空！");
    		return false;
  		}
  		if(trim(contentForm.dateEnd.value)=="")
  		{
  			alert("结束日期不能为空！");
    		return false;
  		}
  		//开始时间不能大于结束时间
		if(trim(contentForm.dateEnd.value)!="" && (trim(contentForm.dateStart.value) >= trim(contentForm.dateEnd.value)))
  		{
    		alert("结束日期不能小于或等于开始日期！");
    		return false;
  		}
		
	}
	if(tValue==2)
	{	//秒杀 开始时间不能同时为空
  		if(trim(contentForm.dateStartM.value)=="")
  		{
    		alert("开始日期不能为空！");
    		return false;
  		}
  		if(trim(contentForm.dateEndM.value)=="")
  		{
  			alert("结束日期不能为空！");
    		return false;
  		}
  		//开始时间不能大于结束时间
		if(trim(contentForm.dateEndM.value)!="" && (trim(contentForm.dateStartM.value) >= trim(contentForm.dateEndM.value)))
  		{
    		alert("结束日期不能小于或等于开始日期！");
    		return false;
  		}
  		//秒杀 开始时间不能同时为空
  		if(trim(contentForm.timeStart.value)=="")
  		{
    		alert("开始时间不能为空！");
    		return false;
  		}
  		if(trim(contentForm.timeEnd.value)=="")
  		{
  			alert("结束时间不能为空！");
    		return false;
  		}
  		//开始时间不能大于结束时间
		if(trim(contentForm.timeEnd.value)!="" && (trim(contentForm.timeStart.value) >= trim(contentForm.timeEnd.value)))
  		{
    		alert("结束时间不能小于或等于时间日期！");
    		return false;
  		}
		
	}
	contentForm.dateStart.disabled = false;
	contentForm.dateEnd.disabled = false;
	contentForm.timeStart.disabled = false;
	contentForm.timeEnd.disabled = false;
	contentForm.dateStartM.disabled = false;
	contentForm.dateEndM.disabled = false;
	
  //折扣率
  if(trim(contentForm.discount.value)=="")
  {
    alert("折扣率不能为空！");
    contentForm.discount.focus();
    return false;
  }
  var disStr=contentForm.discount.value;
  var pattern=/\D+/;
  if(pattern.test(disStr))
	{
      alert("折扣率的值必须是正整数！");
      contentForm.discount.focus();
      return false;
    }
    if(disStr>100||disStr<0)
    {
      alert("折扣率的值必须在0至100之间！");
      contentForm.discount.focus();
      return false;
    }

	if(tValue==1 || tValue==3||tValue==4)
	{
		var timeobj=contentForm.dateStart.value.split(" ");
		contentForm.dateStart.value=timeobj[0];
		contentForm.timeStart.value=timeobj[1];
		var timeobj=contentForm.dateEnd.value.split(" ");
		contentForm.dateEnd.value=timeobj[0];
		contentForm.timeEnd.value=timeobj[1];
	}
	if(tValue==2)
	{
		contentForm.dateStart.value=contentForm.dateStartM.value;
		contentForm.dateEnd.value=contentForm.dateEndM.value;
	}
  return true;
}
function getTypeValue()
{
	typeobj=document.getElementById("type");
	var tValue=typeobj.options[typeobj.options.selectedIndex].value;
	return tValue;
}

function reSetOption(str)
{
	if(str==1)
	{//团购
	document.getElementById("timeName").innerHTML="折扣日期：";
	document.getElementById("timeDiv").style.display="none";
	document.getElementById("miaoDiv").style.display="none";
	document.getElementById("tuanDiv").style.display="block";	
	contentForm.discount.value="";
	contentForm.discount.readOnly="";
	}
	if(str==2)
	{//秒杀
	document.getElementById("timeDiv").style.display="block";
	document.getElementById("miaoDiv").style.display="block";
	document.getElementById("tuanDiv").style.display="none";
	contentForm.discount.value="";
	contentForm.discount.readOnly="";
	}
	if(str==3)
	{//限时免费
	document.getElementById("timeName").innerHTML="限时免费日期：";
	document.getElementById("timeDiv").style.display="none";
	document.getElementById("miaoDiv").style.display="none";
	document.getElementById("tuanDiv").style.display="block";
	contentForm.discount.value="0";
	contentForm.discount.readOnly=true;
	}
}

		
	


reSetOption(getTypeValue());

</script>