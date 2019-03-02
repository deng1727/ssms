package com.aspire.ponaadmin.web.datasync;

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
	 * @throws Exception
	 */
	int dealDataRecrod(DataRecord record) throws Exception;
	/**
	 * 清楚本次同步任务后的无用的数据，保证下次任务时dataDealer保值在最初的状态。
	 */
	void clearDirtyData();

}
