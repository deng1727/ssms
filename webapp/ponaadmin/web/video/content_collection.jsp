<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
String collectionId = Validator.filter(request.getParameter("collectionId")==null?"":request.getParameter("collectionId"));
String parentNodeId = Validator.filter(request.getParameter("parentNodeId")==null?"":request.getParameter("parentNodeId"));
String nodeName = Validator.filter(request.getParameter("nodeName")==null?"":request.getParameter("nodeName"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>��Ƶ�����ڵ����</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript">	
		
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
		
			refForm.perType.value='allExport';
			refForm.submit();
			refForm.perType.value='query';
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
		
      	    refForm.perType.value='export';      	    
			refForm.submit();
			refForm.perType.value='query';
		}
		else
		{
		
			return false;	
		}
	}
	
	function updateData(){
		if(confirm("��ȷ��Ҫ���и��²�����"))
		{		
		
			form1.method.value='update';      	    
			form1.submit();
		}
		else
		{
		
			return false;	
		}
		
	}
		
		</script>
	</head>

	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="form" action="collection.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      �����ڵ��ѯ
      <input type="hidden" name="perType" value="query">
    </td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">�ڵ�ID��
    <input type="hidden" name="method" value="query">
    </td>
    <td width="30%" class="text4"><input type="text" name="collectionId" value="<%=collectionId%>"></td>
    <td width="18%" align="right" class="text3">�ڵ����ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="nodeName" value="<%=nodeName%>"></td>
  </tr>
  <tr>
    <td width="18%" align="right" class="text3">���ڵ�ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="parentNodeId" value="<%=parentNodeId%>"></td>
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
		  <form name="refForm" action="collection.do" method="post">
			<input type="hidden" name="method" value="export">
			<input type="hidden" name="perType" value="input">
			
			<input type="hidden" name="collectionId" value="<%=collectionId%>">
			<input type="hidden" name="parentNodeId" value="<%=parentNodeId%>">
			<input type="hidden" name="nodeName" value="<%=nodeName%>">		
			<input type="hidden" name="contentId" value="">
			<input type="hidden" name="setSortId" value="">
			<logic:empty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1" bgcolor="#FFFFFF">
					<tr bgcolor="#B8E2FC">
						<td colspan="5" align="center">
							<font color="#ff0000">û���ҵ��κμ�¼��</font>
						</td>
					</tr>
				</table>
			</logic:empty>
			<logic:notEmpty name="PageResult" property="pageInfo">
				<table width="95%" border="0" align="center" cellspacing="3"
					class="text4">
					<tr class="text5">
						<td align="center">
							<b>�����ڵ��б�</b>
						</td>
					</tr>
				</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="15%" align="center" class="title1">�ڵ�ID</td> 
    <td width="20%" align="center" class="title1">���ڵ�ID</td>
    <td width="15%" align="center" class="title1">�ڵ�����</td>
    <td width="20%" align="center" class="title1">�Ƿ���ʾ</td>    
    <td width="15%" align="center" class="title1">������</td>
  </tr>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.baseVideo.vo.CollectionResultVO">
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
				<bean:write name="vo" property="collectionId"/>
			</td>
			<td align="center">
      			<bean:write name="vo" property="parentNodeId"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="nodeName"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="isShow"/>
      		</td>
      		<td align="center">
      			<bean:write name="vo" property="reName"/>
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
        params.put("collectionId",collectionId);
        params.put("parentNodeId",parentNodeId);
        params.put("nodeName",nodeName);
        params.put("method",request.getParameter("method"));
        
        %>
							<pager:pager name="PageResult"
								action="/web/baseVideo/collection.do" params="<%=params%>">
								<pager:firstPage label="��ҳ" />&nbsp;
					            <pager:previousPage label="ǰҳ" />&nbsp;
					            <pager:nextPage label="��ҳ" />&nbsp;
					            <pager:lastPage label="βҳ" />&nbsp;
					            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
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

						<input name="buttonImport" type="button" class="input1"
							onClick="exportData(this);" value="����">
								<input name="buttonImport" type="button" class="input1"
							onClick="exportAllData(this);" value="ȫ������">
										</logic:notEmpty>
							
													<input name="buttonImport" type="button" class="input1"
							onClick="importData();" value="��������">										
							
					</td>
				</tr>
			</table>
		</form>
		<form action="importCollection.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method" value="importData">
			<table width="95%" border="0" align="center" cellspacing="1">
				<tr>
					<td align="left" colspan="2">
						<b>��������</b>
						<select name="addType">
						<!-- 
							<option value="ALL">
								ȫ��
							</option>
							 -->
							<option value="ADD">
								����
							</option>
						</select>
						<font color=red>&nbsp;&nbsp;<b>
							<br />�������ڸ���Ƶ���ݼ����������ݣ��������EXCEL��2003�汾����2007�汾��ָ�����ݵ������ݼ�
							<!--<br />ȫ����ɾ��ԭ�иú�������ȫ�����ݣ�ȫ�����EXCEL(2003�汾����2007�汾)ָ�����ݵ��ú����� <b>-->
						</font>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%">
						ѡ��Ҫ�����excel�����ļ���
					</td>
					<td align="left" width="80%">
						<input type="file" name="dataFile">
						˵����������ݽڵ�ID�����ڵ�ID�����Ƿ���ʾ���ڵ�����(����һ�б�ʾ�ڵ�ID���ڶ��б�ʾ���ڵ�ID�������б�ʾ�Ƿ���ʾ���Ǳ�ʾ��ʾ�����ʾ����ʾ�������б�ʾ������)
					</td>
				</tr>
				<tr><td align="right" width="40">
				    <input name="buttonUpdate" type="button" class="input1" onClick="updateData();" value="����">	
				</td></tr>
			</table>
			<br />
			<br />
			<br />
		</form>
	</body>
</html>
