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

function importData()
{
	form1.perType.value="importData";
	
	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("��ѡ��Ҫ�������ݵ��ļ���");
		return false;
    }

    if(!isFileType(filePath,"xls")&&!isFileType(filePath,"xlsx"))
    {
		alert("��ѡ��xls��xlsx��ʽ�������ļ�!");
		return false;
    }
	form1.submit();
}

function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>����λ�ã��˹���Ԥ����----�񵥺�����������������</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>�񵥺�������������</FONT></td>
  </tr>
</table>

<form action="androidBlackListImport.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	��ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">
	    </td>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
      <input type="submit" class="input1" name="genLogo" value="ȷ��" onclick="return importData();">
      <input type="button" class="input1"  value="����" onclick="window.history.go(-1)">
    </td>
  </tr>
</table>
</form>

<br>
<table width="95%"  border="0" align="center" cellspacing="1">

<tr><td><font color="red">*��ʾ�������ļ�Ϊ����Ϊ.xlsx����.xls��ʽ��excel�ļ���
							��һ��Ϊ����ID���ڶ���Ϊ�������ƣ�������Ϊ�����У�add����del��</font><br/>
         <font color="red">*��ǩ��������д:add(���)����del(ɾ��) </font>
   	
</td>
</tr>
</table>
</body>
</html>
