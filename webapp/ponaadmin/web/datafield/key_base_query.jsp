<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<bean:parameter id="keyid" name="keyid"  value=""/>
<bean:parameter id="keyname" name="keyname"  value=""/>
<bean:parameter id="keytable" name="keytable"  value="" />
<bean:parameter id="keydesc" name="keydesc"  value="" />
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
    <td>����λ�ã����ܹ�������----��չ���Թ���</td>
  </tr>
</table>
<form name="keybaseForm" action="keyBaseList.do" method="post" onSubmit="return checkPageSize(keybaseForm.pageSize);">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  
  <tr>
    <td colspan="4" align="center" class="title1">��չ���Բ�ѯ</td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">KEYID��</td>
    <td width="30%" class="text4"><input type="text" name="keyid" value='<bean:write  name="keyid" />'>
		<td width="18%" align="right" class="text3">������</td>
    <td width="30%" class="text4">
		<SELECT name="keytable" id="keytable"> 
		<OPTION value="">ȫ��</OPTION>		
    </SELECT></td>
  </tr>
	<tr>
    <td width="18%" align="right" class="text3">�ֶ�����</td>
    <td width="30%" class="text4"><input type="text" name="keyname" value='<bean:write  name="keyname" />'></td>
    <td width="18%" align="right" class="text3">�ֶ�������</td>
    <td width="30%" class="text4"><input type="text" name="keydesc" value='<bean:write  name="keydesc" />'></td>
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

<form name="listForm" action="" method="post">
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
       params.put("keyid",keyid);
       params.put("keyname",keyname);
       params.put("keytable",keytable);
       params.put("keydesc",keydesc);
       params.put("pageSize",pageSize);
        %>
        <pager:pager name="PageResult" action="/web/datafield/keyBaseList.do" params="<%=params%>">
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
    <td width="10%" align="center" class="title1">KEYID</td>
    <td width="25%" align="center" class="title1">����</td>
    <td width="20%" align="center" class="title1">�ֶ���</td>
    <td width="20%" align="center" class="title1">�ֶ�����</td>
  </tr>

  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
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
  	<td align="center"><input type="checkbox" name="keyBaseDel" value="<bean:write name="vo" property="keyid"/>"></td>
    <td align="center"><a href="keyBaseInfo.do?keyid=<bean:write name="vo" property="keyid"/>"><bean:write name="vo" property="keyid"/></a></td>
    <td align="center" id="keytableTd"><bean:write name="vo" property="keytable"/></td>
    <td align="center" ><a href="keyBaseInfo.do?keyid=<bean:write name="vo" property="keyid"/>"><bean:write name="vo" property="keyname"/></a></td>
    <td align="center"><bean:write name="vo" property="keydesc"/></td>
  </tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr> <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(listForm,'keyBaseDel',this.checked,false);">
      ȫ��ѡ��
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/datafield/keyBaseList.do" params="<%=params%>">
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
	<logic:notEmpty name="PageResult" property="pageInfo">
	<input name="buttonDel" type="button" class="input1" onClick="kBDel();" value="ɾ��">
	</logic:notEmpty>
	<input name="buttonDel" type="button" class="input1" onClick="keyBaseAdd();" value="����">
	<input name="buttonDel" type="button" class="input1" onClick="keyBaseImput();" value="��������">
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
	keybaseForm.keyid.value = "";
	keybaseForm.keyname.value = "";
	document.getElementById('keytable').options[0].selected=true;
	keybaseForm.keydesc.value = "";
	keybaseForm.pageSize.value = "12";
}



var keytableObj=document.getElementById("keytable");
var keytableStr="";
var keytableArr= new Array(); 

<logic:iterate id="keytablename" indexId="ind" name="keytableArr" >
keytableStr='<bean:write name="keytablename"/>';
keytableArr=keytableStr.split('|');
keytableObj.options.add(new Option(keytableArr[1],keytableArr[0]));
</logic:iterate>

for(var i=0;i<keytableObj.length;i++)
{   
	if(keytableObj.options[i].value=='<bean:write  name="keytable" />')
	{
		keytableObj.options[i].selected=true;
	}
}


var tds = document.getElementsByName('keytableTd');
for (var i=0;i<tds.length;i++)
{
	for(var j=0;j<keytableObj.length;j++)
	{   
		if(keytableObj.options[j].value==tds[i].innerText)
		{
			tds[i].innerText=keytableObj.options[j].text;
		}
	}
}

function kBDel()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(listForm,"keyBaseDel"))
  {
    alert("����ѡ��Ҫɾ������Դ��");
    return false;
    
  }
  if(confirm("��ȷ��Ҫ����ɾ��������"))
  {
    listForm.action="keyBaseDel.do";
    listForm.submit();
  }
}
function keyBaseAdd()
{
    listForm.action="keyBaseAdd.do";
    listForm.submit();
}
function keyBaseImput()
{
    listForm.action="keyBaseImput.do";
    listForm.submit();
}
//-->
</script>