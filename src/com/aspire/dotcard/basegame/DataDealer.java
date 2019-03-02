package com.aspire.dotcard.basegame;

import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncBuilder;


public interface DataDealer extends DataSyncBuilder
{
	/**
	 * 本次任务同步前，需要做数据的准备工作。
	 */
	void prepareData()throws Exception;
	/**
	 * 保存数据到货架系统中
	 * @param record
	 * @return 返回值为整数，1表示新增，2表示更新，3表示删除
	 */
	int dealDataRecrod(DataRecord record);
	/**
	 * 清楚本次同步任务后的无用的数据，保证下次任务时dataDealer保值在最初的状态。
	 */
	void clearDirtyData();
	
	/**为了支持删除旧数据。add by aiyan 2012-09-05
	 * @throws Exception */
	void delOldData() throws Exception;

}
