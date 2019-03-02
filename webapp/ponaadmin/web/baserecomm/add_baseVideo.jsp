<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page
	import="com.aspire.ponaadmin.web.baserecomm.basevideo.BaseVideoVO"%>
<%@page import="java.util.*"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>查询基地视频数字内容</title>
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
					基地视频新增
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="baseVideoQuery.do" method="post">
			<input type="hidden" name="actionType" value="addData">
			<br>
			<table width="95%" border="0" align="center" cellspacing="3"
				class="text4">
				<tr class="text5">
					<td align="center">
						<b>基地视频信息添加</b>
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr bgcolor="#B8E2FC">
					<td align="center" class="title1">
						视频名称
					</td>
					<td align="center" class="text4">
						<input type="text" name="videoName" value="">
					</td>
				</tr>
				<tr bgcolor="#B8E2FC">
					<td align="center" class="title1">
						视频URL链接
					</td>
					<td align="center" class="text4">
						<input type="text" name="videoUrl" value="">
					</td>
				</tr>

				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input type="button" value="新增" onclick="addDataList();">
							<input type="button" value="返回"
								onclick="javascript:history.go(-1);">
						</td>
					</tr>
				</table>
		</form>
		<script language="javascript">
		function addDataList()
		{
			var videoName = document.getElementsByName('videoName').value;
			var videoUrl = document.getElementsByName('videoUrl').value;
			
			if(""==videoName)
			{
				alert("视频名称不可以为空！");
				return false;
			}
			
			if(""==videoUrl)
			{
				alert("视频url链接不可以为空！");
				return false;
			}
			
			ContentForm.action = "baseVideoTemp.do";
			ContentForm.submit();
		}
		</script>
	</body>
</html>
