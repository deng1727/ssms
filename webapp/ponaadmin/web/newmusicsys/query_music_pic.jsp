<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%
	String tmpStyle = "text5";
	String musicId = request.getParameter("musicId") == null ? ""
			: request.getParameter("musicId");
	String musicName = request.getParameter("musicName") == null ? ""
			: request.getParameter("musicName");
	String singer = request.getParameter("singer") == null ? ""
			: request.getParameter("singer");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>基地音乐歌手查询</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<base target="_self" />
	</head>
	<script language="javascript">
	function allExport(genLogo)
	{
		if(confirm("您确定要进行导出操作吗？"))
		{
			refForm.perType.value='allExport';
			refForm.submit();
			refForm.perType.value='query';
		}
		else
		{
			return false;	
		}
	}
	
	function queryExport(genLogo)
	{
		if(confirm("您确定要进行导出操作吗？"))
		{
			refForm.perType.value='export';
			refForm.submit();
			refForm.perType.value='query';
		}
		else
		{
			return false;	
		}
	}
	
	function updateInput(genLogo)
	{
		var filePath = form1.dataFile.value;
		
		if(filePath == "" || filePath == null || filePath == "null")
		{
			alert("请选择要导入数据的文件！");
			return false;
	    }
	    genLogo.disabled=true;
	    form1.submit();
	}
	
	</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="refForm" action="queryMusicPic.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						基地音乐查询
						<input type="hidden" name="perType" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						音乐编号：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="musicId" value="<%=musicId%>">
					</td>
					<td width="18%" align="right" class="text3">
						音乐名称：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="musicName" value="<%=musicName%>">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						歌手名称：
					</td>
					<td width="30%" class="text4" scope="3">
						<input type="text" name="singer" value="<%=singer%>">
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="查询">
						<input name="reset" type="reset" class="input1" value="重置">
					</td>
				</tr>
			</table>

			<br>
			<logic:equal name="isFirst" value="1">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">请输入查询条件。</font>
						</td>
					</tr>
				</table>
			</logic:equal>


			<logic:equal name="isFirst" value="">
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
								<b>音乐列表</b>
							</td>
						</tr>
					</table>
					<table width="95%" border="0" align="center" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td width="15%" align="center" class="title1">
								音乐编号
							</td>
							<td width="15%" align="center" class="title1">
								音乐名称
							</td>
							<td width="20%" align="center" class="title1">
								歌手名称
							</td>
							<td width="25%" align="center" class="title1">
								音乐图片
							</td>
							<td width="10%" align="center" class="title1">
								音乐导入时间
							</td>
							<td width="15%" align="center" class="title1">
								音乐有效期
							</td>
						</tr>
						<logic:iterate id="vo" indexId="ind" name="PageResult"
							property="pageInfo"
							type="com.aspire.dotcard.basemusic.vo.BaseMusicVO">
							<%
								if ("text5".equals(tmpStyle))
													{
														tmpStyle = "text4";
													}
													else
													{
														tmpStyle = "text5";
													}
							%>
							<tr class=<%=tmpStyle%>>
								<td align="center" style="word-break: break-all;">
									<bean:write name="vo" property="musicId" />
								</td>
								<td align="center">
									<bean:write name="vo" property="musicName" />
								</td>
								<td align="center">
									<bean:write name="vo" property="singerName" />
								</td>
								<td align="left">
									<bean:write name="vo" property="musicPic" />
								</td>
								<td align="center">
									<bean:write name="vo" property="createTime" />
								</td>
								<td align="center">
									<bean:write name="vo" property="validity" />
								</td>
							</tr>
						</logic:iterate>
					</table>
					<table width="95%" border="0" align="center" cellpadding="0"
						cellspacing="1">
						<tr bgcolor="#B8E2FC">
							<td align="right">
								<%
									java.util.HashMap params = new java.util.HashMap();
											params.put("musicId", musicId);
											params.put("musicName", musicName);
											params.put("singer", singer);
											params.put("perType", request.getParameter("perType"));
								%>
								<pager:pager name="PageResult" form="refForm"
									action="/web/baseMusic/queryMusicPic.do" params="<%=params%>">
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
			</logic:equal>


			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<logic:notEmpty name="PageResult" property="pageInfo">
						<td align="center">
							<input name="buttonAdd" type="button" class="input1"
								onClick="queryExport(this);" value="导出">
						</td>
						<td align="center">
							<input name="buttonAdd" type="button" class="input1"
								onClick="allExport(this);" value="全量导出">
						</td>
					</logic:notEmpty>
				</tr>
			</table>
			<br>

		</form>
		<form action="importMusicPic.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="perType" value="input">
			
			<table width="95%" border="0" align="center" cellspacing="3">
			<tr>
					<td width="20%" align="right" class="text3">
						选择要导入的excel数据文件：
					</td>
					<td class="text4">
						<input type="file" name="dataFile">
					</td>
					<td class="text4">
						<font color=red>&nbsp;&nbsp;<b>说明：导入有音乐ID、音乐图片地址两列数据</b></font>
					</td>
					</tr>
					<tr>
					<td align="center" colspan="3">
						<input name="buttonAdd" type="button" class="input1"
							onClick="updateInput(this);" value="导入">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
