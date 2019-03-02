<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>营销体验门户管理平台</title>
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
  if(confirm("确认执行？该操作将会导致所有自动更新货架重新上架！"))
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
					<FONT class=large>内容数据导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="a8TaskName">
						<option value="music">
							基地音乐导出
						</option>
						<option value="book">
							基地图书导出
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					<font color="red">导出操作占用数据库资源，请勿频繁操作！</font>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="确定"
						onclick="export_A8Data(this);">
				</td>
			</tr>
		</table>

		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>指定货架商品数据导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="category_export();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>指定货架榜单重复率轮换率导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="repchange_export();">
				</td>
			</tr>
		</table>
		
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>货架商品手动更新(将所有自动更新规则时间调整为昨天后更新)<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行部分更新"
						onclick="mupdate();">
				</td>
   <td align="center"><input type="button" class="input1" name="genLogo" value="执行全部更新"
						onclick="category_mupdate();"></td>  
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>创业大赛货架商品手动更新(将所有自动更新规则时间调整为昨天后更新)<br> </FONT>
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
		
			<tr bgcolor="#E9F0F8">
				
   <td align="center"><input type="button" class="input1" name="genLogo" value="执行全部更新"
						onclick="CYAuto_export();"></td>  
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>搜索引擎数据导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="searchExopotType">
						<option value="software">
							软件类型数据导出
						</option>
						<option value="game">
							游戏类型数据导出
						</option>
						<option value="theme">
							主题类型数据导出
						</option>
						<!-- 
						<option value="audio">
							A8全曲类型数据导出
						</option>
						<option value="book">
							广东图书类型数据导出
						</option>
						<option value="colorring">
							彩铃类型数据导出
						</option>
						
						<option value="comic">
							动漫类型数据导出
						</option>
						-->
						<option value="music">
							基地音乐类型数据导出
						</option>
						<option value="read">
							基地读书类型数据导出
						</option>
						<option value="bread">
							基地阅读类型数据导出
						</option>
						<option value="dissertation">
							活动专题类型数据导出
						</option>
						<option value="video">
							视频节目类型数据导出
						</option>
						<option value="NEWMUSIC">
							新基地音乐数据导出
						</option>
						<option value="comic">
							动漫基地数据导出
						</option>
						<option value="tag">
							客户端重点应用标签数据导出
						</option>
						<option value="cy_data_2010">
							2011创业大赛数据导出
						</option>
						<option value="cy_data_2012">
							2012创业大赛数据导出
						</option>
						<option value="cy_data_2013">
							2013创业大赛数据导出
						</option>
						<option value="cy_data_market">
							市场PK数据导出
						</option>
						<option value="cy_data_ultimate">
							终极PK数据导出
						</option>
						<option value="temp">
							紧急上下线数据导出
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<table width="95%" border="0" align="center" cellspacing="3"
					bgcolor="#FFFFFF">
					<tr class="text1">
						<td align="center" width="50%">
							<input type="button" class="input1" name="genLogo" value="执行导出"
								onclick="content_data_export();">
						</td>
						<td align="center">
							<input type="button" class="input1" name="genLogo" value="全量执行导出搜索文件"
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
					<FONT class=large>心机平台数据导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="market">
						<option value="topList">
							应用信息数据导出
						</option>
						<option value="device">
							终端适配应用信息导出
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="market_data_export();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>体验营销劳动竞赛数据导出<br> </FONT>
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="experience">
					<option value="1">
							应用分类信息数据导出
						</option>
						<option value="2">
							增量机型信息导出
						</option>
						<option value="3">
							全量机型信息导出
						</option>
						<option value="4">
							榜单应用数据导出
						</option>
						<option value="5">
							增量应用信息同步导出
						</option>
						<option value="6">
							全量应用信息同步导出
						</option>
						<option value="7">
							应用信息更新数据同步导出
						</option>
						<option value="0">
							全部增量信息数据导出
						</option>
						
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
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
					<FONT class=large>第三方渠道营销数据导出<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
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
					<FONT class=large>实体卡平台局数据导出<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="entitycardExport">
					<option value="1">
							AP信息同步接口全量导出
						</option>
						<option value="2">
							业务信息同步接口全量导出
						</option>
						<option value="3">
							AP信息同步接口增量导出
						</option>
						<option value="4">
							业务信息同步接口增量导出
						</option>
						
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="data_export_entitycard();">
				</td>
			</tr>
		</table>	
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>WAP货架数据导出<br> </FONT>
				</td>
			</tr>
		<br>
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select id="wapcategoryExport">
						<option value="wapcategory">
							WAP货架数据导出
						</option>
						<option value="wapAppAll">
							WAP货架应用数据全量导出
						</option>
						<option value="wapAppDay">
							WAP货架应用数据增量导出
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
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
					<FONT class=large>AP刷榜预警数据<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="data_apwarn();">
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>圈子营销-飞信，139邮箱，139说客数据导出<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">

			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="data_cirle();">
				</td>
			</tr>
		</table>	
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>浙江MSTORE平台文件导出<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="exportType">
					<option value="deviceBase">
							机型信息同步文件
						</option>
						<option value="appBase">
							应用信息同步
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="baseFileExport();">
				</td>
			</tr>
		</table>	
		
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>维表日志文件导出<br> </FONT>
				</td>
			</tr>
		</table>		
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
			<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择任务
					<select name="logFileType">
					<option value="categoryFile">
							货架维表日志文件
						</option>
						<option value="referenceFile">
							商品维表日志文件
						</option>
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="执行导出"
						onclick="logFileExport();">
				</td>
			</tr>
		</table>	
	</body>
</html>
