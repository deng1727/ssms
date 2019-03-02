<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String programId = Validator.filter(request.getParameter("programId")==null?"":request.getParameter("programId"));
String programName = Validator.filter(request.getParameter("programName")==null?"":request.getParameter("programName"));
String videoId = Validator.filter(request.getParameter("videoId")==null?"":request.getParameter("videoId"));
String nodeId = Validator.filter(request.getParameter("nodeId")==null?"":request.getParameter("nodeId"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ƶ��Ŀ����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
	function allExport(genLogo)
	{
		genLogo.disabled=true;
		if(confirm("��ȷ��Ҫ���е���������"))
		{
			form.perType.value='exportAll';
			form.submit();
			form.perType.value='query';
		}
		else
		{
			return false;	
		}
	}
	
	function queryExport(genLogo)
	{
		genLogo.disabled=true;
		if(confirm("��ȷ��Ҫ���е���������"))
		{
			form.perType.value='export';
			form.submit();
			form.perType.value='query';
		}
		else
		{
			return false;	
		}
	}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="queryProgram.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ƶ��Ŀ��ѯ
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">��Ŀ��ţ�
    </td>
    <td width="30%" class="text4"><input type="text" name="programId" value="<%=programId%>"></td>
    <td width="18%" align="right" class="text3">��Ŀ���ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="programName" value="<%=programName%>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">���ݱ��룺
    </td>
    <td width="30%" class="text4"><input type="text" name="videoId" value="<%=videoId%>"></td>
    <td width="18%" align="right" class="text3">��Ŀ���룺
    </td>
    <td width="30%" class="text4"><input type="text" name="nodeId" value="<%=nodeId%>"></td>
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
<form name="refForm" action="queryProgram.do" method="post" >
<input type="hidden" name="perType" value="">
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
     <b>��Ƶ��Ŀ�б�</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="15%" align="center" class="title1">��Ŀ����</td> 
    <td width="20%" align="center" class="title1">���ݱ���</td>    
    <td width="20%" align="center" class="title1">��Ŀ����</td>
    <td width="15%" align="center" class="title1">��Ŀ����</td>
    <td width="15%" align="center" class="title1">��Ŀ����</td>
    <td width="15%" align="center" class="title1">����޸�ʱ��</td>
    <td width="15%" align="center" class="title1">��Ŀʱ��</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseVideo.vo.ProgramVO">
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
				<bean:write name="vo" property="programId"/>
			</td>
			<td align="center">
      			<bean:write name="vo" property="videoId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="programName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="nodeId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="nodeName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="lastUpTime_Y"/>��<bean:write name="vo" property="lastUpTime_M"/>��<bean:write name="vo" property="lastUpTime_D"/>��
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="showTime"/>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("programId",programId);
        params.put("programName",programName);
        params.put("nodeId",nodeId);
		params.put("videoId",videoId);
        params.put("perType",request.getParameter("perType"));
        %>
        <pager:pager name="PageResult" action="/web/baseVideo/queryProgram.do" params="<%=params%>">
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
    	<logic:notEmpty name="PageResult" property="pageInfo">
    		<input name="buttonDel" type="button" class="input1" onClick="queryExport(this);" value="����ǰ��������">
    		<input name="buttonSet" type="button" class="input1" onClick="allExport(this);" value="ȫ������">	
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
</body>
</html>
