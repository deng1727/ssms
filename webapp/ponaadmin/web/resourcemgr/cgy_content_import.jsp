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
   String menuStatus=Validator.filter(request.getParameter("menuStatus"));
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
    //alert(filePath);
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
	//form1.commit();
	return true;
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>��ǰ����λ�ã�<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>�������ݵ���</FONT></td>
  </tr>
</table>
<br>

<form action="categoryImport.do?action=import&cgyPath=<%=cgyPath %>" name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" name="cateId" value="<%=cateId %>">
<input type="hidden" name="menuStatus" value="<%=menuStatus %>">
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr>
    <td   align="left" width="25%">��ѡ��Ҫ�����excel�����ļ���</td>
    <td   align="left" width="75%">
    	<input type="file" name="dataFile">(����һ�б�ʾ��ţ��������У��ڶ��б�ʾ����ID�����磺300XXXXXXXX)
    	
    </td>
  </tr>
   <tr>
    <td   align="left" colspan="2">
    <b>�������ڸû�����������Ʒ�������EXCEL��2003�汾����2007�汾������Ϊ׼</b><br/>
    <b><font color="red">ȫ�����¼�ԭ�иû�����ȫ����Ʒ�����EXCEL��2003�汾����2007�汾��ָ����Ʒ���û�����</font></b>
    
    </td>
  </tr>
  <tr>
    <td   align="left" colspan="2">

	    <b>�Ƿ��ǽ�������Ӧ��:</b>
	    <select name="isSyn">
	    	<option value="0">��</option>
	    	<option value="1">��</option>
	    </select>
	    
	    
	        	<b>��������</b>
        <select name="addType">
            <option value="ALL">ȫ��</option>
	    	<option value="ADD">����</option>
	    </select>
    </td>
  </tr>
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
