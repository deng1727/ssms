<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">系统工具----操作日志详情</td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
  <tr bgcolor="#B8E2FC">
    <td width="15%">&nbsp;</td>
    <td width="85">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)"><font color="#ff0000">返 回</font></a></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">ID：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="logID"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">用户：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="userID"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">用户姓名：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="userName"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">用户角色：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="roles"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">访问IP：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="IP"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">操作动作：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="actionType"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">操作对象：</td>
    <td bgcolor="#EFEFEF" style="word-break:break-all;"><bean:write name="log" property="actionTarget"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">操作结果：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="displayActionResult"/></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">操作时间：</td>
    <td bgcolor="#EFEFEF"><bean:write name="log" property="displayActionTime" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#B8E2FC">描述：</td>
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
