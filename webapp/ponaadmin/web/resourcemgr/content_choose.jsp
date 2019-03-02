<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String author = Validator.filter(request.getParameter("author")==null?"":request.getParameter("author"));
String source = Validator.filter(request.getParameter("source")==null?"":request.getParameter("source"));
String keywords = Validator.filter(request.getParameter("keywords")==null?"":request.getParameter("keywords"));
String isRecursive = Validator.filter(request.getParameter("isRecursive")==null?"true":request.getParameter("isRecursive"));
String want = Validator.filter(request.getParameter("want")==null?"true":request.getParameter("want"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<base target="_self">
<title>资源选择</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<script language="JavaScript">
<!--
function setContent()
{
<logic:notEmpty name="PageResult" property="pageInfo">
  var choosed = false;
  var value;
  for(var i=0;i<chooseContentForm.length;i++)
  {
    if(chooseContentForm.elements[i].type=="radio")
    {
      if(chooseContentForm.elements[i].checked)
      {
        choosed = true;
        value = chooseContentForm.elements[i].value;
        break;
      }
    }
  }
  if(choosed==false)
  {
    alert("请先选择!");
    return;
  }
  window.returnValue=value;
</logic:notEmpty>
  window.close();
}

function checkForm(form)
{
  if(!checkContent(form.keywords,"REG_CHINESE_NUM_LERTER_COMMA"))
  {
    alert("关键字只能包含汉字、英文字母、数字和分割符逗号。");
    form.keywords.focus();
    return false;
  }
  
  return true;
  
}

//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<bean:write name="category" property="namePath"/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="contentListForm" action="contentList.do?isModelWindow=true" method="post" onSubmit="return checkForm(contentListForm);">
  <tr>
    <td colspan="4" align="center" class="title1">
      资源查询<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <input type="hidden" name="want" value="<%=want%>">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">资源名称：
    </td>
    <td width="30%" class="text4">
      <input type="text" name="name" value="<%=name%>">
      <input type="hidden" name="isRecursive" value="true">
    </td>
    <td align="right" class="text3">作者： </td>
    <td class="text4"><input type="text" name="author" value="<%=author%>"></td>
  </tr>
  <tr>
    <td align="right" class="text3">来源： </td>
    <td class="text4"><input type="text" name="source" value="<%=source%>"></td>
    <td align="right" class="text3">关键字： </td>
    <td class="text4"><input type="text" name="keywords" value="<%=keywords%>"></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit" type="submit" class="input1" value="查询">
    <input name="reset" type="reset" class="input1" value="重置"></td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">资源管理</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <td align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
  </logic:empty>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <%
        HashMap params = new HashMap();
        params.put("name",name);
        params.put("author",author);
        params.put("source",source);
        params.put("keywords",keywords);
        params.put("isRecursive",isRecursive);
        params.put("categoryID",request.getParameter("categoryID"));
        params.put("want",want);
        %>
        <pager:pager name="PageResult" form="chooseContentForm" action="/web/resourcemgr/contentList.do?isModelWindow=true" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:locationid="1"/>
        </pager:pager>

    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <% int leave = 0; %>  
  <form name="chooseContentForm" action="" method="post">
    <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.gcontent.GContent">
    <%if(ind.intValue()%4==0){%>
  <tr align="center" class="text4">
    <%}%>
    <td>
        <img src="<%=request.getContextPath()%><bean:write name="content" property="previewURL"/>" width="110" height="87" border="0"></br>
        <input type="radio" name="resourceID" 
        <logic:equal parameter="want" value="URLPath">
        value="<bean:write name="content" property="URLPath"/>"
        </logic:equal>
        <logic:notEqual parameter="want" value="URLPath">
        value="<bean:write name="content" property="id"/>;<bean:write name="content" property="URL"/>"
        </logic:notEqual>
        >
        <bean:write name="content" property="name"/><br>
        <bean:write name="content" property="URL"/>
    </td>
    <%if(ind.intValue()%4==3){%>
    </tr>
    <%}else{%>
      <% leave = 3-(ind.intValue())%4;%>
    <%}%>  
    </logic:iterate>
<%
      //计算出剩下的还有几个需要补<td>
      if(leave!=0)
      {
        for(int b=0;b<leave;b++)
        {
%>
          <td width="25%" align="center">&nbsp</td>
<%
        }
%>
        </tr>
<%
      }
%>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <pager:pager name="PageResult" form="chooseContentForm" action="/web/resourcemgr/contentList.do?isModelWindow=true" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="2"/>
        </pager:pager>

    </td>
  </tr>
</table>
</logic:notEmpty>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
            <td align="center" class="text5">
			<input name="buttonSet" type="button" class="input1"  onClick="return setContent();" value="确定">              
          </td>
        </tr>
</table>
</body>
</html>
