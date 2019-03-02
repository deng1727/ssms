<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>编辑规则排序sql</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/tools.js"></script>
	</head>
	<script language="javascript">
	var num = 1;
	var rid = 1;
	
	function init()
	{
		var obj = window.dialogArguments;
		
		if(trim(obj.sql) != '')
		{
			var osql=obj.sql.split(",");
			
			for(var i=0; i<osql.length; i++)
			{
				var temp = osql[i];
				
				var temp_key = trim(osql[i]).split(" ");
				
				var editSql = document.all.editSql;
				
		        var newTr = editSql.insertRow();
		        
		        newTr.id = "tr"+rid++;
	
		        var newTd = newTr.insertCell();
		        
		        newTd.align="center";
		        
		        var tdStyle = newTd.getAttribute("style");
		        
		        if(tdStyle == "[object]")
				{ 
					tdStyle.setAttribute("cssText","font-family: '宋体'; font-size: 12px; line-height: 1.5em; background-color: #C4EBF4;"); 
			        newTd.setAttribute("style",tdStyle ); 
				}
				else
				{ 
					newTd.setAttribute("class","text3" ); 
				}
				
				var ov;
		        //设置列内容和属性
		        if(temp_key[1] == 'desc')
		        {
		        	ov = '<input id="ov'+ num + '" type="text" name="asc" value="' + temp_key[0] + '"/>&nbsp;&nbsp;<select name="o'+ num +'"><option value="asc">升序</option><option value="desc" selected>降序</option></select>';
		        }
		        else 
		       	{
		       		ov = '<input id="ov'+ num + '" type="text" name="asc" value="' + temp_key[0] + '"/>&nbsp;&nbsp;<select name="o'+ num +'"><option value="asc" selected>升序</option><option value="desc">降序</option></select>';
		       	}
		       	
		       	if(temp_key[2] != null && temp_key[3] != null)
		       	{
		       		if(temp_key[3] == 'last')
		       		{
		       			ov = ov + '<input type="radio" value="nulls last" name="nu'+ num +'" checked />nulls last&nbsp;&nbsp;<input type="radio" value="nulls first" name="nu'+ num +'"/>nulls first';
		       		}
		       		else
		       		{
		       			ov = ov + '<input type="radio" value="nulls last" name="nu'+ num +'"/>nulls last&nbsp;&nbsp;<input type="radio" value="nulls first" name="nu'+ num +'" checked />nulls first';
		       		}
		       	}
		       	else
		       	{	
		       		ov = ov + '<input type="radio" value="nulls last" name="nu'+ num +'"/>nulls last&nbsp;&nbsp;<input type="radio" value="nulls first" name="nu'+ num +'" />nulls first';
		       	}
		        
		        num++;
		        
		        newTd.innerHTML = ov;
			}
		}		
	}
	</script>
	
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="init();">
		<form name="ContentForm" action="" method="post">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">
					编辑规则排序sql
				</td>
			</tr>
		</table>

		<br>
		<br>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF" id="editSql">
			<tr>
				<td align="center" class="text3">
					编辑区
				</td>
			</tr>
			<tr>
				<td align="center" class="text3">
					<input type="button" value="添加升序字段" onClick="addAsc();">
					<input type="button" value="添加降序字段" onClick="addDesc();">
					<input type="button" value="删除最后一字段" onClick="dell();">
				</td>
			</tr>
		</table>
		
		<br>
		<br>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr align="center" class="text3">
				<td colspan="2">
					<input type="button" value="新增" onClick="addr();">
				</td>
			</tr>
		</table>
		</form>
	</body>
	<script language="javascript">
		function addAsc()
		{
			var editSql = document.all.editSql;
			
	        var newTr = editSql.insertRow();
	        newTr.id = "tr"+rid++;

	        var newTd = newTr.insertCell();
	        
	        newTd.align="center";
	        
	        var tdStyle = newTd.getAttribute("style");
	        
	        if(tdStyle == "[object]")
			{ 
				tdStyle.setAttribute("cssText","font-family: '宋体'; font-size: 12px; line-height: 1.5em; background-color: #C4EBF4;"); 
		        newTd.setAttribute("style",tdStyle ); 
			}
			else
			{ 
				newTd.setAttribute("class","text3" ); 
			}
			
	        //设置列内容和属性
	        var ov = '<input id="ov'+ num + '" type="text" name="asc" value=""/>&nbsp;&nbsp;<select name="o'+ num +'"><option value="asc" selected>升序</option><option value="desc">降序</option></select><input type="radio" value="nulls last" name="nu'+ num +'"/>nulls last&nbsp;&nbsp;<input type="radio" value="nulls first" name="nu'+ num++ +'"/>nulls first';
	        newTd.innerHTML = ov;
		}
		
		function addDesc()
		{
			var editSql = document.all.editSql;
			
	        var newTr = editSql.insertRow();
	        newTr.id = "tr"+rid++;

	        var newTd = newTr.insertCell();
	        
	        newTd.align="center";
	        
	        var tdStyle = newTd.getAttribute("style");
	        
	        if(tdStyle == "[object]")
			{ 
				tdStyle.setAttribute("cssText","font-family: '宋体'; font-size: 12px; line-height: 1.5em; background-color: #C4EBF4;"); 
		        newTd.setAttribute("style",tdStyle ); 
			}
			else
			{ 
				newTd.setAttribute("class","text3" ); 
			}
			
	        //设置列内容和属性
	        var ov = '<input id="ov'+ num + '" type="text" name="desc" value=""/>&nbsp;&nbsp;<select name="o'+ num +'"><option value="asc">升序</option><option value="desc" selected>降序</option></select><input type="radio" value="nulls last" name="nu'+ num +'"/>nulls last&nbsp;&nbsp;<input type="radio" value="nulls first" name="nu'+ num++ +'"/>nulls first';
	        newTd.innerHTML = ov;
		}
		
		function dell()
		{
			num--;
			var temp = "tr"+(--rid);
			var poss=document.getElementById(temp); 
			var editSql = document.all.editSql;
			editSql.deleteRow(poss.rowIndex); 
		}
		
		function addr()
		{
			var reg = '!@#$%^&*！@#￥%……&*';
			var temp = '';
			
			for(var i=1;i<num;i++)
			{
				var id = 'ov'+i;
				var oid = 'o'+i;
				var nuid = 'nu'+i;
				var nutemp = '';
				
				var ovalue = document.getElementById(oid)
				var o = document.getElementById(id);
				var nu = document.getElementsByName(nuid);
				
				if(o.value == '')
				{
					alert("请输入内容");
					document.getElementById(id).focus();
					return false;
				}
									
				for (t = 0;t<=(o.value.length-1);t++)
				{
					if (reg.indexOf(o.value.charAt(t))>=0)
					{
						alert("排序sql不可以存在以下@#！@#￥%……&*特殊字符");
						document.getElementById(id).focus();
						return false;
					}
				}
				
				if(nu!=null)
				{
			        for(var h=0;h<nu.length;h++)
			        {
			            if(nu[h].checked)
			            {
			                nutemp= " " + nu[h].value;
			                break;
			            }
			        }
			    }
				temp = temp + o.value + " " + ovalue.value + nutemp +",";
			}
			temp = temp.substring(0,temp.lastIndexOf(","));
			window.returnValue=temp;
		    window.close();
		}
	</script>
</html>
