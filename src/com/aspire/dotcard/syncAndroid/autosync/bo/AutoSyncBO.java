package com.aspire.dotcard.syncAndroid.autosync.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.autosync.dao.AutoSyncDAO;
import com.aspire.dotcard.syncAndroid.autosync.vo.AutoVO;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class AutoSyncBO
{
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AutoSyncBO.class);
	
	private static AutoSyncBO instance = new AutoSyncBO();
	
	private AutoSyncBO()
	{}
	
	/**
	 * 得到单例模式
	 * 
	 */
	public static AutoSyncBO getInstance()
	{
		return instance;
	}
	
	/**
	 * 执行单个货架发送数据中心更新消息
	 * 
	 * @param autoVO
	 * @return
	 */
	public String exceAutoCategory(AutoVO autoVO)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("向数据中心同步自动更新货架结束...");
		}
		
		// 当货架为空时不通知对方
		if ("1".equals(autoVO.getIsNullByAuto()))
		{
			// 查询当前货架下是否存在商品
			if (AutoSyncDAO.getInstance().hasRef(autoVO.getCategoryId()))
			{
				exce(autoVO);
			}
			else
			{
				// 当前货架不存在商品
				return "当前货架不存在商品，按当前情况，不用发送通知消息！";
			}
		}
		else
		{
			exce(autoVO);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("向数据中心同步自动更新货架结束...");
		}
		
		return "生成消息成功！";
	}
	
	/**
	 * 生成待发送数据中心的消息
	 * @param autoVO
	 * @throws BOException 
	 */
	public void exce(AutoVO autoVO) 
	{
		// 生成事务id
		String transactionID = ContextUtil.getTransactionID();
		
		// 货架变更消息接口
		String cid = null;	
			cid = AutoSyncDAO.getInstance().getCategoryCId(autoVO.getCategoryId());
		 if(cid !=  null && !cid.equals("")){
			 PPMSDAO.addMessagesStatic(MSGType.CatogoryModifyReq, transactionID,
						cid+":1"); 
		 }else{
			 logger.error("cid = SSMSDAO.getCategoryCId(autoVO.getCategoryId()) is null"); 
		 }
		
		// 全量货架上下架更新接口
		PPMSDAO.addMessagesStatic(MSGType.BatchRefModifyReq, transactionID,
				autoVO.getCategoryId());
	}
}
