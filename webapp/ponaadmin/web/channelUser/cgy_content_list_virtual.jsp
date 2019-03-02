<%@ page contentType="text/html; charset=gbk" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page import="com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.common.Validator" %>


<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));


String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String backURL = request.getContextPath()+"/web/channelUser/cgyContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;

String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;

backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag + "&tagLogic=" + tagLogic
                  +"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;
String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
backURL=backURL+"&pageSize="+pageSize;

String deviceid=Validator.filter(request.getParameter("deviceid")==null?"":request.getParameter("deviceid"));




//��ȡ��ǰ���ÿ��б�
List cfgList=DBPersistencyCFG.getInstance().getOrderedSubType("nt:gcontent",true);
pageContext.setAttribute("cfgList",cfgList,pageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">
<!--
function checkForm(form)
{
  if(!checkContent(form.keywords,"REG_CHINESE_NUM_LERTER_COMMA"))
  {
    alert("�ؼ���ֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ�����š�");
    form.keywords.focus();
    return false;
  }
  
  return true;
  
}
 function checkPageSize(field)
  {
    var pageSize=field.value;
    var pattern=/\D+/;
    if(pattern.test(pageSize))
    {
      alert("ÿҳչʾ��¼����ֵ������������");
      field.focus();
      return false;
    }
    var Page_MIN=<%=PageSizeConstants.Page_MIN%>;
    var Page_MAX=<%=PageSizeConstants.page_MAX%>;
    if(pageSize<Page_MIN)
    {
      alert("ÿҳչʾ�ļ�¼��������ڻ��ߵ���"+Page_MIN);
      field.focus();
      return false;
    }
    if(pageSize>Page_MAX)
    {
       alert("ÿҳչʾ�ļ�¼������С�ڻ��ߵ���"+Page_MAX);
       field.focus();
       return false;
    }
    return true;
  }
//-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<%=cgyPath %></td>
  </tr>
</table>
 <form name="contentListForm" action="<%=request.getContextPath()%>/web/channelUser/cgyContentList.do?subSystem=ssms" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
 
  <tr>
    <td colspan="4" align="center" class="title1">
      ���ݲ�ѯ
    </td>
  </tr>
  <input type="hidden" name="cgyPath" value="<%=cgyPath%>"/>
   <input type="hidden" name="categoryID" value="<%=categoryID%>"/>
  <tr>
    <td width="18%" align="right" class="text3">����ID��
    </td>
    <td width="30%" class="text4"><%=categoryID %></td>
    <td width="18%" align="right" class="text3">����ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="deviceid" value="<%=deviceid%>"></td>
  </tr>

  <tr>
    <td colspan="4" align="center" class="text5">
    	<input name="Submit" type="submit" class="input1" value="��ѯ" >
    	<input name="reset" type="reset" class="input1" value="����">
		
	</td>
  </tr>
  
</table>
</form>
<br>
 
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">������Ŀ����</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
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
        params.put("categoryID",categoryID);
        params.put("cgyPath",cgyPath);
        params.put("deviceid",deviceid);
        %>
        <pager:pager name="PageResult" action="/web/channelUser/cgyContentList.do?subSystem=ssms" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="1"/>
        </pager:pager>

    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="10%" align="center" class="title1">����ID</td> 
    <td width="18%" align="center" class="title1">����</td>
	<td width="10%" align="center" class="title1">�ṩ��</td>
	<td width="10%" align="center" class="title1">��ҵ����</td>
	<td width="10%" align="center" class="title1">ҵ�����</td>
	<td width="10%" align="center" class="title1">��������</td>
	<td width="10%" align="center" class="title1">�ṩ��Χ</td>
	
  </tr>
  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.syncAndroid.dc.json2.JsonRespVO">
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
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="contentid"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="name"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="spname"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="icpcode"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="icpservid"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="catename"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="vo" property="servattr"/></td>		    
	  </tr>
  </logic:iterate>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/channelUser/cgyContentList.do?subSystem=ssms" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="2"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>
</body>
</html>
