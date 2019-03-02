<%@ page contentType="text/html; charset=gb2312"%>
<jsp:directive.page import="com.aspire.ponaadmin.web.util.PublicUtil" />
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%
			List ruleTypeList = CategoryUpdateConfig.getInstance()
			.getNodeValueList("ruleType");
	pageContext.setAttribute("ruleTypeList", ruleTypeList,
			PageContext.PAGE_SCOPE);
%>

<%@page import="com.aspire.ponaadmin.web.category.CategoryUpdateConfig"%>
<%@page import="java.util.List"%>
<%
String curDate = PublicUtil.getCurDateTime("yyyy-MM-dd");
%>
<html>
	<head>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<title>新增专辑信息</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript">
		   function check()
		   {
		   var f=document.forms[0];
		   var dissName=f.dissName;
		   var descr=f.descr;
		   var startDate=f.startDate;
		   var endDate=f.endDate;
		   var url=f.urlM;
		   	 if(checkChinLength(dissName,"专题名称",-1,40) && checkChinLength(descr,"专题介绍",-1,200)
		   	 && checkNull(startDate,"开始时间") && checkNull(endDate,"结束时间") && checkLength(url,"专题URL地址",-1,1024))
		   	 {
		   	  
		   	   var d=new DateFormat();var format="yyyy-MM-dd";
		   	//  if(d.parseDate(startDate.value,format).valueOf()>d.parseDate(d.curdateToString(format),format).valueOf()){
		   	 // 	 alert("开始时间必须为当天或之前时间！");
		   	//  	 return false;
		   	 // 	 }
		   	  	 if(d.parseDate(startDate.value,format)>d.parseDate(endDate.value,format)){
		   	  	 alert("截止时间必须大于开始时间！");
		   	  	 return false;
		   	  	 }
		   	  if(!checkIllegalChar(f.tag.value,"；")){
		   	  	 alert("标签分隔符不合法，请使用半角分号符！");
		   	  	 return false;
		   	  }
		   	  else if(f.tag.value!="" && !checkChinLength(f.tag,"标签",-1,40)){
		   	  	return false;
		   	  }
		   	  if(!haveChooseChk(f,'types')){
		   	  	 alert("请至少选择一个分类！");
		   	  	 return false;
		   	   }
		   	   return true;
		   	   }
		   	  return false;
		   	  }
		</script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<bean:parameter id="startDate" name="startDate" value="" />
		<bean:parameter id="endDate" name="endDate" value="" />	
		<html:form action="web/dissertation/addDiss.do"   method="post" enctype="multipart/form-data" onsubmit="return check()">

			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF" style="line-height: 80px">
				<tr>
					<td align="center" class="title1"> 
						专题管理----专题详情
					</td>
				</tr>
			</table>
			<input type="hidden" name="param" value="update">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="25%" align="right" class="text3">
						状态：
					</td>
					<td width="75%" class="text4" style="word-break:break-all;">
					<html:text property="status" readonly="true" title="只读"></html:text>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						专题编号：
					</td>
					<td class="text4">
					<html:text property="dissId" style="width: 200px;" readonly="true" title="只读" ></html:text>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						专题名称：
					</td>
					<td class="text4">
					<html:text property="dissName" style="width: 200px;" ></html:text>
						*不能超过20个汉字
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						专题介绍：
					</td>
					<td class="text4">
					<html:textarea property="descr" rows="6" cols="35"></html:textarea>
						*不能超过100个汉字
					</td>
				</tr>
							<tr>
					<td  align="right" class="text3" >
						综合信息：
					</td>
					<td class="text4">
						<html:multibox property="relation" value="M" style="display:none;" ></html:multibox>
						
						<span style="width: 50px"><bean:write name="msg"/></span>
						<img src="<%=request.getAttribute("src") %>" width="60px" name="mpic" styleId="M_Pic" height="50px" border="0" />
						<html:file property="uploadFile[0].file" styleId="file_M"  ></html:file>
						Url :
							<html:text property="urlM" style="width: 150px"  styleId="url_M"></html:text>
						关联货架：
						<html:hidden property="categoryIdM" />
						<html:text property="categoryNameM" style="width:80px" readonly="true"
							onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="请选择" onclick="selectCid(categoryIdM);"/>
						&nbsp;<input type="button" onclick="categoryIdM.value=''"value="清除" title="清除货架"/>
					</td>
				</tr>
				<tr id="span">
					<td align="right" class="text3">
						有效期：
					</td>
					<td class="text4">
							<html:text property="startDate" size="12"  readonly="true"></html:text>
						<img src="../../image/tree/calendar.gif"
							onClick="getDate(startDate);" align="absmiddle"
							style="cursor:hand;">
						&nbsp; 到
							<html:text property="endDate" size="12"  readonly="true"></html:text>
						<img src="../../image/tree/calendar.gif"
							onClick="getDate(endDate);" align="absmiddle"
							style="cursor:hand;">
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						标签：
					</td>
					<td class="text4">
						<html:text property="tag" style="width: 200px;" title="可选项"></html:text>
						不能超过20汉字，用";"分号分隔
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						分类：
					</td>
					<td class="text4">
						&nbsp;&nbsp; 软件
						<html:multibox property="types" value="Soft"></html:multibox>
						&nbsp;&nbsp;&nbsp; 游戏
						<html:multibox property="types" value="Game"></html:multibox>
						&nbsp;&nbsp;&nbsp; 主题
						<html:multibox property="types" value="Theme"></html:multibox>
						&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr align="center" class="text3">
					<td colspan="2" height="50px">
						<input type="submit" value=" 提 交 ">
						<input name="cancel" type="button" onClick="back();"
							value=" 取 消 ">
					</td>
				</tr>
				
			</table>
			</html:form>
	</body>
	<script language="javascript">
   	function selectCid(p)
   	{
   		var returnv=window.showModalDialog("../autoupdate/categoryRuleIdQuery.do?isFirst=1", "diss","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
   		if(returnv != undefined)
		{
			p.value=returnv.id;
			document.all["categoryNameM"].value=returnv.name;
		}  
   	}
   	function back(){
   		history.go(-1);
   	}

	</script>
	<script language="javascript">
<!--
//结束日期默认为系统当前日期
if(trim(document.forms[0].endDate.value)=="")
{
document.forms[0].endDate.value = "<%=curDate%>";
}
if(trim(document.forms[0].startDate.value)=="")
{
document.forms[0].startDate.value = "<%=curDate%>";
}
-->
</script>
</html>
