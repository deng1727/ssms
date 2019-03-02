//����û���Ϣ��js�����ļ�reg_fillinfo.jsp��modify_person.jsp��ͬʹ�á�
function checkForm()
{
  //����û�����
  var name = userInfoForm.name.value;
  if(trim(name)=="" )
  {
    alert("�������û�������");
    userInfoForm.name.focus();
    return false;
  }
  if(!checkIllegalChar(name,"<>~!@#$%^&*()[]{}\\|'\":;./?,")
        || getLength(trim(name))>30)
  {
    alert("�������ܺ���<>~!@#$%^&*()[]{}\\|'\":;./?,�ַ���\n\n���ҳ��Ȳ��ܳ���30���ַ�����һ������ռ�����ַ�����");
    userInfoForm.name.focus();
    return false;
  }
  //�������
  var birthday = userInfoForm.birthday.value;
  if(trim(birthday)=="")
  {
    alert("��ѡ���������ա�");
    //userInfoForm.birthday.focus();
    return false;
  }
  //���֤������
  var certID = userInfoForm.certID.value;
  if(certID=="")
  {
    alert("����������֤�����룡");
    userInfoForm.certID.focus();
    return false;
  }
  if(getLength(certID)!=15 && getLength(certID)!=18)
  {
    alert("���֤��������������������롣");
    userInfoForm.certID.focus();
    return false;
  }
  else
  {
  	//15λ�ģ���������
  	if(getLength(certID)==15)
  	{
  	  if(!checkDigital(certID))
  	  {
        alert("���֤��������������������롣");
        userInfoForm.certID.focus();
        return false;
  	  }
  	}
	else
	{
	  //18λ�ģ�ǰ17λ�����֣���һλ�����ֺ���ĸ
	  if(!checkDigital(certID.substring(0,17)) || !checkCharOrDigital(certID.substring(17,18)))
	  {
        alert("���֤��������������������롣");
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
    alert("���֤���Ա�һ�£�");
    userInfoForm.certID.focus();
    return false;
  }
  if(!checkBirthdayID(certID,birthday))
  {
    alert("���֤�����ղ�һ�£�");
    userInfoForm.certID.focus();
    return false;
  }
  //��鹫˾����
  var companyName = userInfoForm.companyName.value;
  if(trim(companyName)=="" || !checkIllegalChar(companyName,"'\"<>\\/")
        || getLength(trim(companyName))>100)
  {
    alert("��˾���Ʋ���Ϊ�գ����ܰ��� ' \" < > \\ /  ���ַ���\n\n���ҳ��Ȳ��ܳ���100���ַ���һ������ռ�����ַ�����");
    userInfoForm.companyName.focus();
    return false;
  }
  //��鹫˾��ַ
  var companyAddr = userInfoForm.companyAddr.value;
  if(trim(companyAddr)=="" || !checkIllegalChar(companyAddr,"'\"<>\\/")
        || getLength(trim(companyAddr))>160)
  {
    alert("��˾��ַ����Ϊ�գ����ܰ��� ' \" < > \\ /  ���ַ���\n\n���ҳ��Ȳ��ܳ���160���ַ���һ������ռ�����ַ�����");
    userInfoForm.companyAddr.focus();
    return false;
  }
  //�����������
  var postcode = userInfoForm.postcode.value;
  if(trim(postcode)=="" || !checkDigital(postcode) || getLength(trim(postcode))!=6)
  {
    alert("�������������6λ���֡�");
    userInfoForm.postcode.focus();
    return false;
  }
  //���绰����
  var phone = userInfoForm.phone.value;
  if(trim(phone)=="" || !checkTel(phone) || getLength(trim(phone))>80)
  {
    alert("��ϵ�绰����Ϊ�գ����Ȳ�����80λ��\n\n��ʽ�밴��������Եĸ�ʽ��ʾ���롣");
    userInfoForm.phone.focus();
    return false;
  }
  //����ֻ�����
  var mobile = userInfoForm.mobile.value;
  if(trim(mobile)=="" || !checkMobile(mobile))
  {
    alert("��������ȷ���ֻ����롣");
    userInfoForm.mobile.focus();
    return false;
  }
  //���e-mail
  var email = userInfoForm.email.value;
  if(trim(email)=="" || !checkMail(email))
  {
    alert("��������ȷ��E-mail��");
    userInfoForm.email.focus();
    return false;
  }
  //���QQ
  var QQ = userInfoForm.QQ.value;
  if(trim(QQ)!="" && (!checkDigital(QQ) || getLength(trim(QQ))<5 || getLength(trim(QQ))>10))
  {
    alert("QQ����ֻ����5-10λ�����֡�");
    userInfoForm.QQ.focus();
    return false;
  }
  //���MSN
  var MSN = userInfoForm.MSN.value;
  if(trim(MSN)!="" && !checkMail(MSN))
  {
    alert("��������ȷ��MSN��");
    userInfoForm.MSN.focus();
    return false;
  }
  userInfoForm.birthday.disabled = false;
  return true;
}

//������֤�ź��Ա��Ƿ�һ��
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

//������֤�������Ƿ�һ��
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
