<%@page import="com.aspire.ponaadmin.web.auditManagement.vo.ChannelsInfoVO"%>
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
		<title>�����</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	     <form name="ContentForm" action="#" method="post">
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
			         bgcolor="#FFFFFF">
			       <tr>
				         <td align="center" bgcolor="#BFE8FF">
					      <b>����˲�ѯ</b>
				         </td>
			       </tr>
		       </table>
			   
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
					    <td width="15%" align="center" class="title1">
					    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(ContentForm,'checkbox',this.checked,false);"/>
					    ȫѡ
						</td>
						<td width="20%" align="center" class="title1">
							����������
						</td>
						<td width="15%" align="center" class="title1">
							������ID
						</td>
						<td width="20%" align="center" class="title1">
							��������
						</td>
						<td width="15%" align="center" class="title1">
							����ID
						</td>
						<td width="15%" align="center" class="title1">
							����
						</td>
					</tr>
					
					<logic:iterate id="vo" indexId="i" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.auditManagement.vo.ChannelsInfoVO">
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>><!-- ���б�ɫ -->
						<td align="center">
							<input type="checkbox" name="checkbox" value="<%=vo.getCategoryId()%>">
						</td>
						<td align="center">
							<%=vo.getChannelsName()%>
						</td>
						<td align="center">
							<%=vo.getChannelsId()%>
						</td>
						<td align="center">
							<%=vo.getCategoryName()%>
						</td>
						<td align="center">
							<%=vo.getCategoryId()%>
						</td>
						<td align="center">
							<a href="../toaudit/toAuditListAction.do?method=details&categoryId=<%=vo.getCategoryId() %>">�鿴����</a>
						</td>
					</tr>
					</logic:iterate>
					
				</table>
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<pager:pager name="PageResult"
								action="/web/toaudit/toAuditListAction.do?method=queryList">
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
						<input type="button" value="���ͨ��" onclick="auditPass();">
						<input type="button" value="��˾ܾ�" onclick="auditRefuse();">
					</td>
				</tr>
			</table>
			
			</logic:notEmpty>
		</form>
		<script language="javascript">
		function auditPass(){
			var checkbox = document.getElementsByName("checkbox");
			var flag = false;
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					flag = true;
					break;
				}
			}
			if(!flag){
				alert("��ѡ��һ����¼���в�����");
				return false;
			}
			ContentForm.action = "toAuditListAction.do?method=toAudit&flag=1";
			ContentForm.submit();
		}
		
		function auditRefuse(){
			var checkbox = document.getElementsByName("checkbox");
			var flag = false;
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					flag = true;
					break;
				}
			}
			if(!flag){
				alert("��ѡ��һ����¼���в�����");
				return false;
			}
			ContentForm.action = "toAuditListAction.do?method=toAudit&flag=2";
			ContentForm.submit();
		}
		
		</script>
	</body>
</html>
