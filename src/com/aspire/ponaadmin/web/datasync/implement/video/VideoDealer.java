package com.aspire.ponaadmin.web.datasync.implement.video;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * <p>
 * �Խ��������Ƶ���ݽ��д����BO��
 * </p>
 * <p>
 * Copyright (c) 2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.1.1.0
 */
public class VideoDealer implements DataDealer
{

	/**
	 * ��־����
	 */
	private static final JLogger logger = LoggerFactory.getLogger(VideoDealer.class);
//	private static Category contentRoot;// ���ڻ���
//	private static Category videoRoot;// ���ڻ���
//	private static Category categoryRoot;// ���ڻ���

	public int dealDataRecrod(DataRecord record)
	{

		// ��record�еĵļ�¼ת����GVideo����
		// GVideo video = new GVideo();
		// video.setContentID((String) record.get(1));
		// video.setName((String) record.get(2));
		// video.setPkgURL((String) record.get(3));
		// video.setPkgPICURL1((String) record.get(4));
		// video.setPkgPICURL2((String) record.get(5));
		// video.setPkgPICURL3((String) record.get(6));
		// video.setPkgPICURL4((String) record.get(7));
		// video.setSortnumber(Integer.parseInt((String) record.get(8)));
		// video.setChangetype((String) record.get(9));
		// video.setCreateDate(PublicUtil.getCurDateTime());
		// video.setMarketDate(PublicUtil.getCurDateTime());
		String pkgid = (String) record.get(1);
		String pkgName = (String) record.get(2);
		String feeStr = (String) record.get(3);
		int feeint = Integer.valueOf(feeStr).intValue();
		Integer	 fee = new Integer(feeint*10);//���������洢�۸���Ϣ
		// ����ID��ֱ���û�������������
		// video.setId("v" + video.getContentID());

		// Ϊ��ȷ��ϵͳ�����������ͬһ��Ʒ��ID�Ƿ������ϵͳ��

		String insertSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.dealDataRecrod.insert";
		String updateSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.dealDataRecrod.update";
		Object[] paras = { pkgName, fee, pkgid };
		int updateresult = 0;
		try
		{
			updateresult = DB.getInstance().executeBySQLCode(updateSqlCode, paras);
			if (updateresult == 0)
			{
				// ���½����Ϊ0,�����
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);

			}
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			logger.error("ִ�л�����Ƶ���ݲ���ʧ��" + e);
			e.printStackTrace();
			return DataSyncConstants.FAILURE_ADD;
		}

		return DataSyncConstants.SUCCESS_ADD;
	}

	public void init(DataSyncConfig config)
	{
//		try
//		{
//			contentRoot = (Category) Repository.getInstance().getNode(
//					RepositoryConstants.ROOT_CONTENT_ID,
//					RepositoryConstants.TYPE_CATEGORY);
//			videoRoot = (Category) Repository.getInstance().getNode(
//					RepositoryConstants.ROOT_CATEGORY_GVIDEO_ID,
//					RepositoryConstants.TYPE_CATEGORY);
//			categoryRoot=(Category) Repository.getInstance().getNode(
//					RepositoryConstants.ROOT_CATEGORY_ID,
//					RepositoryConstants.TYPE_CATEGORY);
//		} catch (BOException e)
//		{
//			logger.error(e);
//		}

	}

	public void clearDirtyData()
	{
	}

	public void prepareData()
	{
		
	}
}
