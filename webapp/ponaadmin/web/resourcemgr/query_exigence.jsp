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
<title>紧急上线应用内容查询</title>
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
		alert("请选择要移除目标！");
		return;
	}
	
	genLogo.disabled=true;
	
	refForm.submit();
}

function exe()
{
	if(confirm('确定要同步紧急上线应用数据?'))
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
		alert("请选择要导入数据的文件！");
		return false;
    }

    if(!isFileType(filePath,"xls"))
    {
		alert("请选择xls格式的数据文件!");
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
     <b>紧急上线应用内容查询</b>
    </td>
  </tr>
</table>
<br>
<form name="refForm" action="queryExigence.do" method="post" >
<input type="hidden" name="perType" value="">
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      紧急上线应用内容列表
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
	  	全选
	</td>
    <td width="10%" align="center" class="title1">内容编号</td>     
    <td width="18%" align="center" class="title1">导入时间</td>
    <td width="20%" align="center" class="title1">同步类型</td>
    <td width="20%" align="center" class="title1">内容类型</td>
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
    	<input name="buttonAdd" type="button" class="input1"  onClick="add(this);" value="导入内容数据文件">
    	<logic:notEmpty name="PageResult" property="pageInfo">
    		<select name="exeContent">
    			<option value="0" selected="selected">全量处理内容表信息</option>
    			<option value="1" >增量处理内容表信息</option>
    		</select>
    		<select name="exeDeviceType">
    			<option value="1" selected="selected">同步适配关系</option>
    			<option value="0" >不同步适配关系</option>
    		</select>
    		<input name="buttonSet" type="button" class="input1" onClick="exe();" value="同步数据">
    		<input name="buttonDel" type="button" class="input1" onClick="remove(this);" value="移除数据">
    		<input type="button" class="input1" name="genLogo" value="通知四大门户增量同步" onclick="import_waken(this)">
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
	    	请选择要导入的excel数据文件：
	    </td>
	    <td   align="left" width="80%">
			<input type="file" name="dataFile">(表格第一为引用内容的id)<font color=red>&nbsp;&nbsp;<b>注意：导入操作会导致原紧急应用内容被清空.请慎重操作!<b></font>
	    </td>
	</table>
</form>
</body>
</html>
