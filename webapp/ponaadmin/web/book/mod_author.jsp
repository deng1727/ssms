<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>�����Ķ���������</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function resourceUpdate()
	{
		var isO = listForm.isOriginal.value;
		var isP = listForm.isPublish.value;
		var rec = listForm.recommendManual.value;
		var nl = listForm.nameLetter.value;
		var desc = listForm.desc.value;
		
		if(nl!='')
		{
			var reg=/^[A-Z]{1}$/;
		
			if(!reg.test(nl)) 
			{
				alert("��������ĸֻ��Ϊһλ��д��ĸ��");
				listForm.nameLetter.select();
				listForm.nameLetter.focus();
				return false;
			}
		}
		
		if(isO!=1 && isO!=0)
		{
			alert("�Ƿ�ԭ������ֵֻ����ȡ0��1��");
			listForm.isOriginal.focus();
			return false;
		}
		if(isP!=1 && isP!=0)
		{
			alert("�Ƿ��������ֵֻ����ȡ0��1��");
			listForm.isPublish.focus();
			return false;
		}
		
		var reg=/^[1-9]d*|0|-1$/;
		
		if(!reg.test(rec)) 
		{
			alert("�Ƽ����ֻ����Ϊ��ֵ�ͣ�");
			listForm.recommendManual.focus();
			return false;
		}
		
		if(confirm("��ȷ��Ҫ�����޸Ĳ�����"))
		{
			listForm.submit();
		}
		else
		{
			return false;	
		}
	}
	
	function isNumber(value){
		return /^[(-?\d+\.\d+)|(-?\d+)|(-?\.\d+)]+$/.test(value + '');
	}
</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="1">
			<tr>
				<td>
					����λ�ã������Ķ����߹���----���������޸�
				</td>
			</tr>
		</table>
		<form name="listForm" method="post" action="queryAuthor.do"
			enctype="multipart/form-data" onSubmit="return resourceUpdate();">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						�����Ķ����������޸�
					</td>
				</tr>
			</table>
			<input type="hidden" name="perType" value="update" />
			<input type="hidden" name="authorId"
				value="<bean:write name="bookAuthorVO" property="authorId"/>" />
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="40%" align="right" class="text3">
						����ID��
					</td>
					<td class="text4">
						<bean:write name="bookAuthorVO" property="authorId" />
					<br></td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�������ƣ�
					</td>
					<td class="text4">
						<bean:write name="bookAuthorVO" property="authorName" />
					<br></td>
				</tr>
				<tr>
					<td align="right" class="text3">
						���߼�飺
					</td>
					<td class="text4">
						<input type="text" name="desc" value="<bean:write name="bookAuthorVO" property="description" />"/>
					<br></td>
				</tr>
				<tr>
					<td align="right" class="text3">
						��������ĸ��
					</td>
					<td class="text4">
						<input type="text" name="nameLetter" value="<bean:write name="bookAuthorVO" property="nameLetter" />"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�Ƿ�ԭ������
					</td>
					<td class="text4">
						<input type="text" name="isOriginal" value="<bean:write name="bookAuthorVO" property="isOriginal" />"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�Ƿ�������ߣ�
					</td>
					<td class="text4">
						<input type="text" name="isPublish" value="<bean:write name="bookAuthorVO" property="isPublish" />"/>
					</td>
				</tr>
				  <tr>
	    <td align="right" class="text3">����ͼƬ��</td>
	    <td class="text4"><input  type="file"  name="authorPic"   size="60"><a clolor="red"> &nbsp;ͷ���֧�ֹ��75x75
	    png��ʽ</a>
	    <bean:write name="bookAuthorVO" property="authorPic"/>
	    </td>
	  </tr>
				
				<tr>
					<td align="right" class="text3">
						�Ƽ���ţ�
					</td>
					<td class="text4">
						<input type="text" name="recommendManual" value="<bean:write name="bookAuthorVO" property="recommendManual" />"/>
					</td>
				</tr>
				<logic:iterate id="vo" indexId="ind" name="keyBaseList"
					type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
					<tr>
						<td align="right" class="text3">
							<bean:write name="vo" property="keydesc" />
							��
						</td>
						<td class="text4">
							<logic:equal value="1" name="vo" property="keyType">
								<input name="<bean:write name="vo" property="keyname"/>"
									type="text" value="<bean:write name="vo" property="value"/>"
									size="60">
							</logic:equal>
							<logic:equal value="2" name="vo" property="keyType">
								<input type="file"
									name="<bean:write name="vo" property="keyname"/>" size="60"> &nbsp;��֧��png��ʽ<bean:write
									name="vo" property="value" />
							</logic:equal>
							<logic:equal value="3" name="vo" property="keyType">
								<textarea name="<bean:write name="vo" property="keyname"/>"
									rows="5" cols="40"><bean:write name="vo" property="value" /></textarea>
							</logic:equal>
							<logic:notEqual value="" name="vo" property="value">
								<input type="checkbox" value="1"
									name="clear_<bean:write name='vo' property='keyname'/>" />��յ�ǰ��չ�ֶ�����
      						</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="�޸�">
						<input name="button2" type="button" class="input1"
							onClick="history.go(-1);" value="����">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
