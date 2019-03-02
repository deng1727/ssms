<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelMoVo"%>
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
		<title>��ѯ������Ӫ�����б�</title>
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
					<b>������Ӫ������ѯ</b>
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="openOperationList.do?method=list" method="post">
			<input type="hidden" name="channelsId" value="<%=request.getAttribute("channelsId")%>">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="20%" align="right" class="text3">
						������Ӫ����ID��
					</td>
					<td width="80%" class="text4">
						<input type="text" name="channelId"
							value="<bean:write name="channelId"/>">
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
							<b>������Ӫ�����б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="50%" align="center" class="title1">
							������Ӫ����ID
						</td>
						<td width="30%" align="center" class="title1">
							����ʱ��
						</td>
						<td width="20%" align="center" class="title1">
							����
						</td>
					</tr>
					<logic:iterate id="vo" indexId="i" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.channeladmin.vo.OpenOperationChannelVo">
						<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>>
							<td align="center">
								<%=vo.getChannelId()%>
							</td>
							<td align="center">
								<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getCreateDate())%>
							</td>
							<td align="center">
								<a style="color: #0000FF" href="#" onclick="delOperation(<%=vo.getChannelId()%>,<%=vo.getChannelsId()%>)">ɾ��</a>
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
			                params.put("channelId", request.getParameter("channelId"));
			                params.put("channelsId", request.getParameter("channelsId"));
							%>
							<pager:pager name="PageResult"
								action="/web/channeladmin/openOperationList.do?method=list" params="<%=params%>">
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
					<td align="center">
						<input type="button" value="��ӿ�����Ӫ��������" onclick="addOperation(<%=request.getAttribute("channelsId")%>);">
						<input type="button" value="����" onclick="back();">
					</td>
				</tr>
			</table>
		</form>
		<script language="javascript">
		
		function addOperation(channelsId)
		{
			ContentForm.action = "openOperationList.do?method=add&channelsId="+channelsId;
			ContentForm.submit();
		}
		
		function delOperation(channelId,channelsId){
			if(confirm("ȷ��ɾ���������ݣ�")){
				ContentForm.action = "openOperationList.do?method=del&channelId="+channelId+"&channelsId="+channelsId;
				ContentForm.submit();
			}
		}
		
		function back(){
			window.history.back(-1);
		}
		</script>
	</body>
</html>
