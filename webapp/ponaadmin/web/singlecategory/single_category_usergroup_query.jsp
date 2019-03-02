<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="java.util.HashMap"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>

<%
  String code=Validator.filter(request.getParameter("code")==null?"":request.getParameter("code"));
  String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
  String type = Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架用户组列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">货架用户组查询</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="../singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList" method="post" >
  
  <tr>
    <td width="10%" align="right" class="text3">
    	用户组内码：
    </td>
    <td width="40%" class="text4">
    	<input type="text" name="code" value="<bean:write name="code"/>">
    </td>
    <td width="10%" align="right" class="text3">
    	用户组名称：
    </td>
    <td width="40%" class="text4">
    	<input type="text" name="name" value="<bean:write name="name"/>">
    </td>
  </tr>
  <tr>
    <td width="10%" align="right" class="text3">
    	用户组类型：
    </td>
    <td width="40%" class="text4">
    	<select name="type">
			<option value=""   <logic:equal name="type" value="">selected="selected"</logic:equal>  ></option>
			<option value="1"  <logic:equal name="type" value="1">selected="selected"</logic:equal> >货架分类</option>
			<option value="2"  <logic:equal name="type" value="2">selected="selected"</logic:equal> >货架商品</option>
		</select>
    </td>
    <td width="10%" align="right" class="text3">
    </td>
    <td width="40%" class="text4">
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
     货架用户组列表
    </td>
  </tr>
</table>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<form name="form" action="" method="post" >
<input type="hidden" name="selectRadio"/>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF" >
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1" width="8%">
	  	<input type="checkbox" value='' name="allSelect" onclick="selectAllCB(form,'selectRadio',this.checked,false);"/>
	  	全选
	  </td>
	  <td align="center" class="title1" width="8%">用户组内码</td>
	  <td align="center" class="title1" width="20%">用户组名称</td>
	  <td align="center" class="title1" width="20%">用户组类型</td>
	  <td align="center" class="title1" width="15%">操作</td>
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryUserGroupVO">
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
				<input type="checkbox" value='<bean:write name="vo" property="code"/>' name="selectRadio"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="code"/>
			</td>
			<td align="center" >
				<bean:write name="vo" property="name"/>
			</td>
			<td align="center" >
				<logic:equal name="vo" property="type" value="1">货架分类</logic:equal> 
				<logic:equal name="vo" property="type" value="2">货架商品</logic:equal> 
			</td>
     		<td align="center">
     			<input type="button" value="修改" onclick="edit('<bean:write name="vo" property="code"/>','<%=vo.getName()%>','<bean:write name="vo" property="type"/>');"/>
				&nbsp;&nbsp;
				<input type="button" value="删除" onclick="del('<bean:write name="vo" property="code"/>','<bean:write name="vo" property="type"/>');"/> 
			</td>
		</tr>
	</logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("code",request.getParameter("code"));
        params.put("name",request.getParameter("name"));
        params.put("type",request.getParameter("type"));
        %>
        <pager:pager name="PageResult" action="/web/singlecategory/singleCategoryUserGroupAction.do?opType=doQueryList" params="<%=params%>">
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
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="新增用户组" onClick="addCategoryRule();"/>
		</td>
	</tr>
</table>
<script language="javascript">


function del(id,type)
{
	if(!confirm('删除货架用户组会删除此货架用户组下的所有货架,确定要删除这条货架用户组吗？'))
	{
	   return false;
	}
	window.self.location.href="../singlecategory/singleCategoryUserGroupAction.do?opType=doDel&code="+id+"&type="+type;
}

function edit(id,name,type)
{
	window.self.location.href="../singlecategory/singleCategoryUserGroupAction.do?opType=toUpdate&code="+id+"&type="+type+"&name="+name;
}


function addCategoryRule()
{
	window.self.location.href="single_category_usergroup_add.jsp";
}


</script>
</body>
</html>
