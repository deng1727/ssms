<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%
    String tmpStyle = "text5";
    Object unused = request.getAttribute("unused");
    Object used = request.getAttribute("used");
    Object total = request.getAttribute("total");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�������б�</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

function apply()
{
	var total = <%=total %>;
	var unused = <%=unused %>;
	if(parseInt(total) == 0 || parseInt(unused) == 0){
		alert("����������������꣬����������������");
		return;
	}
	refForm.submit();
}
function edit(){
	refForm.perType.value = 'edit';
	var bool = true;
	var radio = document.getElementsByName("radio");
	  for(var i = 0;i < radio.length;i++){
			if(radio[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("��ѡ����Ҫ�༭������");
			return;
		}
	refForm.submit();
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="1" style="margin-top:20px;margin-bottom:20px;border:1px solid #C5ECF3;">
    <tr>
    <td width="1%"></td>
    <td width="99%">
            <br/>
            ������������<%=total %>��������Ŀǰ������ <%=used %>�������������� <%=unused %>����<br/><br/>
    </td>
    </tr>
    
</table>
<form name="refForm" action="channelInfo.do" method="post" >
<input name="perType" type="hidden" id="perType" value="add">
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>�������б�</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
     <td width="3%" align="center" class="title1"></td>
    <td width="22%" align="center" class="title1">����ID</td>     
    <td width="25%" align="center" class="title1">��������</td>
    <td width="25%" align="center" class="title1">��������</td> 
    <td width="25%" align="center" class="title1">����ʱ��</td> 
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.webpps.mychannel.vo.MyChannelVO">
	    <%
		if("text5".equals(tmpStyle))
	  	{
			tmpStyle = "text4";
	  	}
	  	else
	  	{
	  		tmpStyle = "text5";
	  	}
		%>
		<tr class=<%=tmpStyle%>>
		 <td align="center">
      			<input type="radio" name="radio" value="<bean:write name='vo' property='channelId' />">
      		</td>
		<td align="center">
      			<a target="_self" href="channelInfo.do?perType=detail&channelId=<bean:write name='vo' property='channelId' />"/><bean:write name="vo" property="channelId"/></a>
      	</td>
      	<td align="center">
      			    <logic:equal value="0" name="vo" property="channelType">�ͻ���</logic:equal>
      			    <logic:equal value="1" name="vo" property="channelType">���ݽӿ�</logic:equal>
      			    <logic:equal value="2" name="vo" property="channelType">��ҳ</logic:equal>
      		</td>
      	<td align="center">
      			<bean:write name="vo" property="channelName"/>
      		</td>
      	<td align="center">
      			<bean:write name="vo" property="createDate"/>
      		</td>
		</tr>
  </logic:iterate>
</table>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
		<input name="buttonAdd" type="button" onClick="apply();" value="��  ��">
		<logic:notEmpty name="PageResult" property="pageInfo">
		<input name="buttonEdit" type="button" onClick="edit();" value="��  ��">
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
</body>
</html>
