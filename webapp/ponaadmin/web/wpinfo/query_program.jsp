<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String appId = Validator.filter(request.getParameter("appId")==null?"":request.getParameter("appId"));
String appName = Validator.filter(request.getParameter("appName")==null?"":request.getParameter("appName"));

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӧ����Ӳ�ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />
</head>
<script language="javascript">
	function add()
	{
	
		var temp = '';
			refForm.perType.value="add";
		
		if(refForm.dealRef.length == undefined)
		{
		
			if(refForm.dealRef.checked == true)
			{
				temp = temp + refForm.dealRef.value + ';';
			}
		}
		else
		{
		
			for(var i=0;i<refForm.dealRef.length; i++)
			{
			
				if(refForm.dealRef[i].checked==true)
				{
				
					temp = temp + refForm.dealRef[i].value + ';';
				}
			}
		}

        temp = temp.substring(0,temp.lastIndexOf(";"));
        
        window.returnValue=temp;
        
	    window.close();
        
	}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="appInfoReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ʒ��ѯ
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="queryProgram">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">Ӧ��ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="appId" value="<%=appId%>"></td>
    <td width="18%" align="right" class="text3">Ӧ�����ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="appName" value="<%=appName%>"></td>
  </tr>

  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="��ѯ" >
	    <input name="reset" type="reset" class="input1" value="����">
    </td>
  </tr>
</table>
<br>
<logic:equal name="isFirst" value="1">
	<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
	      <tr bgcolor="#B8E2FC">
	          <td colspan="5" align="center"><font color="#ff0000">�������ѯ������</font></td>
	      </tr>
	</table>
</logic:equal>


<logic:equal name="isFirst" value="">
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
		    <td width="10%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td>
		    <td width="10%" align="center" class="title1">Ӧ��ID</td>     
		    <td width="15%" align="center" class="title1">Ӧ������</td>
		    <td width="10%" align="center" class="title1">Ӧ�ü۸�</td>
		    <td width="15%" align="center" class="title1">����޸�ʱ��</td>
		    
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.wpinfo.vo.AppInfoVO">
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
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="appId"/>_<bean:write name="vo" property="appName"/>">
					</td>
					<td align="center">
		      			<bean:write name="vo" property="appId"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="appName"/>
		      		</td>
		      		
		      		<td align="center">
		      			<bean:write name="vo" property="appPrice"/>
		      		</td>
		      		<td align="center">
						<bean:write name="vo" property="lastUpTime"/>
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
		        params.put("appName",appName);
		        params.put("appId",appId);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/wpInfo/appInfoReference.do" params="<%=params%>">
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
</logic:equal>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
		<input name="buttonAdd" type="button" class="input1"  onClick="add();" value="���">
    </td>
  </tr>
</table>
</logic:notEmpty>
</form>
</body>
</html>
