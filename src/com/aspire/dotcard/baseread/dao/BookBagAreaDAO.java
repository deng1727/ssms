package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.BookBagAreaVO;

public class BookBagAreaDAO
{
	protected static JLogger log = LoggerFactory
			.getLogger(BookBagAreaDAO.class);
	
	private static BookBagAreaDAO instance = new BookBagAreaDAO();
	
	public synchronized static BookBagAreaDAO getInstance()
	{
		
		return instance;
	}
	
	private BookBagAreaDAO()
	{}
	
	/**
	 * 用来新增书包地域信息
	 * 
	 * @param bookBagArea
	 * @return
	 * @throws DAOException
	 */
	public int addBookBagArea(BookBagAreaVO bookBagArea) throws DAOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("add addBookBagArea(" + bookBagArea + ")");
		}
		// insert into t_rb_bookbag_area_new(bagid, bagname, viewbagname, province, city) values (?,?,?,?,?)
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.addBookBagArea";
		
		// 定义在sql语句中要替换的参数,
		Object[] paras = { bookBagArea.getBookBagId(),
				bookBagArea.getBookBagName(), bookBagArea.getViewBagName(),
				bookBagArea.getProvince(), bookBagArea.getCity() };
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * 用来改变书包地域信息的数据状态值
	 * @param status start：开始状态，end：结束状态。
	 * @return 变更条数
	 * @throws DAOException
	 */
	public int updateBookBagAreaByStart(String status) throws DAOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("update updateBookBagAreaByStart(" + status + ")");
		}
		
		String sqlCode = null ;
		
		if("start".equals(status))
		{
			// update t_rb_bookbag_area_new a set a.status = '1'
			sqlCode = "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.updateBookBagArea.start";
		}
		else if ("end".equals(status))
		{
			// update t_rb_bookbag_area_new a set a.status = '0' where a.status = '1'
			sqlCode = "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.updateBookBagArea.end";
		}
		
		// 定义在sql语句中要替换的参数,
		return DB.getInstance().executeBySQLCode(sqlCode, null);
	}
	
	/**
	 * 用于同步时最后一步删除失效数据
	 * @param type
	 * @return
	 * @throws DAOException
	 */
	public int delBookBagAreaByStart(String type) throws DAOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("del delBookBagAreaByStart(" + type + ")");
		}
		
		// delete from t_rb_bookbag_area_new a where a.status = ?
		String sqlCode =  "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.updateBookBagArea";
		
		// 定义在sql语句中要替换的参数,
		return DB.getInstance().executeBySQLCode(sqlCode, new Object[]{type});
	}
	
	/**
	 * 查看当前库中插入新增数据数量值
	 * @return 当前表中新增数据总量值
	 * @throws DAOException
	 */
	public int queryCountBookBagArea() throws DAOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("query queryCountBookBagArea( )");
		}
		
		// select count(1) count from t_rb_bookbag_area_new a where a.status = '0'
		String sqlCode =  "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.queryCountBookBagArea";
        ResultSet rs = null;
        int count = 0;
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if (rs.next())
            {
                count = rs.getInt("count");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败", e);
            throw new DAOException("数据库操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return count;
	}
	
	public Map<String,String> queryAreaMap(String type) throws DAOException
	{
		if (log.isDebugEnabled())
		{
			log.debug("query queryProvinceMap( )");
		}
		
		// select * from T_RB_AREA_NEW a 
		String sqlCode =  "com.aspire.dotcard.baseread.dao.BookBagAreaDAO.queryProvinceMap";
        Map<String,String> areaMap = new HashMap<String,String>();
        ResultSet rs = null;
        
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            if("province".equals(type))
            {
                while (rs.next())
                {
                	areaMap.put(rs.getString("provinceId"), rs.getString("mmprovinceId"));
                }
            }
            else if ("city".equals(type))
            {
                while (rs.next())
                {
                	areaMap.put(rs.getString("cityid"), rs.getString("mmcityid"));
                }
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败", e);
            throw new DAOException("数据库操作失败，" + e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return areaMap;
	}
}
