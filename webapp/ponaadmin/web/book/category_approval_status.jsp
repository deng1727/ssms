<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="com.aspire.ponaadmin.web.constant.Constants" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script src="../../js/tools.js"></script>
<script  LANGUAGE="JavaScript">
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="245" class="indexbg1"><br>
      <table width="69%"  border="0" align="center" cellpadding="0" cellspacing="0">
        <form name="contentForm" action="" method="post">
      <tr>
        <td width="99%" height="297" bgcolor="#FFFFFF"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="4">
          <tr>
            <td height="289" bgcolor="#DDEAEE"><table width="90%"  border="0" align="center" bgcolor="#76A8D5">
                <tr>
                  <td align="center"><font color="#FFFFFF">操作成功</font></td>
                </tr>
              </table>
                <table width="90%"  border="0" align="center" cellpadding="2" cellspacing="2" bgcolor="#FFFFFF">
                  <tr>
                    <td align="center"><msg:messageList/><br>
                      <br>
                        <input type="button" class="input1" name="btn_back" value="关闭" onClick="window.returnValue=true,window.close();">
                    </td>  
                  </tr>
                  
              </table></td>
          </tr>
        </table></td>
        </tr>
        </form>
    </table></td>
  </tr>
</table>
 </body>
</html>
