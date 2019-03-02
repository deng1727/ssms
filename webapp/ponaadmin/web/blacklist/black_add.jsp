<%@ page contentType="text/html; charset=gb2312" %>
<%@page import="com.aspire.ponaadmin.web.blacklist.vo.BlackListVO"%>
<%@page import="com.aspire.ponaadmin.web.constant.Constants"%>
<%@page import="com.aspire.ponaadmin.web.util.PublicUtil"%>
<%
BlackListVO black =(BlackListVO)request.getAttribute("black");
  //System.out.println("roleVO...."+roleVO);
  String value=null;
  String name=null;
  String contentId=null;
  String endDate = null;
  String startDate = null;
  int blackType = 0;
  int type= 0;
  if (black==null)
   {
   value=Constants.FORWARD_ADD_TOKEN;
   name="";
   endDate="";
   startDate="";
   }
   else
   {
   value=Constants.FORWARD_UPDATE_TOKEN;
   name=black.getName();
   contentId = black.getContentId();
   blackType=black.getBlackType();
   endDate = black.getEndDate();
   startDate = black.getStartDate();
   type = black.getType();
   }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript">
function checkForm()
{
  //检查角色名称长度
  var contentId = addRoleForm.contentId.value;
  if(trim(contentId)=="" )
  {
    alert("内容ID不能为空");
    addRoleForm.contentId.focus();
    return false;
  }
  var now = new Date();

  if(addRoleForm.startDate.value==""){
	  alert("请选择开始日期");
	  return false;
  }
  if(addRoleForm.endDate.value==""){
	  alert("请选择截止日期");
	  return false;
  }  

  var st = addRoleForm.startDate.value;
  var ed = addRoleForm.endDate.value;
 
  if(!(st<ed)){
	  alert("截止日期必须大于开始日期！");
	  return false;
  }
  //var tmp = addRoleForm.startDate.value.split("-");
  //var d = new Date();  

  //d.setFullYear(parseInt(tmp[0]));
  //d.setMonth(parseInt(tmp[1])-1);
  //d.setDate(tmp[2]);
  //alert(d.toLocaleDateString());
  //if(d>now){
//	  alert("开始日期必须小于等于当前日期！");
	//  return false;
  //}  
  
  //tmp = addRoleForm.endDate.value.split("-");


  //d.setFullYear(parseInt(tmp[0]));
  //d.setMonth(parseInt(tmp[1])-1);
  //d.setDate(tmp[2]);
  //alert(d.toLocaleDateString());
  //if(d<now){
//	  alert("截止日期必须大于等于当前日期！");
//	  return false;
 // }
  
  if(!addRoleForm.type[0].checked&&!addRoleForm.type[1].checked){
	  alert("请选择黑名单类型！");
	  return false;
  }
  
    
  addRoleForm.startDate.disabled = false;
  addRoleForm.endDate.disabled = false;
  return true;
}

function chgType(){
	if(document.getElementById('blackType').value==4){
		document.getElementById('indate_line').style.display="inline";
	}else{
		document.getElementById('indate_line').style.display="none";
	}
}

function addContent(){
	var rt = window.showModalDialog("ctList.do","modal","center:yes;dialogWidth:600px;dialogHeight:700px;resizable: yes; help: no; status: yes; scroll: auto; ");
	if(rt!=''&&rt!=undefined){
		document.getElementById('contentId').value=rt;
	}
}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="0" >
<br>
<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
  <tr class="title1">
    <td align="center"><FONT class=large>刷榜黑名单管理----<%if(value.equals(Constants.FORWARD_ADD_TOKEN)){%>新增<%}else{%>修改<%}%></FONT></td>
  </tr>
</table>
<form name="addRoleForm" method="post" action="blackMain.do" onSubmit="return checkForm();">
      <input type="hidden" name="action" value="<%=PublicUtil.getPageStr(value)%>">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">

  <tr>
    <td width="22%" height="20" align="right" class="text3"><font color="#ff0000">(*)</font>内容ID：</td>
    <td width="78%" class="text3">
      <input name="contentId" id="contentId" size="60" type="text" value="<%=PublicUtil.getPageStr(contentId)%>" <%if(!value.equals(Constants.FORWARD_ADD_TOKEN)){%> readOnly<%} %>>
      
      <%if(value.equals(Constants.FORWARD_ADD_TOKEN)){%>
      (多个以“,”分隔)<input type="button" class="input1" value="获取内容ID" onclick="addContent();"></input>
      <%} %>
    </td>
  </tr>
  <!--  
  <tr>
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>刷榜性质：</td>
    <td class="text4">
        <select name="blackType" id="blackType" onchange="chgType();">
            <option value="1" <%if(blackType==1){ %>selected<%} %>>嫌疑刷榜</option>
            <option value="2" <%if(blackType==2){ %>selected<%} %>>首次刷榜</option>
            <option value="3" <%if(blackType==3){ %>selected<%} %>>多次刷榜</option>
            <option value="4" <%if(blackType==4){ %>selected<%} %>>其它</option>
        </select>
    </td>
  </tr>
  -->
  <tr id="indate_line" >
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>开始日期：</td>
    <td class="text4">
        <input name="startDate" type="text" size="12" value="<%=PublicUtil.getPageStr(startDate)%>" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(addRoleForm.startDate);" align="absmiddle" style="cursor:hand;">
        (此黑名单将于次天失效)
    </td>
  </tr>  
  <tr id="indate_line" >
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>截止日期：</td>
    <td class="text4">
        <input name="endDate" type="text" size="12" value="<%=PublicUtil.getPageStr(endDate)%>" disabled><img src="../../image/tree/calendar.gif"  onClick="getDate(addRoleForm.endDate);" align="absmiddle" style="cursor:hand;">
        (此黑名单将于当天不再干预)
    </td>
  </tr>
  <tr id="indate_line" >
    <td height="20" align="right" valign="top" class="text3"><font color="#ff0000">(*)</font>类型：</td>
    <td class="text4">
        <input name="type" type="radio" size="12" value="1" <%if(1==type){ %>checked<%} %>>期间内不上线，其他时间上线
        <input name="type" type="radio" size="12" value="2" <%if(2==type){ %>checked<%} %>>期间内上线，其他时间不上线
    </td>
  </tr>    
  <tr class="text3">
    <td align="center" colspan="2">
      <input type="submit" class="input1" name="submit01" value="确定">
      <input type="button" class="input1" name="btn_back" value="返回" onClick="javascript:self.location='black.do'">

    </td>
  </tr>
</table>
</form>
</body>
</html>
