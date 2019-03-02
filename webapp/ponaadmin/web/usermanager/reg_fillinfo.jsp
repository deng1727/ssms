<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%

  UserVO userVO = (UserVO)request.getAttribute("userVO");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="Javascript" type="text/javascript" src="../../js/check_userinfo.js"></script>
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
        <table width="79%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="99%" height="297" bgcolor="#FFFFFF"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
                <tr>
                  <td height="459" bgcolor="#DDEAEE">
                    <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="center" bgcolor="#76A8D5"><p><font color="#FFFFFF">您已经成功预注册，请继续输入您的个人详细信息，以便通过管理员的注册审核。<br>
                          (填写准确的用户详细信息有助于您尽快通过审核，带*号的</font><font color="#FFFFFF">为必填项) </font></p></td>
                      </tr>
                    </table>
                      <table width="95%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
                      <form name="userInfoForm" method="post" action="registerInfo.do" onSubmit="return checkForm();">
                        <tr bgcolor="#F5F7F8">
                          <td width="29%" align="right" class="text3"><p><font color="#FF0000">(*)</font>姓名：</p></td>
                          <td width="71%">
                        <input type="text" name="name" size="30" maxlength="30" value="<%=PublicUtil.getPageStr(userVO.getName())%>">
                        <input type="hidden" name="userID" value="<%=userVO.getUserID()%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>性别： </td>
                          <td>
                        <input type="radio" name="sex" value="M" <%if(userVO.getSex()==null||userVO.getSex().equals("M")) out.write("checked");%>>
                          男

                        <input type="radio" name="sex" value="F" <%if(userVO.getSex()!=null&&userVO.getSex().equals("F")) out.write("checked");%>>
                            女</td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>生日：</td>
                          <td>
                        <input name="birthday" type="text" size="10" maxlength="10" value="<%=PublicUtil.getPageStr(userVO.getBirthday())%>" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(userInfoForm.birthday);" align="absmiddle" style="cursor:hand;">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>证件类型：</td>
                          <td>
                        <select name="certType">
                          <option value="10" selected>身份证</option>
                        </select>
                          </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>证件号码：</td>
                          <td width="71%">
                        <input name="certID" type="text" size="25" maxlength="25" value="<%=PublicUtil.getPageStr(userVO.getCertID())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>公司名称：</td>
                          <td>
                        <input name="companyName" type="text" size="50" maxlength="100" value="<%=PublicUtil.getPageStr(userVO.getCompanyName())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>公司地址：</td>
                          <td>
                        <input name="companyAddr" type="text" size="60" maxlength="160" value="<%=PublicUtil.getPageStr(userVO.getCompanyAddr())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>邮政编码：</td>
                          <td>
                        <input name="postcode" type="text" size="6" maxlength="6" value="<%=PublicUtil.getPageStr(userVO.getPostcode())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>联系电话：</td>
                          <td width="71%">
                        <input name="phone" type="text" size="20" maxlength="80" value="<%=PublicUtil.getPageStr(userVO.getPhone())%>">
                        (格式：区号-号码-分机，比如0755-88888888-0259)</td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>手机：</td>
                          <td>
                        <input name="mobile" type="text" size="11" maxlength="11" value="<%=PublicUtil.getPageStr(userVO.getMobile())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>E-mail：</td>
                          <td>
                        <input type="text" name="email" maxlength="80" size="40" value="<%=PublicUtil.getPageStr(userVO.getEmail())%>">
                          </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3">QQ：</td>
                          <td>
                        <input name="QQ" type="text" size="10" maxlength="10" value="<%=PublicUtil.getPageStr(userVO.getQQ())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td align="right" class="text3">MSN：</td>
                          <td>
                        <input type="text" name="MSN" size="40" maxlength="80" value="<%=PublicUtil.getPageStr(userVO.getMSN())%>">
                      </td>
                        </tr>
                        <tr bgcolor="#F5F7F8">
                          <td class="text3" colspan="2" align="center">
                            <input type="submit" class="input1" name="submit01" value="确定">
                            <input type="button" class="input1" name="btn_reset" onClick="javascript:resetForm(userInfoForm);" value="重置">
                            <input type="button" class="input1" name="btn_back" onClick="window.location.href='../index.jsp';" value="返回">
                          </td>
                        </tr>
                  </form>
                  </table></td>
                </tr>
            </table></td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
