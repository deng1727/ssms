<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="name" name="name"  value=""/>
<bean:parameter id="spName" name="spName"  value=""/>
<bean:parameter id="contentID" name="contentID"  value="" />
<bean:parameter id="type" name="type"  value=""/>
<bean:parameter id="isrecomm" name="isrecomm"  value=""/>
<bean:parameter id="date" name="date"  value=""/>
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----应用活动属性管理</td>
  </tr>
</table>
<form name="contentExtListForm" action="contentExtList.do" method="post" onSubmit="return checkPageSize(contentExtListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td colspan="4" align="center" class="title1">资源查询</td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">名称：</td>
    <td width="30%" class="text4"><input type="text" name="name" value='<bean:write  name="name" />'>
	<td width="18%" align="right" class="text3">提供商：</td>
    <td width="30%" class="text4"><input type="text" name="spName" value='<bean:write  name="spName" />'></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">内容ID：</td>
    <td width="30%" class="text4"><input type="text" name="contentID" value='<bean:write  name="contentID" />'></td>
    <td width="18%" align="right" class="text3">内容类型：</td>
    <td width="30%" class="text4"> <SELECT style="width=100pt"  name="type" >
	<OPTION value=""  <logic:equal name="type" value="">selected="selected"</logic:equal> >全部</OPTION>
    <OPTION value="1"  <logic:equal name="type" value="1">selected="selected"</logic:equal> >折扣</OPTION>
    <OPTION value="2"  <logic:equal name="type" value="2">selected="selected"</logic:equal> >秒杀</OPTION>  
    <OPTION value="3"  <logic:equal name="type" value="3">selected="selected"</logic:equal> >限时免费</OPTION>
     <OPTION value="4"  <logic:equal name="type" value="4">selected="selected"</logic:equal> >特约限免</OPTION>
    </SELECT></td>
    </tr>
	<tr>
     <td width="18%" align="right" class="text3">是否推荐：</td>
    <td width="30%" class="text4"> <SELECT style="width=100pt"  name="isrecomm" >
	<OPTION value=""  <logic:equal name="isrecomm" value="">selected="selected"</logic:equal> >全部</OPTION>
    <OPTION value="0"  <logic:equal name="isrecomm" value="0">selected="selected"</logic:equal> >不推荐</OPTION>
    <OPTION value="1"  <logic:equal name="isrecomm" value="1">selected="selected"</logic:equal> >推荐</OPTION>  
    </SELECT></td>
    <td width="18%" align="right" class="text3">日期：</td>
    <td width="30%" class="text4"><input name="date" id="date" type="text"  class="Wdate" value='<bean:write  name="date" />'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  /></td>
    </tr>
  <tr>
    <td width="18%" align="right" class="text3">每页展示记录数：</td>
    <td width="30%" class="text4" ><input type="text" name="pageSize" value="<bean:write  name="pageSize"  />"  ></td>
    <td width="18%" align="right" class="text3"></td>
    <td width="30%" class="text4"></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit" type="submit" class="input1" value="查询">
    <input name="a" type="button" class="input1" value="重置" onClick="javascript:resetFrom();">
  </tr>
</table>
</form>

<form name="contentForm" action="" method="post">
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
       params.put("spName",spName);
       params.put("type",type);
       params.put("contentID",contentID);
       params.put("isrecomm",isrecomm);
       params.put("date",date);
       params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/datafield/contentExtList.do" params="<%=params%>">
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
	<td width="3%" align="center" class="title1">选中</td>
    <td width="3%" align="center" class="title1">ID</td>
    <td width="5%" align="center" class="title1">内容ID</td>
    <td width="20%" align="center" class="title1">名称</td>
    <td width="15%" align="center" class="title1">提供商</td>
    <td width="7%" align="center" class="title1">内容类型</td>
    <td width="7%" align="center" class="title1">是否推荐</td>
    <td width="5%" align="center" class="title1">折扣率</td>
    <td width="25%" align="center" class="title1">时间周期</td>
    <td width="10%" align="center" class="title1">上线时间</td>
  </tr>

  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.datafield.vo.ContentExtVO">
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
	<td align="center"><input type="checkbox" name="idDel" value="<bean:write name="content" property="id"/>"></td>
    <td align="center">
	    <logic:equal name="content" property="type" value="4">
	    <a href="contentExtInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="id"/></a>
	    </logic:equal> 
	    <logic:notEqual name="content" property="type" value="4">
	    <bean:write name="content" property="id"/>
	    </logic:notEqual> 
	</td>
	
    <td align="center"><bean:write name="content" property="contentID"/></td>
    	
    <td align="center">
		<logic:equal name="content" property="type" value="4">
		<a href="contentExtInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="name"/></a>
		</logic:equal> 
		<logic:notEqual name="content" property="type" value="4">
		<bean:write name="content" property="name"/>
		</logic:notEqual> 
		
	</td>
    
    <td align="center"><bean:write name="content" property="spName"/></td>
    <td align="center"><logic:equal name="content" property="type" value="1">折扣</logic:equal>  
    	<logic:equal name="content" property="type" value="2">秒杀</logic:equal> 
    	<logic:equal name="content" property="type" value="3">限时免费</logic:equal> 
    	<logic:equal name="content" property="type" value="4">特约限免</logic:equal> 
    </td>
    <td align="center"><logic:equal name="content" property="isrecomm" value="0">不推荐</logic:equal>  
    	<logic:equal name="content" property="isrecomm" value="1">推荐</logic:equal> 
    </td>
    <td align="center"><bean:write name="content" property="discount"/></td>
    <td align="center"><bean:write name="content" property="dateStartStr"/> <bean:write name="content" property="timeStart"/>至<bean:write name="content" property="dateEndStr"/> <bean:write name="content" property="timeEnd"/></td>
    <td align="center"><bean:write name="content" property="lupDate"/></td>
  </tr>
  </logic:iterate>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr><td width="24%" class="text1">&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'idDel',this.checked,false);"> 全部选中</td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/datafield/contentExtList.do" params="<%=params%>">
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
	<logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1" onClick="contentExtDel();" value="删除"></logic:notEmpty>
	<input name="buttonDel" type="button" class="input1" onClick="contentExtAdd();" value="单条新增">
	<input name="buttonExport" type="button" class="input1" onClick="contentExtImport();" value="批量导入">
		<input name="buttonExport" type="button" class="input1" onClick="contentExtSyn();" value="活动属性同步及徽章更新">
	</td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
<!--
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
    var Page_MIN=1;
    var Page_MAX=100;
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

function resetFrom()
{
	contentExtListForm.name.value = "";
	contentExtListForm.spName.value = "";
	contentExtListForm.contentID.value = "";
	document.getElementById('type').options[0].selected=true;
	document.getElementById('isrecomm').options[0].selected=true;
	contentExtListForm.pageSize.value = "12";
}


function contentExtDel()
{
  //判断有没有最少选择一个
  if(!haveChooseChk(contentForm,"idDel"))
  {
    alert("请先选择要删除的资源！");
    return false;
    
  }
  if(confirm("您确定要进行删除操作吗？"))
  {
    contentForm.action="extDel.do";
    contentForm.submit();
  }
}

function contentExtAdd()
{
    contentForm.action="contentExtAdd.do";
    contentForm.submit();
}

function contentExtImport()
{
    window.location.href="contentExtImport.jsp";
}
function contentExtSyn()
{
     window.location.href="contentExtsynMess.jsp";
  
}
//-->
</script>