<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.common.usermanager.UserSessionVO" %>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerBO" %>
<%@page import="com.aspire.ponaadmin.web.channelUser.vo.ChannelVO"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>修改密码</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/rsa/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../../js/rsa/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../../js/rsa/core.js"></script>
	<script type="text/javascript" src="../../js/rsa/des.js"></script>
<script type="text/javascript">

</script>
</head>
<%
  UserSessionVO userSession = UserManagerBO.getInstance().getUserSessionVO(session);
	String channelsNO = "unknow";
	String noMatchMsg = "";
  if(userSession!=null)
  {
    ChannelVO channelVO = userSession.getChannel();
    channelsNO = channelVO.getChannelsNO();
  }
noMatchMsg = (String)request.getAttribute("noMatchMsg");
//如果noMatchMsg为空，则转为""
noMatchMsg=noMatchMsg==null?"":noMatchMsg;

%>
<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" bgcolor="#BFE8FF">修改密码</td>
	  </tr>
	</table>
	<form name="modUserPwdForm" action="updateChannelPwd.do" method="post" >
	<input type="hidden" name="csrftoken" value="<%=session.getAttribute("csrftoken")%>"/>
	
			<input type='hidden' id="oldPwd" name="oldPwd"/>
			<input type='hidden' id="newPwd" name="newPwd"/>
			
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr>
		<td width="10%" align="right" class="text3">
		  	账号名称：
		</td>
		<td width="40%" class="text4">
			<p><%=channelsNO%></p>
		</td>
		</tr>
		<tr>
		<td width="10%" align="right" class="text3">
		  	原密码：
		</td>
		<td width="40%" class="text4">  
			<input type="password" id="s_oldPwd"  value="" ><span style="color: red;margin-left: 5px;"><%=noMatchMsg %></span>
		</td>
		</tr>
		<tr>
		<td width="10%" align="right" class="text3">
		  	新密码：
		</td>
		<td width="40%" class="text4">  
			<input type="password" id="s_newPwd"  value="" >
		</td>
		</tr>
		<tr>
		<td width="10%" align="right" class="text3">
		  	确认密码：
		</td>
		<td width="40%" class="text4">  
			<input type="password" id="s_newPwd2"  value="" >
		</td>
		</tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		<tr class="text5">
	    <td align="center">
	      <input id="loginBtn" class="btn1" type="button" tabindex="4" value="确定" >      
	      <input type="button" class="input1" name="btn_reset" value="重置" onClick="clearForm(modUserPwdForm);">
	    </td>
		</tr>
	</table>
  </form>
<br>

</body>

<script type="text/javascript" language="javascript">
	$(function() {
		function check() {
			var oldPwd = $.trim($("#s_oldPwd").val());
			var newPwd = $.trim($("#s_newPwd").val());
			var newPwd2 = $.trim($("#s_newPwd2").val());
			
			
			
			
			if (!checkCharOrDigital(oldPwd) || getLength(oldPwd)<6 || getLength(oldPwd)>20){
				$("#s_oldPwd").focus();
				alert("旧密码长度必须是6-20位，并且只能包含数字和字母！");
				return false;
			}
			
			if (!checkCharOrDigital(newPwd) || getLength(newPwd)<6 || getLength(newPwd)>20){
				$("#s_newPwd").focus();
				alert("新密码长度必须是6-20位，并且只能包含数字和字母！");
				return false;
			}
			
  			if(newPwd != newPwd2)
  				{
    				
    				alert("输入的密码和确认密码必须一致！");
    				$("#s_newPwd").val() = "";
    				$("#s_newPwd2").val() = "";
    				$("#s_newPwd").focus();
    				return false;
  }
			
			return true;
		}
		
		function login() {
			if (check()){
				$("#newPwd").val(window.mmEnc($.trim($("#s_newPwd").val())));
				$("#oldPwd").val(window.mmEnc($.trim($("#s_oldPwd").val())));
				document.forms[0].submit();
			}
		}
		
		$("#loginBtn").click(function() {
			login();
		});
		
		$(document).keydown(function(e){
			e = window.event || e;
			if(e.keyCode==13){
				login();
			}
		}); 
	});
</script>
</html>
