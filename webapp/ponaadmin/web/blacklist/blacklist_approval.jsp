<%@ page contentType="text/html; charset=gbk"%>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page
	import="com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator" %>

<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String typeDesc = Validator.filter(request.getParameter("typeDesc")==null?"":request.getParameter("typeDesc"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));
String contentType = Validator.filter(request.getParameter("contentType")==null?"":request.getParameter("contentType"));
String aprovalStatus = Validator.filter(request.getParameter("aprovalStatus")==null?"":request.getParameter("aprovalStatus"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String backURL = "cgyContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;

String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;

backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag + "&tagLogic=" + tagLogic
                  +"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath+"&aprovalStatus="+aprovalStatus;
String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
backURL=backURL+"&pageSize="+pageSize;

String backURL_enc = java.net.URLEncoder.encode(backURL);

//��ȡ��ǰ���ÿ��б�
List cfgList=DBPersistencyCFG.getInstance().getOrderedSubType("nt:gcontent",true);
pageContext.setAttribute("cfgList",cfgList,pageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
<script language="JavaScript">
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

function onload()
{
	var contentType = '<%=contentType%>';
	
	var select_type = contentListForm.contentType;
	
	for(var i=0; i<select_type.length; i++)
	{
		if(select_type.options[i].value==contentType)
		{
			select_type.options[i].selected=true;
			break;
		}
	}
	
  var aprovalStatus = '<%=aprovalStatus%>';
	
   var select_status = contentListForm.aprovalStatus;
	
	for(var i=0; i<select_status.length; i++)
	{
		if(select_status.options[i].value==aprovalStatus)
		{
			select_status.options[i].selected=true;
			break;
		}
	}
}

function selectAll(aForm,checkFieldName,isCheck)
{
  for(var i=0;i<aForm.length;i++)
  {
    if(aForm.elements[i].type=="checkbox")
    {
      if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
      {
      	if(!aForm.elements[i].disabled)
             aForm.elements[i].checked = isCheck;
      }	
    }
  }
}

function approvalBy(){
	  var bool = true;
		var dealContents = document.getElementsByName("dealContent");
		for(var i = 0;i < dealContents.length;i++){
			if(dealContents[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("��ѡ����Ҫ����������");
			return;
		}
	 contentForm.action="<%=request.getContextPath()%>/web/blacklist/approval.do?action=approval";
     contentForm.submit();
 }
 
function approvalNotGo(){
	  var bool = true;
		var dealContents = document.getElementsByName("dealContent");
		for(var i = 0;i < dealContents.length;i++){
			if(dealContents[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("��ѡ����Ҫ����������");
			return;
		}
	 contentForm.action="<%=request.getContextPath()%>/web/blacklist/approval.do?action=refuse";
     contentForm.submit();
}

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="onload();">
<table width="95%" border="0" align="center" cellspacing="1">
	<tr>
		<td>����λ�ã����ݺ���������-��������</td>
	</tr>
</table>
	<form name="contentListForm" action="<%=request.getContextPath()%>/web/blacklist/approval.do?action=showList" method="post"
		onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">

	<tr>
		<td colspan="4" align="center" class="title1">��������ѯ<input
			type="hidden" name="categoryID" value=""> <input
			type="hidden" name="htype" value="<%=type%>"></td>
	</tr>
	<tr>
		<td width="18%" align="right" class="text3">���ƣ�</td>
		<td width="30%" class="text4"><input type="text" name="name"
			value="<%=name%>"></td>
		<td width="18%" align="right" class="text3">�ṩ�̣�</td>
		<td width="30%" class="text4"><input type="text" name="spName"
			value="<%=spName%>"></td>
	</tr>
	<tr>
		<td width="18%" align="right" class="text3">����ID��</td>
		<td width="30%" class="text4"><input type="text" name="contentID"
			value="<%=contentID%>"></td>

		<td width="18%" align="right" class="text3">ÿҳչʾ��¼����</td>
		<td width="30%" class="text4"><input type="text" name="pageSize"
			value="<%=pageSize%>"></td>

	</tr>
	<tr>
		<td width="18%" align="right" class="text3">�������ͣ�</td>
		<td width="30%" class="text4">
			<select name="contentType">
				<option value="">ȫ��</option>
				<option value="M">MMӦ��</option>
				<option value="C">��ҵ����Ӧ��</option>
			</select>
		</td>
	<td width="18%" align="right" class="text3">����״̬</td>
    <td width="30%" class="text4">   
    <SELECT name=aprovalStatus> 
    <OPTION value="2">������</OPTION>
    <OPTION value="0" >�༭</OPTION>
    <OPTION value="3">������ͨ��</OPTION>
    <OPTION value="1" >�ѷ���</OPTION>
    <OPTION value="0,3" >�༭��������ͨ��</OPTION>
    <OPTION value="4">ȫ��</OPTION>
    </select>
    </td>
	</tr>
	<tr>
		<td colspan="4" align="center" class="text5"><input name="Submit"
			type="submit" class="input1" value="��ѯ"> <input name="reset"
			type="reset" class="input1" value="����"> </td>
	</tr>

</table>
</form>
<br>
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">

	<logic:empty name="PageResult" property="pageInfo">
		<tr>
			<td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
		</tr>
	</logic:empty>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="right" class="text1">
			<%
        HashMap params = new HashMap();
        params.put("name",name);
        //params.put("categoryID",request.getParameter("categoryID"));
        //params.put("type",type);
        params.put("spName",spName);
        params.put("contentType",contentType);
        //params.put("cateName",cateName);
        //params.put("icpCode",icpCode);
        //params.put("icpServId",icpServId);
        params.put("contentID",contentID);
        //params.put("contentTag",contentTag);
        //params.put("tagLogic",tagLogic);
        params.put("pageSize",pageSize);
        params.put("aprovalStatus",aprovalStatus);
        //params.put("deviceName",deviceName);
        //params.put("servAttr",servAttr);
        //params.put("cgyPath",cgyPath);
        %> <pager:pager name="PageResult"
				action="/web/blacklist/approval.do?action=showList" params="<%=params%>">
				<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="1" />
			</pager:pager></td>
		</tr>
	</table>
	<form name="contentForm" action="" method="post">
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">		
		<tr>
            <td width="5%" align="center" class="title1">ѡ��</td>
			<td width="6%" align="center" class="title1">����ID</td>
			<td width="13%" align="center" class="title1">����</td>
			<td width="12%" align="center" class="title1">�ṩ��</td>
			<td width="5%" align="center" class="title1">��������</td>
            <td width="7%" align="center" class="title1">��ʼʱ��</td>
			<td width="7%" align="center" class="title1">��ֹʱ��</td>
			<td width="5%" align="center" class="title1">����</td>
			<td width="5%" align="center" class="title1">��������</td>
			<td width="12%" align="center" class="title1">������ʱ��</td>
			<td width="5%" align="center" class="title1">������</td>
			<td width="12%" align="center" class="title1">����ʱ��</td>
			<td width="6%" align="center" class="title1">����״̬</td>
		</tr>

			<%String tmpStyle = "text5";%>
		<logic:iterate id="vo" indexId="ind" name="PageResult"
			property="pageInfo"
			type="com.aspire.ponaadmin.web.blacklist.vo.BlackListOperationVO">

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
                <td align="center"><input type="checkbox" name="dealContent" value="<bean:write name="vo" property="contentId" />" <%if(!"2".equals(vo.getBlacklistStatus())){ %> disabled="true" <%} %>></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="contentId" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="name" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="spName" /></td>
				<td align="center" style="word-break: break-all">
					<logic:equal name="vo" property="contentType" value="M">
						MMӦ��
					</logic:equal>
					<logic:equal name="vo" property="contentType" value="C">
						��ҵ����Ӧ��
					</logic:equal>
				</td>
				<td align="center" style="word-break: break-all">
				    <bean:write name='vo' property='startDate' /></td>
				<td align="center" style="word-break: break-all">
				    <bean:write name='vo' property='endDate' /></td>
				 
				<td align="center" style="word-break: break-all">
				    <logic:equal value="1" name="vo" property="type">�ڼ��ڲ�����</logic:equal> 
					<logic:equal value="2" name="vo" property="type">�ڼ�������</logic:equal> 
				</td>
				<td align="center" style="word-break: break-all">
				     <bean:write name='vo' property='operator' />
				</td>
				<td align="center" style="word-break: break-all">
				     <bean:write name='vo' property='operatorTime' />
				</td>	
				<td align="center" style="word-break: break-all">
				     <bean:write name='vo' property='approval' />
				</td>
				<td align="center" style="word-break: break-all">
				    <bean:write name='vo' property='approvalTime' />
				</td>	
				 <td align="center" style="word-break: break-all">
				    <%if("3".equals(vo.getBlacklistStatus())){ %>
	                       ������ͨ��
	   <%}else if("1".equals(vo.getBlacklistStatus())){ %>
	              �����ɹ�
	   <%}else if("2".equals(vo.getBlacklistStatus())){ %>
	              ������
	   <%}else{ %>
	         �༭
	   <%} %>
				 </td>			
			</tr>
		</logic:iterate>
	</table>
</form>
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
           <td width="24%" class="text1"><input type="checkbox" name="checkAll" value="checkbox" onClick="selectAll(contentForm,'dealContent',this.checked);">
                                      ȫ��ѡ��
            </td>
			<td align="right" class="text1"><pager:pager name="PageResult"
				action="/web/blacklist/approval.do?action=showList" params="<%=params%>">
				<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="2" />
			</pager:pager></td>
		</tr>
	</table>
</logic:notEmpty>
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5">
		    <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonOperation" type="button" class="input1" onClick="return approvalBy();" value="����ͨ��"></logic:notEmpty>
		    <logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonOperation" type="button" class="input1" onClick="return approvalNotGo();" value="������ͨ��"></logic:notEmpty>
		</td>
	</tr>
</table>
</body>
</html>
