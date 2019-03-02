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
		
		//��ȡҪ����Ķ���
		if (sep.startsWith("0x")) 
		{
			// 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
			String s = sep.substring(2,sep.length());
			int i1 = Integer.parseInt(s,16);
			char c = (char)i1;
			sep = String.valueOf(c);
		}	
		RBookLoadTask t = new RBookLoadTask();
		int[] rs = new int[6];	
		//������Ϣ
		if("author".equals(synBookType)){
			t.synBookAuthor(ftp, msgInfo, encoding, sep, rs);
			subject = "����ͼ��������Ϣ����1�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ��������Ϣ����1ʧ�ܣ�����ʼ�����!";
			}
		}else if("type".equals(synBookType)){
			//������Ϣ����
			t.synBookType(ftp, msgInfo, encoding, sep, rs);
			subject = "����ͼ�������Ϣ����2�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ�������Ϣ����2ʧ�ܣ�����ʼ�����!";
			}			
		}else if("book".equals(synBookType)){
			//ͼ����Ϣ
			rs = new int[6];
			t.synBookInfo(ftp, msgInfo, encoding, sep, rs);	
			subject = "����ͼ����Ϣ����3�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ����Ϣ����3ʧ�ܣ�����ʼ�����!";
			}			
		}else if("month".equals(synBookType)){
			//������Ϣ
			rs = new int[4];
			t.synBookMonth(ftp, msgInfo, encoding, sep, rs);	
			subject = "����ͼ�������Ϣ����4�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ�������Ϣ����4ʧ�ܣ�����ʼ�����!";
			}			
		}else if("recommend".equals(synBookType)){
			//�Ƽ�ͼ����Ϣ
			rs = new int[6];
			t.synBookRecommend(ftp, msgInfo, encoding, sep, rs);	
			subject = "����ͼ���Ƽ�ͼ����Ϣ����5�ɹ�������ʼ�����!";	
			if(rs[0]==0){
				subject = "����ͼ���Ƽ�ͼ����Ϣ����5ʧ�ܣ�����ʼ�����!";
			}			
		}else if("catalog".equals(synBookType)){
			//ר����Ϣ
			rs = new int[6];
			t.synBookArea(ftp, msgInfo, encoding, sep, rs);		
			subject = "����ͼ��ר����Ϣ����6�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ��ר����Ϣ����6ʧ�ܣ�����ʼ�����!";
			}			
		}else if("catalogcontent".equals(synBookType)){
			//ר��������Ϣ
			rs = new int[6];
			t.synBookAreaReference(ftp, msgInfo, encoding, sep, rs);	
			subject = "����ͼ��ר��������Ϣ����7�ɹ�������ʼ�����!";
			if(rs[0]==0){
				subject = "����ͼ��ר��������Ϣ����7ʧ�ܣ�����ʼ�����!";
			}			
		}else if("rank".equals(synBookType)){
			//������Ϣ
			//������  0,�������������1��������2�������3���ʻ���4��������5,У�鲻�ϸ�6�����ݴ���ʧ��
			rs = new int[7];
			t.synBookTotalRank(ftp, msgInfo, encoding, sep, rs);				
			//������
			int[] rs1 = new int[7];
			t.synBookMonthRank(ftp, msgInfo, encoding, sep, rs);				
			//������
			int[] rs2 = new int[7];
			t.synBookWeekRank(ftp, msgInfo, encoding, sep, rs);	
			if(rs[0]==0&&rs1[0]==0&&rs[2]==0){
			    subject = "����ͼ��������Ϣ����8ʧ�ܣ�����ʼ�����!";
			}else{
				subject = "����ͼ��������Ϣ����8�ɹ�������ʼ�����!";
			}
		}
		RBookBO.getInstance().updateCateTotal();

	}
}catch(Exception e){
	if("author".equals(synBookType)){
		subject = "����ͼ��������Ϣ����1ʧ�ܣ�����ʼ�����!";
	}else if("type".equals(synBookType)){
		//������Ϣ����
		subject = "����ͼ�������Ϣ����2ʧ�ܣ�����ʼ�����!";
	}else if("book".equals(synBookType)){
		//ͼ����Ϣ
		subject = "����ͼ����Ϣ����3ʧ�ܣ�����ʼ�����!";
	}else if("month".equals(synBookType)){
		//������Ϣ
		subject = "����ͼ�������Ϣ����4ʧ�ܣ�����ʼ�����!";
	}else if("recommend".equals(synBookType)){
		//�Ƽ�ͼ����Ϣ
		subject = "����ͼ���Ƽ�ͼ����Ϣ����5ʧ�ܣ�����ʼ�����!";	
	}else if("catalog".equals(synBookType)){
		//ר����Ϣ
		subject = "����ͼ��ר����Ϣ����6ʧ�ܣ�����ʼ�����!";
	}else if("catalogcontent".equals(synBookType)){
		//ר��������Ϣ
		subject = "����ͼ��ר��������Ϣ����7ʧ�ܣ�����ʼ�����!";
	}else if("rank".equals(synBookType)){
	
		subject = "����ͼ��������Ϣ����8ʧ�ܣ�����ʼ�����!";
	}
}
if(null!=synBookType&&!"".equals(msgInfo.toString())){
	try{
		
		String[] mailTo = BaseBookConfig.get("BaseBookSynMailto").split(",");
		Mail.sendMail(subject, msgInfo.toString(), mailTo);	
	
	}catch(Exception e){
		msgInfo.append("�����ʼ�ʧ��!");
	}
}
%>
<html>
<head>
<title>����ͼ�����ݵ���</title>
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
  <input type="button" class="input1" name="btn_back" value="����" onClick="javascript:history.go(-1);">
  </CENTER>
  
</body>
</html>
