<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String deviceId = Validator.filter(request.getParameter("deviceId")==null?"":request.getParameter("deviceId"));
String deviceName = Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String brandName = Validator.filter(request.getParameter("brandName")==null?"":request.getParameter("brandName"));
String osName = Validator.filter(request.getParameter("osName")==null?"":request.getParameter("osName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ص���Ͳ�ѯ</title>
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
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="device.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	      �ص���Ͳ�ѯ
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">���ͱ�ţ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="deviceId" value="<%=deviceId%>"></td>
	    <td width="18%" align="right" class="text3">�������ƣ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="deviceName" value="<%=deviceName%>"></td>
	  </tr>
	  
	  <tr>
	    <td width="18%" align="right" class="text3">Ʒ�ƣ�
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="brandName" value="<%=brandName%>"></td>
	    <td width="18%" align="right" class="text3">ƽ̨���ƣ�
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="osName" value="<%=osName%>"></td>
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
		     <b>�ص�����б�</b>
		    </td>
		  </tr>
		</table>
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td>
		    <td width="10%" align="center" class="title1">���ͱ��</td>     
		    <td width="20%" align="center" class="title1">��������</td>
		    <td width="15%" align="center" class="title1">Ʒ��</td>
		    <td width="10%" align="center" class="title1">ƽ̨����</td>
		    <td width="10%" align="center" class="title1">����ʱ��</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.pivot.vo.PivotDeviceVO">
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
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="deviceId"/>">
					</td>
					<td align="center">
		      			<bean:write name="vo" property="deviceId"/>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="deviceName"/>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="brandName"/>
					</td>
		      		<td align="center">
		      			<bean:write name="vo" property="osName"/>
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
		        params.put("deviceId",request.getParameter("deviceId"));
		        params.put("deviceName",deviceName);
		        params.put("brandName",brandName);
		        params.put("osName",osName);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/pivot/device.do" params="<%=params%>">
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
	    </td>
	  </tr>
	</table>
</form>
<form action="importDevice.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	��ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">(����һ��Ϊ���͵�id)
	    </td>
	</table>
</form>
</body>
</html>
