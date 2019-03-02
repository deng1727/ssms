<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO" %>
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
		<form name="ContentForm" action="queryOpenChannelsList.do?method=queryOpenChannelsList" method="post">
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<% 
					OpenChannelsVO vo = (OpenChannelsVO)request.getAttribute("vo");
			%>
			<tr>
				<td align="center" bgcolor="#BFE8FF">
					账号详情
				</td>
			</tr>
		</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="10%" align="center" class="title1">
							渠道商ID:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=vo.getChannelsId()%>
						</td>
					</tr>
					<tr>
						<td width="10%" align="center" class="title1">
							渠道商名称:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=vo.getChannelsName()%>
						</td>
					</tr>
					<tr>
						<td width="10%" align="center" class="title1">
							渠道商账号:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=vo.getChannelsNo()%>
						</td>
					</tr>
					<tr>
						<td width="10%" align="center" class="title1">
							创建时间:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getCreateDate())%>
						</td>
						
					</tr>
					<tr>
						<td width="10%" align="center" class="title1">
							状态:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=vo.getStatus()=="0"?"下线":"正常"%>
						</td>
						
					</tr>
					<tr>
						<td width="10%" align="center" class="title1">
							渠道商描述:
						</td>
						<td align="left" bgcolor="#BFE8FF">
							<%=vo.getChannelsDesc()%>
						</td>
						
					</tr>
				</table>
		<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input type="button" value="关闭" onclick="close1('<%=request.getAttribute("flag")%>');">
						</td>
					</tr>
				</table>		

				
			
		</form>
		<script type="text/javascript" language="javascript">
			
			function close1(flag)
			{
				ContentForm.action = "queryOpenChannelsList.do?method=queryOpenChannelsList";
				if(flag!=null && flag == "channelMo"){
					ContentForm.action = "../channeladmin/openChannelMoList.do?method=queryList";
				}
				ContentForm.submit();
			}
			
		
		</script>
	</body>
</html>
