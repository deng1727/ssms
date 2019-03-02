<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="pager" prefix="pager"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.aspire.common.Validator"%>
<%
String tmpStyle = "text5";
//String categoryId = Validator.filter(request.getParameter("categoryId")==null?"":request.getParameter("categoryId"));
String contentId = Validator.filter(request.getParameter("contentId")==null?"":request.getParameter("contentId"));
String contentName = Validator.filter(request.getParameter("contentName")==null?"":request.getParameter("contentName"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>��������������</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="javascript" type="text/javascript"
			src="../../js/My97DatePicker/WdatePicker.js"></script>
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
		
		function add()
		{
			refForm.method.value="add";
			var returnv=window.showModalDialog("queryCbContent.do?method=query_cb_content&isFirst=1", "new","width=800,height=500,toolbar=no,scrollbars=yes,menubar=no,resizable=true ")
			if(returnv != undefined && returnv != '')
			{
				refForm.contentId.value=returnv;
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
		</script>
	</head>

	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<form name="form" action="black.do" method="post">
			<table width="95%" border="0" align="center" cellspacing="1"
				bgcolor="#FFFFFF">
				<tr>
					<td colspan="4" align="center" class="title1">
						���ݲ�ѯ
						<input type="hidden" name="method" value="query">
					</td>
				</tr>
				<tr>
					<td width="18%" align="right" class="text3">
						����ID��
					</td>
					<td width="30%" class="text4">
						<input type="text" name="contentId" value="<%=contentId%>">
					</td>
					<td width="18%" align="right" class="text3">
						�������ƣ�
					</td>
					<td width="30%" class="text4">
						<input type="text" name="contentName" value="<%=contentName%>">
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center" class="text5">
						<input name="Submit" type="submit" class="input1" value="��ѯ">
						<input name="reset" type="reset" class="input1" value="����">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<form name="refForm" action="black.do" method="post">
			<input type="hidden" name="method" value="">
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
							<b>��Ʒ�б�</b>
						</td>
					</tr>
				</table>

				<table width="95%" border="0" align="center" cellspacing="1"
					bgcolor="#FFFFFF">
					<tr>
						<td width="5%" align="center" class="title1">
							<input type="checkbox" value='' name="allSelect"
								onclick="selectAllCB(refForm,'dealRef',this.checked,false);" />
							ȫѡ
						</td>
						<td width="10%" align="center" class="title1">
							����ID
						</td>
						<td width="18%" align="center" class="title1">
							��������
						</td>
						<td width="12%" align="center" class="title1">
							���ʱ��
						</td>
						<td width="10%" align="center" class="title1">
							����
						</td>
						<td width="10%" align="center" class="title1">
							�Ż�
						</td>
					</tr>
					<logic:iterate id="vo" indexId="ind" name="PageResult"
						property="pageInfo"
						type="com.aspire.dotcard.basecomic.vo.BlackVO">
						<%
						    if ("text5".equals(tmpStyle))
						                    {
						                        tmpStyle = "text4";
						                    }
						                    else
						                    {
						                        tmpStyle = "text5";
						                    }
						%>
						<tr class=<%=tmpStyle%>>
							<td align="center" style="word-break: break-all;">
								<input type="checkbox" name="dealRef"
									value="<bean:write name="vo" property="id"/>">
							</td>
							<td align="center">
								<bean:write name="vo" property="contentId" />
							</td>
							<td align="center">
								<bean:write name="vo" property="contentName" />
							</td>
							<td align="center">
								<bean:write name="vo" property="createDate"  format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td align="center">
								<logic:equal name="vo" property="contentType" value="101">
					      			����
						      	</logic:equal>
						      	<logic:equal name="vo" property="contentType" value="115">
						      		����Ƭ
						      	</logic:equal>
						      	<logic:equal name="vo" property="contentType" value="116">
						      		��Ѷ
						      	</logic:equal>
						      	<logic:equal name="vo" property="contentType" value="220">
						      		������
						      	</logic:equal>
							</td>
							<td align="center">
								<logic:greaterThan name="vo" property="contentPortal" value="3">
					      			δ֪
					      		</logic:greaterThan>
								<logic:lessThan name="vo" property="contentPortal" value="1">
					      			δ֪
					      		</logic:lessThan>
								<logic:equal name="vo" property="contentPortal" value="1">
					      			�ͻ���
					      		</logic:equal>
								<logic:equal name="vo" property="contentPortal" value="2">
					      			WAP�Ż�
					      		</logic:equal>
								<logic:equal name="vo" property="contentPortal" value="3">
					      			����
					      		</logic:equal>
							</td>
						</tr>
					</logic:iterate>
				</table>
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="1">
					<tr bgcolor="#B8E2FC">
						<td align="right">
							<%
							    HashMap params = new HashMap();
							        params.put("method", "query");
							        params.put("contentId", contentId);
							        params.put("contentName", contentName);
							%>
							<pager:pager name="PageResult"
								action="/web/comic/black.do" params="<%=params%>">
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
							<input name="buttonDel" type="button" class="input1"
								onClick="remove();" value="�Ƴ�">
						</logic:notEmpty>
						<input name="buttonAdd" type="button" class="input1"
							onClick="add();" value="���">
						<input name="buttonImport" type="button" class="input1"
							onClick="importData();" value="��������">
					</td>
				</tr>
			</table>
		</form>
		<form action="importBlack.do" name="form1" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method" value="importData">
			<table width="95%" border="0" align="center" cellspacing="1">
				<tr>
					<td align="left" colspan="2">
						<b>��������</b>
						<select name="addType">
							<option value="ALL">
								ȫ��
							</option>
							<option value="ADD">
								����
							</option>
						</select>
						<font color=red>&nbsp;&nbsp;<b>
							<br />�������ڸú��������������ݣ��������EXCEL��2003�汾����2007�汾��ָ�����ݵ��ú�����
							<br />ȫ����ɾ��ԭ�иú�������ȫ�����ݣ�ȫ�����EXCEL(2003�汾����2007�汾)ָ�����ݵ��ú����� <b>
						</font>
					</td>
				</tr>
				<tr>
					<td align="right" width="20%">
						ѡ��Ҫ�����excel�����ļ���
					</td>
					<td align="left" width="80%">
						<input type="file" name="dataFile">
						(����һ�б�ʾ����ID)
					</td>
				</tr>
			</table>
			<br />
			<br />
			<br />
		</form>
	</body>
</html>
