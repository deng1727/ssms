<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%

  List ruleTypeList=CategoryUpdateConfig.getInstance().getNodeValueList("ruleType");
  pageContext.setAttribute("ruleTypeList", ruleTypeList, PageContext.PAGE_SCOPE);
%>

<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@page import="java.util.List"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�������ܲ��Թ���</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript">
		   function select()
		   {
		   		var intervalType = ContentForm.intervalType.value;
		   		
		   		if(intervalType==0)
				{
					document.getElementById('span').style.display="none";
					document.getElementById('text').innerHTML="";
				}
				else
				{
					document.getElementById('span').style.display="";
					
					if(intervalType==1)
					{
					    document.getElementById('text1').innerHTML="��";
						document.getElementById('text').innerHTML="�ܼ�ִ��";
					}
					else
					{
					     document.getElementById('text1').innerHTML="��";
						 document.getElementById('text').innerHTML="����ִ��";
					}
				}
		   }
		</script>

	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="ContentForm" action="ruleEdit.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					����������Ϣ
				</td>
			</tr>
		</table>
		<input type="hidden" name="action"
			value="add">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td width="30%" align="right" class="text3">
					�������ƣ�
				</td>
				<td width="70%" class="text4" style="word-break:break-all;">
					<INPUT type="text" name="ruleName" value=""/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������ͣ�
				</td>
				<td class="text4">
					<select name="ruleType">
					    <logic:iterate id="ruleType" name="ruleTypeList" type="com.aspire.ponaadmin.web.category.RecordVO" >
					    <option value="<bean:write name="ruleType" property="key"/>"><bean:write name="ruleType" property="value"/></option>
					    </logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					ִ��ʱ�������ͣ�
				</td>
				<td class="text4">
					<select name="intervalType" onchange="select();">
						<option value="0">��</option>
						<option value="1">��</option>
						<option value="2">��</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					ִ��֮������
				</td>
				<td class="text4">
					<INPUT type="text" name="excuteInterval" value="1"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="5"
					/><span id="text1">��</span>
				</td>
			</tr>
			<tr id="span" style="display:none">
				<td align="right" class="text3">
					��һ��ʱ����֮�ڵ�ִ�����ӣ�
				</td>
				<td class="text4">
					<INPUT type="text" name="excuteTime" value="0"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="5"/><span id="text"></span>
				</td>
			</tr>

			<tr>
				<td align="right" class="text3" title='��Ʒ���ϼ�ǰ�Ƿ���Ҫ�������0�������100�������1~99 ���ͻ��ܷ���С���'>
					����ϼ����ӣ�
				</td>
				<td class="text4">
					<INPUT type="text" name="randomFactor" value="0"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="3"/>
				</td>
			</tr>
			<tr align="center" class="text3">
				<td colspan="2">
					<input type="button" value="����" onClick="addRule();">
					<input name="button2" type="button" onClick="returnList();" value="����">
				</td>
		</table>
		</form>
	</body>
	<script language="javascript">
	   function returnList()
	   {
			window.self.location.href="ruleList.do";
	   }
	   function addRule()
	   {
			var randomFactor = parseInt(ContentForm.randomFactor.value);
			var excuteTime = parseInt(ContentForm.excuteTime.value);
			var excuteInterval = parseInt(ContentForm.excuteInterval.value);
			var intervalType = ContentForm.intervalType.value;
			var ruleName = ContentForm.ruleName.value;			
			
			if(!(randomFactor>=0&&randomFactor<=100))
			{
				alert("����ϼ�����Ҫ������ڵ���0С�ڵ���100������");
				ContentForm.randomFactor.focus();
				return false;
			}
			
			if(intervalType==1)
			{
				if(!(excuteTime>=1&&excuteTime<=7))
				{
					alert("��ִ��ʱ��������Ϊ��ʱ��Ҫ����1-7��������");
					ContentForm.excuteTime.focus();
					return false;
				}
			}
			else if(intervalType==2)
			{
				if(!(excuteTime>=1&&excuteTime<=31))
				{
					alert("��ִ��ʱ��������Ϊ��ʱ��Ҫ������ڵ���1С�ڵ���31������");
					ContentForm.excuteTime.focus();
					return false;
				}
			}
			
			if(excuteInterval==0)
			{
				alert("��ִ��ʱ����������Ϊ����");
				ContentForm.excuteInterval.focus();
				return false;
			}
						
			if(trim(ContentForm.excuteInterval.value)=='')
			{
				alert("��ִ��ʱ����������Ϊ��");
				ContentForm.excuteInterval.focus();
				return false;
			}
			if(trim(ruleName)=='')
			{
				alert("�������Ʋ�����Ϊ��");
				ContentForm.ruleName.focus();
				return false;
			}
			
			var reg = '!@#$%^&*��@#��%����&*';
			
			for (i = 0;i<=(ruleName.length-1);i++)
			{
				if (reg.indexOf(ruleName.charAt(i))>=0)
				{
					flag=true;
					alert("�������Ʋ����Դ�������!@#$%^&*��@#��%����&*�����ַ�");
					ContentForm.ruleName.focus();
					return false;
				}
			}

			ContentForm.submit();
	   }
	</script>
</html>
