<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>Ӫ�������Ż�����ƽ̨</title>
		<link href="../../css/common2.css" rel="stylesheet" type="text/css">
		<script language="Javascript" type="text/javascript"
			src="../../js/tools.js"></script>
		<script language="JavaScript" type="text/javascript">
<!--
function checkForm(form)
{ 
  if(!checkTag(form.tag,"���ݱ�ǩ")) return false;	
  return true;
}
function checkTag(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[\n\ra-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"ֻ�ܰ������ġ���ĸ�����֣�");
    eval(field).focus();
    return false;
    }
}
-->
</script>
	</head>
	<body bgcolor="#F0FBFD" leftmargin="0" topmargin="5">
		<bean:parameter id="action" name="action" value="" />
		<table width="95%" border="0" align="center" cellspacing="1">
			<tr>
				<td>
					����λ�ã�
					<bean:write name="category" property="namePath" />
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellspacing="1"
			bgcolor="#FFFFFF">
			<tr>
				<td align="center" class="title1">�޸����ݱ�ǩ</td>
			</tr>
		</table>
		<form name="contentForm" action="contentSave.do" onSubmit="return checkForm(contentForm);">
		<table width="95%" border="0" align="center" cellspacing="1" bgcolor="#FFFFFF">
			<logic:equal parameter="action" value="update">
				<input name="categoryID" type="hidden" value="<bean:write name="category" property="id"/>">
				<input name="contentID" type="hidden" value="<bean:write name="content" property="id"/>">
				<input name="type" type="hidden" value="<bean:write name="content" property="type"/>">

<logic:equal name="content" property="type" value = "nt:gcontent:colorring">
  <tr>
    <td width="30%" align="right" class="text3">�������ƣ�</td>
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
    <td class="text4"><bean:write name="content" property=contentID/>
    </td>
  </tr> 
  <tr>
    <td width="30%" align="right" class="text3">ý�����ƣ�</td>
    <td width="70%" class="text4"><bean:write name="content" property="name"/></td>
  </tr>
  <tr>
    <td align="right" class="text3">ý��ͼ��url��</td>
    <td class="text4"><bean:write name="content" property=iconUrl/>
    </td>
  </tr> 
  <tr>
    <td align="right" class="text3">ý������url��</td>
    <td class="text4"><bean:write name="content" property=mediaUrl/>
    </td>
  </tr>   
  <tr>
    <td align="right" class="text3">ͷ��id��</td>
    <td class="text4"><bean:write name="content" property=contentTag/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ�����ݱ��⣺</td>
    <td class="text4"><bean:write name="content" property=title/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ��ͼƬurl��</td>
    <td class="text4"><bean:write name="content" property=imageUrl/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">�������ģ�</td>
    <td class="text4"><bean:write name="content" property=introduction/>
    </td>
  </tr>
  <tr>
    <td align="right" class="text3">ͷ������ʱ�䣺</td>
    <td class="text4"><bean:write name="content" property=marketDate/>
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
</logic:equal>
<logic:equal name="content" property="type" value = "nt:gcontent:appTheme">
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
    <td align="right" class="text3">LOGO1ͼƬ��ַ��</td>
    <td class="text4"><bean:write name="content" property="LOGO1"/>
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
</logic:equal>
<logic:equal name="content" property="type" value = "nt:gcontent:appSoftWare">
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
    <td align="right" class="text3">ʹ���ֲ���ļ���ַ��</td>
    <td class="text4"><bean:write name="content" property="manual"/>
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
</logic:equal>				
					<tr>
						<td align="right" class="text3">
							���ݱ�ǩ��
						</td>
						<td style="word-break: break-all" class="text4">
							<textarea name="tag"
								style="width: 100%; height: 100; overflow: auto"><bean:write name="content" property="keywordsFormat" /></textarea>
						</td>
					</tr>
					<tr>
						<td align="right" class="text3">
							����ID��
						</td>
						<td class="text4">
							<bean:write name="content" property="id" />
						</td>
					</tr>
					<tr>
						<td align="right" class="text3">
							����ʱ�䣺
						</td>
						<td class="text4">
							<bean:write name="content" property="createDate" />
						</td>
					</tr>
				</logic:equal>
			<tr>
				<td align="center" class="text5" colspan="2">
					<input name="Submit" type="submit" class="input1" value="ȷ��">
					<input name="button" type="button" class="input1"
						onClick="history.go(-1);" value="����">
				</td>
			</tr>
		</table>
      </form>
	</body>
</html>
