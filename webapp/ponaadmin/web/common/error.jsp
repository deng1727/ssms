<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.constant.Constants" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>
<%

  //////////////////////////////////////////////////////////////////////////////////////////////
  //
  //      ���ô���ҳ�棬goURL��ʾ���ص�url��targetFrame��ʾĿ��frame
  //
  //////////////////////////////////////////////////////////////////////////////////////////////

  String goURL = Validator.filter((String)request.getAttribute("goURL"));
  if(goURL == null || goURL.trim().equals(""))
  {
    goURL = Validator.filter(request.getParameter("goURL"));
    if(goURL == null || goURL.trim().equals(""))
    {
      goURL = "javascript:history.go(-1);";
    }
  }
  
  String targetFrame = (String)request.getAttribute("targetFrame");
  if(targetFrame == null || targetFrame.trim().equals(""))
  {
    targetFrame = request.getParameter("targetFrame");
    if(targetFrame == null || targetFrame.trim().equals(""))
    {
      targetFrame = "_self";
    }
  }
  
  String isClose = (String)request.getAttribute(Constants.PARA_ISCLOSE);
  if(isClose == null || isClose.trim().equals(""))
  {
    isClose = request.getParameter(Constants.PARA_ISCLOSE);
    if(isClose == null || isClose.trim().equals(""))
    {
      isClose = "false";
    }
  }

  String standAlone = (String)request.getAttribute(Constants.PARA_ISSTANDALONE);
  if(standAlone == null || standAlone.trim().equals(""))
  {
    standAlone = request.getParameter(Constants.PARA_ISSTANDALONE);
    if(standAlone == null || standAlone.trim().equals(""))
    {
      standAlone = "false";
    }
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="JavaScript">
<!--
function gotoURL()
{
  window.<%=targetFrame.substring(1)%>.location.href="<%=goURL%>";
}
//-->
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<%if(!standAlone.equals("false")){%>
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
<%}%>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="245" class="indexbg1"><br>
      <table width="69%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="99%" height="297" bgcolor="#FFFFFF"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
          <tr>
            <td height="289" bgcolor="#DDEAEE"><table width="90%"  border="0" align="center" bgcolor="#76A8D5">
                <tr>
                  <td align="center"><font color="#FFFFFF">����ʧ��</font></td>
                </tr>
              </table>
                <table width="90%"  border="0" align="center" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
                  <tr>
                    <td align="center"><msg:messageList/><br>
                      <br>
                      <%if(isClose.equals("true")){%>
                        <input type="button" class="input1" name="btn_close" value="�ر�" onClick="javascript:window.close();">
                      <%}else{%>
                        <input type="button" class="input1" name="btn_back" value="����" onClick="gotoURL();">
                      <%}%>
                    </td>  
                  </tr>
              </table></td>
          </tr>
        </table></td>
        </tr>
    </table></td>
  </tr>
</table>
<%if(!standAlone.equals("false")){%>
<br>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr align="right">
    <td height="1" align="center" bgcolor="#D9E8EA"></td>
  </tr>
  <tr align="right">
    <td width="96%" align="center" bgcolor="#6AA0D1"><span class="link2">׿���Ƽ� ��Ȩ����<font face="Arial, Helvetica, sans-serif"> Copyright 2003-2005 ASPire Technologies. All Rights Reserved.</font></span></td>
 </tr>
</table>
<%}%>
</body>
</html>
