<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�񵥺�����Ԫ���ݲ�ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function remove()
{
	refForm.perType.value="removeApp";
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

function tagImport()
{
    window.location.href="import.jsp";
}

function tagAllExport()
	{
		if(confirm("��ȷ��Ҫ���е���������"))
		{
		    window.location.href="androidBlackListExport.do";
		    //refForm.action="tagExport.do";
			//refForm.submit();
			//refForm.action='tagMgr.do';
		}
		else
		{
			return false;	
		}
	}

</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="androidBlackList.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	               �񵥺�������ѯ
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="5%" align="right" class="text3">�������ƣ�
	    </td>
	    <td width="5%" class="text4"><input type="text" name="name" value="<%=name%>"></td>
	    <td width="5%" align="right" class="text3">����ID��
	    </td>
	    <td width="5%" class="text4"><input type="text" name="contentId" value="<%=contentId%>"></td>
	  </tr> 
	  
	  <tr>
	    <td colspan="4" align="center" class="text5">
		    <input name="Submit" type="submit" class="input1" value="��ѯ" >
		    <input name="reset" type="reset" class="input1" value="����">
	    </td>
	  </tr>
	</table>
	
	<br>

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
		     <b>�񵥺�����Ԫ�����б�</b>
		    </td>
		  </tr>
		</table>
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td width="5%" align="center" class="title1">
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td>
		    <td width="10%" align="center" class="title1">����ID</td>     
		    <td width="20%" align="center" class="title1">��������</td>
		    <td width="10%" align="center" class="title1">�޸�ʱ��</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.category.blacklist.vo.AndroidBlackListVO">
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
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="contentID"/>">
					</td>
					<td align="center">
		      			<bean:write name="vo" property="contentID"/>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="name"/>
		      		</td>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="opDate"/>
		      		</td>
				</tr>
		  </logic:iterate>
          </table>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >
		    	<%
		        java.util.HashMap params = new java.util.HashMap();
		        params.put("contentId",request.getParameter("contentId"));
		        params.put("name",request.getParameter("name"));
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/androidBlackList/androidBlackList.do" params="<%=params%>">
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
			<input name="buttonAdd" type="button" class="input1" onclick="tagImport()" value="��������">
			<input name="buttonExport" type="button" class="input1" onclick="tagAllExport()" value="ȫ������">
	    </td>
	  </tr>
	</table>
</form>
</body>
</html>

