<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>����ͬ��������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<form name="AsynTaskForm" method="post" action="<%=request.getContextPath()%>/web/resourcemgr/dataSync.do">
<input type="hidden" name="action" value="queryTask">
<input type="hidden" name="taskName" value="<%=request.getAttribute("taskName")%>">

<table width="95%"  border="0" align="center" cellpadding="10" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">����ͬ������ִ����Ϣ</td>
  </tr>
</table>
<p>
<table width="95%"  border="0" align="center" cellspacing="2" cellpadding="10" bgcolor="#FFFFFF">
  <tr>
    <td width="40%" align="center" class="text3">��������</td>
    <td width="25%" align="center" class="text3">��ʼʱ��</td>
    <td width="20%" align="center" class="text3">�ѽ���ʱ��</td>
    <td width="15%" align="center" class="text3">������Ա</td>
  </tr>
  <tr>
    <td align="center" class="text4"><bean:write name="taskDesc"/></td>
    <td align="center" class="text4"><bean:write name="startDate" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td align="center" class="text4"><bean:write name="processTime"/></td>
    <td align="center" class="text4"><bean:write name="user"/></td>
  </tr>
</table>

</form>
<table width="95%"  border="0" align="center" cellpadding="10" bgcolor="#F0FBFD">
  <tr>
    <td align="center" > <input type="button" class="input1"  value="����" onclick="javascript:window.location.href='data_import.jsp';"></td>
  </tr>
</table>
<script language="javascript">
function reloadPage()
{
  document.AsynTaskForm.submit();
  setTimeout('reloadPage()',5000);
}

setTimeout('reloadPage()',5000);
</script>
</body>
</html>
