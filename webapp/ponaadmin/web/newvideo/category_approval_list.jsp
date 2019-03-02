
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.dotcard.basevideosync.vo.CategoryApprovalVO"%>
<%@page
	import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant"%>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RoleVO"%>
<%@page
	import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.aspire.common.Validator"%>
<%
	PageResult pageResult = (PageResult) request
			.getAttribute("PageResult");
	List categoryApprovalList = pageResult.getPageInfo();
	String operation = (String)request.getAttribute("operation");

	String categoryId = Validator.filter(request
			.getParameter("categoryId") == null ? "" : request
			.getParameter("categoryId"));
	String categoryName = Validator.filter(request
			.getParameter("categoryName") == null ? "" : request
			.getParameter("categoryName"));
	String parentCategoryId = Validator.filter(request
			.getParameter("parentCategoryId") == null ? "" : request
			.getParameter("parentCategoryId"));
			
	String approvalStatus = (String)request.getAttribute("approvalStatus");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>POMS��Ʒ����-��������</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js">
</script>
		<script language="javascript">

function passApproval() {

	var categoryId = '';
	var operation = document.getElementById("operation").value;
	var obj = document.getElementsByName("id");
	if (obj != null) {
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				categoryId += obj[i].value + ",";
			}
		}
		if (categoryId == '' || categoryId.length <= 0) {
			alert("������ѡ��һ�����ܲ����ύ������");
			return;
		} else {
			//ȥ������Ķ���
			categoryId = categoryId.substring(0, categoryId.length - 1);
		}
	}
	if (window.confirm("ȷ�����ͨ����")) {
		window.location.href = "categoryApproval.do?method=pass&operation="+operation+"&categoryId="
				+ categoryId;
	} else {
		return;
	}
}
function noPassApproval() {
	var categoryId = '';
	var operation = document.getElementById("operation").value;
	var obj = document.getElementsByName("id");
	if (obj != null) {
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				categoryId += obj[i].value + ",";
			}
		}
		if (categoryId == '' || categoryId.length <= 0) {
			alert("������ѡ��һ�����ܲ����ύ������");
			return;
		} else {
			//ȥ������Ķ���
			categoryId = categoryId.substring(0, categoryId.length - 1);
		}

	}
	if (window.confirm("ȷ����˲�ͨ����")) {
		window.location.href = "categoryApproval.do?method=nopass&operation="+operation+"&categoryId="
				+ categoryId;
	} else {
		return;
	}

}
function showCategoryInfo(categoryID) {
	window
			.open("categoryTree.do?Type=query&categoryId=" + categoryID,
					"new",
					"width=600,height=600,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
}

function detail(id){
	 var isReflesh= window.showModalDialog("categoryApproval.do?method=query&categoryId=" + id,"modal","center:yes;dialogWidth:800px;dialogHeight:800px;resizable: yes; help: no; status: yes; scroll: auto; ");
	  if(isReflesh)
	  {
	    window.location.reload();
	  }
	
}

function selectAll(aForm,checkFieldName,checked)
{
  for(var i=0;i<aForm.length;i++)
  {
    if(aForm.elements[i].type=="checkbox")
    {
      if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
      {
    	  if(!aForm.elements[i].disabled)
             aForm.elements[i].checked = checked
      }	
    }
  }
}

</script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
		<br>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					��������
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<form name="queryUserForm" action="categoryApprovalList.do" method="post">
				<input name="operation" type="hidden" id="operation"
					value="<%=operation%>">
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
				</tr>
				<tr>
					<td align="right" class="text3">
						������ID��
					</td>
					<td class="text4">
						<input name="parentCategoryId" type="text" size="20"
							value="<%=parentCategoryId%>">
					</td>
					<td align="right" class="text3">
						����״̬��
					</td>
					<td class="text4">
						<select name="approvalStatus">
							<option value="2" <logic:equal value="2" name="approvalStatus"> selected</logic:equal>>
								������
							</option>
							<option value="0" <logic:equal value="0" name="approvalStatus"> selected</logic:equal>>
								�༭
							</option>
							<option value="1" <logic:equal value="1" name="approvalStatus"> selected</logic:equal>>
								�ѷ���
							</option>
							<option value="3" <logic:equal value="3" name="approvalStatus"> selected</logic:equal>>
								��ͨ��
							</option>
							<option value="-1" <logic:equal value="-1" name="approvalStatus"> selected</logic:equal>>
								ȫ��
							</option>
						</select>
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
		<form name="ContentForm2">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						��������
					</td>
				</tr>
			</table>
			<%
				if (categoryApprovalList.size() > 0) {
			%>

			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr align="center" class="title2">
					<td width="5%" align="center" class="title1">
						<input type="checkbox" value='' name="allSelect"
							onclick="selectAll(ContentForm2,'id',this.checked);" />
						ȫѡ
					</td>
					<td width="10%">
						����ID
					</td>
					<td width="15%">
						��������
					</td>
					<td width="10%">
						�����
					</td>
					<td width="8%">
						����״̬
					</td>
					<td width="15%">
						������ʱ�� 
					</td>
					<td width="12%">
						��������
					</td>
					<td width="15%">
						����ʱ��
					</td>
					<td width="12%">
						������
					</td>
				</tr>
				<%
					CategoryApprovalVO categoryApprovalVO = null;
						for (int i = 0; i < categoryApprovalList.size(); i++) {
							categoryApprovalVO = (CategoryApprovalVO) categoryApprovalList
									.get(i);
				%>
				<tr align="center" class="<%=(i % 2 == 0) ? "text5" : "text1"%>">
					<td>
						<input type="checkbox" name="id" 
						<%if(!"2".equals(categoryApprovalVO.getApprovalStatus())) {%>disabled="disabled"/<%} %>
							value="<%=categoryApprovalVO.getCategoryId()%>" />
					</td>
					<td>
						<a href="#"
							<logic:equal value="1" name="operation"> onclick="showCategoryInfo('<%=categoryApprovalVO.getCategoryId()%>')" </logic:equal>
							<logic:equal value="2" name="operation"> onclick="detail('<%=categoryApprovalVO.getCategoryId()%>')"  </logic:equal>><%=categoryApprovalVO.getCategoryId()%></a>
					</td>
					<td>
						<a href="#"
							<logic:equal value="1" name="operation"> onclick="showCategoryInfo('<%=categoryApprovalVO.getCategoryId()%>')" </logic:equal>
							<logic:equal value="2" name="operation"> onclick="detail('<%=categoryApprovalVO.getCategoryId()%>')"  </logic:equal>><%=categoryApprovalVO.getCategoryName()%></a>
					</td>
					<td><%=categoryApprovalVO.getSortId()%>
					</td>
					<td><%="0".equals(categoryApprovalVO
									.getApprovalStatus()) ? "�༭" : "1"
									.equals(categoryApprovalVO
											.getApprovalStatus()) ? "�ѷ���" : "2"
									.equals(categoryApprovalVO
											.getApprovalStatus()) ? "�����" : "3"
									.equals(categoryApprovalVO
											.getApprovalStatus()) ? "��ͨ��" : ""%>
					</td>
					<td><%=categoryApprovalVO.getOperator()%>
					</td>
					<td><%=categoryApprovalVO.getOperatorTime()%>
					</td>
					<td><%=categoryApprovalVO.getApproval()%>
					</td>
					<td><%=categoryApprovalVO.getApprovalTime()%>
					</td>

				</tr>
				<%
					}
				%>
			</table>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text1">
					<td align="right">
					<%
					HashMap params = new HashMap();
					params.put("categoryId", categoryId);
					params.put("categoryName", categoryName);
					params.put("parentCategoryId", parentCategoryId);
					params.put("approvalStatus", approvalStatus);
					params.put("operation", operation);
				%>
						<pager:pager name="PageResult"
							action="/web/newvideo/categoryApprovalList.do" params="<%=params%>">
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
							<input name="button2" type="button" class="input1"  value="��������"
								onclick="return passApproval();">
							&nbsp;&nbsp;
							<input name="button2" type="button" class="input1" value="������ͨ��"
								onclick="return noPassApproval();">
						</td>
					</tr>
				</table>
			<%
				} else {
			%>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text1">
					<td align="center">
						<font color="#ff0000">û���ҵ�����������������</fong>
					</td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
	</body>
</html>
