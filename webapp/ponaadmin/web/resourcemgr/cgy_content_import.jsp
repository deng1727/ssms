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
   String menuStatus=Validator.filter(request.getParameter("menuStatus"));
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
    //alert(filePath);
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
	//form1.commit();
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>当前货架位置：<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>货架数据导入</FONT></td>
  </tr>
</table>
<br>

<form action="categoryImport.do?action=import&cgyPath=<%=cgyPath %>" name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" name="cateId" value="<%=cateId %>">
<input type="hidden" name="menuStatus" value="<%=menuStatus %>">
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr>
    <td   align="left" width="25%">请选择要导入的excel数据文件：</td>
    <td   align="left" width="75%">
    	<input type="file" name="dataFile">(表格第一列表示序号，升序排列；第二列表示内容ID，例如：300XXXXXXXX)
    	
    </td>
  </tr>
   <tr>
    <td   align="left" colspan="2">
    <b>增量：在该货架下增加商品，序号以EXCEL（2003版本或者2007版本）数据为准</b><br/>
    <b><font color="red">全量：下架原有该货架下全部商品，添加EXCEL（2003版本或者2007版本）指定商品到该货架下</font></b>
    
    </td>
  </tr>
  <tr>
    <td   align="left" colspan="2">

	    <b>是否是紧急上线应用:</b>
	    <select name="isSyn">
	    	<option value="0">否</option>
	    	<option value="1">是</option>
	    </select>
	    
	    
	        	<b>增加类型</b>
        <select name="addType">
            <option value="ALL">全量</option>
	    	<option value="ADD">增量</option>
	    </select>
    </td>
  </tr>
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
