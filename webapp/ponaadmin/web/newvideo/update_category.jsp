<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market视频货架管理系统</title>
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
	if(!checkLength(form.baseName,"货架名称",-1,40)) return false;
	if(!thischeckContent(form.baseName))
	{
		alert("<%=ResourceUtil.getResource("RESOURCE_CATE_FIELD_CHECK_001")%>");
		form.name.focus();
		return false;
	}
	if(!checkLength(form.desc,"货架描述",0,500)) return false;

    var sort = form.sortId.value;
	var reg = /[0-9]{1}/;
	var reg2 = /[1-9][0-9]+/;
	if(reg.exec(sort) != sort && reg2.exec(sort) != sort)
	{
	    alert("排序ID只能为数值型，请重新输入！");
	    form.sortId.focus();
		return false;
	}
	
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
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
	<form name="form1" method="post" action="videoCategory.do" enctype="multipart/form-data"  onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    修改视频货架
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
	      <input type="hidden" name="perType" value="update">
	      <input type="hidden" name="categoryId" value="<bean:write name="videoCategoryVO" property="categoryId"/>">
	      <input type="hidden" name="oldPicture" value="<bean:write name="videoCategoryVO" property="picture"/>">
	      <font color="#ff0000">(*)</font>货架名称：
	    </td>
	    <td width="75%" class="text4"><input name="cname" type="text" value="<bean:write name="videoCategoryVO" property="cname"/>" size="50">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架描述：</td>
	    <td class="text4"><textarea name="cdesc" cols="50"><bean:write name="videoCategoryVO" property="cdesc"/></textarea>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架图片：</td>
	    <td class="text4"><input  type="file"  name="picture"   size="60"> &nbsp;仅支持png格式
	    <logic:notEqual value="" name="videoCategoryVO" property="picture">
	           <bean:write name="videoCategoryVO" property="picture"/>
      			<input type="checkbox" value="1" name="clear_picture"/>
      			清空当前字段内容
      		</logic:notEqual> 
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">是否在门户展示：</td>
	    <td class="text4">
			<input name="isShow" type="radio" value="1" <logic:equal value="1" name="videoCategoryVO" property="isShow">checked</logic:equal>>是
			<input name="isShow" type="radio" value="0" <logic:equal value="0" name="videoCategoryVO" property="isShow">checked</logic:equal>>否
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架排序信息：</td>
	    <td class="text4">
	    	<input name="sortId" type="text" value='<bean:write name="videoCategoryVO" property="sortId"/>' size='8'/>
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
