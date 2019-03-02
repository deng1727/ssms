<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.web.actionlog.ActionLogVO" %>
<%@page import="java.util.HashMap" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%
String curDate = PublicUtil.getCurDateTime("yyyy-MM-dd");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
<!--
function checkForm()
{
  //key和开始时间不能同时为空
  if(trim(queryLogForm.key.value)=="" && trim(queryLogForm.startDate.value)=="")
  {
    alert("开始日期和关键字不能都为空！");
    queryLogForm.key.focus();
    return false;
  }

  //检查key
  var key = queryLogForm.key.value;
  if(trim(key)!="" && (!checkIllegalChar(key,"'\"<>\\/")
        || getLength(trim(key))>60))
  {
    alert("关键字不能超过60个字符（一个中文占两个字符），\n\n并且不能包含' \" < > \\ /  等字符！");
    queryLogForm.key.focus();
    return false;
  }

  //开始时间不能大于结束时间
  if(trim(queryLogForm.endDate.value)!="" && (trim(queryLogForm.startDate.value) > trim(queryLogForm.endDate.value)))
  {
    alert("结束日期不能小于开始日期！");
    return false;
  }
  queryLogForm.startDate.disabled = false;
  queryLogForm.endDate.disabled = false;
  return true;
}
-->
</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<bean:parameter id="key" name="key" value="" />
<bean:parameter id="startDate" name="startDate" value="" />
<bean:parameter id="endDate" name="endDate" value="" />
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">系统工具----操作日志查询</td>
  </tr>
</table>
  <form name="queryLogForm" action="queryLog.do?subSystem=ssms" method="post" onSubmit="return checkForm();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">

  <tr>
    <td align="center" bgcolor="#E9F0F8">查询关键字：
      <input type="text" name="key" value="<bean:write name="key" />">&nbsp;&nbsp;&nbsp;&nbsp;
      <logic:equal name="result" value="-1">
        操作结果： <select name="result">
                  <option value="-1" selected>全部</option>
                  <option value="1">成功</option>
			      <option value="0">失败</option>
                </select> 
      </logic:equal>
      <logic:equal name="result" value="1">
        操作结果： <select name="result">
                  <option value="-1">全部</option>
                  <option value="1" selected>成功</option>
			      <option value="0">失败</option>
                </select> 
      </logic:equal>
      <logic:equal name="result" value="0">
        操作结果： <select name="result">
                  <option value="-1">全部</option>
                  <option value="1">成功</option>
			      <option value="0" selected>失败</option>
                </select> 
      </logic:equal>
     &nbsp;&nbsp;&nbsp;&nbsp; 
     时间：从<input name="startDate" type="text" size="12" value="<bean:write name="startDate" />" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(queryLogForm.startDate);" align="absmiddle" style="cursor:hand;">
      &nbsp;
      到<input name="endDate" type="text" size="12" value="<bean:write name="endDate" />" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(queryLogForm.endDate);" align="absmiddle" style="cursor:hand;">&nbsp;&nbsp;
    </td>
  </tr>
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="查询">
      <input type="reset" class="input1" name="btn_reset" value="重置">
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
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
  <tr bgcolor="#B8E2FC">
    <td width="4%" align="center">ID</td>
    <td width="7%" align="center">用户名</td>
    <td width="10%" align="center">操作类型</td>
    <td width="6%" align="center">操作结果</td>
    <td width="15%" align="center">操作时间</td>
  </tr>
    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.actionlog.ActionLogVO">
    <tr class="<%=ind.intValue()%2==0?"text4":"text3"%>">
    <td align="center"><a href="getDetail.do?subSystem=ssms&logID=<bean:write name="vo" property="logID"/>"><bean:write name="vo" property="logID"/></a></td>
    <td align="center"><bean:write name="vo" property="userID"/></td>
    <td align="center"><bean:write name="vo" property="actionType"/></td>
    <td align="center"><bean:write name="vo" property="displayActionResult"/></td>
    <td align="center"><bean:write name="vo" property="displayActionTime" /></td>
    </tr>
    </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
        <%
        HashMap params = new HashMap();
        params.put("key",request.getParameter("key"));
        params.put("startDate",request.getParameter("startDate"));
        params.put("endDate",request.getParameter("endDate"));
        params.put("result",request.getParameter("result"));
        %>
        <pager:pager name="PageResult" action="/web/actionlog/queryLog.do?subSystem=ssms" params="<%=params%>">
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
<!--
//结束日期默认为系统当前日期
if(trim(queryLogForm.endDate.value)=="")
{
  queryLogForm.endDate.value = "<%=curDate%>";
}
if(trim(queryLogForm.startDate.value)=="")
{
  queryLogForm.startDate.value = "<%=curDate%>";
}
-->
</script>
</body>
</html>
