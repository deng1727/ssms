package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.Date;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class JDCatalogDealer implements DataDealer
{
	private static final JLogger logger = LoggerFactory.getLogger(JDCatalogDealer.class);

	private Category catalogRoot;

	public void prepareData() throws Exception
	{
		//删除货架中不存在的分类。
		JDSyncDAO.getInstance().deleteIllegalCate();
		
	}
	public void clearDirtyData()
	{

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{

		String catalogid = (String) record.get(1);
		//为了和图书分类区分。所有的专区分类都加上前缀c.因为和货架id的映射表都放到t_bookcate_mapping表中。
		catalogid="c"+catalogid;
		String catalogName = (String) record.get(2);
		String desc = (String) record.get(4);

		Category cate = new Category();
		cate.setName(catalogName);
		cate.setDesc(desc);
		cate.setRelation("O");
		cate.setCtype(0);
		cate.setDelFlag(0);
		cate.setChangeDate(new Date());
		cate.setState(1);
		cate.setSortID(0);
		// String picURL=(String)record.get(1);     
		String changeType = (String) record.get(6);

		String id;

		if (changeType.equals("1"))//add
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("新增专区信息开始，Catalogid=" + catalogid);
			}
			if (JDSyncDAO.getInstance().isExistedCateIdMap(catalogid))
			{
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			else
			{
				//需要首先创建货架，然后在t_bookcate_mapping增加记录，这样操作，这样操作没有使用事务，但保证了数据的一致性。
				if (logger.isDebugEnabled())
				{
					logger.debug("新增货架分类。");
				}

				try
				{
					//id = createCategory(catalogRoot, cate);//craateCategory(cateName);
					id=CategoryTools.createCategory(catalogRoot, cate);
				} catch (BOException e)
				{
					logger.error(e);
					return DataSyncConstants.FAILURE_ADD;
				}
				JDSyncDAO.getInstance().insertCateIdMap(catalogid, id);
				return DataSyncConstants.SUCCESS_ADD;
			}

		}
		else if (changeType.equals("2"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("更新专区信息开始，Catalogid=" + catalogid);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(catalogid);
			if (id != null)
			{
				//更新货架名称。
				Category category = (Category) Repository.getInstance().getNode(id,
						RepositoryConstants.TYPE_CATEGORY);
				if (category != null)
				{
					category.setName(catalogName);
					category.setDesc(catalogName);
					category.save();
					return DataSyncConstants.SUCCESS_UPDATE;
				}
				else
				{
					logger.error("不存在此货架。id=" + id);
					return DataSyncConstants.FAILURE;
				}

			}
			else
			{
				logger.error("不存在专区分类id,无法更新！Catalogid=" + catalogid);
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}

		}
		else if (changeType.equals("3"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("删除专区分类开始，Catalogid=" + catalogid);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(catalogid);
			if (id != null)
			{
				//需要先删除货架，在删除映射表的数据
				//删除货架并下架该货架下的所有的商品
				//delCateAndGoods(id);
				CategoryTools.delCategory(id);
				//删除映射表的数据
				JDSyncDAO.getInstance().deleteCateIdMap(catalogid);

				return DataSyncConstants.SUCCESS_DEL;
			}
			else
			{
				logger.error("不存在专区id,无法删除！Catalogid=" + catalogid);
				return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
			}

		}
		else
		{
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		catalogRoot = (Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_READ_CATALOG_ID,
				RepositoryConstants.TYPE_CATEGORY);

	}

}
