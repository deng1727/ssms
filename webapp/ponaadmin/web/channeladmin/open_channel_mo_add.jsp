<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>客户端渠道配置</title>
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
  if(!checkLength(form.channelId,"渠道ID",-1,20)) return false;
  if(!checkLength(form.channelName,"渠道名称",-1,30)) return false;
  if(!thischeckContent(form.channelName))
  {
    alert("<%=ResourceUtil.getResource("RESOURCE_CATE_FIELD_CHECK_001")%>");
    form.channelName.focus();
    return false;
  }
  return true;
}

function back(){
	window.history.back(-1);
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="form1" method="post" action="openChannelMoList.do"   enctype="multipart/form-data"   onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    新增客户端渠道配置
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <input type="hidden" name="method" value="save">
	      <input type="hidden" name="channelsId" value="<bean:write name="vo" property="channelsId" />">
	      <font color="#ff0000">(*)</font>渠道ID：
	    </td>
	    <td width="75%" class="text4"><input name="channelId" type="text" size="20">
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	<font color="#ff0000">(*)</font>渠道名称：
	    </td>
	    <td class="text4"><input name="channelName" type="text" size="20">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="确定">
		    <input type="button" class="input1" value="返回" onclick="back();">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
