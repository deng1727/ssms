/*
 * 常量定义：
          NUM      数字
          LETTER   字母
 */
var NUM = "0123456789";
var LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

/*
 * 检查一个form的field是否为空
 * filed ：要检查的form字段
 * name  ：字段的名称，出现在错误提示中
 */
function checkNull(field,name)
{
  var value = trim(eval(field).value);
  if(value=="")
  {
      alert(name+"不能为空！请重新输入！");
     if(eval(field).isDisabled && eval(field).readOnly)
     eval(field).focus();
    return false;
  }
  return true;
}

/*
 * 检查一个form的field的长度是否合法
 * filed ：要检查的form字段
 * name  ：字段的名称，出现在错误提示中
 * min   ：字段的最小长度 0--表示忽略最小长度，-1表示必填
 * max   ：字段的最大长度
 */
function checkLength(field,name,min,max)
{
  var value = eval(field).value;
      value = trim(value);
  var len = getLength(value);
  if((min>0||min==-1)&&len==0)
  {
      alert(name+"必须输入！");
    eval(field).focus();
    return false;
  }
  if(len<min||len>max)
  {
      if(min == 0 || min == -1)
      {
            alert(name+"长度不能大于"+max+"个字符！");
      }
    else
    {
          alert(name+"长度必须在"+min+"和"+max+"个字符之间！");
    }
    eval(field).focus();
    return false;
  }
  return true;
}
/*
 *与上面的方法相同，只是提示信息变了
 */
function checkChinLength(field,name,min,max)
{
  var value = eval(field).value;
      value = trim(value);
  var len = getLength(value);
  if((min>0||min==-1)&&len==0)
  {
      alert(name+"必须输入！");
    eval(field).focus();
    return false;
  }
  if(len<min||len>max)
  {
      if(min == 0 || min == -1)
      {
            alert(name+"长度不能大于"+max/2+"个汉字！");
      }
    else
    {
          alert(name+"长度必须在"+min/2+"和"+max/2+"个汉字之间！");
    }
    eval(field).focus();
    return false;
  }
  return true;
}
/*
 * 检查一个输入框的内容
 */
function checkContent(field,ctype)
{

    var value = eval(field).value;

    //如果为空，则不做内容检验
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
 * 清空字符头尾的空格。
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
 * 检查一个char在一个字符串中出现的次数
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
 * 获取字符串长度，中文字符当成两个
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



//函数名：checkIllegalChar
//功能介绍：检查字符串中是否有非法字符
//参数说明：targetStr 要检查的字符串
//          IllegalString 非法字符的集合
//返回值：非法false或合法true
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
 * 检查一个字符串，只能包含数字和字母。
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
 * hucy:检查一个字符串，只能包含数字,字母,_。
 * 符合操作系统规则
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
 * hucy:检查一个字符串,不能包含HTML的特殊字符。
 * 目前用于别名的检查
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
 * 检查一个字符串，只能包含数字
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
 * 检查一个form的field，必须是整数
 * filed ：要检查的form字段
 * name  ：字段的名称，出现在错误提示中
 */
function checkInteger(field,name)
{
  var value = eval(field).value;
  var RegularExp=/^[0-9]*$/g
  if(!RegularExp.test(value))
  {
    alert(name+"必须为整数！");
    eval(field).focus();
    return false;
  }
  return true;
}


/*
 * hucy:检查一个字符串，只能包含字母
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
//正则表达式，判断是否为yyyy-mm-dd,yyyy-mm,yyyy格式
if(sDate=="") return true;
if(sDate.match(r)==sDate){
　var arr=sDate.split("-")
　switch(arr.length){
　//根据不同的yyyy-mm-dd,yyyy-mm格式判断年月日数字是否正确
　 case 3:
　　var tmpDate=new Date(arr[0],arr[1],arr[2]);
　　if(tmpDate.getMonth()==arr[1] && tmpDate.getFullYear()==arr[0]) return true;
　　break;
　 case 2:
　　 return false;
　　break;
　 default:
　　return false;
　}
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
        //alert("手机号格式不正确！应该为11位长度的数字！");
        return false;
    }
}

function checkMail(str)
{
   Str = ""+str;
   var reg = /^[\u0016-\u00ff]+$/g
   if (reg.test(Str))
   {
	   //至少要有一个@
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
       //alert("电话号码格式对");
       return true;
   }else{
       //alert("电话号码格式不对");
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
       //alert("传真号码格式对");
       return true;
   }else{
       //alert("传真号码格式不对");
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
      // alert("身份证号格式不对！");
       return false;
   }
}


//打开新窗口
function win(page,nam,wid,hit,top,left,status,toolbar,resize,scroll,menu){
   var  windo=eval('window.open("'+page+'","'+nam+'","status='+status+',toolbar='+toolbar+',resizable='+resize+',scrollbars='+scroll+', menubar='+menu+',width='+wid+',height='+hit+',top='+top+',left='+left+'")');

  }
function  win_pop(url)
  {
window.open (url, 'newwindow', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no')
}

//检验数字是否正确（不可以为负数）
function myJudgeFormat(sourceStr ,int_num,point_num,alertStr) {
RegularExp_tmp="/^[0-9]{0,"+int_num+"}[.]{1}[0-9]{0,"+point_num+"}$|^[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(sourceStr.value)) {
      return true;
 }
 else {
    alert(alertStr+"格式不正确！最长为"+int_num+"位整数位，"+point_num+"位小数位的数字！");
    sourceStr.select();
      return false;
 }
}

//检验数字是否正确（可以为负数）
function JudgeDicimalFormat(sourceStr ,int_num,point_num,alertStr) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+int_num+"}[.]{1}[0-9]{0,"+point_num+"}$|^[+,-]{0,1}[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(sourceStr.value)) {
      return true;
 }
 else {
    alert(alertStr+"格式不正确！最长为"+int_num+"位整数位，"+point_num+"位小数位的数字！");
    sourceStr.select();
      return false;
 }
}
//检验输入的是否为实数（可以为负数）
function JudgeReal(field ,int_num,name) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+int_num+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(field.value)) {
      return true;
 }
 else {
    alert(name+"格式不正确！最长为"+int_num+"位整数位！");
    field.select();
    return false;
 }
}
//将小数四舍五入
//decimalNum 为：保留小数点后几位
function roundFloat(sourceValue, decimalNum) {
    if (isNaN(sourceValue)) {
        return sourceValue;
    }
    var multiplyValue=Math.pow(10,parseInt(decimalNum));
    return (Math.round(multiplyValue*sourceValue))/multiplyValue;
}
//用指定的串替换
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
 * 弹出日期选择框来选择日期的方法。
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
 * 弹出资源选择框来选择资源的方法。
 * resourceIDField，用来保存resourceID的input元素
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
 * 弹出网上调查选择框来选择网上调查主题的方法。
 * pollIDField，用来保存pollID的input元素
 * pollTitleField，用来保存pollTitle的input元素
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
 * 弹出权限选择框来选择权限的方法。
 * rightIDField，用来保存rightID的input元素
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
 * zhangmin:检查一个输入域，只能包含汉字、英文字母、数字
 * name--在提示语中出现
 */
function checkStr3(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"只能包含汉字、英文字母、数字！请重新输入！");
    eval(field).focus();
    return false;
    }
}

/*
 * dengjun:检查一个输入域，只能包含中文、字母、数字！
 * maxportal二期提示语与一期有细微差别！
 * name--在提示语中出现
 */
function checkStr5(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"只能包含中文、字母、数字！");
    eval(field).focus();
    return false;
    }
}

/*
 * maobg:检查一个输入域，只能包含汉字、英文字母、数字、横杠、下划线
 * name--在提示语中出现
 */
function checkStr1(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var reg = /^[\w\-\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"只能包含汉字、英文字母、数字、横杠、下划线！请重新输入！");
    eval(field).focus();
    return false;
    }
}

/*
 * maobg:检查一个输入域，只能包含汉字、英文字母、数字、横杠、下划线、标点符号、空格字符
 * name--在提示语中出现
 */
function checkStr2(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var reg = /^[\w\s\-,.;、，。；！!\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"只能包含汉字、英文字母、数字、横杠、下划线、标点符号、空格字符！请重新输入！");
    eval(field).focus();
    return false;
    }
}

/*
 * 检查一个输入域，不能包含'"<>\/
 * name--在提示语中出现
 */
function checkStr4(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var illegalChars = "'\"<>\\/";
    if(checkIllegalChar(value,illegalChars))
    {
      return true;
    }
    else
    {
      alert(name+"不能包含' \" < > \\ /等字符！");
    eval(field).focus();
    return false;
    }
}

/*
 * 检查一个输入域，不能包含,{}
 * name--在提示语中出现
 */
function checkStr6(field,name)
{
    var value = trim(eval(field).value);
    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var illegalChars = ",{}";
    if(checkIllegalChar(value,illegalChars))
    {
      return true;
    }
    else
    {
      alert(name+"不能包含 ,{} 等字符！");
    eval(field).focus();
    return false;
    }
}

//重置一个form的输入项的确认。
function resetForm(form)
{
    if(confirm("您确定要重置所有的输入重新填写吗？"))
    {
        form.reset();
    }
}

//清空一个form的所有输入框
function clearForm(form)
{
  if(!confirm("您是否确定要清空所有的输入项？"))
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
//全选checkbox框
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
 * gaoyuan:只能包含汉字、英文字母、数字和分割符逗号。
 * name--在提示语中出现
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
      alert(name+'只能包含汉字、英文字母、数字和分隔符","。');
    eval(field).focus();
    return false;
    }
}
//全选还是全不选一个form的checkbox框
//aForm            :  form的名称
//checkFiledName   :  指定需要被操作的checkbox的名称，如果传""表示不指定名称
//isCheck          :  true表示全选，false表示全不选
//needConfirm      :  是否需要用户的确认
function selectAllCB(aForm,checkFieldName,isCheck,needConfirm)
{
  if(needConfirm)
  {
    var msg = "您确定要全选吗？";
    if(!isCheck) msg = "您确定要全不选吗？";
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
 * 张敏:检查一个输入域，第一个字母是否为英文
 * name--在提示语中出现
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
 * gaoyuan:检查一个输入域，只能包含汉字、英文字母、数字
 * name--在提示语中出现
 */
function checkStr9(field,name)
{
    var value = trim(eval(field).value);

    //如果为空，则不错内容检验
    if( getLength(value) == 0)
        return true;

    var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/g
    if(reg.test(value))
    {
      return true;
    }
    else
    {
      alert(name+"只能包含汉字、英文字母、数字！");
    eval(field).focus();
    return false;
    }
}



/*
 * 检查一个form的field，必须是整数
 * filed ：要检查的form字段
 * name  ：字段的名称，出现在错误提示中
 */
function JudgeReal2(field ,name,maxNum) {
RegularExp_tmp="/^[+,-]{0,1}[0-9]{0,"+maxNum+"}$/";
 RegularExp=eval(RegularExp_tmp);
 if (RegularExp.test(field.value)) {
      return true;
 }
 else {
    alert("格式不正确，"+name+"必须为整数！");
    field.select();
    return false;
 }
}


/*
 * pas_076path需求
 * 检查name字符串是否包含星号,
 * 带星号的name名称为运营分类
 * name  ：要检查的名称
 *  返回真为运营内容分类，假为货架内容分类
 */
function isOperatingType(name)
{
   if(name.indexOf("*") == -1)
   {
	//货架内容分类
     return false;
   }
   //运营内容分类
  return true;
}
/*

 * y: 表示年

 * M：表示一年中的月份 1~12

 * d: 表示月份中的天数 1~31

 * H：表示一天中的小时数 00~23

 * m: 表示小时中的分钟数 00~59

 * s: 表示分钟中的秒数   00~59

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

*根据给定的日期时间格式，格式化当前日期

* @params strFormat 格式化字符串， 如："yyyy-MM-dd" 

* @return 返回根据给定格式的字符串表示的时间日期格式<br>

*         若给定的格式不符合规定则返回原字符串格式

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

*根据给定的格式，把给定的时间进行格式化

*@params date 日期值 可以是字符串表示，或者是日期类型，如：'2010-05-28'或 new Date();

*@params strFormat 要得到的日期的格式的格式化字符串，如：'yyyy-MM-dd'

*@return 根据规定格式的时间格式

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

*根据给定的格式对给定的字符串日期时间进行解析，

*@params strDate 要解析的日期的字符串表示

*@params strFormat 解析给定日期的顺序

*@return 返回解析后的Date类型的时间<br>

*        若不能解析则返回当前日期<br>

*        若给定为时间格式 则返回的日期为 1970年1月1日的日期

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

*根据给定的时间间隔类型及间隔值，以给定的格式对给定的时间进行计算并格式化返回

*@params date 给定的时间

*@params interval 时间间隔类型如："YEAR"、"MONTH"、 "DATE", 不区分大小写

*@params amount 时间间隔值，可以正数和负数

*@params strFormat 时间格式

*@return 返回计算并格式化后的时间的字符串

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

*比较两个日期的差距

*@param date1 Date类型的时间

*@param date2 Dete 类型的时间

*@param isFormat boolean 是否对得出的时间进行格式化,<br> 

*       false:返回毫秒数，true：返回格式化后的数据

*@return 返回两个日期之间的毫秒数 或者是格式化后的结果

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

    alert("请输入正确的参数！");

   return 0;

}else{

    var time1 = tmpdate1.getTime(); 

var time2 = tmpdate2.getTime();

    var time = Math.max(time1, time2) - Math.min(time1, time2);

if(!isNaN(time) && time > 0){

   if(isFormat){

  var date = new Date(time);

  var result = "";

  result += (date.getFullYear() - 1970) > 0? (date.getFullYear() - 1970) + "年":"";

  result += (date.getMonth() - 1) > 0? (date.getMonth() - 1) + "月": "";

  result += (date.getDate() - 1) > 0? (date.getDate() - 1) + "日": "";

  result += (date.getHours() - 8) > 0? (date.getHours() - 1) + "小时": "";

  result += date.getMinutes() > 0? date.getMinutes() + "分钟": "";

  result += date.getSeconds() > 0? date.getSeconds() + "秒": "";

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

*根据给定的日期得到日期的月，日，时，分和秒的对象

*@params date 给定的日期 date为非Date类型， 则获取当前日期

*@return 有给定日期的月、日、时、分和秒组成的对象

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