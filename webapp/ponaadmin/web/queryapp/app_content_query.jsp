<%@ page contentType="text/html; charset=gbk" %>
<%@page import="java.util.List"%>
<%@page import="java.util.StringTokenizer"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>

<script language="javascript">

	
 function empty()
 {
 	QueryForm.contentid.value = "";
 	QueryForm.name.value = "";
 	QueryForm.icpcode.value = "";
 	QueryForm.develoepr.value = "";
// 	QueryForm.spname.value = "";
 	QueryForm.catename.value = "";
 	QueryForm.beginDate.value = "";
 	QueryForm.endDate.value = "";
 	QueryForm.id.value = "";
 	QueryForm.servattr.value = "";
 	QueryForm.keywords.value = "";
 }
 

function search()
{
	QueryForm.action = "../queryapp/QueryAppAction.do?opType=doQueryContentList";
	QueryForm.submit();
}


function exportApp()
{
	QueryForm.action = "../queryapp/QueryAppAction.do?opType=doExport&type=content";
	QueryForm.submit();  
}

</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1">
				��Ʒȫ��Ӧ�ò�ѯ
			</td>
		</tr>
	</table>
	<form name="QueryForm" action="" method="post">
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
				<td width="20%" align="center" class="text3">
					Ӧ��ID��
				</td>
				<td width="30%" class="text4">
					<input type="text" name="id" value="<bean:write name="id"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					Ӧ�����ƣ�
				</td>
				<td width="30%" class="text4">
					<input type="text" name="name" value="<bean:write name="name"/>"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="center" class="text3">
					SP��ҵ���룺
				</td>
				<td width="30%" class="text4">
					<input type="text" name="icpcode" value="<bean:write name="icpcode"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					�ṩ�̣�
				</td>
				<td width="30%" class="text4">
					<input type="text" name="developer" value="<bean:write name="developer"/>"/>
<%-- 					<input type="text" name="spname" value="<bean:write name="spname"/>"/> --%>
				</td>
			</tr>
			
			
			
			<tr>
				<td width="20%" align="center" class="text3">
					����ID��
				</td>
				<td width="30%" class="text4">
					<input type="text" name="contentid" value="<bean:write name="contentid"/>"/>
				</td>
				<td width="20%" align="center" class="text3">
					���ݱ�ǩ��
				</td>
				<td width="30%" class="text4">
					<input type="text" name="keywords" value="<bean:write name="keywords"/>"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="center" class="text3">
					��ʼʱ�䣺
				</td>
				
				<td width="30%" class="text4">
			
					<input name="beginDate" class="Wdate" type="text"  value="<bean:write name="beginDate"/>"  style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
				</td>
				
				<td width="20%" align="center" class="text3">
					����ʱ�䣺
				</td>
				<td width="30%" class="text4">
					<input name="endDate" class="Wdate" type="text"  value="<bean:write name="endDate"/>"  style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
				</td>
			</tr>
			
			
			<tr>
				<td width="20%" align="center" class="text3">
					ҵ�����ݷ��ࣺ
				</td>
				<td width="30%" class="text4">
					<select name="catename">
						<option value=""   <logic:equal name="catename" value="">selected="selected"</logic:equal>  >ȫ��</option>
						<option value="���"  <logic:equal name="catename" value="���">selected="selected"</logic:equal> >���</option>
						<option value="��Ϸ"  <logic:equal name="catename" value="��Ϸ">selected="selected"</logic:equal> >��Ϸ</option>
						<option value="����"  <logic:equal name="catename" value="����">selected="selected"</logic:equal> >����</option>
					</select>
				</td>
				
				<td width="20%" align="center" class="text3">
					����Χ��
				</td>
				<td width="30%" class="text4">
					<select name="servattr">
						<option value=""   <logic:equal name="servattr" value="">selected="selected"</logic:equal>  >ȫ��</option>
						<option value="G"  <logic:equal name="servattr" value="G">selected="selected"</logic:equal> >ȫ��ҵ��</option>
						<option value="L"  <logic:equal name="servattr" value="L">selected="selected"</logic:equal> >ʡ��ҵ��</option>
					</select>
				</td>
			</tr>
			 <tr>
				<td width="20%" align="center" class="text3">
					ÿҳչʾ��¼��:
				</td>
				<td width="30%" class="text4">
					<input type="text" name="pageSize" value="<bean:write name="pageSize"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					�����ߣ�
				</td>
				<td width="30%" class="text4">
<%-- 					<input type="text" name="developer" value="<bean:write name="developer"/>"/> --%>
					<input type="text" name="spname" value="<bean:write name="spname"/>"/>
				</td>
			</tr>
				<tr>
					<td align="center" class="text3" colspan="4">
						<input type="button" class="input1" value="��ѯ" onclick="search()">
						<input type="button" class="input1" value="����" onclick="empty()">
						<logic:notEmpty name="PageResult" property="pageInfo">
							<input type='button' value='����' class='input1' onclick='exportApp();'>
						</logic:notEmpty>
					</td>
				</tr>
			</table>
		</form>
		<br>
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					��ѯ���
				</td>
			</tr>
		</table>

		<logic:present name="PageResult">
			<form name="modifyForm" method="post" action="">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
					<tr class="title2">
						<td width="5%" align="center">
							Ӧ��ID
						</td>
						<td width="5%" align="center">
							Ӧ������
						</td>
						<td width="5%" align="center">
							����ID
						</td>
						<td width="5%" align="center">
							SP��ҵ����
						</td>
						<td width="5%" align="center">
							�ṩ��
						</td>
						<td width="5%" align="center">
							������
						</td>
						<td width="5%" align="center">
							ҵ�����ݷ���
						</td>
						<td width="5%" align="center">
							����ʱ��
						</td>
						<td width="5%" align="center">
							������ʱ��
						</td>
						<td width="5%" align="center">
							�۸�(Ԫ)
						</td>
						<td width="5%" align="center">
							����Χ
						</td>
						<td width="5%" align="center">
							��ʷ������
						</td>
						<td width="5%" align="center">
							���ݱ�ǩ
						</td>
						<td width="5%" align="center">
							Ӧ�ü��
						</td>
					</tr>

					<logic:empty name="PageResult" property="pageInfo">
						<table align="center">
							<tr class="text4">
								<td colspan="7" align="center">
									û�з��������ļ�¼
								</td>
							</tr>
						</table>
					</logic:empty>
					<logic:notEmpty name="PageResult">
						<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO">
							<tr <%=ind.intValue()%2== 0 ? "class=\'text4\'" : "class=\'text3\'"%>>

								<td align="center" style='word-break:break-all'>
									<a href="../queryapp/QueryAppAction.do?opType=doQueryAppDetail&id=<bean:write name="vo" property="id"/>&contentId=<bean:write name="vo" property="contentid"/>&catename=<bean:write name="vo" property="catename"/>"><bean:write name="vo" property="id"/></a>
								</td>
								<td align="center" style='word-break:break-all'>
									<a href="../queryapp/QueryAppAction.do?opType=doQueryAppDetail&id=<bean:write name="vo" property="id"/>&contentId=<bean:write name="vo" property="contentid"/>&catename=<bean:write name="vo" property="catename"/>"><bean:write name="vo" property="name"/></a>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="contentid"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="icpcode"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="developer"/>
<%-- 									<bean:write name="vo" property="spname"/> --%>
								</td>
								<td align="center" style='word-break:break-all'>
<%-- 									<bean:write name="vo" property="developer"/> --%>
									<bean:write name="vo" property="spname"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="catename"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="marketdate"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="plupddate"/>
								</td>
								
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="mobileprice"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<logic:equal name="vo" property="servattr" value="G">ȫ��ҵ��</logic:equal> 
									<logic:equal name="vo" property="servattr" value="L">ʡ��ҵ��</logic:equal> 
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="orderTimes"/>
								</td>
								<td align="left" style='word-break:break-all'>
									<%
									if(!"".equals(vo.getKeywords())&&null!=vo.getKeywords())
									{
										StringTokenizer line=new StringTokenizer(vo.getKeywords(),"\n");
										while(line.hasMoreTokens()){
										out.println(line.nextToken()+"<br>");
										}
									}


									%>
								</td>
								<td align="center" style='word-break:break-all'>
									<textarea  readonly="readonly" rows="3"  cols="20"><bean:write name="vo" property="introduction"/></textarea>
								</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</table>
			</form>
		</logic:present>

		<table width="95%" border="0" align="center">
			<logic:notEmpty name="PageResult">
				<logic:notEmpty name="PageResult" property="pageInfo">
					<tr class="text1">
						<td width="24%" class="text1">
							&nbsp;
						</td>
						<td align="right">
							<pager:pager name="PageResult" form="QueryForm" action="/web/queryapp/QueryAppAction.do?opType=doQueryContentList">
								<pager:firstPage label="��ҳ" />&nbsp;
					            <pager:previousPage label="ǰҳ" />&nbsp;
					            <pager:nextPage label="��ҳ" />&nbsp;
					            <pager:lastPage label="βҳ" />&nbsp;
					            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
								�ܼ�¼��:<pager:recordCount />
								<pager:location id="1" />
							</pager:pager>
						</td>
					</tr>
				</logic:notEmpty>
			</logic:notEmpty>
		
		</table>
</body>
</html>
