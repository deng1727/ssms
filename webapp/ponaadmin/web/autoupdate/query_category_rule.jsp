<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="java.util.HashMap"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>
<%@ page import="com.aspire.common.Validator" %>

<%
  String cid=Validator.filter(request.getParameter("cid")==null?"":request.getParameter("cid"));
  String ruleId = Validator.filter(request.getParameter("ruleId")==null?"":request.getParameter("ruleId"));
  String cName = Validator.filter(request.getParameter("cName")==null?"":request.getParameter("cName"));
  String ruleName = Validator.filter(request.getParameter("ruleName")==null?"":request.getParameter("ruleName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>货架策略规则列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">货架策略规则查询</td>
  </tr>
</table>

<form name="ContentForm" action="categoryRuleList.do" method="post" onsubmit="return sub();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  
  <tr>
    <td width="10%" align="right" class="text3">
    	货架内码：
    </td>
    <td width="40%" class="text4">
    	<input type="text" name="cid" value="<bean:write name="cid"/>"
    	onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" maxlength="30">
    </td>
    <td width="10%" align="right" class="text3">
    	规则ID：
    </td>
    <td width="40%" class="text4">  
	    <input type="text" name="ruleId" value="<bean:write name="ruleId"/>"
	    onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" maxlength="8">
    </td>
  </tr>
  <tr>
    <td width="10%" align="right" class="text3">
    	货架名称：
    </td>
    <td width="40%" class="text4">
    	<input type="text" name="cName" value="<bean:write name="cName"/>">
    </td>
    <td width="10%" align="right" class="text3">
    	规则名称：
    </td>
    <td width="40%" class="text4">
    	<input type="text" name="ruleName" value="<bean:write name="ruleName"/>">
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="text5">
    <td align="center">
      <input type="submit" class="input1" name="submit01" value="查询" >      
      <input type="button" class="input1" name="btn_reset" value="重置" onClick="clearForm(ContentForm);">
    </td>
  </tr>
</table>
</form>

  <table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     货架策略规则列表
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
<form name="form" action="" method="post" >
<input type="hidden" name="selectRadio"/>
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF" >
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1" width="8%">
	  	<input type="checkbox" value='' name="allSelect" onclick="selectAllCB(form,'selectRadio',this.checked,false);"/>
	  	全选
	  </td>
	  <td align="center" class="title1" width="8%">规则编码</td>
	  <td align="center" class="title1" width="8%">货架内码</td>
	  <td align="center" class="title1" width="20%">货架路径</td>
	  <td align="center" class="title1" width="15%">规则名称</td>
	  <td align="center" class="title1" width="15%">上次执行时间</td>
	  <td align="center" class="title1" width="15%">生效时间</td>
	  <td align="center" class="title1" width="15%">操作</td>
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.CategoryRuleVO">
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
				<input type="checkbox" value='<bean:write name="vo" property="cid"/>' name="selectRadio"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="ruleId"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="cid"/>
			</td>
			<td align="left" style="word-break:break-all;">
				<bean:write name="vo" property="cidPath"/>
			</td>
			<td align="center">
				<a href="categoryRuleView.do?action=view&ruleId=<bean:write name="vo" property="ruleId"/>">
					<bean:write name="vo" property="ruleName"/>
				</a>
			</td>
      		<td align="center"> 
      			<%=PublicUtil.getDateString(vo.getLastExcuteTime(),"yyyy-MM-dd HH:mm:ss")%>
      		</td>
      		<td align="center">
      			<%=PublicUtil.getDateString(vo.getEffectiveTime(),"yyyy-MM-dd HH:mm:ss")%>
      		</td>
     		<td align="center">
     			<input type="button" value="修改" onclick="edit('<bean:write name="vo" property="cid"/>','<%=PublicUtil.getDateString(vo.getLastExcuteTime(),"yyyy-MM-dd HH:mm:ss")%>','<%=PublicUtil.getDateString(vo.getEffectiveTime(),"yyyy-MM-dd HH:mm:ss")%>');"/>
				&nbsp;&nbsp;
				<input type="button" value="删除" onclick="del('<bean:write name="vo" property="cid"/>');"/> 
			</td>
		</tr>
	</logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
        params.put("cid",request.getParameter("cid"));
        params.put("ruleId",request.getParameter("ruleId"));
        params.put("cName",request.getParameter("cName"));
        params.put("ruleName",request.getParameter("ruleName"));
        %>
        <pager:pager name="PageResult" action="/web/autoupdate/categoryRuleList.do" params="<%=params%>">
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
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input type="button" value="执行" onclick="exe();"/> 
		</td>
	</tr>
</table>
</form>
</logic:notEmpty>
<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
	<tr bgcolor="#B8E2FC">
		<td align="center">
			<input name="button" type="button" value="新增货架策略规则" onClick="addCategoryRule();"/>
		</td>
	</tr>
</table>
<script language="javascript">
function sub()
{
	var cName = ContentForm.cName.value;
	var ruleName = ContentForm.ruleName.value;
	
	var reg = /^[ \w\-\u4e00-\u9fa5]+$/g
	
	if(trim(ruleName)!='')
    {
		if(!reg.test(ruleName))
	    {
			alert("规则名称只能包含汉字、英文字母、数字、横杠、下划线！请重新输入！");
			ContentForm.ruleName.focus();
			ContentForm.ruleName.select();
			return false;
	    }
	}
}

function del(id)
{
	if(!confirm('确定要删除这条货架策略规则吗？'))
	{
	   return false;
	}
	window.self.location.href="categoryRuleDellView.do?action=dell&id="+id;
}

function edit(id,lastExcuteTime,effectiveTime)
{
	window.self.location.href="categoryRuleEditView.do?action=editView&id="+id+"&lastExcuteTime="+lastExcuteTime+"&effectiveTime="+effectiveTime;
}

function exe()
{
	var obj = form.selectRadio;
	var temp = '';
	
	for(var i=0;i<obj.length; i++)
	{
		if(obj[i].checked==true)
		{
			temp = temp + obj[i].value + ',';
		}
	}
	
	temp = temp.substring(0,temp.lastIndexOf(","));
	
	if(temp=='')
	{
		alert("请选择要执行的货架策略规则！");
		return false;
	}
	
	if(!confirm('确定要执行选择的货架策略规则吗？'))
	{
	   return false;
	}
	window.self.location.href="categoryRuleEditView.do?action=exe&id="+temp;
}

function addCategoryRule()
{
	window.self.location.href="add_category_rule.jsp";
}

function addRule()
{
	window.self.location.href="add_rule.jsp";
}
</script>
</body>
</html>
