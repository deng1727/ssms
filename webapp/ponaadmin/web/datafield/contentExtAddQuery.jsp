<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<bean:parameter id="name" name="name"  value=""/>
<bean:parameter id="spName" name="spName"  value=""/>
<bean:parameter id="contentID" name="contentID"  value="" />
<bean:parameter id="type" name="type"  value=""/>
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>添加内容到货架</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----应用活动属性内容资源查询</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="contentListForm" action="contentExtAddQuery.do" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
  <tr>
    <td colspan="4" align="center" class="title1">
      内容查询
    </td>
  </tr>
<tr>
    <td width="18%" align="right" class="text3">名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="name" value='<bean:write  name="name" />'></td>
    <td width="18%" align="right" class="text3">提供商：
    </td>
    <td width="30%" class="text4"><input type="text" name="spName" value='<bean:write  name="spName" />'></td>
  </tr>
<tr>
    <td width="18%" align="right" class="text3">每页展示记录数：</td>
    <td width="30%" class="text4"><input type="text" name="pageSize" value='<bean:write  name="pageSize" />'> </td>
    <td width="18%" align="right" class="text3"></td>
    <td width="30%" class="text4"></td>
 </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5"><input name="querySubmit" type="submit" class="input1" value="查询">
    <input name="reset" type="button" class="input1" value="重置" onClick="javascript:resetFrom();"></td>
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
        params.put("spName",spName);

        %>
        <pager:pager name="PageResult" form="contentListForm" action="/web/datafield/contentExtAddQuery.do" params="<%=params%>">
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
	<td width="10%" align="center" class="title1">内容ID</td>
    <td width="20%" align="center" class="title1">名称</td>
    <td width="13%" align="center" class="title1">提供商</td>
    <td width="8%" align="center" class="title1">企业代码</td>
    <td width="8%" align="center" class="title1">提供范围</td>
    <td width="10%" align="center" class="title1">计费时机</td>
    <td width="6%" align="center" class="title1">价格/元</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.datafield.vo.ContentExtQueryVO">
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
    <td align="center"><input type="radio" name="dealContent" value='<bean:write name="content" property="contentID"/>|<bean:write name="content" property="name"/>|<bean:write name="content" property="spName"/>|<bean:write name="content" property="icpcode"/>|<bean:write name="content" property="mobilePrice"/>|<bean:write name="content" property="mobilePriceStr"/>'></td>
    <td align="center"><bean:write name="content" property="contentID"/></td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="name"/></a></td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="spName"/></td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="icpcode"/></td>
    <td align="center" style="word-break:break-all"><logic:equal value="G" name="content" property="servAttr">全网</logic:equal><logic:equal value="L" name="content" property="servAttr">省内</logic:equal></td>
    <td align="center"><logic:equal value="1" name="content" property="chargetime">下载时计费</logic:equal><logic:equal value="2" name="content" property="chargetime">体验后计费</logic:equal></td>
	<td align="center"><bean:write name="content" property="mobilePriceStr"/></td>
  </tr>
  </logic:iterate>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
  <td align="left" class="text1">  <input name="buttonAdd" type="button" class="input1"  onClick="return addContentExt();" value="添加资源"> <input name="button2" type="button" class="input1"  onClick="window.close();" value="关闭页面"></td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" form="contentListForm" action="/web/datafield/contentExtAddQuery.do" params="<%=params%>">
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
</body>
</html>
<script language="JavaScript">
<!--
function addContentExt()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("请先选择要添加的内容！");
    return false;
  }
  else
  { if(contentForm.dealContent.length==undefined)
  	{
  	window.returnValue= contentForm.dealContent.value;   //返回值    
	window.close(); 
  	}
  	else
  	{
  		for(var i=0;i<contentForm.dealContent.length;i++)
  		{   
		if(contentForm.dealContent[i].checked)
		{
			window.returnValue= contentForm.dealContent[i].value;   //返回值    
			window.close(); 
		}
		}
	}
  }
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
    
    
   if(trim(contentListForm.name.value)==""&&trim(contentListForm.spName.value)=="")
  	{
    alert("请输入名称或提供商！");
    return false;
   }
    //
    contentListForm.querySubmit.disabled=true; //结果没有出来不允许再次提交,防止重复对此提交。
    return true;
}

function resetFrom()
{
	contentListForm.name.value = "";
	contentListForm.spName.value = "";
	contentListForm.pageSize.value = "<bean:write  name="pageSize" />";
	
}
//-->
</script>