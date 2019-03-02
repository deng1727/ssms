<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>����������</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function checkForm(form)
{
  var reg = /^[1-9][0-9]+$/g;
  var cooperationId = form1.cooperationId.value;
  var cooperationName = form1.cooperationName.value;
  if(cooperationId == null || cooperationId == ''){
	  alert("������ID����Ϊ��");
	  return false;
  }
  if(!reg.test(cooperationId) || getLength(cooperationId)< 4 || getLength(cooperationId) > 20){
	  alert("������ID���ȱ�����4-20λ������ֻ�ܰ������֣�");
	  return false; 
  }
  if(cooperationName == null || cooperationName == ''){
	  alert("���������Ʋ���Ϊ��");
	  return false;
  }
  var bool = true;
  var cooperationType = document.getElementsByName("cooperationType");
  for(var i = 0;i < cooperationType.length;i++){
		if(cooperationType[i].checked  == true){
			bool = false;
		}
	}
	
	if(bool){
		alert("��ѡ�������ʽ");
		return false;
	}
  return true;
}

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="form1" method="post" action="cooperationList.do" onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<input name="method" type="hidden" id="method" value="save">
	  <tr>
	    <td align="center" class="title1">
	            ����������
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <font color="#ff0000">(*)</font>������ID��
	    </td>
	    <td width="75%" class="text4"><input name="cooperationId" type="text" size="20" value="">
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	<font color="#ff0000">(*)</font>���������ƣ�
	    </td>
	    <td class="text4"><input name="cooperationName" type="text" value="">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	<font color="#ff0000">(*)</font>������ʽ��
	    </td>
	    <td class="text4">
	      <label><input name="cooperationType" type="checkbox" value="1" />���ݺ��� </label> 
          <label><input name="cooperationType" type="checkbox" value="2" />��������</label> 
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	<font color="#ff0000">(*)</font>����������
	    </td>
	    <td class="text4">
	     <select name="channelNumber">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
						</select>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="����">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
