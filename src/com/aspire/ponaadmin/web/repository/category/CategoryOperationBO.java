package com.aspire.ponaadmin.web.repository.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.gcontent.GContentDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.ReferenceTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;

public class CategoryOperationBO {

	private final static JLogger LOGGER = LoggerFactory
			.getLogger(CategoryOperationBO.class);

	private static CategoryOperationBO instance = new CategoryOperationBO();

	private CategoryOperationBO() {
	}

	public static CategoryOperationBO getInstance() {
		return instance;
	}

	public void productShelves(ProductShelvesVO vo) throws BOException {
		try {
			Category category = (Category) Repository.getInstance().getNode(
					vo.getShelvesID(), RepositoryConstants.TYPE_CATEGORY);
			// 全量上架
			if ("0".equals(vo.getAction())) {
				this.allShelves(vo, category);
				// 全量下架
			} else if ("1".equals(vo.getAction())) {
				this.shelvesALL(vo, category);
				// 增量上架
			}else if ("2".equals(vo.getAction())) {
				this.addShelves(vo, category);
				// 增量下架
			} else if ("3".equals(vo.getAction())) {
				this.shelves(vo, category);
			} else {
				throw new BOException("上架动作不存在，请重新上架");
			}
		} catch (BOException e) {
			LOGGER.error("产品上架发生异常：", e);
			throw new BOException("产品上架发生异常：", e);
		}

	}

	/**
	 * 全部上架
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void allShelves(ProductShelvesVO vo, Category category)
			throws BOException {

		Map oldSortIdCollection = new HashMap();
		LOGGER.info("====================货架Id："+ category.getId() + "进行全部上架操作开始=================================");
		LOGGER.info("下架该货架下的所有商品。cateId=" + category.getId());
		// 商品下架。
		// Category category=(Category)Repository.getInstance().getNode(cateId,
		// RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(
				RepositoryConstants.TYPE_REFERENCE, null, null);
		for (int i = 0; i < subNodes.size(); i++) {
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			oldSortIdCollection.put(node.getRefNodeID(),
					new Integer(node.getSortID()));
			// String id = node.getId();//包括应用和数字内容
			String id = node.getRefNodeID();// 只包含应用
			LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + id + "下架");
			if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// 如果是应用需要填写商品历史表。
			{
				GoodsBO.removeRefContentFromCategory(node.getId());

			} else {
				category.delNode(node);
			}

			GContent content = (GContent) Repository.getInstance().getNode(
					node.getRefNodeID(), "nt:gcontent");

		}
		category.saveNode();

		LOGGER.info("进行上架操作开始。");
		List<ApplicationVO> applicationList = vo.getList();
		List<ReferenceNode> list = new ArrayList<ReferenceNode>();
		for (int i = 0; i < applicationList.size(); i++) {
			ApplicationVO applicationVO = applicationList.get(i);
			ReferenceNode refNode = new ReferenceNode();
			String id;
			try {
				id = GContentDAO.getInstance().getContentByID(
						applicationVO.getAppid());
				if (!"".equals(id)) {
					refNode.setRefNodeID(id);
					refNode.setSortID(Integer.parseInt(applicationVO
							.getPosition()));
					list.add(refNode);
				}
			} catch (DAOException e) {
				LOGGER.error("查询主键id失败：contentid=" + applicationVO.getAppid(),
						e);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ReferenceNode refNode = (ReferenceNode) list.get(i);
			Node node = (Node) Repository.getInstance().getNode(
					refNode.getRefNodeID());
			if (node == null) {
				LOGGER.error("无法找到该商品，id=" + refNode.getRefNodeID());
				continue;
			}
			if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// 判断是否是内容。
			{
				LOGGER.error("id出错，该id不是产品类型的id,忽略该数据。id=" + node.getId());
				continue;
			}
			int newSortId;
			if (oldSortIdCollection.containsKey(refNode.getRefNodeID())) {
				int oldSortId = ((Integer) oldSortIdCollection.get(refNode
						.getRefNodeID())).intValue();
				newSortId = refNode.getSortID() - oldSortId;
			} else {
				newSortId = RepositoryConstants.VARIATION_NEW;
			}
			LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + node.getId() + "上架，序列号为" + refNode.getSortID());
			CategoryTools.addGood(category, node.getId(), refNode.getSortID(),
					newSortId, null);

		}
		LOGGER.info("====================货架Id："+ category.getId() + "进行全部上架操作结束=================================");
	}
	
	/**
	 * 全部下架
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void shelvesALL(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================货架Id："+ category.getId() + "进行全部下架操作开始=================================");
		LOGGER.info("下架该分类下的所有商品。cateId=" + category.getId());
		// 商品下架。
		// Category category=(Category)Repository.getInstance().getNode(cateId,
		// RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(
				RepositoryConstants.TYPE_REFERENCE, null, null);
		for (int i = 0; i < subNodes.size(); i++) {
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			// String id = node.getId();//包括应用和数字内容
			String id = node.getRefNodeID();// 只包含应用
			LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + node.getId() + "下架");
			if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// 如果是应用需要填写商品历史表。
			{
				GoodsBO.removeRefContentFromCategory(node.getId());

			} else {
				category.delNode(node);
			}

		}
		category.saveNode();
		LOGGER.info("====================货架Id："+ category.getId() + "进行全部下架操作结束=================================");
	}

	/**
	 * 增量上架
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void addShelves(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================货架Id："+ category.getId() + "进行增量上架操作开始=================================");
		List<ApplicationVO> applicationList = vo.getList();
		List<ReferenceNode> list = new ArrayList<ReferenceNode>();
		for (int i = 0; i < applicationList.size(); i++) {
			ApplicationVO applicationVO = applicationList.get(i);
			ReferenceNode refNode = new ReferenceNode();
			String id;
			try {
				id = GContentDAO.getInstance().getContentByID(
						applicationVO.getAppid());
				if (!"".equals(id)) {
					refNode.setRefNodeID(id);
					refNode.setSortID(Integer.parseInt(applicationVO
							.getPosition()));
					list.add(refNode);
				}
			} catch (DAOException e) {
				LOGGER.error("查询主键id失败：contentid=" + applicationVO.getAppid(),
						e);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ReferenceNode refNode = (ReferenceNode) list.get(i);
			Node node = (Node) Repository.getInstance().getNode(
					refNode.getRefNodeID());
			if (node == null) {
				LOGGER.error("无法找到该商品，id=" + refNode.getRefNodeID());
				continue;
			}
			if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// 判断是否是内容。
			{
				LOGGER.error("id出错，该id不是产品类型的id,忽略该数据。id=" + node.getId());
				continue;
			}
			String id = ReferenceTools.getInstance().isExistRef(
					category.getCategoryID(), refNode.getRefNodeID());// 查看商品表中是否有这个ID
			if (id == null) {// 新增
				LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + node.getId() + "上架，序列号为" + node.getId());
				CategoryTools.addGood(category, node.getId(),
						refNode.getSortID(), 0, null);
			} else {// 修改序号
				LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + node.getId() + "更改序列号" + node.getId());
				ReferenceTools.getInstance().updateSortid(id,
						refNode.getSortID(), 0, null);

			}

		}
		LOGGER.info("====================货架Id："+ category.getId() + "进行增量上架操作结束=================================");

	}

	/**
	 * 增量下架
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void shelves(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================货架Id："+ category.getId() + "进行增量下架操作开始=================================");
		LOGGER.info("下架该分类下的所有商品。cateId=" + category.getId());

		List<ApplicationVO> applicationList = vo.getList();
		List<ReferenceNode> list = new ArrayList<ReferenceNode>();
		for (int i = 0; i < applicationList.size(); i++) {
			ApplicationVO applicationVO = applicationList.get(i);
			ReferenceNode refNode = new ReferenceNode();
			String id;
			try {
				id = GContentDAO.getInstance().getContentByID(
						applicationVO.getAppid());
				if (!"".equals(id)) {
					refNode.setRefNodeID(id);
					list.add(refNode);
				}
			} catch (DAOException e) {
				LOGGER.error("查询主键id失败：contentid=" + applicationVO.getAppid(),
						e);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			ReferenceNode node = list.get(i);
			// String id = node.getId();//包括应用和数字内容
			String id = node.getRefNodeID();// 只包含应用
			String referenceId = ReferenceTools.getInstance().isExistRef(
					category.getCategoryID(), node.getRefNodeID());
			if (referenceId != null) {
				node.setId(referenceId);
				LOGGER.info("货架Id：" + category.getId() + "下，商品Id：" + node.getId() + "下架");
				if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// 如果是应用需要填写商品历史表。
				{
					GoodsBO.removeRefContentFromCategory(node.getId());

				} else {
					category.delNode(node);
				}
			}

		}
		category.saveNode();
		LOGGER.info("====================货架Id："+ category.getId() + "进行增量下架操作结束=================================");

	}

}
