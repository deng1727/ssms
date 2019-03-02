<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.repository.Category"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>人工干预管理</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">榜单关联信息</td>
	  </tr>
	</table>
<form name="ContentForm" action="categoryList.do" method="post">
	<input name="actionType" type="hidden" value="relatingView"/>
	<input id="id" name="id" type="hidden" value="<bean:write name="vo" property="id"/>"/>
	<input id='type' name="type" type="hidden" value="<bean:write name="vo" property="type"/>"/>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr>
			<td width="10%" align="right" class="text3">
			  	榜单名称：
			</td>
			<td width="40%" class="text4">
				<bean:write name="vo" property="name"/>
			</td>
			<td width="10%" align="right" class="text3">
			  	榜单路径：
			</td>
			<td width="40%" class="text4">  
				<bean:write name="vo" property="namePath"/>
			</td>
		</tr>
		<tr>
			<td width="10%" align="right" class="text3">
			  	榜单简介：
			</td>
			<td width="40%"  class="text4" >
				<bean:write name="vo" property="desc"/>
			</td>
			<td width="10%" align="right" class="text3">
			  	添加容器：
			</td>
			<td width="40%" class="text4">  
				<input type="button" value="从容器目录中提取" onclick="addIntervenorId();">
			</td>
		</tr>
	</table>
</form>	
<br>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">请添加干预容器。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>容器列表</b>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">容器名称</td>
	  <td align="center" class="title1">容器起止时间</td>
	  <td align="center" class="title1">容器干预位置</td>
	  <td align="center" class="title1">容器优先级</td>
	  <td align="center" class="title1">释放容器</td>
	</tr>
	<%
		String tmpStyle = "text5";
		String temp = "";
		int size = 20;
	%>
	<logic:iterate id="voi" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO">
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
      			<a href="#" onclick="viewIntervenor('<bean:write name="voi" property="id"/>');">
					<bean:write name="voi" property="name"/>
				</a>
      		</td>
      		<td align="left">
				<bean:write name="voi" property="startDate"/>
				-
				<bean:write name="voi" property="endDate"/>
			</td>
      		<td align="left">
				<logic:equal name="voi" property="startSortId" value="-1">
					至榜单顶部
				</logic:equal>
				<logic:equal name="voi" property="startSortId" value="-2">
					至榜单底部
				</logic:equal>
				<logic:greaterEqual name="voi" property="startSortId" value="0">
					指定干预第<bean:write name="voi" property="startSortId"/>位
				</logic:greaterEqual>
			</td>
      		<td align="center">
      			<input type="text" id="sortId<bean:write name="voi" property="id"/>" name="sortId" value="<bean:write name="voi" property="sortId"/>" 
      			onkeydown="if(event.keyCode==13) event.keyCode=9"
				onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false"
				size="3" maxlength="3" />
				
				<input type="hidden" id="sortIdHidden<bean:write name="voi" property="id"/>" name="sortIdHidden" value="<bean:write name="voi" property="sortId"/>"/>
				
      			<input type="button" value="设置排序" onclick="setSortId('<bean:write name="voi" property="id"/>');" />
      		</td>
      		<td align="center">
      			<input type="button" value="从榜单中释放容器" onclick="delIntervenorId('<bean:write name="voi" property="id"/>');">
      		</td>
		</tr>
	</logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
    	<input type="button" value="执行" onclick="exeIntervenor();" <logic:equal name="vo" property="type" value="2">disabled="disabled"</logic:equal>/>
    	<input type="button" value="返回" onclick="back();"/>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("id",request.getParameter("id"));
        params.put("actionType",request.getParameter("relatingView"));
        %>
        <pager:pager name="PageResult" action="/web/intervenor/categoryRelatingView.do" params="<%=params%>">
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
<script language="javascript">
function viewIntervenor(id)
{
	var categoryId = document.getElementById('id').value;
	ContentForm.action = "intervenorView.do?actionType=view&id=" + id + "&categoryId=" + categoryId;
	ContentForm.submit();
}

function back()
{
	ContentForm.action = "categoryList.do?actionType=listCategory&id=";
	ContentForm.submit();
}

function exeIntervenor()
{
	var id = document.getElementById('id').value;
	ContentForm.action = "intervenorExe.do?actionType=exe&id=" + id;
	ContentForm.submit();
}

function addIntervenorId()
{
	var categoryId = document.getElementById('id').value;
	var type = document.getElementById('type').value;
	
	var returnv=window.showModalDialog("categoryAddIntervenor.do?actionType=addIntervenor&isFirst=1", "","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300")
	
	if(returnv != undefined)
	{
		var id = returnv;
		
		
		ContentForm.action = "categoryRelating.do?actionType=relating&type=" + type + "&intervenorId=" + id + "&categoryId=" + categoryId;
		ContentForm.submit();
	} 
}

function delIntervenorId(id)
{
	var categoryId = document.getElementById('id').value;
	var categoryId = document.getElementById('id').value;
	ContentForm.action = "categoryDelRelating.do?actionType=del&intervenorId=" + id + "&categoryId=" + categoryId;
	ContentForm.submit();
}

function setSortId(id)
{
	var categoryId = document.getElementById('id').value;
	var sortId = document.getElementById('sortId' + id).value;
	var sort = document.getElementsByName('sortId');
	
	var newSortId = document.getElementById('sortId' + id).value;
	var name = 'sortIdHidden' + id;
	var sort = document.getElementsByName('sortIdHidden');
	
	if(sortId=='')
	{
		alert("排序数值不可以等于空，请输入！");
		document.getElementById('sortId' + id).focus();
		return false;
	}
	
	for(var i=0; i<sort.length; i++)
	{
		if(sort[i].id != name)
		{
			if(sort[i].value == newSortId)
			{
				alert("此榜单中已存在此数值的排序，请重新输入！");
				document.getElementById('sortId' + id).focus();
				return false;
			}
		}
	}
	
	ContentForm.action = "categorySetSortId.do?actionType=setSortId&sortId=" + sortId + "&intervenorId=" + id + "&id=" + categoryId;
	ContentForm.submit();
}
</script>
</body>
</html>
