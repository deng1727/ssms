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
 * 对解析后的视频数据进行处理的BO类
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
	 * 日志引用
	 */
	private static final JLogger logger = LoggerFactory.getLogger(VideoDealer.class);
//	private static Category contentRoot;// 用于缓存
//	private static Category videoRoot;// 用于缓存
//	private static Category categoryRoot;// 用于缓存

	public int dealDataRecrod(DataRecord record)
	{

		// 将record中的的记录转换成GVideo对象
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
		Integer	 fee = new Integer(feeint*10);//按照厘来存储价格信息
		// 设置ID，直接用户区分内容类型
		// video.setId("v" + video.getContentID());

		// 为了确保系统不会出错，检验同一产品包ID是否存在于系统中

		String insertSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.dealDataRecrod.insert";
		String updateSqlCode = "com.aspire.ponaadmin.web.datasync.implement.video.dealDataRecrod.update";
		Object[] paras = { pkgName, fee, pkgid };
		int updateresult = 0;
		try
		{
			updateresult = DB.getInstance().executeBySQLCode(updateSqlCode, paras);
			if (updateresult == 0)
			{
				// 更新结果集为0,则插入
				DB.getInstance().executeBySQLCode(insertSqlCode, paras);

			}
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			logger.error("执行基地视频数据插入失败" + e);
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
