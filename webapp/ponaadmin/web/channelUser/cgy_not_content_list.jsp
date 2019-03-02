<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
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
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String trueContentID = Validator.filter(request.getParameter("trueContentID")==null?"":request.getParameter("trueContentID"));

String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;
String backURL = request.getContextPath()+ "/web/channelUser/cgyNotContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;
backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag 
                  + "&tagLogic=" + tagLogic+"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;

String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
backURL=backURL+"&pageSize="+pageSize;
String backURL_enc = java.net.URLEncoder.encode(backURL);
//��ȡ��ǰ���ÿ��б�
  List contentTypeList=CategoryUpdateConfig.getInstance().getNodeValueList("contentType");
  pageContext.setAttribute("contentTypeList", contentTypeList, PageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>������ݵ�����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
<!--
function addContent()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("����ѡ��Ҫ��ӵ����ݣ�");
    return false;
    
  }
  contentForm.action="<%=request.getContextPath()%>/web/channelUser/cgyContentAdd.do?isClose=true&categoryID=<bean:write name="category" property="id"/>";
  
  contentForm.submit();
}

function addContentToCgy()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("����ѡ��Ҫ��ӵ����ܵ����ݣ�");
    return false;
    
  }
  contentForm.action="<%=request.getContextPath()%>/web/channelUser/contentAddToCgy.do";
  contentForm.submit();
}

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
    //
    contentListForm.querySubmit.disabled=true; //���û�г����������ٴ��ύ,��ֹ�ظ��Դ��ύ��
    return true;
  }
  

//-->
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<%=cgyPath %></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="contentListForm" action="<%=request.getContextPath()%>/web/channelUser/cgyNotContentList.do?subSystem=ssms" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
  <tr>
    <td colspan="4" align="center" class="title1">
      ���ݲ�ѯ<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
            <input type="hidden" name="cgyPath" value="<%=cgyPath  %>">
    </td>
  </tr>
<tr>
    <td width="18%" align="right" class="text3">���ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="name" value="<%=name%>"></td>
    <td width="18%" align="right" class="text3">�ṩ�̣�
    </td>
    <td width="30%" class="text4"><input type="text" name="spName" value="<%=spName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ҵ�����ݷ��ࣺ
    </td>
    <td width="30%" class="text4"><input type="text" name="cateName" value="<%=cateName%>"></td>
    <td width="18%" align="right" class="text3">��ҵ���룺
    </td>
    <td width="30%" class="text4"><input type="text" name="icpCode" value="<%=icpCode%>"></td>
 </tr>
  
 <tr>
    <td width="18%" align="right" class="text3">ҵ����룺
    </td>
    <td width="30%" class="text4"><input type="text" name="icpServId" value="<%=icpServId%>"></td>
    <td width="18%" align="right" class="text3">�������ͣ�
    </td>
    
    <td width="30%" class="text4">  
    <SELECT name=type> 
    <OPTION value="" ></OPTION>
    	<logic:iterate id="contentType" name="contentTypeList" type="com.aspire.ponaadmin.web.category.RecordVO" >
	    	<option value="<bean:write name="contentType" property="key"/>" 
	    		<logic:equal value="<%=type%>" name="contentType" property="key">selected</logic:equal>>
	    		<bean:write name="contentType" property="value"/>
	    	</option>
	    </logic:iterate>             
    </SELECT>
    </td>
    
 </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="contentID" value="<%=contentID%>"></td>
    <td width="18%" align="right" class="text3">���ݱ�ǩ:
    </td>
    <td width="30%" class="text4">
    <input type="text" name="contentTag" value="<%=contentTag%>">
    	<select name="tagLogic">
	    	<option value="AND" <%if(tagLogic.equals("AND")){%>selected<%}%>>AND</option>
	    	<option value="OR" <%if(tagLogic.equals("OR")){%>selected<%}%>>OR</option>
    	</select>
    </td>
 </tr>  
<tr>
  
    <td width="18%" align="right" class="text3">�ֻ��ͺţ�</td>
    <td width="30%" class="text4"><input type="text" name="deviceName" value="<%=deviceName %>"></td>
    <td width="18%" align="right" class="text3">�ṩ��Χ</td>
    <td width="30%" class="text4"><SELECT name=servAttr> 
    <OPTION value="" ></OPTION>
    <OPTION value="G" <%if(servAttr.equals("G")){%>selected<%}%> >ȫ��</OPTION>
    <OPTION value="L" <%if(servAttr.equals("L")){%>selected<%}%> >ʡ��</OPTION>
    </select>
    </td>
 </tr>
     <tr>
    <td width="18%" align="right" class="text3">����ID��</td>
    <td width="30%" class="text4"><input type="text" name="trueContentID" value="<%=trueContentID%>" ></td>
    <td width="18%" align="right" class="text3">ÿҳչʾ��¼����</td>
    <td width="30%" class="text4"><input type="text" name="pageSize" value="<%=pageSize%>" ></td>

 </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5"><input name="querySubmit" type="submit" class="input1" value="��ѯ">
    <input name="reset" type="reset" class="input1" value="����"></td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">������Ŀ����----�������</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <logic:equal value="0" name="exeType" >
          <td align="center"><font color="#ff0000">��ѡ��������ѯ</font></td>
          </logic:equal>
      <logic:notEqual value="0" name="exeType" >
          <td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
          </logic:notEqual>
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
       // params.put("author",author);
        //params.put("source",source);
       // params.put("keywords",keywords);                       
        params.put("categoryID",categoryID);
        //params.put("keywordid",keywordid);
        params.put("type",type);
       // params.put("keywordid",keywordid);
        params.put("spName",spName);
        params.put("cateName",cateName);
        params.put("icpCode",icpCode);
        params.put("icpServId",icpServId);      
        params.put("contentID",contentID);  
        params.put("trueContentID",trueContentID);
        params.put("contentTag",contentTag);
        params.put("tagLogic",tagLogic);
        params.put("pageSize",pageSize);
        params.put("deviceName",deviceName);
        params.put("cgyPath",cgyPath);
        %>
        <pager:pager name="PageResult" form="contentListForm" action="/web/channelUser/cgyNotContentList.do?subSystem=ssms" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">ѡ��</td>
	<td width="10%" align="center" class="title1">ID</td>
    <td width="13%" align="center" class="title1">����</td>
    <td width="10%" align="center" class="title1">����ID</td>
    <td width="12%" align="center" class="title1">�ṩ��</td>
    <td width="10%" align="center" class="title1">���ݳ���</td>
    <td width="15%" align="center" class="title1">���ݱ�ǩ</td>
    <td width="5%" align="center" class="title1">��������</td>
    <td width="5%" align="center" class="title1">�ʷ�</td>
    <td width="5%" align="center" class="title1">�ṩ��Χ</td>
    <td width="10%" align="center" class="title1">����ʱ��</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <input type="hidden" name="backURL" value="<%=backURL %>" >
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
    <td align="center"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>&backURL=<%=backURL_enc%>"><bean:write name="content" property="id"/></a></td>
    <td align="center" style="word-break:break-all;"><a href="<%=request.getContextPath()%>/web/channelUser/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>&backURL=<%=backURL_enc%>"><bean:write name="content" property="name"/></a></td>
   
   <td align="center" style="word-break:break-all"><bean:write name="content" property="contentID"/></td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="spName"/></td>
    <td align="center" style="word-break:break-all;">
       	<logic:equal name="content" property="subType" value="1">
	        ��ͨӦ��
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
	        MM��ҵ����
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="7">
	        ����Ӧ��
	    </logic:equal>
	    <logic:equal name="content" property="subType" value="21">
	        ����App
	    </logic:equal>
	</td>
    <td align="center" style="word-break:break-all;"><bean:write name="content" property="keywordsDesc"/></td>
    <td align="center"><bean:write name="content" property="typeDesc"/></td>
    <td align="center" style="word-break:break-all"><bean:write name="content" property="advertPic"/>��</td>
    <td align="center" style="word-break:break-all">
                                  <logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
                                  <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>
                      </td>
    <td align="center"><bean:write name="content" property="marketDate"/></td>
  </tr>
  </logic:iterate>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'dealContent',this.checked,false);">
      ȫ��ѡ��
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" form="contentListForm" action="/web/channelUser/cgyNotContentList.do?subSystem=ssms" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">&nbsp;
            <logic:notEqual name="category" property="name" value="δ������Դ">
			   <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonAdd" type="button" class="input1"  onClick="return addContent();" value="ȷ��"></logic:notEmpty><input name="btn_close" type="button" class="input1" onClick="window.close();" value="�رմ���">
			</logic:notEqual>
			
            <logic:notEmpty name="PageResult" property="pageInfo">
                <logic:equal name="category" property="name" value="δ������Դ"><input name="buttonAdd" type="button" class="input1"  onClick="return addContentToCgy();" value="��ӵ�����"></logic:equal>
            </logic:notEmpty>
          </td>
        </tr>
</table>
</body>
</html>
