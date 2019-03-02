<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.datasync.DataSyncTaskCenter"%>
<%@page import="java.util.Collection"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<% 
    Collection collection=  DataSyncTaskCenter.getInstance().getAllTask();
    pageContext.setAttribute("collection", collection, PageContext.PAGE_SCOPE);
 %>


<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script>
function import_zcomcontent(genLogo){
    var classType = document.getElementById("zcomType").value;  
    genLogo.disabled=true;
  window.location.href = "../zcomContentImport.jsp?type="+classType;
}
function import_new139music(genLogo){
    var classType = document.getElementById("new139musictype").value;  
    genLogo.disabled=true;
  window.location.href = "../newMusic139Import.jsp?type="+classType;
}
function import_CYcontent(genLogo){
    var classType = document.getElementById("cyType").value;  
    genLogo.disabled=true;
  window.location.href = "../CYContentImport.jsp?type="+classType;
}

function import_bMusic(genLogo){
    genLogo.disabled=true;
    if(confirm("确认执行？该操作将会重新导入基地音乐元数据，以及重新上架基地音乐货架！"))
		{ 
  window.location.href = "../baseMusicImport.jsp";
  }
}
function import_fullMusic(genLogo){
    genLogo.disabled=true;
    if(confirm("确认执行？该操作将会重新全量导入基地音乐元数据，以及重新上架基地音乐货架！"))
		{ 
  window.location.href = "dataImport.do?subSystem=ssms&type=fullMusicData";
;
  }
}
function import_comic(genLogo){
    
    if(confirm("确认执行？该操作将会重新动漫基地元数据?"))
		{
		genLogo.disabled=true; 
  window.location.href = "../dmBaseImport.jsp";
  
  }else{
  	genLogo.disabled=false;
  }
}

function import_videoSync(genLogo)
{
	if(confirm("确认执行？该操作将会清空原有视频基地元数据?"))
	{
		genLogo.disabled=true;
		window.location.href = "../baseVideo/baseVideoImport.do?perType=synVideo&synVideoType="+synVideoType.value;
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_videoSyncAll(genLogo)
{
	if(confirm("确认执行？该操作是全量导入基地视频数据（包括临时表切换动作）?"))
	{
		genLogo.disabled=true;
		window.location.href = "../baseVideo/baseVideoImport.do?perType=synVideoAll";
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_videoSyncRename(genLogo)
{
	if(confirm("确认执行该操作？"))
	{
		genLogo.disabled=true;
		window.location.href = "../baseVideo/baseVideoImport.do?perType=renameDataSync";
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_newVideoSync(genLogo)
{
	if(confirm("确认执行？该操作将会清空原有视频基地元数据?"))
	{
		genLogo.disabled=true;
		window.location.href = "../newVideo/videoImport.do?perType=synVideo&synVideoType="+synNewVideoType.value;
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_newVideoSyncAll(genLogo)
{
	if(confirm("确认执行？该操作是全量导入新基地视频数据（包括中间表切换动作）?"))
	{
		genLogo.disabled=true;
		window.location.href = "../newVideo/videoImport.do?perType=synVideoAll";
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_newVideoSyncRename(genLogo)
{
	if(confirm("确认执行该操作？"))
	{
		genLogo.disabled=true;
		window.location.href = "../newVideo/videoImport.do?perType=renameDataSync";
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_operation(genLogo)
{
    genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=operation";
}
function import_wpApp(genLogo)
{
    genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=wpInfo";
}


function synclassChange()
{
	var iss = document.getElementById("synclassType").value;
	var res = document.getElementById("synclassResourceType")
	if(iss==0)
	{
        var varItem = new Option('增量同步', '1');      
        res.options.add(varItem);  
	}
	else
	{
		for (var i = 0; i < res.options.length; i++) {        
            if (res.options[i].value == '1') {        
                res.options.remove(i);        
                break;        
            }        
        }
	}
}

function import_content(genLogo)
{
    var classType = document.getElementById("synclassType").value;  
    var synclassResourceType = document.getElementById("synclassResourceType").value;
    var isSyn = document.getElementById("isSyn").value;    
    genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=content&classType="+classType+"&isSyn="+isSyn+"&synclassResourceType="+synclassResourceType;

}
function import_CLRDATA(genLogo)
{
    genLogo.disabled=true;
	window.location = "colorringDataImport.do";
}
function import_A8Data(genLogo)
{
  genLogo.disabled=true;
  var temp=document.getElementsByName("a8TaskName");
  var taskName=temp[0];
  window.location = "A8DataImport.do?subSystem=ssms&action="+taskName.value;
}
function import_dateSync(genLogo)
{
  genLogo.disabled=true;
  var temp=document.getElementsByName("taskName");
  var taskName=temp[0];
  if(taskName.value!="")
  {
    window.location = "dataSync.do?action=doTask&taskName="+taskName.value;
  }else
  {
    alert("目前还没有同步任务");
  }
  
}

function import_openContent(genLogo)
{
	genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=open";
}

function import_countNodeNum(genLogo)
{
	genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=countNodeNum";
}

function import_bookSync(genLogo)
{
	var synBookType = document.getElementById("synBookType").value;
	
	genLogo.disabled=true;
	if(confirm("确认执行？该操作将会导入基地图书元数据，以及重新上架基地图书货架！")){
		window.location="data_import_book.jsp?synBookType="+synBookType;
	} 
}

function import_readSync(genLogo)
{
	if(confirm("确认执行？该操作将会清空原有部分阅读基地元数据?"))
	{
		genLogo.disabled=true;
		window.location.href = "../baseRead/baseReadImport.do?synReadType="+synReadType.value;
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_readSyncAll(genLogo)
{
	if(confirm("确认执行？该操作是全量导入阅读基地元数据?"))
	{
		genLogo.disabled=true;
		window.location.href = "../baseRead/baseReadImport.do?synReadType=17";
	}
	else
	{
		genLogo.disabled=false;
	}
}


function import_colorComicSyncAll(genLogo)
{
	if(confirm("确认执行？该操作是全量导入基地彩漫元数据?"))
	{
		genLogo.disabled=true;
		window.location = "dataImport.do?subSystem=ssms&type=colorComic";
	}
	else
	{
		genLogo.disabled=false;
	}
}

function import_report(genLogo)
{
  genLogo.disabled=true;
  var temp=document.getElementsByName("reportDataImport");
  var taskName=temp[0];
  window.location = "ReportImport.do?action="+taskName.value;

}

function import_reportTopList(genLogo)
{
  genLogo.disabled=true;
  var temp=document.getElementsByName("reportTopList");
  var taskName=temp[0];
  window.location = "ReportTopListImport.do?action="+taskName.value;

}

function import_waken(genLogo)
{
    genLogo.disabled=true;
	window.location = "dataImport.do?subSystem=ssms&type=waken";
}

function import_autoSyncCategory(genLogo){
    if(confirm("确认执行？该操作是向数据中心同步自动更新货架?"))
	{
		genLogo.disabled=true;
		window.location = "dataImport.do?subSystem=ssms&type=autoSyncCategory";
	}
	else
	{
		genLogo.disabled=false;
	}
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>PPMS应用数据导入</FONT></td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
<!--  removed by aiyan 2012-05-10
 <tr>
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">货架运营分类同步</td>
    <td width="30%" bgcolor="#E9F0F8" class="text1" align="left"> <input type="button" class="input1" name="genLogo" value="确定" onclick="syn_cate(this)"></td>
  </tr>
  
  --> 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS中的业务数据导入到数据库中</td>
    <td width="30%" bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_operation(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS中的内容数据导入到数据库中
                                                                  内容数据:<select id="synclassType" name="synclassType" onchange="synclassChange()">
                                                                    <option value="0">增量同步</option>
                                                                    <option value="1">全量同步</option>                                                                    
                                                                   </select>
                                                                 适配关系:<select id="synclassResourceType" name="synclassResourceType">
                                                                    <option value="0">不同步</option>
                                                                    <option value="1">增量同步</option> 
                                                                    <option value="2" selected="selected">全量同步</option>                                                                     
                                                                 </select>
                                                                 是否是紧急上线应用:<select id="isSyn" name="isSyn">
																		    	<option value="0" selected="selected">否</option>
																		    	<option value="1">是</option>
																		   </select>
    </td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_content(this)"></td>
  </tr>
  <!-- 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS中的Zcom内容数据导入到数据库中
                                                                  <select id="zcomType" name="zcomType">
                                                                    <option value="0">增量同步</option>
                                                                    <option value="1">全量同步</option>                                                                    
                                                                 </select></td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_zcomcontent(this)"></td>
  </tr>
  -->
   <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS中的创业大赛内容数据导入到数据库中
                                                                  <select id="cyType" name="cyType">
                                                                    <option value="0">增量同步</option>
                                                                    <option value="1">全量同步</option>                                                                    
                                                                 </select></td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_CYcontent(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS中的开放运营数据导入到数据库中</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_openContent(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">统计视频子栏目数与栏目下节目数</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_countNodeNum(this)"></td>
  </tr>
   <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">向数据中心同步自动更新货架</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_autoSyncCategory(this)"></td>
  </tr>
  <!-- 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">临时四大门户增量同步功能</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="确定" onclick="import_waken(this)"></td>
  </tr>
  -->
</table>
<br>
<!-- 
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>彩铃数据同步</FONT></td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">彩铃网关中的彩铃导入到数据库中</td>
    <td align="left" class="text1">
      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_CLRDATA(this)" >
    </td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>A8数据导入<br></FONT></td>
  </tr>
</table>

<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务 <select name="a8TaskName">
                                                                    <option value="music">音乐数据导入</option>
                                                                   <  <option value="singer">歌手数据导入</option>
                                                                    <option value="topList">榜单数据导入</option>                                                                   
                                                                 </select>
    </td>
    	<td align="left" class="text1">
      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_A8Data(this)">
    </td>
  </tr>
  </table>
	<br>
	-->
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>非自有业务类型数据同步</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		  <tr >
	   <td align="center">WP应用数据导入：</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="WP应用数据导入" onclick="import_wpApp(this)"></td>
	  </tr>
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务 <select name="taskName">
	     <logic:notEmpty name="collection">
	     <logic:iterate id="task" name="collection" type="com.aspire.ponaadmin.web.datasync.DataSyncTask" indexId="index">
	                                                                  <option value="<bean:write name="task" property="name" />" <logic:equal value="0" name="index">selected</logic:equal>><bean:write name="task" property="desc" /></option>
	                                                                  </logic:iterate>
	       </logic:notEmpty> <logic:empty name="collection"> <option value="">无任务</option> </logic:empty>                                                           
	                                                                   </select>
	    </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_dateSync(this)">
	    </td>
	  </tr>
	  <tr >
	   <td align="center">基地音乐数据导入：</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="基地音乐数据导入" onclick="import_bMusic(this)"></td>
	  </tr>
	  
	  <tr >
	   <td align="center">动漫基地数据导入：</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="动漫基地数据导入" onclick="import_comic(this)"></td>
	  </tr>
	  	  <tr >
	   <td align="center">基地音乐歌曲数据全量导入：</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="基地音乐歌曲数据全量导入" onclick="import_fullMusic(this)"></td>
	  </tr>
	  
		<tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">基地图书数据
			<select id="synBookType" name="synBookType">
		    <option value="author">基地图书作者信息导入1</option>
		    <option value="type">基地图书分类信息导入2</option>
		    <option value="book">基地图书信息导入3</option>
		    <option value="month">基地图书包月信息导入4</option>
		    <option value="recommend">基地图书推荐图书信息导入5</option>
		    <option value="catalog">基地图书专区信息导入6</option>
		    <option value="catalogcontent">基地图书专区内容信息导入7</option>
		    <option value="rank">基地图书排行信息导入8</option>
		</select>
	    </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="bookLogo" value="确定" onclick="import_bookSync(this)">
	    </td>
		</tr>	
		<tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">基地视频数据    
		    <select id="synVideoType" name="synVideoType">
			    <option value="11">视频文件内容增量导入</option>
			    <option value="4">节目详情内容导入</option>
			    <option value="5">栏目文件内容导入</option>
			    <option value="6">直播节目单内容导入</option>
			    <option value="8">产品内容导入</option>
			    <option value="9">视频节目统计</option>
			    <option value="10">视频内容集节点导入</option>
			    <option value="15">视频内容集导入</option>
			    <option value="12">视频文件内容按小时增量导入</option>
			    <option value="13">节目详情文件内容按小时增量导入</option>
			    <option value="14">热点内容推荐数据导入</option>
			    <option value="222">产品打折关系信息导入</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="确定" onclick="import_videoSync(this)">
			<font color=red>&nbsp;&nbsp;<b>（注：数据入中间表，请在全部导入后执行，基地视频全部中间表处理）<b></font>
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">基地视频全部中间表切换
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="确定" onclick="import_videoSyncRename(this)">
	    </td>
	  </tr>	 
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">全量导入基地视频数据（包括临时表切换动作）
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="全量导入" onclick="import_videoSyncAll(this)">
	    </td>
	  </tr>	  
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">新基地视频数据    
		    <select id="synNewVideoType" name="synNewVideoType">
			    <option value="1">视频普通节目文件内容导入</option>
			    <option value="3">业务产品内容导入</option>
			    <option value="4">产品包促销计费内容导入</option>
			    <option value="5">计费数据内容导入</option>
			    <option value="6">热点主题列表内容导入</option>
			    <option value="2">视频普通节目文件内容按小时增量导入</option>
			    <option value="7">视频节目详情xml文件内容导入</option>
			    <option value="8">直播节目单xml文件内容导入</option>
			    <option value="9">热点内容xml文件内容导入</option>
			    <option value="10">榜单发布内容导入</option>
			    <option value="11">产品包促销计费api请求数据</option>
			    <option value="12">热点主题列表api请求数据</option>
			    <option value="13">业务产品api请求</option>
			    <option value="14">计费信息数据api请求</option>
			    <option value="15">普通节目数据api请求</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="确定" onclick="import_newVideoSync(this)">
			<font color=red>&nbsp;&nbsp;<b>（注：节目详情相关数据入中间表，请在全部xml文件内容导入后执行，新基地视频中间表处理）<b></font>
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">新基地视频节目详情相关中间表切换
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="确定" onclick="import_newVideoSyncRename(this)">
	    </td>
	  </tr>	 
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">全量导入新基地视频数据（包括中间表切换动作）
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="全量导入" onclick="import_newVideoSyncAll(this)">
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">基地阅读数据    
		    <select id="synReadType" name="synReadType">
			    <option value="1">图书分类内容导入</option>
			    <option value="2">作者信息内容导入</option>
			    <option value="3">图书信息内容导入</option>
			    <option value="4">推荐图书内容导入</option>
			    <option value="5">图书统计内容导入</option>
			    <option value="6">图书更新内容导入</option>
			    <option value="7">书包信息内容导入</option>
			    <option value="16">书包地域内容导入</option>
			    <option value="8">终端目录内容导入</option>
			    <option value="9">专区信息导入</option>
			    <option value="10">专区内容信息导入</option>
			    <option value="11">排行榜信息导入</option>
			    <option value="12">猜你喜欢--阅读推荐导入</option>
			    <option value="13">猜你喜欢--名家推荐导入</option>
			    <option value="14">猜你喜欢--阅读关联推荐导入</option>
			    <option value="15">猜你喜欢--订购关联推荐导入</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="确定" onclick="import_readSync(this)">
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">全量导入基地阅读数据    
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="全量导入" onclick="import_readSyncAll(this)">
	    </td>
	  </tr>	 	   
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">全量导入基地彩漫数据    
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="全量导入" onclick="import_colorComicSyncAll(this)">
	    </td>
	  </tr>
	  
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>报表数据导入</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务 <select name="reportDataImport">   																
                                                                    <option value="appstatistic">应用最新,推荐,小编推荐数据导入</option>
                                                                    <option value="contentOfDay">应用日运营数据导入</option>
                                                                    <option value="contentOfWeek">应用周运营数据导入</option>
                                                                    <option value="contentOfMonth">应用月运营数据导入</option>
                                                                    <option value="a8statistic">A8报表统计导入</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_report(this)">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>创业大赛报表数据导入</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务 <select name="reportTopList">   																
                                                                    <option value="0">TOP榜单数据全部导入</option>
                                                                    <option value="1">应用类作品人气指数TOP60数据导入</option>
                                                                    <option value="2">创意类作品人气指数TOP30数据导入</option>
                                                                    <option value="3">应用类作品星探得分TOP50数据导入</option>
                                                                    <option value="4">创意类作品星探得分TOP50数据导入</option>
                                                                     <option value="5">市场PK应用累计收入数据导入</option>
                                                                    <option value="cylist">创业大赛作品报表统计导入</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_reportTopList(this)">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>139音乐接口</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务 <select id="music139Operation">   																
                                                                    <option value="billbor">139音乐接口--榜单基础信息同步(当天增量)</option>												
                                                                    <option value="ablum">139音乐接口--专辑基础信息同步(当天增量)</option>												
                                                                    <option value="keyword">139音乐接口--搜索关键字信息同步(当天增量)</option>										
                                                                    <option value="musicTag">139音乐接口--歌曲标签信息同步(当天增量)</option>
                                                                    <option value="picSysnchro">139音乐接口--专辑和歌曲图片同步</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_music139(this)">
	    </td>
	  </tr>
	    <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">请选择任务(新139音乐) <select id="new139musictype">   																
                                                                    <option value="billbor">139音乐接口--榜单基础信息同步(当天增量)</option>												
                                                                    <option value="ablum">139音乐接口--专辑基础信息同步(当天增量)</option>												
                                                                    <option value="keyword">139音乐接口--搜索关键字信息同步(当天增量)</option>										
                                                                    <option value="musicTag">139音乐接口--歌曲标签信息同步(当天增量)</option>
                                                                    <option value="picSysnchro">139音乐接口--专辑和歌曲图片同步</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="确定" onclick="import_new139music(this)">
	    </td>
	  </tr>
	</table>
	<script type="text/javascript">
	
	
	//	function syn_cate(){
	//		window.location.href = "../../web/catesyn.do";
	//	}
	
		function import_music139(btn){
			btn.disabled = true;
			var op = document.getElementById("music139Operation").value;
			window.location.href = "../../web/music139/ablumsynchro.do?operation=" + op;
		}
		function import_music1392(){
			 request = createRequest();
			var op = document.getElementById("music139Operation").value;
			if(request){
				request.onreadystatechange=state_Change;
				request.open("GET","../../web/music139/ablumsynchro.do?operation=" + op,true);
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
</body>
</html>
