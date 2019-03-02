<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>货架管理子系统</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function resourceUpdate()
	{
		if(confirm("您确定要进行修改操作吗？"))
		{
			listForm.submit();
		}
		else
		{
			return false;	
		}
	}
	
	function onload()
	{
		var typeValue = '<bean:write name="baseSingerVO" property="type" />';
		var type = listForm.type;
		for(var i=0; i<type.length; i++)
		{
			if(type.options[i].value==typeValue)
			{
				type.options[i].selected=true;
				break;
			}
		}
	}
	</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="onload();">
		<table width="95%" border="0" align="center" cellspacing="1">
			<tr>
				<td>
					所在位置：基地音乐歌手管理----歌手属性修改
				</td>
			</tr>
		</table>
		<form name="listForm" method="post" action="querySinger.do"
			enctype="multipart/form-data" onSubmit="return resourceUpdate();">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						基地音乐歌手属性修改
					</td>
				</tr>
			</table>
			<input type="hidden" name="perType" value="update" />
			<input type="hidden" name="singerId"
				value="<bean:write name="baseSingerVO" property="singerId"/>" />
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="40%" align="right" class="text3">
						歌手ID：
					</td>
					<td class="text4">
						<bean:write name="baseSingerVO" property="singerId" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						歌手名称：
					</td>
					<td class="text4">
						<bean:write name="baseSingerVO" property="singerName" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						歌手首字母：
					</td>
					<td class="text4">
						<bean:write name="baseSingerVO" property="nameLetter" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						歌手简介：
					</td>
					<td class="text4">
						<bean:write name="baseSingerVO" property="description" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						歌手图片：
					</td>
					<td class="text4">
						<bean:write name="baseSingerVO" property="imgUrl" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						歌手属性：
					</td>
					<td class="text4">
						<select name="type">
							<option value="">请选择</option>
							<option value="11">华语男</option>
							<option value="12">华语女</option>
							<option value="13">华语组合</option>
							<option value="21">欧美男</option>
							<option value="22">欧美女</option>
							<option value="23">欧美组合</option>
							<option value="31">日韩男</option>
							<option value="32">日韩女</option>
							<option value="33">日韩组合</option>
						</select>
					</td>
				</tr>
				<logic:iterate id="vo" indexId="ind" name="keyBaseList"
					type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
					<tr>
						<td align="right" class="text3">
							<bean:write name="vo" property="keydesc" />
							：
						</td>
						<td class="text4">
							<logic:equal value="1" name="vo" property="keyType">
								<input name="<bean:write name="vo" property="keyname"/>"
									type="text" value="<bean:write name="vo" property="value"/>"
									size="60">
							</logic:equal>
							<logic:equal value="2" name="vo" property="keyType">
								<input type="file"
									name="<bean:write name="vo" property="keyname"/>" size="60"> &nbsp;仅支持png格式<bean:write
									name="vo" property="value" />
							</logic:equal>
							<logic:equal value="3" name="vo" property="keyType">
								<textarea name="<bean:write name="vo" property="keyname"/>"
									rows="5" cols="40"><bean:write name="vo" property="value" /></textarea>
							</logic:equal>
							<logic:notEqual value="" name="vo" property="value">
								<input type="checkbox" value="1"
									name="clear_<bean:write name='vo' property='keyname'/>" />清空当前扩展字段内容
      						</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="修改">
						<input name="button2" type="button" class="input1"
							onClick="history.go(-1);" value="返回">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
