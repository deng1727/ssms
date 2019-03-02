<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RoleVO"%>
<%@page import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>
<%
  String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
  String desc = Validator.filter(request.getParameter("desc")==null?"":request.getParameter("desc"));
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
function delRole(roleID,name)
{
  if(confirm("您确定要删除角色"+name+"？"))
  {
	window.location.href="delRole.do?roleID="+roleID;
  }
}

function chooseRight()
{
  var index = queryForm.rightType.selectedIndex;
  if(index==0)
  {
    alert("请先选择权限类型！");
    return false;
  }
  getRight(queryForm.rightID,queryForm.rightName,queryForm.rightType.options[index].value);
}

function checkForm()
{
  //检查角色名称长度
  var name = queryForm.name.value;
  //检查角色名称内容合法性
  if(!checkIllegalChar(name,"'\"<>\\/"))
  {
    alert("角色名称不能包含 ' \" < > \\ /  等字符。");
    queryForm.name.focus();
    return false;
  }
  //检查角色描述长度
  var desc = queryForm.desc.value;
  //检查角色描述内容合法性
  if(!checkIllegalChar(desc,"'\"<>\\/"))
  {
    alert("角色描述不能包含 ' \" < > \\ /  等字符。");
    queryForm.desc.focus();
    return false;
  }
  return true;
}

-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">权限管理----角色查询</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1">
  <form name="queryForm" method="post" action="roleList.do" onSubmit="return checkForm();">
  <tr>
    <td width="14%" align="right" class="text3">角色名称：</td>
    <td width="46%" class="text4"><input name="name" type="text" size="20" value="<%=name%>"></td>
    <td align="right" class="text3">角色描述：</td>
    <td class="text4"><input name="desc" type="text" size="20" value="<%=desc%>"></td>
  </tr>
  <tr>
    <td width="16%" align="right" class="text3">权限： </td>
    <td width="41%" class="text4">
    <select name="rightType" onChange="queryForm.rightID.value='';queryForm.rightName.value='';">
      <option value="<%=RightManagerConstant.TYPE_RIGHT%>" <%if(rightType.equals(String.valueOf(RightManagerConstant.TYPE_RIGHT))){%>selected<%}%>>操作权限</option>
    </select>
      <input name="rightID" type="hidden" value="<%=rightID%>">
      <input name="rightName" type="text" size="20" readOnly value="<%=rightName%>">
      <a href="#" onClick="chooseRight();">选择</a></td>    <td width="11%" align="right" class="text3">&nbsp;</td>
    <td width="29%" class="text4">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
      <input name="Submit2" type="submit" class="input1" value="查询">
      <input name="Submit22" type="reset" class="input1" value="重置">
    </td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">权限管理----角色管理</td>
  </tr>
</table>
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td height="20" align="center"><font color="#ff0000">没有对应符合条件的角色。</font>
    </td>
  </tr>
</table>    
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr class="title2">
    <td width="20%" height="20" align="center">角色名称</td>
    <td width="38%" align="center">角色描述</td>
    <td width="8%" align="center">修改属性</td>
    <td width="8%" align="center">操作权限</td>
    <td width="8%" align="center">修改用户</td>
    <td width="8%" align="center">删除角色</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.common.rightmanager.RoleVO">
  <tr class="<%=ind.intValue()%2==0?"text4":"text3"%>">
    <input type="hidden" name="roleID" value="<bean:write name="vo" property="roleID"/>">
    <td align="center" height="20"><a href="oneRole.do?roleID=<bean:write name="vo" property="roleID"/>&action=view"><bean:write name="vo" property="name"/></a></td>
    <td align="center" style="word-break:break-all"><bean:write name="vo" property="desc"/></td>
    <td align="center"><right:checkDisplay rightID="0_1102_ROLE_MOD"><logic:notEqual name="vo" property="roleID" value="1"><input type="button" class="input1" value="修改" name="btn_edit" onClick="window.location.href='oneRole.do?roleID=<bean:write name="vo" property="roleID"/>&action=edit';"></logic:notEqual></right:checkDisplay></td>
    <td align="center"><right:checkDisplay rightID="0_1106_ROLE_RIGHT"><logic:notEqual name="vo" property="roleID" value="1"><input type="button" class="input1" value="修改" name="btn_editrole" onClick="window.location.href='rightList.do?roleID=<bean:write name="vo" property="roleID"/>&name=<bean:write name="vo" property="name"/>';"></logic:notEqual></right:checkDisplay></td>
    <td align="center"><right:checkDisplay rightID="0_1108_ROLE_USER"><input type="button" class="input1" value="修改" name="btn_edituser" onClick="window.location.href='editRoleUser.do?roleID=<bean:write name="vo" property="roleID"/>';"></right:checkDisplay></td>
    <td align="center"><right:checkDisplay rightID="0_1103_ROLE_DEL"><logic:notEqual name="vo" property="roleID" value="1"><input type="button" class="input1" value="删除" name="btn_del" onClick="delRole('<bean:write name="vo" property="roleID"/>','<bean:write name="vo" property="name"/>');"></logic:notEqual></right:checkDisplay></td>
  </tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr class="text1">
    <td align="right">
        <%
        HashMap params = new HashMap();
        params.put("name",name);
        params.put("desc",desc);
        params.put("rightID",rightID);
        params.put("rightName",rightName);
        params.put("rightType",rightType);
        %>
        <pager:pager name="PageResult" action="/web/rightmanager/roleList.do" params="<%=params%>">
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
</body>
</html>
