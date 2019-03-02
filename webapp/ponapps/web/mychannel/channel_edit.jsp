<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.webpps.mychannel.vo.MyChannelVO" %>
<%
   MyChannelVO vo = (MyChannelVO)request.getAttribute("channelContent");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>查询渠道</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function save(){
	form1.submit();
}

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
    <input name="perType" type="hidden" id="perType" value="update">
    <input name="channelId" type="hidden" id="channelId" value="<%=vo.getChannelId() %>">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	            查询渠道
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	           渠道ID：
	    </td>
	    <td width="75%" class="text4"><%=vo.getChannelId() %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	渠道类型：
	    </td>
	    <td class="text4"><% if("0".equals(vo.getChannelType())){ %>
	                                         客户端
	       <% }else if("1".equals(vo.getChannelType())){ %>
	                                         数据接口
	       <% }else{  %>
	                                        网页
	       <%} %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	渠道名称：
	    </td>
	    <td class="text4">
	     <input name="channelName" type="text" id="channelName" value="<%=vo.getChannelName() %>">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	渠道描述：
	    </td>
	    <td class="text4">
	     <input name="channelDesc" type="text" id="channelDesc" value="<%=vo.getChannelDesc() %>">
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	创建时间：
	    </td>
	    <td class="text4"><%=vo.getCreateDate() %>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" onclick="save();" value="保存">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
