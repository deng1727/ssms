
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.BookBagContentVO;
import com.aspire.dotcard.baseread.vo.MoDirectoryVO;

public class BookBagContentDao
{
    protected static JLogger log = LoggerFactory.getLogger(BookBagContentDao.class);

    private static BookBagContentDao instance = new BookBagContentDao();

    public synchronized static BookBagContentDao getInstance()
    {

        return instance;
    }

    private BookBagContentDao()
    {
    }

    /**
     * 新增图书分类
     * 
     * @param type
     * @throws Exception
     */
    public void addBookBagContent(BookBagContentVO bookBagContent)
                    throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add BookBagContentVO(" + bookBagContent + ")");
        }
        // 
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.addBookBagContent";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { bookBagContent.getBookBagId(),
                        bookBagContent.getBookId(),
                        new Integer(bookBagContent.getSortId()) };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 更新 修改
     * 
     * @param type
     * @throws Exception
     */
    public void updateBookBagContent(BookBagContentVO bookBagContent)
                    throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update BookBagContentVO(" + bookBagContent + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.updateBookBagContent";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { new Integer(bookBagContent.getSortId()),
                        bookBagContent.getBookBagId(),
                        bookBagContent.getBookId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 删除
     * 
     * @param type
     * @throws Exception
     */
    public void deleteBookBagContent(BookBagContentVO bookBagContent)
                    throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("deleteBookBag BookBagContentVO(" + bookBagContent + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.deleteBookBagContent";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { bookBagContent.getBookBagId(),
                        bookBagContent.getBookId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 检查是否存在
     * 
     * @param type
     * @return false不存在 true存在
     * @throws Exception
     */
    public boolean isExist(BookBagContentVO bookBagContent) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { bookBagContent.getBookBagId(),
                            bookBagContent.getBookId() };
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
     * 新增终端目录
     * 
     * @param type
     * @throws Exception
     */
    public void addMoDirectory(MoDirectoryVO moDirectory)
                    throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add addMoDirectory(" + moDirectory + ")");
        }
        // 
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.addMoDirectory";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { moDirectory.getMoDirectoryId(), moDirectory.getCateId()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * 更新终端目录
     * 
     * @param type
     * @throws Exception
     */
    public void updateMoDirectory(MoDirectoryVO moDirectory)
                    throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update BookBagContentVO(" + moDirectory + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagContentDao.updateMoDirectory";

        // 定义在sql语句中要替换的参数,
        Object[] paras = { moDirectory.getMoDirectoryId(), moDirectory.getCateId()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }
}
