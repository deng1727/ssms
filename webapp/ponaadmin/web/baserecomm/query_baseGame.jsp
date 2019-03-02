<%@ page contentType="text/html; charset=gb2312"%>
<%@ page language="java"%>
<%@page import="com.aspire.ponaadmin.common.page.PageResult"%>
<%@page import="com.aspire.ponaadmin.web.baserecomm.basegame.BaseGameVO"%>
<%@page import="java.util.*"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>查询基地游戏数字内容</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
	</head>

	<body bgcolor="#F3F7F8" leftmargin="0" topmargin="5">
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" bgcolor="#BFE8FF">
					基地游戏查询
				</td>
			</tr>
		</table>
		<form name="ContentForm" action="baseGameQuery.do" method="post">
			<input type="hidden" name="actionType" value="add">
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="right" class="text3">
						游戏名称：
					</td>
					<td width="40%" class="text4">
						<input type="text" name="gameName"
							value="<bean:write name="gameName"/>">
					</td>
					<td width="10%" align="right" class="text3">
						游戏简介：
					</td>
					<td width="40%" class="text4">
						<input type="text" name="gameDesc"
							value="<bean:write name="gameDesc"/>">
					</td>
				</tr>
			</table>
			<table width="95%" border="0" align="center" cellspacing="3"
				bgcolor="#FFFFFF">
				<tr class="text5">
					<td align="center">
						<input type="submit" class="input1" name="submit01" value="查询">
						<input type="button" class="input1" name="btn_reset" value="重置"
							onClick="clearForm(ContentForm);">
					</td>
				</tr>
			</table>

			<br>

			<logic:empty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">没有找到任何记录。</font>
						</td>
					</tr>
				</table>
			</logic:empty>

			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>基地游戏列表</b>
						</td>
					</tr>
				</table>
				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td width="10%" align="center" class="title1">
							<input type="checkbox" value='' name="allSelect"
								onclick="selectAllCB(ContentForm,'selectGame',this.checked,false);" />
							全选
						</td>
						<td width="10%" align="center" class="title1">
							基地游戏ID
						</td>
						<td width="10%" align="center" class="title1">
							类型
						</td>
						<td width="40%" align="center" class="title1">
							游戏名称
						</td>
						<td width="30%" align="center" class="title1">
							游戏简介
						</td>
					</tr>
					<%
	                String desc = "";
	                PageResult pageRes = ( PageResult ) request.getAttribute("PageResult");
	                List list = pageRes.getPageInfo();

	                for (int i = 0; i < list.size(); i++)
	                {
	                    BaseGameVO vo = ( BaseGameVO ) list.get(i);

	                    desc = vo.getPkgDesc();

	                    if (desc == null)
	                    {
	                        desc = "";
	                    }

	                    if (desc.length() > 20)
	                    {
	                        desc = desc.substring(0, 20) + "...";
	                    }
					%>
					<tr class=<%=i % 2 == 0 ? "text4" : "text5"%>>
						<td align="center" style="word-break:break-all;">
							<input type="checkbox" value='<%=vo.getPkgId()%>'
								name="selectGame" />

						</td>
						<td align="center">
							<%=vo.getPkgId()%>
						</td>
						<td align="center">
							<%
		                    if ("0".equals(vo.getPkgType()))
		                    {
		                        out.print("游戏包");
		                    }
		                    else if ("1".equals(vo.getPkgType()))
		                    {
		                        out.print("精品单机");
		                    }
		                    else if ("2".equals(vo.getPkgType()))
		                    {
		                        out.print("单机转激活");
		                    }
							%>
						</td>
						<td align="center">
							<%=vo.getPkgName()%>
						</td>
						<td align="center">
							<%=desc%>
						</td>
					</tr>
					<%
					}
					%>
				</table>
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<%
			                HashMap params = new HashMap();
			                params.put("gameName", request.getParameter("gameName"));
			                params.put("gameDesc", request.getParameter("gameDesc"));
							%>
							<pager:pager name="PageResult"
								action="/web/baserecomm/baseGameQuery.do" params="<%=params%>">
								<pager:firstPage label="首页" />&nbsp;
				            <pager:previousPage label="前页" />&nbsp;
				            <pager:nextPage label="后页" />&nbsp;
				            <pager:lastPage label="尾页" />&nbsp;
				            第<pager:pageNumber />页/共<pager:pageCount />页
				            <pager:location id="1" />
							</pager:pager>
						</td>
					</tr>
				</table>

				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input type="button" value="添加至缓存" onclick="addList();">
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</form>
		<script language="javascript">
		
		function addList()
		{
			var select = document.getElementsByName('selectGame');
			
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
				alert("请选择基地游戏数据");
				return false;
			}
			
			ContentForm.action = "baseGameTemp.do";
			ContentForm.submit();
		}
		
		</script>
	</body>
</html>
