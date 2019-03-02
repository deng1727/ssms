<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%@ page import="com.aspire.common.Validator" %>
<%

  //��ҳ����Խ���actionType������
  //check������û�����ʾ�����ذ�ť��
  //manager�������û��������û������븴λ��
  
  UserVO user = (UserVO)request.getAttribute("userVO");
  String actionType = Validator.filter(request.getParameter("actionType"));
  if(actionType==null)
  {
    actionType = "";
  }
  
  //�����ǰ�û���ϵͳ�ڲ�����Ա���Ͳ���ʾ���ա��Ա�֤������˾����Ϣ
  boolean isAdmin = false;
  if(user.getUserID().equals("ponaadmin"))
  {
    isAdmin = true;
  }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
function checkUserPass()
{
  if(confirm("��ȷ��Ҫͨ����ѡ���û���ע��������"))
  {
    actionForm.result.value="y";
    actionForm.action = "checkUser.do";
    actionForm.submit();
  }
}
function checkUserFail()
{
  if(confirm("��ȷ��Ҫ�ܾ���ѡ���û���ע��������"))
  {
    actionForm.result.value="n";
    actionForm.action = "user_checkfail.jsp";
    actionForm.submit();
  }
}
function lockUser()
{
  if(confirm("��ȷ��Ҫ�������û���"))
  {
    actionForm.action = "lockUser.do";
    actionForm.submit();
  }
}
function unlockUser(userID)
{
  if(confirm("��ȷ��Ҫ�������û���"))
  {
    actionForm.action = "unlockUser.do";
    actionForm.submit();
  }
}
function resetPwd()
{
  if(confirm("��ȷ��Ҫ��λ���û���������"))
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
    <td align="center" class="title1"><FONT class=large>�û�����----</FONT>�û���ϸ��Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">������</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getName())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">�Ա� </td>
    <td class="text4">
  <%
    if(user.getSex()==null||user.getSex().equals("M")) out.write("��");
    else out.write("Ů");
  %>  
      
  </td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">���գ�</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getBirthday())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">֤�����ͣ�</td>
    <td class="text4">���֤
    </td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">֤�����룺</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getCertID())%>
</td>
  </tr>
  <tr <%if(isAdmin){%>style="display:none"<%}%>>
    <td align="right" class="text3">��˾���ƣ�</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getCompanyName())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">��˾��ַ��</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getCompanyAddr())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">�������룺</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getPostcode())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">��ϵ�绰��</td>
    <td width="71%" class="text4">
  <%=PublicUtil.getPageStr(user.getPhone())%>
  </td>
  </tr>
  <tr>
    <td align="right" class="text3">�ֻ���</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getMobile())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">E-mail��</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getEmail())%>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">QQ��</td>
    <td class="text4">
  <%=PublicUtil.getPageStr(user.getQQ())%>
</td>
  </tr>
  <tr>
    <td align="right" class="text3">MSN��</td>
    <td class="text4">
      <%=PublicUtil.getPageStr(user.getMSN())%>
    </td>
  </tr>
  <tr class="text3">
    <td align="center" colspan="2">
      <right:checkDisplay rightID="0_1103_USER_CHECK"><%if(actionType.equals("check")){%><%if(user.getState()==UserManagerConstant.STATE_TO_BE_CHECK){%><input type="button" class="input1" name="btn_pass" value="ͨ�����" onClick="javascript:checkUserPass();"><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1103_USER_CHECK"><%if(actionType.equals("check")){%><%if(user.getState()==UserManagerConstant.STATE_TO_BE_CHECK){%><input type="button" class="input1" name="btn_notpass" value="��ͨ�����" onClick="javascript:checkUserFail();"><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1104_USER_LOCK"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_NORMAL){%><input type="button" class="input1" name="btn_lock" value="����" onClick="javascript:lockUser();"><%}%><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1104_USER_LOCK"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_LOCKED){%><input type="button" class="input1" name="btn_unlock" value="����" onClick="javascript:unlockUser();"><%}%><%}%><%}%></right:checkDisplay>
      <right:checkDisplay rightID="0_1105_USER_RESETPWD"><%if(!isAdmin){%><%if(actionType.equals("manager")){%><%if(user.getState()==UserManagerConstant.STATE_NORMAL || user.getState()==UserManagerConstant.STATE_PWD_RESET){%><input type="button" class="input1" name="btn_resetpwd" value="���븴λ" onClick="javascript:resetPwd();"><%}%><%}%><%}%></right:checkDisplay>
      <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
    </td>
  </tr>
</table>
<form name="actionForm" action="" method="post">
  <input type="hidden" name="userID" value="<%=user.getUserID()%>">
  <input type="hidden" name="result">
</form>
</body>
</html>
