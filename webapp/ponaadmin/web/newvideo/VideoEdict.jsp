<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
	//com.aspire.dotcard.basevideosync.vo.ProgramVO vo = request.getAttribute("vo");
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market��Ƶ���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�������Ƶ����----POMS��Ʒ����-��Ŀ����</td>
  </tr>
</table>
<form name="listForm" method="post" action="queryReference.do"    enctype="multipart/form-data"  onSubmit="return resourceUpdate();" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">��Ŀ����</td>
  </tr>
</table>
<input type="hidden" name="perType" value="saveVideo" />
<input type="hidden" name="videoId" value="603986258" />
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td width="25%" align="right" class="text3">��ĿID��</td>
    <td class="text4"><bean:write name="vo" property="programId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">��Ŀ���ƣ�</td>
    <td class="text4"><bean:write name="vo" property="name"/></td>
  </tr>
   <tr>
    <td align="right" class="text3">���ߣ�</td>
    <td class="text4"><bean:write name="vo" property="vAuthor"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">��Ŀ��飺</td>
    <td class="text4"><textarea rows="" cols=""><bean:write name="vo" property="vShortName"/></textarea> </td>
  </tr>
   
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5">
		<!--<td align="center" class="text5"><input name="Submit" type="submit" class="input1" value="�޸�">
		--><input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="����">
		</td>
	</tr>
</table>
</form>
<script language="JavaScript"> 
<!--
function resourceUpdate()
{
  if(confirm("��ȷ��Ҫ�����޸Ĳ�����"))
  {
    listForm.submit();
  }
  else
  {
  	return false;	
  }
}
//-->
</script>
</body>
</html>
