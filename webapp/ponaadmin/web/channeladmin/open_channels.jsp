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
		<title>��ѯ�������б�</title>
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
					�����̲�ѯ
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="queryOpenChannelsList.do?method=queryOpenChannelsList" method="post">
			<input type="hidden" name="actionType" value="add">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="right" class="text3">
						������ID��
					</td>
					<td width="40%" class="text4">
						<input type="text" name="channelsId" value="">
					</td>
					<td width="10%" align="right" class="text3">
						���������ƣ�
					</td>
					<td width="40%" class="text4">
						<input type="text" name="channelsName" value="">
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
							<b>�������б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="5%" align="center" class="title1">
							
						</td>
						<td width="10%" align="center" class="title1">
							������ID
						</td>
						<td width="15%" align="center" class="title1">
							����������
						</td>
						<td width="10%" align="center" class="title1">
							�������˺�
						</td>
						<td width="10%" align="center" class="title1">
							����������ID
						</td>
						<td width="10%" align="center" class="title1">
							����������
						</td>
						<td width="20%" align="center" class="title1">
							����ʱ��
						</td>
						<td width="10%" align="center" class="title1">
							״̬
						</td>
						<td width="10%" align="center" class="title1">
							
						</td>
					</tr>
					<logic:iterate id="vo" indexId="i" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO">
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>><!-- ���б�ɫ -->
						<td align="center">
							<input type="radio" name="radio" value="<%=vo.getChannelsId()%>">
						</td>
						<td align="center" >
							<a href="#" onclick="detail(<%=vo.getChannelsId()%>);"><%=vo.getChannelsId()%></a>
						</td>
						<td align="center">
						<bean:write name="vo" property="channelsName"/>
						
						</td>
						<td align="center">
							<%=vo.getChannelsNo()%>
						</td>
						<td align="center">
							<%=vo.getParentChannelsId()%>
						</td>
						<td align="center">
							<%=vo.getParentChannelsName()%>
						</td>
						<td align="center">
							<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getCreateDate())%>
						</td>
						<td align="center">
							<%=("1").equals(vo.getStatus())?"����":"����"%>
						</td>
						<td align="center">
							<% 
								if(("1").equals(vo.getStatus())){
							%>
							<input type="button" value="�ָ�" onclick="regain(<%=vo.getChannelsId()%>,<%=vo.getStatus()%>);">
							<% 
								}else{
							%>
							<input type="button" value="����" onclick="offline(<%=vo.getChannelsId()%>,<%=vo.getStatus()%>);">
							<%
								}
							%>
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
			                params.put("channelsNo",request.getParameter("channelsNo"));
			                params.put("parentChannelsId",request.getParameter("parentChannelsId"));
			                params.put("parentChannelsName",request.getParameter("parentChannelsName"));
			                params.put("channelsNo",request.getParameter("channelsNo"));
			                params.put("status",request.getParameter("status"));
							%>
							<pager:pager name="PageResult"
								action="/web/channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList" params="<%=params%>">
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
							<logic:notEmpty name="PageResult" property="pageInfo">
								<input type="button" value="��������Ϣ�༭" onclick="update();">
								<input type="button" value="�˺���������" onclick="updatePwd();">
							</logic:notEmpty>
								<input type="button"  value="����������" onclick="add();">
						</td>
					</tr>
				</table>
			
		</form>
		<script type="text/javascript" language="javascript">
			function add()
			{
				ContentForm.action = "queryOpenChannelsList.do?method=add";
				ContentForm.submit();
			}
			
			function detail(obj)
			{
			//channelsIdû���壬�Ȼ�ȡchannelsId��ֵ���ڴ�����̨
				ContentForm.action = "queryOpenChannelsList.do?method=list&channelsId="+obj;
				ContentForm.submit();
			}
			function update()
			{
				var boxes = document.getElementsByName("radio");
				var flag = false;
				var channelsId = null;
				for(var i=0;i<boxes.length;i++){
					if(boxes[i].checked){
						flag = true;
						channelsId = boxes[i].value;
						break;
					}
				}
				if(!flag){
					alert("��ѡ��һ����¼���в�����");
					return false;
				}
				ContentForm.action = "queryOpenChannelsList.do?method=update&channelsId="+channelsId;
				ContentForm.submit();
			}
			function updatePwd()
			{	
				var boxes = document.getElementsByName("radio");
				var flag = false;
				var channelsId = null;
				for(var i=0;i<boxes.length;i++){
					if(boxes[i].checked){
						flag = true;
						channelsId = boxes[i].value;
						break;
					}
				}
				if(!flag){
					alert("��ѡ��һ����¼���в�����");
					return false;
				}
				ContentForm.action = "queryOpenChannelsList.do?method=updatePwdChannels&channelsId="+channelsId;
				ContentForm.submit();
			}
			function offline(channelsId,status)
			{
				ContentForm.action = "queryOpenChannelsList.do?method=offLine&channelsId="+channelsId+"&status="+status;
				ContentForm.submit();
			}
			function regain(channelsId,status)
			{
				ContentForm.action = "queryOpenChannelsList.do?method=offLine&channelsId="+channelsId+"&status="+status;
				ContentForm.submit();
			}
		
		</script>
	</body>
</html>
