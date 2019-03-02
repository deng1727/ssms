<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.biz.*"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.EntityCardConfig"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.biz.EntityCardBo"%>
<%@page import="com.aspire.ponaadmin.web.mail.Mail"%>
<%@page import="com.aspire.ponaadmin.web.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page import="com.aspire.common.Validator" %>
<%
  //1、AP全量信息  2、AP业务全量信息 3、AP增量 4、AP业务增量
  String  opType    =   Validator.filter(request.getParameter("opType"));
  int op = Integer.parseInt(opType);
	String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	StringBuffer sb = new StringBuffer();
	String mailTitle = "实体卡平台局数据导出结果";
	if(op==1){
		
		//导出AP业务代码数据信息
		EntityCardBo.getInstance().exportAPData(sb);
		mailTitle = "实体卡平台局数据---AP信息同步接口全量导出结果";
	}else if(op==2){
		
		//导出AP业务代码数据信息
		EntityCardBo.getInstance().exportAPOperData(sb);
		mailTitle = "实体卡平台局数据---业务信息同步接口全量导出结果";
    }else if(op==3){
	  //导出AP信息
	  EntityCardBo.getInstance().exportIncrementAPData(sb);
	  mailTitle = "实体卡平台局数据---AP信息同步接口增量导出结果";
	}else if(op==4){
	
	  //导出AP业务代码数据信息
	  EntityCardBo.getInstance().exportIncrementAPOperData(sb);
	  mailTitle = "实体卡平台局数据---业务信息同步接口增量导出结果";
	}
	
	String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	StringBuffer mailContent = new StringBuffer();
	mailContent.append("实体卡平台局数据导出开始时间："+begin+"，结束时间："+end);
	mailContent.append("<br/>");
	mailContent.append(sb.toString());
	
	Mail.sendMail(mailTitle, mailContent.toString(), EntityCardConfig.mailTo);  
%>
<html>
<head>
<title>实体卡AP数据导出</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>手动导出执行完毕，请检查相关结果。<br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
