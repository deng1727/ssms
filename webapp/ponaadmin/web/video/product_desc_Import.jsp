<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market��Ƶ���ܹ���ϵͳ</title>
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
   var filePath = form1.dataFile.value;
   if(filePath == "" || filePath == null || filePath == "null")
   {
      alert("��ѡ��Ҫ�������ݵ��ļ���");
      return false;
    }
     
 
    if(!isFileType(filePath,"xls"))
    {
      alert("��ѡ��xls��ʽ�������ļ�!");
      return false;
    }
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>����λ�ã����ܹ�������----��Ƶ��Ʒ�ʷ�������Ϣ��������</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>��Ʒ�ʷ�������Ϣ��������</FONT></td>
  </tr>
</table>
<form action="../baseVideo/product.do" name="form1" method="post" enctype="multipart/form-data">
<table width="95%"  border="0" align="center" cellspacing="3">
<input type="hidden" name="perType" value="modifyDesc"/>

  <tr>
    <td class="text3"  align="right" width="20%">��ѡ��Ҫ�����excel�����ļ���</td>
    <td   align="left" width="80%">
    	<input type="file" name="dataFile"/>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
      <input type="submit" class="input1" name="genLogo" value="ȷ��" onclick="return import_content();">
      <input type="button" class="input1"  value="����" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>
<br>
<table width="95%"  border="0" align="center" cellspacing="1">

<tr><td><font color="red">*��ʾ�������ʽΪ ��ͨxls�ļ���ʽ����һ��Ϊ��ƷID,�ڶ���Ϊ�ʷ�������������300�����֣�</font><br/>  	
</td>
</tr>
</table>
</body>
</html>
