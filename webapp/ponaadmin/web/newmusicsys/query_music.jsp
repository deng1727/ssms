<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String musicId = Validator.filter(request.getParameter("musicId")==null?"":request.getParameter("musicId"));
String musicName = Validator.filter(request.getParameter("musicName")==null?"":request.getParameter("musicName"));
String singer = Validator.filter(request.getParameter("singer")==null?"":request.getParameter("singer"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>新音乐添加查询</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />

</head>
<script language="javascript">
	function add()
	{
		var temp = '';
		
		if(refForm.dealRef.length == undefined)
		{
			if(refForm.dealRef.checked == true)
			{
				temp = temp + refForm.dealRef.value + ';';
			}
		}
		else
		{
			for(var i=0;i<refForm.dealRef.length; i++)
			{
				if(refForm.dealRef[i].checked==true)
				{
					temp = temp + refForm.dealRef[i].value + ';';
				}
			}
		}
		temp = temp.substring(0,temp.lastIndexOf(";"));
        window.returnValue=temp;
	    window.close();
	}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="queryReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      新音乐查询
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="queryMusic">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">音乐编号：
    </td>
    <td width="30%" class="text4"><input type="text" name="musicId" value="<%=musicId%>"></td>
    <td width="18%" align="right" class="text3">音乐名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="musicName" value="<%=musicName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">歌手：
    </td>
    <td width="30%" class="text4" colspan="3"><input type="text" name="singer" value="<%=singer%>"></td>
 </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
	    <input name="reset" type="reset" class="input1" value="重置">
    </td>
  </tr>
</table>
<br>
<logic:equal name="isFirst" value="1">
	<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
	      <tr bgcolor="#B8E2FC">
	          <td colspan="5" align="center"><font color="#ff0000">请输入查询条件。</font></td>
	      </tr>
	</table>
</logic:equal>


<logic:equal name="isFirst" value="">
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
		     <b>商品列表</b>
		    </td>
		  </tr>
		</table>
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	全选
		  	</td>
		    <td width="10%" align="center" class="title1">音乐id</td>     
		    <td width="18%" align="center" class="title1">音乐名称</td>
		    <td width="12%" align="center" class="title1">歌手</td>
		    <td width="10%" align="center" class="title1">在线试听</td>
    		<td width="10%" align="center" class="title1">彩铃下载</td>
    		<td width="10%" align="center" class="title1">振铃下载</td>
    		<td width="10%" align="center" class="title1">全曲下载</td>
		    <td width="10%" align="center" class="title1">有效期</td>  
		    <td width="5%" align="center" class="title1">排序序号</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO">
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
					<td align="center" style="word-break:break-all;">
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="musicId"/>">
					</td>
					<td align="center">
		      			<bean:write name="vo" property="musicId"/>
		      		</td>
		      		<td align="center"><a href="queryReference.do?musicId=<bean:write name="vo" property="musicId"/>">
		      			<bean:write name="vo" property="musicName"/></a>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="singer"/>
					</td>
					<td align="center">
		      			<bean:write name="vo" property="onlineType"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="coloType"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="ringType"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="songType"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="validity"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="sortId"/>
		      		</td>
				</tr>
		  </logic:iterate>
		</table>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >
		    	<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("categoryId",request.getParameter("categoryId"));
		        params.put("musicId",musicId);
		        params.put("musicName",musicName);
		        params.put("singer",singer);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm"  action="/web/newmusicsys/queryReference.do" params="<%=params%>">
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
</logic:equal>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
		<input name="buttonAdd" type="button" class="input1"  onClick="add();" value="添加">
    </td>
  </tr>
</table>
</logic:notEmpty>
</form>
</body>
</html>
