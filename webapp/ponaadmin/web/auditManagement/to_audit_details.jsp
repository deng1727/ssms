<%@page import="com.aspire.ponaadmin.web.auditManagement.vo.AuditDetailsVO"%>
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
		<title>待审核</title>
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
							<font color="#ff0000">没有找到任何记录。</font>
						</td>
					</tr>
				</table>
			</logic:empty>

			<logic:notEmpty name="PageResult" property="pageInfo">
			     <table width="95%" border="0" align="center" cellspacing="3"
			         bgcolor="#FFFFFF">
			       <tr>
				         <td align="center" bgcolor="#BFE8FF">
					      <b>待审核查询</b>
				         </td>
			       </tr>
		       </table>
			   
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
					    <td width="5%" align="center" class="title1">
						</td>
						<td width="10%" align="center" class="title1">
							应用ID
						</td>
						<td width="15%" align="center" class="title1">
							名称
						</td>
						<td width="20%" align="center" class="title1">
							提供商
						</td>
						<td width="15%" align="center" class="title1">
							内容类型
						</td>
						<td width="20%" align="center" class="title1">
							上线时间
						</td>
						<td width="20%" align="center" class="title1">
							提交审核时间
						</td>
					</tr>
					
					<logic:iterate id="vo" indexId="i" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.auditManagement.vo.AuditDetailsVO">
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>><!-- 隔行变色 -->
						<td align="center">
							<input type="checkbox" name="checkbox" value="<%=vo.getCategoryId() %>/<%=vo.getRefNodeId()%>">
						</td>
						<td align="center">
							<%=vo.getContentId()%>
						</td>
						<td align="center">
							<%=vo.getName()%>
						</td>
						<td align="center">
							<%=vo.getSpName()%>
						</td>
						<td align="center">
							<%=vo.getCateName()%>
						</td>
						<td align="center">
							<%=vo.getMarketDate()%>
						</td>
						<td align="center">
							<%=vo.getLoadDate()%>
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
			                params.put("categoryId", request.getParameter("categoryId"));
							%>
							<pager:pager name="PageResult"
								action="/web/toaudit/toAuditListAction.do?method=details" params="<%=params%>">
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
				
				<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<td align="center">
						<input type="button" value="审核通过" onclick="auditPass();">
						<input type="button" value="审核拒绝" onclick="auditRefuse();">
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
				alert("请选择一条记录进行操作！");
				return false;
			}
			ContentForm.action = "toAuditListAction.do?method=audit&flag=1";
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
				alert("请选择一条记录进行操作！");
				return false;
			}
			ContentForm.action = "toAuditListAction.do?method=audit&flag=2";
			ContentForm.submit();
		}
		
		</script>
	</body>
</html>
