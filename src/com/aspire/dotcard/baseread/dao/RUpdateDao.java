package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.BookUpdateVO;

public class RUpdateDao
{
    protected static JLogger log = LoggerFactory.getLogger(RUpdateDao.class);

    private static RUpdateDao instance = new RUpdateDao();

    public synchronized static RUpdateDao getInstance()
    {

        return instance;
    }

    private RUpdateDao()
    {
    }

    /**
     * ����ͼ�����
     * 
     * @param type
     * @throws Exception
     */
    public void addUpdate(BookUpdateVO update) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add RUpdateDao(" + update + ")");
        }
        // insert into T_RB_STATISTICS_NEW (Contentid, READERNUM, FLOWERSNUM,
        // CLICKNUM, FAVORITESNUM, ORDERNUM, VOTENUM) values (?,?,?,?,?,?,?)
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.addUpdate";

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { update.getContentId(), update.getUpdatTime() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ���� �޸�
     * 
     * @param type
     * @throws Exception
     */
    public void update(BookUpdateVO update) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update RUpdateDao(" + update + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.update";
        
        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { update.getUpdatTime(), update.getContentId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ����Ƿ����
     * 
     * @param type
     * @return false������ true����
     * @throws Exception
     */
    public boolean isExist(BookUpdateVO update) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.RUpdateDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { update.getContentId()};
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

}
