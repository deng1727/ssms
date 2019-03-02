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
		<form name="ContentForm" action="baseGameTemp.do" method="post">
			<input type="hidden" name="actionType" value="del">
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
				<br>
				<br>
				<br>
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
						<td width="5%" align="center" class="title1">
							<input type="checkbox" value='' name="allSelect"
								onclick="selectAllCB(ContentForm,'selectBook',this.checked,false);" />
							ȫѡ
						</td>
						<td width="10%" align="center" class="title1">
							����ͼ��ID
						</td>
						<td width="15%" align="center" class="title1">
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
						<td width="10%" align="center" class="title1">
							����״̬
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
						<td align="center">
							<%
							if("ʧЧ����".equals(vo.getValidityData()))
							{
							    %>
							    <font color="red"><%=vo.getValidityData()%></font> 
							    <%
							}
							else
							{
							    out.print(vo.getValidityData());
							}
							%>
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
							%>
							<pager:pager name="PageResult"
								action="/web/baserecomm/baseBookTemp.do" params="<%=params%>">
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
							<input type="button" value="�ӻ���ɾ��" onclick="delList();">
							<input type="button" value="�����ļ�" onclick="toFile();">
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</form>
		<script language="javascript">
		function delList()
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
		
		function toFile()
		{
			if(!confirm('ȷ����������Ҫ�����ļ���'))
			{
			   return false;
			}
			ContentForm.action = "baseBookTemp.do?actionType=toFile";
			ContentForm.submit();
		}
		</script>
	</body>
</html>
