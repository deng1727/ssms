package com.aspire.ponaadmin.web.datasync.implement.music;

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

public class MusicHotTopDealer implements DataDealer
{
	private static final JLogger logger = LoggerFactory.getLogger(MusicHotTopDealer.class);
	private HashMap cateList = new HashMap();
	private Category topListCate;
	//用于存储本次任务下架的榜单的个数
	private int delCount=0;

	public void clearDirtyData()
	{
		cateList.clear();

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		TopListVO vo = new TopListVO();
		//设置ID，直接用户区分内容类型
		vo.setCateName((String) record.get(1));
		vo.setId("m"+(String) record.get(2));
		vo.setName((String) record.get(3));
		vo.setSortId(-Integer.parseInt((String) record.get(4)));
		//验证音乐id是否存在，不存在忽略。
		Node node=Repository.getInstance().getNode(vo.getId());
		if(node==null)
		{
			logger.error("该榜单不存在对应音乐Id，忽略该数据。musicId="+vo.getId());
			return DataSyncConstants.FAILURE_NOT_EXIST;
		}
		
		//榜单所在分类。
		String cateName = vo.getCateName();
		Category category = (Category) cateList.get(cateName);
		//为null表示第一次访问该榜单
		try
		{
			if (category == null)
			{
				//该榜单的第一个数据，需要查询榜单是是否存在，如果存在就下架该榜单下的所有商品
				//不存在就创建

				Searchor search = new Searchor();
				// 构造大类名称相同的搜索条件
				search.getParams().add(
						new SearchParam("name", RepositoryConstants.OP_EQUAL, vo
								.getCateName()));
				List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY,
						search, null);
				//子类对象
				Category childCategory = null;
				if (null == list || list.size() == 0)
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("音乐分类不存在，需要创建：" + cateName);
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
				}
				else
				{
					childCategory = (Category) list.get(0);
					//已经存在的榜单。第一次需要下架该榜单的所有数据。
					List goods = childCategory.searchNodes(
							RepositoryConstants.TYPE_REFERENCE, null, null);
					for (int i = 0; i < goods.size(); i++)
					{
						childCategory.delNode((ReferenceNode) goods.get(i));
						delCount++;
					}

					//childCategory.saveNode();
				}
				//保存到hashmap中下次可以直接调用
				cateList.put(cateName, childCategory);
				//需要下面的代码执行本次数据的上架操作
				category = childCategory;
			}
			ReferenceNode ref = new ReferenceNode();
			ref.setRefNodeID(vo.getId());
			ref.setSortID(vo.getSortId());
			ref.setCategoryID(category.getCategoryID());
			ref.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|" + vo.getId()
					+ "|", 39, "0"));
			ref.setLoadDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			category.addNode(ref);
			category.saveNode();

		} catch (BOException e)
		{
			logger.error("上架榜单数据出错：" + vo, e);
			return DataSyncConstants.FAILURE_ADD;
		}
		return DataSyncConstants.SUCCESS_ADD;
	}

	public void init(DataSyncConfig config) throws Exception
	{
		topListCate = (Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_MUSIC_TOPLIDT_ID,
				RepositoryConstants.TYPE_CATEGORY);

	}

	public int getDelCount()
	{
		return delCount;
	}

	public void clearDelCount()
	{
		this.delCount = 0;
	}

	public void prepareData()
	{
		
	}

}
