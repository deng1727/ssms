/* 51forbes.net * Copyright(C) 2005 All right reversed *//* You are not allowed to copy or modify the codes * Commercial use requires * license. */
function Dictionary()
{
	this.items=new Array();
};

Dictionary.prototype.add=function(key,value)
{
	this.items[key]=value;
};
Dictionary.prototype.exists=function(key)
{
	return typeof(this.items[key])!= "undefined"
};

function attachSelect(oDsy,aSelectId,aDefaultValue)
{	
	var aSel=[];
	var aDef=aDefaultValue||[];	
	if(!(oDsy instanceof Dictionary))	
		throw new Error('Data source error.');	
	for(var i=0;i<aSelectId.length;i++)		
		aSel[i]=document.getElementById(aSelectId[i]);	
	for(var i=0;i<aSel.length-1;i++)		
		eval("attachEventHandler(aSel["+i+"],\"onchange\",function(){ changedHandler("+(i+1)+") })");	
	changedHandler(0);	
	function attachEventHandler(el,type,fnc)
	{		
		if(el.attachEvent)
		{
			el.attachEvent(type,fnc)
		}		
		else if(el.addEventListener)
		{
			el.addEventListener(type.replace(/^on/i,''),fnc,false)
		}		
		else if(typeof el[type] == "function")
		{
			var oldfnc=el[type];
			el[type]=function(E){oldfnc(e);fnc(e);}
		}		
		else
		{
			el[type]=fnc;
		}
	};	
    function changedHandler(v)
    {
    	var str="0";		
    	for(var i=0;i<v;i++)
    	{ 
    		str +=("_" + aSel[i].selectedIndex);
    	};		
    	var ss=aSel[v];		
    	if(oDsy.exists(str))
    	{
    		with(ss)
    		{
    			length=0;
    			var ar=oDsy.items[str],xx=0;				
    			for(var i=0;i<ar.length;i++)
    			{					
    				var ot=ar[i][0],ov=ar[i][1]||ot;					
    				if(ov == aDef[v])xx=i;					
    				options[i]=new Option(ot,ov);
    			};					
    			options[xx].selected=true;					
    			if(++v<aSel.length)changedHandler(v);			
    		};		
    	}
    	else
    	{			
    		for(var i=v;i<aSel.length;i++)
    		{
    			with(aSel[i])
    			{
    				length=0;options[0]=new Option("--","");
    				options[0].selected=true;
    			}
    		}
    	};
    	aDef=[];
    };
};