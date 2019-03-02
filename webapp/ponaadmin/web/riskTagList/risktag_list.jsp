<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="right" prefix="right"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ taglib uri="message" prefix="msg"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.risktag.vo.RiskVO"%>
<%@page
	import="com.aspire.ponaadmin.common.rightmanager.RightManagerConstant"%>
<%@page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
	String name = Validator.filter(request.getParameter("name") == null
			? ""
			: request.getParameter("name"));
	String desc = Validator.filter(request.getParameter("desc") == null
			? ""
			: request.getParameter("desc"));
	String rightID = Validator
			.filter(request.getParameter("rightID") == null
					? ""
					: request.getParameter("rightID"));
	String rightName = Validator.filter(request
			.getParameter("rightName") == null ? "" : request
			.getParameter("rightName"));
	String rightType = Validator.filter(request
			.getParameter("rightType") == null ? "0" : request
			.getParameter("rightType"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>M-Market���ܹ���ϵͳ</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript"
	src="../../js/tools.js"></script>
	<script language="Javascript" type="text/javascript"
	src="../../js/rsa/jquery-1.3.2.min.js"></script>
<script language="javascript">

<%--  $( function (){  
    var selectedValue1 = '<%=request.getAttribute("id")%>';  
       var selectedValue2 = '<%=request.getAttribute("stats")%>'; 
       changeSelected();
      var a =  document.getElementById("select1");
    function changeSelected(){  
    var opt = document.getElementsByName("id").options;
  	  alert("="+opt.length);
  	  
  	  
        jsSelectItemByValue(document.getElementsByName("id"),selectedValue1);
        
        jsSelectItemByValue(document.getElementsByName("stats"),selectedValue2);    
    }  
      
    function jsSelectItemByValue(objSelect,objItemText) {  
         for(var i=0;i<objSelect.options.length;i++) {  
            if(objSelect.options[i].value == objItemText) {  
                objSelect.options[i].selected = true;  
                break;  
            }   
        }   
    }  --%>

function doCancel()
{
var riskid= document.getElementById("riskid");
var cancelid= document.getElementById("cancelid");
  if(confirm("��ȷ��Ҫִ�в�����"))
  {
	window.location.href="riskTagList.do?perType=cancel&riskid="+riskid+"stats="+cancelid;
  }
}

function tagExport()
	{
		if(confirm("��ȷ��Ҫ����չʾ������"))
		{	
			window.location.href="riskTagList.do?perType=output&type=0"
		    //refForm.action="tagExport.do";
			//refForm.submit();
			//refForm.action='tagMgr.do';
		}
		else
		{
			return false;	
		}
	}
function tagExport1()
	{
		if(confirm("��ȷ��Ҫ�������β�����"))
		{	
			window.location.href="riskTagList.do?perType=output&type=1"
		    //refForm.action="tagExport.do";
			//refForm.submit();
			//refForm.action='tagMgr.do';
		}
		else
		{
			return false;	
		}
	}

function chooseRight()
{
  var index = queryForm.rightType.selectedIndex;
  if(index==0)
  {
    alert("����ѡ��Ȩ�����ͣ�");
    return false;
  }
  getRight(queryForm.rightID,queryForm.rightName,queryForm.rightType.options[index].value);
}

function checkForm()
{
  //����ɫ���Ƴ���
  var name = queryForm.name.value;
  //����ɫ�������ݺϷ���
  if(!checkIllegalChar(name,"'\"<>\\/"))
  {
    alert("��ɫ���Ʋ��ܰ��� ' \" < > \\ /  ���ַ���");
    queryForm.name.focus();
    return false;
  }
  //����ɫ��������
  var desc = queryForm.desc.value;
  //����ɫ�������ݺϷ���
  if(!checkIllegalChar(desc,"'\"<>\\/"))
  {
    alert("��ɫ�������ܰ��� ' \" < > \\ /  ���ַ���");
    queryForm.desc.focus();
    return false;
  }
  return true;
}

/* }); */
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1"></td>
		</tr>
	</table>
	<table width="95%" border="0" align="center" cellspacing="1">
		<form name="queryForm" method="post" action="riskTagList.do"
			onSubmit="return checkForm();">
			<tr>
				<td colspan="4" align="center" class="title1">���ձ�ǩ��ѯ <input
					type="hidden" name="perType" value="query"></td>
			</tr>
			<tr>
				<td width="14%" align="right" class="text3">��ǩ���ƣ�</td>
				<td width="46%" class="text4"><select name="id" id ="select11">
							<option value="0" name="option">
								ȫ��
							</option>
							<option value="1" name="option">
								�й��SDK,��Ƕ���
							</option>
							<option value="2" name="option">
								�����ݰ�Ȩ
							</option>
							<option value="3" name="option">
								����Ϣ���紫��������Ŀ���֤
							</option>
							<option value="4" name="option">
								���������Ȩ
							</option>
				</td>
				<td align="right" class="text3">״̬��</td>
				<td class="text4"><select name="stats" id = "select22">
							<option value="-1" name="option2">
								ȫ��
							</option>
							<option value="1" name="option2">
								����
							</option>
							<option value="0" name="option2">
								չʾ
							</option>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit2" type="submit" class="input1" value="��ѯ"> <input
					name="Submit22" type="reset" class="input1" value="����"></td>
			</tr>
		</form>
		<tr>
				<td colspan="4" align="center" class="text5"><input
					name="Submit3" type="button" class="input1" value="����չʾӦ��" onClick="tagExport()">
				 <input name="Submit33" type="button" class="input1" value="��������Ӧ��" onClick="tagExport1()"></td>
			</tr>
	</table>
	<br>
	<table width="95%" border="0" align="center" cellspacing="1"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" class="title1">���ձ�ǩ����</td>
		</tr>
	</table>
	<logic:empty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td height="20" align="center"><font color="#ff0000">û���ҵ���</font>
				</td>
			</tr>
		</table>
	</logic:empty>

	<logic:notEmpty name="PageResult" property="pageInfo">
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr class="title2">
				<td width="20%" height="20" align="center">ID</td>
				<td width="38%" align="center">��������</td>
				<td width="8%" align="center">״̬</td>
				<td width="8%" align="center">������ʱ��</td>
				<td width="8%" align="center">�鿴</td>
				<td width="8%" align="center">�޸�״̬</td>
			</tr>
			<logic:iterate id="vo" indexId="ind" name="PageResult"
				property="pageInfo"
				type="com.aspire.ponaadmin.web.risktag.vo.RiskVO">
				<tr class="<%=ind.intValue() % 2 == 0 ? "text4" : "text3"%>">
					<input type="hidden" name="riskid" id="riskid"
						value="<bean:write name="vo" property="id"/>">
					<td align="center" height="20"><a
						href="oneRole.do?roleID=<bean:write name="vo" property="id"/>&action=view"><bean:write
								name="vo" property="id" />
					</a>
					</td>
					<td align="center" style="word-break:break-all"><logic:equal
							name="vo" property="risktag" value="1">�й��SDK,��Ƕ���</logic:equal>
						<logic:equal name="vo" property="risktag" value="2">�����ݰ�Ȩ</logic:equal>
						<logic:equal name="vo" property="risktag" value="3">����Ϣ���紫��������Ŀ���֤</logic:equal>
						<logic:equal name="vo" property="risktag" value="4">���������Ȩ</logic:equal>
					</td>
					<td align="center" height="20"><logic:equal name="vo"
							property="stats" value="0">չʾ</logic:equal> <logic:equal
							name="vo" property="stats" value="1">����</logic:equal>
					</td>
					<td align="center" height="20"><bean:write name="vo"
							property="handleTime" />
					</td>
					<td align="center"><input type="button" class="input1"
						value="�鿴" name="btn_edit"
						onClick="window.location.href='riskTagList.do?perType=queryDetail&riskid=<bean:write name="vo" property="risktag"/>';">
					</td>
					<td align="center"><input type="button" class="input1" id ="cancelid"
						<logic:equal name="vo" property="stats" value="1"> value="ȡ������" </logic:equal>
						<logic:equal name="vo" property="stats" value="0"> value="����" </logic:equal>
						name="btn_cancel"
						
						onClick="window.location.href='riskTagList.do?perType=cancel&riskid=<bean:write name="vo" property="risktag"/>&stats=<logic:equal name="vo" property="stats" value="1">0</logic:equal><logic:equal name="vo" property="stats" value="0">1</logic:equal>';">


					</td>
				</tr>
			</logic:iterate>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1">
			<tr class="text1">
				<td align="right">
					<%
						HashMap params = new HashMap();
							params.put("name", name);
							params.put("desc", desc);
							params.put("rightID", rightID);
							params.put("rightName", rightName);
							params.put("rightType", rightType);
					%> <pager:pager name="PageResult"
						action="/web/rightmanager/roleList.do" params="<%=params%>">
						<pager:firstPage label="��ҳ" />&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber />ҳ/��<pager:pageCount />ҳ
            <pager:location id="1" />
					</pager:pager></td>
			</tr>
		</table>
	</logic:notEmpty>
</body>
</html>
