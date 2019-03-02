<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
//String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String programId = Validator.filter(request.getParameter("programId")==null?"":request.getParameter("programId"));
String programName = Validator.filter(request.getParameter("programName")==null?"":request.getParameter("programName"));
String videoId = Validator.filter(request.getParameter("videoId")==null?"":request.getParameter("videoId"));
String nodeId = Validator.filter(request.getParameter("nodeId")==null?"":request.getParameter("nodeId"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>视频黑名单管理</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript">	
		function add()
		{
			refForm.method.value="add";
			var returnv=window.showModalDialog("queryVbContent.do?method=query_vb_content&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
			if(returnv != undefined && returnv != '')
			{
				refForm.contentId.value=returnv;
				refForm.submit();
			}  
		}
		
		function importData()
		{
			form1.method.value="importData";
			
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
		</script>
	</head>

	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="form" action="black.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      视频节目查询
      <input type="hidden" name="perType" value="queryblack">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">节目编号：
    <input type="hidden" name="method" value="query">
    </td>
    <td width="30%" class="text4"><input type="text" name="programId" value="<%=programId%>"></td>
    <td width="18%" align="right" class="text3">节目名称：
    </td>
    <td width="30%" class="text4"><input type="text" name="programName" value="<%=programName%>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">内容编码：
    </td>
    <td width="30%" class="text4"><input type="text" name="videoId" value="<%=videoId%>"></td>
    <td width="18%" align="right" class="text3">栏目编码：
    </td>
    <td width="30%" class="text4"><input type="text" name="nodeId" value="<%=nodeId%>"></td>
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
		<form name="refForm" action="black.do" method="post">
			<input type="hidden" name="method" value="">
			<input type="hidden" name="contentId" value="">
			<input type="hidden" name="setSortId" value="">
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

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
 	<td width="5%" align="center" class="title1">
	<input type="checkbox" value='' name="allSelect"
	onclick="selectAllCB(refForm,'dealRef',this.checked,false);" />
	全选
	</td>
    <td width="15%" align="center" class="title1">节目编码</td> 
    <td width="20%" align="center" class="title1">节目名称</td>
    <td width="15%" align="center" class="title1">栏目编码</td>
    <td width="20%" align="center" class="title1">内容编码</td>    
    <td width="15%" align="center" class="title1">最后修改时间</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseVideo.vo.BlackVO">
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
		<td align="center" style="word-break: break-all;">
		<input type="checkbox" name="dealRef"
		value="<bean:write name="vo" property="id"/>">	
			<td align="center" style="word-break:break-all;">
				<bean:write name="vo" property="programId"/>
			</td>
			<td align="center">
      			<bean:write name="vo" property="programName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="nodeId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="videoId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="lastUpTime" format="yyyy-MM-dd HH:mm:ss"/>
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
        params.put("programId",programId);
        params.put("programName",programName);
        params.put("nodeId",nodeId);
		params.put("videoId",videoId);
	    params.put("method",request.getParameter("method"));
        %>
							<pager:pager name="PageResult"
								action="/web/baseVideo/black.do" params="<%=params%>">
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

			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<td align="center">
						<logic:notEmpty name="PageResult" property="pageInfo">
							<input name="method" type="button"  class="input1"
								onClick="deleteBlack();" value="移除">
						</logic:notEmpty>
						<input name="buttonAdd" type="button" class="input1"
							onClick="add();" value="添加">
						<input name="buttonImport" type="button" class="input1"
							onClick="importData();" value="导入内容">
					</td>
				</tr>
			</table>
		</form>
		<form action="importBlack.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method" value="importData">
			<table width="95%" border="0" align="center" cellspacing="1">
				<tr>
					<td align="left" colspan="2">
						<b>增加类型</b>
						<select name="addType">
						<!-- 
							<option value="ALL">
								全量
							</option>
							 -->
							<option value="ADD">
								增量
							</option>
						</select>
						<font color=red>&nbsp;&nbsp;<b>
							<br />增量：在该黑名单下增加内容，增量添加EXCEL（2003版本或者2007版本）指定内容到该黑名单
							<!--<br />全量：删除原有该黑名单下全部内容，全量添加EXCEL(2003版本或者2007版本)指定内容到该黑名单 <b>-->
						</font>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%">
						选择要导入的excel数据文件：
					</td>
					<td align="left" width="80%">
						<input type="file" name="dataFile">
						(表格第一列表示节目ID，第二列表示栏目ID)
					</td>
				</tr>
			</table>
			<br />
			<br />
			<br />
		</form>
			<script language="javascript">
		function deleteBlack()
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
				alert("请选择视频黑名单数据");
				return false;
			}
			refForm.action = "remove.do?method=remove";
			refForm.submit();
		}
		</script>
	</body>
</html>
