<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));
String contentName = Validator.filter(request.getParameter("contentName")==null?"":request.getParameter("contentName"));
Map<String,Object> map = (Map)request.getAttribute("categoryContent");
String approvalStatus = (String)request.getAttribute("approvalStatus");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�˹���Ԥ����</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">
function remove()
{
	refForm.method.value="remove";
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

function add()
{
	refForm.method.value="add";
	var returnv=window.showModalDialog("referenceTree.do?method=queryItem&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
	if(returnv != undefined && returnv != '')
	{
		refForm.contentId.value=returnv;
		refForm.submit();
	}  
}

function queryChapter( cid)
{

	var returnv=window.showModalDialog("referenceTree.do?method=queryChapter&contentId="+cid, "new","width=1100,height=800 ")
	if(returnv != undefined && returnv != '')
	{
		refForm.contentId.value=returnv;
		refForm.submit();
	}  
}
function SetSortID()
{
	  refForm.method.value="setSort";
	  var temp = '';
	  var i=0;
	   
	   var Taxis=document.getElementsByName("sortId");

	   //debugger��
	    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.comic.vo.ReferenceVO">
	      sortid=<bean:write name="vo" property="sortId"/>;
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
	  if(temp=="")
	     {
	       alert("���޸�����һ�������");
	     }else
	    {
	      temp = temp.substring(0,temp.length-1);
	      refForm.setSortId.value = temp;
	      refForm.submit();
	  }
}
function importData()
{
	form1.method.value="importData";
	
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
		  refForm.action="<%=request.getContextPath()%>/web/comic/categoryApproval.do?method=approval";
		  refForm.submit();
	  }
	  
	  function submitAllApproval(){
		  refForm.approval.value="all";
		  refForm.action="<%=request.getContextPath()%>/web/comic/categoryApproval.do?method=approval";
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

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="form" action="referenceTree.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ��Ʒ��ѯ
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="method" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">����ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="contentId" value="<%=contentId%>"></td>
    <td width="18%" align="right" class="text3">�������ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="contentName" value="<%=contentName%>"></td>
  </tr>
  <tr>
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
<form name="refForm" action="referenceTree.do" method="post" >
<input type="hidden" name="categoryId" value="<%=categoryId%>">
<input type="hidden" name="method" value="export">
<input type="hidden" name="perTypeFlag" value="input">
<input type="hidden" name="approval" >

			<input type="hidden" name="categoryId" value="<%=categoryId%>">
			<input type="hidden" name="contentId" value="<%=contentId%>">
			<input type="hidden" name="contentName" value="<%=contentName%>">	
<input type="hidden" name="setSortId" value="">
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
    <td width="10%" align="center" class="title1">����id</td>     
    <td width="18%" align="center" class="title1">��������</td>
    <td width="12%" align="center" class="title1">���ʱ��</td>
    <td width="10%" align="center" class="title1">����</td>  
    <td width="5%" align="center" class="title1">�鿴�½�</td>  
    <td width="5%" align="center" class="title1">�Ż�</td> 
    <td width="5%" align="center" class="title1">�������</td>
    <td width="10%" align="center" class="title1">����״̬</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.comic.vo.ReferenceVO">
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
				<input type="checkbox" name="dealRef"  <% if("2".equals(vo.getVerify_status())){ %> disabled="disabled" <%} %> value="<bean:write name="vo" property="id"/>#<bean:write name="vo" property="verify_status"/>">
			</td>
			<td align="center">
      			<bean:write name="vo" property="contentId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="contentName"/>
      		</td>
      		<td align="left">
				<bean:write name="vo" property="flowTime"/>
			</td>
      		<td align="center">
      			<bean:write name="vo" property="type"/>
      		</td>
      		<td align="center">
      			<a target="_blank" href="referenceTree.do?method=queryChapter&contentId=<bean:write name='vo' property='contentId' />"/>�鿴�½�</a>
      		</td>
      		<td align="center">
      				<logic:greaterThan name="vo" property="portal" value="3">
		      			δ֪
		      		</logic:greaterThan>	
		      		
		      		<logic:lessThan name="vo" property="portal" value="1">
		      			δ֪
		      		</logic:lessThan>	 
      		   		<logic:equal name="vo" property="portal" value="1">
		      			�ͻ���
		      		</logic:equal>
		      		<logic:equal name="vo" property="portal" value="2">
		      			WAP�Ż�
		      		</logic:equal>
		      		<logic:equal name="vo" property="portal" value="3">
		      			����
		      		</logic:equal>
      		</td>
      		<td align="center">
      			<input id="<bean:write name="vo" property="id"/>" type="text" name="sortId" size="9" value="<bean:write name="vo" property="sortId"/>">
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
        HashMap params = new HashMap();
    	params.put("method","query");
        params.put("categoryId",request.getParameter("categoryId"));
        params.put("contentId",contentId);
        params.put("contentName",contentName);
        params.put("approvalStatus",approvalStatus);
        %>
        <pager:pager name="PageResult" action="/web/comic/referenceTree.do" params="<%=params%>">
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
    		<input name="buttonDel" type="button" class="input1" <% if(map.get("goods_status") != null && "2".equals(map.get("goods_status"))){ %> disabled="disabled"<%} %> onClick="remove();" value="�Ƴ�">
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
	<input type="hidden" name="method" value="importData">
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
