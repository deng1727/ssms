//检查用户信息的js，由文件reg_fillinfo.jsp和modify_person.jsp共同使用。
function checkForm()
{
  //检查用户姓名
  var name = userInfoForm.name.value;
  if(trim(name)=="" )
  {
    alert("请输入用户姓名！");
    userInfoForm.name.focus();
    return false;
  }
  if(!checkIllegalChar(name,"<>~!@#$%^&*()[]{}\\|'\":;./?,")
        || getLength(trim(name))>30)
  {
    alert("姓名不能含有<>~!@#$%^&*()[]{}\\|'\":;./?,字符，\n\n并且长度不能超过30个字符。（一个中文占两个字符）。");
    userInfoForm.name.focus();
    return false;
  }
  //检查生日
  var birthday = userInfoForm.birthday.value;
  if(trim(birthday)=="")
  {
    alert("请选择您的生日。");
    //userInfoForm.birthday.focus();
    return false;
  }
  //检查证件号码
  var certID = userInfoForm.certID.value;
  if(certID=="")
  {
    alert("请输入您的证件号码！");
    userInfoForm.certID.focus();
    return false;
  }
  if(getLength(certID)!=15 && getLength(certID)!=18)
  {
    alert("身份证号码输入错误，请重新输入。");
    userInfoForm.certID.focus();
    return false;
  }
  else
  {
  	//15位的，都是数字
  	if(getLength(certID)==15)
  	{
  	  if(!checkDigital(certID))
  	  {
        alert("身份证号码输入错误，请重新输入。");
        userInfoForm.certID.focus();
        return false;
  	  }
  	}
	else
	{
	  //18位的，前17位是数字，后一位是数字和字母
	  if(!checkDigital(certID.substring(0,17)) || !checkCharOrDigital(certID.substring(17,18)))
	  {
        alert("身份证号码输入错误，请重新输入。");
        userInfoForm.certID.focus();
        return false;
	  }
	}
  }
  var sex = "F";
  if(userInfoForm.sex[0].checked)
  {
    sex = "M";
  }
  if(!checkSexId(certID,sex))
  {
    alert("身份证和性别不一致！");
    userInfoForm.certID.focus();
    return false;
  }
  if(!checkBirthdayID(certID,birthday))
  {
    alert("身份证和生日不一致！");
    userInfoForm.certID.focus();
    return false;
  }
  //检查公司名称
  var companyName = userInfoForm.companyName.value;
  if(trim(companyName)=="" || !checkIllegalChar(companyName,"'\"<>\\/")
        || getLength(trim(companyName))>100)
  {
    alert("公司名称不能为空，不能包含 ' \" < > \\ /  等字符，\n\n并且长度不能超过100个字符（一个中文占两个字符）。");
    userInfoForm.companyName.focus();
    return false;
  }
  //检查公司地址
  var companyAddr = userInfoForm.companyAddr.value;
  if(trim(companyAddr)=="" || !checkIllegalChar(companyAddr,"'\"<>\\/")
        || getLength(trim(companyAddr))>160)
  {
    alert("公司地址不能为空，不能包含 ' \" < > \\ /  等字符，\n\n并且长度不能超过160个字符（一个中文占两个字符）。");
    userInfoForm.companyAddr.focus();
    return false;
  }
  //检查邮政编码
  var postcode = userInfoForm.postcode.value;
  if(trim(postcode)=="" || !checkDigital(postcode) || getLength(trim(postcode))!=6)
  {
    alert("邮政编码必须是6位数字。");
    userInfoForm.postcode.focus();
    return false;
  }
  //检查电话号码
  var phone = userInfoForm.phone.value;
  if(trim(phone)=="" || !checkTel(phone) || getLength(trim(phone))>80)
  {
    alert("联系电话不能为空，长度不超过80位，\n\n格式请按照输入框旁的格式提示输入。");
    userInfoForm.phone.focus();
    return false;
  }
  //检查手机号码
  var mobile = userInfoForm.mobile.value;
  if(trim(mobile)=="" || !checkMobile(mobile))
  {
    alert("请输入正确的手机号码。");
    userInfoForm.mobile.focus();
    return false;
  }
  //检查e-mail
  var email = userInfoForm.email.value;
  if(trim(email)=="" || !checkMail(email))
  {
    alert("请输入正确的E-mail。");
    userInfoForm.email.focus();
    return false;
  }
  //检查QQ
  var QQ = userInfoForm.QQ.value;
  if(trim(QQ)!="" && (!checkDigital(QQ) || getLength(trim(QQ))<5 || getLength(trim(QQ))>10))
  {
    alert("QQ号码只能是5-10位的数字。");
    userInfoForm.QQ.focus();
    return false;
  }
  //检查MSN
  var MSN = userInfoForm.MSN.value;
  if(trim(MSN)!="" && !checkMail(MSN))
  {
    alert("请输入正确的MSN。");
    userInfoForm.MSN.focus();
    return false;
  }
  userInfoForm.birthday.disabled = false;
  return true;
}

//检查身份证号和性别是否一致
function checkSexId(idCard,sex)
{
  var sexId ;
  if(idCard.length==15)
  {
    sexId= idCard.substring(idCard.length-1);
  }
  else
  {
    sexId= idCard.substring(idCard.length-2,idCard.length-1);
  }
  if(sex=="M"&&(sexId%2!=0))
  {
    return true;
  }
  else
  {
    if(sex=="F"&&(sexId%2==0))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}

//检查身份证和生日是否一致
function checkBirthdayID(idCard,birthday)
{
  var mBirthday = birthday.substring(0,4) + birthday.substring(5,7) + birthday.substring(8,10);
  if(idCard.length==15)
  {
    strDay = idCard.substring(6,12);
    mBirthday = mBirthday.substring(2,8);
  }
  else
  {
    strDay = idCard.substring(6,14);
  }
  if(strDay!=mBirthday)
  {
    return false;
  }
  return true;
}
