<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RoleVO"%>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerConstant" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@ page import="com.aspire.common.Validator" %>
<%
  List roleUserList = (List)request.getAttribute("roleUserList");
  RoleVO role=(RoleVO)request.getAttribute("role");
  PageResult pageResult = (PageResult)request.getAttribute("PageResult");
  List userList = pageResult.getPageInfo();

  List allRoleList = (List)request.getAttribute("allRoleList");

  String userID = Validator.filter(request.getParameter("userID")==null?"":request.getParameter("userID"));
  String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
  String companyName = Validator.filter(request.getParameter("companyName")==null?"":request.getParameter("companyName"));
  String state = Validator.filter(request.getParameter("state")==null?"0":request.getParameter("state"));
  String qRoleID = Validator.filter(request.getParameter("qRoleID")==null?"":request.getParameter("qRoleID"));
  String rightID = Validator.filter(request.getParameter("rightID")==null?"":request.getParameter("rightID"));
  String rightName = Validator.filter(request.getParameter("rightName")==null?"":request.getParameter("rightName"));
  String rightType = Validator.filter(request.getParameter("rightType")==null?"0":request.getParameter("rightType"));

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
<!--
//全选还是全不选
function selectAll(check)
{
  for(var i=0;i<saveRoleUserForm.length;i++)
  {
    if(saveRoleUserForm.elements[i].type=="checkbox" && (saveRoleUserForm.elements[i].name == "userID" || saveRoleUserForm.elements[i].name == "userIDs"))
    {
      if(saveRoleUserForm.elements[i].value!="ponaadmin")
      {
        saveRoleUserForm.elements[i].checked = check;
      }
    }
  }
}
function checkForm()
{
  var delUserIDs = "";
  for(var i=0;i<saveRoleUserForm.length;i++)
  {
    var element = saveRoleUserForm.elements[i];
    if(element.type=="checkbox" && element.name == "userID" && !element.checked)
    {
      if(delUserIDs!="") delUserIDs = delUserIDs + ":";
      delUserIDs = delUserIDs + element.value;
    }
  }
  saveRoleUserForm.delUserIDs.value = delUserIDs;
  saveRoleUserForm.action="saveRoleUser.do";
  return true;
}

function chooseRight()
{
  var index = queryUserForm.rightType.selectedIndex;
  if(index==0)
  {
    alert("请先选择权限类型！");
    return false;
  }
  getRight(queryUserForm.rightID,queryUserForm.rightName,queryUserForm.rightType.options[index].value);
}

-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0">
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF"><FONT class=large>权限管理----角色用户修改</FONT>----角色：<font color="#FF0000">&quot;</font><font color="#FF0000"><%=role.getName()%>&quot;</font></td>
  </tr>
</table>
<form name="queryUserForm" action="editRoleUser.do" method="post" onsubmit="return checkForm();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td width="18%" align="right" class="text3">帐号： <input type="hidden" name="roleID" value="<%=role.getRoleID()%>"></td>
    <td class="text4"><input name="userID" type="text" size="20" value="<%=userID%>"></td>
    <td align="right" class="text3">用户姓名：</td>
    <td class="text4"><input name="name" type="text" size="20" value="<%=name%>"></td>
  </tr>
  <tr>
    <td align="right" class="text3">公司名称：</td>
    <td class="text4"><input name="companyName" type="text" size="20" value="<%=companyName%>"></td>
    <td align="right" class="text3">帐号状态：</td>
    <td class="text4">
      <select name="state">
        <option value="0" <%if(state.equals("0")){%>selected<%}%>>全部</option>
        <option value="<%=UserManagerConstant.STATE_NORMAL%>" <%if(state.equals(String.valueOf(UserManagerConstant.STATE_NORMAL))){%>selected<%}%>>正常</option>
        <option value="<%=UserManagerConstant.STATE_LOCKED%>" <%if(state.equals(String.valueOf(UserManagerConstant.STATE_LOCKED))){%>selected<%}%>>锁定</option>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">用户角色：</td>
    <td width="25%" class="text4">
      <select name="roleID">
          <option value="">全部</option>    
<%
  RoleVO qRole = null;
  for(int i=0;i<allRoleList.size();i++)
  {
    qRole = (RoleVO)allRoleList.get(i);
%>
          <option value="<%=qRole.getRoleID()%>" <%if(qRole.getRoleID().equals(qRoleID)){%>selected<%}%>><%=qRole.getName()%></option>    
<%
  }
%>  
      </select>
    </td>
    <td width="16%" align="right" class="text3">权限： </td>
    <td width="41%" class="text4">
    <select name="rightType" onChange="queryUserForm.rightID.value='';queryUserForm.rightName.value='';">
      <option value="<%=RightManagerConstant.TYPE_RIGHT%>" <%if(rightType.equals(String.valueOf(RightManagerConstant.TYPE_RIGHT))){%>selected<%}%>>操作权限</option>
    </select>
      <input name="rightID" type="hidden" value="<%=rightID%>">
      <input name="rightName" type="text" size="20" readOnly value="<%=rightName%>">
      <a href="#" onClick="chooseRight();">选择</a></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit2" type="submit" class="input1" value="查询">
        <input name="Submit22" type="reset" class="input1" value="重置"></td>
  </tr>
</table>
</form>
<form name="saveRoleUserForm" action="editRoleUser.do" method="post" onsubmit="return checkForm();">
      <input type="hidden" name="roleID" value="<%=role.getRoleID()%>">
      <input type="hidden" name="delUserIDs">
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" bgcolor="#FFFFFF">
  <tr align="center" bgcolor="#9DDDFF">
    <td width="8%">选中</td>
    <td width="36%">用户帐号</td>
    <td width="36%">用户姓名</td>
    <td width="20%">帐号状态</td>
  </tr>
<%
  UserVO user = null;
  for(int i=0;i<userList.size();i++)
  {
    user = (UserVO)userList.get(i);
    boolean choosed = roleUserList.contains(user);
%>
  <tr class="<%=(i%2==0)?"text3":"text4"%>">
    <td align="center">
      <input type="checkbox" name="userID<%if(!choosed){%>s<%}%>" value="<%=user.getUserID()%>" <%if(choosed){%>checked<%}%> <%if(user.getUserID().equals("ponaadmin")){%>disabled<%}%>>
    </td>
    <td align="center"><a href="../usermanager/viewUserInfo.do?userID=<%=user.getUserID()%>"><%=user.getUserID()%></a></td>
    <td align="center"><%=user.getName()%></td>
    <td align="center"><msg:UserStateDisplay state="<%=user.getState()%>" /></td>
  </tr>
<%
  }
%>
  <tr class="text1">
    <td align="left">
      <input type="checkbox" name="checkbox" value="checkbox" onClick="selectAll(this.checked);">全选
    </td>
    <td align="right" colspan="3">
        <%
        HashMap params = new HashMap();
        params.put("userID",userID);
        params.put("name",name);
        params.put("companyName",companyName);
        params.put("state",state);
        params.put("qRoleID",qRoleID);
        params.put("rightID",rightID);
        params.put("rightName",rightName);
        params.put("rightType",rightType);
        params.put("roleID",role.getRoleID());
        %>
        <pager:pager name="PageResult" action="/web/rightmanager/editRoleUser.do" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>  
</logic:notEmpty>
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" bgcolor="#FFFFFF">
  <tr align="center" bgcolor="#9DDDFF">
    <td align="center"><font color="#ff0000">没有找到符合条件的角色。</font></td>
  </tr>
</logic:empty>
<table width="95%"  border="0" align="center" bgcolor="#FFFFFF">
  <tr>
    <td class="text3" colspan="4" align="center">
      <logic:notEmpty name="PageResult" property="pageInfo"><input type="submit" class="input1" name="submit01" value="确定"></logic:notEmpty>
      <input type="button" class="input1" name="btn_back" value="返回" onClick="window.location.href='roleList.do';">
    </td>
  </tr>
</table>
</form>
</body>
</html>
