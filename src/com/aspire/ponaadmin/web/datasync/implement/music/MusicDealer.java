package com.aspire.ponaadmin.web.datasync.implement.music;

import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GMusic;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class MusicDealer implements DataDealer
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(MusicDealer.class);
	private static Category contentRoot;//用于缓存
	private static Category musicRoot;//用于缓存
	private static Category  categoryRoot;
	//private HashMap categoryList=new HashMap();//用于缓存
	/**
     * 分类为空的时候需要上架的货架分类
     */
	private static final String OtherCate="其他";

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		GMusic music = new GMusic();
		//设置ID，直接用户区分内容类型
		music.setContentID((String) record.get(1));
		music.setId("m" + music.getContentID());
		music.setName((String) record.get(2));
		music.setSinger((String) record.get(3));
		music.setCateName((String) record.get(4));
		music.setAlbum((String) record.get(5));
		music.setChangeType((String) record.get(6));
		music.setCreateDate(PublicUtil.getCurDateTime());
		music.setMarketDate(PublicUtil.getCurDateTime());
		
		String cateName=music.getCateName();
		//如果分类内容为空，放入"其他"分类中。
		if(cateName.trim().equals(""))
    	{
    		cateName=OtherCate;
    	}

		// 为了确保系统不会出错，检验同一产品包ID是否存在于系统中
		Node old = null;
		try
		{
			// 根据contentId查询pas内容表中是否存在该内容
			old = contentRoot.getNode(music.getId(), false);
		} catch (BOException e1)
		{
			logger.error("根据产品包ID" + music.getId() + "查询是否存在于系统中时发生了数据库异常，对应的记录忽略处理！");
		}
		// 新增：调用框架将内容添加到内容根分类下；将内容上架到对应的视频货架
		if ("1".equals(music.getChangeType()))
		{
			if (old != null)
			{
				logger.error("新增音乐内容失败，contentID=" + music.getContentID() + "，系统已存在该内容");
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			try
			{
				// 保存内容到数据库             
				contentRoot.addNode(music);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("产品包ID" + music.getContentID() + "对应的记录入库时发生了数据库异常，保存失败", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			try
			{
				Category category =musicRoot;
					//(Category) categoryList.get(cateName);
				/*// 为null表示第一次访问该分类
				if (category == null)
				{
					//查询当前该内容的所属分类是否存在，不存在就要创建
					Searchor search = new Searchor();
					// 构造分类名称相同的搜索条件
					search.getParams().add(
							new SearchParam("name", RepositoryConstants.OP_EQUAL,
									cateName));
					List list = musicRoot.searchNodes(RepositoryConstants.TYPE_CATEGORY,
							search, null);
					// 子类对象
					Category childCategory = null;
					if (null == list || list.size() == 0)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("分类不存在，需要创建：" + cateName);
						}
						// 创建大类
						childCategory = new Category();
						childCategory.setName(cateName);
						childCategory.setDesc(cateName);
						childCategory.setRelation("O");
						String childCategoryID = CategoryBO.getInstance().addCategory(
								musicRoot.getId(), childCategory);
						childCategory = (Category) Repository.getInstance().getNode(
								childCategoryID, RepositoryConstants.TYPE_CATEGORY);
						
					}
					else
					{
						childCategory = (Category) list.get(0);
					}
					// 保存到缓存categoryList中下次可以直接调用
					categoryList.put(cateName, childCategory);
					// 需要下面的代码执行本次数据的上架操作
					category = childCategory;
				}*/
				// 放到目标分类中
				ReferenceNode refNodeRoot = new ReferenceNode();
				refNodeRoot.setRefNodeID(music.getId());
				refNodeRoot.setSortID(0);
				refNodeRoot.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
						+ music.getId() + "|", 39, "0"));
				refNodeRoot.setCategoryID(category.getCategoryID());
				refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
				refNodeRoot.setSortID(0);
				category.addNode(refNodeRoot);
				category.saveNode();
			} catch (BOException e)
			{
				logger.error("产品包ID" + music.getContentID() + "对应的记录上架时发生了数据库异常，保存失败", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			return DataSyncConstants.SUCCESS_ADD;
		}

		// 更新：调用框架对内容进行更新。
		else if ("2".equals((String) music.getChangeType()))
		{
			if (old == null)
			{
				logger.error("更新音乐内容失败，contentID=" + music.getContentID() + "，系统不存在该内容");
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}
			try
			{
				music.save();

			} catch (BOException e)
			{
				logger.error("更新音乐内容出错,contentID="+music.getContentID()+e);
				return DataSyncConstants.FAILURE_UPDATE;
			}
			return DataSyncConstants.SUCCESS_UPDATE;
		}
		// 下线：调用框架对商品和内容进行下线。
		else if ("3".equals(music.getChangeType()))
		{
			if (old == null)
			{
				logger.error("删除音乐内容失败，contentID=" + music.getContentID() + "，系统不存在该内容");
				return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
			}
			// 根据old内容对应的id，查找对应的商品
			try
			{
				Searchor searchor2 = new Searchor();
				searchor2.setIsRecursive(true);
				searchor2.getParams().add(
						new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, music
								.getId()));
				List refList = categoryRoot.searchNodes(RepositoryConstants.TYPE_REFERENCE,
						searchor2, null);

				// 将商品全部下架
				for (int i = 0; i < refList.size(); i++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(i);
					categoryRoot.delNode(ref);
				}
				//一次保存操作
				categoryRoot.saveNode();

				// 调用资源管理的删除内容接口将该内容删除
				contentRoot.delNode(music);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("下架音乐内容出错,contentID"+music.getContentID(),e);
				return DataSyncConstants.FAILURE_DEL;
			}
			return DataSyncConstants.SUCCESS_DEL;
		}
		else
		{
			// 异常情况记录错误信息，不处理
			logger.error("contentID为" + music.getContentID()
					+ "，Changetype类型有误,Changetype=" + music.getChangeType());
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		try
		{
			contentRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CONTENT_ID,
					RepositoryConstants.TYPE_CATEGORY);
			musicRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_MUSIC_ID,
					RepositoryConstants.TYPE_CATEGORY);
			categoryRoot=(Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (BOException e)
		{
			logger.error(e);
		}

	}

	public void clearDirtyData()
	{
		
	}

	public void prepareData()
	{
		
	}

}
