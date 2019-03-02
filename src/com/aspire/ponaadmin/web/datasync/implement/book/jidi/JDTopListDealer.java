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
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(JDTopListDealer.class);

	private HashMap cateList = new HashMap();
	private Category topListCate;
	// 用于存储本次任务下架的榜单的个数

	public void clearDirtyData()
	{

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{

		TopListVO vo = new TopListVO();
		// 设置ID，直接用户区分内容类型
		vo.setCateName((String) record.get(2));
		vo.setId("r" + (String) record.get(3));

		vo.setSortId(-Integer.parseInt((String) record.get(4)));

		String changetype = (String) record.get(5);
		// 验证音乐id是否存在，不存在忽略。
		Node node = Repository.getInstance().getNode(vo.getId());
		if (node == null)
		{
			logger.error("该榜单不存在对应音乐Id，忽略该数据。musicId=" + vo.getId());
			return DataSyncConstants.FAILURE_NOT_EXIST;
		}

		Searchor search = null;
		ReferenceNode book = null;

		// 榜单所在分类。
		String cateName = vo.getCateName();
		Category category = (Category) cateList.get(cateName);
		// 为null表示第一次访问该榜单
		try
		{
			if (category == null)
			{
				// 该榜单的第一个数据，需要查询榜单是是否存在，如果存在就下架该榜单下的所有商品
				// 不存在就创建

				search = new Searchor();
				// 构造大类名称相同的搜索条件
				search.getParams().add(
						new SearchParam("name", RepositoryConstants.OP_EQUAL, vo
								.getCateName()));
				List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY,
						search, null);
				// 子类对象
				Category childCategory = null;
				if (null == list || list.size() == 0)
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("榜单分类不存在，需要创建：" + cateName);
					}
					// 创建大类
					childCategory = new Category();
					childCategory.setName(cateName);
					childCategory.setDesc(cateName);
					childCategory.setRelation("O");
					String childCategoryID = CategoryBO.getInstance().addCategory(
							topListCate.getId(), childCategory);
					childCategory = (Category) Repository.getInstance().getNode(
							childCategoryID, RepositoryConstants.TYPE_CATEGORY);
					// 保存到hashmap中下次可以直接调用
					cateList.put(cateName, childCategory);
				}else
				{
					childCategory=(Category)list.get(0);
				}
				
				// 需要下面的代码执行本次数据的上架操作
				category = childCategory;
			}
			// 需要查询该分类下是否
			search = new Searchor();
			// 构造大类名称相同的搜索条件
			search.getParams().add(
					new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, vo.getId()));
			List books = category.searchNodes(RepositoryConstants.TYPE_REFERENCE, search,
					null);
			if (books.size() != 0)
			{
				book = (ReferenceNode) books.get(0);
			}

			if (changetype.equals("1"))// 需要新增榜单
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("新增图书榜单开始，recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book != null)
				{
					logger.error("当前图书榜单中，已存在bookid=" + vo.getId() + ",书，不能上架");
					return DataSyncConstants.FAILURE_ADD_EXIST;
				}
				if (logger.isDebugEnabled())
				{
					logger.info("上架图书bookid=" + vo.getId() + ",到货架"+category.getName()+"");
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
					logger.debug("更新图书榜单开始，recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book == null)
				{
					logger.error("当前图书榜单中，不存在bookid=" + vo.getId() + ",书，不能更新");
					return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;

				}
				else
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("更新图书在bookid=" + vo.getId());
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
					logger.debug("更新图书榜单开始，recommendName=" + vo.getCateName()
							+ ",bookId=" + vo.getId());
				}
				if (book == null)
				{
					logger.error("当前图书榜单中，不存在bookid=" + vo.getId() + ",书，不能删除");
					return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;

				}
				else
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("当前图书榜单中，存在bookid=" + vo.getId() + ",书，可以删除");
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
			logger.error("上架榜单数据出错：" + vo, e);
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
