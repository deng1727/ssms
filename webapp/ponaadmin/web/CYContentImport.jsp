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
	//д������־
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


String actionType = "��ʼ��ҵ�������ݵ���";
String actionTarget = "";
boolean actionResult = true;
String actionDesc = "��ʼ��ҵ�������ݵ���";
this.actionLog(request, actionType, actionTarget, actionResult,actionDesc);

////////////////////////////////////////////////////////////////////////////////


String type = Validator.filter(request.getParameter("type"));
  CYDataSyncBO zb =  new CYDataSyncBO();
  zb.syncCYCon(type);
  
%>
<html>
<head>
<title>��ҵ�������ݵ���</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>��ҵ�������ݵ���ִ����ϣ��������Ŀ¼�����<br/><br/>
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
