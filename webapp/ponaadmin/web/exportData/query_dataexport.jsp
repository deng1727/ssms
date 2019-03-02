<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%
	String tmpStyle = "text5";
	String exportByUser = request.getParameter("exportByUser") == null ? ""
			: request.getParameter("exportByUser");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>���������ѯ��ѯ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<base target="_self" />
	</head>
	<script language="javascript">
	
	function execExport(genLogo, id)
	{
		genLogo.disabled=true;
		if(confirm("��ȷ��Ҫ����ѡ��������"))
		{
			refForm.perType.value="exec";
			refForm.id.value=id;
			refForm.submit();
		}
		else
		{
			return false;	
		}
	}
	
	function execAllExport(genLogo)
	{
		genLogo.disabled=true;
		if(confirm("��ȷ��Ҫ����ȫ��������"))
		{
			refForm.perType.value="execAll";
			refForm.submit();
		}
		else
		{
			return false;	
		}
	}
	
	</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="refForm" action="queryExport.do" method="post">
			<input type="hidden" name="perType" value="">
			<input type="hidden" name="exportByUser" value="<bean:write name="exportByUser" />">
			<input type="hidden" name="id" value="">
			<logic:empty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">û���κοɵ����ļ�¼��</font>
						</td>
					</tr>
				</table>
			</logic:empty>

			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>���������б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr>
						<td width="5%" align="center" class="title1">
							������
						</td>
						<td width="20%" align="center" class="title1">
							��������
						</td>
						<td width="10%" align="center" class="title1">
							���������ļ�����
						</td>
						<td width="9%" align="center" class="title1">
							�ļ�������Ϣ
						</td>
						<td width="4%" align="center" class="title1">
							����
						</td>
						<td width="7%" align="center" class="title1">
							��ҳ����
						</td>
						<td width="7%" align="center" class="title1">
							����
						</td>
					</tr>
					<logic:iterate id="vo" indexId="ind" name="PageResult"
						property="pageInfo"
						type="com.aspire.ponaadmin.web.dataexport.sqlexport.VO.DataExportVO">
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
								<bean:write name="vo" property="id" />
							</td>
							<td align="center">
								<bean:write name="vo" property="exportName" />
							</td>
							<td align="center">
								<logic:equal value="1" name="vo" property="exportType">CSV</logic:equal>
								<logic:equal value="2" name="vo" property="exportType">TXT</logic:equal>
								<logic:equal value="3" name="vo" property="exportType">EXCEl</logic:equal>
							</td>
							<td align="center">
								<logic:equal value="1" name="vo" property="exportType">
										�ָ�����
										<bean:write name="vo" property="exportTypeOther" />
								</logic:equal>
								<logic:equal value="2" name="vo" property="exportType">
										�ָ�����
										<bean:write name="vo" property="exportTypeOther" />
								</logic:equal>
								<logic:equal value="3" name="vo" property="exportType">
										�汾��
									<logic:equal value="1" name="vo" property="exportTypeOther">97-03��</logic:equal>
									<logic:equal value="2" name="vo" property="exportTypeOther">07��</logic:equal>
								</logic:equal>

							</td>
							<td align="center">
								<bean:write name="vo" property="exportLine" />
							</td>
							<td align="center">
								<bean:write name="vo" property="exportPageNum" />
							</td>
							<td align="center" style="word-break: break-all;">
								<input type="button" class="input1"
									onClick="execExport(this,'<bean:write name="vo" property="id" />');"
									value="����">
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
									params.put("exportByUser", exportByUser);
									params.put("perType", request.getParameter("perType"));
							%>
							<pager:pager name="PageResult" form="refForm"
								action="/web/exportData/queryExport.do" params="<%=params%>">
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
			<br>
		</form>
	</body>
</html>
