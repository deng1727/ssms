<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
String taskid = Validator.filter(request.getParameter("taskid")==null?"":request.getParameter("taskid"));
String categoryid = Validator.filter(request.getParameter("categoryid")==null?"":request.getParameter("categoryid"));
String beginDate = Validator.filter(request.getParameter("beginDate")==null?"":request.getParameter("beginDate"));
String endDate = Validator.filter(request.getParameter("endDate")==null?"":request.getParameter("endDate"));
String ip = Validator.filter(request.getParameter("ip")==null?"":request.getParameter("ip"));
String statusinfo = Validator.filter(request.getParameter("statusinfo")==null?"":request.getParameter("statusinfo"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>��������鿴</title>
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
		 	form.statusinfo.value = "";
		 }
		</script>
	</head>

	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="form" action="checklog.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						���������ѯ
						<input type="hidden" name="method" value="queryDetail">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						����ID��
					</td>
					<td width="30%" class="text4">
						<input type="text" name="taskid" value="<%=taskid%>" style="width:120px">
					</td>
					<td width="18%" align="right" class="text3">
						����ID��
					</td>
					<td width="30%" class="text4">
						<input type="text" name="categoryid" value="<%=categoryid%>" style="width:120px">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						����IP��
					</td>
					<td width="30%" class="text4">
						<input type="text" name="ip" value="<%=ip%>" style="width:120px">
					</td>
					<td width="18%" align="right" class="text3">
						״̬��Ϣ��
					</td>
					<td width="30%" class="text4">
						<select name="statusinfo" style="width:120px">
							<option value="" <%="".equals(statusinfo)?"selected":"" %>>����</option>
							<option value="1" <%="1".equals(statusinfo)?"selected":"" %>>LUCENE�д��ڣ����ݿ��в�����</option>
							<option value="2" <%="2".equals(statusinfo)?"selected":"" %>>LUCENE�в����ڣ����ݿ��д���</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right" class="text3">
						ִ�п�ʼʱ�䣺
					</td>
					
					<td width="30%" class="text4">
				
						<input name="beginDate" class="Wdate" type="text"  value="<%=beginDate%>"  style="width:120px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
					</td>
					
					<td width="20%" align="right" class="text3">
						ִ�н���ʱ�䣺
					</td>
					<td width="30%" class="text4">
						<input name="endDate" class="Wdate" type="text"  value="<%=endDate%>"  style="width:120px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
					</td>
				</tr>
			
				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="��ѯ">
						<input name="button" type="button" class="input1" value="����" onclick="empty()">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<form name="refForm" action="checklog.do" method="post">
			<input type="hidden" name="method" value="queryDetail">
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
							<b>���������б�</b>
						</td>
					</tr>
				</table>

				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr>
						
						<td width="10%" align="center" class="title1">
							����ID
						</td>
						<td width="15%" align="center" class="title1">
							ִ��ʱ��
						</td>
						<td width="6%" align="center" class="title1">
							����ID
						</td>
						<td width="14%" align="center" class="title1">
							����·��
						</td>
						<td width="17%" align="center" class="title1">
							��ӦLucene����
						</td>
						<td width="8%" align="center" class="title1">
							��Ӧ�Ļ���IP
						</td>
						<td width="10%" align="center" class="title1">
							PKIDֵ
						</td>
						<td width="20%" align="center" class="title1">
							״̬��Ϣ
						</td>

					</tr>
					<logic:iterate id="vo" indexId="ind" name="PageResult"
						property="pageInfo"
						type="com.aspire.dotcard.audit.vo.CheckDetailVO">
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
								<bean:write name="vo" property="taskid" />
							</td>
							<td align="center">
								<bean:write name="vo" property="checktime"  format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td align="center">
								<bean:write name="vo" property="categoryid" />
							</td>
							<td align="center">
								<logic:notEmpty name="vo" property="categoryPath">���ܸ�Ŀ¼<bean:write name="vo" property="categoryPath" /></logic:notEmpty>
							</td>
							<td align="center">
								<bean:write name="vo" property="lucenepath" />
							</td>
							<td align="center">
					      		<bean:write name="vo" property="ip" />
							</td>
							<td align="center">
								<bean:write name="vo" property="pkid" />
							</td>
							<td align="center">
								<logic:equal name="vo" property="statusinfo"  value="1">LUCENE�д��ڣ����ݿ��в�����</logic:equal>
								<logic:equal name="vo" property="statusinfo"  value="2">LUCENE�в����ڣ����ݿ��д���</logic:equal>
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
							        params.put("method", "queryDetail");
							        params.put("taskid", taskid);
							        params.put("categoryid", categoryid);
							        params.put("ip", ip);
							        params.put("statusinfo", statusinfo);
							        params.put("beginDate", beginDate);
							        params.put("endDate", endDate);
							%>
							<pager:pager name="PageResult"
								action="/web/audit/checklog.do" params="<%=params%>">
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

		</form>
		
	</body>
</html>
