<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@page import="com.aspire.dotcard.syncData.dao.DataSyncDAO"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<%
 //ͬ����������Ӧ�ô�����Ϣ
    //    DataSyncDAO.getInstance().refreshContetExt();
       
//      �Զ������
        DataSyncDAO.getInstance().refreshIteMmark ();

%>


<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td>����λ�ã����ܹ�������----Ӧ�û����ͬ��</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr class="title1">
    <td align="center"><FONT class=large>ͬ��Ӧ�û��Ϣ���Զ����»���</FONT></td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr >
  	<td align="center" class="text5">
     ִ����ɣ���鿴��־
    </td>
  </tr>
</table>

<br>
<table width="95%"  border="0" align="center" cellspacing="1">


</table>
</body>
</html>
