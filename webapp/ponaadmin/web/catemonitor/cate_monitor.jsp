<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>营销体验门户管理平台</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
	</head>
	
<%
HashMap deviceHm = (HashMap) request.getSession().getAttribute("deviceHm");
String deviceName = (String) request.getSession().getAttribute("deviceName");
String brandName = (String) request.getSession().getAttribute("brandName");
	if(deviceName == null )
	{
	deviceName = "";
	}
	if(brandName == null )
	{
	brandName = "";
	}
%>

<script>
function onclickAdd(obj)
		{		
		var devicename = '<%=deviceName%>' ;
		var bland = document.getElementById("brandName").options[window.document.getElementById("brandName").selectedIndex].value;
		switch (bland) {
		case'':
                 var labels=new Array("二级目录");
				 var values=new Array("");
				 break;	
		case'all':
                 clearOptions(obj);
				 break;						   
	<%
	for(Iterator itr = deviceHm.keySet().iterator(); itr.hasNext();)
	{ 
			String key = (String) itr.next(); 
			List devicenames = (List) deviceHm.get(key); 
			%>
			case'<%=key%>':
			clearOptions(obj);
			
			<%
			if(devicenames != null && devicenames.size()>0)
			{
				for(int i = 0 ; i < devicenames.size();i ++)
				{
				String[]  devices = (String[])devicenames.get(i);
				if(devices.length == 2){
			%>   var detemp = '<%=devices[0]%>' ;
				var opt<%=i%> = new Option('<%=devices[1]%>','<%=devices[0]%>');
				obj.options.add(opt<%=i%>); 
				if(devicename==detemp){
				  obj.options.selectedIndex=<%=i%>;
				}	 
			<% }
				}
			}
			%>
			break;	
			<%
			}
	%>
	}
	}
	
	
	
	function setDeviceName(genLogo)
{
  var temp=document.getElementsByName("deviceName");
   var brand=document.getElementsByName("brandName");
  var taskName=temp[0];
  var brandtaskName=brand[0];
  window.location.href = "monitorSetDeviceName.do?deviceName="+taskName.value+"&brandName="+brandtaskName.value;
}
	function clearOptions(colls){
        var length = colls.length;
        for(var i=length-1;i>=0;i--){
               colls.remove(i);
           }
       }	
</script>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="onclickAdd(deviceName);">

		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				<td align="center">
					<FONT class=large>榜单监控选择机型<br> </FONT>
				</td>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择品牌
					<select id="brandName" name="brandName" onChange="onclickAdd(deviceName);">
						<option value="all">
							选择全部品牌
						</option>
						<%
						for(Iterator itr = deviceHm.keySet().iterator(); itr.hasNext();)
						{ 
								String key = (String) itr.next();
								String bg = key.equals(brandName)?"selected=true":"";
							%>
							<option value="<%=key%>" <%=bg%> ><%=key%></option>
							<%
						}
						%>
					</select>
				</td>
				<td bgcolor="#E9F0F8" class="text1" align="center">
					请选择机型
					<select id="deviceName" name="deviceName">						
					</select>
				</td>
			</tr>
			<tr bgcolor="#E9F0F8">
			<td align="center">
					当前选择机型：<%
					if(request.getSession().getAttribute("deviceName") == null || request.getSession().getAttribute("deviceName").equals("")){
					%>全部
					<%
					}else{
					out.print(request.getSession().getAttribute("deviceName"));
					}
					%>
				</td>
				<td align="center">
					<input type="button" class="input1" name="genLogo" value="确定"
						onclick="setDeviceName(this);">
				</td>
				<td align="center">
					
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
	
			<tr>
				
			</tr>
			
		</table>

		<table width="95%" border="0" align="center" cellspacing="3"
			bgcolor="#FFFFFF">
			<tr class="title1">
				
			</tr>
		</table>
		<br>
	
		
		
		<br>
	
	</body>
</html>
