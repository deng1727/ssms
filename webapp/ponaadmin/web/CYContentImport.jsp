<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.dotcard.cysyncdata.bo.CYDataSyncBO"%>


<%@page import="com.aspire.ponaadmin.common.usermanager.UserManagerBO"%>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserSessionVO"%>
<%@page import="com.aspire.ponaadmin.common.usermanager.UserVO"%>
<%@page import="com.aspire.ponaadmin.web.actionlog.ActionLogBO"%>
<%@page import="com.aspire.common.exception.BOException"%>
<%@ page import="com.aspire.common.Validator" %>
<%! 
protected void actionLog (HttpServletRequest request, String actionType,
        String actionTarget, boolean actionResult,
        String desc){
	//写操作日志
	try
	{
	UserVO logUser = null ;
	String IP = null ;
	UserSessionVO userSessionVO = UserManagerBO.getInstance().
	getUserSessionVO(request.getSession()) ;
	
	if (userSessionVO != null)
	{
	logUser = userSessionVO.getUser() ;
	//IP = userSessionVO.getAccessInfo().getIP() ;
	IP = request.getRemoteAddr() ;
	}
	else
	{
	logUser = new UserVO() ;
	IP = request.getRemoteAddr() ;
	logUser.setUserID("unknow") ;
	logUser.setName("unknow") ;
	
	}
	
	ActionLogBO.getInstance().log(logUser.getUserID(), logUser.getName(),
	                    logUser.getUserRolesInfo() == null ?
	                    "" : logUser.getUserRolesInfo(),
	                    actionType, actionResult,
	                    actionTarget, IP, desc) ;
	}
	catch (BOException e)
	{
	
	}
}
%> 
<%


String actionType = "开始创业大赛数据导入";
String actionTarget = "";
boolean actionResult = true;
String actionDesc = "开始创业大赛数据导入";
this.actionLog(request, actionType, actionTarget, actionResult,actionDesc);

////////////////////////////////////////////////////////////////////////////////


String type = Validator.filter(request.getParameter("type"));
  CYDataSyncBO zb =  new CYDataSyncBO();
  zb.syncCYCon(type);
  
%>
<html>
<head>
<title>创业大赛数据导入</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>创业大赛数据导入执行完毕，请检查相关目录结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
