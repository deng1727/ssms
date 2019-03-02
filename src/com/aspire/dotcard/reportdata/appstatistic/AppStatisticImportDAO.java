package com.aspire.dotcard.reportdata.appstatistic;

import javax.sql.RowSet;

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
public class AppStatisticImportDAO
{
	 /**
     * 日志引用reportimp.client
     */
    protected static JLogger logger = LoggerFactory.getLogger(AppStatisticImportDAO.class);
    /**
     * 报表专用日志
     */
    protected static JLogger REPORT_LOG = LoggerFactory.getLogger("reportimp.client");
	private static AppStatisticImportDAO DAO=new AppStatisticImportDAO();
	public static AppStatisticImportDAO getInstance()
	{
		return DAO;
	}
	
	
	
	
	/**
	 * 更新应用打分
	 * @param contentId 内容ID
	 * @param count 统计数据
	 * @throws DAOException
	 */
	public int updateAppBaseStatistic(String updateSql,String insertSql,String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode=updateSql;//"reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//更新返回为0，没有相关记录，插入数据
        	 sqlCode = insertSql;//"reportdata.applaststatistic.updateAppLastStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result=1;
        }
        return result;
       
	}
	

	/**
	 * 更新应用打分
	 * @param contentId 内容ID
	 * @param count 统计数据
	 * @throws DAOException
	 */
	public int updateAppBaseStatistic(String updateSql,String insertSql,Object updateparas[],Object insertparas[])throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + updateparas +','+insertparas+ ")");
        }
        String sqlCode=updateSql;
        int result=DB.getInstance().executeBySQLCode(sqlCode, updateparas);
        if(result<1)
        {//更新返回为0，没有相关记录，插入数据
        	 sqlCode = insertSql;
        	 result=DB.getInstance().executeBySQLCode(sqlCode, insertparas);
        }
        return result;
       
	}
	/**
	 * 更新最新应用打分
	 * @param contentId 内容ID
	 * @param count 统计数据
	 * @throws DAOException
	 */
	public int updateAppLastStatistic(String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode="reportdata.applaststatistic.updateAppLastStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//更新返回为0，没有相关记录，插入数据
        	 sqlCode="reportdata.applaststatistic.updateAppLastStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result=1;
        }
        return result;
       
	}

	/**
	 * 更新推荐应用打分
	 * @param contentId 内容ID
	 * @param count 统计数据
	 * @throws DAOException
	 */
	public int updateAppRecommondStatistic(String contentId,String time,double count)throws DAOException
	{
		
		if (logger.isDebugEnabled())
        {
            logger.debug("updateAppLastStatistic(" + contentId +','+count+ ")");
        }
        String sqlCode="reportdata.apprecommondstatistic.updateAppRecommondStatistic.UPDATE";
        Object paras[]= {new Double(count),time,contentId};
        int result=DB.getInstance().executeBySQLCode(sqlCode, paras);
        if(result<1)
        {//更新返回为0，没有相关记录，插入数据
        	 sqlCode="reportdata.apprecommondstatistic.updateAppRecommondStatistic.INSERT";
        	 Object  paras2[] = {new Double(count),time,contentId};
            DB.getInstance().executeBySQLCode(sqlCode, paras2);
            result= 1;
        }
        return result;
       
	}
    
    /**
     * 全量更新小编推荐数据（dblink查询更新）
     * @return 更新多少条记录
     * @throws DAOException
     */
    public int updateRecommendDate()throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateRecommendDate()");
        }
        String sqlCode = "reportdata.apprecommondstatistic.updateRecommendDate.UPDATE";
        int result = 0;
        Object paras[] = {};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
        
        sqlCode = "reportdata.apprecommondstatistic.insertRecommendDate.INSERT";
        result = DB.getInstance().executeBySQLCode(sqlCode, paras);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("小编推荐插入新数据" + result + "条");
        }

        sqlCode = "reportdata.apprecommondstatistic.queryRecommendDate.SELECT";
        RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
        
        try
        {
            if (rs.next())
            {
                result = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            logger.error("updateRecommendDate() 得到推荐数据信息出错", e);
            throw new DAOException(e);
        }finally{
        	//ADD by tungke for close con & rs
        	DB.close(rs);
        }

        return result;
    }
    
}
