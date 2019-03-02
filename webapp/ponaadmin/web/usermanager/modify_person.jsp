<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%

  UserVO userVO = (UserVO)request.getAttribute("userVO");
  //如果当前用户是系统内部管理员，就不显示生日、性别、证件、公司等信息
  boolean isAdmin = false;
  if(userVO.getUserID().equals("ponaadmin"))
  {
    isAdmin = true;
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="Javascript" type="text/javascript" src="../../js/check_userinfo.js"></script>

</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="245" class="indexbg1"><br>
        <table width="79%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="99%" height="297" bgcolor="#FFFFFF"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
                <tr>
                  <td height="459" bgcolor="#DDEAEE"><table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td><table width="42%"  border="0" cellspacing="1">
                          <tr align="center">
                            <td width="50%" bgcolor="#B5D0E8">
                              <a href="modify_pwd.jsp">个人密码修改</a>
                            </td>
                            <td width="50%" bgcolor="#76A8D5">详细资料设定</td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr>
                        <td align="center" bgcolor="#76A8D5"><p><font color="#FFFFFF">用户详细信息设定<br>
                          (带*号的</font><font color="#FFFFFF">为必填项) </font></p></td>
                      </tr>
                    </table>
                      <table width="95%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FFFFFF">
                      <form name="userInfoForm" method="post" action="saveUserInfo.do" onSubmit="return checkForm();">
                        <tr>
                          <td width="29%" align="right" class="text3"><p><font color="#FF0000">(*)</font>姓名：</p></td>
                          <td width="71%" class="text4">
                        <input type="text" name="name" size="30" maxlength="30" value="<%=PublicUtil.getPageStr(userVO.getName())%>" disabled>
                        <input type="hidden" name="userID" value="<%=userVO.getUserID()%>">
                      </td>
                        </tr>
                        <tr <%if(isAdmin){%>style="display:none"<%}%>>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>性别： </td>
                          <td class="text4">
                        <input type="radio" name="sex" value="M" <%if(userVO.getSex()==null||userVO.getSex().equals("M")) out.write("checked");%> disabled>
                          男

                        <input type="radio" name="sex" value="F" <%if(userVO.getSex()!=null&&userVO.getSex().equals("F")) out.write("checked");%> disabled>
                            女</td>
                        </tr>
                        <tr <%if(isAdmin){%>style="display:none"<%}%>>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>生日：</td>
                          <td class="text4">
                        <input name="birthday" type="text" size="10" maxlength="10" value="<%=PublicUtil.getPageStr(userVO.getBirthday())%>" disabled>
                      </td>
                        </tr>
                        <tr <%if(isAdmin){%>style="display:none"<%}%>>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>证件类型：</td>
                          <td class="text4">
                        <select name="certType" disabled>
                          <option value="10" selected>身份证</option>
                        </select>
                          </td>
                        </tr>
                        <tr <%if(isAdmin){%>style="display:none"<%}%>>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>证件号码：</td>
                          <td width="71%" class="text4">
                        <input name="certID" type="text" size="25" maxlength="25" value="<%=PublicUtil.getPageStr(userVO.getCertID())%>" disabled>
                      </td>
                        </tr>
                        <tr <%if(isAdmin){%>style="display:none"<%}%>>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>公司名称：</td>
                          <td class="text4">
                        <input name="companyName" type="text" size="50" maxlength="100" value="<%=PublicUtil.getPageStr(userVO.getCompanyName())%>" disabled>
                      </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>公司地址：</td>
                          <td class="text4">
                        <input name="companyAddr" type="text" size="60" maxlength="160" value="<%=PublicUtil.getPageStr(userVO.getCompanyAddr())%>">
                      </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>邮政编码：</td>
                          <td class="text4">
                        <input name="postcode" type="text" size="6" maxlength="6" value="<%=PublicUtil.getPageStr(userVO.getPostcode())%>">
                      </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>联系电话：</td>
                          <td width="71%" class="text4">
                        <input name="phone" type="text" size="20" maxlength="80" value="<%=PublicUtil.getPageStr(userVO.getPhone())%>">
                        (格式：区号-号码-分机，比如0755-88888888-0259)</td>
                        </tr>
                        <tr>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>手机：</td>
                          <td class="text4">
                        <input name="mobile" type="text" size="11" maxlength="11" value="<%=PublicUtil.getPageStr(userVO.getMobile())%>">
                      </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3"><font color="#FF0000">(*)</font>E-mail：</td>
                          <td class="text4">
                        <input type="text" name="email" maxlength="80" size="40" value="<%=PublicUtil.getPageStr(userVO.getEmail())%>">
                          </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3">QQ：</td>
                          <td class="text4">
                        <input name="QQ" type="text" size="10" maxlength="10" value="<%=PublicUtil.getPageStr(userVO.getQQ())%>">
                      </td>
                        </tr>
                        <tr>
                          <td align="right" class="text3">MSN：</td>
                          <td class="text4">
                        <input type="text" name="MSN" size="40" maxlength="80" value="<%=PublicUtil.getPageStr(userVO.getMSN())%>">
                      </td>
                        </tr>
                        <tr>
                          <td align="center" class="text3" colspan="2">
                            <input class="input1" type="submit" value="确定" name="submit01">
                            <input class="input1" type="button" value="重置" name="btn_reset" onClick="javascript:resetForm(userInfoForm);">
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
