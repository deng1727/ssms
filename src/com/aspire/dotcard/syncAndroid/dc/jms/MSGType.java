package com.aspire.dotcard.syncAndroid.dc.jms;

public enum MSGType {
	/**
	 * 货架应用数据变更通知接口
	 */
	ContentModifyReq,
	/**
	 * 货架信息变更接口
	 */
	CatogoryModifyReq,
	/**
	 * 上下架信息变更接口
	 */
	RefModifyReq,
	/**
	 * 榜单数据更新接口
	 */
	CountUpdateReq,
	/**
	 * 事务提交接口
	 */
	CommitReq,
	/**
	 * 全量货架上下架更新接口
	 */
	BatchRefModifyReq
}
