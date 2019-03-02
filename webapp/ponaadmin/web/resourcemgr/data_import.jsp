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


<title>Ӫ�������Ż�����ƽ̨</title>
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
    if(confirm("ȷ��ִ�У��ò����������µ����������Ԫ���ݣ��Լ������ϼܻ������ֻ��ܣ�"))
		{ 
  window.location.href = "../baseMusicImport.jsp";
  }
}
function import_fullMusic(genLogo){
    genLogo.disabled=true;
    if(confirm("ȷ��ִ�У��ò�����������ȫ�������������Ԫ���ݣ��Լ������ϼܻ������ֻ��ܣ�"))
		{ 
  window.location.href = "dataImport.do?subSystem=ssms&type=fullMusicData";
;
  }
}
function import_comic(genLogo){
    
    if(confirm("ȷ��ִ�У��ò����������¶�������Ԫ����?"))
		{
		genLogo.disabled=true; 
  window.location.href = "../dmBaseImport.jsp";
  
  }else{
  	genLogo.disabled=false;
  }
}

function import_videoSync(genLogo)
{
	if(confirm("ȷ��ִ�У��ò����������ԭ����Ƶ����Ԫ����?"))
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
	if(confirm("ȷ��ִ�У��ò�����ȫ�����������Ƶ���ݣ�������ʱ���л�������?"))
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
	if(confirm("ȷ��ִ�иò�����"))
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
	if(confirm("ȷ��ִ�У��ò����������ԭ����Ƶ����Ԫ����?"))
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
	if(confirm("ȷ��ִ�У��ò�����ȫ�������»�����Ƶ���ݣ������м���л�������?"))
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
	if(confirm("ȷ��ִ�иò�����"))
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
        var varItem = new Option('����ͬ��', '1');      
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
    alert("Ŀǰ��û��ͬ������");
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
	if(confirm("ȷ��ִ�У��ò������ᵼ�����ͼ��Ԫ���ݣ��Լ������ϼܻ���ͼ����ܣ�")){
		window.location="data_import_book.jsp?synBookType="+synBookType;
	} 
}

function import_readSync(genLogo)
{
	if(confirm("ȷ��ִ�У��ò����������ԭ�в����Ķ�����Ԫ����?"))
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
	if(confirm("ȷ��ִ�У��ò�����ȫ�������Ķ�����Ԫ����?"))
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
	if(confirm("ȷ��ִ�У��ò�����ȫ��������ز���Ԫ����?"))
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
    if(confirm("ȷ��ִ�У��ò���������������ͬ���Զ����»���?"))
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
    <td align="center"><FONT class=large>PPMSӦ�����ݵ���</FONT></td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
<!--  removed by aiyan 2012-05-10
 <tr>
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">������Ӫ����ͬ��</td>
    <td width="30%" bgcolor="#E9F0F8" class="text1" align="left"> <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="syn_cate(this)"></td>
  </tr>
  
  --> 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS�е�ҵ�����ݵ��뵽���ݿ���</td>
    <td width="30%" bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_operation(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS�е��������ݵ��뵽���ݿ���
                                                                  ��������:<select id="synclassType" name="synclassType" onchange="synclassChange()">
                                                                    <option value="0">����ͬ��</option>
                                                                    <option value="1">ȫ��ͬ��</option>                                                                    
                                                                   </select>
                                                                 �����ϵ:<select id="synclassResourceType" name="synclassResourceType">
                                                                    <option value="0">��ͬ��</option>
                                                                    <option value="1">����ͬ��</option> 
                                                                    <option value="2" selected="selected">ȫ��ͬ��</option>                                                                     
                                                                 </select>
                                                                 �Ƿ��ǽ�������Ӧ��:<select id="isSyn" name="isSyn">
																		    	<option value="0" selected="selected">��</option>
																		    	<option value="1">��</option>
																		   </select>
    </td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_content(this)"></td>
  </tr>
  <!-- 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS�е�Zcom�������ݵ��뵽���ݿ���
                                                                  <select id="zcomType" name="zcomType">
                                                                    <option value="0">����ͬ��</option>
                                                                    <option value="1">ȫ��ͬ��</option>                                                                    
                                                                 </select></td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_zcomcontent(this)"></td>
  </tr>
  -->
   <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS�еĴ�ҵ�����������ݵ��뵽���ݿ���
                                                                  <select id="cyType" name="cyType">
                                                                    <option value="0">����ͬ��</option>
                                                                    <option value="1">ȫ��ͬ��</option>                                                                    
                                                                 </select></td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_CYcontent(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">PPMS�еĿ�����Ӫ���ݵ��뵽���ݿ���</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_openContent(this)"></td>
  </tr>
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">ͳ����Ƶ����Ŀ������Ŀ�½�Ŀ��</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_countNodeNum(this)"></td>
  </tr>
   <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">����������ͬ���Զ����»���</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_autoSyncCategory(this)"></td>
  </tr>
  <!-- 
  <tr >
    <td width="70%" bgcolor="#E9F0F8" class="text1" align="center">��ʱ�Ĵ��Ż�����ͬ������</td>
    <td bgcolor="#E9F0F8" class="text1" align="left"><input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_waken(this)"></td>
  </tr>
  -->
</table>
<br>
<!-- 
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>��������ͬ��</FONT></td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">���������еĲ��嵼�뵽���ݿ���</td>
    <td align="left" class="text1">
      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_CLRDATA(this)" >
    </td>
  </tr>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>A8���ݵ���<br></FONT></td>
  </tr>
</table>

<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr>
    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������ <select name="a8TaskName">
                                                                    <option value="music">�������ݵ���</option>
                                                                   <  <option value="singer">�������ݵ���</option>
                                                                    <option value="topList">�����ݵ���</option>                                                                   
                                                                 </select>
    </td>
    	<td align="left" class="text1">
      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_A8Data(this)">
    </td>
  </tr>
  </table>
	<br>
	-->
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>������ҵ����������ͬ��</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		  <tr >
	   <td align="center">WPӦ�����ݵ��룺</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="WPӦ�����ݵ���" onclick="import_wpApp(this)"></td>
	  </tr>
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������ <select name="taskName">
	     <logic:notEmpty name="collection">
	     <logic:iterate id="task" name="collection" type="com.aspire.ponaadmin.web.datasync.DataSyncTask" indexId="index">
	                                                                  <option value="<bean:write name="task" property="name" />" <logic:equal value="0" name="index">selected</logic:equal>><bean:write name="task" property="desc" /></option>
	                                                                  </logic:iterate>
	       </logic:notEmpty> <logic:empty name="collection"> <option value="">������</option> </logic:empty>                                                           
	                                                                   </select>
	    </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_dateSync(this)">
	    </td>
	  </tr>
	  <tr >
	   <td align="center">�����������ݵ��룺</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="�����������ݵ���" onclick="import_bMusic(this)"></td>
	  </tr>
	  
	  <tr >
	   <td align="center">�����������ݵ��룺</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="�����������ݵ���" onclick="import_comic(this)"></td>
	  </tr>
	  	  <tr >
	   <td align="center">�������ָ�������ȫ�����룺</td>
	    <td align="left" class="text1"><input type="button" class="input1" name="genLogo" value="�������ָ�������ȫ������" onclick="import_fullMusic(this)"></td>
	  </tr>
	  
		<tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">����ͼ������
			<select id="synBookType" name="synBookType">
		    <option value="author">����ͼ��������Ϣ����1</option>
		    <option value="type">����ͼ�������Ϣ����2</option>
		    <option value="book">����ͼ����Ϣ����3</option>
		    <option value="month">����ͼ�������Ϣ����4</option>
		    <option value="recommend">����ͼ���Ƽ�ͼ����Ϣ����5</option>
		    <option value="catalog">����ͼ��ר����Ϣ����6</option>
		    <option value="catalogcontent">����ͼ��ר��������Ϣ����7</option>
		    <option value="rank">����ͼ��������Ϣ����8</option>
		</select>
	    </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_bookSync(this)">
	    </td>
		</tr>	
		<tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">������Ƶ����    
		    <select id="synVideoType" name="synVideoType">
			    <option value="11">��Ƶ�ļ�������������</option>
			    <option value="4">��Ŀ�������ݵ���</option>
			    <option value="5">��Ŀ�ļ����ݵ���</option>
			    <option value="6">ֱ����Ŀ�����ݵ���</option>
			    <option value="8">��Ʒ���ݵ���</option>
			    <option value="9">��Ƶ��Ŀͳ��</option>
			    <option value="10">��Ƶ���ݼ��ڵ㵼��</option>
			    <option value="15">��Ƶ���ݼ�����</option>
			    <option value="12">��Ƶ�ļ����ݰ�Сʱ��������</option>
			    <option value="13">��Ŀ�����ļ����ݰ�Сʱ��������</option>
			    <option value="14">�ȵ������Ƽ����ݵ���</option>
			    <option value="222">��Ʒ���۹�ϵ��Ϣ����</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_videoSync(this)">
			<font color=red>&nbsp;&nbsp;<b>��ע���������м������ȫ�������ִ�У�������Ƶȫ���м����<b></font>
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">������Ƶȫ���м���л�
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_videoSyncRename(this)">
	    </td>
	  </tr>	 
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">ȫ�����������Ƶ���ݣ�������ʱ���л�������
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȫ������" onclick="import_videoSyncAll(this)">
	    </td>
	  </tr>	  
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">�»�����Ƶ����    
		    <select id="synNewVideoType" name="synNewVideoType">
			    <option value="1">��Ƶ��ͨ��Ŀ�ļ����ݵ���</option>
			    <option value="3">ҵ���Ʒ���ݵ���</option>
			    <option value="4">��Ʒ�������Ʒ����ݵ���</option>
			    <option value="5">�Ʒ��������ݵ���</option>
			    <option value="6">�ȵ������б����ݵ���</option>
			    <option value="2">��Ƶ��ͨ��Ŀ�ļ����ݰ�Сʱ��������</option>
			    <option value="7">��Ƶ��Ŀ����xml�ļ����ݵ���</option>
			    <option value="8">ֱ����Ŀ��xml�ļ����ݵ���</option>
			    <option value="9">�ȵ�����xml�ļ����ݵ���</option>
			    <option value="10">�񵥷������ݵ���</option>
			    <option value="11">��Ʒ�������Ʒ�api��������</option>
			    <option value="12">�ȵ������б�api��������</option>
			    <option value="13">ҵ���Ʒapi����</option>
			    <option value="14">�Ʒ���Ϣ����api����</option>
			    <option value="15">��ͨ��Ŀ����api����</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_newVideoSync(this)">
			<font color=red>&nbsp;&nbsp;<b>��ע����Ŀ��������������м������ȫ��xml�ļ����ݵ����ִ�У��»�����Ƶ�м����<b></font>
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">�»�����Ƶ��Ŀ��������м���л�
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_newVideoSyncRename(this)">
	    </td>
	  </tr>	 
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">ȫ�������»�����Ƶ���ݣ������м���л�������
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȫ������" onclick="import_newVideoSyncAll(this)">
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">�����Ķ�����    
		    <select id="synReadType" name="synReadType">
			    <option value="1">ͼ��������ݵ���</option>
			    <option value="2">������Ϣ���ݵ���</option>
			    <option value="3">ͼ����Ϣ���ݵ���</option>
			    <option value="4">�Ƽ�ͼ�����ݵ���</option>
			    <option value="5">ͼ��ͳ�����ݵ���</option>
			    <option value="6">ͼ��������ݵ���</option>
			    <option value="7">�����Ϣ���ݵ���</option>
			    <option value="16">����������ݵ���</option>
			    <option value="8">�ն�Ŀ¼���ݵ���</option>
			    <option value="9">ר����Ϣ����</option>
			    <option value="10">ר��������Ϣ����</option>
			    <option value="11">���а���Ϣ����</option>
			    <option value="12">����ϲ��--�Ķ��Ƽ�����</option>
			    <option value="13">����ϲ��--�����Ƽ�����</option>
			    <option value="14">����ϲ��--�Ķ������Ƽ�����</option>
			    <option value="15">����ϲ��--���������Ƽ�����</option>
	        </select>
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȷ��" onclick="import_readSync(this)">
	    </td>
	  </tr>
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">ȫ����������Ķ�����    
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȫ������" onclick="import_readSyncAll(this)">
	    </td>
	  </tr>	 	   
	  <tr>
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">ȫ��������ز�������    
	    </td>
	    <td align="left" class="text1">
			<input type="button" class="input1" name="bookLogo" value="ȫ������" onclick="import_colorComicSyncAll(this)">
	    </td>
	  </tr>
	  
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>�������ݵ���</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������ <select name="reportDataImport">   																
                                                                    <option value="appstatistic">Ӧ������,�Ƽ�,С���Ƽ����ݵ���</option>
                                                                    <option value="contentOfDay">Ӧ������Ӫ���ݵ���</option>
                                                                    <option value="contentOfWeek">Ӧ������Ӫ���ݵ���</option>
                                                                    <option value="contentOfMonth">Ӧ������Ӫ���ݵ���</option>
                                                                    <option value="a8statistic">A8����ͳ�Ƶ���</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_report(this)">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>��ҵ�����������ݵ���</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������ <select name="reportTopList">   																
                                                                    <option value="0">TOP������ȫ������</option>
                                                                    <option value="1">Ӧ������Ʒ����ָ��TOP60���ݵ���</option>
                                                                    <option value="2">��������Ʒ����ָ��TOP30���ݵ���</option>
                                                                    <option value="3">Ӧ������Ʒ��̽�÷�TOP50���ݵ���</option>
                                                                    <option value="4">��������Ʒ��̽�÷�TOP50���ݵ���</option>
                                                                     <option value="5">�г�PKӦ���ۼ��������ݵ���</option>
                                                                    <option value="cylist">��ҵ������Ʒ����ͳ�Ƶ���</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_reportTopList(this)">
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr class="title1">
	    <td align="center"><FONT class=large>139���ֽӿ�</FONT></td>
	  </tr>
	</table>
	<br>
	<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
	  <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������ <select id="music139Operation">   																
                                                                    <option value="billbor">139���ֽӿ�--�񵥻�����Ϣͬ��(��������)</option>												
                                                                    <option value="ablum">139���ֽӿ�--ר��������Ϣͬ��(��������)</option>												
                                                                    <option value="keyword">139���ֽӿ�--�����ؼ�����Ϣͬ��(��������)</option>										
                                                                    <option value="musicTag">139���ֽӿ�--������ǩ��Ϣͬ��(��������)</option>
                                                                    <option value="picSysnchro">139���ֽӿ�--ר���͸���ͼƬͬ��</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_music139(this)">
	    </td>
	  </tr>
	    <tr>
	   
	    <td width="50%" bgcolor="#E9F0F8" class="text1" align="center">��ѡ������(��139����) <select id="new139musictype">   																
                                                                    <option value="billbor">139���ֽӿ�--�񵥻�����Ϣͬ��(��������)</option>												
                                                                    <option value="ablum">139���ֽӿ�--ר��������Ϣͬ��(��������)</option>												
                                                                    <option value="keyword">139���ֽӿ�--�����ؼ�����Ϣͬ��(��������)</option>										
                                                                    <option value="musicTag">139���ֽӿ�--������ǩ��Ϣͬ��(��������)</option>
                                                                    <option value="picSysnchro">139���ֽӿ�--ר���͸���ͼƬͬ��</option>
                                                                 </select>
        </td>
	    <td align="left" class="text1">
	      <input type="button" class="input1" name="genLogo" value="ȷ��" onclick="import_new139music(this)">
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
