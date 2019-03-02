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
<title>图书商品管理</title>
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
		alert("请选择要移除目标！");
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
		alert("请选择要导入数据的文件！");
		return false;
    }

    if(!isFileType(filePath,"xls"))
    {
		alert("请选择xls格式的数据文件!");
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
      商品查询
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">图书编号：
    </td>
    <td width="30%" class="text4"><input type="text" name="bookId" value="<%=bookId%>"></td>
    <td width="18%" align="right" class="text3">图书名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="bookName" value="<%=bookName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">图书作者：
    </td>
    <td width="30%" class="text4"><input type="text" name="authorName" value="<%=authorName%>"></td>
    <td width="18%" align="right" class="text3">图书分类：
    </td>
    <td width="30%" class="text4" ><input type="text" name="typeName" value="<%=typeName%>"></td>
 </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
	    <input name="reset" type="reset" class="input1" value="重置">
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
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>商品列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
	  	全选
	</td>
    <td width="10%" align="center" class="title1">图书编号</td>     
    <td width="18%" align="center" class="title1">图书名称</td>
    <td width="6%" align="center" class="title1">图书作者</td>
    <td width="6%" align="center" class="title1">图书分类</td>  
    <td width="10%" align="center" class="title1">评分</td>  
    <td width="5%" align="center" class="title1">排序序号</td>
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
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
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
    		<input name="buttonDel" type="button" class="input1" onClick="remove();" value="移除">
    		<input name="buttonSet" type="button" class="input1" onClick="SetSortID();" value="排序序号设定">	
		</logic:notEmpty>
		<input name="buttonAdd" type="button" class="input1"  onClick="add();" value="添加">
		<input name="buttonImport" type="button" class="input1"  onClick="importData();" value="导入内容">
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
	    	选择要导入的excel数据文件：
	    </td>
	    <td   align="left" width="80%">
			<input type="file" name="dataFile">(表格第一为引用内容的id)<font color=red>&nbsp;&nbsp;<b>注意：导入操作会导致原货架内容被清空.请慎重操作!<b></font>
	    </td>
	   </tr>
	</table>
</form>
</body>
</html>
