<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>Ӫ�������Ż�����ƽ̨</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
	</head>
	<script>
function export_A8Data(genLogo)
{
  var temp=document.getElementsByName("a8TaskName");
  var taskName=temp[0];
  window.location.href ="DataExport.do?exportType=content&fileType="+taskName.value;
}
function repchange_export(){
   
   //window.open('../category_export.jsp','newwindow', 'height=200, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
  window.location.href = '../repchange_export.jsp';
}
function CYAuto_export(){
   
   //window.open('../category_export.jsp','newwindow', 'height=200, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
  window.location.href = '../CYContentauto.jsp';
}
function category_export(){
   
   //window.open('../category_export.jsp','newwindow', 'height=200, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
  window.location.href = '../category_export.jsp';
}
function category_mupdate(){
  if(confirm("ȷ��ִ�У��ò������ᵼ�������Զ����»��������ϼܣ�"))
		{ 
  				//window.open('../category_export.jsp','newwindow', 'height=200, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
  			window.location.href = '../category_mupdate.jsp';
 		 }
}
function mupdate()
{
 //window.location.href = "manUpdateCategory.do";
window.open('manUpdateCategory.do','newwindow', 'height=500, width=800, top=50, left=50, toolbar=no, menubar=no, scrollbars=yes,resizable=no,location=yes, status=yes');
  
}
function content_data_export()
{
	 var temp=document.getElementsByName("searchExopotType");
	  var taskName=temp[0];
	  window.location.href = "DataExport.do?exportType=search&fileType="+taskName.value;
}

function content_data_exportSearAll(genLogo)
{
genLogo.disabled=true;
	window.location.href = "DataExport.do?exportType=search&fileType=all";
}

function market_data_export()
{
	 var temp=document.getElementsByName("market");
	  var taskName=temp[0];
	  window.location.href = "DataExport.do?exportType=market&fileType="+taskName.value;
}
function experience_data_export()
{
	 var temp=document.getElementsByName("experience");
	  var taskName=temp[0];
	  window.location.href = '../experience_data_export.jsp?opType='+taskName.value;
}

function baseFileExport()
{
	var temp=document.getElementsByName("exportType");
	var taskName=temp[0];
	window.location.href = 'baseFileExport.do?exportType='+taskName.value;
}

function logFileExport()
{
	var temp=document.getElementsByName("logFileType");
	var taskName=temp[0];
	window.location.href = 'logFileExport.do?exportType='+taskName.value;
}

function data_export_channel(){
	window.location.href = 'data_export_channel.jsp';
}

function data_apwarn(){
	window.location.href = 'data_apwarn.jsp';
}
function data_cirle(){
	window.location.href = 'data_cirle.jsp';
}
function data_export_entitycard(){
	//entitycardExport
	  window.location.href = 'data_export_entitycard.jsp?opType='+document.getElementById('entitycardExport').value;	
}
function apps_export(){
	request = createRequest();
	if(request){
		//request.setRequestHeader("Cache-Control","no-cache");
		request.onreadystatechange=state_Change;
		request.open("GET","../../web/appsExport/export.do?opertion=All&dt="+new Date().getTime(),true);
		request.send(null);
	}
}

function export_wapCategory(){
	 request = createRequest();
	if(request){
		var url = "";
		if(document.getElementById("wapcategoryExport").value=="wapcategory"){
			url = "../../web/wapCategoryExport/export.do?dt="+new Date().getTime();
		}
		if(document.getElementById("wapcategoryExport").value=="wapAppAll"){
			url = "../../web/appsExport/export.do?opertion=All&dt="+new Date().getTime();
		}
		if(document.getElementById("wapcategoryExport").value=="wapAppDay"){
			url = "../../web/appsExport/export.do?opertion=Day&dt="+new Date().getTime();
		}
		request.onreadystatechange=state_Change;
		request.open("GET",url,true);
		request.send(null);
	}
}

function state_Change(){
	if(request.readyState==4){
		alert(request.responseText);
	}
}

function createRequest(){
	var xmlHttp = false;
	try {
	    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
	    try {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } catch (e2) {
	        xmlHttp = false;
	
	    }
	}
	if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
	    xmlHttp = new XMLHttpRequest();
	}
	if(xmlHttp) xmlHttp
	return xmlHttp;
}
	</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>�������ݵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="a8TaskName">
						<option value="music">
							�������ֵ���
						</option>
						<option value="book">
							����ͼ�鵼��
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					<font color="red">��������ռ�����ݿ���Դ������Ƶ��������</font>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ȷ��"
						onclick="export_A8Data(this);">
				</td>
			</tr>
		</table>

		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>ָ��������Ʒ���ݵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="category_export();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>ָ�����ܰ��ظ����ֻ��ʵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="repchange_export();">
				</td>
			</tr>
		</table>
		
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>������Ʒ�ֶ�����(�������Զ����¹���ʱ�����Ϊ��������)<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�в��ָ���"
						onclick="mupdate();">
				</td>
   <td align="center"><input type="button" class="input1" name="genLogo" value="ִ��ȫ������"
						onclick="category_mupdate();"></td>  
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>��ҵ����������Ʒ�ֶ�����(�������Զ����¹���ʱ�����Ϊ��������)<br> </FONT>
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				
   <td align="center"><input type="button" class="input1" name="genLogo" value="ִ��ȫ������"
						onclick="CYAuto_export();"></td>  
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>�����������ݵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="searchExopotType">
						<option value="software">
							����������ݵ���
						</option>
						<option value="game">
							��Ϸ�������ݵ���
						</option>
						<option value="theme">
							�����������ݵ���
						</option>
						<!-- 
						<option value="audio">
							A8ȫ���������ݵ���
						</option>
						<option value="book">
							�㶫ͼ���������ݵ���
						</option>
						<option value="colorring">
							�����������ݵ���
						</option>
						
						<option value="comic">
							�����������ݵ���
						</option>
						-->
						<option value="music">
							���������������ݵ���
						</option>
						<option value="read">
							���ض����������ݵ���
						</option>
						<option value="bread">
							�����Ķ��������ݵ���
						</option>
						<option value="dissertation">
							�ר���������ݵ���
						</option>
						<option value="video">
							��Ƶ��Ŀ�������ݵ���
						</option>
						<option value="NEWMUSIC">
							�»����������ݵ���
						</option>
						<option value="comic">
							�����������ݵ���
						</option>
						<option value="tag">
							�ͻ����ص�Ӧ�ñ�ǩ���ݵ���
						</option>
						<option value="cy_data_2010">
							2011��ҵ�������ݵ���
						</option>
						<option value="cy_data_2012">
							2012��ҵ�������ݵ���
						</option>
						<option value="cy_data_2013">
							2013��ҵ�������ݵ���
						</option>
						<option value="cy_data_market">
							�г�PK���ݵ���
						</option>
						<option value="cy_data_ultimate">
							�ռ�PK���ݵ���
						</option>
						<option value="temp">
							�������������ݵ���
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<table width="95%" border="0" align="center" cellspacing="3"
					bgcolor="#FFFFFF">
					<tr class="text1">
						<td align="center" width="50%">
							<input type="button" class="input1" name="genLogo" value="ִ�е���"
								onclick="content_data_export();">
						</td>
						<td align="center">
							<input type="button" class="input1" name="genLogo" value="ȫ��ִ�е��������ļ�"
								onclick="content_data_exportSearAll(this);">
						</td>
					</tr>
				</table>
				
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>�Ļ�ƽ̨���ݵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="market">
						<option value="topList">
							Ӧ����Ϣ���ݵ���
						</option>
						<option value="device">
							�ն�����Ӧ����Ϣ����
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="market_data_export();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>����Ӫ���Ͷ��������ݵ���<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="experience">
					<option value="1">
							Ӧ�÷�����Ϣ���ݵ���
						</option>
						<option value="2">
							����������Ϣ����
						</option>
						<option value="3">
							ȫ��������Ϣ����
						</option>
						<option value="4">
							��Ӧ�����ݵ���
						</option>
						<option value="5">
							����Ӧ����Ϣͬ������
						</option>
						<option value="6">
							ȫ��Ӧ����Ϣͬ������
						</option>
						<option value="7">
							Ӧ����Ϣ��������ͬ������
						</option>
						<option value="0">
							ȫ��������Ϣ���ݵ���
						</option>
						
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="experience_data_export();">
				</td>
			</tr>
		</table>
	    <br />
	    <br />
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>����������Ӫ�����ݵ���<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="data_export_channel();">
				</td>
			</tr>
		</table>	    
	    <br />
	    <br />	    	
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>ʵ�忨ƽ̨�����ݵ���<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="entitycardExport">
					<option value="1">
							AP��Ϣͬ���ӿ�ȫ������
						</option>
						<option value="2">
							ҵ����Ϣͬ���ӿ�ȫ������
						</option>
						<option value="3">
							AP��Ϣͬ���ӿ���������
						</option>
						<option value="4">
							ҵ����Ϣͬ���ӿ���������
						</option>
						
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="data_export_entitycard();">
				</td>
			</tr>
		</table>	
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>WAP�������ݵ���<br> </FONT>
				</td>
			</tr>
		<br>
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select id="wapcategoryExport">
						<option value="wapcategory">
							WAP�������ݵ���
						</option>
						<option value="wapAppAll">
							WAP����Ӧ������ȫ������
						</option>
						<option value="wapAppDay">
							WAP����Ӧ��������������
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="export_wapCategory(this);">
				</td>
			</tr>
		</table>
		<br/>
		<br/>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>APˢ��Ԥ������<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="data_apwarn();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>Ȧ��Ӫ��-���ţ�139���䣬139˵�����ݵ���<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="data_cirle();">
				</td>
			</tr>
		</table>	
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>�㽭MSTOREƽ̨�ļ�����<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="exportType">
					<option value="deviceBase">
							������Ϣͬ���ļ�
						</option>
						<option value="appBase">
							Ӧ����Ϣͬ��
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="baseFileExport();">
				</td>
			</tr>
		</table>	
		
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>ά����־�ļ�����<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					��ѡ������
					<select name="logFileType">
					<option value="categoryFile">
							����ά����־�ļ�
						</option>
						<option value="referenceFile">
							��Ʒά����־�ļ�
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="ִ�е���"
						onclick="logFileExport();">
				</td>
			</tr>
		</table>	
	</body>
</html>
