/**
 * <p>
 * �Խ�����Ķ������ݽ��д����BO��
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
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(ComicDealer.class);
	private static Category contentRoot;// ���ڻ���
	private static Category comicRoot;// ���ڻ���
	private static Category categoryRoot;// ���ڻ���
	
	public void prepareData() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#dealDataRecrod(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	public int dealDataRecrod(DataRecord record) throws Exception {
	

		// ��record�еĵļ�¼ת����GComic����
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
		
		//����ID��ֱ���û�������������
		gComic.setId("c" + gComic.getContentID());
		// Ϊ��ȷ��ϵͳ�����������ͬһ��Ʒ��ID�Ƿ������ϵͳ��
		Node old = null;
		try
		{
			// ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
			old = contentRoot.getNode(gComic.getId(), false);
		} catch (BOException e1)
		{
			logger.error("���ݲ�Ʒ��ID" + gComic.getId() + "��ѯ�Ƿ������ϵͳ��ʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
		}
		// ���������ÿ�ܽ�������ӵ����ݸ������£��������ϼܵ���Ӧ�Ķ�������
		if ("1".equals(gComic.getChangeType()))
		{
			if(old != null)
			{
				logger.error("������������ʧ�ܣ�contentID="+gComic.getContentID()+"��ϵͳ�Ѵ��ڸö�������");
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			try
			{
				  // �������ݵ����ݿ�             
                contentRoot.addNode(gComic);
                logger.info(gComic.toString());//add  temp
                contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("��Ʒ��ID" + gComic.getContentID() + "��Ӧ�ļ�¼���ʱ���������ݿ��쳣������ʧ��", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			try
			{
				// �ŵ�Ŀ�������
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
				logger.error("��Ʒ��ID" + gComic.getContentID() + "��Ӧ�ļ�¼�ϼ�ʱ���������ݿ��쳣������ʧ��", e);
				return DataSyncConstants.FAILURE_ADD;
			}
			return DataSyncConstants.SUCCESS_ADD;
			
			//���£����ÿ�ܶ����ݽ��и��¡�
		}else if("2".equals(gComic.getChangeType()))
		{

			if(old == null)
			{
				logger.error("���¶�������ʧ�ܣ�contentID="+gComic.getContentID()+"��ϵͳ�����ڸö�������");
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}
			try
			{
				gComic.save();
				// �ҵ��ϼܵ���Ʒ��������˳����������趨
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
	
	 }// ���ߣ����ÿ�ܶ���Ʒ�����ݽ������ߡ�
		else if ("3".equals(gComic.getChangeType()) )
		{
			// ����old���ݶ�Ӧ��id�����Ҷ�Ӧ����Ʒ
			if(old==null)
			{
				logger.error("ɾ����������ʧ�ܣ�contentID="+gComic.getContentID()+"��ϵͳ�����ڸö�������");
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

				// ����Ʒȫ���¼�
				for (int i = 0; i < refList.size(); i++)
				{
					ReferenceNode ref = (ReferenceNode) refList.get(i);
					categoryRoot.delNode(ref);
				}
				categoryRoot.saveNode();

				// ������Դ�����ɾ�����ݽӿڽ�������ɾ��
				contentRoot.delNode(old);
				contentRoot.saveNode();
			} catch (BOException e)
			{
				logger.error("���ÿ�ܶ���Ʒ�����ݽ�������ʱ�������ݿ��쳣����Ӧ��ƷIDΪ" + old.getId()
						+ ",������¼����ʧ�ܣ�",e);
				return DataSyncConstants.FAILURE_DEL;
			}
			return DataSyncConstants.SUCCESS_DEL;
		}else
		{
			logger.error("contentIDΪ" + gComic.getContentID() + "��Changetype��������,Changetype="+gComic.getChangeType());
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
