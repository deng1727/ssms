package com.aspire.ponaadmin.web.auditManagement.bo;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.auditManagement.dao.AuditDetailsDao;

public class AuditDetailsBO {
	
	protected static final JLogger logger = LoggerFactory
			.getLogger(AuditDetailsBO.class);

	private static AuditDetailsBO instance = new AuditDetailsBO();

	private AuditDetailsBO() {
	}

	/**
	 * »ñÈ¡ÊµÀý
	 */
	public static AuditDetailsBO getInstance() {
		return instance;
	}
	
	public void auditDetails(PageResult page,String categoryId) throws DAOException {
		AuditDetailsDao.getInstance().auditDetails(page, categoryId);
	}
	
	public void toAudit(String[] categoryId,String flag){
		AuditDetailsDao.getInstance().toAudit(categoryId, flag);
	}

}
