<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.channel.bo.*"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig"%>
<%@page import="com.aspire.ponaadmin.web.dataexport.channel.ZipUtil"%>
<%@page import="com.aspire.ponaadmin.web.mail.Mail"%>
<%@page import="com.aspire.ponaadmin.web.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.*"%>
<%
StringBuffer sb = new StringBuffer();
String toDay = DateUtil.formatDate(new Date(), "yyyyMMdd");
String localDir = ChannelConfig.LOCALDIR+File.separator+toDay;
File f = new File(localDir);
if(!f.exists()){
	f.mkdirs();
}
String begin = DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
//����WWW���� Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWwwZx(sb,localDir);
		
//����WWW���� Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWwwZr(sb,localDir);

//����WWWС���Ƽ�  Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWwwXbtj(sb,localDir);

//����WAP���� Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWapZx(sb,localDir);

//����WAP���� Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWapZr(sb,localDir);

//����WAPС���Ƽ�  Ӧ��������Ϣ
ThirdChannelMkBo.getInstance().exportWapXbtj(sb,localDir);

//�г��ļ���ѹ��
File[] filese = f.listFiles(new FileFilter() {
	public boolean accept(File pathname) {
		if(pathname.getName().toLowerCase().endsWith(".csv")){
			return true;
		}
		return false;
	}
});
String end = DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
StringBuffer mailContent = new StringBuffer();
mailContent.append("��������������Ӫ�����ݿ�ʼʱ�䣺"+begin+"������ʱ�䣺"+end);
mailContent.append("<br/>");
mailContent.append(sb.toString());
String mailTitle = "����������Ӫ�����ݵ������";		
File zipFile  = null;
if(filese.length!=0){
	//ѹ��
	zipFile = new File(localDir+File.separator+toDay+".zip");
	ZipUtil.compress(zipFile, localDir,"*.csv");

	Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo, new File[]{zipFile});
}else{
	//�޸���
	Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo);
} 
%>
<html>
<head>
<title>����������Ӫ�����ݵ���</title>
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
