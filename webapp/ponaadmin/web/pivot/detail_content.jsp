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
<title>营销体验门户管理平台</title>
<link href="../../css/common2.css" rel="stylesheet" type="text/css">
</head>
<script language="JavaScript">
</script>
<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td align="center" class="title1">重点内容详细信息</td>
  </tr>
</table>
<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
<logic:equal name="content" property="type" value = "nt:gcontent:colorring">
  <tr>
    <td nowrap align="right" class="text3">内容名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">铃音编码：</td>
    <td class="text4"><bean:write name="content" property="contentID"/>&nbsp;
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">价格：</td>
    <td class="text4"><bean:write name="content" property="price_Y"/>&nbsp;元
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">歌手：</td>
    <td class="text4"><bean:write name="content" property="singer"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">有效期：</td>
    <td class="text4"><bean:write name="content" property="displayExpire"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">下载次数：</td>
    <td class="text4"><bean:write name="content" property="downloadtimes"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">设置次数：</td>
    <td class="text4"><bean:write name="content" property="settimes"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">试听地址：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="auditionUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">播放时长（单位：毫秒）：</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>        
</logic:equal>  

<logic:equal name="content" property="type" value = "nt:gcontent:audio">
  <tr>
    <td width="30%" align="right" class="text3">歌曲名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">歌曲ID：</td>
    <td class="text4"><bean:write name="content" property="contentID"/>&nbsp;
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">歌手：</td>
    <td class="text4"><bean:write name="content" property="singer"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">播放时长（单位：毫秒）：</td>
    <td class="text4"><bean:write name="content" property="averageMark"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">aac文件试听地址：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="aacAuditionUrl"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">mp3文件试听地址：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="mp3AuditionUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">歌词URL：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="lrcURL"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">风格流派分类：</td>
    <td class="text4"><bean:write name="content" property="cateName"/>
    </td>
  </tr>  
  <tr>
    <td align="right" class="text3">歌曲语言分类：</td>
    <td class="text4"><bean:write name="content" property="audioLanguage"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">歌手地区分类：</td>
    <td class="text4"><bean:write name="content" property="singerZone"/>
    </td>
  </tr>           
</logic:equal>

<logic:equal name="content" property="type" value = "nt:gcontent:news">
  <tr>
    <td align="right" class="text3">媒体ID：</td>
    <td class="text4"><bean:write name="content" property="contentID"/>
    </td>
  </tr> 
  <tr>
    <td width="30%" align="right" class="text3">媒体名称：</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">媒体图标url：</td>
    <td class="text4"><bean:write name="content" property="iconUrl"/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">媒体链接url：</td>
    <td class="text4"><bean:write name="content" property="mediaUrl"/>
    </td>
  </tr>   
  <tr>
    <td align="right" class="text3">头条id：</td>
    <td class="text4"><bean:write name="content" property="contentTag"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">头条内容标题：</td>
    <td class="text4"><bean:write name="content" property="title"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">头条图片url：</td>
    <td class="text4"><bean:write name="content" property="imageUrl"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">内容正文：</td>
    <td class="text4"><bean:write name="content" property="introduction"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">头条发布时间：</td>
    <td class="text4"><bean:write name="content" property="marketDate"/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">头条内容url：</td>
    <td style="word-break:break-all" class="text4"><bean:write name="content" property="hotUrl"/>
    </td>
  </tr>        
</logic:equal>

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

<table width="95%"  border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
   <tr>
     <td align="center" class="text5">
       <input name="button2" type="button" class="input1"  onClick="history.go(-1);" value="返回">
     </td>
    </tr>      
</table>
</body>
</html>
