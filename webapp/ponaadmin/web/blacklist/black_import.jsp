<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.common.Validator" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%
   String cateId=Validator.filter(request.getParameter("cateId"));
   String cgyPath=Validator.filter(request.getParameter("cgyPath"));
 %>
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script>
function isFileType(filename,type)
{
   var pos = filename.lastIndexOf(".");

   if(pos<0) return false;

   var fileType = filename.substr(pos+1);

   if(fileType.toUpperCase()!=type.toUpperCase()) return false;

   return true;
}

function import_content()
{
   //避免直接进入该页面。
    if(form1.cateId.value=="")
    {
      return false;
    }
    
   var filePath = form1.dataFile.value;
   // alert(filePath);
   if(filePath == "" || filePath == null || filePath == "null")
   {
      alert("请选择要导入数据的文件！");
      return false;
    }
     

    if(!isFileType(filePath,"txt"))
    {
      alert("请选择txt格式的数据文件!");
      return false;
    }
	//form1.commit();
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>黑名单数据导入</FONT></td>
  </tr>
</table>
<br>

<form action="importBlack.do" name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" name="cateId" value="<%=cateId %>">
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="text5" >
    <td   align="right" width="25%">请选择要导入的txt数据文件：</td>
    
    <td   align="left" width="75%"><input type="file" name="dataFile">
    </td>
  </tr>
  <tr class="text4"><td colspan="2" align="center">(第一列开始日期(yyyyMMdd)，第二列截止日期(yyyyMMdd)，第三列表示引用内容的id(12位数字)，第四列类型(1或2)，共29位，如20100526201006263000000006301)</td></tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr >
  	<td align="center">
      <input type="submit" class="input1" name="genLogo" value="确定" onclick="return import_content()">
      <input type="button" class="input1"  value="返回" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>
<br>
</body>
</html>
