<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.common.Validator" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%
   String cateId=Validator.filter(request.getParameter("cateId"));
   String cgyPath=Validator.filter(request.getParameter("cgyPath"));
 %>
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script>
function isFileType(filename,type)
{
   var pos = filename.lastIndexOf(".");

   if(pos<0) return false;

   var fileType = filename.substr(pos+1);

   if(fileType.toUpperCase()!=type.toUpperCase()) return false;

   return true;
}

function import_content()
{
   //����ֱ�ӽ����ҳ�档
    if(form1.cateId.value=="")
    {
      return false;
    }
    
   var filePath = form1.dataFile.value;
   // alert(filePath);
   if(filePath == "" || filePath == null || filePath == "null")
   {
      alert("��ѡ��Ҫ�������ݵ��ļ���");
      return false;
    }
     

    if(!isFileType(filePath,"txt"))
    {
      alert("��ѡ��txt��ʽ�������ļ�!");
      return false;
    }
	//form1.commit();
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>���������ݵ���</FONT></td>
  </tr>
</table>
<br>

<form action="importBlack.do" name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" name="cateId" value="<%=cateId %>">
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="text5" >
    <td   align="right" width="25%">��ѡ��Ҫ�����txt�����ļ���</td>
    
    <td   align="left" width="75%"><input type="file" name="dataFile">
    </td>
  </tr>
  <tr class="text4"><td colspan="2" align="center">(��һ�п�ʼ����(yyyyMMdd)���ڶ��н�ֹ����(yyyyMMdd)�������б�ʾ�������ݵ�id(12λ����)������������(1��2)����29λ����20100526201006263000000006301)</td></tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr >
  	<td align="center">
      <input type="submit" class="input1" name="genLogo" value="ȷ��" onclick="return import_content()">
      <input type="button" class="input1"  value="����" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>
<br>
</body>
</html>
