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

function importData()
{
	form1.perType.value="importData";
	
	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("请选择要导入数据的文件！");
		return false;
    }

    if(!isFileType(filePath,"xls")&&!isFileType(filePath,"xlsx"))
    {
		alert("请选择xls或xlsx格式的数据文件!");
		return false;
    }
	form1.submit();
}

function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>所在位置：人工干预管理----榜单黑名单批量新增导入</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>榜单黑名单批量新增</FONT></td>
  </tr>
</table>

<form action="androidBlackListImport.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	请选择要导入的excel数据文件：
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">
	    </td>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
      <input type="submit" class="input1" name="genLogo" value="确定" onclick="return importData();">
      <input type="button" class="input1"  value="返回" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>

<br>
<table width="95%"  border="0" align="center" cellspacing="1">

<tr><td><font color="red">*提示：导入文件为导入为.xlsx或者.xls格式的excel文件，
							第一列为内容ID，第二列为内容名称，第三列为操作列（add或者del）</font><br/>
         <font color="red">*标签操作列填写:add(添加)或者del(删除) </font>
   	
</td>
</tr>
</table>
</body>
</html>
