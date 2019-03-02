<%@ page contentType="text/html; charset=gbk" %>
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
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<%@ page import="com.aspire.common.Validator" %>


<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String typeDesc = Validator.filter(request.getParameter("typeDesc")==null?"":request.getParameter("typeDesc"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String trueContentID = Validator.filter(request.getParameter("trueContentID")==null?"":request.getParameter("trueContentID"));

String platform=Validator.filter(request.getParameter("platform")==null?"":request.getParameter("platform"));

String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String backURL = request.getContextPath()+"/web/channelUser/cgyContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;

String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;

backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag + "&tagLogic=" + tagLogic
                  +"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;
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
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">
<!--
function addContent()
{
  var isReflesh=window.showModalDialog("<%=request.getContextPath()%>/web/channelUser/cgyNotContentList.do?subSystem=ssms&exeType=0&cgyPath=<%=cgyPath%>&categoryID=<bean:write name="category" property="id"/>&isClose=true","modal","center:yes;dialogWidth:700px;dialogHeight:800px;resizable: yes; help: no; status: yes; scroll: auto; ");
  if(isReflesh)
  {
    window.location.reload();
  }
  
}
function tactic()
{
	contentListForm.action="<%=request.getContextPath()%>/web/channelUser/tactic.do?subSystem=ssms";
    contentListForm.submit();
}
function removeContent()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("请先选择要移除的内容！");
    return false;
    
  }
  if(confirm("<%=ResourceUtil.getResource("PUBLIC_REMOVE_ALERT")%>"))
  {
    contentForm.action="<%=request.getContextPath()%>/web/channelUser/cgyContentRemove.do?categoryID=<bean:write name="category" property="id"/>";
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

function mod_click(taxis)
{
  if(taxis.value=="")
  { 
   alert("请输入排序序号");
   return false;
  }
  if(!JudgeReal2(taxis,"排序序号",9))
  {
	return false;
  }
	
  if((taxis.value < -9999)||(taxis.value >9999))
  {
     alert("排序序号只能在-9999至9999之间！");
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
    return true;
  }
  function SetSortIDS()
  {
   var i=0;var sortid=0;
   var changedValues="";
   var isFirst=true;
   
   var Taxis=document.getElementsByName("Taxis");
   var dealContent=document.getElementsByName("dealContent");

   //debugger；
    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.ReferenceNode">
      sortid=<bean:write name="vo" property="sortID"/>;
      if(!mod_click(Taxis[i]))
      {
        Taxis[i].select();
        Taxis[i].focus();
        return false;
      }
      if(sortid!=Taxis[i].value)
      {
        if(isFirst)
       {
        changedValues=changedValues+dealContent[i].value+"#"+Taxis[i].value;
        isFirst=false;
       }else
       {
         changedValues+=":"+dealContent[i].value+"#"+Taxis[i].value;
       }
      }

      i++;
     </logic:iterate>
     if(changedValues=="")
     {
       alert("请修改至少一个排序号");
     }else
     {
       contentForm.changedValues.value=changedValues;
       contentForm.action="<%=request.getContextPath()%>/web/channelUser/cgyContentTaxis.do?action=mod";
       contentForm.submit();
     }
     
     
  }
  
  function exportDate()
  {
		contentListForm.isExport.value="1";
		contentListForm.submit();  
		contentListForm.isExport.value="0";
  }

//-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<%=cgyPath %></td>
  </tr>
</table>
 <form name="contentListForm" action="<%=request.getContextPath()%>/web/channelUser/cgyContentList.do?subSystem=ssms" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
 
  <tr>
    <td colspan="4" align="center" class="title1">
      内容查询<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <input type="hidden" name="isExport" value="0">
      <input type="hidden" name="htype" value="<%=type%>">
    </td>
  </tr>
  <input type="hidden" name="cgyPath" value="<%=cgyPath%>"/>
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
    <td width="18%" align="right" class="text3">内容标签:</td>
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
    <td width="18%" align="right" class="text3">所属平台：</td>
    <td width="30%" class="text4" colspan="3"><input type="text" name="platform" value="<%=platform%>" ></td>
 </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
    	<input name="Submit" type="submit" class="input1" value="查询" >
    	<input name="reset" type="reset" class="input1" value="重置">
		<!--
		<input name="reset" type="button" class="input1" value="内容同步策略管理" onClick="tactic();">
		-->
		<logic:notEmpty name="PageResult" property="pageInfo">
			<input name="reset" type="button" class="input1" value="导出" onClick="exportDate();">
			<font color="red"> 注：此导出为当前条件查询所得数据</font>
  		</logic:notEmpty>
		
	</td>
  </tr>
  
</table>
</form>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">货架栏目管理</td>
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
        params.put("categoryID",request.getParameter("categoryID"));
        params.put("type",type);
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
        params.put("servAttr",servAttr);
        params.put("cgyPath",cgyPath);
        params.put("platform",platform);
        %>
        <pager:pager name="PageResult" action="/web/channelUser/cgyContentList.do?subSystem=ssms" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="4%" align="center" class="title1">选中</td>
    <td width="7%" align="center" class="title1">ID</td> 
    <td width="12%" align="center" class="title1">名称</td>
	<td width="10%" align="center" class="title1">内容ID</td>
    <td width="12%" align="center" class="title1">提供商</td>
    <td width="6%" align="center" class="title1">内容类型</td>  
    <td width="15%" align="center" class="title1">内容标签</td> 
    <td width="5%" align="center" class="title1">资费</td>
    <td width="5%" align="center" class="title1">提供范围</td> 
    <td width="14%" align="center" class="title1">上线时间</td>       	  	
    <td width="5%" align="center" class="title1">排序序号</td>
    <td width="5%" align="center" class="title1">审核状态</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <input type="hidden" name="contentType" value="sort">
  <input type="hidden" name="backURL" value="<%=backURL %>" >
  <input type="hidden" name="changedValues" >
  <input type="hidden" name="cateid" value="<bean:write name="category" property="id"/>">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.ReferenceNode">
    <bean:define id="content" name="vo" property="refNode" type="com.aspire.dotcard.gcontent.GContent"/>
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
	    <td align="center"><input type="checkbox" name="dealContent" value="<bean:write name="content" property="id"/>#<bean:write name="vo" property="id"/>"></td>
	    <td align="center" style="word-break:break-all"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>"><bean:write name="content" property="id"/></a></td>
	    <td align="center" style="word-break:break-all"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name='content' property='id'/>&categoryID=<bean:write name='category' property='id'/>&goodsID=<bean:write name='vo' property='goodsID'/>" id="contentName<bean:write name='vo' property='id'/>"><bean:write name="content" property="name"/></a></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="contentID"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="spName"/></td>  
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="typeDesc"/></td>
	   
	    <td align="center" style="word-break:break-all">
	     <script language="JavaScript">
	   	 var str = '<bean:write name="content" property="keywordsDesc"/>';
	   	 if(str.length > 8 ){
	   	 var trunckedStr = str.substring(0,8)+'...';
	   	 }else{
	   	 var trunckedStr = str;
	   	 }
			document.write(trunckedStr);
		</script>
	    </td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="advertPic"/>分</td>
	    <td align="center" style="word-break:break-all">
	                                  <logic:equal value="G" name="content" property="servAttr">全网</logic:equal>
	                                  <logic:equal value="L" name="content" property="servAttr">省内</logic:equal>    </td>
	    <td align="center" style="word-break:break-all"><bean:write name='content' property='marketDate'/></td>
	    
	    
	    	<td align="center"><input type="text" name="Taxis" size="4" value="<bean:write name='vo' property='sortID'/>"/></td>
	   		<td align="center" style="word-break:break-all">
	                                  <logic:equal value="0" name="content" property="verifyStatus">审核中</logic:equal>
	                                  <logic:equal value="1" name="content" property="verifyStatus">通过</logic:equal>
	                                  <logic:equal value="2" name="content" property="verifyStatus">不通过</logic:equal>    </td>
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
        <pager:pager name="PageResult" action="/web/channelUser/cgyContentList.do?subSystem=ssms" params="<%=params%>">
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
          <td align="center" class="text5">
            <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1" onClick="return removeContent();" value="移除"></logic:notEmpty>
            <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonSet" type="button" class="input1" onClick="return SetSortIDS();" value="排序序号设定"></logic:notEmpty>
            <input name="buttonAdd" type="button" class="input1"  onClick="return addContent();" value="添加分类资源">
            <input name="buttonImport" type="button" class="input1"  onClick="window.location.href='cgy_content_import.jsp?cateId=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath %>'" value="导入货架商品">
          </td>
        </tr>
</table>
</body>
</html>
