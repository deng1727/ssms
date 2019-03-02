<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";

String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>动漫章节管理</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">



function closewin()
{
self.opener = null;
self.close();
}


function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >

<br>
<form name="refForm" action="referenceTree.do" method="post" >

<input type="hidden" name="method" value="">
<input type="hidden" name="contentId" value="">


<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>动漫章节列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
   
    <td width="20%" align="center" class="title1">章节id</td>     
    <td width="70%" align="center" class="title1">章节名称</td>
    <td width="10%" align="center" class="title1">章节资费</td>
  
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.comic.vo.ComicChapterVO">
	    
		<tr class=text4>
			
			<td align="center">
      			<bean:write name="vo" property="chapterId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="chapterName"/>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="fee"/>
			</td>
      		
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        HashMap params = new HashMap();
    	params.put("method","queryChapter");
   
        params.put("contentId",contentId);
       
        %>
        <pager:pager name="PageResult" action="/web/comic/referenceTree.do" params="<%=params%>">
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

<table width="95%"  border="0" align="center" cellspacing="1">
		   <tr>
	    <td   align="left" colspan="2">
	        	
       <input name="buttonclose" type="button" class="input1"  onClick="closewin();" value="关闭">
	   
    </td>
	   </tr>
	  <tr>
	    <td   align="right" width="20%">
	    	
	    </td>
	    <td   align="left" width="80%"> 
			
	    </td>
	   </tr>
	</table>

</form>

</body>
</html>
