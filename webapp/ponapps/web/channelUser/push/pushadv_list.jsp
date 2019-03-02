<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="com.aspire.common.Validator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>应用推送列表查询</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
	function removePush(id, contentId) {

		if (confirm("确定从推送列表中移除该推送？")) {
			pushAdvForm.perType.value = "remove";
			pushAdvForm.id.value = id;
			pushAdvForm.contentId.value = contentId;
			pushAdvForm.submit();
		}
	}
	function editPush(id, contentId) {
			pushAdvForm.perType.value = "edit";
			pushAdvForm.id.value = id;
			pushAdvForm.contentId.value = contentId;
			pushAdvForm.submit();
	}
	
	
	
	function add() {
		window.location.href = "pushAdv.do?perType=add";
	}

	function importData() {
		importForm.perType.value = "importData";

		var filePath = importForm.dataFile.value;

		if (filePath == "" || filePath == null || filePath == "null") {
			alert("请选择要导入数据的文件！");
			return false;
		}

		if (!isFileType(filePath, "xls") && !isFileType(filePath, "xlsx")) {
			alert("请选择xls或xlsx格式的数据文件!");
			return false;
		}
		importForm.submit();
	}

	function isFileType(filename, type) {
		var pos = filename.lastIndexOf(".");

		if (pos < 0)
			return false;

		var fileType = filename.substr(pos + 1);

		if (fileType.toUpperCase() != type.toUpperCase())
			return false;

		return true;
	}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
	<form name="refForm" action="pushAdv.do" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td colspan="4" align="center" class="title1">应用推送列表查询 <input
					type="hidden" name="perType" value="query"></td>
			</tr>
			<tr>
				<td width="18%" align="right" class="text3">推送ID：</td>
				<td width="30%" class="text4"><input type="text"
					name="contentId" value="<bean:write name="contentId"/>">
				</td>
				<td width="18%" align="right" class="text3">推送类型：</td>
				<td width="30%" class="text4"><select name="catename"
					id="catename">
						<option value="-1"
							<logic:equal name="type" value="-1">selected="selected"</logic:equal>>全部</option>
						<option value="0"
							<logic:equal name="type" value="0">selected="selected"</logic:equal>>应用推送</option>
						<option value="1"
							<logic:equal name="type" value="1">selected="selected"</logic:equal>>媒体化文章推送</option>
						<option value="2"
							<logic:equal name="type" value="2">selected="selected"</logic:equal>>Deeplink页面推送</option>
						<option value="3"
							<logic:equal name="type" value="3">selected="selected"</logic:equal>>营销活动推送</option>
				</select>
				</td>
			</tr>
			<tr>
				<td width="18%" align="right" class="text3">开始时间：</td>
				<td width="30%" class="text4"><input type="text" class="Wdate"
					name="startTime" value="<bean:write name="startTime"/>"
					style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
					readonly>
				</td>
				<td width="18%" align="right" class="text3">结束时间：</td>
				<td width="30%" class="text4"><input type="text" class="Wdate"
					name="endTime" value="<bean:write name="endTime"/>"
					style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"
					readonly>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit" type="submit" value="查  询"> <input
					name="reset" type="reset" value="重  置"></td>
			</tr>
		</table>
	</form>
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
		<form name="pushAdvForm" action="pushAdv.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="3"
				class="text4">
				<tr class="text5">
					<td align="center"><b>应用推送列表</b></td>
				</tr>
			</table>

			<input type="hidden" name="perType" value=""> <input
				type="hidden" name="id" value=""> <input type="hidden"
				name="contentId" value="">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td width="10%" align="center" class="title1">推送ID</td>
					<td width="20%" align="center" class="title1">推送类型</td>
					<td width="15%" align="center" class="title1">主标题</td>
					<td width="10%" align="center" class="title1">副标题</td>
					<td width="15%" align="center" class="title1">开始时间</td>
					<td width="10%" align="center" class="title1">结束时间</td>
					<td width="10%" align="center" class="title1">操作</td>
				</tr>
				<logic:iterate id="vo" indexId="ind" name="PageResult"
					property="pageInfo"
					type="com.aspire.ponaadmin.web.pushadv.vo.PushAdvVO">

					<tr
						<%=ind.intValue() % 2 == 0 ? "class=\'text4\'"
							: "class=\'text3\'"%>>
						<td align="center"><bean:write name="vo" property="contentId" />
						</td>
						<td align="center"><logic:equal name="vo" property="type"
								value="0">
                                                          应用推送 </logic:equal> <logic:equal
								name="vo" property="type" value="1">
                                                         媒体化文章推送</logic:equal> <logic:equal
								name="vo" property="type" value="2">
                       Deeplink页面推送 </logic:equal> <logic:equal
								name="vo" property="type" value="3">
                  	       营销活动推送 </logic:equal></td>
						<td align="center"><bean:write name="vo" property="title" />
						</td>
						<td align="center"><bean:write name="vo" property="subTitle" />
						</td>
						<td align="center"><bean:write name="vo" property="startTime" />
						</td>
						<td align="center"><bean:write name="vo" property="endTime" />
						</td>
						<td align="center" style="word-break: break-all"><input
							name="ad" type="button"
							onClick="removePush('<bean:write name="vo" property="id" />','<bean:write name="vo" property="contentId" />');"
							value="删除" /> <input name="ad" type="button"
							onClick="editPush('<bean:write name="vo" property="id" />','<bean:write name="vo" property="contentId" />');"
							value="编辑" /></td>
					</tr>
				</logic:iterate>
			</table>

			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1">
				<tr bgcolor="#B8E2FC">
					<td align="right"><pager:pager name="PageResult"
							form="refForm" action="/web/channelUser/pushAdv.do">
							<pager:firstPage label="首页" />&nbsp;
		            <pager:previousPage label="前页" />&nbsp;
		            <pager:nextPage label="后页" />&nbsp;
		            <pager:lastPage label="尾页" />&nbsp;
		            第<pager:pageNumber />页/共<pager:pageCount />页
		            <pager:location id="1" />
						</pager:pager></td>
				</tr>
			</table>
		</form>
	</logic:notEmpty>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="1">
		<tr bgcolor="#B8E2FC">
			<td align="center"><input name="buttonAdd" type="button"
				onClick="return add();" value="新增推送"></td>
		</tr>
	</table>

</body>
</html>
