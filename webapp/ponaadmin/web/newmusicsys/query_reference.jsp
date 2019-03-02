<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String musicId = Validator.filter(request.getParameter("musicId")==null?"":request.getParameter("musicId"));
String musicName = Validator.filter(request.getParameter("musicName")==null?"":request.getParameter("musicName"));
String singer = Validator.filter(request.getParameter("singer")==null?"":request.getParameter("singer"));
String showCreateTime = Validator.filter(request.getParameter("showCreateTime")==null?"":request.getParameter("showCreateTime"));
Map<String,Object> map = (Map)request.getAttribute("categoryContent");
String approvalStatus = (String)request.getAttribute("approvalStatus");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>人工干预管理</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
function removeItem()
{
	refForm.perType.value="remove";
	var isSele = false;
	
	if(refForm.dealRef.length == undefined)
	{
		if(refForm.dealRef.checked == true)
		{
			isSele = true;
		}
	}
	else
	{
		for(var i=0; i<refForm.dealRef.length; i++ )
		{
			if(refForm.dealRef[i].checked == true)
			{
				isSele = true;
				break;
			}
		}
	}
	if(isSele == false)
	{
		alert("请选择要移除目标！");
		return;
	}
	refForm.submit();
}

function add()
{
	refForm.perType.value="add";
	var returnv=window.showModalDialog("queryReference.do?perType=queryMusic&isFirst=1&categoryId=<%=categoryId%>", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	if(returnv != undefined && returnv != '')
	{
		refForm.addMusicId.value=returnv;
		refForm.submit();
	}  
}

function mod_click(taxis)
{
  if(taxis.value=="")
  { 
   alert("请输入排序序号");
   return false;
  }
  if(!JudgeReal2(taxis,"排序序号",9))
  {
	return false;
  }
	
  if((taxis.value < -9999)||(taxis.value >9999))
  {
     alert("排序序号只能在-9999至9999之间！");
     return false; 
  }
  return true;

}

function SetSortID()
{
	refForm.perType.value="setSort";
	var temp = '';
	var i=0;
	var Taxis=document.getElementsByName("sortId");

	   //debugger；
	    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO">
	      sortid=<bean:write name="vo" property="sortId"/>;
	      if(!mod_click(Taxis[i]))
	      {
	        Taxis[i].select();
	        Taxis[i].focus();
	        return false;
	      }
	      if(sortid!=Taxis[i].value)
	      {
	        if(refForm.sortId.length == undefined)
	        {
	        	temp = refForm.sortId.id + "_" + refForm.sortId.value + ";";
	        }else{
	        	temp = temp + refForm.sortId[i].id + "_" + refForm.sortId[i].value + ";";
	        }	
	      }

	      i++;
	     </logic:iterate>
	if(temp=="") {
	   alert("请修改至少一个排序号");
	}else{   	 
	   refForm.setSortId.value = temp;
	   refForm.submit();
	}
}
function exportData()
{
	if(confirm("您确定要进行导出操作吗？"))
	{
		refForm.perType.value="exportData";
		refForm.submit();
		refForm.perType.value='query';
	}
	else
	{
		return false;	
	}
}

function importData(filename,type)
{
	form1.perType.value="importData";
	
	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("请选择要导入数据的文件！");
		return false;
    }

    if(!isFileType(filePath,"xls")&&!isFileType(filePath,"xlsx"))
    {
      alert("请选择xls或xlsx格式的数据文件!");
      return false;
    }
	form1.submit();
}

function isFileType(filename,type)
{
	var pos = filename.lastIndexOf(".");
	
	if(pos<0) return false;
	
	var fileType = filename.substr(pos+1);
	
	if(fileType.toUpperCase()!=type.toUpperCase()) return false;
	
	return true;
}
function submitApproval(){
	 checkItem();
	  var bool = true;
		var dealContents = document.getElementsByName("dealRef");
		for(var i = 0;i < dealContents.length;i++){
			if(dealContents[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("请选择需要审批的内容");
			return;
		}
	  refForm.approval.value="part";
	  refForm.action="<%=request.getContextPath()%>/web/newmusicsys/categoryApproval.do?method=approval";
	  refForm.submit();
 }
 
 function submitAllApproval(){
	  refForm.approval.value="all";
	  refForm.action="<%=request.getContextPath()%>/web/newmusicsys/categoryApproval.do?method=approval";
	  refForm.submit();
 }	  
 function selectAll(aForm,checkFieldName,isCheck)
 {
   for(var i=0;i<aForm.length;i++)
   {
     if(aForm.elements[i].type=="checkbox")
     {
       if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
       {
       	if(!aForm.elements[i].disabled)
              aForm.elements[i].checked = isCheck;
       }	
     }
   }
 }
 

	function checkItem() {
		for (var i = 0; i < refForm.length; i++) {
			if (refForm.elements[i].type == "checkbox") {
				var itemValue = refForm.elements[i].value;
				var strs = itemValue.split("#");
				if(!(strs.length == 2 && (strs[1] == '3' || strs[1] == '0'))){
					refForm.elements[i].checked = false;
				}
			}
		}
	}
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="queryReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      商品查询
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">音乐编号：
    </td>
    <td width="30%" class="text4"><input type="text" name="musicId" value="<%=musicId%>"></td>
    <td width="18%" align="right" class="text3">音乐名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="musicName" value="<%=musicName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">歌手：
    </td>
    <td width="30%" class="text4"><input type="text" name="singer" value="<%=singer%>"></td>
    <td width="18%" align="right" class="text3">入库时间：
    </td>
    <td width="30%" class="text4">
    	    	<input name="showCreateTime" class="Wdate" type="text"
					id="textbox2" style="width:170px"
					onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
					value="<%=showCreateTime%>"
					readonly="true" />
	</td>
 </tr>
 
 <td width="18%" align="right" class="text3">审批状态：
    </td>
    <td width="30%" class="text4" colspan="3">
     <SELECT name=approvalStatus> 
                           <option value="-1" <logic:equal value="-1" name="approvalStatus"> selected</logic:equal>>
								全部
							</option>
                           <option value="2" <logic:equal value="2" name="approvalStatus"> selected</logic:equal>>
								待审批
							</option>
							<option value="0" <logic:equal value="0" name="approvalStatus"> selected</logic:equal>>
								编辑
							</option>
							<option value="1" <logic:equal value="1" name="approvalStatus"> selected</logic:equal>>
								已发布
							</option>
							<option value="3" <logic:equal value="3" name="approvalStatus"> selected</logic:equal>>
								不通过
							</option>
							<option value="0,3" <logic:equal value="0,3" name="approvalStatus"> selected</logic:equal>>
								编辑与审批不通过
							</option>
    </select>
    </td>
  </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="查询" >
	    <input name="reset" type="reset" class="input1" value="重置">
    </td>
  </tr>
</table>
</form>
<br>
<form name="refForm" action="queryReference.do" method="post" >
<input type="hidden" name="categoryId" value="<%=categoryId%>">
<input type="hidden" name="perType" value="">
<input type="hidden" name="addMusicId" value="">
<input type="hidden" name="setSortId" value="">
<input type="hidden" name="approval" >
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>商品列表</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAll(refForm,'dealRef',this.checked);"/>
	  	全选
	</td>
    <td width="10%" align="center" class="title1">音乐id</td>     
    <td width="18%" align="center" class="title1">音乐名称</td>
    <td width="10%" align="center" class="title1">歌手</td>
    <td width="12%" align="center" class="title1">入库时间</td>
    <td width="5%" align="center" class="title1">在线试听</td>
    <td width="5%" align="center" class="title1">彩铃下载</td>
    <td width="5%" align="center" class="title1">振铃下载</td>
    <td width="7%" align="center" class="title1">全曲下载</td>
    <td width="10%" align="center" class="title1">有效期</td>  
    <td width="5%" align="center" class="title1">排序序号</td>
     <td width="10%" align="center" class="title1">审批状态</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.newmusicsys.vo.NewMusicRefVO">
	    <%
		if("text5".equals(tmpStyle))
	  	{
			tmpStyle = "text4";
	  	}
	  	else
	  	{
	  		tmpStyle = "text5";
	  	}
		%>
		<tr class=<%=tmpStyle%>>
			<td align="center" style="word-break:break-all;">
				<input type="checkbox" name="dealRef" <% if("2".equals(vo.getVerify_status())){ %> disabled="disabled" <%} %>  value="<bean:write name="vo" property="musicId"/>#<bean:write name="vo" property="verify_status"/>">
			</td>
			<td align="center"><a href="queryReference.do?perType=showMusic&musicId=<bean:write name="vo" property="musicId"/>">
      			<bean:write name="vo" property="musicId"/></a>
      		</td>
      		<td align="center"><a href="queryReference.do?perType=showMusic&musicId=<bean:write name="vo" property="musicId"/>">
      			<bean:write name="vo" property="musicName"/></a>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="singer"/>
			</td>
      		<td align="center">
				<bean:write name="vo" property="showCreateTime_Y"/>年<bean:write name="vo" property="showCreateTime_M"/>月<bean:write name="vo" property="showCreateTime_D"/>日
			</td>
      		<td align="center">
      			<bean:write name="vo" property="onlineType"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="coloType"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="ringType"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="songType"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="validity"/>
      		</td>
      		<td align="center">
      			<input id="<bean:write name="vo" property="musicId"/>" type="text" name="sortId" size="9" value="<bean:write name="vo" property="sortId"/>">
      		</td>
      		<td align="center">
      			 <%if("0".equals(vo.getVerify_status())){ %>
	             编辑
	   <%}else if("1".equals(vo.getVerify_status())){ %>
	              发布成功
	   <%}else if("2".equals(vo.getVerify_status())){ %>
	              待审批
	   <%}else{ %>
	       审批不通过  
	   <%} %>
      		</td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("musicId",musicId);
        params.put("musicName",musicName);
        params.put("singer",singer);
        params.put("showCreateTime",showCreateTime);
        params.put("perType",request.getParameter("perType"));
        params.put("approvalStatus",approvalStatus);
        %>
        <pager:pager name="PageResult" action="/web/newmusicsys/queryReference.do" params="<%=params%>">
            <pager:firstPage label="首页"/>&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber/>页/共<pager:pageCount/>页
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
    	<logic:notEmpty name="PageResult" property="pageInfo">
    		<input name="buttonDel" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %> onClick="removeItem();" value="移除">
    		<input name="buttonSet" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %> onClick="SetSortID();" value="排序序号设定">	
		</logic:notEmpty>
		<input name="buttonAdd" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %>  onClick="add();" value="添加">
		<input name="buttonImport" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %>  onClick="importData();" value="导入内容">
		<logic:notEmpty name="PageResult" property="pageInfo">
    		<input name="buttonExport" type="button" class="input1"  onClick="exportData();" value="导出内容">
		</logic:notEmpty>
		<logic:notEmpty name="PageResult" property="pageInfo">
		     <input name="buttonOperation" type="button" class="input1" <% if(!("0".equals(map.get("goods_status")) || "3".equals(map.get("goods_status")))){ %> disabled="disabled"<%} %>  onClick="submitApproval();" value="批量提交审批">
		     <input name="buttonOperation" type="button" class="input1" <% if(!("0".equals(map.get("goods_status")) || "3".equals(map.get("goods_status")))){ %> disabled="disabled"<%} %>  onClick="submitAllApproval();" value="全部提交审批">
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
<form action="importReference.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<input type="hidden" name="categoryId" value="<%=categoryId%>">
	
		<table width="95%"  border="0" align="center" cellspacing="1">
		   <tr>
	    <td   align="left" colspan="2">
	        	<b>增加类型</b>
        <select name="addType">
            <option value="ALL">全量</option>
	    	<option value="ADD">增量</option>
	    	
	    	
	    </select>
	    <font color=red>&nbsp;&nbsp;<b><br/>增量：在该货架下增加商品，序号以EXCEL（2003版本或者2007版本）数据为准<br/>
全量：下架原有该货架下全部商品，添加EXCEL(2003版本或者2007版本)指定商品到该货架下 <b></font>
    </td>
	   </tr>
	  <tr>
	    <td   align="right" width="20%">
	    	选择要导入的excel数据文件：
	    </td>
	    <td   align="left" width="80%"> 
			<input type="file" name="dataFile">(表格第一列表示序号，升序排列；第二列表示内容ID)
	    </td>
	   </tr>
	</table>
	
</form>
</body>
</html>
