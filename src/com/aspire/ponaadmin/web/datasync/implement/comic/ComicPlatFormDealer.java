/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 7, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * @author dongke
 *
 */
public class ComicPlatFormDealer implements DataDealer {

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#prepareData()
	 */
	public void prepareData() throws Exception {
//		删除之前的所有动漫频道支持平台数据
		ComicPlatFormDAO.getInstance().delAllComicPlatForm();
	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#dealDataRecrod(com.aspire.ponaadmin.web.datasync.DataRecord)
	 */
	public int dealDataRecrod(DataRecord record) throws Exception {
	
		
		String comicPlatFormId = (String) record.get(1);
		if(null == comicPlatFormId){
			comicPlatFormId = "";	
		}
		boolean ispfIdexist = ComicPlatFormDAO.getInstance().isComicPlatFormIDExist(comicPlatFormId);
		
		int res = DataSyncConstants.FAILURE_ADD;
//		该comicPlatFormId 数据库不存在则添加，否则不添加，防止重复添加
		if(!ispfIdexist){
		  res =	ComicPlatFormDAO.getInstance().addComicPlatForm(comicPlatFormId);	
		}		
		return res;
	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataDealer#clearDirtyData()
	 */
	public void clearDirtyData() {


	}

	/* (non-Javadoc)
	 * @see com.aspire.ponaadmin.web.datasync.DataSyncBuilder#init(com.aspire.ponaadmin.web.datasync.DataSyncConfig)
	 */
	public void init(DataSyncConfig config) throws Exception {


	}

}
