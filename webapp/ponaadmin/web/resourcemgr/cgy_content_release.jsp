<%@ page contentType="text/html; charset=gbk" %>
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
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<%@ page import="com.aspire.common.Validator" %>


<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String typeDesc = Validator.filter(request.getParameter("typeDesc")==null?"":request.getParameter("typeDesc"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String trueContentID = Validator.filter(request.getParameter("trueContentID")==null?"":request.getParameter("trueContentID"));

String platform=Validator.filter(request.getParameter("platform")==null?"":request.getParameter("platform"));

String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String backURL = "/web/resourcemgr/categoryGoodsList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;

String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String aprovalStatus = Validator.filter(request.getParameter("aprovalStatus")==null?"":request.getParameter("aprovalStatus"));
backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag + "&tagLogic=" + tagLogic
                  +"&deviceName="+deviceName+"&servAttr="+servAttr+"&aprovalStatus="+aprovalStatus;
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
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">

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

function approvalRelease(){	
	var bool = true;
	var dealContents = document.getElementsByName("dealContent");
	for(var i = 0;i < dealContents.length;i++){
		if(dealContents[i].checked  == true){
			bool = false;
		}
	}
	
	if(bool){
		alert("��ѡ����Ҫ��������������");
		return;
	}
	
	 contentForm.action="<%=request.getContextPath()%>/web/resourcemgr/categoryGoodsList.do?subSystem=approval";
     contentForm.submit();
	
}

function approvalRefuse(){
	var bool = true;
	var dealContents = document.getElementsByName("dealContent");
	for(var i = 0;i < dealContents.length;i++){
		if(dealContents[i].checked  == true){
			bool = false;
		}
	}
	
	if(bool){
		alert("��ѡ����Ҫ������ͨ��������");
		return;
	}
	 contentForm.action="<%=request.getContextPath()%>/web/resourcemgr/categoryGoodsList.do?subSystem=refuse";
     contentForm.submit();
}

function selectAll(aForm,checkFieldName)
{
  for(var i=0;i<aForm.length;i++)
  {
    if(aForm.elements[i].type=="checkbox")
    {
      if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
      {
    	  if(!aForm.elements[i].disabled)
             aForm.elements[i].checked = true
      }	
    }
  }
}

//-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >

 <form name="contentListForm" action="categoryGoodsList.do?subSystem=ssms" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
 
  <tr>
    <td colspan="4" align="center" class="title1">
      ���ݲ�ѯ<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <input type="hidden" name="isExport" value="0">
      <input type="hidden" name="htype" value="<%=type%>">
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
    <td width="18%" align="right" class="text3">���ݱ�ǩ:</td>
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
    <td width="18%" align="right" class="text3">����ƽ̨��</td>
    <td width="30%" class="text4"><input type="text" name="platform" value="<%=platform%>" ></td>
    <td width="18%" align="right" class="text3">����״̬</td>
    <td width="30%" class="text4"><SELECT name=aprovalStatus> 
    <OPTION value="4" <%if(aprovalStatus == null || "".equals(aprovalStatus)||aprovalStatus.equals("4")){%>selected<%}%>>ȫ��</OPTION>
    <OPTION value="3" <%if(aprovalStatus.equals("3")){%>selected<%}%> >�༭</OPTION>
    <OPTION value="0" <%if(aprovalStatus.equals("0")){%>selected<%}%> >������</OPTION>
    <OPTION value="2" <%if(aprovalStatus.equals("2")){%>selected<%}%> >������ͨ��</OPTION>
    <OPTION value="1" <%if(aprovalStatus.equals("1")){%>selected<%}%> >�ѷ���</OPTION>
    <OPTION value="3,2" <%if(aprovalStatus.equals("3,2")){%>selected<%}%> >�༭��������ͨ��</OPTION>
    </select>
    </td>
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
        params.put("categoryID",request.getParameter("categoryID"));
        params.put("type",type);
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
        params.put("servAttr",servAttr);
        params.put("platform",platform);
        params.put("aprovalStatus",aprovalStatus);
        %>
        <pager:pager name="PageResult" action="/web/resourcemgr/categoryGoodsList.do?subSystem=ssms" params="<%=params%>">
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
    <td width="5%" align="center" class="title1">ѡ��</td>
    <td width="5%" align="center" class="title1">ID</td> 
    <td width="10%" align="center" class="title1">����</td>
	<td width="10%" align="center" class="title1">����ID</td>
    <td width="10%" align="center" class="title1">�ṩ��</td>
    <td width="10%" align="center" class="title1">��������</td>  
    <td width="15%" align="center" class="title1">���ݱ�ǩ</td> 
    <td width="5%" align="center" class="title1">�ʷ�</td>
    <td width="5%" align="center" class="title1">�ṩ��Χ</td> 
    <td width="13%" align="center" class="title1">����ʱ��</td>       	  	
    <td width="5%" align="center" class="title1">�������</td>
    <td width="7%" align="center" class="title1">����״̬</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <input type="hidden" name="contentType" value="sort">
  <input type="hidden" name="backURL" value="<%=backURL %>" >
  <input type="hidden" name="changedValues" >
  <input type="hidden" name="cateid" value="<bean:write name="category" property="id"/>">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.ReferenceNode">
    <bean:define id="content" name="vo" property="refNode" type="com.aspire.dotcard.gcontent.GContent"/>
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
	    <input type="checkbox" name="dealContent" value="<bean:write name="content" property="id"/>#<bean:write name="vo" property="id"/>#<bean:write name="vo" property="delFlag"/>"  <%if(!"0".equals(vo.getVerifyStatus())){ %> disabled="true" <%} %>></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="id"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="name"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="contentID"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="spName"/></td>  
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="typeDesc"/></td>
	   
	    <td align="center" style="word-break:break-all">
	     <script language="JavaScript">
	   	 var str = '<bean:write name="content" property="keywordsDesc"/>';
	   	 if(str.length > 8 ){
	   	 var trunckedStr = str.substring(0,8)+'...';
	   	 }else{
	   	 var trunckedStr = str;
	   	 }
			document.write(trunckedStr);
		</script>
	    </td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="advertPic"/>��</td>
	    <td align="center" style="word-break:break-all">
	                                  <logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
	                                  <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>    </td>
	    <td align="center" style="word-break:break-all"><bean:write name='content' property='marketDate'/></td>
	    
	    
	    <td align="center" style="word-break:break-all"><bean:write name='vo' property='sortID'/></td>
	   
	   <td align="center" style="word-break:break-all">
	    <%if("3".equals(vo.getVerifyStatus())){ %>
	             �༭
	   <%}else if("1".equals(vo.getVerifyStatus())){ %>
	              �����ɹ�
	   <%}else if("2".equals(vo.getVerifyStatus())){ %>
	              ������ͨ��
	   <%}else{ %>
	         ������
	   <%} %>
	   </td>
	  </tr>
  </logic:iterate>
  </form>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAll(contentForm,'dealContent');">
      ȫ��ѡ��
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/resourcemgr/categoryGoodsList.do?subSystem=ssms" params="<%=params%>">
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
          <td align="center" class="text5">
            <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1" onClick="return approvalRelease();" value="��������"></logic:notEmpty>
            <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonSet" type="button" class="input1" onClick="return approvalRefuse();" value="������ͨ��"></logic:notEmpty>
            <input name="buttonClose" type="button" class="input1"  onClick="window.returnValue=true,window.close();" value="�ر�">
          </td>
        </tr>
</table>
</body>
</html>
