<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="java.util.HashMap"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架策略规则列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
   function into()
   {
		var ruleType = "<bean:write name="ruleType"/>";
		var intervalType = "<bean:write name="intervalType"/>";
		var select_rule = ContentForm.ruleType;
		var select_intervalType = ContentForm.intervalType;
		
		for(var i=0; i<select_rule.length; i++)
		{
			if(select_rule.options[i].value==ruleType)
			{
				select_rule.options[i].selected=true;
				break;
			}
		}
		
		for(var i=0; i<select_intervalType.length; i++)
		{
			if(select_intervalType.options[i].value==intervalType)
			{
				select_intervalType.options[i].selected=true;
				break;
			}
		}
	}
	
	function subToRuleView(ruleId,backId,backName,backRuleType,backIntervalType)
	{
		ContentFormRule.ruleId.value = ruleId;
		ContentFormRule.backId.value = backId;
		ContentFormRule.backName.value = backName;
		ContentFormRule.backRuleType.value = backRuleType;
		ContentFormRule.backIntervalType.value = backIntervalType;
		ContentFormRule.submit();
	}
	
</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" onload="into();">

<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">自动更新规则查询</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="ruleList.do" method="post">
  <tr>
    <td width="15%" align="right" class="text3">
    	规则ID：
    </td>
    <td width="35%" class="text4">
    	<input type="text" name="ruleId" value="<bean:write name="ruleId"/>"
    	onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" maxlength="30">
    </td>
    <td width="15%" align="right" class="text3">
    	规则名称：
    </td>
    <td width="35%" class="text4">  
	    <input type="text" name="ruleName" value="<bean:write name="ruleName"/>"/>
    </td>
  </tr>
  <tr>
    <td width="15%" align="right" class="text3">
    	规则类型：
    </td>
    <td width="35%" class="text4">
		<select name="ruleType">
			<option value="">全部</option>
			<option value="0"><%=CategoryUpdateConfig.getInstance().getShowValue("ruleType","0") %></option>
			<option value="1"><%=CategoryUpdateConfig.getInstance().getShowValue("ruleType","1") %></option>
			<option value="5"><%=CategoryUpdateConfig.getInstance().getShowValue("ruleType","5") %></option>
			<option value="6"><%=CategoryUpdateConfig.getInstance().getShowValue("ruleType","6") %></option>
		</select>
    </td>
    <td width="15%" align="right" class="text3">
    	执行时间间隔类型：
    </td>
    <td width="35%" class="text4">  
		<select name="intervalType">
			<option value="">全部</option>
			<option value="0"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","0") %></option>
			<option value="1"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","1") %></option>
			<option value="2"><%=CategoryUpdateConfig.getInstance().getShowValue("intervalType","2") %></option>
		</select>
    </td>
  </tr>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="查询" >      
      <input type="button" class="input1" name="btn_reset" value="重置" onClick="clearForm(ContentForm);">
    </td>
  </tr>
</table>
  </form>
</table>
  <table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     规则列表
    </td>
  </tr>
</table>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<form name="ContentFormRule" action="ruleView.do" method="post">
	<input type="hidden" name="action" value="editView"/>
	<input type="hidden" name="ruleId" value=""/>
	<input type="hidden" name="backId" value=""/>
	<input type="hidden" name="backName" value=""/>
	<input type="hidden" name="backRuleType" value=""/>
	<input type="hidden" name="backIntervalType" value=""/>

<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td width="10%" align="center" class="title1">规则编号</td>
	  <td width="10%" align="center" class="title1">规则名称</td>
	  <td width="10%" align="center" class="title1">规则类型</td>
	  <td width="10%" align="center" class="title1">执行时间间隔类型</td>
	  <td width="10%" align="center" class="title1">执行时间间隔</td>
	  <td width="10%" align="center" class="title1">在时间间隔内的执行日子</td>
	  <td width="10%" align="center" class="title1">随机上架因子</td>	  
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.rule.RuleVO">
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
			<td align="center" style="word-break:break-all;">
				<bean:write name="vo" property="ruleId"/>
			</td>
			<td align="center">
				<a href="javascript: subToRuleView('<bean:write name="vo" property="ruleId"/>','<bean:write name="ruleId"/>','<bean:write name="ruleId"/>',
				'<bean:write name="ruleType"/>','<bean:write name="intervalType"/>');">
					<bean:write name="vo" property="ruleName"/>
				</a>
			</td>
      		<td align="center">
      			<%=CategoryUpdateConfig.getInstance().getShowValue("ruleType",String.valueOf(vo.getRuleType())) %>
      		</td>
      		<td align="center">
      			<%=CategoryUpdateConfig.getInstance().getShowValue("intervalType",String.valueOf(vo.getIntervalType())) %>
      		</td>
     		<td align="center">
				<bean:write name="vo" property="excuteInterval"/>&nbsp;<logic:equal name="vo" property="intervalType" value="0">天</logic:equal>
				                                                       <logic:equal name="vo" property="intervalType" value="1">周</logic:equal>
				                                                       <logic:equal name="vo" property="intervalType" value="2">月</logic:equal>
			</td>
			<td align="center">
			    <%
      			if(vo.getIntervalType()==0)
      			{
      			    out.print("可忽视");
      			}
      			else if(vo.getIntervalType()==1)
      			{
      			  	out.print("周 "+vo.getExcuteTime()+" 执行");
      			}
      			else if(vo.getIntervalType()==2)
      			{
      			  	out.print("月中 "+vo.getExcuteTime()+" 号执行");
      			}
      			%>
      		</td>
     		<td align="center">
				<bean:write name="vo" property="randomFactor"/>
			</td>
		</tr>
	</logic:iterate>
</table>
</form>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("ruleId",request.getParameter("ruleId"));
        params.put("ruleName",request.getParameter("ruleName"));
        params.put("ruleType",request.getParameter("ruleType"));
        params.put("intervalType",request.getParameter("intervalType"));
        %>
        <pager:pager name="PageResult" action="/web/autoupdate/ruleList.do" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="新增规则" onClick="addRule();"/>
		</td>
	</tr>
</table>
<script language="javascript">
function addRule()
{
	window.self.location.href="add_rule.jsp";
}
</script>
</body>
</html>
