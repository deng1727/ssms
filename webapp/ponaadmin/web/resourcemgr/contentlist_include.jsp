<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">选中</td>
    <td width="10%" align="center" class="title1">内容ID</td>
    <td width="20%" align="center" class="title1">名称</td>
    <td width="10%" align="center" class="title1">提供商</td>
    <td width="10%" align="center" class="title1">内容出处</td>
    <td width="20%" align="center" class="title1">内容标签</td>
    <td width="10%" align="center" class="title1">内容类型</td>
    <td width="15%" align="center" class="title1">上线时间</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.gcontent.GContent">
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
    <td align="center"><input type="checkbox" name="dealContent" value="<bean:write name="content" property="id"/>"></td>
    <td align="center"><a href="contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>"><bean:write name="content" property="id"/></a></td>
    <td align="center"><a href="contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>"><bean:write name="content" property="name"/></a></td>
    <td align="center"><bean:write name="content" property="spName"/></td>
    <td align="center">
    	<logic:equal name="content" property="subType" value="1">
	        普通应用
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="2">
	        Widget
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="3">
	        ZCOM
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="4">
	        FMM
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="5">
	        JIL
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="6">
	        MM创业大赛
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="7">
	        孵化应用
	    </logic:equal>
    </td>
    <td align="center" style="word-break:break-all;"><a href="contentEdit.do?action=update&contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>">
    <script language="JavaScript">
	   	 var str = '<bean:write name="content" property="keywordsDesc"/>';
	   	 if(str.length > 8 ){
	   	 var trunckedStr = str.substring(0,8)+'...';
	   	 }else{
	   	 var trunckedStr = str;
	   	 }
			document.write(trunckedStr);
		</script>
    </a></td>
    <td align="center"><bean:write name="content" property="typeDesc"/></td>
    <td align="center"><bean:write name="content" property="marketDate"/></td>
  </tr>
  </logic:iterate>
  </form>
</table>
