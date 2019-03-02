<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
//String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String taskid = Validator.filter(request.getParameter("taskid")==null?"":request.getParameter("taskid"));
String categoryid = Validator.filter(request.getParameter("categoryid")==null?"":request.getParameter("categoryid"));
String beginDate = Validator.filter(request.getParameter("beginDate")==null?"":request.getParameter("beginDate"));
String endDate = Validator.filter(request.getParameter("endDate")==null?"":request.getParameter("endDate"));
String ip = Validator.filter(request.getParameter("ip")==null?"":request.getParameter("ip"));
String resultcount = Validator.filter(request.getParameter("resultcount")==null?"":request.getParameter("resultcount"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>稽核管理</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript">
		 function empty()
		 {
		 	form.taskid.value = "";
		 	form.categoryid.value = "";
		 	form.beginDate.value = "";
		 	form.endDate.value = "";
		 	form.ip.value = "";
		 	form.resultcount.value = "-1";
		 }
		</script>
	</head>

	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="form" action="checklog.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						稽核查询
						<input type="hidden" name="method" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						任务ID：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="taskid" value="<%=taskid%>" style="width:120px">
					</td>
					<td width="18%" align="right" class="text3">
						货架ID：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="categoryid" value="<%=categoryid%>" style="width:120px">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						机器IP：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="ip" value="<%=ip%>" style="width:120px">
					</td>
					<td width="18%" align="right" class="text3">
						比对结果：
					</td>
					<td width="30%" class="text4">
						<select name="resultcount" style="width:120px">
							<option value="-1" <%="-1".equals(resultcount)?"selected":"" %>>所有</option>
							<option value="1" <%="1".equals(resultcount)?"selected":"" %>>一致</option>
							<option value="0" <%="0".equals(resultcount)?"selected":"" %>>不一致</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="text3">
						执行开始时间：
					</td>
					<td width="30%" class="text4">
						<input name="beginDate" class="Wdate" type="text"  value="<%=beginDate%>"  style="width:120px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
					</td>
					<td width="20%" align="right" class="text3">
						执行结束时间：
					</td>
					<td width="30%" class="text4">
						<input name="endDate" class="Wdate" type="text"  value="<%=endDate%>"  style="width:120px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
					</td>
				</tr>
			
				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="查询">
						<input name="button" type="button" class="input1" value="重置" onclick="empty()">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<form name="refForm" action="checklog.do" method="post">
			<input type="hidden" name="method" value="">
			<input type="hidden" name="taskid" value="">
			<input type="hidden" name="setSortId" value="">
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
							<b>稽核日志列表</b>
						</td>
					</tr>
				</table>

				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr>

						<td width="10%" align="center" class="title1">
							任务ID
						</td>
						<td width="12%" align="center" class="title1">
							执行时间
						</td>
						<td width="6%" align="center" class="title1">
							货架ID
						</td>
						<td width="14%" align="center" class="title1">
							货架路径
						</td>
						<td width="17%" align="center" class="title1">
							对应Lucene索引
						</td>
						<td width="8%" align="center" class="title1">
							对应的机器IP
						</td>
						<td width="8%" align="center" class="title1">
							数据库记录数
						</td>
						<td width="8%" align="center" class="title1">
							Lucene中记录数
						</td>
						<td width="8%" align="center" class="title1">
							记录数比对结果
						</td>
						<td width="9%" align="center" class="title1">
							操 作
						</td>
					</tr>
					<logic:iterate id="vo" indexId="ind" name="PageResult"
						property="pageInfo"
						type="com.aspire.dotcard.audit.vo.CheckLogVO">
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
							<td align="center">
								<a href="../audit/checklog.do?method=queryDetail&taskid=<bean:write name="vo" property="taskid" />"><bean:write name="vo" property="taskid" /></a>
							</td>
							<td align="center">
								<bean:write name="vo" property="checktime"  format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td align="center">
								<bean:write name="vo" property="categoryid" />
							</td>
							<td align="center">
								<logic:notEmpty name="vo" property="categoryPath">货架根目录<bean:write name="vo" property="categoryPath" /></logic:notEmpty>
							</td>
							<td align="center">
								<bean:write name="vo" property="lucenepath" />
							</td>
							<td align="center">
					      		<bean:write name="vo" property="ip" />
							</td>
							<td align="center">
								<bean:write name="vo" property="dbcount" />
							</td>
							<td align="center">
								<bean:write name="vo" property="lucenecount" />
							</td>
							<td align="center">
								<logic:equal name="vo" property="resultcount"  value="1"><font color=blue>一 致</font></logic:equal>
								<logic:equal name="vo" property="resultcount"  value="0"><font color=red>不一致</font></logic:equal>
							</td>
							<td align="center">
								<a href="../audit/checklog.do?method=queryDetail&taskid=<bean:write name="vo" property="taskid" />">查看详情</a>
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
							        params.put("method", "query");
							        params.put("taskid", taskid);
							        params.put("categoryid", categoryid);
							        params.put("ip", ip);
							        params.put("resultcount", resultcount);
							        params.put("beginDate", beginDate);
							        params.put("endDate", endDate);
							%>
							<pager:pager name="PageResult"
								action="/web/audit/checklog.do" params="<%=params%>">
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

		</form>
		
	</body>
</html>
