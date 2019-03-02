<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
   Map<String,Object> map = (Map)request.getAttribute("cooperationInfo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>查询合作商</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript" type="text/javascript">

function checkForm(form)
{
	 var bool = true;
	  var cooperationType = document.getElementsByName("cooperationType");
	  for(var i = 0;i < cooperationType.length;i++){
			if(cooperationType[i].checked  == true){
				bool = false;
			}
		}
		
		if(bool){
			alert("请选择合作方式");
			return false;
		}
  return true;
}
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
    <form name="form1" method="post" action="cooperationList.do" onSubmit="return checkForm(form1);">
    <input name="method" type="hidden" id="method" value="update">
    <input name="cooperationId" type="hidden" id="cooperationId" value="<%=map.get("cooperationId") %>">
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td align="center" class="title1">
	            查询合作商
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td width="25%" align="right" class="text3">
	           合作商ID：
	    </td>
	    <td width="75%" class="text4"><%=map.get("cooperationId") %>
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	合作商名称：
	    </td>
	    <td class="text4"><%=map.get("cooperationName") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	合作日期：
	    </td>
	    <td class="text4"><%=map.get("codate") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	合作方式：
	    </td>
	    <td class="text4">
	      <label><input name="cooperationType" type="checkbox" value="1" <% if(map.get("cotype").toString().contains("1")){ %> checked<%} %> />内容合作 </label> 
          <label><input name="cooperationType" type="checkbox" value="2" <% if(map.get("cotype").toString().contains("2")){ %> checked<%} %> />换量合作</label> 
	    </td>
	  </tr>
	   <tr>
	    <td align="right" class="text3">
	    	根货架ID：
	    </td>
	    <td class="text4"><%=map.get("categoryId") %>
	    </td>
	  </tr>
	  <tr>
	    <td align="right" class="text3">
	    	渠道个数：
	    </td>
	    <td class="text4">
	    <select name="channelNumber">
							<option value="1" <% if("1".equals(map.get("channelsnumber"))){ %> selected<%} %>>1</option>
							<option value="2" <% if("2".equals(map.get("channelsnumber"))){ %> selected<%} %>>2</option>
							<option value="3" <% if("3".equals(map.get("channelsnumber"))){ %> selected<%} %>>3</option>
							<option value="4" <% if("4".equals(map.get("channelsnumber"))){ %> selected<%} %>>4</option>
							<option value="5" <% if("5".equals(map.get("channelsnumber"))){ %> selected<%} %>>5</option>
							<option value="6" <% if("6".equals(map.get("channelsnumber"))){ %> selected<%} %>>6</option>
							<option value="7" <% if("7".equals(map.get("channelsnumber"))){ %> selected<%} %>>7</option>
							<option value="8" <% if("8".equals(map.get("channelsnumber"))){ %> selected<%} %>>8</option>
							<option value="9" <% if("9".equals(map.get("channelsnumber"))){ %> selected<%} %>>9</option>
							<option value="10" <% if("10".equals(map.get("channelsnumber"))){ %> selected<%} %>>10</option>
							<option value="11" <% if("11".equals(map.get("channelsnumber"))){ %> selected<%} %>>11</option>
							<option value="12" <% if("12".equals(map.get("channelsnumber"))){ %> selected<%} %>>12</option>
							<option value="13" <% if("13".equals(map.get("channelsnumber"))){ %> selected<%} %>>13</option>
							<option value="14" <% if("14".equals(map.get("channelsnumber"))){ %> selected<%} %>>14</option>
							<option value="15" <% if("15".equals(map.get("channelsnumber"))){ %> selected<%} %>>15</option>
							<option value="16" <% if("16".equals(map.get("channelsnumber"))){ %> selected<%} %>>16</option>
							<option value="17" <% if("17".equals(map.get("channelsnumber"))){ %> selected<%} %>>17</option>
							<option value="18" <% if("18".equals(map.get("channelsnumber"))){ %> selected<%} %>>18</option>
							<option value="19" <% if("19".equals(map.get("channelsnumber"))){ %> selected<%} %>>19</option>
							<option value="20" <% if("20".equals(map.get("channelsnumber"))){ %> selected<%} %>>20</option>
						</select>
	    </td>
	  </tr>
	</table>
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		<tr>
		  <td align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="保存">
		  </td>
		</tr>
	</table>
	</form>
</body>
</html>
