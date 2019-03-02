<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.risktag.vo.RiskVO"%>
<%@page
	import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
	String name = Validator.filter(request.getParameter("name") == null
			? ""
			: request.getParameter("name"));
	String desc = Validator.filter(request.getParameter("desc") == null
			? ""
			: request.getParameter("desc"));
	String rightID = Validator
			.filter(request.getParameter("rightID") == null
					? ""
					: request.getParameter("rightID"));
	String rightName = Validator.filter(request
			.getParameter("rightName") == null ? "" : request
			.getParameter("rightName"));
	String rightType = Validator.filter(request
			.getParameter("rightType") == null ? "0" : request
			.getParameter("rightType"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market货架管理系统</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
	<script language="Javascript" type="text/javascript"
	src="../../js/rsa/jquery-1.3.2.min.js"></script>
<script language="javascript">

<%--  $( function (){  
    var selectedValue1 = '<%=request.getAttribute("id")%>';  
       var selectedValue2 = '<%=request.getAttribute("stats")%>'; 
       changeSelected();
      var a =  document.getElementById("select1");
    function changeSelected(){  
    var opt = document.getElementsByName("id").options;
  	  alert("="+opt.length);
  	  
  	  
        jsSelectItemByValue(document.getElementsByName("id"),selectedValue1);
        
        jsSelectItemByValue(document.getElementsByName("stats"),selectedValue2);    
    }  
      
    function jsSelectItemByValue(objSelect,objItemText) {  
         for(var i=0;i<objSelect.options.length;i++) {  
            if(objSelect.options[i].value == objItemText) {  
                objSelect.options[i].selected = true;  
                break;  
            }   
        }   
    }  --%>

function doCancel()
{
var riskid= document.getElementById("riskid");
var cancelid= document.getElementById("cancelid");
  if(confirm("您确定要执行操作？"))
  {
	window.location.href="riskTagList.do?perType=cancel&riskid="+riskid+"stats="+cancelid;
  }
}

function tagExport()
	{
		if(confirm("您确定要导出展示操作吗？"))
		{	
			window.location.href="riskTagList.do?perType=output&type=0"
		    //refForm.action="tagExport.do";
			//refForm.submit();
			//refForm.action='tagMgr.do';
		}
		else
		{
			return false;	
		}
	}
function tagExport1()
	{
		if(confirm("您确定要导出屏蔽操作吗？"))
		{	
			window.location.href="riskTagList.do?perType=output&type=1"
		    //refForm.action="tagExport.do";
			//refForm.submit();
			//refForm.action='tagMgr.do';
		}
		else
		{
			return false;	
		}
	}

function chooseRight()
{
  var index = queryForm.rightType.selectedIndex;
  if(index==0)
  {
    alert("请先选择权限类型！");
    return false;
  }
  getRight(queryForm.rightID,queryForm.rightName,queryForm.rightType.options[index].value);
}

function checkForm()
{
  //检查角色名称长度
  var name = queryForm.name.value;
  //检查角色名称内容合法性
  if(!checkIllegalChar(name,"'\"<>\\/"))
  {
    alert("角色名称不能包含 ' \" < > \\ /  等字符。");
    queryForm.name.focus();
    return false;
  }
  //检查角色描述长度
  var desc = queryForm.desc.value;
  //检查角色描述内容合法性
  if(!checkIllegalChar(desc,"'\"<>\\/"))
  {
    alert("角色描述不能包含 ' \" < > \\ /  等字符。");
    queryForm.desc.focus();
    return false;
  }
  return true;
}

/* }); */
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1"></td>
		</tr>
	</table>
	<table width="95%" border="0" align="center" cellspacing="1">
		<form name="queryForm" method="post" action="riskTagList.do"
			onSubmit="return checkForm();">
			<tr>
				<td colspan="4" align="center" class="title1">风险标签查询 <input
					type="hidden" name="perType" value="query"></td>
			</tr>
			<tr>
				<td width="14%" align="right" class="text3">标签名称：</td>
				<td width="46%" class="text4"><select name="id" id ="select11">
							<option value="0" name="option">
								全部
							</option>
							<option value="1" name="option">
								有广告SDK,内嵌广告
							</option>
							<option value="2" name="option">
								无内容版权
							</option>
							<option value="3" name="option">
								无信息网络传播视听节目许可证
							</option>
							<option value="4" name="option">
								无气象局授权
							</option>
				</td>
				<td align="right" class="text3">状态：</td>
				<td class="text4"><select name="stats" id = "select22">
							<option value="-1" name="option2">
								全部
							</option>
							<option value="1" name="option2">
								屏蔽
							</option>
							<option value="0" name="option2">
								展示
							</option>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit2" type="submit" class="input1" value="查询"> <input
					name="Submit22" type="reset" class="input1" value="重置"></td>
			</tr>
		</form>
		<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit3" type="button" class="input1" value="导出展示应用" onClick="tagExport()">
				 <input name="Submit33" type="button" class="input1" value="导出屏蔽应用" onClick="tagExport1()"></td>
			</tr>
	</table>
	<br>
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1">风险标签管理</td>
		</tr>
	</table>
	<logic:empty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td height="20" align="center"><font color="#ff0000">没有找到。</font>
				</td>
			</tr>
		</table>
	</logic:empty>

	<logic:notEmpty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr class="title2">
				<td width="20%" height="20" align="center">ID</td>
				<td width="38%" align="center">风险描述</td>
				<td width="8%" align="center">状态</td>
				<td width="8%" align="center">最后操作时间</td>
				<td width="8%" align="center">查看</td>
				<td width="8%" align="center">修改状态</td>
			</tr>
			<logic:iterate id="vo" indexId="ind" name="PageResult"
				property="pageInfo"
				type="com.aspire.ponaadmin.web.risktag.vo.RiskVO">
				<tr class="<%=ind.intValue() % 2 == 0 ? "text4" : "text3"%>">
					<input type="hidden" name="riskid" id="riskid"
						value="<bean:write name="vo" property="id"/>">
					<td align="center" height="20"><a
						href="oneRole.do?roleID=<bean:write name="vo" property="id"/>&action=view"><bean:write
								name="vo" property="id" />
					</a>
					</td>
					<td align="center" style="word-break:break-all"><logic:equal
							name="vo" property="risktag" value="1">有广告SDK,内嵌广告</logic:equal>
						<logic:equal name="vo" property="risktag" value="2">无内容版权</logic:equal>
						<logic:equal name="vo" property="risktag" value="3">无信息网络传播视听节目许可证</logic:equal>
						<logic:equal name="vo" property="risktag" value="4">无气象局授权</logic:equal>
					</td>
					<td align="center" height="20"><logic:equal name="vo"
							property="stats" value="0">展示</logic:equal> <logic:equal
							name="vo" property="stats" value="1">屏蔽</logic:equal>
					</td>
					<td align="center" height="20"><bean:write name="vo"
							property="handleTime" />
					</td>
					<td align="center"><input type="button" class="input1"
						value="查看" name="btn_edit"
						onClick="window.location.href='riskTagList.do?perType=queryDetail&riskid=<bean:write name="vo" property="risktag"/>';">
					</td>
					<td align="center"><input type="button" class="input1" id ="cancelid"
						<logic:equal name="vo" property="stats" value="1"> value="取消屏蔽" </logic:equal>
						<logic:equal name="vo" property="stats" value="0"> value="屏蔽" </logic:equal>
						name="btn_cancel"
						
						onClick="window.location.href='riskTagList.do?perType=cancel&riskid=<bean:write name="vo" property="risktag"/>&stats=<logic:equal name="vo" property="stats" value="1">0</logic:equal><logic:equal name="vo" property="stats" value="0">1</logic:equal>';">


					</td>
				</tr>
			</logic:iterate>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<tr class="text1">
				<td align="right">
					<%
						HashMap params = new HashMap();
							params.put("name", name);
							params.put("desc", desc);
							params.put("rightID", rightID);
							params.put("rightName", rightName);
							params.put("rightType", rightType);
					%> <pager:pager name="PageResult"
						action="/web/rightmanager/roleList.do" params="<%=params%>">
						<pager:firstPage label="首页" />&nbsp;
            <pager:previousPage label="前页" />&nbsp;
            <pager:nextPage label="后页" />&nbsp;
            <pager:lastPage label="尾页" />&nbsp;
            第<pager:pageNumber />页/共<pager:pageCount />页
            <pager:location id="1" />
					</pager:pager></td>
			</tr>
		</table>
	</logic:notEmpty>
</body>
</html>
