package com.aspire.ponaadmin.web.auditManagement.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.auditManagement.dao.ChanelsInfoDao;

public class ChannelsInfoBO {

	protected static final JLogger logger = LoggerFactory
			.getLogger(ChannelsInfoBO.class);

	private static ChannelsInfoBO instance = new ChannelsInfoBO();

	private ChannelsInfoBO() {
	}

	/**
	 * »ñÈ¡ÊµÀý
	 */
	public static ChannelsInfoBO getInstance() {
		return instance;
	}

	public void channelsInfoList(PageResult page) throws DAOException {
		ChanelsInfoDao.getInstance().channelsInfoList(page);
	}
	
	public void toAudit(String[] categoryId,String flag){
		ChanelsInfoDao.getInstance().toAudit(categoryId, flag);
	}

}
