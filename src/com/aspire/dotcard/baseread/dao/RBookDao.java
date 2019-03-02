
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.RBookVO;

public class RBookDao
{

    protected static JLogger log = LoggerFactory.getLogger(RBookDao.class);

    private static RBookDao instance = new RBookDao();

    private RBookDao()
    {

    }

    public static RBookDao getInstance()
    {
        return instance;
    }

    /**
     * ����
     * 
     * @param book
     * @throws Exception
     */
    public void add(RBookVO book) throws Exception
    {
		if (log.isDebugEnabled())
		{
			log.debug("add Book(" + book.getBookName() + ")");
		}
		try
		{
			if (!exist(book))
			{
				// insert into t_rb_book_new (bookid, bookname, keyword,
				// longrecommend, shortrecommend, description, authorid, typeid,
				// intime, chargetype, fee,
				// isfinish,WORDCOUNT,CHAPTERCOUNT,FREECHAPTERCOUNT,BOOKPIC) values (?, ?, ?, ?,
				// ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)
				String sqlCode = "com.aspire.dotcard.baseread.dao.RBookDao.addBook";

				// ������sql�����Ҫ�滻�Ĳ���,
				Object[] paras =
				{ book.getBookId(), book.getBookName(), book.getKeyWord(),
						book.getLongRecommend(), book.getShortRecommend(),
						book.getDescription(), book.getAuthorId(),
						book.getTypeId(), book.getInTime(),
						new Integer(book.getChargeType()),
						new Integer(book.getFee()), book.getIsFinish(),
						new Integer(book.getSize()),
						new Integer(book.getChapterCount()),
						 new Integer(book.getFreeChapterCount()),//����½���,add by 20140122
					        
						book.getBookPic() };
				DB.getInstance().executeBySQLCode(sqlCode, paras);
			}
			else
			{
				update(book);
			}
		}
		catch (Exception e)
		{
			log.error("���ݿ����ʧ�ܣ�ͼ�飺" + book.getBookName() + ","
					+ book.getBookId(), e);
			throw e;
		}

	}

    /**
	 * �޸�
	 * 
	 * @param book
	 * @throws Exception
	 */
    public void update(RBookVO book) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update Book(" + book + ")");
        }
        try
        {
            String sqlCode = "com.aspire.dotcard.baseread.dao.RBookDao.updateBook";

            // ������sql�����Ҫ�滻�Ĳ���,
            Object[] paras = { book.getBookName(), book.getKeyWord(),
                            book.getLongRecommend(), book.getShortRecommend(),
                            book.getDescription(), book.getAuthorId(),
                            book.getTypeId(), book.getInTime(),
                            new Integer(book.getChargeType()),
                            new Integer(book.getFee()), book.getIsFinish(),
                            new Integer(book.getSize()),
                            new Integer(book.getChapterCount()),
                            new Integer(book.getFreeChapterCount()),//����½���,add by 20140122
                            book.getBookId() };
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ�ܣ�ͼ�飺" + book.getBookName() + ","
                      + book.getBookId(), e);
            throw e;
        }
    }

    /**
     * ɾ��
     * 
     * @param bookId
     * @throws Exception
     */
    public void delete(String bookId) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("delete Book(" + bookId + ")");
        }

        String sqlCode = "com.aspire.dotcard.baseread.dao.RBookDao.delBook";

        // ������sql�����Ҫ�滻�Ĳ���
        Object[] paras = { bookId };

        // ɾ����Ʒ����
        String delRecommendSql = "com.aspire.dotcard.baseread.dao.RBookDao.delRecommendBook";

        // ɾ���Ƽ�
        String delCateSql = "com.aspire.dotcard.baseread.dao.RBookDao.delCateBook";

        String[] sqls = new String[] { sqlCode, delRecommendSql, delCateSql };
        Object[][] p = new Object[][] { paras, paras, paras };
        try
        {
            DB.getInstance().executeMutiBySQLCode(sqls, p);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ�ܣ�ͼ��ID��" + bookId, e);
            throw e;
        }
    }

    /**
     * ���ͼ���Ƿ����
     * 
     * @param book
     * @return
     * @throws Exception
     */
    public boolean exist(RBookVO book) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.RBookDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { book.getBookId() };
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
     * ��ѯ����δɾ����ͼ����Ϣ
     * 
     * @return
     * @throws Exception
     */
    public Map<String, String> queryAllBooks() throws Exception
    {
        String sqlCode = "com.aspire.dotcard.baseread.dao.RBookDao.queryAllBooks";
        Map<String, String> m = null;
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);
            m = new HashMap<String, String>();
            while (rs.next())
            {
                m.put(rs.getString("bookid"), rs.getString("bookid"));
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
        return m;
    }
}
