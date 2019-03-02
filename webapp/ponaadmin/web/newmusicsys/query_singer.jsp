<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%
	String tmpStyle = "text5";
	String singerId = request.getParameter("singerId") == null ? ""
			: request.getParameter("singerId");
	String singerName = request.getParameter("singerName") == null ? ""
			: request.getParameter("singerName");
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
		<form name="refForm" action="querySinger.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						基地音乐歌手查询
						<input type="hidden" name="perType" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						歌手编号：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="singerId" value="<%=singerId%>">
					</td>
					<td width="18%" align="right" class="text3">
						歌手名称：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="singerName" value="<%=singerName%>">
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
								<b>歌手列表</b>
							</td>
						</tr>
					</table>
					<table width="95%" border="0" align="center" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td width="15%" align="center" class="title1">
								歌手编号
							</td>
							<td width="10%" align="center" class="title1">
								歌手首字母
							</td>
							<td width="15%" align="center" class="title1">
								歌手名称
							</td>
							<td width="20%" align="center" class="title1">
								歌手简介
							</td>
							<td width="15%" align="center" class="title1">
								歌手图片
							</td>
							<td width="10%" align="center" class="title1">
								歌手属性
							</td>
							<td width="15%" align="center" class="title1">
								最后修改日期
							</td>
						</tr>
						<logic:iterate id="vo" indexId="ind" name="PageResult"
							property="pageInfo"
							type="com.aspire.dotcard.basemusic.vo.BaseSingerVO">
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
									<bean:write name="vo" property="singerId" />
								</td>
								<td align="center">
									<bean:write name="vo" property="nameLetter" />
								</td>
								<td align="center" title="<bean:write name="vo" property="singerName" />">
									<a
										href="querySinger.do?perType=mod&singerId=<bean:write name="vo" property="singerId"/>"><bean:write
											name="vo" property="showName" /> </a>
								</td>
								<td align="left" title="<bean:write name="vo" property="description" />">
									<bean:write name="vo" property="showDesc" />
								</td>
								<td align="center">
									<bean:write name="vo" property="imgUrl" />
								</td>
								<td align="center">
									<logic:equal value="11" name="vo" property="type">华语男</logic:equal>
									<logic:equal value="12" name="vo" property="type">华语女</logic:equal>
									<logic:equal value="13" name="vo" property="type">华语组合</logic:equal>
									<logic:equal value="21" name="vo" property="type">欧美男</logic:equal>
									<logic:equal value="22" name="vo" property="type">欧美女</logic:equal>
									<logic:equal value="23" name="vo" property="type">欧美组合</logic:equal>
									<logic:equal value="31" name="vo" property="type">日韩男</logic:equal>
									<logic:equal value="32" name="vo" property="type">日韩女</logic:equal>
									<logic:equal value="33" name="vo" property="type">日韩组合</logic:equal>
								</td>
								<td align="center">
									<bean:write name="vo" property="showDate" />
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
											params.put("singerId", singerId);
											params.put("singerName", singerName);
											params.put("perType", request.getParameter("perType"));
								%>
								<pager:pager name="PageResult" form="refForm"
									action="/web/baseMusic/querySinger.do" params="<%=params%>">
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
		<form action="importSinger.do" name="form1" method="post"
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
						<font color=red>&nbsp;&nbsp;<b>说明：导入有歌手ID、歌手属性两列数据，据据歌手ID更新歌手属性值，举例：
						如歌手ID 600902000004527732  对应华语男 则第一列为600902000004527732 第二列为11。
						属性映射分别为：11:华语男、12:华语女、13:华语组合、21:欧美男、22:欧美女、23:欧美组合、31:日韩男、32:日韩女、33:日韩组合 
						</b>
						</font>
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
