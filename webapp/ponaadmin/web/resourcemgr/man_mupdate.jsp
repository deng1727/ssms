<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%
List categoryRuleNames = (List)request.getAttribute("categoryRuleNames");
%>
<html>
<head>
<title>货架商品手动更新</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
	<script language="javascript">
	function checkc(){
var opts =     document.getElementsByName("cid");
		  var  linkStr = ""; //提交URL,自己改造
		 
        for(i=0;i<opts.length;i++){
            if(opts[i].checked==true){
                linkStr+="&opt="+opts[i].value;
            }
        }
        if(linkStr == null || linkStr == ""){
        	alert("没有选择不允许提交");
        	}else{
        	document.mudate.submit();
        	}
        	
 }
</script>       	
<body  bgcolor="#F0FBFD">
<TABLE width="95%" align="center" border="0">
<TR>
<TD></TD>
</TR>
</TABLE>
 <table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">选中</td>
	<td width="10%" align="center" class="title1">货架ID</td>
    <td width="15%" align="center" class="title1">名称</td>
    <td width="35%" align="center" class="title1">路径</td>  
  </tr>
  <form name="mudate" action="../category_mupdate.jsp" method="post">
  <%
  if(categoryRuleNames != null && categoryRuleNames.size() > 0)
  {
    for(int i = 0 ; i < categoryRuleNames.size();i ++)
    {
    String[] rulenames = {"","","",""};
    rulenames =(String[])categoryRuleNames.get(i);
    %>
   
   <tr>
    <td width="5%"  align="center" class="title1"><input type="checkbox" name="cid" value="<%=rulenames[0]%>"></td>
	<td width="10%" align="center" class="title1"><%=rulenames[0]%></td>
    <td width="15%" align="center" class="title1"><%=rulenames[2]%></td>
    <td width="35%" align="center" class="title1"><%=rulenames[3]%></td>  
  </tr>
   <%
      
    }
  }
  %>
   <tr>
    <td  width="5%"  align="center">选中更新</td>
	<td width="10%" align="center" ><input type="button" class="input1" name="genLogo" value="执行选中更新"
				onclick="checkc();"		></td>
				<td width="15%" align="center" ></td>
    <td width="35%" align="center"><font color="red">如果当天没有更新，该操作将不只是更新选中，还会更新其他符合条件的</font></td>
  </tr>
  </form>
  </table>
</body>
</html>
