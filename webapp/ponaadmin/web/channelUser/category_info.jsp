<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>
<%
	String root = request.getContextPath();
	String cgyPath=request.getAttribute("cgyPath")==null?"":(String)request.getAttribute("cgyPath");
	String deviceName=request.getAttribute("deviceName")==null?"":(String)request.getAttribute("deviceName");
	String platFormName=request.getAttribute("platFormName")==null?"":(String)request.getAttribute("platFormName");
	String cityName=request.getAttribute("cityName")==null?"":(String)request.getAttribute("cityName");
 %>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../ztree/js/jquery-1.4.4.min.js"></script>
</head>
<script language="JavaScript">
<!--
function addCategory()
{
  window.location.href = "<%=root%>/web/channelUser/categoryEdit.do?action=new&pCategoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>"+
                         "&ctype=<bean:write name="category" property="ctype"/>";
}

function modCategory()
{
  window.location.href = "<%=root%>/web/channelUser/categoryEdit.do?action=update&pCategoryID=<bean:write name="category" property="parentID"/>&categoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>"+
                         "&ctype=<bean:write name="category" property="ctype"/>";
}

function delCategory()
{
  if(confirm("<%=ResourceUtil.getResource("PUBLIC_DELETE_ALERT")%>"))
  {
    window.location.href = "<%=root%>/web/channelUser/categoryDel.do?pCategoryID=<bean:write name="category" property="parentID"/>&categoryID=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath%>";
    
  }
}
 
 

 
//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">�������ƣ� </td>
    <td width="71%" class="text4"><bean:write name="category" property="name"/></td>
  </tr>
  
  <tr>
    <td width="29%" align="right" class="text3">�����ŵ꣺ </td>
    <td width="71%" class="text4">
		<bean:write name="category" property="displayRelation"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����˵���� </td>
    <td class="text4">
      <textarea name="desc" rows="4" cols="50" readonly><bean:write name="category" property="desc"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">������ţ� </td>
    <td class="text4">
      <bean:write name="category" property="sortID"/>
    </td>
  </tr>
  
  <tr>
    <td align="right" class="text3">����URL�� </td>
    <td class="text4">
      <bean:write name="category" property="multiurl"/>
    </td>
  </tr>
  
   <tr>
    <td align="right" class="text3">���ܿ�ʼ/��Чʱ�䣺 </td>
    <td class="text4">
      <bean:write name="category" property="startDate"/>
    </td>
  </tr>
   <tr>
    <td align="right" class="text3">���ܽ���/ʧЧʱ�䣺 </td>
    <td class="text4">
      <bean:write name="category" property="endDate"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�Ƿ�֧�������� </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="othernet"> ֧�� </logic:equal>
     <logic:equal value="0" name="category" property="othernet"> ��֧�� </logic:equal>
    </td>
  </tr>
   <tr>
    <td align="right" class="text3">�Ƿ����Ż�չʾ�� </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="state"> �� </logic:equal>
     <logic:equal value="0" name="category" property="state"> �� </logic:equal>
    </td>
  </tr>
  <logic:equal value="11" name="category" property="ctype">
   <tr>
    <td width="29%" align="right" class="text3">����Ԥ��ͼ�� </td>
    <td width="71%" class="text4">
        <logic:notEmpty name="category" property="picURL">
		<img src="<bean:write name="category" property="picURL"/>logo4.png">
		</logic:notEmpty>
		<logic:empty name="category" property="picURL">
		��Ԥ��ͼ
		</logic:empty>
		
    </td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">����ͳ����Ϣ�� </td>
    <td width="71%" class="text4">
		<bean:write name="category" property="statistic"/>
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">�����Ƿ��ǻ��ͻ��ܣ� </td>
    <td class="text4">
     <logic:equal value="1" name="category" property="deviceCategory"> �� </logic:equal>
     <logic:equal value="0" name="category" property="deviceCategory"> �� </logic:equal>
    </td>
  </tr>
  <logic:equal value="1" name="category" property="deviceCategory">
  <tr>
    <td align="right" class="text3">���ܶ�Ӧ���ͣ� </td>
    <td class="text4">
		<%=deviceName %>
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">��������ƽ̨�� </td>
    <td class="text4">
		<%=platFormName %>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����������� </td>
    <td class="text4">
		<%=cityName %>
    </td>
  </tr>
 <logic:iterate id="vo" indexId="ind" name="keyResourceList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	   <td width="29%" align="right" class="text3"><bean:write name="vo" property="keydesc"/> </td>
    <td width="71%" class="text4">
    <logic:equal value="3" name="vo" property="keyType">
    <textarea name="desc" rows="4" cols="50" readonly>
    <bean:write name="vo" property="value"/>
    </textarea>
    </logic:equal>
    <logic:equal value="1" name="vo" property="keyType">
 
    <bean:write name="vo" property="value"/>

    </logic:equal><logic:equal value="2" name="vo" property="keyType">
   
    <bean:write name="vo" property="value"/>
    
    </logic:equal>
    </td> 
	  </tr>
	    </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
        <input name="button1" type="button" class="input1" value="����" onclick="return addCategory();">
        <input name="Submit" type="submit" class="input1" value="�޸�����" onclick="return modCategory();">
        <logic:notEqual name="category" property="ctype" value="1"><input name="Submit" type="submit" class="input1" value="ɾ��" onclick="return delCategory();"></logic:notEqual>
    </td>
  </tr>
</table>
</body>
</html>
