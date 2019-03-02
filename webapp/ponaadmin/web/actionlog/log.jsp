<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.web.actionlog.ActionLogVO" %>
<%@page import="java.util.HashMap" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%
String curDate = PublicUtil.getCurDateTime("yyyy-MM-dd");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
<!--
function checkForm()
{
  //key�Ϳ�ʼʱ�䲻��ͬʱΪ��
  if(trim(queryLogForm.key.value)=="" && trim(queryLogForm.startDate.value)=="")
  {
    alert("��ʼ���ں͹ؼ��ֲ��ܶ�Ϊ�գ�");
    queryLogForm.key.focus();
    return false;
  }

  //���key
  var key = queryLogForm.key.value;
  if(trim(key)!="" && (!checkIllegalChar(key,"'\"<>\\/")
        || getLength(trim(key))>60))
  {
    alert("�ؼ��ֲ��ܳ���60���ַ���һ������ռ�����ַ�����\n\n���Ҳ��ܰ���' \" < > \\ /  ���ַ���");
    queryLogForm.key.focus();
    return false;
  }

  //��ʼʱ�䲻�ܴ��ڽ���ʱ��
  if(trim(queryLogForm.endDate.value)!="" && (trim(queryLogForm.startDate.value) > trim(queryLogForm.endDate.value)))
  {
    alert("�������ڲ���С�ڿ�ʼ���ڣ�");
    return false;
  }
  queryLogForm.startDate.disabled = false;
  queryLogForm.endDate.disabled = false;
  return true;
}
-->
</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<bean:parameter id="key" name="key" value="" />
<bean:parameter id="startDate" name="startDate" value="" />
<bean:parameter id="endDate" name="endDate" value="" />
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">ϵͳ����----������־��ѯ</td>
  </tr>
</table>
  <form name="queryLogForm" action="queryLog.do?subSystem=ssms" method="post" onSubmit="return checkForm();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">

  <tr>
    <td align="center" bgcolor="#E9F0F8">��ѯ�ؼ��֣�
      <input type="text" name="key" value="<bean:write name="key" />">&nbsp;&nbsp;&nbsp;&nbsp;
      <logic:equal name="result" value="-1">
        ��������� <select name="result">
                  <option value="-1" selected>ȫ��</option>
                  <option value="1">�ɹ�</option>
			      <option value="0">ʧ��</option>
                </select> 
      </logic:equal>
      <logic:equal name="result" value="1">
        ��������� <select name="result">
                  <option value="-1">ȫ��</option>
                  <option value="1" selected>�ɹ�</option>
			      <option value="0">ʧ��</option>
                </select> 
      </logic:equal>
      <logic:equal name="result" value="0">
        ��������� <select name="result">
                  <option value="-1">ȫ��</option>
                  <option value="1">�ɹ�</option>
			      <option value="0" selected>ʧ��</option>
                </select> 
      </logic:equal>
     &nbsp;&nbsp;&nbsp;&nbsp; 
     ʱ�䣺��<input name="startDate" type="text" size="12" value="<bean:write name="startDate" />" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(queryLogForm.startDate);" align="absmiddle" style="cursor:hand;">
      &nbsp;
      ��<input name="endDate" type="text" size="12" value="<bean:write name="endDate" />" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(queryLogForm.endDate);" align="absmiddle" style="cursor:hand;">&nbsp;&nbsp;
    </td>
  </tr>
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="��ѯ">
      <input type="reset" class="input1" name="btn_reset" value="����">
    </td>
  </tr>

</table>
  </form>
<br>
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
  <tr bgcolor="#B8E2FC">
    <td width="4%" align="center">ID</td>
    <td width="7%" align="center">�û���</td>
    <td width="10%" align="center">��������</td>
    <td width="6%" align="center">�������</td>
    <td width="15%" align="center">����ʱ��</td>
  </tr>
    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.actionlog.ActionLogVO">
    <tr class="<%=ind.intValue()%2==0?"text4":"text3"%>">
    <td align="center"><a href="getDetail.do?subSystem=ssms&logID=<bean:write name="vo" property="logID"/>"><bean:write name="vo" property="logID"/></a></td>
    <td align="center"><bean:write name="vo" property="userID"/></td>
    <td align="center"><bean:write name="vo" property="actionType"/></td>
    <td align="center"><bean:write name="vo" property="displayActionResult"/></td>
    <td align="center"><bean:write name="vo" property="displayActionTime" /></td>
    </tr>
    </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
        <%
        HashMap params = new HashMap();
        params.put("key",request.getParameter("key"));
        params.put("startDate",request.getParameter("startDate"));
        params.put("endDate",request.getParameter("endDate"));
        params.put("result",request.getParameter("result"));
        %>
        <pager:pager name="PageResult" action="/web/actionlog/queryLog.do?subSystem=ssms" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>
<script language="javascript">
<!--
//��������Ĭ��Ϊϵͳ��ǰ����
if(trim(queryLogForm.endDate.value)=="")
{
  queryLogForm.endDate.value = "<%=curDate%>";
}
if(trim(queryLogForm.startDate.value)=="")
{
  queryLogForm.startDate.value = "<%=curDate%>";
}
-->
</script>
</body>
</html>
