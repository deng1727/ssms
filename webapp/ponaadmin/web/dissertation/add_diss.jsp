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
		   	 if(checkChinLength(dissName,"专题名称",-1,40) && checkChinLength(descr,"专题介绍",-1,200)
		   	 && checkNull(startDate,"开始时间") && checkNull(endDate,"结束时间"))
		   	 {
		   	   if(!haveChooseChk(f,'relation')){
		   	  	 alert("请至少选择一个专题所要关联的门户！");
		   	  	 return false;
		   	   }
		   	   else
		   	   {//用户已经选择了某个门户，依次检查其logo,专题url是否为空
		   	      var relations=document.getElementsByName("relation");
		   	      for(var i=0;i<relations.length;i++){
		   	      	  var cb=relations[i];
		   	      	  if(cb.checked)
		   	      	  {
		   	      	  	//var file=f.elements["file_"+cb.value];
		   	      	  	var url=f.elements["url_"+cb.value];
		   	      	  	//if(!checkNull(file,"关联"+cb.value+"门户的logo图片") || !checkLogoUrl(url) || !checkLength(url,"专题URL地址",-1,1024))
		   	      	  	if( !checkLogoUrl(url) || !checkLength(url,"专题URL地址",-1,1024))
		   	      	  		return false;
		   	      	  		 var categoryn = "categoryName"+cb.value;
		   	      	  		 if(categoryn != "" && categoryn!= null && categoryn!="undefined"&& categoryn=="categoryNameM"){
		   	      	  		 	if(!checkNull(f.categoryNameM,"关联货架") ){		   	      	  				 
		   	      	  		 		return false;
		   	      	  		 	}
		   	      	  		 }
		   	      	  }
		   	      }
		   	   }
		   	   var d=new DateFormat();var format="yyyy-MM-dd";
		   	  if(d.parseDate(startDate.value,format)>d.parseDate(d.curdateToString(format),format)){
		   	  	 alert("开始时间必须为当天或之前时间！");
		   	  	 return false;
		   	  	 }
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
		   	  function checkLogoUrl(urlV){
		   	  	 if(urlV.value=="请输入URL地址...")
		   	  	 {
		   	  	 	alert("专题url不能为空");
		   	  	 	urlV.value="";
		   	  	 	urlV.focus();
		   	  	 	return false;
		   	  	 	}
		   	  	 return true;
		   	  }
		   	   function checkCategory(ca){
		   	  	 if(ca.value=="" || ca.value==null ||ca.value== "undefined")
		   	  	 {
		   	  	 	alert("MO门户货架不能为空");
		   	  	 	urlV.value="";
		   	  	 	urlV.focus();
		   	  	 	return false;
		   	  	 	}
		   	  	 return true;
		   	  }
		   	    function clearCategory(id,name){
		   	  	 if(id !="undefined" && name == "undefined")
		   	  	 {
		   	  	 document.forms[0].id.value='';
		   	  	 document.forms[0].name.value='';
		   	  	 	alert("清空完毕");
		  
		   	  	 	}
		   	  	 return true;
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
						新增专题信息
					</td>
				</tr>
			</table>
			<input type="hidden" name="param" value="add">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="20%" align="right" class="text3">
						状态：
					</td>
					<td width="80%" class="text4" style="word-break:break-all;">
					<html:text property="status" readonly="true" value="有效" title="默认只读"></html:text>
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
						*不能超过100个汉字;如果选择了MO门户，关联货架必须选择！
					</td>
				</tr>
							<tr class="text4">
					<td rowspan="5" align="right" class="text3" valign="top">
						综合信息：
					</td>
					<td >
						<html:multibox property="relation" value="M" ></html:multibox>
						<span style="width: 50px">MO</span>					
						<html:file property="uploadFile[0].file" styleId="file_M"  ></html:file>
						<!--  <button
							style="left:1px;border:1px solid #000000; background-color:#FFFFFF;width:60px;height:20px"
							onclick="var obj=document.all('uploadFile[0].file');obj.click();mpic.src=obj.value">
							选择logo
						</button>
						-->
						Url :
						<html:text property="urlM" style="width: 150px"  styleId="url_M"   value="请输入URL地址..." onclick="clearurl(this)"></html:text>
						*关联货架：
						<html:hidden property="categoryIdM"  />
						<html:text property="categoryNameM" style="width:150px" value="" readonly="true"
							onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false"  />
						<input type="button" value="请选择" onclick="selectCid(categoryNameM,categoryIdM);"/>
						&nbsp;<input type="button" onclick="document.forms[0].categoryIdM.value='';document.forms[0].categoryNameM.value=''"value="清除" title="清除货架"/>
					</td>
				</tr>
				<tr class="text4">
					<td>
						<html:multibox property="relation" value="W" ></html:multibox>
						<span style="width: 50px">WWW</span>
						
						<html:file property="uploadFile[1].file"   styleId="file_W"></html:file>
						Url :
						<html:text property="urlW" style="width: 150px"  styleId="url_W"  value="请输入URL地址..." onclick="clearurl(this)"/>
						关联货架：
						<html:hidden property="categoryIdW" />
						<html:text property="categoryNameW" style="width: 150px" value="" readonly="true"
							onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="请选择" onclick="selectCid(categoryNameW,categoryIdW);" />
						&nbsp;<input type="button" onclick="document.forms[0].categoryIdW.value='';document.forms[0].categoryNameW.value=''"value="清除" title="清除货架"/>
					</td>
				</tr>
				<tr class="text4">
					<td>
						<html:multibox property="relation" value="P1" ></html:multibox>
						<span style="width: 50px">WAP1.0</span>
						
						<html:file property="uploadFile[2].file" styleId="file_P1"  ></html:file>
						Url :
						<html:text property="p1DissUrl" style="width: 150px"  styleId="url_P1"  value="请输入URL地址..." onclick="clearurl(this)"></html:text>
						关联货架：
						<html:hidden property="p1CategoryId" />
						<html:text property="p1CategoryName"  style="width: 150px" readonly="true"
							value="" onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="请选择"
							onclick="selectCid(p1CategoryName,p1CategoryId);" />
							&nbsp;<input type="button" onclick="document.forms[0].p1CategoryId.value='';document.forms[0].p1CategoryName.value=''"value="清除" title="清除货架"/>
					</td>
				</tr>
				<tr class="text4">
					<td>
						<html:multibox property="relation" value="P2" ></html:multibox>
						<span style="width: 50px">WAP2.0</span>
						
						<html:file property="uploadFile[3].file" styleId="file_P2"  ></html:file>
						Url :
							<html:text property="p2DissUrl" style="width: 150px"  styleId="url_P2"   value="请输入URL地址..." onclick="clearurl(this)"></html:text>
						关联货架：
						<html:hidden property="p2CategoryId" />
						<html:text property="p2CategoryName" style="width: 150px" readonly="true"
							value="" onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="请选择"
							onclick="selectCid(p2CategoryName,p2CategoryId);" />
							&nbsp;<input type="button" onclick="document.forms[0].p2CategoryId.value='';document.forms[0].p2CategoryName.value=''"value="清除" title="清除货架"/>
					</td>
				</tr>
				<tr class="text4">
					<td>
						<html:multibox property="relation" value="P3" ></html:multibox>
						<span style="width: 50px">WAPTouch</span>
					
						<html:file property="uploadFile[4].file" styleId="file_P3"  ></html:file>
						Url :
						<html:text property="p3DissUrl" style="width: 150px"  styleId="url_P3"   value="请输入URL地址..." onclick="clearurl(this)"></html:text>
						关联货架：
						<html:hidden property="p3CategoryId"/>
						<html:text property="p3CategoryName" style="width: 150px" readonly="true"
							value="" onkeydown="if(event.keyCode==13) event.keyCode=9"
							onKeyPress="if ((event.keyCode <48 || event.keyCode>57)) event.returnValue=false" />
						<input type="button" value="请选择"
							onclick="selectCid(p3CategoryName,p2CategoryId);" />
							&nbsp;<input type="button" onclick="document.forms[0].p3CategoryId.value='';document.forms[0].p3CategoryName.value=''"value="清除" title="清除货架"/>
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
						*分类：
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
					<td colspan="2" height="7	0px">
						<input type="submit" value=" 提 交 ">
						<input name="button2" type="button" onClick="history.go(-1)"
							value=" 取 消 ">
					</td>
				</tr>
				
			</table>
			</html:form>
	</body>
	<script language="javascript">
   	function selectCid(p,obj)
   	{

   		var returnv=window.showModalDialog("../autoupdate/categoryRuleIdQuery.do?isFirst=1", "diss","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
   		if(returnv != undefined)
		{
			obj.value=returnv.id;
			p.value=returnv.name
		}  
   	}
   	function clearurl(obj){
   	  if(obj.value=="请输入URL地址..."){
   	   obj.value="";
   	  }
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
