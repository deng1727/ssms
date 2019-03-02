<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>榜单黑名单元数据查询</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function remove()
{
	refForm.perType.value="removeApp";
	var isSele = false;
	
	refForm.submit();
}
function cancelAll(){
if(confirm("您确定下线所有已屏蔽吗？")) {
refForm.action = "riskTagList.do?perType=cancel";
refForm.submit();
refForm.action = "riskTagList.do?perType=queryDetail";
}
			
}
function tagExport()
	{	
	var contentid =  document.getElementById("contentid");
	var riskid =  document.getElementById("riskid");
		if(confirm("您确定要执行导出操作吗？"))
		{	
			refForm.action = "riskTagList.do?perType=output";
			refForm.submit();
		}
		else
		{
			return false;	
		}
	}
function cancel()
{
    if(confirm("您确定对选定应用取消屏蔽吗？"))
		{
		    var select = document.getElementsByName('dealRef');
		    var isSelect = false;
			
			for(var i=0; i< select.length; i++)
			{
				if(select[i].checked == true)
				{
					isSelect = true;
					break;
				}
			}
			
			if(isSelect == false)
			{
				alert("请选择需要屏蔽的应用");
				return false;
			}
			
			refForm.action = "riskTagList.do?perType=edit&isblack=0";
			refForm.submit();
		}
		else
		{
			return false;	
		}
}

function black()
	{
		if(confirm("您确定要屏蔽吗？"))
		{
		    var select = document.getElementsByName('dealRef');
		    var isSelect = false;
			
			for(var i=0; i< select.length; i++)
			{
				if(select[i].checked == true)
				{
					isSelect = true;
					break;
				}
			}
			
			if(isSelect == false)
			{
				alert("请选择需要屏蔽的应用");
				return false;
			}
			
			refForm.action = "riskTagList.do?perType=edit&isblack=1";
			refForm.submit();

		}
		else
		{
			return false;	
		}
	}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="refForm" action="riskTagList.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td colspan="4" align="center" class="title1">风险标签查询 <input
					type="hidden" name="perType" value="queryDetail"></td>
			</tr>
			<input type="hidden" name="riskid" id ="riskid" value=<%=request.getAttribute("riskid")%>> 
			<tr>
				<td width="5%" align="right" class="text3">内容ID：</td>
				<td width="5%" class="text4"><input type="text"
					name="contentid" id ="contentid" value= <%=request.getAttribute("contentid")%>>    
				</td>
			<td width="5%" align="right" class="text3">内容名称：</td>
			<td width="5%" class="text4"><input type="text" name="content"
				value=<%=request.getAttribute("content")%>>    <%-- <%=content%> --%>
			</td>
			</tr>
			<tr>
				<%-- <td width="5%" align="right" class="text3">内容ID：</td>
				<td width="5%" class="text4"><input type="text"
					name="contentId" value="<%=contentId%>">
				</td> --%>
			<td width="5%" align="right" class="text3">状态：</td>
				<td width="5%" class="text4"><select name="stats">
							<option value="-1">
								全部
							</option>
							<option value="1">
								屏蔽
							</option>
							<option value="0">
								展示
							</option>
						</select>
						</td> 
			</tr>
			<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit" type="submit" class="input1" value="查询"> <input
					name="reset" type="reset" class="input1" value="重置"></td>
			</tr>
			<tr>
				<td colspan="4" align="center" class="text5"><input
					name="outPut" type="submit" class="input1" value="导出" onClick="tagExport()"> <input
					name="return" type="reset" class="input1"  onClick="javascript:history.back(-1);" value="返回"></td>
			</tr>
		</table>

		<br>

		<logic:empty name="PageResult" property="pageInfo">
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#FFFFFF">
				<tr bgcolor="#B8E2FC">
					<td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font>
					</td>
				</tr>
			</table>
		</logic:empty>

		<logic:notEmpty name="PageResult" property="pageInfo">
			<table width="95%" border="0" align="center" cellspacing="3"
				class="text4">
				<tr class="text5">
					<td align="center"><b>风险标签列表元数据列表</b></td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<!-- <td width="5%" align="center" class="title1"><input
						type="checkbox" value='' name="allSelect"
						onclick="selectAllCB(refForm,'dealRef',this.checked,false);" /> 全选
					</td> -->
					<td width="10%" align="center" class="title1">内容ID</td>
					<td width="10%" align="center" class="title1">内容名称</td>
					<td width="10%" align="center" class="title1">提供商</td>
					<td width="5%" align="center" class="title1">类型</td>
					<td width="10%" align="center" class="title1">状态</td>
					<td width="20%" align="center" class="title1">修改时间</td>
				</tr>
				<logic:iterate id="vo" indexId="ind" name="PageResult"
					property="pageInfo"
					type="com.aspire.ponaadmin.web.risktag.vo.RiskTagVO">
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
						<%-- <td align="center" style="word-break:break-all;"><input
							type="checkbox" name="dealRef"
							value="<bean:write name="vo" property="contentID"/>"></td> --%>
						<td align="center"><bean:write name="vo" property="contentID" />
						</td>
						<td align="center"><bean:write name="vo" property="name" /></td>
						<td align="center"><bean:write name="vo" property="company" />
						</td>
						<td align="center"><bean:write name="vo" property="type" />
						</td>
						<td align="center">
						<logic:equal name="vo" property="isblack" value="1"> 屏蔽 </logic:equal>
						<logic:equal name="vo" property="isblack" value="0"> 展示 </logic:equal>
						<logic:equal name="vo" property="isblack" value=""> 展示 </logic:equal>
						</td>
						<td align="center"><bean:write name="vo"
								property="time" /></td>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<td align="right">
						<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("contentId",request.getParameter("contentId"));
		        params.put("isblack",request.getParameter("isblack"));
		        params.put("perType",request.getParameter("perType"));
		        %> <pager:pager name="PageResult" form="refForm"
							action="/web/risktag/riskTagList.do" params="<%=params%>">
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

<!-- 		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1">
			<tr bgcolor="#B8E2FC">
				<td align="center"><input name="buttonAdd" type="button"
					class="input1" onclick="cancel()" value="取消屏蔽"> <input
					name="buttonExport" type="button" class="input1" onclick="black()"
					value="屏蔽"></td>
					<td align="center"><input name="buttonAdd" type="button"
					class="input1" onclick="cancelAll()" value="一键下线所有已屏蔽">
			</tr>
		</table> -->

	</form>
</body>
</html>

