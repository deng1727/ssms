<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="com.aspire.ponaadmin.web.baserecomm.basevideo.BaseVideoVO"%>
<%@page import="java.util.*"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>��ѯ������Ƶ��������</title>
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
					������Ƶ��ѯ
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="baseVideoQuery.do" method="post">
			<input type="hidden" name="actionType" value="add">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="right" class="text3">
						ר�����ƣ�
					</td>
					<td width="40%" class="text4">
						<input type="text" name="videoName"
							value="<bean:write name="videoName"/>">
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text5">
					<td align="center">
						<input type="submit" class="input1" name="submit01" value="��ѯ">
						<input type="button" class="input1" name="btn_reset" value="����"
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
							<b>������Ƶ�б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="10%" align="center" class="title1">
							<input type="checkbox" value='' name="allSelect"
								onclick="selectAllCB(ContentForm,'selectVideo',this.checked,false);" />
							ȫѡ
						</td>
						<td width="10%" align="center" class="title1">
							������ƵID
						</td>
						<td width="40%" align="center" class="title1">
							��Ƶ����
						</td>
						<td width="40%" align="center" class="title1">
							��ƵURL����
						</td>
					</tr>
					<%
	                PageResult pageRes = ( PageResult ) request.getAttribute("PageResult");
	                List list = pageRes.getPageInfo();

	                for (int i = 0; i < list.size(); i++)
	                {
	                    BaseVideoVO vo = ( BaseVideoVO ) list.get(i);
					%>
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>>
						<td align="center" style="word-break:break-all;">
							<input type="checkbox" value='<%=vo.getContentId()%>'
								name="selectVideo" />
						</td>
						<td align="center">
							<%=vo.getContentId()%>
						</td>
						<td align="center">
							<%=vo.getVideoName()%>
						</td>
						<td align="center">
							<%=vo.getVideoURL()%>
						</td>
					</tr>
					<%
					}
					%>
				</table>
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<%
			                HashMap params = new HashMap();
			                params.put("videoName", request.getParameter("videoName"));
							%>
							<pager:pager name="PageResult"
								action="/web/baserecomm/baseVideoQuery.do" params="<%=params%>">
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

				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input type="button" value="���������" onclick="addList();">
						</td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input type="button" value="����������Ƶ��Ϣ" onclick="addDataList();">
						</td>
					</tr>
				</table>
		</form>
		<script language="javascript">
		function addList()
		{
			var select = document.getElementsByName('selectVideo');
			
			var isSelect = false;
			
			for(var i=0; i< select.length; i++)
			{
				if(select[i].checked == true)
				{
					isSelect = true;
					break;
				}
			}
			
			if(isSelect == false)
			{
				alert("��ѡ�������Ƶ����");
				return false;
			}
			
			ContentForm.action = "baseVideoTemp.do";
			ContentForm.submit();
		}
		
		function addDataList()
		{
			ContentForm.action = "add_baseVideo.jsp";
			ContentForm.submit();
		}
		</script>
	</body>
</html>
