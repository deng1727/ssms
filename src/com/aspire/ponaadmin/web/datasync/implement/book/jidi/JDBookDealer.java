package com.aspire.ponaadmin.web.datasync.implement.book.jidi;

import java.util.Date;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GRead;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class JDBookDealer implements DataDealer
{
	/**
	 * ���ݸ����ࡣ
	 */
	private Category contentRoot;
	private static JLogger logger =LoggerFactory.getLogger(JDBookDealer.class);
	
	public void clearDirtyData()
	{

	}

	public int dealDataRecrod(DataRecord record) throws Exception
	{
		//����vo��
		GRead vo=new GRead();
		vo.setContentID((String)record.get(1));
		vo.setId("r"+vo.getContentID());
		vo.setName((String)record.get(2));
		vo.setIntroduction((String)record.get(3));
		//����ͼ��id
		vo.setContentTag((String)record.get(4));
		  AuthorVO authorVO=JDSyncDAO.getInstance().getAuthorVO(vo.getContentTag());
		  if(authorVO==null)
		  {
			  logger.error("�޷��ҵ�����id="+vo.getContentTag());
			  return DataSyncConstants.FAILURE;
		  }
		  vo.setSinger(authorVO.getName());//����ͼ������
		  vo.setHandBook(authorVO.getDesc());//����ͼ�����
		String dateString=(String)record.get(6);
		Date date=PublicUtil.parseStringToDate(dateString, "yyyyMMddHHmmss");
		vo.setMarketDate(PublicUtil.getDateString(date, "yyyy-MM-dd HH:mm:ss"));
		vo.setContentUrl((String)record.get(7));
		vo.setChargeType((String)record.get(8));
		vo.setFee((String)record.get(9));
		vo.setIsFinish((String)record.get(10));
		vo.setChangeType((String)record.get(11));
		
		String jdCateId=(String)record.get(5);
		String cateId=JDSyncDAO.getInstance().getIdByCateId(jdCateId);
		//��ҪУ��ͼ������Ƿ���ڡ�
		if(cateId==null)
		{
			logger.error("������ͼ����ࣺbookCateId="+jdCateId);
			return DataSyncConstants.FAILURE;
		}
		String changeType=vo.getChangeType();
		
		if(changeType.equals("1"))
		{
			//���ж��Ƿ���ڸ�����
			Node node=Repository.getInstance().getNode(vo.getId());
			
			if(node==null)
			{
				contentRoot.addNode(vo);
				contentRoot.saveNode();
				
				Category pCate=(Category)Repository.getInstance().getNode(cateId, RepositoryConstants.TYPE_CATEGORY);
				//��Ҫ�Զ��ϼܡ�
				//this.addCateGoods(pCate, vo.getId());
				CategoryTools.addGood(pCate, vo.getId(),null);
				return DataSyncConstants.SUCCESS_ADD;
			}else
			{
				logger.error("����ʧ�ܡ�id="+vo.getContentID());
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
			
		}else if(changeType.equals("2"))
		{
			vo.save();
			return DataSyncConstants.SUCCESS_UPDATE;
		}else if(changeType.equals("3"))
		{
			//delAllGoodsByRefId(vo.getId());
			CategoryTools.delAllGoodsByRefId(vo.getId());
			contentRoot.delNode(vo);
			contentRoot.saveNode();
			return DataSyncConstants.SUCCESS_DEL;
		}else
		{
			return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
		}
	}

	public void init(DataSyncConfig config) throws Exception
	{
		contentRoot=(Category)Repository.getInstance().getNode(RepositoryConstants.ROOT_CONTENT_ID, RepositoryConstants.TYPE_CATEGORY);
		
	}

	public void prepareData()
	{
		
	}

}
