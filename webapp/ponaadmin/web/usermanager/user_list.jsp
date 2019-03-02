<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RoleVO" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant" %>
<%@page import="com.aspire.ponaadmin.common.page.PageResult" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>
<%

  PageResult pageResult = (PageResult)request.getAttribute("PageResult");
  List userList = pageResult.getPageInfo();

  List allRoleList = (List)request.getAttribute("allRoleList");

  String userID = Validator.filter(request.getParameter("userID")==null?"":request.getParameter("userID"));
  String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
  String companyName = Validator.filter(request.getParameter("companyName")==null?"":request.getParameter("companyName"));
  String state = Validator.filter(request.getParameter("state")==null?"0":request.getParameter("state"));
  String roleID = Validator.filter(request.getParameter("roleID")==null?"":request.getParameter("roleID"));
  String rightID = Validator.filter(request.getParameter("rightID")==null?"":request.getParameter("rightID"));
  String rightName = Validator.filter(request.getParameter("rightName")==null?"":request.getParameter("rightName"));
  String rightType = Validator.filter(request.getParameter("rightType")==null?"0":request.getParameter("rightType"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>wwwportal�Ż�����ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
<!--
function lockUser(userID)
{
  if(confirm("��ȷ��Ҫ�����û�"+userID+"��"))
  {
    queryUserForm.action = "lockUser.do";
    queryUserForm.userID.value = userID;
    queryUserForm.submit();
  }
}
function unlockUser(userID)
{
  if(confirm("��ȷ��Ҫ�����û�"+userID+"��"))
  {
    queryUserForm.action = "unlockUser.do";
    queryUserForm.userID.value = userID;
    queryUserForm.submit();
  }
}
function resetPwd(userID)
{
  if(confirm("��ȷ��Ҫ��λ�û�"+userID+"��������"))
  {
    queryUserForm.action = "resetPwd.do";
    queryUserForm.userID.value = userID;
    queryUserForm.submit();
  }
}
function chooseRight()
{
  var index = queryUserForm.rightType.selectedIndex;
  if(index==0)
  {
    alert("����ѡ��Ȩ�����ͣ�");
    return false;
  }
  getRight(queryUserForm.rightID,queryUserForm.rightName,queryUserForm.rightType.options[index].value);
}

function checkForm()
{
  //����û��ʺ�
  var userID = queryUserForm.userID.value;
  if(!checkIllegalChar(userID,"'\"<>\\/"))
  {
    alert("�ʺŲ��ܰ��� ' \" < > \\ /  ���ַ���");
    queryUserForm.userID.focus();
    return false;
  }

  //����û�����
  var name = queryUserForm.name.value;
  if(!checkIllegalChar(name,"<>~!@#$%^&*()[]{}\\|'\":;./?,"))
  {
    alert("�������ܺ���<>~!@#$%^&*()[]{}\\|'\":;./?,�ַ�");
    queryUserForm.name.focus();
    return false;
  }

  //��鹫˾����
  var companyName = queryUserForm.companyName.value;
  if(!checkIllegalChar(companyName,"'\"<>\\/"))
  {
    alert("��˾���Ʋ��ܰ��� ' \" < > \\ /  ���ַ���");
    queryUserForm.companyName.focus();
    return false;
  }

  return true;
}

-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">�û�����----�û���ѯ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1">
  <form name="queryUserForm" action="queryUser.do" method="post" onSubmit="return checkForm();">
  <tr>
    <td width="18%" align="right" class="text3">�ʺţ� </td>
    <td class="text4"><input name="userID" type="text" size="20" value="<%=userID%>"></td>
    <td align="right" class="text3">�û�������</td>
    <td class="text4"><input name="name" type="text" size="20" value="<%=name%>"></td>
  </tr>
  <tr>
    <td align="right" class="text3">��˾���ƣ�</td>
    <td class="text4"><input name="companyName" type="text" size="20" value="<%=companyName%>"></td>
    <td align="right" class="text3">�ʺ�״̬��</td>
    <td class="text4">
      <select name="state">
        <option value="0" <%if(state.equals("0")){%>selected<%}%>>ȫ��</option>
        <option value="<%=UserManagerConstant.STATE_NORMAL%>" <%if(state.equals(String.valueOf(UserManagerConstant.STATE_NORMAL))){%>selected<%}%>>����</option>
        <option value="<%=UserManagerConstant.STATE_LOCKED%>" <%if(state.equals(String.valueOf(UserManagerConstant.STATE_LOCKED))){%>selected<%}%>>����</option>
        <option value="<%=UserManagerConstant.STATE_PWD_RESET%>" <%if(state.equals(String.valueOf(UserManagerConstant.STATE_PWD_RESET))){%>selected<%}%>>���븴λ</option>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�û���ɫ��</td>
    <td width="25%" class="text4">
      <select name="roleID">
          <option value="">ȫ��</option>    
<%
  RoleVO role = null;
  for(int i=0;i<allRoleList.size();i++)
  {
    role = (RoleVO)allRoleList.get(i);
%>
          <option value="<%=role.getRoleID()%>" <%if(role.getRoleID().equals(roleID)){%>selected<%}%>><%=role.getName()%></option>    
<%
  }
%>  
      </select>
    </td>
    <td width="16%" align="right" class="text3">Ȩ�ޣ� </td>
    <td width="41%" class="text4">
    <select name="rightType" onChange="queryUserForm.rightID.value='';queryUserForm.rightName.value='';">
      <option value="<%=RightManagerConstant.TYPE_RIGHT%>" <%if(rightType.equals(String.valueOf(RightManagerConstant.TYPE_RIGHT))){%>selected<%}%>>����Ȩ��</option>
    </select>
      <input name="rightID" type="hidden" value="<%=rightID%>">
      <input name="rightName" type="text" size="20" readOnly value="<%=rightName%>">
      <a href="#" onClick="chooseRight();">ѡ��</a></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit2" type="submit" class="input1" value="��ѯ">
        <input name="Submit22" type="reset" class="input1" value="����"></td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">�û�����----�û��б�</td>
  </tr>
</table>
<%if(userList.size()>0){%>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text1">
    <td align="right">
        <%
        HashMap params = new HashMap();
        params.put("userID",userID);
        params.put("name",name);
        params.put("companyName",companyName);
        params.put("state",state);
        params.put("roleID",roleID);
        params.put("rightID",rightID);
        params.put("rightName",rightName);
        params.put("rightType",rightType);
        %>
        <pager:pager name="PageResult" action="/web/usermanager/queryUser.do" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr align="center" class="title2">
    <td width="10%">�û��ʺ�</td>
    <td width="12%">�û�����</td>
    <td width="33%">�û���ɫ</td>
    <td width="8%">�ʺ�״̬</td>
    <td width="12%">�û���ɫ�޸�</td>
    <td width="9%">�û�����</td>
    <td width="12%">���븴λ</td>
  </tr>
<%
  UserVO user = null;
  for(int i=0;i<userList.size();i++)
  {
    user = (UserVO)userList.get(i);
%>    
  <tr align="center"  class="<%=(i%2==0)?"text5":"text1"%>">
    <td><a href="viewUserInfo.do?actionType=manager&userID=<%=user.getUserID()%>"><%=user.getUserID()%></a></td>
    <td><%=user.getName()%></td>
    <td><%=user.getUserRolesInfo()%></td>
    <td><msg:UserStateDisplay state="<%=user.getState()%>" /></td>
    <td><right:checkDisplay rightID="0_1106_USER_ROLE"><%if(!user.getUserID().equals("ponaadmin")){%><input type="button" class="input1" name="btn_edit" value="�޸�" onClick="window.location.href='editUserRole.do?userID=<%=user.getUserID()%>'"><%}%></right:checkDisplay></td>
    <td>
      <right:checkDisplay rightID="0_1104_USER_LOCK">
        <%if(!user.getUserID().equals("ponaadmin")){%>
          <%if(user.getState()==UserManagerConstant.STATE_NORMAL){%><input type="button" class="input1" name="btn_lock" value="����" onClick="javascript:lockUser('<%=user.getUserID()%>');"><%}%>
          <%if(user.getState()==UserManagerConstant.STATE_LOCKED){%><input type="button" class="input1" name="btn_unlock" value="����" onClick="javascript:unlockUser('<%=user.getUserID()%>');"><%}%>
        <%}%>
      </right:checkDisplay>  
    </td>
    <td>
      <right:checkDisplay rightID="0_1105_USER_RESETPWD">
        <%if(!user.getUserID().equals("ponaadmin")){%>
          <%if(user.getState()==UserManagerConstant.STATE_NORMAL || user.getState()==UserManagerConstant.STATE_PWD_RESET){%><input type="button" class="input1" name="btn_resetPwd" value="���븴λ" onClick="javascript:resetPwd('<%=user.getUserID()%>');"><%}%>    
        <%}%>
      </right:checkDisplay>
    </td>
  </tr>
<%
  }
%>    
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text1">
    <td align="right">
        <pager:pager name="PageResult" action="/web/usermanager/queryUser.do" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="2"/>
        </pager:pager>
    </td>
  </tr>
</table>
<%}else{%>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text1">
    <td align="center"><font color="#ff0000">û���ҵ������������û���</fong></td>
  </tr>
</table>
<%}%>
</body>
</html>
