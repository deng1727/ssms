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
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(MusicDealer.class);
	private static Category contentRoot;//���ڻ���
	private static Category musicRoot;//���ڻ���
	private static Category  categoryRoot;
	//private HashMap categoryList=new HashMap();//���ڻ���
	/**
     * ����Ϊ�յ�ʱ����Ҫ�ϼܵĻ��ܷ���
     */
	private static final String OtherCate="����";

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		GMusic music = new GMusic();
		//����ID��ֱ���û�������������
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
		//�����������Ϊ�գ�����"����"�����С�
		if(cateName.trim().equals(""))
    	{
    		cateName=OtherCate;
    	}

		// Ϊ��ȷ��ϵͳ�����������ͬһ��Ʒ��ID�Ƿ������ϵͳ��
		Node old = null;
		try
		{
			// ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
			old = contentRoot.getNode(music.getId(), false);
		} catch (BOException e1)
		{
			logger.error("���ݲ�Ʒ��ID" + music.getId() + "��ѯ�Ƿ������ϵͳ��ʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
		}
		// ���������ÿ�ܽ�������ӵ����ݸ������£��������ϼܵ���Ӧ����Ƶ����
		if ("1".equals(music.getChangeType()))
		{
			if (old != null)
			{
				logger.error("������������ʧ�ܣ�contentID=" + music.getContentID() + "��ϵͳ�Ѵ��ڸ�����");
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			try
			{
				// �������ݵ����ݿ�             
				contentRoot.addNode(music);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("��Ʒ��ID" + music.getContentID() + "��Ӧ�ļ�¼���ʱ���������ݿ��쳣������ʧ��", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			try
			{
				Category category =musicRoot;
					//(Category) categoryList.get(cateName);
				/*// Ϊnull��ʾ��һ�η��ʸ÷���
				if (category == null)
				{
					//��ѯ��ǰ�����ݵ����������Ƿ���ڣ������ھ�Ҫ����
					Searchor search = new Searchor();
					// �������������ͬ����������
					search.getParams().add(
							new SearchParam("name", RepositoryConstants.OP_EQUAL,
									cateName));
					List list = musicRoot.searchNodes(RepositoryConstants.TYPE_CATEGORY,
							search, null);
					// �������
					Category childCategory = null;
					if (null == list || list.size() == 0)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("���಻���ڣ���Ҫ������" + cateName);
						}
						// ��������
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
					// ���浽����categoryList���´ο���ֱ�ӵ���
					categoryList.put(cateName, childCategory);
					// ��Ҫ����Ĵ���ִ�б������ݵ��ϼܲ���
					category = childCategory;
				}*/
				// �ŵ�Ŀ�������
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
				logger.error("��Ʒ��ID" + music.getContentID() + "��Ӧ�ļ�¼�ϼ�ʱ���������ݿ��쳣������ʧ��", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			return DataSyncConstants.SUCCESS_ADD;
		}

		// ���£����ÿ�ܶ����ݽ��и��¡�
		else if ("2".equals((String) music.getChangeType()))
		{
			if (old == null)
			{
				logger.error("������������ʧ�ܣ�contentID=" + music.getContentID() + "��ϵͳ�����ڸ�����");
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}
			try
			{
				music.save();

			} catch (BOException e)
			{
				logger.error("�����������ݳ���,contentID="+music.getContentID()+e);
				return DataSyncConstants.FAILURE_UPDATE;
			}
			return DataSyncConstants.SUCCESS_UPDATE;
		}
		// ���ߣ����ÿ�ܶ���Ʒ�����ݽ������ߡ�
		else if ("3".equals(music.getChangeType()))
		{
			if (old == null)
			{
				logger.error("ɾ����������ʧ�ܣ�contentID=" + music.getContentID() + "��ϵͳ�����ڸ�����");
				return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
			}
			// ����old���ݶ�Ӧ��id�����Ҷ�Ӧ����Ʒ
			try
			{
				Searchor searchor2 = new Searchor();
				searchor2.setIsRecursive(true);
				searchor2.getParams().add(
						new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, music
								.getId()));
				List refList = categoryRoot.searchNodes(RepositoryConstants.TYPE_REFERENCE,
						searchor2, null);

				// ����Ʒȫ���¼�
				for (int i = 0; i < refList.size(); i++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(i);
					categoryRoot.delNode(ref);
				}
				//һ�α������
				categoryRoot.saveNode();

				// ������Դ�����ɾ�����ݽӿڽ�������ɾ��
				contentRoot.delNode(music);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("�¼��������ݳ���,contentID"+music.getContentID(),e);
				return DataSyncConstants.FAILURE_DEL;
			}
			return DataSyncConstants.SUCCESS_DEL;
		}
		else
		{
			// �쳣�����¼������Ϣ��������
			logger.error("contentIDΪ" + music.getContentID()
					+ "��Changetype��������,Changetype=" + music.getChangeType());
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
