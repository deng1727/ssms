<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" onload="into();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">�˹���Ԥ������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="intervenorView.do" method="post">
  <input type="hidden" name="id" value="<bean:write name="intervenorVO" property="id"/>" />
  <input type="hidden" name="contentId" value="" />
  <input type="hidden" name="actionType" value="" />
  <input type="hidden" name="startSortId" value="" />
  <input type="hidden" name="endSortId" value="" />
  <input type="hidden" name="oldName" value="<bean:write name="intervenorVO" property="name"/>" />
  <input type="hidden" name="categoryId" value="<bean:write name="categoryId" />" />

  <tr>
    <td width="20%" align="right" class="text3">
    	��ǰ����:
    </td>
    <td width="30%" class="text4">
    	<bean:write name="intervenorVO" property="name"/>
    </td>
    <td width="50%" align="center" class="text4"> 
    	<input type="button" value="����"  onclick="back('<bean:write name="categoryId" />');" />
    </td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	�趨��������λ��:
    </td>
    <td class="text4" colspan="2">
    	<select name="isselect" onchange="selectSort();">
    		<option value="0">�ֶ�����</option>
    		<option value="-1">���񵥶���</option>
    		<option value="-2">���񵥵ײ�</option>
    	</select>
    	&nbsp;
		<input type="text" name="sortId" 
		value=""
		onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false">
		&nbsp;&nbsp;&nbsp;
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	����������Чʱ������:
    </td>
    <td class="text4" colspan="2">
    	<input name="startDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="startDate"/>"
			readonly="true" />
			&nbsp;��&nbsp;
		<input name="endDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="endDate"/>"
			readonly="true" />
	</td>
  </tr>
  </form>
</table>
<br>
<br>

<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     �Ѽ�����Ʒ�б�
    </td>
  </tr>
</table>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">��Ʒ����</td>
	  <td align="center" class="title1">��Ʒ����</td>
	  <td align="center" class="title1">��Ʒ����</td>
	  <td align="center" class="title1">�ṩ��</td>
	  <td align="center" class="title1">����ʱ��</td>
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO">
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
				<bean:write name="vo" property="sortId"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="name"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="contentId"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="spName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="marketDate"/>
      		</td>
		</tr>
	</logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("id",request.getParameter("id"));
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("actionType",request.getParameter("actionType"));
        %>
        <pager:pager name="PageResult" action="/web/intervenor/intervenorView.do" params="<%=params%>">
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
<script language="javascript">
function back(id)
{
	ContentForm.action = "categoryRelatingView.do?actionType=relatingView&id=" + id;
	ContentForm.submit();
}

function into()
{
	var start = '<bean:write name="intervenorVO" property="startSortId"/>';
	var end = '<bean:write name="intervenorVO" property="endSortId"/>';
	
	ContentForm.sortId.value = start;
	var iss = ContentForm.isselect;
		
	for(var i=0; i<iss.length; i++)
	{
		if(iss.options[i].value == start)
		{
			iss.options[i].selected=true;
			ContentForm.sortId.style.display="none";
			break;
		}
	}
}

function selectSort()
{
	var iss = ContentForm.isselect.value;
	
	if(iss!=0)
	{
		ContentForm.sortId.style.display="none";
	}
	else
	{
		ContentForm.sortId.style.display="";
		ContentForm.sortId.value='1';
	}
}
</script>
</body>
</html>
