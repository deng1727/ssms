<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryRuleVO"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>编辑渠道商信息</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1"> 
					编辑渠道商信息 
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="openChannels.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<% 
					OpenChannelsVO vo = (OpenChannelsVO)request.getAttribute("vo");
				%>
				<tr>
					<input name="channelsId" type="hidden" value="<%=vo.getChannelsId()%>"/>
					<td align="right" class="text3">
						渠道商名称：
					</td>
					<td class="text4" style="word-break:break-all;">
						<input name="channelsName" type="text" value="<%=vo.getChannelsName()%>"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						渠道商描述：
					</td>
					<td class="text4" style="word-break:break-all;">
						<textarea name="channelsDesc" cols="50"/><%=vo.getChannelsDesc()==null?"":vo.getChannelsDesc()%></textarea>
					</td>
				</tr>
				
				<tr align="center" class="text3">
					<td colspan="2">
						<input name="button2" type="button" onclick="updateInfo();" value="保存">
						<input name="button2" type="button" onclick="back();" value="返回">
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript" language="javascript">
   
	function updateInfo()
	{
		var channelsName = ContentForm.channelsName.value;
		var channelsDesc = ContentForm.channelsDesc.value;
		if(channelsName=="")
		{
			alert("对不起，请输入渠道商名称！");
			ContentForm.id.focus();
			return false;
		}
		
		ContentForm.action = "queryOpenChannelsList.do?method=updateChannels&channelsName="+channelsName+"&channelsDesc"+channelsDesc;
		ContentForm.submit();
	}
	function back(){
	window.history.back(-1);
	}
</script>
</html>
