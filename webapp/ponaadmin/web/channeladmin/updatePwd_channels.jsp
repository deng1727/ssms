<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsVO"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>������������Ϣ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="init()">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<% 
					OpenChannelsVO vo = (OpenChannelsVO)request.getAttribute("vo");
				%>
			<tr>
				<td align="center" class="title1">
					��������������
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="openChannels.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				
				
				<tr>
					<input name="channelsId" type="hidden" value="<%=vo.getChannelsId()%>"/>
					<td align="right" class="text3">
						�������˺ţ�
					</td>
					<td class="text4">
						<%=vo.getChannelsNo()%>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�����룺
					</td>
					<td class="text4">
						<input type="password" id="newPwd" name="channelsPwd" size="20" value="" style="width:35%;">
					</td>
				</tr>
				<tr>
					<td align="right" class="text3" >
						ȷ�������룺
					</td>
					<td class="text4">
						<input type="password" name="channelsRePwd" size="20" value="" style="width:35%;">
					</td>
				</tr>
				<tr align="center" class="text3">
					<td colspan="2">
						<input name="button2" type="button" onclick="updatePwd();" value="����">
						<input name="button" type="button" onclick="back();" value="����">
						
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript" language="javascript">

   function init(){
		document.getElementById("newPwd").value="";
   }
   window.onload=setTimeout('init()',20);
	function updatePwd()
	{
		var reg = /^[a-z|A-Z|0-9]+$/g;
		var channelsId= ContentForm.channelsId.value
		var channelsPwd = ContentForm.channelsPwd.value;
		var channelsRePwd = ContentForm.channelsRePwd.value;
		
		
		if(channelsPwd=="")
		{
			alert("�Բ�������������������");
			ContentForm.effectiveTime.focus();
			return false;
		}
		if(channelsRePwd=="")
		{
			alert("�Բ���������ȷ������");
			ContentForm.effectiveTime.focus();
			return false;
		}
		if(channelsRePwd != channelsPwd )
		{
			alert("�Բ��������������벻��ͬ������������");
			ContentForm.effectiveTime.focus();
			return false;
		}
		 if(!reg.test(channelsPwd) || getLength(channelsPwd)<6 || getLength(channelsPwd)>20)
		  {
		    alert("���볤�ȱ�����6-20λ������ֻ�ܰ������ֺ���ĸ��");
		    ContentForm.channelsPwd.focus();
		    return false;
		  }
		ContentForm.action = "queryOpenChannelsList.do?method=updatePwd&channelsId="+channelsId;
		ContentForm.submit();
	}
	
	function back(){
		window.history.back(-1);
	}
	
</script>
</html>
