<%@ page contentType="text/html; charset=gb2312"%>
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
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));
String contentType = Validator.filter(request.getParameter("contentType")==null?"":request.getParameter("contentType"));

String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;
String backURL = "cgyNotContentList.do?subSystem=ssms&categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;
backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag 
                  + "&tagLogic=" + tagLogic+"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;

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
<title>������ݵ�����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
function addBlack()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"ctid"))
  {
    alert("����ѡ��Ҫ��ӵ����ܵ����ݣ�");
    return false;
    
  }
  
  var tmp = document.getElementsByName('ctid');
  var tmpCt="";
  for(var i=0;i<tmp.length;i++){
	  if(tmp[i].checked){
		  tmpCt += tmp[i].value;
		  tmpCt +=",";
	  }
  }
  tmpCt = tmpCt.substring(0,tmpCt.length-1);

  window.returnValue=tmpCt;
  window.close();
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
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="onload();">

<form name="contentListForm" action="ctList.do" method="post"
	onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">

	<tr>
		<td colspan="4" align="center" class="title1">���ݲ�ѯ</td>
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
		<td width="30%" class="text4" colspan="3">
			<select name="contentType">
				<option value="">ȫ��</option>
				<option value="M">MMӦ��</option>
				<option value="C">��ҵ����Ӧ��</option>
			</select>
		</td>
	</tr>

	<tr>
		<td colspan="4" align="center" class="text5"><input
			name="querySubmit" type="submit" class="input1" value="��ѯ"> <input
			name="reset" type="reset" class="input1" value="����"></td>
	</tr>

</table>
</form>
<br>
<form name="contentForm" action="" method="post">
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="title1">������Ŀ����----�������</td>
	</tr>
	<logic:empty name="PageResult" property="pageInfo">
		<tr>
			<logic:equal value="0" name="exeType">
				<td align="center"><font color="#ff0000">��ѡ��������ѯ</font></td>
			</logic:equal>
			<logic:notEqual value="0" name="exeType">
				<td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
			</logic:notEqual>
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
        params.put("spName",spName);
        params.put("contentID",contentID);
        params.put("contentType",contentType);
        params.put("pageSize",pageSize);
        %> <pager:pager name="PageResult"
				action="/web/blacklist/ctList.do" params="<%=params%>">
				<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="1" />
			</pager:pager></td>
		</tr>
	</table>
	
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td width="5%" align="center" class="title1">ѡ��</td>
			<td width="10%" align="center" class="title1">����ID</td>
			<td width="15%" align="center" class="title1">����</td>
			<td width="10%" align="center" class="title1">�ṩ��</td>
			<td width="10%" align="center" class="title1">��������</td>
			<td width="15%" align="center" class="title1">����ʱ��</td>

		</tr>

		<%String tmpStyle = "text5";%>
		<logic:iterate id="vo" indexId="ind" name="PageResult"
			property="pageInfo"
			type="com.aspire.ponaadmin.web.blacklist.vo.ContentVO">

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
				<td align="center"><input type="checkbox" name="ctid"
					value="<bean:write name="vo" property="contentId"/>"></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="contentId" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="name" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="spName" /></td>
				<td align="center" style="word-break: break-all">
					<logic:equal name="vo" property="subType" value="M">
						MMӦ��
					</logic:equal>
					<logic:equal name="vo" property="subType" value="C">
						��ҵ����Ӧ��
					</logic:equal>
				</td>
				<td align="center" style="word-break: break-all"><bean:write
					name='vo' property='marketDate' /></td>

			</tr>
		</logic:iterate>
	</table>

	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td width="24%" class="text1"><input type="checkbox"
				name="checkbox" value="checkbox"
				onClick="selectAllCB(contentForm,'ctid',this.checked,false);">
			ȫ��ѡ��</td>
			<td align="right" class="text1"><pager:pager name="PageResult"
				action="/web/blacklist/ctList.do" params="<%=params%>">
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
	</form>
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><input name="ad" type="button"
			class="input1" onClick="addBlack();" value="ȷ��" /> <input name="ad"
			type="button" class="input1" onClick="window.close();" value="�ر�" />
		</td>
	</tr>
</table>
</body>
</html>


