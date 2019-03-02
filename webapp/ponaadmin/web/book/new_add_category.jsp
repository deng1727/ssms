<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>M-Market�Ķ����ܹ���ϵͳ</title>
		<%
		                            String platFormName = request.getAttribute("platFormName") == null ? ""
		                            : ( String ) request.getAttribute("platFormName");
		            String cityName = request.getAttribute("cityName") == null ? ""
		                            : ( String ) request.getAttribute("cityName");
		%>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="JavaScript" type="text/javascript">
//   add by tungke 
function thischeckContent(field)
{
	var value = eval(field).value;
    //���Ϊ�գ��������ݼ���
    if( getLength(value) == 0) return true;
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
	if(!checkLength(form.decrisption,"��������",0,500)) return false;


	
	var sort = form.sortId.value;
	
	var reg = /[0-9]{1}/;
	var reg2 = /[1-9][0-9]+/;
	
	if(reg.exec(sort) != sort && reg2.exec(sort) != sort)
	{
	    alert("����IDֻ��Ϊ��ֵ�ͣ����������룡");
	    form.sortId.focus();
		return false;
	}

	return true;
}

function clearFile()
{
	var fileup = document.getElementById("dataFile");
	fileup.outerHTML = fileup.outerHTML;
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

function selectPlatFormId()
{
	var returnv=window.showModalDialog("../resourcemgr/categoryPlatForm.do?isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
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

function selectCityId()
{
	var returnv=window.showModalDialog("../baseRead/categoryTree.do?perType=city&isFirst=1&pCategoryID=<bean:write name="pCategoryId" />&categoryID=<bean:write name="readCategory" property="id"/>", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
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
		<form name="form1" method="post" action="categorySave.do"
			enctype="multipart/form-data" onSubmit="return checkForm(form1);">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="title1">
						����ͼ�����
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="25%" align="right" class="text3">
						<input type="hidden" name="perType" value="save">
						<input type="hidden" name="pCategoryId"
							value="<bean:write name="pCategoryId" />">
						<input type="hidden" name="categoryId"
							value="<bean:write name="readCategory" property="id"/>">
						<input name="catalogType" type="hidden" value="0">
						<font color="#ff0000">(*)</font>�������ƣ�
					</td>
					<td width="75%" class="text4">
						<input name="name" type="text"
							value="<bean:write name="readCategory" property="categoryName"/>"
							size="50">
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						����������
					</td>
					<td class="text4">
						<textarea name="decrisption" cols="50"><bean:write name="readCategory" property="decrisption" /></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						�Ƿ����Ż�չʾ��
					</td>
					<td class="text4">
						<input name="type" type="radio" value="1" checked>
						��
						<input name="type" type="radio" value="0">
						��
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						����������Ϣ��
					</td>
					<td class="text4">
						<input name="sortId" type="text" value='0' size='8' />
					</td>
				</tr>
				<tr id='platFormNameText'>
					<td align="right" class="text3">
						<input type="button" value="ѡ���������ƽ̨"
							onclick="selectPlatFormId();" />
						<input type="button" value="����Ϊȫƽ̨ͨ��" onclick="clearPlatFormId();" />
						<span id="platForm"> </span>
					</td>
					<td class="text4">
						<textarea name="platFormName" rows="4" cols="80" readonly><%=platFormName%></textarea>
					</td>
				</tr>
				<tr id='cityNameText'>
					<td align="right" class="text3">
						<input type="button" value="ѡ������������" onclick="selectCityId();" />
						<input type="button" value="����Ϊȫ��ͨ��  " onclick="clearCityId();" />
						<span id="city"> </span>
					</td>
					<td class="text4">
						<textarea name="cityName" rows="4" cols="80" readonly><%=cityName%></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="text3">
						ͼƬ���ã�
					</td>
					<td class="text4">
						<input type="file" name="dataFile" id="dataFile">
						<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;��֧��jpg��png��ʽ</font>
						<!-- &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="input1" onclick="clearFile()"  value="���ͼƬ��Ϣ"> -->
					</td>
				</tr>
				<logic:iterate id="vo" indexId="ind" name="keyBaseList"
					type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
					<tr>
						<td align="right" class="text3">
							<bean:write name="vo" property="keydesc" />
						</td>
						<td class="text4">
							<logic:equal value="1" name="vo" property="keyType">
								<input name="<bean:write name="vo" property="keyname"/>"
									type="text" value="<bean:write name="vo" property="value"/>"
									size="60">
							</logic:equal>
							<logic:equal value="2" name="vo" property="keyType">
								<input type="file"
									name="<bean:write name="vo" property="keyname"/>" size="60"> &nbsp;��֧��png��ʽ</logic:equal>
							<logic:equal value="3" name="vo" property="keyType">
								<textarea name="<bean:write name="vo" property="keyname"/>"
									rows="5" cols="40"></textarea>
							</logic:equal>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="ȷ��">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
