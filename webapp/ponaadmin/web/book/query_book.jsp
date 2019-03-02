<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<%
String tmpStyle = "text5";
String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String bookId = Validator.filter(request.getParameter("bookId")==null?"":request.getParameter("bookId"));
String bookName = Validator.filter(request.getParameter("bookName")==null?"":request.getParameter("bookName"));
String authorName = Validator.filter(request.getParameter("authorName")==null?"":request.getParameter("authorName"));
String typeName = Validator.filter(request.getParameter("typeName")==null?"":request.getParameter("typeName"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>ͼ����Ӳ�ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<base target="_self" />
</head>
<script language="javascript">
	function add()
	{
		var temp = '';
		
		if(refForm.dealRef.length == undefined)
		{
			if(refForm.dealRef.checked == true)
			{
				temp = temp + refForm.dealRef.value + ';';
			}
		}
		else
		{
			for(var i=0;i<refForm.dealRef.length; i++)
			{
				if(refForm.dealRef[i].checked==true)
				{
					temp = temp + refForm.dealRef[i].value + ';';
				}
			}
		}
		temp = temp.substring(0,temp.lastIndexOf(";"));
        window.returnValue=temp;
	    window.close();
	}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="queryReference.do" method="post" >
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td colspan="4" align="center" class="title1">
      ͼ���ѯ
      <input type="hidden" name="categoryId" value="<%=categoryId%>">
      <input type="hidden" name="perType" value="queryBook">
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
    <td width="30%" class="text4" ><input type="text" name="authorName" value="<%=authorName%>"></td>
    <td width="18%" align="right" class="text3">ͼ����ࣺ
    </td>
    <td width="30%" class="text4" ><input type="text" name="typeName" value="<%=typeName%>"></td>
 </tr>
  <tr>
    <td colspan="4" align="center" class="text5">
	    <input name="Submit" type="submit" class="input1" value="��ѯ" >
	    <input name="reset" type="reset" class="input1" value="����">
    </td>
  </tr>
</table>
<br>
<logic:equal name="isFirst" value="1">
	<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
	      <tr bgcolor="#B8E2FC">
	          <td colspan="5" align="center"><font color="#ff0000">�������ѯ������</font></td>
	      </tr>
	</table>
</logic:equal>


<logic:equal name="isFirst" value="">
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
		    <input type="checkbox" value='' name="allSelect" onclick="selectAllCB(refForm,'dealRef',this.checked,false);"/>
		  	ȫѡ
		  	</td>
		    <td width="10%" align="center" class="title1">ͼ����</td>     
		    <td width="20%" align="center" class="title1">ͼ������</td>
		    <td width="15%" align="center" class="title1">ͼ������</td>
		    <td width="10%" align="center" class="title1">ͼ�����</td>
		    <td width="10%" align="center" class="title1">ͼ�����</td>
		    <td width="30%" align="center" class="title1">ͼ�����</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.book.vo.BookRefVO">
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
						<input type="checkbox" name="dealRef" value="<bean:write name="vo" property="bookId"/>">
					</td>
					<td align="center">
		      			<bean:write name="vo" property="bookId"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="bookName"/>
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="authorName"/>
					</td>
		      		<td align="center">
		      			<bean:write name="vo" property="typeName"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="fee"/>��
		      		</td>
		      		<td align="left">
						<bean:write name="vo" property="shortRecommend"/>
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
		        params.put("perType",request.getParameter("perType"));
		        %>
		        <pager:pager name="PageResult" form="refForm" action="/web/book/queryReference.do" params="<%=params%>">
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
</logic:equal>

<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
  <tr bgcolor="#B8E2FC">
    <td align="center" >
		<input name="buttonAdd" type="button" class="input1"  onClick="add();" value="���">
    </td>
  </tr>
</table>
</logic:notEmpty>
</form>
</body>
</html>
