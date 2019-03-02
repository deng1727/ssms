<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>营销体验门户管理平台</title>

<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script src="../../js/jquery.min.js" type="text/javascript"></script>
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript"
	src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    $(function () {
    
/*      var option = $('#catename option:selected').val();
     
     var title = $('#title').text();
     $('#extend').hide(); */
     
     
     
     
     $("#catename").click(function () {
        var option = $("#catename option:selected").val();    
        
        if (option == 1 ){
       		 $("#extend").hide();
       		 $("#title").text("文章ID：");
        }
        
        if (option == 2 ){
       		 $("#extend").hide();
       		  $("#title").text("页面ID："); 
        }
        if (option == 3 ){
       		 $("#extend").hide();
       		 $("#contentId").hide();
       		 $("#title").text("营销活动："); 
        }
        
        if (option == 0 ){
       		 $("#extend").show();
       		 $("#title").text("应用ID：");
        }
      });
    
    
      $("#add").click(function () {
        var $option = $("#select1 option:selected");  //获取选中的选项
        $option.appendTo("#select2");  //追加给对方
      });

      $("#remove").click(function () {
        var $option = $("#select2 option:selected");
        $option.appendTo("#select1");
        location.reload();
      });

		

       $("#addVersion").click(function () {
        var $option = $("#select11 option:selected");  //获取选中的选项
        $option.appendTo("#select22");  //追加给对方
      });

      $("#removeVersion").click(function () {
        var $option = $("#select22 option:selected");
        $option.appendTo("#select11");
        location.reload();
      });
      
      
      
       $("#addAll").click(function () {
        var $option = $("#select11 option");  //全选
        $option.appendTo("#select22 ");  
        $("#select22 option").prop("selected",true);
        
      });

      $("#removeAll").click(function () {    //全不选
        var $option = $("#select22 option");
        $option.appendTo("#select11 ");
        $("#select11 option").prop("selected",false);
      });
    });
</script>
<script language="JavaScript" type="text/javascript">

//   add by tungke 
function thischeckContent(field)
{
    var value = eval(field).value;
    //如果为空，则不做内容检验
    if( getLength(value) == 0) return true;
   // var reg　= /^[\w/!/@/#/$/%/^/&/(/)/{/}/【/】/＊/。/！/＠/＃/￥/％/……/＆/（/）/《/》/“/”/？/：/·\_\u4e00-\u9fa5]+$/g ; 
  //  return reg.test(value);
  var other = /^[\w\s\!\@\#\$\%\^\-\－\(\)\[\]\{\}\【\】\＊\。\！\＠\＃\￥\％\……\＆\（\）\《\》\“\”\？\：\．\·\_\u4e00-\u9fa5]+$/g ;
   return (other.test(value));
}

function checkForm(form)
{
	if($("#catename").val() != 3){
		if(!checkLength(form.contentId,"应用ID",-1,30)) return false;
	}
	if(!checkLength(form.title,"主标题",-1,100)) return false;
	if(!checkLength(form.subTitle,"副标题",-1,100)) return false;
	if(!checkLength(form.startTime,"开始时间",-1,30)) return false;
	if(!checkLength(form.endTime,"结束时间",-1,30)) return false;
	if(eval(form.startTime).value >= eval(form.endTime).value){
		alert("结束时间必须大于开始时间");
		return false;
	}
	return true;
  
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

	if(!isFileType(filePath,"jpg")&&!isFileType(filePath,"jpeg")&&!isFileType(filePath,"png")&&!isFileType(filePath,"bmp"))
    {
      alert("请选择jpg,jpeg,png,bmp格式的数据文件!");
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


function showFileUpload()
{
      document.getElementById('upload').style.display ='block';
}
function isFileType(filename,type)
{
   var pos = filename.lastIndexOf(".");

   if(pos<0) return false;

   var fileType = filename.substr(pos+1);

   if(fileType.toUpperCase()!=type.toUpperCase()) return false;

   return true;
}


function selectDevice(obj)
{
	if(obj.value==1)
	{
		document.getElementById('deviceCategoryText').style.display ='block';
	}
	else
	{
		document.getElementById('deviceCategoryText').style.display ='none';
	}
	
}

function selectContentId()
{
	var returnv=window.showModalDialog("<%=request.getContextPath()%>/web/channelUser/pushAdv.do?perType=content&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	if(returnv != undefined)
	{
		document.getElementById('contentId').value = returnv;
	}
}

function hasDeviceId(device)
{
	var obj = form1.devices;
	if(obj != undefined)
	{
		if(obj.length == undefined)
		{
			if(obj.value == device.id + ";" + device.name)
			{
				alert(obj.value + "已存在");
				return true;
			}
		}
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].value == device.id + ";" + device.name)
			{
				alert(obj[i].value + "已存在");
				return true;
			}
		}
	}
	return false;
}




function importData()
{
	importForm.perType.value="importData";
	
	var filePath = importForm.phone.value;
	
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
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1">新增推送</td>
		</tr>
	</table>
	<form name="form1" method="post" action="pushAdv.do?style=add"
		onSubmit="return checkForm(form1);" name="importForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="perType" value="save">

		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">


			<tr>
				<td width="25%" align="right" class="text3"><font
					color="#ff0000">(*)</font>推送类型：</td>
				<td width="75%" class="text4"><select name="catename" id="catename" >
						<option value="0"
							<logic:equal name="type" value="0">selected="selected"</logic:equal>>应用推送</option>
						<option value="1"
							<logic:equal name="type" value="1">selected="selected"</logic:equal>>媒体化文章推送</option>
						<option value="2"
							<logic:equal name="type" value="2">selected="selected"</logic:equal>>Deeplink页面推送</option>
						<option value="3"
							<logic:equal name="type" value="3">selected="selected"</logic:equal>>营销活动推送</option>
								
				</select></td>
			</tr>

			<tr>
				<td width="25%" align="right" class="text3" ><font
					color="#ff0000">(*)</font><a id="title">应用ID：</a></td>
				<td width="75%" class="text4"><input name="contentId"
					id="contentId" type="text" size="30"> <input type="button"
					value="选择应用" onclick="selectContentId();" id ="extend"/> <span id="platForm"></span>
				</td>
			</tr>

			<tr>
				<td width="25%" align="right" class="text3"><font
					color="#ff0000">(*)</font>主标题：</td>
				<td width="75%" class="text4"><input name="title" type="text"
					value="" size="100">
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">副标题：</td>
				<td class="text4"><input name="subTitle" type="text" value=""
					size="100">
				</td>
			</tr>


			<tr>
				<td align="right" class="text3">banner图：</td>
				<td class="text4"><input type="file" name="dataFile">
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">手机号码(xls文件)：</td>
				<td class="text4"><input type="file" name="phone">
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">URI：</td>
				<td class="text4"><input name="uri" type="text" value=""
					size="100">
				</td>
			</tr>


			<tr>
				<td align="right" class="text3">开始时间：</td>
				<td class="text4"><input name="startTime" class="Wdate"
					type="text" id="textbox2" style="width:170px"
					onFocus="new WdatePicker(this,'%Y-%M-%D %H:%m:%S',true)" value="" />
				</td>
			</tr>
			<tr>
				<td align="right" class="text3">结束时间：</td>
				<td class="text4"><input name="endTime" class="Wdate"
					type="text" id="textbox2" style="width:170px"
					onFocus="new WdatePicker(this,'%Y-%M-%D %H:%m:%S',true)" value="" />
				</td>
			</tr>
			
			<tr>
				<td align="right" class="text3">推荐品牌：</td>
				<td class="text4">
					<table>
						<tr>
							<td><select id="select1" multiple="multiple"
								style="width:300px;height:350px">
									<logic:iterate id="elements" name="brandMap">
										<optgroup label="<bean:write name="elements" property="key"/>">
											<logic:iterate id="element" name="elements" property="value">
												<option class="son" value="<bean:write name="element"/>">
													&nbsp&nbsp&nbsp
													<bean:write name="element" />
												</option>
											</logic:iterate>
										</optgroup>
									</logic:iterate>
							</select>
							</td>
							<td>
								<button id="add">>|</button> <br />
							<a style="color:red">第一行+</a> <br /> <a style="color:red">shift+</a>
								<br />
							<a style="color:red">最后一行</a><br />
							<a style="color:red">为全选</a> <br />

								<button id="remove">|< </button>
							</td>
							<td><select id="select2" name="reDevice" multiple="multiple"
								style="width:300px;height:350px">
							</select>
							</td>
						</tr>
					</table></td>
			</tr>
			
			
			
					<tr>
				<td align="right" class="text3">推送版本：</td>
				<td class="text4">
					<table>
						<tr>
							<td><select id="select11" multiple="multiple"
								style="width:300px;height:350px">
									<logic:iterate id="version" name="versions">
												<option class="version" value="<bean:write name="version"/>">
													<bean:write name="version" />
												</option>
									</logic:iterate>
							</select>
							</td>
							<td>
								<button id="addVersion">>|</button> <br />
								<button id="addAll">>></button> <br />
								<button id="removeAll"><<</button> <br />	
								<button id="removeVersion">|<</button>
							</td>
							<td><select id="select22" name="bversions" multiple="multiple"
								style="width:300px;height:350px">
							</select>
							</td>
						</tr>
					</table></td>
			</tr>
			
			
			
	

		</table>
	
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="text5"><input name="Submit"
					type="submit" class="input1" value="保存" onclick="importData();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
