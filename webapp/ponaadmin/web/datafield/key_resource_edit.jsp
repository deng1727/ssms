<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã����ܹ�������----Ӧ����չ�����޸�</td>
  </tr>
</table>
<form name="form1" method="post" action="resourceUpdate.do"    enctype="multipart/form-data"  onSubmit="return resourceUpdate();" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">Ӧ����չ�����޸�</td>
  </tr>
</table>
<input type="hidden" name="tid" value="<bean:write name="vo" property="contentID"/>" />
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="35%" align="right" class="text3">ID��</td>
    <td width="65%" class="text4"><bean:write name="vo" property="id"/></td>
  </tr>
	<tr>
    <td align="right" class="text3">����ID��</td>
    <td class="text4"><bean:write name="vo" property="contentID"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">�������ƣ�</td>
    <td class="text4"><bean:write name="vo" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ�����ݷ��ࣺ</td>
    <td class="text4"><bean:write name="vo" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">�ṩ�̣�</td>
    <td class="text4"><bean:write name="vo" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP��ҵ���룺</td>
    <td class="text4"><bean:write name="vo" property="icpCode"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ����룺</td>
    <td class="text4"><bean:write name="vo" property="icpServId"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">�ṩ��Χ��</td>
    <td class="text4"><logic:equal name="vo" property="servattr" value="G">ȫ��ҵ��</logic:equal><logic:equal name="vo" property="servattr" value="L">ʡ��ҵ��</logic:equal><td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݱ�ʶ��</td>
    <td class="text4"><bean:write name="vo" property="contenttag"/><td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݱ�ǩ��</td>
    <td class="text4"><bean:write name="vo" property="keywordsFormat"/><td>
  </tr>
  <tr>
    <td align="right" class="text3">����ʱ�䣺</td>
    <td class="text4"><bean:write name="vo" property="marketdate"/><td>
  </tr>
    <logic:iterate id="vo" indexId="ind" name="map"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	    <td align="right" class="text3"><bean:write name="vo" property="keydesc"/></td>
	    <td class="text4"><logic:equal value="1" name="vo" property="keyType"> <input name="<bean:write name="vo" property="keyname"/>" type="text" value="<bean:write name="vo" property="value"/>" size="60"> </logic:equal> 
			<logic:equal value="2" name="vo" property="keyType"> <input  type="file"  name="<bean:write name="vo" property="keyname"/>"   size="60"> &nbsp;��֧��png��ʽ<bean:write name="vo" property="value"/>	
			</logic:equal> 
			 <logic:equal value="3" name="vo" property="keyType"> <textarea  name="<bean:write name="vo" property="keyname"/>" rows="5" cols="40"><bean:write name="vo" property="value"/></textarea></logic:equal>
			 <logic:notEqual value="" name="vo" property="value">
      			<input type="checkbox" value="1" name="clear_<bean:write name='vo' property='keyname'/>"/>
      			��յ�ǰ��չ�ֶ�����
      		</logic:notEqual>
	    </td>
	  </tr>
	    </logic:iterate>
 
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><logic:notEmpty name="map"><input name="Submit" type="submit" class="input1" value="�޸�"></logic:notEmpty> <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="����"></td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
function resourceUpdate()
{
  if(confirm("��ȷ��Ҫ�����޸Ĳ�����"))
  {
    listForm.submit();
  }
  else
  {
  	return false;	
  }
}
//-->
</script>