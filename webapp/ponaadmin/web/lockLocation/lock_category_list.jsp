<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.lockLocation.vo.LockLocationVO"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
	PageResult pageResult = (PageResult) request
			.getAttribute("PageResult");
	List lockLocationList = pageResult.getPageInfo();
	String nodeId = Validator
			.filter(request.getParameter("nodeId") == null ? ""
					: request.getParameter("nodeId"));
	String categoryId = Validator.filter(request
			.getParameter("categoryId") == null ? "" : request
			.getParameter("categoryId"));
	String categoryName = Validator.filter(request
			.getParameter("categoryName") == null ? "" : request
			.getParameter("categoryName"));

	String isLock = (String) request.getAttribute("isLock");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>wwwportal�Ż�����ϵͳ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js">
</script>
		<script language="javascript">

function lock() {
	var nodeId = '';
	var categoryId='';
	var obj = document.getElementsByName("id");
	var count=0;
	if (obj != null) {
		for (i=0;i < obj.length; i++) {
			if (obj[i].checked) {
				count++;
				var str = new Array();
				str = obj[i].value.split('|');
				nodeId = str[0];
				categoryId = str[1];
			}
		}
		if (nodeId == '' || nodeId.length <= 0) {
			alert("��ѡ��һ�����ܲ��ܽ�������������");
			return;
		} else if(count>1){
			alert("���ֻ��ѡ��һ�����ܽ�������������");
			return;
			//ȥ������Ķ���
			//nodeId = nodeId.substring(0, nodeId.length - 1);
		}
	}
	window.location.href = "lockRefListAction.do?categoryId="+categoryId+"&nodeId=" + nodeId;
}
function removeLock() {
	var nodeId = '';
	var obj = document.getElementsByName("id");
	if (obj != null) {
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				var str = new Array();
				str = obj[i].value.split('|');
				nodeId += str[0] + ",";
			}
		}
		if (nodeId == '' || nodeId.length <= 0) {
			alert("����ѡ��һ�����ܲ��ܽ��н������������");
			return;
		} else {
			//ȥ������Ķ���
			nodeId = nodeId.substring(0, nodeId.length - 1);
		}

	}
	if (window.confirm("������������Ὣѡ�������µ���������������Ʒ�¼ܣ�ȷ��Ҫ�����")) {
		window.location.href = "removeLockLocationAction.do?nodeId=" + nodeId+"&removeType=1";
	} else {
		return;
	}

}
function showCategoryInfo(categoryID) {
	var isReflesh = window
			.showModalDialog(
					"categoryInfo.do?approvalFlag=yes&Popup=Popup&categoryID="
							+ categoryID, "new",
					"width=600,height=600,toolbar=no,scrollbars=yes,menubar=no,resizable=true ");
	if (isReflesh) {
		window.location.reload();
	}
//window.open("categoryInfo.do?approvalFlag=yes&Popup=Popup&categoryID=" + categoryID,"new","width=600,height=600,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
}

</script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
		<br>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					������Ʒ�����б�
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<form name="queryUserForm" action="lockLocationListAction.do"
				method="post" onSubmit="return checkForm();">
				<tr>
					<td width="18%" align="right" class="text3">
						����ID(����÷ֺŷָ�)��
					</td>
					<td class="text4">
						<input name="categoryId" type="text" size="20"
							value="<%=categoryId%>">
					</td>
					<td align="right" class="text3">
						�������ƣ�
					</td>
					<td class="text4">
						<input name="categoryName" type="text" size="20"
							value="<%=categoryName%>">
					</td>
					<!--  
					<td align="right" class="text3">
						�Ƿ�������
					</td>
					<td class="text4">
						<select name="isLock">
							<option value="-1">
								ȫ��
							</option>
							<option value="1" <%if ("1".equals(isLock)) {%>
								selected="selected" <%}%>>
								��
							</option>
							<option value="0" <%if ("0".equals(isLock)) {%>
								selected="selected" <%}%>>
								��
							</option>
						</select>
					</td>
					-->
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
		<form name="ContentForm2">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						������Ʒ�����б�
					</td>
				</tr>
			</table>
			<%
				if (lockLocationList.size() > 0) {
			%>

			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr align="center" class="title2">
					<td width="10%" align="center" class="title1">
						<input type="checkbox" value='' name="allSelect"
							onclick="selectAllCB(ContentForm2,'id',this.checked,false);" />
						ȫѡ
					</td>
					<td width="20%">
						����ID
					</td>
					<td width="20%">
						��������
					</td>
					<td width="15%">
						�����
					</td>
					<td width="20%">
						����������ʱ��
					</td>
					<td width="15%">
						����״̬
					</td>
				</tr>
				<%
					LockLocationVO lockLocationVO = null;
						for (int i = 0; i < lockLocationList.size(); i++) {
							lockLocationVO = (LockLocationVO) lockLocationList.get(i);
				%>
				<tr align="center" class="<%=(i % 2 == 0) ? "text5" : "text1"%>">
					<td>
						<input type="checkbox" name="id"
							value="<%=(lockLocationVO.getId()+"|"+lockLocationVO.getCategoryId())%>" />
					</td>
					<td>
						<a href="#"
							onclick="showCategoryInfo('<%=lockLocationVO.getId()%>')"> <%=lockLocationVO.getCategoryId()%></a>
					</td>
					<td>
						<a href="#" onclick="showCategoryInfo('<%=lockLocationVO.getId()%>')"> <%=lockLocationVO.getCategoryName()%></a>
					</td>
					<td><%=lockLocationVO.getSortId()%>
					</td>
					<td>
						<%=lockLocationVO.getLockTime()%>
					</td>
					<td>
						<%="1".equals(lockLocationVO.getIsLock()) ? "��"
							: "��"%>
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
							params.put("nodeId", nodeId);
							params.put("categoryId", categoryId);
							params.put("categoryName", categoryName);
							params.put("isLock", isLock);
						%>
						<pager:pager name="PageResult"
							action="/web/lockLocation/lockLocationListAction.do"
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
						<input name="button2" type="button" class="input1" value="����"
							onclick="return lock();">
						&nbsp;&nbsp;
						<input name="button2" type="button" class="input1" value="�������"
							onclick="return removeLock();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
