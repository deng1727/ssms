package com.aspire.dotcard.hwcolorring.clrLoad;

import com.aspire.common.threadtask.Task;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class ColorringDeleteTask extends Task
{
	private String clrID;
	

	public ColorringDeleteTask(String clrID)
	{
		this.clrID = clrID;
	}

	public void task() throws Throwable
	{

		/*// 待删除的彩铃节点
		GColorring clrVO = (GColorring) Repository.getInstance().getNode(clrID,
				GColorring.TYPE_COLORRING);

		Searchor searchor = new Searchor();
		searchor.getParams().add(
				new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, clrID));
		searchor.setIsRecursive(true);
		Category rootCagetory =ColorContentBO.rootColorCagetory; (Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_ID, RepositoryConstants.TYPE_CATEGORY);
		List refList = rootCagetory.searchNodes(RepositoryConstants.TYPE_REFERENCE,
				searchor, null);
		// 删除商品
		for (int j = 0; j < refList.size(); j++)
		{
			ReferenceNode ref = (ReferenceNode) refList.get(j);
			Category pCategory = (Category) Repository.getInstance().getNode(
					ref.getParentID(), RepositoryConstants.TYPE_CATEGORY);
			pCategory.delNode(ref);
			pCategory.saveNode();
		}
		// 还要删除彩铃数据
		Category rootSaveNode = ColorContentBO.rootContentNode;(Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CONTENT_ID, RepositoryConstants.TYPE_CATEGORY);
		rootSaveNode.delNode(clrVO);
		rootSaveNode.saveNode();*/
		//CategoryTools.delAllGoodsByRefId(clrID);
	//	Node color=new Node(clrID);
	  //  color.setType(RepositoryConstants.TYPE_COLORRING);
	//	ColorContentBO.rootContentNode.delNode(color);
	//	ColorContentBO.rootContentNode.saveNode();
		ColorContentDAO.getInstance().delGColorringById(clrID);
		
		
	}

}
