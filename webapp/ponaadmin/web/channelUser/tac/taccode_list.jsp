<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String tacCode = Validator.filter(request.getParameter("tacCode")==null?"":request.getParameter("tacCode"));
String brand = Validator.filter(request.getParameter("brand")==null?"":request.getParameter("brand"));
String device = Validator.filter(request.getParameter("device")==null?"":request.getParameter("device"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>TAC码库查询</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function removeTac(id,taccode)
{

  if(confirm("确定从TAC码库中移除该TAC码？"))
  {
	  tacCodeForm.perType.value = "remove";
	  tacCodeForm.id.value = id;
	  tacCodeForm.tacCode.value = taccode;
	  tacCodeForm.submit();
  }
}

function remove()
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

function importData()
{
	importForm.perType.value="importData";
	
	var filePath = importForm.dataFile.value;
	
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
    importForm.submit();
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
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="tacCode.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	      TAC码库查询
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">TAC码：
	    </td>
	    <td width="30%" class="text4"><input type="text" name="tacCode" value="<%=tacCode%>"></td>
	    <td width="18%" align="right" class="text3">手机品牌：
	    </td>
	    <td width="30%" class="text4"><input type="text" name="brand" value="<%=brand%>"></td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">手机型号：
	    </td>
	    <td width="30%" class="text4"><input type="text" name="device" value="<%=device%>"></td>
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
		     <b>TAC码列表</b>
		    </td>
		  </tr>
		</table>
	<form name="tacCodeForm" action="tacCode.do" method="post">
		<input type="hidden" name="perType" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="tacCode" value="">
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <!-- <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	全选
		  	</td> -->
		    <td width="10%" align="center" class="title1">TAC码</td>     
		    <td width="20%" align="center" class="title1">手机品牌</td>
		    <td width="15%" align="center" class="title1">手机型号</td>
		    <td width="10%" align="center" class="title1">操作</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.taccode.vo.TacVO">
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
					<%-- <td align="center" style="word-break:break-all;">
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="id"/>">
					</td> --%>
					<td align="center">
		      			<bean:write name="vo" property="tacCode"/>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="brand"/>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="device"/>
					</td>
					<td align="center" style="word-break: break-all">
				       <input name="ad" type="button" class="input1" onClick="removeTac('<bean:write name="vo" property="id" />','<bean:write name="vo" property="tacCode" />');" value="删除"/>
				    </td>
		      		<%-- <td align="center">
		      			<bean:write name="vo" property="createTime"/>
		      		</td> --%>
				</tr>
		  </logic:iterate>
		</table>
		</form>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >
		    	<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("tacCode",tacCode);
		        params.put("brand",device);
		        params.put("device",brand);
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/channelUser/tacCode.do" params="<%=params%>">
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
	        <%-- <logic:notEmpty name="PageResult" property="pageInfo">
			    <input name="buttonDel" type="button" class="input1" onclick="remove()" value="删除">
			</logic:notEmpty> --%>
			<input name="buttonAdd" type="button" class="input1" onclick="importData()" value="批量导入TAC码">
	    </td>
	  </tr>
	</table>
</form>
<form action="tacCode.do" name="importForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="importData">
	<table width="95%"  border="0" align="center" cellspacing="3">
	  <tr>
	    <td   align="right" width="20%">
	    	请选择要导入的excel数据文件：
	    </td>
	    <td align="left" width="80%">
			<input type="file" name="dataFile">(表格第一列为TAC码,第二列为手机品牌,第三列为手机型号)
	    </td>
	</table>
</form>
</body>
</html>
