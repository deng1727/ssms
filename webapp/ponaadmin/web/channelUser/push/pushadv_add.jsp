<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>

<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
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
	if(!checkLength(form.contentId,"Ӧ��ID",-1,30)) return false;
	if(!checkLength(form.title,"������",-1,100)) return false;
	if(!checkLength(form.subTitle,"������",-1,100)) return false;
	if(!checkLength(form.startTime,"��ʼʱ��",-1,30)) return false;
	if(!checkLength(form.endTime,"����ʱ��",-1,30)) return false;
	if(eval(form.startTime).value >= eval(form.endTime).value){
		alert("����ʱ�������ڿ�ʼʱ��");
		return false;
	}
	return true;
  
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

function selectContentId()
{
	var returnv=window.showModalDialog("<%=request.getContextPath()%>/web/channelUser/pushAdv.do?perType=content&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	if(returnv != undefined)
	{
		document.getElementById('contentId').value = returnv;
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

</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	    ��������
	    </td>
	  </tr>
	</table>
<form name="form1" method="post" action="pushAdv.do" onSubmit="return checkForm(form1);">

<input type="hidden" name="perType" value="save">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000">(*)</font>Ӧ��ID��
    </td>
  <td width="75%" class="text4">
	   <input name="contentId" id = "contentId" type="text" size="30">
	   <input type="button" value="ѡ��Ӧ��" onclick="selectContentId();"/>
	    <span id="platForm"></span>
	    </td>
  </tr>

  <tr>
    <td width="25%" align="right" class="text3">
      <font color="#ff0000">(*)</font>�����⣺ 
    </td>
    <td width="75%" class="text4">
       <input name="title" type="text" value="" size="100">
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�����⣺</td>
    <td class="text4">
       <input name="subTitle" type="text" value="" size="100">
    </td>
  </tr>

  <tr>
 	<td align="right" class="text3">
		��ʼʱ�䣺
	</td>
	<td class="text4">
	 <input name="startTime" class="Wdate" type="text"
							id="textbox2" style="width:170px"
							onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
							value=""
							readonly="true" />
	</td>
  </tr>		
  <tr>
	<td align="right" class="text3">
		����ʱ�䣺
	</td>
	<td class="text4">
		<input name="endTime" class="Wdate" type="text"
							id="textbox2" style="width:170px"
							onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
							value=""
							readonly="true"/>
	</td>
  </tr>
 
 <tr>
    <td align="right" class="text3">�Ƽ�Ʒ�ƣ�</td>
    <td class="text4">
<!--        <select name="reBrand" class="text_sketch"> -->
<!--           <option value="ȫ��">ȫ��</option> -->
<!--           <option value="����">����</option> -->
<!--           <option value="С��">С��</option> -->
<!--         </select> -->
        
        <select name="reBrand"  class="text_sketch">
				
				<logic:iterate id="brand" name="brandList" >
					<option value="<bean:write name="brand" />" ><bean:write name="brand" /></option>
				</logic:iterate>
			</select>
    </td>
  </tr>

</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">
            <input name="Submit" type="submit" class="input1" value="����">
          </td>
        </tr>
</table>
</body>
</html>
