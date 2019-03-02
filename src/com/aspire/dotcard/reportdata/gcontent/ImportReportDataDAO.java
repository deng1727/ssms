package com.aspire.dotcard.reportdata.gcontent;

import java.sql.ResultSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.reportdata.TopDataConstants;

public class ImportReportDataDAO
{
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ImportReportDataDAO.class);

	protected static JLogger topDatalog = LoggerFactory
			.getLogger("reportimp.client");

	private static ImportReportDataDAO dao = new ImportReportDataDAO();

	private ImportReportDataDAO()
	{
	}

	/**
	 * singleton模式,获取实例 
	 * @return 实例
	 */
	public static final ImportReportDataDAO getInstance()
	{

		return dao;
	}
	
    /**
     * 更新数据到数据库
     */
    public void updateProductValues(ImportReportDataVO vo,Integer[] result)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateProductValues(" + vo.toString() + ")");
        }
        if(null == vo)
        {
        	logger.error("导入的ImportReportDataVO对象为null.");
        	topDatalog.error("导入的ImportReportDataVO对象为null.");
        	return;
        }
        String sqlCode;
        try
        {
        	//初始化统计数据
        	if(result[1] == null){
        		result[1] = new Integer(0);
        		
        	}
//        	初始化统计数据
        	if(result[2] == null){
        		result[2] = new Integer(0);
        		
        	}
            int updateCount= 0;
        	switch(vo.getType())
        	{
        		case TopDataConstants.CONTENT_TYPE_DAY:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEDAY";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    //new Long(vo.getAvgScoreCount()),  去除从报表取值，从门户表 t_pps_comment_grade计算
                                    new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    new Long(vo.getAdd7DaysOrderCount()),
                                    new Long(vo.getAdd30DaysOrderCount()),
                                    new Long(vo.getDownLoadCount()),
                                    vo.getContentId() };
                    
                    int c1 = DB.getInstance().executeBySQLCode(sqlCode, paras);
                    int c2 = 0;
                    if(c1==0){
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEDAY_CY";
                    Object paras1[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    //new Long(vo.getAvgScoreCount()),  去除从报表取值，从门户表 t_pps_comment_grade计算
                                    new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    new Long(vo.getAdd7DaysOrderCount()),
                                    new Long(vo.getAdd30DaysOrderCount()),
                                    new Long(vo.getDownLoadCount()),
                                    vo.getContentId() };
                    
                    c2 = DB.getInstance().executeBySQLCode(sqlCode, paras1);     
                    }
                    updateCount = c1 + c2;
	        		break;
        		}
        		case TopDataConstants.CONTENT_TYPE_WEEK:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEWEEK";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    vo.getContentId() };
                    
                    updateCount = DB.getInstance().executeBySQLCode(sqlCode, paras);
	        		break;
        		}
        		case TopDataConstants.CONTENT_TYPE_MONTH:
        		{
	        		sqlCode = "Reportdata.ImportReportDataDAO.updateProductValues().UPDATEMONTH";
                    Object paras[] = { new Long(vo.getSearchCount()),
                                    new Long(vo.getPvCount()),
                                    new Long(vo.getSubsCount()),
                                    new Long(vo.getRemarkCount()),
                                    new Long(vo.getScoreCount()),
                                    new Long(vo.getCommendCount()),
                                    new Long(vo.getCollectionCount()),
                                    vo.getContentId() };
                    
                    updateCount = DB.getInstance().executeBySQLCode(sqlCode, paras);
	        		break;
        		}
        		default:
	        		topDatalog.error("日内容运营数据导入错误:无效的导入周期类型:" + vo.getType());
	        		return ;
        	}
        	
            if(updateCount == 0)
            {
            	topDatalog.error("保存数据失败，指定的内容id：" + vo.getContentId() + "和产品名称:" + vo.getContentName() +" 不存在！");
            	result[2] = new Integer(result[2].intValue() + 1);
            }else{
            	//执行成功
            	result[1] = new Integer(result[1].intValue() + 1);
            }
        }
        catch (Exception ex)
        {
            topDatalog.error("保存到数据库失败，内容id：" + vo.getContentId() + " 和产品名称:" + vo.getContentName());
            
            throw new DAOException(ex);
        }
    }
    
    /**
     * 取得最近一次成功导入数据的日期值
     * @param type
     * @return
     */
    public String queryLastDate(int type) throws DAOException
    {
        String sqlCode = "Reportdata.TopDataDAO.updateLastDate().SELECT";
        ResultSet rs = null;
        Object[] paras = { new Integer(type) };
        String result = "";
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if (rs != null && rs.next())
            {
                result = rs.getString(1);
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("queryLastDate error!!", ex);
        }
        finally
        {
            DB.close(rs);
        }
        
        return result;
    }
    
    /**
     * 更新最近一次成功导入数据的日期值,如果表中没有记录,就新增一条
     * @param type
     * @return
     */
    public void updateLastDate(int type,String lastDate) throws DAOException
    
    {
        String sqlCode = "Reportdata.TopDataDAO.updateLastDate().UPDATE";
        Object[] paras = {lastDate ,new Integer(type)};
        int result = 0;
        try
        {
            result = DB.getInstance().executeBySQLCode(sqlCode, paras);
            if(result == 0)
            {
                //如果没有更新数据,就插入一条记录
                sqlCode = "Reportdata.TopDataDAO.updateLastDate().INSERT";
                Object[] paras2 = {new Integer(type),lastDate};
                DB.getInstance().executeBySQLCode(sqlCode, paras2);
            }
        }
        catch (Exception ex)
        {
            throw new DAOException("updateLastDate error!!", ex);
        }        
    }
}
