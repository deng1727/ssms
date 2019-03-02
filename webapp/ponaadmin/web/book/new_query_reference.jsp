<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
String tmpStyle = "text5";
String categoryId = request.getParameter("categoryId")==null?"":request.getParameter("categoryId");
String bookId = request.getParameter("bookId")==null?"":request.getParameter("bookId");
String bookName = request.getParameter("bookName")==null?"":request.getParameter("bookName");
String authorName = request.getParameter("authorName")==null?"":request.getParameter("authorName");
String typeName = request.getParameter("typeName")==null?"":request.getParameter("typeName");
String chargeType = request.getParameter("chargeType")==null?"":request.getParameter("chargeType");
Map<String,Object> map = (Map)request.getAttribute("categoryContent");
String approvalStatus = (String)request.getAttribute("approvalStatus");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ͼ����Ʒ����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
function removeItem()
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
		alert("��ѡ��Ҫ�Ƴ�Ŀ�꣡");
		return;
	}
	refForm.submit();
}

function add()
{
	refForm.perType.value="add";
	var returnv=window.showModalDialog("queryReference.do?perType=queryRead&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ");
	if(returnv != undefined && returnv != '')
	{
		refForm.addBookId.value=returnv;
		refForm.submit();
	}  
}

function mod_click(taxis)
{
  if(taxis.value=="")
  { 
   alert("�������������");
   return false;
  }
  if(!JudgeReal2(taxis,"�������",9))
  {
	return false;
  }
	
  if((taxis.value < -9999)||(taxis.value >9999))
  {
     alert("�������ֻ����-9999��9999֮�䣡");
     return false; 
  }
  return true;

}

function SetSortID()
{
	refForm.perType.value="setSort";
	var temp = '';
	var i=0;
	var Taxis=document.getElementsByName("sortId");

	   //debugger��
	    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseread.vo.ReadReferenceVO">
	      sortid=<bean:write name="vo" property="sortNumber"/>;
	      if(!mod_click(Taxis[i]))
	      {
	        Taxis[i].select();
	        Taxis[i].focus();
	        return false;
	      }
	      if(sortid!=Taxis[i].value)
	      {
	        if(refForm.sortId.length == undefined)
	        {
	        	temp = refForm.sortId.id + "_" + refForm.sortId.value + ";";
	        }else{
	        	temp = temp + refForm.sortId[i].id + "_" + refForm.sortId[i].value + ";";
	        }	
	      }

	      i++;
	     </logic:iterate>
	if(temp=="") {
	   alert("���޸�����һ�������");
	}else{   	 
	   refForm.setSortId.value = temp;
	   refForm.submit();
	}
}

function importData(filename,type)
{
	form1.perType.value="importData";
	
	var filePath = form1.dataFile.value;
	
	if(filePath == "" || filePath == null || filePath == "null")
	{
		alert("��ѡ��Ҫ�������ݵ��ļ���");
		return false;
    }
    
    if(!isFileType(filePath,"xls")&&!isFileType(filePath,"xlsx"))
    {
      alert("��ѡ��xls��xlsx��ʽ�������ļ�!");
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

   function into()
   {
		var chargeType = "<%=chargeType%>";
		var select_chargeType = form.chargeType;
		
		for(var i=0; i<select_chargeType.length; i++)
		{
			if(select_chargeType.options[i].value==chargeType)
			{
				select_chargeType.options[i].selected=true;
				break;
			}
		}
	}
	function exportAllData(genLogo)
	{
		if(confirm("��ȷ��Ҫ���е���������"))
		{
		
			refForm.perTypeFlag.value='allExport';
			refForm.submit();
			refForm.perTypeFlag.value='query';
		}
		else
		{
			return false;	
		}
	}
	
	function exportData(genLogo)
	{
	
		if(confirm("��ȷ��Ҫ���е���������"))
		{		
		
      	    refForm.perTypeFlag.value='export';      	    
			refForm.submit();
			refForm.perTypeFlag.value='query';
		}
		else
		{
		
			return false;	
		}
	}	
	
	function submitApproval(){
		 checkItem();
		  var bool = true;
			var dealContents = document.getElementsByName("dealRef");
			for(var i = 0;i < dealContents.length;i++){
				if(dealContents[i].checked  == true){
					bool = false;
				}
			}
			
			if(bool){
				alert("��ѡ����Ҫ����������");
				return;
			}
		  refForm.approval.value="part";
		  refForm.action="<%=request.getContextPath()%>/web/baseRead/categoryApproval.do?method=approval";
		  refForm.submit();
	  }
	  
	  function submitAllApproval(){
		  refForm.approval.value="all";
		  refForm.action="<%=request.getContextPath()%>/web/baseRead/categoryApproval.do?method=approval";
		  refForm.submit();
	  }	  
	  function selectAll(aForm,checkFieldName,isCheck)
	  {
	    for(var i=0;i<aForm.length;i++)
	    {
	      if(aForm.elements[i].type=="checkbox")
	      {
	        if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
	        {
	        	if(!aForm.elements[i].disabled)
	               aForm.elements[i].checked = isCheck;
	        }	
	      }
	    }
	  }
	  
	 
		function checkItem() {
			for (var i = 0; i < refForm.length; i++) {
				if (refForm.elements[i].type == "checkbox") {
					var itemValue = refForm.elements[i].value;
					var strs = itemValue.split("#");
					if(!(strs.length == 2 && (strs[1] == '3' || strs[1] == '0'))){
						refForm.elements[i].checked = false;
					}
				}
			}
		}
		
</script>
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" onload="into();">
<form name="form" action="queryReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ʒ��ѯ
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">ͼ���ţ�
    </td>
    <td width="30%" class="text4"><input type="text" name="bookId" value="<%=bookId%>"></td>
    <td width="18%" align="right" class="text3">ͼ�����ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="bookName" value="<%=bookName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ͼ�����ߣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="authorName" value="<%=authorName%>"></td>
    <td width="18%" align="right" class="text3">ͼ����ࣺ
    </td>
    <td width="30%" class="text4" ><input type="text" name="typeName" value="<%=typeName%>"></td>
 </tr>
 <tr>
    <td width="18%" align="right" class="text3">�������ͣ�
    </td>
    <td width="30%" class="text4">
    	<select name="chargeType">
			<option value="">Ĭ��ȫѡ</option>
			<option value="0">���</option>
			<option value="1">�����շ�</option>
			<option value="2">�����շ�</option>    		
			<option value="3">�����շ�</option>
    	</select>
	</td>
    <td width="18%" align="right" class="text3">����״̬��
    </td>
    <td width="30%" class="text4">
     <SELECT name=approvalStatus> 
                           <option value="-1" <logic:equal value="-1" name="approvalStatus"> selected</logic:equal>>
								ȫ��
							</option>
                           <option value="2" <logic:equal value="2" name="approvalStatus"> selected</logic:equal>>
								������
							</option>
							<option value="0" <logic:equal value="0" name="approvalStatus"> selected</logic:equal>>
								�༭
							</option>
							<option value="1" <logic:equal value="1" name="approvalStatus"> selected</logic:equal>>
								�ѷ���
							</option>
							<option value="3" <logic:equal value="3" name="approvalStatus"> selected</logic:equal>>
								��ͨ��
							</option>
							<option value="0,3" <logic:equal value="0,3" name="approvalStatus"> selected</logic:equal>>
								�༭��������ͨ��
							</option>
    </select>
    </td>
  </tr>
  
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="��ѯ" >
	    <input name="reset" type="reset" class="input1" value="����">
    </td>
  </tr>
</table>
</form>
<br>
<form name="refForm" action="queryReference.do" method="post" >
<input type="hidden" name="categoryId" value="<%=categoryId%>">
<input type="hidden" name="perTypeFlag" value="">
<input type="hidden" name="perType" value="export">
<input type="hidden" name="addBookId" value="">
<input type="hidden" name="setSortId" value="">
<input type="hidden" name="approval" >
			<input type="hidden" name="categoryId" value="<%=categoryId%>">
			<input type="hidden" name="bookId" value="<%=bookId%>">
			<input type="hidden" name="bookName" value="<%=bookName%>">	
			<input type="hidden" name="authorName" value="<%=authorName%>">
			<input type="hidden" name="typeName" value="<%=typeName%>">
			<input type="hidden" name="chargeType" value="<%=chargeType%>">	
<logic:empty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
      <tr bgcolor="#B8E2FC">
          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
</table>
</logic:empty>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="3" class="text4">
  <tr class="text5">
    <td align="center">
     <b>��Ʒ�б�</b>
    </td>
  </tr>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">
    <input type="checkbox" value='' name="allSelect" onclick="selectAll(refForm,'dealRef',this.checked);"/>
	  	ȫѡ
	</td>
    <td width="8%" align="center" class="title1">ͼ����</td>     
    <td width="15%" align="center" class="title1">ͼ������</td>
    <td width="6%" align="center" class="title1">ͼ������</td>
    <td width="6%" align="center" class="title1">ͼ�����</td>  
    <td width="8%" align="center" class="title1">����</td>
    <td width="10%" align="center" class="title1">�Ʒ�����</td>
    <td width="7%" align="center" class="title1">ͼ�����</td>
    <td width="10%" align="center" class="title1">������ʱ��</td>  
    <td width="5%" align="center" class="title1">�������</td>
    <td width="10%" align="center" class="title1">����״̬</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseread.vo.ReadReferenceVO">
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
			<td align="center" style="word-break:break-all;">
				<input type="checkbox"  <% if("2".equals(vo.getVerify_status())){ %> disabled="disabled" <%} %> name="dealRef" value="<bean:write name="vo" property="bookId"/>#<bean:write name="vo" property="verify_status"/>">
			</td>
			<td align="center"><a href="queryReference.do?perType=showRead&bookId=<bean:write name="vo" property="bookId"/>">
      			<bean:write name="vo" property="bookId"/></a>
      		</td>
      		<td align="center"><a href="queryReference.do?perType=showRead&bookId=<bean:write name="vo" property="bookId"/>">
      			<bean:write name="vo" property="bookName"/></a>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="authorName"/>
			</td>
			<td align="left">
				<bean:write name="vo" property="typeName"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="rankValue"/>
      		</td>
      		<td align="center">
      			<logic:equal name="vo" property="chargeType" value="0">���</logic:equal>
		      	<logic:equal name="vo" property="chargeType" value="1">�����շ�</logic:equal>
		      	<logic:equal name="vo" property="chargeType" value="2">�����շ�</logic:equal>
		      	<logic:equal name="vo" property="chargeType" value="3">�����շ�</logic:equal>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="fee"/>��
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="lastUpTime_Y"/>��<bean:write name="vo" property="lastUpTime_M"/>��<bean:write name="vo" property="lastUpTime_D"/>��
      		</td>
      		<td align="center">
      			<input id="<bean:write name="vo" property="bookId"/>" type="text" name="sortId" size="9" value="<bean:write name="vo" property="sortNumber"/>">
      		</td>
      		<td align="center" style="word-break:break-all">
	    <%if("0".equals(vo.getVerify_status())){ %>
	             �༭
	   <%}else if("1".equals(vo.getVerify_status())){ %>
	              �����ɹ�
	   <%}else if("2".equals(vo.getVerify_status())){ %>
	              ������
	   <%}else{ %>
	       ������ͨ��  
	   <%} %>
	   </td>
		</tr>
  </logic:iterate>
</table>
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="right" >
    	<%
        java.util.HashMap params = new java.util.HashMap();
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("bookId",bookId);
        params.put("bookName",bookName);
        params.put("authorName",authorName);
        params.put("typeName",typeName);
        params.put("chargeType",chargeType);
        params.put("perType",request.getParameter("perType"));
        params.put("approvalStatus",approvalStatus);
        %>
        <pager:pager name="PageResult" action="/web/baseRead/queryReference.do" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="1"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>

<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
    	<logic:notEmpty name="PageResult" property="pageInfo">
    		<input name="buttonDel" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %> onClick="removeItem();" value="�Ƴ�">
    		<input name="buttonSet" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %> onClick="SetSortID();" value="��������趨">
    		<input name="buttonImport" type="button" class="input1"
							onClick="exportData(this);" value="����">
											<input name="buttonImport" type="button" class="input1"
							onClick="exportAllData(this);" value="ȫ������">	
		</logic:notEmpty>
		<input name="buttonAdd" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %>  onClick="add();" value="���">
		<input name="buttonImport" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %>  onClick="importData();" value="��������">
		<logic:notEmpty name="PageResult" property="pageInfo">
		     <input name="buttonOperation" type="button" class="input1" <% if(!("0".equals(map.get("goods_status")) || "3".equals(map.get("goods_status")))){ %> disabled="disabled"<%} %>  onClick="submitApproval();" value="�����ύ����">
		     <input name="buttonOperation" type="button" class="input1" <% if(!("0".equals(map.get("goods_status")) || "3".equals(map.get("goods_status")))){ %> disabled="disabled"<%} %>  onClick="submitAllApproval();" value="ȫ���ύ����">
		</logic:notEmpty>
    </td>
  </tr>
</table>
</form>
<form action="importReference.do" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="perType" value="">
	<input type="hidden" name="categoryId" value="<%=categoryId%>">
	<table width="95%"  border="0" align="center" cellspacing="1">
		   <tr>
	    <td   align="left" colspan="2">
	        	<b>��������</b>
        <select name="addType">
            <option value="ALL">ȫ��</option>
	    	<option value="ADD">����</option>
	    </select>
	    <font color=red>&nbsp;&nbsp;<b><br/>�������ڸû�����������Ʒ�������EXCEL��2003�汾����2007�汾������Ϊ׼<br/>
ȫ�����¼�ԭ�иû�����ȫ����Ʒ�����EXCEL(2003�汾����2007�汾)ָ����Ʒ���û����� <b></font>
    </td>
	   </tr>
	  <tr>
	    <td   align="right" width="20%">
	    	ѡ��Ҫ�����excel�����ļ���
	    </td>
	    <td   align="left" width="80%"> 
			<input type="file" name="dataFile">(����һ�б�ʾ��ţ��������У��ڶ��б�ʾ����ID)
	    </td>
	   </tr>
	</table>
	<br/>
	<br/>
	<br/>
</form>
</body>
</html>
