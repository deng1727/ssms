<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">ϵͳ����----������־����</td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
  <tr bgcolor="#B8E2FC">
    <td width="15%">&nbsp;</td>
    <td width="85">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)"><font color="#ff0000">�� ��</font></a></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">ID��</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="logID"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">�û���</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="userID"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">�û�������</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="userName"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">�û���ɫ��</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="roles"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">����IP��</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="IP"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">����������</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="actionType"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">��������</td>
    <td bgcolor="#EFEFEF" style="word-break:break-all;"><bean:write name="log" property="actionTarget"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">���������</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="displayActionResult"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">����ʱ�䣺</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="displayActionTime" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">������</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="actionDesc"/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >&nbsp;</td>
  </tr>
</table>
</body>
</html>
