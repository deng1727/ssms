<%@ page contentType="text/html; charset=gbk" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@ page import="com.aspire.common.Validator" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Ӧ�������б��ѯ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="javascript" type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
</head>
<script language="javascript">
function removePush(id,contentId)
{

  if(confirm("ȷ���������б����Ƴ������ͣ�"))
  {
	  pushAdvForm.perType.value = "remove";
	  pushAdvForm.id.value = id;
	  pushAdvForm.contentId.value = contentId;
	  pushAdvForm.submit();
  }
}

function add()
{
  window.location.href = "pushAdv.do?perType=add";
}
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<form name="refForm" action="pushAdv.do" method="post" >
	<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
	    <td colspan="4" align="center" class="title1">
	      Ӧ�������б��ѯ
	      <input type="hidden" name="perType" value="query">
	    </td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">Ӧ��ID��
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentId" value="<bean:write name="contentId"/>"></td>
	    <td width="18%" align="right" class="text3">Ӧ�����ƣ�
	    </td>
	    <td width="30%" class="text4"><input type="text" name="contentName" value="<bean:write name="contentName"/>"></td>
	  </tr>
	  <tr>
	    <td width="18%" align="right" class="text3">��ʼʱ�䣺
	    </td>
	    <td width="30%" class="text4"><input type="text" class="Wdate" name="startTime" value="<bean:write name="startTime"/>" style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly></td>
	    <td width="18%" align="right" class="text3">����ʱ�䣺
	    </td>
	    <td width="30%" class="text4"><input type="text" class="Wdate" name="endTime" value="<bean:write name="endTime"/>" style="width:100px" onFocus="new WdatePicker(this,'%Y-%M-%D',true)"  readonly></td>
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
		     <b>Ӧ�������б�</b>
		    </td>
		  </tr>
		</table>
		<form name="pushAdvForm" action="pushAdv.do" method="post">
		<input type="hidden" name="perType" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="contentId" value="">
		<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
		  <tr>
		    <td width="10%" align="center" class="title1">Ӧ��ID</td>     
		    <td width="20%" align="center" class="title1">Ӧ������</td>
		    <td width="15%" align="center" class="title1">������</td>
		    <td width="10%" align="center" class="title1">������</td>
		    <td width="15%" align="center" class="title1">��ʼʱ��</td>
		    <td width="10%" align="center" class="title1">����ʱ��</td>
		    <td width="10%" align="center" class="title1">����</td>
		  </tr>
		  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.pushadv.vo.PushAdvVO">

				<tr <%=ind.intValue()%2== 0 ? "class=\'text4\'" : "class=\'text3\'"%>>
					<td align="center">
		      			<bean:write name="vo" property="contentId"/>
		      		</td>
					<td align="center">
		      			<bean:write name="vo" property="contentName"/>
		      		</td>
		      		<td align="center">
						<bean:write name="vo" property="title"/>
					</td>
		      		<td align="center">
		      			<bean:write name="vo" property="subTitle"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="startTime"/>
		      		</td>
		      		<td align="center">
		      			<bean:write name="vo" property="endTime"/>
		      		</td>
		      		<td align="center" style="word-break: break-all">
				       <input name="ad" type="button" onClick="removePush('<bean:write name="vo" property="id" />','<bean:write name="vo" property="contentId" />');" value="ɾ��"/>
				    </td>
				</tr>
		  </logic:iterate>
		</table>
		</form>
		<table width="95%"  border="0" align="center" cellpadding="0" cellspacing="1" >
		  <tr bgcolor="#B8E2FC">
		    <td align="right" >

		        <pager:pager name="PageResult" form="refForm" action="/web/channelUser/pushAdv.do" >
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
			<input name="buttonAdd" type="button" onClick="return add();" value="��������">
	    </td>
	  </tr>
	</table>

</body>
</html>
