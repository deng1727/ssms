<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.category.rule.RuleVO"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%

  List ruleTypeList=CategoryUpdateConfig.getInstance().getNodeValueList("ruleType");
  pageContext.setAttribute("ruleTypeList", ruleTypeList, PageContext.PAGE_SCOPE);
%>

<%@page import="java.util.List"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>���ܲ��Թ�����ϸ��Ϣ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript">
		   function into()
		   {
				var ruleType = "<bean:write name="vo" property="ruleType" />";
				var intervalType = "<bean:write name="vo" property="intervalType" />";
				var select_rule = ContentForm.ruleType;
				var select_intervalType = ContentForm.intervalType;
				
				for(var i=0; i<select_rule.length; i++)
				{
					if(select_rule.options[i].value==ruleType)
					{
						select_rule.options[i].selected=true;
						break;
					}
				}
				
				for(var i=0; i<select_intervalType.length; i++)
				{
					if(select_intervalType.options[i].value==intervalType)
					{
						select_intervalType.options[i].selected=true;
						break;
					}
				}
				
				displayLines(intervalType);
				selectRuleType()
		   }
		   
		   function selectRuleType(){
		   		var ruleType = ContentForm.ruleType.value;
		   		if(ruleType=="7"){
		   			document.getElementById('tr_maxGoodsNum').style.display="";
		   			document.getElementById('td_random').innerHTML="����COMPANYID������߸�����"
		   			document.getElementById('td_random_after').innerHTML="Ϊ0��-1��ʾ�����ǣ�����ط�����Ϊ������COMPANYID��һ�������µ�������";
		   		}else{
		   			document.getElementById('tr_maxGoodsNum').style.display="none";
		   			document.getElementById('td_random').innerHTML="����ϼ����ӣ�"
		   			document.getElementById('td_random_after').innerHTML="";
		   		}
		   }
		   
		   function select()
		   {
		   		var intervalType = ContentForm.intervalType.value;
		   		displayLines(intervalType);
		   		
		   }
		   function displayLines(intervalType)
		   {
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
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="into();">
		<form name="ContentForm" action="ruleEdit.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					������ϸ��Ϣ
				</td>
			</tr>
		</table>
		<input type="hidden" name="id"
			value="<bean:write name="vo" property="ruleId"/>">
		<input type="hidden" name="action"
			value="edit">
		<input type="hidden" name="isEditName"
			value="0">
		<input type="hidden" name="oldRuleName"
			value="<bean:write name="vo" property="ruleName" />">
		<input type="hidden" name="actionUrl"
			value="ruleList.do">			
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td width="30%" align="right" class="text3">
					�����ţ�
				</td>
				<td width="70%" class="text4" style="word-break:break-all;">
					<bean:write name="vo" property="ruleId"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������ƣ�
				</td>
				<td class="text4" style="word-break:break-all;">
					<INPUT type="text" size="30" name="ruleName" value="<bean:write name="vo" property="ruleName" />" maxlength="30"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������ͣ�
				</td>
				<td class="text4">
				
					<select name="ruleType" onChange="selectRuleType();">
					    <logic:iterate id="ruleType" name="ruleTypeList" type="com.aspire.ponaadmin.web.category.RecordVO" >
					    <option value="<bean:write name="ruleType" property="key"/>"><bean:write name="ruleType" property="value"/></option>
					    </logic:iterate>
					</select>
				</td>
			</tr>
			
			<tr id = "tr_maxGoodsNum">
				<td align="right" class="text3">
					����ˢ�»�����Ʒ��������
				</td>
				<td class="text4">
					<INPUT type="text" size="20" name="maxGoodsNum" value="<bean:write name="vo" property="maxGoodsNum" />" maxlength="20"/> -1��ʾ�����ơ��������һ�������С�������������Ʒ���࣬��Ϊ��������ˢ�µġ�
				</td>
			</tr>
			
			<tr>
				<td align="right" class="text3">
					ִ��ʱ�������ͣ�
				</td>
				<td class="text4">
					<select name="intervalType" onChange="select();">
						<option value="0"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","0") %></option>
						<option value="1"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","1") %></option>
						<option value="2"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","2") %></option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					ִ��֮������
				</td>
				<td class="text4">
					<INPUT type="text" name="excuteInterval" 
					value="<bean:write name="vo" property="excuteInterval" />"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="5"
					/><span id="text1">��</span>
				</td>
			</tr>
			<tr id="span" >
				<td align="right" class="text3">
					��һ��ʱ����֮�ڵ�ִ�����ӣ�
				</td>
				<td class="text4">
					<INPUT type="text" name="excuteTime" 
					value="<bean:write name="vo" property="excuteTime" />"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="5"/><span id="text"></span>
				</td>
			</tr>
			<tr>
				<td id = "td_random" align="right" class="text3">
					����ϼ����ӣ�
				</td>
				<td class="text4">
					<INPUT type="text" name="randomFactor" 
					value="<bean:write name="vo" property="randomFactor" />"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="3"/><span id="td_random_after"></span>
				</td>
			</tr>
			<tr align="center" class="text3">
				<td colspan="2">
					<input type="button" value="�޸�" onClick="editRule();">
					<input type="button" value="ɾ��" onClick="delRule();">					
					<input name="button2" type="button" onClick="returnList();" value="����">
				</td>
			</tr>
		</table>
		
		
		<br>
		<br>  
		
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td align="center" class="title1">�����б�</td>
		  </tr>
		</table>
		
		<logic:empty name="PageResult" property="pageInfo">
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
		      <tr bgcolor="#B8E2FC">
		          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
		      </tr>
		</table>
		</logic:empty>
		
		<logic:notEmpty name="PageResult" property="pageInfo">
		<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
		  <tr class="text5">
			<td align="center">�������</td>
		    <td align="center">��������</td>
		    <td align="center">��������</td>
		    <td align="center">����sql</td>
		    <td align="center">����sql</td>
		    <td align="center">��ȡ��Ʒ����</td>
   		    <td align="center">����</td>
   		    <td align="center">����</td>
		  </tr>
			<%String tmpStyle = "text5";%>
			<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.rule.condition.ConditionVO">
			    <%
				if("text5".equals(tmpStyle))
			  	{
					tmpStyle = "text4";
			  	}
			  	else
			  	{
			  		tmpStyle = "text5";
			  	}
				%>
				<tr class=<%=tmpStyle%>>
					<td align="center">
						<bean:write name="vo" property="ruleId"/>
					</td>
					<td align="center" style="word-break:break-all;">
						<bean:write name="vo" property="cid"/>
					</td>
					<td align="center">
						<bean:write name="vo" property="baseCondName"/>			
					</td>
		      		<td align="center">
		      			<bean:write name="vo" property="WSql"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="OSql"/>
		      		</td>
		     		<td align="center">
		     			<logic:equal value="-1" name="vo" property='count'>����</logic:equal>
		     			<logic:notEqual value="-1" name="vo" property='count'><bean:write name="vo" property="count"/></logic:notEqual>
					</td>
					<td align="center">
		     			<bean:write name="vo" property="sortId"/>
					</td>
					<td align="center">
		     			<a href="conditionEdit.do?action=delete&id=<bean:write name="vo" property="id"/>&ruleId=<bean:write name="vo" property="ruleId"/>">
		     			ɾ��
		     			</a>
		     			&nbsp;&nbsp;
						<a href="conditionEditView.do?action=editView&id=<bean:write name="vo" property="id"/>&ruleId=<bean:write name="vo" property="ruleId"/>">
						�޸�
						</a>
					</td>
				</tr>
			</logic:iterate>
		</table>
		</logic:notEmpty>
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	        <tr>
	            <td align="center" class="text2">
				<input type="button" onClick="return addCond();" value="��������ѡ��">
	          	</td>
	        </tr>
		</table>
	</form>
	</body>
	<script language="javascript">
	   function returnList()
	   {
			window.self.location.href="ruleList.do?ruleId=<bean:write name="backId"/>&ruleName=<bean:write name="backName" />&ruleType=<bean:write name="backRuleType" />&intervalType=<bean:write name="backIntervalType" />";
	   }
	   
	   function addCond()
	   {
			window.self.location.href="conditionAddView.do?action=addView&id=<bean:write name="vo" property="ruleId"/>";
	   }
	   
	   function delRule()
	   {
			if(!confirm('ɾ������ʱ��ɾ����Ӧ������ȷ��Ҫɾ���������ܲ��Թ�����'))
			{
			   return false;
			}
	   		ContentForm.action.value="del";
	   		ContentForm.submit();
	   }
	   function editRule()
	   {
			var randomFactor = parseInt(ContentForm.randomFactor.value);
			var excuteTime = parseInt(ContentForm.excuteTime.value);
			var excuteInterval = parseInt(ContentForm.excuteInterval.value);
			var intervalType = ContentForm.intervalType.value;
			var oldRuleName = ContentForm.oldRuleName.value;
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
			
			if(oldRuleName!=ruleName)
			{
				ContentForm.isEditName.value='1';
			}
			
			ContentForm.submit();
	   }
	</script>
</html>
