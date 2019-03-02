<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.system.Config" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>
<%
  String userID =  Validator.filter((String)request.getAttribute("userID"));
  if(userID==null)
  {
    userID = "";
  }
  String adminInfo = Config.getInstance().getModuleConfig().getItemValue("adminContactInfo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../js/tools.js"></script>
<script language="javascript">

function showAdminInfo()
{
  var alertInfo = "����ϵ����Ա��ȡ���������룬��ϵ��ʽ���£�\n\n<%=adminInfo%>";
  alert(alertInfo);
}
function checkForm()
{
  //����û��ʺ�
  var userID = loginForm.userID.value;
  if(trim(userID)=="" || !checkName(userID,"REG_CHINESE_NUM_LERTER_COMMA")
        || getLength(trim(userID))<4  || getLength(trim(userID))>20)
  {
    alert("�û���ֻ�ܰ������֡���ĸ�����š��»��ߡ����֣����ҳ��ȱ���Ϊ4-20���ַ���һ������ռ�����ַ�����");
    loginForm.userID.focus();
    return false;
  }
  //�������
  var password = loginForm.password.value;
  if(!checkCharOrDigital(password) || getLength(password)<6 || getLength(password)>10)
  {
    alert("���볤�ȱ�����6-10λ������ֻ�ܰ������ֺ���ĸ��");
    loginForm.password.focus();
    return false;
  }
  //���ͼ�θ�����
  var imgCode = loginForm.imgCode.value;
  if(!checkDigital(imgCode) || getLength(imgCode)!=4)
  {
    alert("�����������4λ�����֣�");
    loginForm.imgCode.focus();
    return false;
  }
  return true;
}

/*
 * ���һ������������
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
<script language="javascript">
<!--
if(top.location.href!=window.location.href)
{
  top.location.href=window.location.href;
}
-->
</script>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" background="../image/index_bg01.gif">
  <tr>
    <td width="33%"><img src="../image/index_logo01.gif" width="446" height="65"></td>
    <td width="67%">&nbsp;</td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" background="../image/index_bg02.gif">
  <tr>
    <td width="38%"><img src="../image/index_bg02.gif" width="54" height="21"></td>
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
            <td width="226" height="289"><img src="../image/index_pic01.gif" width="226" height="289"></td>
            <td valign="bottom" class="text1">
              <form name="loginForm" method="post" action="login.do" onSubmit="return checkForm();">
              <table width="80%" height="103"  border="0" align="center" cellpadding="2" cellspacing="3">
                <tr>
                  <td colspan="2" align="center">&nbsp;<font color="#ff0000"><msg:messageList/></font></td>
                </tr>
                <tr>
                  <td width="26%" height="22" align="right">�û�����</td>
                  <td width="74%"><input type="text" name="userID" size="20" maxlength="20" value="<%=userID%>"></td>
                </tr>
                <tr>
                  <td height="22" align="right">�ܡ��룺</td>
                  <td><input type="password" name="password" size="20" maxlength="10" value=""></td>
                </tr>
                <tr>
                  <td height="22" align="right">�����룺</td>
                  <td><input name="imgCode" type="text" size="10" maxlength="4" align="absmiddle" value=""><img src="<%=request.getContextPath()%>/randomNumImg?.pa=<%=System.currentTimeMillis()%>" align="absmiddle"></td>
                </tr>
                <tr>
                  <td height="22" align="right">&nbsp;</td>
                  <td>
                    <input type="submit" name="submit01" class="input1" value="�� ¼">
                    <input type="button" name="btn_reg" class="input1" value="ע ��" onClick="window.location.href='usermanager/reg_id.jsp';">
                  </td>
                </tr>
              </table>
              <br>  
              <table width="81%"  border="0" align="center">
                <tr>
                  <td align="right"><hr align="center" size="1" color="#a8a8a8">
                    <img src="../image/tree/mobile.gif" align="absmiddle"> <a href="#" onClick="javascript:showAdminInfo()">ȡ������</a></td>
                </tr>
              </table>
              <br>
            </form></td>
          </tr>
        </table></td>
        <td width="1%" valign="bottom"><img src="../image/index_shadow01.gif" width="7" height="287"></td>
      </tr>
      <tr align="right">
        <td colspan="2"><img src="../image/index_shadow02.gif" width="670" height="6"></td>
        </tr>
      <tr align="right">
        <td height="60" colspan="2"><hr align="center" size="1" color="#a8a8a8">          
          ׿���Ƽ� ��Ȩ����<FONT face="Arial, Helvetica, sans-serif"> Copyright 2003-2006 ASPire Technologies. All Rights Reserved.</FONT></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
