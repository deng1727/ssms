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
<title>重点内容查询</title>
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
		alert("请选择要移除目标！");
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

function downloadData()
{
	if(confirm('提示确认是否已经导入了重点应用和重点机型?'))
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
	      重点内容查询
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">内容编号：
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentId" value="<%=contentId%>"></td>
	    <td width="18%" align="right" class="text3">内容名称：
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentName" value="<%=contentName%>"></td>
	  </tr>
	  
	  <tr>
	    <td width="18%" align="right" class="text3">AP编码：
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="apCode" value="<%=apCode%>"></td>
	    <td width="18%" align="right" class="text3">AP名称：
	    </td>
	    <td width="30%" class="text4" ><input type="text" name="apName" value="<%=apName%>"></td>
	 </tr>
	  <tr>
	    <td colspan="4" align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="查询" >
		    <input name="reset" type="reset" class="input1" value="重置">
	    </td>
	  </tr>
	</table>
	
	<br>

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
		     <b>重点内容列表</b>
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
		    <td width="20%" align="center" class="title1">内容名称</td>
		    <td width="15%" align="center" class="title1">AP编码</td>
		    <td width="10%" align="center" class="title1">AP名称</td>
		    <td width="10%" align="center" class="title1">创建时间</td>
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
			<input name="buttonDel" type="button" class="input1" onclick="remove()" value="删除">
			<input name="buttonAdd" type="button" class="input1" onclick="importData()" value="导入数据">
			<input name="buttonDownload" type="button" class="input1" onclick="downloadData()" value="下载应用与机型对应缺失数据">
	    </td>
	  </tr>
	</table>
</form>
<form action="importContent.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	请选择要导入的excel数据文件：
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">(表格第一列为内容的id)
	    </td>
	</table>
</form>
</body>
</html>
