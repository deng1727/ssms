<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.List" %>
<%@ page import="com.aspire.ponaadmin.web.repository.ContentGoodsInfo" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Ӫ�������Ż�����ƽ̨</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">�ص�������ϸ��Ϣ</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<logic:equal name="content" property="type" value = "nt:gcontent:colorring">
  <tr>
    <td nowrap align="right" class="text3">�������ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">�������룺</td>
    <td class="text4"><bean:write name="content" property="contentID"/>&nbsp;
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">�۸�</td>
    <td class="text4"><bean:write name="content" property="price_Y"/>&nbsp;Ԫ
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">���֣�</td>
    <td class="text4"><bean:write name="content" property="singer"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">��Ч�ڣ�</td>
    <td class="text4"><bean:write name="content" property="displayExpire"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ش�����</td>
    <td class="text4"><bean:write name="content" property="downloadtimes"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">���ô�����</td>
    <td class="text4"><bean:write name="content" property="settimes"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">������ַ��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="auditionUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">����ʱ������λ�����룩��</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>        
</logic:equal>  

<logic:equal name="content" property="type" value = "nt:gcontent:audio">
  <tr>
    <td width="30%" align="right" class="text3">�������ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">����ID��</td>
    <td class="text4"><bean:write name="content" property="contentID"/>&nbsp;
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">���֣�</td>
    <td class="text4"><bean:write name="content" property="singer"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">����ʱ������λ�����룩��</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">aac�ļ�������ַ��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="aacAuditionUrl"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">mp3�ļ�������ַ��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="mp3AuditionUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���URL��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="lrcURL"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">������ɷ��ࣺ</td>
    <td class="text4"><bean:write name="content" property="cateName"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">�������Է��ࣺ</td>
    <td class="text4"><bean:write name="content" property="audioLanguage"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ֵ������ࣺ</td>
    <td class="text4"><bean:write name="content" property="singerZone"/>
    </td>
  </tr>           
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:news">
  <tr>
    <td align="right" class="text3">ý��ID��</td>
    <td class="text4"><bean:write name="content" property="contentID"/>
    </td>
  </tr> 
  <tr>
    <td width="30%" align="right" class="text3">ý�����ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ý��ͼ��url��</td>
    <td class="text4"><bean:write name="content" property="iconUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">ý������url��</td>
    <td class="text4"><bean:write name="content" property="mediaUrl"/>
    </td>
  </tr>   
  <tr>
    <td align="right" class="text3">ͷ��id��</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ�����ݱ��⣺</td>
    <td class="text4"><bean:write name="content" property="title"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ��ͼƬurl��</td>
    <td class="text4"><bean:write name="content" property="imageUrl"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�������ģ�</td>
    <td class="text4"><bean:write name="content" property="introduction"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ������ʱ�䣺</td>
    <td class="text4"><bean:write name="content" property="marketDate"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ������url��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="hotUrl"/>
    </td>
  </tr>        
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:appGame">
  <tr>
    <td width="30%" align="right" class="text3">�������ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ�����ݷ��ࣺ</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">�ṩ�̣�</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP��ҵ���룺</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ����룺</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�ṩ��Χ��</td>
    <td class="text4"> <logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
                       <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">����ָ�ϣ�</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="handBook"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">���ݱ�ʶ��</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">��Ϸ��Ƶ��ַ��</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="gameVideo"/>
    </td>
  </tr>    
  <tr>
    <td align="right" class="text3">���û��ͣ�</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">��������������</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������������</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���ݶ���������</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">�������۴�����</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������ִ�����</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����Ƽ�������</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����ղش�����</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݼ�Ȩƽ���֣�</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>    
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:appTheme">
  <tr>
    <td  align="right"  nowrap class="text3">�������ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" nowrap class="text3">ҵ�����ݷ��ࣺ</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">�ṩ�̣�</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP��ҵ���룺</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ����룺</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�ṩ��Χ��</td>
    <td class="text4"> <logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
                       <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">LOGO1ͼƬ��ַ��</td>
    <td class="text4"><bean:write name="content" property="LOGO1"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���ݱ�ʶ��</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">���û��ͣ�</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
    <tr>
    <td align="right" class="text3">��������������</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������������</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���ݶ���������</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">�������۴�����</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������ִ�����</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����Ƽ�������</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����ղش�����</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݼ�Ȩƽ���֣�</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr> 
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:appSoftWare">
  <tr>
    <td nowrap align="right" class="text3">�������ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ�����ݷ��ࣺ</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">�ṩ�̣�</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP��ҵ���룺</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ҵ����룺</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�ṩ��Χ��</td>
    <td class="text4"><logic:equal value="G" name="content" property="servAttr">ȫ��</logic:equal>
                      <logic:equal value="L" name="content" property="servAttr">ʡ��</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ʹ���ֲ���ļ���ַ��</td>
    <td class="text4"><bean:write name="content" property="manual"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���û��ͣ�</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݱ�ʶ��</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">��������������</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������������</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">���ݶ���������</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">�������۴�����</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�������ִ�����</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����Ƽ�������</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">�����ղش�����</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">���ݼ�Ȩƽ���֣�</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr> 
</logic:equal>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
   <tr>
     <td align="center" class="text5">
       <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="����">
     </td>
    </tr>      
</table>
</body>
</html>
