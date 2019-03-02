package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

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

public class JDCategoryDealer implements DataDealer
{
	/**
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(JDCategoryDealer.class);

	private Category cateSet;
	
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
		String cateId = (String) record.get(1);
		if(cateId.equals(""))
		{
			//总体推荐的id是读书分类的父id
			cateId=RepositoryConstants.ROOT_CATEGORY_READ_SUBCATE_ID;
		}
		String cateName = (String) record.get(2);
		String id;
		String changeType = (String) record.get(3);

		if (changeType.equals("1"))//add
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("新增图书分类信息开始，cateId=" + cateId);
			}
			if (JDSyncDAO.getInstance().isExistedCateIdMap(cateId))
			{
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			else
			{
				//需要首先创建货架，然后在t_bookcate_mapping增加记录，这样操作，这样操作没有使用事务，但保证了数据的一致性。
				if (logger.isDebugEnabled())
				{
					logger.debug("新增图书分类。cateId=" + cateId);
				}
				try
				{
					//id = createCategory(cateSet, cateName);//craateCategory(cateName);
					id=CategoryTools.createCategory(cateSet, cateName);
				} catch (BOException e)
				{
					logger.error(e);
					return DataSyncConstants.FAILURE_ADD;
				}
				JDSyncDAO.getInstance().insertCateIdMap(cateId, id);
				return DataSyncConstants.SUCCESS_ADD;
			}

		}
		else if (changeType.equals("2"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("更新图书分类信息开始，cateId=" + cateId);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(cateId);
			if (id != null)
			{
				//更新货架名称。
				Category category = (Category) Repository.getInstance().getNode(id,
						RepositoryConstants.TYPE_CATEGORY);
				if (category != null)
				{
					category.setName(cateName);
					category.setDesc(cateName);
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
				logger.error("不存在图书分类id,无法更新！cateId=" + cateId);
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}

		}
		else if (changeType.equals("3"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("删除图书分类信息开始，cateId=" + cateId);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(cateId);
			if (id != null)
			{
				//需要先删除货架，在删除映射表的数据
				//删除货架并下架该货架下的所有的商品
				//delCateAndGoods(id);
				CategoryTools.delCategory(id);
				//删除映射表的数据
				JDSyncDAO.getInstance().deleteCateIdMap(cateId);

				return DataSyncConstants.SUCCESS_DEL;
			}
			else
			{
				logger.error("不存在图书分类id,无法删除！cateId=" + cateId);
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
		cateSet = (Category) Repository.getInstance().getNode(
				RepositoryConstants.ROOT_CATEGORY_READ_SUBCATE_ID,
				RepositoryConstants.TYPE_CATEGORY);
	}

}
