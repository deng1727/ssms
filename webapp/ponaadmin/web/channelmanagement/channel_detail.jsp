<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO" %>
<%
   ChannelListVO vo = (ChannelListVO)request.getAttribute("channelContent");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>��ѯ����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function back(){
	window.history.back(-1);
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	            ��ѯ����
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	           ����ID��
	    </td>
	    <td width="75%" class="text4"><%=vo.getChannelId() %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	�������ͣ�
	    </td>
	    <td class="text4"><% if("0".equals(vo.getChannelType())){ %>
	                                         �ͻ���
	       <% }else if("1".equals(vo.getChannelType())){ %>
	                                         ���ݽӿ�
	       <% }else{  %>
	                                        ��ҳ
	       <%} %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	�������ƣ�
	    </td>
	    <td class="text4"><%=vo.getChannelName() %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	����ʱ�䣺
	    </td>
	    <td class="text4"><%=vo.getCreateDate() %>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	���������ƣ�
	    </td>
	    <td class="text4"><%=vo.getChannelsName() %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	����������
	    </td>
	    <td class="text4"><%=vo.getChannelDesc() %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	������ID��
	    </td>
	    <td class="text4"><%=vo.getChannelsId() %>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" onclick="back();" value="�����б�">
		  </td>
		</tr>
	</table>
</body>
</html>
