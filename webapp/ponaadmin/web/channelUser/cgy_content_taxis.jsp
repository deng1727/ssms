<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>资源选择</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
<!--

function modTaxis()
{
  if(!checkLength(sortForm.taxis,"排序序号",-1,9999999))
    {
		return false;
	}
	var value = sortForm.taxis.value;
	if(!JudgeReal2(sortForm.taxis,"排序序号",9))
	{
	  return false;
	}
	
   if((value < -9999)||(value >9999))
   {
      alert("排序序号只能在-9999~9999之间！");
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
    <td>所在位置：<bean:write name="category" property="namePath"/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">资源分类排序信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<tr>
    <td width="29%" align="right" class="text3">当前排序序号范围：</td>
    <td width="71%" class="text4">最小值：<bean:write name="minTaxis"/>|最大值：<bean:write name="maxTaxis"/></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">排序序号：</td>
    <td width="71%" class="text4"><input type="text" name="taxis" value="<bean:write name="refNode" property="sortID"/>" onKeyDown="if(event.keyCode==13) return modTaxis();"></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="确定" onclick="return modTaxis();">
        <input name="button1" type="button" class="input1" value="返回" onclick="javaScript:history.go(-1);">
    </td>
  </tr>
</table>
</form>
</body>
</html>
