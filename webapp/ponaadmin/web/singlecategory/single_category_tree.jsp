<%@page language="java" contentType="text/html;charset=GBK"%>
<%@ page import="com.aspire.common.Validator" %>
<%
String userId = Validator.filter(request.getParameter("userId"));
String type = Validator.filter(request.getParameter("type"));
%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - Async</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="../../css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="../../js/ztree/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="../../js/ztree/jquery.ztree.core-3.0.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
		var setting = {
			async: {
				enable: true,
				url:"category_async_ztree_data.jsp?userId=<%=userId %>&type=<%=type %>",
				autoParam:["id"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				beforeAsync: beforeAsync
			}
		};
		
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