<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<%@page import="java.util.HashMap"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����б�</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />
</head>
<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">ƽ̨�б�</td>
  </tr>
</table>
<form name="ContentForm" action="<%=request.getContextPath()%>/web/channelUser/categoryPlatForm.do" method="post">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="20%" align="right" class="text3">
    	ƽ̨��ţ�
    </td>
    <td width="30%" class="text4">  
	    <input type="text" name="platFormId" value="<bean:write name="platFormId"/>"/>
    </td>
    <td width="20%" align="right" class="text3">
    	ƽ̨���ƣ�
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="platFormName" value="<bean:write name="platFormName"/>"/>
    </td>
  </tr>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="��ѯ" >      
      <input type="button" class="input1" name="btn_reset" value="����" onClick="clearForm(ContentForm);">
    </td>
  </tr>
</table>
</form>
</table>
  <table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     ƽ̨�б�
    </td>
  </tr>
</table>
<logic:notEmpty name="notice">
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="1" bgcolor="#FFFFFF">
		<tr bgcolor="#B8E2FC">
			<td colspan="5" align="center"><font color="#ff0000"><bean:write
				name="notice" /></font></td>
		</tr>
	</table>
</logic:notEmpty>
<logic:empty name="notice">
	<logic:empty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr bgcolor="#B8E2FC">
				<td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
			</tr>
		</table>
	</logic:empty>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td width="10%" align="center" class="title1">
	  ��ѡ��
	  </td>
	  <td width="25%" align="center" class="title1">ƽ̨����</td>
	  <td width="45%" align="center" class="title1">ƽ̨����</td>
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.gcontent.PlatFormVO">
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
				<input type="checkbox" name="id" value="<bean:write name="vo" property="platFormId"/>" />
			</td>
      		<td align="center">
      			<bean:write name="vo" property="platFormId"/>
      		</td>
      		<td align="left">
      			<input type="hidden" name="cname<bean:write name="ind"/>" value="<bean:write name="vo" property="platFormName"/>">
				<bean:write name="vo" property="platFormName"/>
			</td>
		</tr>
	</logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("platFormId",request.getParameter("platFormId"));
        params.put("platFormName",request.getParameter("platFormName"));
        %>
        <pager:pager name="PageResult" form="ContentForm" action="/web/channelUser/categoryPlatForm.do" params="<%=params%>">
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
	var my_array = new Array();
	var obj = document.getElementsByName("id");
	var t=0;
    if(obj!=null){
        var i;
        
        var platFormId_id_value= "";
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].checked)
            {
            	var d=new Object();
            	d.id=obj[i].value;
            	d.name=document.all["cname"+i].value;
                my_array[t++] = d;
                var str = ",{id:'"+d.id+"',name:'"+d.name+"'}";
                platFormId_id_value +=str;
            }
        }
        
         if(platFormId_id_value.length>0){
        	platFormId_id_value = "["+platFormId_id_value.substring(1)+"]";
        }else{
        	platFormId_id_value = "[]";
        }
    }
    window.returnValue=my_array;
    window.close();
}
</script>
</body>
</html>
