<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>

<script language="JavaScript1.2">
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
                
                <!-- kb1 �û����� -->
                <div id='KB1Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1101_USER">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB1'); return false">�û�����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB1Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1101_USER_LIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../usermanager/queryUser.do" target="mainFrame">�û��б�</a><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1103_USER_CHECK"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../usermanager/queryUncheckUser.do" target="mainFrame">�û����</a><br></right:checkDisplay>
                      </td>  
                    </tr>
                  </table>
                </div>

                <!-- kb2 Ȩ�޹��� -->
                <div id='KB2Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1101_RIGHT">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB2'); return false">Ȩ�޹���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB2Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1105_ROLE_LIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../rightmanager/roleList.do" target="mainFrame">��ɫ����</a><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1101_ROLE_ADD"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../rightmanager/add_role.jsp" target="mainFrame">������ɫ</a></right:checkDisplay>
                      </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb3 ���ܹ������� -->
                <div id='KB3Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_0801_RESOURCE">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB3'); return false">���ܹ�������</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB3Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                      <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../ztree/category_main.jsp?approvalFlag=yes" target=mainFrame class="link1">���ܷ������_NEW</A><br>
                      <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/categoryApprovalList.do?operation=1" target=mainFrame class="link1">�������-��������</A><br>
                      <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../ztree/cgy_content_main.jsp?menuStatus=1" target=mainFrame class="link1">������Ʒ����_NEW</A><br>
                      <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/categoryApprovalList.do?operation=2" target=mainFrame class="link1">��Ʒ����-��������</A><br>
                      <!-- 
                        <right:checkDisplay rightID="1_0801_RESOURCE_CGY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/categoryTree.do" target=mainFrame class="link1">���ܷ������</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="1_0802_RESOURCE_CGYCNT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/cgyContentTree.do" target=mainFrame class="link1">������Ʒ����</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="1_0802_RESOURCE_CNT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/contentTree.do" target=mainFrame class="link1">�������ݹ���</A><br></right:checkDisplay>
                        
                         -->
                        
                        <right:checkDisplay rightID="0_0801_RES_SERVCONTENSYNC"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/data_import.jsp" target=mainFrame class="link1">ҵ���������ݵ���</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_0801_RES_DATA_EXPORT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../resourcemgr/data_export.jsp" target=mainFrame class="link1">�������ݵ���</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_0801_RES_DATA_EXPORT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../catemonitor/monitorContentTree.do" target=mainFrame class="link1">�񵥷ֻ��ͼ��</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="1_0807_RES_BLACKLIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../blacklist/black.do" target=mainFrame class="link1">���ݺ���������</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="1_0807_RES_BLACKLIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../blacklist/approval.do?action=showList&aprovalStatus=2" target=mainFrame class="link1">���ݺ���������-��������</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0808_RES_KEYBASELIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../datafield/keyBaseList.do" target=mainFrame class="link1">��չ���Թ���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0809_RES_CONTENTLIST"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../datafield/contentList.do" target=mainFrame class="link1">Ӧ����չ���Թ���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0810_RES_SYS_DATA"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../exigence/queryExigence.do?perType=query" target=mainFrame class="link1">�����������ݹ���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0811_PIVOT_DEVICE"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../pivot/device.do?perType=query" target=mainFrame class="link1">�ص���͹���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0812_PIVOT_CONTENT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../pivot/content.do?perType=query" target=mainFrame class="link1">�ص����ݹ���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0813_RES_CONTENTEXT"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../datafield/contentExtList.do" target=mainFrame class="link1">Ӧ�û���Թ���</A><br></right:checkDisplay>
						<right:checkDisplay rightID="1_0813_RES_LOCK_LOCATION"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../lockLocation/category_main.jsp" target=mainFrame class="link1">��Ʒ��������</A><br></right:checkDisplay>
						
						
						<right:checkDisplay rightID="0_0801_RES_SERVCONTENSYNC"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../exportData/queryExport.do?perType=query&amp;exportByUser=1" target=mainFrame class="link1">��������ϵͳ����</A><br></right:checkDisplay>
						<right:checkDisplay rightID="0_0801_RES_SERVCONTENSYNC"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../exportData/queryExport.do?perType=query&amp;exportByUser=2" target=mainFrame class="link1">����������ά����</A><br></right:checkDisplay>
                       </td>
                    </tr>
                  </table>
                </div> 
                
                <!-- kb5 �����Զ����� -->
                <div id='KB5Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1301_UPDATE">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB5'); return false">�����Զ�����</a> </td>
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
                        <right:checkDisplay rightID="0_1301_CATEGORY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../autoupdate/categoryRuleList.do" target=mainFrame class="link1">������������</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1301_RULE"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../autoupdate/ruleList.do" target=mainFrame class="link1">��������������</A><br></right:checkDisplay>
                       </td>
                    </tr>
                  </table>
                </div>                    
                
                <!-- kb6 �˹���Ԥ���� -->
                <div id='KB6Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1401_INTERVENOR">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB6'); return false">�˹���Ԥ����</a> </td>
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
                        <right:checkDisplay rightID="0_1401_INTERVENOR"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../intervenor/intervenorList.do" target=mainFrame class="link1">��������</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1401_CATEGORY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../intervenor/categoryList.do?actionType=listCategory" target=mainFrame class="link1">�񵥸�Ԥ����</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1401_CATEGORY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../androidBlackList/androidBlackList.do?perType=query" target=mainFrame class="link1">�񵥺���������</A><br></right:checkDisplay>
                       	<right:checkDisplay rightID="0_1401_CATEGORY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../risktag/riskTagList.do?perType=query" target=mainFrame class="link1">���ձ�ǩ��Ԥ����</A><br></right:checkDisplay>
                       </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb7 ������Ϸ�Ƽ����� -->
                <div id='KB7Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1501_GAME">
                <a href="#" onClick="expandIt('KB7'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB7'); return false">������Ϸ����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB7Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1501_DATA"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseGameQuery.do" target=mainFrame class="link1">��Ϸ�Ƽ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1501_TEMP"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseGameTemp.do" target=mainFrame class="link1">�Ƽ������ļ�</A><br></right:checkDisplay>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../basegame/black.do?method=query" target="mainFrame">��Ϸ����������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb8 ���������Ƽ����� -->
                <div id='KB8Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1502_MUSIC">
                <a href="#" onClick="expandIt('KB8'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB8'); return false">�������ֹ���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB8Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1502_DATA"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseMusicQuery.do" target=mainFrame class="link1">�����Ƽ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1502_TEMP"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseMusicTemp.do" target=mainFrame class="link1">�Ƽ������ļ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1502_NEW_CATEGORY">
                        	<img src="../../image/icon/icon013.gif" align="absmiddle"> 
                      		<a href="../newmusicsys/category_main.jsp" target="mainFrame">���ֻ��ܹ���</a><br>
                      	</right:checkDisplay> 
                      	<img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newmusicsys/categoryApprovalList.do?approvalStatus=2&operation=1" target="mainFrame">���ֻ��ܹ���-��������</A><br>
                      	<right:checkDisplay rightID="0_1502_NEW_REFERENCE">
                      		<img src="../../image/icon/icon013.gif" align="absmiddle"> 
							<a href="../newmusicsys/reference_main.jsp" target="mainFrame">������Ʒ����</a><br>
                      	</right:checkDisplay> 
                      	 <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newmusicsys/categoryApprovalList.do?approvalStatus=2&operation=2" target="mainFrame">������Ʒ����-��������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb9 ����ͼ����� -->
                <div id='KB9Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1503_BOOK">
                <a href="#" onClick="expandIt('KB9'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB9'); return false">����ͼ�����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB9Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1503_DATA"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseBookQuery.do" target=mainFrame class="link1">ͼ���Ƽ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1503_TEMP"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseBookTemp.do" target=mainFrame class="link1">�Ƽ������ļ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1503_CATEGORY"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../book/category_main.jsp" target="mainFrame">ͼ����ܹ���</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1503_REFERENCE"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../book/reference_main.jsp" target="mainFrame">ͼ����Ʒ����</A><br></right:checkDisplay>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../book/new_category_main.jsp" target="mainFrame">��ͼ����ܹ���</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseRead/categoryApprovalList.do?approvalStatus=2&operation=1" target="mainFrame">��ͼ����ܹ���-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../book/new_reference_main.jsp" target="mainFrame">��ͼ����Ʒ����</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseRead/categoryApprovalList.do?approvalStatus=2&operation=2" target="mainFrame">��ͼ����Ʒ����-��������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb10 ������Ƶ���� -->
                <div id='KB10Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1504_VIDEO">
                <a href="#" onClick="expandIt('KB10'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB10'); return false">������Ƶ����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB10Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <right:checkDisplay rightID="0_1504_DATA"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseVideoQuery.do" target=mainFrame class="link1">��Ƶ�Ƽ�</A><br></right:checkDisplay>
                        <right:checkDisplay rightID="0_1504_TEMP"><img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baserecomm/baseVideoTemp.do" target=mainFrame class="link1">�Ƽ������ļ�</A><br></right:checkDisplay>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../video/category_main.jsp" target="mainFrame">��Ƶ���ܹ���</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/categoryApprovalList.do?approvalStatus=2&operation=1" target="mainFrame">��Ƶ���ܹ���-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../video/reference_main.jsp" target="mainFrame">��Ƶ��Ʒ����</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/categoryApprovalList.do?approvalStatus=2&operation=2" target="mainFrame">��Ƶ��Ʒ����-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/product.do?perType=listProduct" target="mainFrame">��Ƶ��Ʒ����</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/videoNode.do?perType=query" target="mainFrame">��Ƶ��Ŀ˵������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/black.do?method=query" target="mainFrame">��Ƶ����������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../baseVideo/collection.do?method=query" target="mainFrame">��Ƶ���ݼ�����</A><br>
                       
                       </td>
                    </tr>
                  </table>
                </div>
                 <!-- kb14 �»�����Ƶ���� -->
                <div id='KB14Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1504_VIDEO">
                <a href="#" onClick="expandIt('KB14'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB14'); return false">�»�����Ƶ����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB14Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newvideo/category_main.jsp" target="mainFrame">��Ƶ���ܹ���</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newvideo/categoryApprovalList.do?approvalStatus=2&operation=1" target="mainFrame">POMS���ܹ���-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newvideo/reference_main.jsp" target="mainFrame">��Ƶ��Ʒ����</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../newvideo/categoryApprovalList.do?approvalStatus=2&operation=2" target="mainFrame">��Ƶ��Ʒ����-��������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                     <!-- kb13 �������ع��� -->
                <div id='KB13Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1505_COMIC">
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB13'); return false">���ض�������</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                  </right:checkMenuDisplay>
                </div>
                <div id='KB13Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../comic/category_main.jsp" target="mainFrame">�������ܹ���</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../comic/categoryApprovalList.do?approvalStatus=2&operation=1" target="mainFrame">�������ܹ���-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../comic/reference_main.jsp" target="mainFrame">������Ʒ����</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../comic/categoryApprovalList.do?approvalStatus=2&operation=2" target="mainFrame">������Ʒ����-��������</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../comic/black.do?method=query" target="mainFrame">��������������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
  
                
                 <!-- kb11 ר����Ϣ���� -->
                <div id='KB11Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_0601_DISSERTATION">
                <a href="#" onClick="expandIt('KB11'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align=""> <a href="#" onClick="expandIt('KB11'); return false">ר����Ϣ����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB11Child' class='child'><table width="95%"  border="0" align="center">
                  <tr>
                    <td width="13%" align="center">&nbsp;</td>
                    <td width="87%">
                      <right:checkDisplay rightID="0_0601_DISS_ADMIN"><img src="../../image/icon/icon013.gif" align="absmiddle"> 
                      <a href="../dissertation/queryDiss.do?param=all" target="mainFrame">ר����Ϣ����</a><br></right:checkDisplay> 
					</td>  
                  </tr>
                </table> </div>
                
                <!-- kb12 �����ֹ��� -->
                <div id='KB12Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_0701_NEWMUSIC">
                <a href="#" onClick="expandIt('KB12'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align=""> <a href="#" onClick="expandIt('KB12'); return false">�����ֹ���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB12Child' class='child'><table width="95%"  border="0" align="center">
                  <tr>
                    <td width="13%" align="center">&nbsp;</td>
                    <td width="87%">
                      
					</td>  
                  </tr>
                </table> </div> 
                
                <!-- kb4 ϵͳ���� -->
                <div id='KB4Parent' class='parent'>
                <right:checkMenuDisplay rightID="2_1201_SYSTEM">
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
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align=""> <a href="#" onClick="expandIt('KB4'); return false">ϵͳ����</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </right:checkMenuDisplay>
                </div>
                <div id='KB4Child' class='child'><table width="95%"  border="0" align="center">
                  <tr>
                    <td width="13%" align="center">&nbsp;</td>
                    <td width="87%">
                      <right:checkDisplay rightID="0_1201_ACTIONLOG"><img src="../../image/icon/icon013.gif" align="absmiddle"> <a href="../actionlog/queryLog.do" target="mainFrame">��־�鿴</a><br></right:checkDisplay> 
                      <img src="../../image/icon/icon013.gif" align="absmiddle"> <a href="../audit/checklog.do?method=query" target="mainFrame">���˲鿴</a><br/>
					</td>  
                  </tr>
                </table> </div>
                

                <!-- kb15 wp��۹��� -->
                <div id='KB15Parent' class='parent'>
                <a href="#" onClick="expandIt('KB15'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB15'); return false">WP��۹���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB15Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../wpinfo/category_main.jsp" target="mainFrame">wp��ۻ��ܹ���</A><br>
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../wpinfo/reference_main.jsp" target="mainFrame">wp�����Ʒ����</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                <!-- kb16 �˺Ź��� -->
                <div id='KB16Parent' class='parent'>
                <a href="#" onClick="expandIt('KB16'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB16'); return false">�˺Ź���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB16Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channeladmin/queryOpenChannelsList.do?method=queryOpenChannelsList" target="mainFrame">�������б�</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                
                <!-- kb17 ������������� -->
                <div id='KB17Parent' class='parent'>
                <a href="#" onClick="expandIt('KB17'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB17'); return false">�������������</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB17Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../channeladmin/openChannelMoList.do?method=queryList" target="mainFrame">����������</A><br>
                       </td>
                    </tr>
                     <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../singlecategory/singleCgyContentTreeAction.do?userId=cdfh_category_content" target="mainFrame">������Ʒ����</A><br>
                       </td>
                    </tr>
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../toaudit/toAuditListAction.do?method=queryList" target="mainFrame">�����</A><br>
                       </td>
                    </tr>
                  </table>
                </div>

                   <!--<div id='KB4Child' class='child'><table width="95%"  border="0" align="center">
                  <tr>
                    <td width="13%" align="center">&nbsp;</td>
                    <td width="87%">
                      <img src="../../image/icon/icon013.gif" align="absmiddle"> <a href="../test/model.do?flag=query" target="mainFrame">ģ�����</a><br/>
					</td>  
					<td width="87%">
                      <img src="../../image/icon/icon013.gif" align="absmiddle"> <a href="../test/model2.do" target="mainFrame">�����ѯ</a><br/>
					</td> 
					<td width="87%">
                      <img src="../../image/icon/icon013.gif" align="absmiddle"> <a href="../test/model3.do" target="mainFrame">ʹ�ò�ѯ</a><br/>
					</td> 
                  </tr>
                </table> </div>
                
                 <!-- kb18 �������� -->
                <div id='KB18Parent' class='parent'>
                <a href="#" onClick="expandIt('KB18'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB18'); return false">��������</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB18Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../cooperation/cooperationList.do?method=query" target="mainFrame">�������б�</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                 <!-- kb19 �������� -->
                <div id='KB19Parent' class='parent'>
                <a href="#" onClick="expandIt('KB19'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB19'); return false">��������</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB19Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../cooperation/channelList.do?method=query" target="mainFrame">�����б�</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
                
                 <!-- kb20 �����Ź��� -->
                <div id='KB20Parent' class='parent'>
                <a href="#" onClick="expandIt('KB20'); return false">
                  </a>
                  <table width="100%" height="5"  border="0">
                    <tr>
                      <td></td>
                    </tr>
                  </table>
                  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0" background="../../image/but_pic03.gif">
                    <tr>
                      <td width="8%"><img src="../../image/but_pic01.gif" width="10" height="24"></td>
                      <td width="84%"><img src="../../image/tree/arrow.gif" width="15" height="15" border="0" align="absmiddle"> <a href="#" class="link2" onClick="expandIt('KB20'); return false">�����Ź���</a> </td>
                      <td width="8%" align="right"><img src="../../image/but_pic02.gif" width="10" height="24"></td>
                    </tr>
                  </table>
                </div>
                <div id='KB20Child' class='child'>
                  <table width="95%"  border="0" align="center">
                    <tr>
                      <td width="13%" align="center">&nbsp;</td>
                      <td width="87%">
                        <img src="../../image/icon/icon013.gif" align="absmiddle">&nbsp;<a href="../cooperation/channelsNoInfoList.do?method=query" target="mainFrame">������</A><br>
                       </td>
                    </tr>
                  </table>
                </div>
       
                
                <script>numTotal=20;scores[1]='KB1';scores[2]='KB2';scores[3]='KB3';scores[4]='KB4';scores[5]='KB5';scores[6]='KB6';scores[7]='KB7';scores[8]='KB8';scores[9]='KB9';scores[10]='KB10';scores[11]='KB11';scores[12]='KB12';scores[13]='KB13';scores[14]='KB14';scores[15]='KB15';scores[16]='KB16';scores[17]='KB17';scores[18]='KB18';scores[19]='KB19';scores[20]='KB20'</script>
              </td>
            </tr>
</table>
</body>
</html>
