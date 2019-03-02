<%@ page contentType="text/html; charset=gbk" %>
<%@page import="java.util.List"%>
<%@page import="java.util.StringTokenizer"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>

<script language="javascript">

	
 function empty()
 {
 	QueryForm.contentid.value = "";
 	QueryForm.name.value = "";
 	QueryForm.icpcode.value = "";
 	QueryForm.develoepr.value = "";
// 	QueryForm.spname.value = "";
 	QueryForm.catename.value = "";
 	QueryForm.beginDate.value = "";
 	QueryForm.endDate.value = "";
 	QueryForm.id.value = "";
 	QueryForm.servattr.value = "";
 	QueryForm.keywords.value = "";
 }
 

function search()
{
	QueryForm.action = "../queryapp/QueryAppAction.do?opType=doQueryContentList";
	QueryForm.submit();
}


function exportApp()
{
	QueryForm.action = "../queryapp/QueryAppAction.do?opType=doExport&type=content";
	QueryForm.submit();  
}

</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1">
				商品全量应用查询
			</td>
		</tr>
	</table>
	<form name="QueryForm" action="" method="post">
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
				<td width="20%" align="center" class="text3">
					应用ID：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="id" value="<bean:write name="id"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					应用名称：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="name" value="<bean:write name="name"/>"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="center" class="text3">
					SP企业代码：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="icpcode" value="<bean:write name="icpcode"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					提供商：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="developer" value="<bean:write name="developer"/>"/>
<%-- 					<input type="text" name="spname" value="<bean:write name="spname"/>"/> --%>
				</td>
			</tr>
			
			
			
			<tr>
				<td width="20%" align="center" class="text3">
					内容ID：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="contentid" value="<bean:write name="contentid"/>"/>
				</td>
				<td width="20%" align="center" class="text3">
					内容标签：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="keywords" value="<bean:write name="keywords"/>"/>
				</td>
			</tr>
			
			<tr>
				<td width="20%" align="center" class="text3">
					开始时间：
				</td>
				
				<td width="30%" class="text4">
			
					<input name="beginDate" class="Wdate" type="text"  value="<bean:write name="beginDate"/>"  style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
				</td>
				
				<td width="20%" align="center" class="text3">
					结束时间：
				</td>
				<td width="30%" class="text4">
					<input name="endDate" class="Wdate" type="text"  value="<bean:write name="endDate"/>"  style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly/>
				</td>
			</tr>
			
			
			<tr>
				<td width="20%" align="center" class="text3">
					业务内容分类：
				</td>
				<td width="30%" class="text4">
					<select name="catename">
						<option value=""   <logic:equal name="catename" value="">selected="selected"</logic:equal>  >全部</option>
						<option value="软件"  <logic:equal name="catename" value="软件">selected="selected"</logic:equal> >软件</option>
						<option value="游戏"  <logic:equal name="catename" value="游戏">selected="selected"</logic:equal> >游戏</option>
						<option value="主题"  <logic:equal name="catename" value="主题">selected="selected"</logic:equal> >主题</option>
					</select>
				</td>
				
				<td width="20%" align="center" class="text3">
					服务范围：
				</td>
				<td width="30%" class="text4">
					<select name="servattr">
						<option value=""   <logic:equal name="servattr" value="">selected="selected"</logic:equal>  >全部</option>
						<option value="G"  <logic:equal name="servattr" value="G">selected="selected"</logic:equal> >全网业务</option>
						<option value="L"  <logic:equal name="servattr" value="L">selected="selected"</logic:equal> >省内业务</option>
					</select>
				</td>
			</tr>
			 <tr>
				<td width="20%" align="center" class="text3">
					每页展示记录数:
				</td>
				<td width="30%" class="text4">
					<input type="text" name="pageSize" value="<bean:write name="pageSize"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					开发者：
				</td>
				<td width="30%" class="text4">
<%-- 					<input type="text" name="developer" value="<bean:write name="developer"/>"/> --%>
					<input type="text" name="spname" value="<bean:write name="spname"/>"/>
				</td>
			</tr>
				<tr>
					<td align="center" class="text3" colspan="4">
						<input type="button" class="input1" value="查询" onclick="search()">
						<input type="button" class="input1" value="重置" onclick="empty()">
						<logic:notEmpty name="PageResult" property="pageInfo">
							<input type='button' value='导出' class='input1' onclick='exportApp();'>
						</logic:notEmpty>
					</td>
				</tr>
			</table>
		</form>
		<br>
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					查询结果
				</td>
			</tr>
		</table>

		<logic:present name="PageResult">
			<form name="modifyForm" method="post" action="">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
					<tr class="title2">
						<td width="5%" align="center">
							应用ID
						</td>
						<td width="5%" align="center">
							应用名称
						</td>
						<td width="5%" align="center">
							内容ID
						</td>
						<td width="5%" align="center">
							SP企业代码
						</td>
						<td width="5%" align="center">
							提供商
						</td>
						<td width="5%" align="center">
							开发者
						</td>
						<td width="5%" align="center">
							业务内容分类
						</td>
						<td width="5%" align="center">
							上线时间
						</td>
						<td width="5%" align="center">
							最后更新时间
						</td>
						<td width="5%" align="center">
							价格(元)
						</td>
						<td width="5%" align="center">
							服务范围
						</td>
						<td width="5%" align="center">
							历史下载量
						</td>
						<td width="5%" align="center">
							内容标签
						</td>
						<td width="5%" align="center">
							应用简介
						</td>
					</tr>

					<logic:empty name="PageResult" property="pageInfo">
						<table align="center">
							<tr class="text4">
								<td colspan="7" align="center">
									没有符合条件的记录
								</td>
							</tr>
						</table>
					</logic:empty>
					<logic:notEmpty name="PageResult">
						<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.queryapp.vo.QueryAppVO">
							<tr <%=ind.intValue()%2== 0 ? "class=\'text4\'" : "class=\'text3\'"%>>

								<td align="center" style='word-break:break-all'>
									<a href="../queryapp/QueryAppAction.do?opType=doQueryAppDetail&id=<bean:write name="vo" property="id"/>&contentId=<bean:write name="vo" property="contentid"/>&catename=<bean:write name="vo" property="catename"/>"><bean:write name="vo" property="id"/></a>
								</td>
								<td align="center" style='word-break:break-all'>
									<a href="../queryapp/QueryAppAction.do?opType=doQueryAppDetail&id=<bean:write name="vo" property="id"/>&contentId=<bean:write name="vo" property="contentid"/>&catename=<bean:write name="vo" property="catename"/>"><bean:write name="vo" property="name"/></a>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="contentid"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="icpcode"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="developer"/>
<%-- 									<bean:write name="vo" property="spname"/> --%>
								</td>
								<td align="center" style='word-break:break-all'>
<%-- 									<bean:write name="vo" property="developer"/> --%>
									<bean:write name="vo" property="spname"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="catename"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="marketdate"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="plupddate"/>
								</td>
								
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="mobileprice"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<logic:equal name="vo" property="servattr" value="G">全网业务</logic:equal> 
									<logic:equal name="vo" property="servattr" value="L">省内业务</logic:equal> 
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="orderTimes"/>
								</td>
								<td align="left" style='word-break:break-all'>
									<%
									if(!"".equals(vo.getKeywords())&&null!=vo.getKeywords())
									{
										StringTokenizer line=new StringTokenizer(vo.getKeywords(),"\n");
										while(line.hasMoreTokens()){
										out.println(line.nextToken()+"<br>");
										}
									}


									%>
								</td>
								<td align="center" style='word-break:break-all'>
									<textarea  readonly="readonly" rows="3"  cols="20"><bean:write name="vo" property="introduction"/></textarea>
								</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</table>
			</form>
		</logic:present>

		<table width="95%" border="0" align="center">
			<logic:notEmpty name="PageResult">
				<logic:notEmpty name="PageResult" property="pageInfo">
					<tr class="text1">
						<td width="24%" class="text1">
							&nbsp;
						</td>
						<td align="right">
							<pager:pager name="PageResult" form="QueryForm" action="/web/queryapp/QueryAppAction.do?opType=doQueryContentList">
								<pager:firstPage label="首页" />&nbsp;
					            <pager:previousPage label="前页" />&nbsp;
					            <pager:nextPage label="后页" />&nbsp;
					            <pager:lastPage label="尾页" />&nbsp;
					            第<pager:pageNumber />页/共<pager:pageCount />页
								总记录数:<pager:recordCount />
								<pager:location id="1" />
							</pager:pager>
						</td>
					</tr>
				</logic:notEmpty>
			</logic:notEmpty>
		
		</table>
</body>
</html>
