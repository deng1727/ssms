<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>��Դѡ��</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
<!--

function modTaxis()
{
  if(!checkLength(sortForm.taxis,"�������",-1,9999999))
    {
		return false;
	}
	var value = sortForm.taxis.value;
	if(!JudgeReal2(sortForm.taxis,"�������",9))
	{
	  return false;
	}
	
   if((value < -9999)||(value >9999))
   {
      alert("�������ֻ����-9999~9999֮�䣡");
      return false; 
   }
  
 // var url = "<%=request.getContextPath()%>/web/channelUser/cgyContentTaxis.do?action=save&refid="+id+"&cateid="+cateid;
 
  //document.location = url;
    sortForm.action="<%=request.getContextPath()%>/web/channelUser/cgyContentTaxis.do?action=save";
   
    sortForm.submit();
}
//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<form name="sortForm" action="" method="post">
<input type="hidden" name="refid" value="<bean:write name="refNode" property="id"/>">
<input type="hidden" name="cateid" value="<bean:write name="category" property="id"/>">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<bean:write name="category" property="namePath"/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">��Դ����������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<tr>
    <td width="29%" align="right" class="text3">��ǰ������ŷ�Χ��</td>
    <td width="71%" class="text4">��Сֵ��<bean:write name="minTaxis"/>|���ֵ��<bean:write name="maxTaxis"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">������ţ�</td>
    <td width="71%" class="text4"><input type="text" name="taxis" value="<bean:write name="refNode" property="sortID"/>" onKeyDown="if(event.keyCode==13) return modTaxis();"></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="ȷ��" onclick="return modTaxis();">
        <input name="button1" type="button" class="input1" value="����" onclick="javaScript:history.go(-1);">
    </td>
  </tr>
</table>
</form>
</body>
</html>
