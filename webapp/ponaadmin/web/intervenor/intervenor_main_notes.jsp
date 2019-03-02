<%@ page contentType="text/html; charset=gb2312" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="5" class="body1" onload="into();">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">货架管理中心----人工干预容器</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="81%" align="center" class="text4">您可以在这里进行人工干预的相关操作！</td>
  </tr>
</table>
</body>
<script language="javascript">
function into()
{
	var href = "parent.listframe.location='intervenorListView.do?actionType=list'";
	window.setTimeout(href,0);
	window.status="完成";
}
</script>
</html>
