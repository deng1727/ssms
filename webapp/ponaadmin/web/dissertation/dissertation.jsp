<%@ page contentType="text/html; charset=gb2312" %>
<jsp:directive.page import="com.aspire.ponaadmin.web.util.StringTool"/>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@page import="java.util.List" %>
<%@page import="com.aspire.ponaadmin.web.actionlog.ActionLogVO" %>
<%@page import="java.util.HashMap" %>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil" %>
<%
String curDate = PublicUtil.getCurDateTime("yyyy-MM-dd");
Object dissName = request.getAttribute("dissName")==null?"":request.getAttribute("dissName");
Object status = request.getAttribute("status")==null?"":request.getAttribute("status");
Object tag = request.getAttribute("tag")==null?"":request.getAttribute("tag");
Object type = request.getAttribute("type")==null?"":request.getAttribute("type");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>

</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
<bean:parameter id="startDate" name="startDate" value="" />
<bean:parameter id="endDate" name="endDate" value="" />
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">ר�����----ר����Ϣ��ѯ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <html:form  action="web/dissertation/queryDiss" method="post">
  <tr>
    <td align="center" bgcolor="#E9F0F8">
    ר������:
    <html:text property="dissName"></html:text>
    &nbsp;״̬:
      <html:select property="status" >
       <html:option value="2">ȫ��</html:option>
       <html:option value="1">��Ч</html:option>
       <html:option value="0">δ��Ч</html:option>
       <html:option value="-1">����</html:option>
      </html:select>
    &nbsp;��ǩ:
    <html:text property="tag"></html:text>
     &nbsp;����:
      <html:select property="type">
       <html:option value="all">ȫ��</html:option>
       <html:option value="Theme">����</html:option>
       <html:option value="Game">��Ϸ</html:option>
       <html:option value="Soft">���</html:option>
      </html:select>
    </td>
  </tr>
  <tr class="text5">
    <td align="center">
	<html:submit styleClass="input1"  > �� ѯ </html:submit>
      <html:reset styleClass="input1" > �� �� </html:reset>
      <html:button property="" styleClass="input1" onclick="addDiss()">�½�ר��</html:button>
      <script language="javascript">
function addDiss()
{
	window.self.location.href="add_diss.jsp";
}
</script>
    </td>
  </tr>
  </html:form>
</table>
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
    <td width="8%" align="center">ר����</td>
    <td width="10%" align="center">�����Ż�</td>
    <td width="10%" align="center">ר������</td>
    <td width="15%" align="center">��Ч��</td>
    <td width="10%" align="center">��ǩ</td>
    <td width="8%" align="center">����</td>
    <td width="5%" align="center">״̬</td>
    <td width="10%" align="center">����</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.dissertation.DisserVo">
    <tr class="<%=ind.intValue()%2==0?"text4":"text3"%>">
    <td align="center"><a href="addDiss.do?param=detail&dissId=<%=vo.getDissId() %>"><%=vo.getDissId() %></a></td>
    <td align="center"><%=vo.getShowRelation() %></td>
    <td align="center"><a href="addDiss.do?param=detail&dissId=<%=vo.getDissId() %>"><%=StringTool.subStr(5,vo.getDissName()) %></a></td>
    <td align="center"><bean:write name="vo" property="startDate"/>&nbsp;&nbsp;��&nbsp;&nbsp;<bean:write name="vo" property="endDate"/></td>
    <td align="center"><%=StringTool.showKeywords(5,vo.getKeywords(),';')  %></td>
    <td align="center"><bean:write name="vo" property="type"/></td>
    <td align="center"><%=vo.showStatus() %></td>
     <td align="center">
     	<input type="button" value="�޸�" onclick="location.href='addDiss.do?param=detail&dissId=<%=vo.getDissId() %>'" />
		&nbsp;&nbsp;
		<input type="button" value="ɾ��" onclick="if(confirm('ȷ��ɾ���ü�¼��?'))location.href='addDiss.do?param=del&dissId=<%=vo.getDissId() %>'"/>
     </td>
    </tr>
    </logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
        <%
        HashMap params = new HashMap();
        params.put("dissName",dissName);
        params.put("status",status);
        params.put("tag",tag);
        params.put("type",type);
        %>
        <pager:pager name="PageResult" action="/web/dissertation/queryDiss.do" params="<%=params%>">
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
</body>
</html>
