<%@ page contentType="text/html; charset=gb2312" %>
<%@ page language="java"   %>   
<%@page import="com.aspire.ponaadmin.web.category.intervenor.IntervenorVO"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ taglib uri="message" prefix="msg" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ������Ϣ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" onload="into();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">�˹���Ԥ������Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <form name="ContentForm" action="intervenorView.do" method="post">
  <input type="hidden" name="id" value="<bean:write name="intervenorVO" property="id"/>" />
  <input type="hidden" name="contentId" value="" />
  <input type="hidden" name="actionType" value="" />
  <input type="hidden" name="startSortId" value="" />
  <input type="hidden" name="endSortId" value="" />
  <input type="hidden" name="oldName" value="<bean:write name="intervenorVO" property="name"/>" />
  
  <tr>
    <td width="20%" align="right" class="text3">
    	��ǰ����:
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="name" value="<bean:write name="intervenorVO" property="name"/>" />
    </td>
    <td width="50%" align="center" class="text4"> 
    	<input type="button" value="ɾ��������" onclick="delIntervenor();">  
    	<input type="button" value="�����������Ϣ���޸�" onclick="editIntervenor();">  
    </td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	�趨��������λ��:
    </td>
    <td class="text4" colspan="2">
    	<select name="isselect" onchange="selectSort();">
    		<option value="0">�ֶ�����</option>
    		<option value="-1">���񵥶���</option>
    		<option value="-2">���񵥵ײ�</option>
    	</select>
    	&nbsp;
		<input type="text" name="sortId" 
		value=""  maxlength="5"
		onkeydown="if(event.keyCode==13) event.keyCode=9"
		onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false">
		&nbsp;&nbsp;&nbsp;
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	����������Чʱ������:
    </td>
    <td class="text4" colspan="2">
    	<input name="startDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="startDate"/>"
			readonly="true" />
			&nbsp;��&nbsp;
		<input name="endDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="endDate"/>"
			readonly="true" />
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	������Ʒ������:
    </td>
    <td colspan="2" class="text4">
    	<input type="button" value="����ƷĿ¼����ȡ" onclick="addContentId();">
	</td>
  </tr>
  </form>
  <form action="intervenorView.do?actionType=importFile&id=<bean:write name="intervenorVO" property="id"/>" name="form1" method="post" enctype="multipart/form-data">
  <tr>
    <td width="20%" align="right" class="text3">
    	�������������ļ�:
    </td>
    <td colspan="2" class="text4">
    	<input type="file" name="dataFile">(��ѡ��excel�����ļ�,����һ�б�ʾ�������ݵ�����)
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    </td>
    <td colspan="2" class="text4">
    	<input type="submit" name="genLogo" value="�������������ļ�" onclick="return import_content()">
    	<b><font color=red>ע�⣺���������ɾ���������µ�����ԭ����.�����ز���!</font></b>
	</td>
  </tr>
  </form>
</table>
<br>
<br>

<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     �Ѽ�����Ʒ�б�
    </td>
  </tr>
</table>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">��Ʒ����</td>
	  <td align="center" class="title1">��Ʒ����</td>
	  <td align="center" class="title1">�ṩ��</td>
	  <td align="center" class="title1">����ʱ��</td>
	  <td align="center" class="title1" colspan="2">����</td>
	</tr>
	<%String tmpStyle = "text5";%>
	<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.intervenor.gcontent.IntervenorGcontentVO">
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
				<bean:write name="vo" property="name"/>
			</td>
			<td align="center">
				<bean:write name="vo" property="contentId"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="spName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="marketDate"/>
      		</td>
			<td align="center" style="word-break:break-all;">
				<input id="<bean:write name="vo" property="id"/>" type="text" 
				name="conSortId" 
				value="<bean:write name="vo" property="sortId"/>" size="5" maxlength="4"
				onkeydown="if(event.keyCode==13) event.keyCode=9"
				onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
			</td>
			<td align="center">
				<a href="#" onclick="delContent('<bean:write name="vo" property="id"/>');">
					���������ͷ�
				</a>
			</td>
		</tr>
	</logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("id",request.getParameter("id"));
        params.put("actionType",request.getParameter("actionType"));
        %>
        <pager:pager name="PageResult" action="/web/intervenor/intervenorView.do" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC" >
      	<td align="center">
          <input type="button" value="��������" onclick="setSortIdNew()"/>
        </td>
      </tr>
</table>
</logic:notEmpty>
<script language="javascript">
function import_content()
{

	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("��ѡ��Ҫ�������ݵ��ļ���");
		return false;
	}
	 
	if(!isFileType(filePath,"xls"))
	{
		alert("��ѡ��xls��ʽ�������ļ�!");
		return false;
	}
	
	if(!confirm('ȷ��Ҫ�����˹���Ԥ����������'))
	{
	   return false;
	}
	return true;
}

function isFileType(filename,type)
{
   var pos = filename.lastIndexOf(".");

   if(pos<0) return false;

   var fileType = filename.substr(pos+1);

   if(fileType.toUpperCase()!=type.toUpperCase()) return false;

   return true;
}

function into()
{
	var start = '<bean:write name="intervenorVO" property="startSortId"/>';
	var end = '<bean:write name="intervenorVO" property="endSortId"/>';
	
	ContentForm.sortId.value = start;
	var iss = ContentForm.isselect;
		
	for(var i=0; i<iss.length; i++)
	{
		if(iss.options[i].value == start)
		{
			iss.options[i].selected=true;
			ContentForm.sortId.style.display="none";
			break;
		}
	}
	
	<%
	String isReload = request.getParameter("isReload");
	if(isReload != null && "yes".equals(isReload))
	{
	%>
	var href = "parent.listframe.location='intervenorListView.do?actionType=list'";
	window.setTimeout(href,0);
	window.status="���";
	<%
	}
	%>
}

function selectSort()
{
	var iss = ContentForm.isselect.value;
	
	if(iss!=0)
	{
		ContentForm.sortId.style.display="none";
	}
	else
	{
		ContentForm.sortId.style.display="";
		ContentForm.sortId.value='1';
	}
}

function delIntervenor(id)
{
	if(!confirm('ȷ��Ҫɾ�����˹���Ԥ������'))
	{
	   return false;
	}
	ContentForm.actionType.value = 'delete';
	ContentForm.action = 'intervenorDel.do';
	ContentForm.submit();
}
function delContent(id)
{
	if(!confirm('ȷ��Ҫ���������ͷŴ�������'))
	{
	   return false;
	}
	
	ContentForm.actionType.value = 'deleteContent';
	ContentForm.contentId.value = id;
	ContentForm.action = 'intervenorDelContent.do';
	ContentForm.submit();
}

function editIntervenor()
{
	if(!confirm('ȷ��Ҫ�޸ĸ��˹���Ԥ������'))
	{
	   return false;
	}
	var name = ContentForm.name.value;
	var sdate = ContentForm.startDate.value;
	var edate = ContentForm.endDate.value;
	
	if(getLength(name)>30)
	{
		alert("�������Ʋ����Գ���30λ�����޸ģ�");
		ContentForm.name.focus();
		return false;
	}
	
	
	if(trim(name)=='')
	{
		alert("�������Ʋ�����Ϊ��");
		ContentForm.name.focus();
		return false;
	}
	
	var reg = /^[\w\-\u4e00-\u9fa5]+$/g
	
    if(!reg.test(name))
    {
		alert("��������ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߣ����������룡");
		ContentForm.name.focus();
		return false;
    }
	
	var iss = ContentForm.isselect.value;
	
	if(iss==0)
	{
		if(ContentForm.sortId.value == '')
		{
			alert("�趨��������λ���еĹ̶�λ�ò�����Ϊ��");
			ContentForm.sortId.focus();
			return false;
		}
		if(ContentForm.sortId.value == '0')
		{
			alert("�趨��������λ���еĹ̶�λ�ò�����Ϊ��");
			ContentForm.sortId.focus();
			return false;
		}
		ContentForm.startSortId.value = ContentForm.sortId.value;
		ContentForm.endSortId.value = ContentForm.sortId.value;
	}
	else
	{
		ContentForm.startSortId.value = iss;
		ContentForm.endSortId.value = iss;
	}

	
	if(edate < sdate)
	{
		alert("�����ͷ�ʱ�����޿�ʼʱ�䲻�ܴ��ڽ���ʱ��");
		ContentForm.startDate.focus();
		return false;
	}
	
	ContentForm.actionType.value = 'edit';
	ContentForm.action = 'intervenorEdit.do';
	ContentForm.submit();
}

function setSortIdNew()
{
	var sortId = document.getElementsByName('conSortId');
	
	var value = '';
	
	for(var i=0; i<sortId.length; i++)
	{
		var thisId = sortId[i].id;
		
		if(sortId[i].value == '')
		{
			alert("������ֵ������Ϊ�գ����������룡");
			sortId[i].focus();
			sortId[i].select();
			return false;
		}
		
		for(var j=i+1; j<sortId.length; j++)
		{
			if(sortId[i].value == sortId[j].value)
			{
				alert("����ֵ������ֵ���Ѵ��ڣ����������룡");
				sortId[j].focus();
				sortId[j].select();
				return false;
			}
		}
		
		value = value + thisId + "_" + sortId[i].value + ";";
	}
	
	ContentForm.actionType.value = 'setSort';
	ContentForm.contentId.value = value;
	ContentForm.action = 'intervenorSetSort.do';
	ContentForm.submit();
}

function addContentId()
{
	var returnv=window.showModalDialog("add_gcontent.jsp", "","width=600,height=400,toolbar=no,scrollbars=yes,menubar=no,top=300,left=300")
	
	if(returnv != undefined)
	{
		var id = returnv;
		
		ContentForm.contentId.value = returnv;
		
		saveContentId();
	} 
}

function saveContentId()
{
	var contentId = ContentForm.contentId.value;
	
	if(contentId == '')
	{
		return false;
	}
	
	ContentForm.actionType.value = 'addContent';
	ContentForm.action = 'contentAdd.do';
	ContentForm.submit();
}
</script>
</body>
</html>
