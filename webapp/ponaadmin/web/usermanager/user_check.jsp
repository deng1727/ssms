<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.page.PageResult" %>
<%@page import="java.util.HashMap" %>
<%
  PageResult pageResult = (PageResult)request.getAttribute("PageResult");
  List userList = pageResult.getPageInfo();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="javascript">
function checkUserPass()
{
  if(checkForm() && confirm("您确定要通过所选择用户的注册请求吗？"))
  {
    checkUserForm.result.value="y";
    checkUserForm.action = "checkUser.do";
    checkUserForm.submit();
  }
}
function checkUserFail()
{
  if(checkForm() && confirm("您确定要拒绝所选择用户的注册请求吗？"))
  {
    checkUserForm.result.value="n";
    checkUserForm.action = "user_checkfail.jsp";
    checkUserForm.submit();
  }
}
function checkForm()
{
  //检查是否至少选择了一个用户进行审核操作
  var choosed = false;
  for(var i=0;i<checkUserForm.length;i++)
  {
    if(checkUserForm.elements[i].type=="radio" && checkUserForm.elements[i].name == "userID")
    {
      if(checkUserForm.elements[i].checked)
      {
        choosed = true;
        break;
      }
    }
  }
  if(choosed==false)
  {
    alert("请选择您要处理的用户！");
    return false;
  }
  return true;
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" bgcolor="#FFFFFF">
  <form name="checkUserForm" action="checkUser.do" method="post" onSubmit="return checkForm();">
  <tr class="title1">
    <td align="center" colspan="3"><FONT class=large>用户管理----用户审核</FONT></td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
  <tr>
    <td colspan="3" align="center"><font color="#ff0000">没有未审核的用户。</font></td>
  </tr>
  </logic:empty>
  <logic:notEmpty name="PageResult" property="pageInfo">
  <tr align="center" class="title1">
    <td width="20%">选定<input type="hidden" name="result"></td>
    <td width="40%">待审用户帐号</td>
    <td width="40%">用户姓名</td>
  </tr>
<%
  UserVO userVO = null;
  for(int i=0;i<userList.size();i++)
  {
    userVO = (UserVO)userList.get(i);
%>  
  <tr align="center" class="<%=(i%2==0)?"text4":"text3"%>">
    <td><input type="radio" name="userID" value="<%=userVO.getUserID()%>"></td>
    <td><a href="viewUserInfo.do?actionType=check&userID=<%=userVO.getUserID()%>"><%=userVO.getUserID()%></a></td>
    <td><a href="viewUserInfo.do?userID=<%=userVO.getUserID()%>"><%=userVO.getName()%></a></td>
  </tr>
<%
  }
%>    
  <tr class="text1">
    <td align="right" colspan="3">
        <%
        HashMap params = new HashMap();
        params.put("key",request.getParameter("key"));
        %>
        <pager:pager name="PageResult" action="/web/usermanager/queryUncheckUser.do" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
  <tr class="text3">
    <td align="center" colspan="3">
      <right:checkDisplay rightID="0_1103_USER_CHECK"><input type="button" class="input1" name="btn_pass" value="通过审核" onClick="javascript:checkUserPass();"></right:checkDisplay>
      <right:checkDisplay rightID="0_1103_USER_CHECK"><input type="button" class="input1" name="btn_notpass" value="不通过审核" onClick="javascript:checkUserFail();"></right:checkDisplay>
    </td>
  </tr>
  </logic:notEmpty>
  </form>
</table>
</body>
</html>
