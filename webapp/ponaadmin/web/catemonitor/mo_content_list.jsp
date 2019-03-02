<%@ page contentType="text/html; charset=gbk" %>
<%@page import="com.aspire.ponaadmin.web.system.PageSizeConstants"%>
<%@page import="com.aspire.ponaadmin.web.repository.persistency.db.DBPersistencyCFG"%>
<%@page import="java.util.List"%>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<%@ taglib uri="pager" prefix="pager" %>
<%@page import="java.util.HashMap" %>
<%@ page import="com.aspire.ponaadmin.web.constant.ResourceUtil" %>
<%@ page import="com.aspire.common.Validator" %>

<%
String name = Validator.filter(request.getParameter("name")==null?"":request.getParameter("name"));
String spName = Validator.filter(request.getParameter("spName")==null?"":request.getParameter("spName"));
String cateName = Validator.filter(request.getParameter("cateName")==null?"":request.getParameter("cateName"));
String typeDesc = Validator.filter(request.getParameter("typeDesc")==null?"":request.getParameter("typeDesc"));
String icpCode = Validator.filter(request.getParameter("icpCode")==null?"":request.getParameter("icpCode"));
String icpServId = Validator.filter(request.getParameter("icpServId")==null?"":request.getParameter("icpServId"));
String type= Validator.filter(request.getParameter("type")==null?"":request.getParameter("type"));

String contentID = Validator.filter(request.getParameter("contentID")==null?"":request.getParameter("contentID"));
String contentTag = Validator.filter(request.getParameter("contentTag")==null?"":request.getParameter("contentTag"));
String tagLogic = Validator.filter(request.getParameter("tagLogic")==null?"AND":request.getParameter("tagLogic"));
String categoryID = Validator.filter(request.getParameter("categoryID"));
String _TotalRows = Validator.filter(request.getParameter("TotalRows")==null?"":request.getParameter("TotalRows"));
String _pagerNumber = Validator.filter(request.getParameter("pagerNumber")==null?"":request.getParameter("pagerNumber"));
String backURL = "moContentList.do?categoryID=" + categoryID + "&TotalRows=" + _TotalRows + "&pagerNumber="+_pagerNumber + "&name=" + name + "&spName=" + spName;

String deviceName=Validator.filter(request.getParameter("deviceName")==null?"":request.getParameter("deviceName"));
String servAttr = Validator.filter(request.getParameter("servAttr")==null?"":request.getParameter("servAttr"));
String cgyPath=Validator.filter(request.getParameter("cgyPath")) ;

backURL = backURL + "&cateName=" + cateName + "&icpCode=" + icpCode + "&icpServId=" + icpServId + "&type=" + type + "&contentID=" + contentID + "&contentTag=" + contentTag + "&tagLogic=" + tagLogic
                  +"&deviceName="+deviceName+"&servAttr="+servAttr+"&cgyPath="+cgyPath;
String pageSize = Validator.filter(request.getParameter("pageSize")==null?PageSizeConstants.page_DEFAULT:request.getParameter("pageSize"));
backURL=backURL+"&pageSize="+pageSize;




String backURL_enc = java.net.URLEncoder.encode(backURL);

//��ȡ��ǰ���ÿ��б�
List cfgList=DBPersistencyCFG.getInstance().getOrderedSubType("nt:gcontent",true);
pageContext.setAttribute("cfgList",cfgList,pageContext.PAGE_SCOPE);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<script language="Javascript" type="text/javascript" src="../../js/tools.js"></script>
<script language="JavaScript">
<!--
function addContent()
{
  var isReflesh=window.showModalDialog("cgyNotContentList.do?subSystem=ssms&exeType=0&cgyPath=<%=cgyPath%>&categoryID=<bean:write name="category" property="id"/>&isClose=true","modal","center:yes;dialogWidth:600px;dialogHeight:700px;resizable: yes; help: no; status: yes; scroll: auto; ");
  if(isReflesh)
  {
    window.location.reload();
  }
  
}
function tactic()
{
	contentListForm.action="<%=request.getContextPath()%>/web/systemmgr/tactic.do?subSystem=ssms";
    contentListForm.submit();
}
function removeContent()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"dealContent"))
  {
    alert("����ѡ��Ҫ�Ƴ������ݣ�");
    return false;
    
  }
  if(confirm("<%=ResourceUtil.getResource("PUBLIC_REMOVE_ALERT")%>"))
  {
    contentForm.action="cgyContentRemove.do?categoryID=<bean:write name="category" property="id"/>";
    contentForm.submit();
  }
}

function checkForm(form)
{
  if(!checkContent(form.keywords,"REG_CHINESE_NUM_LERTER_COMMA"))
  {
    alert("�ؼ���ֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ�����š�");
    form.keywords.focus();
    return false;
  }
  
  return true;
  
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
    return true;
  }
  function SetSortIDS()
  {
   var i=0;var sortid=0;
   var changedValues="";
   var isFirst=true;
   
   var Taxis=document.getElementsByName("Taxis");
   var dealContent=document.getElementsByName("dealContent");

   //debugger��
    <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.ReferenceNode">
      sortid=<bean:write name="vo" property="sortID"/>;
      if(!mod_click(Taxis[i]))
      {
        Taxis[i].select();
        Taxis[i].focus();
        return false;
      }
      if(sortid!=Taxis[i].value)
      {
        if(isFirst)
       {
        changedValues=changedValues+dealContent[i].value+"#"+Taxis[i].value;
        isFirst=false;
       }else
       {
         changedValues+=":"+dealContent[i].value+"#"+Taxis[i].value;
       }
      }

      i++;
     </logic:iterate>
     if(changedValues=="")
     {
       alert("���޸�����һ�������");
     }else
     {
       contentForm.changedValues.value=changedValues;
       contentForm.action="<%=request.getContextPath()%>/web/resourcemgr/cgyContentTaxis.do?action=mod";
       contentForm.submit();
     }
     
     
  }

//-->
</script>
</head>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5" >
<table width="95%"  border="0" align="center" cellspacing="1">
  <tr>
    <td>����λ�ã�<%=cgyPath %></td>
    <td align="center">
					��ǰѡ����ͣ�<font color="red"><%
					if(request.getSession().getAttribute("deviceName") == null || request.getSession().getAttribute("deviceName").equals("")){
					%>ȫ��
					<%
					}else{
					out.print(request.getSession().getAttribute("deviceName"));
					}
					%></font>
				</td>
				<td><a href="cate_monitor.jsp">��������ѡ�����</a></td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <form name="contentListForm" action="moContentList.do" method="post" onSubmit="return checkPageSize(contentListForm.pageSize);">
  <tr>
    <td colspan="4" align="center" class="title1">
      ���ݲ�ѯ<input type="hidden" name="categoryID" value="<bean:write name="category" property="id"/>">
      <input type="hidden" name="htype" value="<%=type%>">
    </td>
  </tr>
  <input type="hidden" name="cgyPath" value="<%=cgyPath%>"/>
  <tr>
    <td width="18%" align="right" class="text3">���ƣ�
    </td>
    <td width="30%" class="text4"><input type="text" name="name" value="<%=name%>"></td>
    <td width="18%" align="right" class="text3">�ṩ�̣�
    </td>
    <td width="30%" class="text4"><input type="text" name="spName" value="<%=spName%>"></td>
  </tr>
  
  <tr>
    <td width="18%" align="right" class="text3">ҵ�����ݷ��ࣺ
    </td>
    <td width="30%" class="text4"><input type="text" name="cateName" value="<%=cateName%>"></td>
    <td width="18%" align="right" class="text3">��ҵ���룺
    </td>
    <td width="30%" class="text4"><input type="text" name="icpCode" value="<%=icpCode%>"></td>
 </tr>
  
 <tr>
    <td width="18%" align="right" class="text3">ҵ����룺
    </td>
    <td width="30%" class="text4"><input type="text" name="icpServId" value="<%=icpServId%>"></td>
    <td width="18%" align="right" class="text3">�������ͣ�
    </td>
    
    <td width="30%" class="text4">  
    <SELECT name=type> 
    <OPTION value="" ></OPTION>
    <logic:iterate id="nodeType" name="cfgList" type="com.aspire.ponaadmin.web.repository.persistency.db.NodeCFG">
    
    <OPTION value="<bean:write name="nodeType" property="type"/>" <logic:equal value="<%=type %>" name="nodeType" property="type">selected</logic:equal> ><bean:write name="nodeType" property="typeDesc"/></OPTION>
    </logic:iterate>              
    </SELECT>
    </td>
    
 </tr>
  <tr>
    <td width="18%" align="right" class="text3">����ID��
    </td>
    <td width="30%" class="text4"><input type="text" name="contentID" value="<%=contentID%>"></td>
    <td width="18%" align="right" class="text3">���ݱ�ǩ:</td>
    <td width="30%" class="text4">
    <input type="text" name="contentTag" value="<%=contentTag%>">
    	<select name="tagLogic">
	    	<option value="AND" <%if(tagLogic.equals("AND")){%>selected<%}%>>AND</option>
	    	<option value="OR" <%if(tagLogic.equals("OR")){%>selected<%}%>>OR</option>
    	</select>
    </td>
 </tr>
 <tr>
  
    <td width="18%" align="right" class="text3">�ֻ��ͺţ�</td>
    <td width="30%" class="text4">
    <font color="red"><%
					if(request.getSession().getAttribute("deviceName") == null || request.getSession().getAttribute("deviceName").equals("")){
					%>ȫ��
					<%
					}else{
					out.print(request.getSession().getAttribute("deviceName"));
					}
					%></font>
    <!-- 
    <input type="text" name="deviceName" value="<%=deviceName %>">
    -->
    </td>
    <td width="18%" align="right" class="text3">�ṩ��Χ</td>
    <td width="30%" class="text4"><SELECT name=servAttr> 
    <OPTION value="" ></OPTION>
    <OPTION value="G" <%if(servAttr.equals("G")){%>selected<%}%> >ȫ��</OPTION>
    <OPTION value="L" <%if(servAttr.equals("L")){%>selected<%}%> >ʡ��</OPTION>
    </select>
    </td>
 </tr>
     <tr>
    <td width="18%" align="right" class="text3">ÿҳչʾ��¼����</td>
    <td width="30%" class="text4"><input type="text" name="pageSize" value="<%=pageSize%>" ></td>
    <td width="18%" align="right" class="text3"></td>
    <td width="30%" class="text4"></td>
 </tr>
  <tr>
    <td colspan="4" align="center" class="text5"><input name="Submit" type="submit" class="input1" value="��ѯ" >
    <input name="reset" type="reset" class="input1" value="����">
     <input name="reset" type="button" class="input1" value="����ͬ�����Թ���" onClick="tactic();"></td>
  </tr>
  </form>
</table>
<br>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">������Ŀ����</td>
  </tr>
  <logic:empty name="PageResult" property="pageInfo">
      <tr>
          <td align="center"><font color="#ff0000">û���ҵ��κμ�¼��</font></td>
      </tr>
  </logic:empty>
</table>
<logic:notEmpty name="PageResult" property="pageInfo">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="right" class="text1">
        <%
        HashMap params = new HashMap();
        params.put("name",name);
        params.put("categoryID",request.getParameter("categoryID"));
        params.put("type",type);
        params.put("spName",spName);
        params.put("cateName",cateName);
        params.put("icpCode",icpCode);
        params.put("icpServId",icpServId);
        params.put("contentID",contentID);
        params.put("contentTag",contentTag);
        params.put("tagLogic",tagLogic);
        params.put("pageSize",pageSize);
        params.put("deviceName",deviceName);
        params.put("servAttr",servAttr);
        params.put("cgyPath",cgyPath);
        %>
        <pager:pager name="PageResult" action="/web/catemonitor/moContentList.do" params="<%=params%>">
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
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="5%" align="center" class="title1">ѡ��</td>
    <td width="10%" align="center" class="title1">����ID</td>     
    <td width="18%" align="center" class="title1">����</td>
    <td width="12%" align="center" class="title1">�ṩ��</td>
    <td width="10%" align="center" class="title1">��������</td>  
    <td width="15%" align="center" class="title1">���ݱ�ǩ</td> 
    <td width="10%" align="center" class="title1">�ṩ��Χ</td> 
    <td width="15%" align="center" class="title1">����ʱ��</td>       	  	
    <td width="5%" align="center" class="title1">�������</td>
  </tr>
  <form name="contentForm" action="" method="post">
  <input type="hidden" name="contentType" value="sort">
  <input type="hidden" name="backURL" value="<%=backURL %>" >
  <input type="hidden" name="changedValues" >
  <input type="hidden" name="cateid" value="<bean:write name="category" property="id"/>">
  <%String tmpStyle = "text5";%>
  <logic:iterate id="vo" indexId="ind" name="PageResult" property="pageInfo" type="com.aspire.ponaadmin.web.repository.ReferenceNode">
    <bean:define id="content" name="vo" property="refNode" type="com.aspire.dotcard.gcontent.GContent"/>
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
	    <td align="center"><input type="checkbox" name="dealContent" value="<bean:write name="content" property="id"/>#<bean:write name="vo" property="id"/>"></td>
	    <td align="center" style="word-break:break-all"><a href="../resourcemgr/contentInfo.do?contentID=<bean:write name="content" property="id"/>&categoryID=<bean:write name="category" property="id"/>"><bean:write name="content" property="id"/></a></td> 
	    <td align="center" style="word-break:break-all"><a href="../resourcemgr/contentInfo.do?contentID=<bean:write name='content' property='id'/>&categoryID=<bean:write name='category' property='id'/>&goodsID=<bean:write name='vo' property='goodsID'/>" id="contentName<bean:write name='vo' property='id'/>"><bean:write name="content" property="name"/></a></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="spName"/></td>  
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="typeDesc"/></td>
	    <td align="center" style="word-break:break-all"><bean:write name="content" property="keywordsDesc"/></td>
	    <td align="center" style="word-break:break-all">
	                                  <logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
	                                  <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>    </td>
	    <td align="center" style="word-break:break-all"><bean:write name='content' property='marketDate'/></td>
	    

	    	<td align="center"><input type="text" name="Taxis" size="4" value="<bean:write name='vo' property='sortID'/>"/></td>

	   
	    	<td align="center"><bean:write name='vo' property='sortID'/></td>

	  </tr>
  </logic:iterate>
  </form>
</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td width="24%" class="text1"><input type="checkbox" name="checkbox" value="checkbox" onClick="selectAllCB(contentForm,'dealContent',this.checked,false);">
      ȫ��ѡ��
    </td>
    <td align="right" class="text1">
        <pager:pager name="PageResult" action="/web/catemonitor/moContentList.do" params="<%=params%>">
            <pager:firstPage label="��ҳ"/>&nbsp;
            <pager:previousPage label="ǰҳ" />&nbsp;
            <pager:nextPage label="��ҳ" />&nbsp;
            <pager:lastPage label="βҳ" />&nbsp;
            ��<pager:pageNumber/>ҳ/��<pager:pageCount/>ҳ
            <pager:location id="2"/>
        </pager:pager>
    </td>
  </tr>
</table>
</logic:notEmpty>
<!-- 
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
        <tr>
          <td align="center" class="text5">
            <right:checkDisplay rightID="0_0805_RESOURCE_CGYCNT_REMOVE"><logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonDel" type="button" class="input1" onClick="return removeContent();" value="�Ƴ�"></logic:notEmpty></right:checkDisplay>
            <right:checkDisplay rightID="0_0811_RESOURCE_CGYCNT_ORDER"><logic:notEmpty name="PageResult" property="pageInfo"><input name="buttonSet" type="button" class="input1" onClick="return SetSortIDS();" value="��������趨"></logic:notEmpty></right:checkDisplay>	
            <right:checkDisplay rightID="0_0804_RESOURCE_CGYCNT_ADD"><input name="buttonAdd" type="button" class="input1"  onClick="return addContent();" value="���ӷ�����Դ"></right:checkDisplay>
            <input name="buttonImport" type="button" class="input1"  onClick="window.location.href='cgy_content_import.jsp?cateId=<bean:write name="category" property="id"/>&cgyPath=<%=cgyPath %>'" value="���������Ʒ">
          </td>
        </tr>
</table>

-->
</body>
</html>