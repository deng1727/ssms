package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.a8.TopListVO;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class JDTopListDealer implements DataDealer
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(JDTopListDealer.class);

	private HashMap cateList = new HashMap();
	private Category topListCate;
	// ���ڴ洢���������¼ܵİ񵥵ĸ���

	public void clearDirtyData()
	{

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{

		TopListVO vo = new TopListVO();
		// ����ID��ֱ���û�������������
		vo.setCateName((String) record.get(2));
		vo.setId("r" + (String) record.get(3));

		vo.setSortId(-Integer.parseInt((String) record.get(4)));

		String changetype = (String) record.get(5);
		// ��֤����id�Ƿ���ڣ������ں��ԡ�
		Node node = Repository.getInstance().getNode(vo.getId());
		if (node == null)
		{
			logger.error("�ð񵥲����ڶ�Ӧ����Id�����Ը����ݡ�musicId=" + vo.getId());
			return DataSyncConstants.FAILURE_NOT_EXIST;
		}

		Searchor search = null;
		ReferenceNode book = null;

		// �����ڷ��ࡣ
		String cateName = vo.getCateName();
		Category category = (Category) cateList.get(cateName);
		// Ϊnull��ʾ��һ�η��ʸð�
		try
		{
			if (category == null)
			{
				// �ð񵥵ĵ�һ�����ݣ���Ҫ��ѯ�����Ƿ���ڣ�������ھ��¼ܸð��µ�������Ʒ
				// �����ھʹ���

				search = new Searchor();
				// �������������ͬ����������
				search.getParams().add(
						new SearchParam("name", RepositoryConstants.OP_EQUAL, vo
								.getCateName()));
				List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY,
						search, null);
				// �������
				Category childCategory = null;
				if (null == list || list.size() == 0)
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("�񵥷��಻���ڣ���Ҫ������" + cateName);
					}
					// ��������
					childCategory = new Category();
					childCategory.setName(cateName);
					childCategory.setDesc(cateName);
					childCategory.setRelation("O");
					String childCategoryID = CategoryBO.getInstance().addCategory(
							topListCate.getId(), childCategory);
					childCategory = (Category) Repository.getInstance().getNode(
							childCategoryID, RepositoryConstants.TYPE_CATEGORY);
					// ���浽hashmap���´ο���ֱ�ӵ���
					cateList.put(cateName, childCategory);
				}else
				{
					childCategory=(Category)list.get(0);
				}
				
				// ��Ҫ����Ĵ���ִ�б������ݵ��ϼܲ���
				category = childCategory;
			}
			// ��Ҫ��ѯ�÷������Ƿ�
			search = new Searchor();
			// �������������ͬ����������
			search.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, vo.getId()));
			List books = category.searchNodes(RepositoryConstants.TYPE_REFERENCE, search,
					null);
			if (books.size() != 0)
			{
				book = (ReferenceNode) books.get(0);
			}

			if (changetype.equals("1"))// ��Ҫ������
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("����ͼ��񵥿�ʼ��recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book != null)
				{
					logger.error("��ǰͼ����У��Ѵ���bookid=" + vo.getId() + ",�飬�����ϼ�");
					return DataSyncConstants.FAILURE_ADD_EXIST;
				}
				if (logger.isDebugEnabled())
				{
					logger.info("�ϼ�ͼ��bookid=" + vo.getId() + ",������"+category.getName()+"");
				}
				ReferenceNode ref = new ReferenceNode();
				ref.setRefNodeID(vo.getId());
				ref.setSortID(vo.getSortId());
				ref.setCategoryID(category.getCategoryID());
				ref.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
						+ vo.getId() + "|", 39, "0"));
				ref.setLoadDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				category.addNode(ref);
				category.saveNode();

				return DataSyncConstants.SUCCESS_ADD;
			}
			else if (changetype.equals("2"))
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("����ͼ��񵥿�ʼ��recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book == null)
				{
					logger.error("��ǰͼ����У�������bookid=" + vo.getId() + ",�飬���ܸ���");
					return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;

				}
				else
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("����ͼ����bookid=" + vo.getId());
					}
					book.setRefNodeID(vo.getId());
					book.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
							+ vo.getId() + "|", 39, "0"));
					book.setSortID(vo.getSortId());
					book.save();
					return DataSyncConstants.SUCCESS_UPDATE;
				}

				/*ReferenceNode ref = new ReferenceNode();
				ref.setRefNodeID(vo.getId());
				ref.setSortID(vo.getSortId());
				ref.setCategoryID(category.getCategoryID());
				ref.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
						+ vo.getId() + "|", 39, "0"));
				ref.setLoadDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				category.addNode(ref);
				category.saveNode();*/

				
			}
			else if (changetype.equals("3"))
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("����ͼ��񵥿�ʼ��recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book == null)
				{
					logger.error("��ǰͼ����У�������bookid=" + vo.getId() + ",�飬����ɾ��");
					return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;

				}
				else
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("��ǰͼ����У�����bookid=" + vo.getId() + ",�飬����ɾ��");
					}
					category.delNode(book);
					category.saveNode();
					return DataSyncConstants.SUCCESS_DEL;
				}
			}
			else
			{
				return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
			}

		} catch (BOException e)
		{
			logger.error("�ϼܰ����ݳ���" + vo, e);
			return DataSyncConstants.FAILURE;
		}

	}

	public void init(DataSyncConfig config) throws Exception
	{
		topListCate=( Category ) Repository.getInstance()
		.getNode(RepositoryConstants.ROOT_CATEGORY_READ_TOP_ID,RepositoryConstants.TYPE_CATEGORY);
	}

	public void prepareData() throws Exception
	{
		
	}

}
