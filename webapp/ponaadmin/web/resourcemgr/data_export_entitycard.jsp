<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.biz.*"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.EntityCardConfig"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.entitycard.biz.EntityCardBo"%>
<%@page import="com.aspire.ponaadmin.web.mail.Mail"%>
<%@page import="com.aspire.ponaadmin.web.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page import="com.aspire.common.Validator" %>
<%
  //1��APȫ����Ϣ  2��APҵ��ȫ����Ϣ 3��AP���� 4��APҵ������
  String  opType    =   Validator.filter(request.getParameter("opType"));
  int op = Integer.parseInt(opType);
	String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	StringBuffer sb = new StringBuffer();
	String mailTitle = "ʵ�忨ƽ̨�����ݵ������";
	if(op==1){
		
		//����APҵ�����������Ϣ
		EntityCardBo.getInstance().exportAPData(sb);
		mailTitle = "ʵ�忨ƽ̨������---AP��Ϣͬ���ӿ�ȫ���������";
	}else if(op==2){
		
		//����APҵ�����������Ϣ
		EntityCardBo.getInstance().exportAPOperData(sb);
		mailTitle = "ʵ�忨ƽ̨������---ҵ����Ϣͬ���ӿ�ȫ���������";
    }else if(op==3){
	  //����AP��Ϣ
	  EntityCardBo.getInstance().exportIncrementAPData(sb);
	  mailTitle = "ʵ�忨ƽ̨������---AP��Ϣͬ���ӿ������������";
	}else if(op==4){
	
	  //����APҵ�����������Ϣ
	  EntityCardBo.getInstance().exportIncrementAPOperData(sb);
	  mailTitle = "ʵ�忨ƽ̨������---ҵ����Ϣͬ���ӿ������������";
	}
	
	String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	StringBuffer mailContent = new StringBuffer();
	mailContent.append("ʵ�忨ƽ̨�����ݵ�����ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
	mailContent.append("<br/>");
	mailContent.append(sb.toString());
	
	Mail.sendMail(mailTitle, mailContent.toString(), EntityCardConfig.mailTo);  
%>
<html>
<head>
<title>ʵ�忨AP���ݵ���</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER>�ֶ�����ִ����ϣ�������ؽ����<br/><br/>
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
