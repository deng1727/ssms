<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%
	String tmpStyle = "text5";
	String authorId = request.getParameter("authorId") == null ? ""
			: request.getParameter("authorId");
	String authorName = request.getParameter("authorName") == null ? ""
			: request.getParameter("authorName");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>基地阅读作者查询</title>
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
		<form name="refForm" action="queryAuthor.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						基地阅读作者查询
						<input type="hidden" name="perType" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						作者编号：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="authorId" value="<%=authorId%>">
					</td>
					<td width="18%" align="right" class="text3">
						作者名称：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="authorName" value="<%=authorName%>">
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
								<b>作者列表</b>
							</td>
						</tr>
					</table>
					<table width="95%" border="0" align="center" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td width="10%" align="center" class="title1">
								作者编号
							</td>
							<td width="10%" align="center" class="title1">
								作者首字母
							</td>
							<td width="20%" align="center" class="title1">
								作者名称
							</td>
							<td width="30%" align="center" class="title1">
								作者简介
							</td>
							<td width="10%" align="center" class="title1">
								是否原创大神
							</td>
							<td width="10%" align="center" class="title1">
								是否出版作者
							</td>
							<td width="10%" align="center" class="title1">
								推荐序号
							</td>
						</tr>
						<logic:iterate id="vo" indexId="ind" name="PageResult"
							property="pageInfo"
							type="com.aspire.dotcard.baseread.vo.BookAuthorVO">
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
									<bean:write name="vo" property="authorId" />
								</td>
								<td align="center">
									<bean:write name="vo" property="nameLetter" />
								</td>
								<td align="center"
									title="<bean:write name="vo" property="authorName" />">
									<a
										href="queryAuthor.do?perType=mod&authorId=<bean:write name="vo" property="authorId"/>"><bean:write
											name="vo" property="showName" /> </a>
								</td>
								<td align="left" title="<bean:write name="vo" property="description" />">
									<bean:write name="vo" property="showDesc" />
								</td>
								<td align="center">
									<logic:equal value="1" name="vo" property="isOriginal">是</logic:equal>
									<logic:equal value="0" name="vo" property="isOriginal">否</logic:equal>
								</td>
								<td align="center">
									<logic:equal value="1" name="vo" property="isPublish">是</logic:equal>
									<logic:equal value="0" name="vo" property="isPublish">否</logic:equal>
								</td>
								<td align="left">
									<bean:write name="vo" property="recommendManual" />
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
											params.put("authorId", authorId);
											params.put("authorName", authorName);
											params.put("perType", request.getParameter("perType"));
								%>
								<pager:pager name="PageResult" form="refForm"
									action="/web/baseRead/queryAuthor.do" params="<%=params%>">
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
					<td align="center">
						<input name="buttonAdd" type="button" class="input1"
							onClick="updateInput(this);" value="导入">
					</td>
				</tr>
			</table>
			<br>

		</form>
		<form action="importAuthor.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="perType" value="input">
			<table width="95%" border="0" align="center" cellspacing="3">
				<tr>
					<td align="right" width="20%">
						选择要导入的excel数据文件：
					</td>
					<td align="left" width="80%">
						<input type="file" name="dataFile">
						<font color=red>&nbsp;&nbsp;<b>说明：导入有作者ID、作者名称大写首字母、是否原创大神(0：否、1：是)、是否出版作者(0：否、1：是)、推荐序号、作者简介六列数据，根据作者ID更新是否原创大神、是否出版作者、推荐序号值、作者简介!<b>
						</font>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
