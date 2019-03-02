<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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
    <td>所在位置：货架管理中心----应用活动属性新增</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>折扣信息批量上传</FONT></td>
  </tr>
</table>
<form action="contentExtImport.do" name="form1" method="post" enctype="multipart/form-data">
<table width="95%"  border="0" align="center" cellspacing="3">

<tr>
   
	   <td  class="text3" align="right" width="20%">折扣类型：</td>
	   <td  align="left" width="80%">
	    <select name="type">
	    	<option value="1">折扣</option>
	    	<option value="2">秒杀</option>
	    	<option value="3">限时免费</option>
	    </select>
	    
    </td>
  </tr>
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

<tr><td><font color="red">*提示，模板中的日期格式是yyyy-MM-dd，如2011-09-12，这里不能写成2011-9-12；秒杀时间段参考范围09:00:00到11:00:00</font><br/>
可供参考的模板如下：<br/>
<a href="./download/File_ZK.xls">折扣批量上传模板</a><br/>
    	<a href="./download/File_MS.xls">秒杀批量上传模板</a><br/>
    	<a href="./download/File_XSMF.xls">限时免费批量上传模板</a><br/>
    
    	
 为了让你选择的类型和上传的文件不出现类型选错的情况，<br/>
 
 我们把折扣、秒杀、限时免费，分别规定用_ZK.xls 、_MS.xls、_XSMF.xls  <br/>
 
 目前，不支持批量修改。
    	
</td>
</tr>
</table>
</body>
</html>
