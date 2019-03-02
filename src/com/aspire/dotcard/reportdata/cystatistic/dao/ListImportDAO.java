package com.aspire.dotcard.reportdata.cystatistic.dao;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author wangminlong
 * @version 
 */
public class ListImportDAO
{
	 /**
     * 日志引用reportimp.client
     */
    protected static JLogger logger = LoggerFactory.getLogger(ListImportDAO.class);
    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	private static ListImportDAO DAO=new ListImportDAO();
	public static ListImportDAO getInstance()
	{
		return DAO;
	}

	/**
	 * @param time
	 * @param contentid
	 * @param count
	 * @param type
	 * @return
	 * @throws DAOException
	 */
	public int insertToListStatistic(String time,String contentid,long count,int type)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("insertToListStatistic(" + time +','+contentid+ ','+count+','+type+")");
        }
        String sqlCode="reportdata.TopListImportDAO.insertToListStatistic.INSERT";
        Object paras[]= {time,contentid,new Long(count),new Integer(type)};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {
        	REPORT_LOG.error("不存在该contentid："+contentid+",该条数据导入失败");
        	logger.error("不存在该contentid："+contentid+",该条数据导入失败");
        }
        return result;
       
	}


    /**
     * @param type
     * @throws DAOException
     */
    public void deleteToListStatistic(int type)throws DAOException
    {
        
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteToListStatistic(" +type+")");
        }
        String sqlCode="reportdata.TopListImportDAO.deleteToListStatistic.DElETE";
        Object paras[]= {new Integer(type)};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteToListStatistic:"+"共删除"+result+"条数据");
        }
       
    }
}
