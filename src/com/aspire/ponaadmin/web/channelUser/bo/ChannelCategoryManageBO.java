package com.aspire.ponaadmin.web.channelUser.bo;


import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.awms.CategoryVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.channelUser.dao.ChannelCategoryManageDAO;
import com.aspire.ponaadmin.web.repository.Category;

public class ChannelCategoryManageBO {

	private final static JLogger logger = LoggerFactory.getLogger(ChannelCategoryManageBO.class);
	private static ChannelCategoryManageBO instance = new ChannelCategoryManageBO();
	
	private ChannelCategoryManageBO(){
		
	}
	public static ChannelCategoryManageBO getInstance(){
		 return instance;
	}
	
	/**
	 * 根据渠道商账号，查询货架列表。
	 * @param ChannelNO
	 * @return
	 */
	public void getCategoryListByChannelNO(PageResult page,String channelNO) throws BOException{
		if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryListByChannelNO(" + channelNO + ")") ;
        }
		try {
			ChannelCategoryManageDAO.getInstance().getCategoryListByChannelNO(page,channelNO);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new BOException("根据渠道商账号，查询货架列表失败",e);
		}
	}
	
	public CategoryVO getCategoryInfoByID(String categoryId) throws BOException{
		if (logger.isDebugEnabled())
        {
            logger.debug("getCategoryInfoByID(" + categoryId + ")") ;
        }
		try {
			return ChannelCategoryManageDAO.getInstance().getCategoryInfoByID(categoryId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new BOException("查看货架详细",e);
		}
	}
}
