<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%
List categoryRuleNames = (List)request.getAttribute("categoryRuleNames");
%>
<html>
<head>
<title>������Ʒ�ֶ�����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
	<script language="javascript">
	function checkc(){
var opts =     document.getElementsByName("cid");
		  var  linkStr = ""; //�ύURL,�Լ�����
		 
        for(i=0;i<opts.length;i++){
            if(opts[i].checked==true){
                linkStr+="&opt="+opts[i].value;
            }
        }
        if(linkStr == null || linkStr == ""){
        	alert("û��ѡ�������ύ");
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
    <td width="5%" align="center" class="title1">ѡ��</td>
	<td width="10%" align="center" class="title1">����ID</td>
    <td width="15%" align="center" class="title1">����</td>
    <td width="35%" align="center" class="title1">·��</td>  
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
    <td  width="5%"  align="center">ѡ�и���</td>
	<td width="10%" align="center" ><input type="button" class="input1" name="genLogo" value="ִ��ѡ�и���"
				onclick="checkc();"		></td>
				<td width="15%" align="center" ></td>
    <td width="35%" align="center"><font color="red">�������û�и��£��ò�������ֻ�Ǹ���ѡ�У����������������������</font></td>
  </tr>
  </form>
  </table>
</body>
</html>
