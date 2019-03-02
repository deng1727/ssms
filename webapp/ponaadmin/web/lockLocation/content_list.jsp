<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.lockLocation.vo.ContentVO"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
	PageResult pageResult = (PageResult) request
			.getAttribute("PageResult");
	List contentList = pageResult.getPageInfo();
	String categoryId = Validator.filter(request
			.getParameter("categoryId") == null ? "" : request
			.getParameter("categoryId"));
	String contentId = Validator.filter(request
			.getParameter("contentId") == null ? "" : request
			.getParameter("contentId"));		
	String nodeId = Validator.filter(request
			.getParameter("nodeId") == null ? "" : request
			.getParameter("nodeId"));		
	String name = Validator.filter(request
			.getParameter("name") == null ? "" : request
			.getParameter("name"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>wwwportal�Ż�����ϵͳ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js">
</script>
		<script language="javascript">
function add()
{
var obj = window.dialogArguments ;
var lockNums = obj.lockNums;
  var contentId = '';
  var obj = document.getElementsByName("id");
  if (obj != null) {
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				contentId = obj[i].value;
			}
		}
		if (contentId == '' || contentId.length <= 0) {
			alert("��ѡ��Ҫ��ӵ�Ӧ�á�");
			return;
		} 
	}
	var lockNum = document.getElementById('lockNum').value;
  var pattern=/\D+/;
  //||!reg.test(lockNum)
  if(pattern.test(lockNum)){
  	alert("��������ȷ��λ�á�");
	return;
  }
  var str= new Array();
  str = lockNums.split(',');
  for(var i=0;i<str.length;i++){
     if(lockNum==str[i]){
     	alert('λ��'+str[i]+'�Ѿ��������ˣ���������������λ�ã�');
     	return false;
   	}
   }
   window.dialogArguments.lockNums=lockNums+','+lockNum;
	var contentForm=document.getElementById("contentForm2");
	document.getElementById('id_contentId').value=contentId;
  contentForm.action="addLockContentAction.do?isClose=true";
  contentForm.submit();
}

</script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
		<br>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					Ӧ���б�
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<form name="queryUserForm" action="contentListAction.do"
				method="post" onSubmit="return checkForm();">
				<input type="hidden" name="nodeId" value="<%=nodeId %>">
				<input type="hidden" name="categoryId" value="<%=categoryId %>">
				<tr>
					<td width="18%" align="right" class="text3">
						Ӧ��ID��
					</td>
					<td class="text4">
						<input name="contentId" type="text" size="20"
							value="<%=contentId%>">
					</td>
					<td align="right" class="text3">
						Ӧ�����ƣ�
					</td>
					<td class="text4">
						<input name="name" type="text" size="20"
							value="<%=name%>">
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit2" type="submit" class="input1" value="��ѯ">
						<input name="Submit22" type="reset" class="input1" value="����">
					</td>
				</tr>
			</form>
		</table>
		<br>
		<form name="ContentForm2" name = "ContentForm2">
		<input type="hidden" name="nodeId" value="<%=nodeId %>">
		<input type="hidden" name="categoryId" value="<%=categoryId %>">
		<input type="hidden" name="contentId" id="id_contentId">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						������Ʒ�����б�
					</td>
				</tr>
			</table>
			<%
				if (contentList.size() > 0) {
			%>

			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr align="center" class="title2">
					<td width="10%" align="center" class="title1">
						
					</td>
					<td width="20%">
						Ӧ��ID
					</td>
					<td width="20%">
						Ӧ������
					</td>
				</tr>
				<%
					ContentVO contentVO = null;
						for (int i = 0; i < contentList.size(); i++) {
							contentVO = (ContentVO) contentList.get(i);
				%>
				<tr align="center" class="<%=(i % 2 == 0) ? "text5" : "text1"%>">
					<td>
						<input type="radio" name="id"
							value="<%=contentVO.getId()%>" />
					</td>
					<td>
						 <%=contentVO.getContentId()%>
					</td>
					<td>
						 <%=contentVO.getName()%>
					</td>

				</tr>
				<%
					}
				%>
			</table>
			<%
				}
			%>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text1">
					<td align="right">
						<%
							HashMap params = new HashMap();
							params.put("categoryId", categoryId);
							params.put("nodeId", nodeId);
							params.put("contentId", contentId);
							params.put("name", name);
						%>
						<pager:pager name="PageResult"
							action="/web/lockLocation/contentListAction.do"
							params="<%=params%>">
							<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="2" />
						</pager:pager>
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text1" align="center">
					<td align="center">
						����λ�ã�<input type="text" name = "lockNum" id="lockNum">
						
						<input name="button2" type="button" class="input1" value="���"
							onclick="return add();">
						&nbsp;&nbsp;
						<input name="button2" type="button" class="input1" value="�ر�"
							onClick="window.close();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
