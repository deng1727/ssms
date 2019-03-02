<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String programId = Validator.filter(request.getParameter("programId")==null?"":request.getParameter("programId"));
String programName = Validator.filter(request.getParameter("programName")==null?"":request.getParameter("programName"));
String cmsId = Validator.filter(request.getParameter("cmsId")==null?"":request.getParameter("cmsId"));
String subcateName = Validator.filter(request.getParameter("subcateName")==null?"":request.getParameter("subcateName"));
String keyName = Validator.filter(request.getParameter("keyName")==null?"":request.getParameter("keyName"));

Map<String,Object> map = (Map)request.getAttribute("categoryContent");
String approvalStatus = (String)request.getAttribute("approvalStatus");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>������Ʒ����-��������</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
function approvalRelease(){	
	var bool = true;
	var dealRefs = document.getElementsByName("dealRef");
	for(var i = 0;i < dealRefs.length;i++){
		if(dealRefs[i].checked  == true){
			bool = false;
		}
	}
	
	if(bool){
		alert("��ѡ����Ҫ��������������");
		return;
	}
	
	refForm.action="<%=request.getContextPath()%>/web/newvideo/categoryApproval.do?method=accept&categoryId=<%=categoryId%>";
	refForm.submit();
	
}

function approvalRefuse(){
	var bool = true;
	var dealRefs = document.getElementsByName("dealRef");
	for(var i = 0;i < dealRefs.length;i++){
		if(dealRefs[i].checked  == true){
			bool = false;
		}
	}
	
	if(bool){
		alert("��ѡ����Ҫ������ͨ��������");
		return;
	}
	refForm.action="<%=request.getContextPath()%>/web/newvideo/categoryApproval.do?method=refuse&categoryId=<%=categoryId%>";
	refForm.submit();
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

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="categoryApproval.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ʒ��ѯ
      <input type="hidden" id="categoryId" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="method" value="query">
    </td>
  </tr>
  <tr>
  	<td width="18%" align="right" class="text3">��������ؼ��֣�
    </td>
    <td width="30%" class="text4"><input type="text" name="keyName" value="<%=keyName%>">
    </td>
    <td width="48%" align="left" class="text3" colspan="2">
    	<input type="radio" id = "subcateName" name="subcateName" value="�������" style="margin-left:50px;" <logic:equal value="�������" name="subcateName"> checked</logic:equal> />������� &nbsp; &nbsp; <input type="radio" id = "subcateName" name="subcateName" value="���Ҽ�����" <logic:equal value="���Ҽ�����" name="subcateName"> checked</logic:equal>/>���Ҽ�����<br/>
    	<input type="radio" id = "subcateName" name="subcateName" value="��������" style="margin-left:50px;" <logic:equal value="��������" name="subcateName"> checked</logic:equal> />�������� &nbsp; &nbsp;<input type="radio" id = "subcateName" name="subcateName" value="������̬" <logic:equal value="������̬" name="subcateName"> checked</logic:equal>/>������̬<br/>
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">��ĿID��
    </td>
    <td width="30%" class="text4"><input type="text" name="programId" value="<%=programId%>"></td>
    <td width="18%" align="right" class="text3" style="margin-right:5px;">��Ŀ���ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="programName" value="<%=programName%>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">����״̬��
    </td>
    <td width="30%" class="text4" colspan="3">
    <SELECT name=approvalStatus> 
                           <option value="-1" <logic:equal value="-1" name="approvalStatus"> selected</logic:equal>>
								ȫ��
							</option>
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
    </select>
	</td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="��ѯ" >
	    <input name="reset" type="reset" class="input1" value="����">
    </td>
  </tr>
</table>
</form>
<br>
<form name="refForm" action="*" method="post" >
<input type="hidden" name="categoryId" value="<%=categoryId%>">

<input type="hidden" name="programId" value="<%=programId%>">

<input type="hidden" name="programName" value="<%=programName%>">


<input type="hidden" name="cmsId" value="<%=cmsId%>">

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>��Ʒ�б�</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAll(refForm,'dealRef',this.checked);"/>
	  	ȫѡ
	</td>
    <td width="10%" align="center" class="title1">��ĿID</td>     
    <td width="20%" align="center" class="title1">��Ŀ����</td>
    <td width="10%" align="center" class="title1">һ������</td>
    <td width="15%" align="center" class="title1">����޸�ʱ��</td>
    <td width="10%" align="center" class="title1">�ʷ�����</td>
    <td width="5%" align="center" class="title1">�������</td>
    <td width="10%" align="center" class="title1">����״̬</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.basevideosync.vo.VideoReferenceVO">
	    <%
		if("text5".equals(tmpStyle))
	  	{
			tmpStyle = "text4";
	  	}
	  	else
	  	{
	  		tmpStyle = "text5";
	  	}
		%>
		<tr class=<%=tmpStyle%>>
			<td align="center" style="word-break:break-all;">
				<input type="checkbox" name="dealRef" <% if(!"2".equals(vo.getVerifyStatus())){ %> disabled="disabled" <%} %>  value="<bean:write name="vo" property="programId"/>#<bean:write name="vo" property="verifyStatus"/>">
			</td>
			<td align="center">
      			<bean:write name="vo" property="programId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="programName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="tagName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="lastUpTime"/>
      		</td>
      		<td align="center">
      			<% if("1".equals(vo.getFeetype())) {%>
      			   ���
      			<% }else if("2".equals(vo.getFeetype())){ %>
      			�շ�
      			<% }else if("3".equals(vo.getFeetype())){%>
      			��֧�ְ���
      			<%} %>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="sortId"/>
      		</td>
      		<td align="center">
      			 <%if("0".equals(vo.getVerifyStatus())){ %>
	                                            �༭
	           <%}else if("1".equals(vo.getVerifyStatus())){ %>
	                                           �����ɹ�
	           <%}else if("2".equals(vo.getVerifyStatus())){ %>
	                                          ������
	           <%}else{ %>
	                                         ������ͨ��  
	           <%} %>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("programId",programId);
        params.put("programName",programName);
		params.put("cmsId",cmsId);
		params.put("subcateName",subcateName);
		params.put("keyName",keyName);
        params.put("method","query");
        %>
        <pager:pager name="PageResult" action="/web/newvideo/categoryApproval.do" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center">
		<logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1"  onClick="return approvalRelease();" value="��������"></logic:notEmpty>
            <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonSet" type="button" class="input1"  onClick="return approvalRefuse();" value="������ͨ��"></logic:notEmpty>
            <input name="buttonClose" type="button" class="input1"  onClick="window.returnValue=true,window.close();" value="�ر�">
	</td>
  </tr>
</table>
</form>
</body>
</html>
