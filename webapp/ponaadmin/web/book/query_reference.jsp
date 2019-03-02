<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>

<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String bookId = Validator.filter(request.getParameter("bookId")==null?"":request.getParameter("bookId"));
String bookName = Validator.filter(request.getParameter("bookName")==null?"":request.getParameter("bookName"));
String authorName = Validator.filter(request.getParameter("authorName")==null?"":request.getParameter("authorName"));
String typeName = Validator.filter(request.getParameter("typeName")==null?"":request.getParameter("typeName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ͼ����Ʒ����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
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

function add()
{
	refForm.perType.value="add";
	var returnv=window.showModalDialog("queryReference.do?perType=queryBook&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ");
	if(returnv != undefined && returnv != '')
	{
		refForm.addBookId.value=returnv;
		refForm.submit();
	}  
}

function SetSortID()
{
	refForm.perType.value="setSort";
	var temp = '';
	
	if(refForm.sortId.length == undefined)
	{
		temp = refForm.sortId.id + "_" + refForm.sortId.value + ";";
	}
	else
	{
		for(var i=0; i<refForm.sortId.length; i++ )
		{
			temp = temp + refForm.sortId[i].id + "_" + refForm.sortId[i].value + ";";
		}
		
		temp = temp.substring(0,temp.length-1);
	}
	
	refForm.setSortId.value = temp;
	refForm.submit();
}

function importData(filename,type)
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
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="queryReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ʒ��ѯ
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">ͼ���ţ�
    </td>
    <td width="30%" class="text4"><input type="text" name="bookId" value="<%=bookId%>"></td>
    <td width="18%" align="right" class="text3">ͼ�����ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="bookName" value="<%=bookName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ͼ�����ߣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="authorName" value="<%=authorName%>"></td>
    <td width="18%" align="right" class="text3">ͼ����ࣺ
    </td>
    <td width="30%" class="text4" ><input type="text" name="typeName" value="<%=typeName%>"></td>
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
<form name="refForm" action="queryReference.do" method="post" >
<input type="hidden" name="categoryId" value="<%=categoryId%>">
<input type="hidden" name="perType" value="">
<input type="hidden" name="addBookId" value="">
<input type="hidden" name="setSortId" value="">
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
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
	  	ȫѡ
	</td>
    <td width="10%" align="center" class="title1">ͼ����</td>     
    <td width="18%" align="center" class="title1">ͼ������</td>
    <td width="6%" align="center" class="title1">ͼ������</td>
    <td width="6%" align="center" class="title1">ͼ�����</td>  
    <td width="10%" align="center" class="title1">����</td>  
    <td width="5%" align="center" class="title1">�������</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.book.vo.BookRefVO">
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
				<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="bookId"/>">
			</td>
			<td align="center"><a href="queryReference.do?perType=showBook&bookId=<bean:write name="vo" property="bookId"/>">
      			<bean:write name="vo" property="bookId"/></a>
      		</td>
      		<td align="center"><a href="queryReference.do?perType=showBook&bookId=<bean:write name="vo" property="bookId"/>">
      			<bean:write name="vo" property="bookName"/></a>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="authorName"/>
			</td>
			<td align="left">
				<bean:write name="vo" property="typeName"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="rankValue"/>
      		</td>
      		<td align="center">
      			<input id="<bean:write name="vo" property="bookId"/>" type="text" name="sortId" size="9" value="<bean:write name="vo" property="sortNumber"/>">
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
        params.put("bookId",bookId);
        params.put("bookName",bookName);
        params.put("authorName",authorName);
        params.put("typeName",typeName);
        params.put("perType",request.getParameter("perType"));
        %>
        <pager:pager name="PageResult" action="/web/book/queryReference.do" params="<%=params%>">
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
    		<input name="buttonDel" type="button" class="input1" onClick="remove();" value="�Ƴ�">
    		<input name="buttonSet" type="button" class="input1" onClick="SetSortID();" value="��������趨">	
		</logic:notEmpty>
		<input name="buttonAdd" type="button" class="input1"  onClick="add();" value="���">
		<input name="buttonImport" type="button" class="input1"  onClick="importData();" value="��������">
    </td>
  </tr>
</table>
</form>
<form action="importReference.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<input type="hidden" name="categoryId" value="<%=categoryId%>">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td   align="left" width="80%">
			<input type="file" name="dataFile">(����һΪ�������ݵ�id)<font color=red>&nbsp;&nbsp;<b>ע�⣺��������ᵼ��ԭ�������ݱ����.�����ز���!<b></font>
	    </td>
	   </tr>
	</table>
</form>
</body>
</html>
