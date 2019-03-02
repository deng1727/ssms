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
	//���ڴ洢���������¼ܵİ񵥵ĸ���
	private int delCount=0;

	public void clearDirtyData()
	{
		cateList.clear();

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		TopListVO vo = new TopListVO();
		//����ID��ֱ���û�������������
		vo.setCateName((String) record.get(1));
		vo.setId("m"+(String) record.get(2));
		vo.setName((String) record.get(3));
		vo.setSortId(-Integer.parseInt((String) record.get(4)));
		//��֤����id�Ƿ���ڣ������ں��ԡ�
		Node node=Repository.getInstance().getNode(vo.getId());
		if(node==null)
		{
			logger.error("�ð񵥲����ڶ�Ӧ����Id�����Ը����ݡ�musicId="+vo.getId());
			return DataSyncConstants.FAILURE_NOT_EXIST;
		}
		
		//�����ڷ��ࡣ
		String cateName = vo.getCateName();
		Category category = (Category) cateList.get(cateName);
		//Ϊnull��ʾ��һ�η��ʸð�
		try
		{
			if (category == null)
			{
				//�ð񵥵ĵ�һ�����ݣ���Ҫ��ѯ�����Ƿ���ڣ�������ھ��¼ܸð��µ�������Ʒ
				//�����ھʹ���

				Searchor search = new Searchor();
				// �������������ͬ����������
				search.getParams().add(
						new SearchParam("name", RepositoryConstants.OP_EQUAL, vo
								.getCateName()));
				List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY,
						search, null);
				//�������
				Category childCategory = null;
				if (null == list || list.size() == 0)
				{
					if (logger.isDebugEnabled())
					{
						logger.debug("���ַ��಻���ڣ���Ҫ������" + cateName);
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
				}
				else
				{
					childCategory = (Category) list.get(0);
					//�Ѿ����ڵİ񵥡���һ����Ҫ�¼ܸð񵥵��������ݡ�
					List goods = childCategory.searchNodes(
							RepositoryConstants.TYPE_REFERENCE, null, null);
					for (int i = 0; i < goods.size(); i++)
					{
						childCategory.delNode((ReferenceNode) goods.get(i));
						delCount++;
					}

					//childCategory.saveNode();
				}
				//���浽hashmap���´ο���ֱ�ӵ���
				cateList.put(cateName, childCategory);
				//��Ҫ����Ĵ���ִ�б������ݵ��ϼܲ���
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
			logger.error("�ϼܰ����ݳ���" + vo, e);
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
