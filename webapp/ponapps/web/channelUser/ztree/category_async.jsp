<%@page language="java" contentType="text/html;charset=GBK"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - Async</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/demo.css" type="text/css">
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core-3.0.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
	function del_node(path){
		$("a[href$='"+path+"']").parent("li").remove();
 	}
 
 
		<!--
		var setting = {
			view: {
				addDiyDom: addDiyDom
			},
			async: {
				enable: true,
				url:"category_async_ztree_data.jsp",
				autoParam:["id"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				beforeAsync: beforeAsync
			}
		};

		function addDiyDom(treeId, treeNode) {
			if(treeNode.id==701) return;
			if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
			var aObj = $("#" + treeNode.tId + "_a");
			
			//http://localhost:7001/ponaadmin/web/resourcemgr/categoryEdit.do?action=new&pCategoryID=7287&cgyPath=ªıº‹>>÷’∂À“Ù¿÷Õ∆ºˆ
			var editStr = "<span id='diyBtn_" +treeNode.id+ "'><a href='../categoryEdit.do?action=new&pCategoryID="+treeNode.id+"&cgyPath="+getPath(treeNode)+"' target='mainFrame1'>(+)</a></span>";
			aObj.after(editStr);
		}
		
		function getPath(treeNode){
			return (treeNode.url.substring(treeNode.url.lastIndexOf("=")+1));
		}
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		function beforeAsync(treeId, treeNode) {
			return treeNode ? treeNode.level < 100 : true;
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting);
		});
		//-->
	</SCRIPT>
</HEAD>


<BODY style="background:#E9F0F8;">
 	<div>
 	<ul>
 	<div id="treeDemo" class="ztree"></div>
 	</ul>
 	 </div>
 	
</BODY>
</HTML>