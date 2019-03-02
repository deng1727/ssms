<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<%
String platFormName=request.getAttribute("platFormName")==null?"":(String)request.getAttribute("platFormName");
String cityName=request.getAttribute("cityName")==null?"":(String)request.getAttribute("cityName");
%>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">
//   add by tungke 
function thischeckContent(field)
{
	var value = eval(field).value;
    //如果为空，则不做内容检验
    if( getLength(value) == 0) return true;
	var other = /^[\w\s\!\@\#\$\%\^\-\－\(\)\[\]\{\}\【\】\＊\。\！\＠\＃\￥\％\……\＆\（\）\《\》\“\”\？\：\．\·\_\u4e00-\u9fa5]+$/g ;
	return (other.test(value));
}

function checkForm(form)
{
  if(!checkLength(form.categoryName,"货架名称",-1,40)) return false;
  if(!thischeckContent(form.categoryName))
  {
    alert("<%=ResourceUtil.getResource("RESOURCE_CATE_FIELD_CHECK_001")%>");
    form.categoryName.focus();
    return false;
  }
  if(!checkLength(form.categoryDesc,"货架描述",0,500)) return false;
	if(form.picture.value!="")
	{
		if(!isFileType(form.picture.value,"png"))
		{
	        alert("不支持该格式，请重新选择文件，目前图片仅支持png格式！");
	        return false;
		}
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
function load()
{
}

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onLoad="load();">
	<form name="form1" method="post" action="categoryTree.do"   enctype="multipart/form-data"  onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    修改动漫货架
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="right" class="text3">货架路径：</td>
	    <td class="text4"><bean:write name="path"/>
	    </td>
	  </tr>
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <input type="hidden" name="method" value="update">
	      <input type="hidden" name="categoryId" value="<bean:write name="categoryVO" property="categoryId"/>">
	      <font color="#ff0000">(*)</font>货架名称：
	    </td>
	    <td width="75%" class="text4"><input name="categoryName" type="text" value="<bean:write name="categoryVO" property="categoryName"/>" size="50">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架描述：</td>
	    <td class="text4"><textarea name="categoryDesc" cols="50"><bean:write name="categoryVO" property="categoryDesc"/></textarea>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">货架图片：</td>
	    <td class="text4"><input  type="file"  name="picture"   size="60"> &nbsp;仅支持png格式<bean:write name="categoryVO" property="picture"/>
	    <logic:notEqual value="" name="categoryVO" property="picture">
      			<input type="checkbox" value="1" name="clear_picture"/>
      			清空当前字段内容
      		</logic:notEqual> 
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架排序：</td>
	    <td class="text4">
			<input name="sortId" type="text" value="<bean:write name="categoryVO" property="sortId"/>" size="8" 
			onkeydown="if(event.keyCode==13) event.keyCode=9"
			onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">是否在门户展示：</td>
	    <td class="text4">
			<input name="delFlag" type="radio" value="0" <logic:equal value="0" name="categoryVO" property="delFlag">checked</logic:equal>>是
			<input name="delFlag" type="radio" value="1" <logic:equal value="1" name="categoryVO" property="delFlag">checked</logic:equal>>否
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="确定">
		  </td>
		</tr>
	</table>
</body>
</html>
