
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
     * ����ͼ�����
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { bookBagContent.getBookBagId(),
                        bookBagContent.getBookId(),
                        new Integer(bookBagContent.getSortId()) };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ���� �޸�
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { new Integer(bookBagContent.getSortId()),
                        bookBagContent.getBookBagId(),
                        bookBagContent.getBookId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ɾ��
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { bookBagContent.getBookBagId(),
                        bookBagContent.getBookId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ����Ƿ����
     * 
     * @param type
     * @return false������ true����
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
            log.error("���ݿ����ʧ��");
            throw new DAOException("���ݿ����ʧ�ܣ�" + e);

        }
        finally
        {
            DB.close(rs);
        }
        return count == 0 ? false : true;
    }
    
    /**
     * �����ն�Ŀ¼
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { moDirectory.getMoDirectoryId(), moDirectory.getCateId()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * �����ն�Ŀ¼
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { moDirectory.getMoDirectoryId(), moDirectory.getCateId()};
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }
}
