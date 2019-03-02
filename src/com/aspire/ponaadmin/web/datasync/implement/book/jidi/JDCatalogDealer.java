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
		//ɾ�������в����ڵķ��ࡣ
		JDSyncDAO.getInstance().deleteIllegalCate();
		
	}
	public void clearDirtyData()
	{

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{

		String catalogid = (String) record.get(1);
		//Ϊ�˺�ͼ��������֡����е�ר�����඼����ǰ׺c.��Ϊ�ͻ���id��ӳ����ŵ�t_bookcate_mapping���С�
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
				logger.debug("����ר����Ϣ��ʼ��Catalogid=" + catalogid);
			}
			if (JDSyncDAO.getInstance().isExistedCateIdMap(catalogid))
			{
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			else
			{
				//��Ҫ���ȴ������ܣ�Ȼ����t_bookcate_mapping���Ӽ�¼��������������������û��ʹ�����񣬵���֤�����ݵ�һ���ԡ�
				if (logger.isDebugEnabled())
				{
					logger.debug("�������ܷ��ࡣ");
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
				logger.debug("����ר����Ϣ��ʼ��Catalogid=" + catalogid);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(catalogid);
			if (id != null)
			{
				//���»������ơ�
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
					logger.error("�����ڴ˻��ܡ�id=" + id);
					return DataSyncConstants.FAILURE;
				}

			}
			else
			{
				logger.error("������ר������id,�޷����£�Catalogid=" + catalogid);
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}

		}
		else if (changeType.equals("3"))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("ɾ��ר�����࿪ʼ��Catalogid=" + catalogid);
			}
			id = JDSyncDAO.getInstance().getIdByCateId(catalogid);
			if (id != null)
			{
				//��Ҫ��ɾ�����ܣ���ɾ��ӳ��������
				//ɾ�����ܲ��¼ܸû����µ����е���Ʒ
				//delCateAndGoods(id);
				CategoryTools.delCategory(id);
				//ɾ��ӳ��������
				JDSyncDAO.getInstance().deleteCateIdMap(catalogid);

				return DataSyncConstants.SUCCESS_DEL;
			}
			else
			{
				logger.error("������ר��id,�޷�ɾ����Catalogid=" + catalogid);
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
