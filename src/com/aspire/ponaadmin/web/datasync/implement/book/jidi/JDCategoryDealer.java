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
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(JDCategoryDealer.class);

	private Category cateSet;
	
	public void prepareData() throws Exception
	{
		//ɾ�������в����ڵķ��ࡣ
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
			//�����Ƽ���id�Ƕ������ĸ�id
			cateId=RepositoryConstants.ROOT_CATEGORY_READ_SUBCATE_ID;
		}
		String cateName = (String) record.get(2);
		String id;
		String changeType = (String) record.get(3);

		if (changeType.equals("1"))//add
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("����ͼ�������Ϣ��ʼ��cateId=" + cateId);
			}
			if (JDSyncDAO.getInstance().isExistedCateIdMap(cateId))
			{
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			else
			{
				//��Ҫ���ȴ������ܣ�Ȼ����t_bookcate_mapping���Ӽ�¼��������������������û��ʹ�����񣬵���֤�����ݵ�һ���ԡ�
				if (logger.isDebugEnabled())
				{
					logger.debug("����ͼ����ࡣcateId=" + cateId);
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
				logger.debug("����ͼ�������Ϣ��ʼ��cateId=" + cateId);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(cateId);
			if (id != null)
			{
				//���»������ơ�
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
					logger.error("�����ڴ˻��ܡ�id=" + id);
					return DataSyncConstants.FAILURE;
				}

			}
			else
			{
				logger.error("������ͼ�����id,�޷����£�cateId=" + cateId);
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}

		}
		else if (changeType.equals("3"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("ɾ��ͼ�������Ϣ��ʼ��cateId=" + cateId);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(cateId);
			if (id != null)
			{
				//��Ҫ��ɾ�����ܣ���ɾ��ӳ��������
				//ɾ�����ܲ��¼ܸû����µ����е���Ʒ
				//delCateAndGoods(id);
				CategoryTools.delCategory(id);
				//ɾ��ӳ��������
				JDSyncDAO.getInstance().deleteCateIdMap(cateId);

				return DataSyncConstants.SUCCESS_DEL;
			}
			else
			{
				logger.error("������ͼ�����id,�޷�ɾ����cateId=" + cateId);
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
