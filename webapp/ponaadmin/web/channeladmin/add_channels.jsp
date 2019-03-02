<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryRuleVO"%>
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
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					��������ϸ��Ϣ
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="queryOpenChannelsList.do?method=add" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="right" class="text3">
						���������ƣ�
					</td>
					<td class="text4" style="word-break:break-all;">
						<input id="n" name="channelsName" type="text" value="" style="width:30%;" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						������������
					</td>
					<td class="text4" style="word-break:break-all;">
						<textarea name="channelsDesc" cols="50"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�������˺ţ�
					</td>
					<td class="text4">
						<input type="channelsNo" name="channelsNo" value=""  style="width:30%;"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						���������룺
					</td>
					<td class="text4">
						<input type="password" name="channelsPwd" size="20" id="pwd"   value="" style="width:30%;">
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						ȷ�����룺
					</td>
					<td class="text4">
						<input type="password" name="channelsRePwd" size="20"  value="" style="width:30%;">
					</td>
				</tr>
				<tr align="center" class="text3">
					<td colspan="2">
						<input name="button2" type="button"  value="����" onclick="add();">
						<input name="button2" type="button"  value="����" onclick="back();">
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script language="javascript">
	function add()
	{
		var reg = /^[a-z|A-Z|0-9]+$/g;
		var reg1 = /^[a-z | A-Z | 0-9 | _]+$/g;
		var channelsName = ContentForm.channelsName.value;
		var channelsDesc = ContentForm.channelsDesc.value;
		var channelsNo = ContentForm.channelsNo.value;
		var channelsPwd = ContentForm.channelsPwd.value;
		var channelsRePwd = ContentForm.channelsRePwd.value;
		
		if(channelsName=="")
		{
			alert("�Բ������������������ƣ�");
			ContentForm.id.focus();
			return false;
		}
		if(channelsNo=="")
		{
			alert("�Բ����������������˺�");
			ContentForm.ruleId.focus();
			return false;
		}
		if(!reg1.test(channelsNo) || getLength(channelsNo)<4 || getLength(channelsNo)>20){
			alert("�������ʺų��ȱ�����4-20λ������ֻ�ܰ������֡���ĸ���»��ߣ�");
			ContentForm.channelsNo.focus();
			return false;
		}
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
		if(!checkName(channelsName,"REG_CHINESE_NUM_LERTER_COMMA")
			        || getLength(trim(channelsName))<4  || getLength(trim(channelsName))>20)
			  {
			    alert("����������ֻ�ܰ������֡���ĸ�����š��»��ߡ����֣����ҳ��ȱ���Ϊ4-20���ַ���һ������ռ�����ַ�����");
			    ContentForm.channelsName.focus();
			    return false;
			  }
		 if(!reg.test(channelsPwd) || getLength(channelsPwd)<6 || getLength(channelsPwd)>20)
		  {
		    alert("���볤�ȱ�����6-20λ������ֻ�ܰ������ֺ���ĸ��");
		    ContentForm.channelsPwd.focus();
		    return false;
		  }
		  
		ContentForm.action = "queryOpenChannelsList.do?method=save";
			ContentForm.submit();
	}
	
	function back(){
	window.history.back(-1);
	}

	
	function addInit(){
		document.getElementById("n").value="";
		document.getElementById("pwd").value="";
	}
	window.onload=setTimeout('addInit()',20); 
	
	
	
	 
	/*
	 * ���һ������������
	 */
	function checkName(field,ctype)
	{
	    var reg;
	    if(ctype=="REG_CHINESE_NUM_LETTER")
	    {
	        reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
	    }
	    else if(ctype=="REG_CHINESE_NUM_LERTER_COMMA")
	    {
	        reg = /^[\w,\u4e00-\u9fa5]+$/g
	    }
	    else if(ctype=="REG_CHINESE_NUM_LERTER_UNDERLINE")
	    {
	        reg = /^[\w\_\u4e00-\u9fa5]+$/g
	    }
	    return reg.test(field);
	}
							
					
</script>
</html>
