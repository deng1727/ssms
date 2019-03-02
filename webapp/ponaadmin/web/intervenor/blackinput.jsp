<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>人工干预容器信息</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
  
  <body>
  <table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
     <form action="blackinput.do?actionType=importfile" name="form1" method="post" enctype="multipart/form-data">
  <tr>
    <td width="20%" align="right" class="text3">
    	导入内容数据文件:
    </td>
    <td colspan="2" class="text4">
    	<input type="file" name="dataFile">(请选择.xls数据文件)
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    </td>
    <td colspan="2" class="text4">
    	<input type="submit" name="genLogo" value="导入内容数据文件" onclick="return import_content()">
    	<b><font color=red>注意：请注意xls文件的格式!</font></b>
	</td>
  </tr>
  </table> 
  </form>
  <script language="javascript">
	function import_content()
	{

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
	
		if(!confirm('确定要导入榜单黑名单表吗？'))
		{
	  	 return false;
		}
		return true;
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
  </body>
</html>
