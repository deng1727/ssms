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
	    <td align="center" bgcolor="#BFE8FF">榜单查询</td>
	  </tr>
	</table>
	<form name="ContentForm" action="categoryList.do" method="post">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<input name="actionType" type="hidden" value="listCategory"/>
		<tr>
		<td width="10%" align="right" class="text3">
		  	榜单编码：
		</td>
		<td width="40%" class="text4">
			<input type="text" name="id" value="<bean:write name="id"/>"
		  	onkeydown="if(event.keyCode==13) event.keyCode=9"
			onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" maxlength="30">
		</td>
		<td width="10%" align="right" class="text3">
		  	榜单名称：
		</td>
		<td width="40%" class="text4">  
			<input type="text" name="name" value="<bean:write name="name"/>" >
		</td>
		</tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr class="text5">
	    <td align="center">
	      <input type="submit" class="input1" name="submit01" value="查询" >      
	      <input type="button" class="input1" name="btn_reset" value="重置" onClick="clearForm(ContentForm);">
	    </td>
		</tr>
	</table>
  </form>
<br>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<form name="exeForm" action="intervenorExe.do" method="post">
<input name="actionType" type="hidden" value="exeList"/>
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>榜单列表</b>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">
	  	<input type="checkbox" value='' name="allSelect" onclick="selectAllCB(exeForm,'exeId',this.checked,false);"/>
	  	全选
	  </td>
	  <td align="center" class="title1">榜单编码</td>
	  <td align="center" class="title1">榜单名称</td>
	  <td align="center" class="title1">榜单路径</td>
	  <td align="center" class="title1">榜单简介</td>
	  <td align="center" class="title1">操作</td>
	</tr>
	<%
		String tmpStyle = "text5";
		String temp = "";
		int size = 20;
	%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.Category">
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
			<logic:equal name="vo" property="type" value="1">
				<input type="checkbox" name="exeId" value="<bean:write name="vo" property="id"/>"/>
			</logic:equal>
			<logic:equal name="vo" property="type" value="2">
				<input type="checkbox" name="aaa" value="<bean:write name="vo" property="id"/>" disabled="disabled"/>
			</logic:equal>
			</td>
			<td align="center">
				<bean:write name="vo" property="id"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="name"/>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="namePath"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="desc"/>
      		</td>
      		<td align="center">
				<input type="button" value="执行" onclick="exeIntervenor('<bean:write name="vo" property="id"/>');" <logic:equal name="vo" property="type" value="2">disabled="disabled"</logic:equal>/>
				<input type="button" value="修改关联容器" onclick="editIntervenor('<bean:write name="vo" property="id"/>');" />
      		</td>
		</tr>
	</logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("id",request.getParameter("id"));
        params.put("name",request.getParameter("name"));
        params.put("actionType",request.getParameter("actionType"));
        %>
        <pager:pager name="PageResult" action="/web/intervenor/categoryList.do" params="<%=params%>">
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
</form>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
    	<logic:notEmpty name="PageResult" property="pageInfo">
    	<input type="button" value="紧急上线执行" onclick="exeIntervenorList();">
    	</logic:notEmpty>
    	<input type="button" value="新增容器关联" onclick="addIntervenor();">
    </td>
  </tr>
</table>
<script language="javascript">
function addIntervenor()
{
	ContentForm.action = "add_category.jsp";
	ContentForm.submit();
}

function editIntervenor(id)
{
	ContentForm.action = "categoryRelatingView.do?actionType=relatingView&id=" + id;
	ContentForm.submit();
}

function exeIntervenor(id)
{
	ContentForm.action = "intervenorExe.do?actionType=exe&id=" + id;
	ContentForm.submit();
}

function exeIntervenorList()
{
	var isSelect = "false";
	
	if(exeForm.exeId.length == undefined)
	{
		if(exeForm.exeId.checked == true)
		{
			isSelect = "true";
		}
	}
	else
	{
		for(var i=0; i<exeForm.exeId.length; i++)
		{
			if(exeForm.exeId[i].checked == true)
			{
				isSelect = "true";
				break;
			}
		}
	}
	
	if(isSelect == "false")
	{
		alert("请选择要执行紧急上线应用的货架！");
		return;
	}
	
	exeForm.submit();
}
</script>
</body>
</html>
