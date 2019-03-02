<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>

<%
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String trueContentID = Validator.filter(request.getParameter("trueContentID")==null?"":request.getParameter("trueContentID"));

String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;
String backURL = request.getContextPath()+ "/web/channelUser/cgyNotContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;
backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag 
                  + "&tagLogic=" + tagLogic+"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;

String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
backURL=backURL+"&pageSize="+pageSize;
String backURL_enc = java.net.URLEncoder.encode(backURL);
//获取当前配置库列表
  List contentTypeList=CategoryUpdateConfig.getInstance().getNodeValueList("contentType");
  pageContext.setAttribute("contentTypeList", contentTypeList, PageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>添加内容到货架</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
<!--
function addContent()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("请先选择要添加的内容！");
    return false;
    
  }
  contentForm.action="<%=request.getContextPath()%>/web/channelUser/cgyContentAdd.do?isClose=true&categoryID=<bean:write name="category" property="id"/>";
  
  contentForm.submit();
}

function addContentToCgy()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("请先选择要添加到货架的内容！");
    return false;
    
  }
  contentForm.action="<%=request.getContextPath()%>/web/channelUser/contentAddToCgy.do";
  contentForm.submit();
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
    //
    contentListForm.querySubmit.disabled=true; //结果没有出来不允许再次提交,防止重复对此提交。
    return true;
  }
  

//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="contentListForm" action="<%=request.getContextPath()%>/web/channelUser/cgyNotContentList.do?subSystem=ssms" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
  <tr>
    <td colspan="4" align="center" class="title1">
      内容查询<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
            <input type="hidden" name="cgyPath" value="<%=cgyPath  %>">
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
    	<logic:iterate id="contentType" name="contentTypeList" type="com.aspire.ponaadmin.web.category.RecordVO" >
	    	<option value="<bean:write name="contentType" property="key"/>" 
	    		<logic:equal value="<%=type%>" name="contentType" property="key">selected</logic:equal>>
	    		<bean:write name="contentType" property="value"/>
	    	</option>
	    </logic:iterate>             
    </SELECT>
    </td>
    
 </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="contentID" value="<%=contentID%>"></td>
    <td width="18%" align="right" class="text3">内容标签:
    </td>
    <td width="30%" class="text4">
    <input type="text" name="contentTag" value="<%=contentTag%>">
    	<select name="tagLogic">
	    	<option value="AND" <%if(tagLogic.equals("AND")){%>selected<%}%>>AND</option>
	    	<option value="OR" <%if(tagLogic.equals("OR")){%>selected<%}%>>OR</option>
    	</select>
    </td>
 </tr>  
<tr>
  
    <td width="18%" align="right" class="text3">手机型号：</td>
    <td width="30%" class="text4"><input type="text" name="deviceName" value="<%=deviceName %>"></td>
    <td width="18%" align="right" class="text3">提供范围</td>
    <td width="30%" class="text4"><SELECT name=servAttr> 
    <OPTION value="" ></OPTION>
    <OPTION value="G" <%if(servAttr.equals("G")){%>selected<%}%> >全网</OPTION>
    <OPTION value="L" <%if(servAttr.equals("L")){%>selected<%}%> >省内</OPTION>
    </select>
    </td>
 </tr>
     <tr>
    <td width="18%" align="right" class="text3">内容ID：</td>
    <td width="30%" class="text4"><input type="text" name="trueContentID" value="<%=trueContentID%>" ></td>
    <td width="18%" align="right" class="text3">每页展示记录数：</td>
    <td width="30%" class="text4"><input type="text" name="pageSize" value="<%=pageSize%>" ></td>

 </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5"><input name="querySubmit" type="submit" class="input1" value="查询">
    <input name="reset" type="reset" class="input1" value="重置"></td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">货架栏目管理----添加内容</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <logic:equal value="0" name="exeType" >
          <td align="center"><font color="#ff0000">请选择条件查询</font></td>
          </logic:equal>
      <logic:notEqual value="0" name="exeType" >
          <td align="center"><font color="#ff0000">没有找到任何记录。</font></td>
          </logic:notEqual>
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
       // params.put("author",author);
        //params.put("source",source);
       // params.put("keywords",keywords);                       
        params.put("categoryID",categoryID);
        //params.put("keywordid",keywordid);
        params.put("type",type);
       // params.put("keywordid",keywordid);
        params.put("spName",spName);
        params.put("cateName",cateName);
        params.put("icpCode",icpCode);
        params.put("icpServId",icpServId);      
        params.put("contentID",contentID);  
        params.put("trueContentID",trueContentID);
        params.put("contentTag",contentTag);
        params.put("tagLogic",tagLogic);
        params.put("pageSize",pageSize);
        params.put("deviceName",deviceName);
        params.put("cgyPath",cgyPath);
        %>
        <pager:pager name="PageResult" form="contentListForm" action="/web/channelUser/cgyNotContentList.do?subSystem=ssms" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">选中</td>
	<td width="10%" align="center" class="title1">ID</td>
    <td width="13%" align="center" class="title1">名称</td>
    <td width="10%" align="center" class="title1">内容ID</td>
    <td width="12%" align="center" class="title1">提供商</td>
    <td width="10%" align="center" class="title1">内容出处</td>
    <td width="15%" align="center" class="title1">内容标签</td>
    <td width="5%" align="center" class="title1">内容类型</td>
    <td width="5%" align="center" class="title1">资费</td>
    <td width="5%" align="center" class="title1">提供范围</td>
    <td width="10%" align="center" class="title1">上线时间</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <input type="hidden" name="backURL" value="<%=backURL %>" >
  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.gcontent.GContent">
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
    <td align="center"><input type="checkbox" name="dealContent" value="<bean:write name="content" property="id"/>"></td>
    <td align="center"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>&backURL=<%=backURL_enc%>"><bean:write name="content" property="id"/></a></td>
    <td align="center" style="word-break:break-all;"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>&backURL=<%=backURL_enc%>"><bean:write name="content" property="name"/></a></td>
   
   <td align="center" style="word-break:break-all"><bean:write name="content" property="contentID"/></td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="spName"/></td>
    <td align="center" style="word-break:break-all;">
       	<logic:equal name="content" property="subType" value="1">
	        普通应用
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="2">
	        Widget
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="3">
	        ZCOM
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="4">
	        FMM
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="5">
	        JIL
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="6">
	        MM创业大赛
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="7">
	        孵化应用
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="21">
	        定制App
	    </logic:equal>
	</td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="keywordsDesc"/></td>
    <td align="center"><bean:write name="content" property="typeDesc"/></td>
    <td align="center" style="word-break:break-all"><bean:write name="content" property="advertPic"/>分</td>
    <td align="center" style="word-break:break-all">
                                  <logic:equal value="G" name="content" property="servAttr">全网</logic:equal>
                                  <logic:equal value="L" name="content" property="servAttr">省内</logic:equal>
                      </td>
    <td align="center"><bean:write name="content" property="marketDate"/></td>
  </tr>
  </logic:iterate>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'dealContent',this.checked,false);">
      全部选中
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" form="contentListForm" action="/web/channelUser/cgyNotContentList.do?subSystem=ssms" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">&nbsp;
            <logic:notEqual name="category" property="name" value="未分类资源">
			   <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonAdd" type="button" class="input1"  onClick="return addContent();" value="确定"></logic:notEmpty><input name="btn_close" type="button" class="input1" onClick="window.close();" value="关闭窗口">
			</logic:notEqual>
			
            <logic:notEmpty name="PageResult" property="pageInfo">
                <logic:equal name="category" property="name" value="未分类资源"><input name="buttonAdd" type="button" class="input1"  onClick="return addContentToCgy();" value="添加到分类"></logic:equal>
            </logic:notEmpty>
          </td>
        </tr>
</table>
</body>
</html>
