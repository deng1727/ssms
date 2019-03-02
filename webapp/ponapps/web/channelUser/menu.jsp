<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<script language="JavaScript1.2">

function(){
	alert("dfda");
}

scores = new Array(20);
var numTotal=0;
NS4 = (document.layers) ? 1 : 0;
IE4 = (document.all) ? 1 : 0;
ver4 = (NS4 || IE4) ? 1 : 0;
if (ver4) {    
with (document) {       
write("<STYLE TYPE='text/css'>");        
if (NS4) {            
write(".parent {position:absolute; visibility:visible}");            
write(".child {position:absolute; visibility:visible}");            
write(".regular {position:absolute; visibility:visible}")        
}else {            
write(".child {display:none}")        
}        
write("</STYLE>");    
}}
function getIndex(el) {    
ind = null;    
for (i=0; i<document.layers.length; i++) {        
whichEl = document.layers[i];        
if (whichEl.id == el) {            
ind = i;            
break;        
}    
}    
return ind;
}
function arrange() {    
nextY = document.layers[firstInd].pageY +document.layers[firstInd].document.height;    
for (i=firstInd+1; i<document.layers.length; i++) {        
whichEl = document.layers[i];        
if (whichEl.visibility != "hide") {            
whichEl.pageY = nextY;            
nextY += whichEl.document.height;        
}    
}}
function initIt(){    
if (!ver4) return;    
if (NS4) {        
for (i=0; i<document.layers.length; i++) {            
whichEl = document.layers[i];            
if (whichEl.id.indexOf("Child") != -1) whichEl.visibility = "hide";       
}        

arrange();    
}    
else {
divColl = document.all.tags("DIV");        
for (i=0; i<divColl.length; i++) {
whichEl = divColl(i);            
if (whichEl.className == "child") whichEl.style.display = "none";        
}}}
function expandIt(el) 
{	if (!ver4) return;    
if (IE4) {        
whichEl1 = eval(el + "Child");	
for(i=1;i<=numTotal;i++){
	whichEl = eval(scores[i] + "Child");	
	if(whichEl!=whichEl1) {				
		whichEl.style.display = "none";			
	}
}        
whichEl1 = eval(el + "Child");        
if (whichEl1.style.display == "none") {            
	whichEl1.style.display = "block";        
}else{            
	whichEl1.style.display = "none";        
}}else{        
	whichEl = eval("document." + el + "Child");		
for(i=1;i<=numTotal;i++){
	whichEl = eval("document." + scores[i] + "Child");
if(whichEl!=whichEl1) {				
	whichEl.visibility = "hide";			
}		
}        
if (whichEl.visibility == "hide") {            
	whichEl.visibility = "show";        
}else {            
	whichEl.visibility = "hide";
}        
arrange();
}}
onload = initIt;
</script>

<body bgcolor="#DCECF3" leftmargin="0" topmargin="5">
          <table border=0 borderlight=green width="150">
            <tr>
              <td width="155" align=left>
                
                <!-- kb1 账号管理 -->
                <div id='KB1Parent' class='parent'>
                <a href="#" onClick="expandIt('KB1'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB1'); return false">账号管理</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB1Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/channelUserMenu.do?action=channelInfo" target="mainFrame">账号详细</a><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/channelUserMenu.do?action=toUpdateChannelPwd" target="mainFrame">修改密码</a><br>
                      </td>  
                    </tr>
                  </table>
                </div>
                
				<!-- kb2 我的渠道 -->
			    <div id='KB2Parent' class='parent'>
                <a href="#" onClick="expandIt('KB2'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB2'); return false">我的渠道</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB2Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../mychannel/channelInfo.do?perType=query" target="mainFrame">我的渠道</a>
                      </td>
                    </tr>
                  </table>
                </div>
				
				<!-- kb3 我的应用 -->
                <div id='KB3Parent' class='parent'>
                <a href="#" onClick="expandIt('KB3'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB3'); return false">我的应用</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB3Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../mycontent/contentInfo.do?perType=query" target="mainFrame">我的应用</a>
                      </td>
                    </tr>
                  </table>
                </div>
				
                <!-- kb4 我的货架 -->
                <div id='KB4Parent' class='parent'>
                <a href="#" onClick="expandIt('KB4'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB4'); return false">我的货架</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB4Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <!--
                        <right:checkDisplay rightID="0_1105_ROLE_LIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/channelUserMenu.do?action=category_main" target="mainFrame">货架分类管理</a><br></right:checkDisplay>
                        -->
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/channelUserMenu.do?action=cgy_content_main" target="mainFrame">货架商品管理</a>
                      </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb5 TAC码库管理 -->
                <div id='KB5Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1101_RIGHT">
                <a href="#" onClick="expandIt('KB5'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB5'); return false">推送渠道管理</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                  </right:checkMenuDisplay>
                </div>
                <div id='KB5Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/tacCode.do?perType=query" target="mainFrame">推送渠道管理</a>
                      </td>
                    </tr>
                  </table>
                  
                </div>
                
                <!-- kb6 配置推送广告管理 -->
                <div id='KB6Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1101_PUSHADV">
                <a href="#" onClick="expandIt('KB6'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB6'); return false">配置推送管理</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                  </right:checkMenuDisplay>
                </div>
                <div id='KB6Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channelUser/pushAdv.do?perType=query" target="mainFrame">推送管理</a>
                      </td>
                    </tr>
                  </table>                 
                </div>
                
                <script>numTotal=6;scores[1]='KB1';scores[2]='KB2';scores[3]='KB3';scores[4]='KB4';scores[5]='KB5';scores[6]='KB6';</script>
              </td>
            </tr>
</table>
</body>
</html>
