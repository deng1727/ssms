<%@ page contentType="text/html; charset=gbk" %>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%
    String tmpStyle = "text5";
    Object unused = request.getAttribute("unused");
    Object used = request.getAttribute("used");
    Object total = request.getAttribute("total");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�������б�</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

function importData()
{
	
	var filePath = form1.dataFile.value;
	
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
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="1" style="margin-top:20px;margin-bottom:20px;border:1px solid #C5ECF3;">
    <tr>
    <td width="1%"></td>
    <td width="99%">
            <br/>�������ܺ� <%=total %> ������ʹ�� <%=used %> ����δʹ�� <%=unused %> ����<br/><br/>
    </td>
    </tr>
    
</table>
<form action="importChannelsNo.do" name="form1" method="post" enctype="multipart/form-data">
	<table width="95%"  border="0" align="center" cellspacing="1">
	<input name="method" type="hidden" id="method" value="importData">
	  <tr>
	    <td   align="left" width="20%"> 
			<input type="file" name="dataFile" value="ѡ���ļ�">
	    </td>
	     <td   align="left" width="80%"> 
			<input type="button" class="input1"  value="��������������" onClick="importData();">
	    </td>
	   </tr>
	</table>
</form>
<form name="refForm" action="channelsNoInfoList.do" method="post" >
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
     <b>�������б�</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="33%" align="center" class="title1">��������</td>     
    <td width="33%" align="center" class="title1">������</td>
    <td width="33%" align="center" class="title1">��������</td> 
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.coManagement.vo.ChannelsNoVO">
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
		<td align="center">
      			<bean:write name="vo" property="createDate"/>
      		</td>
      	<td align="center">
      			<bean:write name="vo" property="operator"/>
      		</td>
      	<td align="center">
      			<bean:write name="vo" property="total"/>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
    	params.put("method","query");
        %>
        <pager:pager name="PageResult" action="/web/cooperation/channelsNoInfoList.do" params="<%=params%>">
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
</form>
</body>
</html>
