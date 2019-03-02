<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%
String tmpStyle = "text5";
String categoryName = request.getParameter("categoryName")==null?"":request.getParameter("categoryName");
String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>M-Market阅读货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<form name="form" action="categoryTree.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
    <tr>
    <td colspan="4" align="center" class="title1">
      基地阅读货架信息查询
      <input type="hidden" name="perType" value="categoryQuery">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">货架名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="categoryName" value="<%=categoryName%>"></td>
    <td width="18%" align="right" class="text3">货架ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="categoryId" value="<%=categoryId%>"></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
	    <input name="reset" type="reset" class="input1" value="重置">
    </td>
  </tr>
</table>
</form>

<form name="refForm" action="categoryTree.do" method="post" >
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
     <b>货架详情列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	
  <tr>
    <td width="10%" align="center" class="title1">货架ID</td>     
    <td width="18%" align="center" class="title1">货架名称</td>
    <td width="10%" align="center" class="title1">父货架ID</td>
    <td width="30%" align="center" class="title1">货架路径</td>  
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseread.vo.ReadCategoryVO">
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
			<td align="left">
				<bean:write name="vo" property="id"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="categoryName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="parentId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="path"/>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("categoryName",request.getParameter("categoryName"));
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("perType",request.getParameter("perType"));
        %>
        <pager:pager name="PageResult" action="/web/baseRead/categoryTree.do" params="<%=params%>">
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
</form>
</body>
</html>
