<%@page contentType="text/html; charset=GBK" %>
<%@ page import="com.aspire.common.config.ServerInfo"%>
<%@ page import="com.aspire.ponaadmin.web.system.ProductInfo"%>
<%@ page import="com.aspire.ponaadmin.web.system.PatchVO"%>
<%@ page import="com.aspire.common.Validator" %>
<%
	String warnInfo=ProductInfo.getWarnInfo();
	String version=ProductInfo.getVersionID();
	String ownerInfo=ProductInfo.getOwnerInfo();
	String systemName = ServerInfo.getSystemName();
	String strInternal=Validator.filter(request.getParameter("isInternal"));
	String context = request.getContextPath();
	boolean isInternal=false;
	if ("true".equals(strInternal))
	{
		isInternal=true;
	}
	PatchVO[] patch=ProductInfo.queryPatchsArray(isInternal);
%>
<html>
<head>
<title>版本信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="<%=context%>/css/common.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FFFFFF">
<div align="center" class="fontb">
  <table width="467" border="1" cellspacing="0" cellpadding="0" bordercolorlight="#CCCCCC" bordercolordark="#FFFFFF">
    <tr valign="top" align="center">
    <!--@UpdateTime@-->
      <td> <table border="0" cellspacing="1" cellpadding="0" bordercolorlight="#0033CC" bordercolordark="#FFFFFF">
          <tr>
            <td height="26" width="444"><img src="<%=context%>/image/company.gif" width="40" height="20"></td>
          </tr>
          <tr valign="top" align="center">
            <td height="116" width="444"><img src="<%=context%>/image/<%=systemName%>.gif" width="444" height="106"></td>
          </tr>
          <tr valign="top" align="center">
            <td width="444"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!--DWLayoutTable-->
                <tr>
                  <td height="20" width="15%" ></td>
                  <td height="20" width="20%">版本：</td>
                  <td height="20" colspan="2"><%=version%></td>
                </tr>
                <tr>
                  <td height="20"></td>
                  <td height="20" valign="top">更新版本：</td>
                  <td height="20" colspan="2"><%
			  if (patch!=null)
			  {
			  	for (int i=0;i<patch.length;i++)
			  	{
				 	out.print(patch[i].getPatchID());
				  	out.print("<br>\n");
			  	}
			  }
		  %> </td>
                </tr>
               <tr>
                  <td></td>
                  <td height="80" colspan=3 align="left" valign="bottom"> <textarea name="textfield" cols="45" readonly rows="3" wrap="VIRTUAL" ><%=warnInfo%>
              </textarea> </td>
                  <!--警告: 本计算机程序受著作权法和国际公约的保护，未经授权擅自复制或散布本程序的部分或全部，将承受严厉的民事和刑事处罚，对已知的违反者将给予法律范围内的全面制裁。-->
                </tr>
                <tr>
                  <td height="28"></td>
                  <td colspan="3" valign="middle">
                    <a href="http://www.aspire-tech.com/" target="_blank" class="fontb"><%=ownerInfo%></a></td>
                    <!--版权所有 (C) 2000-2007 卓望数码技术(深圳)有限公司-->
                </tr>
                <tr>
                  <td height="21"></td>
                  <td> <a href="http://www.aspire-tech.com/" target="_blank" class="fontb">谢谢</a> </td>
                  <td width="200" align="right" valign="top"> <input type=button name = "name22" value ="确  定" onClick = "javascript:window.close();" > </td>
                <td width="58" valign="top"></td>
                </tr>
                <tr>
                  <td height="13" colspan="3"></td>
                  <td></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
</body>
</html>
