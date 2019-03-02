
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
     * ����
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId(), new Integer(ref.getSortNumber()), ref.getProvince(), ref.getCity() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }

    /**
     * �޸�
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
        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { new Integer(ref.getSortNumber()),ref.getProvince(), ref.getCity(),
                        new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }

    /**
     * ɾ��
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
        // ������sql�����Ҫ�滻�Ĳ���,

        Object[] paras = { new Integer(ref.getcId()), ref.getCategoryId(),
                        ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }

    /**
     * �Ƿ����
     * 
     * @return false������ true ����
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
     * �Ƿ����
     * 
     * @return false������ true ����
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
     * ����
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

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { ref.getCategoryId(), ref.getCateName(),
                        ref.getBookId(), new Integer(ref.getSortNumber()) };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }

    /**
     * �޸�
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
        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { new Integer(ref.getSortNumber()), ref.getCateName(),
                        ref.getCategoryId(), ref.getBookId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }
    
    /**
     * �޸�
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
        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { ref.getCategoryId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (Exception e)
        {
            log.error("���ݿ����ʧ��", e);
            throw e;
        }
    }
    
    /**
     * ��ѯ����ͼ����Ʒ��Ϣ
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
