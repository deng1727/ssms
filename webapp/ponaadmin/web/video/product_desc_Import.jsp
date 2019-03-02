<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market视频货架管理系统</title>
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
   var filePath = form1.dataFile.value;
   if(filePath == "" || filePath == null || filePath == "null")
   {
      alert("请选择要导入数据的文件！");
      return false;
    }
     
 
    if(!isFileType(filePath,"xls"))
    {
      alert("请选择xls格式的数据文件!");
      return false;
    }
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>所在位置：货架管理中心----视频产品资费描述信息批量导入</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>产品资费描述信息批量导入</FONT></td>
  </tr>
</table>
<form action="../baseVideo/product.do" name="form1" method="post" enctype="multipart/form-data">
<table width="95%"  border="0" align="center" cellspacing="3">
<input type="hidden" name="perType" value="modifyDesc"/>

  <tr>
    <td class="text3"  align="right" width="20%">请选择要导入的excel数据文件：</td>
    <td   align="left" width="80%">
    	<input type="file" name="dataFile"/>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
      <input type="submit" class="input1" name="genLogo" value="确定" onclick="return import_content();">
      <input type="button" class="input1"  value="返回" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>
<br>
<table width="95%"  border="0" align="center" cellspacing="1">

<tr><td><font color="red">*提示，导入格式为 普通xls文件格式，第一列为产品ID,第二列为资费描述（不超过300个汉字）</font><br/>  	
</td>
</tr>
</table>
</body>
</html>
