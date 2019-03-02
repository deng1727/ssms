<%@ page contentType="text/html; charset=gbk" %>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator"%>
<%
    String tmpStyle = "text5";
	String channelId = Validator.filter(request
			.getParameter("channelId") == null ? "" : request
			.getParameter("channelId"));
	String channelName = Validator.filter(request
			.getParameter("channelName") == null ? "" : request
			.getParameter("channelName"));
	String distributorId = Validator.filter(request
			.getParameter("distributorId") == null ? "" : request
			.getParameter("distributorId"));
	String distributorName = Validator.filter(request
			.getParameter("distributorName") == null ? "" : request
			.getParameter("distributorName"));
	String channelType = Validator.filter(request
			.getParameter("channelType") == null ? "" : request
			.getParameter("channelType"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>渠道管理列表</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

function add(){
	
}

function edit(){
	
}

</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="channelList.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    
    <td colspan="4" align="center" class="title1">
    <input name="method" type="hidden" id="method" value="query">
      渠道管理列表
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">渠道ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="channelId" value="<%=channelId %>"></td>
    <td width="18%" align="right" class="text3">渠道名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="channelName" value="<%=channelName %>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">渠道商ID：
    </td>
    <td width="30%" class="text4"><input type="text" name="distributorId" value="<%=distributorId %>"></td>
    <td width="18%" align="right" class="text3">渠道商名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="distributorName" value="<%=distributorName %>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">渠道类型：
    </td>
    <td width="30%" class="text4">
    <select name="channelType">
							<option value="">全部</option>
							<option value="0" <logic:equal value="0" name="channelType"> selected</logic:equal>>客户端</option>
							<option value="1" <logic:equal value="1" name="channelType"> selected</logic:equal>>数据接口</option>
							<option value="2" <logic:equal value="2" name="channelType"> selected</logic:equal>>网页</option>
		</select>
    </td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
    </td>
  </tr>
</table>
</form>
<br>
<form name="refForm" action="*" method="post" >
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
     <b>渠道管理列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="10%" align="center" class="title1">渠道ID</td>     
    <td width="25%" align="center" class="title1">渠道名称</td>
    <td width="10%" align="center" class="title1">渠道类型</td>
    <td width="10%" align="center" class="title1">合作商ID</td>     
    <td width="25%" align="center" class="title1">合作商名称</td>
    <td width="15%" align="center" class="title1">申请时间</td>  
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.coManagement.vo.ChannelListVO">
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
      			<a target="_self" href="channelList.do?method=detail&channelId=<bean:write name='vo' property='channelId' />"/><bean:write name="vo" property="channelId"/></a>
      		</td>
      		 <td align="center">
      			<a target="_self" href="channelList.do?method=detail&channelId=<bean:write name='vo' property='channelId' />"/><bean:write name="vo" property="channelName"/></a>
      		</td>
      		 <td align="center">
      			<logic:equal name="vo" property="channelType" value="0">
		      			客户端
		      	</logic:equal>
		      	<logic:equal name="vo" property="channelType" value="1">
		      			数据接口
		      	</logic:equal>
		      	<logic:equal name="vo" property="channelType" value="2">
		      			网页
		      	</logic:equal>
      		</td>
      		 <td align="center">
      			<bean:write name="vo" property="channelsId"/>
      		</td>
      		 <td align="center">
      			<bean:write name="vo" property="channelsName"/>
      		</td>
      		 <td align="center">
      			<bean:write name="vo" property="createDate"/>
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
    	params.put("channelId",channelId);
    	params.put("channelName",channelName);
    	params.put("distributorId",distributorId);
    	params.put("distributorName",distributorName);
    	params.put("channelType",channelType);
        %>
        <pager:pager name="PageResult" action="/web/cooperation/channelList.do" params="<%=params%>">
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
</form>
</body>
</html>
