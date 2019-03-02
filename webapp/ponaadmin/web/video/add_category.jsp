`<%@ page contentType="text/html; charset=gbk" %>
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
	if(!checkLength(form.name,"货架名称",-1,40)) return false;
	if(!thischeckContent(form.name))
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

	return true;
}

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="form1" method="post" action="categoryTree.do" enctype="multipart/form-data" onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    新增视频货架
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <input type="hidden" name="perType" value="save">
	      <input type="hidden" name="pCategoryId" value="<bean:write name="pCategoryId" />">
	      <input type="hidden" name="pBaseCategoryId" value="<bean:write name="videoCategoryVO" property="baseId"/>">
	      <input type="hidden" name="baseType" value="<bean:write name="videoCategoryVO" property="baseType"/>">
	      <font color="#ff0000">(*)</font>货架名称：
	    </td>
	    <td width="75%" class="text4"><input name="name" type="text" value="" size="50">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架描述：</td>
	    <td class="text4"><textarea name="desc" cols="50"></textarea>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">是否在门户展示：</td>
	    <td class="text4">
			<input name="isShow" type="radio" value="1" checked>是
			<input name="isShow" type="radio" value="0">否
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">货架排序信息：</td>
	    <td class="text4">
			<input name="sortId" type="text" value='0' size='8'/>
	    </td>
	  </tr>
	<logic:iterate id="vo" indexId="ind" name="keyBaseList"
		type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
		<tr>
			<td align="right" class="text3">
				<bean:write name="vo" property="keydesc" />
			</td>
			<td class="text4">
				<logic:equal value="1" name="vo" property="keyType">
					<input name="<bean:write name="vo" property="keyname"/>"
						type="text" value="<bean:write name="vo" property="value"/>"
						size="60">
				</logic:equal>
				<logic:equal value="2" name="vo" property="keyType">
					<input type="file"
						name="<bean:write name="vo" property="keyname"/>" size="60"> &nbsp;仅支持png格式</logic:equal>
				<logic:equal value="3" name="vo" property="keyType">
					<textarea name="<bean:write name="vo" property="keyname"/>"
						rows="5" cols="40"></textarea>
				</logic:equal>
			</td>
		</tr>
	</logic:iterate>
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
