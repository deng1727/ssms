package com.aspire.dotcard.baseVideoNew.sync;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;

public class UpdateIsshowFieldTask {
	protected static JLogger logger = LoggerFactory
	.getLogger(UpdateIsshowFieldTask.class);
	public void updateIsshowField()
	throws BOException, DAOException{
	if (logger.isDebugEnabled())
	{
		logger.debug("���ݼ��������ֶ�isshow����������ָ�����ݼ�Ϊ��,��ʼ");
	}
	String sqlCode = "baseVideo.dao.VideoReferenceDAO.updateIsshowFieldList().UPDATE";
    String sql = null;
    int ret1 = -1;
    int ret2 = -1;
	try
	{
        sql = SQLCode.getInstance().getSQLStatement(sqlCode);
		//int ret = DB.getInstance().executeBySQLCode(sqlCode, null);
		 ret1 = DB.getInstance().execute(sql, null);

	}
	catch (DAOException e)
	{
		logger.error("��������ָ�����ݼ�(isShow�ֶ�)Ϊ��ʱ�����쳣�����ݿ����ʧ�ܣ�", e);
		throw new BOException("��������ָ�����ݼ�(isShow�ֶ�)Ϊ��ʱ�����쳣", e);
	} catch (DataAccessException e) {
        throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                e);
}
	if (logger.isDebugEnabled())
	{
		logger.debug("��������"+ret1+"��ָ�����ݼ�(isShow�ֶ�)Ϊ��,����");
	}	
	String sqlnameCode = "baseVideo.dao.VideoReferenceDAO.updateCnodeNameFieldList().UPDATE";
    String sqlname = null;
    String sqlname2Code = "baseVideo.dao.VideoReferenceDAO.updateCnodeNameFieldList2().UPDATE";
    String sqlname2 = null;
    
	try
	{
		sqlname = SQLCode.getInstance().getSQLStatement(sqlnameCode);
		//int ret = DB.getInstance().executeBySQLCode(sqlCode, null);
		 ret2 = DB.getInstance().execute(sqlname, null);
		 sqlname2 = SQLCode.getInstance().getSQLStatement(sqlname2Code);
			//int ret = DB.getInstance().executeBySQLCode(sqlCode, null);
			 ret2 += DB.getInstance().execute(sqlname2, null);

	}
	catch (DAOException e)
	{
		logger.error("��������ָ�����ݼ�(nodebyname�ֶ�)Ϊ��ʱ�����쳣�����ݿ����ʧ�ܣ�", e);
		throw new BOException("��������ָ�����ݼ�(nodebyname�ֶ�)Ϊ��ʱ�����쳣", e);
	} catch (DataAccessException e) {
        throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                e);
}	
	
	if (logger.isDebugEnabled())
	{
		logger.debug("��������"+ret2+"ָ�����ݼ�(nodebyname�ֶ�)Ϊ��,����");
	}	
	}
}
