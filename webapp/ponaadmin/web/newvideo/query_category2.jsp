<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.dotcard.basevideosync.vo.POMSCategoryQueryVO" %>
<% POMSCategoryQueryVO pcq=(POMSCategoryQueryVO)request.getAttribute("POMSCategoryQueryVO");%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">POMS货架信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="29%" align="right" class="text3">货架路径： </td>
    <td width="71%" class="text4"><%=pcq.getPath()%></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架名称： </td>
    <td width="71%" class="text4"><%=pcq.getCname()%></td>
  </tr>
  <tr>
    <td width="29%" align="right" class="text3">货架ID： </td>
    <td width="71%" class="text4"><%=pcq.getCategoryID()%></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述： </td>
    <td class="text4">
		<textarea name="desc" rows="4" cols="50" readonly><%=pcq.getcDesc()%></textarea>
    </td>
  </tr>
   <tr>
    <td width="29%" align="right" class="text3">货架图片： </td>
    <td width="71%" class="text4"><%= pcq.getPic()%></td>
  </tr>
  <tr>
    <td align="right" class="text3">货架排序：</td>
    <td class="text4">
		<%=pcq.getSortID() %>
    </td>
  </tr>
	 <tr>
    <td align="right" class="text3">审批状态：</td>
    <td class="text4">
    	<%="0".equals(pcq
						 .getVideo_status()) ? "编辑" : "1"
						 .equals(pcq
						 .getVideo_status()) ? "已发布" : "2"
						 .equals(pcq
						 .getVideo_status()) ? "待审核" : "3"
						 .equals(pcq
						 .getVideo_status()) ? "不通过" : ""%>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<tr>
    <td align="center" class="text5">
<input name="button1" type="button" class="input1" value="关闭" onclick="window.close();">
</td>
  </tr>
</table>
</body>
</html>
