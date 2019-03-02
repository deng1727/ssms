<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.constant.ResourceConstants" %>
<%@page import="com.aspire.ponaadmin.web.constant.ErrorCode" %>
<%@ taglib uri="message" prefix="msg" %>
<%

  //////////////////////////////////////////////////////////////////////////////////////////////
  //
  //      登录提示页面，提示用户登录失败，或者一些状态信息（如用户被锁定、密码被复位等）
  //        goURL表示返回的url
  //
  //////////////////////////////////////////////////////////////////////////////////////////////

  String goURL = (String)request.getAttribute("goURL");
  if(goURL == null || goURL.trim().equals(""))
  {
    goURL = "javascript:history.go(-1);";
  }
  
  int loginResult = ((Integer)request.getAttribute("loginResult")).intValue();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<table width="100%"  border="0" cellpadding="0" cellspacing="0" background="../../image/index_bg01.gif">
  <tr>
    <td width="33%"><img src="../../image/index_logo01.gif" width="446" height="65"></td>
    <td width="67%">&nbsp;</td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" background="../../image/index_bg02.gif">
  <tr>
    <td width="38%"><img src="../../image/index_bg02.gif" width="54" height="21"></td>
    <td width="62%">&nbsp;</td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="245" class="indexbg1"><br>
      <table width="69%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="99%" height="297" bgcolor="#FFFFFF"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
          <tr>
            <td width="226" height="289"><img src="../../image/index_pic01.gif" width="226" height="289"></td>
            <td valign="bottom" bgcolor="#DDEAEE">
                    <table width="80%" height="103"  border="0" align="center" cellpadding="2" cellspacing="3">
                      <tr> 
                        <td height="22" align="center"><%if(loginResult!=ErrorCode.SUCC){%>登录失败！<br/><%}%><msg:messageList/></td>
                        </td>
                      </tr>
                      <tr> 
                        <td align="center">
                          <input type="button" class="input1" name="btn_ok" value="确定" onClick="window.location.href='<%=goURL%>';">
                        </td>
                      </tr>
                    </table>
              <br>
              <br>  
              <table width="81%"  border="0" align="center">
                <tr>
                  <td align="right"><hr align="center" size="1" color="#a8a8a8">
                    </td>
                </tr>
              </table>
              <br>
            </td>
          </tr>
        </table></td>
        <td width="1%" valign="bottom"><img src="../../image/index_shadow01.gif" width="7" height="287"></td>
      </tr>
      <tr align="right">
        <td colspan="2"><img src="../../image/index_shadow02.gif" width="670" height="6"></td>
        </tr>
      <tr align="right">
        <td height="60" colspan="2"><hr align="center" size="1" color="#a8a8a8">
          <img src="../../image/index_rights.gif" width="396" height="12"></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
