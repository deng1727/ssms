<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="name" name="name"  value=""/>
<bean:parameter id="spName" name="spName"  value=""/>
<bean:parameter id="icpServId" name="icpServId"  value="" />
<bean:parameter id="icpCode" name="icpCode"  value="" />
<bean:parameter id="contentID" name="contentID"  value="" />
<bean:parameter id="type" name="type"  value=""/>
<bean:parameter id="provider" name="provider"  value="" />
<bean:parameter id="cateName" name="cateName"  value=""/>
<bean:parameter id="subtype" name="subtype"  value="" />
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã����ܹ�������----Ӧ����չ���Թ���</td>
  </tr>
</table>
<form name="contentListForm" action="contentList.do" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td colspan="4" align="center" class="title1">��Դ��ѯ</td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">���ƣ�</td>
    <td width="30%" class="text4"><input type="text" name="name" value='<bean:write  name="name" />'>
		<td width="18%" align="right" class="text3">�ṩ�̣�</td>
    <td width="30%" class="text4"><input type="text" name="spName" value='<bean:write  name="spName" />'></td>
  </tr>
	<tr>
    <td width="18%" align="right" class="text3">ҵ����룺</td>
    <td width="30%" class="text4"><input type="text" name="icpServId" value='<bean:write  name="icpServId" />'></td>
    <td width="18%" align="right" class="text3">��ҵ���룺</td>
    <td width="30%" class="text4"><input type="text" name="icpCode" value='<bean:write  name="icpCode" />'></td>
  </tr>
	<tr>
    <td width="18%" align="right" class="text3">�������ͣ�</td>
    <td width="30%" class="text4"><input type="radio" name="provider" value="O" <logic:notEqual name="provider" value="B"> checked="checked"</logic:notEqual> onClick="javascript:mmProvider();" />MM����ҵ��  <input type="radio" name="provider" value="B" <logic:equal name="provider" value="B"> checked="checked"</logic:equal> onClick="javascript:gameProvider();" />��Ϸ����ҵ��</td>
    <td width="18%" align="right" class="text3">�������ͣ�</td>
    <td width="30%" class="text4">
    <SELECT style="width=100pt" name="cateName" id="cateName"  <logic:equal name="provider" value="B">disabled="true"</logic:equal>> 
    <OPTION value="" <logic:equal name="cateName" value="">selected="selected"</logic:equal> ></OPTION>
    <OPTION value="1" <logic:equal name="cateName" value="1">selected="selected"</logic:equal> >���</OPTION>
    <OPTION value="2" <logic:equal name="cateName" value="2">selected="selected"</logic:equal> >��Ϸ</OPTION>
    <OPTION value="3" <logic:equal name="cateName" value="3">selected="selected"</logic:equal> >����</OPTION>
    </SELECT></td>
  </tr>
	<tr>
    <td width="18%" align="right" class="text3">���ݳ�����</td>
    <td width="30%" class="text4">
    <SELECT style="width=100pt"  name="subtype" id="subtype" <logic:equal name="provider" value="B">disabled="true"</logic:equal>> 
    <OPTION value="1"  <logic:equal name="subtype" value="1">selected="selected"</logic:equal> >MM��ͨӦ��</OPTION>
    <OPTION value="2"  <logic:equal name="subtype" value="2">selected="selected"</logic:equal> >WIDGETӦ��</OPTION>
    <OPTION value="5"  <logic:equal name="subtype" value="5">selected="selected"</logic:equal> >JILӦ��</OPTION>
    <OPTION value="6"  <logic:equal name="subtype" value="6">selected="selected"</logic:equal> >MM����Ӧ��</OPTION>
    <OPTION value="7"  <logic:equal name="subtype" value="7">selected="selected"</logic:equal> >����Ӧ��</OPTION>
    <OPTION value="10" <logic:equal name="subtype" value="10">selected="selected"</logic:equal> >OVIӦ��</OPTION>
    <OPTION value="11" <logic:equal name="subtype" value="11">selected="selected"</logic:equal> >�ײ�</OPTION>
    <OPTION value="12" <logic:equal name="subtype" value="12">selected="selected"</logic:equal>>MOTO</OPTION>
    </SELECT></td>
    <td width="18%" align="right" class="text3">����ID��</td>
    <td width="30%" class="text4"><input type="text" name="contentID" value='<bean:write  name="contentID" />'></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">ÿҳչʾ��¼����</td>
    <td width="30%" class="text4" ><input type="text" name="pageSize" value="<bean:write  name="pageSize"  />"  ></td>
    <td width="18%" align="right" class="text3"></td>
    <td width="30%" class="text4"></td>
  </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit" type="submit" class="input1" value="��ѯ">
    <input name="a" type="button" class="input1" value="����" onClick="javascript:resetFrom();">
  </tr>
</table>
</form>


<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">��Դ���ݹ���</td>
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
        java.util.HashMap params = new java.util.HashMap();
       params.put("name",name);
       params.put("spName",spName);
       params.put("icpServId",icpServId);
       params.put("icpCode",icpCode);
       params.put("provider",provider);
       params.put("cateName",cateName);
       params.put("subtype",subtype);
       params.put("contentID",contentID);
       params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/datafield/contentList.do" params="<%=params%>">
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
    <td width="6%" align="center" class="title1">ID</td>
    <td width="24%" align="center" class="title1">����</td>
    <td width="20%" align="center" class="title1">�ṩ��</td>
    <td width="8%" align="center" class="title1">���ݳ���</td>
    <td width="20%" align="center" class="title1">���ݱ�ǩ</td>
    <td width="8%" align="center" class="title1">��������</td>
    <td width="14%" align="center" class="title1">����ʱ��</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.datafield.vo.ContentVO">
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
    <td align="center"><a href="contentInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="id"/></a></td>
    <td align="center"><a href="contentInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="name"/></a></td>
    <td align="center"><bean:write name="content" property="spName"/></td>
    <td align="center"><logic:equal name="content" property="subType" value="">��Ϸ����ҵ��</logic:equal>  
    	<logic:equal name="content" property="subType" value="1">MM��ͨӦ��</logic:equal>  
			<logic:equal name="content" property="subType" value="2">WIDGETӦ��</logic:equal> 
			<logic:equal name="content" property="subType" value="5">JILӦ��</logic:equal>
			<logic:equal name="content" property="subType" value="6">MM����Ӧ��</logic:equal> 
			<logic:equal name="content" property="subType" value="7">����Ӧ��</logic:equal> 
			<logic:equal name="content" property="subType" value="10">OVIӦ��</logic:equal> 
			<logic:equal name="content" property="subType" value="11">�ײ�</logic:equal> 
			<logic:equal name="content" property="subType" value="12">MOTO</logic:equal> 
    </td>
    <td align="center" style="word-break:break-all;"><script language="JavaScript">
	   	 var str = '<bean:write name="content" property="keywordsFormat"/>';
	   	 if(str.length > 16 ){
	   	 var trunckedStr = str.substring(0,16)+'...';
	   	 }else{
	   	 var trunckedStr = str;
	   	 }
			document.write(trunckedStr);
		</script></td>
    <td align="center"><bean:write name="content" property="cateName"/></td>
    <td align="center"><bean:write name="content" property="marketdate"/></td>
  </tr>
  </logic:iterate>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/datafield/contentList.do" params="<%=params%>">
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
<script language="JavaScript">
<!--
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
    var Page_MIN=1;
    var Page_MAX=100;
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

function resetFrom()
{
	contentListForm.name.value = "";
	contentListForm.spName.value = "";
	contentListForm.icpServId.value = "";
	contentListForm.icpCode.value = "";
	contentListForm.contentID.value = "";
	document.getElementById('provider').checked=true;
	document.getElementById('cateName').disabled=false;
	document.getElementById('cateName').options[0].selected=true;
	document.getElementById('subtype').disabled=false;
	document.getElementById('subtype').options[0].selected=true;
	contentListForm.pageSize.value = "12";
}

function mmProvider()
{
	document.getElementById('subtype').disabled=false;
	document.getElementById('cateName').disabled=false;
}

function gameProvider()
{
	document.getElementById('subtype').disabled=true;	
	document.getElementById('cateName').disabled=true;	
}

//-->
</script>