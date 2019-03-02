<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.dotcard.syncData.tactic.TacticVO" %>
<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@page import="java.util.List"%>
<%
TacticVO vo = (TacticVO)request.getAttribute("tacticVO");
String contentType ="all";
String umFlag = "Z";
int relation = 0;
if(null != vo)
{
	contentType = vo.getContentType();
	umFlag = vo.getUmFlag();
	relation = vo.getTagRelation();
}
List appCateNameList_1=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_1");
List appCateNameList_2=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_2");
List appCateNameList_3=CategoryUpdateConfig.getInstance().getNodeValueList("appCateName_3");
pageContext.setAttribute("appCateNameList_1", appCateNameList_1, PageContext.PAGE_SCOPE);
pageContext.setAttribute("appCateNameList_2", appCateNameList_2, PageContext.PAGE_SCOPE);
pageContext.setAttribute("appCateNameList_3", appCateNameList_3, PageContext.PAGE_SCOPE);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�����Դ������</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self"></head>
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">
function checkForm()
{
  var tag = eval(modTacticForm.contentTag).value;
  var relation = eval(modTacticForm.tagRelation).value;
  if( (getLength(tag) == 0 && relation !=0) || (getLength(tag) != 0 && relation ==0) ) 
  {
  	alert("��ͬʱ����ǩ�ͱ�ǩ��ϵ��ֵ��");
  	return false;
  }
  
  if(!(checkContent1(modTacticForm.contentTag)))
  {
    alert("��ǩֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ����ֺš�");
    modTacticForm.contentTag.focus();
    return false;
  }
  
  if(!(checkContent(modTacticForm.appCateName,"REG_CHINESE_NUM_LERTER_UNDERLINE")))
  {
    alert("Ӧ�÷�������ֻ�ܰ������֡�Ӣ����ĸ�����ֺ��»��ߣ�");
    modTacticForm.appCateName.focus();
    return false;
  }
  
  return true;
  
}
function goBack()
{
	modTacticForm.action="<%=request.getContextPath()%>/web/systemmgr/tactic.do?subSystem=ssms";
    modTacticForm.submit();
}
function checkContent1(field)
{
    var value = eval(field).value;
    //���Ϊ�գ��������ݼ���
    if( getLength(value) == 0) 
    {
    	return true;
    }
    var reg = /^[a-zA-Z0-9;\u4e00-\u9fa5]+$/g    
    return reg.test(value);
}

function selectAppCate(obj)
{
	var selectHtml = '<SELECT name="appCateName">';
	
	for(var i=0; i<obj.options.length; i++)
	{
		if(obj.options[i].selected == true)
		{
			if(obj.options[i].value == 'all')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
		    	<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appSoftWare')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appTheme')
			{
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appGame')
			{
				<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
		}
	}
	selectHtml = selectHtml + '</SELECT>'
	document.getElementById('appCateName').innerHTML = selectHtml;
}

function init()
{
	var obj = modTacticForm.contentType;
	var selectHtml = '<SELECT name="appCateName">';
	
	for(var i=0; i<obj.options.length; i++)
	{
		if(obj.options[i].selected == true)
		{
			if(obj.options[i].value == 'all')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
		    	<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appSoftWare')
			{
				<logic:iterate id="appCateName" name="appCateNameList_1" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appTheme')
			{
				<logic:iterate id="appCateName" name="appCateNameList_2" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';
				</logic:iterate>
			}
			else if(obj.options[i].value == 'nt:gcontent:appGame')
			{
				<logic:iterate id="appCateName" name="appCateNameList_3" type="com.aspire.ponaadmin.web.category.RecordVO" >
					selectHtml = selectHtml + '<option value="<bean:write name="appCateName" property="key"/>">'
								+'<bean:write name="appCateName" property="value"/>'
								+'</option>';				
				</logic:iterate>
			}
		}
	}
	selectHtml = selectHtml + '</SELECT>'
	document.getElementById('appCateName').innerHTML = selectHtml;
	
	var appCateName = "<bean:write name="tacticVO" property="appCateName"/>";
	var select_appCateName = modTacticForm.appCateName;
	
	for(var i=0; i<select_appCateName.length; i++)
	{
		if(select_appCateName.options[i].value==appCateName)
		{
			select_appCateName.options[i].selected=true;
			break;
		}
	}
}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="init();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td align="center" class="title1">
      ����ͬ�������޸�
    </td>
  </tr> 
</table>

<table width="95%"  border="0" align="center" cellspacing="1" >
  <form name="modTacticForm" action="tactic.do?subSystem=ssms&action=modify" method="post" onSubmit="return checkForm();">
  <input type = "hidden" name = "id" value ="<bean:write name="tacticVO" property="id"/>">
  <input type = "hidden" name = "categoryID" value ="<bean:write name="tacticVO" property="categoryID"/>">
  <input type = "hidden" name="umFlag" value="0" />
  <input type = "hidden" name="contentTag" value="" />
  <input type = "hidden" name="tagRelation" value="0" />
  <tr>
  	<td width="30%" align="right" class="text3">����·����</td>
  	<td width="30%" class="text4"><bean:write name="category" property="namePath"/></td>
  </tr>
  <tr>
    <td width="30%" align="right" class="text3">�������ͣ�</td>
    <td width="30%" class="text4">  
    <SELECT name=contentType onchange="selectAppCate(this)"> 
    <OPTION value="all" <%if(contentType.equalsIgnoreCase("all")){%> selected <%}%>>ȫ��</OPTION>
    <OPTION value="nt:gcontent:appGame" <%if(contentType.equalsIgnoreCase("nt:gcontent:appGame")){%> selected <%}%>>��Ϸ</OPTION>
    <OPTION value="nt:gcontent:appTheme" <%if(contentType.equalsIgnoreCase("nt:gcontent:appTheme")){%> selected <%}%>>����</OPTION>            
    <OPTION value="nt:gcontent:appSoftWare" <%if(contentType.equalsIgnoreCase("nt:gcontent:appSoftWare")){%> selected <%}%>>���</OPTION>            
    </SELECT>
    </td>
  </tr>
  <tr>
    <td width="30%" align="right" class="text3">Ӧ�÷��ࣺ</td>
    <td width="30%" class="text4" id="appCateName">
    	<input type="text" name="appCateName" value="<bean:write name="tacticVO" property="appCateName"/>" maxLength="40">
	</td>
  </tr>
 <tr>
    <td width="30%" align="right" class="text3">����ʱ�䣺</td>
    <td width="30%" class="text4"><bean:write name="tacticVO" property="creatTime"/></td>
    </tr><tr>
    <td width="30%" align="right" class="text3">����޸�ʱ�䣺</td>
    <td width="30%" class="text4"><bean:write name="tacticVO" property="lastUpdateTime"/></td>
 </tr>
  <tr>
    <td colspan="2" align="center" class="text5">
    <input name="Submit" type="submit" class="input1" value="ȷ��">
    <input name="button1" type="button" class="input1" value="����" onClick="goBack();">
  </tr>
  </form>
</table>
</body>
</html>
