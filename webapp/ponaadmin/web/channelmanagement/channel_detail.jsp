<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO" %>
<%
   ChannelListVO vo = (ChannelListVO)request.getAttribute("channelContent");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>查询渠道</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function back(){
	window.history.back(-1);
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
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
	    <td class="text4"><%=vo.getChannelName() %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	创建时间：
	    </td>
	    <td class="text4"><%=vo.getCreateDate() %>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	合作商名称：
	    </td>
	    <td class="text4"><%=vo.getChannelsName() %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	渠道描述：
	    </td>
	    <td class="text4"><%=vo.getChannelDesc() %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	合作商ID：
	    </td>
	    <td class="text4"><%=vo.getChannelsId() %>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" onclick="back();" value="返回列表">
		  </td>
		</tr>
	</table>
</body>
</html>
