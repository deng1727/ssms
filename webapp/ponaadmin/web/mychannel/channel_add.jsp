<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>新增合作商</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function checkForm(form)
{
  var channelName = form1.channelName.value;
  if(channelName == null || channelName == ''){
	  alert("渠道名称不能为空");
	  return false;
  }
  return true;
}

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="form1" method="post" action="channelInfo.do" onSubmit="return checkForm(form1);">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<input name="perType" type="hidden" id="perType" value="save">
	  <tr>
	    <td align="center" class="title1">
	            申请渠道
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="right" class="text3">
	    	<font color="#ff0000">(*)</font>渠道类型：
	    </td>
	    <td class="text4">
	      <label><input name="channelType" type="radio" value="0" checked="checked" />客户端 </label> 
          <label><input name="channelType" type="radio" value="1" />数据接口</label> 
          <label><input name="channelType" type="radio" value="2" />网页</label> 
	    </td>
	  </tr>
	  <tr>
	    <td width="25%" align="right" class="text3">
	      <font color="#ff0000">(*)</font>渠道名称：
	    </td>
	    <td width="75%" class="text4"><input name="channelName" type="text" size="20" value="">
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	渠道描述：
	    </td>
	    <td class="text4"><input name="channelDesc" type="text" value="">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="申请">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
