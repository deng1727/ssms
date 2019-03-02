<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
    String tmpStyle = "text5";
    String contentId = Validator.filter(request.getParameter("contentId") == null ? "" : request.getParameter("contentId"));
    String contentName = Validator.filter(request.getParameter("contentName") == null ? "" : request.getParameter("contentName"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>添加查询</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<base target="_self" />

	</head>
	<script language="javascript">
	function add()
	{
		var temp = '';
		
		if(refForm.dealRef.length == undefined)
		{
			if(refForm.dealRef.checked == true)
			{
				temp = temp + refForm.dealRef.value + ';';
			}
		}
		else
		{
			for(var i=0;i<refForm.dealRef.length; i++)
			{
				if(refForm.dealRef[i].checked==true)
				{
					temp = temp + refForm.dealRef[i].value + ';';
				}
			}
		}
		temp = temp.substring(0,temp.lastIndexOf(";"));
        window.returnValue=temp;
	    window.close();
	}
</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="refForm" action="queryCbContent.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						内容查询
						<input type="hidden" name="method" value="query_cb_content">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						内容编号：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="contentId" value="<%=contentId%>">
					</td>
					<td width="18%" align="right" class="text3">
						内容名称：
					</td>
					<td width="30%" class="text4">
						<input type="text" name="contentName" value="<%=contentName%>">
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="查询">
						<input name="reset" type="reset" class="input1" value="重置">
					</td>
				</tr>
			</table>
			<br>
			<logic:equal name="isFirst" value="1">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">请输入查询条件。</font>
						</td>
					</tr>
				</table>
			</logic:equal>


			<logic:equal name="isFirst" value="">
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
								<b>商品列表</b>
							</td>
						</tr>
					</table>
					<table width="95%" border="0" align="center" cellspacing="1"
						bgcolor="#FFFFFF">
						<tr>
							<td width="5%" align="center" class="title1">
								<input type="checkbox" value='' name="allSelect"
									onclick="selectAllCB(refForm,'dealRef',this.checked,false);" />
								全选
							</td>
							<td width="10%" align="center" class="title1">
								内容ID
							</td>
							<td width="18%" align="center" class="title1">
								内容名称
							</td>
							<td width="5%" align="center" class="title1">
								类型
							</td>
							<td width="5%" align="center" class="title1">
								门户
							</td>
							
						</tr>
						<logic:iterate id="vo" indexId="ind" name="PageResult"
							property="pageInfo"
							type="com.aspire.dotcard.basecomic.vo.BlackVO">
							<%
							    if ("text5".equals(tmpStyle))
		                        {
		                            tmpStyle = "text4";
		                        }
		                        else
		                        {
		                            tmpStyle = "text5";
		                        }
							%>
							<tr class=<%=tmpStyle%>>
								<td align="center" style="word-break: break-all;">
									<input type="checkbox" name="dealRef"
										value="<bean:write name="vo" property="contentId"/>">
								</td>
								<td align="center">
									<bean:write name="vo" property="contentId" />
								</td>
								<td align="center">
									<a
										href="queryCbContent.do?contentId=<bean:write name="vo" property="contentId"/>">
										<bean:write name="vo" property="contentName" />
									</a>
								</td>

								
								<td align="center">
									<%
									    //	101:Theme，主题
				                        //	116-104:MovieSeries，动画片
				                        //	115:Information，资讯
				                        //	220-221:ComicSeries，漫画书
									%>
									<logic:equal name="vo" property="contentType" value="101">
						      			主题
							      	</logic:equal>
							      	<logic:equal name="vo" property="contentType" value="115">
							      		动画片
							      	</logic:equal>
							      	<logic:equal name="vo" property="contentType" value="116">
							      		资讯
							      	</logic:equal>
							      	<logic:equal name="vo" property="contentType" value="220">
							      		漫画书
							      	</logic:equal>
								</td>
								<td align="center">
										<logic:greaterThan name="vo" property="contentPortal" value="3">
							      			未知
							      		</logic:greaterThan>
										<logic:lessThan name="vo" property="contentPortal" value="1">
							      			未知
							      		</logic:lessThan>
										<logic:equal name="vo" property="contentPortal" value="1">
							      			客户端
							      		</logic:equal>
										<logic:equal name="vo" property="contentPortal" value="2">
							      			WAP门户
							      		</logic:equal>
										<logic:equal name="vo" property="contentPortal" value="3">
							      			所有
							      		</logic:equal>
								</td>
								
							</tr>
						</logic:iterate>
					</table>
					<table width="95%" border="0" align="center" cellpadding="0"
						cellspacing="1">
						<tr bgcolor="#B8E2FC">
							<td align="right">
								<%
								    HashMap params = new HashMap();
								            params.put("contentId", contentId);
								            params.put("contentName", contentName);
								            params.put("method", request.getParameter("method"));
								%>
								<pager:pager name="PageResult" form="refForm"
									action="/web/comic/queryCbContent.do" params="<%=params%>">
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
				</logic:notEmpty>
			</logic:equal>

			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input name="buttonAdd" type="button" class="input1"
								onClick="add();" value="添加">
						</td>
					</tr>
				</table>
			</logic:notEmpty>
		</form>
	</body>
</html>
