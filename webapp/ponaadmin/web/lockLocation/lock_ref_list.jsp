<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String categoryId = request
			.getParameter("categoryId") == null ? "" : request
			.getParameter("categoryId");	
	PageResult pageResult = (PageResult) request
			.getAttribute("PageResult");
	List lockRefList = pageResult.getPageInfo();
	String nodeId = request
			.getParameter("nodeId") == null ? "" : request
			.getParameter("nodeId");
	String contentId = Validator.filter(request
			.getParameter("contentId") == null ? "" : request
			.getParameter("contentId"));
	String name = Validator.filter(request
			.getParameter("name") == null ? "" : request
			.getParameter("name"));
	String lockNums = (String)request.getAttribute("lockNums") == null ? "" : (String)request
			.getAttribute("lockNums");
			
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

 function SetLockNums()
  {
   var i=0;var sortid=0;
   var changedValues="";
   var isFirst=true;
   
   var Taxis=document.getElementsByName("lockNum");
   
   var dealContent=document.getElementsByName("id");
   var lockNums=document.getElementById("id_LockNums").value;
   var pattern=/\D+/;
   //debugger��
    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO">
      sortid=<bean:write name="vo" property="lockNum"/>;
      
      if(!mod_click(Taxis[i]))
      {
        Taxis[i].select();
        Taxis[i].focus();
        return false;
      }
      if(sortid!=Taxis[i].value)
      {
      var str= new Array();
   		str = lockNums.split(',');
   		for(var j=0;j<str.length;j++){
     		if(Taxis[i].value==str[j]){
     		alert('λ��'+str[j]+'�Ѿ��������ˣ���������������λ�ã�');
     		return false;
     	}
   	}
   	if(pattern.test(Taxis[i].value)){
  	alert("��������ȷ��λ�á�");
	return;
  }
        if(isFirst)
       {
        changedValues=changedValues+dealContent[i].value+"#"+Taxis[i].value;
        isFirst=false;
       }else
       {
         changedValues+=":"+dealContent[i].value+"#"+Taxis[i].value;
       }
      }

      i++;
     </logic:iterate>
     if(changedValues=="")
     {
       alert("���޸�����һ�������");
     }else
     {
     var contentForm=document.getElementById("contentForm");
     document.getElementById('changedValues').value=changedValues;
      contentForm.action="<%=request.getContextPath()%>/web/lockLocation/modifyLockLocationAction.do";
      contentForm.submit();
     }
     
     
  }
  function mod_click(taxis)
{
  if(taxis.value=="")
  { 
   alert("�������������");
   return false;
  }
  if(!JudgeReal2(taxis,"�������",9))
  {
	return false;
  }
	
  if(taxis.value < 1)
  {
     alert("Ҫ������λ�ñ������0��");
     return false; 
  }
  return true;

}
function remove() {
	var rId = '';
	var obj = document.getElementsByName("id");
	if (obj != null) {
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				rId += obj[i].value + ",";
			}
		}
		if (rId == '' || rId.length <= 0) {
			alert("������ѡ��һ��Ӧ�ò��ܽ��н������������");
			return;
		} else {
			//ȥ������Ķ���
			rId = rId.substring(0, rId.length - 1);
		}

	}
	if (window.confirm("ȷ��Ҫ�����")) {
		window.location.href = "removeLockLocationAction.do?nodeId=<%=nodeId%>"
				+"&rId="+rId+"&removeType=2";
	} else {
		return;
	}

}

function addContent()
{
 var lockNums=document.getElementById("id_LockNums").value;
var obj = new Object(); 
obj.lockNums=lockNums; 
//window.open("contentListAction.do?categoryId=<%=categoryId%>&nodeId=<%=nodeId%>&lockNums=<%=lockNums%>","new","width=600,height=600,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
 var isReflesh=window.showModalDialog("contentListAction.do?subSystem=ssms&categoryId=<%=categoryId%>&nodeId=<%=nodeId%>",obj,"center:yes;dialogWidth:700px;dialogHeight:800px;resizable: yes; help: no; status: yes; scroll: auto; ");
  if(isReflesh)
  {
    window.location.reload();
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
					������Ʒ�����б�
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<form name="queryUserForm"
				action="lockRefListAction.do"
				method="post" onSubmit="return checkForm();">
				<input type="hidden" name="nodeId" id="id_nodeId" value="<%=nodeId %>"/>
				<input type="hidden" name="categoryId" id="id_categoryId" value="<%=categoryId %>"/>
				<input type="hidden" name="lockNums" id="id_LockNums" value="<%=lockNums %>"/>
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
		<form name="ContentForm" id="contentForm">
		<input type="hidden" name="changedValues" id="changedValues">
		<input type="hidden" name="nodeId" value="<%=nodeId %>">
		<input type="hidden" name="categoryId" value="<%=categoryId %>">
		<input type="hidden" name="lockNums" id="id_LockNums2" value="<%=lockNums %>"/>
		<input type="hidden" name="removeType" value="2">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						������Ʒ�����б�
					</td>
				</tr>
			</table>
			<%
				if (lockRefList.size() > 0) {
			%>

			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr align="center" class="title2">
					<td width="10%" align="center" class="title1">
						<input type="checkbox" value='' name="allSelect"
							onclick="selectAllCB(ContentForm,'id',this.checked,false);" />
						ȫѡ
					</td>
					<td width="20%">
						Ӧ��ID
					</td>
					<td width="20%">
						Ӧ������
					</td>
					<td width="15%">
						����λ��
					</td>
				</tr>
				<%
					RefrenceVO refrenceVO = null;
						for (int i = 0; i < lockRefList.size(); i++) {
							refrenceVO = (RefrenceVO) lockRefList
									.get(i);
				%>
				<tr align="center" class="<%=(i % 2 == 0) ? "text5" : "text1"%>">
					<td>
						<input type="checkbox" name="id"
							value="<%=refrenceVO.getId()%>" />
					</td>
					<td>
						 <%=refrenceVO.getContentId()%>
					</td>
					<td>
						 <%=refrenceVO.getName()%>
					</td>
					<td>
						<input type="text" name = "lockNum" value="<%=refrenceVO.getLockNum()%>"> 
					</td>

				</tr>
				<%
					}
				%>
			</table>
			<%} %>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text1">
					<td align="right">
					<%
					HashMap params = new HashMap();
					params.put("nodeId", nodeId);
					params.put("categoryId", categoryId);
					params.put("contentId", contentId);
					params.put("name", name);
				%>
						<pager:pager name="PageResult"
							action="/web/lockLocation/lockRefListAction.do" params="<%=params%>">
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
							<input name="button2" type="button" class="input1" value="���"
								onclick="return addContent();">
							&nbsp;&nbsp;
							<input name="button2" type="button" class="input1" value="ɾ��"
								onclick="return remove();">
								&nbsp;&nbsp;
							<input name="button2" type="button" class="input1" value="��������λ��"
								onclick="return SetLockNums();">
						</td>
					</tr>
				</table>
		</form>
	</body>
</html>
