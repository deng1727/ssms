
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.ReferenceVO;

public class BookReferenceDao
{

    protected static JLogger log = LoggerFactory.getLogger(BookReferenceDao.class);

    private static BookReferenceDao instance = new BookReferenceDao();

    private BookReferenceDao()
    {
    }

    public static BookReferenceDao getInstance()
    {
        return instance;
    }

    /**
     * 新增
     * 
     * @param ref
     * @throws Exception
     */
    public void add(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add BookReferenceVO(" + ref + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.addArea";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId(), new Integer(ref.getSortNumber()), ref.getProvince(), ref.getCity() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }

    /**
     * 修改
     * 
     * @param ref
     * @throws Exception
     */
    public void update(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update BookReferenceVO(" + ref + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.updateArea";
        // 定义在sql语句中要替换的参数,
        Object[] paras = { new Integer(ref.getSortNumber()),ref.getProvince(), ref.getCity(),
                        new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }

    /**
     * 删除
     * 
     * @param ref
     * @throws Exception
     */
    public void delete(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("delete BookReferenceVO(" + ref + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.deleteArea";
        // 定义在sql语句中要替换的参数,

        Object[] paras = { new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }

    /**
     * 是否存在
     * 
     * @return false不存在 true 存在
     * @throws Exception
     */
    public boolean isExist(ReferenceVO ref) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { new Integer(ref.getcId()), ref.getCategoryId(),
                            ref.getBookId() };
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                count = rs.getInt("count");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * 是否存在
     * 
     * @return false不存在 true 存在
     * @throws Exception
     */
    public boolean isExistByRank(ReferenceVO ref) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.isExistByRank";

        ResultSet rs = null;
        try
        {
            Object[] paras = { ref.getCategoryId(), ref.getBookId() };
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                count = rs.getInt("count");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }

    /**
     * 新增
     * 
     * @param ref
     * @throws Exception
     */
    public void addRank(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add BookReferenceVO(" + ref + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.addRank";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { ref.getCategoryId(), ref.getCateName(),
                        ref.getBookId(), new Integer(ref.getSortNumber()) };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }

    /**
     * 修改
     * 
     * @param ref
     * @throws Exception
     */
    public void updateRank(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update BookReferenceVO(" + ref + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.updateRank";
        // 定义在sql语句中要替换的参数,
        Object[] paras = { new Integer(ref.getSortNumber()), ref.getCateName(),
                        ref.getCategoryId(), ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }
    
    /**
     * 修改
     * 
     * @param ref
     * @throws Exception
     */
    public void delRank(ReferenceVO ref) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("delRank BookReferenceVO(" + ref + ")");
        }
        
        // delete from T_RB_RANK_NEW r where r.rankid=?
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.delRank";
        // 定义在sql语句中要替换的参数,
        Object[] paras = { ref.getCategoryId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("数据库操作失败", e);
            throw e;
        }
    }
    
    /**
     * 查询所有图书商品信息
     * 
     * @return
     * @throws Exception
     */
    public Map<String, String> queryAllReferences() throws Exception
    {
    	//select r.categoryid ||','|| r.bookid as cbid from t_rb_reference_new r 
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookReferenceDao.queryAllReferences";
        Map<String, String> m = null;
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap<String, String>();
            while (rs.next())
            {
                m.put(rs.getString("cbid"), "");
            }
        }
        catch (SQLException e)
        {
            log.error("数据库操作失败");
            throw new DAOException("数据库操作失败，" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return m;
    }
}
