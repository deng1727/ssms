<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="message" prefix="msg" %>
<%
  String userID = (String)request.getAttribute("userID");
  if(userID==null)
  {
    userID = "";
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
var form_disabled = false;
function checkForm()
{

  if(form_disabled)
  {
    return false;
  }
  
  //检查用户帐号
  var userID = registerForm.userID.value;

  if(trim(userID)=="")
  {
    alert("请输入您的用户名！");
    registerForm.userID.focus();
    return false;
  }

  if(!checkName(trim(userID),"REG_CHINESE_NUM_LERTER_COMMA") 
  || getLength(trim(userID))<4  || getLength(trim(userID))>20)
  {
    alert("用户名只能包含数字、字母、逗号、下划线、汉字，\n\n并且长度必须为4-20个字符（一个中文占两个字符）。");
    registerForm.userID.focus();
    return false;
  }
  //检查密码
  var password = registerForm.password.value;
  if(!checkCharOrDigital(password) || getLength(password)<6 || getLength(password)>10)
  {
    alert("密码只能包含字母和数字，并且长度为6-10位！");
    registerForm.password.focus();
    return false;
  }
  if(registerForm.password.value != registerForm.password2.value)
  {
    alert("输入的密码和确认密码必须一致。");
    registerForm.password.focus();
    return false;
  }
  //检查图形附加码
  var imgCode = registerForm.imgCode.value;
  if(!checkDigital(imgCode) || getLength(imgCode)!=4)
  {
    alert("附加码必须是4位的数字！");
    registerForm.imgCode.focus();
    return false;
  }
  form_disabled = true;
  return true;
}

/*
 * 检查一个输入框的内容
 */
function checkName(field,ctype)
{
    var reg;
    if(ctype=="REG_CHINESE_NUM_LETTER")
    {
        reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    }
    else if(ctype=="REG_CHINESE_NUM_LERTER_COMMA")
    {
        reg = /^[\w,\u4e00-\u9fa5]+$/g
    }
    else if(ctype=="REG_CHINESE_NUM_LERTER_UNDERLINE")
    {
        reg = /^[\w\_\u4e00-\u9fa5]+$/g
    }
    return reg.test(field);
}
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
        <td width="99%" height="297" bgcolor="#FFFFFF">
        <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
          <tr>
            <td align="center"><font color="#ff0000"><msg:messageList/></font></td>
          </tr>
          <tr>
            <td height="289" bgcolor="#DDEAEE"><table width="90%"  border="0" align="center" bgcolor="#76A8D5">
                <tr>
                  <td align="center"><font color="#FFFFFF">新用户注册</font></td>
                </tr>
              </table>
                <table width="90%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
                <form name="registerForm" method="post" action="register.do" onSubmit="return checkForm();" disabled>
                  <tr bgcolor="#F5F7F8">
                    <td width="29%" align="right" class="text3"><font color="#ff0000">(*)</font>用 户 名：</td>
                    <td width="71%"><input type="text" name="userID" size="20" maxlength="20" value=<%=userID%>>
                    </td>
                  </tr>
                  <tr bgcolor="#F5F7F8">
                    <td align="right" class="text3"><font color="#ff0000">(*)</font>密　　码：</td>
                    <td><input type="password" name="password" size="20" maxlength="10">
                    </td>
                  </tr>
                  <tr bgcolor="#F5F7F8">
                    <td align="right" class="text3"><font color="#ff0000">(*)</font>确认密码：</td>
                    <td><input type="password" name="password2" size="20" maxlength="10">
                    </td>
                  </tr>
                  <tr bgcolor="#F5F7F8" class="text3">
                    <td align="right"><font color="#ff0000">(*)</font>附 加 码：</td>
                    <td bgcolor="#F5F7F8"><input name="imgCode" type="text" size="10" maxlength="4"><img src="<%=request.getContextPath()%>/randomNumImg?.pa=<%=System.currentTimeMillis()%>" align="absmiddle"></td>
                  </tr>
                  <tr>
                    <td align="center" class="text3" colspan="2">
                       <input type="submit" class="input1" name="submit01" value="下一步">
                       <input name="btn_back" type="button" class="input1" onClick="window.location.href='../index.jsp';" value="返回">
                    </td>
                  </tr>
                </form>
                </table></td>
          </tr>
        </table></td>
        </tr>
      <tr align="right">
        <td height="45" valign="bottom"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
            <tr align="right">
              <td height="1" align="center" bgcolor="#D9E8EA"></td>
            </tr>
            <tr align="right">
              <td width="96%" align="center" bgcolor="#6AA0D1"><span class="link2">卓望科技 版权所有<font face="Arial, Helvetica, sans-serif"> Copyright 2003-2005 ASPire Technologies. All Rights Reserved.</font></span></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
