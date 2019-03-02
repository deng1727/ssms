/**
 * <p>
 * 对解析后的动漫数据进行处理的BO类
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 4, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import java.util.Date;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GComic;
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
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author dongke
 *
 */
public class ComicDealer implements DataDealer {

	
	
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ComicDealer.class);
	private static Category contentRoot;// 用于缓存
	private static Category comicRoot;// 用于缓存
	private static Category categoryRoot;// 用于缓存
	
	public void prepareData() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#dealDataRecrod(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	public int dealDataRecrod(DataRecord record) throws Exception {
	

		// 将record中的的记录转换成GComic对象
		GComic gComic = new GComic();
		gComic.setContentID((String) record.get(1));
		gComic.setName((String)record.get(2));
		gComic.setIntroduction((String) record.get(3));
		gComic.setAuthor((String) record.get(4));
		gComic.setCateName((String) record.get(5));
		gComic.setContentUrl((String)record.get(6));
		gComic.setInvalidTime((String)record.get(7));
		gComic.setChangeType((String)record.get(8));
		gComic.setCreateDate(PublicUtil.getCurDateTime());
		gComic.setMarketDate(PublicUtil.getCurDateTime());
		
		//设置ID，直接用户区分内容类型
		gComic.setId("c" + gComic.getContentID());
		// 为了确保系统不会出错，检验同一产品包ID是否存在于系统中
		Node old = null;
		try
		{
			// 根据contentId查询pas内容表中是否存在该内容
			old = contentRoot.getNode(gComic.getId(), false);
		} catch (BOException e1)
		{
			logger.error("根据产品包ID" + gComic.getId() + "查询是否存在于系统中时发生了数据库异常，对应的记录忽略处理！");
		}
		// 新增：调用框架将内容添加到内容根分类下；将内容上架到对应的动漫货架
		if ("1".equals(gComic.getChangeType()))
		{
			if(old != null)
			{
				logger.error("新增动漫内容失败，contentID="+gComic.getContentID()+"，系统已存在该动漫内容");
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			try
			{
				  // 保存内容到数据库             
                contentRoot.addNode(gComic);
                logger.info(gComic.toString());//add  temp
                contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("产品包ID" + gComic.getContentID() + "对应的记录入库时发生了数据库异常，保存失败", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			try
			{
				// 放到目标分类中
				ReferenceNode refNodeRoot = new ReferenceNode();
				refNodeRoot.setRefNodeID(gComic.getId());
				refNodeRoot.setSortID(0);
				refNodeRoot.setGoodsID(PublicUtil.rPad(comicRoot.getCategoryID() + "|"
						+ gComic.getId() + "|", 39, "0"));
				refNodeRoot.setCategoryID(comicRoot.getCategoryID());
				refNodeRoot.setLoadDate(DateUtil.formatDate(new Date(),
						"yyyy-MM-dd HH:mm:ss"));
				//refNodeRoot.setSortID(-1 * gComic.getSortnumber());
				refNodeRoot.setSortID(0);
				comicRoot.addNode(refNodeRoot);
				comicRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("产品包ID" + gComic.getContentID() + "对应的记录上架时发生了数据库异常，保存失败", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			return DataSyncConstants.SUCCESS_ADD;
			
			//更新：调用框架对内容进行更新。
		}else if("2".equals(gComic.getChangeType()))
		{

			if(old == null)
			{
				logger.error("更新动漫内容失败，contentID="+gComic.getContentID()+"，系统不存在该动漫内容");
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}
			try
			{
				gComic.save();
				// 找到上架的商品，对排序顺序进行重新设定
				Searchor searchor1 = new Searchor();
				searchor1.getParams().add(
						new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, gComic
								.getId()));
				List refList = comicRoot.searchNodes(RepositoryConstants.TYPE_REFERENCE,
						searchor1, null);
				ReferenceNode ref = (ReferenceNode) refList.get(0);
				//ref.setSortID(-1 * video.getSortnumber());
				ref.setSortID(0);
				ref.save();
			} catch (BOException e)
			{
				logger.error(e);
				return DataSyncConstants.FAILURE_UPDATE;
			}
			return DataSyncConstants.SUCCESS_UPDATE;
	
	 }// 下线：调用框架对商品和内容进行下线。
		else if ("3".equals(gComic.getChangeType()) )
		{
			// 根据old内容对应的id，查找对应的商品
			if(old==null)
			{
				logger.error("删除动漫内容失败，contentID="+gComic.getContentID()+"，系统不存在该动漫内容");
				return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
			}
			try
			{
				Searchor searchor2 = new Searchor();
				searchor2.setIsRecursive(true);
				searchor2.getParams().add(
						new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, old
								.getId()));
				List refList = categoryRoot.searchNodes(RepositoryConstants.TYPE_REFERENCE,
						searchor2, null);

				// 将商品全部下架
				for (int i = 0; i < refList.size(); i++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(i);
					categoryRoot.delNode(ref);
				}
				categoryRoot.saveNode();

				// 调用资源管理的删除内容接口将该内容删除
				contentRoot.delNode(old);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("调用框架对商品和内容进行下线时发生数据库异常，对应产品ID为" + old.getId()
						+ ",本条记录处理失败！",e);
				return DataSyncConstants.FAILURE_DEL;
			}
			return DataSyncConstants.SUCCESS_DEL;
		}else
		{
			logger.error("contentID为" + gComic.getContentID() + "，Changetype类型有误,Changetype="+gComic.getChangeType());
			return 	DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}

	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#clearDirtyData()
	 */
	public void clearDirtyData() {
		
	}

	public void init(DataSyncConfig config)
	{
		try
		{
			contentRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CONTENT_ID,
					RepositoryConstants.TYPE_CATEGORY);
			comicRoot = (Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_COMIC_ID,
					RepositoryConstants.TYPE_CATEGORY);
			categoryRoot=(Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (BOException e)
		{
			logger.error(e);
		}

	}

}
