<%@ page contentType="text/html; charset=gb2312"%>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page
	import="com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-nested" prefix="nested"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӧ���б�</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
</head>
<script language="JavaScript">
function selectContentId()
{
	var obj = document.getElementsByName("cid");
	
    if(obj!=null){
    	var ids="";
        var i;
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].checked)
            {
                //opener.ContentForm.id.value=obj[i].value;
                var v=window.dialogArguments;
                if(v=='diss')
                {
                  var d=new Object();d.id=obj[i].value;d.name=document.all["cname"+i].value;
                  window.returnValue=d;
                }
                else
                {
                ids=ids+","+obj[i].value;
                }
            }
        }
        if(ids.indexOf(",")==0){
        //alert("dd"+ids.length);
        	ids = ids.substring(1,ids.length);
        }
        window.returnValue = ids;
    }
    window.close();
}

function checkPageSize(field)
  {
    var pageSize=field.value;
    var pattern=/\D+/;
    if(pattern.test(pageSize))
    {
      alert("ÿҳչʾ��¼����ֵ������������");
      field.focus();
      return false;
    }
    var Page_MIN=<%=PageSizeConstants.Page_MIN%>;
    var Page_MAX=<%=PageSizeConstants.page_MAX%>;
    if(pageSize<Page_MIN)
    {
      alert("ÿҳչʾ�ļ�¼��������ڻ��ߵ���"+Page_MIN);
      field.focus();
      return false;
    }
    if(pageSize>Page_MAX)
    {
       alert("ÿҳչʾ�ļ�¼������С�ڻ��ߵ���"+Page_MAX);
       field.focus();
       return false;
    }
    //
    contentListForm.querySubmit.disabled=true; //���û�г����������ٴ��ύ,��ֹ�ظ��Դ��ύ��
    return true;
  }
  
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<form name="contentListForm" action="pushAdv.do" method="post"
	onSubmit="return checkPageSize(contentListForm.pageSize);">
<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">

	<tr>
		<td colspan="4" align="center" class="title1">Ӧ�ò�ѯ</td>
		<input type="hidden" name="perType" value="content">
	</tr>
	<tr>
	    <td width="18%" align="right" class="text3">Ӧ��ID��</td>
		<td width="30%" class="text4"><input type="text" name="contentId"
			value="<bean:write name="contentId"/>"></td>
		<td width="18%" align="right" class="text3">Ӧ�����ƣ�</td>
		<td width="30%" class="text4"><input type="text" name="contentName"
			value="<bean:write name="contentName"/>"></td>
	</tr>

	<tr>
		<td width="18%" align="right" class="text3">ÿҳչʾ��¼����</td>
		<td width="30%" class="text4"><input type="text" name="pageSize"
			value="<bean:write name="pageSize"/>"></td>
	</tr>
	<tr>
		<td colspan="4" align="center" class="text5"><input
			name="querySubmit" type="submit" class="input1" value="��ѯ"> <input
			name="reset" type="reset" class="input1" value="����"></td>
	</tr>

</table>
</form>
<br>

<table width="95%" border="0" align="center" cellspacing="1"
	bgcolor="#FFFFFF">
	<tr>
		<td align="center" class="title1">���͹���----���Ӧ��</td>
	</tr>
	<logic:empty name="PageResult" property="pageInfo">
		<tr>
			<logic:equal value="0" name="exeType">
				<td align="center"><font color="#ff0000">��ѡ��������ѯ</font></td>
			</logic:equal>
			<logic:notEqual value="0" name="exeType">
				<td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
			</logic:notEqual>
		</tr>
	</logic:empty>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="right" class="text1">
			 <pager:pager name="PageResult"
				form="contentListForm" action="/web/channelUser/pushAdv.do">
				<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="1" />
			</pager:pager></td>
		</tr>
	</table>
	<form name="contentForm" action="" method="post">
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
		    <td width="5%" align="center" class="title1"></td>
			<td width="10%" align="center" class="title1">Ӧ��ID</td>
			<td width="15%" align="center" class="title1">Ӧ������</td>
			<td width="15%" align="center" class="title1">�ṩ��</td>
			<td width="5%" align="center" class="title1">Ӧ������</td>
			<td width="10%" align="center" class="title1">����ʱ��</td>

		</tr>
		<logic:iterate id="vo" indexId="ind" name="PageResult"
			property="pageInfo"
			type="com.aspire.ponaadmin.web.pushadv.vo.ContentVO">
			<tr <%=ind.intValue()%2== 0 ? "class=\'text4\'" : "class=\'text3\'"%>>
				<td align="center"><input type="radio" name="cid"
					value="<bean:write name="vo" property="contentId"/>"></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="contentId" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="name" /></td>
				<td align="center" style="word-break: break-all"><bean:write
					name="vo" property="spName" /></td>
				<td align="center" style="word-break: break-all">
					<bean:write
					name="vo" property="catename" />
				</td>
				<td align="center" style="word-break: break-all"><bean:write
					name='vo' property='marketDate' /></td>

			</tr>
		</logic:iterate>
	</table>
    </form>
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			
			<td align="right" class="text1"><pager:pager name="PageResult"
				form="contentListForm" action="/web/channelUser/pushAdv.do">
				<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="2" />
			</pager:pager></td>
		</tr>
	</table>
    <table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
	  <tr>
		<td align="center" class="text5"><input name="ad" type="button"
			class="input1" onClick="selectContentId();" value="ȷ��" /> <input name="ad"
			type="button" class="input1" onClick="window.close();" value="�ر�" />
		</td>
	  </tr>
    </table>
</logic:notEmpty>
	
</body>
</html>


