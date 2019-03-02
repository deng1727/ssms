<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@page import="java.util.List"%>
<%@ page import="com.aspire.common.Validator" %>
<%
String categoryID = Validator.filter(request.getParameter("categoryID"));
List appCateNameList_1=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_1");
List appCateNameList_2=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_2");
List appCateNameList_3=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_3");
pageContext.setAttribute("appCateNameList_1", appCateNameList_1, PageContext.PAGE_SCOPE);
pageContext.setAttribute("appCateNameList_2", appCateNameList_2, PageContext.PAGE_SCOPE);
pageContext.setAttribute("appCateNameList_3", appCateNameList_3, PageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>同步策略管理</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self"></head>
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">

function checkForm()
{
  var tag = eval(addTacticForm.contentTag).value;
  var relation = eval(addTacticForm.tagRelation).value;
  if( (getLength(tag) == 0 && relation !=0) || (getLength(tag) != 0 && relation ==0) ) 
  {
  	alert("请同时给标签和标签关系赋值！");
  	return false;
  }
  
  if(!(checkContent1(addTacticForm.contentTag)))
  {
    alert("标签只能包含汉字、英文字母、数字和分隔符分号。");
    addTacticForm.contentTag.focus();
    return false;
  }
  
  if(!(checkContent(addTacticForm.appCateName,"REG_CHINESE_NUM_LERTER_UNDERLINE")))
  {
    alert("应用分类名称只能包含汉字、英文字母、数字和下划线！");
    addTacticForm.appCateName.focus();
    return false;
  }
  
  return true;  
}

function exeTactic()
{
	addTacticForm.action = "tactic.do?subSystem=ssms&action=exeTactic";
	addTacticForm.submit();
}


function modify(id)
{
	document.location.href="tactic.do?subSystem=ssms&action=showTactic&id="+id;
}

function del(id)
{
	if(confirm("您确定要删除这条记录吗？"))
	{
		document.location.href="tactic.do?subSystem=ssms&action=delete&id="+id + "&categoryID=" + <%=categoryID%>;
	}
}

function checkContent1(field)
{
    var value = eval(field).value;
    //如果为空，则不做内容检验
    if( getLength(value) == 0) 
    {
    	return true;
    }
    var reg = /^[a-zA-Z0-9;\u4e00-\u9fa5]+$/g 
    return reg.test(value);
}

function init()
{
	<logic:empty name="tacticList">
		document.getElementById('allExt').disabled = true;
	</logic:empty>
	
	var selectHtml = '<SELECT name="appCateName">';
	<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
		selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
					+'<bean:write name="appCateName" property="value"/>'
					+'</option>';
	</logic:iterate>
	<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
		selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
					+'<bean:write name="appCateName" property="value"/>'
					+'</option>';
	</logic:iterate>
   	<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
		selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
					+'<bean:write name="appCateName" property="value"/>'
					+'</option>';				
	</logic:iterate>
	selectHtml = selectHtml + '</SELECT>'
	document.getElementById('appCateName').innerHTML = selectHtml;
}

function selectAppCate(obj)
{
	var selectHtml = '<SELECT name="appCateName">';
	
	for(var i=0; i<obj.options.length; i++)
	{
		if(obj.options[i].selected == true)
		{
			if(obj.options[i].value == 'all')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
		    	<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appSoftWare')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appTheme')
			{
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appGame')
			{
				<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
		}
	}
	selectHtml = selectHtml + '</SELECT>'
	document.getElementById('appCateName').innerHTML = selectHtml;
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="init();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<bean:write name="category" property="namePath"/></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="addTacticForm" action="tactic.do?subSystem=ssms&action=add" method="post" onSubmit="return checkForm();">
  <input type = "hidden" name = "categoryID" value ="<%=categoryID%>">
  <input type = "hidden" name = "umFlag" value ="0">
  <input type = "hidden" name = "contentTag" value ="">
  <input type = "hidden" name = "tagRelation" value ="0">
  <tr>
    <td colspan="6" align="center" class="title1">
      添加内容同步策略<input type="hidden" name="categoryID" value="<%=categoryID%>">
    </td>
  </tr>  
 <tr>
    <td width="20%" align="right" class="text3">内容类型：</td>    
    <td width="20%" class="text4">  
	    <SELECT name=contentType onchange="selectAppCate(this)"> 
		    <OPTION value="all" selected>全部</OPTION>
		    <OPTION value="nt:gcontent:appGame">游戏</OPTION>
		    <OPTION value="nt:gcontent:appTheme">主题</OPTION>            
		    <OPTION value="nt:gcontent:appSoftWare">软件</OPTION>   
	    </SELECT>
    </td>
    <td width="20%" align="right" class="text3">应用分类: </td>
    <td id="appCateName" width="40%" class="text4">
    </td>
 </tr>
 <tr>
    <td align="center" class="text3" colspan="5"><input name="Submit" type="submit" class="input1" value="添加">
 </tr>
</form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="title1">
    <input id="allExt" type="button" onClick="exeTactic();" class="input1" value="全量手动执行内容同步策略"/>
    </td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">内容同步策略</td>
  </tr>
  <logic:empty name="tacticList">
      <tr>
          <td align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
  </logic:empty>
</table>
<logic:notEmpty name="tacticList">
<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="30%" align="center" class="title1">内容类型</td>
	<td width="30%" align="center" class="title1">应用分类</td>
    <td width="15%" align="center" class="title1">修改</td>
    <td width="15%" align="center" class="title1">删除</td>
  </tr>
  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" name="tacticList" type="com.aspire.dotcard.syncData.tactic.TacticVO">
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
    <td align="center"><bean:write name="vo" property="showContentType"/></td>
    <td align="center"><bean:write name="vo" property="appCateName"/></td>
    <td align="center"><input name="button1" type="button" class="input1" value="修改" onClick="modify(<bean:write name='vo' property='id'/>);"></td>
    <td align="center"><input name="button2" type="button" class="input1" value="删除" onClick="del(<bean:write name='vo' property='id'/>);"></td>
  </tr>
  </logic:iterate>
</table>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center"><font color="#ff0000">建议：对同步策略条件操作后，请手动执行货架全量同步策略。</font></td>
	</tr>
</table>

</body>
</html>
