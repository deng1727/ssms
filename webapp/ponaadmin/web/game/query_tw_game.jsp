<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%
	String tmpStyle = "text5";
	String gameId = request.getParameter("gameId") == null ? ""
			: request.getParameter("gameId");
	String gameName = request.getParameter("gameName") == null ? ""
			: request.getParameter("gameName");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>ͼ����Ϸ��ѯ</title>
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
		if(confirm("��ȷ��Ҫ���е���������"))
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
		if(confirm("��ȷ��Ҫ���е���������"))
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
			alert("��ѡ��Ҫ�������ݵ��ļ���");
			return false;
	    }
	    genLogo.disabled=true;
	    form1.submit();
	}
	
	</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="refForm" action="queryGameTW.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						ͼ����Ϸ��ѯ
						<input type="hidden" name="perType" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						��ϷID��
					</td>
					<td width="30%" class="text4">
						<input type="text" name="gameId" value="<%=gameId%>">
					</td>
					<td width="18%" align="right" class="text3">
						��Ϸ���ƣ�
					</td>
					<td width="30%" class="text4">
						<input type="text" name="gameName" value="<%=gameName%>">
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="��ѯ">
						<input name="reset" type="reset" class="input1" value="����">
					</td>
				</tr>
			</table>

			<br>

			<logic:empty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">û���ҵ��κμ�¼��</font>
						</td>
					</tr>
				</table>
			</logic:empty>

			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>��Ϸ�б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr>
						<td width="10%" align="center" class="title1">
							��ϷID
						</td>
						<td width="15%" align="center" class="title1">
							��Ϸ����
						</td>
						<td width="15%" align="center" class="title1">
							��Ӧ��
						</td>
						<td width="30%" align="center" class="title1">
							��Ϸ���
						</td>
						<td width="10%" align="center" class="title1">
							ԭ���ʷѣ��֣�
						</td>
						<td width="10%" align="center" class="title1">
							�ּ��ʷѣ��֣�
						</td>
						<td width="10%" align="center" class="title1">
							�������
						</td>
					</tr>
					<logic:iterate id="vo" indexId="ind" name="PageResult"
						property="pageInfo"
						type="com.aspire.ponaadmin.web.game.vo.TWGameVO">
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
								<bean:write name="vo" property="gameId" />
							</td>
							<td align="center">
								<bean:write name="vo" property="gameName" />
							</td>
							<td align="center"">
								<bean:write name="vo" property="CPName" />
							</td>
							<td align="center"
								title="<bean:write name="vo" property="gameDesc" />">
								<bean:write name="vo" property="gameDescByPage" />
							</td>
							<td align="center">
								<bean:write name="vo" property="oldPrice" />
							</td>
							<td align="center">
								<bean:write name="vo" property="fee" />
							</td>
							<td align="left">
								<bean:write name="vo" property="sortId" />
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
									params.put("gameId", gameId);
									params.put("gameName", gameName);
									params.put("perType", request.getParameter("perType"));
							%>
							<pager:pager name="PageResult" form="refForm"
								action="/web/baseGame/queryGameTW.do" params="<%=params%>">
								<pager:firstPage label="��ҳ" />&nbsp;
		            <pager:previousPage label="ǰҳ" />&nbsp;
		            <pager:nextPage label="��ҳ" />&nbsp;
		            <pager:lastPage label="βҳ" />&nbsp;
		            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
		            <pager:location id="1" />
							</pager:pager>
						</td>
					</tr>
				</table>
			</logic:notEmpty>


			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<logic:notEmpty name="PageResult" property="pageInfo">
						<td align="center">
							<input name="buttonAdd" type="button" class="input1"
								onClick="queryExport(this);" value="����">
						</td>
						<td align="center">
							<input name="buttonAdd" type="button" class="input1"
								onClick="allExport(this);" value="ȫ������">
						</td>
					</logic:notEmpty>
					<td align="center">
						<input name="buttonAdd" type="button" class="input1"
							onClick="updateInput(this);" value="����">
					</td>
				</tr>
			</table>
			<br>

		</form>
		<form action="importGameTW.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="perType" value="input">
			<table width="95%" border="0" align="center" cellspacing="3">
				<tr>
					<td align="right" width="20%">
						ѡ��Ҫ�����excel�����ļ���
					</td>
					<td align="left" width="80%">
						<input type="file" name="dataFile">
						<font color=red>&nbsp;&nbsp;<b>˵������������ϷID��������Ŷ������ݣ�������ϷID�����������ֵ���������ֵΪ���ԭֵ���Ը�1��!</b>
						</font>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
