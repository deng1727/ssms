<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="com.aspire.dotcard.basebook.BaseBookFtpProcess"%>
<%@page import="com.aspire.dotcard.basebook.config.BaseBookConfig"%>
<%@page import="com.aspire.dotcard.basebook.timer.RBookLoadTask"%>
<%@page import="com.aspire.dotcard.basebook.biz.RBookBO"%>
<%@page import="com.aspire.ponaadmin.web.mail.Mail"%>
<%@ page import="com.aspire.common.Validator" %>
<%
String msg = "";
StringBuffer msgInfo = new StringBuffer();
String synBookType = Validator.filter(request.getParameter("synBookType"));
String subject = "";
try{
	if(null!=synBookType){
		BaseBookFtpProcess ftp = new BaseBookFtpProcess();
		
		
		String encoding = BaseBookConfig.get("fileEncoding");
		String sep = BaseBookConfig.get("BBookListSep");
		if(null==encoding){
			encoding = "UTF-8";
		}
		if(null==sep){
			sep = "|";
		}
		
		//获取要处理的对象
		if (sep.startsWith("0x")) 
		{
			// 0x开头的，表示是16进制的，需要转换
			String s = sep.substring(2,sep.length());
			int i1 = Integer.parseInt(s,16);
			char c = (char)i1;
			sep = String.valueOf(c);
		}	
		RBookLoadTask t = new RBookLoadTask();
		int[] rs = new int[6];	
		//作者信息
		if("author".equals(synBookType)){
			t.synBookAuthor(ftp, msgInfo, encoding, sep, rs);
			subject = "基地图书作者信息导入1成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书作者信息导入1失败，详见邮件正文!";
			}
		}else if("type".equals(synBookType)){
			//分类信息导入
			t.synBookType(ftp, msgInfo, encoding, sep, rs);
			subject = "基地图书分类信息导入2成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书分类信息导入2失败，详见邮件正文!";
			}			
		}else if("book".equals(synBookType)){
			//图书信息
			rs = new int[6];
			t.synBookInfo(ftp, msgInfo, encoding, sep, rs);	
			subject = "基地图书信息导入3成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书信息导入3失败，详见邮件正文!";
			}			
		}else if("month".equals(synBookType)){
			//包月信息
			rs = new int[4];
			t.synBookMonth(ftp, msgInfo, encoding, sep, rs);	
			subject = "基地图书包月信息导入4成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书包月信息导入4失败，详见邮件正文!";
			}			
		}else if("recommend".equals(synBookType)){
			//推荐图书信息
			rs = new int[6];
			t.synBookRecommend(ftp, msgInfo, encoding, sep, rs);	
			subject = "基地图书推荐图书信息导入5成功，详见邮件正文!";	
			if(rs[0]==0){
				subject = "基地图书推荐图书信息导入5失败，详见邮件正文!";
			}			
		}else if("catalog".equals(synBookType)){
			//专区信息
			rs = new int[6];
			t.synBookArea(ftp, msgInfo, encoding, sep, rs);		
			subject = "基地图书专区信息导入6成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书专区信息导入6失败，详见邮件正文!";
			}			
		}else if("catalogcontent".equals(synBookType)){
			//专区内容信息
			rs = new int[6];
			t.synBookAreaReference(ftp, msgInfo, encoding, sep, rs);	
			subject = "基地图书专区内容信息导入7成功，详见邮件正文!";
			if(rs[0]==0){
				subject = "基地图书专区内容信息导入7失败，详见邮件正文!";
			}			
		}else if("rank".equals(synBookType)){
			//排行信息
			//总排行  0,处理的总行数；1，畅销；2，点击；3，鲜花；4，搜索；5,校验不合格；6，数据处理失败
			rs = new int[7];
			t.synBookTotalRank(ftp, msgInfo, encoding, sep, rs);				
			//月排行
			int[] rs1 = new int[7];
			t.synBookMonthRank(ftp, msgInfo, encoding, sep, rs);				
			//周排行
			int[] rs2 = new int[7];
			t.synBookWeekRank(ftp, msgInfo, encoding, sep, rs);	
			if(rs[0]==0&&rs1[0]==0&&rs[2]==0){
			    subject = "基地图书排行信息导入8失败，详见邮件正文!";
			}else{
				subject = "基地图书排行信息导入8成功，详见邮件正文!";
			}
		}
		RBookBO.getInstance().updateCateTotal();

	}
}catch(Exception e){
	if("author".equals(synBookType)){
		subject = "基地图书作者信息导入1失败，详见邮件正文!";
	}else if("type".equals(synBookType)){
		//分类信息导入
		subject = "基地图书分类信息导入2失败，详见邮件正文!";
	}else if("book".equals(synBookType)){
		//图书信息
		subject = "基地图书信息导入3失败，详见邮件正文!";
	}else if("month".equals(synBookType)){
		//包月信息
		subject = "基地图书包月信息导入4失败，详见邮件正文!";
	}else if("recommend".equals(synBookType)){
		//推荐图书信息
		subject = "基地图书推荐图书信息导入5失败，详见邮件正文!";	
	}else if("catalog".equals(synBookType)){
		//专区信息
		subject = "基地图书专区信息导入6失败，详见邮件正文!";
	}else if("catalogcontent".equals(synBookType)){
		//专区内容信息
		subject = "基地图书专区内容信息导入7失败，详见邮件正文!";
	}else if("rank".equals(synBookType)){
	
		subject = "基地图书排行信息导入8失败，详见邮件正文!";
	}
}
if(null!=synBookType&&!"".equals(msgInfo.toString())){
	try{
		
		String[] mailTo = BaseBookConfig.get("BaseBookSynMailto").split(",");
		Mail.sendMail(subject, msgInfo.toString(), mailTo);	
	
	}catch(Exception e){
		msgInfo.append("发送邮件失败!");
	}
}
%>
<html>
<head>
<title>基地图书数据导入</title>
<link href="../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
  <CENTER><%= subject%><br/><br/>
  <%= msgInfo.toString() %>
  <br/><br/>
  <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
