<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String tacCode = Validator.filter(request.getParameter("tacCode")==null?"":request.getParameter("tacCode"));
String brand = Validator.filter(request.getParameter("brand")==null?"":request.getParameter("brand"));
String device = Validator.filter(request.getParameter("device")==null?"":request.getParameter("device"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>TAC����ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function removeTac(id,taccode)
{

  if(confirm("ȷ����TAC������Ƴ���TAC�룿"))
  {
	  tacCodeForm.perType.value = "remove";
	  tacCodeForm.id.value = id;
	  tacCodeForm.tacCode.value = taccode;
	  tacCodeForm.submit();
  }
}

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
	importForm.perType.value="importData";
	
	var filePath = importForm.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("��ѡ��Ҫ�������ݵ��ļ���");
		return false;
    }

	if(!isFileType(filePath,"xls")&&!isFileType(filePath,"xlsx"))
    {
      alert("��ѡ��xls��xlsx��ʽ�������ļ�!");
      return false;
    }
    importForm.submit();
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
<form name="refForm" action="tacCode.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	      TAC����ѯ
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">TAC�룺
	    </td>
	    <td width="30%" class="text4"><input type="text" name="tacCode" value="<%=tacCode%>"></td>
	    <td width="18%" align="right" class="text3">�ֻ�Ʒ�ƣ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="brand" value="<%=brand%>"></td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">�ֻ��ͺţ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="device" value="<%=device%>"></td>
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
		     <b>TAC���б�</b>
		    </td>
		  </tr>
		</table>
	<form name="tacCodeForm" action="tacCode.do" method="post">
		<input type="hidden" name="perType" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="tacCode" value="">
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <!-- <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td> -->
		    <td width="10%" align="center" class="title1">TAC��</td>     
		    <td width="20%" align="center" class="title1">�ֻ�Ʒ��</td>
		    <td width="15%" align="center" class="title1">�ֻ��ͺ�</td>
		    <td width="10%" align="center" class="title1">����</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.taccode.vo.TacVO">
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
					<%-- <td align="center" style="word-break:break-all;">
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="id"/>">
					</td> --%>
					<td align="center">
		      			<bean:write name="vo" property="tacCode"/>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="brand"/>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="device"/>
					</td>
					<td align="center" style="word-break: break-all">
				       <input name="ad" type="button" class="input1" onClick="removeTac('<bean:write name="vo" property="id" />','<bean:write name="vo" property="tacCode" />');" value="ɾ��"/>
				    </td>
		      		<%-- <td align="center">
		      			<bean:write name="vo" property="createTime"/>
		      		</td> --%>
				</tr>
		  </logic:iterate>
		</table>
		</form>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >
		    	<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("tacCode",tacCode);
		        params.put("brand",device);
		        params.put("device",brand);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/channelUser/tacCode.do" params="<%=params%>">
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
	        <%-- <logic:notEmpty name="PageResult" property="pageInfo">
			    <input name="buttonDel" type="button" class="input1" onclick="remove()" value="ɾ��">
			</logic:notEmpty> --%>
			<input name="buttonAdd" type="button" class="input1" onclick="importData()" value="��������TAC��">
	    </td>
	  </tr>
	</table>
</form>
<form action="tacCode.do" name="importForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="importData">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	��ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">(����һ��ΪTAC��,�ڶ���Ϊ�ֻ�Ʒ��,������Ϊ�ֻ��ͺ�)
	    </td>
	</table>
</form>
</body>
</html>
