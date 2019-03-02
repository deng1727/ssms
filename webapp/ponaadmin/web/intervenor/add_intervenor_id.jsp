<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO"%>
<%@page import="java.util.HashMap" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">�˹���Ԥ������ѯ</td>
	  </tr>
	</table>
	<form name="ContentForm" action="categoryAddIntervenor.do" method="post">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<input name="actionType" type="hidden" value="addIntervenor"/>
		<tr>
		<td width="20%" align="right" class="text3">
		  	�������ƣ�
		</td>
		<td width="30%" class="text4">  
			<input type="text" name="name" value="<bean:write name="name"/>" >
		</td>
		</tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr class="text5">
	    <td align="center">
	      <input type="submit" class="input1" name="submit01" value="��ѯ" >      
	      <input type="button" class="input1" name="btn_reset" value="����" onClick="clearForm(ContentForm);">
	    </td>
		</tr>
	</table>
  </form>
<br>

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
     <b>�˹���Ԥ�����б�</b>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">ѡ������</td>
	  <td align="center" class="title1">��������</td>
	  <td align="center" class="title1">������ֹʱ��</td>
	  <td align="center" class="title1">������Ԥλ��</td>
	</tr>
	<%
		String tmpStyle = "text5";
		String temp = "";
		int size = 20;
	%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO">
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
				<input type="radio" name="id" value="<bean:write name="vo" property="id"/>" />
			</td>
      		<td align="center">
      			<bean:write name="vo" property="name"/>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="startDate"/>
				-
				<bean:write name="vo" property="endDate"/>
			</td>
      		<td align="left">
				<logic:equal name="vo" property="startSortId" value="-1">
					���񵥶���
				</logic:equal>
				<logic:equal name="vo" property="startSortId" value="-2">
					���񵥵ײ�
				</logic:equal>
				<logic:greaterEqual name="vo" property="startSortId" value="0">
					ָ����Ԥ��<bean:write name="vo" property="startSortId"/>λ
				</logic:greaterEqual>
			</td>
		</tr>
	</logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("name",request.getParameter("name"));
        params.put("actionType",request.getParameter("addIntervenor"));
        %>
        <pager:pager name="PageResult"  form="ContentForm"  action="/web/intervenor/categoryAddIntervenor.do" params="<%=params%>">
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
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="ȷ��" onClick="fReturn();"/>
		</td>
	</tr>
</table>
<script language="javascript">
function fReturn()
{
	var obj = document.getElementsByName("id");
	
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].checked)
            {
                //opener.ContentForm.id.value=obj[i].value;
                window.returnValue=obj[i].value;
				break;
            }
        }
    }
    window.close();
}
</script>
</body>
</html>
