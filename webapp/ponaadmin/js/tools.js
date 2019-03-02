/*
 * �������壺
          NUM      ����
          LETTER   ��ĸ
 */
var NUM = "0123456789";
var LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

/*
 * ���һ��form��field�Ƿ�Ϊ��
 * filed ��Ҫ����form�ֶ�
 * name  ���ֶε����ƣ������ڴ�����ʾ��
 */
function checkNull(field,name)
{
  var value = trim(eval(field).value);
  if(value=="")
  {
      alert(name+"����Ϊ�գ����������룡");
     if(eval(field).isDisabled && eval(field).readOnly)
     eval(field).focus();
    return false;
  }
  return true;
}

/*
 * ���һ��form��field�ĳ����Ƿ�Ϸ�
 * filed ��Ҫ����form�ֶ�
 * name  ���ֶε����ƣ������ڴ�����ʾ��
 * min   ���ֶε���С���� 0--��ʾ������С���ȣ�-1��ʾ����
 * max   ���ֶε���󳤶�
 */
function checkLength(field,name,min,max)
{
  var value = eval(field).value;
      value = trim(value);
  var len = getLength(value);
  if((min>0||min==-1)&&len==0)
  {
      alert(name+"�������룡");
    eval(field).focus();
    return false;
  }
  if(len<min||len>max)
  {
      if(min == 0 || min == -1)
      {
            alert(name+"���Ȳ��ܴ���"+max+"���ַ���");
      }
    else
    {
          alert(name+"���ȱ�����"+min+"��"+max+"���ַ�֮�䣡");
    }
    eval(field).focus();
    return false;
  }
  return true;
}
/*
 *������ķ�����ͬ��ֻ����ʾ��Ϣ����
 */
function checkChinLength(field,name,min,max)
{
  var value = eval(field).value;
      value = trim(value);
  var len = getLength(value);
  if((min>0||min==-1)&&len==0)
  {
      alert(name+"�������룡");
    eval(field).focus();
    return false;
  }
  if(len<min||len>max)
  {
      if(min == 0 || min == -1)
      {
            alert(name+"���Ȳ��ܴ���"+max/2+"�����֣�");
      }
    else
    {
          alert(name+"���ȱ�����"+min/2+"��"+max/2+"������֮�䣡");
    }
    eval(field).focus();
    return false;
  }
  return true;
}
/*
 * ���һ������������
 */
function checkContent(field,ctype)
{

    var value = eval(field).value;

    //���Ϊ�գ��������ݼ���
    if( getLength(value) == 0) return true;
    var reg;
    if(ctype=="REG_CHINESE_NUM_LETTER")
    {
        reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    }
    else if(ctype=="REG_CHINESE_NUM_LERTER_COMMA")
    {
        reg = /^[\w,\u4e00-\u9fa5]+$/g
    }
    else if(ctype=="REG_CHINESE_NUM_LERTER_UNDERLINE")
    {
        reg = /^[\w\_\u4e00-\u9fa5]+$/g
    }
    return reg.test(value);
}

/*
 * ����ַ�ͷβ�Ŀո�
 */
function trim(str)
{
    var i=0;
    var i2=-1;
    var chr;
    if (str) {
        for(i=0;i<str.length-1;i++)	{
            chr=str.charAt(i);
            if(chr!=' ')
            break;
        }
        for(i2=str.length-1;i2>=0;i2--)	{
            chr=str.charAt(i2);
            if(chr!=' ')
            break;
        }
    }
    if(i<=i2) {
        return str.substring(i,i2+1);
    } else {
        return "";
    }
}

/*
 * ���һ��char��һ���ַ����г��ֵĴ���
 */
function countChar(str,char)
{
    var count = 0; 
    var chr;
    if (str!="") 
    {
        for(i=0;i<str.length;i++)	{
            chr=str.charAt(i);
            if(chr==char) count++;
        }
    }
    return count;
}

/*
 * ��ȡ�ַ������ȣ������ַ���������
 */
function getLength(strTemp)
{
    var i,sum;
    sum = 0;
    for(i=0;i<strTemp.length;i++)
    {
        if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255))
            sum = sum + 1;
        else
            sum = sum + 2;
    }
    return sum;
}



//��������checkIllegalChar
//���ܽ��ܣ�����ַ������Ƿ��зǷ��ַ�
//����˵����targetStr Ҫ�����ַ���
//          IllegalString �Ƿ��ַ��ļ���
//����ֵ���Ƿ�false��Ϸ�true
function checkIllegalChar(targetStr,IllegalString)
{
       for (var j=0;j<IllegalString.length;j++)
       {
          if (targetStr.indexOf(IllegalString.charAt(j))>=0)
          {
            return false;
        }
    }
    return true;
}

/*
 * ���һ���ַ�����ֻ�ܰ������ֺ���ĸ��
 */
function checkCharOrDigital(targetStr)
{
  var valid = NUM+LETTER;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))<0)
    {
      return false;
    }
  }
  return true;
}
/*
 * hucy:���һ���ַ�����ֻ�ܰ�������,��ĸ,_��
 * ���ϲ���ϵͳ����
 */
function checkForFS(targetStr)
{
  var valid = NUM+LETTER+"_";
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))<0)
    {
      return false;
    }
  }
  return true;
}
/*
 * hucy:���һ���ַ���,���ܰ���HTML�������ַ���
 * Ŀǰ���ڱ����ļ��
 */
function checkForFSH(targetStr)
{
  var valid ="<>&'\\\"#";
  var flag=false;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))>=0)
    {
      flag=true;
      break;
    }
  }
  return flag;
}


/*
 * ���һ���ַ�����ֻ�ܰ�������
 */
function checkDigital(targetStr)
{
  var valid = NUM;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))<0)
    {
      return false;
    }
  }
  return true;
}

/*
 * ���һ��form��field������������
 * filed ��Ҫ����form�ֶ�
 * name  ���ֶε����ƣ������ڴ�����ʾ��
 */
function checkInteger(field,name)
{
  var value = eval(field).value;
  var RegularExp=/^[0-9]*$/g
  if(!RegularExp.test(value))
  {
    alert(name+"����Ϊ������");
    eval(field).focus();
    return false;
  }
  return true;
}


/*
 * hucy:���һ���ַ�����ֻ�ܰ�����ĸ
 */
function checkChar(targetStr)
{
  var valid = LETTER;
  for (i = 0;i<=(targetStr.length-1);i++)
  {
    if (valid.indexOf(targetStr.charAt(i))<0)
    {
      return false;
    }
  }
  return true;
}

function chkDate2(sDate){
var r=/\d{4}(?:-\d{1,2}){0,2}/
//������ʽ���ж��Ƿ�Ϊyyyy-mm-dd,yyyy-mm,yyyy��ʽ
if(sDate=="") return true;
if(sDate.match(r)==sDate){
��var arr=sDate.split("-")
��switch(arr.length){
��//���ݲ�ͬ��yyyy-mm-dd,yyyy-mm��ʽ�ж������������Ƿ���ȷ
�� case 3:
����var tmpDate=new Date(arr[0],arr[1],arr[2]);
����if(tmpDate.getMonth()==arr[1] && tmpDate.getFullYear()==arr[0]) return true;
����break;
�� case 2:
���� return false;
����break;
�� default:
����return false;
��}
}
return false;
}


function checkMobile(str) {
    Str=""+str;
    RegularExp1=/^13[0-9]{9}$/
    RegularExp2=/^159[0-9]{8}$/
    if (RegularExp1.test(Str)||RegularExp2.test(Str)) {
        return true;
    }
    else {
        //alert("�ֻ��Ÿ�ʽ����ȷ��Ӧ��Ϊ11λ���ȵ����֣�");
        return false;
    }
}

function checkMail(str)
{
   Str = ""+str;
   var reg = /^[\u0016-\u00ff]+$/g
   if (reg.test(Str))
   {
	   //����Ҫ��һ��@
	   if(str.indexOf("@") == -1)
	   {
	       return false;
	   }
	   return true;
   }else{
       return false;
   }
}


function checkTel(str)
{
   //
   Str = ""+str;
   RegularExp = /^(([0-9]{4}|[0-9]{3})-){0,1}[0-9]*(-([0-9]{3,4})){0,1}$/
   if (RegularExp.test(Str))
   {
       //alert("�绰�����ʽ��");
       return true;
   }else{
       //alert("�绰�����ʽ����");
       return false;
   }
}

function checkFaxFormat(str)
{
   //
   Str = ""+str;
   RegularExp = /^(([0-9]{4}|[0-9]{3})-){0,1}([0-9]{7}|[0-9]{8}){1}(-([0-9]{3,4})){0,1}$/
   if (RegularExp.test(Str))
   {
       //alert("��������ʽ��");
       return true;
   }else{
       //alert("��������ʽ����");
       return false;
   }
}

function checkID_Card(str)
{
   Str = ""+str;
   RegularExp = /^[0-9]{17}[0-9A-Za-z]{1}$|^[0-9]{14}[0-9A-Za-z]{1}$/
   if (RegularExp.test(Str))
   {
       return true;
   }else{
      // alert("���֤�Ÿ�ʽ���ԣ�");
       return false;
   }
}


//���´���
function win(page,nam,wid,hit,top,left,status,toolbar,resize,scroll,menu){
   var  windo=eval('window.open("'+page+'","'+nam+'","status='+status+',toolbar='+toolbar+',resizable='+resize+',scrollbars='+scroll+', menubar='+menu+',width='+wid+',height='+hit+',top='+top+',left='+left+'")');

  }
function  win_pop(url)
  {
window.open (url, 'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')
}

//���������Ƿ���ȷ��������Ϊ������
function myJudgeFormat(sourceStr ,int_num,point_num,alertStr) {
RegularExp_tmp="/^[0-9]{0,"+int_num+"}[.]{1}[0-9]{0,"+point_num+"}$|^[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(sourceStr.value)) {
      return true;
 }
 else {
    alert(alertStr+"��ʽ����ȷ���Ϊ"+int_num+"λ����λ��"+point_num+"λС��λ�����֣�");
    sourceStr.select();
      return false;
 }
}

//���������Ƿ���ȷ������Ϊ������
function JudgeDicimalFormat(sourceStr ,int_num,point_num,alertStr) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+int_num+"}[.]{1}[0-9]{0,"+point_num+"}$|^[+,-]{0,1}[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(sourceStr.value)) {
      return true;
 }
 else {
    alert(alertStr+"��ʽ����ȷ���Ϊ"+int_num+"λ����λ��"+point_num+"λС��λ�����֣�");
    sourceStr.select();
      return false;
 }
}
//����������Ƿ�Ϊʵ��������Ϊ������
function JudgeReal(field ,int_num,name) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(field.value)) {
      return true;
 }
 else {
    alert(name+"��ʽ����ȷ���Ϊ"+int_num+"λ����λ��");
    field.select();
    return false;
 }
}
//��С����������
//decimalNum Ϊ������С�����λ
function roundFloat(sourceValue, decimalNum) {
    if (isNaN(sourceValue)) {
        return sourceValue;
    }
    var multiplyValue=Math.pow(10,parseInt(decimalNum));
    return (Math.round(multiplyValue*sourceValue))/multiplyValue;
}
//��ָ���Ĵ��滻
function funReplaceStr(aSourceStr,toBeReplaed, replaceStr) {
        var  targetStr="";
        aSourceStr=""+aSourceStr;
        var strToPlaceLength=toBeReplaed.length;
        var startIndex=aSourceStr.indexOf(toBeReplaed);
        var leftStr=aSourceStr;
        while (startIndex!=-1) {
            targetStr+=leftStr.suring(startIndex+strToPlaceLength,leftStr.length);
            startIndex=leftStr.indexOf(toBeReplaed);
        }
        return targetStr+leftStr;
}

/*
 * ��������ѡ�����ѡ�����ڵķ�����
 *
 */
function getDate(dateField)
{
  var now = window.showModalDialog("../../js/date.html",dateField,"center:yes;dialogWidth:200px;dialogHeight:220px;resizable: yes; help: no; status: no; scroll: no; ");
  if(now != null)
  {
    dateField.value = now;
  }
}

/*
 * ������Դѡ�����ѡ����Դ�ķ�����
 * resourceIDField����������resourceID��inputԪ��
 */
function getResource(resourceIDField,categoryID,resourceNameField)
{
  var result = window.showModalDialog("../../web/resourcemgr/contentList.do?isClose=true&isModelWindow=true&categoryID="+categoryID,resourceIDField,"center:yes;dialogWidth:600px;dialogHeight:800px;resizable: yes; help: no; status: no; scroll: auto; ");
  if(result != null)
  {
	  var pos = result.indexOf(";");
	  var resourceID = result.substring(0,pos);
	  var resourceName = result.substring(pos+1,result.length);
      resourceIDField.value = resourceID;
	  resourceNameField.value = resourceName;  
  }
}

/*
 * �������ϵ���ѡ�����ѡ�����ϵ�������ķ�����
 * pollIDField����������pollID��inputԪ��
 * pollTitleField����������pollTitle��inputԪ��
 */
function getPoll(pollIDField,pollTitleField,categoryID)
{
  var result = window.showModalDialog("../../web/webresearch/pollAction.do?isClose=true&action=getpollpage&type=all&valid=all&select=selectpoll",pollIDField,"center:yes;dialogWidth:600px;dialogHeight:500px;resizable: yes; help: no; status: no; scroll: auto; ");
  if(result != null)
  {
  	var pos = result.indexOf(";");
  	var pollID = result.substring(0,pos);
  	var pollTitle = result.substring(pos+1,result.length);
    pollIDField.value = pollID;
    pollTitleField.value = pollTitle;
  }
}

/*
 * ����Ȩ��ѡ�����ѡ��Ȩ�޵ķ�����
 * rightIDField����������rightID��inputԪ��
 */
function getRight(rightIDField,rightNameField,rightType)
{
  var right = window.showModalDialog("../../web/rightmanager/chooseRight.do?isClose=true&rightType="+rightType,rightIDField,"center:yes;dialogWidth:600px;dialogHeight:400px;resizable: yes; help: no; status: no; scroll: auto; ");
  if(right != null)
  {
      var idx = right.indexOf("#");
    var rightID = right.substring(0,idx);
    var rightName = right.substring(idx+1,right.length);
    rightIDField.value = rightID;
    rightNameField.value = rightName;
  }
}

/*
 * zhangmin:���һ��������ֻ�ܰ������֡�Ӣ����ĸ������
 * name--����ʾ���г���
 */
function checkStr3(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"ֻ�ܰ������֡�Ӣ����ĸ�����֣����������룡");
    eval(field).focus();
    return false;
    }
}

/*
 * dengjun:���һ��������ֻ�ܰ������ġ���ĸ�����֣�
 * maxportal������ʾ����һ����ϸ΢���
 * name--����ʾ���г���
 */
function checkStr5(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
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

/*
 * maobg:���һ��������ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»���
 * name--����ʾ���г���
 */
function checkStr1(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[\w\-\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߣ����������룡");
    eval(field).focus();
    return false;
    }
}

/*
 * maobg:���һ��������ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߡ������š��ո��ַ�
 * name--����ʾ���г���
 */
function checkStr2(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[\w\s\-,.;����������!\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"ֻ�ܰ������֡�Ӣ����ĸ�����֡���ܡ��»��ߡ������š��ո��ַ������������룡");
    eval(field).focus();
    return false;
    }
}

/*
 * ���һ�������򣬲��ܰ���'"<>\/
 * name--����ʾ���г���
 */
function checkStr4(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var illegalChars = "'\"<>\\/";
    if(checkIllegalChar(value,illegalChars))
    {
      return true;
    }
    else
    {
      alert(name+"���ܰ���' \" < > \\ /���ַ���");
    eval(field).focus();
    return false;
    }
}

/*
 * ���һ�������򣬲��ܰ���,{}
 * name--����ʾ���г���
 */
function checkStr6(field,name)
{
    var value = trim(eval(field).value);
    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var illegalChars = ",{}";
    if(checkIllegalChar(value,illegalChars))
    {
      return true;
    }
    else
    {
      alert(name+"���ܰ��� ,{} ���ַ���");
    eval(field).focus();
    return false;
    }
}

//����һ��form���������ȷ�ϡ�
function resetForm(form)
{
    if(confirm("��ȷ��Ҫ�������е�����������д��"))
    {
        form.reset();
    }
}

//���һ��form�����������
function clearForm(form)
{
  if(!confirm("���Ƿ�ȷ��Ҫ������е������"))
  {
      return false;
  }
  for(var i=0;i<form.length;i++)
  {
    if(form.elements[i].type=="text")
    {
      form.elements[i].value="";
    }
    if(form.elements[i].type=="password")
    {
      form.elements[i].value="";
    }
    if(form.elements[i].type.indexOf("select")!=-1)
    {
      form.elements[i].selectedIndex = 0;
    }
  }
  return true;
}

function haveChooseChk(form,name)
{
  var choosed = false;
  for(var i=0;i<form.length;i++)
  {
    if((form.elements[i].type=="checkbox" || form.elements[i].type=="radio") && form.elements[i].name == name)
    {
      if(form.elements[i].checked)
      {
        choosed = true;
        break;
      }
    }
  }
  return choosed;
}
//ȫѡcheckbox��
function selectAll(form,name)
{
  for(var i=0;i<form.length;i++)
  {
    if(form.elements[i].type=="checkbox" && form.elements[i].name == name)
    {
    form.elements[i].checked=true;
    }
  }
  return true;
}
/*
 * gaoyuan:ֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ�����š�
 * name--����ʾ���г���
 */
function checkStr_8(field,name)
{
    var value = trim(eval(field).value);

    
    if( getLength(value) == 0)
        return true;

    var reg = /^[?,a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+'ֻ�ܰ������֡�Ӣ����ĸ�����ֺͷָ���","��');
    eval(field).focus();
    return false;
    }
}
//ȫѡ����ȫ��ѡһ��form��checkbox��
//aForm            :  form������
//checkFiledName   :  ָ����Ҫ��������checkbox�����ƣ������""��ʾ��ָ������
//isCheck          :  true��ʾȫѡ��false��ʾȫ��ѡ
//needConfirm      :  �Ƿ���Ҫ�û���ȷ��
function selectAllCB(aForm,checkFieldName,isCheck,needConfirm)
{
  if(needConfirm)
  {
    var msg = "��ȷ��Ҫȫѡ��";
    if(!isCheck) msg = "��ȷ��Ҫȫ��ѡ��";
    if(!confirm(msg))
    {
      return false;
    }
  }	
  for(var i=0;i<aForm.length;i++)
  {
    if(aForm.elements[i].type=="checkbox")
    {
      if(checkFieldName=="" || aForm.elements[i].name == checkFieldName)
      {
          aForm.elements[i].checked = isCheck
      }	
    }
  }
}


/*
 * ����:���һ�������򣬵�һ����ĸ�Ƿ�ΪӢ��
 * name--����ʾ���г���
 */
function checkFirstLetter(name)
{
    var value = trim(name.substring(0,1));
    var reg = /[a-z]/gi
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      return false;
    }
}
/*
 * gaoyuan:���һ��������ֻ�ܰ������֡�Ӣ����ĸ������
 * name--����ʾ���г���
 */
function checkStr9(field,name)
{
    var value = trim(eval(field).value);

    //���Ϊ�գ��򲻴����ݼ���
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"ֻ�ܰ������֡�Ӣ����ĸ�����֣�");
    eval(field).focus();
    return false;
    }
}



/*
 * ���һ��form��field������������
 * filed ��Ҫ����form�ֶ�
 * name  ���ֶε����ƣ������ڴ�����ʾ��
 */
function JudgeReal2(field ,name,maxNum) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+maxNum+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(field.value)) {
      return true;
 }
 else {
    alert("��ʽ����ȷ��"+name+"����Ϊ������");
    field.select();
    return false;
 }
}


/*
 * pas_076path����
 * ���name�ַ����Ƿ�����Ǻ�,
 * ���Ǻŵ�name����Ϊ��Ӫ����
 * name  ��Ҫ��������
 *  ������Ϊ��Ӫ���ݷ��࣬��Ϊ�������ݷ���
 */
function isOperatingType(name)
{
   if(name.indexOf("*") == -1)
   {
	//�������ݷ���
     return false;
   }
   //��Ӫ���ݷ���
  return true;
}
/*

 * y: ��ʾ��

 * M����ʾһ���е��·� 1~12

 * d: ��ʾ�·��е����� 1~31

 * H����ʾһ���е�Сʱ�� 00~23

 * m: ��ʾСʱ�еķ����� 00~59

 * s: ��ʾ�����е�����   00~59

 */

var DateFormat = function(){

   this.initialize();

}

DateFormat.prototype = {

    DEFAULT_DATE_FORMAT: "yyyy-MM-dd",

DEFAULT_MONTH_FORMAT: "yyyy-MM",

DEFAULT_YEAR_FORMAT: "yyyy",

DEFAULT_TIME_FORMAT: "HH:mm:ss",

DEFAULT_DATETIME_FORMAT: "yyyy-MM-dd HH:mm:ss",

    DEFAULT_YEAR: "YEAR",

DEFAULT_MONTH: "MONTH",

DEFAULT_DATE: "DATE",


initialize: function(){

 this.curDate = new Date();

},


/**

*���ݸ���������ʱ���ʽ����ʽ����ǰ����

* @params strFormat ��ʽ���ַ����� �磺"yyyy-MM-dd" 

* @return ���ظ��ݸ�����ʽ���ַ�����ʾ��ʱ�����ڸ�ʽ<br>

*         �������ĸ�ʽ�����Ϲ涨�򷵻�ԭ�ַ�����ʽ

*/

curdateToString: function(strFormat){

  var dates = this.getDateObject(this.curDate);

  if(/(y+)/.test(strFormat)){

     var fullYear = this.curDate.getFullYear() + "";

     var year = RegExp.$1.length == 4? fullYear: fullYear.substr(4 - RegExp.$1.length);

 strFormat = strFormat.replace(RegExp.$1, year);

  }

  for(var i in dates){

     if(new RegExp("(" + i + ")").test(strFormat)){

   var target = RegExp.$1.length == 1? dates[i]: ("0" + dates[i]).substr(("" + dates[i]).length - 1);

   strFormat = strFormat.replace(RegExp.$1, target);

 }

  }

  return strFormat;

},


/**

*���ݸ����ĸ�ʽ���Ѹ�����ʱ����и�ʽ��

*@params date ����ֵ �������ַ�����ʾ���������������ͣ��磺'2010-05-28'�� new Date();

*@params strFormat Ҫ�õ������ڵĸ�ʽ�ĸ�ʽ���ַ������磺'yyyy-MM-dd'

*@return ���ݹ涨��ʽ��ʱ���ʽ

*/

format: function(date, strFormat){

 try{

if(typeof date == "string"){

   this.curDate = this.parseDate(date, strFormat);

}else if(date instanceof Date){

   this.curDate = date;

}

return this.curdateToString(strFormat);

 }catch(e){

 }

},


/**

*���ݸ����ĸ�ʽ�Ը������ַ�������ʱ����н�����

*@params strDate Ҫ���������ڵ��ַ�����ʾ

*@params strFormat �����������ڵ�˳��

*@return ���ؽ������Date���͵�ʱ��<br>

*        �����ܽ����򷵻ص�ǰ����<br>

*        ������Ϊʱ���ʽ �򷵻ص�����Ϊ 1970��1��1�յ�����

*/

parseDate: function(strDate, strFormat){

   if(typeof strDate != "string"){

 return new Date();

}

var longTime = Date.parse(strDate);

if(isNaN(longTime)){

  var tmpDate = new Date();

  var regFormat = /(\w{4})|(\w{2})|(\w{1})/g;

  var regDate = /(\d{4})|(\d{2})|(\d{1})/g;

  var formats = strFormat.match(regFormat);

  var dates = strDate.match(regDate);

  if(formats !== null && formats !== null && formats.length == dates.length){

  for(var i = 0; i < formats.length; i++){

    var format = formats[i];

 if(format === "yyyy"){

    tmpDate.setFullYear(parseInt(dates[i], 10));

 }else if(format == "yy"){

    var prefix = (tmpDate.getFullYear() + "").substring(0, 2);

var year = (parseInt(dates[i], 10) + "").length == 4? parseInt(dates[i], 10): prefix + (parseInt(dates[i], 10) + "00").substring(0, 2);

var tmpYear = parseInt(year, 10);

tmpDate.setFullYear(tmpYear);

 }else if(format == "MM" || format == "M"){

    tmpDate.setMonth(parseInt(dates[i], 10) - 1);

 }else if(format == "dd" || format == "d"){

    tmpDate.setDate(parseInt(dates[i], 10));

 }else if(format == "HH" || format == "H"){

    tmpDate.setHours(parseInt(dates[i], 10));

 }else if(format == "mm" || format == "m"){

    tmpDate.setMinutes(parseInt(dates[i], 10));

 }else if(format == "ss" || format == "s"){

    tmpDate.setSeconds(parseInt(dates[i], 10));

 }

  }

  return tmpDate;

 }

 return tmpDate;

}else{

  return new Date(longTime);

}

},


/**

*���ݸ�����ʱ�������ͼ����ֵ���Ը����ĸ�ʽ�Ը�����ʱ����м��㲢��ʽ������

*@params date ������ʱ��

*@params interval ʱ���������磺"YEAR"��"MONTH"�� "DATE", �����ִ�Сд

*@params amount ʱ����ֵ�����������͸���

*@params strFormat ʱ���ʽ

*@return ���ؼ��㲢��ʽ�����ʱ����ַ���

*/

changeDate: function(date, interval, amount, strFormat){

    var tmpdate = new Date();

   if(typeof date == "string"){

  tmpdate = this.parseDate(date, strFormat);

}else if(date instanceof Date){

  tmpdate = date;

}

var field  =  (typeof interval == "string")? interval.toUpperCase(): "DATE";


if(!(typeof amount == "number")){

  amount = 0;

}


if(field == this.DEFAULT_YEAR){

  tmpdate.setFullYear(tmpdate.getFullYear() + amount);

}else if(field == this.DEFAULT_MONTH){

  tmpdate.setMonth(tmpdate.getMonth() + amount);

}else if(field == this.DEFAULT_DATE){

  tmpdate.setDate(tmpdate.getDate() + amount);

} 

        this.curDate = tmpdate;

return this.curdateToString(strFormat);

},


/**

*�Ƚ��������ڵĲ��

*@param date1 Date���͵�ʱ��

*@param date2 Dete ���͵�ʱ��

*@param isFormat boolean �Ƿ�Եó���ʱ����и�ʽ��,<br> 

*       false:���غ�������true�����ظ�ʽ���������

*@return ������������֮��ĺ����� �����Ǹ�ʽ����Ľ��

*/

compareTo: function(date1, date2, isFormat){

  try{

    var len = arguments.length;

var tmpdate1 = new Date();

var tmpdate2 = new Date();

if(len == 1){

   tmpdate1 = date1;

}else if(len == 2){

  tmpdate1 = date1;

  tmpdate2 = date2;

}

    if(!(tmpdate1 instanceof Date) || !(tmpdate2 instanceof Date)){

    alert("��������ȷ�Ĳ�����");

   return 0;

}else{

    var time1 = tmpdate1.getTime(); 

var time2 = tmpdate2.getTime();

    var time = Math.max(time1, time2) - Math.min(time1, time2);

if(!isNaN(time) && time > 0){

   if(isFormat){

  var date = new Date(time);

  var result = "";

  result += (date.getFullYear() - 1970) > 0? (date.getFullYear() - 1970) + "��":"";

  result += (date.getMonth() - 1) > 0? (date.getMonth() - 1) + "��": "";

  result += (date.getDate() - 1) > 0? (date.getDate() - 1) + "��": "";

  result += (date.getHours() - 8) > 0? (date.getHours() - 1) + "Сʱ": "";

  result += date.getMinutes() > 0? date.getMinutes() + "����": "";

  result += date.getSeconds() > 0? date.getSeconds() + "��": "";

  return result;

}else {

 return time;

}

}else{

  return 0;

}

}

  }catch(e){

   alert(e.message);

  }

},


/**

*���ݸ��������ڵõ����ڵ��£��գ�ʱ���ֺ���Ķ���

*@params date ���������� dateΪ��Date���ͣ� ���ȡ��ǰ����

*@return �и������ڵ��¡��ա�ʱ���ֺ�����ɵĶ���

*/

getDateObject: function(date){

     if(!(date instanceof Date)){

   date = new Date();

 }

    return {

"M+" : date.getMonth() + 1, 

                "d+" : date.getDate(),   

                "H+" : date.getHours(),   

                "m+" : date.getMinutes(), 

                "s+" : date.getSeconds()

     };
}
}