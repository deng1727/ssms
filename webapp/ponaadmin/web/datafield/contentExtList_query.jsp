<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="name" name="name"  value=""/>
<bean:parameter id="spName" name="spName"  value=""/>
<bean:parameter id="contentID" name="contentID"  value="" />
<bean:parameter id="type" name="type"  value=""/>
<bean:parameter id="isrecomm" name="isrecomm"  value=""/>
<bean:parameter id="date" name="date"  value=""/>
<bean:parameter id="pageSize" name="pageSize"  value="12" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã����ܹ�������----Ӧ�û���Թ���</td>
  </tr>
</table>
<form name="contentExtListForm" action="contentExtList.do" method="post" onSubmit="return checkPageSize(contentExtListForm.pageSize);">
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
    <td width="18%" align="right" class="text3">����ID��</td>
    <td width="30%" class="text4"><input type="text" name="contentID" value='<bean:write  name="contentID" />'></td>
    <td width="18%" align="right" class="text3">�������ͣ�</td>
    <td width="30%" class="text4"> <SELECT style="width=100pt"  name="type" >
	<OPTION value=""  <logic:equal name="type" value="">selected="selected"</logic:equal> >ȫ��</OPTION>
    <OPTION value="1"  <logic:equal name="type" value="1">selected="selected"</logic:equal> >�ۿ�</OPTION>
    <OPTION value="2"  <logic:equal name="type" value="2">selected="selected"</logic:equal> >��ɱ</OPTION>  
    <OPTION value="3"  <logic:equal name="type" value="3">selected="selected"</logic:equal> >��ʱ���</OPTION>
     <OPTION value="4"  <logic:equal name="type" value="4">selected="selected"</logic:equal> >��Լ����</OPTION>
    </SELECT></td>
    </tr>
	<tr>
     <td width="18%" align="right" class="text3">�Ƿ��Ƽ���</td>
    <td width="30%" class="text4"> <SELECT style="width=100pt"  name="isrecomm" >
	<OPTION value=""  <logic:equal name="isrecomm" value="">selected="selected"</logic:equal> >ȫ��</OPTION>
    <OPTION value="0"  <logic:equal name="isrecomm" value="0">selected="selected"</logic:equal> >���Ƽ�</OPTION>
    <OPTION value="1"  <logic:equal name="isrecomm" value="1">selected="selected"</logic:equal> >�Ƽ�</OPTION>  
    </SELECT></td>
    <td width="18%" align="right" class="text3">���ڣ�</td>
    <td width="30%" class="text4"><input name="date" id="date" type="text"  class="Wdate" value='<bean:write  name="date" />'  style="width:100px;cursor:hand;" onFocus="new WdatePicker(this,'%Y-%M-%D',false);"  /></td>
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

<form name="contentForm" action="" method="post">
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
		HashMap params = new HashMap();
       params.put("name",name);
       params.put("spName",spName);
       params.put("type",type);
       params.put("contentID",contentID);
       params.put("isrecomm",isrecomm);
       params.put("date",date);
       params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/datafield/contentExtList.do" params="<%=params%>">
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
	<td width="3%" align="center" class="title1">ѡ��</td>
    <td width="3%" align="center" class="title1">ID</td>
    <td width="5%" align="center" class="title1">����ID</td>
    <td width="20%" align="center" class="title1">����</td>
    <td width="15%" align="center" class="title1">�ṩ��</td>
    <td width="7%" align="center" class="title1">��������</td>
    <td width="7%" align="center" class="title1">�Ƿ��Ƽ�</td>
    <td width="5%" align="center" class="title1">�ۿ���</td>
    <td width="25%" align="center" class="title1">ʱ������</td>
    <td width="10%" align="center" class="title1">����ʱ��</td>
  </tr>

  <%String tmpStyle = "text5";%>
  <logic:iterate id="content" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.datafield.vo.ContentExtVO">
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
	<td align="center"><input type="checkbox" name="idDel" value="<bean:write name="content" property="id"/>"></td>
    <td align="center">
	    <logic:equal name="content" property="type" value="4">
	    <a href="contentExtInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="id"/></a>
	    </logic:equal> 
	    <logic:notEqual name="content" property="type" value="4">
	    <bean:write name="content" property="id"/>
	    </logic:notEqual> 
	</td>
	
    <td align="center"><bean:write name="content" property="contentID"/></td>
    	
    <td align="center">
		<logic:equal name="content" property="type" value="4">
		<a href="contentExtInfo.do?id=<bean:write name="content" property="id"/>"><bean:write name="content" property="name"/></a>
		</logic:equal> 
		<logic:notEqual name="content" property="type" value="4">
		<bean:write name="content" property="name"/>
		</logic:notEqual> 
		
	</td>
    
    <td align="center"><bean:write name="content" property="spName"/></td>
    <td align="center"><logic:equal name="content" property="type" value="1">�ۿ�</logic:equal>  
    	<logic:equal name="content" property="type" value="2">��ɱ</logic:equal> 
    	<logic:equal name="content" property="type" value="3">��ʱ���</logic:equal> 
    	<logic:equal name="content" property="type" value="4">��Լ����</logic:equal> 
    </td>
    <td align="center"><logic:equal name="content" property="isrecomm" value="0">���Ƽ�</logic:equal>  
    	<logic:equal name="content" property="isrecomm" value="1">�Ƽ�</logic:equal> 
    </td>
    <td align="center"><bean:write name="content" property="discount"/></td>
    <td align="center"><bean:write name="content" property="dateStartStr"/> <bean:write name="content" property="timeStart"/>��<bean:write name="content" property="dateEndStr"/> <bean:write name="content" property="timeEnd"/></td>
    <td align="center"><bean:write name="content" property="lupDate"/></td>
  </tr>
  </logic:iterate>

</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr><td width="24%" class="text1">&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'idDel',this.checked,false);"> ȫ��ѡ��</td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/datafield/contentExtList.do" params="<%=params%>">
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
	<logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1" onClick="contentExtDel();" value="ɾ��"></logic:notEmpty>
	<input name="buttonDel" type="button" class="input1" onClick="contentExtAdd();" value="��������">
	<input name="buttonExport" type="button" class="input1" onClick="contentExtImport();" value="��������">
		<input name="buttonExport" type="button" class="input1" onClick="contentExtSyn();" value="�����ͬ�������¸���">
	</td>
	</tr>
</table>
</form>
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
	contentExtListForm.name.value = "";
	contentExtListForm.spName.value = "";
	contentExtListForm.contentID.value = "";
	document.getElementById('type').options[0].selected=true;
	document.getElementById('isrecomm').options[0].selected=true;
	contentExtListForm.pageSize.value = "12";
}


function contentExtDel()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"idDel"))
  {
    alert("����ѡ��Ҫɾ������Դ��");
    return false;
    
  }
  if(confirm("��ȷ��Ҫ����ɾ��������"))
  {
    contentForm.action="extDel.do";
    contentForm.submit();
  }
}

function contentExtAdd()
{
    contentForm.action="contentExtAdd.do";
    contentForm.submit();
}

function contentExtImport()
{
    window.location.href="contentExtImport.jsp";
}
function contentExtSyn()
{
     window.location.href="contentExtsynMess.jsp";
  
}
//-->
</script>