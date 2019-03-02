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
		logger.debug("内容集表新增字段isshow，批量更新指定内容集为是,开始");
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
		logger.error("批量更新指定内容集(isShow字段)为是时发生异常，数据库更新失败！", e);
		throw new BOException("批量更新指定内容集(isShow字段)为是时发生异常", e);
	} catch (DataAccessException e) {
        throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                e);
}
	if (logger.isDebugEnabled())
	{
		logger.debug("批量更新"+ret1+"条指定内容集(isShow字段)为是,结束");
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
		logger.error("批量更新指定内容集(nodebyname字段)为是时发生异常，数据库更新失败！", e);
		throw new BOException("批量更新指定内容集(nodebyname字段)为是时发生异常", e);
	} catch (DataAccessException e) {
        throw new DAOException("从sql.properties中获取sql语句时发生错误，对应sqlCode不存在。",
                e);
}	
	
	if (logger.isDebugEnabled())
	{
		logger.debug("批量更新"+ret2+"指定内容集(nodebyname字段)为是,结束");
	}	
	}
}
