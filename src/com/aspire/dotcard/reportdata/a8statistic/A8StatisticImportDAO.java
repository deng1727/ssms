package com.aspire.dotcard.reportdata.a8statistic;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * 
 * 更新货架统计信息
 * @author zhangwei
 *
 */
public class A8StatisticImportDAO
{
	 /**
     * 日志引用reportimp.client
     */
    protected static JLogger logger = LoggerFactory.getLogger(A8StatisticImportDAO.class);
    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	private static A8StatisticImportDAO DAO=new A8StatisticImportDAO();
	public static A8StatisticImportDAO getInstance()
	{
		return DAO;
	}
	/**
	 * 更新货架统计信息
	 * @param categoryId 货架编码
	 * @param count 统计数据
	 * @throws DAOException
	 */
	public int updateCategoryStatistic(String categoryId,long count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateCategoryStatistic(" + categoryId +','+count+ ")");
        }
        String sqlCode="reportdata.a8statistic.updateCategoryStatistic.UPDATE";
        Object paras[]= {new Long(count),categoryId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {
        	REPORT_LOG.error("不存在该货架编码："+categoryId+",该条数据导入失败");
        	logger.error("不存在该货架编码："+categoryId+",该条数据导入失败");
        }
        return result;
       
	}

}
