<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%
String tmpStyle = "text5";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��������Ӧ�����ݲ�ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
function remove(genLogo)
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
	
	genLogo.disabled=true;
	
	refForm.submit();
}

function exe()
{
	if(confirm('ȷ��Ҫͬ����������Ӧ������?'))
	{
		refForm.buttonSet.disabled="disabled";
		refForm.perType.value="exe";
		refForm.submit();
	}
}

function add(genLogo)
{
	form1.perType.value="add";
	
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
    
    genLogo.disabled=true;
    
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

function import_waken(genLogo)
{
    genLogo.disabled=true;
	window.location = "../resourcemgr/dataImport.do?subSystem=ssms&type=waken";
}


</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>��������Ӧ�����ݲ�ѯ</b>
    </td>
  </tr>
</table>
<br>
<form name="refForm" action="queryExigence.do" method="post" >
<input type="hidden" name="perType" value="">
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��������Ӧ�������б�
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
    <td width="18%" align="center" class="title1">����ʱ��</td>
    <td width="20%" align="center" class="title1">ͬ������</td>
    <td width="20%" align="center" class="title1">��������</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.web.ContentExigenceVO">
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
      			<bean:write name="vo" property="contentId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="sysdate"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="type"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="subType"/>
      		</td>
		</tr>
  </logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("perType",request.getParameter("perType"));
        %>
        <pager:pager name="PageResult" action="/web/exigence/queryExigence.do" params="<%=params%>">
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
    	<input name="buttonAdd" type="button" class="input1"  onClick="add(this);" value="�������������ļ�">
    	<logic:notEmpty name="PageResult" property="pageInfo">
    		<select name="exeContent">
    			<option value="0" selected="selected">ȫ���������ݱ���Ϣ</option>
    			<option value="1" >�����������ݱ���Ϣ</option>
    		</select>
    		<select name="exeDeviceType">
    			<option value="1" selected="selected">ͬ�������ϵ</option>
    			<option value="0" >��ͬ�������ϵ</option>
    		</select>
    		<input name="buttonSet" type="button" class="input1" onClick="exe();" value="ͬ������">
    		<input name="buttonDel" type="button" class="input1" onClick="remove(this);" value="�Ƴ�����">
    		<input type="button" class="input1" name="genLogo" value="֪ͨ�Ĵ��Ż�����ͬ��" onclick="import_waken(this)">
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
<form action="importExigence.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	��ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td   align="left" width="80%">
			<input type="file" name="dataFile">(����һΪ�������ݵ�id)<font color=red>&nbsp;&nbsp;<b>ע�⣺��������ᵼ��ԭ����Ӧ�����ݱ����.�����ز���!<b></font>
	    </td>
	</table>
</form>
</body>
</html>
