function Cookie(delim){ 
      this._Cookie=[]; 
      this.Load=function(){ 
	  if(document.cookie.indexOf(";")!=-1){ 
            var _sp,_name,_tp,_tars,_tarslength; 
            var _item=document.cookie.split("; "); 
            var _itemlength=_item.length; 
            while(_itemlength>0){ 
                _sp=_item[--_itemlength].split("="); 
                _name=_sp[0]; 
                _tp=_sp[1].split(","); 
                _tars=_tp.slice(1,_tp.length); 
                this._Cookie[_name]=[]; 
                this._Cookie[_name]=_tars; 
                this._Cookie[_name]["timeout"]=_tp[0]; 
                } 
            return true; 
            } 
        return false; 
        } 
    this.Save=function(){ 
        var _str,_ars,_mars,_marslength,timeout,i,key; 
        for(key in this._Cookie){ 
            if(!this._Cookie[key])return; 
            _str=[]; 
            _mars=CookieClass._Cookie[key]; 
            _marslength=_mars.length; for(i=0;i<_marslength;i++)_str[_str.length]=escape(_mars[i]);	document.cookie=key+"="+_mars["timeout"]+(_str.length>0?",":"")+_str+(_mars["timeout"]==0?"":";expires="+new Date(parseInt(_mars["timeout"])).toGMTString()); 
            } 
        } 
    this.GetCookieCount=function(){ 
        var _length=0,key; 
        for(key in this._Cookie)_length++; 
        return _length; 
        } 
    this.Create=function(name,days){ 
        days=days?days:0; 
        if(!this._Cookie[name])this._Cookie[name]=[]; 
        this._Cookie[name]["timeout"]=days!=0?new Date().getTime()+parseInt(days)*86400000:0; 
        } 
    this.Modify=function(name,days){ 
        this.Create(name,days); 
        } 
    this.GetTime=function(name){ 
        return new Date(parseInt(this._Cookie[name]["timeout"])); 
        } 
    this.Delete=function(name){ 
        this.Create(name,0); 
        } 
    this.AddItem=function(name,value){ 
        this._Cookie[name][this._Cookie[name].length]=value; 
        } 
    this.DelItem=function(name,index){ 
        var _ttime=this._Cookie[name]["timeout"]; this._Cookie[name]=this._Cookie[name].slice(0,index).concat(this._Cookie[name].slice(parseInt(index)+1,this._Cookie[name].length)); 
        this._Cookie[name]["timeout"]=_ttime; 
        } 
    this.GetCount=function(name){ 
        return this._Cookie[name].length; 
        } 
    this.GetItem=function(name,index){ 
        return this._Cookie[name][index]; 
        } 
    } 

function eyunCookie() 
  {this.key="";//初始化key。 
   this.value="";//初始化key's value。 
   this.expires=0;//初始化cookie的有效时间，单位毫秒。 
   this.init=function()//对象初始化 
                 {this.key=""; 
                this.value=""; 
                this.expires=0; 
                              } 
   this.set=function(key,value,expires)//设置cookie 
              {if(this.key=="")this.key=key; 
                             if(this.value=="")this.value=value; 
                             if(this.expires<=0)this.expires=expires; 
                             if(this.key==""||typeof(this.key)!="string") 
                 {
                                  this.init(); 
                  return false; 
                 } 
               if(this.key.match(/[,; ]/)) 
                 {
                                  this.init(); 
                  return false; 
                 } 
               if(this.value.toString().match(/[,; ]/)||typeof(this.value)=="undefined") 
                 {
                                  this.init(); 
                  return false; 
                 } 
               if(this.expires<=0||typeof(this.expires)!="number") 
                 {
                                  this.init(); 
                  return false; 
                 } 
               var cookie=document.cookie; 
               var dt=new Date(); 
               dt.setTime(dt.getTime()+this.expires); 
                             document.cookie=this.key+"="+this.value+";expires="+dt.toGMTString(); 
                             this.init(); 
               return true; 
              } 
   this.get=function(key)//取得名为key的cookie的值 
              {if(key==""||key.match(/[,; ]/)) 
                 {
                  return false; 
                 } 
               var cookie=document.cookie; 
               var start=cookie.indexOf(key+"="); 
               if(start==-1) 
                 {
                  return false; 
                 } 
               var end=cookie.indexOf(";",start); 
                             if(end==-1) 
                               end=cookie.length; 
               var getCookie=cookie.substring(start+key.length+1,end); 
               
               return getCookie; 
              } 
   //this.showAll=function(){alert("共有以下cookie对：\n"+document.cookie.split(";").toString().replace(/,/g,"\n"));}//显示所有cookie 
   this.del=function(key)//删除名为key的cookie 
              {if(key==""||key.match(/[,; ]/)) 
                 {
                  return false; 
                 } 
                             var dt=new Date(); 
               dt.setTime(dt.getTime()); 
                             document.cookie=key+"=eyunDelete;expires="+dt.toGMTString(); 
                             this.init(); 
               return true; 
              } 
   this.destroy=function()//销毁所有cookie 
                    {var dt=new Date(); 
                   dt.setTime(dt.getTime()); 
                                     while(document.cookie!="") 
                                       document.cookie=document.cookie+";expires="+dt.toGMTString(); 
                                     this.init(); 
                                     return true 
                                    } 
  }