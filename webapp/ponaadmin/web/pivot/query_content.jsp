<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));
String contentName = Validator.filter(request.getParameter("contentName")==null?"":request.getParameter("contentName"));
String apCode = Validator.filter(request.getParameter("apCode")==null?"":request.getParameter("apCode"));
String apName = Validator.filter(request.getParameter("apName")==null?"":request.getParameter("apName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ص����ݲ�ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function remove()
{
	refForm.perType.value="remove";
	var isSele = false;
	
	if(refForm.dealRef.length == undefined)
	{
		if(refForm.dealRef.checked == true)
		{
			isSele = true;
		}
	}
	else
	{
		for(var i=0; i<refForm.dealRef.length; i++ )
		{
			if(refForm.dealRef[i].checked == true)
			{
				isSele = true;
				break;
			}
		}
	}
	if(isSele == false)
	{
		alert("��ѡ��Ҫ�Ƴ�Ŀ�꣡");
		return;
	}
	refForm.submit();
}

function importData()
{
	form1.perType.value="importData";
	
	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("��ѡ��Ҫ�������ݵ��ļ���");
		return false;
    }

    if(!isFileType(filePath,"xls"))
    {
		alert("��ѡ��xls��ʽ�������ļ�!");
		return false;
    }
	form1.submit();
}

function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}

function downloadData()
{
	if(confirm('��ʾȷ���Ƿ��Ѿ��������ص�Ӧ�ú��ص����?'))
	{
		refForm.buttonDownload.disabled="disabled";
		form1.perType.value="downloadData";
		form1.submit();
	}
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="content.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	      �ص����ݲ�ѯ
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">���ݱ�ţ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentId" value="<%=contentId%>"></td>
	    <td width="18%" align="right" class="text3">�������ƣ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentName" value="<%=contentName%>"></td>
	  </tr>
	  
	  <tr>
	    <td width="18%" align="right" class="text3">AP���룺
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="apCode" value="<%=apCode%>"></td>
	    <td width="18%" align="right" class="text3">AP���ƣ�
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="apName" value="<%=apName%>"></td>
	 </tr>
	  <tr>
	    <td colspan="4" align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="��ѯ" >
		    <input name="reset" type="reset" class="input1" value="����">
	    </td>
	  </tr>
	</table>
	
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
		     <b>�ص������б�</b>
		    </td>
		  </tr>
		</table>
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td>
		    <td width="10%" align="center" class="title1">���ݱ��</td>     
		    <td width="20%" align="center" class="title1">��������</td>
		    <td width="15%" align="center" class="title1">AP����</td>
		    <td width="10%" align="center" class="title1">AP����</td>
		    <td width="10%" align="center" class="title1">����ʱ��</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.pivot.vo.PivotContentVO">
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
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="contentId"/>">
					</td>
					<td align="center">
		      			<a href="content.do?perType=detail&contentId=<bean:write name="vo" property="contentId"/>" ><bean:write name="vo" property="contentId"/></a>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="contentName"/>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="apCode"/>
					</td>
		      		<td align="center">
		      			<bean:write name="vo" property="apName"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="creDate"/>
		      		</td>
				</tr>
		  </logic:iterate>
		</table>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >
		    	<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("contentId",contentId);
		        params.put("contentName",contentName);
		        params.put("apCode",apCode);
		        params.put("apName",apName);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/pivot/content.do" params="<%=params%>">
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
	    <td align="center" >
			<input name="buttonDel" type="button" class="input1" onclick="remove()" value="ɾ��">
			<input name="buttonAdd" type="button" class="input1" onclick="importData()" value="��������">
			<input name="buttonDownload" type="button" class="input1" onclick="downloadData()" value="����Ӧ������Ͷ�Ӧȱʧ����">
	    </td>
	  </tr>
	</table>
</form>
<form action="importContent.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	��ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">(����һ��Ϊ���ݵ�id)
	    </td>
	</table>
</form>
</body>
</html>
