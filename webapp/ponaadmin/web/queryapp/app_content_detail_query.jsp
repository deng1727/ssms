<%@ page contentType="text/html; charset=gb2312" %>
<%@ taglib uri="right" prefix="right" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-nested" prefix="nested" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
<base target="_self">
</head>

<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">应用详细信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">

<logic:equal name="content" property="type" value = "nt:gcontent:appGame">
  <tr>
    <td width="30%" align="right" class="text3">内容名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">业务内容分类：</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">提供商：</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP企业代码：</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">业务代码：</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">提供范围：</td>
    <td class="text4"> <logic:equal value="G" name="content" property="servAttr">全网</logic:equal>
                       <logic:equal value="L" name="content" property="servAttr">省内</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">操作指南：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="handBook"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">内容标识：</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">游戏视频地址：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="gameVideo"/>
    </td>
  </tr>    
  <tr>
    <td align="right" class="text3">适用机型：</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容搜索次数：</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容浏览次数：</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容订购次数：</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">内容评论次数：</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容评分次数：</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容推荐次数：</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容收藏次数：</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容加权平均分：</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>    
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:appTheme">
  <tr>
    <td  align="right"  nowrap class="text3">内容名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" nowrap class="text3">业务内容分类：</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">提供商：</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP企业代码：</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">业务代码：</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">提供范围：</td>
    <td class="text4"> <logic:equal value="G" name="content" property="servAttr">全网</logic:equal>
                       <logic:equal value="L" name="content" property="servAttr">省内</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">LOGO1图片地址：</td>
    <td class="text4"><bean:write name="content" property="LOGO1"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容标识：</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">适用机型：</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
    <tr>
    <td align="right" class="text3">内容搜索次数：</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容浏览次数：</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容订购次数：</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">内容评论次数：</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容评分次数：</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容推荐次数：</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容收藏次数：</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容加权平均分：</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr> 
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:appSoftWare">
  <tr>
    <td nowrap align="right" class="text3">内容名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">业务内容分类：</td>
    <td class="text4"><bean:write name="content" property="cateName"/></td>
  </tr>  
  <tr>
    <td align="right" class="text3">提供商：</td>
    <td class="text4"><bean:write name="content" property="spName"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">SP企业代码：</td>
    <td class="text4"><bean:write name="content" property="icpCode"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">业务代码：</td>
    <td class="text4"><bean:write name="content" property="icpServId"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">提供范围：</td>
    <td class="text4"><logic:equal value="G" name="content" property="servAttr">全网</logic:equal>
                      <logic:equal value="L" name="content" property="servAttr">省内</logic:equal>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">使用手册的文件地址：</td>
    <td class="text4"><bean:write name="content" property="manual"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">适用机型：</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="formatDeviceName" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="formatDeviceName"/></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容标识：</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">内容搜索次数：</td>
    <td class="text4"><bean:write name="content" property="searchTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容浏览次数：</td>
    <td class="text4"><bean:write name="content" property="scanTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容订购次数：</td>
    <td class="text4"><bean:write name="content" property="orderTimes"/>
    </td>
  </tr> 
    <tr>
    <td align="right" class="text3">内容评论次数：</td>
    <td class="text4"><bean:write name="content" property="commentTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容评分次数：</td>
    <td class="text4"><bean:write name="content" property="markTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容推荐次数：</td>
    <td class="text4"><bean:write name="content" property="commendTimes"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">内容收藏次数：</td>
    <td class="text4"><bean:write name="content" property="collectTimes"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容加权平均分：</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr> 
</logic:equal>

  <tr>
    <td align="right" class="text3">内容标签：</td>
    <td style="word-break:break-all" class="text4">
    	<textarea name="contentTag" style="width:100%; height:100; overflow:auto" readonly="readonly" class="textareareadonley"><bean:write name="content" property="keywordsFormat"/></textarea>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">ID：</td>
    <td class="text4"><bean:write name="content" property="id"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">内容ID：</td>
    <td class="text4"><bean:write name="contentId"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">上线时间：</td>
    <td class="text4"><bean:write name="content" property="marketDate"/>
    </td>
  </tr>
  
  <tr>
    <td align="right" class="text3">平台：</td>
    <td class="text4"><bean:write name="content" property="platform"/>
    </td>
  </tr>

</table>

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
   <tr>
     <td align="center" class="text5">
       <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回">
     </td>
    </tr>      
</table>

</body>
</html>
