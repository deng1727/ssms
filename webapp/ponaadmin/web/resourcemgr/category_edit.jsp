<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<%
//approvalFlag = "yes" ��ʾ�ò�����Ҫ����������
String approvalFlag = request.getParameter("approvalFlag")==null?"no":request.getParameter("approvalFlag");
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>
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
    //���Ϊ�գ��������ݼ���
    if( getLength(value) == 0) return true;
   // var reg��= /^[\w/!/@/#/$/%/^/&/(/)/{/}/��/��/��/��/��/��/��/��/��/����/��/��/��/��/��/��/��/��/��/��\_\u4e00-\u9fa5]+$/g ; 
  //  return reg.test(value);
  var other = /^[\w\s\!\@\#\$\%\^\-\��\(\)\[\]\{\}\��\��\��\��\��\��\��\��\��\����\��\��\��\��\��\��\��\��\��\��\��\_\u4e00-\u9fa5]+$/g ;
   return (other.test(value));
}

function checkForm(form)
{
  if(!checkLength(form.name,"��������",-1,40)) return false;
  if(!thischeckContent(form.name))
  {
    alert("<%=ResourceUtil.getResource("RESOURCE_CATE_FIELD_CHECK_001")%>");
    form.name.focus();
    return false;
  }
  if(!haveChooseChk(form,"relation"))
  {
    alert("�����ŵ�������Ҫѡ��һ�����ͣ�");
    return false;
    
  }
  if(!checkLength(form.desc,"��������",0,500)) return false;
  if(form.dataFile.value!="")
  {
     if(!isFileType(form.dataFile.value,"png")&&!isFileType(form.dataFile.value,"jpg"))
     {
        alert("��֧�ָø�ʽ��������ѡ���ļ���ĿǰͼƬ��֧��jpg��png��ʽ��");
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
	  			alert("��Ϊ����ָ���������ͣ�");
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
	if(categoryid!="701" && prelation.indexOf(va)==-1)//�����ܷ��಻��Ҫ�жϡ�
	{
	  alert("������ѡ���˸����ͣ��ӻ��ܲſ���ѡ������ͣ������Ӹ����ܸ�����");
		obj.checked = false;
		return;
	}
	if(!obj.checked && subRelation!='' && subRelation.indexOf(va)!=-1)//ֻ���ڷ�Ҷ�ӽڵ�ʱ��ȡ��ĳ���������Ҫ��顣
	{
	    alert("�ӻ����Ѵ��ڸ����ͣ�������ɾ������ɾ���ӻ��ܸ����ͺ���ɾ��");
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
				alert(obj.value + "�Ѵ���");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == device.id + ";" + device.name)
			{
				alert(obj[i].value + "�Ѵ���");
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
		
		if(form1.platFormName.value == 'ȫƽ̨ͨ��.')
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
				alert(platForm.name + "�Ѵ���");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == platForm.id)
			{
				alert(platForm.name + "�Ѵ���");
				return true;
			}
		}
	}
	return false;
}

function clearPlatFormId()
{
	document.getElementById('platForm').innerHTML = "";
	form1.platFormName.value="ȫƽ̨ͨ��.";
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
		
		
		if(form1.cityName.value == 'ȫ��ͨ��.')
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
				alert(city.name + "�Ѵ���");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == city.id)
			{
				alert(city.name + "�Ѵ���");
				return true;
			}
		}
	}
	return false;
}

function clearCityId()
{
	document.getElementById('city').innerHTML = "";
	form1.cityName.value="ȫ��ͨ��.";
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onLoad="load();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<%=cgyPath %></td>
  </tr>
</table>
<form name="form1" method="post" action="categorySave.do?approvalFlag=<%=approvalFlag %>" enctype="multipart/form-data" onSubmit="return checkForm(form1);">
<input type="hidden" name="cgyPath" value="<%=cgyPath %>">
<input type="hidden" name="ctype" value="<bean:write name="category" property="ctype"/>">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1"><logic:equal parameter="action" value="new">����</logic:equal><logic:equal parameter="action" value="update">�޸�</logic:equal>����</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="25%" align="right" class="text3">
      <input type="hidden" name="pCategoryID" value="<bean:write name="pCategoryID" />">
      <input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <font color="#ff0000">(*)</font>�������ƣ�
    </td>
    <td width="75%" class="text4"><input name="name" type="text" value="<bean:write name="category" property="name"/>" size="50">
    </td>
  </tr>

  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000">(*)</font>�����ŵ꣺ 
    </td>
    <td width="75%" class="text4">
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="W" <%if (relation.indexOf("W")!=-1) {%><%out.print("checked");}%>>WWW�ŵ�
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="O" <%if (relation.indexOf("O")!=-1) {%><%out.print("checked");}%>>�ն��ŵ�
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="P" <%if (relation.indexOf("P")!=-1) {%><%out.print("checked");}%>>PC�ŵ�
		<input type="checkbox" name="relation" onclick="recheck(this,'<bean:write name="category" property="id"/>');"  value="A" <%if (relation.indexOf("A")!=-1) {%><%out.print("checked");}%>>WAP�ŵ�
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����������</td>
    <td class="text4"><textarea name="desc" cols="50"><bean:write name="category" property="desc"/></textarea>
    </td>
  </tr>
  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000"></font>������ţ�
    </td>
    <td width="75%" class="text4"><input name="sortID" type="text" value="<bean:write name="category" property="sortID"/>" size="8">ȡֵ��Χ0-999999
    </td>
  </tr>
  
    <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000"></font>����URL��
    </td>
    <td class="text4"><textarea name="multiurl" cols="50"><bean:write name="category" property="multiurl"/></textarea>(�ն��Ż�����ʹ�õ�URL)
    </td>
  </tr>
  
  
  
  <tr>
					<td align="right" class="text3">
						����(��ʼ)��Чʱ�䣺
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
						����(����)ʧЧʱ�䣺
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
    <td align="right" class="text3">�����Ƿ�֧��������</td>
    <td class="text4">
    	<INPUT type="radio" name="othernet" value="1" 	
    		<logic:equal value="1" name="category" property="othernet"> checked="true" </logic:equal> 
    		/>֧��&nbsp; 
    	<INPUT type="radio" name="othernet" value="0" 
    		<logic:equal value="0" name="category" property="othernet"> checked="true" </logic:equal> 
    		/>��֧��</td>
  </tr>				
  <tr>
    <td align="right" class="text3">�����Ƿ����Ż�չʾ��</td>
    <td class="text4">
    	<INPUT type="radio" name="state" value="1" 	
    		<logic:equal value="1" name="category" property="state"> checked="true" </logic:equal> 
    		/>��&nbsp; 
    	<INPUT type="radio" name="state" value="0" 
    		<logic:equal value="0" name="category" property="state"> checked="true" </logic:equal> 
    		/>��</td>
  </tr>
    <logic:equal value="11" name="category" property="ctype">
   <tr>
    <td  align="right" class="text3">����Ԥ��ͼ�� </td>
    <td  class="text4">
        <logic:notEmpty name="category" property="picURL">
		<img src="<bean:write name="category" property="picURL"/>logo4.png">&nbsp;&nbsp;&nbsp;&nbsp;
		<button onClick="showFileUpload();">����</button>
		</logic:notEmpty>
		<logic:empty name="category" property="picURL">
		<font color="red">��Ԥ��ͼ</font> &nbsp;&nbsp;&nbsp;&nbsp;
		<button onClick="showFileUpload();">�ϴ�</button>
		</logic:empty>
		
    </td>
  </tr>
  </logic:equal>
  <tr>
    <td align="right" class="text3">�����Ƿ��ǻ��ͻ��ܣ�</td>
    <td class="text4">
	    <INPUT type="radio" name="deviceCategory" value="1" 
	    	<logic:equal value="1" name="category" property="deviceCategory"> checked="true" </logic:equal> 
	    	onclick="selectDevice(this);"/>��&nbsp; 
	    <INPUT type="radio" name="deviceCategory" value="0" 
	    	<logic:equal value="0" name="category" property="deviceCategory"> checked="true" </logic:equal> 
	    	onclick="selectDevice(this);"/>��
	    	<span id="device">
  			</span>
  			&nbsp;<font color="#ff0000">���飺�Ի��ͻ��ܵ��޸ĳɹ������ֶ�ִ�л���ȫ��ͬ�����ԡ�</font>
    </td>
  </tr>
  <tr id='deviceCategoryText'>
    <td align="right" class="text3">
    <input type="button" value="ѡ����ܶ�Ӧ����" onclick="selectDeviceId();"/>
    <input type="button" value="��ջ��ܶ�Ӧ����" onclick="clearDeviceId();"/>
    </td>
    <td class="text4">
    	<textarea name="deviceName" rows="4" cols="80" readonly><%=deviceName %></textarea>
    </td>
  </tr>
  <tr id='platFormNameText'>
	    <td align="right" class="text3">
	    <input type="button" value="ѡ���������ƽ̨" onclick="openPlatFormId();"/>
	    <input type="button" value="����Ϊȫƽ̨ͨ��" onclick="clearPlatFormId();"/>
	    <span id="platForm">
	  	</span>
    </td>
    <td class="text4">
    	<textarea name="platFormName" rows="4" cols="80" readonly><%=platFormName %></textarea>
    </td>
  </tr>
  <tr id='cityNameText'>
    <td align="right" class="text3">
	    <input type="button" value="ѡ������������" onclick="openCityId();"/>
	    <input type="button" value="����Ϊȫ��ͨ��  " onclick="clearCityId();"/>
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
			<logic:equal value="2" name="vo" property="keyType"> <input  type="file"  name="<bean:write name="vo" property="keyname"/>"   size="60"> &nbsp;��֧��png��ʽ<bean:write name="vo" property="value"/></logic:equal> 
			<logic:equal value="3" name="vo" property="keyType"> <textarea  name="<bean:write name="vo" property="keyname"/>" cols="50"><bean:write name="vo" property="value"/></textarea></logic:equal>
			<logic:notEqual value="" name="vo" property="value">
      			<input type="checkbox" value="1" name="clear_<bean:write name='vo' property='keyname'/>"/>
      			��յ�ǰ��չ�ֶ�����
      		</logic:notEqual> 
	    </td>
	  </tr>
	    </logic:iterate>
  
  
</table>
<div id="upload">
  <table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
     <tr>
    <td width="25%" align="right" class="text3">
      ��ѡ��ͼƬ�ļ���
    </td><!-- 
    <td width="75%" class="text4"><input type="file" name="dataFile12"><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;��֧��jpg��png��ʽ</font>
    </td>
    -->
  </tr>
  </table>
</div>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">
            <input name="Submit" type="submit" class="input1" value="ȷ��">
          </td>
        </tr>
        <tr>
          <td align="center" class="text5">
            <font color="#ff0000">���飺�������޸Ļ���ʱ������ǰ���ӡ�WWW������WWW�Ż����ܣ����նˡ������ն��Ż����ܣ����û���ǰ�治�ӱ�ʶ��</font>
          </td>
        </tr>
</table>
</body>
</html>
