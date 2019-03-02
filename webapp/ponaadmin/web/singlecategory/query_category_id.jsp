<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<%@page import="java.util.HashMap"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />
</head>
<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">货架列表</td>
  </tr>
</table>
<form name="ContentForm" action="../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryCategoryList" method="post">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="20%" align="right" class="text3">
    	货架内码：
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="categoryId" value="<bean:write name="categoryId"/>"/>
    </td>
    <td width="20%" align="right" class="text3">
    	货架名称：
    </td>
    <td width="30%" class="text4">  
	    <input type="text" name="categoryName" value="<bean:write name="categoryName"/>"/>
    </td>
  </tr>
  
  <tr>
    <td width="20%" align="right" class="text3">
    	关联门户
    </td>
    <td width="30%" class="text4">
    	
		<select name="relation">
			<option value=""   <logic:equal name="relation" value="">selected="selected"</logic:equal>></option>
			<option value="W"  <logic:equal name="relation" value="W">selected="selected"</logic:equal> >WWW门户</option>
			<option value="O"  <logic:equal name="relation" value="O">selected="selected"</logic:equal> >终端门户</option>
			<option value="P"  <logic:equal name="relation" value="P">selected="selected"</logic:equal> >PC门户</option>
			<option value="A"  <logic:equal name="relation" value="A">selected="selected"</logic:equal> >WAP门户</option>
		</select>
    </td>
    <td width="20%" align="right" class="text3">
    	
    </td>
    <td width="30%" class="text4">  
    </td>
  </tr>

<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="查询" >      
      <input type="button" class="input1" name="btn_reset" value="重置" onClick="clearForm(ContentForm);">
    </td>
  </tr>
</table>
</form>
</table>
  <table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     货架列表
    </td>
  </tr>
</table>
<logic:notEmpty name="notice">
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="1" bgcolor="#FFFFFF">
		<tr bgcolor="#B8E2FC">
			<td colspan="5" align="center"><font color="#ff0000"><bean:write
				name="notice" /></font></td>
		</tr>
	</table>
</logic:notEmpty>
<logic:empty name="notice">
	<logic:empty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#FFFFFF">
			<tr bgcolor="#B8E2FC">
				<td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
			</tr>
		</table>
	</logic:empty>
</logic:empty>
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="确定" onClick="fReturn();"/>
		</td>
	</tr>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td width="10%" align="center" class="title1">请选择</td>
	  <td width="25%" align="center" class="title1">货架名称</td>
	  <td width="40%" align="center" class="title1">货架路径</td>
	  <td width="25%" align="center" class="title1">货架简介</td>
	</tr>
	<%String tmpStyle = "text5";%>
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
				<input type="checkbox" name="id" value="<bean:write name="vo" property="id"/>" />
			</td>
      		<td align="center">
      		<input type="hidden" name="cname<bean:write name="ind"/>" value="<bean:write name="vo" property="name"/>">
      			<bean:write name="vo" property="name"/>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="namePath"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="desc"/>
      		</td>
		</tr>
	</logic:iterate>
</table>
<!-- 
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("categoryName",request.getParameter("categoryName"));
        %>
        <pager:pager name="PageResult" form="ContentForm" action="/web/singlecategory/singleCategoryUserGroupAction.do?opType=doQueryCategoryList" params="<%=params%>">
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
 -->
</logic:notEmpty>

<script language="javascript">
function fReturn()
{
	var obj = document.getElementsByName("id");
	var str="";
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].checked)
            {
                str=str+obj[i].value+",";
            }
        }
    }
    if(str!="")
    {
    	str=str.substring(0,str.length-1);
    }
    window.returnValue=str;
    window.close();
}
</script>
</body>
</html>
