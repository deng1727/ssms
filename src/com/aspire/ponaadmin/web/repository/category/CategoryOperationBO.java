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
			// ȫ���ϼ�
			if ("0".equals(vo.getAction())) {
				this.allShelves(vo, category);
				// ȫ���¼�
			} else if ("1".equals(vo.getAction())) {
				this.shelvesALL(vo, category);
				// �����ϼ�
			}else if ("2".equals(vo.getAction())) {
				this.addShelves(vo, category);
				// �����¼�
			} else if ("3".equals(vo.getAction())) {
				this.shelves(vo, category);
			} else {
				throw new BOException("�ϼܶ��������ڣ��������ϼ�");
			}
		} catch (BOException e) {
			LOGGER.error("��Ʒ�ϼܷ����쳣��", e);
			throw new BOException("��Ʒ�ϼܷ����쳣��", e);
		}

	}

	/**
	 * ȫ���ϼ�
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void allShelves(ProductShelvesVO vo, Category category)
			throws BOException {

		Map oldSortIdCollection = new HashMap();
		LOGGER.info("====================����Id��"+ category.getId() + "����ȫ���ϼܲ�����ʼ=================================");
		LOGGER.info("�¼ܸû����µ�������Ʒ��cateId=" + category.getId());
		// ��Ʒ�¼ܡ�
		// Category category=(Category)Repository.getInstance().getNode(cateId,
		// RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(
				RepositoryConstants.TYPE_REFERENCE, null, null);
		for (int i = 0; i < subNodes.size(); i++) {
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			oldSortIdCollection.put(node.getRefNodeID(),
					new Integer(node.getSortID()));
			// String id = node.getId();//����Ӧ�ú���������
			String id = node.getRefNodeID();// ֻ����Ӧ��
			LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + id + "�¼�");
			if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// �����Ӧ����Ҫ��д��Ʒ��ʷ��
			{
				GoodsBO.removeRefContentFromCategory(node.getId());

			} else {
				category.delNode(node);
			}

			GContent content = (GContent) Repository.getInstance().getNode(
					node.getRefNodeID(), "nt:gcontent");

		}
		category.saveNode();

		LOGGER.info("�����ϼܲ�����ʼ��");
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
				LOGGER.error("��ѯ����idʧ�ܣ�contentid=" + applicationVO.getAppid(),
						e);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ReferenceNode refNode = (ReferenceNode) list.get(i);
			Node node = (Node) Repository.getInstance().getNode(
					refNode.getRefNodeID());
			if (node == null) {
				LOGGER.error("�޷��ҵ�����Ʒ��id=" + refNode.getRefNodeID());
				continue;
			}
			if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// �ж��Ƿ������ݡ�
			{
				LOGGER.error("id������id���ǲ�Ʒ���͵�id,���Ը����ݡ�id=" + node.getId());
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
			LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + node.getId() + "�ϼܣ����к�Ϊ" + refNode.getSortID());
			CategoryTools.addGood(category, node.getId(), refNode.getSortID(),
					newSortId, null);

		}
		LOGGER.info("====================����Id��"+ category.getId() + "����ȫ���ϼܲ�������=================================");
	}
	
	/**
	 * ȫ���¼�
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void shelvesALL(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================����Id��"+ category.getId() + "����ȫ���¼ܲ�����ʼ=================================");
		LOGGER.info("�¼ܸ÷����µ�������Ʒ��cateId=" + category.getId());
		// ��Ʒ�¼ܡ�
		// Category category=(Category)Repository.getInstance().getNode(cateId,
		// RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(
				RepositoryConstants.TYPE_REFERENCE, null, null);
		for (int i = 0; i < subNodes.size(); i++) {
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			// String id = node.getId();//����Ӧ�ú���������
			String id = node.getRefNodeID();// ֻ����Ӧ��
			LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + node.getId() + "�¼�");
			if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// �����Ӧ����Ҫ��д��Ʒ��ʷ��
			{
				GoodsBO.removeRefContentFromCategory(node.getId());

			} else {
				category.delNode(node);
			}

		}
		category.saveNode();
		LOGGER.info("====================����Id��"+ category.getId() + "����ȫ���¼ܲ�������=================================");
	}

	/**
	 * �����ϼ�
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void addShelves(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================����Id��"+ category.getId() + "���������ϼܲ�����ʼ=================================");
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
				LOGGER.error("��ѯ����idʧ�ܣ�contentid=" + applicationVO.getAppid(),
						e);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			ReferenceNode refNode = (ReferenceNode) list.get(i);
			Node node = (Node) Repository.getInstance().getNode(
					refNode.getRefNodeID());
			if (node == null) {
				LOGGER.error("�޷��ҵ�����Ʒ��id=" + refNode.getRefNodeID());
				continue;
			}
			if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// �ж��Ƿ������ݡ�
			{
				LOGGER.error("id������id���ǲ�Ʒ���͵�id,���Ը����ݡ�id=" + node.getId());
				continue;
			}
			String id = ReferenceTools.getInstance().isExistRef(
					category.getCategoryID(), refNode.getRefNodeID());// �鿴��Ʒ�����Ƿ������ID
			if (id == null) {// ����
				LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + node.getId() + "�ϼܣ����к�Ϊ" + node.getId());
				CategoryTools.addGood(category, node.getId(),
						refNode.getSortID(), 0, null);
			} else {// �޸����
				LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + node.getId() + "�������к�" + node.getId());
				ReferenceTools.getInstance().updateSortid(id,
						refNode.getSortID(), 0, null);

			}

		}
		LOGGER.info("====================����Id��"+ category.getId() + "���������ϼܲ�������=================================");

	}

	/**
	 * �����¼�
	 * 
	 * @param vo
	 * @param category
	 * @throws BOException
	 */
	public void shelves(ProductShelvesVO vo, Category category)
			throws BOException {
		LOGGER.info("====================����Id��"+ category.getId() + "���������¼ܲ�����ʼ=================================");
		LOGGER.info("�¼ܸ÷����µ�������Ʒ��cateId=" + category.getId());

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
				LOGGER.error("��ѯ����idʧ�ܣ�contentid=" + applicationVO.getAppid(),
						e);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			ReferenceNode node = list.get(i);
			// String id = node.getId();//����Ӧ�ú���������
			String id = node.getRefNodeID();// ֻ����Ӧ��
			String referenceId = ReferenceTools.getInstance().isExistRef(
					category.getCategoryID(), node.getRefNodeID());
			if (referenceId != null) {
				node.setId(referenceId);
				LOGGER.info("����Id��" + category.getId() + "�£���ƷId��" + node.getId() + "�¼�");
				if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// �����Ӧ����Ҫ��д��Ʒ��ʷ��
				{
					GoodsBO.removeRefContentFromCategory(node.getId());

				} else {
					category.delNode(node);
				}
			}

		}
		category.saveNode();
		LOGGER.info("====================����Id��"+ category.getId() + "���������¼ܲ�������=================================");

	}

}
