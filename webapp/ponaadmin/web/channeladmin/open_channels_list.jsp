<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="java.util.*"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>查询渠道商列表</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" bgcolor="#BFE8FF">
					<b>渠道商查询</b>
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="openChannelMoList.do?method=queryList" method="post">
			<input type="hidden" name="actionType" value="add">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="right" class="text3">
						渠道商ID：
					</td>
					<td width="40%" class="text4">
						<input type="text" name="channelsId"
							value="<bean:write name="channelsId"/>">
					</td>
					<td width="10%" align="right" class="text3">
						渠道商名称：
					</td>
					<td width="40%" class="text4">
						<input type="text" name="channelsName"
							value="<bean:write name="channelsName"/>">
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text5">
					<td align="center">
						<input type="submit" class="input1" name="submit01" value="查询">
						<input type="button" class="input1" name="btn_reset" value="重置"
							onClick="clearForm(ContentForm);">
					</td>
				</tr>
			</table>

			<br>

			<logic:empty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">没有找到任何记录。</font>
						</td>
					</tr>
				</table>
			</logic:empty>

			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>渠道商列表</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="20%" align="center" class="title1">
							渠道商ID
						</td>
						<td width="45%" align="center" class="title1">
							渠道商名称
						</td>
						<td width="35%" align="center" class="title1">
							相关配置
						</td>
					</tr>
					<logic:iterate id="vo" indexId="i" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO">
						<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>>
							<td align="center">
								<a href="#" style="color: blue;" onclick="detail(<%=vo.getChannelsId()%>);"><%=vo.getChannelsId()%></a>
							</td>
							<td align="center">
								<%=vo.getChannelsName()%>
							</td>
							<td align="center">
								<input type="button" value="根货架配置" onclick="rootMarketConfigure(<%=vo.getChannelsId()%>);">
								<input type="button" value="客户端渠道配置" onclick="clientList(<%=vo.getChannelsId()%>);">
								<input type="button" value="开放运营渠道配置" onclick="openOperationList(<%=vo.getChannelsId()%>);">
							</td>
						</tr>
					</logic:iterate>
				</table>
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<%
			                HashMap params = new HashMap();
			                params.put("channelsId", request.getParameter("channelsId"));
			                params.put("channelsName", request.getParameter("channelsName"));
							%>
							<pager:pager name="PageResult"
								action="/web/channeladmin/openChannelMoList.do?method=queryList" params="<%=params%>">
							<pager:firstPage label="首页" />&nbsp;
				            <pager:previousPage label="前页" />&nbsp;
				            <pager:nextPage label="后页" />&nbsp;
				            <pager:lastPage label="尾页" />&nbsp;
				           	 第<pager:pageNumber />页/共<pager:pageCount />页
				            <pager:location id="1" />
							</pager:pager>
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</form>
		<script language="javascript">
		
		function rootMarketConfigure(channelsId){
			ContentForm.action = "openChannelsCategory.do?method=add&channelsId="+channelsId;
			ContentForm.submit();
		}
		
		function clientList(channelsId)
		{
			ContentForm.action = "openChannelMoList.do?method=list&channelsId="+channelsId;
			ContentForm.submit();
		}
		
		function openOperationList(channelsId)
		{
			ContentForm.action = "openOperationList.do?method=list&channelsId="+channelsId;
			ContentForm.submit();
		}
		
		function detail(channelsId)
		{
			ContentForm.action = "../channeladmin/queryOpenChannelsList.do?method=list&channelsId="+channelsId+"&flag=channelMo";
			ContentForm.submit();
		}
		
		</script>
	</body>
</html>
