<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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
    <td>����λ�ã����ܹ�������----Ӧ�û��������</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>�ۿ���Ϣ�����ϴ�</FONT></td>
  </tr>
</table>
<form action="contentExtImport.do" name="form1" method="post" enctype="multipart/form-data">
<table width="95%"  border="0" align="center" cellspacing="3">

<tr>
   
	   <td  class="text3" align="right" width="20%">�ۿ����ͣ�</td>
	   <td  align="left" width="80%">
	    <select name="type">
	    	<option value="1">�ۿ�</option>
	    	<option value="2">��ɱ</option>
	    	<option value="3">��ʱ���</option>
	    </select>
	    
    </td>
  </tr>
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

<tr><td><font color="red">*��ʾ��ģ���е����ڸ�ʽ��yyyy-MM-dd����2011-09-12�����ﲻ��д��2011-9-12����ɱʱ��βο���Χ09:00:00��11:00:00</font><br/>
�ɹ��ο���ģ�����£�<br/>
<a href="./download/File_ZK.xls">�ۿ������ϴ�ģ��</a><br/>
    	<a href="./download/File_MS.xls">��ɱ�����ϴ�ģ��</a><br/>
    	<a href="./download/File_XSMF.xls">��ʱ��������ϴ�ģ��</a><br/>
    
    	
 Ϊ������ѡ������ͺ��ϴ����ļ�����������ѡ��������<br/>
 
 ���ǰ��ۿۡ���ɱ����ʱ��ѣ��ֱ�涨��_ZK.xls ��_MS.xls��_XSMF.xls  <br/>
 
 Ŀǰ����֧�������޸ġ�
    	
</td>
</tr>
</table>
</body>
</html>
