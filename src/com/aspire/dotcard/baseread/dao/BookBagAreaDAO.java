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
	 * �����������������Ϣ
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
		
		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras = { bookBagArea.getBookBagId(),
				bookBagArea.getBookBagName(), bookBagArea.getViewBagName(),
				bookBagArea.getProvince(), bookBagArea.getCity() };
		return DB.getInstance().executeBySQLCode(sqlCode, paras);
	}
	
	/**
	 * �����ı����������Ϣ������״ֵ̬
	 * @param status start����ʼ״̬��end������״̬��
	 * @return �������
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
		
		// ������sql�����Ҫ�滻�Ĳ���,
		return DB.getInstance().executeBySQLCode(sqlCode, null);
	}
	
	/**
	 * ����ͬ��ʱ���һ��ɾ��ʧЧ����
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
		
		// ������sql�����Ҫ�滻�Ĳ���,
		return DB.getInstance().executeBySQLCode(sqlCode, new Object[]{type});
	}
	
	/**
	 * �鿴��ǰ���в���������������ֵ
	 * @return ��ǰ����������������ֵ
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
            log.error("���ݿ����ʧ��", e);
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);
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
            log.error("���ݿ����ʧ��", e);
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);
        }
        finally
        {
            DB.close(rs);
        }
        
        return areaMap;
	}
}
