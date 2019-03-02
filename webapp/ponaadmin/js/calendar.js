function IncludeJsFile(filename)
{
	var path = location.href.toLowerCase();
	var i = path.indexOf(".nsf");
	if (i>0)
	{
		path = path.substring(0,i);
		path = path.substring(0,path.lastIndexOf("/"))+"/js/"+filename;
	}
	document.write("<script src="+path+"></script>");
}
IncludeJsFile("popwin.js");

//日历对象
function CalendarObject()
{
	this.PopWin = new PopWinObject();
	this.PopWin.HideWhenMouseOut = false;
	this.PopWin.ClearWhenHide = true;
	this.PopWin.Document.oncontextmenu = function(){return false};
	this.PopWin.Body.style.marginTop = 0;
	this.PopWin.Body.style.marginBottom = 4;
	this.PopWin.Body.style.marginLeft = 0;
	this.PopWin.Body.style.marginRight = 4;
	this.PopWin.Body.style.backgroundColor = "#aabbdd";
	this.PopWin.Window.CalendarObject = this;
	
	this.Show = function ()
	{
		var i;
	  	var days=[31,31,(this.Year%4==0&&this.Year%100!=0||this.Year%400==0)?29:28,31,30,31,30,31,31,30,31,30,31];
	  	var FirstDate=new Date(this.Year,this.Month-1,1);
	  	var FirstPos=FirstDate.getDay();
	  	var TotalDays=days[this.Month];
		var strTable1="<table border=0 align=center width=148 bgcolor=#aabbdd><tr><td style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick=\"CalendarObject.SetYear('-1');return false;\" title='上一年'>[&lt;&lt;]</a></td><td style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick=\"CalendarObject.SetMonth('-1');return false;\" title='上一月'>[&lt;]</a></td>";
		strTable1+="<td style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick=\"CalendarObject.SetMonth('+1');return false;\" title='下一月'>[&gt;]</a></td><td style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick=\"CalendarObject.SetYear('+1');return false;\" title='下一年'>[&gt;&gt;]</a></td><td style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick='PopWinObject.Hide();return false;' title='关闭'>╳</a></td></tr></table>";
		strTable1+="<table border=0 align=center width=148 height=27 bgcolor=#aabbdd><tr><td width=74 align=center style='font-size:9pt;color:#33883e;cursor:hand' nowrap onMouseOver='this.style.color=\"#eeeeff\";' onMouseOut='this.style.color=\"#33883e\";' onClick='this.innerHTML=CalendarObject.GetYearList();this.onclick=null;' title='点击选择年份'>"+this.Year+"年</td>";
		strTable1+="<td width=74 align=center style='font-size:9pt;color:#33883e;cursor:hand' nowrap onMouseOver='this.style.color=\"#eeeeff\";' onMouseOut='this.style.color=\"#33883e\";' onClick='this.innerHTML=CalendarObject.GetMonthList();this.onclick=null;' title='点击选择月份'>"+this.TwoPos(this.Month)+"月</td></tr></table>";
		var strTable2="<table border=0 align=center width=148 bgcolor=#aabbdd><tr><td style='color:#ff0000;font-size:9pt'>日</td><td style='font-size:9pt'>一</td><td style='font-size:9pt'>二</td><td style='font-size:9pt'>三</td>";
		strTable2+="<td style='font-size:9pt'>四</td><td style='font-size:9pt'>五</td><td style='font-size:9pt;color:#ff0000;'>六</td></tr>";
		var bday=1-FirstPos;
		while(bday<=TotalDays)
		{
			strTable2+="<tr>";
			for(i=0;i<7;i++,bday++)
			{
				strTable2+="<td align=right style='font-size:9pt' onMouseOver='this.style.backgroundColor=\"#eeeeff\";'";
				strTable2+=" onMouseOut='this.style.backgroundColor=\"#ffffff\";'";
				strTable2+=(bday==this.Day)?" bgcolor=#ffeeff>":" bgcolor=#ffffff>"
				if(bday>0 && bday<=TotalDays)
					strTable2+="<a style='text-decoration:none;"+((bday==this.Day)?"color:#ff0000":"color:#000000")+"' href='' onClick='CalendarObject.ReturnSelectedDay("+bday+");return false;'>"+bday+"</a>";
				strTable2+="</td>";
			}
			strTable2+="</tr>";
		}
		strTable2+="</table>";
		this.PopWin.PopByPoint("<table border=0><tr><td>" + strTable1 + "</td></tr><tr><td>"+strTable2+"</td></tr></table>",this.EventX,this.EventY);
	}
	
	this.TwoPos = function(n)
	{
		var s = "" + (n+100);
		return s.substring(1);
	}
	
	this.SetMonth = function(m)
	{
		switch(m)
		{
			case "+1":
				this.Month++;
				if(this.Month>12)
				{
					this.Month=1;
					this.Year++;
				}
				break;
			case "-1":
				this.Month--;
				if(this.Month<1)
				{
					this.Month=12;
					this.Year--;
				}
				break;
			default:
				this.Month = parseInt(m);
		}
		this.Show();
	}
	
	this.SetYear = function(y)
	{
		switch(y)
		{
			case "+1":
				this.Year++;
				if(this.Year>9999)
					this.Year=9999;
				break;
			case "-1":
				this.Year--;
				if(this.Year<1)
					this.Year=1;
				break;
			default:
				this.Year = parseInt(y);
		}
		this.Show();
	}
	
	this.GetMonthList = function()
	{
		var strList="<select style='font-size:9pt;color:#0000ff;background:#ffffff' onChange='CalendarObject.SetMonth(this.value)'>";
		for(var i=1;i<13;i++)
			strList +="<option value=" + i + ((i==this.Month)?" selected":"") +">" + this.TwoPos(i) + "月";
		strList +="</select>";
		return strList;
	}
	
	this.GetYearList = function()
	{
		var strList="<select style='font-size:9pt;color:#0000ff;background:#ffffff' onChange='CalendarObject.SetYear(this.value)'>";
		for(var i=this.Year-20;i<this.Year+20;i++)
			strList +="<option value=" + i + ((i==this.Year)?" selected":"") +">" + i + "年";
		strList +="</select>";
		return strList
	}
	
	this.ReturnSelectedDay = function(d)
	{
		var re=/YYYY/;
		var strFormat=this.Format.replace(re,this.Year);
		re=/MM/;
		strFormat=strFormat.replace(re,this.TwoPos(this.Month));
		re=/DD/;
		strFormat=strFormat.replace(re,this.TwoPos(d));
		
		var objField=eval("document.all."+this.FieldName);
		objField.value=strFormat;
		this.PopWin.Hide();
		if(window.onDateSelected)
			onDateSelected(this.FieldName);
	}
}

//选择日期
function selectDate(fieldname)
{
	var objField=eval("document.all."+fieldname);
	if(objField==null)
	{
		alert("对象不存在或目前不能编辑！");
		return false;
	}
	
	var objCal = new CalendarObject();
	if(arguments[1]!=null)
		objCal.Format=arguments[1];
	else
		objCal.Format="YYYY-MM-DD";
	objCal.FieldName=fieldname;
	var theDate;
	var oldDate=objField.value;
	theDate = formatDate(oldDate,objCal.Format);
	var d=theDate.getYear();
	objCal.Year=(d<100)?(1900+d):d;
	objCal.Month=theDate.getMonth()+1;
	objCal.Day=theDate.getDate();
	objCal.EventX = event.x;
	objCal.EventY = event.y;
	objCal.Show();
}

//时钟对象
function ClockObject()
{
	this.PopWin = new PopWinObject();
	this.PopWin.HideWhenMouseOut = false;
	this.PopWin.ClearWhenHide = true;
	this.PopWin.Document.oncontextmenu = function(){return false};
	this.PopWin.Body.style.marginTop = 0;
	this.PopWin.Body.style.marginBottom = 4;
	this.PopWin.Body.style.marginLeft = 0;
	this.PopWin.Body.style.marginRight = 4;
	this.PopWin.Body.style.backgroundColor = "#aabbdd";
	this.PopWin.Window.ClockObject = this;
	this.HPoint=5; 
	this.MPoint=7;
	this.PLength=7;
	this.HLength=50;
	this.MLength=70;
	
	this.Show = function ()
	{
		var i;
		var strTable="<table width=160 height=180><tr valign=top><td style='font-size:9pt'>[<a id='aAM' style='text-decoration:none;color:#000000' href='javascript:ClockObject.SetAMPM(0);'>上午</a>]</td><td style='font-size:9pt'>[<a id=aPM style='text-decoration:none;color:#000000' href='javascript:ClockObject.SetAMPM(1);'>下午</a>]</td><td align=right style='font-size:9pt'><a style='text-decoration:none;color:#000000' href='' onClick='PopWinObject.Hide();return false;' title='关闭'>╳</a></td></tr></table>";
		for(i=1;i<=12;i++)
		{
			strTable +="<div id=minute" + i + " style='position: absolute'><a href='javascript:ClockObject.SetMinute(" + (i-1)*5 + ")'>" + (i-1)*5 + "</a></div>";
			strTable +="<div id=hour" + i + " style='position: absolute'><a href='javascript:ClockObject.SetHour(" + i + ")'>" + i + "</a></div>";
		}
		for(i=0;i<this.MPoint;i++)
			strTable +="<div id=mpoint" + i + " style='position: absolute;color:#009900;font-size:12pt'>.</div>";
		for(i=0;i<this.HPoint;i++)
			strTable +="<div id=hpoint" + i + " style='position: absolute;color:#0000ff;font-size:12pt'>.</div>";
	
		strTable +="<table><tr><td width=70% align=center><input name=calHInput size=2 maxlength=2 value="+this.TwoPos(this.Hour)+"><b>&nbsp;:&nbsp;</b><input name=calMInput size=2 maxlength=2 value="+this.TwoPos(this.Minute)+"></td>";
		strTable +="<td width=30%><input type=button onclick='ClockObject.ReturnSelectedTime();' value='确定'></td></tr></table>";
		this.PopWin.Document.createStyleSheet();
		this.PopWin.Document.styleSheets[0].addRule("input","font-size: 12px; color: #333333");
		this.PopWin.Document.styleSheets[0].addRule("b","font-size: 12px; color: #333333");
		this.PopWin.Document.styleSheets[0].addRule("a","font-size: 12px; color: #003048");
		this.PopWin.Document.styleSheets[0].addRule("a:hover","font-size: 12px; color: #0052c2");
		this.PopWin.Document.styleSheets[0].addRule("a:active","font-size: 12px; color: #787878; text-decoration: underline");
		this.PopWin.OnShow = function()
		{
			this.Window.ClockObject.ShowClockNumber();
			this.Window.ClockObject.ShowClockPoint();
			this.Window.ClockObject.SetAMPM();
		}
		this.PopWin.PopByPoint(strTable,this.EventX,this.EventY);
	}
	
	this.ShowClockNumber = function()
	{
		for(var i=1;i<=12;i++)
		{
			x=this.MLength*Math.cos(0.523598*(i-4))+75;
			y=this.MLength*Math.sin(0.523598*(i-4))+95;
			this.PopWin.Document.all["minute" + i].style.posLeft=x;
			this.PopWin.Document.all["minute" + i].style.posTop=y;
	
			x=this.HLength*Math.cos(0.523598*(i-3))+75;
			y=this.HLength*Math.sin(0.523598*(i-3))+95;
			this.PopWin.Document.all["hour" + i].style.posLeft=x;
			this.PopWin.Document.all["hour" + i].style.posTop=y;
		}
	}
	
	this.ShowClockPoint = function()
	{
		var i,x,y;
		for(i=0;i<this.MPoint;i++)
		{	
			x = i*this.PLength*Math.cos(0.523598*(this.Minute/5-3))+75;
			y = i*this.PLength*Math.sin(0.523598*(this.Minute/5-3))+95;
			this.PopWin.Document.all["mpoint" + i].style.posLeft = x;
			this.PopWin.Document.all["mpoint" + i].style.posTop = y;
		}
		for(i=0;i<this.HPoint;i++)
		{	
			x = i*this.PLength*Math.cos(0.523598*(this.Hour+this.Minute/60-3))+75;
			y = i*this.PLength*Math.sin(0.523598*(this.Hour+this.Minute/60-3))+95;
			this.PopWin.Document.all["hpoint" + i].style.posLeft = x;
			this.PopWin.Document.all["hpoint" + i].style.posTop = y;
		}
	}
	
	this.SetAMPM = function(ap)
	{
		if(ap !=null)
			this.AMPM=ap;
		if(this.AMPM==0)
		{
			this.PopWin.Document.all.aAM.style.fontWeight="bold";
			this.PopWin.Document.all.aAM.style.color="#FF0000";
			this.PopWin.Document.all.aPM.style.fontWeight="normal";
			this.PopWin.Document.all.aPM.style.color="#000000";
		}
		else
		{
			this.PopWin.Document.all.aPM.style.fontWeight="bold";
			this.PopWin.Document.all.aPM.style.color="#FF0000";
			this.PopWin.Document.all.aAM.style.fontWeight="normal";
			this.PopWin.Document.all.aAM.style.color="#000000";
		}
	}
	
	this.SetHour = function(h)
	{
		this.Hour = this.AMPM*12+h%12;
		this.PopWin.Document.all.calHInput.value=this.TwoPos(this.Hour);
		this.ShowClockPoint();
	}
	
	this.SetMinute = function(m)
	{
		this.Minute = m;
		this.PopWin.Document.all.calMInput.value=this.TwoPos(m);
		this.ShowClockPoint();
	}
	
	
	this.TwoPos = function(n)
	{
		var s = "" + (n+100);
		return s.substring(1);
	}
	
	this.ReturnSelectedTime = function(d)
	{
		var strTime=this.PopWin.Document.all.calHInput.value + ":" + this.PopWin.Document.all.calMInput.value;
		if(!isValidTime(strTime))
		{
			alert("时间格式有误！");
			return false;
		}
		strTime=this.TwoPos(parseInt(this.PopWin.Document.all.calHInput.value,10)) + ":" +
			this.TwoPos(parseInt(this.PopWin.Document.all.calMInput.value,10));
		
		var objField=eval("document.all."+this.FieldName);
		objField.value=strTime;
		this.PopWin.Hide();
		if(window.onTimeSelected)
			onTimeSelected(this.FieldName);
	}
}

//选择时间
function selectTime(fieldname)
{
	var objField=eval("document.all."+fieldname);
	if(objField==null)
	{
		alert("对象不存在或目前不能编辑！");
		return false;
	}
	var objClk = new ClockObject();
	objClk.FieldName=fieldname;
	var oldTime=objField.value;
	if(isValidTime(oldTime))
	{
		var arrTime = oldTime.split(":");
		objClk.Hour = parseInt(arrTime[0],10);
		if(arrTime.length>1)
			objClk.Minute = parseInt(arrTime[1],10);
	}
	else
	{
		var theDate = new Date();
		objClk.Hour=theDate.getHours();
		objClk.Minute=theDate.getMinutes();
	}
	if(objClk.Hour<12)
		objClk.AMPM=0;
	else
		objClk.AMPM=1;
	objClk.EventX = event.x;
	objClk.EventY = event.y;
	objClk.Show();
}

//将字符串转化为日期对象，第一个参数为日期字符串，默认格式为“YYYY-MM-DD”，如果要自定义格式
//把格式做为第二个参数，格式中必须含有"YYYY"、"MM"、"DD"等字串
function formatDate(strdate)
{
	var arr = new Array;
	var arr1 = new Array;
	var arrsort=new Array(0,1,2);
	var arrtemp=new Array;
	var arrdate =new Array;
	var tt=new Date;
	arrdate[0]=tt.getYear();
	arrdate[1]=tt.getMonth()+1;
	arrdate[2]=tt.getDate();
	var strformat;
	var strtemp;
	var numtemp;
	var theDate;

	if(arguments[1]!=null)
		strformat=arguments[1];
	else
		strformat="YYYY-MM-DD";
	strtemp=strformat.replace("YYYY","-");
	strtemp=strtemp.replace("MM","-");
	strtemp=strtemp.replace("DD","-");
	arr=strtemp.split("-");
	strtemp=strdate;
	for(var i=0;i<arr.length;i++)
		strtemp=strtemp.replace(arr[i],"-")
	arr=strtemp.split("-");
	for(i=0;i<arr.length;i++)
		if(arr[i]!="")
			arr1[arr1.length]=arr[i];
	arrtemp[0]=strformat.indexOf("YYYY");
	arrtemp[1]=strformat.indexOf("MM");
	arrtemp[2]=strformat.indexOf("DD");
	if(arrtemp[arrsort[0]]>arrtemp[arrsort[1]])
	{
		numtemp=arrsort[0];
		arrsort[0]=arrsort[1];
		arrsort[1]=numtemp;
	}
	if(arrtemp[arrsort[0]]>arrtemp[arrsort[2]])
	{
		numtemp=arrsort[0];
		arrsort[0]=arrsort[2];
		arrsort[2]=numtemp;
	}
	if(arrtemp[arrsort[1]]>arrtemp[arrsort[2]])
	{
		numtemp=arrsort[1];
		arrsort[1]=arrsort[2];
		arrsort[2]=numtemp;
	}
	var j;
	for(i=arrsort.length-1,j=arr1.length-1;j>-1;i--,j--)
	{
		if(arrtemp[arrsort[i]] != -1)
			arrdate[arrsort[i]]=arr1[j];
	}
	if(strdate=="")
	{
		theDate=new Date();
	}
	else
	{	
		theDate=new Date(arrdate[0],arrdate[1]-1,arrdate[2]);
	}
	return theDate;
}

//时间比较函数
function compareDate(endDate,beginDate,endTime,beginTime)
{
	//参数为字符串，后两个参数可以为空
	//返回值：0、相等；正数：结束时间大于开始时间；负数：结束时间小于开始时间
	var arrDate;
	var strBDate,strEDate;
	var datBDate,datEDate;
	var datToday = new Date();

	//初始化开始日期，为空串则设为今天
	if(beginDate=="")
	{
		datBDate = new Date(datToday.getYear(),datToday.getMonth(),datToday.getDate())
	}
	else
	{
		arrDate=beginDate.split("-");
		datBDate = new Date(arrDate[1] + "-" + arrDate[2] + "-" + arrDate[0]);
	}

	//初始化结束日期，为空串则设为今天
	if(endDate=="")
	{
		datEDate = new Date(datToday.getYear(),datToday.getMonth(),datToday.getDate())
	}
	else
	{
		arrDate=endDate.split("-");
		datEDate = new Date(arrDate[1] + "-" + arrDate[2] + "-" + arrDate[0]);
	}

	//初始化开始时间，为空则忽略
	if(beginTime!=null && beginTime!="")
	{
		arrDate=beginTime.split(":");
		datBDate.setHours(arrDate[0]);
		if(arrDate.length>1)
		{
			datBDate.setMinutes(arrDate[1]);
		}
		if(arrDate.length>2)
		{
			datBDate.setSeconds(arrDate[2]);
		}
	}
	//初始化开始时间，为空串则设为现在
	if(beginTime=="")
	{
		datBDate.setHours(datToday.getHours());
		datBDate.setMinutes(datToday.getMinutes());
	}

	//初始化结束时间，为空则忽略
	if(endTime!=null && endTime!="")
	{
		arrDate=endTime.split(":");
		datEDate.setHours(arrDate[0]);
		if(arrDate.length>1)
		{
			datEDate.setMinutes(arrDate[1]);
		}
		if(arrDate.length>2)
		{
			datEDate.setSeconds(arrDate[2]);
		}
	}
	//初始化开始时间，为空串则设为现在
	if(endTime=="")
	{
		datEDate.setHours(datToday.getHours());
		datEDate.setMinutes(datToday.getMinutes());
	}
	return (datEDate - datBDate);
}
//判断时间格式是否正确
function isValidTime(strTime)
{
	var n;
	if(strTime=="")
		return false;
	var re=/[^\d:]/g;
	if(re.test(strTime))
		return false;
	var arrTime = strTime.split(":");
	n = parseInt(arrTime[0],10);
	if(isNaN(n))
		return false;
	if(n>23 || n<0)
		return false;
	if(arrTime.length>1)
	{
		n=parseInt(arrTime[1],10);
		if(isNaN(n))
			return false;
		if(n>59 || n<0)
			return false;
	}
	if(arrTime.length>2)
	{
		n=parseInt(arrTime[2],10);
		if(isNaN(n))
			return false;
		if(n>59 || n<0)
			return false;
	}
	return true;
}

