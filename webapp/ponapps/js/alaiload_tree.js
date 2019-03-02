/*----------------------------------------------------------------------------\
|                               alaiload_tree 1.00		                      | 
\----------------------------------------------------------------------------*/

function getScriptPath(js)
{
	js=js.toLowerCase()
	var script=document.getElementsByTagName("SCRIPT");
	for(var i=0;i<script.length;i++)
	{
		var s=script[i].src.toLowerCase()
		if(s.indexOf(js)!=-1)return s.replace(js,"")
	}
	return null
}

function alaiload_tree(key,name,toObject,sXmlSrc)
{
	var path=getScriptPath("alai_tree.js")
	if(path==null){alert("run alaiload_tree() fail, please load alai_tree.js first!");return;}
	var icons=new alai_imagelist()
	icons.path=path+"images/"
	icons.add("file.png","default")
	icons.add("fold2k_open")
	icons.add("fold2k_close") 
	icons.add("plus_m","expand")
	icons.add("plus_top","expand_top")
	icons.add("plus_end","expand_end")
	icons.add("minus_m","collapse")
	icons.add("minus_top","collapse_top")
	icons.add("minus_end","collapse_end")
	icons.add("branch","leaf")
	icons.add("branch_end","twig")
	icons.add("vline","line")
	icons.add("blank")

    var tree=new alai_tree(icons,0,toObject)
    loadXmlTree(key,name,sXmlSrc,tree)

	// custom event
	tree.onclick=function(srcNode)
	{
	    return false;
	}
	tree.onexpand=function(srcNode)
	{
		srcNode.icon.src=icons.item["fold2k_open"].src	
	}
	tree.oncollapse=function(srcNode)
	{	
		srcNode.icon.src=icons.item["fold2k_close"].src
	}
    return tree
}

function loadTreeNode(sSrc,tree,node) {

    if(node.label.sign==1) return;

	var xmlHttp = XmlHttp.create();
	xmlHttp.open("GET", sSrc, true);	
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			loadxmlGetString(xmlHttp.responseXML,tree,node);
		} 
	};

	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);
}

function loadxmlGetString(oXmlDoc,tree,node) {

	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
	   alert(oXmlDoc.xml);
       window.status = "Error loading oXmlDoc";
	}
	else {
        get(oXmlDoc.xml,tree,node);
	}
}

var string='';

function get(strxml,tree,node)
{
    string='';
    var n=new Array();
    strxml=strxml.replace('&lt;','<');
	strxml=strxml.replace('&gt;','>');
	var xDoc=new ActiveXObject("Msxml2.DOMDocument");
	xDoc.async=false;
	xDoc.loadXML(strxml);
	n[0]="";
	//从XML得到字符串	
	toString(xDoc.documentElement,n);
    //加载节点
	if(string!="")
	{   
		//tree.loadParse(node,string.replace(/\s/g,""),true);
		tree.loadParse(node,string,true);
		//已经加载标记
		node.label.sign=1;
	}
	//alert("该节点KEY为"+node.label.id);
	//alert("加载后该节点有"+node.children.length+"个子节点");
	//alert("Tree的节点总数为"+tree.count()+"个");
	tree.expandTier(node);
}

function toString(node,s)
{
    var m=new Array();		
    var setp=0;
    setp+=1;
		
	var i=node.childNodes.length;
		
	for(m[setp]=0;m[setp]<i;m[setp]++)
	{
			
		sPath=s[setp-1];
		if(node.childNodes[m[setp]].childNodes.length>0)
		{
				
			sPath=s[setp-1]+node.childNodes[m[setp]].nodeName+"/";	
			s[setp]=sPath;			
			toString(node.childNodes[m[setp]],s);
		}
		else
		{
				
			//string+=sPath+node.childNodes[m[setp]].nodeName;
				
			if(node.childNodes[m[setp]].attributes[0]!=null)
			{
				string+=node.childNodes[m[setp]].attributes[0].value;
			}
			if(node.childNodes[m[setp]].attributes[1]!=null)
			{
					
				string+=','+node.childNodes[m[setp]].attributes[1].value;					
			}
			if(node.childNodes[m[setp]].attributes[2]!=null)
			{
					
				string+=','+node.childNodes[m[setp]].attributes[2].value;					
			}
			if(node.childNodes[m[setp]].attributes[3]!=null)
			{
					
				string+=','+node.childNodes[m[setp]].attributes[3].value;					
			}
			if(node.childNodes[m[setp]].attributes[4]!=null)
			{
					
				string+=','+node.childNodes[m[setp]].attributes[4].value;					
			}
			if(node.childNodes[m[setp]].attributes[5]!=null)
			{
					
				string+=','+node.childNodes[m[setp]].attributes[5].value;					
			}
			string+=";";
				
		}	
				
	}
	setp-=1;	
}

// creates the xmlhttp object and starts the load of the xml document
function loadXmlTree(key,name,sSrc,tree) {

	var xmlHttp = XmlHttp.create();
	xmlHttp.open("GET", sSrc, true);	
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			xmlGetString(key,name,xmlHttp.responseXML,tree);
		}  
	};

	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);
}

// Inserts an xml document as a subtree to the provided node
function xmlGetString(key,name,oXmlDoc,tree) {

	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
		alert(oXmlDoc.xml);
        window.status = "Error loading oXmlDoc";
	}
	else {
        getString(key,name,oXmlDoc.xml,tree);
	}

}

var str='';


function getString(key,name,strxml,tree)
{
    var s=new Array();
    strxml=strxml.replace('&lt;','<');
	strxml=strxml.replace('&gt;','>');
	var xmlDoc=new ActiveXObject("Msxml2.DOMDocument");
	xmlDoc.async=false;
	xmlDoc.loadXML(strxml);
	s[0]="";
	//从XML得到字符串	
	getToString(xmlDoc.documentElement,s);
    //生成目录树
    //tree.pathParse(key,name,str.replace(/\s/g,""),true);
    tree.pathParse(key,name,str,true);
	tree.expandToTier(2);
}

function getToString(node,s)
{
    var j=new Array();		
    var setp=0;
    setp+=1;
		
	var i=node.childNodes.length;
		
	for(j[setp]=0;j[setp]<i;j[setp]++)
	{
			
		sPath=s[setp-1];
		if(node.childNodes[j[setp]].childNodes.length>0)
		{
				
			sPath=s[setp-1]+node.childNodes[j[setp]].nodeName+"/";	
			s[setp]=sPath;			
			getToString(node.childNodes[j[setp]],s);
		}
		else
		{
				
			//str+=sPath+node.childNodes[j[setp]].nodeName;
				
			if(node.childNodes[j[setp]].attributes[0]!=null)
			{
				str+=node.childNodes[j[setp]].attributes[0].value;
			}
			if(node.childNodes[j[setp]].attributes[1]!=null)
			{
					
				str+=','+node.childNodes[j[setp]].attributes[1].value;					
			}
			if(node.childNodes[j[setp]].attributes[2]!=null)
			{
					
				str+=','+node.childNodes[j[setp]].attributes[2].value;					
			}
			if(node.childNodes[j[setp]].attributes[3]!=null)
			{
					
				str+=','+node.childNodes[j[setp]].attributes[3].value;					
			}
			if(node.childNodes[j[setp]].attributes[4]!=null)
			{
					
				str+=','+node.childNodes[j[setp]].attributes[4].value;					
			}
			if(node.childNodes[j[setp]].attributes[5]!=null)
			{
					
				str+=','+node.childNodes[j[setp]].attributes[5].value;					
			}
			str+=";";
				
		}	
				
	}
	setp-=1;	
}
