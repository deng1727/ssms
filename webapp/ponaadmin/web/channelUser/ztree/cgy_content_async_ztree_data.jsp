<%@page language="java" contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNodeManager"%>
<%@page import="com.aspire.ponaadmin.web.ztree.TreeNode"%>
<%@ page import="com.aspire.common.Validator"%>
<%@ page
	import="com.aspire.ponaadmin.web.channelUser.vo.ChannelCategoryVO"%>
<%@ page import="com.aspire.ponaadmin.common.usermanager.UserManagerBO"%>
<%@ page import="com.aspire.ponaadmin.common.usermanager.UserSessionVO"%>

<%
	UserSessionVO userSession = UserManagerBO.getInstance()
			.getUserSessionVO(request.getSession());
	//request.getContextPath();
	String id = Validator.filter(request.getParameter("id"));
	if (id == null) {
		List openChannelsCategoryList = (List)session.getAttribute("openChannelsCategoryList");
		if (openChannelsCategoryList != null
				&& openChannelsCategoryList.size() > 0){
			out
					.println("[{id:701,pId:100,name:'货架',isParent:true,open:true,url:'"+request.getContextPath()+"/web/channelUser/cgyContentList.do?categoryID=701&cgyPath=货架',target:'mainFrame1',childs:");
			out.println("[");
			for (int k = 0; k < openChannelsCategoryList.size(); k++) {
				ChannelCategoryVO vo = (ChannelCategoryVO) openChannelsCategoryList
						.get(k);
				out
						.print("{id:"
								+ vo.getId()
								+ ",pId:"
								+ vo.getParentcategoryId()
								+ ",name:'"
								+ vo.getCategoryName()
								+ "',url:'../cgyContentList.do?categoryID="
								+ vo.getId()
								+ "&cgyPath="
								+ TreeNodeManager.getPath(vo.getId(),vo.getCategoryName())
								+ "',isParent:'true',target:'mainFrame1'}");
				if (k != openChannelsCategoryList.size() - 1) {
					out.println(",");
				}

			}
			out.println("]");
			out.println("}]");
		}else{
			out.println("暂无货架");
		}

	} else {

		List nodeList = new TreeNodeManager().getchildrenNodes(id);
		if (nodeList != null && nodeList.size() > 0) {
			out.println("[");
			for (int i = 0; i < nodeList.size(); i++) {
				TreeNode node = (TreeNode) nodeList.get(i);

				out
						.print("{id:"
								+ node.getId()
								+ ",pId:"
								+ node.getParentId()
								+ ",name:'"
								+ node.getName()
								+ "',url:'"+request.getContextPath()+"/web/channelUser/cgyContentList.do?categoryID="
								+ node.getId()
								+ "&cgyPath="
								+ TreeNodeManager.getPath(node.getId(),node.getName())
								+ "',isParent:'true',target:'mainFrame1'}");
				if (i != nodeList.size() - 1) {
					out.println(",");
				}

			}
			out.println("]");
		}

	}

	//out.println("[{id:1,name:'aiyan123',pId:0,isParent:'true'},{id:101,name:'qqq',pId:1}]");
	//out.println("{id:2,name:'qqq',,pId:1}]");
%>