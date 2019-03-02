<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page import="com.aspire.common.Validator" %>
<%
  String newPwd = Validator.filter(request.getParameter("newPwd"));
  if(newPwd == null)
  {
    newPwd = "";
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
function checkForm()
{
  //检查旧密码
  var oldPwd = modUserPwdForm.oldPwd.value;
  if(!checkCharOrDigital(oldPwd) || getLength(oldPwd)<6 || getLength(oldPwd)>10)
  {
    alert("密码长度必须是6-10位，并且只能包含数字和字母！");
    modUserPwdForm.oldPwd.focus();
    return false;
  }
  //检查新密码
  var newPwd = modUserPwdForm.newPwd.value;
  if(!checkCharOrDigital(newPwd) || getLength(newPwd)<6 || getLength(newPwd)>10)
  {
    alert("密码长度必须是6-10位，并且只能包含数字和字母！");
    modUserPwdForm.newPwd.focus();
    return false;
  }
  if(modUserPwdForm.newPwd.value != modUserPwdForm.newPwd2.value)
  {
    alert("输入的密码和确认密码必须一致！");
    modUserPwdForm.newPwd.value = "";
    modUserPwdForm.newPwd2.value = "";
    modUserPwdForm.newPwd.focus();
    return false;
  }
  return true;
}
</script></head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="245" class="indexbg1"><br>
        <table width="68%"  border="0" align="center" cellpadding="0" cellspacing="4" bgcolor="#FFFFFF">
          <tr>
            <td height="294" bgcolor="#DDEAEE"><table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><table width="42%"  border="0" cellspacing="1">
                      <tr align="center">
                        <td width="50%" bgcolor="#76A8D5">个人密码修改</td>
                        <td width="50%" bgcolor="#B5D0E8"><a href="editUserInfo.do">详细资料设定</a></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td align="center" bgcolor="#76A8D5"><p><font color="#FFFFFF">用户密码修改</font></p></td>
                </tr>
              </table>
                <table width="90%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
                <form name="modUserPwdForm" method="post" action="modUserPwd.do" onSubmit="return checkForm();">
                  <tr>
                    <td align="center" colspan="2" class="text4">&nbsp;<font color="#ff0000"><msg:messageList/></font></td>
                  </tr>
                  <tr>
                    <td width="29%" align="right" class="text3"><font color="#ff0000">(*)</font>旧 密 码：</td>
                    <td width="71%" class="text4"><input type="password" name="oldPwd"><input type="hidden" name="userID" value="shisam">
                    </td>
                  </tr>
                  <tr>
                    <td align="right" class="text3"><font color="#ff0000">(*)</font>新 密 码：</td>
                    <td class="text4"><input type="password" name="newPwd" value="<%=newPwd%>">
                    </td>
                  </tr>
                  <tr>
                    <td align="right" class="text3"><font color="#ff0000">(*)</font>新密码确认：</td>
                    <td class="text4"><input type="password" name="newPwd2" value="<%=newPwd%>">
                    </td>
                  </tr>
                  <tr>
                    <td align="center" class="text3" colspan="2">
                      <input class="input1" type="submit" value="确定">
                    </td>
                  </tr>
                </form>
              </table></td>
          </tr>
        </table></td>
  </tr>
</table>
</body>
</html>
