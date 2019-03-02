
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.RCountVO;

public class RCountDao
{
    protected static JLogger log = LoggerFactory.getLogger(RCountDao.class);

    private static RCountDao instance = new RCountDao();

    public synchronized static RCountDao getInstance()
    {

        return instance;
    }

    private RCountDao()
    {
    }

    /**
     * ����ͼ�����
     * 
     * @param type
     * @throws Exception
     */
    public void addCount(RCountVO count) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("add RCountDao(" + count + ")");
        }
        // insert into T_RB_STATISTICS_NEW (Contentid, READERNUM, FLOWERSNUM,
        // CLICKNUM, FAVORITESNUM, ORDERNUM, VOTENUM) values (?,?,?,?,?,?,?)
        String sqlCode = "com.aspire.dotcard.baseread.dao.RCountDao.addCount";

        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { count.getCountId(), new Integer(count.getReadNum()),
                        new Integer(count.getFlowersNum()),
                        new Integer(count.getClickNum()),
                        new Integer(count.getFavoritesNum()),
                        new Integer(count.getOrderNum()),
                        new Integer(count.getVoteNum()) };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ���� �޸�
     * 
     * @param type
     * @throws Exception
     */
    public void updateCount(RCountVO count) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug("update RCountDao(" + count + ")");
        }
        String sqlCode = "com.aspire.dotcard.baseread.dao.RCountDao.updateCount";
        
        // ������sql�����Ҫ�滻�Ĳ���,
        Object[] paras = { new Integer(count.getReadNum()),
                        new Integer(count.getFlowersNum()),
                        new Integer(count.getClickNum()),
                        new Integer(count.getFavoritesNum()),
                        new Integer(count.getOrderNum()),
                        new Integer(count.getVoteNum()), count.getCountId() };
        DB.getInstance().executeBySQLCode(sqlCode, paras);
    }

    /**
     * ����Ƿ����
     * 
     * @param type
     * @return false������ true����
     * @throws Exception
     */
    public boolean isExist(RCountVO countVo) throws Exception
    {
        int count = 0;
        String sqlCode = "com.aspire.dotcard.baseread.dao.RCountDao.isExist";

        ResultSet rs = null;
        try
        {
            Object[] paras = { countVo.getCountId() };
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
     * ���ڲ�ѯ����ͳ������
     * @return
     * @throws Exception
     */
    public Set queryAllCount() throws Exception
    {
    	// select t.contentid from t_rb_statistics_new t
        String sqlCode = "com.aspire.dotcard.baseread.dao.RCountDao.queryAllCount";
        ResultSet rs = null;
        Set set = new HashSet();
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
                set.add(rs.getString("contentid"));
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
        return set;
    }

}
