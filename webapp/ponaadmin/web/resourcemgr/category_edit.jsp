<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<%
//approvalFlag = "yes" 表示该操作需要经过审批。
String approvalFlag = request.getParameter("approvalFlag")==null?"no":request.getParameter("approvalFlag");
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>
<%String relation=request.getAttribute("relation")==null?"":(String)request.getAttribute("relation");
String prelation=request.getAttribute("prelation")==null?"":(String)request.getAttribute("prelation");
String subRelation=request.getAttribute("subRelation")==null?"":(String)request.getAttribute("subCateRelation");
String cgyPath=request.getAttribute("cgyPath")==null?"":(String)request.getAttribute("cgyPath");
String deviceName=request.getAttribute("deviceName")==null?"":(String)request.getAttribute("deviceName");
String platFormName=request.getAttribute("platFormName")==null?"":(String)request.getAttribute("platFormName");
String cityName=request.getAttribute("cityName")==null?"":(String)request.getAttribute("cityName");
%>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
<bean:parameter id="pCategoryID" name="pCategoryID" value="" />
<script language="JavaScript" type="text/javascript">

//   add by tungke 
function thischeckContent(field)
{
    var value = eval(field).value;
    //如果为空，则不做内容检验
    if( getLength(value) == 0) return true;
   // var reg　= /^[\w/!/@/#/$/%/^/&/(/)/{/}/【/】/＊/。/！/＠/＃/￥/％/……/＆/（/）/《/》/“/”/？/：/·\_\u4e00-\u9fa5]+$/g ; 
  //  return reg.test(value);
  var other = /^[\w\s\!\@\#\$\%\^\-\－\(\)\[\]\{\}\【\】\＊\。\！\＠\＃\￥\％\……\＆\（\）\《\》\“\”\？\：\．\·\_\u4e00-\u9fa5]+$/g ;
   return (other.test(value));
}

function checkForm(form)
{
  if(!checkLength(form.name,"货架名称",-1,40)) return false;
  if(!thischeckContent(form.name))
  {
    alert("<%=ResourceUtil.getResource("RESOURCE_CATE_FIELD_CHECK_001")%>");
    form.name.focus();
    return false;
  }
  if(!haveChooseChk(form,"relation"))
  {
    alert("关联门店至少需要选择一种类型！");
    return false;
    
  }
  if(!checkLength(form.desc,"货架描述",0,500)) return false;
  if(form.dataFile.value!="")
  {
     if(!isFileType(form.dataFile.value,"png")&&!isFileType(form.dataFile.value,"jpg"))
     {
        alert("不支持该格式，请重新选择文件，目前图片仅支持jpg和png格式！");
        return false;
     }
  }
  
  	var device = form.deviceCategory;
  	for(var i=0; i<device.length;i++)
  	{
		if(device[i].checked && device[i].value=='1')
		{
			if(form.deviceName.value=="")
	  		{
	  			alert("请为货架指定关联机型！");
	  			return false;
	  		}
	  	}
  	}  
  return true;
  
}

function recheck(obj,categoryid)
{
    var prelation='<bean:write name="prelation"/>';
    var subRelation='<bean:write name="subRelation"/>';
	var va = obj.value;
	// alert(va);
	if(categoryid!="701" && prelation.indexOf(va)==-1)//根货架分类不需要判断。
	{
	  alert("父货架选择了该类型，子货架才可以选择该类型，请增加父货架该类型");
		obj.checked = false;
		return;
	}
	if(!obj.checked && subRelation!='' && subRelation.indexOf(va)!=-1)//只有在非叶子节点时候，取消某个分类才需要检查。
	{
	    alert("子货架已存在该类型，不允许删除，请删除子货架该类型后再删除");
		obj.checked = true;
		return;
	}
	
}
function showFileUpload()
{
      document.getElementById('upload').style.display ='block';
}
function isFileType(filename,type)
{
   var pos = filename.lastIndexOf(".");

   if(pos<0) return false;

   var fileType = filename.substr(pos+1);

   if(fileType.toUpperCase()!=type.toUpperCase()) return false;

   return true;
}
function load()
{
	document.getElementById('upload').style.display ='none';
	var deviceCategory = <bean:write name="category" property="deviceCategory"/>;
	if(deviceCategory == 0)
	{
		document.getElementById('deviceCategoryText').style.display ='none';
	}
	else
	{
		document.getElementById('deviceCategoryText').style.display ='block';
		var htmp = "";
		<logic:notEmpty name="deviceList">
			<logic:iterate id="vo" name="deviceList" type="com.aspire.dotcard.gcontent.DeviceVO">
				htmp = htmp + "<input type='hidden' name='devices' value='" + "<bean:write name="vo" property="deviceId"/>" + ";" + "<bean:write name="vo" property="deviceName"/>" + "'/>";
			</logic:iterate>
		</logic:notEmpty>
		document.getElementById('device').innerHTML = htmp;
	}	
	
		var platFormHtmp = "";
		<logic:notEmpty name="platFormList">
		<logic:iterate id="vo" name="platFormList" type="String">
			platFormHtmp = platFormHtmp + "<input type='hidden' name='platForms' value='<bean:write name="vo"/>'/>";
		</logic:iterate>
		</logic:notEmpty>
		document.getElementById('platForm').innerHTML = platFormHtmp;
	
		var cityHtmp = "";
		<logic:notEmpty name="cityList">
		<logic:iterate id="vo" name="cityList" type="String">
			cityHtmp = cityHtmp + "<input type='hidden' name='citys' value='" + <bean:write name="vo"/> + "'/>";
		</logic:iterate>
		</logic:notEmpty>
		document.getElementById('city').innerHTML = cityHtmp;
}

function selectDevice(obj)
{
	if(obj.value==1)
	{
		document.getElementById('deviceCategoryText').style.display ='block';
	}
	else
	{
		document.getElementById('deviceCategoryText').style.display ='none';
	}
	
}

function selectDeviceId()
{
	var returnv=window.showModalDialog("categoryDevice.do?isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	var name = "";
	var htmp = "";
	if(returnv != undefined)
	{
		for(var i=0; i<returnv.length; i++)
		{
			var temp = returnv[i];
			
			if(!hasDeviceId(temp))
			{
				name = name + temp.name + ".";
				htmp = htmp + "<input type='hidden' name='devices' value='" + temp.id + ";" + temp.name + "'/>";
			}
		}
		document.getElementById('device').innerHTML = document.getElementById('device').innerHTML + htmp;
		form1.deviceName.value=form1.deviceName.value + name;
	}
}

function hasDeviceId(device)
{
	var obj = form1.devices;
	if(obj != undefined)
	{
		if(obj.length == undefined)
		{
			if(obj.value == device.id + ";" + device.name)
			{
				alert(obj.value + "已存在");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == device.id + ";" + device.name)
			{
				alert(obj[i].value + "已存在");
				return true;
			}
		}
	}
	return false;
}

function clearDeviceId()
{
	document.getElementById('device').innerHTML = "";
	form1.deviceName.value="";
}


function openPlatFormId(){
	window.open("categoryPlatForm.do?isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
}
function selectPlatFormId(value)
{
	var returnv=eval("("+value+")");
	var name = "";
	var htmp = "";
	if(returnv != undefined)
	{
		for(var i=0; i<returnv.length; i++)
		{
			var temp = returnv[i];
			
			if(!hasPlatFormId(temp))
			{
				name = name + temp.name + "; ";
				htmp = htmp + "<input type='hidden' name='platForms' value='" + temp.id + "'/>";
			}
		}
		document.getElementById('platForm').innerHTML = document.getElementById('platForm').innerHTML + htmp;
		
		if(form1.platFormName.value == '全平台通用.')
		{
			form1.platFormName.value=name;
		}
		else
		{
			form1.platFormName.value=form1.platFormName.value + name;
		}
	}
}

function hasPlatFormId(platForm)
{
	var obj = form1.platForms;
	if(obj != undefined)
	{
		if(obj.length == undefined)
		{
			if(obj.value == platForm.id)
			{
				alert(platForm.name + "已存在");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == platForm.id)
			{
				alert(platForm.name + "已存在");
				return true;
			}
		}
	}
	return false;
}

function clearPlatFormId()
{
	document.getElementById('platForm').innerHTML = "";
	form1.platFormName.value="全平台通用.";
}


function openCityId(){
	window.open("categoryCity.do?isFirst=1&pCategoryID=<bean:write name="pCategoryID" />&categoryID=<bean:write name="category" property="id"/>", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
}
function selectCityId(value)
{
	var returnv = eval("("+value+")");
	
	var name = "";
	var htmp = "";
	if(returnv != undefined)
	{
		for(var i=0; i<returnv.length; i++)
		{
			var temp = returnv[i];
			
			if(!hasCityId(temp))
			{
				name = name + temp.name + "; ";
				htmp = htmp + "<input type='hidden' name='citys' value='" + temp.id + "'/>";
			}
		}
		
		document.getElementById('city').innerHTML = document.getElementById('city').innerHTML + htmp;
		
		
		if(form1.cityName.value == '全国通用.')
		{
			form1.cityName.value=name;
		}
		else
		{
			form1.cityName.value=form1.cityName.value + name;
		}
	}
}

function hasCityId(city)
{
	var obj = form1.citys;
	if(obj != undefined)
	{
		if(obj.length == undefined)
		{
			if(obj.value == city.id)
			{
				alert(city.name + "已存在");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == city.id)
			{
				alert(city.name + "已存在");
				return true;
			}
		}
	}
	return false;
}

function clearCityId()
{
	document.getElementById('city').innerHTML = "";
	form1.cityName.value="全国通用.";
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onLoad="load();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>所在位置：<%=cgyPath %></td>
  </tr>
</table>
<form name="form1" method="post" action="categorySave.do?approvalFlag=<%=approvalFlag %>" enctype="multipart/form-data" onSubmit="return checkForm(form1);">
<input type="hidden" name="cgyPath" value="<%=cgyPath %>">
<input type="hidden" name="ctype" value="<bean:write name="category" property="ctype"/>">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1"><logic:equal parameter="action" value="new">新增</logic:equal><logic:equal parameter="action" value="update">修改</logic:equal>货架</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="25%" align="right" class="text3">
      <input type="hidden" name="pCategoryID" value="<bean:write name="pCategoryID" />">
      <input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <font color="#ff0000">(*)</font>货架名称：
    </td>
    <td width="75%" class="text4"><input name="name" type="text" value="<bean:write name="category" property="name"/>" size="50">
    </td>
  </tr>

  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000">(*)</font>关联门店： 
    </td>
    <td width="75%" class="text4">
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="W" <%if (relation.indexOf("W")!=-1) {%><%out.print("checked");}%>>WWW门店
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="O" <%if (relation.indexOf("O")!=-1) {%><%out.print("checked");}%>>终端门店
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="P" <%if (relation.indexOf("P")!=-1) {%><%out.print("checked");}%>>PC门店
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="A" <%if (relation.indexOf("A")!=-1) {%><%out.print("checked");}%>>WAP门店
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">货架描述：</td>
    <td class="text4"><textarea name="desc" cols="50"><bean:write name="category" property="desc"/></textarea>
    </td>
  </tr>
  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000"></font>货架序号：
    </td>
    <td width="75%" class="text4"><input name="sortID" type="text" value="<bean:write name="category" property="sortID"/>" size="8">取值范围0-999999
    </td>
  </tr>
  
    <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000"></font>货架URL：
    </td>
    <td class="text4"><textarea name="multiurl" cols="50"><bean:write name="category" property="multiurl"/></textarea>(终端门户混排使用的URL)
    </td>
  </tr>
  
  
  
  <tr>
					<td align="right" class="text3">
						货架(开始)生效时间：
					</td>
					<td class="text4">
						<input name="startDate" class="Wdate" type="text"
							id="textbox2" style="width:170px"
							onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
							value="<bean:write name="category" property="startDate"/>"
							readonly="true" />
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						货架(结束)失效时间：
					</td>
					<td class="text4">
						<input name="endDate" class="Wdate" type="text"
							id="textbox2" style="width:170px"
							onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
							value="<bean:write name="category" property="endDate"/>"
							readonly="true" />
					</td>
				</tr>
  <tr>
    <td align="right" class="text3">货架是否支持异网：</td>
    <td class="text4">
    	<INPUT type="radio" name="othernet" value="1" 	
    		<logic:equal value="1" name="category" property="othernet"> checked="true" </logic:equal> 
    		/>支持&nbsp; 
    	<INPUT type="radio" name="othernet" value="0" 
    		<logic:equal value="0" name="category" property="othernet"> checked="true" </logic:equal> 
    		/>不支持</td>
  </tr>				
  <tr>
    <td align="right" class="text3">货架是否在门户展示：</td>
    <td class="text4">
    	<INPUT type="radio" name="state" value="1" 	
    		<logic:equal value="1" name="category" property="state"> checked="true" </logic:equal> 
    		/>是&nbsp; 
    	<INPUT type="radio" name="state" value="0" 
    		<logic:equal value="0" name="category" property="state"> checked="true" </logic:equal> 
    		/>否</td>
  </tr>
    <logic:equal value="11" name="category" property="ctype">
   <tr>
    <td  align="right" class="text3">货架预览图： </td>
    <td  class="text4">
        <logic:notEmpty name="category" property="picURL">
		<img src="<bean:write name="category" property="picURL"/>logo4.png">&nbsp;&nbsp;&nbsp;&nbsp;
		<button onClick="showFileUpload();">更新</button>
		</logic:notEmpty>
		<logic:empty name="category" property="picURL">
		<font color="red">无预览图</font> &nbsp;&nbsp;&nbsp;&nbsp;
		<button onClick="showFileUpload();">上传</button>
		</logic:empty>
		
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">货架是否是机型货架：</td>
    <td class="text4">
	    <INPUT type="radio" name="deviceCategory" value="1" 
	    	<logic:equal value="1" name="category" property="deviceCategory"> checked="true" </logic:equal> 
	    	onclick="selectDevice(this);"/>是&nbsp; 
	    <INPUT type="radio" name="deviceCategory" value="0" 
	    	<logic:equal value="0" name="category" property="deviceCategory"> checked="true" </logic:equal> 
	    	onclick="selectDevice(this);"/>否
	    	<span id="device">
  			</span>
  			&nbsp;<font color="#ff0000">建议：对机型货架的修改成功后，请手动执行货架全量同步策略。</font>
    </td>
  </tr>
  <tr id='deviceCategoryText'>
    <td align="right" class="text3">
    <input type="button" value="选择货架对应机型" onclick="selectDeviceId();"/>
    <input type="button" value="清空货架对应机型" onclick="clearDeviceId();"/>
    </td>
    <td class="text4">
    	<textarea name="deviceName" rows="4" cols="80" readonly><%=deviceName %></textarea>
    </td>
  </tr>
  <tr id='platFormNameText'>
	    <td align="right" class="text3">
	    <input type="button" value="选择货架适配平台" onclick="openPlatFormId();"/>
	    <input type="button" value="设置为全平台通用" onclick="clearPlatFormId();"/>
	    <span id="platForm">
	  	</span>
    </td>
    <td class="text4">
    	<textarea name="platFormName" rows="4" cols="80" readonly><%=platFormName %></textarea>
    </td>
  </tr>
  <tr id='cityNameText'>
    <td align="right" class="text3">
	    <input type="button" value="选择货架适配地域" onclick="openCityId();"/>
	    <input type="button" value="设置为全国通用  " onclick="clearCityId();"/>
	    <span id="city">
	  	</span>
    </td>
    <td class="text4">
    	<textarea name="cityName" rows="4" cols="80" readonly><%=cityName %></textarea>
    </td>
  </tr>
  
  
   <logic:iterate id="vo" indexId="ind" name="keyResourceList"  type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
	   <tr>
	    <td align="right" class="text3"><bean:write name="vo" property="keydesc"/></td>
	    <td class="text4"><logic:equal value="1" name="vo" property="keyType"> <input name="<bean:write name="vo" property="keyname"/>" type="text" value="<bean:write name="vo" property="value"/>" size="60"> </logic:equal> 
			<logic:equal value="2" name="vo" property="keyType"> <input  type="file"  name="<bean:write name="vo" property="keyname"/>"   size="60"> &nbsp;仅支持png格式<bean:write name="vo" property="value"/></logic:equal> 
			<logic:equal value="3" name="vo" property="keyType"> <textarea  name="<bean:write name="vo" property="keyname"/>" cols="50"><bean:write name="vo" property="value"/></textarea></logic:equal>
			<logic:notEqual value="" name="vo" property="value">
      			<input type="checkbox" value="1" name="clear_<bean:write name='vo' property='keyname'/>"/>
      			清空当前扩展字段内容
      		</logic:notEqual> 
	    </td>
	  </tr>
	    </logic:iterate>
  
  
</table>
<div id="upload">
  <table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
     <tr>
    <td width="25%" align="right" class="text3">
      请选择图片文件：
    </td><!-- 
    <td width="75%" class="text4"><input type="file" name="dataFile12"><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;仅支持jpg和png格式</font>
    </td>
    -->
  </tr>
  </table>
</div>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">
            <input name="Submit" type="submit" class="input1" value="确定">
          </td>
        </tr>
        <tr>
          <td align="center" class="text5">
            <font color="#ff0000">建议：新增或修改货架时，名称前增加“WWW”代表WWW门户货架，“终端”代表终端门户货架，公用货架前面不加标识。</font>
          </td>
        </tr>
</table>
</body>
</html>
