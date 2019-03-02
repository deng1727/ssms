<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%@ taglib uri="pager" prefix="pager" %>
<%
String isQuery = (String)request.getAttribute("isQuery");
String size = (String)request.getAttribute("size");
String id = (String)request.getAttribute("id")==null?"":(String)request.getAttribute("id");
String name = (String)request.getAttribute("name")==null?"":(String)request.getAttribute("name");
String spName = (String)request.getAttribute("spName")==null?"":(String)request.getAttribute("spName");
String keywordsDesc = (String)request.getAttribute("keywordsDesc")==null?"":(String)request.getAttribute("keywordsDesc");
String contentId = (String)request.getAttribute("contentId")==null?"":(String)request.getAttribute("contentId");
String contentTag = (String)request.getAttribute("contentTag")==null?"":(String)request.getAttribute("contentTag");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>���ݲ�ѯ</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<base target="_self" />
	</head>
	<script language="javascript">
	function selectContent()
	{
		var name = ContentForm.name.value;
		var id = ContentForm.id.value;
		var spName = ContentForm.spName.value;
		var keywordsDesc = ContentForm.keywordsDesc.value;
		var contentId = ContentForm.contentId.value;
		var contentTag = ContentForm.contentTag.value;
		
		
		var reg = /^[\w]+$/;
		
		if(trim(id)!='')
	    {
			if(!reg.test(id))
		    {
				alert("���ݱ���ֻ�ܰ���Ӣ����ĸ�����֣����������룡");
				ContentForm.id.focus();
				ContentForm.id.select();
				return false;
		    }
		}
		
		if(trim(contentId)!='')
	    {
			if(!reg.test(contentId))
		    {
				alert("��������ֻ�ܰ���Ӣ����ĸ�����֣����������룡");
				ContentForm.contentId.focus();
				ContentForm.contentId.select();
				return false;
		    }
		}
		
		if(trim(contentTag)!='')
	    {
			if(!reg.test(contentTag))
		    {
				alert("��������ֻ�ܰ���Ӣ����ĸ�����֣����������룡");
				ContentForm.contentTag.focus();
				ContentForm.contentTag.select();
				return false;
		    }
		}
		
		var reg = /^[\w\-\u4e00-\u9fa5]+$/g

		if(trim(spName)!='')
	    {
		    if(!reg.test(spName))
		    {
				alert("�ṩ��ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߣ����������룡");
				ContentForm.spName.focus();
				ContentForm.spName.select();
				return false;
		    }
	    }
	    
	    if(trim(keywordsDesc)!='')
	    {
		    if(!reg.test(keywordsDesc))
		    {
				alert("���ݱ�ǩֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߣ����������룡");
				ContentForm.keywordsDesc.focus();
				ContentForm.keywordsDesc.select();
				return false;
		    }
	    }


		ContentForm.action="contentList.do";
		ContentForm.submit();
	}
	function fReturn()
	{
		var obj = form.addContent;
		var temp = '';
		
		for(var i=0;i<obj.length; i++)
		{
			if(obj[i].checked==true)
			{
				temp = temp + obj[i].value + ',';
			}
		}
		temp = temp.substring(0,temp.lastIndexOf(","));
        window.returnValue=temp;
	    window.close();
	}
	function winback()
	{
		window.close();
	}
	</script>
	
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		  <tr>
		    <td align="center" bgcolor="#BFE8FF">���ݲ�ѯ</td>
		  </tr>
		</table>
		<form name="ContentForm" action="" method="post">
		<input type="hidden" name="actionType" value="queryContent" />
		<table width="95%"  border="0" align="center" cellspacing="3" bgcolor="#FFFFFF">
		  <tr>
		    <td width="20%" align="right" class="text3">
		    	����id:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="id" value="<%=id %>" />
		    </td>
		    <td width="20%" align="right" class="text3">
		    	��������:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="name" value="<%=name %>" />
		    </td>
		  </tr>
		  <tr>
		    <td width="20%" align="right" class="text3">
		    	�ṩ��:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="spName" value="<%=spName %>" />
		    </td>
		    <td width="20%" align="right" class="text3">
		    	���ݱ�ǩ:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="keywordsDesc" value="<%=keywordsDesc %>" />
		    </td>
		  </tr>
		  <tr>
		    <td width="20%" align="right" class="text3">
		    	��������:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="contentId" value="<%=contentId %>" />
		    </td>
		    <td width="20%" align="right" class="text3">
		    	��������:
		    </td>
		    <td width="30%" class="text4">
		    	<input type="text" name="contentTag" value="<%=contentTag %>" />
		    </td>
		  </tr>
		  <tr>
		    <td colspan="4" align="center" class="text3">
		    	<input type="button" value="��ѯ" onclick="selectContent();">
			</td>
		  </tr>
		</table>
		</form>
		<br>
		<br>
		
		<%
		if(isQuery==null || "".equals(isQuery))
		{
		%>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
	      <tr bgcolor="#B8E2FC">
	          <td colspan="5" align="center"><font color="#ff0000">�������ѯ����</font></td>
	      </tr>
		</table>
		<%
		}
		else
		{
		    if(size==null || "0".equals(size))
		    {
		%>
			<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
			      <tr bgcolor="#B8E2FC">
			          <td colspan="5" align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
			      </tr>
			</table>
		<%
		    }
		    else
		    {
		        %>
		        <table width="95%"  border="0" align="center"  cellspacing="1" bgcolor="#FFFFFF">
		        	<form name="form" action="" method="post">
		        	<input type="hidden" name="addContent"/>
					<tr bgcolor="#B8E2FC">
					    <td width="5%" align="center" class="title1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(form,'addContent',this.checked,false);" />ȫѡ</td>
						<td width="10%" align="center" class="title1">����ID</td>
					    <td width="20%" align="center" class="title1">����</td>
					    <td width="15%" align="center" class="title1">�ṩ��</td>
					    <td width="20%" align="center" class="title1">���ݱ�ǩ</td>
					    <td width="10%" align="center" class="title1">��������</td>
					    <td width="10%" align="center" class="title1">����ʱ��</td>
					</tr>
					<%String tmpStyle = "text5";%>
					<logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.dotcard.gcontent.GContent">
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
						    <td align="center">
						    	<input type="checkbox" name="addContent" value="<bean:write name="vo" property="id"/>">
						    </td>
						    <td align="center">
								<bean:write name="vo" property="id"/>
						    </td>
						    <td align="center" style="word-break:break-all;">
						    	<bean:write name="vo" property="name"/>
						    </td>
						    <td align="center" style="word-break:break-all;">
						    	<bean:write name="vo" property="spName"/>
						    </td>
						    <td align="center" style="word-break:break-all;">
						    	<bean:write name="vo" property="keywordsDesc"/>
						    </td>
						    <td align="center">
						    	<bean:write name="vo" property="typeDesc"/>
						    </td>
	    					<td align="center">
	    						<bean:write name="vo" property="marketDate"/>
	    					</td>
						</tr>
					</logic:iterate>
					</form>
				</table>
				<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
				  <tr bgcolor="#B8E2FC">
				    <td align="right" >
				    	<%
				        java.util.HashMap params = new java.util.HashMap();
				        params.put("id",request.getAttribute("id"));
				        params.put("name",request.getAttribute("name"));
				        params.put("spName",request.getAttribute("spName"));
				        params.put("keywordsDesc",request.getAttribute("keywordsDesc"));
				        params.put("contentId",request.getAttribute("contentId"));
				        params.put("contentTag",request.getAttribute("contentTag"));				        
				        params.put("actionType",request.getAttribute("actionType"));
				        %>
				        <pager:pager name="PageResult" action="/web/intervenor/contentList.do" params="<%=params%>">
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
				<br>
				<br>
				<table width="95%" border="0" cellpadding="1" cellspacing="2" align="center">
					<tr bgcolor="#B8E2FC">
						<td align="center">
							<input name="button" type="button" value="���" onClick="fReturn();"/>
							<input name="button" type="button" value="����" onClick="winback();"/>
						</td>
					</tr>
				</table>
		        <%
		    }
		}
		%>
	</body>
</html>
