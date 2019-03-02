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
//导出WWW最新 应用数据信息
ThirdChannelMkBo.getInstance().exportWwwZx(sb,localDir);
		
//导出WWW最热 应用数据信息
ThirdChannelMkBo.getInstance().exportWwwZr(sb,localDir);

//导出WWW小编推荐  应用数据信息
ThirdChannelMkBo.getInstance().exportWwwXbtj(sb,localDir);

//导出WAP最新 应用数据信息
ThirdChannelMkBo.getInstance().exportWapZx(sb,localDir);

//导出WAP最热 应用数据信息
ThirdChannelMkBo.getInstance().exportWapZr(sb,localDir);

//导出WAP小编推荐  应用数据信息
ThirdChannelMkBo.getInstance().exportWapXbtj(sb,localDir);

//列出文件，压缩
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
mailContent.append("导出第三方渠道营销数据开始时间："+begin+"，结束时间："+end);
mailContent.append("<br/>");
mailContent.append(sb.toString());
String mailTitle = "第三方渠道营销数据导出结果";		
File zipFile  = null;
if(filese.length!=0){
	//压缩
	zipFile = new File(localDir+File.separator+toDay+".zip");
	ZipUtil.compress(zipFile, localDir,"*.csv");

	Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo, new File[]{zipFile});
}else{
	//无附件
	Mail.sendMail(mailTitle, mailContent.toString(), ChannelConfig.mailTo);
} 
%>
<html>
<head>
<title>第三方渠道营销数据导出</title>
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
