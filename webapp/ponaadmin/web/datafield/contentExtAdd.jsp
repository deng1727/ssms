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
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã����ܹ�������----Ӧ�û��������</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">Ӧ�û��������</td>
  </tr>
</table>
<form name="contentForm" action="contentExtSave.do" method="post" onsubmit="return checkForm()">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<input type="hidden" name="mobilePrice" value="">
	<tr>
    <td width="35%" align="right" class="text3">����ID��</td>
    <td width="65%" class="text4"><input name="contentID" type="text" value="" size="35" ><input name="buttonAdd" type="button" class="input1"  onClick="contentExtAddQuery();"  value="��ѯ������Դ"></td>
  </tr>
  <tr>
    <td align="right" class="text3">�������ƣ�</td>
    <td class="text4"><input name="name" type="text" value="" size="35" ></td>
  </tr>
  <tr>
    <td align="right" class="text3">�ṩ�̣�</td>
    <td class="text4"><input name="spName" type="text" value="" size="35" ></td>
  </tr>
  <tr>
	<td align="right" class="text3">��ҵ���룺</td>
    <td class="text4"><input name="icpcode" type="text" value="" size="35" ></td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������ͣ�</td>
    <td class="text4"><select style="width=100pt"  name="type"  id="type" onchange="reSetOption(this.options[this.options.selectedIndex].value);" >
		<!-- 
		<option value="1">�ۿ�</option>
		<option value="2">��ɱ</option>
		<option value="3">��ʱ���</option>
		-->
		<option value="4">��Լ����</option>
	</select></td>
  </tr>
  <tr id="tuanDiv" >
    <td id="timeName" align="right" class="text3">����ڣ�</td>
    <td class="text4">�� <input name="dateStart" class="Wdate" type="text" id="dateStart" style="width:180px" onFocus="new WdatePicker(this,'%Y-%M-%D',false)" MINDATE="#Year#-#Month#-{#Day#+1}"  MAXDATE="#F{$('dateEnd').value}"   value="<%=dateStart%>" readonly="true" /> �� <input name="dateEnd" id="dateEnd"  type="text"   class="Wdate" value="<%=dateEnd%>"   style="width:180px;cursor:hand;"  onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  MINDATE="#F{$('dateStart').value}"  /></td>
  </tr>
  <!-- 
  <tr id="miaoDiv" >
    <td align="right" class="text3">��ɱ���ڣ�</td>
    <td class="text4">�� <input name="dateStartM" id="dateStartM" type="text"  class="Wdate" value='<%=DateUtil.getSpecifyDate(1)%>'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  /> �� <input name="dateEndM" id="dateEndM" type="text"  class="Wdate"  value='<%=DateUtil.getSpecifyDate(1)%>'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"   /></td>
  </tr>
  
   <tr id="timeDiv" >
    <td align="right" class="text3">ʱ�䣺</td>
    <td class="text4">�� <input name="timeStart" id="timeStart" type="text" class="Wdate"  value="09:00:00"  style="width:100px;cursor:hand;"" onFocus="new WdatePicker(this,'%h:%m:%s',true)"   /> �� <input name="timeEnd" id="timeEnd" type="text"  class="Wdate" value="11:00:00"  style="width:100px;cursor:hand;"" onFocus="new WdatePicker(this,'%h:%m:%s',true)"   />
    </td>
  </tr>
  -->
  <input name="timeStart" id="timeStart" type="hidden" class="Wdate"  value="00:00:00" />
  <input name="timeEnd" id="timeEnd" type="hidden"  class="Wdate" value="23:59:59" />
  
	<tr>
    <td align="right" class="text3">ԭ�ۣ�</td>
    <td class="text4"><input name="mobilePriceStr" type="text" value="" size="35" >.Ԫ</td>
  </tr>
    <tr>
    <td align="right" class="text3">�ۿ��ʣ�</td>
    <td class="text4">
     
    <input name="discount" type="hidden" value="0" size="35" >
    
    0
    <font color="red" > �ۿ��ʱ�����0-100������</font></td>
  </tr>
	<tr>
    <td align="right" class="text3">�Ƿ��Ƽ���</td>
    <td class="text4"><input name="isrecomm" type="radio" value="0" checked="checked" />���Ƽ�<input type="radio" name="isrecomm" value="1" />�Ƽ�</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><input type="submit" class="input1" value="����"> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="����"></td>
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
	//��ʼʱ�䲻��ͬʱΪ��
  if(contentForm.contentID.value=="")
  {
    alert("���ѯ������Դ��ѡ��");
    return false;
   }
 
	var tValue=getTypeValue();
	if(tValue==1 || tValue==3)
	{	//�Ź� ��ʼʱ�䲻��ͬʱΪ��
  		if(trim(contentForm.dateStart.value)=="")
  		{
    		alert("��ʼ���ڲ���Ϊ�գ�");
    		return false;
  		}
  		if(trim(contentForm.dateEnd.value)=="")
  		{
  			alert("�������ڲ���Ϊ�գ�");
    		return false;
  		}
  		//��ʼʱ�䲻�ܴ��ڽ���ʱ��
		if(trim(contentForm.dateEnd.value)!="" && (trim(contentForm.dateStart.value) >= trim(contentForm.dateEnd.value)))
  		{
    		alert("�������ڲ���С�ڻ���ڿ�ʼ���ڣ�");
    		return false;
  		}
		
	}
	if(tValue==2)
	{	//��ɱ ��ʼʱ�䲻��ͬʱΪ��
  		if(trim(contentForm.dateStartM.value)=="")
  		{
    		alert("��ʼ���ڲ���Ϊ�գ�");
    		return false;
  		}
  		if(trim(contentForm.dateEndM.value)=="")
  		{
  			alert("�������ڲ���Ϊ�գ�");
    		return false;
  		}
  		//��ʼʱ�䲻�ܴ��ڽ���ʱ��
		if(trim(contentForm.dateEndM.value)!="" && (trim(contentForm.dateStartM.value) >= trim(contentForm.dateEndM.value)))
  		{
    		alert("�������ڲ���С�ڻ���ڿ�ʼ���ڣ�");
    		return false;
  		}
  		//��ɱ ��ʼʱ�䲻��ͬʱΪ��
  		if(trim(contentForm.timeStart.value)=="")
  		{
    		alert("��ʼʱ�䲻��Ϊ�գ�");
    		return false;
  		}
  		if(trim(contentForm.timeEnd.value)=="")
  		{
  			alert("����ʱ�䲻��Ϊ�գ�");
    		return false;
  		}
  		//��ʼʱ�䲻�ܴ��ڽ���ʱ��
		if(trim(contentForm.timeEnd.value)!="" && (trim(contentForm.timeStart.value) >= trim(contentForm.timeEnd.value)))
  		{
    		alert("����ʱ�䲻��С�ڻ����ʱ�����ڣ�");
    		return false;
  		}
		
	}
	contentForm.dateStart.disabled = false;
	contentForm.dateEnd.disabled = false;
	contentForm.timeStart.disabled = false;
	contentForm.timeEnd.disabled = false;
	contentForm.dateStartM.disabled = false;
	contentForm.dateEndM.disabled = false;
	
  //�ۿ���
  if(trim(contentForm.discount.value)=="")
  {
    alert("�ۿ��ʲ���Ϊ�գ�");
    contentForm.discount.focus();
    return false;
  }
  var disStr=contentForm.discount.value;
  var pattern=/\D+/;
  if(pattern.test(disStr))
	{
      alert("�ۿ��ʵ�ֵ��������������");
      contentForm.discount.focus();
      return false;
    }
    if(disStr>100||disStr<0)
    {
      alert("�ۿ��ʵ�ֵ������0��100֮�䣡");
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
	{//�Ź�
	document.getElementById("timeName").innerHTML="�ۿ����ڣ�";
	document.getElementById("timeDiv").style.display="none";
	document.getElementById("miaoDiv").style.display="none";
	document.getElementById("tuanDiv").style.display="block";	
	contentForm.discount.value="";
	contentForm.discount.readOnly="";
	}
	if(str==2)
	{//��ɱ
	document.getElementById("timeDiv").style.display="block";
	document.getElementById("miaoDiv").style.display="block";
	document.getElementById("tuanDiv").style.display="none";
	contentForm.discount.value="";
	contentForm.discount.readOnly="";
	}
	if(str==3)
	{//��ʱ���
	document.getElementById("timeName").innerHTML="��ʱ������ڣ�";
	document.getElementById("timeDiv").style.display="none";
	document.getElementById("miaoDiv").style.display="none";
	document.getElementById("tuanDiv").style.display="block";
	contentForm.discount.value="0";
	contentForm.discount.readOnly=true;
	}
}

		
	


reSetOption(getTypeValue());

</script>