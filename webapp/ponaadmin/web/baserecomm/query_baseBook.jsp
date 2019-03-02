<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="com.aspire.ponaadmin.web.baserecomm.basebook.BaseBookVO"%>
<%@page import="java.util.*"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>��ѯ����ͼ����������</title>
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
					����ͼ���ѯ
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="baseBookQuery.do" method="post">
			<input type="hidden" name="actionType" value="add">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="right" class="text3">
						ͼ�����ƣ�
					</td>
					<td width="40%" class="text4">
						<input type="text" name="bookName"
							value="<bean:write name="bookName"/>">
					</td>
					<td width="10%" align="right" class="text3">
						���ߣ�
					</td>
					<td width="40%" class="text4">
						<input type="text" name="authorName"
							value="<bean:write name="authorName"/>">
					</td>
				</tr>
				<tr>
					<td width="10%" align="right" class="text3">
						���ࣺ
					</td>
					<td width="40%" class="text4">
						<input type="text" name="bookType"
							value="<bean:write name="bookType"/>">
					</td>
					<td width="10%" align="right" class="text3">
						�ؼ��֣�
					</td>
					<td width="40%" class="text4">
						<input type="text" name="key"
							value="<bean:write name="key"/>">
					</td>
				</tr>
				<tr>
					<td width="10%" align="right" class="text3">
						ͼ���飺
					</td>
					<td width="40%" class="text4" colspan="3">
						<input type="text" name="bookDesc"
							value="<bean:write name="bookDesc"/>">
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
							<b>����ͼ���б�</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="10%" align="center" class="title1">
							<input type="checkbox" value='' name="allSelect"
								onclick="selectAllCB(ContentForm,'selectBook',this.checked,false);" />
							ȫѡ
						</td>
						<td width="10%" align="center" class="title1">
							����ͼ��ID
						</td>
						<td width="20%" align="center" class="title1">
							ͼ������
						</td>
						<td width="10%" align="center" class="title1">
							����
						</td>
						<td width="10%" align="center" class="title1">
							����
						</td>
						<td width="40%" align="center" class="title1">
							ͼ����
						</td>
					</tr>
					<%
	                String desc = "";
	                PageResult pageRes = ( PageResult ) request.getAttribute("PageResult");
	                List list = pageRes.getPageInfo();

	                for (int i = 0; i < list.size(); i++)
	                {
	                    BaseBookVO vo = ( BaseBookVO ) list.get(i);

	                    desc = vo.getBookDesc();

	                    if (desc == null)
	                    {
	                        desc = "";
	                    }

	                    if (desc.length() > 20)
	                    {
	                        desc = desc.substring(0, 20) + "...";
	                    }
					%>
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>>
						<td align="center" style="word-break:break-all;">
							<input type="checkbox" value='<%=vo.getBookId()%>'
								name="selectBook" />

						</td>
						<td align="center">
							<%=vo.getBookId()%>
						</td>
						<td align="center">
							<%=vo.getBookName()%>
						</td>
						<td align="center">
							<%=vo.getAuthorName() %>
						</td>
						<td align="center">
							<%=vo.getBookType()%>
						</td>
						<td align="center">
							<%=desc%>
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
			                params.put("bookName", request.getParameter("bookName"));
			                params.put("authorName", request.getParameter("authorName"));
			                params.put("bookType", request.getParameter("bookType"));
			                params.put("key", request.getParameter("key"));
			                params.put("bookDesc", request.getParameter("bookDesc"));
							%>
							<pager:pager name="PageResult"
								action="/web/baserecomm/baseBookQuery.do" params="<%=params%>">
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
		</form>
		<script language="javascript">
		
		function addList()
		{
			var select = document.getElementsByName('selectBook');
			
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
				alert("��ѡ�����ͼ������");
				return false;
			}
			
			ContentForm.action = "baseBookTemp.do";
			ContentForm.submit();
		}
		
		</script>
	</body>
</html>
