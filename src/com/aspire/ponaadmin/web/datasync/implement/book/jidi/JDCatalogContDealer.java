package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.HashMap;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class JDCatalogContDealer implements DataDealer
{
	private HashMap cateList = new HashMap();
	private static JLogger logger = LoggerFactory.getLogger(JDCatalogContDealer.class);

	public void prepareData() throws Exception
	{
		
	}
	public void clearDirtyData()
	{
		cateList.clear();
	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		// ����vo��
		String catalogid = (String) record.get(1);
		catalogid = "c" + catalogid;
		String bookId = "r"+(String) record.get(2);
		String changeType = (String) record.get(3);
		// ͨ��ӳ���ϵ����ר��id�ҵ����ܷ���id
		Category category = (Category) cateList.get(catalogid);
		//���治���ڣ���Ҫ�ؽ�����
		if (category == null)
		{
			String cateId = JDSyncDAO.getInstance().getIdByCateId(catalogid);
			if (cateId == null)
			{
				logger.error("������ר����catalogid=" + catalogid);
				return DataSyncConstants.FAILURE;
			}
			category = (Category) Repository.getInstance().getNode(cateId,
					RepositoryConstants.TYPE_CATEGORY);
			if (category == null)
			{
				logger.error("�����ڻ��ܣ���categoryId=" + cateId);
				return DataSyncConstants.FAILURE;
			}
			cateList.put(catalogid, category);
		}

		if (changeType.equals("1"))
		{
			// ���ж��Ƿ���ڸ�����
			Node node = Repository.getInstance().getNode(bookId);

			if (node == null)
			{
				logger.error("����ר�������ǣ�bookid=" + bookId + ",ͼ�鲻���ڣ��޷��ϼ�");
				return DataSyncConstants.FAILURE;
			}
			else
			{
				CategoryTools.addGood(category, bookId,null);
			}
			return DataSyncConstants.SUCCESS_ADD;

		}
		else if (changeType.equals("2"))
		{
			// vo.save();�޷�����
			return DataSyncConstants.SUCCESS_UPDATE;
		}
		else if (changeType.equals("3"))
		{
			//this.delCateGoods(category, bookId);
			CategoryTools.delGoodsByRefId(category, bookId);
			return DataSyncConstants.SUCCESS_DEL;
		}
		else
		{
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
	}

}
