<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
  
  <body>
  <table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
     <form action="blackinput.do?actionType=importfile" name="form1" method="post" enctype="multipart/form-data">
  <tr>
    <td width="20%" align="right" class="text3">
    	�������������ļ�:
    </td>
    <td colspan="2" class="text4">
    	<input type="file" name="dataFile">(��ѡ��.xls�����ļ�)
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    </td>
    <td colspan="2" class="text4">
    	<input type="submit" name="genLogo" value="�������������ļ�" onclick="return import_content()">
    	<b><font color=red>ע�⣺��ע��xls�ļ��ĸ�ʽ!</font></b>
	</td>
  </tr>
  </table> 
  </form>
  <script language="javascript">
	function import_content()
	{

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
	
		if(!confirm('ȷ��Ҫ����񵥺���������'))
		{
	  	 return false;
		}
		return true;
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
  </body>
</html>
