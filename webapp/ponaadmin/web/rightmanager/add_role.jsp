<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.*"%>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%
  RoleVO roleVO =(RoleVO)request.getAttribute("roleVO");
  //System.out.println("roleVO...."+roleVO);
  String value=null;
  String name=null;
  String desc=null;
  String roleID=null;
  if (roleVO==null)
   {
   value=Constants.FORWARD_ADD_TOKEN;
   name="";
   desc="";
   roleID="";
   }
   else
   {
   value=Constants.FORWARD_UPDATE_TOKEN;
   name=roleVO.getName();
   desc=roleVO.getDesc();
   roleID=roleVO.getRoleID();
   }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
function checkForm()
{
  //����ɫ���Ƴ���
  var name = addRoleForm.name.value;
  if(trim(name)=="" || getLength(trim(name))<6  || getLength(trim(name))>40)
  {
    alert("��ɫ���Ƴ��ȱ���Ϊ6��40���ַ���һ������ռ�����ַ�����");
    addRoleForm.name.focus();
    return false;
  }
  //����ɫ�������ݺϷ���
  if(!checkIllegalChar(name,"'\"<>\\/"))
  {
    alert("��ɫ���Ʋ��ܰ��� ' \" < > \\ /  ���ַ���");
    addRoleForm.name.focus();
    return false;
  }
  //����ɫ��������
  var descs = addRoleForm.descs.value;
  if(trim(descs)=="" || getLength(trim(descs))>200)
  {
    alert("�������ȱ���С��200���ַ���һ������ռ�����ַ��������Ҳ���Ϊ�գ�");
    addRoleForm.descs.focus();
    return false;
  }
  //����ɫ�������ݺϷ���
  if(!checkIllegalChar(descs,"'\"<>\\/"))
  {
    alert("��ɫ�������ܰ��� ' \" < > \\ /  ���ַ���");
    addRoleForm.name.focus();
    return false;
  }
  return true;
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>Ȩ�޹���----<%if(value.equals(Constants.FORWARD_ADD_TOKEN)){%>����<%}else{%>�޸�<%}%>��ɫ</FONT></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<form name="addRoleForm" method="post" action="roleManager.do" onSubmit="return checkForm();">
  <tr>
    <td width="22%" height="20" align="right" class="text3"><font color="#ff0000">(*)</font>��ɫ���ƣ�</td>
    <td width="78%" class="text3">
      <input name="name" type="text" value="<%=PublicUtil.getPageStr(name)%>">
    </td>
  </tr>
  <tr>
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>��ɫ������</td>
    <td class="text4">
      <textarea name="descs" cols="60" rows="4" ><%=PublicUtil.getPageStr(desc)%></textarea></td>
      <input type="hidden" name="action" value="<%=PublicUtil.getPageStr(value)%>">
      <input type="hidden" name="roleID" value="<%=PublicUtil.getPageStr(roleID)%>">
  </tr>
  <tr class="text3">
    <td align="center" colspan="2">
      <input type="submit" class="input1" name="submit01" value="ȷ��">
      <%if(!value.equals(Constants.FORWARD_ADD_TOKEN)){%><input type="button" class="input1" name="btn_back" value="����" onClick="history.go(-1);">
<%}%>
    </td>
  </tr>
</table>
</form>
</body>
</html>
