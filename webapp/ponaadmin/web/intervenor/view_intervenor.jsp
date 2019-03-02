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
<title>人工干预容器信息</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5" onload="into();">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#BFE8FF">人工干预容器信息</td>
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
    	当前容器:
    </td>
    <td width="30%" class="text4">
    	<input type="text" name="name" value="<bean:write name="intervenorVO" property="name"/>" />
    </td>
    <td width="50%" align="center" class="text4"> 
    	<input type="button" value="删除该容器" onclick="delIntervenor();">  
    	<input type="button" value="保存对容器信息的修改" onclick="editIntervenor();">  
    </td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	设定容器排名位置:
    </td>
    <td class="text4" colspan="2">
    	<select name="isselect" onchange="selectSort();">
    		<option value="0">手动输入</option>
    		<option value="-1">至榜单顶部</option>
    		<option value="-2">至榜单底部</option>
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
    	设置容器生效时间期限:
    </td>
    <td class="text4" colspan="2">
    	<input name="startDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="startDate"/>"
			readonly="true" />
			&nbsp;至&nbsp;
		<input name="endDate" class="Wdate" type="text"
			id="textbox2" style="width:170px"
			onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
			value="<bean:write name="intervenorVO" property="endDate"/>"
			readonly="true" />
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    	加入商品至容器:
    </td>
    <td colspan="2" class="text4">
    	<input type="button" value="从商品目录中提取" onclick="addContentId();">
	</td>
  </tr>
  </form>
  <form action="intervenorView.do?actionType=importFile&id=<bean:write name="intervenorVO" property="id"/>" name="form1" method="post" enctype="multipart/form-data">
  <tr>
    <td width="20%" align="right" class="text3">
    	导入内容数据文件:
    </td>
    <td colspan="2" class="text4">
    	<input type="file" name="dataFile">(请选择excel数据文件,表格第一列表示引用内容的内码)
	</td>
  </tr>
  <tr>
    <td width="20%" align="right" class="text3">
    </td>
    <td colspan="2" class="text4">
    	<input type="submit" name="genLogo" value="导入内容数据文件" onclick="return import_content()">
    	<b><font color=red>注意：导入操作会删除该容器下的所有原内容.请慎重操作!</font></b>
	</td>
  </tr>
  </form>
</table>
<br>
<br>

<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     已加入商品列表
    </td>
  </tr>
</table>

<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
	<tr bgcolor="#B8E2FC">
	  <td align="center" class="title1">商品名称</td>
	  <td align="center" class="title1">商品编码</td>
	  <td align="center" class="title1">提供商</td>
	  <td align="center" class="title1">商用时间</td>
	  <td align="center" class="title1" colspan="2">操作</td>
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
					从容器中释放
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
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC" >
      	<td align="center">
          <input type="button" value="设置排序" onclick="setSortIdNew()"/>
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
		alert("请选择要导入数据的文件！");
		return false;
	}
	 
	if(!isFileType(filePath,"xls"))
	{
		alert("请选择xls格式的数据文件!");
		return false;
	}
	
	if(!confirm('确定要导入人工干预容器内容吗？'))
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
	window.status="完成";
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
	if(!confirm('确定要删除该人工干预容器吗？'))
	{
	   return false;
	}
	ContentForm.actionType.value = 'delete';
	ContentForm.action = 'intervenorDel.do';
	ContentForm.submit();
}
function delContent(id)
{
	if(!confirm('确定要从容器中释放此内容吗？'))
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
	if(!confirm('确定要修改该人工干预容器吗？'))
	{
	   return false;
	}
	var name = ContentForm.name.value;
	var sdate = ContentForm.startDate.value;
	var edate = ContentForm.endDate.value;
	
	if(getLength(name)>30)
	{
		alert("容器名称不可以超过30位。请修改！");
		ContentForm.name.focus();
		return false;
	}
	
	
	if(trim(name)=='')
	{
		alert("容器名称不可以为空");
		ContentForm.name.focus();
		return false;
	}
	
	var reg = /^[\w\-\u4e00-\u9fa5]+$/g
	
    if(!reg.test(name))
    {
		alert("容器名称只能包含汉字、英文字母、数字、横杠、下划线！请重新输入！");
		ContentForm.name.focus();
		return false;
    }
	
	var iss = ContentForm.isselect.value;
	
	if(iss==0)
	{
		if(ContentForm.sortId.value == '')
		{
			alert("设定容器排名位置中的固定位置不可以为空");
			ContentForm.sortId.focus();
			return false;
		}
		if(ContentForm.sortId.value == '0')
		{
			alert("设定容器排名位置中的固定位置不可以为零");
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
		alert("容器释放时间期限开始时间不能大于结束时间");
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
			alert("排序数值不可以为空，请重新输入！");
			sortId[i].focus();
			sortId[i].select();
			return false;
		}
		
		for(var j=i+1; j<sortId.length; j++)
		{
			if(sortId[i].value == sortId[j].value)
			{
				alert("此数值在排序值中已存在，请重新输入！");
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
