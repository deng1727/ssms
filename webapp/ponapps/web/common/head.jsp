<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserSessionVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerBO" %>

<%
  UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(session);

  String userID = "unKnown";
  String userRoles = "unKnown";
  if(userSession!=null)
  {
    UserVO user = userSession.getUser();
    userID = user.getUserID();
    userRoles = user.getUserRolesInfo();
  }
  String context = request.getContextPath();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<script language="javascript">
function showDialog(url)
{
  window.showModalDialog(url,"","center:yes;dialogWidth:200px;dialogHeight:220px;resizable: yes; help: no; status: no; scroll: no; ");
}

function showVersionPage(url)
{	
	var top=(screen.height-362)/2;
	var left=(screen.width-467)/2;
	var params="height=362, width=467, top ="+top+" ,left ="+left+",toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no";
	var popup=window.open(url,"",params);
}

</script>

<body bgcolor="#1F8AC3" leftmargin="0" topmargin="0">
<%
  //如果用户的userSession不存在，说明用户没有登录或者session过期，转到登录页面。
  if(userSession==null)
  {
%>
    <script language="javascript">
    <!--
      window.top.location="<%=context%>/web/notLogin.do";
    -->
    </script>
<%  
  }
%>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" background="../../image/page_bg01.gif">
  <tr>
    <td width="48%"><img src="../../image/page_logo01.gif" width="382" height="48"></td>
    <td width="52%"><table width="74%"  border="0" align="right" cellspacing="1">
      <tr align="center">
        <td width="40%">&nbsp;</td>
        <td width="30%"><a href="../usermanager/editUserInfo.do" target="mainFrame"><font color="#FFFFFF">个人资料修改</font></a></td>
        <td width="30%"><a href="<%=context%>/web/exit.jsp" target="_parent"><font color="#FFFFFF">退出系统</font></a></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="80%">
      <img src="../../image/tree/mobile.gif" width="15" height="15" border="0" align="absmiddle"><font color="#FFFFFF"> 欢迎您，<%=userID%>，您的身份是</font><span class="bluetxt"><%=userRoles%></span>
    </td>
    <td width="18%" align="right"><a href="#"   onClick="javascript:showVersionPage('<%=context%>/web/version.jsp')" >版本信息</a></td>
    <td width="2%" align="right">&nbsp;</td>
  </tr>
</table>
</body>
</html>
