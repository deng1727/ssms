<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="load();">
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã����ܹ�������----��չ��������</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">��չ��������</td>
  </tr>
</table>
<form name="keybaseForm" action="keyImputData.do" method="post" enctype="multipart/form-data">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
	    <td width="35%" align="right" class="text3">������</td>
	    <td width="65%" class="text4">
		    <SELECT name="keytable" id="keytable" onchange="tableChange()">
		    	<option value="">��ѡ��</option>
		    </SELECT>
	    </td>
  	</tr>
  	<tr>
	    <td width="35%" align="right" class="text3">�ֶ�����</td>
	    <td width="65%" class="text4" id="keyName">
		    <logic:notEmpty name="keyNameList">
		    	<table width="50%"  border="1" align="left" cellspacing="1">
		    	<logic:iterate id="vo" indexId="ind" name="keyNameList" type="com.aspire.ponaadmin.web.datafield.vo.ResourceVO">
		    	<tr>
		    		<td>
		    			<input type="radio" name="keyid" value="<bean:write name="vo" property="keyid"/>" checked="checked"/>
		    		</td>
		    		<td>
		    			<bean:write name="vo" property="keyname"/>
		    		</td>
		    		<td>
		    			<bean:write name="vo" property="keydesc"/>
		    		</td>
		    		<td>
		    			<logic:equal value="1" name="vo" property='keyType'>
		    				��ͨ�ı�
		    			</logic:equal>
		    			<logic:equal value="3" name="vo" property='keyType'>
		    				���ı�
		    			</logic:equal>
		    		</td>
		    	</tr>
				</logic:iterate>
				</table>
		    </logic:notEmpty>
	    </td>
  	</tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="3">
  <tr>
    <td align="right" width="30%">
    	ѡ��Ҫ�����excel�����ļ���
    </td>
    <td align="left" width="70%">
		<input type="file" name="dataFile">(����һ��Ϊ��Ӧ���ݵ�id���ڶ���Ϊ�����ݵ���չ�������ݡ�)
    </td>
   </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="text5"><input name="button" type="button" class="input1" onClick="importData();"  value="����������չ������Ϣ"> <input name="button2" type="button" class="input1"  onClick="back();" value="����"></td>
	</tr>
</table>
</form>
</body>
</html>
<script language="JavaScript">
function load()
{
	var keytableObj=document.getElementById("keytable");
	var keytableStr="";
	var keytableArr= new Array(); 
	
	<logic:iterate id="keytablename" indexId="ind" name="keytableArr" >
	keytableStr='<bean:write name="keytablename"/>';
	keytableArr=keytableStr.split('|');
	keytableObj.options.add(new Option(keytableArr[1],keytableArr[0]));
	</logic:iterate>
	
	var keytable = '<bean:write name="keyTable"/>';
	var option = keytableObj.options;
	
	for(var i=0; i<option.length; i++)
	{
		if(option[i].value==keytable)
		{
			option[i].selected = true;
		}
	}
}

function back()
{
	keybaseForm.action = "keyBaseList.do";
	keybaseForm.submit();
}

function tableChange()
{
	var keytableObj=document.getElementById("keytable");
	var option = keytableObj.options;
	var v = "";
	for(var i=0; i<option.length; i++)
	{
		if(option[i].selected==true)
		{
			v = option[i].value;
		}
	}
	
	var keyName=document.getElementById("keyName");
	keyName.innerHTML = v;
	keybaseForm.action="keyBaseImput.do?keyTable=" + v;
	keybaseForm.submit();
}

function importData()
{
	if(keybaseForm.keyid == undefined)
	{
		alert("��ѡ��һ������д�����չ�ֶ�����");
		return false;
	}

	var filePath = keybaseForm.dataFile.value;
	
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
	keybaseForm.submit();
}

function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}
</script>