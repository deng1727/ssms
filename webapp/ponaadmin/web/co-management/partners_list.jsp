<%@ page contentType="text/html; charset=gbk" %>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator"%>
<%
    String tmpStyle = "text5";
	String cooperationId = Validator.filter(request
			.getParameter("cooperationId") == null ? "" : request
			.getParameter("cooperationId"));
	String cooperationName = Validator.filter(request
			.getParameter("cooperationName") == null ? "" : request
			.getParameter("cooperationName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>合作商列表查询</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

function add(){
	refForm.method.value = 'add';
	refForm.submit();
}

function edit(){
	refForm.method.value = 'edit';
	var bool = true;
	var radio = document.getElementsByName("radio");
	  for(var i = 0;i < radio.length;i++){
			if(radio[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("请选择需要编辑的合作商");
			return;
		}
	refForm.submit();
}

</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="cooperationList.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <input name="method" type="hidden" id="method" value="query">
  <tr>
    <td colspan="4" align="center" class="title1">
      合作商列表查询
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">合作商ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="cooperationId" value="<bean:write name="cooperationId"/>"></td>
    <td width="18%" align="right" class="text3">合作商名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="cooperationName" value="<bean:write name="cooperationName"/>"></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
    </td>
  </tr>
</table>
</form>
<br>
<form name="refForm" action="cooperationList.do" method="post" >
<input name="method" type="hidden" id="method" value="add">
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>合作商列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="3%" align="center" class="title1"></td>
    <td width="20%" align="center" class="title1">合作商ID</td>     
    <td width="25%" align="center" class="title1">合作商名称</td>
    <td width="22%" align="center" class="title1">合作日期</td>
    <td width="15%" align="center" class="title1">状态</td>  
    <td width="15%" align="center" class="title1">操作</td>  
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.coManagement.vo.CooperationVO">
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
      			<input type="radio" name="radio" value="<bean:write name='vo' property='cooperationId' />">
      		</td>
		    <td align="center">
      			<a target="_self" href="cooperationList.do?method=detail&cooperationId=<bean:write name='vo' property='cooperationId' />"/><bean:write name="vo" property="cooperationId"/></a>
      		</td>
      	    <td align="center">
      	        <a target="_self" href="cooperationList.do?method=detail&cooperationId=<bean:write name='vo' property='cooperationId' />"/><bean:write name="vo" property="cooperationName"/></a>
      			
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="cooperationDate"/>
      		</td>
      		<td align="center">
      		   <% if("0".equals(vo.getStatus())){   %>
      		    	正常
      		     <% }else{  %>
      		    	终止合作
      		     <%  } %>
      		   
      		</td>
      		<td align="center">
      			<% if("0".equals(vo.getStatus())){ %>
      		    	<a target="_self" href="cooperationList.do?method=operation&type=1&cooperationId=<bean:write name='vo' property='cooperationId' />"/>终止合作</a>
      		     <%  }else{ %>
      		    	<a target="_self" href="cooperationList.do?method=operation&type=0&cooperationId=<bean:write name='vo' property='cooperationId' />"/>恢复合作</a>
      		      <% } %>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
    	params.put("method","query");
    	params.put("cooperationId",cooperationId);
    	params.put("cooperationName",cooperationName);
        %>
        <pager:pager name="PageResult" action="/web/cooperation/cooperationList.do" params="<%=params%>">
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

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
		<input name="buttonAdd" type="button" class="input1" onClick="add();" value="新增">
		<logic:notEmpty name="PageResult" property="pageInfo">
		<input name="buttonEdit" type="button" class="input1" onClick="edit();" value="编辑">
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
</body>
</html>
