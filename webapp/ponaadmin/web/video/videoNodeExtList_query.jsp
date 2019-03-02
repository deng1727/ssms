<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="nodeName" name="nodeName"  value=""/>
<bean:parameter id="nodeId" name="nodeId"  value=""/>
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market视频货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：货架管理中心----视频栏目说明管理</td>
  </tr>
</table>
<form name="videoNodeExtListForm" action="videoNode.do" method="post" onSubmit="return checkPageSize(videoNodeExtListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td colspan="4" align="center" class="title1">资源查询</td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">栏目名称：</td>
    <td width="30%" class="text4"><input type="text" name="nodeName" value='<bean:write  name="nodeName" />'>
	<td width="18%" align="right" class="text3">栏目ID：</td>
    <td width="30%" class="text4"><input type="text" name="nodeId" value='<bean:write  name="nodeId" />'></td>
  </tr>
  <tr>
    
    <td width="18%" align="right" class="text3">每页展示记录数：</td>
    <td width="30%" class="text4" ><input type="text" name="pageSize" value="<bean:write  name="pageSize"  />"  ></td>
    
    </tr>
	
  <tr>
   <input type="hidden" name="perType" value="query">
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
    <td align="center" class="title1">视频栏目说明管理</td>
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
       params.put("nodeName",nodeName);
       params.put("nodeId",nodeId);
        params.put("perType","query");
       params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/baseVideo/videoNode.do" params="<%=params%>">
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
  
    <td width="3%" align="center" class="title1">栏目ID</td>
    <td width="20%" align="center" class="title1">栏目名称</td>
  
    <td width="18%" align="center" class="title1">栏目说明</td>
  </tr>


  <logic:iterate id="node" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseVideo.vo.VideoNodeExtVO">
  
  <tr class="text5">
	
    <td align="center">
    
    <bean:write name="node" property="nodeId"/></td>
    <td align="center">
       
    <bean:write name="node" property="nodeName"/></td>
  <td align="center"><bean:write name="node" property="nodeDesc"/></td>
  </tr>
  </logic:iterate>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/baseVideo/videoNode.do" params="<%=params%>">
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
	

	<input name="buttonExport" type="button" class="input1" onClick="videoNodeExtImport();" value="批量导入">
		
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

function videoNodeExtImport()
{
    window.location.href="../video/videoNodeExt_Import.jsp";
}
function contentExtSyn()
{
     window.location.href="contentExtsynMess.jsp";
  
}
//-->
</script>