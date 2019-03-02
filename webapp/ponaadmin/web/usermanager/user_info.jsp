<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%@ page import="com.aspire.common.Validator" %>
<%

  //本页面可以接受actionType参数。
  //check：审核用户，显示审核相关按钮；
  //manager：锁定用户；解锁用户；密码复位；
  
  UserVO user = (UserVO)request.getAttribute("userVO");
  String actionType = Validator.filter(request.getParameter("actionType"));
  if(actionType==null)
  {
    actionType = "";
  }
  
  //如果当前用户是系统内部管理员，就不显示生日、性别、证件、公司等信息
  boolean isAdmin = false;
  if(user.getUserID().equals("ponaadmin"))
  {
    isAdmin = true;
  }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
function checkUserPass()
{
  if(confirm("您确定要通过所选择用户的注册请求吗？"))
  {
    actionForm.result.value="y";
    actionForm.action = "checkUser.do";
    actionForm.submit();
  }
}
function checkUserFail()
{
  if(confirm("您确定要拒绝所选择用户的注册请求吗？"))
  {
    actionForm.result.value="n";
    actionForm.action = "user_checkfail.jsp";
    actionForm.submit();
  }
}
function lockUser()
{
  if(confirm("您确定要锁定此用户？"))
  {
    actionForm.action = "lockUser.do";
    actionForm.submit();
  }
}
function unlockUser(userID)
{
  if(confirm("您确定要解锁此用户？"))
  {
    actionForm.action = "unlockUser.do";
    actionForm.submit();
  }
}
function resetPwd()
{
  if(confirm("您确定要复位此用户的密码吗？"))
  {
    actionForm.action = "resetPwd.do";
    actionForm.submit();
  }
}
-->
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1"><FONT class=large>用户管理----</FONT>用户详细信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">姓名：</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getName())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">性别： </td>
    <td class="text4">
  <%
    if(user.getSex()==null||user.getSex().equals("M")) out.write("男");
    else out.write("女");
  %>  
      
  </td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">生日：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getBirthday())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">证件类型：</td>
    <td class="text4">身份证
    </td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">证件号码：</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getCertID())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">公司名称：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getCompanyName())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">公司地址：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getCompanyAddr())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">邮政编码：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getPostcode())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">联系电话：</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getPhone())%>
  </td>
  </tr>
  <tr>
    <td align="right" class="text3">手机：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getMobile())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">E-mail：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getEmail())%>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">QQ：</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getQQ())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">MSN：</td>
    <td class="text4">
      <%=PublicUtil.getPageStr(user.getMSN())%>
    </td>
  </tr>
  <tr class="text3">
    <td align="center" colspan="2">
      <right:checkDisplay rightID="0_1103_USER_CHECK"><%if(actionType.equals("check")){%><%if(user.getState()==UserManagerConstant.STATE_TO_BE_CHECK){%><input type="button" class="input1" name="btn_pass" value="通过审核" onClick="javascript:checkUserPass();"><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1103_USER_CHECK"><%if(actionType.equals("check")){%><%if(user.getState()==UserManagerConstant.STATE_TO_BE_CHECK){%><input type="button" class="input1" name="btn_notpass" value="不通过审核" onClick="javascript:checkUserFail();"><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1104_USER_LOCK"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_NORMAL){%><input type="button" class="input1" name="btn_lock" value="锁定" onClick="javascript:lockUser();"><%}%><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1104_USER_LOCK"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_LOCKED){%><input type="button" class="input1" name="btn_unlock" value="解锁" onClick="javascript:unlockUser();"><%}%><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1105_USER_RESETPWD"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_NORMAL || user.getState()==UserManagerConstant.STATE_PWD_RESET){%><input type="button" class="input1" name="btn_resetpwd" value="密码复位" onClick="javascript:resetPwd();"><%}%><%}%><%}%></right:checkDisplay>
      <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</table>
<form name="actionForm" action="" method="post">
  <input type="hidden" name="userID" value="<%=user.getUserID()%>">
  <input type="hidden" name="result">
</form>
</body>
</html>
