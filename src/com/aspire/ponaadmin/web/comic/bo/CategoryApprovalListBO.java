package com.aspire.ponaadmin.web.comic.bo;

import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.comic.dao.CategoryApprovalListDAO;

public class CategoryApprovalListBO {
	


	private final static JLogger LOGGER = LoggerFactory.getLogger(CategoryApprovalListBO.class);
	
	private static CategoryApprovalListBO instance = new CategoryApprovalListBO();
	private CategoryApprovalListBO(){
	}
	
	public static CategoryApprovalListBO getInstance(){
		return instance;
	}
	
	/**
	 * ��ҳ��ѯ���ܷ����б�
	 * @param page
	 */
	public void queryCategoryApprovalList(PageResult page,Map map) throws BOException{
		if (LOGGER.isDebugEnabled())
        {
			LOGGER.debug("queryCategoryApprovalList( )") ;
        }
		try {
			CategoryApprovalListDAO.getInstance().queryCategoryApprovalList(page,map);
		} catch (DAOException e) {
			throw new BOException("���ܷ��������������---��ҳ��ѯ���ܷ����б�",e);
		}
	}

}
