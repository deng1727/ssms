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
<title>货架列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	

<form name="exeForm" action="<%=request.getContextPath()%>/web/channelUser/intervenorExe.do" method="post">
<input name="actionType" type="hidden" value="exeList"/>
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>货架列表</b>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">
	  	<input type="checkbox" value='' name="allSelect" onclick="selectAllCB(exeForm,'exeId',this.checked,false);"/>
	  	全选
	  </td>
	  <td align="center" class="title1">渠道名称</td>
	  <td align="center" class="title1">货架ID</td>
	  <td align="center" class="title1">操作</td>
	</tr>
	<%
		String tmpStyle = "text5";
		String temp = "";
		int size = 20;
	%>
	<logic:notEmpty name="PageResult" property="pageInfo">
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryManageVO">
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
				<input type="checkbox" name="aaa" value="<bean:write name="vo" property="categoryId"/>"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="channelsName"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="categoryId"/>
      		</td>
      		
      		<td align="center">
				<input type="button" value="查看详情" onclick="exeIntervenor('<bean:write name="vo" property="categoryId"/>');" />
      		</td>
		</tr>
	</logic:iterate>
	</logic:notEmpty>
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
        <pager:pager name="PageResult" action="/web/channelUser/channelCategoryManage.do" params="<%=params%>">
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


<script language="javascript">


function editIntervenor(id)
{
	ContentForm.action = "channelCategoryManage.do?actionType=showCategoryInfo&id=" + id;
	ContentForm.submit();
}

</script>
</body>
</html>
