<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.common.Validator" %>
<%
  String userID = Validator.filter(request.getParameter("userID"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
function checkForm()
{
  //����û��ʺ�
  var desc = checkUserForm.desc.value;
  if(trim(desc)=="" || !checkIllegalChar(desc,"'\"<>\\/")
        || getLength(trim(desc))>250)
  {
    alert("��ͨ��ԭ�������д�����ܰ��� ' \" < > \\ /  ���ַ���\n\n���ҳ��ȱ���С��250���ַ���һ������ռ�����ַ�����");
    checkUserForm.desc.focus();
    return false;
  }
  return true;
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" bgcolor="#FFFFFF">
<form name="checkUserForm" action="checkUser.do" method="post" onSubmit="return checkForm();">
  <tr class="title1">
    <td align="center"><FONT class=large>�û�����----�û����</FONT></td>
  </tr>
  <tr class="title1" align="center">
      <td width="16%">��˵���û�<%=userID%>��ͨ����˵�����
        <input type="hidden" name="userID" value="<%=userID%>">
      </td>
  </tr>
  <tr align="center" bgcolor="#E9F0F8">
    <td><textarea name="desc" cols="60" rows="6"></textarea></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#BFE8FF">
      <input type="submit" class="input1" name="submit01" value="ȷ��">
      <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</table>
</form>
</body>
</html>
