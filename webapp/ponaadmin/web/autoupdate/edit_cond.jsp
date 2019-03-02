<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>

<%@page import="java.util.List"%><html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�޸Ĺ�������</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/tools.js"></script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="ContentForm" action="conditionEdit.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					����������Ϣ
				</td>
			</tr>
		</table>
		<input type="hidden" name="action"
			value="edit">
		<input type="hidden" name="ruleId"
			value="<bean:write name="ruleId" />">
		<input type="hidden" name="condId"
			value="<bean:write name="id" />">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td width="30%" align="right" class="text3">
					������룺
				</td>
				<td width="70%" class="text4" style="word-break:break-all;">
					<bean:write name="ruleId" />
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������룺
				</td>
				<td class="text4">
					<input name="id" type="text" value="<bean:write name="vo" property="cid"/>" 
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
					<input type="button" value="��ѡ��" onclick="selectCid();"/>
					<input type="button" value="���" onclick="delCid();"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������ͣ�
				</td>
				<td class="text4">
					<select name="condType" >
						<logic:iterate id="baseCond" name="baseCondList"
						type="com.aspire.ponaadmin.web.category.ui.condition.BaseCondVO">
						<option value="<bean:write name="baseCond" property="baseId"/>"><bean:write
							name="baseCond" property="baseName" />
					    </option>
					    </logic:iterate>
					    
					</select>
					<script language="javascript">
						var condType = ContentForm.condType;
						var type = '<bean:write name="vo" property="condType"/>';
						
						for(var i=0; i<condType.options.length; i++)
						{
							if(condType.options[i].value==type)
							{
								condType.options[i].selected=true;
								break;
							}
						}
						
					</script>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					��ȡ������sql��
				</td>
				<td class="text4">
					<INPUT type="text" name="wSql" value="<bean:write name="vo" property="WSql"/>" size="100"/>
					<input type="button" value="�༭" onclick="editWSql();"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					��ȡ������sql��
				</td>
				<td class="text4">
					<INPUT type="text" name="oSql" value="<bean:write name="vo" property="OSql"/>" size="100"/>
					<input type="button" value="�༭" onclick="editOSql();"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					��ȡ����Ʒ������
				</td>
				<td class="text4">
					<INPUT type="text" name="count" value="<bean:write name="vo" property="count"/>"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="5"/>
					��
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">
					�������
				</td>
				<td class="text4">
					<INPUT type="text" name="sortId" value="<bean:write name="vo" property="sortId"/>"
					onkeydown="if(event.keyCode==13) event.keyCode=9"
					onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" 
					maxlength="3"/>
				</td>
			</tr>
			<tr align="center" class="text3">
				<td colspan="2">
					<input type="button" value="�޸�" onClick="editCond();">
					<input name="button2" type="button" onClick="returnList();" value="����">
				</td>
			</tr>
		</table>
		</form>
	</body>
	<script language="javascript">
		function returnList()
		{
			history.back();
		}
		function delCid()
		{
			ContentForm.id.value = '';
		}
		function editOSql()
		{
			var obj = new Object();
			obj.sql=ContentForm.oSql.value;
			
			var returnv=window.showModalDialog("add_osql.jsp", obj,"width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300") 
			
			if(returnv != undefined)
			{
				ContentForm.oSql.value=returnv;
			}
		}
		function editWSql()
		{
			var obj = new Object();
			obj.sql=ContentForm.wSql.value;
			
			var returnv=window.showModalDialog("add_wsql.jsp", obj,"width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300") 
			
			if(returnv != undefined)
			{
				ContentForm.wSql.value=returnv;
			}
		}
		function selectCid()
		{
			var returnv=window.showModalDialog("categoryRuleIdQuery.do?isFirst=1", "new","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300")
			if(returnv != undefined)
			{
				ContentForm.id.value=returnv;
			}  
		}
		function editCond()
		{
			var cid = ContentForm.id.value;
			var wSql = ContentForm.wSql.value;
			var oSql = ContentForm.oSql.value;
			var count = ContentForm.count.value;

			var reg = '!@#$%^&*��@#��%����&*';
			
			if(trim(oSql)!='')
			{
				for (i = 0;i<=(oSql.length-1);i++)
				{
					if (reg.indexOf(oSql.charAt(i))>=0)
					{
						flag=true;
						alert("����sql�����Դ�������!@#$%^&*��@#��%����&*�����ַ�");
						ContentForm.oSql.focus();
						return false;
					}
				}
			}

			reg = '@#��@#��%����&*';
			
			if(trim(wSql)!='')
			{
				for (i = 0;i<=(wSql.length-1);i++)
				{
					if (reg.indexOf(wSql.charAt(i))>=0)
					{
						flag=true;
						alert("����sql�����Դ�������@#��@#��%����&*�����ַ�");
						ContentForm.wSql.focus();
						return false;
					}
				}
			}
			
			if(count.indexOf('-')>=0)
			{
				if(count!='-1')
				{
					alert("��ȡ����Ϊ������ʱֻ��Ĭ��Ϊ-1��������Ϊ������");
					ContentForm.count.value='-1';
					ContentForm.count.focus();
					return false;
				}
			}
			
			ContentForm.submit();
		}
	</script>
</html>
