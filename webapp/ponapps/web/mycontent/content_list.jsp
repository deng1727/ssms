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
 	QueryForm.catename.value = "";
 	QueryForm.beginDate.value = "";
 	QueryForm.endDate.value = "";
 	QueryForm.keywords.value = "";
 }

</script>
</head>

<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
	<form name="QueryForm" action="contentInfo.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
			<td colspan="4" align="center" class="title1">
				全量应用查询
			</td>
		  </tr>
		  <tr>
		  <input type="hidden" name="perType" value="query">
				<td width="20%" align="center" class="text3">
				
					应用ID：
				</td>
				<td width="30%" class="text4">
					<input type="text" name="contentid" value="<bean:write name="contentid"/>"/>
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
					应用分类：
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
					应用标签：
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
					每页展示记录数:
				</td>
				<td width="30%" class="text4">
					<input type="text" name="pageSize" value="<bean:write name="pageSize"/>"/>
				</td>
				
				<td width="20%" align="center" class="text3">
					
				</td>
				<td width="30%" class="text4">
					
				</td>
			</tr>
				<tr>
					<td align="center" class="text3" colspan="4">
						<input name="Submit" type="submit" value="查  询">
						<input type="button" value="重  置" onclick="empty()">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<logic:empty name="PageResult" property="pageInfo">
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
		      <tr bgcolor="#B8E2FC">
		          <td colspan="5" align="center"><font color="#ff0000">没有找到任何记录。</font></td>
		      </tr>
		</table>
	    </logic:empty>
		
		<logic:notEmpty name="PageResult" property="pageInfo">
			<form name="modifyForm" method="post" action="">
			   <table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>应用列表</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
					<tr class="title2">
						<td width="5%" align="center">
							应用ID
						</td>
						<td width="5%" align="center">
							应用名称
						</td>
						<td width="5%" align="center">
							应用分类
						</td>
						<td width="5%" align="center">
							上线时间
						</td>
						<td width="5%" align="center">
							最后更新时间
						</td>
						<td width="5%" align="center">
							应用标签
						</td>
						<td width="10%" align="center">
							应用简介
						</td>
					</tr>
						<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.webpps.mycontent.vo.MyContentVO">
							<tr <%=ind.intValue()%2== 0 ? "class=\'text4\'" : "class=\'text3\'"%>>

								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="contentid"/>
								</td>
								<td align="center" style='word-break:break-all'>
									<bean:write name="vo" property="name"/>
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
									<textarea  readonly="readonly" rows="3"  cols="40"><bean:write name="vo" property="introduction"/></textarea>
								</td>
							</tr>
						</logic:iterate>				
				</table>
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<pager:pager name="PageResult" form="QueryForm" action="/web/mycontent/contentInfo.do?perType=query">
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
		</table>
			</form>
		</logic:notEmpty>

		
</body>
</html>
