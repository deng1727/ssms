<%@ page contentType="text/html; charset=gb2312" session="true" %>
<%@ page import="com.aspire.ponaadmin.web.repository.RepositoryConstants" %>
<%
String rootNodeID = RepositoryConstants.ROOT_CATEGORY_ID;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<script type="text/javascript" src="../../js/tools.js"></script>
<script type="text/javascript" src="../../js/alai_tree.js"></script>
<script type="text/javascript" src="../../js/tree/xmlextras.js"></script>
<script type="text/javascript" src="../../js/alaiload_tree.js"></script>
<script  LANGUAGE="JavaScript">
<!--
function addContent()
{
  //�ж���û������ѡ��һ��
  if(!haveChooseChk(contentForm,"categoryIDs"))
  {
    alert("����ѡ����࣡");
    return false;
    
  }
  contentForm.submit();
}
 
-->
</script>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#E9F0F8" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1"><FONT class=large>��Դ����--ѡ�������Ŀ</FONT></td>
  </tr>
</table>
<table border="0" align="center" cellspacing="1" align="center" width="50%">
  <form name="contentForm" method="post" action="<%=request.getContextPath()%>/web/channelUser/contentAddToCgy.do?isFinished=true">
  <tr>
    <td><div id="divTree1"></div></td>
  </tr>
  </form>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="text5">
      <input name="button1" type="button" class="input1" onClick="return addContent();" value="ȷ��">
      <input name="button2" type="button" class="input1" onClick="history.go(-1);" value="����">          
    </td>
  </tr>
</table>
</body>
</html>

<script  LANGUAGE="JavaScript">
<!--
  function reload(mykey){
	var obj=findByMykey(tree1,mykey);
	obj.reload();
  }

//����
  function treeControl(){
  }

//�ص�
  function WWWExtend(obj){
    var mykey = obj.label.id;
	var name=obj.label.name;

    if(mykey!="<%=rootNodeID%>"&&(isOperatingType(name)==false))
    {    
      return " <input type='checkbox' name='categoryIDs' value='"+mykey+"'>";
    }
    return " ";
    
  }

  //������ڵ�
  var key="<%=rootNodeID%>";
  var name="��Դ����";
  var url="../../tree?type=resource&key="+key;
  var tree1=new alaiload_tree(key,name,divTree1,url);
  
-->
</script>

