<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page import="com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String typeDesc = Validator.filter(request.getParameter("typeDesc")==null?"":request.getParameter("typeDesc"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String author = Validator.filter(request.getParameter("author")==null?"":request.getParameter("author"));
String source = Validator.filter(request.getParameter("source")==null?"":request.getParameter("source"));
String keywords = Validator.filter(request.getParameter("keywords")==null?"":request.getParameter("keywords"));
String isRecursive = Validator.filter(request.getParameter("isRecursive")==null?"true":request.getParameter("isRecursive"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));

String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
String backURL_enc = "";
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
//获取当前配置库列表
List cfgList=DBPersistencyCFG.getInstance().getOrderedSubType("nt:gcontent",true);
pageContext.setAttribute("cfgList",cfgList,pageContext.PAGE_SCOPE);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
<!--
function delContent()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("请先选择要删除的资源！");
    return false;
    
  }
  if(confirm("<%=ResourceUtil.getResource("PUBLIC_DELETE_ALERT")%>"))
  {
    contentForm.action="contentDel.do?categoryID=<bean:write name="category" property="id"/>";
    contentForm.submit();
  }
}

function checkForm(form)
{
  if(!checkContent(form.keywords,"REG_CHINESE_NUM_LERTER_COMMA"))
  {
    alert("关键字只能包含汉字、英文字母、数字和分割符逗号。");
    form.keywords.focus();
    return false;
  }
  
  return true;
  
}

function editTag(method)
{

	var tagSelectAll =document.getElementById("tagSelectAll");
	var tagValue = document.getElementById("tagValue");

	//标签内容合法性检查
	if(tagValue.value.length == 0)
	{
		alert("标签内容不能为空!");
		return false;
	}
	if(tagValue.value.length >1498)
	{
		alert("标签内容不能大于1498个字符!");
		return false;
	}
	if(!checkStr5(tagValue,"标签内容"))
	{
		return false;
	}
	
	var action = "contentTag.do?tagValue="+tagValue.value;
	var act_s ="";
	//判断是新增,还是删除
	if(method == "add")
	{
		act_s = "增加";
		action += "&action=add";
	}
	else
	{
		act_s = "删除";
		action += "&action=del";
	}
	//没有数据集
	if(!document.contentForm )
	{
	    alert("您没有选择需要"+act_s+"标签的内容!");
    	return false;
	}
	
	//判断是不是全选
	if(tagSelectAll.checked == true)
	{
		if(!confirm("您确认对全部内容"+act_s+"\""+tagValue.value+"\"标签?"))
    	{
      		return false;
    	}
		action += "&opt=all";
		//alert(action);
	}
	else
	{
		//若不是全选,则检查是否有选择内容
		//判断有没有最少选择一个
  		if(!haveChooseChk(contentForm,"dealContent"))
  		{
  			alert("您没有选择需要"+act_s+"标签的内容！");
 			return false;
  		}
		action += "&opt=select"
	}
	
	action += "&name=<%=name%>&spName=<%=spName%>&cateName=<%=cateName%>&icpCode=<%=icpCode%>&icpServId=<%=icpServId%>&type=<%=type%>&contentID=<%=contentID%>&isRecursive=<%=isRecursive%>&contentTag=<%=contentTag%>&tagLogic=<%=tagLogic%>&CurrPageNo=<bean:write name="PageResult" property="currentPageNo"/>";

	action += "&categoryID=<bean:write name="category" property="id"/>";
	contentForm.action= action;
    contentForm.submit();
}
function checkPageSize(field)
  {
    var pageSize=field.value;
    var pattern=/\D+/;
    if(pattern.test(pageSize))
    {
      alert("每页展示记录数的值必须是整数！");
      field.focus();
      return false;
    }
    var Page_MIN=<%=PageSizeConstants.Page_MIN%>;
    var Page_MAX=<%=PageSizeConstants.page_MAX%>;
    if(pageSize<Page_MIN)
    {
      alert("每页展示的记录数必须大于或者等于"+Page_MIN);
      field.focus();
      return false;
    }
    if(pageSize>Page_MAX)
    {
       alert("每页展示的记录数必须小于或者等于"+Page_MAX);
       field.focus();
       return false;
    }
    return true;
  }
//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<bean:write name="category" property="namePath"/></td>
  </tr>
</table>
<form name="contentListForm" action="contentList.do" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td colspan="4" align="center" class="title1">
      资源查询<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="name" value="<%=name%>"></td>
    <td width="18%" align="right" class="text3">提供商：
    </td>
    <td width="30%" class="text4"><input type="text" name="spName" value="<%=spName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">业务内容分类：
    </td>
    <td width="30%" class="text4"><input type="text" name="cateName" value="<%=cateName%>"></td>
    <td width="18%" align="right" class="text3">企业代码：
    </td>
    <td width="30%" class="text4"><input type="text" name="icpCode" value="<%=icpCode%>"></td>
 </tr>
  
 <tr>
    <td width="18%" align="right" class="text3">业务代码：
    </td>
    <td width="30%" class="text4"><input type="text" name="icpServId" value="<%=icpServId%>"></td>
    <td width="18%" align="right" class="text3">内容类型：
    </td>
    
    <td width="30%" class="text4">  
    <SELECT name=type> 
    <OPTION value="" ></OPTION>
    <logic:iterate id="nodeType" name="cfgList" type="com.aspire.ponaadmin.web.repository.persistency.db.NodeCFG">
    
    <OPTION value="<bean:write name="nodeType" property="type"/>" <logic:equal value="<%=type %>" name="nodeType" property="type">selected</logic:equal> ><bean:write name="nodeType" property="typeDesc"/></OPTION>
    </logic:iterate>              
    </SELECT>
    </td>
    
 </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">内容ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="contentID" value="<%=contentID%>"></td>
    <logic:notEqual name="category" property="name" value="未分类资源">
    	<td width="18%" align="right" class="text3">包含子分类： </td>
    	<td width="30%" class="text4">
    	<select name="isRecursive">
      		<option value="true" <%if(isRecursive.equals("true")){%>selected<%}%>>是</option>
      		<option value="false" <%if(isRecursive.equals("false")){%>selected<%}%>>否</option>
    	</select>
    </logic:notEqual>	
    <logic:equal name="category" property="name" value="未分类资源">
        <td width="18%" align="right" class="text3">
    	</td>
    	<td width="30%" class="text4"></td>
    </logic:equal>
  </tr>

  <tr>
    <td width="18%" align="right" class="text3">内容标签：</td>
    <td width="30%" class="text4">
    	<input type="text" name="contentTag" value="<%=contentTag%>">
    	<select name="tagLogic">
	    	<option value="AND" <%if(tagLogic.equals("AND")){%>selected<%}%>>AND</option>
	    	<option value="OR" <%if(tagLogic.equals("OR")){%>selected<%}%>>OR</option>
    	</select>
    </td>
    <td width="18%" align="right" class="text3">提供范围</td>
    <td width="30%" class="text4"><SELECT name=servAttr> 
    <OPTION value="" ></OPTION>
   <OPTION value="G" <%if(servAttr.equals("G")){%>selected<%}%> >全网</OPTION>
    <OPTION value="L" <%if(servAttr.equals("L")){%>selected<%}%> >省内</OPTION>
    </select>
    </td>
  </tr>  
      <tr>
    <td width="18%" align="right" class="text3">每页展示记录数：</td>
    <td width="30%" class="text4"><input type="text" name="pageSize" value="<%=pageSize%>" ></td>
    <td width="18%" align="right" class="text3"></td>
    <td width="30%" class="text4"></td>
 </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit" type="submit" class="input1" value="查询">
    <input name="reset" type="reset" class="input1" value="重置">
    <input name="tagEdit_b" type="button" class="input1" value="内容标签编辑" onclick="javascript:tagEdit.style.display='block';"></td>
  </tr>
</table>
</form>

<div id ="tagEdit" style="display:none;">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1" colspan="2">内容标签编辑</td>

  </tr>
<tr>
    <td align="center" class="text5" width="70%">
		需更新的标签内容:<input type="text" id="tagValue">&nbsp;&nbsp;<input type="checkbox" id="tagSelectAll">全选
	</td>
	<td align="left" class="text5" width="30%">	
		<input name="addTag" type="button" class="input1" value="新增" onclick="javascript: editTag('add');">
        <input name="delTag" type="button" class="input1" value="删除" onclick="javascript: editTag('del');">
        <input name="retTag" type="button" class="input1" value="返回" onclick="javascript: tagEdit.style.display='none';">
    </td>
</tr>
</table>
<br>
</div>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">资源内容管理</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <td align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
  </logic:empty>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <%
        HashMap params = new HashMap();
        params.put("name",name);
        params.put("author",author);
        params.put("source",source);
        params.put("keywords",keywords);
        params.put("isRecursive",isRecursive);
        params.put("categoryID",request.getParameter("categoryID"));
        params.put("type",type);
        params.put("spName",spName);
        params.put("cateName",cateName);
        params.put("icpCode",icpCode);
        params.put("icpServId",icpServId); 
        params.put("contentID",contentID);
        params.put("contentTag",contentTag);
        params.put("tagLogic",tagLogic);
        params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/resourcemgr/contentList.do" params="<%=params%>">
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
<%@ include file = "contentlist_include.jsp" %>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'dealContent',this.checked,false);">
      全部选中
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/resourcemgr/contentList.do" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="2"/>
        </pager:pager>

    </td>
  </tr>
</table>
</logic:notEmpty>
<logic:notEqual name="category" property="id" value="701">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
            <td align="center" class="text5">
            <logic:notEmpty name="PageResult" property="pageInfo">
			  <input name="buttonDel" type="button" class="input1" onClick="return delContent();" value="删除">
            </logic:notEmpty>
          </td>
        </tr>
</table>
</logic:notEqual>
</body>
</html>
